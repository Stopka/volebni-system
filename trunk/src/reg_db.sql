SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_czech_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Admin` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `password` VARCHAR(45) NOT NULL ,
  `role` VARCHAR(10) NOT NULL ,
  `login` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`User` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `IDCard` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  `login` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(128) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Action`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Action` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Action` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `start` DATETIME NOT NULL ,
  `end` DATETIME NOT NULL ,
  `place` VARCHAR(45) NULL COMMENT 'kde se akce koná' ,
  `name` VARCHAR(45) NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Participation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Participation` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Participation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `Action_id` BIGINT NOT NULL ,
  `User_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Participation_Action1` (`Action_id` ASC) ,
  INDEX `fk_Participation_User1` (`User_id` ASC) ,
  CONSTRAINT `fk_Participation_Action1`
    FOREIGN KEY (`Action_id` )
    REFERENCES `mydb`.`Action` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Participation_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `mydb`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Presence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Presence` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Presence` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `checkin` DATETIME NOT NULL ,
  `checkout` DATETIME NULL ,
  `Participation_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Presence_Participation` (`Participation_id` ASC) ,
  CONSTRAINT `fk_Presence_Participation`
    FOREIGN KEY (`Participation_id` )
    REFERENCES `mydb`.`Participation` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Additional_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Additional_info` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Additional_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `key` VARCHAR(45) NOT NULL ,
  `value` TEXT NULL ,
  `User_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Additional_User1` (`User_id` ASC) ,
  CONSTRAINT `fk_Additional_User1`
    FOREIGN KEY (`User_id` )
    REFERENCES `mydb`.`User` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Admin_manages_Action`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Admin_manages_Action` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Admin_manages_Action` (
  `Admin_id` BIGINT NOT NULL ,
  `Action_id` BIGINT NOT NULL ,
  PRIMARY KEY (`Admin_id`, `Action_id`) ,
  INDEX `fk_Admin_has_Action_Action1` (`Action_id` ASC) ,
  INDEX `fk_Admin_has_Action_Admin1` (`Admin_id` ASC) ,
  CONSTRAINT `fk_Admin_has_Action_Admin1`
    FOREIGN KEY (`Admin_id` )
    REFERENCES `mydb`.`Admin` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Admin_has_Action_Action1`
    FOREIGN KEY (`Action_id` )
    REFERENCES `mydb`.`Action` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
