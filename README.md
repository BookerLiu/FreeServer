
[![Download](https://img.shields.io/github/stars/Demo-Liu/FreeServer?style=social)](https://github.com/Demo-Liu/FreeServer) [![Download](https://img.shields.io/github/search/Demo-Liu/FreeServer/FreeServer)](https://github.com/Demo-Liu/FreeServer)
# FreeServer
最近发现了两个叫 [阿贝云](http://www.abeiyun.com/free/)  [三丰云](https://www.sanfengyun.com/)(新加) 的云服务器厂商,推出了一款号称永久免费的服务器,比较恶心的是,每过几天就要进行一次延期(在推荐的网站发表一篇推广软文,然后将文章截图提交),于是写了这个自动延期的项目
# Log
数据库脚本及phantomjs所用到的截图js已上传至 resources目录下  
新加了[三丰云](https://www.sanfengyun.com/)  
20190617重构了项目,新加入了发布CSDN博客进行延期,并且**新浪博客的发布已停止维护**,不对其可用性做保证  
添加邮件通知功能  
**20190911**-**CSDN登录接口加密,接口获取cookie不可用,暂时手动配置cookie**
# 项目架构
JDK1.8 + SpringBoot + Mybatis + Mysql5.6 + [PhantomJS](http://phantomjs.org/download.html)  
# 总体流程
![postpone](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer%E5%BB%B6%E6%9C%9F2.0.png)
# 部署
1. JDK1.8 + MySql5.6
2. 申请三丰云或阿贝云免费服务器   百度即可
3. 执行/resources/database.sql 数据库脚本, 添加用户数据,请配置CSDN博客,新浪的我已停止维护,不对其可用性做保证
4. 下载 PhantomJS 2.1.1 下载地址:[http://phantomjs.org/download.html](http://phantomjs.org/download.html)
5. 在/resources/Properties.properties 文件中指定PhantomJS配置目录及邮箱配置  JS文件在/resources目录下
6. 发布运行项目
# PS
如果你申请了多个免费服务器,你甚至可以用它来做集群使用:+1:  
另外,你有任何好的功能或修改,可以PR给我
