CREATE TABLE if not exists publish_companies(
publish_company_id INT AUTO_INCREMENT PRIMARY KEY,
name_of_company VARCHAR(255) NOT NULL);

CREATE TABLE if not exists printed_elements(
printed_element_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
title VARCHAR(255) NOT NULL,
type_of_element VARCHAR(255) NOT NULL,
style VARCHAR(255) NOT NULL,
amount_of_elements INT NOT NULL,
year_of_publish INT NOT NULL,
num_of_publish INT NOT NULL,
publish_company INT NOT NULL,
FOREIGN KEY(publish_company) REFERENCES publish_companies(publish_company_id));

CREATE TABLE if not exists authors(
author_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
name VARCHAR(255) NOT NULL);
CREATE TABLE if not exists readers(
reader_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
name VARCHAR(255) NOT NULL);

CREATE TABLE if not exists movements(
move_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
status VARCHAR(200) NOT NULL,
date DATE,
reader INT,
FOREIGN KEY(reader) REFERENCES readers(reader_id),
printed_element INT,
FOREIGN KEY(printed_element) REFERENCES printed_elements(printed_element_id));

CREATE TABLE if not exists elements_join_authors(
join_id INT AUTO_INCREMENT PRIMARY KEY,
author INT NOT NULL,
FOREIGN KEY(author) REFERENCES authors(author_id),
printed_element INT NOT NULL,
FOREIGN KEY(printed_element) REFERENCES printed_elements(printed_element_id));

CREATE TABLE if not exists users(
user_id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
active boolean default false,
role VARCHAR(255) NOT NULL);

CREATE TABLE if not exists tokens(
token_id INT AUTO_INCREMENT PRIMARY KEY,
value longtext NOT NULL,
revoked boolean default false,
expired boolean default false,
user INT NOT NULL,
FOREIGN KEY(user) REFERENCES users(user_id));

