CREATE TABLE FRE_ACTION(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
DELEGATE VARCHAR(64),
INTERNAL VARCHAR(64),
PRIME VARCHAR(64),
FRE_ACTION_TYPE_ID VARCHAR(64),
FRE_MAINTAIN_ID VARCHAR(64),
DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE FRE_ACTION_TYPE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
TYPE_NAME VARCHAR(64),
DELEGATE VARCHAR(64),
INTERNAL VARCHAR(64),
PRIME VARCHAR(64),
DELEGATE_TEMPLATE_ID VARCHAR(64),
SCOPE VARCHAR(64),
DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE FRE_ACTION_FIELD(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
FIELD_NAME VARCHAR(64),
FIELD_COLUMN VARCHAR(64),
FIELD_TYPE VARCHAR(64),
REQUIRED VARCHAR(64),
PARTICIPATE VARCHAR(64),
CAN_SELECT VARCHAR(64),
VATTR_ID VARCHAR(64),
VCLS_ID VARCHAR(64),
VCOLUMN VARCHAR(64),
VFILTER_ID VARCHAR(64),
FOR_BOX VARCHAR(64),

ACTION_TYPE_ID VARCHAR(64),
DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE FRE_ACTION_VALUE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
STRING_VALUE VARCHAR(64),
TEXT_VALUE TEXT,
DOUBLE_VALUE DOUBLE(12,4),
INT_VALUE INT,
DATE_VALUE TIMESTAMP,
FRE_ACTION_FIELD_ID VARCHAR(64),
FRE_ACTION_ID VARCHAR(64),
FRE_ORDER_BOX_ID VARCHAR(64),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE FRE_MAINTAIN_ACTION(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
FRE_MAINTAIN_TYPE_ID VARCHAR(64),
FRE_ACTION_TYPE_ID VARCHAR(64),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);