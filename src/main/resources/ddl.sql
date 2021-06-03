
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE status_category_seq;

CREATE TABLE status_category (
  id INT NOT NULL DEFAULT NEXTVAL ('status_category_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
  );
  
 -- SQLINES LICENSE FOR EVALUATION USE ONLY
 CREATE SEQUENCE status_seq;

 CREATE TABLE status (
  id INT NOT NULL DEFAULT NEXTVAL ('status_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status_category INT NOT NULL,
  PRIMARY KEY (id),
 
  CONSTRAINT fk_status_status_category
    FOREIGN KEY (status_category)
    REFERENCES status_category (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_status_status_category_idx ON status (status_category ASC);
        
    
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE sys_role_seq;

CREATE TABLE sys_role (
  id INT NOT NULL DEFAULT NEXTVAL ('sys_role_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_sys_role_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_sys_role_status_idx ON sys_role (status ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE title_seq;

CREATE TABLE title (
  id INT NOT NULL DEFAULT NEXTVAL ('title_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_title_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_title_status_idx ON title (status ASC);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sys_user (
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  title INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  status INT NOT NULL,
  student BOOLEAN DEFAULT FALSE,
  reset_password SMALLINT NOT NULL DEFAULT 0,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (username),
  CONSTRAINT fk_sys_user_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_sys_user_title
    FOREIGN KEY (title)
    REFERENCES title (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE INDEX fk_sys_user_status_idx ON sys_user (status ASC);
CREATE INDEX fx_sys_user_title_idx ON sys_user (title ASC);
    
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE section_seq;

CREATE TABLE section (
  id INT NOT NULL DEFAULT NEXTVAL ('section_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_section_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_section_status_idx ON section (status ASC);
 
    
    
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE authority_seq;

CREATE TABLE authority (
  id INT NOT NULL DEFAULT NEXTVAL ('authority_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  auth_code VARCHAR(20) NOT NULL,
  url VARCHAR(80) NULL,
  section INT NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_authority_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_authority_section
    FOREIGN KEY (section)
    REFERENCES section (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE INDEX fk_authority_status_idx ON authority (status ASC);
CREATE INDEX fx_authority_section_idx ON authority (section ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sys_user_sys_role (
  sys_user VARCHAR(100) NOT NULL,
  sys_role INT NOT NULL,
  PRIMARY KEY (sys_user, sys_role)
 ,
  CONSTRAINT fk_sys_user_sys_role_sys_user
    FOREIGN KEY (sys_user)
    REFERENCES sys_user (username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_sys_user_sys_role_sys_role
    FOREIGN KEY (sys_role)
    REFERENCES sys_role (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_sys_user_sys_role_sys_role_idx ON sys_user_sys_role (sys_role ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sys_role_authoriry (
  sys_role INT NOT NULL,
  authority INT NOT NULL,
  PRIMARY KEY (sys_role, authority)
 ,
  CONSTRAINT fk_sys_role_authority_sys_role
    FOREIGN KEY (sys_role)
    REFERENCES sys_role (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_sys_role_authority_authority
    FOREIGN KEY (authority)
    REFERENCES authority (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_sys_role_authoriry_authority_idx ON sys_role_authoriry (authority ASC);
    
    
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE master_data (
  code VARCHAR(150) NOT NULL,
  value LONGTEXT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (code));

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE email_body (
  code VARCHAR(20) NOT NULL,
  subject VARCHAR(100) NULL,
  content LONGTEXT NULL,
  enable BOOLEAN NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (code));

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sys_user_authority (
  sys_user VARCHAR(100) NOT NULL,
  authority INT NOT NULL,
  is_grant INT NOT NULL DEFAULT 1,
  PRIMARY KEY (sys_user, authority)
 ,
  CONSTRAINT fk_sys_user_authority_sys_user
    FOREIGN KEY (sys_user)
    REFERENCES sys_user (username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_sys_user_authority_authority
    FOREIGN KEY (authority)
    REFERENCES authority (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_sys_user_authority_authority_idx ON sys_user_authority (authority ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE city_seq;

CREATE TABLE city (
  id INT NOT NULL DEFAULT NEXTVAL ('city_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_city_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_city_status_idx ON city (status ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE password_policy_seq;

CREATE TABLE password_policy (
  id INT NOT NULL DEFAULT NEXTVAL ('password_policy_seq'),
  min_length INT NOT NULL DEFAULT 0,
  max_length INT NOT NULL DEFAULT 0,
  min_char INT NOT NULL DEFAULT 0,
  min_num INT NOT NULL DEFAULT 0,
  min_spe_char INT NOT NULL DEFAULT 0,
  no_of_invalid_attempt INT NOT NULL DEFAULT 0,
  no_of_history_password INT NOT NULL DEFAULT 0,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_password_policy_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_password_policy_status_idx ON password_policy (status ASC);




-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE password_reset_request_seq;

CREATE TABLE password_reset_request (
  id INT NOT NULL DEFAULT NEXTVAL ('password_reset_request_seq'),
  sys_user VARCHAR(100) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_password_reset_request_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_password_reset_request_sys_user
    FOREIGN KEY (sys_user)
    REFERENCES sys_user (username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE INDEX fk_password_reset_request_status_idx ON password_reset_request (status ASC);
CREATE INDEX fx_password_reset_request_sys_user_idx ON password_reset_request (sys_user ASC);



-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE question_category_seq;

CREATE TABLE question_category (
  id INT NOT NULL DEFAULT NEXTVAL ('question_category_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  status INT NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_question_category_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_question_category_status_idx ON question_category (status ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE question_seq;

CREATE TABLE question (
  id INT NOT NULL DEFAULT NEXTVAL ('question_seq'),
  code VARCHAR(10) NOT NULL,
  description LONGTEXT NOT NULL,
  status INT NOT NULL,
  typ VARCHAR(12) NOT NULL,
  grp VARCHAR(2) NOT NULL,
  lab VARCHAR(1) NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_question_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_question_status_idx ON question (status ASC);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE question_question_category (
  question INT NOT NULL,
  question_category INT NOT NULL,
  PRIMARY KEY (question, question_category),
  CONSTRAINT fk_question_question_question_category
    FOREIGN KEY (question)
    REFERENCES question (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_question_category_question_question_category
    FOREIGN KEY (question_category)
    REFERENCES question_category (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE question_answer_seq;

CREATE TABLE question_answer (
  id INT NOT NULL DEFAULT NEXTVAL ('question_answer_seq'),
  description LONGTEXT NOT NULL,
  position INT NOT NULL,
  status INT NOT NULL,
  question INT NOT NULL,
  correct BOOLEAN DEFAULT FALSE,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_question_answer_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_question_answer_question
    FOREIGN KEY (question)
    REFERENCES question (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_question_answer_status_idx ON question_answer (status ASC);
CREATE INDEX fk_question_answer_question_idx ON question_answer (question ASC);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE examination_seq;

CREATE TABLE examination (
  id INT NOT NULL DEFAULT NEXTVAL ('examination_seq'),
  code VARCHAR(10) NOT NULL,
  description VARCHAR(50) NOT NULL,
  question_category INT NOT NULL,
  no_question INT NOT NULL DEFAULT 0,
  duration VARCHAR(10),
  date_on TIMESTAMP(0) NULL,
  location VARCHAR(200),
  type VARCHAR(100),
  pass_mark DECIMAL(5,2) DEFAULT 0,
  status INT NOT NULL,
  effective_on TIMESTAMP(0) NULL,
  expier_on TIMESTAMP(0)  NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_examination_question_category
    FOREIGN KEY (question_category)
    REFERENCES question_category (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_examination_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_examination_question_category_idx ON examination (status ASC);
CREATE INDEX fk_examination_status_idx ON examination (status ASC);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE student (
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  initial_password VARCHAR(100) NULL,
  telephone VARCHAR(50) NULL,
  address VARCHAR(100) NULL,
  company VARCHAR(100) NULL,
  city INT NULL,
  zip_code VARCHAR(20) NULL,
  vat VARCHAR(29) NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (username)
 ,
  CONSTRAINT fk_student_city
    FOREIGN KEY (city)
    REFERENCES city (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_student_city_idx ON student (city ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE student_examination_seq;

CREATE TABLE student_examination (
  id INT NOT NULL DEFAULT NEXTVAL ('student_examination_seq'),
  student VARCHAR(100) NOT NULL,
  examination INT NOT NULL,
  status INT NOT NULL,
  start_on TIMESTAMP(0) NULL,
  end_on TIMESTAMP(0) NULL,
  final_mark DECIMAL(5,2) DEFAULT 0,
  is_pass BOOLEAN DEFAULT FALSE,
  pass_mark DECIMAL(5,2) DEFAULT 0,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_student_examination_student
    FOREIGN KEY (student)
    REFERENCES sys_user (username)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_student_examination_examination
    FOREIGN KEY (examination)
    REFERENCES examination (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_student_examination_status
    FOREIGN KEY (status)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_student_examination_student_idx ON student_examination (student ASC);
CREATE INDEX fk_student_examination_examination_idx ON student_examination (examination ASC);
CREATE INDEX fk_student_examination_status_idx ON student_examination (status ASC);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE student_examination_question_answer_seq;

CREATE TABLE student_examination_question_answer (
  id INT NOT NULL DEFAULT NEXTVAL ('student_examination_question_answer_seq'),
  seq INT,
  student_examination INT NOT NULL,
  question INT NOT NULL,
  question_answer INT NULL,
  correct BOOLEAN DEFAULT FALSE,
  correct_question_answer INT NULL,
  created_by VARCHAR(100) NOT NULL,
  created_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(100) NOT NULL,
  updated_on TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_student_examination_question_answer_student_examination
    FOREIGN KEY (student_examination)
    REFERENCES student_examination (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_student_examination_question_answer_question
    FOREIGN KEY (question)
    REFERENCES question (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_student_examination_question_answer_question_answer
    FOREIGN KEY (question_answer)
    REFERENCES question_answer (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
 ,
  CONSTRAINT fk_student_examination_question_answer_question_answer_c
    FOREIGN KEY (correct_question_answer)
    REFERENCES question_answer (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE INDEX fk_student_examination_question_answer_student_examination_idx ON student_examination_question_answer (student_examination ASC);
CREATE INDEX fk_student_examination_question_answer_question_idx ON student_examination_question_answer (question ASC);
CREATE INDEX fk_student_examination_question_answer_question_answer_idx ON student_examination_question_answer (question_answer ASC);
CREATE INDEX fk_student_examination_question_answer_question_answer_c_idx ON student_examination_question_answer (question_answer ASC);

    
INSERT INTO status_category (code, description) VALUES ('DEFAULT', 'Default');
INSERT INTO status_category (code,description) VALUES ('DELETE','Delete');
INSERT INTO status_category (code,description) VALUES ('PASREREQ','Password Reset Request');
INSERT INTO status_category (code,description) VALUES ('EXAM','Examination');

INSERT INTO status (code,description,status_category) VALUES ('ACTIVE','Active',1);
INSERT INTO status (code,description,status_category) VALUES ('INACTIVE','Inactive',1);
INSERT INTO status (code,description,status_category) VALUES ('DELETE','Delete',2);
INSERT INTO status (code,description,status_category) VALUES ('REQUEST','Requested',3);
INSERT INTO status (code,description,status_category) VALUES ('PRESET','Password Reset',3);
INSERT INTO status (code,description,status_category) VALUES ('PENDING','Pending',4);
INSERT INTO status (code,description,status_category) VALUES ('START','Started',4);
INSERT INTO status (code,description,status_category) VALUES ('CLOSED','Closed',4);

INSERT INTO  section(code,description,status,created_by,updated_by) VALUES ('USER_MGT_S','System Management',1,'SYSTEM','SYSTEM');
INSERT INTO  section(code,description,status,created_by,updated_by) VALUES ('REF_MGT_S','Reference Data',1,'SYSTEM','SYSTEM');
INSERT INTO  section(code,description,status,created_by,updated_by) VALUES ('SYS_CFG_S','Master Data',1,'SYSTEM','SYSTEM');
INSERT INTO  section(code,description,status,created_by,updated_by) VALUES ('EXAM_MGT_S','Exam Management',1,'SYSTEM','SYSTEM');

INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_NONE','None',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_MR','Mr.',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_MRS','Mrs.',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_MISS','Miss.',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_MS','Ms.',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_PROF','Prof.',1,'SYSTEM','SYSTEM');
INSERT INTO title (code,description,status,created_by,updated_by) VALUES ('CODE_DR','Dr.',1,'SYSTEM','SYSTEM');

INSERT INTO master_data(code,value,created_by,updated_by) VALUES
('DEFAULT_PASSWORD',null,'SYSTEM','SYSTEM'),
('COMPANY_NAME',null,'SYSTEM','SYSTEM'),
('COMPANY_LOGO',null,'SYSTEM','SYSTEM'),
('STUDENT_ROLE',null,'SYSTEM','SYSTEM');



INSERT INTO email_body(code,subject,content,enable,created_by,updated_by) VALUES
('EFR',null,null,1,'SYSTEM','SYSTEM'),
('EFSR',null,null,1,'SYSTEM','SYSTEM'),
('EFPR',null,null,1,'SYSTEM','SYSTEM');

INSERT INTO authority(code,description,auth_code,url,section,status,created_by,updated_by)  VALUES
('USER','System User','ROLE_USER','/sysUser/',1,1,'SYSTEM','SYSTEM'),
('ROLE','System Role','ROLE_ROLE','/sysRole/',1,1,'SYSTEM','SYSTEM'),
('SECTION','Section','ROLE_SECTION','/section/',1,1,'SYSTEM','SYSTEM'),
('ROLEAUTHOR','System Roles Authority','ROLE_ROLEAUTHORITY','/sysRoleAuthority/',1,1,'SYSTEM','SYSTEM'),
('AUTHORITY','Authority','ROLE_AUTHORITY','/authority/',1,1,'SYSTEM','SYSTEM'),
('TITLE','Salutation','ROLE_TITLE','/title/',2,1,'SYSTEM','SYSTEM'),
('USERROLE','System Users Role','ROLE_USERROLE','/sysUserSysRole/',1,1,'SYSTEM','SYSTEM'),
('MASTERDATA','Master Data','ROLE_MASTERDATA','/masterData/',1,1,'SYSTEM','SYSTEM'),
('USERAUTHOR','Sys Users Authority','ROLE_USERAUTHORITY','/sysUserAuthority/',1,1,'SYSTEM','SYSTEM'),
('CITY','City','ROLE_CITY','/city/',1,1,'SYSTEM','SYSTEM'),
('RPASS','Reset Password','ROLE_PASSREREQ','/passwordResetRequest/',1,1,'SYSTEM','SYSTEM'),
('QUECAT','Question Category','ROLE_QUECATEGORY','/questionCategory/',4,1,'SYSTEM','SYSTEM'),
('EXAM','Exam Management','ROLE_EXAMMGT','/examination/',4,1,'SYSTEM','SYSTEM'),
('QUSMGT','Question','ROLE_QUS','/question/',4,1,'SYSTEM', 'SYSTEM'),
('STUD','Student','ROLE_STUD','/student/',4,1,'SYSTEM','SYSTEM'),
('STUEXAM','Examination','ROLE_STUEXAM','/studentExams/',4,1,'SYSTEM','SYSTEM'),
('STUEXAMADD','Student Exams','ROLE_STUEXAMADD','/studentExamination/',4,1,'SYSTEM','SYSTEM'),
('EMAIL','Email Editor','ROLE_EMAIL','/email/',1,1,'SYSTEM','SYSTEM');


INSERT INTO sys_role (code,description,status,created_by,updated_by) VALUES ('SYSTEM','System',1,'SYSTEM','SYSTEM');

INSERT INTO sys_user (username,password,title,name,status,created_by,updated_by) VALUES ('SYSTEM','$2a$10$/9rQ/RW6jv1DS0uXS4FoxeHvzZPiWpgnB6XdIxjymSYE9UFoGKEnq',1,'System',1,'SYSTEM','SYSTEM');



INSERT INTO sys_user_sys_role (sys_user, sys_role) VALUES ('SYSTEM', '1');

INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '1');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '2');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '3');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '4');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '5');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '7');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '8');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '9');
INSERT INTO sys_role_authoriry (sys_role, authority) VALUES ('1', '11');