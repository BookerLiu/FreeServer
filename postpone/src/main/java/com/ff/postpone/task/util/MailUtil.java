package com.ff.postpone.task.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author LiuFei
 * @Date 2019/6/17 15:38
 * @description
 */
public class MailUtil {


    /**
     * 发送邮件
     * @param title 邮件标题
     * @param body  邮件正文
     */
    public static void sendMaid(String title, String body){
        PropertiesUtil pro = new PropertiesUtil();
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.connectiontimeout", "5000");
        try{
            //1、创建session
            Session session = Session.getInstance(props);
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            //session.setDebug(true);
            //2、通过session得到transport对象
            Transport ts = session.getTransport("smtp");
            //3、使用邮箱的用户名和密码连上邮件服务器，
            ts.connect(pro.getProperty("SERVER_HOST"), Integer.parseInt(pro.getProperty("SERVER_PORT")), pro.getProperty("SEND_USER"), pro.getProperty("PASSWORD"));
            //4、创建邮件
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(pro.getProperty("SEND_USER")));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(pro.getProperty("RECEIVE_USER")));
            //邮件的标题
            message.setSubject(title);
            //邮件的文本内容
            message.setContent(body, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
