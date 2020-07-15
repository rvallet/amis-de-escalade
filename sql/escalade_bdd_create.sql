CREATE DATABASE `escalade_bdd`

CREATE TABLE `area` (
  `id_area` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `img_path_th_attribute` varchar(255) DEFAULT NULL,
  `is_promoted` bit(1) DEFAULT NULL,
  `name` varchar(75) NOT NULL,
  `release_date` datetime DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_area`)
)

CREATE TABLE `comment` (
  `id_comment` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `content` varchar(1000) NOT NULL,
  `release_date` datetime DEFAULT NULL,
  `title` varchar(75) NOT NULL,
  `id_area` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_comment`),
)

CREATE TABLE `route` (
  `id_route` bigint(20) NOT NULL AUTO_INCREMENT,
  `climbing_grade` varchar(255) DEFAULT NULL,
  `description` varchar(250) NOT NULL,
  `name` varchar(75) NOT NULL,
  `nb_length` int(11) NOT NULL,
  `id_sector` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_route`),
)

CREATE TABLE `sector` (
  `id_sector` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(250) NOT NULL,
  `name` varchar(75) NOT NULL,
  `id_area` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_sector`),
) 
CREATE TABLE `topo` (
  `id_topo` bigint(20) NOT NULL AUTO_INCREMENT,
  `belong_to` varchar(255) DEFAULT NULL,
  `description` varchar(1000) NOT NULL,
  `img_path_th_attribute` varchar(255) DEFAULT NULL,
  `is_available_for_loan` bit(1) DEFAULT NULL,
  `is_online` bit(1) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(75) NOT NULL,
  `release_date` datetime DEFAULT NULL,
  `short_description` varchar(255) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_topo`),
)

CREATE TABLE `topo_loan` (
  `id_topo_loan` bigint(20) NOT NULL AUTO_INCREMENT,
  `borrower` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `lender` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id_topo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_topo_loan`),
)

CREATE TABLE `user` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pseudo` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
)
