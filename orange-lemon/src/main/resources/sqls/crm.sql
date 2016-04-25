CREATE TABLE CRM_CUSTOMER(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
CUSTOMER_Type VARCHAR(64),
CUSTOMER_NAME VARCHAR(64),
CUSTOMER_GRADE VARCHAR(64),
CREDIT_GRADE VARCHAR(64),

CARGO_CONDITION VARCHAR(64),
TRANSPORT_TYPE VARCHAR(64),

ADDRESS VARCHAR(64),
COUNTRY VARCHAR(64),
PROVINCE VARCHAR(64),
CITY VARCHAR(64),

CONTACT_PERSON VARCHAR(64),
CONTACT_POSITION VARCHAR(64),
CONTACT_PHONE VARCHAR(64),
CONTACT_MAIL VARCHAR(64),

FOLLOW_TYPE VARCHAR(64),
ORG_ENTITY_ID VARCHAR(64),
USER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP DEFAULT 0,
MODIFY_TIME TIMESTAMP DEFAULT 0,
DISP_INX INT
);

CREATE TABLE CRM_PARTNER(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
PARTNER_Type VARCHAR(64),
PARTNER_NAME VARCHAR(64),
PARTNER_GRADE VARCHAR(64),
ENGAGE_SCOPE VARCHAR(64),
CORE_ADVANTAGE VARCHAR(64),

ADDRESS VARCHAR(64),
COUNTRY VARCHAR(64),
PROVINCE VARCHAR(64),
CITY VARCHAR(64),

CONTACT_PERSON VARCHAR(64),
CONTACT_POSITION VARCHAR(64),
CONTACT_PHONE VARCHAR(64),
CONTACT_MAIL VARCHAR(64),

FOLLOW_TYPE VARCHAR(64),
ORG_ENTITY_ID VARCHAR(64),
USER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP DEFAULT 0,
MODIFY_TIME TIMESTAMP DEFAULT 0,
DISP_INX INT
);

CREATE TABLE CRM_FOLLOW_CUSTOMER(
ID VARCHAR(64) PRIMARY KEY NOT NULL,

CONTACT_PERSON VARCHAR(64),
CONTACT_POSITION VARCHAR(64),
CONTACT_PHONE VARCHAR(64),

LAST_FOLLOW_TIME TIMESTAMP DEFAULT 0,
NEXT_FOLLOW_TIME TIMESTAMP DEFAULT 0,
CURRENT_FOLLOW_TIME TIMESTAMP DEFAULT 0,

FOLLOW_CONTENT VARCHAR(256),
CHANCE_PLAN VARCHAR(256),
CHANCE_SUGGEST VARCHAR(256),

ORG_ENTITY_ID VARCHAR(64),
USER_ID VARCHAR(64),

CRM_CUSTOMER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP DEFAULT 0,
MODIFY_TIME TIMESTAMP DEFAULT 0,
DISP_INX INT
);

CREATE TABLE CRM_FOLLOW_PARTNER(
ID VARCHAR(64) PRIMARY KEY NOT NULL,

CONTACT_PERSON VARCHAR(64),
CONTACT_POSITION VARCHAR(64),
CONTACT_PHONE VARCHAR(64),

LAST_FOLLOW_TIME TIMESTAMP DEFAULT 0,
NEXT_FOLLOW_TIME TIMESTAMP DEFAULT 0,
CURRENT_FOLLOW_TIME TIMESTAMP DEFAULT 0,

FOLLOW_CONTENT VARCHAR(256),
CHANCE_PLAN VARCHAR(256),
CHANCE_SUGGEST VARCHAR(256),

ORG_ENTITY_ID VARCHAR(64),
USER_ID VARCHAR(64),

CRM_PARTNER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP DEFAULT 0,
MODIFY_TIME TIMESTAMP DEFAULT 0,
DISP_INX INT
);