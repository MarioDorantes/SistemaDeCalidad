-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: sgcfei
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `academia`
--

DROP TABLE IF EXISTS `academia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academia` (
  `idAcademia` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `idCoordinador` int DEFAULT NULL,
  PRIMARY KEY (`idAcademia`),
  KEY `fk_academia_1` (`idCoordinador`),
  CONSTRAINT `fk_academia_1` FOREIGN KEY (`idCoordinador`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academia`
--

LOCK TABLES `academia` WRITE;
/*!40000 ALTER TABLE `academia` DISABLE KEYS */;
/*!40000 ALTER TABLE `academia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academiaintegrantes`
--

DROP TABLE IF EXISTS `academiaintegrantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academiaintegrantes` (
  `idAcademia` int NOT NULL,
  `idAcademico` int NOT NULL,
  PRIMARY KEY (`idAcademia`,`idAcademico`),
  KEY `fk_aca_int_2` (`idAcademico`),
  CONSTRAINT `fk_aca_int_1` FOREIGN KEY (`idAcademia`) REFERENCES `academia` (`idAcademia`),
  CONSTRAINT `fk_aca_int_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academiaintegrantes`
--

LOCK TABLES `academiaintegrantes` WRITE;
/*!40000 ALTER TABLE `academiaintegrantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `academiaintegrantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academico`
--

DROP TABLE IF EXISTS `academico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academico` (
  `idAcademico` int NOT NULL AUTO_INCREMENT,
  `numeroPersonal` varchar(50) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `gradoAcademico` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idAcademico`),
  KEY `fk_academico` (`telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academico`
--

LOCK TABLES `academico` WRITE;
/*!40000 ALTER TABLE `academico` DISABLE KEYS */;
INSERT INTO `academico` VALUES (5,'33337','Manuel Lopez Obrador','2791287654','Licenciatura'),(45,'44556','Martín Carvajal Cortina','2781240928','Doctorado'),(47,'09184','Enrique Peña Nieto','2791240968','Licenciatura'),(48,'27890','Xavier Capistran Mendoza','2791273457','Licenciatura'),(51,'12091','Juan Gabriel Gómez Gutierrez','2789012333','Licenciatura'),(53,'45678','Gladys Marlet García López','2791104587','Maestría'),(54,'11008','Matias Trujillo Cuevas','2791213451','Especialización'),(57,'13182','Mario Eduardo Dorantes Hernández','2281223813','Doctorado'),(58,'09120','Jorge Luis Reyes López','2791245694','Maestría');
/*!40000 ALTER TABLE `academico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `areaacademica`
--

DROP TABLE IF EXISTS `areaacademica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areaacademica` (
  `idAreaAcademica` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idAreaAcademica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areaacademica`
--

LOCK TABLES `areaacademica` WRITE;
/*!40000 ALTER TABLE `areaacademica` DISABLE KEYS */;
/*!40000 ALTER TABLE `areaacademica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avanceprogramatico`
--

DROP TABLE IF EXISTS `avanceprogramatico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avanceprogramatico` (
  `idAvanceProgramatico` int NOT NULL AUTO_INCREMENT,
  `idExperienciaEducativa` int DEFAULT NULL,
  `idAcademico` int DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idAvanceProgramatico`),
  KEY `fk_av_pr_1` (`idExperienciaEducativa`),
  KEY `fk_av_pr_2` (`idAcademico`),
  CONSTRAINT `fk_av_pr_1` FOREIGN KEY (`idExperienciaEducativa`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_av_pr_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avanceprogramatico`
--

LOCK TABLES `avanceprogramatico` WRITE;
/*!40000 ALTER TABLE `avanceprogramatico` DISABLE KEYS */;
/*!40000 ALTER TABLE `avanceprogramatico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avanceprogramaticoporcentaje`
--

DROP TABLE IF EXISTS `avanceprogramaticoporcentaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avanceprogramaticoporcentaje` (
  `idAvancePorcentaje` int NOT NULL,
  `unidad` int DEFAULT NULL,
  `porcentaje` int DEFAULT NULL,
  `observaciones` varchar(500) DEFAULT NULL,
  `idAvanceProgramatico` int DEFAULT NULL,
  PRIMARY KEY (`idAvancePorcentaje`),
  KEY `fk_poraca_1` (`idAvanceProgramatico`),
  CONSTRAINT `fk_poraca_1` FOREIGN KEY (`idAvanceProgramatico`) REFERENCES `avanceprogramatico` (`idAvanceProgramatico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avanceprogramaticoporcentaje`
--

LOCK TABLES `avanceprogramaticoporcentaje` WRITE;
/*!40000 ALTER TABLE `avanceprogramaticoporcentaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `avanceprogramaticoporcentaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avanceprogramaticounidad`
--

DROP TABLE IF EXISTS `avanceprogramaticounidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avanceprogramaticounidad` (
  `idAvanceUnidad` int NOT NULL AUTO_INCREMENT,
  `unidad` int DEFAULT NULL,
  `temas` varchar(255) DEFAULT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `tareas` varchar(500) DEFAULT NULL,
  `tecnica` varchar(500) DEFAULT NULL,
  `idAvanceProgramatico` int DEFAULT NULL,
  PRIMARY KEY (`idAvanceUnidad`),
  KEY `fk_avancep_1` (`idAvanceProgramatico`),
  CONSTRAINT `fk_avancep_1` FOREIGN KEY (`idAvanceProgramatico`) REFERENCES `avanceprogramatico` (`idAvanceProgramatico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avanceprogramaticounidad`
--

LOCK TABLES `avanceprogramaticounidad` WRITE;
/*!40000 ALTER TABLE `avanceprogramaticounidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `avanceprogramaticounidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campus`
--

DROP TABLE IF EXISTS `campus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campus` (
  `idCampus` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCampus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campus`
--

LOCK TABLES `campus` WRITE;
/*!40000 ALTER TABLE `campus` DISABLE KEYS */;
/*!40000 ALTER TABLE `campus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrera` (
  `idCarrera` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `idFacultad` int DEFAULT NULL,
  PRIMARY KEY (`idCarrera`),
  KEY `fk_carrera_1` (`idFacultad`),
  CONSTRAINT `fk_carrera_1` FOREIGN KEY (`idFacultad`) REFERENCES `facultad` (`idFacultad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera`
--

LOCK TABLES `carrera` WRITE;
/*!40000 ALTER TABLE `carrera` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogodeacademia`
--

DROP TABLE IF EXISTS `catalogodeacademia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogodeacademia` (
  `idCatalogoDeAcademia` int NOT NULL AUTO_INCREMENT,
  `idLicenciatura` int DEFAULT NULL,
  `nombreAcademia` varchar(255) DEFAULT NULL,
  `nombreCoordinador` varchar(255) DEFAULT NULL,
  `estatus` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`idCatalogoDeAcademia`),
  KEY `idLicenciatura` (`idLicenciatura`),
  CONSTRAINT `catalogodeacademia_ibfk_1` FOREIGN KEY (`idLicenciatura`) REFERENCES `licenciatura` (`idLicenciatura`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogodeacademia`
--

LOCK TABLES `catalogodeacademia` WRITE;
/*!40000 ALTER TABLE `catalogodeacademia` DISABLE KEYS */;
INSERT INTO `catalogodeacademia` VALUES (1,1,'Bases de Datos','Maria Fernanda Cerdan Hernandez','Activo');
/*!40000 ALTER TABLE `catalogodeacademia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogodeee`
--

DROP TABLE IF EXISTS `catalogodeee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogodeee` (
  `idCatalogoDeEE` int NOT NULL AUTO_INCREMENT,
  `idLicenciatura` int DEFAULT NULL,
  `programa` varchar(40) DEFAULT NULL,
  `nrc` varchar(20) DEFAULT NULL,
  `nombreDeLaEE` varchar(100) DEFAULT NULL,
  `creditos` varchar(20) DEFAULT NULL,
  `bloque` varchar(20) DEFAULT NULL,
  `periodo` varchar(40) DEFAULT NULL,
  `estatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idCatalogoDeEE`),
  KEY `idLicenciatura` (`idLicenciatura`),
  CONSTRAINT `catalogodeee_ibfk_1` FOREIGN KEY (`idLicenciatura`) REFERENCES `licenciatura` (`idLicenciatura`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogodeee`
--

LOCK TABLES `catalogodeee` WRITE;
/*!40000 ALTER TABLE `catalogodeee` DISABLE KEYS */;
INSERT INTO `catalogodeee` VALUES (1,1,'ISOF-14-E-CR','80612','Principios de Construccion de Software','10','2','202101','Activo');
/*!40000 ALTER TABLE `catalogodeee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ceneval`
--

DROP TABLE IF EXISTS `ceneval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ceneval` (
  `idCeneval` int NOT NULL AUTO_INCREMENT,
  `idAlumno` int DEFAULT NULL,
  `fechaExamen` date DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  `puntaje` float DEFAULT NULL,
  PRIMARY KEY (`idCeneval`),
  KEY `fk_alumno_1` (`idAlumno`),
  CONSTRAINT `fk_alumno_1` FOREIGN KEY (`idAlumno`) REFERENCES `estudiante` (`idEstudiante`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ceneval`
--

LOCK TABLES `ceneval` WRITE;
/*!40000 ALTER TABLE `ceneval` DISABLE KEYS */;
INSERT INTO `ceneval` VALUES (1,3,'2020-05-26','202106',9.6);
/*!40000 ALTER TABLE `ceneval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuerpoacademico`
--

DROP TABLE IF EXISTS `cuerpoacademico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuerpoacademico` (
  `idCuerpoAcademico` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `mision` varchar(255) DEFAULT NULL,
  `estatus` varchar(50) DEFAULT NULL,
  `idLGCA` int DEFAULT NULL,
  PRIMARY KEY (`idCuerpoAcademico`),
  KEY `fk_lgca_1` (`idLGCA`),
  CONSTRAINT `fk_lgca_1` FOREIGN KEY (`idLGCA`) REFERENCES `lgca` (`idLGCA`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuerpoacademico`
--

LOCK TABLES `cuerpoacademico` WRITE;
/*!40000 ALTER TABLE `cuerpoacademico` DISABLE KEYS */;
INSERT INTO `cuerpoacademico` VALUES (14,'Aplicado a la estadística','2020-03-05','Cuerpo académico enfocado a la estadística','Crear un cuerpo académico responsable','Activo',2),(15,'Aplicado a la Inteligencia Artificial','2021-04-15','Enfocado a la Inteligencia Artificial','Crear un cuerpo académico eficiente ','Inactivo',1),(16,'Aplicado a Pricipios de programación','2020-01-01','Cuerpo compuesto por docentes de la FEI','Que los integrantes esten al pendiente de las EE de programación','Activo',3);
/*!40000 ALTER TABLE `cuerpoacademico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuerpoacademicointegrantes`
--

DROP TABLE IF EXISTS `cuerpoacademicointegrantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuerpoacademicointegrantes` (
  `idCuerpoAcademico` int NOT NULL,
  `idAcademico` int NOT NULL,
  `rol` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idCuerpoAcademico`,`idAcademico`),
  KEY `fk_curp_acai_2` (`idAcademico`),
  CONSTRAINT `cuerpoacademicointegrantes_ibfk_1` FOREIGN KEY (`idCuerpoAcademico`) REFERENCES `cuerpoacademico` (`idCuerpoAcademico`),
  CONSTRAINT `fk_curp_acai_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuerpoacademicointegrantes`
--

LOCK TABLES `cuerpoacademicointegrantes` WRITE;
/*!40000 ALTER TABLE `cuerpoacademicointegrantes` DISABLE KEYS */;
INSERT INTO `cuerpoacademicointegrantes` VALUES (14,45,'Representante'),(14,48,'Docente'),(15,47,'Docente'),(15,51,'Representante'),(15,57,'Docente'),(16,51,'Representante'),(16,54,'Docente');
/*!40000 ALTER TABLE `cuerpoacademicointegrantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `idEstudiante` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `matricula` varchar(20) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `estatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idEstudiante`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (1,'Leonel Andres Messi Cuccitini','0124567','messi@gmail.com','Activo'),(2,'Steve Rogers','0123455','captain@gmail.com','Activo'),(3,'Gustavo Cerati','zS18019633','adostereo@gmail.com','Activo'),(4,'Bruno Diaz','zS17028745','batman@estudiantes.uv.mx','Activo');
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experienciaeducativa`
--

DROP TABLE IF EXISTS `experienciaeducativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experienciaeducativa` (
  `idExperienciaEducativa` int NOT NULL AUTO_INCREMENT,
  `idMateria` int DEFAULT NULL,
  `nrc` varchar(10) DEFAULT NULL,
  `idAcademia` int DEFAULT NULL,
  `idPrerequisito` int DEFAULT NULL,
  `idCorequisito` int DEFAULT NULL,
  PRIMARY KEY (`idExperienciaEducativa`),
  KEY `fk_exp_edi_1` (`idAcademia`),
  KEY `fk_exp_edi_2` (`idPrerequisito`),
  KEY `fk_exp_edi_3` (`idCorequisito`),
  KEY `fk_exp_edi_4` (`idMateria`),
  CONSTRAINT `fk_exp_edi_1` FOREIGN KEY (`idAcademia`) REFERENCES `academia` (`idAcademia`),
  CONSTRAINT `fk_exp_edi_2` FOREIGN KEY (`idPrerequisito`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_exp_edi_3` FOREIGN KEY (`idCorequisito`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_exp_edi_4` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`idMateria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experienciaeducativa`
--

LOCK TABLES `experienciaeducativa` WRITE;
/*!40000 ALTER TABLE `experienciaeducativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `experienciaeducativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultad`
--

DROP TABLE IF EXISTS `facultad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facultad` (
  `idFacultad` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idFacultad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultad`
--

LOCK TABLES `facultad` WRITE;
/*!40000 ALTER TABLE `facultad` DISABLE KEYS */;
/*!40000 ALTER TABLE `facultad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lgca`
--

DROP TABLE IF EXISTS `lgca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lgca` (
  `idLGCA` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(10) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `grado` int DEFAULT NULL,
  `adscripcion` varchar(255) DEFAULT NULL,
  `unidadAdscripcion` varchar(250) DEFAULT NULL,
  `estatus` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idLGCA`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lgca`
--

LOCK TABLES `lgca` WRITE;
/*!40000 ALTER TABLE `lgca` DISABLE KEYS */;
INSERT INTO `lgca` VALUES (1,'UV107','Técnicas y modelos estadisticos','2008-07-15',4,'Económico Administrativa','Facultad de Estadística e Informática','Activo'),(2,'UV108','Tecnologías de la información','2002-08-12',3,'Ecónomico Administrativa','Facultad de Estadística e Informática','Activo'),(3,'UV109','Inteligencia Artificial enfocado a la IS','2020-01-09',1,'Económico Administrativa','Facultad de estádistica e informática','Inactivo');
/*!40000 ALTER TABLE `lgca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `licenciatura`
--

DROP TABLE IF EXISTS `licenciatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `licenciatura` (
  `idLicenciatura` int NOT NULL AUTO_INCREMENT,
  `nombreLicenciatura` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idLicenciatura`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `licenciatura`
--

LOCK TABLES `licenciatura` WRITE;
/*!40000 ALTER TABLE `licenciatura` DISABLE KEYS */;
INSERT INTO `licenciatura` VALUES (1,'Licenciatura en Ingenieria de Software'),(2,'Licenciatura en Redes'),(3,'Licenciatura en Comunicacion'),(4,'Licenciatura en Derecho');
/*!40000 ALTER TABLE `licenciatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `idMateria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idMateria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minutaacademia`
--

DROP TABLE IF EXISTS `minutaacademia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `minutaacademia` (
  `idMinuta` int NOT NULL AUTO_INCREMENT,
  `idAcademia` int DEFAULT NULL,
  `idCarrera` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  `lugar` varchar(500) DEFAULT NULL,
  `objetivo` varchar(1000) DEFAULT NULL,
  `temas` varchar(1000) DEFAULT NULL,
  `conclusiones` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idMinuta`),
  KEY `fk_minaca_1` (`idAcademia`),
  KEY `fk_minaca_2` (`idCarrera`),
  CONSTRAINT `fk_minaca_1` FOREIGN KEY (`idAcademia`) REFERENCES `academia` (`idAcademia`),
  CONSTRAINT `fk_minaca_2` FOREIGN KEY (`idCarrera`) REFERENCES `carrera` (`idCarrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minutaacademia`
--

LOCK TABLES `minutaacademia` WRITE;
/*!40000 ALTER TABLE `minutaacademia` DISABLE KEYS */;
/*!40000 ALTER TABLE `minutaacademia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minutaacademiaagenda`
--

DROP TABLE IF EXISTS `minutaacademiaagenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `minutaacademiaagenda` (
  `idMinutaAgenda` int NOT NULL AUTO_INCREMENT,
  `asunto` varchar(1000) DEFAULT NULL,
  `idAcademico` int DEFAULT NULL,
  `idMinutaAcademia` int DEFAULT NULL,
  PRIMARY KEY (`idMinutaAgenda`),
  KEY `fk_minaa_1` (`idAcademico`),
  KEY `fk_minaa_2` (`idMinutaAcademia`),
  CONSTRAINT `fk_minaa_1` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`),
  CONSTRAINT `fk_minaa_2` FOREIGN KEY (`idMinutaAcademia`) REFERENCES `minutaacademia` (`idMinuta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minutaacademiaagenda`
--

LOCK TABLES `minutaacademiaagenda` WRITE;
/*!40000 ALTER TABLE `minutaacademiaagenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `minutaacademiaagenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minutaacademiaparticipante`
--

DROP TABLE IF EXISTS `minutaacademiaparticipante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `minutaacademiaparticipante` (
  `idMinutaAcademia` int NOT NULL,
  `idAcademico` int NOT NULL,
  PRIMARY KEY (`idMinutaAcademia`,`idAcademico`),
  KEY `fk_map_2` (`idAcademico`),
  CONSTRAINT `fk_map_1` FOREIGN KEY (`idMinutaAcademia`) REFERENCES `minutaacademia` (`idMinuta`),
  CONSTRAINT `fk_map_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minutaacademiaparticipante`
--

LOCK TABLES `minutaacademiaparticipante` WRITE;
/*!40000 ALTER TABLE `minutaacademiaparticipante` DISABLE KEYS */;
/*!40000 ALTER TABLE `minutaacademiaparticipante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademia`
--

DROP TABLE IF EXISTS `planacademia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademia` (
  `idPlanAcademia` int NOT NULL AUTO_INCREMENT,
  `idAcademia` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `periodo` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idPlanAcademia`),
  KEY `fk_paca_1` (`idAcademia`),
  CONSTRAINT `fk_paca_1` FOREIGN KEY (`idAcademia`) REFERENCES `academia` (`idAcademia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademia`
--

LOCK TABLES `planacademia` WRITE;
/*!40000 ALTER TABLE `planacademia` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiaaccion`
--

DROP TABLE IF EXISTS `planacademiaaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiaaccion` (
  `idPlanAccion` int NOT NULL AUTO_INCREMENT,
  `objetivo` varchar(255) DEFAULT NULL,
  `meta` varchar(255) DEFAULT NULL,
  `idPlanAcademia` int DEFAULT NULL,
  PRIMARY KEY (`idPlanAccion`),
  KEY `fk_pac_1` (`idPlanAcademia`),
  CONSTRAINT `fk_pac_1` FOREIGN KEY (`idPlanAcademia`) REFERENCES `planacademia` (`idPlanAcademia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiaaccion`
--

LOCK TABLES `planacademiaaccion` WRITE;
/*!40000 ALTER TABLE `planacademiaaccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiaaccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiaacciondetalle`
--

DROP TABLE IF EXISTS `planacademiaacciondetalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiaacciondetalle` (
  `idAccionDetalle` int NOT NULL AUTO_INCREMENT,
  `accion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `forma` varchar(255) DEFAULT NULL,
  `idPlanAcademiaAccion` int DEFAULT NULL,
  PRIMARY KEY (`idAccionDetalle`),
  KEY `fk_pacd_1` (`idPlanAcademiaAccion`),
  CONSTRAINT `fk_pacd_1` FOREIGN KEY (`idPlanAcademiaAccion`) REFERENCES `planacademiaaccion` (`idPlanAccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiaacciondetalle`
--

LOCK TABLES `planacademiaacciondetalle` WRITE;
/*!40000 ALTER TABLE `planacademiaacciondetalle` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiaacciondetalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiaevaluacion`
--

DROP TABLE IF EXISTS `planacademiaevaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiaevaluacion` (
  `idPlanEvaluacion` int NOT NULL AUTO_INCREMENT,
  `elemento` varchar(255) DEFAULT NULL,
  `porcentaje` int DEFAULT NULL,
  `idPlanAcademia` int DEFAULT NULL,
  PRIMARY KEY (`idPlanEvaluacion`),
  KEY `fk_pap_1` (`idPlanAcademia`),
  CONSTRAINT `fk_pap_1` FOREIGN KEY (`idPlanAcademia`) REFERENCES `planacademia` (`idPlanAcademia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiaevaluacion`
--

LOCK TABLES `planacademiaevaluacion` WRITE;
/*!40000 ALTER TABLE `planacademiaevaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiaevaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiaexamen`
--

DROP TABLE IF EXISTS `planacademiaexamen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiaexamen` (
  `idPlanExamen` int NOT NULL AUTO_INCREMENT,
  `idExperienciaEducativa` int DEFAULT NULL,
  `temaParcial1` varchar(255) DEFAULT NULL,
  `temaParcial2` varchar(255) DEFAULT NULL,
  `idPlanAcademia` int DEFAULT NULL,
  PRIMARY KEY (`idPlanExamen`),
  KEY `fk_tae_1` (`idExperienciaEducativa`),
  KEY `fk_tae_2` (`idPlanAcademia`),
  CONSTRAINT `fk_tae_1` FOREIGN KEY (`idExperienciaEducativa`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_tae_2` FOREIGN KEY (`idPlanAcademia`) REFERENCES `planacademia` (`idPlanAcademia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiaexamen`
--

LOCK TABLES `planacademiaexamen` WRITE;
/*!40000 ALTER TABLE `planacademiaexamen` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiaexamen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiamiembros`
--

DROP TABLE IF EXISTS `planacademiamiembros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiamiembros` (
  `idPlanAcademia` int NOT NULL,
  `idAcademico` int NOT NULL,
  PRIMARY KEY (`idPlanAcademia`,`idAcademico`),
  KEY `fk_pam_2` (`idAcademico`),
  CONSTRAINT `fk_pam_1` FOREIGN KEY (`idPlanAcademia`) REFERENCES `academia` (`idAcademia`),
  CONSTRAINT `fk_pam_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiamiembros`
--

LOCK TABLES `planacademiamiembros` WRITE;
/*!40000 ALTER TABLE `planacademiamiembros` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiamiembros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planacademiarevision`
--

DROP TABLE IF EXISTS `planacademiarevision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planacademiarevision` (
  `idPlanRevision` int NOT NULL,
  `noRevison` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `seccion` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `idPlanAcademia` int DEFAULT NULL,
  `idAcademicoPropuesta` int DEFAULT NULL,
  `idAcademicoAutoriza` int DEFAULT NULL,
  `fechaAutorizacion` date DEFAULT NULL,
  `fechaVigor` date DEFAULT NULL,
  PRIMARY KEY (`idPlanRevision`),
  KEY `fk_par_1` (`idPlanAcademia`),
  KEY `fk_par_2` (`idAcademicoPropuesta`),
  KEY `fk_par_3` (`idAcademicoAutoriza`),
  CONSTRAINT `fk_par_1` FOREIGN KEY (`idPlanAcademia`) REFERENCES `planacademia` (`idPlanAcademia`),
  CONSTRAINT `fk_par_2` FOREIGN KEY (`idAcademicoPropuesta`) REFERENCES `academico` (`idAcademico`),
  CONSTRAINT `fk_par_3` FOREIGN KEY (`idAcademicoAutoriza`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planacademiarevision`
--

LOCK TABLES `planacademiarevision` WRITE;
/*!40000 ALTER TABLE `planacademiarevision` DISABLE KEYS */;
/*!40000 ALTER TABLE `planacademiarevision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plancurso`
--

DROP TABLE IF EXISTS `plancurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plancurso` (
  `idPlanCurso` int NOT NULL,
  `idExperienciaEducativa` int DEFAULT NULL,
  `bloque` int DEFAULT NULL,
  `seccion` int DEFAULT NULL,
  `idAcademico` int DEFAULT NULL,
  PRIMARY KEY (`idPlanCurso`),
  KEY `fk_pc_1` (`idExperienciaEducativa`),
  KEY `fk_pc_2` (`idAcademico`),
  CONSTRAINT `fk_pc_1` FOREIGN KEY (`idExperienciaEducativa`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_pc_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plancurso`
--

LOCK TABLES `plancurso` WRITE;
/*!40000 ALTER TABLE `plancurso` DISABLE KEYS */;
/*!40000 ALTER TABLE `plancurso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plancursobibliografia`
--

DROP TABLE IF EXISTS `plancursobibliografia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plancursobibliografia` (
  `idPlanBibliografia` int NOT NULL AUTO_INCREMENT,
  `autor` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `editorial` varchar(255) DEFAULT NULL,
  `anio` int DEFAULT NULL,
  `idPlanCurso` int DEFAULT NULL,
  PRIMARY KEY (`idPlanBibliografia`),
  KEY `fk_pcb_1` (`idPlanCurso`),
  CONSTRAINT `fk_pcb_1` FOREIGN KEY (`idPlanCurso`) REFERENCES `plancurso` (`idPlanCurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plancursobibliografia`
--

LOCK TABLES `plancursobibliografia` WRITE;
/*!40000 ALTER TABLE `plancursobibliografia` DISABLE KEYS */;
/*!40000 ALTER TABLE `plancursobibliografia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plancursoevaluacion`
--

DROP TABLE IF EXISTS `plancursoevaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plancursoevaluacion` (
  `idPlanEvaluacion` int NOT NULL AUTO_INCREMENT,
  `unidades` varchar(255) DEFAULT NULL,
  `fechas` varchar(255) DEFAULT NULL,
  `criterio` varchar(255) DEFAULT NULL,
  `idPlanCurso` int DEFAULT NULL,
  PRIMARY KEY (`idPlanEvaluacion`),
  KEY `fk_pce_1` (`idPlanCurso`),
  CONSTRAINT `fk_pce_1` FOREIGN KEY (`idPlanCurso`) REFERENCES `plancurso` (`idPlanCurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plancursoevaluacion`
--

LOCK TABLES `plancursoevaluacion` WRITE;
/*!40000 ALTER TABLE `plancursoevaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `plancursoevaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plancursounidad`
--

DROP TABLE IF EXISTS `plancursounidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plancursounidad` (
  `idPlanUnidad` int NOT NULL AUTO_INCREMENT,
  `unidad` int DEFAULT NULL,
  `tema` varchar(255) DEFAULT NULL,
  `fechas` varchar(255) DEFAULT NULL,
  `idPlanCurso` int DEFAULT NULL,
  PRIMARY KEY (`idPlanUnidad`),
  KEY `fk_pcu_1` (`idPlanCurso`),
  CONSTRAINT `fk_pcu_1` FOREIGN KEY (`idPlanCurso`) REFERENCES `plancurso` (`idPlanCurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plancursounidad`
--

LOCK TABLES `plancursounidad` WRITE;
/*!40000 ALTER TABLE `plancursounidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `plancursounidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programaestudio`
--

DROP TABLE IF EXISTS `programaestudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programaestudio` (
  `idProgramaEstudio` int NOT NULL AUTO_INCREMENT,
  `idExperienciaEducativa` int DEFAULT NULL,
  `idCarrera` int DEFAULT NULL,
  `idCampus` int DEFAULT NULL,
  `idAreaAcademica` int DEFAULT NULL,
  `codigo` varchar(10) DEFAULT NULL,
  `creditos` int DEFAULT NULL,
  `modalidad` varchar(255) DEFAULT NULL,
  `oportunidades` varchar(255) DEFAULT NULL,
  `fechaElaboracion` date DEFAULT NULL,
  `fechaMoficacion` date DEFAULT NULL,
  `fechaAprobacion` date DEFAULT NULL,
  `perfil` varchar(255) DEFAULT NULL,
  `saberTeorico` varchar(255) DEFAULT NULL,
  `saberHeuristico` varchar(255) DEFAULT NULL,
  `saberAxiologico` varchar(255) DEFAULT NULL,
  `acreditacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProgramaEstudio`),
  KEY `fk_pe_1` (`idExperienciaEducativa`),
  KEY `fk_pe_2` (`idCarrera`),
  KEY `fk_pe_3` (`idCampus`),
  KEY `fk_pe_4` (`idAreaAcademica`),
  CONSTRAINT `fk_pe_1` FOREIGN KEY (`idExperienciaEducativa`) REFERENCES `experienciaeducativa` (`idExperienciaEducativa`),
  CONSTRAINT `fk_pe_2` FOREIGN KEY (`idCarrera`) REFERENCES `carrera` (`idCarrera`),
  CONSTRAINT `fk_pe_3` FOREIGN KEY (`idCampus`) REFERENCES `campus` (`idCampus`),
  CONSTRAINT `fk_pe_4` FOREIGN KEY (`idAreaAcademica`) REFERENCES `areaacademica` (`idAreaAcademica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programaestudio`
--

LOCK TABLES `programaestudio` WRITE;
/*!40000 ALTER TABLE `programaestudio` DISABLE KEYS */;
/*!40000 ALTER TABLE `programaestudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programaestudioacademico`
--

DROP TABLE IF EXISTS `programaestudioacademico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programaestudioacademico` (
  `idProgramaEstudio` int NOT NULL,
  `idAcademico` int NOT NULL,
  PRIMARY KEY (`idProgramaEstudio`,`idAcademico`),
  KEY `fk_pae_2` (`idAcademico`),
  CONSTRAINT `fk_pae_1` FOREIGN KEY (`idProgramaEstudio`) REFERENCES `programaestudio` (`idProgramaEstudio`),
  CONSTRAINT `fk_pae_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programaestudioacademico`
--

LOCK TABLES `programaestudioacademico` WRITE;
/*!40000 ALTER TABLE `programaestudioacademico` DISABLE KEYS */;
/*!40000 ALTER TABLE `programaestudioacademico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programaestudiobibliografia`
--

DROP TABLE IF EXISTS `programaestudiobibliografia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programaestudiobibliografia` (
  `idProgramaBibliografia` int NOT NULL AUTO_INCREMENT,
  `autor` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `editorial` varchar(255) DEFAULT NULL,
  `anio` int DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  `idProgramaEstudio` int DEFAULT NULL,
  PRIMARY KEY (`idProgramaBibliografia`) USING BTREE,
  KEY `fk_pcb_1` (`tipo`),
  KEY `fk_peb_1` (`idProgramaEstudio`),
  CONSTRAINT `fk_peb_1` FOREIGN KEY (`idProgramaEstudio`) REFERENCES `programaestudio` (`idProgramaEstudio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programaestudiobibliografia`
--

LOCK TABLES `programaestudiobibliografia` WRITE;
/*!40000 ALTER TABLE `programaestudiobibliografia` DISABLE KEYS */;
/*!40000 ALTER TABLE `programaestudiobibliografia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programaestudioevaluacion`
--

DROP TABLE IF EXISTS `programaestudioevaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programaestudioevaluacion` (
  `idProgramaEvaluacion` int NOT NULL,
  `evidencia` varchar(255) DEFAULT NULL,
  `criterio` varchar(255) DEFAULT NULL,
  `ambito` varchar(255) DEFAULT NULL,
  `porcentaje` int DEFAULT NULL,
  `idProgramaEstudio` int DEFAULT NULL,
  PRIMARY KEY (`idProgramaEvaluacion`),
  KEY `fk_pee_1` (`idProgramaEstudio`),
  CONSTRAINT `fk_pee_1` FOREIGN KEY (`idProgramaEstudio`) REFERENCES `programaestudio` (`idProgramaEstudio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programaestudioevaluacion`
--

LOCK TABLES `programaestudioevaluacion` WRITE;
/*!40000 ALTER TABLE `programaestudioevaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `programaestudioevaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int NOT NULL AUTO_INCREMENT,
  `tipoRol` varchar(20) DEFAULT NULL,
  `numeroPersonal` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (5,'Director','33337'),(40,'Representante','44556'),(42,'Docente','09184'),(43,'Docente','27890'),(46,'Representante','12091'),(48,'Coordinador','45678'),(49,'Docente','11008'),(52,'Docente','13182'),(53,'Coordinador','09120');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajorecepcional`
--

DROP TABLE IF EXISTS `trabajorecepcional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajorecepcional` (
  `idTrabajoRecepcional` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fechaRegistro` date DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estatus` varchar(50) DEFAULT NULL,
  `idAlumno` int DEFAULT NULL,
  `idDirector` int DEFAULT NULL,
  `idCoDirector` int DEFAULT NULL,
  `idSinodal` int DEFAULT NULL,
  PRIMARY KEY (`idTrabajoRecepcional`),
  KEY `fk_tra_re1` (`idAlumno`),
  KEY `fk_tra_re2` (`idDirector`),
  KEY `fk_tra_re3` (`idCoDirector`),
  KEY `fk_tra_re4` (`idSinodal`),
  CONSTRAINT `fk_tra_re1` FOREIGN KEY (`idAlumno`) REFERENCES `estudiante` (`idEstudiante`),
  CONSTRAINT `fk_tra_re2` FOREIGN KEY (`idDirector`) REFERENCES `academico` (`idAcademico`),
  CONSTRAINT `fk_tra_re3` FOREIGN KEY (`idCoDirector`) REFERENCES `academico` (`idAcademico`),
  CONSTRAINT `fk_tra_re4` FOREIGN KEY (`idSinodal`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajorecepcional`
--

LOCK TABLES `trabajorecepcional` WRITE;
/*!40000 ALTER TABLE `trabajorecepcional` DISABLE KEYS */;
INSERT INTO `trabajorecepcional` VALUES (1,'Enfocado a la programación segura','2021-01-11','Técnicas de programación defensiva','Activo',1,48,54,47),(3,'Enfocado al manejo de bases de datos','2021-01-09','Manejo avanzado de BD','Activo',2,54,47,48);
/*!40000 ALTER TABLE `trabajorecepcional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `correo` varchar(100) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `idRol` int DEFAULT NULL,
  `idAcademico` int DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_usuario_1` (`idRol`),
  KEY `fk_usuario_2` (`idAcademico`),
  CONSTRAINT `fk_usuario_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `fk_usuario_2` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idAcademico`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (5,'obrador@gmail.com','mexicoUnido',5,5),(36,'martin@gmail.com','mart09_34',40,45),(38,'peña@gmail.com','exPresidente',42,47),(39,'xavier@gmail.com','xavier34',43,48),(42,'gabriel@gmail.com','gbgb13.12',46,51),(44,'gladys@gmail.com','gladys26*1',48,53),(46,'matias@gmail.com','matias3.',49,54),(49,'mario@gmail.com','MARIO13_23',52,57),(50,'reyes@uv.mx','jorge12_09',53,58);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-14 15:28:04
