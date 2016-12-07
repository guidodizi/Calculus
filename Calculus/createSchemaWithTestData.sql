CREATE DATABASE  IF NOT EXISTS `pis2016` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pis2016`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: pis2016
-- ------------------------------------------------------
-- Server version	5.5.19

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
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hor2f73krqwrea222a8tvilo5` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (8,'a@a.com.uy','Andres Gonzalez','a'),(9,'b@b.com','Brian Rodriguez','b'),(10,'c@c.com.uy','Carlos Gutierrez','c'),(11,'d@d.com.uy','Dario Perez','d'),(12,'e@e.com.uy','Esteban Hernandez','e'),(13,'admin@admin.com','Administrador','admin');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (20),(20),(20),(20),(20);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duda`
--

DROP TABLE IF EXISTS `duda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `duda` (
  `id` bigint(20) NOT NULL,
  `duda` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `leida` bit(1) NOT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `respuesta` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `alumno_persona_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdlekaqwq89tabataye4oa9mk5` (`alumno_persona_id`),
  CONSTRAINT `FKdlekaqwq89tabataye4oa9mk5` FOREIGN KEY (`alumno_persona_id`) REFERENCES `alumno` (`persona_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duda`
--

LOCK TABLES `duda` WRITE;
/*!40000 ALTER TABLE `duda` DISABLE KEYS */;
INSERT INTO `duda` VALUES (14,'La integral de e a la x da como resultado e a la x, pero si en vez de x tengo 3x? la integral de e a la 3x es e a la 3x?','2016-11-11','\0','Integrando e.',NULL,'Integrando e.',8),(15,'Como se resuelve una integral con la variable a integrar en el exponencial? ejemplo: 3 a la x.','2016-09-11','\0','Integral con variable en el exponencial.',NULL,'Integral con variable en el exponencial.',8),(16,'Que sucede si tengo Limite de (x / e a la x) con x tendiendo a infinito? tanto numerador como denominador tienden a infinito.','2016-08-11','\0','Limite infinito sobre infinito.',NULL,'Limite infinito sobre infinito.',9);
/*!40000 ALTER TABLE `duda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno` (
  `puntaje` int(11) NOT NULL,
  `persona_id` bigint(20) NOT NULL,
  PRIMARY KEY (`persona_id`),
  CONSTRAINT `FKhla6j5g35uv4aymk363v1mgon` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (10,8),(20,9),(60,10),(40,11),(100,12);
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `error`
--

DROP TABLE IF EXISTS `error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `error` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `leida` bit(1) NOT NULL,
  `alumno_persona_id` bigint(20) DEFAULT NULL,
  `pregunta_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8p6hvsn2m89f3dyesbf2xucrm` (`alumno_persona_id`),
  KEY `FKegrllqjso3mt793ssevm0atn2` (`pregunta_id`),
  CONSTRAINT `FKegrllqjso3mt793ssevm0atn2` FOREIGN KEY (`pregunta_id`) REFERENCES `pregunta` (`id`),
  CONSTRAINT `FK8p6hvsn2m89f3dyesbf2xucrm` FOREIGN KEY (`alumno_persona_id`) REFERENCES `alumno` (`persona_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error`
--

LOCK TABLES `error` WRITE;
/*!40000 ALTER TABLE `error` DISABLE KEYS */;
INSERT INTO `error` VALUES (17,'La imagen de la pregunta no se ve.','2016-11-11','\0',8,3),(18,'La pregunta esta mal planteada.','2016-11-11','\0',8,4),(19,'La imagen de la pregunta no se ve.','2016-11-11','\0',9,3);
/*!40000 ALTER TABLE `error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta`
--

DROP TABLE IF EXISTS `pregunta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pregunta` (
  `id` bigint(20) NOT NULL,
  `cantRespuestas` int(11) NOT NULL,
  `cantRespuestasCorrectas` int(11) NOT NULL,
  `descripcion_imagen` varchar(255) DEFAULT NULL,
  `descripcion_texto` varchar(255) DEFAULT NULL,
  `descripcion_video` varchar(255) DEFAULT NULL,
  `inicial` bit(1) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `respuesta` varchar(255) DEFAULT NULL,
  `tip_imagen` varchar(255) DEFAULT NULL,
  `tip_texto` varchar(255) DEFAULT NULL,
  `tip_video` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `profesor_id` bigint(20) DEFAULT NULL,
  `TEMA_ID` bigint(20) DEFAULT NULL,
  `preguntasCreadas_KEY` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86w8v9df0q2iqsd4fhvqm7mme` (`profesor_id`),
  KEY `FKge7bapu8bsbf9gsigm8vclrad` (`TEMA_ID`),
  CONSTRAINT `FKge7bapu8bsbf9gsigm8vclrad` FOREIGN KEY (`TEMA_ID`) REFERENCES `tema` (`id`),
  CONSTRAINT `FK86w8v9df0q2iqsd4fhvqm7mme` FOREIGN KEY (`profesor_id`) REFERENCES `profesor` (`persona_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta`
--

LOCK TABLES `pregunta` WRITE;
/*!40000 ALTER TABLE `pregunta` DISABLE KEYS */;
INSERT INTO `pregunta` VALUES (3,0,0,'http://c5.staticflickr.com/6/5613/30490740212_b07bfc659c_m.jpg','Resuelva la siguiente integral:',NULL,'',1,'1','http://c6.staticflickr.com/6/5675/30607919165_4ae7eddc47_m.jpg','Recuerda lo siguiente:','https://www.youtube.com/watch?v=oi3H4XxXk6o','Integral de e a la x.',NULL,1,NULL),(4,0,0,'http://c7.staticflickr.com/6/5647/30490740142_4370bb38e7_m.jpg','Escriba el resultado de la integral.',NULL,'\0',1,'1','http://c4.staticflickr.com/6/5670/30607919115_915ac3fa95_m.jpg','Recuerda lo siguiente:',NULL,'x al cuadrado en el denominador.',NULL,1,NULL),(5,0,0,'http://c5.staticflickr.com/6/5604/29976033404_e87fe2de29_m.jpg','Escriba el resultado de la integral.',NULL,'\0',1,'1','http://c7.staticflickr.com/6/5345/29976033534_431c32029f_m.jpg','Recuerda la siguiente propiedad.',NULL,'Integral de una resta.',NULL,1,NULL),(6,0,0,'http://c4.staticflickr.com/6/5565/30519564331_3e32f447df_m.jpg','Escriba el resultado de la integral.',NULL,'\0',1,'1','http://c7.staticflickr.com/6/5501/29976033494_0a3ccfca04_m.jpg','Recuerda lo siguiente:',NULL,'Integral de x al cuadrado.',NULL,1,NULL),(7,0,0,'http://c8.staticflickr.com/6/5583/30607919095_6cc6875c40_m.jpg','Resuelva el siguiente limite:',NULL,'',1,'1','http://c8.staticflickr.com/6/5650/30519564591_3b6ac8f022.jpg','A medida que el denominador crece la el valor total se hace mas pequeño, piensa a cuanto tiende este limite.',NULL,'Limite con denominador tendiendo a infinito.',NULL,2,7);
/*!40000 ALTER TABLE `pregunta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntasquedebloquea`
--

DROP TABLE IF EXISTS `preguntasquedebloquea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preguntasquedebloquea` (
  `pregunta_id` bigint(20) NOT NULL,
  `desbloquea` bigint(20) DEFAULT NULL,
  KEY `FK3vpq1xnnedwmj35yeovhrb7kw` (`pregunta_id`),
  CONSTRAINT `FK3vpq1xnnedwmj35yeovhrb7kw` FOREIGN KEY (`pregunta_id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntasquedebloquea`
--

LOCK TABLES `preguntasquedebloquea` WRITE;
/*!40000 ALTER TABLE `preguntasquedebloquea` DISABLE KEYS */;
INSERT INTO `preguntasquedebloquea` VALUES (3,4),(3,5),(4,6),(5,6);
/*!40000 ALTER TABLE `preguntasquedebloquea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opcion`
--

DROP TABLE IF EXISTS `opcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opcion` (
  `pregunta_id` bigint(20) NOT NULL,
  `opciones` varchar(255) DEFAULT NULL,
  KEY `FKffkmd4xf5pvvhmbu0lny5g3cv` (`pregunta_id`),
  CONSTRAINT `FKffkmd4xf5pvvhmbu0lny5g3cv` FOREIGN KEY (`pregunta_id`) REFERENCES `preguntamo` (`preguntaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opcion`
--

LOCK TABLES `opcion` WRITE;
/*!40000 ALTER TABLE `opcion` DISABLE KEYS */;
/*!40000 ALTER TABLE `opcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntacompletar`
--

DROP TABLE IF EXISTS `preguntacompletar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preguntacompletar` (
  `preguntaId` bigint(20) NOT NULL,
  PRIMARY KEY (`preguntaId`),
  CONSTRAINT `FKl0ndoorqrrto6w6bd5pnqoqek` FOREIGN KEY (`preguntaId`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntacompletar`
--

LOCK TABLES `preguntacompletar` WRITE;
/*!40000 ALTER TABLE `preguntacompletar` DISABLE KEYS */;
INSERT INTO `preguntacompletar` VALUES (3),(4),(5),(6),(7);
/*!40000 ALTER TABLE `preguntacompletar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tema`
--

DROP TABLE IF EXISTS `tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tema` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `tema` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sw28beo9qn5r6yg8wlo8aogp9` (`tema`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tema`
--

LOCK TABLES `tema` WRITE;
/*!40000 ALTER TABLE `tema` DISABLE KEYS */;
INSERT INTO `tema` VALUES (1,'Usualmente las integrales se usan en el cálculo de áreas, longitudes de curvas y volúmenes de cuerpos de revolución.','Integrales'),(2,'Esta es una poderosa herramienta cuando comenzamos a pensar en la pendiente de una recta tangente a una curva','Limites');
/*!40000 ALTER TABLE `tema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntamo`
--

DROP TABLE IF EXISTS `preguntamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preguntamo` (
  `respuesta` varchar(255) DEFAULT NULL,
  `preguntaId` bigint(20) NOT NULL,
  PRIMARY KEY (`preguntaId`),
  CONSTRAINT `FK1harj2v5q3n0p1o7s8ka9d5od` FOREIGN KEY (`preguntaId`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntamo`
--

LOCK TABLES `preguntamo` WRITE;
/*!40000 ALTER TABLE `preguntamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `preguntamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntaalumno`
--

DROP TABLE IF EXISTS `preguntaalumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preguntaalumno` (
  `alumno_id` bigint(20) NOT NULL,
  `blocked` bit(1) DEFAULT NULL,
  `pregunta_id` bigint(20) DEFAULT NULL,
  `respuestaCorrecta` bit(1) DEFAULT NULL,
  `respuestaIncorrecta` int(11) DEFAULT NULL,
  `preguntasAlumno_KEY` bigint(20) NOT NULL,
  PRIMARY KEY (`alumno_id`,`preguntasAlumno_KEY`),
  KEY `FK8qjk9x9puibd8wau0jrmj56ie` (`pregunta_id`),
  CONSTRAINT `FKqxa45uwp5roa61immg1htojp7` FOREIGN KEY (`alumno_id`) REFERENCES `alumno` (`persona_id`),
  CONSTRAINT `FK8qjk9x9puibd8wau0jrmj56ie` FOREIGN KEY (`pregunta_id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntaalumno`
--

LOCK TABLES `preguntaalumno` WRITE;
/*!40000 ALTER TABLE `preguntaalumno` DISABLE KEYS */;
INSERT INTO `preguntaalumno` VALUES (8,'\0',3,'\0',0,3),(8,'',4,'\0',0,4),(8,'',5,'\0',0,5),(8,'',6,'\0',0,6),(8,'\0',7,'\0',0,7),(9,'\0',3,'\0',0,3),(9,'',4,'\0',0,4),(9,'',5,'\0',0,5),(9,'',6,'\0',0,6),(9,'\0',7,'\0',0,7),(10,'\0',3,'\0',0,3),(10,'',4,'\0',0,4),(10,'',5,'\0',0,5),(10,'',6,'\0',0,6),(10,'\0',7,'\0',0,7),(11,'\0',3,'\0',0,3),(11,'',4,'\0',0,4),(11,'',5,'\0',0,5),(11,'',6,'\0',0,6),(11,'\0',7,'\0',0,7),(12,'\0',3,'\0',0,3),(12,'',4,'\0',0,4),(12,'',5,'\0',0,5),(12,'',6,'\0',0,6),(12,'\0',7,'\0',0,7);
/*!40000 ALTER TABLE `preguntaalumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor` (
  `persona_id` bigint(20) NOT NULL,
  PRIMARY KEY (`persona_id`),
  CONSTRAINT `FKinpg6atorw51a3af445u91jr5` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES (13);
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntasprevias`
--

DROP TABLE IF EXISTS `preguntasprevias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preguntasprevias` (
  `pregunta_id` bigint(20) NOT NULL,
  `previa` bigint(20) DEFAULT NULL,
  KEY `FKm505d5bkdfhd7x0134rsjmkno` (`pregunta_id`),
  CONSTRAINT `FKm505d5bkdfhd7x0134rsjmkno` FOREIGN KEY (`pregunta_id`) REFERENCES `pregunta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntasprevias`
--

LOCK TABLES `preguntasprevias` WRITE;
/*!40000 ALTER TABLE `preguntasprevias` DISABLE KEYS */;
INSERT INTO `preguntasprevias` VALUES (4,3),(5,3),(6,4),(6,5);
/*!40000 ALTER TABLE `preguntasprevias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'pis2016'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-11 20:03:45
