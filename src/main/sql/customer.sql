SET FOREIGN_KEY_CHECKS=0;
use demo;
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL DEFAULT AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*
insert
*/
INSERT into customer VALUES (null,'customer1','cpy','13585979361','540399769@qq.com','');
INSERT into customer VALUES (null,'customer2','yxx','13816917524','578007402@qq.com','');
