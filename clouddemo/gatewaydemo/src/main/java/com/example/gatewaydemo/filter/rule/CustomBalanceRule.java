package com.example.gatewaydemo.filter.rule;

import com.alibaba.fastjson.JSONObject;
import com.example.gatewaydemo.feigh.TestlFeignClient;
import com.google.common.hash.Hashing;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import feign.Feign;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import feign.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/26 12:20
 **/
public class CustomBalanceRule extends AbstractLoadBalancerRule {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Server choose(Object key) {//这里的key就是过滤器中传过来的noteid
        //所有可用服务器（不可变list）
        List<Server> unmodifiedServers = this.getLoadBalancer().getReachableServers();
        List<Server> servers = Collections.synchronizedList(new ArrayList<>(unmodifiedServers));

        if (servers.isEmpty()) {
            return null;
        }
        if (key == null) {
            return randomChoose(servers);
        }

        Server serverInstance = hashKeyChoose(servers, key);
        //服务器不可用时，重新选择服务器
        while (serverInstance != null) {
            // target 链接目标feign，并指定访问域名
            TestlFeignClient client = getFeignClient(TestlFeignClient.class, "http://" + serverInstance.getId());
            try {
                String resp = client.getHealthInfo();
                if (resp != null) {
                    JSONObject obj = JSONObject.parseObject(resp);
                    if ("up".equals(obj.getString("status"))) {
                        break;
                    }
                }
            } catch (Exception e) {
                logger.info("服务不可用：" + serverInstance);
            }
            logger.info("服务不可用：" + serverInstance);
            servers.remove(serverInstance);
            serverInstance = hashKeyChoose(servers, key);
        }
        logger.info("select host: " + serverInstance);
        return serverInstance;
    }

    /**
     * 随机返回一个服务实例
     *
     * @param servers
     * @return
     */
    private Server randomChoose(List<Server> servers) {
        int randomIndex = new Random().nextInt(servers.size());
        return servers.get(randomIndex);
    }

    /**
     * 一致性hash
     *
     * @param servers
     * @param key
     * @return
     */
    private Server hashKeyChoose(List<Server> servers, Object key) {
        if (servers.isEmpty()) {
            return null;
        }
        int hashCode = Math.abs(key.hashCode());
        //使用guava一致性hash
        int index = Hashing.consistentHash(hashCode, servers.size());
        return servers.get(index);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig config) {

    }

    /**
     * 统一的 feign 接口实现类获取逻辑
     */
    public static final <T> T getFeignClient(Class<T> clazz, String url) {
        T feignClient = Feign.builder()
                // 默认 http
                .client(new Client.Default(null, null))
                //3s超时,1次重试
                .retryer(new Retryer.Default(3000, 3000, 1))
                .target(clazz, url);
        return feignClient;
    }
}
