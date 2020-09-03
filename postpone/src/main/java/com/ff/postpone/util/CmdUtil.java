package com.ff.postpone.util;


import java.io.*;

/**
 * 执行 系统命令
 */
public class CmdUtil {


    /**
     * 执行系统命令, 返回执行结果
     * @param cmd 需要执行的命令
     */
    public static Process execCmdGetP(String cmd) throws IOException {

        Process process;
        String[] strs = getCmd();
        ProcessBuilder pb = new ProcessBuilder(strs[0],strs[1],cmd);
        process = pb.start();
        Process finalProcess = process;
        new Thread(){
            @Override
            public void run() {
                try {
                    finalProcess.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return process;
    }

    public static String execCmd(String cmd) throws InterruptedException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        Process process = null;
        try {
            String[] strs = getCmd();
            ProcessBuilder pb = new ProcessBuilder(strs[0],strs[1],cmd);
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


    public static void destroy(Process process){
        if(process!=null){
            process.destroy();
        }
    }

    private static String[] getCmd(){
        //mac 系统未经测试
        String[] strs = new String[2];
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("linux")){
            strs[0] = "/bin/sh";
            strs[1] = "-c";
        }else if(osName.contains("windows")){
            strs[0] = "cmd";
            strs[1] = "/c";
        }else{
            strs[0] = "/bin/sh";
            strs[1] = "-c";
        }
        return strs;
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
