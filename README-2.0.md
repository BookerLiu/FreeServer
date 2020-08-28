
[![Download](https://img.shields.io/github/stars/Demo-Liu/FreeServer?style=social)](https://github.com/Demo-Liu/FreeServer) [![Download](https://img.shields.io/github/search/Demo-Liu/FreeServer/FreeServer)](https://github.com/Demo-Liu/FreeServer)
# FreeServer 2.0
**新版本终于到来**  
最近发现了两个叫 [阿贝云](http://www.abeiyun.com/free/)  [三丰云](https://www.sanfengyun.com/)(新加) 的云服务器厂商,推出了一款号称永久免费的服务器,比较恶心的是,每过几天就要进行一次延期(在推荐的网站发表一篇推广软文,然后将文章截图提交),于是写了这个自动延期的项目 
  

# 新版变化
  - 增加jar包方式部署(默认),更加方便
  - 发布博客方式, 1.0使用第三方博客发布,2.0则是使用个人搭建博客发布
  - 相对于1.0 删除了数据库方式,改为配置文件方式,减小了配置量
  - **windows** 和 **linux_x86_64** 系统 [phantomjs](https://phantomjs.org/download.html)无需再配置(相应的项目体积增大,后续可能会有所改变)  
  其它系统请自行下载[phantomjs](https://phantomjs.org/download.html) 并在项目application-config.yml中指定phantomjs路径
# 项目架构
JDK1.8 + SpringBoot + [PhantomJS](http://phantomjs.org/download.html)  
# 总体流程
![postpone](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer%E5%BB%B6%E6%9C%9F2.0.png)
# 部署
  **2.0 使用 GitHub Pages 进行发布博客,我提供了一个公共的仓库(已在配置文件中)进行发布博客,但推荐使用自己搭建的GitHub Pages,使用公用仓库发布博客,可能由于博客初始化过久而导致程序超时,审核失败**  
  - **1.JDK1.8**  
  2.0之后,项目仅仅需要配置基础的运行环境jdk即可
  - **2.申请三丰云或阿贝云免费服务器**  
  [阿贝云](http://www.abeiyun.com/free/) [三丰云](https://www.sanfengyun.com/)
  注册完账号后 进入**控制台**, 点击 **产品-->免费产品-->免费云服务器**  
  ![](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer/%E7%94%B3%E8%AF%B7%E6%AD%A5%E9%AA%A4.png)
  - **3.搭建个人博客(可选),推荐使用个人博客**    
  为了照顾懒癌晚期的朋友,你只需要 Fork 这个仓库即可-->[demo-liu.github.io](https://github.com/Demo-Liu/demo-liu.github.io)  
  **记得仔细看一看其中READEME**,其中包含了搭建个人博客和生成ssh key的详细步骤,这里不再一一赘述
  - **4.指定application-config.yml配置**    
  在项目application-config.yml中配置你的**云账号密码**,**邮箱**,**个人博客地址**等配置
  - **5.发布运行项目**    
  项目默认打包方式为jar,可以自行更改  
  jar包运行方式 如下, **log.log** 为指定的日志输出文件
  ```
  nohup java -jar postpone.jar > log.log 2>&1 &
  ```  
  另外你可以创建以下脚本 **start.sh stop.sh**,并赋予可执行权限 将这两个脚本同postpone.jar放在同级目录中 以便快速的**启动 停止** 项目  
  - **start.sh**  
    
    ```
    #! /bin/bash
      nohup java -jar postpone.jar > log.log 2>&1 &
      echo $! > postpone.pid
    ```
     
   - **stop.sh**
     
     ```
      #! /bin/bash
      PID=$(cat postpone.pid)
      kill -9 $PID
     ```
  
    
    
    
    
# 如何打war包?
- **1.修改pom.xml文件中的打包方式为war,注释图中所示部分**  

![](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer/war1.png)  

- **2.将com.ff.postpone.ServletInitializer类中如下图所示部分,取消注释**  

![](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer/war2.png)
    
      
      
# linux 如何修改jar包内的配置文件
  ### linux修改jar包内的配置文件需要安装 **vim zip unzip**
  centos执行
  ```
  yum install -y vim zip unzip
  ```
  ubuntu执行
  ```
  sudo apt-get install -y vim zip unzip
  ```
  ### 修改jar包配置文件
  1.输入以下命令会罗列jar包内所有文件
  ```
  vim postpone.jar
  ```
  2.在文件罗列界面输入以下命令查找需要修改的文件,然后回车进入文件内修改
  ```
  /applicaiton-config.yml
  ```
  3.修改完毕后依次执行以下命令保存退出
  ```
  :wq!
  ```
  ```
  :q
  ```
  
  
# PS
如果你申请了多个免费服务器,甚至可以用它来做集群使用:+1:  
另外,有任何好的功能或修改,可以PR给我或者提交Issues  

如果觉得还不错,请作者喝杯咖啡吧 :couplekiss:  
<img src="https://github.com/Demo-Liu/MyPicture/raw/master/%E6%94%B6%E6%AC%BE%E7%A0%81.png" width="700" height="330" />  
欢迎关注我的微信公众号 "**抓几个娃**",回复 **免费服务器** 随时了解此项目的最新信息,还有各种好玩的项目等你发现  
![postpone](https://img-blog.csdnimg.cn/20200525141707561.jpg)
