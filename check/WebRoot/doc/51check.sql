/*
MySQL Data Transfer
Source Host: localhost
Source Database: 51check
Target Host: localhost
Target Database: 51check
Date: 2012-12-24 22:04:39
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for article
-- ----------------------------
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `content` longtext,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lwreport
-- ----------------------------
CREATE TABLE `lwreport` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sign` int(1) DEFAULT NULL COMMENT '标志：1.pp  2.万方  3.知网',
  `title` varchar(255) DEFAULT NULL COMMENT '论文名称',
  `author` varchar(255) DEFAULT NULL COMMENT '论文作者',
  `link` varchar(255) DEFAULT NULL COMMENT '论文报告链接地址',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL,
  `content` longtext,
  `uid` varchar(255) DEFAULT NULL COMMENT '用户id',
  `ppid` varchar(255) DEFAULT NULL COMMENT 'pp站上的文件名称唯一',
  `need_price` double(13,2) DEFAULT NULL COMMENT '需要付费',
  PRIMARY KEY (`id`),
  KEY `lwreport_user_fk` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `sign` int(1) DEFAULT NULL COMMENT '标志（1管理员0用户）',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `login_date` datetime DEFAULT NULL COMMENT '登录时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `account` double(13,2) DEFAULT NULL COMMENT '账户值',
  `price` double(13,2) DEFAULT NULL COMMENT '订单价值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wz
-- ----------------------------
CREATE TABLE `wz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `content` longtext,
  `create_date` datetime DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wztype
-- ----------------------------
CREATE TABLE `wztype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `lwreport` VALUES ('1', '1', '123123', '21312', '57017d2482b2dcdc9af8cec104e190ad201211291528970', 'sdfa', '2012-12-03 15:30:04', 'sdfasdfasdf', '123456789', '57017d2482b2dcdc9af8cec104e190ad201211291528970', null);
INSERT INTO `lwreport` VALUES ('2', '1', '车市信息', '消费者', 'b87763b4861717e61b7a4f608812320f20121224204849726', null, '2012-12-24 20:51:23', '车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息', '211756915814746', 'b87763b4861717e61b7a4f608812320f20121224204849726', '1.60');
INSERT INTO `lwreport` VALUES ('4', '1', '车市信息', '消费者', 'b87763b4861717e61b7a4f608812320f20121224215224510', null, '2012-12-24 21:52:27', '车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息车市信息', '211756915814746', 'b87763b4861717e61b7a4f608812320f20121224215224510', '1.60');
INSERT INTO `user` VALUES ('1', '123456789', null, null, '2012-12-13 15:58:01', '2012-12-13 16:18:43', null, null, null);
INSERT INTO `user` VALUES ('2', 'admin', '21232f297a57a5a743894a0e4a801fc3', null, '2012-12-13 15:58:04', '2012-12-13 16:18:47', null, null, null);
INSERT INTO `user` VALUES ('6', '211756915814746', null, '0', '2012-12-24 20:34:54', null, null, '1.60', '0.00');
INSERT INTO `wz` VALUES ('1', '测试文章', '赵晓飞', '测试文章编码，<strong>事实上，有很多不同<span style=\"background-color:#CC33E5;\">的颜色</span>和</strong><span style=\"background-color:#E56600;\"></span><u><em>形状</em></u>', null, '1', '测试而已');
INSERT INTO `wztype` VALUES ('1', '第一', '1');
INSERT INTO `wztype` VALUES ('2', '第二', '2');
INSERT INTO `wztype` VALUES ('3', '第三', '3');
INSERT INTO `wztype` VALUES ('4', '第四', '4');
