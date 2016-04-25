CREATE TABLE SYS_AUTH_ORG_ENTITY_IDENTITY(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
PRIORITY INT,
ORG_ENTITY_ID VARCHAR(64),
USER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE SYS_AUTH_ORG_TYPE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
TYPE_NAME VARCHAR(64),
PRIORITY INT,

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE SYS_AUTH_ORG_ENTITY(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
ORG_ENTITY_NAME VARCHAR(64),
TYPE_ID VARCHAR(64),
PARENT_ORG_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE SYS_AUTH_POSITION(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
POSITION_NAME VARCHAR(64),
PRIORITY INT,
GRADE INT,
ORG_ENTITY_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);