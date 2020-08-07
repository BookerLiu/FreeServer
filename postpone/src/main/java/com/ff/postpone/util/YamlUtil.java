package com.ff.postpone.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author LiuFei
 * @create 2020-08-06 16:54
 * @description 操作yml文件
 */
public class YamlUtil {


    /**
     * 持久化到文件
     * @param obj
     * @param filePath
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
}
