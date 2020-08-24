package com.ff.postpone.constant;

/**
 * @author Demo-Liu
 * @create 2020-08-05 16:30
 * @description 请求返回参数 key
 */
public class CloudData {

    /**
     * 登录接口返回
     */
    //登录状态
    public final static String LOGIN_STATUS = "msg";
    //登录成功信息
    public final static String LOGIN_SUCCESS = "登录成功";

    /**
     * 状态接口返回
     */
    //服务器信息
    public final static String STATUS_DATA = "msg";
    //延期状态
    public final static String DELAY_STATUS1 = "delay_enable";
    public final static String DELAY_STATUS2 = "delay_state";
    //下次执行时间
    public final static String NEXT_TIME = "next_time";


    /**
     * 延期记录接口返回
     */
    //信息头
    public final static String CHECK_MSG = "msg";
    //延期记录
    public final static String CHECK_DATA = "content";
    //延期状态
    public final static String CHECK_STATUS = "State";
    //延期博客url
    public final static String CHECK_URL = "url";
    //审核通过
    public final static String CHECK_SUCCESS = "审核通过";
    //审核中
    public final static String CHECK_ING = "审核中";


    /**
     * 提交博客接口返回
     */
    //博客提交状态
    public final static String BLOG_STATUS = "msg";
    //提交成功信息
    public final static String BLOG_SUCCESS = "提交成功";






}
