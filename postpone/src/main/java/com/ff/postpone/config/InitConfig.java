package com.ff.postpone.config;

import com.ff.postpone.constant.Profile;
import com.ff.postpone.constant.ResourceAbPath;
import com.ff.postpone.constant.ResourcePath;
import com.ff.postpone.util.CmdUtil;
import com.ff.postpone.util.FileUtil;
import com.ff.postpone.util.StringUtil;
import com.ff.postpone.util.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demo-Liu
 * @create 2020-08-25 15:59
 * @description 初始化项目,将资源文件提取至缓存目录
 */
@Component
@DependsOn("profile")
@Order(1)
public class InitConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(InitConfig.class);

    @Override
    public void run(String... args) throws Exception {
        String protocol = InitConfig.class.getResource("").getProtocol();
        String osName = System.getProperty("os.name").toLowerCase();
        log.info("======================================项目初始化开始======================================");
        if(StringUtil.isEmpty(Profile.PJ_EXEC)){
            log.info("phantomjs.7z 压缩文件提取...");
            String resourcePath;
            String pjName;
            if(osName.contains("windows")){
                resourcePath = ResourcePath.PJ_7Z_WIN_PATH;
                pjName = "/phantomjs.exe";
            }else if(osName.contains("linux")){
                resourcePath = ResourcePath.PJ_7Z_LINUX_X86_64_PATH;
                pjName = "/phantomjs";
            }else{
                log.error("phantomjs 未配置...");
                throw new Exception("phantomjs 未配置...");
            }
            File pj7z = new File(Profile.RESOURCE_TEMP_FILEPATH+resourcePath);
            FileUtil.copyResourceToFile(resourcePath, pj7z);

            String pj7zPath = pj7z.getAbsolutePath();
            String pjExecPath = pj7zPath.substring(0, pj7zPath.lastIndexOf(File.separator));

            FileUtil.un7z(pj7zPath, pjExecPath);
            Profile.PJ_EXEC = pjExecPath + pjName;
        }

        if("jar".equals(protocol)){
            log.info("打包方式为jar,开始提取资源文件...");

            log.info("私钥文件提取...");
            File privateKey = new File(Profile.RESOURCE_TEMP_FILEPATH + ResourcePath.PRIVATE_KEY_PATH);
            FileUtil.copyResourceToFile(ResourcePath.PRIVATE_KEY_PATH, privateKey);
            ResourceAbPath.PRIVATE_KEY_ABPATH = privateKey.getAbsolutePath();

            log.info("截图js文件提取...");
            File jsFIle = new File(Profile.RESOURCE_TEMP_FILEPATH + ResourcePath.PIC_JS_PATH);
            FileUtil.copyResourceToFile(ResourcePath.PIC_JS_PATH, jsFIle);
            //由于windows 获取的路径会在盘符前加上 /  如:/D:/1.txt 这种方式 java File可以识别
            //但是 windows cmd 窗口会找不到文件所以 如果是windows 前面要去掉 /
            if(osName.contains("windows")){
                ResourceAbPath.PIC_JS_ABPATH = jsFIle.getAbsolutePath().substring(1);
            }else{
                ResourceAbPath.PIC_JS_ABPATH = jsFIle.getAbsolutePath();
            }

            log.info("持久化文件提取...");
            File permanentFile = new File(Profile.RESOURCE_TEMP_FILEPATH + ResourcePath.PERMANENT_PATH);
            FileUtil.copyResourceToFile(ResourcePath.PERMANENT_PATH,permanentFile);
            ResourceAbPath.PERMANENT_ABPATH = permanentFile.getAbsolutePath();
            //加载持久化信息至内存
            Object read = YamlUtil.read(ResourceAbPath.PERMANENT_ABPATH);
            if(read != null){
                Profile.userInfos = ((Map<String, Map<String, Map<String,String>>>) read).get("userInfos");
            }else{
                Profile.userInfos = new HashMap();
            }
            log.info("jar包资源文件提取完毕...");
        }

        if(osName.contains("linux")){
            String cmd = "chmod 777 " + Profile.PJ_EXEC;
            log.info("赋予phantomjs可执行权限: {}", cmd);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(cmd);

            cmd = "cat /etc/issue";
            log.info("获取系统版本信息:{}" ,cmd);
            String sysInfo = CmdUtil.execCmd(cmd, false).toLowerCase();
            log.info("系统版本为:{}", sysInfo);
            log.info("安装phantomjs运行环境");
            if(sysInfo.contains("ubuntu")){
                CmdUtil.execCmd("sudo apt-get install fontconfig -y",false);
                CmdUtil.execCmd("sudo apt-get install libfontconfig -y",false);
                CmdUtil.execCmd("sudo apt-get install freetype -y",false);
                CmdUtil.execCmd("sudo apt-get install freetype-devel -y",false);
                CmdUtil.execCmd("sudo apt-get install fontconfig-devel -y",false);
                CmdUtil.execCmd("sudo apt-get install libstdc++ -y",false);
                CmdUtil.execCmd("sudo apt-get install xfonts-wqy -y",false);
            }else{
                cmd = "yum install -y fontconfig libfontconfig freetype freetype-devel fontconfig-devel libstdc++ bitmap-fonts bitmap-fonts-cjk";
                CmdUtil.execCmd(cmd,false);
            }
        }
        log.info("======================================项目初始化完毕======================================");
    }


}
