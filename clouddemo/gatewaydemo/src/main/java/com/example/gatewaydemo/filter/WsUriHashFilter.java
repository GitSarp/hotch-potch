package com.example.gatewaydemo.filter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/26 11:25
 **/
public class WsUriHashFilter extends LoadBalancerClientFilter {

    public WsUriHashFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        //请求uri
        URI uri = (URI)(exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR));
        String scheme = uri.getScheme();
        MultiValueMap queryParams = request.getQueryParams();
        //根据请求参数判断是否需要hash
        boolean baseLoadBalance = !("ws".equals(scheme) || "wss".equals(scheme)) || queryParams == null || !queryParams.containsKey("nodeid");
        if(baseLoadBalance){
            return super.choose(exchange);
        }

        if (this.loadBalancer instanceof RibbonLoadBalancerClient) {
            RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
            String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
            //这里使用uri做为选择服务实例的key
            return client.choose(serviceId, uri);
        }
        return super.choose(exchange);
    }
}
