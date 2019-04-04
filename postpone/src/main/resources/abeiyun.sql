/*
MySQL - 5.6.36 : Database - abeiyun
*********************************************************************
*/

/*
数据库很简单   只存储了一些用户信息
 */

CREATE DATABASE /*!32312 IF NOT EXISTS*/`abeiyun` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `abeiyun`;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `yunusername` varchar(50) DEFAULT NULL COMMENT '阿贝云用户名',
  `yunpassword` varchar(50) DEFAULT NULL COMMENT '阿贝云密码',
  `sinausername` varchar(50) DEFAULT NULL COMMENT '新浪用户名',
  `sinapassword` varchar(50) DEFAULT NULL COMMENT '新浪密码',
  `sina_cookie` text COMMENT '新浪cookie',
  `next_time` datetime DEFAULT NULL COMMENT '下次延期时间',
  `sina_url` varchar(255) DEFAULT 'success' COMMENT '延期博客url',
  `status` varchar(2) DEFAULT NULL COMMENT '0 使用本地cookie, 1 使用接口获取cookie',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

