/*==============================================================*/
/* DBMS name:      PostgreSQL	                                */
/* Created on:     2022/3/31 14:12:07                           */
/*==============================================================*/


-- drop table "blogschema.blog";

-- drop table "blogschema.user";

/*==============================================================*/
/* Table: "blogschema.blog"                                     */
/*==============================================================*/
create table "blogschema.blog" (
   blog_id              bigint               not null,
   user_id              bigint               null,
   title                VARCHAR(255)         null,
   description          VARCHAR(255)         null,
   content              TEXT                 null,
   created              TIMESTAMP            null,
   status               INTEGER                 null,
   constraint "PK_BLOGSCHEMA.BLOG" primary key (blog_id)
);

/*==============================================================*/
/* Table: "blogschema.user"                                     */
/*==============================================================*/
create table "blogschema.user" (
   user_id              bigint               not null,
   username             VARCHAR(64)          null,
   avatar               VARCHAR(255)         null,
   email                VARCHAR(64)          null,
   password             VARCHAR(64)          null,
   status               bigint               null,
   created              TIMESTAMP            null,
   last_login           TIMESTAMP            null,
   constraint "PK_BLOGSCHEMA.USER" primary key (user_id)
);

alter table "blogschema.blog"
   add constraint FK_BLOGSCHE_REFERENCE_BLOGSCHE foreign key (user_id)
      references "blogschema.user" (user_id)
      on delete restrict on update restrict;









INSERT INTO "blogschema.user" (user_id,username,avatar,email,"password",status,created,last_login) VALUES (
'1', 
'root', 
'localhost', 
'lzy@gmail.com',
'root', 
'1', 
'2001-04-09 00:00:00', 
'2001-04-09 00:00:00');


INSERT INTO "blogschema.blog" (blog_id,user_id,title,description,"content",created,status)
VALUES
(0, 0, 'TEST','test','Just a simple test.','2001-04-09 00:00:00',1);

