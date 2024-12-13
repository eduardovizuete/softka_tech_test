CREATE DATABASE IF NOT EXISTS `microdev`;
USE microdev;
CREATE TABLE `person` (
  `person_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(255) NOT NULL,
  `identification` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_4r2cs4eybw7joyi0u8v7vywhg` (`identification`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `client` (
  `client_id` bigint NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `person_id` bigint NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_bfjdoy2dpussylq7g1s3s1tn8` (`client_id`),
  CONSTRAINT `FKkxflpsue6s9kscgmuwt7ob1f3` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `number` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dbfiubqahb32ns85k023gr6nn` (`number`),
  KEY `FKkm8yb63h4ownvnlrbwnadntyn` (`client_id`),
  CONSTRAINT `FKkm8yb63h4ownvnlrbwnadntyn` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `balance_before_tx` double DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `type` enum('DEPOSIT','WITHDRAWAL') DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6g20fcr3bhr6bihgy24rq1r1b` (`account_id`),
  CONSTRAINT `FK6g20fcr3bhr6bihgy24rq1r1b` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;