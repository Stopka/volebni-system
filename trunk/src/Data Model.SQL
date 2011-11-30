SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS action
;
DROP TABLE IF EXISTS additional_info
;
DROP TABLE IF EXISTS admin
;
DROP TABLE IF EXISTS admin_manages_action
;
DROP TABLE IF EXISTS participation
;
DROP TABLE IF EXISTS presence
;
DROP TABLE IF EXISTS user
;

CREATE TABLE action
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	start DATETIME NOT NULL,
	end DATETIME NOT NULL,
	place VARCHAR(45),
	name VARCHAR(45) NOT NULL,
	description TEXT,
	PRIMARY KEY (id),
	UNIQUE name_UNIQUE(name)
) TYPE=InnoDB
;


CREATE TABLE additional_info
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	key VARCHAR(45) NOT NULL,
	value TEXT,
	User_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	INDEX fk_Additional_User1 (User_id ASC)
) TYPE=InnoDB
;


CREATE TABLE admin
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	password VARCHAR(45) NOT NULL,
	role VARCHAR(10) NOT NULL,
	login VARCHAR(45) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE login_UNIQUE(login)
) TYPE=InnoDB
;


CREATE TABLE admin_manages_action
(
	Admin_id BIGINT NOT NULL,
	Action_id BIGINT NOT NULL,
	PRIMARY KEY (Admin_id, Action_id),
	INDEX fk_Admin_has_Action_Action1 (Action_id ASC),
	INDEX fk_Admin_has_Action_Admin1 (Admin_id ASC)
) TYPE=InnoDB
;


CREATE TABLE participation
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	Action_id BIGINT NOT NULL,
	User_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	INDEX fk_Participation_Action1 (Action_id ASC),
	INDEX fk_Participation_User1 (User_id ASC)
) TYPE=InnoDB
;


CREATE TABLE presence
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	checkin DATETIME NOT NULL,
	checkout DATETIME,
	Participation_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	INDEX fk_Presence_Participation (Participation_id ASC)
) TYPE=InnoDB
;


CREATE TABLE user
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	IDCard INTEGER NOT NULL,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	login VARCHAR(45) NOT NULL,
	password VARCHAR(45) NOT NULL,
	email VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)
) TYPE=InnoDB
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE additional_info ADD CONSTRAINT fk_Additional_User1 
	FOREIGN KEY (User_id) REFERENCES user (id)
;

ALTER TABLE admin_manages_action ADD CONSTRAINT fk_Admin_has_Action_Admin1 
	FOREIGN KEY (Admin_id) REFERENCES admin (id)
;

ALTER TABLE admin_manages_action ADD CONSTRAINT fk_Admin_has_Action_Action1 
	FOREIGN KEY (Action_id) REFERENCES action (id)
;

ALTER TABLE participation ADD CONSTRAINT fk_Participation_Action1 
	FOREIGN KEY (Action_id) REFERENCES action (id)
;

ALTER TABLE participation ADD CONSTRAINT fk_Participation_User1 
	FOREIGN KEY (User_id) REFERENCES user (id)
;

ALTER TABLE presence ADD CONSTRAINT fk_Presence_Participation 
	FOREIGN KEY (Participation_id) REFERENCES participation (id)
;
