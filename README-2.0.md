
[![Download](https://img.shields.io/github/stars/Demo-Liu/FreeServer?style=social)](https://github.com/Demo-Liu/FreeServer) [![Download](https://img.shields.io/github/search/Demo-Liu/FreeServer/FreeServer)](https://github.com/Demo-Liu/FreeServer)
# FreeServer 2.0
**新版本终于到来**
最近发现了两个叫 [阿贝云](http://www.abeiyun.com/free/)  [三丰云](https://www.sanfengyun.com/)(新加) 的云服务器厂商,推出了一款号称永久免费的服务器,比较恶心的是,每过几天就要进行一次延期(在推荐的网站发表一篇推广软文,然后将文章截图提交),于是写了这个自动延期的项目 
  

# 新版变化
  - 发布博客方式, 1.0使用公用博客发布,2.0则是使用个人搭建博客发布
  - 相对于1.0 删除了数据库方式,改为配置文件方式,减小了配置量
  - windows 和 linux_x86_64系统 phantomjs无需再配置(相应的项目体积增大,后续可能会有所改变)
# 项目架构
JDK1.8 + SpringBoot + [PhantomJS](http://phantomjs.org/download.html)  
# 总体流程
![postpone](https://github.com/Demo-Liu/MyPicture/raw/master/FreeServer%E5%BB%B6%E6%9C%9F2.0.png)
# 部署
  2.0 使用 GitHub Pages 进行发布博客,我提供了一个公共的仓库(已在配置文件中)进行发布博客,推荐使用自己搭建的GitHub Pages
  1.JDK1.8
  2.申请三丰云或阿贝云免费服务器   百度即可
  3.搭建个人博客(可选),推荐使用个人博客
  4.指定application-config.yml配置
  5.发布运行项目
# PS
如果你申请了多个免费服务器,你甚至可以用它来做集群使用:+1:  
另外,你有任何好的功能或修改,可以PR给我

欢迎关注我的微信公众号 "**抓几个娃**",回复 **免费服务器** 随时了解此项目的最新信息,还有各种好玩的项目等你发现  
![postpone](https://img-blog.csdnimg.cn/20200525141707561.jpg)
