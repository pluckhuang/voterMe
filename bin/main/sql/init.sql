CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(24) NOT NULL DEFAULT '' COMMENT 'user phone',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT 'password',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT 'name',
  `enabled` boolean not NULL DEFAULT TRUE,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user quest item rel',
  `user_id` int(11) NOT NULL COMMENT 'user id',
  `quest_id` int(11) NOT NULL COMMENT 'question id',
  `item_id` int(11) NOT NULL COMMENT 'itme id',
  `enabled` boolean NOT NULL DEFAULT TRUE,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_ques_item` (`user_id`,`quest_id`, `item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_quest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL DEFAULT '',
  `enabled` boolean DEFAULT TRUE,
  `valid_sec` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quest_id` int(11) NOT NULL,
  `content` varchar(32) NOT NULL DEFAULT '',
  `enabled` boolean DEFAULT TRUE,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  index `idx_quest_id` (`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT 'name',
  `authority` varchar(32) NOT NULL DEFAULT 'ROLE_USER' COMMENT 'USER, ADMIN, SUPER',
  `enabled` boolean DEFAULT TRUE,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  index `idx_username_authority` (`username`, `authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
