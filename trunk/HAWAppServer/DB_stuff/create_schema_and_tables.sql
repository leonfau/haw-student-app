SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';



DROP SCHEMA IF EXISTS `haw_app` ;

CREATE SCHEMA IF NOT EXISTS `haw_app` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

USE `haw_app` ;



-- -----------------------------------------------------

-- Table `haw_app`.`Semester`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `haw_app`.`Semester` (

  `uuid` VARCHAR(255) NOT NULL DEFAULT '' ,

  `begin` DATE NULL ,

  `end` DATE NULL ,

  `lastModified` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,

  PRIMARY KEY (`uuid`) )

ENGINE = InnoDB;





-- -----------------------------------------------------

-- Table `haw_app`.`Category`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `haw_app`.`Category` (

  `uuid` VARCHAR(255) NOT NULL DEFAULT '' ,

  `name` VARCHAR(255) NULL ,

  `lastModified` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,

  `Semester_uuid` VARCHAR(255) NOT NULL ,

  PRIMARY KEY (`uuid`) ,

  INDEX `fk_Category_Semester_idx` (`Semester_uuid` ASC) ,

  CONSTRAINT `fk_Category_Semester`

    FOREIGN KEY (`Semester_uuid` )

    REFERENCES `haw_app`.`Semester` (`uuid` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB;





-- -----------------------------------------------------

-- Table `haw_app`.`Lecture`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `haw_app`.`Lecture` (

  `uuid` VARCHAR(255) NOT NULL ,

  `lastModified` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,

  `Category_uuid` VARCHAR(255) NOT NULL ,

  `name` VARCHAR(255) NULL ,

  `lecturerName` VARCHAR(255) NULL ,

  PRIMARY KEY (`uuid`) ,

  INDEX `fk_Lecture_Category1_idx` (`Category_uuid` ASC) ,

  CONSTRAINT `fk_Lecture_Category1`

    FOREIGN KEY (`Category_uuid` )

    REFERENCES `haw_app`.`Category` (`uuid` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB;





-- -----------------------------------------------------

-- Table `haw_app`.`Appointment`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `haw_app`.`Appointment` (

  `uuid` VARCHAR(255) NOT NULL ,

  `name` VARCHAR(255) NULL ,

  `begin` DATETIME NULL ,

  `end` DATETIME NULL ,

  `location` VARCHAR(255) NULL ,

  `details` VARCHAR(1000) NULL ,

  `lastModified` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,

  `Lecture_uuid` VARCHAR(255) NOT NULL ,

  PRIMARY KEY (`uuid`) ,

  INDEX `fk_Appointment_Lecture1_idx` (`Lecture_uuid` ASC) ,

  CONSTRAINT `fk_Appointment_Lecture1`

    FOREIGN KEY (`Lecture_uuid` )

    REFERENCES `haw_app`.`Lecture` (`uuid` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB;





-- -----------------------------------------------------

-- Table `haw_app`.`ChangeMessage`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `haw_app`.`ChangeMessage` (

  `uuid` VARCHAR(255) NOT NULL ,

  `lastModified` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,

  `changeat` DATETIME NULL ,

  `reason` VARCHAR(1000) NULL ,

  `what` VARCHAR(1000) NULL ,

  `person` VARCHAR(255) NULL ,

  `Lecture_uuid` VARCHAR(255) NOT NULL ,

  PRIMARY KEY (`uuid`) ,

  INDEX `fk_Change_Lecture1_idx` (`Lecture_uuid` ASC) ,

  CONSTRAINT `fk_Change_Lecture1`

    FOREIGN KEY (`Lecture_uuid` )

    REFERENCES `haw_app`.`Lecture` (`uuid` )

    ON DELETE NO ACTION

    ON UPDATE NO ACTION)

ENGINE = InnoDB;

-- -----------------------------------------------------

-- Tabellen für das Schwarze Brett

-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS haw_app.Blackboard_Image (
	id bigint(20) NOT NULL AUTO_INCREMENT, 
	image blob NOT NULL, 
	PRIMARY KEY (id)) ENGINE=InnoDB;
	
CREATE TABLE IF NOT EXISTS haw_app.Blackboard_Offer (
	id bigint(20) NOT NULL AUTO_INCREMENT, 
	header varchar(125) NOT NULL, 
	categoryName varchar(125) NOT NULL, 
	imageId bigint(20), 
	description varchar(255), 
	contact varchar(255),
	dateOfCreation date, 
	deletionKey varchar(11) NOT NULL, 
	PRIMARY KEY (id)) ENGINE=InnoDB;
	
CREATE TABLE IF NOT EXISTS haw_app.Blackboard_Category (
	name varchar(125) NOT NULL, 
	PRIMARY KEY (name)) ENGINE=InnoDB;
	
CREATE TABLE IF NOT EXISTS haw_app.Blackboard_Report (
	id bigint(20) NOT NULL AUTO_INCREMENT, 
	reason varchar(255) NOT NULL, 
	offerId bigint(20) NOT NULL, 
	PRIMARY KEY (id)) ENGINE=InnoDB;

-- -----------------------------------------------------

-- Erzeugen der Fremdschlüsselbeziehungen

-- -----------------------------------------------------

ALTER TABLE haw_app.Blackboard_Offer ADD INDEX fk_image (imageId), 
	ADD CONSTRAINT fk_image FOREIGN KEY (imageId) REFERENCES haw_app.Blackboard_Image (id);
ALTER TABLE haw_app.Blackboard_Offer ADD INDEX fk_category (categoryName), 
	ADD CONSTRAINT fk_category FOREIGN KEY (categoryName) REFERENCES haw_app.Blackboard_Category (name);
ALTER TABLE haw_app.Blackboard_Report ADD INDEX fk_offer (offerId), 
	ADD CONSTRAINT fk_offer FOREIGN KEY (offerId) REFERENCES haw_app.Blackboard_Offer (id);




USE `haw_app` ;





SET SQL_MODE=@OLD_SQL_MODE;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

