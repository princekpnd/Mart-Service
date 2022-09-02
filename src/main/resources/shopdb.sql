-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: shop
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message` (
  `MESSAGEID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_FROM` int(10) NOT NULL,
  `USER_TO` int(10) NOT NULL,
  `BODYCONTENT` varchar(500) COLLATE utf8_bin NOT NULL,
  `CREATEDATE` date DEFAULT NULL,
  PRIMARY KEY (`MESSAGEID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message`
--

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;
INSERT INTO `Message` VALUES (1,5,13,'This is very first begining of message','2019-09-29'),(2,13,5,'okay good plz put your question','2019-09-29'),(3,9,16,'hallo 16 how are u',NULL),(4,16,9,'i am good 9',NULL),(5,5,13,'today is sunday','2019-09-29');
/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_transaction`
--

DROP TABLE IF EXISTS `account_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_transaction` (
  `ID` int(11) NOT NULL,
  `DEBIT_FROM` int(11) NOT NULL,
  `CREDIT_TO` int(11) NOT NULL,
  `AMOUNT` int(11) NOT NULL,
  `REMARKS` varchar(200) NOT NULL,
  `STATUS` tinyint(1) NOT NULL,
  `DATE` int(11) NOT NULL,
  `CURRENCY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_transaction`
--

LOCK TABLES `account_transaction` WRITE;
/*!40000 ALTER TABLE `account_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `article_id` int(10) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `Teaser` varchar(200) DEFAULT NULL,
  `body_content` text,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'Courses Test','Let start learning','This is test'),(2,'null test','test null','null Test is going on');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articlefile`
--

DROP TABLE IF EXISTS `articlefile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articlefile` (
  `ArticleFileId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fileName` varchar(200) NOT NULL,
  `fileDescription` varchar(200) DEFAULT NULL,
  `fileType` varchar(45) DEFAULT NULL,
  `fileSize` int(11) DEFAULT NULL,
  `domain` varchar(45) DEFAULT NULL,
  `filePath` varchar(250) DEFAULT NULL,
  `fileUrl` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ArticleFileId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articlefile`
--

LOCK TABLES `articlefile` WRITE;
/*!40000 ALTER TABLE `articlefile` DISABLE KEYS */;
/*!40000 ALTER TABLE `articlefile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_details`
--

DROP TABLE IF EXISTS `bank_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_NO` int(30) NOT NULL,
  `BANK_NAME` varchar(100) NOT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ADDRESS` varchar(200) NOT NULL,
  `IFSC` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_details`
--

LOCK TABLES `bank_details` WRITE;
/*!40000 ALTER TABLE `bank_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `POST_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `POST_CATEGORY` int(11) NOT NULL,
  `ARTICLE` int(11) NOT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `BUDGET` int(11) DEFAULT NULL,
  `CURRENCY` int(11) NOT NULL,
  PRIMARY KEY (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_application`
--

DROP TABLE IF EXISTS `job_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_application` (
  `ID` int(11) NOT NULL,
  `JOB_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `APPL_DATE` int(11) NOT NULL,
  `PROPOSE_PRICE` int(11) NOT NULL,
  `ARTICLE` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_application`
--

LOCK TABLES `job_application` WRITE;
/*!40000 ALTER TABLE `job_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lookup`
--

DROP TABLE IF EXISTS `lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lookup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOOKUP_NAME` varchar(100) COLLATE utf8_bin NOT NULL,
  `LOOKUP_LABEL` varchar(500) COLLATE utf8_bin NOT NULL,
  `LOOKUP_TYPE_ID` int(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup`
--

LOCK TABLES `lookup` WRITE;
/*!40000 ALTER TABLE `lookup` DISABLE KEYS */;
INSERT INTO `lookup` VALUES (1,'USER','USER',1),(2,'ARTICLE','ARTICLE',1),(3,'ARTICLE_FILE','ARTICLE_FILE',1),(4,'USER_ROLE','USER_ROLE',1),(5,'USER_DEVICE','USER_DEVICE',1),(6,'USER_BANK','USER_BANK',1),(7,'INR','INR',2),(8,'USD','USD',2),(9,'EUR','EUR',2),(10,'SELF','SELF',3),(11,'ADMIN','ADMIN',3),(12,'BUSINESS','BUSINESS',4),(13,'FINANCE','FINANCE',4),(14,'BIOSCIENCE','BIOSCIENCE',4),(15,'NANOTECHNOLOGY','NANOTECHNOLOGY',4),(16,'AVIATION','AVIATION',4),(17,'SALES','SALES',12),(18,'MARKETING','MARKETING',12),(19,'AUDIT','AUDIT',12),(20,'ANALYTICS','ANALYTICS',13),(21,'MICROFINANCE','MICROFINANCE',13);
/*!40000 ALTER TABLE `lookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lookup_type`
--

DROP TABLE IF EXISTS `lookup_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lookup_type` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `LOOKUP_TYPE_NAME` varchar(20) COLLATE utf8_bin NOT NULL,
  `LOOKUP_TYPE_LABEL` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lookup_type`
--

LOCK TABLES `lookup_type` WRITE;
/*!40000 ALTER TABLE `lookup_type` DISABLE KEYS */;
INSERT INTO `lookup_type` VALUES (1,'MANAGED_OBJECT_TYPE','MANAGED_OBJECT_TYPE'),(2,'CURRENCY','CURRENCY'),(3,'MANAGED_OBJECT_CREAT','MANAGED_OBJECT_CREATOR'),(4,'CATAGORY','CATAGORY');
/*!40000 ALTER TABLE `lookup_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `managedobject`
--

DROP TABLE IF EXISTS `managedobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `managedobject` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(40) COLLATE utf8_bin NOT NULL,
  `object_type` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `managedobject`
--

LOCK TABLES `managedobject` WRITE;
/*!40000 ALTER TABLE `managedobject` DISABLE KEYS */;
INSERT INTO `managedobject` VALUES (3,'cf13c823-1620-4ce2-bf09-6543dfdc7c48',1,'2019-09-08',0,NULL,0),(4,'5ad923b5-911c-42d2-ac2a-eb65bfe1c65f',1,'2019-09-08',10,NULL,0),(5,'18d17946-40d6-4292-bd6b-80752c652b64',1,'2019-09-08',10,NULL,0),(6,'352dba05-2520-4d59-90c5-31392e45b064',1,'2019-09-08',10,NULL,0);
/*!40000 ALTER TABLE `managedobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `PROFILE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `PROFILE_CATEGORY` int(11) NOT NULL,
  `ARTICLE` int(11) NOT NULL,
  `SKILL_SET` int(11) DEFAULT NULL,
  PRIMARY KEY (`PROFILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(50) CHARACTER SET latin1 NOT NULL,
  `FIRST_NAME` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `LAST_NAME` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `INITIALS` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  `SUPERVISOR_ID` int(11) DEFAULT NULL,
  `ACTIVE_IND` bit(1) NOT NULL,
  `DOB` datetime DEFAULT NULL,
  `LAST_LOGIN_DATE` datetime DEFAULT NULL,
  `MO_ID` int(11) DEFAULT NULL,
  `PWD` varchar(40) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jaykrs@in.com','Avinash','Kumar','Mr',5,_binary '\0','2019-02-02 00:00:00',NULL,123,'Welcome@9'),(2,'jay@inc.com','jay','k','Mr',5,_binary '\0','2019-02-02 00:00:00',NULL,345,'Welcome@0'),(3,'jcykrs@indcom.com','Jatin','micheal',NULL,NULL,_binary '\0',NULL,NULL,3,'V2VsY29tZUA5'),(4,'jcykrs@indcnm.com','Jatin','micheal',NULL,NULL,_binary '\0',NULL,NULL,4,'V2VsY29tZUA5'),(5,'Avinash@shop.com','jay','ant',NULL,NULL,_binary '\0',NULL,NULL,5,'V2VsY29tZUAw'),(6,'omar@shop.com','Omar','ant',NULL,NULL,_binary '\0',NULL,NULL,6,'V2VsY29tZUAw');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_device`
--

DROP TABLE IF EXISTS `user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device` (
  `ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `DEVICE_ID` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_device`
--

LOCK TABLES `user_device` WRITE;
/*!40000 ALTER TABLE `user_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_device_id_mapping`
--

DROP TABLE IF EXISTS `user_device_id_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device_id_mapping` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) NOT NULL,
  `DEVICE_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `APNS_TOKEN_ID` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_device_id_mapping`
--

LOCK TABLES `user_device_id_mapping` WRITE;
/*!40000 ALTER TABLE `user_device_id_mapping` DISABLE KEYS */;
INSERT INTO `user_device_id_mapping` VALUES (1,1,'4345-tr45-23ytr-34ytre-54ttt','55555'),(2,2,'44444444','ttttt444'),(3,0,NULL,NULL);
/*!40000 ALTER TABLE `user_device_id_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `CUSTOMER` tinyint(1) DEFAULT NULL,
  `CONSULTANT` tinyint(1) DEFAULT NULL,
  `ADMIN` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-29 11:50:06
