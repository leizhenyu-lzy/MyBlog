drop table "User";

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" (
   user_id              INT20                not null,
   username             VARCHAR(64)          null,
   avatar               VARCHAR(255)         null,
   email                VARCHAR(64)          null,
   password             VARCHAR(64)          null,
   status               INT5                 null,
   created              TIMESTAMP            null,
   last_login           TIMESTAMP            null,
   constraint PK_USER primary key (user_id)
);


drop table Blog;

/*==============================================================*/
/* Table: Blog                                                  */
/*==============================================================*/
create table Blog (
   blog_id              INT20                not null,
   user_id              INT20                null,
   title                VARCHAR(255)         null,
   description          VARCHAR(255)         null,
   content              TEXT                 null,
   created              TIMESTAMP            null,
   status               INT4                 null,
   constraint PK_BLOG primary key (blog_id)
);
