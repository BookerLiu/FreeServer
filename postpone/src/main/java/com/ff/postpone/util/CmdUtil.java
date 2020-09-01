package com.ff.postpone.util;


import java.io.*;

/**
 * 执行 系统命令
 */
public class CmdUtil {


    /**
     * 执行系统命令, 返回执行结果
     * @param cmd 需要执行的命令
     * @param master false 子进程执行  true 主进程执行
     */
    public static String execCmd(String cmd, boolean master) throws InterruptedException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        Process process = null;
        try {
            ProcessBuilder pb;
            if(master){
                pb = new ProcessBuilder(cmd);
            }else{
                pb = new ProcessBuilder("/bin/sh","-c",cmd);
            }
            pb.redirectErrorStream(true);
            process = pb.start();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            process.waitFor();
        } finally {
            closeStream(br);
            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }
        // 返回执行结果
        return sb.toString();
    }

    /**
     * 关闭流
     * @param stream io流
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
