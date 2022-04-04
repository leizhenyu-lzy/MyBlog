/*==============================================================*/
/* DBMS name:      PostgreSQL	                                */
/*==============================================================*/

create schema blogschema;

-- drop table blogschema.blogs;
-- drop table blogschema.users;

/*==============================================================*/
/* Table:  blogschema.blogs                                     */
/*==============================================================*/
create table blogschema.blogs (
   blog_id              bigint               not null,
   user_id              bigint               null,
   title                VARCHAR(255)         null,
   description          VARCHAR(255)         null,
   blog_content         TEXT                 null,
   created              TIMESTAMP            null,
   status               INTEGER              null,
   constraint PK_BLOGSCHEMA_BLOGS primary key (blog_id)
);

/*==============================================================*/
/* Table:  blogschema.users                                     */
/*==============================================================*/
create table blogschema.users (
   user_id              bigint               not null,
   username             VARCHAR(64)          null,
   avatar               VARCHAR(255)         null,
   email                VARCHAR(64)          null,
   user_password        VARCHAR(64)          null,
   status               bigint               null,
   created              TIMESTAMP            null,
   last_login           TIMESTAMP            null,
   constraint PK_BLOGSCHEMA_USERS primary key (user_id)
);









alter table blogschema.blogs
   add constraint FK_BLOGSCHE_REFERENCE_BLOGSCHE foreign key (user_id)
      references blogschema.users (user_id)
      on delete restrict on update restrict;









INSERT INTO blogschema.users (user_id,username,avatar,email,user_password,status,created,last_login) VALUES (
0, 
'root', 
'localhost', 
'lzy@gmail.com',
'root', 
0, 
'2001-04-09 00:00:00', 
'2001-04-09 00:00:00');


INSERT INTO blogschema.blogs (blog_id,user_id,title,description,blog_content,created,status)
VALUES
(0, 0, 'TEST','test','Just a simple test.','2001-04-09 00:00:00',1);






















create sequence blog_id_seq start with 1 increment by 1 no minvalue no maxvalue cache 1;

alter table blogschema.blogs alter column blog_id set default nextval('blog_id_seq');

create sequence user_id_seq start with 1 increment by 1 no minvalue no maxvalue cache 1;

alter table blogschema.users alter column user_id set default nextval('user_id_seq');

drop sequence blog_id_seq cascade