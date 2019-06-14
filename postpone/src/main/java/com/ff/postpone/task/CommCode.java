package com.ff.postpone.task;

import com.ff.postpone.mapper.UserInfoMapper;
import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.task.util.Sina;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author LiuFei
 * @Date 2019/6/13 16:55
 * @description
 */
public class CommCode {


    private static Logger log = Logger.getLogger(CommCode.class);
    /**
     * 检查服务器状态
     * @return
     */
    public static int checkServerStatus(JSONObject json, String username, UserInfoMapper infoMapper, UserInfo userInfo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String msg = json.getString("msg");
        json = JSONObject.fromObject(msg);
        String status;
        try{
            status = json.getString("delay_enable");
        }catch (Exception e){
            status = json.getString("delay_state");
        }
        switch (status){
            case "1":
                log.info(username + "已到审核期!!!");
                return 1;
            case "0":
                String next_time = json.getString("next_time");
                log.info(username + "未到审核期,下次审核开始时间:"+next_time);
                Date nextTime = sdf.parse(next_time);
                userInfo.setNextTime(nextTime);
                infoMapper.updateByPrimaryKey(userInfo);
                return 2;
            case "待审核":
                log.info(username + "正在审核!!!");
                return 3;
        }
        log.info(username + "服务器返回其他状态:"+status);
        return 0;
    }

    /**
     * 检查审核状态
     * @param json
     * @param userInfo
     * @param infoMapper
     * @return
     */
    public static int checkCheckStatus(JSONObject json, UserInfo userInfo, UserInfoMapper infoMapper, List<String> cookieList) throws IOException, URISyntaxException {
        String username = userInfo.getCloudUser();
        json = JSONObject.fromObject(json.getString("msg"));
        JSONArray array = JSONArray.fromObject(json.getString("content"));
        if(array.size()>0){
            json = JSONObject.fromObject(array.get(0));
            String state = json.getString("State");
            String url = json.getString("url");
            if("待审核".equals(state)){
                log.info(username + "审核中,无需审核!!!");
                return 1;
            }else if("审核通过".equals(state)){
                log.info(username + "审核通过!!!");
                if(userInfo.getBlogUrl().equals(url)){
                    userInfo.setBlogUrl("success");
                    infoMapper.updateByPrimaryKey(userInfo);
                    Sina.deleteBlog(userInfo,url,infoMapper,cookieList);
                    log.info("修改url为success,删除延期博客!!!");
                }
                return 2;
            }else{
                log.info(username + "审核失败,审核结果:"+state);
                if(userInfo.getBlogUrl().equals(url)){
                    userInfo.setBlogUrl("error");
                    infoMapper.updateByPrimaryKey(userInfo);
                    Sina.deleteBlog(userInfo,url,infoMapper,cookieList);
                    log.info("修改url为error,删除延期博客!!!");
                }
                return 3;
            }
        }else{
            log.info("没有延期记录!!!");
            return 4;
        }
    }

    /**
     * 根据博客类型调用不同的方法删除博客
     * @param userInfo
     */
    private static void deleteBlogByType(UserInfo userInfo){

    }
}
