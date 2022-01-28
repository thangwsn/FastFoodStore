-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: tsdv_fastfood
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `ID` varchar(255) NOT NULL,
  `CustomerID` varchar(255) NOT NULL,
  `Total` double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKCart195823` (`CustomerID`),
  CONSTRAINT `FKCart195823` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES ('1cfaeec6-628b-4659-8bc1-184e3a0b71cb','1cfaeec6-628b-4659-8bc1-184e3a0b71cb',0),('482be240-2f6a-4ebb-903e-f6ee37bdd3cc','482be240-2f6a-4ebb-903e-f6ee37bdd3cc',0),('6a042823-bdc7-4495-9307-8cf0ab74048c','6a042823-bdc7-4495-9307-8cf0ab74048c',0),('95fd1673-31cc-40b1-8ad6-26b2d96d5365','95fd1673-31cc-40b1-8ad6-26b2d96d5365',0),('a2404d9b-b613-44ec-89a5-7a7175acc0ec','a2404d9b-b613-44ec-89a5-7a7175acc0ec',0),('dd00b690-c717-447e-bb79-966fdd60b3a0','dd00b690-c717-447e-bb79-966fdd60b3a0',0),('dd53df61-817e-46ad-9589-c30d84b73d82','dd53df61-817e-46ad-9589-c30d84b73d82',0);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartline`
--

DROP TABLE IF EXISTS `cartline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartline` (
  `ID` varchar(255) NOT NULL,
  `CartID` varchar(255) NOT NULL,
  `FoodID` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKCartLine281390` (`CartID`),
  KEY `FKCartLine875385` (`FoodID`),
  CONSTRAINT `FKCartLine281390` FOREIGN KEY (`CartID`) REFERENCES `cart` (`ID`),
  CONSTRAINT `FKCartLine875385` FOREIGN KEY (`FoodID`) REFERENCES `food` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartline`
--

LOCK TABLES `cartline` WRITE;
/*!40000 ALTER TABLE `cartline` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `FoodQuantity` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Combo',4),(2,'Other',4),(3,'Chicken',5),(4,'Burger',4),(5,'Potato',2),(6,'Drink',4);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combofood`
--

DROP TABLE IF EXISTS `combofood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combofood` (
  `ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FKComboFood271212` FOREIGN KEY (`ID`) REFERENCES `food` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combofood`
--

LOCK TABLES `combofood` WRITE;
/*!40000 ALTER TABLE `combofood` DISABLE KEYS */;
INSERT INTO `combofood` VALUES (62),(63),(64),(65);
/*!40000 ALTER TABLE `combofood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comboline`
--

DROP TABLE IF EXISTS `comboline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comboline` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ComboFoodID` int NOT NULL,
  `SingleFoodID` int NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKComboLine796344` (`ComboFoodID`),
  KEY `FKComboLine168541` (`SingleFoodID`),
  CONSTRAINT `FKComboLine168541` FOREIGN KEY (`SingleFoodID`) REFERENCES `singlefood` (`ID`),
  CONSTRAINT `FKComboLine796344` FOREIGN KEY (`ComboFoodID`) REFERENCES `combofood` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comboline`
--

LOCK TABLES `comboline` WRITE;
/*!40000 ALTER TABLE `comboline` DISABLE KEYS */;
INSERT INTO `comboline` VALUES (1,62,52,4),(2,62,50,1),(3,62,51,1),(4,62,53,1),(5,62,56,4),(6,63,52,2),(7,63,48,1),(8,63,54,1),(9,63,55,2),(10,64,51,1),(11,64,54,1),(12,64,56,1),(13,65,52,2),(14,65,48,1),(15,65,50,1),(16,65,53,1),(17,65,56,2);
/*!40000 ALTER TABLE `comboline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ID` varchar(255) NOT NULL,
  `Level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FKCustomer470656` FOREIGN KEY (`ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('1cfaeec6-628b-4659-8bc1-184e3a0b71cb','Member'),('482be240-2f6a-4ebb-903e-f6ee37bdd3cc','Silver'),('687053dd-8d44-49d1-8bdf-adf4f67cfbf0','Member'),('6a042823-bdc7-4495-9307-8cf0ab74048c','Member'),('8eaec8b5-4754-4582-ab47-feea79b3e63b','Member'),('95fd1673-31cc-40b1-8ad6-26b2d96d5365','Member'),('a2404d9b-b613-44ec-89a5-7a7175acc0ec','Member'),('ca8f0227-97f0-4caf-b3e6-7e5984b0c0dc','Member'),('da0c2431-85da-4f6d-8093-49e6e539a4e0','Member'),('dd00b690-c717-447e-bb79-966fdd60b3a0','Silver'),('dd53df61-817e-46ad-9589-c30d84b73d82','Member');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Price` double NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `SoldQuantity` int NOT NULL,
  `Image` varchar(1000) DEFAULT NULL,
  `CategoryID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_food_category_idx` (`CategoryID`),
  CONSTRAINT `fk_food_category` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (44,300000,'Gà Nướng - 9 Miếng','Gà Nướng - 9 Miếng\r\nGà Nướng - 9 Miếng\r\nGà Nướng - 9 Miếng\r\nGà Nướng - 9 Miếng',1,'2dedca98-477a-4bea-8f96-9aedd3f21d8dchicken-534x374px_grilled-chicken_2.png',3),(45,102000,'Gà Sốt H&S - 3 Miếng','Gà Sốt H&S - 3 Miếng\r\nGà Sốt H&S - 3 Miếng\r\nGà Sốt H&S - 3 Miếng\r\nGà Sốt H&S - 3 Miếng\r\nGà Sốt H&S - 3 Miếng',3,'ed0ef80d-7ec7-4562-bf6f-139756138ec0chicken-534x374px_hs-3.png',3),(46,32000,'Gà Nướng - 1 Miếng','Gà Nướng - 1 Miếng\r\nGà Nướng - 1 Miếng\r\nGà Nướng - 1 Miếng\r\nGà Nướng - 1 Miếng\r\nGà Nướng - 1 Miếng',0,'57c806eb-6a40-4756-b0b6-ada0be29c388chicken-534x374px_grilled-1.png',3),(47,32000,'Gà Sốt Đậu - 1 Miếng','Gà Sốt Đậu - 1 Miếng\r\nGà Sốt Đậu - 1 Miếng\r\nGà Sốt Đậu - 1 Miếng\r\nGà Sốt Đậu - 1 Miếng\r\nGà Sốt Đậu - 1 Miếng',1,'da45592b-17fe-4bac-a77c-94a6ff1c2951chicken-534x374px_soybean-1.png',3),(48,55000,'Burger Mozzarella','Burger Mozzarella\r\nBurger Mozzarella\r\nBurger Mozzarella\r\nBurger Mozzarella\r\nBurger Mozzarella',0,'c57ddd66-09d0-4cb0-93f5-2b45c9d1758dburger-534x374px_mozzarella-burger.png',4),(49,59000,'Burger Double Double','Burger Double Double\r\nBurger Double Double\r\nBurger Double Double\r\nBurger Double Double\r\nBurger Double Double',6,'7553b483-74f5-4904-9c9d-6397843d5e55burger-534x374px_ddouble-burger.png',4),(50,40000,'Burger Tôm','Burger Tôm\r\nBurger Tôm\r\nBurger Tôm\r\nBurger Tôm\r\nBurger Tôm',1,'ff995176-a44c-463e-b619-86ada4aeef4cburger-534x374px_shrimp-burger.png',4),(51,32000,'Burger Bò Teriyaki','Burger Bò Teriyaki\r\nBurger Bò Teriyaki\r\nBurger Bò Teriyaki\r\nBurger Bò Teriyaki\r\nBurger Bò Teriyaki',3,'e37550a2-a0fb-4ca1-af40-ee983c3ebb0cburger-534x374px_beef-teriyaki-burger.png',4),(52,30000,'Gà Rán - 1 Miếng','Gà Rán - 1 Miếng\r\nGà Rán - 1 Miếng\r\nGà Rán - 1 Miếng\r\nGà Rán - 1 Miếng\r\nGà Rán - 1 Miếng',1,'7b67a64c-b164-472a-86fc-94b20b7de07echicken-534x374px_fried-1.png',3),(53,40000,'Khoai Tây Lắc','Khoai Tây Lắc\r\nKhoai Tây Lắc\r\nKhoai Tây Lắc\r\nKhoai Tây Lắc\r\nKhoai Tây Lắc',1,'2aa01828-02b8-492b-a190-3c886a2f7b80dessert-534x374px_shake-potato.png',5),(54,23000,'Khoai Tây Chiên (M)','Khoai Tây Chiên (M)\r\nKhoai Tây Chiên (M)\r\nKhoai Tây Chiên (M)\r\nKhoai Tây Chiên (M)\r\nKhoai Tây Chiên (M)',0,'65c9009f-8528-410d-9db9-2c74c560478adessert-534x374px_frenchfries.png',5),(55,25000,'Nước Cam','Nước Cam\r\nNước Cam\r\nNước Cam\r\nNước Cam\r\nNước Cam',1,'8a14e15c-e8c5-4f06-a5cb-eb5a78f5e4dedrink-534x374px_orange.png',6),(56,12000,'7 UP (M)','7 UP (M)\r\n7 UP (M)\r\n7 UP (M)\r\n7 UP (M)\r\n7 UP (M)',2,'b810934d-4acd-4671-a655-0556d1fe30d7drink-534x374px_pepsi_1.png',6),(57,24000,'Tornado Blueberry','Tornado Blueberry\r\nTornado Blueberry\r\nTornado Blueberry\r\nTornado Blueberry\r\nTornado Blueberry\r\n',1,'e37d862e-52f9-409f-b2c8-39401dae2923drink-534x374px_tornado-blueberry.png',6),(58,20000,'Milo','Milo\r\nMilo\r\nMilo\r\nMilo',7,'c7d99822-7c5f-45cd-aae8-bc90a6e20642drink-534x374px_milo.png',6),(59,30000,'Bánh mì bò phô mai','Bánh mì bò phô mai\r\nBánh mì bò phô mai\r\nBánh mì bò phô mai\r\nBánh mì bò phô mai',4,'03835c39-cfa7-4aa9-beaa-f55919c16858foody-upload-api-foody-mobile-minh-nhat-jpg-180831092255.jpg',2),(60,25000,'Bánh mì pate chà bông','Bánh mì pate chà bông\r\nBánh mì pate chà bông\r\nBánh mì pate chà bông\r\nBánh mì pate chà bông\r\nBánh mì pate chà bông',0,'92fadfd8-7c64-4003-9280-7f3f5f18bfe2banh_mi__pate_cha_bong_master.jpg',2),(61,26000,'Phô Mai Que','Phô Mai Que\r\nPhô Mai Que\r\nPhô Mai Que\r\nPhô Mai Que\r\nPhô Mai Que',4,'c89094e0-ddbd-45bc-9267-f57111504d80dessert-534x374px_cheese-stick.png',2),(62,252000,'Family Set ','Family set bao gồm: 04 Gà rán truyền thống, 01 Burger Bò Teriyaki, 01 Burger Tôm, 01 Khoai tây lắc, 04 7 UP (M)',2,'2df2a297-7e5d-4a94-ae3d-024148e67248pack-534x374px_familyset.png',1),(63,169200,'Couple Set 1','Couple Set 1 bao gồm: 02 Gà rán truyền thống, 01 Burger Mozzarella, 01 Khoai tây chiên (M), 02 Nước Cam',3,'1b1df415-6420-4475-abfd-6dd0a623f16cpack-534x374px_coupleset1.png',1),(64,60300,'Combo Burger Bò Teriyaki','01 Burger Bò Teriyaki (Teriyaki Burger), 01 7 UP (M), 01 Khoai Tây Chiên (French Fries) - M',1,'18a30d7e-1a09-416c-a857-6435d689a5ddcombo-burger-534x374px_beef-teriyaki-burger_1.png',1),(65,197100,'Special Set','Special Set bao gồm: 02 Gà rán truyền thống, 01 Burger Mozzarella, 01 Burger Tôm, 01 Khoai tây lắc, 02 7 UP (M)',2,'642323ba-d05f-4c01-b2bf-3fbb1ad31425pack-534x374px_specialset.png',1),(67,10000,'Test','dhdhf',0,'39fa206d-a3b7-484f-9a48-884622095c8fdessert-534x374px_sweet-potato_1.png',2);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderline`
--

DROP TABLE IF EXISTS `orderline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderline` (
  `ID` varchar(255) NOT NULL,
  `FoodID` int NOT NULL,
  `OrderID` varchar(255) NOT NULL,
  `Quantity` int NOT NULL,
  `Price` double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKOrderLine150838` (`OrderID`),
  KEY `FKOrderLine663494` (`FoodID`),
  CONSTRAINT `FKOrderLine150838` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`ID`),
  CONSTRAINT `FKOrderLine663494` FOREIGN KEY (`FoodID`) REFERENCES `food` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderline`
--

LOCK TABLES `orderline` WRITE;
/*!40000 ALTER TABLE `orderline` DISABLE KEYS */;
INSERT INTO `orderline` VALUES ('09a45a32-de9f-4322-8be4-052fbe700f77',49,'a83fc409-2771-4046-8daf-5f132e964315',1,59000),('0cdf2fc7-0804-44f6-94d0-e57d0c162541',47,'b9d8e3fc-f80f-444f-9ed7-642a332f7158',1,32000),('11b785b5-af92-4104-8cd3-dd6878afc4b0',49,'991515ce-6f55-4a18-9175-c256a9c2f39c',4,59000),('1723666b-94be-457c-b161-05e6ece014f7',61,'9894e6a8-6ce3-4892-84a6-267463149b83',1,26000),('1cd2bfbf-9a5f-463d-ab5a-7a4cec8fa7a4',51,'7b8a8c3e-9357-4e81-8616-a77104f4f90b',3,32000),('33bd6ebd-8867-447b-891c-752a946cb539',61,'5a45a4d9-0140-45de-84ee-b86f3dcd5686',1,26000),('40ddd610-79a7-4640-988b-8368b7b5389b',65,'677fdfe7-f2cc-4e22-bf95-b611f3e2e6ff',2,197100),('5353bc2e-eba0-4ec1-a985-a45807a5e432',56,'ec58ca95-8789-4c05-a2b3-6ec57b747f54',2,12000),('73b65c7e-6aac-473b-ad67-cae116b1ea74',55,'b9d8e3fc-f80f-444f-9ed7-642a332f7158',1,25000),('7cdbea13-7331-4402-aa9a-73d1fc4e0340',58,'5a45a4d9-0140-45de-84ee-b86f3dcd5686',2,20000),('85f99438-c65b-4694-9b0b-96ded4ca2365',45,'bf14cc8d-4d64-453d-9a3b-bf63437cec52',1,102000),('87222c86-b999-445e-b67e-37e5ac0f996a',61,'3ba7cb0a-9e40-4a80-a2a2-bfa34c5e002c',2,26000),('87b6a57a-0223-4e5f-b336-9a8277f0c1e4',63,'3262beb4-4cf3-4b30-b809-1fbd505c77db',1,169200),('8e9f261e-0cb1-49a9-af54-0ca740901129',64,'deaed77a-a263-444b-8c0e-66ab29c7d833',1,60300),('92efb00d-b6a7-4613-abe8-cca1ce0107d6',58,'9845ccd4-0861-46f6-b4c9-e37d36fd449d',3,20000),('bb3ba97b-6c49-4fff-a908-3347141a4f4f',63,'3ba7cb0a-9e40-4a80-a2a2-bfa34c5e002c',1,169200),('c1d22835-43b8-4791-a5b3-ca0870b608ec',63,'453e622f-08a4-4651-9a8d-7a53ce7391d0',1,169200),('c2c97903-8db0-4956-a2a4-b9102684cbc9',53,'9845ccd4-0861-46f6-b4c9-e37d36fd449d',1,40000),('c96f0f2e-6c39-4d75-897d-70b4ae1b0d63',59,'ec58ca95-8789-4c05-a2b3-6ec57b747f54',4,30000),('d4a963d9-b849-40ee-aff0-77b313efe75f',45,'d6e5b0ba-4758-48bb-ade1-28ab74dbd20d',2,102000),('d536fda0-1af5-46a8-b8a8-2b226d367dd0',50,'8d57471f-6d68-43bc-88bf-f90ba022f6d0',1,40000),('d5981a34-ce8d-49df-a70b-478e239ff332',57,'a83fc409-2771-4046-8daf-5f132e964315',1,24000),('d779cdcb-6226-4600-861d-634649034caa',62,'33c02a73-d8d1-43c3-bbcc-03d55957e8d7',1,252000),('d89f5d8d-3ea9-4472-b8d8-41b46516682f',58,'33c02a73-d8d1-43c3-bbcc-03d55957e8d7',2,20000),('dc4ed595-3ff0-46c5-b0b4-0d50733ea373',49,'e46d8ebb-5b4e-4c14-89d3-18d78c482504',1,59000),('f7ac380e-a4d1-471e-8b81-495c672f85a9',44,'c301f5aa-ca40-49a4-b9b6-5e1c1cf35908',1,300000),('f971f0ca-4e80-447c-b496-e4928f462d9f',52,'ec58ca95-8789-4c05-a2b3-6ec57b747f54',1,30000),('fd083643-c592-4e2d-88ac-526188be7367',62,'9894e6a8-6ce3-4892-84a6-267463149b83',1,252000);
/*!40000 ALTER TABLE `orderline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ID` varchar(255) NOT NULL,
  `CustomerID` varchar(255) NOT NULL,
  `CreateTime` datetime NOT NULL,
  `Total` double NOT NULL,
  `StatusCustomer` varchar(255) NOT NULL,
  `PaymentID` varchar(255) NOT NULL,
  `ShipmentID` varchar(255) NOT NULL,
  `StatusStaff` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKOrder556711` (`CustomerID`),
  KEY `FKOrder92191` (`PaymentID`),
  KEY `FKOrder751924` (`ShipmentID`),
  CONSTRAINT `FKOrder556711` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`ID`),
  CONSTRAINT `FKOrder751924` FOREIGN KEY (`ShipmentID`) REFERENCES `shipment` (`ID`),
  CONSTRAINT `FKOrder92191` FOREIGN KEY (`PaymentID`) REFERENCES `payment` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('3262beb4-4cf3-4b30-b809-1fbd505c77db','dd53df61-817e-46ad-9589-c30d84b73d82','2022-01-23 23:52:53',169200,'Pending','89978345-6246-4942-9a8e-105ad52f9641','368fe073-3356-48f1-80e9-73adfe4ac01f','Pending'),('33c02a73-d8d1-43c3-bbcc-03d55957e8d7','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-24 11:00:15',292000,'Pending','888cc3f2-5135-46ef-9455-484d543abce0','d6cd873d-15bd-4911-9ee8-32bcafb21877','Pending'),('3ba7cb0a-9e40-4a80-a2a2-bfa34c5e002c','1cfaeec6-628b-4659-8bc1-184e3a0b71cb','2022-01-24 07:03:27',221200,'Pending','d205008b-81b7-4539-96f7-748a945b6709','dea9425d-d342-426a-b4b2-ea1cfef32ff8','Pending'),('453e622f-08a4-4651-9a8d-7a53ce7391d0','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-24 11:01:05',169200,'Pending','bbee6467-8468-40c9-833f-ffb1e24ecdf7','a3d462a7-9cb2-4e69-ad58-d3dd06072e2e','Completed'),('5a45a4d9-0140-45de-84ee-b86f3dcd5686','95fd1673-31cc-40b1-8ad6-26b2d96d5365','2022-01-24 09:12:33',66000,'Pending','c1c4a5f8-c757-4c58-9e14-000ee01b0976','3f763b36-27d2-4cb9-ae38-66066d51744d','Pending'),('677fdfe7-f2cc-4e22-bf95-b611f3e2e6ff','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-24 11:12:27',394200,'Completed','9e516f94-f799-4300-af76-f6017b6077fc','19e96a26-3173-4060-a5ed-25559a8d8be7','Pending'),('7b8a8c3e-9357-4e81-8616-a77104f4f90b','dd00b690-c717-447e-bb79-966fdd60b3a0','2022-01-24 07:15:49',96000,'Pending','8873de99-5178-4288-9a7d-b0071a8c4994','e52a7d2c-c3ab-4329-9baf-4243685955bf','Pending'),('8d57471f-6d68-43bc-88bf-f90ba022f6d0','6a042823-bdc7-4495-9307-8cf0ab74048c','2022-01-23 22:13:48',40000,'Pending','6f11e6af-f011-4399-aaa2-ba3680c01c45','69d69567-2fb7-45cd-b96b-9df0fb2dddb6','Pending'),('9845ccd4-0861-46f6-b4c9-e37d36fd449d','a2404d9b-b613-44ec-89a5-7a7175acc0ec','2022-01-23 23:27:13',100000,'Pending','7110e891-118e-45f6-9de2-b5868dab0a24','ad96a166-8f63-44dd-858f-944daa679017','Pending'),('9894e6a8-6ce3-4892-84a6-267463149b83','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-23 14:55:08',278000,'Completed','bf5928a0-2f53-42f8-bd0f-a0a178149f95','8e53249b-28ca-4bfe-9acb-7a474cee3700','Completed'),('991515ce-6f55-4a18-9175-c256a9c2f39c','dd53df61-817e-46ad-9589-c30d84b73d82','2022-01-23 23:52:24',236000,'Pending','68386f7c-2b76-4f26-a361-387f4557b4c4','2cbdc770-6915-4efd-ba45-950e2e3f605e','Pending'),('a83fc409-2771-4046-8daf-5f132e964315','6a042823-bdc7-4495-9307-8cf0ab74048c','2022-01-23 22:16:45',83000,'Pending','0731e715-c534-4c60-9bee-7d6b416647ae','153c124e-a9c9-4b42-9529-ddfb2faf5f36','Pending'),('b9d8e3fc-f80f-444f-9ed7-642a332f7158','1cfaeec6-628b-4659-8bc1-184e3a0b71cb','2022-01-24 07:04:04',57000,'Pending','ee721da8-432d-4436-b660-d57839422c7f','f0aa671e-9b0c-4db7-98bf-fa6f2e55cdd2','Pending'),('bf14cc8d-4d64-453d-9a3b-bf63437cec52','dd00b690-c717-447e-bb79-966fdd60b3a0','2022-01-24 07:16:30',102000,'Pending','762b412c-e1a4-42e3-bd13-fd59a6c96c83','53d1b01d-972c-4c99-8274-5e524cd5eb72','Pending'),('c301f5aa-ca40-49a4-b9b6-5e1c1cf35908','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-23 14:51:01',300000,'Completed','3a7ca8e7-6e29-40d7-8fff-6903c766752f','f5515f27-cd82-447b-a439-60aca81bc2b4','Completed'),('d6e5b0ba-4758-48bb-ade1-28ab74dbd20d','6a042823-bdc7-4495-9307-8cf0ab74048c','2022-01-23 23:56:09',204000,'Pending','880d6988-4b60-443b-b78f-52676371902a','c333ba9a-acfd-4985-a84b-4c23d8c55b96','Pending'),('deaed77a-a263-444b-8c0e-66ab29c7d833','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-24 10:28:52',60300,'Pending','c9ae0c33-9057-4689-8ea0-56b10223d317','bc30bd3e-ece4-4e87-ab5d-2861d6e1e2f0','Pending'),('e46d8ebb-5b4e-4c14-89d3-18d78c482504','482be240-2f6a-4ebb-903e-f6ee37bdd3cc','2022-01-24 10:27:46',59000,'Pending','e20c3e31-dd48-4961-a102-af67553feca1','d829d29f-fcd8-47e3-8a25-1943f156acb3','Pending'),('ec58ca95-8789-4c05-a2b3-6ec57b747f54','a2404d9b-b613-44ec-89a5-7a7175acc0ec','2022-01-23 23:31:46',174000,'Pending','9cb1254a-676e-430b-a905-7ddf21a01569','8723ac80-aa91-4f6b-b6e3-ae61a1dbc711','Pending');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `ID` varchar(255) NOT NULL,
  `Amount` double NOT NULL,
  `Method` varchar(255) NOT NULL,
  `Status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('0731e715-c534-4c60-9bee-7d6b416647ae',103000,'Check','Paid'),('3a7ca8e7-6e29-40d7-8fff-6903c766752f',320000,'COD','Paid'),('68386f7c-2b76-4f26-a361-387f4557b4c4',266000,'COD','Unpaid'),('6f11e6af-f011-4399-aaa2-ba3680c01c45',60000,'Check','Unpaid'),('7110e891-118e-45f6-9de2-b5868dab0a24',130000,'COD','Unpaid'),('762b412c-e1a4-42e3-bd13-fd59a6c96c83',122000,'Check','Unpaid'),('880d6988-4b60-443b-b78f-52676371902a',229000,'Check','Paid'),('8873de99-5178-4288-9a7d-b0071a8c4994',116000,'COD','Unpaid'),('888cc3f2-5135-46ef-9455-484d543abce0',322000,'Check','Paid'),('89978345-6246-4942-9a8e-105ad52f9641',194200,'Check','Paid'),('9cb1254a-676e-430b-a905-7ddf21a01569',199000,'Check','Paid'),('9e516f94-f799-4300-af76-f6017b6077fc',414200,'Check','Paid'),('bbee6467-8468-40c9-833f-ffb1e24ecdf7',189200,'COD','Paid'),('bf5928a0-2f53-42f8-bd0f-a0a178149f95',298000,'COD','Paid'),('c1c4a5f8-c757-4c58-9e14-000ee01b0976',91000,'Check','Paid'),('c9ae0c33-9057-4689-8ea0-56b10223d317',80300,'Check','Paid'),('d205008b-81b7-4539-96f7-748a945b6709',251200,'COD','Unpaid'),('e20c3e31-dd48-4961-a102-af67553feca1',84000,'Check','Unpaid'),('ee721da8-432d-4436-b660-d57839422c7f',77000,'Check','Paid');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `ID` varchar(255) NOT NULL,
  `FullName` varchar(255) NOT NULL,
  `Mobile` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Note` varchar(255) NOT NULL,
  `ShipmentType` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES ('153c124e-a9c9-4b42-9529-ddfb2faf5f36','Quan Nguyen Van','8584585853','Mo Lao, Ha Dong, Ha Noi','','GHTK'),('19e96a26-3173-4060-a5ed-25559a8d8be7','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','GHTK'),('2cbdc770-6915-4efd-ba45-950e2e3f605e','Tran Thi Ngoc Anh','0099556556','Thanh Xuan Bac, Thanh Xuan, Ha Noi','','SPX'),('368fe073-3356-48f1-80e9-73adfe4ac01f','Tran Thi Ngoc Anh','0099556556','Thanh Xuan Bac, Thanh Xuan, Ha Noi','','GHN'),('3f763b36-27d2-4cb9-ae38-66066d51744d','Tran Van Nam','0987678998','Xa La, Ha Dong, Ha Noi','','GHN'),('53d1b01d-972c-4c99-8274-5e524cd5eb72','Do Hung Anh','0945858833','Tan Trieu, Thanh Tri, Ha Noi','','GHTK'),('69d69567-2fb7-45cd-b96b-9df0fb2dddb6','Quan Nguyen Van','8584585853','Mo Lao, Ha Dong, Ha Noi','','GHTK'),('8723ac80-aa91-4f6b-b6e3-ae61a1dbc711','Huyen Tran','5675646566','Tien Thang, Ly Nhan, Ha Nam','','GHN'),('8e53249b-28ca-4bfe-9acb-7a474cee3700','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','GHTK'),('a3d462a7-9cb2-4e69-ad58-d3dd06072e2e','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','GHTK'),('ad96a166-8f63-44dd-858f-944daa679017','Huyen Tran','5675646566','Tien Thang, Ly Nhan, Ha Nam','','SPX'),('bc30bd3e-ece4-4e87-ab5d-2861d6e1e2f0','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','GHTK'),('c333ba9a-acfd-4985-a84b-4c23d8c55b96','Quan Nguyen Van','8584585853','Mo Lao, Ha Dong, Ha Noi','','GHN'),('d6cd873d-15bd-4911-9ee8-32bcafb21877','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','SPX'),('d829d29f-fcd8-47e3-8a25-1943f156acb3','Thang Nguyen','0854930590','Phuc La, Ha Dong, Ha Noi','','GHN'),('dea9425d-d342-426a-b4b2-ea1cfef32ff8','Tran Van Minh','0985497790','Bo De, Binh Luc, Ha Nam','','SPX'),('e52a7d2c-c3ab-4329-9baf-4243685955bf','Do Hung Anh','0945858833','Tan Trieu, Thanh Tri, Ha Noi','','GHTK'),('f0aa671e-9b0c-4db7-98bf-fa6f2e55cdd2','Tran Van Minh','0985497790','Bo De, Binh Luc, Ha Nam','','GHTK'),('f5515f27-cd82-447b-a439-60aca81bc2b4','Thang Nguyen','0854930590','43, Phuc La, Ha Dong, Ha Noi','Giao ngay','GHTK');
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singlefood`
--

DROP TABLE IF EXISTS `singlefood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `singlefood` (
  `ID` int NOT NULL,
  `Calories` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FKSingleFood83792` FOREIGN KEY (`ID`) REFERENCES `food` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singlefood`
--

LOCK TABLES `singlefood` WRITE;
/*!40000 ALTER TABLE `singlefood` DISABLE KEYS */;
INSERT INTO `singlefood` VALUES (44,1000),(45,400),(46,100),(47,360),(48,360),(49,350),(50,300),(51,290),(52,300),(53,100),(54,90),(55,100),(56,70),(57,140),(58,120),(59,250),(60,220),(61,100),(67,1000);
/*!40000 ALTER TABLE `singlefood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `ID` varchar(255) NOT NULL,
  `Position` varchar(255) NOT NULL,
  `BaseSalary` double NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FKStaff855219` FOREIGN KEY (`ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('f91a5581-b0d4-4b8d-afb0-062971c6bc38','Staff',4000000);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `FullName` varchar(255) NOT NULL,
  `Gender` varchar(255) NOT NULL,
  `Mobile` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Dob` date NOT NULL,
  `Role` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1cfaeec6-628b-4659-8bc1-184e3a0b71cb','minhtv@gmail.com','$2a$10$Dr/MVEMzGli9N2O6dJNyE.2jE52LDbCr27sq994vqxLoIYn3I8/PC','Tran Van Minh','Male','0985497790','Bo De, Binh Luc, Ha Nam','2002-01-12','Customer'),('482be240-2f6a-4ebb-903e-f6ee37bdd3cc','nguyenduythangk6@gmail.com','$2a$10$z3O9OvZbeCT2CcYKn/evZ.fxnN1FZG5IKtNBb/o0V8vbYLg3C58lO','Thang Nguyen','Male','0854930590','Phuc La, Ha Dong, Ha Noi','2000-03-11','Customer'),('687053dd-8d44-49d1-8bdf-adf4f67cfbf0','customer6@gmail.com','$2a$10$riBuVuHJcQEkIn4ZzIeQU.A82J4gB6QoEn3oeg1oP3w67lh185Yzi','Tran Van Manh','Male','0983847584','My Dinh, Nam Tu Liem, Ha Noi','1998-11-21','Customer'),('6a042823-bdc7-4495-9307-8cf0ab74048c','quannv@gmail.com','$2a$10$flaDB./2H/4DK6JvDbG.8utBWUvX2sXhBXJL16Kqq8AKZet4OAYDi','Quan Nguyen Van','Male','8584585853','Mo Lao, Ha Dong, Ha Noi','2001-02-12','Customer'),('8eaec8b5-4754-4582-ab47-feea79b3e63b','customer4@gmail.com','$2a$10$WprsKFB2PnvXCRt1zJEBuuzPOvO34PUJzj1zLXPFnq5nnF9muUou6','Ngo Thi Mai','Female','0394858384','Thuong Dinh, Thanh Xuan, Ha Noi','2000-09-01','Customer'),('95fd1673-31cc-40b1-8ad6-26b2d96d5365','customer3@gmail.com','$2a$10$g0bo.Fzz88zsl8LD/fXiQuVEtyK/BSDD3AAEJRW3RUUYNDJvYXdUm','Tran Van Nam','Male','0987678998','Xa La, Ha Dong, Ha Noi','1997-07-21','Customer'),('a2404d9b-b613-44ec-89a5-7a7175acc0ec','tranhuyen@gmail.com','$2a$10$MPNPxObYguP7FB6VV4phMeHs3Jjt/b.Wl7qR6oy88FuwZbdcCMYdK','Huyen Tran','Female','5675646566','Tien Thang, Ly Nhan, Ha Nam','1999-04-14','Customer'),('ca8f0227-97f0-4caf-b3e6-7e5984b0c0dc','customer2@gmail.com','$2a$10$cbfj1XkgpxJRkAL/M609Juv0sskVR2R1L45ZVmdpj2h8Cu6Y87DL.','Nguyen Thi Loan','Female','0938454833','Trung Hoa, Cau Giay, Ha Noi','2000-06-30','Customer'),('da0c2431-85da-4f6d-8093-49e6e539a4e0','customer5@gmail.com','$2a$10$2wZYjEUYcX/PjGFVys0XwORdLFPjcRGR8MWLEjL4nbpa.PPXQno/O','Tran Do Minh','Male','0947832000','Nguyen Trai, Thanh Xuan, Ha Noi','1999-05-20','Customer'),('dd00b690-c717-447e-bb79-966fdd60b3a0','customer1@gmail.com','$2a$10$P8ZnTV3nYo.UaoN9RVCzg.jPX.C85t6dNyYabRxgeTNrNkksOGQRO','Do Hung Anh','Male','0945858833','Tan Trieu, Thanh Tri, Ha Noi','2004-03-21','Customer'),('dd53df61-817e-46ad-9589-c30d84b73d82','anh@gmail.com','$2a$10$0daWmFChdds8mPNvc4eZN.MnhguR5qGpglcz69WwTIg1DKQrA6wWO','Tran Thi Ngoc Anh','Female','0099556556','Thanh Xuan Bac, Thanh Xuan, Ha Noi','2000-01-02','Customer'),('f91a5581-b0d4-4b8d-afb0-062971c6bc38','huyen@gmail.com','$2a$10$HO3H2eViThsBMzqy0R6HWeuj963dfXYgq7zH01lfinNtAuV1S7xJ2','Tran Thi Huyen','Female','8584585853','Phuc Dien, Bac Tu Liem, Ha Noi','2000-04-11','Staff');
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

-- Dump completed on 2022-01-29  0:37:36
