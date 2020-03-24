package com.example.gatewaydemo.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/5 14:13
 **/

public class HttpClientUtil {


    /**
     * 发送get请求
     *
     * @param url
     * @param map
     * @param charset
     * @param headers
     * @return
     */
    public static String doGetWithHeader(String url, Map<String,Object> map, String charset, Map<String, String> headers){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            //httpClient = new SSLClient();
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if(map != null){
                Iterator iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                    list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
                }
            }
            if(list.size() > 0){
                URIBuilder uriBuilder = new URIBuilder(url);
                uriBuilder.setParameters(list);
                httpGet = new HttpGet(uriBuilder.build());
            }
            if(headers != null && !headers.isEmpty()){
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                if(response.getStatusLine().getStatusCode() == 200){
                    HttpEntity resEntity = response.getEntity();
                    if(resEntity != null){
                        result = EntityUtils.toString(resEntity,charset);
                    }
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }
        return result;
    }

    /**
     * 发送json请求
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static String doJsonPost(String url, String body) {
        return doJsonPost(url, body, "utf-8");
    }

    public static String doJsonPost(String url, String body, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            //httpClient = new SSLClient();
            httpClient = HttpClients.createDefault();

            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            //不设置utf-8，可能出现中文乱码
            StringEntity entity = new StringEntity(body, charset);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
