CREATE TABLE user (
id INT AUTO_INCREMENT,
name VARCHAR(45),
login VARCHAR(45),
password VARCHAR(45),
email VARCHAR(45),
PRIMARY KEY(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE developer(
id INT AUTO_INCREMENT,
name VARCHAR(45),
age INT,
sex VARCHAR(6),
PRIMARY KEY(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE skill (
id INT AUTO_INCREMENT,
language VARCHAR(45),
level VARCHAR(6),
PRIMARY KEY(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE developer_skill (
developer_id INT DEFAULT NULL,
skill_id INT DEFAULT NULL
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE developer_skill ADD INDEX(developer_id);
ALTER TABLE developer_skill ADD INDEX(skill_id);

ALTER TABLE developer_skill
ADD CONSTRAINT dp_developer_fk
FOREIGN KEY (developer_id) REFERENCES developer(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE developer_skill
ADD CONSTRAINT dp_skill_fk
FOREIGN KEY (skill_id) REFERENCES skill(id)
ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE customer (
id INT AUTO_INCREMENT,
name VARCHAR(45),
age INT,
sex VARCHAR(6),
PRIMARY KEY(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE project(
id INT AUTO_INCREMENT,
customer_id INT,
name VARCHAR(45),
type VARCHAR(45),
PRIMARY KEY(id),
CONSTRAINT project_customer_fk
FOREIGN KEY (customer_id) REFERENCES customer(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE developer_project (
developer_id INT DEFAULT NULL,
project_id INT DEFAULT NULL
)DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE developer_project ADD INDEX(developer_id);
ALTER TABLE developer_project ADD INDEX(project_id);

ALTER TABLE developer_project
ADD CONSTRAINT dp_developer_fk1
FOREIGN KEY (developer_id) REFERENCES developer(id)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE developer_project
ADD CONSTRAINT dp_project_fk
FOREIGN KEY (project_id) REFERENCES project(id)
ON UPDATE CASCADE ON DELETE CASCADE;
