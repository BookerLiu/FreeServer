package com.ff.postpone.task;

import com.ff.postpone.constant.Constans;
import com.ff.postpone.constant.Profile;
import com.ff.postpone.util.FileUtil;
import com.ff.postpone.util.StringUtil;

import java.io.File;

/**
 * @author LiuFei
 * @create 2020-08-07 11:05
 * @description psvm
 */
public class Test {


    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        String BLANK = " ";
        String osName = System.getProperty("os.name");

        if(StringUtil.isEmpty(Profile.PJ_EXEC)){
            if(osName.toLowerCase().contains("windows")){
                sb.append(Constans.PJ_WIN).append(BLANK);
            }else if(osName.toLowerCase().contains("linux")){
                sb.append(Constans.PJ_LINUX_X86_64.substring(1)).append(BLANK);
            }else{
                throw new Exception("未配置phantomJs: "+osName);
            }
        }else{
            sb.append(Profile.PJ_EXEC.substring(1)).append(BLANK);
        }
        sb.append(Constans.PIC_JS.substring(1)).append(BLANK)
                .append("https://demo-ad.github.io/2020/08/04/demoliu.html").append(BLANK)
                .append("D:\\freeServer\\phantomJsPic\\postpone.png");

        //执行一次删除防止上次任务残留
        File file = FileUtil.deleteFile("D:\\freeServer\\phantomJsPic\\postpone.png");

        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(sb.toString());
            Thread.sleep(20000);
            int i = 0;
            while(!file.exists()){
                System.out.println("文件未创建成功,等待10秒...");
                i++;
                if(i==20){
                    System.out.println("文件创建失败: {}");
                }
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println("文件创建失败: {}");
            e.printStackTrace();
        }
        System.out.println("文件创建成功!!!");
    }
}
