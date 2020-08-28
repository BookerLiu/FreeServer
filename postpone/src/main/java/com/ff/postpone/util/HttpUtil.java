package com.ff.postpone.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Author Demo-Liu
 * @Date 2019/3/15 15:42
 * @description http 工具类
 */
public class HttpUtil {


    /**
     * 获取HttpClient
     * @return
     */
    public static HttpClient getHttpClient(){
        return HttpClients.createDefault();
    }
    public static HttpClient getHttpClient(BasicCookieStore cs){
        return HttpClients.custom()
                .setDefaultCookieStore(cs)
                .build();
    }


    /**
     * @Author Demo_Liu
     * @Date 2019/6/12 10:05
     * @description 获取执行后的 HttpPost 返回结果
     * @Param [client, url, pair, headerMap]
     * @Reutrn java.lang.String
    */
    public static String getPostRes(HttpClient client, String url, NameValuePair pair[], Map<String,String> headerMap) throws IOException, URISyntaxException {
        HttpPost httpPost;
        //设置请求参数
        if(pair!=null){
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameters(pair);
            httpPost = new HttpPost(uriBuilder.build());
        }else{
            httpPost = new HttpPost(url);
        }
        return getPostRes(client,httpPost,headerMap);
    }
    public static String getPostRes(HttpClient client, String url,  NameValuePair pair[]) throws IOException, URISyntaxException {
        return getPostRes(client,url,pair,null);
    }

    /**
     * @Author Demo_Liu
     * @Date 2019/6/12 10:05
     * @description 获取执行后的 HttpPost 返回结果
     * @Param [client, url, entity, headerMap]
     * @Reutrn java.lang.String
    */
    public static String getPostRes(HttpClient client, String url, HttpEntity entity, Map<String,String> headerMap) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return getPostRes(client,httpPost,headerMap);
    }
    public static String getPostRes(HttpClient client, String url, HttpEntity entity) throws IOException {
        return getPostRes(client,url,entity,null);
    }

    private static String getPostRes(HttpClient client, HttpPost httpPost, Map<String,String> headerMap) throws IOException {
        //设置请求头
        if(headerMap!=null){
            for (String key : headerMap.keySet()) {
                httpPost.setHeader(key, headerMap.get(key));
            }
        }
        HttpResponse response = client.execute(httpPost);
        String res = EntityUtils.toString(response.getEntity());
        if(res.startsWith("\uFEFF")){
            res = res.replace("\uFEFF","");
        }
        return res;
    }

    /**
     * @Author Demo_Liu
     * @Date 2019/6/12 10:15
     * @description 获取执行后的 HttpGet 返回结果
     * @Param [client, url, pair, headerMap]
     * @Reutrn java.lang.String
    */
    public static String getGetRes(HttpClient client, String url, NameValuePair pair[], Map<String,String> headerMap) throws IOException, URISyntaxException {
        HttpGet httpGet;
        //设置请求参数
        if(pair!=null){
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameters(pair);
            httpGet = new HttpGet(uriBuilder.build());
        }else{
            httpGet = new HttpGet(url);
        }
        //设置请求头
        if(headerMap!=null){
            for (String key : headerMap.keySet()) {
                httpGet.setHeader(key, headerMap.get(key));
            }
        }
        HttpResponse response = client.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }
    public static String getGetRes(HttpClient client, String url, NameValuePair pair[]) throws IOException, URISyntaxException {
        return getGetRes(client,url,pair,null);
    }
}
