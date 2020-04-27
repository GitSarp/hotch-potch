package com.example.gatewaydemo.filter.rule;

import com.alibaba.fastjson.JSONObject;
import com.example.gatewaydemo.feigh.TestlFeignClient;
import com.google.common.base.Splitter;
import com.google.common.hash.Hashing;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import feign.Client;
import feign.Feign;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author 刘亚林
 * @description
 * @create 2020/4/24 9:43
 **/
public class CustomBalanceRuleV2 extends AbstractLoadBalancerRule {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    //之前连接的服务器,键值groupId
    private ConcurrentHashMap<String, String> prevServer;
    //之前的服务器情况,键值groupId
    private ConcurrentHashMap<String, List<String>> prevServers;

    public CustomBalanceRuleV2() {
        this.prevServer = new ConcurrentHashMap<>();
        this.prevServers = new ConcurrentHashMap<>();
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            logger.warn("no load balancer");
            return null;
        } else {
            Server server = null;
            int count = 0;
            //选择的主机索引
            int nextServerIndex = -1;

            while(true) {
                if (server == null && count++ < 10) {
                    List<Server> reachableServers = lb.getReachableServers();
                    List<Server> unmodifiedServers = lb.getAllServers();
                    List<Server> servers = new ArrayList<>(unmodifiedServers);
                    List<Server> allServers = servers.stream().sorted(Comparator.comparing(Server::getId)).collect(Collectors.toList());

                    int upCount = reachableServers.size();
                    int serverCount = allServers.size();

                    if (upCount != 0 && serverCount != 0) {
                        if(nextServerIndex == -1){
                            //使用guava一致性hash
                            int hashCode = Math.abs(key.hashCode());
                            nextServerIndex = Hashing.consistentHash(hashCode, serverCount);
                        }
                        server = (Server)allServers.get(nextServerIndex % allServers.size());

                        if (server == null) {
                            Thread.yield();
                        } else {
                            // target 链接目标feign，并指定访问域名
                            TestlFeignClient client = getFeignClient(TestlFeignClient.class, "http://" + server.getId());
                            try {
                                String resp = client.getHealthInfo();
                                if (resp != null) {
                                    JSONObject obj = JSONObject.parseObject(resp);
                                    //if (server.isAlive() && server.isReadyToServe()) 不靠谱
                                    if ("up".equals(obj.getString("status"))) {
                                        //groupId=xxx
                                        String query = ((URI)key).getQuery();
                                        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(query);
                                        String groupId = split.get("groupId");
                                        if(groupId != null){
                                            //之前连接的服务器
                                            String oldServer = prevServer.get(groupId);
                                            //旧服务器列表
                                            List<String> oldServers = prevServers.get(groupId);
                                            //新服务器列表
                                            List<String> sids= allServers.stream().map(i -> i.getId()).collect(Collectors.toList());

                                            //没有设置原来的服务器，是该文档的第一次连接，不需要通知断开
                                            if(oldServer != null){
                                                //选择了新的服务器
                                                if(!server.getId().equals(oldServer)){
                                                    //原来连接的服务器是否下线
                                                    long down = oldServers.stream()
                                                            //下线的机子
                                                            .filter(o -> !sids.contains(o))
                                                            //原本连的服务器是否下线
                                                            .filter(l -> l.equals(oldServer)).count();
                                                    if(down == 0){
                                                        // target 链接目标feign，并指定访问域名
                                                        client = getFeignClient(TestlFeignClient.class, "http://" + oldServer);
                                                        try {
                                                            logger.info(oldServer + " closing websocket: " + groupId);
                                                            client.closeWebsocket(groupId);
                                                        }catch (Exception e){
                                                            logger.warn(oldServer + " websocket not closed, " + e.getMessage());
                                                        }
                                                    }else {
                                                        //如果原来连接的服务器下线，无需特殊处理，客户端会主动重连
                                                        logger.info("host down:" + oldServer);
                                                    }
                                                }else{
                                                    //选择的主机不变,无需特殊处理
                                                    //logger.info("select original host...");
                                                }
                                            }

                                            //保存最新选择的服务器和服务器列表；避免重复调用接口
                                            prevServer.put(groupId, server.getId());
                                            prevServers.put(groupId, sids);
                                        }


                                        logger.info("select host: " + server);
                                        return server;
                                    }else {
                                        logger.info("服务不可用：" + server);
                                    }
                                }
                            } catch (Exception e) {
                                logger.info("服务不可用：" + server);
                            }

                            //选择下一主机
                            server = null;
                        }
                        //选择下一主机
                        nextServerIndex++;
                        continue;
                    }

                    //无可用服务器
                    logger.warn("No up servers available from load balancer: " + lb);
                    return null;
                }

                if (count >= 10) {
                    logger.warn("No available alive servers after 10 tries from load balancer: " + lb);
                }

                logger.info("select host: " + server);
                return server;
            }
        }
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
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
