package com.ff.postpone.util;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * @author LiuFei
 * @create 2020-08-04 16:46
 * @description 文件工具类  补充 common-io
 */
public class FileUtil {



    private final static String LINE_FEED = "\n";

    /**
     * 判断文件 或 文件夹路径是否存在
     * @param path 路径
     * @return
     */
    public static boolean exist(String path){
        return new File(path).exists();
    }


    /**
     * 替换文件中的字符串
     * @param file 文件
     * @param replaceParam key 原始字符串, val 要替换的字符串
     * @throws IOException
     */
    public static void replaceFileStr(File file, Map<String,String> replaceParam) throws IOException {

        String fileStr = readFileStr(file);

        Set<String> params = replaceParam.keySet();
        for (String param : params) {
            fileStr = fileStr.replace(param, replaceParam.get(param));
        }

        writeFileStr(file, fileStr);
    }

    /**
     * 读取文件内容
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFileStr(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line=br.readLine()) != null){
                sb.append(line).append(LINE_FEED);
            }
        } finally {
            try{
                if(br != null) br.close();
                if(fr != null) fr.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    /**
     * 写入str到文件
     * @param file
     * @param str
     * @throws IOException
     */
    public static void writeFileStr(File file, String str) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(str);
            bw.flush();
        } finally {
            try{
                if(bw != null) bw.close();
                if(fw != null) fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据路径删除文件
     * @param filePath 文件路径
     */
    public static File deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        return file;
    }
}
