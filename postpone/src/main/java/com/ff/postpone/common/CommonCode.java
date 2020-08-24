package com.ff.postpone.common;


import com.ff.postpone.constant.CloudData;
import com.ff.postpone.constant.Constans;
import com.ff.postpone.constant.Profile;
import com.ff.postpone.util.HttpUtil;
import com.ff.postpone.util.YamlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demo-Liu
 * @create 2020-08-03 9:43
 * @description 公共代码
 */

public class CommonCode {


    private static final Logger log = LoggerFactory.getLogger(CommonCode.class);

    /**
     * 检查是否到期  当前时间 > expireDate
     * @param expireDate yyyy-MM-dd HH:mm:ss  到期时间
     */
    public static boolean isExpire(String expireDate){
        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return nowDate.compareTo(expireDate) > 0;
    }


    /**
     * 获取用户唯一key
     * @param username 用户名
     * @param type 服务器类型
     * @return
     */
    public static String getUserKey(String username, String type){
        return username + "_" + type;
    }

    /**
     * 区分日志
     * @param username 用户名
     * @param cloudName 云服务器名
     * @return
     */
    public static String getUKLog(String username, String cloudName){
        return cloudName+"账号: "+username +",";
    }

    /**
     * 持久化文件
     * @param uKey 用户唯一key
     * @param userInfo 用户持久化信息
     * @throws IOException
     */
    public static void userInfosPermanent(String uKey, Map<String,String> userInfo) throws IOException {
        Map<String,Map<String,Map<String,String>>> map = new HashMap();
        Profile.userInfos.put(uKey, userInfo);
        map.put("userInfos", Profile.userInfos);
        YamlUtil.dump(map, Constans.PERSISTENT_FILE);
    }


    /**
     * 检查审核状态
     * @param json 返回数据
     * @param ukLog 用户日志key
     * @throws Exception
     */
    public static void checkCheckStatus(JSONObject json, String ukLog, String blogUrl) throws Exception {

        json = JSONObject.fromObject(json.getString(CloudData.CHECK_MSG));

        JSONArray array = JSONArray.fromObject(json.getString(CloudData.CHECK_DATA));
        if(array.size()>0){

            json = JSONObject.fromObject(array.get(0));
            String state = json.getString(CloudData.CHECK_STATUS);
            String url = json.getString(CloudData.CHECK_URL);
            boolean delete = false;

            switch (state) {
                case CloudData.CHECK_ING:
                    log.info("{}审核中,无需审核!!!", ukLog);
                    break;
                case CloudData.CHECK_SUCCESS:
                    delete = true;
                    log.info("{}审核通过!!!", ukLog);
                    break;
                default:
                    delete = true;
                    log.info("{}审核失败,审核结果:{}", ukLog, state);
            }
            //删除博客
            if(delete && blogUrl!=null && blogUrl.equals(url)) BlogGit.deleteBlog(blogUrl);

        }else{
            log.info("没有延期记录!!!");
        }
    }

    /**
     * 检查git是否将博客md文件格式化为 html
     * @param blogUrl 博客url
     * @return
     */
    public static boolean isInitBlog(String blogUrl) throws IOException, URISyntaxException {
        HttpClient httpClient = HttpUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(blogUrl);
        HttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode() == 200){
            return true;
        }else{
            return false;
        }
    }



}
