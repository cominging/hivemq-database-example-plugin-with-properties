CREATE TABLE `messages` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `client_id` TEXT NOT NULL ,
    `topic` TEXT NOT NULL ,
    `message` MEDIUMTEXT NOT NULL ,
    `qos` TINYINT UNSIGNED NOT NULL ,
    `receive_time` DATETIME NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `username`, `password`)
  VALUES
  (1, 'test', 'test');
