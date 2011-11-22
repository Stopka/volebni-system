SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Admin`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `password` VARCHAR(45) NOT NULL ,
  `isAdmin` TINYINT(1)  NOT NULL ,
  `isSuperAdmin` TINYINT(1)  NOT NULL ,
  `login` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`User` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `street` VARCHAR(45) NOT NULL ,
  `number` VARCHAR(45) NOT NULL COMMENT 'číslo popisné' ,
  `city` VARCHAR(45) NOT NULL ,
  `IDCard` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Action`
-- -----------------------------------------------------
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
-- Table `mydb`.`Additional`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Additional` (
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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
