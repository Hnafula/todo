# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category_details (
  id                        bigint auto_increment not null,
  categoryname              varchar(255),
  categorydetails           varchar(255),
  constraint pk_category_details primary key (id))
;

create table tbl_items (
  id                        bigint auto_increment not null,
  itemname                  varchar(255),
  itemdetails               varchar(255),
  categoryid                bigint,
  constraint pk_tbl_items primary key (id))
;

create table tbl_users (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  pin                       varchar(255),
  constraint pk_tbl_users primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category_details;

drop table tbl_items;

drop table tbl_users;

SET FOREIGN_KEY_CHECKS=1;

