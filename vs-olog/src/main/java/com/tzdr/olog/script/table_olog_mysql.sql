/*==============================================================*/
/* Table: SYS_OLOG                                              */
/*==============================================================*/
CREATE TABLE SYS_OLOG  (
   ID                   VARCHAR(32)                      NOT NULL,
   MODULE               VARCHAR(256),
   MODULE_NAME          VARCHAR(32),
   ACTION               VARCHAR(32),
   ACTION_NAME          VARCHAR(32),
   EXECUTE_MILLISECONDS bigint(10)                      NOT NULL,
   OPERATE_TIME         timestamp                           DEFAULT current_timestamp,
   OPERATE_USER         VARCHAR(64),
   OPERATE_USER_ID      bigint(10),
   REQUEST_PARAMETERS   VARCHAR(512),
   OPERATE_RESULT       int(1)                      DEFAULT 1 NOT NULL,
   OPERATE_MESSAGE      VARCHAR(512),
   CLIENT_INFORMATIONS  VARCHAR(256),
   DESCN                VARCHAR(256),
   VERSION               bigint(20),    
   CONSTRAINT PK_SYS_OLOG_ID PRIMARY KEY (ID)
);