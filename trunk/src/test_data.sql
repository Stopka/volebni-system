-- phpMyAdmin SQL Dump
-- version 3.3.2deb1
-- http://www.phpmyadmin.net
--
-- Počítač: localhost
-- Vygenerováno: Středa 14. prosince 2011, 15:07
-- Verze MySQL: 5.1.41
-- Verze PHP: 5.3.2-1ubuntu4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `regsys`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `Action`
--

CREATE TABLE IF NOT EXISTS `Action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `place` varchar(45) COLLATE utf8_czech_ci DEFAULT NULL COMMENT 'kde se akce koná',
  `name` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  `description` text COLLATE utf8_czech_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=2 ;

--
-- Vypisuji data pro tabulku `Action`
--

INSERT INTO `Action` (`id`, `start`, `end`, `place`, `name`, `description`) VALUES
(1, '2011-12-14 14:00:00', '2011-12-14 20:00:00', 'ČVUT, KN:E-107', 'Softwarové inženýrství', 'Akce všech studentů, kteří navštěvují předmět A7B36SI2');

-- --------------------------------------------------------

--
-- Struktura tabulky `Additional_info`
--

CREATE TABLE IF NOT EXISTS `Additional_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  `value` text COLLATE utf8_czech_ci,
  `User_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Additional_User1` (`User_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=3 ;

--
-- Vypisuji data pro tabulku `Additional_info`
--

INSERT INTO `Additional_info` (`id`, `key`, `value`, `User_id`) VALUES
(1, 'Adresa', 'Slívová 5, Praha 10', 37),
(2, 'Telefon', '123456789', 37);

-- --------------------------------------------------------

--
-- Struktura tabulky `Admin`
--

CREATE TABLE IF NOT EXISTS `Admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  `role` varchar(10) COLLATE utf8_czech_ci NOT NULL,
  `login` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=3 ;

--
-- Vypisuji data pro tabulku `Admin`
--

INSERT INTO `Admin` (`id`, `password`, `role`, `login`) VALUES
(1, 'admin', 'SUPE', 'admin'),
(2, 'minipenis', 'SUPE', 'Lahvi');

-- --------------------------------------------------------

--
-- Struktura tabulky `Admin_manages_Action`
--

CREATE TABLE IF NOT EXISTS `Admin_manages_Action` (
  `Admin_id` bigint(20) NOT NULL,
  `Action_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Admin_id`,`Action_id`),
  KEY `fk_Admin_has_Action_Action1` (`Action_id`),
  KEY `fk_Admin_has_Action_Admin1` (`Admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Vypisuji data pro tabulku `Admin_manages_Action`
--

INSERT INTO `Admin_manages_Action` (`Admin_id`, `Action_id`) VALUES
(1, 1),
(2, 1);

-- --------------------------------------------------------

--
-- Struktura tabulky `Participation`
--

CREATE TABLE IF NOT EXISTS `Participation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Action_id` bigint(20) NOT NULL,
  `User_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Participation_Action1` (`Action_id`),
  KEY `fk_Participation_User1` (`User_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=53 ;

--
-- Vypisuji data pro tabulku `Participation`
--

INSERT INTO `Participation` (`id`, `Action_id`, `User_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11),
(12, 1, 12),
(13, 1, 13),
(14, 1, 14),
(15, 1, 15),
(16, 1, 16),
(17, 1, 17),
(18, 1, 18),
(19, 1, 19),
(20, 1, 20),
(21, 1, 21),
(22, 1, 22),
(23, 1, 23),
(24, 1, 24),
(25, 1, 25),
(26, 1, 26),
(27, 1, 27),
(28, 1, 28),
(29, 1, 29),
(30, 1, 30),
(31, 1, 31),
(32, 1, 32),
(33, 1, 33),
(34, 1, 34),
(35, 1, 35),
(36, 1, 36),
(37, 1, 37),
(38, 1, 38),
(39, 1, 39),
(40, 1, 40),
(41, 1, 41),
(42, 1, 42),
(43, 1, 43),
(44, 1, 44),
(45, 1, 45),
(46, 1, 46),
(47, 1, 47),
(48, 1, 48),
(49, 1, 49),
(50, 1, 50),
(51, 1, 51),
(52, 1, 52);

-- --------------------------------------------------------

--
-- Struktura tabulky `Presence`
--

CREATE TABLE IF NOT EXISTS `Presence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `checkin` datetime NOT NULL,
  `checkout` datetime DEFAULT NULL,
  `Participation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Presence_Participation` (`Participation_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=3 ;

--
-- Vypisuji data pro tabulku `Presence`
--

INSERT INTO `Presence` (`id`, `checkin`, `checkout`, `Participation_id`) VALUES
(1, '2011-12-14 13:23:29', NULL, 37),
(2, '2011-12-14 13:25:51', NULL, 38);

-- --------------------------------------------------------

--
-- Struktura tabulky `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `IDCard` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  `surname` varchar(45) COLLATE utf8_czech_ci NOT NULL,
  `login` varchar(45) COLLATE utf8_czech_ci DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_czech_ci DEFAULT NULL,
  `email` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=53 ;

--
-- Vypisuji data pro tabulku `User`
--

INSERT INTO `User` (`id`, `IDCard`, `name`, `surname`, `login`, `password`, `email`) VALUES
(1, 0, 'Tomáš', 'Apeltauer', NULL, NULL, 'apelttom@fel.cvut.cz'),
(2, 0, 'Pavel', 'Brož', NULL, NULL, 'brozpav1@fel.cvut.cz'),
(3, 0, 'Šárka', 'Buranská', NULL, NULL, 'buransar@fel.cvut.cz'),
(4, 0, 'Pavel', 'Čontoš', NULL, NULL, 'contopav@fel.cvut.cz'),
(5, 0, 'Evgenia', 'Filkina', NULL, NULL, 'filkievg@fel.cvut.cz'),
(6, 0, 'Aneta', 'Fuchsová', NULL, NULL, 'fuchsane@fel.cvut.cz'),
(7, 0, 'Artem', 'Golyakov', NULL, NULL, 'golyaart@fel.cvut.cz'),
(8, 0, 'Ondřej', 'Harcuba', NULL, NULL, 'harcuond@fel.cvut.cz'),
(9, 0, 'Karel', 'Helan', NULL, NULL, 'helankar@fel.cvut.cz'),
(10, 0, 'Petr', 'Hlaváček', NULL, NULL, 'hlavap13@fel.cvut.cz'),
(11, 0, 'Jan', 'Hyka', NULL, NULL, 'hykajan@fel.cvut.cz'),
(12, 0, 'Jan', 'Jakeš', NULL, NULL, 'jakesjan@fel.cvut.cz'),
(13, 0, 'Jakub', 'Ječmínek', NULL, NULL, 'jecmijak@fel.cvut.cz'),
(14, 0, 'Jiří', 'Ježek', NULL, NULL, 'jezekji1@fel.cvut.cz'),
(15, 0, 'Tomáš', 'Jiříček', NULL, NULL, 'jiricto2@fel.cvut.cz'),
(16, 0, 'Ondřej', 'Kmoch', NULL, NULL, 'kmochond@fel.cvut.cz'),
(17, 0, 'Vojtěch', 'Koukal', NULL, NULL, 'koukavoj@fel.cvut.cz'),
(18, 0, 'Zenit', 'Kovačević', NULL, NULL, 'kovaczen@fel.cvut.cz'),
(19, 0, 'Marek', 'Krátký', NULL, NULL, 'kratkma2@fel.cvut.cz'),
(20, 0, 'Ondřej', 'Kulatý', NULL, NULL, 'kulatond@fel.cvut.cz'),
(21, 0, 'Alexandr', 'Makarič', NULL, NULL, 'makaral1@fel.cvut.cz'),
(22, 0, 'Dominik', 'Mališ', NULL, NULL, 'malisdom@fel.cvut.cz'),
(23, 0, 'Erik', 'Matys', NULL, NULL, 'matyseri@fel.cvut.cz'),
(24, 0, 'Martin', 'Mazanec', NULL, NULL, 'mazanma3@fel.cvut.cz'),
(25, 0, 'Daniel', 'Meister', NULL, NULL, 'meistdan@fel.cvut.cz'),
(26, 0, 'Jan', 'Minařík', NULL, NULL, 'minarja4@fel.cvut.cz'),
(27, 0, 'Dominik', 'Moštěk', NULL, NULL, 'mostedom@fel.cvut.cz'),
(28, 0, 'Zdeněk', 'Mrázek', NULL, NULL, 'mrazezd1@fel.cvut.cz'),
(29, 0, 'Petr', 'Nejedlý', NULL, NULL, 'nejedpe1@fel.cvut.cz'),
(30, 0, 'Stanislav', 'Novák', NULL, NULL, 'novakst6@fel.cvut.cz'),
(31, 0, 'Petr', 'Pavlík', NULL, NULL, 'pavlipe7@fel.cvut.cz'),
(32, 0, 'Tomáš', 'Poledný', NULL, NULL, 'poledtom@fel.cvut.cz'),
(33, 0, 'Tomáš', 'Přasličák', NULL, NULL, 'prasltom@fel.cvut.cz'),
(34, 0, 'Kamil', 'Procházka', NULL, NULL, 'prochka6@fel.cvut.cz'),
(35, 0, 'Insar', 'Ryspekov', NULL, NULL, 'ryspeins@fel.cvut.cz'),
(36, 0, 'Ondřej', 'Šatera', NULL, NULL, 'saterond@fel.cvut.cz'),
(37, 0, 'Štěpán', 'Škorpil', 'skorpste', 'heslo', 'skorpste@fel.cvut.cz'),
(38, 0, 'Roman', 'Smetana', 'smetarom', 'heslo', 'smetarom@fel.cvut.cz'),
(39, 0, 'Robert', 'Soják', NULL, NULL, 'sojakrob@fel.cvut.cz'),
(40, 0, 'Olessya', 'Solovyeva', NULL, NULL, 'solovole@fel.cvut.cz'),
(41, 0, 'Marcel', 'Soukeník', NULL, NULL, 'soukema4@fel.cvut.cz'),
(42, 0, 'Martin', 'Štajner', NULL, NULL, 'stajnmar@fel.cvut.cz'),
(43, 0, 'Petr', 'Šuták', NULL, NULL, 'sutakpet@fel.cvut.cz'),
(44, 0, 'Matúš', 'Szépe', NULL, NULL, 'szepemat@fel.cvut.cz'),
(45, 0, 'Petr', 'Tarant', NULL, NULL, 'taranpe1@fel.cvut.cz'),
(46, 0, 'Václav', 'Tarantík', NULL, NULL, 'taranvac@fel.cvut.cz'),
(47, 0, 'Štěpán', 'Tesař', NULL, NULL, 'tesarst2@fel.cvut.cz'),
(48, 0, 'Martin', 'Tomášek', NULL, NULL, 'tomasma5@fel.cvut.cz'),
(49, 0, 'Thi Phuong Linh', 'Tran', NULL, NULL, 'tranthip@fel.cvut.cz'),
(50, 0, 'Michal', 'Trnka', NULL, NULL, 'trnkami1@fel.cvut.cz'),
(51, 0, 'Lukáš', 'Tůma', NULL, NULL, 'tumaluk9@fel.cvut.cz'),
(52, 0, 'Tomáš', 'Turek', NULL, NULL, 'turekto5@fel.cvut.cz');

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `Additional_info`
--
ALTER TABLE `Additional_info`
  ADD CONSTRAINT `fk_Additional_User1` FOREIGN KEY (`User_id`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `Admin_manages_Action`
--
ALTER TABLE `Admin_manages_Action`
  ADD CONSTRAINT `fk_Admin_has_Action_Admin1` FOREIGN KEY (`Admin_id`) REFERENCES `Admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Admin_has_Action_Action1` FOREIGN KEY (`Action_id`) REFERENCES `Action` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `Participation`
--
ALTER TABLE `Participation`
  ADD CONSTRAINT `fk_Participation_Action1` FOREIGN KEY (`Action_id`) REFERENCES `Action` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Participation_User1` FOREIGN KEY (`User_id`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `Presence`
--
ALTER TABLE `Presence`
  ADD CONSTRAINT `fk_Presence_Participation` FOREIGN KEY (`Participation_id`) REFERENCES `Participation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
