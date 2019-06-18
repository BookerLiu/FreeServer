# FreeServer
最近发现了两个叫 [阿贝云](http://www.abeiyun.com/free/)  [三丰云](https://www.sanfengyun.com/)(新加) 的云服务器厂商,推出了一款号称永久免费的服务器,比较恶心的是,每过几天就要进行一次延期(在推荐的网站发表一篇推广软文,然后将文章截图提交),于是写了这个自动延期的项目
# Log
数据库脚本及phantomjs所用到的截图js已上传至 resources目录下  
新加了[三丰云](https://www.sanfengyun.com/)  
20190617重构了项目,新加入了发布CSDN博客进行延期,并且**新浪博客的发布已停止维护**,不对其可用性做保证  
添加邮件通知功能
# 项目架构
JDK1.8 + SpringBoot + Mybatis + Mysql5.6 + [PhantomJS](http://phantomjs.org/download.html)  
# 总体流程
![postpone](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer%E5%BB%B6%E6%9C%9F2.0.png)
# PS
如果你申请了多个免费服务器,你甚至可以用它来做集群使用:+1:
