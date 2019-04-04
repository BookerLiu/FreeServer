package com.ff.postpone.task.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.Map;

/**
 * @Author Demo-Liu
 * @Date 2019/3/15 15:42
 * @description
 */
public class HttpUtil {


    /**
     * 获取HttpClient并设置在请求中附带Cookie
     * @param encoding
     * @return
     */
    public static HttpClient getHttpClient(String encoding){
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset(encoding);
        //设置在请求中附带Cookie
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        return  client;
    }

    /**
     * 获取执行后的 PostMethod 返回结果
     * @param client
     * @param pair
     * @param url
     * @param headerMap
     * @return
     * @throws IOException
     */
    public static String getPostRes(HttpClient client, String url, NameValuePair pair[], Map<String,String> headerMap) throws IOException {
        PostMethod postMethod = new PostMethod(url);
        //设置请求参数
        if(pair!=null){
            postMethod.setRequestBody(pair);
        }
        //设置请求头
        if(headerMap!=null){
            for (String key : headerMap.keySet()) {
                postMethod.setRequestHeader(key, headerMap.get(key));
            }
        }
        client.executeMethod(postMethod);
        return postMethod.getResponseBodyAsString();
    }
    public static String getPostRes(HttpClient client, String url,  NameValuePair pair[]) throws IOException {
        return getPostRes(client,url,pair,null);
    }

    /**
     * 获取执行后的 GetMethod 返回结果
     * @param client
     * @param url
     * @param pair
     * @param headerMap
     * @return
     * @throws IOException
     */
    public static String getGetRes(HttpClient client, String url, NameValuePair pair[], Map<String,String> headerMap) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        //设置请求参数
        if(pair!=null){
            getMethod.setQueryString(pair);
        }
        //设置请求头
        if(headerMap!=null){
            for (String key : headerMap.keySet()) {
                getMethod.setRequestHeader(key, headerMap.get(key));
            }
        }
        client.executeMethod(getMethod);
        return getMethod.getResponseBodyAsString();
    }
}
