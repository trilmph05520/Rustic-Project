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
-- Table structure for table `vnpay_trans_info`
--

DROP TABLE IF EXISTS `vnpay_trans_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vnpay_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `vnp_locale` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_curr_code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_order_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_order_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_amount` bigint(20) DEFAULT NULL,
  `vnp_ip_addr` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_create_date` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_bank_code` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_bank_tran_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_pay_date` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_transaction_no` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnp_response_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_bill` bigint(20) DEFAULT NULL,
  `code` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-19 16:21:17
