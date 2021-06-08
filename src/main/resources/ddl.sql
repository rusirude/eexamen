
CREATE TABLE `status_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC)
  );
  
 CREATE TABLE `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status_category` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC),
  INDEX `fk_status_status_category_idx` (`status_category` ASC),
  CONSTRAINT `fk_status_status_category`
    FOREIGN KEY (`status_category`)
    REFERENCES `status_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);
        
    
CREATE TABLE `sys_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_sys_role_status_idx` (`status` ASC),
  CONSTRAINT `fk_sys_role_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `title` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_title_status_idx` (`status` ASC),
  CONSTRAINT `fk_title_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


CREATE TABLE `sys_user` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `title` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `status` INT NOT NULL,
  `student` BOOLEAN DEFAULT FALSE,
  `reset_password` TINYINT NOT NULL DEFAULT 0,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk_sys_user_status_idx` (`status` ASC),
  CONSTRAINT `fk_sys_user_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fx_sys_user_title_idx` (`title` ASC),
  CONSTRAINT `fk_sys_user_title`
    FOREIGN KEY (`title`)
    REFERENCES `title` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
    
CREATE TABLE `section` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_section_status_idx` (`status` ASC),
  CONSTRAINT `fk_section_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);
 
    
    
CREATE TABLE `authority` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `auth_code` VARCHAR(20) NOT NULL,
  `url` VARCHAR(80) NULL,
  `section` INT NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC),
  UNIQUE INDEX `auth_code_UNIQUE` (`auth_code` ASC),
  INDEX `fk_authority_status_idx` (`status` ASC),
  CONSTRAINT `fk_authority_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fx_authority_section_idx` (`section` ASC),
  CONSTRAINT `fk_authority_section`
    FOREIGN KEY (`section`)
    REFERENCES `section` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE TABLE `sys_user_sys_role` (
  `sys_user` VARCHAR(100) NOT NULL,
  `sys_role` INT NOT NULL,
  PRIMARY KEY (`sys_user`, `sys_role`),
  INDEX `fk_sys_user_sys_role_sys_role_idx` (`sys_role` ASC),
  CONSTRAINT `fk_sys_user_sys_role_sys_user`
    FOREIGN KEY (`sys_user`)
    REFERENCES `sys_user` (`username`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_sys_role_sys_role`
    FOREIGN KEY (`sys_role`)
    REFERENCES `sys_role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `sys_role_authoriry` (
  `sys_role` INT NOT NULL,
  `authority` INT NOT NULL,
  PRIMARY KEY (`sys_role`, `authority`),
  INDEX `fk_sys_role_authoriry_authority_idx` (`authority` ASC),
  CONSTRAINT `fk_sys_role_authority_sys_role`
    FOREIGN KEY (`sys_role`)
    REFERENCES `sys_role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_authority_authority`
    FOREIGN KEY (`authority`)
    REFERENCES `authority` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);
    
    
CREATE TABLE `master_data` (
  `code` VARCHAR(150) NOT NULL,
  `value` LONGTEXT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`code`));

CREATE TABLE `email_body` (
  `code` VARCHAR(20) NOT NULL,
  `subject` VARCHAR(100) NULL,
  `content` LONGTEXT NULL,
  `enable` BOOLEAN NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`code`));

CREATE TABLE `sys_user_authority` (
  `sys_user` VARCHAR(100) NOT NULL,
  `authority` INT NOT NULL,
  `is_grant` INT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`sys_user`, `authority`),
  INDEX `fk_sys_user_authority_authority_idx` (`authority` ASC),
  CONSTRAINT `fk_sys_user_authority_sys_user`
    FOREIGN KEY (`sys_user`)
    REFERENCES `sys_user` (`username`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_authority_authority`
    FOREIGN KEY (`authority`)
    REFERENCES `authority` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_city_status_idx` (`status` ASC),
  CONSTRAINT `fk_city_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `password_policy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `min_length` INT NOT NULL DEFAULT 0,
  `max_length` INT NOT NULL DEFAULT 0,
  `min_char` INT NOT NULL DEFAULT 0,
  `min_num` INT NOT NULL DEFAULT 0,
  `min_spe_char` INT NOT NULL DEFAULT 0,
  `no_of_invalid_attempt` INT NOT NULL DEFAULT 0,
  `no_of_history_password` INT NOT NULL DEFAULT 0,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_password_policy_status_idx` (`status` ASC),
  CONSTRAINT `fk_password_policy_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);




CREATE TABLE `password_reset_request` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sys_user` VARCHAR(100) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_password_reset_request_status_idx` (`status` ASC),
  CONSTRAINT `fk_password_reset_request_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fx_password_reset_request_sys_user_idx` (`sys_user` ASC),
  CONSTRAINT `fk_password_reset_request_sys_user`
    FOREIGN KEY (`sys_user`)
    REFERENCES `sys_user` (`username`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);



CREATE TABLE `question_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_question_category_status_idx` (`status` ASC),
  CONSTRAINT `fk_question_category_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` LONGTEXT NOT NULL,
  `status` INT NOT NULL,
  `typ` VARCHAR(12) NOT NULL,
  `grp` VARCHAR(2) NOT NULL,
  `lab` VARCHAR(1) NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_question_status_idx` (`status` ASC),
  CONSTRAINT `fk_question_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


CREATE TABLE `question_question_category` (
  `question` INT NOT NULL,
  `question_category` INT NOT NULL,
  PRIMARY KEY (`question`, `question_category`),
  CONSTRAINT `fk_question_question_question_category`
    FOREIGN KEY (`question`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_question_category_question_question_category`
    FOREIGN KEY (`question_category`)
    REFERENCES `question_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `question_answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` LONGTEXT NOT NULL,
  `position` INT NOT NULL,
  `status` INT NOT NULL,
  `question` INT NOT NULL,
  `correct` BOOLEAN DEFAULT FALSE,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_question_answer_status_idx` (`status` ASC),
  CONSTRAINT `fk_question_answer_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_question_answer_question_idx` (`question` ASC),
  CONSTRAINT `fk_question_answer_question`
    FOREIGN KEY (`question`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


CREATE TABLE `examination` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `exam_type` INT NOT NULL,
  `duration` VARCHAR(10),
  `date_on` DATETIME NULL,
  `location` VARCHAR(200),
  `status` INT NOT NULL,
  `effective_on` DATETIME NULL,
  `expier_on` DATETIME  NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_examination_exam_type_idx` (`exam_type` ASC),
  CONSTRAINT `fk_examination_exam_type`
    FOREIGN KEY (`exam_type`)
    REFERENCES `exam_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_examination_status_idx` (`status` ASC),
  CONSTRAINT `fk_examination_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


CREATE TABLE `student` (
  `username` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `initial_password` VARCHAR(100) NULL,
  `telephone` VARCHAR(50) NULL,
  `address` VARCHAR(100) NULL,
  `company` VARCHAR(100) NULL,
  `city` INT NULL,
  `zip_code` VARCHAR(20) NULL,
  `vat` VARCHAR(29) NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`),
  INDEX `fk_student_city_idx` (`city` ASC),
  CONSTRAINT `fk_student_city`
    FOREIGN KEY (`city`)
    REFERENCES `city` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `student_examination` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `student` VARCHAR(100) NOT NULL,
  `examination` INT NOT NULL,
  `status` INT NOT NULL,
  `start_on` DATETIME NULL,
  `end_on` DATETIME NULL,
  `t_final_mark` DECIMAL(5,2) DEFAULT 0,
  `w_final_mark` DECIMAL(5,2) DEFAULT 0,
  `is_pass` BOOLEAN DEFAULT FALSE,
  `t_pass_mark` DECIMAL(5,2) DEFAULT 0,
  `w_pass_mark` DECIMAL(5,2) DEFAULT 0,
  `t_count` INT DEFAULT 0,
  `w_count` INT DEFAULT 0,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_student_examination_student_idx` (`student` ASC),
  CONSTRAINT `fk_student_examination_student`
    FOREIGN KEY (`student`)
    REFERENCES `sys_user` (`username`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_student_examination_examination_idx` (`examination` ASC),
  CONSTRAINT `fk_student_examination_examination`
    FOREIGN KEY (`examination`)
    REFERENCES `examination` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_student_examination_status_idx` (`status` ASC),
  CONSTRAINT `fk_student_examination_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `student_examination_question_answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `seq` INT,
  `student_examination` INT NOT NULL,
  `question` INT NOT NULL,
  `question_answer` INT NULL,
  `correct` BOOLEAN DEFAULT FALSE,
  `correct_question_answer` INT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_student_examination_question_answer_student_examination_idx` (`student_examination` ASC),
  CONSTRAINT `fk_student_examination_question_answer_student_examination`
    FOREIGN KEY (`student_examination`)
    REFERENCES `student_examination` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_student_examination_question_answer_question_idx` (`question` ASC),
  CONSTRAINT `fk_student_examination_question_answer_question`
    FOREIGN KEY (`question`)
    REFERENCES `question` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_student_examination_question_answer_question_answer_idx` (`question_answer` ASC),
  CONSTRAINT `fk_student_examination_question_answer_question_answer`
    FOREIGN KEY (`question_answer`)
    REFERENCES `question_answer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_student_examination_question_answer_question_answer_c_idx` (`question_answer` ASC),
  CONSTRAINT `fk_student_examination_question_answer_question_answer_c`
    FOREIGN KEY (`correct_question_answer`)
    REFERENCES `question_answer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


CREATE TABLE `exam_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `status` INT NOT NULL,
  `exam_category` VARCHAR(12) NOT NULL,
  `question_category` INT NOT NULL,
  `t_pass_mark` DECIMAL(5,2) DEFAULT 0,
  `w_pass_mark` DECIMAL(5,2) DEFAULT 0,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_exam_type_status_idx` (`status` ASC),
  CONSTRAINT `fk_exam_type_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_exam_type_question_category_idx` (`status` ASC),
  CONSTRAINT `fk_exam_type_question_category`
    FOREIGN KEY (`question_category`)
    REFERENCES `question_category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `exam_type_question_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `exam_type` INT NOT NULL,
  `typ` VARCHAR(12) NOT NULL,
  `grp` VARCHAR(2) NOT NULL,
  `lab` VARCHAR(1) NULL,
  `no_question` INT NOT NULL DEFAULT 0,
  `status` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NOT NULL,
  `updated_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_exam_type_question_model_exam_type_idx` (`status` ASC),
  CONSTRAINT `fk_exam_type_question_models_exam_type`
    FOREIGN KEY (`exam_type`)
    REFERENCES `exam_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  INDEX `fk_exam_type_question_models_status_idx` (`status` ASC),
  CONSTRAINT `fk_exam_type_question_models_status`
    FOREIGN KEY (`status`)
    REFERENCES `status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

    
INSERT INTO `status_category` (`code`, `description`) VALUES ('DEFAULT', 'Default');
INSERT INTO `status_category` (`code`,`description`) VALUES ('DELETE','Delete');
INSERT INTO `status_category` (`code`,`description`) VALUES ('PASREREQ','Password Reset Request');
INSERT INTO `status_category` (`code`,`description`) VALUES ('EXAM','Examination');

INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('ACTIVE','Active',1);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('INACTIVE','Inactive',1);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('DELETE','Delete',2);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('REQUEST','Requested',3);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('PRESET','Password Reset',3);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('PENDING','Pending',4);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('START','Started',4);
INSERT INTO `status` (`code`,`description`,`status_category`) VALUES ('CLOSED','Closed',4);

INSERT INTO  `section`(`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('USER_MGT_S','System Management',1,'SYSTEM','SYSTEM');
INSERT INTO  `section`(`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('REF_MGT_S','Reference Data',1,'SYSTEM','SYSTEM');
INSERT INTO  `section`(`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('SYS_CFG_S','Master Data',1,'SYSTEM','SYSTEM');
INSERT INTO  `section`(`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('EXAM_MGT_S','Exam Management',1,'SYSTEM','SYSTEM');

INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_NONE','None',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_MR','Mr.',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_MRS','Mrs.',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_MISS','Miss.',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_MS','Ms.',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_PROF','Prof.',1,'SYSTEM','SYSTEM');
INSERT INTO `title` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('CODE_DR','Dr.',1,'SYSTEM','SYSTEM');

INSERT INTO `master_data`(`code`,`value`,`created_by`,`updated_by`) VALUES
('DEFAULT_PASSWORD',null,'SYSTEM','SYSTEM'),
('COMPANY_NAME',null,'SYSTEM','SYSTEM'),
('COMPANY_LOGO',null,'SYSTEM','SYSTEM'),
('STUDENT_ROLE',null,'SYSTEM','SYSTEM');



INSERT INTO `email_body`(`code`,`subject`,`content`,`enable`,`created_by`,`updated_by`) VALUES
('EFR',null,null,1,'SYSTEM','SYSTEM'),
('EFSR',null,null,1,'SYSTEM','SYSTEM'),
('EFPR',null,null,1,'SYSTEM','SYSTEM');

INSERT INTO `authority`(`code`,`description`,`auth_code`,`url`,`section`,`status`,`created_by`,`updated_by`)  VALUES
('USER','System User','ROLE_USER','/sysUser/',1,1,'SYSTEM','SYSTEM'),
('ROLE','System Role','ROLE_ROLE','/sysRole/',1,1,'SYSTEM','SYSTEM'),
('SECTION','Section','ROLE_SECTION','/section/',1,1,'SYSTEM','SYSTEM'),
('ROLEAUTHOR','System Role\'s Authority','ROLE_ROLEAUTHORITY','/sysRoleAuthority/',1,1,'SYSTEM','SYSTEM'),
('AUTHORITY','Authority','ROLE_AUTHORITY','/authority/',1,1,'SYSTEM','SYSTEM'),
('TITLE','Salutation','ROLE_TITLE','/title/',2,1,'SYSTEM','SYSTEM'),
('USERROLE','System User\'s Role','ROLE_USERROLE','/sysUserSysRole/',1,1,'SYSTEM','SYSTEM'),
('MASTERDATA','Master Data','ROLE_MASTERDATA','/masterData/',1,1,'SYSTEM','SYSTEM'),
('USERAUTHOR','Sys User\'s Authority','ROLE_USERAUTHORITY','/sysUserAuthority/',1,1,'SYSTEM','SYSTEM'),
('CITY','City','ROLE_CITY','/city/',1,1,'SYSTEM','SYSTEM'),
('RPASS','Reset Password','ROLE_PASSREREQ','/passwordResetRequest/',1,1,'SYSTEM','SYSTEM'),
('QUECAT','Question Category','ROLE_QUECATEGORY','/questionCategory/',4,1,'SYSTEM','SYSTEM'),
('EXAM','Exam Management','ROLE_EXAMMGT','/examination/',4,1,'SYSTEM','SYSTEM'),
('QUSMGT','Question','ROLE_QUS','/question/',4,1,'SYSTEM', 'SYSTEM'),
('STUD','Student','ROLE_STUD','/student/',4,1,'SYSTEM','SYSTEM'),
('STUEXAM','Examination','ROLE_STUEXAM','/studentExams/',4,1,'SYSTEM','SYSTEM'),
('STUEXAMADD','Student Exams','ROLE_STUEXAMADD','/studentExamination/',4,1,'SYSTEM','SYSTEM'),
('EMAIL','Email Editor','ROLE_EMAIL','/email/',1,1,'SYSTEM','SYSTEM'),
('EXMTYP','Exam Type','ROLE_EXMTYP','/examType/',4,1,'SYSTEM','SYSTEM');


INSERT INTO `sys_role` (`code`,`description`,`status`,`created_by`,`updated_by`) VALUES ('SYSTEM','System',1,'SYSTEM','SYSTEM');

INSERT INTO `sys_user` (`username`,`password`,`title`,`name`,`status`,`created_by`,`updated_by`) VALUES ('SYSTEM','$2a$10$/9rQ/RW6jv1DS0uXS4FoxeHvzZPiWpgnB6XdIxjymSYE9UFoGKEnq',1,'System',1,'SYSTEM','SYSTEM');



INSERT INTO `sys_user_sys_role` (`sys_user`, `sys_role`) VALUES ('SYSTEM', '1');

INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '1');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '2');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '3');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '4');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '5');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '7');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '8');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '9');
INSERT INTO `sys_role_authoriry` (`sys_role`, `authority`) VALUES ('1', '11');

    
    
    