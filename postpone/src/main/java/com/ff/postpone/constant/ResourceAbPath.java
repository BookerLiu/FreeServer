package com.ff.postpone.constant;


import com.ff.postpone.common.CommonCode;

/**
 *  resource文件夹 文件绝对路径
 */
public class ResourceAbPath {

    /**
     * resources 目录路径
     */
    private final static String RESOURCE_PATH = CommonCode.class.getResource("").getPath();


    /**
     * 私钥
     */
    public static String PRIVATE_KEY_ABPATH = RESOURCE_PATH + ResourcePath.PRIVATE_KEY_PATH;

    /**
     * 截图js
     */
    public static String PIC_JS_ABPATH = RESOURCE_PATH + ResourcePath.PIC_JS_PATH;

    /**
     * 持久化文件
     */
    public static String PERMANENT_ABPATH = RESOURCE_PATH + ResourcePath.PERMANENT_PATH;

}
