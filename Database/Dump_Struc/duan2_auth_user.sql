-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: duan2
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'admin','Nguyễn',NULL,'Duyệt',NULL,'admin@yo.com','0','5876695f8e4e1811','$2a$10$B3oHbYckX3Kn54nVHlhOo.SSS4DGTePm8VfDgl.pZ2cYy69y.2ZFe','2019-09-24',NULL,1,1,0),(2,'duyetndph05701@fpt.edu.vn','Nguyễn','Đình','Duyệt','Nguyễn Đình Duyệt','duyetndph05701@fpt.edu.vn','0','5876695f8e4e1811','$2a$10$l2uAWPHO7La6DuWQmajNZu.PmiVSwr9k5WJl1aNKHHc7L3pkQ3Fey','2020-03-18',NULL,1,1,2),(3,'duyetnguyendinh19','Nguyễn','Đình','Duyệt','Nguyễn Đình Duyệt','duyetnguyendinh19@gmail.com','0','5876695f8e4e1811','$2a$10$iYyRZSKiCgsmGBfZHaAXueIUqAFp9lQ4y0D814s3yUbvyXik5kGAi','2020-03-19',NULL,1,1,3),(4,'luongminhtri.mt1@gmail.com','Nguyễn ','Đình','Duyệt','Nguyễn  Đình Duyệt','luongminhtri.mt1@gmail.com','0','5876695f8e4e1811','$2a$10$pVqi0DOHeKrcsQOHiM2MP.cgsWBQpDkOpcWjd7Eu6iL3PxSdkvErK','2020-03-23',NULL,1,1,2),(5,'duyetnd123','Nguyễn','Đình','Duyệt','Nguyễn Đình Duyệt','duyetnguyendinh16@gmail.com','0','5876695f8e4e1811','$2a$10$yc9eGZjeBCw.ZRBZZqX22uNWXnRujRnaLwmcUXnbp8xEkGTwhPXXe','2020-04-30',NULL,1,1,2);
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-08 23:23:46
