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
-- Dumping data for table `bank_info`
--

LOCK TABLES `bank_info` WRITE;
/*!40000 ALTER TABLE `bank_info` DISABLE KEYS */;
INSERT INTO `bank_info` VALUES (1,'VIETCOMBANK','Ngân hàng Ngoại thương (Vietcombank)','/static/bank/vietcombank_logo.png',0,''),(2,'VIETINBANK','Ngân hàng Công thương (Vietinbank)','/static/bank/vietinbank_logo.png',0,''),(3,'BIDV','Ngân hàng đầu tư và phát triển Việt Nam (BIDV)','/static/bank/bidv_logo.png',0,NULL),(4,'AGRIBANK','Ngân hàng Nông nghiệp (Agribank)','/static/bank/agribank_logo.png',0,NULL),(5,'SACOMBANK','Ngân hàng TMCP Sài Gòn Thương Tín (SacomBank)','/static/bank/sacombank_logo.png',0,NULL),(6,'TECHCOMBANK','Ngân hàng Kỹ thương Việt Nam (TechcomBank)	','/static/bank/techcombank_logo.png',0,NULL),(7,'ACB','Ngân hàng ACB','/static/bank/acb_logo.png',0,NULL),(8,'VPBANK','Ngân hàng Việt Nam Thịnh vượng (VPBank)','/static/bank/vpbank_logo.png',0,NULL),(9,'DONGABANK	','Ngân hàng Đông Á (DongABank)','/static/bank/dongabank_logo.png',0,NULL),(10,'EXIMBANK','Ngân hàng EximBank','/static/bank/eximbank_logo.png',0,NULL),(11,'TPBANK','Ngân hàng Tiên Phong (TPBank)','/static/bank/tpbank_logo.png',0,NULL),(12,'NCB','Ngân hàng Quốc dân (NCB)','/static/bank/ncb_logo.png',0,NULL),(13,'OJB','Ngân hàng Đại Dương (OceanBank)	','/static/bank/ojb_logo.png',0,NULL),(14,'MSBANK','Ngân hàng Hàng Hải (MSBANK)','/static/bank/msbank_logo.png',0,NULL),(15,'HDBANK','Ngan hàng HDBank','/static/bank/hdbank_logo.png',0,NULL),(16,'NAMABANK','Ngân hàng Nam Á (NamABank)','/static/bank/namabank_logo.png',0,NULL),(17,'OCB','Ngân hàng Phương Đông (OCB)','/static/bank/ocb_logo.png',0,NULL),(18,'VISA','Thẻ quốc tế Visa','/static/bank/visa_logo.png',2,NULL),(19,'MASTERCARD','Thẻ quốc tế MasterCard','/static/bank/mastercard_logo.png',2,NULL),(20,'JCB','Thẻ quốc tế JCB','/static/bank/jcb_logo.png',2,NULL),(21,'VNMART','Ví điện tử VnMart','/static/bank/vnmart_logo.png',0,NULL),(22,'SCB','Ngân hàng TMCP Sài Gòn (SCB)','/static/bank/scb_logo.png',0,NULL),(23,'IVB','Ngân hàng TNHH Indovina (IVB)','/static/bank/ivb_logo.png',0,NULL),(24,'ABBANK','Ngân hàng thương mại cổ phần An Bình (ABBANK)','/static/bank/abbank_logo.png',0,NULL),(25,'SHB','Ngân hàng Thương mại cổ phần Sài Gòn (SHB)','/static/bank/shb_logo.png',0,NULL),(26,'VIB','Ngân hàng Thương mại cổ phần Quốc tế Việt Nam (VIB)','/static/bank/vib_logo.png',0,NULL),(27,'VNPAYQR','Cổng thanh toán VNPAYQR','/static/bank/CTT-VNPAY-QR.png',0,NULL),(28,'VIETCAPITALBANK','Ngân Hàng Bản Việt','/static/bank/vccb_logo.png',0,NULL),(29,'PVCOMBANK','Ngân hàng TMCP Đại Chúng Việt Nam','/static/bank/PVComBank_logo.png',0,NULL),(30,'SAIGONBANK','Ngân hàng thương mại cổ phần Sài Gòn Công Thương','/static/bank/saigonbank.png',0,NULL),(31,'MBBANK','Ngân hàng thương mại cổ phần Quân đội','/static/bank/mbbank_logo.png',0,NULL),(32,'BACABANK','Ngân Hàng TMCP Bắc Á','/static/bank/bacabank_logo.png',0,NULL),(33,'UPI','UnionPay International','/static/bank/upi_logo.png',0,NULL),(34,'VIETCOMBANK','Ngân hàng Ngoại thương (Vietcombank)','/static/bank/vietcombank_logo.png',1,'{\"atmNumber\" : \"12500012312\" , \"name\" : \"Bùi Văn Tấn\",\"bank_name\": \"VietComBank chi nhánh Cầu Giấy, Hà Nội\", \"urlIpay\" : \"https://www.vietcombank.com.vn/IBanking2015/55c3c0a782b739e063efa9d5985e2ab4/Account/Login\"}'),(35,'VIETINBANK','Ngân hàng Công thương (Vietinbank)','/static/bank/vietinbank_logo.png',1,'{\"atmNumber\" : \"123123123\" , \"name\" : \"Nguyễn Đình Duyệt\",\"bank_name\": \"VietinBank chi nhánh Hà Đông, Hà Nội\", \"urlIpay\" : \"https://ebanking.vietinbank.vn/rcas/portal/web/retail/bflogin\"}');
/*!40000 ALTER TABLE `bank_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-19 16:22:50
