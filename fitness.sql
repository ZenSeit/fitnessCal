-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: fitnessdb
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `proteinqgr` double NOT NULL,
  `carbsqgr` double NOT NULL,
  `fatqgr` double NOT NULL,
  `referencequantity` double NOT NULL,
  `createdat` date DEFAULT NULL,
  `updatedat` date DEFAULT NULL,
  `deletedat` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'Rice','cooked',2.7,28,0.3,100,'2022-06-13',NULL,NULL),(2,'Chicken Breast','cooked trying',31,0,3.6,100,'2022-06-13','2022-06-13',NULL),(3,'Salmon','cooked',20,0,13,100,'2022-06-13','2022-06-13',NULL),(4,'prueba',NULL,0,0,0,0,'2022-06-13','2022-06-13','2022-06-16'),(5,'Boiled Potatoe','Boiled',2,17.5,0,100,'2022-06-16','2022-06-16',NULL),(6,'Scrambled eggs','grill',10,1.6,11,1,'2022-06-16','2022-06-16',NULL),(7,'Banana','fresh',1,23,0,1,'2022-06-16','2022-06-16',NULL),(8,'Bacon D1','1 piece from package',2,2,6,1,'2022-06-16','2022-06-16',NULL),(9,'Smart Nutrition Protein','1 scoop of 30gr',26,1,0,1,'2022-06-16','2022-06-16',NULL),(10,'Peanuts','100 gr of product',26,16,49,100,'2022-06-16','2022-06-16',NULL),(11,'Bread','100 gr of product',9,49,3.2,100,'2022-06-16','2022-06-16',NULL),(12,'Chitaga Cheese','100 gr of product',20,2.5,24,100,'2022-06-16','2022-06-16',NULL);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relusersfood`
--

DROP TABLE IF EXISTS `relusersfood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relusersfood` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_food` int NOT NULL,
  `quantityuser` double DEFAULT NULL,
  `formquantity` int DEFAULT NULL,
  `day` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_idx` (`id_user`),
  KEY `foodid_idx` (`id_food`),
  CONSTRAINT `foodid` FOREIGN KEY (`id_food`) REFERENCES `food` (`id`),
  CONSTRAINT `userid` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relusersfood`
--

LOCK TABLES `relusersfood` WRITE;
/*!40000 ALTER TABLE `relusersfood` DISABLE KEYS */;
INSERT INTO `relusersfood` VALUES (5,19,2,200,0,0),(15,21,8,2,1,0),(16,21,5,150,0,0),(17,11,3,100,0,0),(18,11,1,300,0,0),(19,11,2,150,0,0),(20,11,10,50,0,0),(24,1,2,20,0,0);
/*!40000 ALTER TABLE `relusersfood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `profilephoto` varchar(300) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `password` varchar(350) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `activityday` int DEFAULT NULL,
  `fitnessgoal` int DEFAULT NULL,
  `createdAt` date DEFAULT NULL,
  `updatedAt` date DEFAULT NULL,
  `deletedAt` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'https://m.media-amazon.com/images/I/71qDSQ05WMS._SS500_.jpg','Zensei','$argon2id$v=19$m=1024,t=1,p=1$1ull+mGtD8Nil4mS1bwkow$9xBmsouMAZHCSl/ZxG8gkBmhoNUyd4oNzgyF9UTe5bA','diegofer1110@correo.com','Diego Fernando','Becerra Zambrano','1993-10-11',1,'Colombia',74.8,176,2,1,'2022-06-08','2022-06-08',NULL),(2,'https://www.geekmi.news/__export/1619491109877/sites/debate/img/2021/04/26/gon1.jpg_375108060.jpg','Gonzo','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','gonzalo@correo.com','Gonzalo','Barrera','1989-08-15',1,'Chile',80.5,170,0,0,'2022-06-08','2022-06-08','2022-06-08'),(3,'https://www.amazon.com/-/es/dp/B097QFWF54','Zensei3','$argon2id$v=19$m=1024,t=1,p=1$MZdsedbyQvpdcwCWFakzHA$NJEhGToLjw40tRGaA9j8luV5QGyS/PVcrz+nOpxH2eo','diego@correo.com','Diego','Becerra','1993-10-11',1,'Colombia',74.8,176,0,0,'2022-06-09','2022-06-09','2022-06-09'),(4,'https://www.amazon.com/-/es/dp/B097QFWF54','Zensei4','$argon2id$v=19$m=1024,t=1,p=1$zrZbpIY45dy6cZ1UWqYc6g$8n6pao2EYpCJKbjv4FDopHKqEpHzlTUuWP/qy+TkFUI','diego@correo.com','Diego','Becerra','1993-10-11',1,'Colombia',74.8,176,0,0,'2022-06-09','2022-06-09','2022-06-09'),(7,NULL,'DanKaiser','$argon2id$v=19$m=1024,t=1,p=1$UA1QS4QxHxFE6crwubjGzQ$zcC+gCtFn9K0pOlYTeUPTmck3UFJL05GimWhmHr1glA','daniel@correo.com','Daniel','Becerra','2001-06-20',1,'Colombia',61,170,0,0,'2022-06-09','2022-06-09',NULL),(8,NULL,'DanKaiser2','$argon2id$v=19$m=1024,t=1,p=1$SaN83tGR5nF/SgkP+46I7w$tgBQjlsDJ6o2CbE7AQQ1RHqq3FkVuaoTsdZ68qqoH64',NULL,'Daniel','Becerra','2001-06-20',1,'Colombia',61,170,0,0,'2022-06-09','2022-06-09',NULL),(9,NULL,'DanKaiser3','$argon2id$v=19$m=1024,t=1,p=1$4KyZGlVcKPu7Mi5D33JSGg$ZP2hQ3d5KLAekdLzYzZbljzmDiyaLGsC2jFT1f+iusA',NULL,'Daniel','Becerra','2001-06-20',1,'Colombia',61,170,0,0,'2022-06-09','2022-06-09',NULL),(10,NULL,'DanKaiser4','$argon2id$v=19$m=1024,t=1,p=1$bodBQ6yz76sQCUlAT8u2HA$Cb1/uXQUux0+4NczvyToVV5BLjPUgNDnqz69b5fp5+E',NULL,'Daniel','Becerra','2001-06-20',1,'Colombia',61,170,0,0,'2022-06-09','2022-06-09',NULL),(11,'imagenes.com','Lau2569','$argon2id$v=19$m=1024,t=1,p=1$BGHdGcwsANEwFoSSDYCmaQ$ka4IIFVDxcK8IKyH2rkskMiGcVHcCzNvCsgpAXKwizY','lau@correo.com','Daniela','Herrera','1994-10-18',2,'Colombia',58,170,2,2,'2022-06-09','2022-06-09',NULL),(12,NULL,'LauS','$argon2id$v=19$m=1024,t=1,p=1$4MS7WF1EMpO7qBTH7slKbA$JOrnQe4K1U/xJIovoj4yhXaPiOUPq5+Eqtcb0UnyoUU','lau@correo.com','Laura','Suarez','1994-10-18',2,'Colombia',58,170,2,1,'2022-06-09','2022-06-09',NULL),(13,'imagenes.com','DanH','$argon2id$v=19$m=1024,t=1,p=1$sRVpnz/87DhRgezsOiB4+A$LiFGnjdqBTVHnQ7r1F+O7VAQPS3kV89APa/VuxhCR3g','dani@correo.com','Daniela','Herrera','1999-07-29',2,'Colombia',61,170,2,2,'2022-06-09','2022-06-10',NULL),(18,'superImaganes.com','Marian89','$argon2id$v=19$m=1024,t=1,p=1$gcWm3gbb9l7OL1M8bTSB+Q$C4XHWfP2nPWvABtxsu+Zghs2kq0+fAQITGv+9wLdbMM','mariana@correo.com',NULL,NULL,NULL,0,NULL,0,0,0,0,'2022-06-10','2022-06-10',NULL),(19,'superImaganeshd.com','Mauro72','$argon2id$v=19$m=1024,t=1,p=1$1fj8K1wSFwRtiYsK0LVo9Q$MXmnG4vuRvGbo9Lj9F1Dbl8iLAI8J09lj/+KaBrzD1g','mauro8954@correo.com',NULL,NULL,NULL,0,NULL,0,0,0,0,'2022-06-10','2022-06-10',NULL),(20,'dexter.jpg','Carlitos85','$argon2id$v=19$m=1024,t=1,p=1$qIpCiwD1STSjkbkIJaHiwg$/FhmjvIHaFMakPJaDZ7YVSCKK9th35TKsbt9klFnYGg','carlosmoreno@correo.com','Carlos','Moreno','1994-11-16',1,'Colombia',69.8,185,2,2,'2022-06-13','2022-06-13',NULL),(21,'worldphotos.com','Tatiana83','$argon2id$v=19$m=1024,t=1,p=1$nAKBtbwnDnwvqgb0yxXNMw$z30cL0zQ34bjaW2mtV/9dyAM7oEUfa7cJF2I93qydeA','tatiana@correo.com','Tatiana','Camacho','2006-12-16',2,'Colombia',66,160,0,0,'2022-06-16','2022-06-16',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_condition`
--

DROP TABLE IF EXISTS `users_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_condition` (
  `idusers_condition` int NOT NULL AUTO_INCREMENT,
  `weight` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `activityday` int DEFAULT NULL,
  `caloriesmaintance` double DEFAULT NULL,
  `fitnessgoal` double DEFAULT NULL,
  `updated_At` datetime DEFAULT NULL,
  PRIMARY KEY (`idusers_condition`),
  CONSTRAINT `id_user` FOREIGN KEY (`idusers_condition`) REFERENCES `users_log` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_condition`
--

LOCK TABLES `users_condition` WRITE;
/*!40000 ALTER TABLE `users_condition` DISABLE KEYS */;
INSERT INTO `users_condition` VALUES (1,74.8,1.76,8,0,0,NULL);
/*!40000 ALTER TABLE `users_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_info`
--

DROP TABLE IF EXISTS `users_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_info` (
  `idusers_info` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `lastname` varchar(150) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `updated_At` datetime DEFAULT NULL,
  PRIMARY KEY (`idusers_info`),
  CONSTRAINT `id_log` FOREIGN KEY (`idusers_info`) REFERENCES `users_log` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_info`
--

LOCK TABLES `users_info` WRITE;
/*!40000 ALTER TABLE `users_info` DISABLE KEYS */;
INSERT INTO `users_info` VALUES (1,'Diego','Becerra',28,'1993-10-11 00:00:00',1,'Colombia',NULL),(6,'Mario','Gonzales',30,'1992-02-15 00:00:00',1,'Argentina',NULL);
/*!40000 ALTER TABLE `users_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_log`
--

DROP TABLE IF EXISTS `users_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `profilephoto` varchar(300) DEFAULT NULL,
  `nickname` varchar(45) NOT NULL,
  `email` varchar(130) NOT NULL,
  `password` varchar(350) NOT NULL,
  `created_At` datetime NOT NULL,
  `deleted_At` datetime DEFAULT NULL,
  `updated_At` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_log`
--

LOCK TABLES `users_log` WRITE;
/*!40000 ALTER TABLE `users_log` DISABLE KEYS */;
INSERT INTO `users_log` VALUES (1,'https://www.amazon.com/-/es/dp/B097QFWF54','Zensei','diegofer@correo.com','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','2022-06-07 09:35:27',NULL,NULL),(6,'imagenes.com','Gonzo','gon@correo.com','1234','2022-06-08 10:04:51',NULL,NULL);
/*!40000 ALTER TABLE `users_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-21 17:44:14
