package com.ff.postpone.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author Demo_Liu
 * @Date 2019/6/17 15:38
 * @description
 */
public class MailUtil {

    private String host;
    private Integer port;
    private String password;
    private String receiveUser;
    private String sendUser;



    /**
     * 发送邮件
     * @param title 邮件标题
     * @param body  邮件正文
     */
    public void sendMail(String title, String body){
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.connectiontimeout", "5000");
        //1、创建session
        Session session = Session.getInstance(props);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport("smtp");
            //3、使用邮箱的用户名和密码连上邮件服务器，
            ts.connect(host, port, sendUser, password);
            //4、创建邮件
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(sendUser));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveUser));
            //邮件的标题
            message.setSubject(title);
            //邮件的文本内容
            message.setContent(body, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (MessagingException e){
            e.printStackTrace();
        }finally {
            if (ts!=null){
                try {
                    ts.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private MailUtil(){

    }

    public static final class MailUtilBuilder {
        private String host;
        private Integer port;
        private String password;
        private String receiveUser;
        private String sendUser;

        private MailUtilBuilder() {
        }

        public static MailUtilBuilder getBuilder() {
            return new MailUtilBuilder();
        }

        public MailUtilBuilder setHost(String host) {
            this.host = host;
            return this;
        }

        public MailUtilBuilder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public MailUtilBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public MailUtilBuilder setReceiveUser(String receiveUser) {
            this.receiveUser = receiveUser;
            return this;
        }

        public MailUtilBuilder setSendUser(String sendUser) {
            this.sendUser = sendUser;
            return this;
        }

        public MailUtil build() {
            MailUtil mailUtil = new MailUtil();
            mailUtil.sendUser = this.sendUser;
            mailUtil.receiveUser = this.receiveUser;
            mailUtil.host = this.host;
            mailUtil.password = this.password;
            mailUtil.port = this.port;
            return mailUtil;
        }
    }
}
