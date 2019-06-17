/*
MySQL - 5.6.36 : Database - freeserver
*********************************************************************
*/

/*
数据库很简单   只存储了一些用户信息
 */


CREATE DATABASE /*!32312 IF NOT EXISTS*/`freeserver` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `freeserver`;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cloud_user` varchar(50) NOT NULL COMMENT '云用户名',
  `cloud_pass` varchar(50) NOT NULL COMMENT '云密码',
  `blog_user` varchar(50) NOT NULL COMMENT '博客用户名',
  `blog_pass` varchar(50) NOT NULL COMMENT '博客密码',
  `blog_url` varchar(255) DEFAULT 'success' COMMENT '延期博客url',
  `blog_cookie` text COMMENT '博客网站cookie',
  `next_time` datetime DEFAULT NULL COMMENT '下次延期时间',
  `cloud_type` varchar(2) NOT NULL DEFAULT '0' COMMENT '云类型 (0 阿贝云, 1 三丰云)',
  `blog_type` varchar(2) NOT NULL DEFAULT '1' COMMENT '使用博客类型 (0 新浪博客, 1 CSDN博客)',
  `cookie_type` varchar(2) NOT NULL DEFAULT '1' COMMENT 'cookie获取方式(0 使用本地cookie, 1 使用接口获取cookie)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

