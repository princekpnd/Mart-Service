/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 5.7.33-log : Database - combinedShop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`combinedShop` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `combinedShop`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` int(20) NOT NULL,
  `USER_TYPE` int(20) DEFAULT NULL,
  `ACCOUNT_NUM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `IFSC` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `BANK_NAME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `CURRENCY` int(11) DEFAULT NULL,
  `UPDATED_ON` datetime NOT NULL,
  `CREATED_ON` datetime NOT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `ACCOUNT_HOLDER_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `BRANCH_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `PAN_NUM` varchar(15) COLLATE utf8_bin NOT NULL,
  `ADHAR_NUM` varchar(15) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `account` */

insert  into `account`(`ID`,`ADMIN_ID`,`USER_TYPE`,`ACCOUNT_NUM`,`IFSC`,`BANK_NAME`,`CURRENCY`,`UPDATED_ON`,`CREATED_ON`,`MOBILE_NUMBER`,`SHOP_ID`,`IS_DELETED`,`IS_ACTIVE`,`ACCOUNT_HOLDER_NAME`,`BRANCH_NAME`,`PAN_NUM`,`ADHAR_NUM`) values 
(1,3,2,'2334333','5455ttrr','BOB',0,'2021-08-17 21:40:22','2021-08-17 21:40:22',NULL,'MILAAN43',0,1,'Raja Kumar','Patna','ff5r555','34445554'),
(3,2,1,'223223','de334e','SBI',0,'2021-08-20 15:17:34','2021-08-20 15:17:34','4545454545','MILAAN42',0,1,'Rahul Kumar','Patna','233eree','233423343434'),
(4,1,1,'123','asd','sbi',0,'2021-08-20 15:46:53','2021-08-20 15:46:53','454545454','MILAAN61',0,1,'radh','patn','da45','5655443333'),
(5,8,1,'12345678996','Rt123uy','Bob',0,'2021-08-21 11:09:35','2021-08-21 11:09:35','123456789098','MILAAN98',0,1,'Ram','Patna ','57wgeu','123456789963');

/*Table structure for table `account_transaction` */

DROP TABLE IF EXISTS `account_transaction`;

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

/*Data for the table `account_transaction` */

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PIN_CODE` int(20) DEFAULT NULL,
  `CITY` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `STATE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `COUNTRY` varchar(100) COLLATE utf8_bin NOT NULL,
  `LONGITUDE` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `LATITUDE` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `LAND_MARK` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` varchar(30) COLLATE utf8_bin NOT NULL,
  `DEFAULT_ADDRESS` tinyint(1) DEFAULT '0',
  `USER_TYPE` int(3) NOT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `POST_OFFICE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `POLICE_STATION` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_ID` int(5) DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STREET` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `address` */

insert  into `address`(`ID`,`PIN_CODE`,`CITY`,`STATE`,`COUNTRY`,`LONGITUDE`,`LATITUDE`,`MOBILE_NUMBER`,`LAND_MARK`,`USER_ID`,`DEFAULT_ADDRESS`,`USER_TYPE`,`SHOP_ID`,`CREATED_ON`,`IS_DELETED`,`IS_ACTIVE`,`DISTRICT`,`POST_OFFICE`,`POLICE_STATION`,`ADMIN_ID`,`NAME`,`STREET`) values 
(1,821305,'S BLOCK ','Bihar ','India ',NULL,NULL,'9507247655 ',NULL,'1',1,2,'','2021-08-17',0,1,'ROHTAS ','DALMIANAGAR ','DALMIANAGAR ',NULL,'Deep','S BLOCK '),
(2,800002,'Patna','Bihar','India',NULL,NULL,'767725666',NULL,'2',1,2,'','2021-08-17',0,1,'Patna','Anishabad','Gardanibagh',NULL,'Raju','1'),
(3,821305,'Dehri','Bihar','India',NULL,NULL,'9912316625',NULL,'3',1,2,'','2021-08-17',0,1,'Rojtas','Dehri','Dalmianagar ',NULL,'Vishal','Dalmianagar '),
(4,821305,'DELHI','DELHI','INDIA',NULL,NULL,'1111111111','PAHARGANJ','4',1,2,'','2021-08-18',0,1,'DELHI','DELHI','DELHI',NULL,'VISHAL','NEAR NEW DELHI RAILWAY STATION'),
(5,841301,'Digha Ashiyana Road','Bihar','India',NULL,NULL,'7903615551',NULL,'6',1,2,'','2021-08-20',0,1,'Patna','Digha','Digha',NULL,'Akhilesh Kumar','Digha Ashiyana Road'),
(6,250615,'Chamrawal','Uttar Pradesh','India',NULL,NULL,'9876543210',NULL,'7',1,2,'','2021-08-21',0,1,'Baghpat','Dhikoli','Dhikoli',NULL,'Prabhakar Tiwari','4'),
(8,821305,'F kg zj','Sbdjx','India',NULL,NULL,'987753126788',NULL,'4',0,2,'','2021-08-25',0,1,'Xjfk','Xjjc','Gjfk',NULL,'Dirk 1st','HJsh'),
(9,1313,'qweqe','wqeqwe','12313',NULL,NULL,'121212121',NULL,'11',1,2,'','2021-08-26',0,1,'eqweqe','eqe','weqe',NULL,'qwq','wqeq'),
(10,821305,'Bos','Bihar','India',NULL,NULL,'9155316625',NULL,'14',1,2,'','2021-08-26',0,1,'Hsjsh','Hsjsj','Hshdb',NULL,'Vajsv','Bsjs');

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `F_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `L_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(11) NOT NULL,
  `TOKEN` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `AVATAR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PAN_AVATAR` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `ADHAR_AVATAR` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `ADHAR_NUM` varchar(15) COLLATE utf8_bin DEFAULT '0',
  `PAN_NUM` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `PWD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_TYPE` int(6) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `LAST_LOGIN_DATE` datetime DEFAULT NULL,
  `OTP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `GST_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `AVAILABLE_AMOUNT` float NOT NULL DEFAULT '0',
  `WALLET` float NOT NULL DEFAULT '0',
  `PAYMENT_STATUS` tinyint(1) DEFAULT NULL,
  `VALIDITY` int(10) DEFAULT NULL,
  `GENDER` int(1) DEFAULT NULL,
  `LATITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LONGITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REGISTRATION_STATUS` int(1) NOT NULL DEFAULT '0',
  `DESCRIPTION` varchar(5000) COLLATE utf8_bin DEFAULT NULL,
  `VALIDITY_EXPIRY_DATE` datetime DEFAULT NULL,
  `VALIDITY_UPDATED_ON` datetime DEFAULT NULL,
  `PLAN_PURCHASED_ON` datetime DEFAULT NULL,
  `REFRAL_CODE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LOGED_IN` tinyint(1) NOT NULL DEFAULT '0',
  `PLAYER_ID` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `admin` */

insert  into `admin`(`ID`,`EMAIL_ID`,`SHOP_ID`,`F_NAME`,`L_NAME`,`USER_TYPE`,`TOKEN`,`AVATAR`,`PAN_AVATAR`,`ADHAR_AVATAR`,`ADHAR_NUM`,`PAN_NUM`,`MOBILE_NUMBER`,`CREATED_ON`,`PWD`,`SHOP_NAME`,`SHOP_TYPE`,`IS_ACTIVE`,`IS_DELETED`,`LAST_LOGIN_DATE`,`OTP`,`GST_NUMBER`,`AVAILABLE_AMOUNT`,`WALLET`,`PAYMENT_STATUS`,`VALIDITY`,`GENDER`,`LATITUDE`,`LONGITUDE`,`REGISTRATION_STATUS`,`DESCRIPTION`,`VALIDITY_EXPIRY_DATE`,`VALIDITY_UPDATED_ON`,`PLAN_PURCHASED_ON`,`REFRAL_CODE`,`LOGED_IN`,`PLAYER_ID`) values 
(1,'prabhakar1@milaan.com','MILAAN61','Prabhakar','Kumar',1,'f5f34f26-7504-47e3-ad3b-7b6e7aa21bb9-1',NULL,NULL,NULL,'5655443333','da45','9140506909','2021-08-17 19:08:55','QXVnQDIwMjE=','Grocery store',6,1,0,'2021-08-28 17:55:41',NULL,'',0,0,0,13393,1,'28.871464','77.3764416',87,'Store','2058-04-01 19:08:55','2021-08-30 00:00:00','2021-08-28 17:56:25',NULL,0,'11a99194-818c-43b5-acce-9f297c31ef27'),
(2,'md2@milaan.com','MILAAN42','Md','EKRAM',1,'78510954-eba7-498f-aa40-099c17f8bfce-2',NULL,NULL,NULL,'233423343434','233eree','9898767898','2021-08-17 19:48:45','MTIzNDU=','ALIBABA MEDICAL STORE',4,1,0,'2021-08-28 15:48:53',NULL,'',0,0,0,377,1,'25.880303','86.592817',87,'Good','2022-08-12 19:48:45','2021-08-30 00:00:00','2021-08-28 16:40:45',NULL,1,'ggffg'),
(3,'sonu3@milaan.com','MILAAN43','Sonu','Tiwari',1,NULL,NULL,NULL,NULL,'34445554','ff5r555','9140506980','2021-08-17 19:49:29','U2VwQDIwMjE=','Tiwari Medical ',4,1,0,NULL,NULL,'',0,0,0,17,1,'24.9273601','84.1854644',87,'24×7','2021-08-17 19:49:29','2021-08-30 00:00:00','2021-08-17 19:49:29',NULL,0,NULL),
(4,'pp4@milaan.com','MILAAN44','Pp','Kkk',1,NULL,NULL,NULL,NULL,NULL,NULL,'1234566543','2021-08-17 19:54:27','cXE=','Cgb',4,1,0,NULL,NULL,'',0,0,0,21,1,NULL,NULL,85,'Dgg','2021-08-17 19:54:27','2021-08-30 00:00:00','2021-08-17 19:54:27',NULL,0,NULL),
(5,'prince5@milaan.com','MILAAN95','Prince','Kumar ',1,NULL,NULL,NULL,NULL,'12455','Wwjsh','7739031244','2021-08-17 19:57:44','UHJpbmNlQDEyMw==','Prince  electronic ',9,1,0,NULL,NULL,'Sshd344',0,0,0,21,1,'25.59180872','85.1558776',87,'Good shop','2021-08-17 19:57:44','2021-08-30 00:00:01','2021-08-17 19:57:44',NULL,0,NULL),
(6,'anil6@milaan.com','MILAAN46','Anil','Kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'9430213739','2021-08-17 20:30:59','YW5pbEAxMjM=','Gupta Medical',4,1,0,NULL,NULL,'',0,0,0,21,1,'25.59180872','85.1558776',87,'Good','2021-08-17 20:30:59','2021-08-30 00:00:01','2021-08-17 20:30:59',NULL,0,NULL),
(7,'anil7@milaan.com','MILAAN47','Anil','Kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'8409502861','2021-08-17 20:41:53','YW5pbEAxMjM0','Gupta Medical Store',4,1,0,NULL,NULL,'',0,0,0,21,1,'25.59180872','85.1558776',87,'Good','2021-08-17 20:41:53','2021-08-30 00:00:01','2021-08-17 20:41:53',NULL,0,NULL),
(8,'hari8@milaan.com','MILAAN98','Hari','Om',1,'3bddf44c-9827-48ba-a125-8c82a78a6903-8',NULL,NULL,NULL,'123456789963','57wgeu','9155316666','2021-08-20 17:24:28','VmlzaGFsQDE4','Hari Om Electronics',9,1,0,'2021-08-29 09:19:09',NULL,'',1680,1680,0,1475,1,'24.7809382','84.9838522',87,'A Quality of Excellence ','2025-08-13 17:24:28','2021-08-30 00:00:01','2021-08-28 17:57:50',NULL,1,'2ce2d7fb-4657-4870-b437-ed2080332bae'),
(9,'prince9@milaan.com','MILAAN49','Prince','Kumar',1,'fc492a00-1139-4ac4-92c3-ce620e456676-9',NULL,NULL,NULL,'567788766545','Gsshsh33u4','2233445566','2021-08-20 18:33:50','UHJpbmNlMTIz','Prince medical services ',4,1,0,'2021-08-20 21:38:42',NULL,'Shsu3383yh',0,0,0,21,1,'25.59180872','85.1558776',87,'Good shop ','2021-08-20 18:33:50','2021-08-30 00:00:01','2021-08-20 18:33:50',NULL,0,'96913216-2bab-4b76-aa75-11ba5cb2c984'),
(10,'vishal10@milaan.com','MILAAN910','Vishal','Kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'9155316699','2021-08-20 20:13:44','VmlzaGFsQDE4','Vishal Electronics ',9,1,0,NULL,NULL,'',0,0,0,21,1,'25.577526','85.0587009',87,'A quality service provide in Electronics ','2021-08-20 20:13:44','2021-08-30 00:00:01','2021-08-20 20:13:44',NULL,0,NULL),
(11,'akhilesh11@milaan.com','MILAAN711','Akhilesh','Kumar',1,'a1a94619-a21f-4148-807f-8b698546f106-11',NULL,NULL,NULL,NULL,NULL,'9507247655','2021-08-20 20:25:48','bW9uaWRlZXBAMTk4MQ==','निहार फैशन',7,1,0,'2021-08-20 21:36:26',NULL,'',0,0,0,21,1,'24.9236824','84.1865353',87,'It fulfill your whole desire','2021-08-20 20:25:48','2021-08-30 00:00:01','2021-08-20 20:25:48',NULL,1,'fe80c4cc-ced2-48ca-9647-635e33cf415b'),
(12,'aditya12@milaan.com','MILAAN1112','Aditya','Tiwari',1,'2e9e5f97-bb6e-40f9-bf7f-9d1721a656e0-12',NULL,NULL,NULL,NULL,NULL,'9140506965','2021-08-20 20:27:09','QXVnQDIwMjE=','Aditya General Store',11,1,0,'2021-08-27 23:24:25',NULL,'',0,0,0,21,1,'28.8714656','77.3764464',87,'Best quality','2021-08-20 20:27:09','2021-08-30 00:00:01','2021-08-20 20:27:09',NULL,1,'a0e85d66-7ff4-495f-a28c-27fc7ab91d65'),
(13,'sanat13@milaan.com','MILAAN413','Sanat','kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'7677256089','2021-08-21 10:46:17','cXE=','sanat test shop',4,1,0,NULL,NULL,'',0,0,0,21,1,'28.8714656','77.3764464',87,'test shop','2021-08-21 10:46:17','2021-08-30 00:00:01','2021-08-21 10:46:17',NULL,0,NULL),
(14,'sanat14@milaan.com','MILAAN414','Sanat','Kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'7677256088','2021-08-21 17:38:45','cXE=','Sanat shop',4,0,0,NULL,NULL,'',0,0,0,0,1,'12.840641','80.1534283',87,'Gdhdh','2021-08-21 17:38:45','2021-08-21 17:38:45','2021-08-21 17:38:45',NULL,0,NULL),
(15,'sanat15@milaan.com','MILAAN515','Sanat','kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'7766554433','2021-08-26 00:21:44','cXE=','sanat test shop2',5,0,0,NULL,NULL,'',0,0,0,0,101,'28.630712751027318','77.27757453918457',87,'sasasa','2021-08-26 00:21:44','2021-08-26 00:21:44','2021-08-26 00:21:44',NULL,0,NULL),
(16,'cs16@milaan.com','MILAAN516','Cs','casc',1,NULL,NULL,NULL,NULL,NULL,NULL,'1231234566','2021-08-26 00:32:29','cXE=','sacsacsa',5,0,0,NULL,NULL,'',0,0,0,0,101,'25.578159199999998','85.0551689',86,'sacasc','2021-08-26 00:32:29','2021-08-26 00:32:29','2021-08-26 00:32:29',NULL,0,NULL),
(17,'c17@milaan.com','MILAAN7217','C','dcsd',1,NULL,NULL,NULL,NULL,NULL,NULL,'122111234','2021-08-26 00:58:03','cXE=','dsddddd',72,0,0,NULL,NULL,'',0,0,0,0,1,'25.59180872','85.1558776',86,'  c v  v ','2021-08-26 00:58:03','2021-08-26 00:58:03','2021-08-26 00:58:03',NULL,0,NULL),
(18,'sanat18@milaan.com','MILAAN818','Sanat','kumar',1,NULL,NULL,NULL,NULL,NULL,NULL,'9999000009','2021-08-26 01:26:18','cXE=','sanat shoptest',8,0,0,NULL,NULL,'',0,0,0,0,101,'25.5885312','85.0558976',87,'aa','2021-08-26 01:26:18','2021-08-26 01:26:18','2021-08-26 01:26:18',NULL,0,NULL),
(19,'ss19@milaan.com','MILAAN1219','Ss','kk',1,NULL,NULL,NULL,NULL,NULL,NULL,'12343235676','2021-08-26 01:28:41','cXE=','aa',12,0,0,NULL,NULL,'',0,0,0,0,101,'25.5885312','85.0558976',87,'aa','2021-08-26 01:28:41','2021-08-26 01:28:41','2021-08-26 01:28:41',NULL,0,NULL),
(20,'ahsan20@milaan.com','MILAAN620','Ahsan','EKRAM',1,'0e4c5d1b-a061-4bc7-9f53-bd5a26e14a2a-20',NULL,NULL,NULL,NULL,NULL,'8987654567','2021-08-26 16:33:59','MTIzNDU=','KIRAN GENERAL STORE',6,1,0,'2021-08-28 14:36:10',NULL,'',0,0,0,26,101,'25.5885312','85.0558976',87,'GOOD','2021-08-26 16:33:59','2021-08-30 00:00:01','2021-08-26 16:33:59',NULL,1,'ggffg'),
(21,'suraj21@milaan.com','MILAAN921','Suraj','KUMAR',1,'c76f551a-b781-451d-af25-e3c7afa36249-21',NULL,NULL,NULL,NULL,NULL,'8544028658','2021-08-26 16:34:33','TWlsYWFubmV3c2hvcA==','PRINCE ELECTRONICS',9,1,0,'2021-08-26 16:51:14',NULL,'',0,0,0,26,101,'25.5973','85.0659',87,'GADGETS','2021-08-26 16:34:33','2021-08-30 00:00:01','2021-08-26 16:34:33',NULL,1,'itWasNull#Blocked');

/*Table structure for table `admin_address` */

DROP TABLE IF EXISTS `admin_address`;

CREATE TABLE `admin_address` (
  `ID` int(8) NOT NULL AUTO_INCREMENT,
  `PIN_CODE` int(6) DEFAULT NULL,
  `CITY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STATE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `COUNTRY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LONGITUDE` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `LATITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `LAND_MARK` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(5) NOT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `POST_OFFICE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `POLICE_STATION` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STREET` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `admin_address` */

/*Table structure for table `admin_bill_book` */

DROP TABLE IF EXISTS `admin_bill_book`;

CREATE TABLE `admin_bill_book` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(10) NOT NULL DEFAULT '0',
  `SHOP_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DEBIT` float NOT NULL DEFAULT '0',
  `CREDIT` float NOT NULL DEFAULT '0',
  `LAST_DEBITED_ON` date DEFAULT NULL,
  `LAST_CREDITED_ON` date DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `UPDATED_ON` date DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `admin_bill_book` */

insert  into `admin_bill_book`(`ID`,`ADMIN_ID`,`USER_TYPE`,`SHOP_ID`,`DEBIT`,`CREDIT`,`LAST_DEBITED_ON`,`LAST_CREDITED_ON`,`CREATED_ON`,`UPDATED_ON`,`IS_ACTIVE`,`IS_DELETED`) values 
(1,'8',1,'MILAAN98',307272,308952,'2021-08-27','2021-08-27','2021-08-26','2021-08-27',1,0);

/*Table structure for table `admin_device_id_mapping` */

DROP TABLE IF EXISTS `admin_device_id_mapping`;

CREATE TABLE `admin_device_id_mapping` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` int(10) NOT NULL,
  `DEVICE_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `APNS_TOKEN_ID` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `PLAYER_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `admin_device_id_mapping` */

insert  into `admin_device_id_mapping`(`ID`,`ADMIN_ID`,`DEVICE_ID`,`APNS_TOKEN_ID`,`PLAYER_ID`) values 
(2,8,'itWasNull#Blocked',NULL,'itWasNull#Blocked'),
(5,1,'c8e5697d-985f-4f43-bf0e-6774087e906a',NULL,'c8e5697d-985f-4f43-bf0e-6774087e906a'),
(6,12,'itWasNull#Blocked',NULL,'itWasNull#Blocked'),
(7,11,'9400eac0-bdb4-4951-a7fe-e85610b31a3e',NULL,'9400eac0-bdb4-4951-a7fe-e85610b31a3e'),
(10,11,'6bac1368692b7da1',NULL,'fe80c4cc-ced2-48ca-9647-635e33cf415b'),
(11,12,'8c70c549f96d6a95',NULL,'1265863e-9f12-4876-9ebb-c9efd0ba829c'),
(12,12,'49d8b03e-2b13-49a7-b6d8-7458533eda53',NULL,'49d8b03e-2b13-49a7-b6d8-7458533eda53'),
(13,12,'a0e85d66-7ff4-495f-a28c-27fc7ab91d65',NULL,'a0e85d66-7ff4-495f-a28c-27fc7ab91d65'),
(14,1,'itWasNull#Blocked',NULL,'itWasNull#Blocked'),
(16,8,'9510a7c6-638d-4975-897a-7e3357345013',NULL,'9510a7c6-638d-4975-897a-7e3357345013'),
(17,8,'9f448efe-673e-43f3-b73e-3f9261e517c3',NULL,'9f448efe-673e-43f3-b73e-3f9261e517c3'),
(21,8,'665dbc07-110a-42a1-a954-7ad813b839b3',NULL,'665dbc07-110a-42a1-a954-7ad813b839b3'),
(25,8,'c8e5697d-985f-4f43-bf0e-6774087e906a',NULL,'c8e5697d-985f-4f43-bf0e-6774087e906a'),
(31,1,'9510a7c6-638d-4975-897a-7e3357345013',NULL,'9510a7c6-638d-4975-897a-7e3357345013'),
(32,2,'5a0e16b6-0c94-45f6-a9a2-563345380ca8',NULL,'5a0e16b6-0c94-45f6-a9a2-563345380ca8'),
(33,8,'f5d445ddebb7ac88',NULL,'e7d25b61-c67b-4e83-84cc-c9771f192755'),
(35,8,'4289ad95-8081-4fed-8a35-87dc4e8b5812',NULL,'4289ad95-8081-4fed-8a35-87dc4e8b5812'),
(39,1,'df5389fabc68b273',NULL,'0bfcc1b5-8030-44d4-a725-425f5d086f7a'),
(41,12,'729cade07cbb4526',NULL,'a012308d-76ce-49c1-a73a-b4c417ec05b1'),
(44,8,'f633588e81819cf4',NULL,'0ed20897-7df5-48d8-8cf9-22e2cc6aaa25'),
(45,8,'324a7f2a-ed5e-4bc6-8b2e-554bf2e720d9',NULL,'324a7f2a-ed5e-4bc6-8b2e-554bf2e720d9'),
(46,20,'itWasNull#Blocked',NULL,'itWasNull#Blocked'),
(47,21,'itWasNull#Blocked',NULL,'itWasNull#Blocked'),
(48,8,'8fbff3caa2188338',NULL,'a313e177-9865-47b9-acf6-745eedb98106'),
(49,8,'8fbff3caa2188338',NULL,'a313e177-9865-47b9-acf6-745eedb98106'),
(50,8,'3c16a39d-aec5-4e4a-a143-2fe73d454269',NULL,'3c16a39d-aec5-4e4a-a143-2fe73d454269'),
(52,8,'847e32fce2fed06b',NULL,'45655460-7e26-460a-a84a-720cba923c8b'),
(54,8,'ddeca437bfb1b1ed',NULL,'3fdaa7e7-a296-4ce0-9d63-c7bdccec3e6c'),
(57,12,'44dfb0fc94f2f8ce',NULL,'a34de687-f436-42ff-a31f-770961a5080a'),
(59,8,'dbdfffb502ecbaf7',NULL,'11a99194-818c-43b5-acce-9f297c31ef27'),
(60,8,'2ce2d7fb-4657-4870-b437-ed2080332bae',NULL,'2ce2d7fb-4657-4870-b437-ed2080332bae');

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `article_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Title` varchar(200) CHARACTER SET latin1 NOT NULL,
  `Teaser` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `body_content` text CHARACTER SET latin1,
  `MO_ID` int(11) DEFAULT NULL,
  `Article_Type` int(11) DEFAULT NULL,
  `Publish_Status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `article` */

insert  into `article`(`article_id`,`Title`,`Teaser`,`body_content`,`MO_ID`,`Article_Type`,`Publish_Status`) values 
(1,'Full Name','name ask','What is your full name?',0,37,0),
(2,'Age','age ask','What is your age?',0,37,0),
(3,'Full Address','address','What is your full address?',0,37,0),
(4,'This is React Native Intro Project','Impulse Season 2: From the director of The Bourne Identity and Edge of Tomorrow','addd',31,35,0),
(5,'This is Native script Intro Project','Impulse Season 2: From the director of The Bourne Identity and Edge of Tomorrow',NULL,32,35,0),
(6,'My name is Avinash, work on UI Technologies for web and mobile','What is Spring Boot CLI?Spring Boot CLI(Command Line Interface) is a Spring Boot software to run and test Spring Boot applications from command prompt. When we run Spring Boot applications using CLI',NULL,35,35,0),
(7,'My name is Avinash, work on UI Technologies for web and mobile','What is Spring Boot CLI?Spring Boot CLI(Command Line Interface) is a Spring Boot software to run and test Spring Boot applications from command prompt. When we run Spring Boot it internally',NULL,40,35,0);

/*Table structure for table `articlefile` */

DROP TABLE IF EXISTS `articlefile`;

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

/*Data for the table `articlefile` */

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_ID` varchar(22) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `ATTENDANCE` int(2) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `WORK_ID` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `attendance` */

/*Table structure for table `bank` */

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_NUM` int(30) NOT NULL,
  `BANK_NAME` varchar(100) NOT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ADDRESS` varchar(200) NOT NULL,
  `IFSC` varchar(100) NOT NULL,
  `SHOP_ID` varchar(50) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `bank` */

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AVATAR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `TITLE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY` int(11) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `brand` */

insert  into `brand`(`ID`,`AVATAR`,`TITLE`,`NAME`,`SHOP_ID`,`CATEGORY`,`IS_ACTIVE`,`IS_DELETED`) values 
(1,NULL,'RAJDHANI','RAJDHANI','MILAAN61',1,1,0),
(2,NULL,'INALSA','INALSA','MILAAN61',2,1,0),
(3,NULL,'ZINGURE','ZINGURE','MILAAN42',3,1,0),
(5,NULL,'MILTON','MILTON','MILAAN61',5,1,0),
(6,NULL,'AJANTA','AJANTA','MILAAN61',6,1,0),
(7,NULL,'SUGART','SUGART','MILAAN61',1,1,0),
(8,NULL,'BASMATI','BASMATI','MILAAN61',8,1,0),
(9,NULL,'BASMATI','BASMATI','MILAAN61',7,1,0),
(10,NULL,'SONY','SONY','MILAAN98',9,1,0),
(11,NULL,'SAMSUNG','SAMSUNG','MILAAN98',9,1,0),
(12,NULL,'PHILIPS','PHILIPS','MILAAN98',10,1,0),
(13,NULL,'LG','LG','MILAAN98',11,1,0),
(14,NULL,'MAGALSUTRA','MAGALSUTRA','MILAAN1112',12,1,0),
(15,NULL,'ODOMOS','ODOMOS','MILAAN1112',13,1,0),
(16,NULL,'TANISQ','TANISQ','MILAAN711',14,1,0),
(17,NULL,'MEDICINE_','MEDICINE ','MILAAN49',15,1,0),
(18,NULL,'REYNOLD_','REYNOLD ','MILAAN711',16,1,0),
(19,NULL,'MEDICINE_1','MEDICINE 1','MILAAN49',15,1,0),
(20,NULL,'LG','LG','MILAAN98',10,1,0),
(21,NULL,'BAJAJ','BAJAJ','MILAAN620',19,1,0),
(22,NULL,'RELIANCE','RELIANCE','MILAAN921',20,1,0),
(23,NULL,'ELECTRONIC_','ELECTRONIC ','MILAAN98',21,1,0),
(24,NULL,'ELECTRONIC_1','ELECTRONIC 1','MILAAN98',21,1,0),
(25,NULL,'ANCHOR','ANCHOR','MILAAN98',22,1,0),
(26,NULL,'AATA_5KG','AATA 5KG','MILAAN61',23,1,0),
(27,NULL,'TATA','TATA','MILAAN61',23,1,0),
(28,NULL,'LOTUS_','LOTUS ','MILAAN98',24,1,0),
(29,NULL,'REEBOK','REEBOK','MILAAN1112',25,1,0);

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `CREATED_ON` datetime DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `GST_AMOUNT` float NOT NULL DEFAULT '0',
  `TOTAL_AMOUNT` float NOT NULL DEFAULT '0',
  `TRANSACTION_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PAID` float NOT NULL DEFAULT '0',
  `DUES` float NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `USER_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `ORDER_STATUS` int(1) NOT NULL DEFAULT '0',
  `TRANSACTION_TYPE` int(5) NOT NULL DEFAULT '0',
  `ORDER_DATE` datetime DEFAULT NULL,
  `DISCOUNT` float NOT NULL DEFAULT '0',
  `DELIVERY_CHARGE` float NOT NULL DEFAULT '0',
  `ORDER_TYPE` int(10) NOT NULL DEFAULT '0',
  `USER_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADDRESS_ID` int(10) NOT NULL DEFAULT '1',
  `SLOT_DATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `SHIPPING_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DELIVERY_TYPE` int(1) NOT NULL DEFAULT '0',
  `SHIPPING_DATE` datetime DEFAULT NULL,
  `DELIVERY_DATE` datetime DEFAULT NULL,
  `SHIPPING_NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `D_BOY_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `D_BOY_NUMBER` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DELIVERY_TIME` datetime DEFAULT NULL,
  `COURIER_NAME` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `REVIEW` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `IS_OFFLINE` tinyint(1) NOT NULL DEFAULT '0',
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_ID` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `PAYABLE_AMOUNT` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `cart` */

insert  into `cart`(`ID`,`CREATED_ON`,`SHOP_ID`,`GST_AMOUNT`,`TOTAL_AMOUNT`,`TRANSACTION_ID`,`PAID`,`DUES`,`IS_ACTIVE`,`IS_DELETED`,`USER_ID`,`ORDER_STATUS`,`TRANSACTION_TYPE`,`ORDER_DATE`,`DISCOUNT`,`DELIVERY_CHARGE`,`ORDER_TYPE`,`USER_NAME`,`MOBILE_NUMBER`,`ADDRESS_ID`,`SLOT_DATE`,`DESCRIPTION`,`SHIPPING_ID`,`DELIVERY_TYPE`,`SHIPPING_DATE`,`DELIVERY_DATE`,`SHIPPING_NAME`,`D_BOY_NAME`,`D_BOY_NUMBER`,`DELIVERY_TIME`,`COURIER_NAME`,`REVIEW`,`IS_OFFLINE`,`SHOP_NAME`,`ADMIN_ID`,`PAYABLE_AMOUNT`) values 
(1,'2021-08-26 16:09:20','MILAAN61',0,0,NULL,0,0,1,0,'14',70,0,NULL,0,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Grocery store','1',0),
(2,'2021-08-26 16:10:26','MILAAN98',44712,293112,'1',293112,-913478,0,0,'14',83,14,'2021-08-26 16:21:59',27600,23,73,'Vajsv','9155316625',10,'2021-08-28 00:00:00',NULL,NULL,78,'2021-08-26 16:24:30','2021-08-31 03:25:00',NULL,'Rahul','98670',NULL,NULL,'Vishal Jake NJ is sjnsjsinjsn in snmsnsjsnmskkdnjsjkdmdjdnks. Djsnjsnnsnsnnsjs sbnsnsnsjndndndndnndnd dbsnn',0,'Hari Om Electronics','8',293112),
(3,'2021-08-26 19:00:35','MILAAN98',85542,595442,NULL,0,184703,1,0,'14',70,0,NULL,35300,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Hari Om Electronics','8',595442),
(4,'2021-08-26 22:59:51','MILAAN98',21069,138119,'14',138119,0,0,0,'4',84,13,NULL,9000,0,73,'VISHAL','1111111111',4,'2021-08-28 00:00:00',NULL,NULL,78,'2021-08-27 01:28:38','2021-08-29 00:00:00',NULL,'Hsghs','9155316625',NULL,NULL,'Yes',0,'Hari Om Electronics','8',138119),
(5,'2021-08-27 01:26:31','MILAAN1112',144,1944,NULL,0,1944,1,0,'4',70,0,NULL,0,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Aditya General Store','12',1944),
(6,'2021-08-27 12:22:37','MILAAN98',1440,15840,'23',15840,0,0,0,'4',84,14,'2021-08-27 12:24:11',1600,20,73,'VISHAL','1111111111',4,'2021-08-31 00:00:00',NULL,NULL,78,'2021-08-27 12:27:13','2021-08-31 00:00:00',NULL,'Victoria ','9155316625',NULL,NULL,'Very good services',0,'Hari Om Electronics','8',15840),
(7,'2021-08-28 12:28:19','MILAAN98',34063.4,236978,NULL,0,236978,1,0,'7',70,0,NULL,12200,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Hari Om Electronics','8',236978),
(8,'2021-08-28 14:09:41','MILAAN98',10.4,75.4,NULL,0,75.4,1,0,'6',70,0,NULL,0,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Grocery store','8',76);

/*Table structure for table `casual_leave` */

DROP TABLE IF EXISTS `casual_leave`;

CREATE TABLE `casual_leave` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `LEAVE_FROM` date DEFAULT NULL,
  `LEAVE_TO` date DEFAULT NULL,
  `REASION` varchar(400) COLLATE utf8_bin DEFAULT NULL,
  `EMPLOYEE_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LEAVE_TYPE` int(30) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `CREATED_ON` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `casual_leave` */

insert  into `casual_leave`(`ID`,`LEAVE_FROM`,`LEAVE_TO`,`REASION`,`EMPLOYEE_ID`,`SHOP_ID`,`LEAVE_TYPE`,`IS_ACTIVE`,`IS_DELETED`,`CREATED_ON`) values 
(1,'2021-03-18','2021-03-23','meeting','shu123','Avi23',11,0,0,NULL),
(2,'2021-03-26','2021-03-27','meeting','shu123','Avi23',11,1,0,NULL),
(6,NULL,NULL,NULL,'shu1232','shu12355',NULL,0,0,NULL),
(7,'2021-04-05','2021-04-05','meeting','shu111','shop',11,0,0,NULL),
(8,'2021-04-05','2021-04-05','meeting','shu222','shop',11,0,0,NULL),
(9,'2021-04-05','2021-04-05','meeting','shu444','electronic',11,0,0,NULL),
(10,'2021-04-01','2021-04-01','meeting','shu555','shop333',NULL,0,0,NULL),
(11,'2021-04-01','2021-04-01','meeting','shu666','shop333',23,0,0,NULL),
(12,'2021-05-13','2021-05-13','meeting','1','Avi23',11,0,0,'2021-05-13'),
(13,'2021-05-13','2021-05-13','meeting','2','Avi23',11,0,0,'2021-05-13'),
(14,'2021-05-13','2021-05-13','meeting','4','Avi23',11,0,0,'2021-05-13'),
(15,'0022-09-11','0027-09-11','meeting','6','Avi23',11,1,0,'2021-05-13');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `AVATAR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `category` */

insert  into `category`(`ID`,`TITLE`,`NAME`,`AVATAR`,`SHOP_ID`,`IS_ACTIVE`,`IS_DELETED`) values 
(1,'SUGAR','SUGAR',NULL,'MILAAN61',1,0),
(2,'ELECTRONICS','ELECTRONICS',NULL,'MILAAN61',1,0),
(3,'SURP','SURP',NULL,'MILAAN42',1,0),
(5,'SUMMER','SUMMER',NULL,'MILAAN61',1,0),
(6,'COPY','COPY',NULL,'MILAAN61',1,0),
(7,'TEST_CAT','TEST CAT',NULL,'MILAAN61',1,0),
(8,'RICE','RICE',NULL,'MILAAN61',1,0),
(9,'AC','AC',NULL,'MILAAN98',1,0),
(10,'TV','TV',NULL,'MILAAN98',1,0),
(11,'REFRIGERATOR_','REFRIGERATOR ',NULL,'MILAAN98',1,0),
(12,'JEWELLERY','JEWELLERY',NULL,'MILAAN1112',1,0),
(13,'GENERAL','GENERAL',NULL,'MILAAN1112',1,0),
(14,'JEWELLERY_','JEWELLERY ',NULL,'MILAAN711',1,0),
(15,'MEDICINE_','MEDICINE ',NULL,'MILAAN49',1,0),
(16,'PEN','PEN',NULL,'MILAAN711',1,0),
(17,'CATTEST2','CATTEST2',NULL,'MILAAN61',1,0),
(18,'CATTES3','CATTES3',NULL,'MILAAN61',1,0),
(19,'OIL','OIL',NULL,'MILAAN620',1,0),
(20,'CLOTH','CLOTH',NULL,'MILAAN921',1,0),
(21,'ELECTRONIC_','ELECTRONIC ',NULL,'MILAAN98',1,0),
(22,'SWITCH','SWITCH',NULL,'MILAAN98',1,0),
(23,'AATA','AATA',NULL,'MILAAN61',1,0),
(24,'HANDBAG','HANDBAG',NULL,'MILAAN98',1,0),
(25,'SHOES','SHOES',NULL,'MILAAN1112',1,0);

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `COMMENTS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TOPICS_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `CREATED_ON` date NOT NULL,
  `COMMENTS_TEXT` text COLLATE utf8_bin NOT NULL,
  `COMMENTS_TYPE` int(11) NOT NULL,
  PRIMARY KEY (`COMMENTS_ID`),
  KEY `TOPICS_REF` (`TOPICS_ID`),
  CONSTRAINT `TOPICS_REF` FOREIGN KEY (`TOPICS_ID`) REFERENCES `topics` (`TOPICS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `comments` */

/*Table structure for table `consultation_workflow` */

DROP TABLE IF EXISTS `consultation_workflow`;

CREATE TABLE `consultation_workflow` (
  `CONSULTATION_WORKFLOW_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `CONSULTANT_ID` int(11) NOT NULL,
  `ARTICLE_ID` int(11) DEFAULT NULL,
  `PRICE` int(11) DEFAULT NULL,
  `STATUS` tinyint(1) DEFAULT NULL,
  `REASON_FOR_REJECT` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `FINISHEDATE` datetime DEFAULT NULL,
  `RATING` int(11) DEFAULT NULL,
  `FEEDBACK` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`CONSULTATION_WORKFLOW_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `consultation_workflow` */

insert  into `consultation_workflow`(`CONSULTATION_WORKFLOW_ID`,`USER_ID`,`CONSULTANT_ID`,`ARTICLE_ID`,`PRICE`,`STATUS`,`REASON_FOR_REJECT`,`CREATEDATE`,`FINISHEDATE`,`RATING`,`FEEDBACK`) values 
(1,2,3,NULL,6,1,NULL,NULL,NULL,0,NULL),
(3,2,7,NULL,5,1,NULL,NULL,NULL,0,NULL),
(4,2,1,NULL,5,1,NULL,NULL,NULL,0,NULL),
(5,2,4,NULL,12,1,NULL,NULL,NULL,0,NULL),
(6,22,4,NULL,12,1,NULL,NULL,NULL,0,NULL);

/*Table structure for table `eaddress` */

DROP TABLE IF EXISTS `eaddress`;

CREATE TABLE `eaddress` (
  `ID` int(8) NOT NULL AUTO_INCREMENT,
  `PIN_CODE` int(6) DEFAULT NULL,
  `CITY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STATE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `COUNTRY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LONGITUDE` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `LATITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(3) NOT NULL,
  `EMPLOYEE_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `DISTRICT` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `POST_OFFICE` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `POLICE_STATION` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `LAND_MARK` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STREET` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `eaddress` */

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `F_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `L_NAME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `FATHER` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(10) COLLATE utf8_bin NOT NULL,
  `ADHAR_NUM` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ADHAR_AVATAR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PAN_NUM` varchar(13) COLLATE utf8_bin DEFAULT NULL,
  `PAN_AVATAR` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SALARY` int(11) DEFAULT NULL,
  `SALARY_TYPE` int(20) DEFAULT NULL,
  `JOINING_DATE` date DEFAULT NULL,
  `WORKING_DAYS` int(2) DEFAULT NULL,
  `DESIGNATION` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `EMPLOYEE_ID` varchar(10) COLLATE utf8_bin NOT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `STREET` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `LEAVING_DATE` date DEFAULT NULL,
  `VOTER_ID` int(11) DEFAULT NULL,
  `GENDER` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `employee` */

/*Table structure for table `hashtag` */

DROP TABLE IF EXISTS `hashtag`;

CREATE TABLE `hashtag` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATAGORY_ID` int(2) NOT NULL,
  `TAG_TEXT` varchar(200) COLLATE utf8_bin NOT NULL,
  `CREATED_ON` date NOT NULL,
  `USER_ID` int(5) NOT NULL,
  `DESCRIPTION` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `hashtag` */

insert  into `hashtag`(`ID`,`CATAGORY_ID`,`TAG_TEXT`,`CREATED_ON`,`USER_ID`,`DESCRIPTION`,`IS_DELETED`) values 
(1,13,'CMS','2020-10-20',1,'CMS',0),
(2,13,'ECM','2020-10-20',1,'CMS',0),
(3,13,'WCM','2020-10-19',1,'CMS',0),
(4,13,'WCM','2020-10-19',1,'AEM CONTENT CMS HASHTAG',0);

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `ID` int(30) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(30) DEFAULT '0',
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `AVATAR_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `image` */

insert  into `image`(`ID`,`PRODUCT_ID`,`SHOP_ID`,`AVATAR_NAME`,`IS_ACTIVE`,`IS_DELETED`,`CREATED_ON`) values 
(3,6,'MILAAN61','6_1_MILAAN61_product.png',1,0,'2021-08-18'),
(4,6,'MILAAN61','6_2_MILAAN61_product.png',1,0,'2021-08-18'),
(5,6,'MILAAN61','6_3_MILAAN61_product.png',1,0,'2021-08-18'),
(6,7,'MILAAN61','7_1_MILAAN61_product.png',1,0,'2021-08-18'),
(7,6,'MILAAN61','6_4_MILAAN61_product.png',1,0,'2021-08-18'),
(8,6,'MILAAN61','6_5_MILAAN61_product.png',1,0,'2021-08-18'),
(9,6,'MILAAN61','6_6_MILAAN61_product.png',1,0,'2021-08-18'),
(10,8,'MILAAN61','8_1_MILAAN61_product.png',1,0,'2021-08-18'),
(11,13,'MILAAN61','13_1_MILAAN61_product.png',1,0,'2021-08-18'),
(12,14,'MILAAN61','14_1_MILAAN61_product.png',1,0,'2021-08-18'),
(13,14,'MILAAN61','14_2_MILAAN61_product.png',1,0,'2021-08-18'),
(14,15,'MILAAN61','15_1_MILAAN61_product.png',1,0,'2021-08-19'),
(15,16,'MILAAN61','16_1_MILAAN61_product.png',1,0,'2021-08-19'),
(16,17,'MILAAN61','17_1_MILAAN61_product.png',1,0,'2021-08-19'),
(17,18,'MILAAN61','18_1_MILAAN61_product.png',1,0,'2021-08-19'),
(18,19,'MILAAN61','19_1_MILAAN61_product.png',1,0,'2021-08-19'),
(19,20,'MILAAN61','20_1_MILAAN61_product.png',1,0,'2021-08-19'),
(20,20,'MILAAN61','20_2_MILAAN61_product.png',1,0,'2021-08-19'),
(21,21,'MILAAN98','21_1_MILAAN98_product.png',1,0,'2021-08-20'),
(22,21,'MILAAN98','21_2_MILAAN98_product.png',1,0,'2021-08-20'),
(23,21,'MILAAN98','21_3_MILAAN98_product.png',1,0,'2021-08-20'),
(24,22,'MILAAN98','22_1_MILAAN98_product.png',1,0,'2021-08-20'),
(25,22,'MILAAN98','22_2_MILAAN98_product.png',1,0,'2021-08-20'),
(26,23,'MILAAN98','23_1_MILAAN98_product.png',1,0,'2021-08-20'),
(27,23,'MILAAN98','23_2_MILAAN98_product.png',1,0,'2021-08-20'),
(28,23,'MILAAN98','23_3_MILAAN98_product.png',1,0,'2021-08-20'),
(29,23,'MILAAN98','23_4_MILAAN98_product.png',1,0,'2021-08-20'),
(30,24,'MILAAN1112','24_1_MILAAN1112_product.png',1,0,'2021-08-20'),
(31,24,'MILAAN1112','24_2_MILAAN1112_product.png',1,0,'2021-08-20'),
(32,24,'MILAAN1112','24_3_MILAAN1112_product.png',1,0,'2021-08-20'),
(33,25,'MILAAN1112','25_1_MILAAN1112_product.png',1,0,'2021-08-20'),
(34,25,'MILAAN1112','25_2_MILAAN1112_product.png',1,0,'2021-08-20'),
(35,28,'MILAAN49','28_1_MILAAN49_product.png',1,0,'2021-08-20'),
(36,29,'MILAAN711','29_1_MILAAN711_product.png',1,0,'2021-08-20'),
(37,30,'MILAAN49','30_1_MILAAN49_product.png',1,0,'2021-08-20'),
(38,32,'MILAAN98','32_1_MILAAN98_product.png',1,0,'2021-08-21'),
(39,33,'MILAAN61','33_1_MILAAN61_product.png',1,0,'2021-08-23'),
(40,34,'MILAAN61','34_1_MILAAN61_product.png',1,0,'2021-08-24'),
(41,35,'MILAAN61','35_1_MILAAN61_product.png',1,0,'2021-08-24'),
(42,36,'MILAAN98','36_1_MILAAN98_product.png',1,0,'2021-08-24'),
(43,37,'MILAAN98','37_1_MILAAN98_product.png',1,0,'2021-08-25'),
(44,38,'MILAAN61','38_1_MILAAN61_product.png',1,0,'2021-08-26'),
(45,39,'MILAAN61','39_1_MILAAN61_product.png',1,0,'2021-08-26'),
(46,40,'MILAAN61','40_1_MILAAN61_product.png',1,0,'2021-08-26'),
(47,41,'MILAAN61','41_1_MILAAN61_product.png',1,0,'2021-08-26'),
(48,42,'MILAAN98','42_1_MILAAN98_product.png',1,0,'2021-08-26'),
(49,43,'MILAAN61','43_1_MILAAN61_product.png',1,0,'2021-08-26'),
(50,44,'MILAAN98','44_1_MILAAN98_product.png',1,0,'2021-08-27'),
(51,45,'MILAAN1112','45_1_MILAAN1112_product.png',1,0,'2021-08-27');

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

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

/*Data for the table `job` */

/*Table structure for table `job_application` */

DROP TABLE IF EXISTS `job_application`;

CREATE TABLE `job_application` (
  `ID` int(11) NOT NULL,
  `JOB_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `APPL_DATE` int(11) NOT NULL,
  `PROPOSE_PRICE` int(11) NOT NULL,
  `ARTICLE` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `job_application` */

/*Table structure for table `lookup` */

DROP TABLE IF EXISTS `lookup`;

CREATE TABLE `lookup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOOKUP_NAME` varchar(100) COLLATE utf8_bin NOT NULL,
  `LOOKUP_LABEL` varchar(500) COLLATE utf8_bin NOT NULL,
  `LOOKUP_TYPE_ID` int(10) NOT NULL,
  `SHOP_ID` varchar(60) COLLATE utf8_bin NOT NULL DEFAULT 'MILAAN',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `lookup` */

insert  into `lookup`(`ID`,`LOOKUP_NAME`,`LOOKUP_LABEL`,`LOOKUP_TYPE_ID`,`SHOP_ID`,`IS_DELETED`,`IS_ACTIVE`) values 
(1,'ADMIN','ADMIN',1,'MILAAN',0,1),
(2,'CUSTOMER','CUSTOMER',1,'MILAAN',0,1),
(3,'EMPLOYEE','EMPLOYEE',1,'MILAAN',0,1),
(4,'MEDICAL','MEDICAL',2,'MILAAN',0,1),
(5,'FURNITURE','FURNITURE',2,'MILAAN',0,1),
(6,'GROCERY','GROCERY',2,'MILAAN',0,1),
(7,'JEWELLERY','JEWELLERY',2,'MILAAN',0,1),
(8,'BOOK_SHOP','BOOK SHOP',2,'MILAAN',0,1),
(9,'ELECTRONIC','ELECTRONIC',2,'MILAAN',0,1),
(10,'GARMENT_SHOP','GARMENT SHOP',2,'MILAAN',0,1),
(11,'COSMETIC','COSMETIC',2,'MILAAN',0,1),
(12,'HARDWARE','HARDWARE',2,'MILAAN',0,1),
(13,'CASH','CASH',3,'MILAAN',0,1),
(14,'ONLINE_PAYMENT','ONLINE PAYMENT',3,'MILAAN',0,1),
(15,'PCS','PCS',4,'MILAAN',0,1),
(16,'KG','KG',4,'MILAAN',0,1),
(17,'GM','GM',4,'MILAAN',0,1),
(18,'LITRE','LITRE',4,'MILAAN',0,1),
(19,'METER','METER',4,'MILAAN',0,1),
(20,'ADIDAS','ADIDAS',5,'MILAAN',0,1),
(21,'SONY','SONY',5,'MILAAN',0,1),
(22,'HP','HP',5,'MILAAN',0,1),
(23,'PATANJALI','PATANJALI',5,'MILAAN',0,1),
(24,'TRAVEL/TOURISM','?????',44,'MILAAN',0,1),
(25,'OTHERS','???????',44,'MILAAN',0,1),
(26,'RESTAURANT','RESTAURANT',2,'MILAAN',0,1),
(27,'ABSENT','ABSENT',10,'MILAAN',0,1),
(28,'PRESENT','PRESENT',10,'MILAAN',0,1),
(29,'USER','USER',5,'MILAAN',0,1),
(30,'ERP CONSULTANT','ERP CONSULTANT',33,'MILAAN',0,1),
(31,'CLOUD ARCHITECT','CLOUD ARCHITECT',33,'MILAAN',0,1),
(32,'DATA ANALYST','DATA ANALYST',33,'MILAAN',0,1),
(33,'ALL','ALL',44,'MILAAN',0,1),
(34,'NOTIFICATION','NOTIFICATION',6,'MILAAN',0,1),
(35,'PROFILE','PROFILE',6,'MILAAN',0,1),
(36,'FEEDBACK','FEEDBACK',6,'MILAAN',0,1),
(37,'POST','POST',6,'MILAAN',0,1),
(38,'BLOG','BLOG',6,'MILAAN',0,1),
(39,'WEBPAGE','WEBPAGE',6,'MILAAN',0,1),
(43,'AVAILABLE','AVAILABLE',8,'MILAAN',0,1),
(44,'UNAVAILABLE','UNAVAILABLE',8,'MILAAN',0,1),
(45,'AVAILABLE BUT CAN\'T ANSWER','AVAILABLE BUT CAN\'T ANSWER',8,'MILAAN',0,1),
(46,'ENGLISH','ENGLISH',7,'MILAAN',0,1),
(47,'ARABIC','ARABIC',7,'MILAAN',0,1),
(48,'FRENCH','FRENCH',7,'MILAAN',0,1),
(49,'UNREAD','UNREAD',25,'MILAAN',0,1),
(50,'READ','READ',25,'MILAAN',0,1),
(52,'DELETED','DELETED',25,'MILAAN',0,1),
(53,'FREE','FREE AVAILABILITY ',8,'MILAAN',0,1),
(54,'TEXT','TEXT',25,'MILAAN',0,1),
(55,'AUDIO','AUDIO',25,'MILAAN',0,1),
(56,'VIDEO','VIDEO',25,'MILAAN',0,1),
(57,'QUESTION','QUESTION',25,'MILAAN',0,1),
(58,'IMAGE','IMAGE',25,'MILAAN',0,1),
(59,'QUE_ANS','QUE ANS',25,'MILAAN',0,1),
(60,'MyList','MyList',44,'MILAAN',0,1),
(61,'PLACED','PLACED',9,'MILAAN',0,1),
(62,'PACKED','PACKED',9,'MILAAN',0,1),
(63,'SHIPPED','SHIPPED',9,'MILAAN',0,1),
(64,'DELIVERED','DELIVERED',9,'MILAAN',0,1),
(65,'SUCCESSFUL','SUCCESSFUL',9,'MILAAN',0,1),
(67,'ACCEPTED','ACCEPTED',9,'MILAAN',0,1),
(69,'REJECTED','REJECTED',9,'MILAAN',0,1),
(70,'DEACTIVE','DEACTIVE',9,'MILAAN',0,1),
(71,'BUILDER','BUILDER',2,'MILAAN',0,1),
(72,'SOFTWARE','SOFTWARE',2,'MILAAN',0,1),
(73,'HOME_DELIVERY','HOME_DELIVERY',11,'MILAAN',0,1),
(74,'SELF_PICKUP','SELF_PICKUP',11,'MILAAN',0,1),
(75,'COMPLETED','COMPLETED',12,'MILAAN',0,1),
(76,'FAILD','FAILD',12,'MILAAN',0,1),
(78,'BY_DELIVERY_BOY','BY DELIVERY BOY',13,'MILAAN',0,1),
(79,'SELF_DELIVERY','SELF DELIVERY',13,'MILAAN',0,1),
(80,'COURIER','COURIER',13,'MILAAN',0,1),
(81,'CART_REVIEW','CART REVIEW',14,'MILAAN',0,1),
(82,'PRODUCT_REVIEW','PRODUCT REVIEW',14,'MILAAN',0,1),
(83,'DENIED','DENIED',9,'MILAAN',0,1),
(84,'RECEIVED','RECEIVED',9,'MILAAN',0,1),
(85,'REG_ADDRESS','REG ADDRESS',16,'MILAAN',0,1),
(86,'REG_IMAGE','REG IMAGE',16,'MILAAN',0,1),
(87,'REG_COMPLETED','REG COMPLETED',16,'MILAAN',0,1),
(88,'YEARLY','YEARLY',15,'MILAAN',0,1),
(89,'WALLET_PAYMENT','WALLET PAYMENT',3,'MILAAN',0,1),
(90,'USER_DENIED','USER DENIED',17,'MILAAN',0,1),
(91,'SHOPING','SHOPING',17,'MILAAN',0,1),
(92,'PLAN_PURCHASE','PLAN PURCHASE',17,'MILAAN',0,1),
(93,'MILAAN_10','10',18,'MILAAN',0,1),
(94,'MILAAN_20','20',18,'MILAAN',0,1),
(95,'MILAAN_50','50',18,'MILAAN',0,1),
(96,'WITHDRAW_COMPLETED','WITHDRAW COMPLETED',19,'MILAAN',0,1),
(97,'WITHDRAW_FAILD','WITHDRAW FAILD',19,'MILAAN',0,1),
(98,'WITHDRAW_PENDING','WITHDRAW PENDING',19,'MILAAN',0,1),
(99,'WITHDRAW_REQUEST','WITHDRAW REQUEST',17,'MILAAN',0,1),
(100,'WITHDRAW_DONE','WITHDRAW DONE',17,'MILAAN',0,1),
(101,'MALE','MALE',20,'MILAAN',0,1),
(102,'FEMALE','FEMALE',20,'MILAAN',0,1),
(103,'GRAM','GRAM',4,'MILAAN',0,1),
(104,'ML','ML',4,'MILAAN',0,1),
(106,'MM','MM',4,'MILAAN',0,1),
(107,'FT','FT',4,'MILAAN',0,1),
(109,'SQ_FT','SQ FT',4,'MILAAN',0,1),
(110,'SQ_METER','SQ METER',4,'MILAAN',0,1),
(111,'KM','KM',4,'MILAAN',0,1),
(112,'SET','SET',4,'MILAAN',0,1),
(113,'HOUR','HOUR',4,'MILAAN',0,1),
(114,'DAY','DAY',4,'MILAAN',0,1),
(115,'BUNCH','BUNCH',4,'MILAAN',0,1),
(116,'BUNDLE','BUNDLE',4,'MILAAN',0,1),
(117,'MONTH','MONTH',4,'MILAAN',0,1),
(118,'YEAR','YEAR',4,'MILAAN',0,1),
(119,'SERVICE','SERVICE',4,'MILAAN',0,1),
(120,'WORK','WORK',4,'MILAAN',0,1),
(121,'PACKET','PACKET',4,'MILAAN',0,1),
(122,'BOX','BOX',4,'MILAAN',0,1),
(123,'POUND','POUND',4,'MILAAN',0,1),
(124,'DOZEN','DOZEN',4,'MILAAN',0,1),
(125,'GUNTA','GUNTA',4,'MILAAN',0,1),
(126,'PAIR','PAIR',4,'MILAAN',0,1),
(127,'MINUTE','MINUTE',4,'MILAAN',0,1),
(128,'QUINTAL','QUINTAL',4,'MILAAN',0,1),
(129,'TON','TON',4,'MILAAN',0,1),
(130,'CAPSULE','CAPSULE',4,'MILAAN',0,1),
(131,'TABLET','TABLET',4,'MILAAN',0,1),
(132,'PLATE','PLATE',4,'MILAAN',0,1),
(133,'INCH','INCH',4,'MILAAN',0,1),
(134,'OTHER','OTHER',20,'MILAAN',0,1),
(135,'TRANS_PENDING','TRANS_PENDING',12,'MILAAN',0,1),
(136,'ADMIN_REJECTED','ADMIN REJECTED',17,'MILAAN',0,1),
(137,'CLINIC','CLINIC',2,'MILAAN',0,1),
(138,'PATH_LAB','PATH LAB',2,'MILAAN',0,1),
(139,'SALOON_AND_SPA','SALOON AND SPA',2,'MILAAN',0,1),
(140,'SYRUP','SYRUP',4,'MILAAN',0,1);

/*Table structure for table `lookup_type` */

DROP TABLE IF EXISTS `lookup_type`;

CREATE TABLE `lookup_type` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `LOOKUP_TYPE_NAME` varchar(20) COLLATE utf8_bin NOT NULL,
  `LOOKUP_TYPE_LABEL` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `lookup_type` */

insert  into `lookup_type`(`ID`,`LOOKUP_TYPE_NAME`,`LOOKUP_TYPE_LABEL`) values 
(1,'USER_TYPE','USER_TYPE'),
(2,'SHOP_TYPE','SHOP TYPE'),
(3,'PAYMENT_MODE','PAYMENT MODE'),
(4,'MEASURMENT_TYPE','MEASURMENT TYPE'),
(5,'BRAND','BRAND'),
(6,'AVAILABILITY','AVAILABILITY'),
(7,'NOTIFICATION_STATUS','NOTIFICATION_STATUS'),
(8,'CATEGORY','CATEGORY'),
(9,'ORDER_STATUS','ORDER_STATUS'),
(10,'ATTENDANCE','ATTENDANCE'),
(11,'ORDER_TYPE','ORDER_TYPE'),
(12,'TRANSACTION_STATUS','TRANSACTION_STATUS'),
(13,'DELIVERY_TYPE','DELIVERY_TYPE'),
(14,'REVIEW_TYPE','REVIEW_TYPE'),
(15,'PLAN_TYPE','PLAN_TYPE'),
(16,'REGISTRATION_STATUS','REGISTRATION_STATUS'),
(17,'TRANSACTION_TYPE','TRANSACTION_TYPE'),
(18,'DISCOUNT_TYPE','DISCOUNT_TYPE'),
(19,'WITHDRAW_STATUS','WITHDRAW_STATUS'),
(20,'GENDER','GENDER');

/*Table structure for table `managedobject` */

DROP TABLE IF EXISTS `managedobject`;

CREATE TABLE `managedobject` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(40) COLLATE utf8_bin NOT NULL,
  `object_type` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `managedobject` */

insert  into `managedobject`(`ID`,`guid`,`object_type`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(3,'cf13c823-1620-4ce2-bf09-6543dfdc7c48',1,'2019-09-08',0,NULL,0),
(4,'5ad923b5-911c-42d2-ac2a-eb65bfe1c65f',1,'2019-09-08',10,NULL,0),
(5,'18d17946-40d6-4292-bd6b-80752c652b64',1,'2019-09-08',10,NULL,0),
(6,'352dba05-2520-4d59-90c5-31392e45b064',1,'2019-09-08',10,NULL,0),
(7,'3c1d7cc1-aa58-42fe-9be2-16c8e5838057',1,'2019-09-12',10,NULL,0),
(8,'5337a632-264c-46ea-b691-362f7c05f03a',1,'2019-09-12',10,NULL,0),
(9,'4b710113-9834-4ffe-ab7a-2459baacab6a',1,'2019-09-12',10,NULL,0),
(10,'d8457444-302c-4698-97ea-ebabf9483317',1,'2019-10-26',10,NULL,0),
(12,'eb228160-6ac2-4ec6-97af-73f42a7cac63',1,'2019-10-27',10,NULL,0),
(13,'befbeea8-00ca-4579-afe2-ad045da7696c',1,'2019-10-27',10,NULL,0),
(14,'fcf0e715-efa7-4967-adf5-03c97ac214e4',1,'2019-10-28',10,NULL,0),
(15,'7570b8dd-d644-4e51-93a3-8124b9efdeee',1,'2019-10-28',10,NULL,0),
(16,'76ab6e81-6633-4c80-a755-e46d60443347',1,'2019-10-28',10,NULL,0),
(17,'680477ba-4025-4522-a8a6-418b46fdaad4',1,'2019-10-28',10,NULL,0),
(18,'1f017ac7-c39e-4557-81cf-7a30d836a99b',1,'2019-10-28',10,NULL,0),
(19,'f9c61e27-4323-4ef7-9ce8-c0f1059ed040',1,'2019-10-28',10,NULL,0),
(20,'b54a6e3c-7e44-490c-a3f9-4476caea6706',1,'2019-10-28',10,NULL,0),
(21,'8d427c60-b00b-49fe-a4a9-377ec970c6f4',1,'2019-10-28',10,NULL,0),
(22,'25f58cae-838c-40cc-bc8a-89a0448af4d4',1,'2019-10-28',10,NULL,0),
(23,'d48985d6-d15a-4cde-9ace-0f6278381121',1,'2019-10-28',10,NULL,0),
(24,'3a42961d-7569-4129-806f-11719d2ca2d2',1,'2019-10-28',10,NULL,0),
(25,'93f7bd50-1824-40f6-aeee-49fa0c13cd4e',1,'2019-10-31',10,NULL,0),
(26,'02d110e7-982a-4158-9c8c-8a23251d8710',1,'2019-10-31',10,NULL,0),
(27,'ac0ba693-1ad5-4501-9a59-a0386d26cf32',1,'2019-10-31',10,NULL,0),
(28,'c149689c-339d-4fee-8dcb-94597e56d582',1,'2019-10-31',10,NULL,0),
(29,'152a9a26-07d2-4a8a-9f83-c5d75cb460ac',1,'2019-11-01',10,NULL,0),
(30,'cb9b5857-0f2e-42ff-b171-dfd96e7e7b3d',1,'2019-11-01',10,NULL,0),
(31,'63c601ab-e405-410a-b3b6-72c7fcc1f36d',2,'2019-11-01',10,NULL,0),
(32,'61431947-8159-4c16-b07e-3784efdfa95b',2,'2019-11-01',10,NULL,0),
(33,'63665c86-6a71-466b-ad3e-ad2f8083202b',2,'2019-11-02',10,NULL,0),
(34,'628843d1-9afb-476f-b48a-fe3fd3157e25',2,'2019-11-02',10,NULL,0),
(35,'62aae6a7-5c17-40e3-9e5c-ca6a654ee65f',2,'2019-11-02',10,NULL,0),
(36,'4511ccf7-bc93-4c2e-8bd5-680fbc373264',2,'2019-11-02',10,NULL,0),
(37,'908aa078-fe24-42f0-8e78-4c30a086b841',2,'2019-11-02',10,NULL,0),
(38,'4cc92f44-478c-4834-b1c4-38507ea8b0bb',2,'2019-11-04',10,NULL,0),
(39,'0d55c2ef-394b-4d32-a8bb-544fdad83a84',2,'2019-11-04',10,NULL,0),
(40,'30040370-cb23-460d-9a8b-08d91feaa4b6',2,'2019-11-04',10,NULL,0),
(41,'70d1836c-3589-4786-959d-c758efd96901',1,'2019-11-04',10,NULL,0),
(42,'f75b050e-f8ae-4233-bbf2-1e430c673a91',1,'2019-11-05',10,NULL,0),
(43,'435877ec-8cb5-4ee8-91ea-18e2ffc3e678',1,'2019-11-13',10,NULL,0),
(44,'320572b4-a351-4e02-b4b2-487d5707c565',1,'2019-11-13',10,NULL,0),
(45,'22356910-5b4d-4b79-98e9-99c57cee089d',1,'2019-11-13',10,NULL,0),
(46,'fc141ec8-c1bd-4397-8221-2d337df60cbb',1,'2019-11-13',10,NULL,0),
(47,'fcf8319b-df91-4535-86cb-407a4246e7c4',1,'2019-11-13',10,NULL,0),
(48,'99717df2-2381-49e5-96a8-2a22a3cd90ae',1,'2019-12-09',10,NULL,0),
(49,'e0b3069e-7471-4892-8924-c8dfca853684',1,'2019-12-21',10,NULL,0),
(50,'2a3baeb6-276a-4fb7-913f-b223cce7d808',1,'2019-12-21',10,NULL,0),
(51,'a093eb4f-6402-4a9b-832b-7e465035799b',1,'2019-12-21',10,NULL,0),
(52,'e71776a4-2600-45db-bc2c-9b1e856b2378',1,'2019-12-23',10,NULL,0),
(53,'68efdce5-3716-4588-a5ad-5bbd42a1bb56',1,'2019-12-23',10,NULL,0),
(54,'8dbcbc2f-5328-444d-8f9b-a8d11c7d2ae9',1,'2019-12-23',10,NULL,0),
(55,'ae81ab6d-ca6f-4583-87a1-b751eb9875a5',1,'2019-12-23',10,NULL,0),
(56,'520b2909-be1e-4b5c-bbfb-ffc249525467',1,'2019-12-23',10,NULL,0),
(57,'0c4cfe8f-cd55-42a6-a173-5f9d3b7d4fe7',1,'2019-12-27',10,NULL,0),
(58,'ba27599b-355c-458b-b087-cb2c00a802ca',1,'2019-12-27',10,NULL,0),
(59,'c3cb586c-8e44-4c0f-99ee-455c77f17ff2',1,'2019-12-28',10,NULL,0),
(60,'5bd75c5f-aac5-451f-ade5-2087bb26be02',1,'2020-01-06',10,NULL,0),
(61,'5a1cd11e-ad73-4783-b224-f5316fa48153',1,'2020-01-06',10,NULL,0),
(62,'37166821-ab87-4617-987c-4431b3d074b4',1,'2020-01-07',10,NULL,0),
(63,'9da50d9d-cd16-4cfe-ade1-cb29964fb1a1',1,'2020-01-07',10,NULL,0),
(64,'f5c92b9e-0ae1-4331-9f8f-f40dfedffe40',1,'2020-01-13',10,NULL,0),
(65,'6afc8115-f818-45b5-bf86-9df578a8e3d0',1,'2020-01-23',10,NULL,0),
(66,'d892b495-5594-4f51-a85a-7870e5b5ba15',1,'2020-01-23',10,NULL,0),
(67,'6c279aa7-0f69-4a07-b5f2-41c9cc97bb2c',1,'2020-01-23',10,NULL,0),
(68,'e7da328f-0ce9-4b7c-baee-e76e5eac8d3b',1,'2020-01-24',10,NULL,0),
(69,'42d98d7c-cfdc-4645-987d-eae6789b8ec1',1,'2020-01-25',10,NULL,0),
(70,'a4b72e59-37de-4fd8-93c8-b86830a1f74a',1,'2020-01-25',10,NULL,0),
(71,'22b7e224-6033-4d32-afd0-ceb1c8e0ec37',1,'2020-01-25',10,NULL,0),
(72,'4921c3f1-8fde-4018-8f03-246329a0c184',1,'2020-01-25',10,NULL,0),
(73,'e92399a5-2cff-4614-a8db-0e659c46ae5b',1,'2020-01-25',10,NULL,0),
(74,'4f274f08-7dd4-483c-b28b-6b2cbbe22d1b',1,'2020-01-25',10,NULL,0),
(75,'ed79c53b-7b7f-4cac-aca2-3a253bdc2be6',1,'2020-01-25',10,NULL,0),
(76,'d49d352a-61b3-49bd-bc08-8f909bf2b81d',1,'2020-02-01',10,NULL,0),
(123,'4a9e3c46-7004-49d5-9260-7689db517c50',1,'2020-02-14',10,NULL,0),
(124,'dfadff89-af46-4fcd-ae5f-0529e3c4bac4',1,'2020-02-14',10,NULL,0),
(125,'50f28660-9c85-40b3-8497-a733d255ae8a',1,'2020-03-03',10,NULL,0),
(126,'02ff2628-4ea4-4f23-8aa3-69165d8a83df',1,'2020-03-03',10,NULL,0),
(127,'269201c0-6ff6-46b8-8624-3c832d22be58',1,'2020-04-02',10,NULL,0);

/*Table structure for table `measurement` */

DROP TABLE IF EXISTS `measurement`;

CREATE TABLE `measurement` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `measurement` */

insert  into `measurement`(`ID`,`TITLE`,`NAME`,`CREATED_ON`,`IS_ACTIVE`,`IS_DELETED`,`SHOP_ID`) values 
(1,'MEASUREMENT','LTR','2021-07-18',1,0,'MILAAN61'),
(2,'Liter','LTR','2021-08-10',1,0,'MILAAN55');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `MESSAGEID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_FROM` int(10) NOT NULL,
  `USER_TO` int(10) NOT NULL,
  `BODYCONTENT` varchar(500) COLLATE utf8_bin NOT NULL,
  `CREATEDATE` date DEFAULT NULL,
  PRIMARY KEY (`MESSAGEID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `message` */

insert  into `message`(`MESSAGEID`,`USER_FROM`,`USER_TO`,`BODYCONTENT`,`CREATEDATE`) values 
(1,5,13,'This is very first begining of message','2019-09-29'),
(2,13,5,'okay good plz put your question','2019-09-29'),
(3,9,16,'hallo 16 how are u',NULL),
(4,16,9,'i am good 9',NULL),
(5,5,13,'today is sunday','2019-09-29'),
(6,5,1,'Your ballence is very low you can\'t ask any questiuon','2019-11-01');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `NOTIFICATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ARTICLE_ID` int(11) DEFAULT NULL,
  `SUMMERY_DETAILS` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `CATEGORY` int(11) DEFAULT NULL,
  `SUB_CATAGORY` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `DISPLAY_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` int(2) NOT NULL DEFAULT '0',
  `CREATED_ON` date DEFAULT NULL,
  `TIMELINE_ID` int(11) DEFAULT NULL,
  `USER_TYPE` int(11) DEFAULT NULL,
  `USER_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ADMIN_ID` int(3) NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`NOTIFICATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `notification` */

insert  into `notification`(`NOTIFICATION_ID`,`USER_ID`,`ARTICLE_ID`,`SUMMERY_DETAILS`,`CATEGORY`,`SUB_CATAGORY`,`DISPLAY_NAME`,`STATUS`,`CREATED_ON`,`TIMELINE_ID`,`USER_TYPE`,`USER_NAME`,`SHOP_NAME`,`ADMIN_ID`,`IS_ACTIVE`,`IS_DELETED`) values 
(1,2,4,'this is test notification',12,'4','Test notification',50,NULL,0,NULL,NULL,NULL,0,0,1),
(2,2,5,'notification test',12,'4','notificaiton tst',50,NULL,0,NULL,NULL,NULL,0,0,1),
(13,48,0,'RT @turkialhussini1: .. \n\n?? ??? ?? ????? ?????? ???? ?? ???? ????? ????? ! \n \n???? ??? ???????? ???? ????? ??? ??????? ? \n\n.',0,NULL,'RT @turkialhussini1: .. \n\n?? ??? ?? ????? ?????? ?',49,NULL,0,NULL,NULL,NULL,0,0,1),
(14,48,0,'RT @dr_ibrahimarifi: @turkialhussini1 ???? ????? ????\n\n?????? ??? ???? ?????? ???? ?????? ????? ??????? ??????? ??????? ???? ????? ????????',0,NULL,'RT @dr_ibrahimarifi: @turkialhussini1 ???? ????? ?',49,NULL,0,NULL,NULL,NULL,0,0,1),
(15,3,0,'Hola chicos, mi nombre es Avinash, un profesional de la inform?tica que est? explorando aplicaciones empresariales con dominio experto en la industria de fabricaci?n, ventas, banca, finanzas y servicios de medios.',0,NULL,'Hola chicos, mi nombre es Avinash, un profesional d',49,NULL,0,NULL,NULL,NULL,0,0,1),
(16,1,0,'user , jay26 Nov 2019\nHow to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate.',0,NULL,'user , jay26 Nov 2019\nHow to do bidirection mappin',49,NULL,0,NULL,NULL,NULL,0,0,1),
(17,1,0,'????? ????? ???? ???? ?????? ?? ??????? ?????? ?????? ??? ????? ?????. ?? ?????? ????? ???? ?? ??? ???????',0,NULL,'????? ????? ???? ???? ?????? ?? ??????? ?????? ???',49,NULL,0,NULL,NULL,NULL,0,0,1),
(18,48,0,'RT @saudi_umran: ????? ????? #??????? ??????? ????????? ?? ??? ???? ??????? @aldegheishem ???? ???? ????? ???? ????? ????????? ????????? GI?',0,NULL,'RT @saudi_umran: ????? ????? #??????? ??????? ????',49,NULL,0,NULL,NULL,NULL,0,0,1),
(19,5,0,'?????? \nTest',0,NULL,'?????? \nTest',49,NULL,0,NULL,NULL,NULL,0,0,1),
(20,48,0,'RT @Kahrabaiat: ???? ??????? ??? ???? ?? #?????_???????_?????? ?? ????? ??????? ???? ??????? ???? ????? ????? ??????? ?????? ?????? ????????',0,NULL,'RT @Kahrabaiat: ???? ??????? ??? ???? ?? #?????_??',49,NULL,0,NULL,NULL,NULL,0,0,1),
(21,48,0,'RT @Fahad_Allahaim: ????? ?????? ???? ?????? ???? ??? .. https://t.co/SkTgtckkMi',0,NULL,'RT @Fahad_Allahaim: ????? ?????? ???? ?????? ???? ',49,NULL,0,NULL,NULL,NULL,0,0,1),
(22,48,0,'RT @Umran_ID: ??? ???? ??????? ??????? ???????? ???????? ????? ??????? ??? ??????? ?????? ?????? ????? ??? ???????? ?? ?????? ???????? ?? ??',0,NULL,'RT @Umran_ID: ??? ???? ??????? ??????? ???????? ??',49,NULL,0,NULL,NULL,NULL,0,0,1),
(23,5,0,'RT @AlhadabiBadar: ???? ???????? ????????? ????????? ????? ??????-??????? ?? ?? ???? ???????? ????? ?? ?????. ??????? ?????? ?????? ???????',0,NULL,'RT @AlhadabiBadar: ???? ???????? ????????? ???????',49,NULL,0,NULL,NULL,NULL,0,0,1),
(24,5,0,'RT @AlHulafa: ????? ????? ??? ??????? ??????? ????\n\n? ???? ?? ????? ???????\n? ??? ????? ???????\n? ????? ??? ?? ????? ???????\n? ?????? ??? ??',0,NULL,'RT @AlHulafa: ????? ????? ??? ??????? ??????? ????',49,NULL,0,NULL,NULL,NULL,0,0,1),
(25,1,0,'???? ?????? ?????. ??? ???? ?? ????????',0,NULL,'???? ?????? ?????. ??? ???? ?? ????????',49,'2020-01-04',0,NULL,NULL,NULL,0,0,1),
(30,48,0,'RT @turkialhussini1: @nada___97 ???? ???? ??? ???????? .. ?????? ????? ?????????? ???? ????? ?????? ??????????',0,NULL,'RT @turkialhussini1: @nada___97 ???? ???? ??? ????',49,'2020-01-06',0,NULL,NULL,NULL,0,0,1),
(31,48,0,'RT @nada___97: @turkialhussini1 ???? ??? ??? ???????? ??? ?????? ?????? ??? ???? ???? ?? ?????? ?',0,NULL,'RT @nada___97: @turkialhussini1 ???? ??? ??? ?????',49,'2020-01-06',0,NULL,NULL,NULL,0,0,1),
(32,48,0,'??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ???? 3.4 ? ??? ??????? ?? ???? ??????? ??????  .. ????? ?????? ??????? ?????? ??? ???? ??? ??? ?????? ?????? ????? ???? ?????? ?????? ???? 4 ?? ?? 4 ?? ??? 1.4 ? ???? ?????? ????? .. ??????? ??? ????? ??? ????? ???? ??? ?????? https://t.co/vwiKnMCzxS',0,NULL,'??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ??',49,'2020-01-06',0,NULL,NULL,NULL,0,0,1),
(33,48,0,'RT @Fahad_Allahaim: ???? ???? ??????? ??????? ?? ??? ?????? ??? ???? ??? ??????? ?? ?? ???? ????? ????? ???? ??? ??????? ?????? https://t.c?',0,NULL,'RT @Fahad_Allahaim: ???? ???? ??????? ??????? ?? ?',49,'2020-01-06',0,NULL,NULL,NULL,0,0,1),
(34,51,0,'How to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate.',0,NULL,'How to do bidirection mapping in jpa , relation of',49,'2020-01-06',0,NULL,NULL,NULL,0,0,1),
(35,51,0,'\n??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ???? 3.4 ? ??? ??????? ?? ???? ??????? ?????? .. ????? ?????? ??????? ?????? ??? ???? ??? ??? ?????? ?????? ????? ???? ?????? ?????? ???? 4 ?? ?? 4 ?? ??? 1.4 ? ???? ?????? ????? .. ??????? ??? ????? ??? ????? ???? ??? ??????',0,NULL,'\n??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ?',49,'2020-01-07',0,NULL,NULL,NULL,0,0,1),
(40,26,0,'asd',0,NULL,'asd',49,'2020-01-20',0,NULL,NULL,NULL,0,0,1),
(41,1,0,'',0,NULL,'',49,'2020-01-24',0,NULL,NULL,NULL,0,0,1),
(42,1,0,'??? ?? ?????? ?????? ???????? ????? ???? ???? ???.',0,NULL,'??? ?? ?????? ?????? ???????? ????? ???? ???? ???.',49,'2020-01-24',0,NULL,NULL,NULL,0,0,1),
(43,1,0,'?? ??????? ???? ???????? ??????????? ????????? ???????',0,NULL,'?? ??????? ???? ???????? ??????',49,'2020-01-24',0,NULL,NULL,NULL,0,0,1),
(44,1,0,'??????????? ????????? ??????? ??? ?? ???? ??? ???? ??? ?? ????? ????',0,NULL,'??????',49,'2020-01-24',0,NULL,NULL,NULL,0,0,1),
(45,1,0,'Delhi assembly election is on 8th Feb. Keep count for your valuable votes.',0,NULL,'Delhi assembly election is on 8th Feb. Keep count ',49,'2020-01-25',0,NULL,NULL,NULL,0,0,1),
(46,1,0,'Test',0,NULL,'Test',49,'2020-01-25',0,NULL,NULL,NULL,0,0,1),
(47,1,0,'????????? ?????????????????',0,NULL,'????????? ?????????????????',49,'2020-01-25',0,NULL,NULL,NULL,0,0,1),
(48,1,0,'content://media/external/images/media/46134',0,NULL,'content://media/external/images/media/46134',49,'2020-01-28',0,NULL,NULL,NULL,0,0,1),
(49,1,0,'/storage/emulated/0/DCIM/Camera/Like_6785736832278221935.mp4',0,NULL,'/storage/emulated/0/DCIM/Camera/Like_6785736832278',49,'2020-01-28',0,NULL,NULL,NULL,0,0,1),
(50,1,0,'content://com.google.android.apps.photos.contentprovider/-1/2/content%3A%2F%2Fmedia%2Fexternal%2Fvideo%2Fmedia%2F46224/ORIGINAL/NONE/video%2Fmp4/616830718',0,NULL,'content://com.google.android.apps.photos.contentpr',49,'2020-01-28',0,NULL,NULL,NULL,0,0,1),
(51,1,0,'content://media/external/images/media/46138',0,NULL,'content://media/external/images/media/46138',49,'2020-01-28',0,NULL,NULL,NULL,0,0,1),
(52,1,0,'Failure will never overtake me if my determination to succeed is strong enough',0,NULL,'Failure will never overtake me if my determination',49,'2020-01-28',0,NULL,NULL,NULL,0,0,1),
(53,26,0,'26_56_9861db7e-8b1f-4e7a-bbce-0e453041e74a-trailer_hd.mp4',0,NULL,'26_56_9861db7e-8b1f-4e7a-bbce-0e453041e74a-trailer',49,'2020-01-29',0,NULL,NULL,NULL,0,0,1),
(54,1,0,'Knowing is not enough, we must apply. Willing is not enough, we must do.',0,NULL,'Knowing is not enough, we must apply. Willing is n',49,'2020-01-29',0,NULL,NULL,NULL,0,0,1),
(55,1,0,'Knowing is not enough, we must apply',0,NULL,'Knowing is not enough, we must apply',49,'2020-01-29',0,NULL,NULL,NULL,0,0,1),
(56,26,0,'26_56_065fb788-9868-425a-8c58-9b8bbd73c869-y2mate.com - akshayakalpa_organic_milk_6SWVV7LELSk_240p.mp4',0,NULL,'26_56_065fb788-9868-425a-8c58-9b8bbd73c869-y2mate.',49,'2020-01-30',0,NULL,NULL,NULL,0,0,1),
(57,1,0,'1_56_52012bff-8dc5-42fd-9cd3-b5294acc1a6b-WhatsApp Video 2020-01-30 at 1.03.58 PM.mp4',0,NULL,'1_56_52012bff-8dc5-42fd-9cd3-b5294acc1a6b-WhatsApp',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(58,1,0,'1_58_7fa74d87-98d4-4110-9e16-de0faae74051-peripherals.jpg',0,NULL,'1_58_7fa74d87-98d4-4110-9e16-de0faae74051-peripher',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(59,1,0,'1_58_e3d8f975-2feb-48f1-8f40-121b29d03ce5-images.jpg',0,NULL,'1_58_e3d8f975-2feb-48f1-8f40-121b29d03ce5-images.j',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(60,1,0,'1_58_aa3e2193-64d1-4f67-aee1-4591c78b18fc-images.jpg',0,NULL,'1_58_aa3e2193-64d1-4f67-aee1-4591c78b18fc-images.j',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(61,1,0,'1_58_dff5af3e-325a-46cb-9406-31bb443f607d-5cc230d9c3a7c15db8365bd5-1136-853.jpg',0,NULL,'1_58_dff5af3e-325a-46cb-9406-31bb443f607d-5cc230d9',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(62,1,0,'1_58_5402b020-2cea-4ed5-9cca-4ff197720cca-b844a205b331be4dd32fd0472af96261.jpg',0,NULL,'1_58_5402b020-2cea-4ed5-9cca-4ff197720cca-b844a205',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(63,1,0,'1_56_e53c4260-ebe2-4ded-9811-2b07ed3732d5-Like_6785736832278221935.mp4',0,NULL,'1_56_e53c4260-ebe2-4ded-9811-2b07ed3732d5-Like_678',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(64,1,0,'',0,NULL,'',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(65,1,0,'',0,NULL,'',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(66,1,0,'',0,NULL,'',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(67,1,0,'1_55_ac889052-dbdc-4581-865d-add0d70b2b85-Tujhe Kitna Chahein Aur Hum | Kabir Singh | Jubin Nautiyal Live | Mithoon | Thomso 2019 | IIT Roorke',0,NULL,'1_55_ac889052-dbdc-4581-865d-add0d70b2b85-Tujhe Ki',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(68,1,0,'1_56_c88651f5-acd3-4cfb-94be-0ec0dd38d302-video.mp4',0,NULL,'1_56_c88651f5-acd3-4cfb-94be-0ec0dd38d302-video.mp',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(69,1,0,'1_56_05e50f1b-6624-41a1-ba01-ffe197c5d8b8-video.mp4',0,NULL,'1_56_05e50f1b-6624-41a1-ba01-ffe197c5d8b8-video.mp',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(70,1,0,'1_56_baf6313a-2f5e-43c7-8c77-f18e87e18d00-timeline.mp4',0,NULL,'1_56_baf6313a-2f5e-43c7-8c77-f18e87e18d00-timeline',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(71,1,0,'1_56_4316e317-821c-420d-b66e-e70863def247-thoughtPF_timeline.mp4',0,NULL,'1_56_4316e317-821c-420d-b66e-e70863def247-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(72,1,0,'1_58_388b2ed6-f5b3-4fa4-af1f-5af957053691-image-058c5db4-828d-4262-a87a-2ed69a31df2e.jpg',0,NULL,'1_58_388b2ed6-f5b3-4fa4-af1f-5af957053691-image-05',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(73,1,0,'1_58_8d7b057e-734f-4bc0-9a73-cbf969302663-thoughtPF_image.jpg',0,NULL,'1_58_8d7b057e-734f-4bc0-9a73-cbf969302663-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(74,1,0,'1_58_dcfb17e2-80e6-419c-aaaf-a15573051549-thoughtPF_image.jpg',0,NULL,'1_58_dcfb17e2-80e6-419c-aaaf-a15573051549-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(75,1,0,'1_58_6d160cae-6bc0-4abd-9614-fd14042bda4c-image-319b6693-b297-4b12-9919-f5ab434446d6.jpgthoughtPF_image.jpg',0,NULL,'1_58_6d160cae-6bc0-4abd-9614-fd14042bda4c-image-31',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(76,1,0,'1_58_f50f3f1a-dbd5-4142-a5db-c2f8c62ba393-thoughtPF_imageimage-a16576d7-bc7b-4ac1-94d8-eb09914c3b8d.jpg',0,NULL,'1_58_f50f3f1a-dbd5-4142-a5db-c2f8c62ba393-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(77,1,0,'1_58_82885be4-7c0b-44bc-b304-275938ea9dc7-thoughtPF_imageb844a205b331be4dd32fd0472af96261.jpg',0,NULL,'1_58_82885be4-7c0b-44bc-b304-275938ea9dc7-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(78,1,0,'1_55_e0b49b98-4c17-4360-b38c-2e7e7b9f5761-thoughtPF_audioTujhe Kitna Chahein Aur Hum | Kabir Singh | Jubin Nautiyal Live | Mithoon | Thomso 2019 | IIT Roorke',0,NULL,'1_55_e0b49b98-4c17-4360-b38c-2e7e7b9f5761-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(79,1,0,'Abcd check',0,NULL,'Abcd check',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(80,1,0,'1_58_fd3265c9-3cb1-4068-abd3-a6c24668dbc7-thoughtPF_imageimage-40cfc759-4ae8-41f6-9b94-ecba056123f3.jpg',0,NULL,'1_58_fd3265c9-3cb1-4068-abd3-a6c24668dbc7-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(81,1,0,'1_55_e0f02e08-8f95-4164-b6f0-e92bd4fc0960-thoughtPF_audioTujhe Kitna Chahein Aur Hum | Kabir Singh | Jubin Nautiyal Live | Mithoon | Thomso 2019 | IIT Roorke',0,NULL,'1_55_e0f02e08-8f95-4164-b6f0-e92bd4fc0960-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(82,1,0,'1_56_ed53bbd4-b2be-44da-93c9-5fb375b4ac64-thoughtPF_timeline.mp4',0,NULL,'1_56_ed53bbd4-b2be-44da-93c9-5fb375b4ac64-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(83,1,0,'1_56_0a23a3ae-c57c-40aa-a81f-9d71dc8a5565-thoughtPF_timeline.mp4',0,NULL,'1_56_0a23a3ae-c57c-40aa-a81f-9d71dc8a5565-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(84,1,0,'1_58_180d1861-b0e4-46be-8132-f82dc5af665d-thoughtPF_imageimage-e64e21fb-a7f1-4e9c-8056-d94fd7228165.jpg',0,NULL,'1_58_180d1861-b0e4-46be-8132-f82dc5af665d-thoughtP',49,'2020-01-31',0,NULL,NULL,NULL,0,0,1),
(85,1,0,'Test ask question',0,NULL,'Test ask question',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(86,1,0,'1_56_5d6629f8-b8f1-403f-9898-39c7053b0526-thoughtPF_timeline.mp4',0,NULL,'1_56_5d6629f8-b8f1-403f-9898-39c7053b0526-thoughtP',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(87,1,0,'1_56_9c060a34-e0e3-4add-8522-7367e0929e81-thoughtPF_timeline.mp4',0,NULL,'1_56_9c060a34-e0e3-4add-8522-7367e0929e81-thoughtP',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(88,2,0,'',0,NULL,'',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(89,51,0,'',0,NULL,'',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(90,48,0,'What\'s your name? ',0,NULL,'What\'s your name? ',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(91,2,0,'What do you have? ',0,NULL,'What do you have? ',49,'2020-02-03',0,NULL,NULL,NULL,0,0,1),
(92,51,0,'Ghtdhfijgfjy',0,NULL,'Ghtdhfijgfjy',49,'2020-02-04',0,NULL,NULL,NULL,0,0,1),
(93,51,0,'Asdf',0,NULL,'Asdf',49,'2020-02-08',0,NULL,NULL,NULL,0,0,1),
(94,1,0,'1_56_12bd614f-3509-4365-bfd3-feac44ce796c-thoughtPF_timeline.mp4',0,NULL,'1_56_12bd614f-3509-4365-bfd3-feac44ce796c-thoughtP',49,'2020-02-09',0,NULL,NULL,NULL,0,0,1),
(95,1,0,'1_56_df96b6d7-f63d-4abd-bc32-f49e4431b4a7-thoughtPF_timeline.mp4',0,NULL,'1_56_df96b6d7-f63d-4abd-bc32-f49e4431b4a7-thoughtP',49,'2020-02-09',0,NULL,NULL,NULL,0,0,1),
(96,2,0,'Test ask question',0,NULL,'Test ask question',49,'2020-02-10',58,NULL,NULL,NULL,0,0,1),
(97,2,0,'Test ask question',0,NULL,'Test ask question',49,'2020-02-10',59,NULL,NULL,NULL,0,0,1),
(98,1,0,'Test ask question',0,NULL,'Test ask question',49,'2020-02-10',59,NULL,NULL,NULL,0,0,1),
(99,2,0,'Test ask question second time',0,NULL,'Test ask question second time',49,'2020-02-10',59,NULL,NULL,NULL,0,0,1),
(100,2,0,'Ughyyy',0,NULL,'Ughyyy',49,'2020-02-10',54,NULL,NULL,NULL,0,0,1),
(101,48,0,'Ask',0,NULL,'Ask',49,'2020-02-11',59,NULL,NULL,NULL,0,0,1),
(102,48,0,'Question for free',0,NULL,'Question for free',49,'2020-02-11',59,NULL,NULL,NULL,0,0,1),
(103,2,0,'I\'m asking free question to jay user ',0,NULL,'I\'m asking free question to jay user ',49,'2020-02-11',54,NULL,NULL,NULL,0,0,1),
(104,2,0,'Test ask question second time',0,NULL,'Test ask question second time',49,'2020-02-11',59,NULL,NULL,NULL,0,0,1),
(105,2,0,'Ask questions ',0,NULL,'Ask questions ',49,'2020-02-11',59,NULL,NULL,NULL,0,0,1),
(106,2,0,'Ask questions 124',0,NULL,'Ask questions 124',49,'2020-02-11',59,NULL,NULL,NULL,0,0,1),
(107,2,0,'Ask 12345',13,NULL,'Ask 12345',49,'2020-02-11',141,NULL,NULL,NULL,0,0,1),
(108,1,0,'Great color mixing activities for kids, including how to mix paint, color science ... FREE printable book activity for toddlers to go along with',12,NULL,'Great color mixing activities for kids, including ',49,'2020-02-11',142,NULL,NULL,NULL,0,0,1),
(109,51,0,'This guide walks you through the process of building an application that uses Spring Data JPA to store and retrieve data in a relational database.',13,NULL,'This guide walks you through the process of buildi',49,'2020-02-11',143,NULL,NULL,NULL,0,0,1),
(110,26,0,'Asking question here',13,NULL,'Asking question here',49,'2020-02-12',144,NULL,NULL,NULL,0,0,1),
(111,2,0,'Asking free question\n',13,NULL,'Asking free question\n',49,'2020-02-12',145,NULL,NULL,NULL,0,0,1),
(112,47,0,'Test ask question second time',16,NULL,'Test give Answer',49,'2020-02-14',133,NULL,NULL,NULL,0,0,1),
(113,1,0,'Hi jaynt',16,NULL,'Hi jaynt',49,'2020-02-14',146,NULL,NULL,NULL,0,0,1),
(114,51,0,'Hi jaynt',16,NULL,'Hi Avinash',49,'2020-02-15',146,NULL,NULL,NULL,0,0,1),
(115,48,0,'RT @Eng_A_C: ???? ???? ??? ?????? ???????? ???????? ??:\n????? ??????? ?? ???? ?????\n?????? ??????? ??????\n?????????? ?? ????? ?????? ???????',14,NULL,'RT @Eng_A_C: ???? ???? ??? ?????? ???????? ???????',49,'2020-02-22',147,NULL,NULL,NULL,0,0,1),
(116,48,0,'?',14,NULL,'?',49,'2020-02-22',148,NULL,NULL,NULL,0,0,1),
(117,26,0,'????',13,NULL,'????',49,'2020-02-22',149,NULL,NULL,NULL,0,0,1),
(118,1,0,'???? ????? ?????? ???? .....\n\n??? ????? ???? ????? ??? ????? ?????? ??? ??? ????? ??? ???? ?? ???? ?',12,NULL,'???? ????? ?????? ???? .....\n\n??? ????? ???? ?????',49,'2020-02-25',150,NULL,NULL,NULL,0,0,1),
(119,1,0,'',12,NULL,'',49,'2020-03-07',151,NULL,NULL,NULL,0,0,1),
(120,1,0,'',12,NULL,'',49,'2020-03-07',152,NULL,NULL,NULL,0,0,1),
(121,1,0,'',12,NULL,'',49,'2020-03-07',153,NULL,NULL,NULL,0,0,1),
(122,51,0,'51_58_c816f555-b982-4833-9590-be730d788139-thoughtPF_imageimage-8a6f9c33-3825-4a73-81a8-432d56befb97.jpg',13,NULL,'51_58_c816f555-b982-4833-9590-be730d788139-thought',49,'2020-03-07',154,NULL,NULL,NULL,0,0,1),
(123,77,22,'wel',2,'true','Mobile',1,'2021-08-12',2,2,'Rohan','electronic',33,1,0),
(124,67,22,'wel',2,'true','Mobile',1,'2021-08-12',2,2,'Rohan','electronic',33,1,0),
(125,57,22,'wel',2,'true','Mobile',1,'2021-08-12',2,2,'Rohan','electronic',33,1,0);

/*Table structure for table `offline` */

DROP TABLE IF EXISTS `offline`;

CREATE TABLE `offline` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `STOCK` int(3) NOT NULL DEFAULT '0',
  `PRODUCT_QUANTITY` int(11) DEFAULT NULL,
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `GST_AMOUNT` float NOT NULL DEFAULT '0',
  `SELLING_PRICE` float NOT NULL DEFAULT '0',
  `ADMIN_ID` int(4) NOT NULL DEFAULT '0',
  `SHOP_ID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` int(3) NOT NULL DEFAULT '0',
  `BILLING_DATE` datetime DEFAULT NULL,
  `PRODUCT_ID` int(10) NOT NULL DEFAULT '0',
  `OFFER_ACTIVE_END` tinyint(1) NOT NULL DEFAULT '0',
  `OLD_PRICE` float NOT NULL DEFAULT '0',
  `TOTAL_PRICE` float NOT NULL DEFAULT '0',
  `DISCOUNT` float NOT NULL DEFAULT '0',
  `OFFER_PERCENT` int(5) NOT NULL DEFAULT '0',
  `GST_PERCENT` int(4) NOT NULL DEFAULT '0',
  `BRAND_NAME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `MEASUREMENT` int(4) DEFAULT NULL,
  `BATCH_NUMBER` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `DATE_OF_EXPIRE` date DEFAULT NULL,
  `PAYABLE_AMOUNT` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `offline` */

insert  into `offline`(`ID`,`USER_NAME`,`MOBILE_NUMBER`,`PRODUCT_NAME`,`STOCK`,`PRODUCT_QUANTITY`,`SHOP_NAME`,`CREATED_ON`,`IS_DELETED`,`IS_ACTIVE`,`GST_AMOUNT`,`SELLING_PRICE`,`ADMIN_ID`,`SHOP_ID`,`USER_ID`,`BILLING_DATE`,`PRODUCT_ID`,`OFFER_ACTIVE_END`,`OLD_PRICE`,`TOTAL_PRICE`,`DISCOUNT`,`OFFER_PERCENT`,`GST_PERCENT`,`BRAND_NAME`,`MEASUREMENT`,`BATCH_NUMBER`,`DATE_OF_EXPIRE`,`PAYABLE_AMOUNT`) values 
(1,'Mohan',NULL,NULL,0,0,NULL,'2021-08-25',0,0,117.499,19835.2,0,'MILAAN61',0,NULL,0,0,19643,20000,302.77,0,0,NULL,NULL,NULL,NULL,20000),
(2,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-25',0,0,0,88,0,'MILAAN61',0,NULL,0,0,88,88,0,0,0,NULL,NULL,NULL,NULL,88),
(3,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-25',0,0,21.69,367,0,'MILAAN61',0,NULL,0,0,377,376,10,0,0,NULL,NULL,NULL,NULL,376),
(4,'SANAT','7677256089',NULL,0,0,NULL,'2021-08-26',0,0,46.4,323,0,'MILAAN61',0,NULL,0,0,338,448.6,15,0,0,NULL,NULL,NULL,NULL,449),
(5,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,0,50,0,'MILAAN61',0,NULL,0,0,50,50,0,0,0,NULL,NULL,NULL,NULL,50),
(6,'aa bb','7677256089',NULL,0,0,NULL,'2021-08-26',0,0,8.8,88,0,'MILAAN61',0,NULL,0,0,88,88,0,0,0,NULL,NULL,NULL,NULL,88),
(7,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,8.8,88,0,'MILAAN61',0,NULL,0,0,88,176,0,0,0,NULL,NULL,NULL,NULL,176),
(8,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,0,0,0,'MILAAN61',0,NULL,0,0,0,0,0,0,0,NULL,NULL,NULL,NULL,0),
(9,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,8.8,88,0,'MILAAN61',0,NULL,0,0,88,88,0,0,0,NULL,NULL,NULL,NULL,88),
(10,'rishabh dev','09905852747',NULL,0,0,NULL,'2021-08-26',0,0,4.0375,80.75,0,'MILAAN61',0,NULL,0,0,85,80.75,4.25,0,0,NULL,NULL,NULL,NULL,81),
(11,'rishabh dev','09905852747',NULL,0,0,NULL,'2021-08-26',0,0,8.8,88,0,'MILAAN61',0,NULL,0,0,88,88,0,0,0,NULL,NULL,NULL,NULL,88),
(12,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,8.8,88,0,'MILAAN61',0,NULL,0,0,88,88,0,0,0,NULL,NULL,NULL,NULL,88),
(13,'rishabh dev','09905852747',NULL,0,0,NULL,'2021-08-26',0,0,609,12090,0,'MILAAN98',0,NULL,0,0,12100,12099,10,0,0,NULL,NULL,NULL,NULL,12099),
(14,'SANAT','07677256089',NULL,0,0,NULL,'2021-08-26',0,0,0.69,69,0,'MILAAN61',0,NULL,0,0,69,69,0,0,0,NULL,NULL,NULL,NULL,69),
(15,'sxsa','07677256089',NULL,0,0,NULL,'2021-08-26',0,1,22.5,230,0,'MILAAN61',0,NULL,0,0,250,252.5,20,0,0,NULL,NULL,NULL,NULL,253),
(16,'vis','54156464564',NULL,0,0,NULL,'2021-08-26',0,0,3753.38,41031.5,0,'MILAAN98',0,NULL,0,0,44250,126706,3218.5,0,0,NULL,NULL,NULL,NULL,126706),
(17,'Vishal','9155316625',NULL,0,0,NULL,'2021-08-29',0,1,7209.5,45095,0,'MILAAN98',0,NULL,0,0,45100,225200,5,0,0,NULL,NULL,NULL,NULL,225200);

/*Table structure for table `offline_product` */

DROP TABLE IF EXISTS `offline_product`;

CREATE TABLE `offline_product` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `BRAND_NAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `QUANTITY` float NOT NULL DEFAULT '0',
  `PRICE` float NOT NULL DEFAULT '0',
  `OLD_PRICE` float NOT NULL DEFAULT '0',
  `TOTAL_PRICE` float NOT NULL DEFAULT '0',
  `DISCOUNT` float NOT NULL DEFAULT '0',
  `OFFER_PERCENT` int(4) NOT NULL DEFAULT '0',
  `GST_PERCENT` int(4) NOT NULL DEFAULT '0',
  `GST_AMOUNT` float NOT NULL DEFAULT '0',
  `OFFLINE_CART_ID` int(10) NOT NULL DEFAULT '0',
  `SHOP_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_ON` date DEFAULT NULL,
  `PRODUCT_ID` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `BILLING_DATE` datetime DEFAULT NULL,
  `MEASUREMENT` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `BATCH_NUMBER` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DATE_OF_EXPIRE` date DEFAULT NULL,
  `STOCK_ACTIVE_IND` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `offline_product` */

insert  into `offline_product`(`ID`,`PRODUCT_NAME`,`BRAND_NAME`,`QUANTITY`,`PRICE`,`OLD_PRICE`,`TOTAL_PRICE`,`DISCOUNT`,`OFFER_PERCENT`,`GST_PERCENT`,`GST_AMOUNT`,`OFFLINE_CART_ID`,`SHOP_ID`,`IS_ACTIVE`,`IS_DELETED`,`CREATED_ON`,`PRODUCT_ID`,`SHOP_NAME`,`BILLING_DATE`,`MEASUREMENT`,`BATCH_NUMBER`,`DATE_OF_EXPIRE`,`STOCK_ACTIVE_IND`) values 
(1,'SUGAR','SUGART',1,65.34,66,65.34,0.66,1,16,0,1,'MILAAN61',1,0,'2021-08-25','13','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(2,'SUGAR2','SUGART',1,77,77,77,0,0,16,0,1,'MILAAN61',1,0,'2021-08-25','14','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(3,'SUGAR10','SUGART',1,77,77,77,0,0,0,0,1,'MILAAN61',1,0,'2021-08-25','15','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(4,'SUGAR 5','RAJDHANI',1,65,65,65,0,0,16,0,1,'MILAAN61',1,0,'2021-08-25','33','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(5,'SUGAR CUBE','RAJDHANI',1,50,50,50,0,0,0,0,1,'MILAAN61',1,0,'2021-08-25','8','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(6,'SUGAR3','RAJDHANI',1,69,69,69,0,0,1,0,1,'MILAAN61',1,0,'2021-08-25','10','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(7,'SUGAR4','RAJDHANI',1,66,66,66,0,1,0,0,1,'MILAAN61',1,0,'2021-08-25','11','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(8,'SUGAR11','RAJDHANI',1,81,90,81,9,10,10,0,1,'MILAAN61',1,0,'2021-08-25','16','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(9,'WATER HEATER','INALSA',1,1000,1000,1000,0,0,8,0,1,'MILAAN61',1,0,'2021-08-25','2','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(10,'TEST SUGAR','RAJDHANI',1,70,70,70,0,0,16,0,1,'MILAAN61',1,0,'2021-08-25','9','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(11,'SUGAR88','SUGART',1,99,0,99,0,0,16,0,1,'MILAAN61',1,0,'2021-08-25','34','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(12,'SSs','BBb',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(13,'SSS2','BBB2',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(14,'SSS3','BBB3',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(15,'SSS4','BBB4',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(16,'SSS5','BBB5',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(17,'SSa','DWSd',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(18,'Ss','SDSa',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(19,'SADSa','SADa',1,109.89,111,110.989,1.11,1,1,1.0989,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(20,'SSWDd','DADd',1,97.68,111,109.402,13.32,12,12,11.7216,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(21,'SASa','BABa',1,106.48,121,117.128,14.52,12,10,10.648,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(22,'SANATp','BRAND B',1,90,100,99,10,10,10,9,1,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(23,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,0,2,'MILAAN61',1,0,'2021-08-25','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(24,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,0,3,'MILAAN61',1,0,'2021-08-25','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(25,'TESt','CDc',1,90,100,99,10,10,10,9,3,'MILAAN61',1,0,'2021-08-25',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(26,'SUGAR CUBE','RAJDHANI',1,50,50,50,0,0,0,0,3,'MILAAN61',1,0,'2021-08-25','8','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(27,'TEST SUGAR','RAJDHANI',1,70,70,70,0,0,16,12,3,'MILAAN61',1,0,'2021-08-25','9','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(28,'SUGAR3','RAJDHANI',1,69,69,69,0,0,1,0.69,3,'MILAAN61',1,0,'2021-08-25','10','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(29,'SUGAR','RAJDHANI',2,88,88,176,0,0,10,8.8,4,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(30,'AATa','Qq',1,100,100,116,0,0,16,16,4,'MILAAN61',1,0,'2021-08-26',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(31,'CHINNi','LOCAl',1,90,100,104.4,10,10,16,14.4,4,'MILAAN61',1,0,'2021-08-26',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(32,'MAIDa','LOCAl',1,45,50,52.2,5,10,16,7.2,4,'MILAAN61',1,0,'2021-08-26',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(33,'SUGAR CUBE','RAJDHANI',1,50,50,50,0,0,0,0,5,'MILAAN61',1,0,'2021-08-26','8','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(34,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,8.8,6,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(35,'SUGAR','RAJDHANI',2,88,88,176,0,0,10,8.8,7,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(36,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,8.8,9,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(37,'VISHAL SUGAR','SUGART',1,80.75,85,80.75,4.25,5,5,4.0375,10,'MILAAN61',1,0,'2021-08-26','20','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(38,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,8.8,11,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(39,'SUGAR','RAJDHANI',1,88,88,88,0,0,10,8.8,12,'MILAAN61',1,0,'2021-08-26','1','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(40,'AC','SONY',1,12000,12000,12000,0,0,5,600,13,'MILAAN98',1,0,'2021-08-26','32','Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',1),
(41,'MAIDA','LOCAL',1,90,100,99,10,10,10,9,13,'MILAAN98',1,0,'2021-08-26',NULL,'Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',0),
(42,'SUGAR3','RAJDHANI',1,69,69,69,0,0,1,0.69,14,'MILAAN61',1,0,'2021-08-26','10','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(43,'SUGAR CUBE','RAJDHANI',1,50,50,50,0,0,0,0,15,'MILAAN61',1,0,'2021-08-26','8','Grocery store',NULL,NULL,NULL,'0002-11-30',1),
(44,'DADDSa','SDAs',1,90,100,94.5,10,10,5,4.5,15,'MILAAN61',1,0,'2021-08-26',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(45,'ADAs','ASDa',1,90,100,108,10,10,20,18,15,'MILAAN61',1,0,'2021-08-26',NULL,'Grocery store',NULL,NULL,NULL,'0002-11-30',0),
(46,'AC','SONY',2,12000,12000,24000,0,0,5,600,16,'MILAAN98',1,0,'2021-08-26','32','Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',1),
(47,'LG TV','LG',2,14400,16000,28800,1600,10,10,1440,16,'MILAAN98',1,0,'2021-08-26','37','Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',1),
(48,'EFf','FSDf',10,95,100,1121,5,5,18,171,16,'MILAAN98',1,0,'2021-08-26',NULL,'Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',0),
(49,'VISHAl','VISHAl',5,136.5,150,784.875,13.5,9,15,102.375,16,'MILAAN98',1,0,'2021-08-26',NULL,'Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',0),
(50,'AC SONY','SONY',5,45000,45000,225000,0,0,16,7200,17,'MILAAN98',1,0,'2021-08-29','36','Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',1),
(51,'LUx','HIMALAYa',2,95,100,199.5,5,5,5,9.5,17,'MILAAN98',1,0,'2021-08-29',NULL,'Hari Om Electronics',NULL,NULL,NULL,'0002-11-30',0);

/*Table structure for table `plan` */

DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PLAN_TYPE` int(1) NOT NULL DEFAULT '0',
  `VALIDITY` int(10) NOT NULL DEFAULT '0',
  `AMOUNT` int(11) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_ON` date DEFAULT NULL,
  `DISCOUNT` int(11) NOT NULL DEFAULT '0',
  `IS_DISCOUNT` tinyint(1) NOT NULL DEFAULT '0',
  `OFFER_ACTIVE_END` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `plan` */

insert  into `plan`(`ID`,`PLAN_TYPE`,`VALIDITY`,`AMOUNT`,`IS_ACTIVE`,`IS_DELETED`,`CREATED_ON`,`DISCOUNT`,`IS_DISCOUNT`,`OFFER_ACTIVE_END`) values 
(1,88,360,6000,1,0,'2021-08-01',0,0,1),
(2,88,180,3000,1,0,'2021-07-16',0,0,0),
(3,88,14,1500,1,0,'2021-07-09',0,0,0);

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `POST_SUBCATAGORY` int(11) DEFAULT NULL,
  `POST_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `POST_CATEGORY` int(11) NOT NULL,
  `ARTICLE` int(11) NOT NULL,
  `ACTIVE` tinyint(1) DEFAULT NULL,
  `BUDGET` int(11) DEFAULT NULL,
  `CURRENCY` int(11) NOT NULL,
  PRIMARY KEY (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `post` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY` int(60) DEFAULT NULL,
  `BRAND` int(30) DEFAULT NULL,
  `SHOP_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `AVATAR` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PRICE` float DEFAULT NULL,
  `QUANTITY` int(10) DEFAULT NULL,
  `REVIEW` varchar(156) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(5000) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `BARCODE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `STOCK` int(6) DEFAULT NULL,
  `SELLING_PRICE` float DEFAULT NULL,
  `COST_PRICE` float DEFAULT NULL,
  `OLD_PRICE` float DEFAULT NULL,
  `OFFER_PERCENT` int(5) DEFAULT NULL,
  `OFFER_FROM` datetime DEFAULT NULL,
  `OFFER_TO` datetime DEFAULT NULL,
  `OFFER_ACTIVE_IND` tinyint(1) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `GST_AMOUNT` float DEFAULT NULL,
  `MEASUREMENT` int(10) DEFAULT NULL,
  `DELIVERY_CHARGE` float DEFAULT NULL,
  `GST_PERCENT` int(11) NOT NULL DEFAULT '0',
  `CART_ID` int(3) NOT NULL DEFAULT '0',
  `DISCOUNT` float DEFAULT NULL,
  `DATE_OF_MANUFACTURING` datetime DEFAULT NULL,
  `DATE_OF_EXPIRE` datetime DEFAULT NULL,
  `LONGITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LATITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `OUT_OF_STOCK` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `product` */

insert  into `product`(`ID`,`NAME`,`CATEGORY`,`BRAND`,`SHOP_ID`,`AVATAR`,`PRICE`,`QUANTITY`,`REVIEW`,`DESCRIPTION`,`IS_ACTIVE`,`IS_DELETED`,`BARCODE`,`STOCK`,`SELLING_PRICE`,`COST_PRICE`,`OLD_PRICE`,`OFFER_PERCENT`,`OFFER_FROM`,`OFFER_TO`,`OFFER_ACTIVE_IND`,`CREATED_ON`,`GST_AMOUNT`,`MEASUREMENT`,`DELIVERY_CHARGE`,`GST_PERCENT`,`CART_ID`,`DISCOUNT`,`DATE_OF_MANUFACTURING`,`DATE_OF_EXPIRE`,`LONGITUDE`,`LATITUDE`,`SHOP_NAME`,`OUT_OF_STOCK`) values 
(0,'SUGAR',1,1,'MILAAN61',NULL,88,1,NULL,'Desi',1,0,NULL,746,88,40,88,0,'2021-08-23 00:00:00','2021-08-31 00:00:00',0,'2021-08-17 00:00:00',8.8,16,0,10,0,0,'2021-06-01 00:00:00','2022-03-31 00:00:00','77.3764416','28.871464','Grocery store',0),
(2,'WATER HEATER',2,2,'MILAAN61',NULL,1000,1,NULL,'Good',1,0,'Abcd16372',2384,1000,800,1000,0,'2021-08-17 00:00:00','2021-09-30 00:00:00',0,'2021-08-17 00:00:00',80,15,0,8,0,0,'2017-08-17 00:00:00','2025-08-17 00:00:00','77.3764416','28.871464','Grocery store',0),
(3,'MONICIPLE',3,3,'MILAAN42',NULL,102,1,NULL,'DONN\'T USE WITHOUGHT DOCTOR ADVICE',1,0,NULL,144,102,49,102,0,'2021-08-17 00:00:00','2021-08-17 00:00:00',0,'2021-08-17 00:00:00',13,15,0,12,0,0,'2021-01-10 00:00:00','2023-07-17 00:00:00','86.592817','25.880303','ALIBABA MEDICAL STORE',0),
(6,'WATER CAMPER',5,5,'MILAAN61',NULL,441,10,NULL,'10 ltr',1,0,'Aghj',4987,490,400,490,10,'2021-08-24 00:00:00','2021-08-31 00:00:00',1,'2021-08-18 15:32:16',13.23,15,0,3,0,49,NULL,NULL,'77.3764416','28.871464','Grocery store',0),
(7,'DIARY',6,6,'MILAAN61',NULL,23.75,5,NULL,'Good',1,0,'Abcd1234',185,25,20,25,5,'2021-08-25 00:00:00','2021-09-11 00:00:00',1,'2021-08-18 15:36:18',0.2375,15,0,1,0,1.25,NULL,NULL,'77.3764416','28.871464','Grocery store',0),
(8,'SUGAR CUBE',1,1,'MILAAN61',NULL,50,1,NULL,'cubes of sugar with fewer calories',1,0,'cube',88,50,50,50,0,'2021-08-18 00:00:00','2021-08-21 00:00:00',0,'2021-08-18 20:44:16',0,16,0,0,0,0,'2021-08-19 00:00:00','2021-08-21 00:00:00','77.3764416','28.871464','Grocery store',0),
(9,'TEST SUGAR',1,1,'MILAAN61',NULL,70,1,NULL,'Testing',1,0,'Sugar',97,70,50,70,0,'2021-08-18 00:00:00','2021-08-21 00:00:00',0,'2021-08-18 21:06:45',12,15,0,16,0,0,'2021-08-01 00:00:00','2021-08-31 00:00:00','77.3764416','28.871464','Grocery store',0),
(10,'SUGAR3',1,1,'MILAAN61',NULL,69,1,NULL,'Sugar',1,0,'Sugar',9550,69,50,69,0,'2021-08-18 00:00:00','2021-08-28 00:00:00',0,'2021-08-18 21:39:58',0.69,16,0,1,0,0,'2021-08-01 00:00:00','2021-08-31 00:00:00','77.3764416','28.871464','Grocery store',0),
(11,'SUGAR4',1,1,'MILAAN61',NULL,66,1,NULL,'sugar',1,0,'sugar4',118,66,55,66,0,'2021-08-18 00:00:00','2021-08-25 00:00:00',0,'2021-08-18 21:46:56',0,16,0,0,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(12,'SUGAR7',1,1,'MILAAN61',NULL,99,1,NULL,'ss',1,0,'sss',111,99,55,99,0,'2021-08-18 00:00:00','2021-08-21 00:00:00',0,'2021-08-18 22:00:10',1,16,0,1,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(13,'SUGAR',1,7,'MILAAN61',NULL,65.34,1,NULL,'ss',1,0,'sssbb',1109,66,44,66,1,'2021-08-24 00:00:00','2021-09-11 00:00:00',1,'2021-08-18 22:08:46',10.4544,16,0,16,0,0.66,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(14,'SUGAR2',1,7,'MILAAN61',NULL,77,1,NULL,'sugar',1,0,'sugar',108,77,55,77,0,'2021-08-18 00:00:00','2021-08-21 00:00:00',0,'2021-08-18 23:50:21',13,16,0,16,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(15,'SUGAR10',1,7,'MILAAN61',NULL,77,1,NULL,'ss',1,0,'ssb',1108,77,66,77,0,'2021-08-19 00:00:00','2021-09-10 00:00:00',0,'2021-08-19 00:07:54',0,16,0,0,0,0,'2021-08-02 00:00:00','2021-08-21 00:00:00','77.3764416','28.871464','Grocery store',0),
(16,'SUGAR11',1,1,'MILAAN61',NULL,81,1,NULL,'test salt11',1,0,'salt11',219,90,33,90,10,'2021-08-23 00:00:00','2021-08-31 00:00:00',1,'2021-08-19 00:14:40',8.1,16,0,10,0,9,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(17,'SUGAR POWDER',1,7,'MILAAN61',NULL,70,1,NULL,'Powder',1,0,'Sb',8,70,60,70,0,'2021-08-19 00:00:00','2021-08-28 00:00:00',0,'2021-08-19 09:47:58',11.2,16,0,16,0,0,'2021-08-01 00:00:00','2021-08-31 00:00:00','77.3764416','28.871464','Grocery store',0),
(18,'RICE',8,8,'MILAAN61',NULL,1275,1,NULL,'Good quality.',1,0,'Asdf1234',959,1275,1200,1275,0,'2021-08-19 00:00:00','2021-08-28 00:00:00',0,'2021-08-19 11:06:12',229.5,16,0,18,0,0,NULL,NULL,'77.3764416','28.871464','Grocery store',0),
(19,'RICE1',7,9,'MILAAN61',NULL,1401,1,NULL,'Good quality.',1,0,'Asdf1234',72,1401,1200,1401,0,'2021-08-19 00:00:00','2021-08-28 00:00:00',0,'2021-08-19 11:13:55',252.18,16,0,18,0,0,NULL,NULL,'77.3764416','28.871464','Grocery store',0),
(20,'VISHAL SUGAR',1,7,'MILAAN61',NULL,80.75,5,NULL,'Good',1,0,'Abcd1234',797,85,100,85,5,'2021-08-23 00:00:00','2021-08-31 00:00:00',1,'2021-08-19 11:16:59',4.0375,16,0,5,0,4.25,'2020-08-20 00:00:00','2024-08-19 00:00:00','77.3764416','28.871464','Grocery store',0),
(21,'1.5 TON AC',9,11,'MILAAN98',NULL,40500,1,NULL,'An air conditioner is a system that is used to cool down a space by removing heat from the space and moving it to some outside area. The cool air can then be moved throughout a building through ventilation. ... Air conditioners act similarly to a heat pump, but instead follow a cooling cycle',1,0,'Abcd1234',1096,45000,39000,45000,10,'2021-08-25 00:00:00','2022-08-28 00:00:00',1,'2021-08-20 17:41:34',7290,15,0,18,0,4500,'2021-08-01 00:00:00','2025-08-28 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(22,'SMART TV',10,12,'MILAAN98',NULL,36050,1,NULL,' smart TV, also known as a connected TV (CTV), is a traditional television set with integrated Internet and interactive Web 2.0 features, which allows users to stream music and videos, browse the internet, and view photos. ... Smart TV should not be confused with Internet TV, IPTV, or streaming television',1,0,'Abxd1234',1120,36050,29000,36050,0,'2021-08-20 00:00:00','2022-08-26 00:00:00',0,'2021-08-20 17:45:45',6489,15,0,18,0,0,'2020-08-21 00:00:00','2023-08-24 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(23,'DOUBLE DOOR REFRIGERATOR ',11,13,'MILAAN98',NULL,69300,1,NULL,'What is a refrigerator? A refrigerator is one of our most valuable household appliances. It keeps food and drinks cool, by pushing a liquid refrigerant through a sealed system, which causes it to vaporize, and draw heat out of the fridge',1,0,'Abcd123456',11526,77000,55000,77000,10,'2021-08-25 00:00:00','2023-08-26 00:00:00',1,'2021-08-20 17:49:45',12474,15,0,18,0,7700,'2021-08-20 00:00:00','2024-08-30 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(24,'NECKLACE',12,14,'MILAAN1112',NULL,1800,1,NULL,'Nice look',1,0,'12345ashs',100,1800,1200,1800,0,'2021-08-04 00:00:00','2021-07-01 00:00:00',0,'2021-08-20 20:51:26',144,15,0,8,0,0,'2018-08-20 00:00:00','2045-08-20 00:00:00','77.3764464','28.8714656','Aditya General Store',0),
(25,'ODOMOS GEL',13,15,'MILAAN1112',NULL,74.25,1,NULL,'Good for rainy season.',1,0,'Aga12337',134,75,60,75,1,'2021-08-21 00:00:00','2021-09-30 00:00:00',1,'2021-08-20 21:00:48',2.2275,15,0,3,0,0.75,'2021-05-01 00:00:00','2025-08-31 00:00:00','77.3764464','28.8714656','Aditya General Store',0),
(26,'ODOMOS GEL +',13,15,'MILAAN1112',NULL,74.25,1,NULL,'100g',1,0,'Ahah12334',153,75,60,75,1,'2021-08-21 00:00:00','2021-10-31 00:00:00',1,'2021-08-20 21:06:25',2.2275,15,0,3,0,0.75,'2021-08-21 00:00:00','2021-10-31 00:00:00','77.3764464','28.8714656','Aditya General Store',0),
(27,'EARRINGS ',14,16,'MILAAN711',NULL,5880,12,NULL,'23 carot weight 2g',1,0,'Akh1981',499,6000,5000,6000,2,'2021-08-20 00:00:00','2021-08-31 00:00:00',1,'2021-08-20 21:24:10',294,15,0,5,0,120,'2021-08-11 00:00:00','2021-08-31 00:00:00','84.1865353','24.9236824','निहार फैशन',0),
(28,'Parasetamol',15,17,'MILAAN49',NULL,8,500,NULL,'Good',1,0,'2345',1000,8,5,0,0,NULL,NULL,0,'2021-08-20 21:27:55',1,15,0,1,0,0,NULL,NULL,'85.1558776','25.59180872','Prince medical services ',0),
(29,'PEN',16,18,'MILAAN711',NULL,15,24,NULL,'Smooth writing ',1,0,'Ak123',98,15,11,15,0,'2021-08-11 00:00:00','2021-08-29 00:00:00',0,'2021-08-20 21:29:16',1,15,0,6,0,0,'2021-08-13 00:00:00','2021-08-30 00:00:00','84.1865353','24.9236824','निहार फैशन',0),
(30,'Aclock',15,17,'MILAAN49',NULL,3,300,NULL,'Good ',1,0,'345',2000,3,5,0,0,NULL,NULL,0,'2021-08-20 21:30:32',1,15,0,2,0,0,NULL,NULL,'85.1558776','25.59180872','Prince medical services ',0),
(31,'Becosules',15,19,'MILAAN49',NULL,7.84,3000,NULL,'Good ',1,0,'244',5000,8,3,8,2,'2021-08-21 00:00:00','2021-08-31 00:00:00',1,'2021-08-20 21:36:56',0.0784,15,0,1,0,0.16,NULL,NULL,'85.1558776','25.59180872','Prince medical services ',0),
(32,'AC',9,10,'MILAAN98',NULL,12000,1,NULL,'Good',1,0,'Abcd1234',292,12000,10000,12000,0,'2021-08-11 00:00:00','2021-08-28 00:00:00',0,'2021-08-21 17:43:08',600,15,0,5,0,0,'2021-08-20 00:00:00','2021-08-28 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(33,'SUGAR 5',1,1,'MILAAN61',NULL,65,1,NULL,'sugar granules',1,0,'sugar',10006,65,40,65,0,NULL,NULL,0,'2021-08-23 16:25:49',10.4,16,0,16,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(34,'SUGAR88',1,7,'MILAAN61',NULL,99,1,NULL,'sugar',1,0,'sugar',6,99,88,0,0,NULL,NULL,0,'2021-08-24 01:19:24',16,16,0,16,0,0,'2021-08-02 00:00:00','2021-09-08 00:00:00','77.3764416','28.871464','Grocery store',0),
(35,'RICE DEMO',8,8,'MILAAN61',NULL,88,1,NULL,'rice',1,0,'rice',7,88,55,0,0,NULL,NULL,0,'2021-08-24 02:06:57',15,16,0,16,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',0),
(36,'AC SONY',9,10,'MILAAN98',NULL,45000,1,NULL,'ac 2 ton',1,0,'ac',594,45000,40000,45000,0,NULL,NULL,0,'2021-08-24 11:56:24',7200,15,0,16,0,0,'2021-08-01 00:00:00','2021-09-04 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(37,'LG TV',10,20,'MILAAN98',NULL,14400,1,NULL,'Good product.',1,0,'LGTV',42,16000,15000,16000,10,'2021-08-25 00:00:00','2021-08-31 00:00:00',1,'2021-08-25 19:54:25',1440,15,0,10,0,1600,'2021-08-25 00:00:00','2022-08-24 00:00:00','84.9838522','24.7809382','Hari Om Electronics',0),
(38,'SUGAR FREE',1,1,'MILAAN61',NULL,44.5,1,NULL,'acdcsdc',1,0,'dsacdcddc',100,50,30,50,11,'2021-08-27 00:00:00','2021-09-04 00:00:00',1,'2021-08-26 17:49:24',4.895,16,0,11,0,5.5,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',4),
(39,'SUGAR2 RAJDHANI',1,1,'MILAAN61',NULL,150,1,NULL,NULL,1,0,NULL,111,150,100,0,0,NULL,NULL,0,'2021-08-26 17:54:59',2,16,0,1,0,0,'2021-08-01 00:00:00','2021-09-09 00:00:00','77.3764416','28.871464','Grocery store',11),
(40,'ELECTRONIC',2,2,'MILAAN61',NULL,60,1,NULL,'fff',1,0,'ghhh',222,60,55,0,0,NULL,NULL,0,'2021-08-26 17:59:56',0,16,0,0,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',15),
(41,'SUGAR33',1,7,'MILAAN61',NULL,65,1,NULL,NULL,1,0,NULL,1111,65,55,0,0,NULL,NULL,0,'2021-08-26 18:27:16',0,16,0,0,0,0,'2021-08-01 00:00:00','2021-09-11 00:00:00','77.3764416','28.871464','Grocery store',11),
(42,'Switch',22,25,'MILAAN98',NULL,30,1,NULL,'Good product.',1,0,'gjkgjhgujh678658',100,30,20,30,0,'2021-08-26 00:00:00','2021-08-27 00:00:00',0,'2021-08-26 18:55:49',3,15,0,10,0,0,'2019-06-01 00:00:00','2023-08-30 00:00:00','84.9838522','24.7809382','Hari Om Electronics',20),
(43,'AATA 5 KG',23,27,'MILAAN61',NULL,30,1,NULL,'daily use aata. This document is an electronic record in terms of Information Technology Act, 2000 and rules there under as applicable and the amended provisions pertaining to electronic records in various statutes as amended by the Information Technology Act, 2000. This electronic record is generated by a computer system and does not require any physical or digital signatures.\n',1,0,'aata123',111,30,22,0,0,NULL,NULL,0,'2021-08-26 21:30:39',0,16,0,0,0,0,'2021-08-01 00:00:00','2021-09-10 00:00:00','77.3764416','28.871464','Grocery store',12),
(44,'Vishal',24,28,'MILAAN98',NULL,600,1,NULL,'Well deserved ',1,0,'Abcd12345',25,600,500,0,0,NULL,NULL,0,'2021-08-27 12:31:56',108,15,0,18,0,0,'2021-08-28 00:00:00','2022-02-28 00:00:00','84.9838522','24.7809382','Hari Om Electronics',15),
(45,'Adidas shoe   size 9',25,29,'MILAAN1112',NULL,1188,1,NULL,'Nice fitting',1,0,'Sfhuy1224',100,1200,1000,1200,1,'2021-08-27 00:00:00','2021-08-31 00:00:00',1,'2021-08-27 23:19:09',95.04,15,0,8,0,12,'2021-08-01 00:00:00','2021-10-01 00:00:00','77.3764464','28.8714656','Aditya General Store',40);

/*Table structure for table `product_list` */

DROP TABLE IF EXISTS `product_list`;

CREATE TABLE `product_list` (
  `Id` int(3) NOT NULL AUTO_INCREMENT,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `PRODUCT_QUANTITY` int(11) NOT NULL DEFAULT '0',
  `PRICE` float NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `PRODUCT_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `OFFER` int(5) NOT NULL DEFAULT '0',
  `CART_ID` int(11) NOT NULL DEFAULT '0',
  `OFFERS_AVAILABLE` tinyint(1) DEFAULT NULL,
  `OLD_PRICE` float DEFAULT NULL,
  `DISCOUNT` float NOT NULL DEFAULT '0',
  `DELIVERY_CHARGE` float NOT NULL DEFAULT '0',
  `GST_AMOUNT` float NOT NULL DEFAULT '0',
  `OFFER_TO` datetime DEFAULT NULL,
  `OFFER_FROM` datetime DEFAULT NULL,
  `MEASUREMENT` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `product_list` */

insert  into `product_list`(`Id`,`SHOP_ID`,`USER_ID`,`PRODUCT_NAME`,`CREATED_ON`,`PRODUCT_QUANTITY`,`PRICE`,`IS_ACTIVE`,`IS_DELETED`,`PRODUCT_ID`,`OFFER`,`CART_ID`,`OFFERS_AVAILABLE`,`OLD_PRICE`,`DISCOUNT`,`DELIVERY_CHARGE`,`GST_AMOUNT`,`OFFER_TO`,`OFFER_FROM`,`MEASUREMENT`) values 
(12,'MILAAN98','14','1.5 TON AC','2021-08-26 16:18:20',1,40500,1,0,'21',10,2,1,45000,4500,0,7290,'2022-08-28 00:00:00','2021-08-25 00:00:00',15),
(14,'MILAAN98','14','DOUBLE DOOR REFRIGERATOR ','2021-08-26 16:18:24',3,207900,1,0,'23',10,2,1,231000,23100,0,37422,'2023-08-26 00:00:00','2021-08-25 00:00:00',15),
(15,'MILAAN98','14','SMART TV','2021-08-26 19:00:35',4,144200,1,0,'22',0,3,0,108150,0,0,25956,NULL,NULL,15),
(16,'MILAAN98','14','1.5 TON AC','2021-08-26 19:00:36',1,40500,1,0,'21',10,3,1,45000,4500,0,7290,'2022-08-28 00:00:00','2021-08-25 00:00:00',15),
(17,'MILAAN98','14','AC','2021-08-26 19:00:39',4,48000,1,0,'32',0,3,0,36000,0,0,2400,NULL,NULL,15),
(18,'MILAAN98','14','DOUBLE DOOR REFRIGERATOR ','2021-08-26 19:00:41',4,277200,1,0,'23',10,3,1,308000,30800,0,49896,'2023-08-26 00:00:00','2021-08-25 00:00:00',15),
(19,'MILAAN98','4','1.5 TON AC','2021-08-26 22:59:51',2,81000,1,0,'21',10,4,1,90000,9000,0,14580,'2022-08-28 00:00:00','2021-08-25 00:00:00',15),
(23,'MILAAN98','4','SMART TV','2021-08-27 01:26:29',1,36050,1,0,'22',0,4,0,0,0,0,6489,NULL,NULL,15),
(24,'MILAAN1112','4','NECKLACE','2021-08-27 01:26:31',1,1800,1,0,'24',0,5,0,0,0,0,144,NULL,NULL,15),
(26,'MILAAN98','4','LG TV','2021-08-27 12:22:45',1,14400,1,0,'37',10,6,1,16000,1600,0,1440,'2021-08-31 00:00:00','2021-08-25 00:00:00',15),
(27,'MILAAN98','7','AC','2021-08-28 12:28:19',1,12000,1,0,'32',0,7,0,0,0,0,600,NULL,NULL,15),
(28,'MILAAN98','7','1.5 TON AC','2021-08-28 12:29:25',1,40500,1,0,'21',10,7,1,45000,4500,0,7290,'2022-08-28 00:00:00','2021-08-25 00:00:00',15),
(29,'MILAAN98','7','AC SONY','2021-08-28 12:30:05',1,45000,1,0,'36',0,7,0,0,0,0,7200,NULL,NULL,15),
(30,'MILAAN98','7','SMART TV','2021-08-28 12:30:50',1,36050,1,0,'22',0,7,0,0,0,0,6489,NULL,NULL,15),
(31,'MILAAN98','7','SUGAR 5','2021-08-28 12:31:26',1,65,1,0,'33',0,7,0,0,0,0,10.4,NULL,NULL,16),
(32,'MILAAN98','6','SUGAR 5','2021-08-28 14:09:41',1,65,1,0,'33',0,8,0,0,0,0,10.4,NULL,NULL,16),
(33,'MILAAN98','7','DOUBLE DOOR REFRIGERATOR ','2021-08-28 15:50:42',1,69300,1,0,'23',10,7,1,77000,7700,0,12474,'2023-08-26 00:00:00','2021-08-25 00:00:00',15);

/*Table structure for table `profile` */

DROP TABLE IF EXISTS `profile`;

CREATE TABLE `profile` (
  `PROFILE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `CATEGORY` int(11) NOT NULL,
  `ARTICLE_ID` int(11) NOT NULL,
  `BACKGROUND_EXPERTISE` varchar(500) DEFAULT NULL,
  `SUMMERY_DETAILS` varchar(500) DEFAULT NULL,
  `SUB_CATAGORY` varchar(50) DEFAULT NULL,
  `SERVED_CONSULTATION` int(10) DEFAULT NULL,
  `CONSULTATION_PRICE` int(10) DEFAULT NULL,
  `TOTAL_EARNINGS` int(10) DEFAULT NULL,
  `AVG_RATING` int(10) DEFAULT NULL,
  `AVAILIBILITY` int(10) DEFAULT NULL,
  `AVG_RESPONSE_TIME` int(10) DEFAULT NULL,
  `DISPLAY_NAME` varchar(50) DEFAULT NULL,
  `LANGUAGE_PRIMARY` int(2) NOT NULL DEFAULT '0',
  `LANGUAGE_SECONDARY` int(2) NOT NULL DEFAULT '0',
  `KEYWORD` varchar(200) NOT NULL DEFAULT 'NA',
  `TWITTER_HANDLE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PROFILE_ID`,`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `profile` */

insert  into `profile`(`PROFILE_ID`,`USER_ID`,`CATEGORY`,`ARTICLE_ID`,`BACKGROUND_EXPERTISE`,`SUMMERY_DETAILS`,`SUB_CATAGORY`,`SERVED_CONSULTATION`,`CONSULTATION_PRICE`,`TOTAL_EARNINGS`,`AVG_RATING`,`AVAILIBILITY`,`AVG_RESPONSE_TIME`,`DISPLAY_NAME`,`LANGUAGE_PRIMARY`,`LANGUAGE_SECONDARY`,`KEYWORD`,`TWITTER_HANDLE`) values 
(1,1,12,43,'IT cloud computing and infrastructure',' cloud , Google cloud. architect, cms',NULL,0,30,0,0,43,0,'Kumar Avinash',46,2,'','jaykrs'),
(2,3,13,2,'IT marketing analyst','I have been working on different IT service and product marketing startgies from last 10 year. worked on marketing analysis of various domain from treditional legacy system to modern architectuere. Passionate to learn new ideas on marketing stratgities.','2',0,6,0,0,43,0,'Jatin Consultant',46,47,'',''),
(3,7,12,43,'IT','Per the HTML specification, the default style for <summary> elements includes display: list-item. This makes it possible to change or remove the icon displayed as the disclosure widget next to the label from the default, which is typically a triangle.You can also change the style to display: block to remove the disclosure triangle.See the Browser compatibility section for details, as not all browsers support full functionality of this element yet.',NULL,0,5,0,0,43,0,'Omkar Consultant',46,47,'',NULL),
(4,5,13,123,'cloud , Google cloud. architect, Manager','cloud , Google cloud. architect, Manager','2',0,5,0,0,43,0,'Javed Ahmad Khan',46,47,'','Thoughtconstru1'),
(5,4,22,43,'today is sunday','today is sunday summery','21',0,10,0,0,43,0,'Vikash Kumar',46,48,'',NULL),
(9,26,13,43,'React-Native, Java, Oracle, Mysql','Fresher',NULL,0,10,0,0,53,0,NULL,0,0,'NA',NULL),
(10,48,14,43,'Businesses law','Businesses and law',NULL,0,5,0,0,53,0,NULL,0,0,'NA','Turkialhussini1'),
(11,2,13,43,'Ui/server','Bca/Mca',NULL,0,5,0,0,53,0,NULL,0,0,'NA',NULL),
(12,51,13,43,'React Native ','BCA',NULL,0,5,0,0,53,0,'Kumar  Avinash ',46,0,'NA',NULL),
(13,47,24,0,NULL,NULL,NULL,0,0,0,0,44,0,NULL,48,0,'NA',NULL),
(14,66,15,0,NULL,NULL,NULL,0,0,0,0,53,0,'Kumar Vijay',46,0,'NA',NULL);

/*Table structure for table `purchase` */

DROP TABLE IF EXISTS `purchase`;

CREATE TABLE `purchase` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADMIN_ID` int(50) NOT NULL DEFAULT '0',
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PLAN_TYPE` int(10) NOT NULL DEFAULT '0',
  `EXPIRY_DATE` datetime DEFAULT NULL,
  `PLAN_VALIDITY` int(10) NOT NULL DEFAULT '0',
  `TRANSACTION_ID` int(10) NOT NULL DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `PLAN_ID` int(10) NOT NULL DEFAULT '0',
  `PLAN_AMOUNT` float NOT NULL DEFAULT '0',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `PAYMENT_MODE` int(12) NOT NULL DEFAULT '0',
  `IS_DISCOUNT` tinyint(1) NOT NULL DEFAULT '0',
  `DISCOUNT_PERCENT` int(10) NOT NULL DEFAULT '0',
  `DISCOUNT_AMOUNT` float NOT NULL DEFAULT '0',
  `DISCOUNT_TYPE` varchar(50) COLLATE utf8_bin DEFAULT '0',
  `GST_PERCENT` int(10) NOT NULL DEFAULT '0',
  `GST_AMOUNT` float NOT NULL DEFAULT '0',
  `TOTAL_AMOUNT` float NOT NULL DEFAULT '0',
  `PAYABLE_AMOUNT` int(10) NOT NULL DEFAULT '0',
  `AMOUNT` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `purchase` */

insert  into `purchase`(`ID`,`ADMIN_ID`,`SHOP_ID`,`PLAN_TYPE`,`EXPIRY_DATE`,`PLAN_VALIDITY`,`TRANSACTION_ID`,`CREATED_ON`,`PLAN_ID`,`PLAN_AMOUNT`,`IS_DELETED`,`IS_ACTIVE`,`PAYMENT_MODE`,`IS_DISCOUNT`,`DISCOUNT_PERCENT`,`DISCOUNT_AMOUNT`,`DISCOUNT_TYPE`,`GST_PERCENT`,`GST_AMOUNT`,`TOTAL_AMOUNT`,`PAYABLE_AMOUNT`,`AMOUNT`) values 
(1,1,'MILAAN61',88,'2021-08-27 11:34:03',360,15,'2021-08-27 11:34:03',1,6000,0,0,14,0,0,0,'0',0,0,0,0,7080),
(2,1,'MILAAN61',88,'2021-08-27 11:34:45',360,16,'2021-08-27 11:34:45',1,6000,0,0,14,0,0,0,'0',0,0,0,0,7080),
(3,1,'MILAAN671',88,'2021-08-27 11:36:51',360,17,'2021-08-27 11:36:51',1,6000,0,0,14,0,0,0,'0',0,0,0,0,7080),
(4,1,'MILAAN420',88,'2021-08-27 11:46:49',360,18,'2021-08-27 11:46:49',1,6000,0,0,14,0,0,0,'0',18,1080,7080,8160,7080),
(5,1,'MILAAN420',88,'2021-08-27 11:49:55',360,19,'2021-08-27 11:49:55',1,6000,0,0,14,0,0,0,'0',18,1080,7080,8160,7080),
(6,1,'MILAAN490',88,'2021-08-27 11:54:13',360,20,'2021-08-27 11:54:13',1,6000,0,0,14,0,0,708,'0',18,1080,7080,8160,7080),
(7,1,'MILAAN490',88,'2021-08-27 12:01:36',360,21,'2021-08-27 12:01:36',1,6000,0,0,14,0,0,600,'0',18,972,6372,7344,5400),
(8,1,'MILAAN490',88,'2021-08-27 12:05:22',360,22,'2021-08-27 12:05:22',1,6000,0,0,14,0,10,600,'0',18,972,6372,6372,5400),
(9,1,'MILAAN61',88,'2021-08-28 17:40:35',360,35,'2021-08-28 17:40:35',1,6000,0,0,14,0,0,0,'',18,1080,7080,7080,0),
(10,1,'MILAAN61',88,'2021-08-28 17:46:40',360,37,'2021-08-28 17:46:40',1,6000,0,0,14,0,0,0,NULL,18,1080,7080,7080,0),
(11,1,'MILAAN61',88,'2021-08-28 17:56:25',180,38,'2021-08-28 17:56:25',2,3000,0,1,14,0,0,0,'',18,540,3540,3540,0),
(12,8,'MILAAN98',88,'2021-08-28 17:57:50',180,39,'2021-08-28 17:57:50',2,3000,0,1,14,0,0,0,'',18,540,3540,3540,0);

/*Table structure for table `review` */

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `COMMENT` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `CART_ID` int(11) DEFAULT NULL,
  `REVIEW_TYPE` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `review` */

insert  into `review`(`ID`,`PRODUCT_ID`,`USER_ID`,`SHOP_ID`,`COMMENT`,`NAME`,`IS_DELETED`,`IS_ACTIVE`,`CREATED_ON`,`CART_ID`,`REVIEW_TYPE`) values 
(1,'MILAAN61','1','MILAAN61','Didn\'t received bhhbbbhhhhb hhhhjj hhvvc vbbbb vvvgbj ghjj ghjjn cfhjjfd gdsss jkkkjhg vcxxx vhhhj gfd','Sanat Kumar',0,1,'2021-07-08',1,81),
(2,'MILAAN611','4','MILAAN611','God service ','Akhilesh Kumar ',0,1,'2021-07-10',6,81),
(3,'MILAAN527','9','MILAAN527','Very good service ','Vishal',0,1,'2021-07-23',13,81),
(4,'MILAAN447','12','MILAAN447','Good','Ashu',0,1,'2021-08-05',33,81),
(5,'MILAAN441','1','MILAAN441',' ','Sanat Kumar',0,1,'2021-08-12',5,81),
(6,'MILAAN456','16','MILAAN456','Good','Vishal',0,1,'2021-08-12',14,81),
(7,'MILAAN98','4','MILAAN98','Very fast service','Dirk 1st',0,1,'2021-08-20',5,81),
(8,'MILAAN1112','4','MILAAN1112','Agdjfn fjfjfjg gjcj FG jgh fjgjdjri FG jdjdk Hf FG kxj FG kfkfkfkfk DJ kdk FG k FG k SD k DJ k DJ kdkxk SD k SD','Dirk 1st',0,1,'2021-08-20',7,81);

/*Table structure for table `salary` */

DROP TABLE IF EXISTS `salary`;

CREATE TABLE `salary` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADVANCE_SALARY` int(11) DEFAULT NULL,
  `TOTAL_SALARY` int(11) DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SALARY` int(11) DEFAULT NULL,
  `DEDUCTION` int(11) DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `salary` */

/*Table structure for table `shop_image` */

DROP TABLE IF EXISTS `shop_image`;

CREATE TABLE `shop_image` (
  `ID` int(30) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(30) DEFAULT '0',
  `SHOP_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `AVATAR_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `shop_image` */

insert  into `shop_image`(`ID`,`PRODUCT_ID`,`SHOP_ID`,`AVATAR_NAME`,`IS_ACTIVE`,`IS_DELETED`,`CREATED_ON`) values 
(1,1,'MILAAN61','1_1_MILAAN61_shop.png',1,0,'2021-08-17'),
(2,1,'MILAAN61','1_2_MILAAN61_shop.png',1,0,'2021-08-17'),
(3,1,'MILAAN61','1_3_MILAAN61_shop.png',1,0,'2021-08-17'),
(4,1,'MILAAN61','1_4_MILAAN61_shop.png',1,0,'2021-08-17'),
(5,2,'MILAAN42','2_1_MILAAN42_shop.png',1,0,'2021-08-17'),
(6,2,'MILAAN42','2_2_MILAAN42_shop.png',1,0,'2021-08-17'),
(7,3,'MILAAN43','3_1_MILAAN43_shop.png',1,0,'2021-08-17'),
(8,5,'MILAAN95','5_1_MILAAN95_shop.png',1,0,'2021-08-17'),
(9,6,'MILAAN46','6_1_MILAAN46_shop.png',1,0,'2021-08-17'),
(10,7,'MILAAN47','7_1_MILAAN47_shop.png',1,0,'2021-08-17'),
(11,8,'MILAAN98','8_1_MILAAN98_shop.png',1,0,'2021-08-20'),
(12,8,'MILAAN98','8_2_MILAAN98_shop.png',1,0,'2021-08-20'),
(13,9,'MILAAN49','9_1_MILAAN49_shop.png',1,0,'2021-08-20'),
(14,10,'MILAAN910','10_1_MILAAN910_shop.png',1,0,'2021-08-20'),
(15,10,'MILAAN910','10_2_MILAAN910_shop.png',1,0,'2021-08-20'),
(16,11,'MILAAN711','11_1_MILAAN711_shop.png',1,0,'2021-08-20'),
(17,11,'MILAAN711','11_2_MILAAN711_shop.png',1,0,'2021-08-20'),
(18,12,'MILAAN1112','12_1_MILAAN1112_shop.png',1,0,'2021-08-20'),
(19,12,'MILAAN1112','12_2_MILAAN1112_shop.png',1,0,'2021-08-20'),
(20,13,'MILAAN413','13_1_MILAAN413_shop.png',1,0,'2021-08-21'),
(21,14,'MILAAN414','14_1_MILAAN414_shop.png',1,0,'2021-08-21'),
(22,14,'MILAAN414','14_2_MILAAN414_shop.png',1,0,'2021-08-21'),
(23,15,'MILAAN515','15_1_MILAAN515_shop.png',1,0,'2021-08-26'),
(24,18,'MILAAN818','18_1_MILAAN818_shop.png',1,0,'2021-08-26'),
(25,19,'MILAAN1219','19_1_MILAAN1219_shop.png',1,0,'2021-08-26'),
(26,20,'MILAAN620','20_1_MILAAN620_shop.png',1,0,'2021-08-26'),
(27,21,'MILAAN921','21_1_MILAAN921_shop.png',1,0,'2021-08-26');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `TOTAL_AMOUNT` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `test` */

insert  into `test`(`ID`,`USER_ID`,`SHOP_ID`,`TOTAL_AMOUNT`) values 
(1,'1','Avi23',0),
(2,'1','Avi23',0),
(3,'1','Avi23',0);

/*Table structure for table `timeline` */

DROP TABLE IF EXISTS `timeline`;

CREATE TABLE `timeline` (
  `TIMELINE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `CONSULTANT_ID` int(11) DEFAULT NULL,
  `Q_ARTICLE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `A_ARTICLE` text COLLATE utf8_bin,
  `LIKE_COUNT` int(4) unsigned zerofill DEFAULT NULL,
  `DISLIKE_COUNT` int(4) unsigned zerofill DEFAULT NULL,
  `SHARE_URL` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LIKE_USER_ID` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `DISLIKE_USER_ID` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `ANSWER_STATUS` tinyint(1) DEFAULT NULL,
  `Q_TYPE` int(2) unsigned zerofill DEFAULT NULL,
  `CREATED_ON` date NOT NULL,
  `ANSWERED_ON` date DEFAULT NULL,
  `CATAGORY_ID` int(2) NOT NULL DEFAULT '0',
  `TWITTER_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TIMELINE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `timeline` */

insert  into `timeline`(`TIMELINE_ID`,`USER_ID`,`CONSULTANT_ID`,`Q_ARTICLE`,`A_ARTICLE`,`LIKE_COUNT`,`DISLIKE_COUNT`,`SHARE_URL`,`LIKE_USER_ID`,`DISLIKE_USER_ID`,`ANSWER_STATUS`,`Q_TYPE`,`CREATED_ON`,`ANSWERED_ON`,`CATAGORY_ID`,`TWITTER_ID`) values 
(1,0,2,'How to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate.',NULL,0000,0000,NULL,'50,2,47,51','26,51,47,57',0,54,'2019-11-26',NULL,12,NULL),
(3,2,2,'what is best possible way to reach Sofia in Bulgaria from London via train. My preferred way is either train or self drive car.',NULL,0000,0002,NULL,'50,51','47,1',0,54,'2019-11-26',NULL,12,NULL),
(4,2,2,'????? ????? ???? ???? ?????? ?? ??????? ?????? ?????? ??? ????? ?????. ?? ?????? ????? ???? ?? ??? ???????',NULL,0000,0001,NULL,'50,51','1',0,54,'2019-11-26',NULL,12,NULL),
(5,2,2,'Vous pourriez ne pas ?tre int?ress? par ce guide, si vous avez initialis? l\'application ? l\'aide du mod?le UI Kitten car il comprend d?j? Eva Icons. Quoi qu\'il en soit, si vous voulez savoir comment utiliser des packages d\'ic?nes tiers comme react-native-vector-icons, utilisez ce guide pour le faire fonctionner dans votre application.',NULL,0001,0001,NULL,'51,48,47','47',0,54,'2019-11-26',NULL,12,NULL),
(6,2,2,'Hola chicos, mi nombre es Avinash, un profesional de la inform?tica que est? explorando aplicaciones empresariales con dominio experto en la industria de fabricaci?n, ventas, banca, finanzas y servicios de medios.',NULL,0000,0001,NULL,'','51',0,54,'2019-12-17',NULL,12,NULL),
(7,3,3,'The supreme court of the Netherlands has ordered the government to reduce greenhouse gas emissions sharply. https://t.co/BCHkd7LpQd https://t.co/BCHkd7LpQd',NULL,0002,0001,NULL,'48,1','26',0,54,'2019-12-21',NULL,13,'1208366086145617920'),
(8,0,3,'RT @nytclimate: The supreme court of the Netherlands has ordered the government to reduce greenhouse gas emissions sharply. https://t.co/Xu?',NULL,0001,0001,NULL,'50','1',0,54,'2019-12-21',NULL,13,'1208365939164532741'),
(9,0,3,'RT @latimes: U.S. Steel to cut 1,545 Michigan jobs as weakness overwhelms Trump\'s protection https://t.co/CiLyNoFXpx',NULL,0002,0000,NULL,'50,1','',0,54,'2019-12-21',NULL,13,'1208365893052334081'),
(10,0,3,'Gazprom and Ukraine reached an agreement that will allow Russian gas to flow to Europe via its neighbor through the end of 2024 and settle all of the related legal disputes https://t.co/eRsVmzgZyU https://t.co/eRsVmzgZyU',NULL,0002,0001,NULL,'50,1','1',0,54,'2019-12-21',NULL,13,'1208365501728079872'),
(11,0,3,'RT @business: Gazprom and Ukraine reached an agreement that will allow Russian gas to flow to Europe via its neighbor through the end of 20?',NULL,0000,0000,NULL,'',NULL,0,54,'2019-12-21',NULL,13,'1208365374984445952'),
(12,0,3,'RT @ShekharGupta: India?s new Muslim flaunts the Tricolour on the steps of Jama Masjid, sings national anthem & isn?t afraid to look Muslim?',NULL,0000,0000,NULL,'',NULL,0,54,'2019-12-21',NULL,13,'1208365096474333184'),
(13,0,3,'India?s new Muslim flaunts the Tricolour on the steps of Jama Masjid, sings national anthem & isn?t afraid to look Muslim...\n\nAnd wouldn?t allow any majority to reimagine the Constitution?s republic..\n\nhttps://t.co/2ESYN2D1gw https://t.co/2ESYN2D1gw',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208365086634598400'),
(14,0,3,'RT @business: Amazon seeks out first Irish warehouse to fulfill orders currently shipped from the U.K. as Brexit deadline looms https://t.c?',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208364653379649539'),
(15,0,3,'\"How can you detain passengers in a bus, can you please explain it to me?\" Govind, a student on the bus said. \"We are in a fascist state.? \n\n@ahanpenkar reports. https://t.co/RXKHUy4Bzt https://t.co/RXKHUy4Bzt',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208364340040085504'),
(16,0,3,'CAA Protests: UP Police Detain, Threaten, Beat Up Activists, Journalist in Lucknow https://t.co/xt4vdb4Uru',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208364055477604352'),
(17,0,3,'RT @thecaravanindia: \"How can you detain passengers in a bus, can you please explain it to me?\" Govind, a student on the bus said. \"We are?',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208363796550447105'),
(18,0,3,'RT @the_hindu: At least 11 people, including an 8-year-old boy, have lost their lives in #UttarPradesh as the protests against the Citizens?',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208363730074865665'),
(19,0,3,'RT @LiveLawIndia: Internet Shut Down Explainer By Apar Gupta[Video]\n@apar1984 \n@internetfreedom \n#internetshutdown \n\nhttps://t.co/pSrbm74gUi',NULL,0001,0000,NULL,'1',NULL,0,54,'2019-12-21',NULL,13,'1208363576638894080'),
(20,0,3,'Legality Of Internet Shut Downs Explained By Apar Gupta[Video]\n\nhttps://t.co/skxiJr7TBb https://t.co/skxiJr7TBb',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208363206642733056'),
(21,0,3,'Goldman Sachs is negotiating with U.S. prosecutors to pay a fine of as much as $2 billion, and have a subsidiary plead guilty, to settle claims about its role in a Malaysian fund scandal, a person with knowledge of the matter said \nVia @newyorktimes',NULL,0001,0000,NULL,'1',NULL,0,54,'2019-12-21',NULL,13,'1208305011249336320'),
(22,0,3,'RT @nytimesbusiness: Goldman Sachs is negotiating with U.S. prosecutors to pay a fine of as much as $2 billion, and have a subsidiary plead?',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208304632872595456'),
(23,0,3,'No more religious exemptions: Montreal is taxing churches https://t.co/ymNOa4GcCO',NULL,0000,0000,NULL,NULL,NULL,0,54,'2019-12-21',NULL,13,'1208285037134831616'),
(24,0,3,'Jared Kushner \'admitted Donald Trump lies to his base because he thinks they\'re stupid\' https://t.co/AGC2qvXU4p',NULL,0001,0000,NULL,'1',NULL,0,54,'2019-12-21',NULL,13,'1208284575157440517'),
(25,0,2,'How to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate. Avinash',NULL,0001,0002,NULL,'48','47,1',0,54,'2019-12-26',NULL,14,NULL),
(26,0,3,'How to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate. Vijay',NULL,0000,0000,NULL,'',NULL,0,54,'2019-12-26',NULL,13,NULL),
(40,0,48,'RT @dr_ibrahimarifi: @turkialhussini1 ???? ????? ????\n\n?????? ??? ???? ?????? ???? ?????? ????? ??????? ??????? ??????? ???? ????? ????????',NULL,0002,0001,NULL,'48,1','1',0,54,'2019-12-29',NULL,14,'1211350011767857153'),
(41,0,3,'Hola chicos, mi nombre es Avinash, un profesional de la inform?tica que est? explorando aplicaciones empresariales con dominio experto en la industria de fabricaci?n, ventas, banca, finanzas y servicios de medios.',NULL,0001,0001,NULL,'48','48',0,54,'2020-01-03',NULL,13,NULL),
(42,0,1,'user , jay26 Nov 2019\nHow to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate.',NULL,0001,0001,NULL,'47','47',0,54,'2020-01-03',NULL,15,NULL),
(43,0,1,'????? ????? ???? ???? ?????? ?? ??????? ?????? ?????? ??? ????? ?????. ?? ?????? ????? ???? ?? ??? ???????',NULL,0003,0000,NULL,'1,48,47',NULL,0,54,'2020-01-03',NULL,15,NULL),
(44,0,48,'RT @saudi_umran: ????? ????? #??????? ??????? ????????? ?? ??? ???? ??????? @aldegheishem ???? ???? ????? ???? ????? ????????? ????????? GI?',NULL,0001,0000,NULL,'48',NULL,0,54,'2020-01-03',NULL,14,'1213043455083077634'),
(51,0,5,'?????? \nTest',NULL,0001,0001,NULL,'48','48',0,54,'2020-01-03',NULL,13,'1213043704568668160'),
(52,0,48,'RT @Kahrabaiat: ???? ??????? ??? ???? ?? #?????_???????_?????? ?? ????? ??????? ???? ??????? ???? ????? ????? ??????? ?????? ?????? ????????',NULL,0000,0000,NULL,'',NULL,0,54,'2020-01-04',NULL,14,'1213361882071019520'),
(53,0,48,'RT @Fahad_Allahaim: ????? ?????? ???? ?????? ???? ??? .. https://t.co/SkTgtckkMi',NULL,0001,0001,NULL,'48','1',0,54,'2020-01-04',NULL,14,'1213323234013319169'),
(54,0,48,'RT @Umran_ID: ??? ???? ??????? ??????? ???????? ???????? ????? ??????? ??? ??????? ?????? ?????? ????? ??? ???????? ?? ?????? ???????? ?? ??',NULL,0002,0000,NULL,'48,1',NULL,0,54,'2020-01-04',NULL,14,'1213305660743585793'),
(55,0,5,'RT @AlhadabiBadar: ???? ???????? ????????? ????????? ????? ??????-??????? ?? ?? ???? ???????? ????? ?? ?????. ??????? ?????? ?????? ???????',NULL,0001,0001,NULL,'1','1',0,54,'2020-01-03',NULL,13,'1213221021966381059'),
(56,0,5,'RT @AlHulafa: ????? ????? ??? ??????? ??????? ????\n\n? ???? ?? ????? ???????\n? ??? ????? ???????\n? ????? ??? ?? ????? ???????\n? ?????? ??? ??',NULL,0000,0000,NULL,'',NULL,0,54,'2020-01-03',NULL,13,'1213221000919289856'),
(57,0,1,'???? ?????? ?????. ??? ???? ?? ????????',NULL,0001,0000,NULL,'1',NULL,0,54,'2020-01-04',NULL,13,NULL),
(63,0,48,'RT @turkialhussini1: @nada___97 ???? ???? ??? ???????? .. ?????? ????? ?????????? ???? ????? ?????? ??????????',NULL,0001,0000,NULL,'48',NULL,0,54,'2020-01-06',NULL,14,'1214213011885441024'),
(64,0,48,'RT @nada___97: @turkialhussini1 ???? ??? ??? ???????? ??? ?????? ?????? ??? ???? ???? ?? ?????? ?',NULL,0001,0000,NULL,'48',NULL,0,54,'2020-01-06',NULL,14,'1214213004167958532'),
(65,0,48,'??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ???? 3.4 ? ??? ??????? ?? ???? ??????? ??????  .. ????? ?????? ??????? ?????? ??? ???? ??? ??? ?????? ?????? ????? ???? ?????? ?????? ???? 4 ?? ?? 4 ?? ??? 1.4 ? ???? ?????? ????? .. ??????? ??? ????? ??? ????? ???? ??? ?????? https://t.co/vwiKnMCzxS',NULL,0001,0000,NULL,'48',NULL,0,54,'2020-01-06',NULL,14,'1214195927042449408'),
(66,0,48,'RT @Fahad_Allahaim: ???? ???? ??????? ??????? ?? ??? ?????? ??? ???? ??? ??????? ?? ?? ???? ????? ????? ???? ??? ??????? ?????? https://t.c?',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-06',NULL,14,'1214168609775333376'),
(67,0,51,'How to do bidirection mapping in jpa , relation of one to one mapping from one entity to another entity. How much it is different from hibernate.',NULL,0001,0000,NULL,'3',NULL,0,54,'2020-01-06',NULL,13,NULL),
(68,0,51,'\n??? ????? ???? ??? ???? ??? 40 ?? ??????? 4 ??? ???? 3.4 ? ??? ??????? ?? ???? ??????? ?????? .. ????? ?????? ??????? ?????? ??? ???? ??? ??? ?????? ?????? ????? ???? ?????? ?????? ???? 4 ?? ?? 4 ?? ??? 1.4 ? ???? ?????? ????? .. ??????? ??? ????? ??? ????? ???? ??? ??????',NULL,0002,0000,NULL,'3,1',NULL,0,54,'2020-01-07',NULL,13,NULL),
(74,0,26,'asd',NULL,0000,0000,NULL,'',NULL,0,54,'2020-01-20',NULL,13,NULL),
(75,0,1,'',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-24',NULL,15,NULL),
(76,0,1,'??? ?? ?????? ?????? ???????? ????? ???? ???? ???.',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-24',NULL,15,NULL),
(77,0,1,'?? ??????? ???? ???????? ??????????? ????????? ???????',NULL,0001,0000,NULL,'1',NULL,0,54,'2020-01-24',NULL,15,NULL),
(78,0,1,'??????????? ????????? ??????? ??? ?? ???? ??? ???? ??? ?? ????? ????',NULL,0000,0000,NULL,'',NULL,0,54,'2020-01-24',NULL,15,NULL),
(79,0,1,'Delhi assembly election is on 8th Feb. Keep count for your valuable votes.',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-25',NULL,15,NULL),
(80,0,1,'Test',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-25',NULL,15,NULL),
(81,0,1,'????????? ?????????????????',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-25',NULL,15,NULL),
(86,0,1,'Failure will never overtake me if my determination to succeed is strong enough',NULL,0002,0001,NULL,'1,51','1',0,54,'2020-01-28',NULL,16,NULL),
(87,0,26,'26_56_9861db7e-8b1f-4e7a-bbce-0e453041e74a-trailer_hd.mp4',NULL,0000,0000,NULL,NULL,NULL,0,56,'2020-01-29',NULL,13,NULL),
(88,0,1,'Knowing is not enough, we must apply. Willing is not enough, we must do.',NULL,0000,0002,NULL,'','1,47',0,54,'2020-01-29',NULL,16,NULL),
(89,0,1,'Knowing is not enough, we must apply',NULL,0001,0001,NULL,'1','1',0,54,'2020-01-29',NULL,16,NULL),
(92,0,1,'1_58_7fa74d87-98d4-4110-9e16-de0faae74051-peripherals.jpg',NULL,0000,0000,NULL,'',NULL,0,58,'2020-01-31',NULL,16,NULL),
(93,0,1,'1_58_e3d8f975-2feb-48f1-8f40-121b29d03ce5-images.jpg',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-01-31',NULL,16,NULL),
(95,0,1,'1_58_dff5af3e-325a-46cb-9406-31bb443f607d-5cc230d9c3a7c15db8365bd5-1136-853.jpg',NULL,0002,0001,NULL,'1,47','1',0,58,'2020-01-31',NULL,16,NULL),
(111,0,1,'1_58_82885be4-7c0b-44bc-b304-275938ea9dc7-thoughtPF_imageb844a205b331be4dd32fd0472af96261.jpg',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-01-31',NULL,16,NULL),
(112,0,1,'1_55_e0b49b98-4c17-4360-b38c-2e7e7b9f5761-thoughtPF_audioTujhe Kitna Chahein Aur Hum | Kabir Singh | Jubin Nautiyal Live | Mithoon | Thomso 2019 | IIT Roorke',NULL,0000,0000,NULL,NULL,NULL,0,55,'2020-01-31',NULL,16,NULL),
(113,0,1,'Abcd check',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-01-31',NULL,16,NULL),
(114,0,1,'1_58_fd3265c9-3cb1-4068-abd3-a6c24668dbc7-thoughtPF_imageimage-40cfc759-4ae8-41f6-9b94-ecba056123f3.jpg',NULL,0001,0001,NULL,'1','1',0,58,'2020-01-31',NULL,16,NULL),
(115,0,1,'1_55_e0f02e08-8f95-4164-b6f0-e92bd4fc0960-thoughtPF_audioTujhe Kitna Chahein Aur Hum | Kabir Singh | Jubin Nautiyal Live | Mithoon | Thomso 2019 | IIT Roorke',NULL,0000,0000,NULL,NULL,NULL,0,55,'2020-01-31',NULL,16,NULL),
(118,0,1,'1_58_180d1861-b0e4-46be-8132-f82dc5af665d-thoughtPF_imageimage-e64e21fb-a7f1-4e9c-8056-d94fd7228165.jpg',NULL,0001,0000,NULL,'1',NULL,0,58,'2020-01-31',NULL,16,NULL),
(119,47,1,'Test ask question',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-03',NULL,16,NULL),
(124,1,48,'What\'s your name? ',NULL,0001,0000,NULL,'1',NULL,0,54,'2020-02-03',NULL,14,NULL),
(125,1,2,'What do you have? ',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-03',NULL,13,NULL),
(126,1,51,'Ghtdhfijgfjy',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-04',NULL,13,NULL),
(127,1,51,'Asdf',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-08',NULL,13,NULL),
(130,47,2,'Test ask question',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-02-10',NULL,16,NULL),
(131,47,2,'Test ask question',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-10',NULL,16,NULL),
(132,47,1,'Test ask question',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-10',NULL,16,NULL),
(133,47,2,'Test ask question second time','Test give Answer',0000,0000,NULL,NULL,NULL,0,59,'2020-02-10',NULL,16,NULL),
(134,57,2,'Ughyyy',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-10',NULL,13,NULL),
(135,1,48,'Ask',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,14,NULL),
(136,1,48,'Question for free',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,14,NULL),
(137,1,2,'I\'m asking free question to jay user ',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-11',NULL,13,NULL),
(138,47,2,'Test ask question second time',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,16,NULL),
(139,1,2,'Ask questions ',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,13,NULL),
(140,1,2,'Ask questions 124',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,13,NULL),
(141,1,2,'Ask 12345',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-11',NULL,13,NULL),
(142,0,1,'Great color mixing activities for kids, including how to mix paint, color science ... FREE printable book activity for toddlers to go along with',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-11',NULL,12,NULL),
(143,1,51,'This guide walks you through the process of building an application that uses Spring Data JPA to store and retrieve data in a relational database.',NULL,0000,0000,NULL,NULL,NULL,0,54,'2020-02-11',NULL,13,NULL),
(144,51,26,'Asking question here',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-12',NULL,13,NULL),
(145,1,2,'Asking free question\n',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-12',NULL,13,NULL),
(146,51,1,'Hi jaynt','Hi Avinash',0000,0000,NULL,NULL,NULL,0,59,'2020-02-14',NULL,16,NULL),
(147,0,48,'RT @Eng_A_C: ???? ???? ??? ?????? ???????? ???????? ??:\n????? ??????? ?? ???? ?????\n?????? ??????? ??????\n?????????? ?? ????? ?????? ???????',NULL,0000,0000,NULL,NULL,NULL,0,01,'2020-02-22',NULL,14,'1231102063205134336'),
(148,1,48,'?',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-22',NULL,14,NULL),
(149,1,26,'????',NULL,0000,0000,NULL,NULL,NULL,0,59,'2020-02-22',NULL,13,NULL),
(150,0,1,'???? ????? ?????? ???? .....\n\n??? ????? ???? ????? ??? ????? ?????? ??? ??? ????? ??? ???? ?? ???? ?',NULL,0001,0000,NULL,'1',NULL,0,54,'2020-02-25',NULL,12,NULL),
(151,0,1,'',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-03-07',NULL,12,NULL),
(152,0,1,'',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-03-07',NULL,12,NULL),
(153,0,1,'',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-03-07',NULL,12,NULL),
(154,0,51,'51_58_c816f555-b982-4833-9590-be730d788139-thoughtPF_imageimage-8a6f9c33-3825-4a73-81a8-432d56befb97.jpg',NULL,0000,0000,NULL,NULL,NULL,0,58,'2020-03-07',NULL,13,NULL);

/*Table structure for table `topics` */

DROP TABLE IF EXISTS `topics`;

CREATE TABLE `topics` (
  `TOPICS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `TOPICS_ARTICLE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LIKE_COUNT` int(4) unsigned zerofill DEFAULT NULL,
  `DISLIKE_COUNT` int(4) unsigned zerofill DEFAULT NULL,
  `SHARE_URL` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LIKE_USER_ID` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `DISLIKE_USER_ID` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `TOPICS_TYPE` int(2) unsigned zerofill DEFAULT NULL,
  `CREATED_ON` date NOT NULL,
  `CATAGORY_ID` int(2) NOT NULL DEFAULT '0',
  `COMMENTS_COUNT` int(2) DEFAULT NULL,
  `HASHTAG` int(11) DEFAULT NULL,
  `KEYWORD` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `USER_DISPLAY_NAME` varchar(100) COLLATE utf8_bin NOT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TOPICS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `topics` */

insert  into `topics`(`TOPICS_ID`,`USER_ID`,`TOPICS_ARTICLE`,`LIKE_COUNT`,`DISLIKE_COUNT`,`SHARE_URL`,`LIKE_USER_ID`,`DISLIKE_USER_ID`,`TOPICS_TYPE`,`CREATED_ON`,`CATAGORY_ID`,`COMMENTS_COUNT`,`HASHTAG`,`KEYWORD`,`USER_DISPLAY_NAME`,`IS_DELETED`) values 
(1,1,'AEM CONTENT CMS Topics',0000,0000,NULL,NULL,NULL,00,'2020-10-19',13,0,2,NULL,'Avinash K',0),
(2,1,'Alfresco CONTENT CMS Topics',0000,0000,NULL,NULL,NULL,00,'2020-10-19',13,0,2,NULL,'Avinash K',0),
(3,1,'Magnolia ECM WEB CONTENT CMS',0000,0000,NULL,NULL,NULL,00,'2020-10-19',13,0,2,NULL,'Avinash K',0);

/*Table structure for table `transaction` */

DROP TABLE IF EXISTS `transaction`;

CREATE TABLE `transaction` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) DEFAULT NULL,
  `SHOP_ID` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `PAYMENT_MODE` int(30) DEFAULT NULL,
  `TRANSACTION_TYPE` int(10) NOT NULL DEFAULT '0',
  `TRANSACTION_STATUS` int(1) DEFAULT NULL,
  `AMOUNT` float DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `TRANSACTION_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(30) DEFAULT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `TOTAL_AMOUNT` float NOT NULL DEFAULT '0',
  `PAID` float NOT NULL DEFAULT '0',
  `DUES` float NOT NULL DEFAULT '0',
  `CART_ID` int(11) DEFAULT NULL,
  `ADMIN_ID` int(11) DEFAULT '2',
  `DISCREPTION` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `ORDER_RCPTID_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `PURCHASE_ID` int(10) NOT NULL DEFAULT '0',
  `WITHDRAW_ID` int(12) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `transaction` */

insert  into `transaction`(`ID`,`USER_ID`,`SHOP_ID`,`PAYMENT_MODE`,`TRANSACTION_TYPE`,`TRANSACTION_STATUS`,`AMOUNT`,`CREATED_ON`,`TRANSACTION_ID`,`USER_TYPE`,`IS_DELETED`,`IS_ACTIVE`,`TOTAL_AMOUNT`,`PAID`,`DUES`,`CART_ID`,`ADMIN_ID`,`DISCREPTION`,`ORDER_RCPTID_ID`,`PURCHASE_ID`,`WITHDRAW_ID`) values 
(1,14,'MILAAN98',14,91,75,293112,'2021-08-26 16:19:40','pay_HpnfAsb14ecctZ',2,0,1,293112,293112,0,2,8,'payment done of 293112 by user 9155316611 with order id 2and transaction ID ispay_HpnfAsb14ecctZ','dc3915b2-e',0,0),
(2,14,'MILAAN98',89,90,75,293112,'2021-08-26 16:25:19',NULL,1,0,1,293112,293112,0,2,8,'Vishal Jake NJ is sjnsjsinjsn in snmsnsjsnmskkdnjsjkdmdjdnks. Djsnjsnnsnsnnsjs sbnsnsnsjndndndndnndnd dbsnn','1',0,0),
(3,0,'MILAAN98',14,92,75,1770,'2021-08-26 19:11:36','pay_HpqYMYBgrzlBxY',1,0,0,1770,0,0,0,8,NULL,NULL,0,0),
(4,0,'MILAAN98',14,92,75,3540,'2021-08-26 19:12:47','pay_HpqZcXHoc6Or2Q',1,0,0,3540,0,0,0,8,NULL,NULL,0,0),
(5,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:17:50','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(6,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:22:32','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(7,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:27:11','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(8,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:30:31','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(9,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:31:32','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(10,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:33:34','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(11,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:35:54','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(12,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:37:15','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(13,0,'MILAAN61',14,92,75,7080,'2021-08-26 19:40:32','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(14,4,'MILAAN98',13,91,75,138119,'2021-08-27 01:26:55',NULL,2,0,1,138119,0,138119,4,8,'payment done of 138119.0 by user 1111111111 with order id 4and transaction ID is688514b7-afc2-47ae-9177-ec013c088052','5107c431-0',0,0),
(15,0,'MILAAN61',14,92,75,7080,'2021-08-27 11:34:02','1222',1,0,0,0,0,0,0,1,NULL,NULL,1,0),
(16,0,'MILAAN61',14,92,75,7080,'2021-08-27 11:34:32','1222',1,0,0,0,0,0,0,1,NULL,NULL,2,0),
(17,0,'MILAAN671',14,92,75,7080,'2021-08-27 11:36:51','1222',1,0,0,0,0,0,0,1,NULL,NULL,3,0),
(18,0,'MILAAN420',14,92,75,7080,'2021-08-27 11:46:49','1222',1,0,0,0,0,0,0,1,NULL,NULL,4,0),
(19,0,'MILAAN420',14,92,75,7080,'2021-08-27 11:49:54','1222',1,0,0,0,0,0,0,1,NULL,NULL,5,0),
(20,0,'MILAAN490',14,92,75,7080,'2021-08-27 11:54:13','1222',1,0,0,0,0,0,0,1,NULL,NULL,6,0),
(21,0,'MILAAN490',14,92,75,6372,'2021-08-27 12:01:36','1222',1,0,0,0,0,0,0,1,NULL,NULL,7,0),
(22,0,'MILAAN490',14,92,75,6372,'2021-08-27 12:05:21','1222',1,0,0,0,0,0,0,1,NULL,NULL,8,0),
(23,4,'MILAAN98',14,91,75,15840,'2021-08-27 12:23:30','pay_Hq895eBM5ziHro',2,0,1,15840,15840,0,6,8,'payment done of 15840 by user 9155316644 with order id 6and transaction ID ispay_Hq895eBM5ziHro','198ceea7-c',0,0),
(24,0,'MILAAN98',89,92,75,7080,'2021-08-27 12:37:37','WALLET-MILAAN-123',1,0,0,7080,0,0,0,8,NULL,NULL,0,0),
(25,0,'MILAAN98',89,92,75,7080,'2021-08-27 12:37:40','WALLET-MILAAN-123',1,0,0,7080,0,0,0,8,NULL,NULL,0,0),
(26,0,'MILAAN61',14,92,75,7080,'2021-08-28 16:38:29','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(27,0,'MILAAN61',14,92,75,7080,'2021-08-28 16:39:52','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(28,0,'MILAAN61',14,92,75,7080,'2021-08-28 16:40:44','10',1,0,1,7080,0,0,0,2,NULL,NULL,0,0),
(29,0,'MILAAN61',14,92,75,7080,'2021-08-28 16:40:54','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(30,0,'MILAAN61',14,92,75,7080,'2021-08-28 16:41:39','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(31,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:06:27','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(32,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:19:23','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(33,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:35:01','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(34,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:39:20','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(35,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:40:34','10',1,0,0,7080,0,0,0,1,NULL,NULL,9,0),
(36,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:44:41','10',1,0,0,7080,0,0,0,1,NULL,NULL,0,0),
(37,0,'MILAAN61',14,92,75,7080,'2021-08-28 17:46:40','10',1,0,0,7080,0,0,0,1,NULL,NULL,10,0),
(38,0,'MILAAN61',14,92,75,3540,'2021-08-28 17:56:25','pay_HqcLBzJYOEYznR',1,0,1,3540,0,0,0,1,NULL,NULL,11,0),
(39,0,'MILAAN98',14,92,75,3540,'2021-08-28 17:57:49','pay_HqcMgEKYQEzMQl',1,0,1,3540,0,0,0,8,NULL,NULL,12,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(50) CHARACTER SET latin1 NOT NULL,
  `F_NAME` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `USER_TYPE` int(11) NOT NULL,
  `L_NAME` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `INITIALS` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  `ACTIVE_IND` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT '1',
  `DOB` datetime DEFAULT NULL,
  `LAST_LOGIN_DATE` datetime DEFAULT NULL,
  `PWD` varchar(40) CHARACTER SET latin1 DEFAULT NULL,
  `TOKEN` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `WALLET_BALANCE` float NOT NULL DEFAULT '0',
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT '0',
  `WISH_COUNT` int(30) NOT NULL DEFAULT '0',
  `WISH_LIST` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `OTP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DUES` float DEFAULT NULL,
  `LAST_PAID` float DEFAULT NULL,
  `LAST_PAID_ON` datetime DEFAULT NULL,
  `DUES_STATUS` tinyint(1) DEFAULT NULL,
  `AVATAR` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `GENDER` int(1) NOT NULL DEFAULT '0',
  `PLAYER_ID` varchar(200) COLLATE utf8_bin NOT NULL,
  `LOGED_IN` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user` */

insert  into `user`(`ID`,`EMAIL_ID`,`F_NAME`,`USER_TYPE`,`L_NAME`,`INITIALS`,`ACTIVE_IND`,`IS_DELETED`,`DOB`,`LAST_LOGIN_DATE`,`PWD`,`TOKEN`,`WALLET_BALANCE`,`MOBILE_NUMBER`,`WISH_COUNT`,`WISH_LIST`,`CREATED_ON`,`OTP`,`SHOP_ID`,`DUES`,`LAST_PAID`,`LAST_PAID_ON`,`DUES_STATUS`,`AVATAR`,`UNAME`,`GENDER`,`PLAYER_ID`,`LOGED_IN`) values 
(1,'','DEEP',2,'SINGH',NULL,1,0,NULL,'2021-08-17 19:48:26','bW9uaWRlZXBAMTk4MQ==','80f9fc1d-de64-4f82-9fc9-e71f6090fd71-1',0,'9507247655',0,'','2021-08-17 19:47:56','','',3170,0,'1900-01-01 00:00:00',0,'','DEEP',0,'a02d5550-40c0-4943-bcee-024f3f9cceb0',0),
(2,'','Sanat',2,'Kumar',NULL,1,0,NULL,'2021-08-24 18:22:29','TWlsYWFuQDEyMw==','32926e6b-bfd5-4ff9-9d08-ca272f0ff4cf-2',0,'7677256089',6,'1,2,6,3,22,21','2021-08-17 20:14:36','','',53121,0,'1900-01-01 00:00:00',0,'','Sanat',0,'4dd276e3-4df0-4aae-8363-a669a659c819',0),
(3,'','Vishal',2,'Kumar',NULL,1,0,NULL,'2021-08-17 20:21:10','VmlzaGFsQDE4','053804b3-e48f-4d46-974c-edd51a4775f8-3',0,'9155316622',0,'','2021-08-17 20:20:51','','',5130,0,'1900-01-01 00:00:00',0,'','Vishal',0,'16ec2d77-9386-4ffb-abc3-186620d8b694',0),
(4,'','Janu',2,'Kumar',NULL,1,0,NULL,'2021-08-28 11:52:11','VmlzaGFsQDE4','4f9132e6-5aaa-4aeb-a420-1eba3ac61d46-4',0,'9155316644',3,'1729381,23,25','2021-08-18 12:42:24','','',1048300,0,'1900-01-01 00:00:00',0,'','Vishal ',0,'4944c08d-847e-4703-8c2d-2659c9da0d6c',1),
(5,'','Vishal ',2,'Kumar',NULL,1,0,NULL,'2021-08-18 15:46:53','QXVnQDIwMjE=','',0,'9111111111',0,'','2021-08-18 15:46:53','','',0,0,'1900-01-01 00:00:00',0,'','Vishal ',0,'',0),
(6,'','Akhilesh',2,'Kumar',NULL,1,0,NULL,'2021-08-20 11:30:39','MTIzNGFiY2Q=','bd6d7357-1dae-4019-8df3-ceee50822761-6',0,'7903615551',0,'','2021-08-20 11:25:20','','',0,0,'1900-01-01 00:00:00',0,'','Akhilesh',0,'ebe4b42b-295b-4e81-8e0e-70dcca411670',1),
(7,'','Sanat',2,'Kumar',NULL,1,0,NULL,'2021-08-30 11:02:03','TWlsYWFuQDEyMw==','ea72d098-86a1-4389-9121-ed1681ba2358-7',0,'1111111111',0,'','2021-08-20 19:36:31','','',53100,0,'1900-01-01 00:00:00',0,'','Sanat',0,'ggffg',1),
(8,'','Prabhkar',2,'Kumar',NULL,1,0,NULL,'2021-08-20 21:58:32','VmlzaGFsQDEw','',0,'9155316699',0,'','2021-08-20 21:58:32','','',0,0,'1900-01-01 00:00:00',0,'','Prabhkar',0,'',0),
(9,'','Prabhakar',2,'Tiwari',NULL,1,0,NULL,'2021-08-21 12:24:06','QXVnQDIwMjE=','c2fd99c4-de36-45e2-9ddf-115fe4614f7c-9',0,'9123456789',1,'3','2021-08-21 12:23:43','','',0,0,'1900-01-01 00:00:00',0,'','Prabhakar',0,'b1eb43bb-e989-4aa2-a9b3-37cfe6f46245',1),
(10,'','Bablu',2,'Kumar',NULL,1,0,NULL,'2021-08-21 21:48:51','QXVnQDIwMjE=','7c24b2c3-b3f5-4615-9020-c39ec17dea61-10',0,'9876543210',1,'25','2021-08-21 21:48:11','','',196573,0,'1900-01-01 00:00:00',0,'','Bablu',0,'97be0091-b3ba-490f-8ce2-2b6eeb7a8520',0),
(11,'','Avi',2,'Tiwari',NULL,1,0,NULL,'2021-08-27 01:22:24','MTExMTExMTE=','c066289e-60c0-4c54-88c3-8884fefb5ee6-11',0,'2222222222',3,'3,2,6','2021-08-26 06:10:10','','',77862.1,0,'1900-01-01 00:00:00',0,'','Avi',0,'0602e271-01e1-4951-84bd-8b0bd13cf083',1),
(12,'','Janu',2,'Kumar',NULL,1,0,NULL,'2021-08-26 09:27:59','VmlzaGFsQDE4','0c472233-ecd7-499e-80b4-557468704fe2-12',0,'9155316688',0,'','2021-08-26 09:27:41','','',0,0,'1900-01-01 00:00:00',0,'','Janu',0,'d1b3ace3-f0a9-4b4f-81f6-55f4eff56f06',0),
(13,'','Akhilesh ',2,'Kumar',NULL,1,0,NULL,'2021-08-26 10:54:20','bW9uaWRlZXBAMTk4MQ==','235684bd-da56-4dd4-9bb9-d3547b0645bb-13',0,'5555555555',0,'','2021-08-26 09:29:25','','',0,0,'1900-01-01 00:00:00',0,'','Akhilesh ',0,'00ae3af8-53c7-4d2f-b1ea-af22a968bf6b',0),
(14,'','Avinas',2,'Kumar',NULL,1,0,NULL,'2021-08-26 16:03:32','VmlzaGFsQDE4','372a1176-9e90-4335-952f-19cdb36f6625-14',293112,'9155316611',1,'27','2021-08-26 13:04:21','','',293112,0,'1900-01-01 00:00:00',0,'','Avinas',0,'d1b3ace3-f0a9-4b4f-81f6-55f4eff56f06',1),
(15,'','avi',2,'tiwari',NULL,1,0,NULL,'2021-08-26 13:08:05','MTIzNDU2Nzg=','',0,'1234567890',0,'','2021-08-26 13:08:05','','',0,0,'1900-01-01 00:00:00',0,'','avi',0,'',0),
(16,'','Abhishek',2,'Singh',NULL,1,0,NULL,'2021-08-27 01:39:50','aWJoYSMjMjAwMTM=','9e69c17c-7375-4e38-bc54-ee20a8d24be1-16',0,'9355477609',0,'','2021-08-27 01:39:33','','',0,0,'1900-01-01 00:00:00',0,'','Abhishek',0,'7d0d1269-c689-4249-9dd5-3b3e181b5703',1);

/*Table structure for table `user_address` */

DROP TABLE IF EXISTS `user_address`;

CREATE TABLE `user_address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PIN_CODE` int(6) DEFAULT NULL,
  `CITY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STATE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `COUNTRY` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `LATITUDE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `LONGITUDE` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(3) DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `DISTRICT` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `POST_OFFICE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `POLICE_STATION` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LAND_MARK` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `STREET` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_address` */

insert  into `user_address`(`ID`,`PIN_CODE`,`CITY`,`STATE`,`COUNTRY`,`LATITUDE`,`LONGITUDE`,`MOBILE_NUMBER`,`USER_TYPE`,`SHOP_ID`,`CREATED_ON`,`IS_DELETED`,`IS_ACTIVE`,`DISTRICT`,`POST_OFFICE`,`POLICE_STATION`,`LAND_MARK`,`USER_ID`,`STREET`,`NAME`) values 
(1,250615,'Baghpat','U. P','India','28.871464','77.3764416',NULL,1,'MILAAN61','2021-08-17',0,1,'Baghpat','Dhikoli','Dhikoli','Af','1','5',NULL),
(2,852106,'SAHARSA','BIHAR','INDIA','25.880303','86.592817','9898767898',1,'MILAAN42','2021-08-17',0,1,'SAHARSA','SAHARSA','SIMRIBAKHTIYAR PUR','THANA CHOCK','2','DB ROAD',NULL),
(3,801522,'Dehri','Bihar','India','24.9273601','84.1854644','9140506980',1,'MILAAN43','2021-08-17',0,1,'Rohtas','Dalmianagar','Dalmianagar','S block','3','6',NULL),
(4,844111,'Hajipur','Bihar','India ','25.59180872','85.1558776','7739031244',1,'MILAAN95','2021-08-17',0,1,'Vaishali','Hajipur','Hajipur','Bmp16','5','Aa',NULL),
(5,821301,'Dehri','Bihar','India','25.59180872','85.1558776','9430213739',1,'MILAAN46','2021-08-17',0,1,'Rohtas','Akorhi gola','Akorhi gola',NULL,'6','Rajnagar',NULL),
(6,821301,'Dehri','Bihar','India','25.59180872','85.1558776','8409502861',1,'MILAAN47','2021-08-17',0,1,'Rohtas','Akorhi gola','Akorhi gola','Idea tower','7','Rajnagar',NULL),
(7,801505,'Gaya','Bihar','India','24.7809382','84.9838522','9155316666',1,'MILAAN98','2021-08-20',0,1,'Gaya','Gaya','Gaya','Near Post office','8','Jail Road',NULL),
(8,844111,'Muzaffarpur ','Bihar','India','25.59180872','85.1558776','2233445566',1,'MILAAN49','2021-08-20',0,1,'Muzaffarpur ','Muzaffarpur ','Muzaffarpur ','Bmp16','9','Aa',NULL),
(9,801505,'Patna','Bihar','India','25.577526','85.0587009','9155316699',1,'MILAAN910','2021-08-20',0,1,'Patna','Patna','Patna','Near Bmp 16','10','Phulwari KHAGAUL road',NULL),
(10,821305,'DALMIANAGAR ','बिहार ','India ','24.9236824','84.1865353','9507247655',1,'MILAAN711','2021-08-20',0,1,'ROHTAS ','DALMIANAGAR ','Dehri ','ROHTAS CLUB ','11','S block ',NULL),
(11,821506,'Gaya','Bihar','India','28.8714656','77.3764464','9140506965',1,'MILAAN1112','2021-08-20',0,1,'Gaya','Gaya','Gaya','Near ','12',NULL,NULL),
(12,801505,'Patna','Bihar','India','28.8714656','77.3764464','7677256089',1,'MILAAN413','2021-08-21',0,1,'patna','anishabad','gardanibagh',NULL,'13','Inorbit complex, flat No:- 101,',NULL),
(13,600127,'Chennai','Tamil Nadu','India','12.840641','80.1534283','7677256088',1,'MILAAN414','2021-08-21',0,1,'Kelabakam','Kelabakam','Kelabakam',NULL,'14','Kelambakkam - Vandalur Rd, Rajan Nagar, Chennai,',NULL),
(14,110092,'East Delhi','Delhi','India','28.630712751027318','77.27757453918457','7766554433',1,'MILAAN515','2021-08-26',0,1,'delhi','lakshmi nagar','lakshimi nagar','metro piller 33','15','metro piler 38',NULL),
(15,110092,'East Delhi','Delhi','India','25.578159199999998','85.0551689','1231234566',1,'MILAAN516','2021-08-26',0,1,'dghghg','hsahcjs','cscscC','czxc','16','metro piler 38',NULL),
(16,110092,'East Delhi','Delhi','India','25.59180872','85.1558776','122111234',1,'MILAAN7217','2021-08-26',0,1,'fff','ffffff','ff','ff','17','metro piler 38',NULL),
(17,110092,'East Delhi','Delhi','India','25.5885312','85.0558976','9999000009',1,'MILAAN818','2021-08-26',0,1,'aa','aa','aa','aa','18','metro piler 38',NULL),
(18,110092,'East Delhi','Delhi','India','25.5885312','85.0558976',NULL,1,'MILAAN1219','2021-08-26',0,1,'tt','tt','tt',NULL,'19','metro piler 38',NULL),
(19,789876,'PATNATA','BIHAR','India','25.5885312','85.0558976','8987654567',1,'MILAAN620','2021-08-26',0,1,'PATNA','PATNA','PATNA',NULL,'20','DSJDDSJ',NULL),
(20,801505,'PATNA','BIHAR','INDIA','25.5973','85.0659',NULL,1,'MILAAN921','2021-08-26',0,1,'PATNA','KHAGAUL','KHAGAUL','BMP 16','21','KHAGAUL ROAD',NULL);

/*Table structure for table `user_bill_book` */

DROP TABLE IF EXISTS `user_bill_book`;

CREATE TABLE `user_bill_book` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `USER_TYPE` int(10) NOT NULL DEFAULT '0',
  `SHOP_ID` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `DEBIT` float NOT NULL DEFAULT '0',
  `CREDIT` float NOT NULL DEFAULT '0',
  `LAST_DEBITED_ON` date DEFAULT NULL,
  `LAST_CREDITED_ON` date DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `UPDATED_ON` date DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_bill_book` */

insert  into `user_bill_book`(`ID`,`USER_ID`,`USER_TYPE`,`SHOP_ID`,`DEBIT`,`CREDIT`,`LAST_DEBITED_ON`,`LAST_CREDITED_ON`,`CREATED_ON`,`UPDATED_ON`,`IS_ACTIVE`,`IS_DELETED`) values 
(1,'14',2,'MILAAN98',293112,293112,'2021-08-26','2021-08-26','2021-08-26','2021-08-26',1,0),
(2,'4',2,'MILAAN98',15840,0,'2021-08-27',NULL,'2021-08-27','2021-08-27',1,0);

/*Table structure for table `user_device` */

DROP TABLE IF EXISTS `user_device`;

CREATE TABLE `user_device` (
  `ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `DEVICE_ID` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_device` */

/*Table structure for table `user_device_id_mapping` */

DROP TABLE IF EXISTS `user_device_id_mapping`;

CREATE TABLE `user_device_id_mapping` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) DEFAULT NULL,
  `DEVICE_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `APNS_TOKEN_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `PLAYER_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_device_id_mapping` */

insert  into `user_device_id_mapping`(`ID`,`USER_ID`,`DEVICE_ID`,`APNS_TOKEN_ID`,`PLAYER_ID`) values 
(7,7,'7d37583dd947235a',NULL,'395580ba-5703-42f3-919f-3b429b89c736'),
(12,9,'729cade07cbb4526',NULL,'b1eb43bb-e989-4aa2-a9b3-37cfe6f46245'),
(15,2,'154eea80a55cd6f4',NULL,'79225177-a5a5-42e1-a75e-fa0e0cfb64c1'),
(17,4,'a54ce14c69f3e9c7',NULL,'1b8f6055-08b7-4820-958b-6b8c45ea3ac7'),
(20,7,'nnn57585875gjhgjhkjhkjh',NULL,'ggffgjhgjgytyut76876876'),
(23,7,'9b2943f2d96a9989',NULL,'22048502-eca9-4220-b003-062814192e92'),
(24,7,'9b2943f2d96a9989',NULL,'22048502-eca9-4220-b003-062814192e92'),
(25,4,'729cade07cbb4526',NULL,'0501cd77-d5ea-44e3-9ea5-c1370e5ae386'),
(29,11,'f633588e81819cf4',NULL,'4dd276e3-4df0-4aae-8363-a669a659c819'),
(33,11,'gghkghkg1223232313',NULL,'hdiowhioe2323fhwhh'),
(37,14,'1756ed5f548cabd2',NULL,'d1b3ace3-f0a9-4b4f-81f6-55f4eff56f06'),
(38,4,'41fdb9096d0e9b03',NULL,'b4e84d14-3976-4f85-a0a1-c6e839be3396'),
(39,11,'dbdfffb502ecbaf7',NULL,'0602e271-01e1-4951-84bd-8b0bd13cf083'),
(42,16,'f7ca983453977cec',NULL,'7d0d1269-c689-4249-9dd5-3b3e181b5703'),
(45,4,'1756ed5f548cabd2',NULL,'4944c08d-847e-4703-8c2d-2659c9da0d6c');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `CUSTOMER` tinyint(1) DEFAULT NULL,
  `CONSULTANT` tinyint(1) DEFAULT NULL,
  `ADMIN` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_role` */

/*Table structure for table `withdraw_request` */

DROP TABLE IF EXISTS `withdraw_request`;

CREATE TABLE `withdraw_request` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_HOLDER_NAME` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `ACCOUNT_NUM` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `BANK_NAME` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `WITHDRAW_BALANCE` int(11) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `ADMIN_ID` int(6) DEFAULT NULL,
  `USER_ID` int(6) DEFAULT NULL,
  `SHOP_ID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE_NUMBER` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `ACCOUNT_ID` int(15) DEFAULT NULL,
  `WITHDRAW_STATUS` int(3) NOT NULL,
  `REQUEST_TRANSACTION_ID` int(12) NOT NULL,
  `PAID_TRANSACTION_ID` int(13) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `withdraw_request` */

/*Table structure for table `work` */

DROP TABLE IF EXISTS `work`;

CREATE TABLE `work` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `ABSENT` int(2) DEFAULT NULL,
  `PRESENT` int(2) DEFAULT NULL,
  `BONUS` int(11) DEFAULT NULL,
  `CREATED_ON` date DEFAULT NULL,
  `ADVANCE_SALARY` int(11) DEFAULT NULL,
  `SALARY` int(11) DEFAULT NULL,
  `SALARY_TYPE` int(10) DEFAULT NULL,
  `LEAVE_FROM` date DEFAULT NULL,
  `LEAVE_TO` date DEFAULT NULL,
  `LEAVE_TYPE` int(10) DEFAULT NULL,
  `INCENTIVE` int(11) DEFAULT NULL,
  `ATTENDANCE` int(3) DEFAULT NULL,
  `EMPLOYEE_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `SHOP_ID` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL,
  `IS_DELETED` tinyint(1) NOT NULL,
  `TOTAL_SALARY` int(11) NOT NULL DEFAULT '0',
  `UPDATED_ON` date NOT NULL,
  `DEDUCTION` int(11) DEFAULT '0',
  `PAYMENT_STATUS` tinyint(1) DEFAULT '0',
  `PAID_SALARY` int(11) DEFAULT '0',
  `DUES_SALARY` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `work` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
