package com.ff.postpone.constant;

import com.ff.postpone.common.CommonCode;

public final class Constans {

    /**
     * resources 目录路径
     */
    private final static String RESOURCE_PATH = CommonCode.class.getResource("/").getPath();

    /**
     * 配置文件
     */
    //服务器 key
    public final static String CLOUD_USERNAME = "username";
    public final static String CLOUD_PASSWORD = "password";
    public final static String CLOUD_TYPE = "type";


    /**
     * 私钥文件路径名
     */
    public final static String PRIVATE_KEY = RESOURCE_PATH + "key/id_rsa";

    /**
     * 文章模板路径名
     */
    public final static String MD_TEMPLATE =  RESOURCE_PATH +"static/template.md";

    /**
     * 持久化文件路径名
     */
    public final static String PERSISTENT_FILE = RESOURCE_PATH + "application-permanent.yml";


    /**
     * 截图js 文件路径
     */
    public final static String PIC_JS = RESOURCE_PATH + "phantomJs/js/GPBlog.js";

    /**
     * window phantomjs 路径
     */
    public final static String PJ_WIN = RESOURCE_PATH + "phantomJs/windows/phantomjs.exe";

    /**
     * linux phantomjs 路径
     */
    public final static String PJ_LINUX_X86_64 = RESOURCE_PATH + "phantomJs/linux_x86_64/phantomjs";

    /**
     * git 信息目录
     */
    public final static String GIT_INFO_DIR = ".git";

    /**
     * blog md 所在仓库文件目录
     */
    public final static String BLOG_ROOT_DIR = "_posts";

    /**
     * 云服务器名  占位符
     */
    public final static String MD_CLOUD_NAME = "{cloud}";

    /**
     * 云服务器官网  占位符
     */
    public final static String MD_CLOUD_OS = "{uri}";

}
