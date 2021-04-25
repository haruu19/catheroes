# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

-- ssafydb.`user` definition

CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;