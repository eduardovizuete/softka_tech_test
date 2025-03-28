USE microdev;
--
-- Dumping data for table `person`
--
LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` (`person_id`, `address`, `age`, `gender`, `identification`, `name`, `telephone`) 
VALUES 
(1,'Otavalo sn y principal',30,'male','1234567890','Jose Lema','098254785'),
(2,'Amazonas y NNUU',29,'female','1234567891','Marianela Montalvo','097548965'),
(3,'13 junio y Equinoccial',28,'male','1234567892','Juan Osorio','098874587');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client`
--
LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`client_id`, `password`, `status`, `person_id`)
VALUES 
(111,'1234','active',1),
(222,'5678','active',2),
(333,'1245','active',3);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `account`
--
LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `balance`, `number`, `status`, `type`, `client_id`) 
VALUES 
(1,2000,'478758','Activo','Ahorros',111),
(2,100,'225487','Activo','Corriente',222),
(3,0,'495878','Activo','Ahorros',333),
(4,540,'496825','Activo','Ahorros',222),
(5,1000,'585545','Activo','Corriente',111);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` 
(`id`,`amount`,`balance_before_tx`,`balance`,`date`,`type`,`account_id`,`is_deleted`)
VALUES 
    (1,575,2000,1425,'2025-01-03 09:04:42.688472','WITHDRAWAL',1,0),
    (2,600,100,700,'2025-01-03 09:05:45.241177','DEPOSIT',2,0),
    (3,150,150,0,'2025-01-03 09:06:30.803102','DEPOSIT',3,0),
    (4,540,0,540,'2025-01-03 09:07:38.860785','WITHDRAWAL',4,0);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` 
(`id`, `enabled`, `password`, `username`) 
VALUES 
	(1, 1, '$2a$10$6ScYcBjlsA4H16Cl2mdXh.VQUp/sJ1TGdsa.xfASu1CfrM4C0eA/e', 'admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

