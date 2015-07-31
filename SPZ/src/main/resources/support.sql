-- MySQL dump 10.15  Distrib 10.0.20-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: support
-- ------------------------------------------------------
-- Server version	10.0.20-MariaDB

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
-- Table structure for table `SEQUENCE`
--

DROP TABLE IF EXISTS `SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE`
--

LOCK TABLES `SEQUENCE` WRITE;
/*!40000 ALTER TABLE `SEQUENCE` DISABLE KEYS */;
INSERT INTO `SEQUENCE` VALUES ('SEQ_GEN',50);
/*!40000 ALTER TABLE `SEQUENCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `content` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `spznote_id` int(11) NOT NULL,
  `ts` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR00D` (`spznote_id`),
  CONSTRAINT `CNSTR00D` FOREIGN KEY (`spznote_id`) REFERENCES `spznote` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `code` varchar(32) NOT NULL,
  `description` varchar(255) NOT NULL,
  `seqnumber` int(11) NOT NULL,
  `ts` int(11) NOT NULL,
  `project_code` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR009` (`project_code`),
  CONSTRAINT `CNSTR009` FOREIGN KEY (`project_code`) REFERENCES `project` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `name` varchar(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ts` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `login` varchar(32) NOT NULL,
  `role_` varchar(32) NOT NULL,
  PRIMARY KEY (`login`,`role_`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('sa','SA'),('sa','USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence` (
  `seq_name` varchar(32) NOT NULL,
  `seq_count` int(11) NOT NULL,
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence`
--

LOCK TABLES `sequence` WRITE;
/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
INSERT INTO `sequence` VALUES ('attachment',1),('configuration',1),('spz',1),('spznote',1),('spzstate',1),('useraccess',1);
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spz`
--

DROP TABLE IF EXISTS `spz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spz` (
  `id` int(11) NOT NULL,
  `reqNumber` varchar(10) NOT NULL,
  `priority` tinyint(4) NOT NULL,
  `issueDate` datetime NOT NULL,
  `contactPerson` varchar(32) NOT NULL,
  `requestType` varchar(32) NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `requestDescription` text NOT NULL,
  `implementationAcceptDate` datetime DEFAULT NULL,
  `ts` int(11) NOT NULL,
  `issuer_login` varchar(32) NOT NULL,
  `analyst_login` varchar(32) DEFAULT NULL,
  `developer_login` varchar(32) DEFAULT NULL,
  `configuration_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR00C` (`configuration_id`),
  KEY `CNSTR00K` (`analyst_login`),
  KEY `CNSTR00L` (`developer_login`),
  CONSTRAINT `CNSTR00C` FOREIGN KEY (`configuration_id`) REFERENCES `configuration` (`id`),
  CONSTRAINT `CNSTR00K` FOREIGN KEY (`analyst_login`) REFERENCES `user_` (`login`),
  CONSTRAINT `CNSTR00L` FOREIGN KEY (`developer_login`) REFERENCES `user_` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spz`
--

LOCK TABLES `spz` WRITE;
/*!40000 ALTER TABLE `spz` DISABLE KEYS */;
/*!40000 ALTER TABLE `spz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spznote`
--

DROP TABLE IF EXISTS `spznote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spznote` (
  `id` int(11) NOT NULL,
  `external_note` tinyint(4) NOT NULL,
  `noteDate` datetime NOT NULL,
  `ntext` text NOT NULL,
  `state_id` int(11) NOT NULL,
  `issuer_login` varchar(32) NOT NULL,
  `ts` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR00E` (`state_id`),
  KEY `CNSTR00F` (`issuer_login`),
  CONSTRAINT `CNSTR00E` FOREIGN KEY (`state_id`) REFERENCES `spzstate` (`id`),
  CONSTRAINT `CNSTR00F` FOREIGN KEY (`issuer_login`) REFERENCES `user_` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spznote`
--

LOCK TABLES `spznote` WRITE;
/*!40000 ALTER TABLE `spznote` DISABLE KEYS */;
/*!40000 ALTER TABLE `spznote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spzstate`
--

DROP TABLE IF EXISTS `spzstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spzstate` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `ts` int(11) NOT NULL,
  `issuer_login` varchar(32) NOT NULL,
  `spz_id` int(11) NOT NULL,
  `revisedRequestDescription` text,
  `solutionDescription` text,
  `assumedManDays` double DEFAULT NULL,
  `manDays` double DEFAULT NULL,
  `releaseNotes` text,
  `class_type` tinyint(4) NOT NULL,
  `idate` datetime NOT NULL,
  `current_state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR00I` (`spz_id`),
  KEY `CNSTR00J` (`issuer_login`),
  CONSTRAINT `CNSTR00I` FOREIGN KEY (`spz_id`) REFERENCES `spz` (`id`),
  CONSTRAINT `CNSTR00J` FOREIGN KEY (`issuer_login`) REFERENCES `user_` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spzstate`
--

LOCK TABLES `spzstate` WRITE;
/*!40000 ALTER TABLE `spzstate` DISABLE KEYS */;
/*!40000 ALTER TABLE `spzstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_`
--

DROP TABLE IF EXISTS `user_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_` (
  `login` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `company` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `class_type` tinyint(4) NOT NULL,
  `ts` int(11) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_`
--

LOCK TABLES `user_` WRITE;
/*!40000 ALTER TABLE `user_` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccess`
--

DROP TABLE IF EXISTS `useraccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccess` (
  `id` int(11) NOT NULL,
  `login` varchar(32) NOT NULL,
  `configuration_id` int(11) NOT NULL,
  `role_` varchar(32) NOT NULL,
  `ts` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CNSTR00A` (`configuration_id`),
  KEY `CNSTR00B` (`login`),
  CONSTRAINT `CNSTR00A` FOREIGN KEY (`configuration_id`) REFERENCES `configuration` (`id`),
  CONSTRAINT `CNSTR00B` FOREIGN KEY (`login`) REFERENCES `user_` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccess`
--

LOCK TABLES `useraccess` WRITE;
/*!40000 ALTER TABLE `useraccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `useraccess` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-28  5:24:07
