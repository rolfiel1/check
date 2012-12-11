/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : 51check

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2012-12-11 14:59:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `author` varchar(100) default NULL,
  `content` longtext,
  `create_date` datetime default NULL,
  `update_date` datetime default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for `lwreport`
-- ----------------------------
DROP TABLE IF EXISTS `lwreport`;
CREATE TABLE `lwreport` (
  `id` int(11) NOT NULL auto_increment COMMENT 'id',
  `sign` int(1) default NULL COMMENT '标志：1.pp  2.万方  3.知网',
  `title` varchar(255) default NULL COMMENT '论文名称',
  `author` varchar(255) default NULL COMMENT '论文作者',
  `link` varchar(255) default NULL COMMENT '论文报告链接地址',
  `remark` varchar(300) default NULL COMMENT '备注',
  `create_date` datetime default NULL,
  `content` longtext,
  `uid` varchar(255) default NULL COMMENT '用户id',
  `ppid` varchar(255) default NULL COMMENT 'pp站上的文件名称唯一',
  PRIMARY KEY  (`id`),
  KEY `lwreport_user_fk` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lwreport
-- ----------------------------
INSERT INTO lwreport VALUES ('1', '1', '123123', '21312', '57017d2482b2dcdc9af8cec104e190ad201211291528970', 'sdfa', '2012-12-03 15:30:04', 'sdfasdfasdf', '123456789', '57017d2482b2dcdc9af8cec104e190ad201211291528970');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment COMMENT 'id',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(32) default NULL COMMENT '密码',
  `sign` int(1) default NULL COMMENT '标志（1管理员0用户）',
  `create_date` datetime default NULL COMMENT '创建时间',
  `login_date` datetime default NULL COMMENT '登录时间',
  `remark` varchar(100) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('1', '123456789', null, null, null, null, null);

-- ----------------------------
-- Table structure for `wz`
-- ----------------------------
DROP TABLE IF EXISTS `wz`;
CREATE TABLE `wz` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(255) default NULL,
  `author` varchar(255) default NULL,
  `content` longtext,
  `create_date` datetime default NULL,
  `type` int(1) default NULL,
  `remark` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wz
-- ----------------------------

-- ----------------------------
-- Table structure for `wztype`
-- ----------------------------
DROP TABLE IF EXISTS `wztype`;
CREATE TABLE `wztype` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `type` int(1) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wztype
-- ----------------------------
INSERT INTO wztype VALUES ('1', '第一', '1');
INSERT INTO wztype VALUES ('2', '第二', '2');
INSERT INTO wztype VALUES ('3', '第三', '3');
INSERT INTO wztype VALUES ('4', '第四', '4');
