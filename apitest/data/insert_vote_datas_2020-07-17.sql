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
# Dump of table t_item
# ------------------------------------------------------------
LOCK TABLES `t_item` WRITE;
/*!40000 ALTER TABLE `t_item` DISABLE KEYS */;

INSERT INTO `t_item` (`id`, `quest_id`, `content`, `create_time`, `update_time`, `enabled`)
VALUES
	(1,1,'ping-pong','2019-04-23 12:16:46','2019-04-24 11:42:39',1),
	(2,1,'basketball','2019-04-23 12:16:46','2019-04-24 11:42:39',1),
	(3,1,'soccer','2019-04-23 12:16:46','2019-04-24 11:42:39',1),
	(4,2,'yes','2019-04-23 12:16:46','2019-04-24 11:42:39',1),
	(5,2,'no','2019-04-23 12:16:46','2019-04-24 11:42:39',1);

/*!40000 ALTER TABLE `t_item` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_quest
# ------------------------------------------------------------
LOCK TABLES `t_quest` WRITE;
/*!40000 ALTER TABLE `t_quest` DISABLE KEYS */;

INSERT INTO `t_quest` (`id`, `title`, `create_time`, `valid_sec`, `update_time`, `enabled`)
VALUES
	(1,'whta is your favorite sports?','2019-04-23 12:16:46',236000000,'2019-04-27 08:40:59',1),
	(2,'do you like me?','2019-04-23 12:16:46',236000000,'2019-04-27 08:41:03',1);

/*!40000 ALTER TABLE `t_quest` ENABLE KEYS */;
UNLOCK TABLES;
