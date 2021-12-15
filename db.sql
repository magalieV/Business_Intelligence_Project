-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: bi_db
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `rs_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'$2a$10$1E..4.T4EbTh8Bkw029Tbu8z/lItUKNZUpQ/H3flVL2sZsSOGdN9.','Alie','Van','\"iLTIxaVvbxNIGgaLePSj\"\n'),(8,'$2a$10$gkp7ZqLsLzXyt8GsnKsWB.4xxXLe5zpeRPD5uANhxOvRUgthpfIgi','Kelly','Smith','\"xo93LajACHddN1Ragb4P\"\n'),(9,'$2a$10$b9W6T2ag6FJrGJ82bIAe9O/w.UksB9At.4xvtkN8DGGccmga0fcyq','Jean','James','\"6cNk4yhXKnYbpXa6W60R\"\n'),(10,'$2a$10$87SrS4F/JfWc3HYMkbaloeZJVXRdBdaG51rR7xnKQmp2NHmZQP8om','James','Potter','\"ZrG9dUlt2FWSVaPqqOaK\"\n'),(11,'$2a$10$1Bwx8Vdpq1O/hCbS5q5isOJQw6/Tb8czXD4t9pYmAg1uReuVun1U.','Harry','Potter','\"LOdbjye1n4uKuzIiQdUf\"\n'),(12,'$2a$10$CIBfgPR.9p3X/zJED9bz4ONzp6djCnYK1YpCPuOoc6pvuBf.3EHzK','Alice','Smith','\"MXUhBdGHSHpqMS0Jpaa6\"\n'),(13,'$2a$10$0EEakuBae0h49olRc44ch.zGnxXO8wUpdAvrwX2JOJHte1g/PTATS','Alex','Smith','\"xWn42XueJhqrOX41N5aE\"\n'),(14,'$2a$10$Mtt22Tsle4pcEVO7HBaNVuIm/iWaj1OxGuLLqcprDlNUZ1AYLV4GS','Hermione','Granger','\"VOrus2sqrhUkrKp9pUNM\"\n');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-15  9:49:59
