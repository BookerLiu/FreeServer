package com.ff.postpone.util;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

/**
 * @author Demo-Liu
 * @create 2020-08-06 16:54
 * @description 操作yml文件
 */
public class YamlUtil {


    /**
     * 持久化到文件
     * @param obj 持久化信息
     * @param filePath 持久化文件路径
     * @throws IOException
     */
    public static void dump(Object obj, String filePath) throws IOException {
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(filePath));
            Yaml yaml = new Yaml();
            yaml.dump(obj, fw);
        } finally {
            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取yml文件
     * @param filePath 文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static Object read(String filePath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream is = null;
        try{
            is = new FileInputStream(new File(filePath));
            return yaml.load(is);
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
