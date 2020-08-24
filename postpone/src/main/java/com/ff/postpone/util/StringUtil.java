package com.ff.postpone.util;

/**
 * @author Demo-Liu
 * @create 2020-08-03 13:54
 * @description 字符串工具类
 */
public class StringUtil {


    /**
     * 检查传入的str 是否有为空
     * @param strs 字符串 或 数组
     * @return
     */
    public static boolean isEmpty(String...strs){
        if(strs==null || strs.length==0) return true;
        for (String str : strs) {
            if(str==null || str.trim().length()==0 || str.equals("null")) return true;
        }
        return false;
    }

    /**
     * 截取字符串
     * @param str 原字符串
     * @param s 查找字符串
     * @param num s 倒数位  从 0 开始
     * @return
     */
    public static String subByLastIndex(String str, String s, int num){
        StringBuilder sb = new StringBuilder(str);
        str = sb.reverse().toString();
        sb.setLength(0);
        for (int i = 0; i <= num; i++) {
            sb.append(str, 0, str.indexOf(s)+1);
            str = str.substring(str.indexOf(s)+1);
        }
        sb.reverse();
        return sb.toString();
    }

}
