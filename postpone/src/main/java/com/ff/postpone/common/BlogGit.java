package com.ff.postpone.common;

import com.ff.postpone.constant.CloudInfo;
import com.ff.postpone.constant.Constans;
import com.ff.postpone.constant.Profile;
import com.ff.postpone.util.FileUtil;
import com.ff.postpone.util.StringUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.eclipse.jgit.api.Git.cloneRepository;

/**
 * @author LiuFei
 * @create 2020-07-30 10:18
 * @description 操作 git 发布博客
 */
public class BlogGit {

    private final static int DELETE = 1;
    private final static int ADD = 2;

    /**
     * 发布博客
     * @param type 云服务器类型
     * @return
     * @throws IOException
     * @throws GitAPIException
     */
    public static String sendCustomBlogByType(String type) throws IOException, GitAPIException {

        StringBuilder sb = new StringBuilder();
        //生成文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
        String nowStr = sdf.format(new Date());

        int i = (int) (Math.random() * Integer.MAX_VALUE);


        String fileName = sb.append(nowStr)
                .append(Profile.BLOG_USERNAME)
                .append("_")
                .append(i)
                .append(".md").toString();


        Git git;
        //判断git仓库是否存在
        if(FileUtil.exist(Profile.BLOG_LOCAL_PATH + "/" + Constans.GIT_INFO_DIR)){
            git = Git.open(new File(Profile.BLOG_LOCAL_PATH));
        }else{
            git = gitClone();
        }

        //创建新的blog
        String newBlogFilePath = Profile.BLOG_LOCAL_PATH+"/"+Constans.BLOG_ROOT_DIR+"/"+fileName;
        File newBlogFile = new File(newBlogFilePath);
        FileUtils.copyFile(new File(Constans.MD_TEMPLATE), newBlogFile);

        Map<String,String> map = new HashMap<>();

        CloudInfo cloudInfo = CloudInfo.getCloudInfo(type);
        map.put(Constans.MD_CLOUD_NAME, cloudInfo.getCloudName());
        map.put(Constans.MD_CLOUD_OS, cloudInfo.getOs());

        FileUtil.replaceFileStr(newBlogFile, map);

        //将新的blog提交至git
        gitPush(git, Constans.BLOG_ROOT_DIR + "/" + fileName, ADD);

        //生成blog 链接地址
        sb.setLength(0);
        String blogUrl = sb.append(Profile.BLOG_URL)
                .append("/")
                .append(nowStr.replace("-","/"))
                .append(Profile.BLOG_USERNAME)
                .append("_")
                .append(i)
                .append(".html").toString();
        return blogUrl;
    }

    /**
     * 从配置地址新clone一个仓库
     * @return
     * @throws GitAPIException
     */
    public static Git gitClone() throws GitAPIException {
        return cloneRepository().setURI(Profile.BLOG_URI)
                .setDirectory(new File(Profile.BLOG_LOCAL_PATH))
                .setCloneAllBranches(true)
                .setTransportConfigCallback(new MyTransportConfigCallback()).call();
    }

    /**
     * 提交文件
     * @param git
     * @param rootBlogPath blog文件所在根路径
     */
    public static void gitPush(Git git, String rootBlogPath,int type) throws GitAPIException {
        if(type == DELETE){
            git.rm().addFilepattern(rootBlogPath).call();
        }else{
            git.add().addFilepattern(rootBlogPath).call();
        }
        git.commit().setMessage(Profile.BLOG_USERNAME).call();
        git.push()
                .setRemote("origin")
                .add("master")
                .setTransportConfigCallback(new MyTransportConfigCallback())
                .call();
    }

    /**
     * 删除博客
     * @param blogUrl
     * @throws IOException
     * @throws GitAPIException
     */
    public static void deleteBlog(String blogUrl) throws IOException, GitAPIException {
        //获取原文件名
        String fileName = StringUtil.subByLastIndex(blogUrl, "/", 3)
                .substring(1)
                .replace("/","-");
        fileName = fileName.substring(0,fileName.length()-4) + ".md";

        String filePath = Constans.BLOG_ROOT_DIR + "/" + fileName;

        Git git = Git.open(new File(Profile.BLOG_LOCAL_PATH));

        gitPush(git, filePath, DELETE);
    }


    /**
     * 连接配置
     */
    private static class MyTransportConfigCallback implements TransportConfigCallback{
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, com.jcraft.jsch.Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch jsch = super.createDefaultJSch(fs);
                jsch.removeAllIdentity();
                jsch.addIdentity(Constans.PRIVATE_KEY);
                return jsch;
            }
        };
        @Override
        public void configure(Transport transport) {
            SshTransport sshTransport = ( SshTransport )transport;
            sshTransport.setSshSessionFactory( sshSessionFactory );
        }
    }
}
