package com.ff.postpone.config;

import com.ff.postpone.constant.Constans;
import com.ff.postpone.constant.Profile;
import com.ff.postpone.util.StringUtil;
import com.ff.postpone.util.YamlUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demo-Liu
 * @create 2020-08-25 15:59
 * @description 用于打包jar时 将资源文件提取至缓存目录
 */
@Component
public class JarFileConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JarFileConfig.class);

    @Override
    public void run(String... args) throws Exception {
        String protocol = JarFileConfig.class.getResource("").getProtocol();
        if("jar".equals(protocol)){
            log.info("打包方式为jar,开始提取资源文件...");

            log.info("私钥文件提取...");
            File privateKey = new File(Profile.JAR_RESOURCE_PATH + "key/id_rsa");
            copyToFile("/key/id_rsa", privateKey);
            Constans.PRIVATE_KEY = privateKey.getAbsolutePath();

            File mdFile = new File(Profile.JAR_RESOURCE_PATH + "static/template.md");
            copyToFile("/static/template.md", mdFile);
            Constans.MD_TEMPLATE = mdFile.getAbsolutePath();

            if(StringUtil.isEmpty(Profile.PJ_EXEC)){
                log.info("phantomjs 文件提取...");
                String osName = System.getProperty("os.name").toLowerCase();
                String resourcePath;
                if(osName.contains("windows")){
                    resourcePath = "/phantomJs/windows/phantomjs.exe";
                }else if(osName.contains("linux")){
                    resourcePath = "/phantomJs/linux_x86_64/phantomjs";
                }else{
                    log.error("phantomjs 未配置...");
                    throw new Exception("phantomjs 未配置...");
                }
                File pjExec = new File(Profile.JAR_RESOURCE_PATH+resourcePath.substring(1));
                copyToFile(resourcePath, pjExec);
                Constans.PJ_LINUX_X86_64 = pjExec.getAbsolutePath();
                Constans.PJ_WIN = pjExec.getAbsolutePath();
            }

            log.info("截图js文件提取...");
            File jsFIle = new File(Profile.JAR_RESOURCE_PATH + "phantomJs/js/GPBlog.js");
            copyToFile("/phantomJs/js/GPBlog.js", mdFile);
            Constans.PIC_JS = jsFIle.getAbsolutePath();

            log.info("持久化文件提取...");
            File permanentFile = new File(Profile.JAR_RESOURCE_PATH+"application-permanent.yml");
            copyToFile("/application-permanent.yml",permanentFile);
            Constans.PERMANENT_FILE = permanentFile.getAbsolutePath();
            //加载持久化信息至内存
            Object read = YamlUtil.read(Constans.PERMANENT_FILE);
            if(read != null){
                Profile.userInfos = (Map<String, Map<String, String>>) read;
            }else{
                Profile.userInfos = new HashMap();
            }
            log.info("jar包资源文件提取完毕...");
        }
    }

    /**
     * copy资源文件夹中文件至新的路径
     * @param resourcePath 资源文件领
     * @param file 新的文件
     * @throws IOException
     */
    private void copyToFile(String resourcePath, File file) throws IOException {
        if(!file.exists()){
            FileUtils.copyToFile(JarFileConfig.class.getResourceAsStream(resourcePath), file);
        }
    }
}
