package com.example.gatewaydemo.filter.rule;

import com.google.common.hash.Hashing;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public Server choose(Object key) {//这里的key就是过滤器中传过来的nodeid
        List<Server> servers = this.getLoadBalancer().getReachableServers();
        if (servers.isEmpty()) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }

        if (key == null) {
            return randomChoose(servers);
        }
        Server serverInstance = hashKeyChoose(servers, key);
        logger.info("select host: " + serverInstance);
        return serverInstance;
    }

    /**
     * 随机返回一个服务实例
     * @param servers
     * @return
     */
    private Server randomChoose(List<Server> servers) {
        int randomIndex = new Random().nextInt(servers.size());
        return servers.get(randomIndex);
    }

    /**
     * 一致性hash
     * @param servers
     * @param key
     * @return
     */
    private Server hashKeyChoose(List<Server> servers, Object key) {
        int hashCode = Math.abs(key.hashCode());
        //if (hashCode < servers.size()) {
        //   return servers.get(hashCode);
        //}
        //int index = hashCode % servers.size();
        //使用guava一致性hash
        int index = Hashing.consistentHash(hashCode, servers.size());
        return servers.get(index);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig config) {

    }
}
