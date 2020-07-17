# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.18)
# Database: voter
# Generation Time: 2020-07-17 03:22:24 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_authority
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_authority`;

CREATE TABLE `t_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT 'name',
  `authority` varchar(32) NOT NULL DEFAULT 'ROLE_USER' COMMENT 'USER, ADMIN, SUPER',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_username_authority` (`username`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table t_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_item`;

CREATE TABLE `t_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quest_id` int(11) NOT NULL,
  `content` varchar(32) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_quest_id` (`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_quest
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_quest`;

CREATE TABLE `t_quest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valid_sec` int(11) NOT NULL DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(24) NOT NULL DEFAULT '' COMMENT 'user phone',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT 'password',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT 'password',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_vote
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_vote`;

CREATE TABLE `t_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user quest item rel',
  `user_id` int(11) NOT NULL COMMENT 'user id',
  `quest_id` int(11) NOT NULL COMMENT 'question id',
  `item_id` int(11) NOT NULL COMMENT 'itme id',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_ques_item` (`user_id`,`quest_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table t_authority
# ------------------------------------------------------------
LOCK TABLES `t_authority` WRITE;
/*!40000 ALTER TABLE `t_authority` DISABLE KEYS */;

INSERT INTO `t_authority` (`id`, `username`, `authority`, `enabled`, `create_time`, `update_time`)
VALUES
	(1,'huang','ROLE_USER',1,'2019-02-25 11:30:40','2019-02-25 12:42:18'),
	(2,'jiang','ROLE_ADMIN',1,'2019-02-25 13:00:13','2019-02-25 13:00:31');

/*!40000 ALTER TABLE `t_authority` ENABLE KEYS */;
UNLOCK TABLES;

# Dump of table t_user
# ------------------------------------------------------------
LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`id`, `phone`, `password`, `username`, `enabled`, `create_time`, `update_time`)
VALUES
	(1,'','$2a$10$MWU/YblpydjBB8AvpNBQ/.PLxLuBEbGTZbX5Sd5BlT2LFvPESIh/2','huang',1,'2019-02-24 12:10:12','2020-06-22 11:01:44'),
	(2,'','$2a$10$MWU/YblpydjBB8AvpNBQ/.PLxLuBEbGTZbX5Sd5BlT2LFvPESIh/2','jiang',1,'2019-02-24 22:10:36','2020-06-22 11:01:47');

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
