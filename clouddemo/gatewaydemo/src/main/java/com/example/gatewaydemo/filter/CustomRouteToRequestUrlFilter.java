package com.example.gatewaydemo.filter;

import com.example.gatewaydemo.utils.HttpClientUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/23 16:16
 **/
//@Component
@Deprecated
public class CustomRouteToRequestUrlFilter extends RouteToRequestUrlFilter {

    //ws服务主机
    private List<String> hosts = new ArrayList<String>() {
        {
            add("127.0.0.1:3000");
            add("127.0.0.1:3001");
        }
    };

    @Override
    public int getOrder() {
        //需要大于RouteToRequestUrlFilter的返回值（10000）
        // 这样才能将优先级置于RouteToRequestUrlFilter之下
        return 10001;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取token
        HttpHeaders headers = request.getHeaders();
        List<String> tokenHeader = headers.get("Sec-WebSocket-Protocol");
        if(tokenHeader != null && tokenHeader.size() > 0){
            System.out.println("token: " + tokenHeader.get(0));
        }

        //request.getURI();是原始请求
        //ws://127.0.0.1:3000/sharedb?groupId=xxx   是转发请求
        URI uri = (URI)(exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR));
        String scheme = uri.getScheme();
        MultiValueMap queryMap = request.getQueryParams();
        //根据请求参数groupId判断是否需要hash
        if(("ws".equals(scheme) || "wss".equals(scheme)) && queryMap != null && queryMap.containsKey("groupId")){
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, redirectURI(uri, scheme));
        }
        return chain.filter(exchange);
    }

    /**
     * ip hash选择服务器
     * @return
     */
    protected URI redirectURI(URI uri, String scheme){
        //ip hash选择服务器
        int num = Math.abs(uri.hashCode()) % hosts.size();
        String host = hosts.get(num);
        //todo 执行健康检查
        //如果服务崩了，虽然可以转到新的服务器，但之前的文档是否还存在？
        if(HttpClientUtil.doGetWithHeader("http://" + host + "/info", null, "utf-8", null) == null){
            //注意，这里测试只做一次循环
            host = hosts.get((++num) % hosts.size());
        }

        StringBuilder sb = new StringBuilder(scheme);
        sb.append("://");
        sb.append(host);
//            sb.append(":");
//            sb.append(uri.getPort());
        sb.append(uri.getPath());
        sb.append("?");
        sb.append(uri.getQuery());
        return URI.create(sb.toString());
    }
}
