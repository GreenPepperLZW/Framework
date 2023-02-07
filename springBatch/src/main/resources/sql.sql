CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `firstName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `lastName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `birthday` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
