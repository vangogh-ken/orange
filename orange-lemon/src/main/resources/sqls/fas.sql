CREATE TABLE FAS_INVOICE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
INVOICE_NUMBER VARCHAR(64),
FAS_INVOICE_TYPE_ID VARCHAR(64),
RELEASE_PART VARCHAR(64),
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
INCOME_OR_EXPENSE VARCHAR(64),
RELEASE_TIME TIMESTAMP,
RECORD_TIME TIMESTAMP,
CLAIM_TIME TIMESTAMP,

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE FAS_ACCOUNT(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
ACCOUNT_NUMBER VARCHAR(64),
MONEY_COUNT DOUBLE(12,4),
BANK_NAME VARCHAR(64),
BANK_ADDRESS VARCHAR(64),
CURRENCY VARCHAR(64),
FRE_PART_ID VARCHAR(64),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE FAS_RECONCILE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
INCOME_OR_EXPENSE VARCHAR(64),
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
SOURCE_ID VARCHAR(64),
TARGET_ID VARCHAR(64),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE FAS_EXCHANGE_RATE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
START_TIME TIMESTAMP,
END_TIME TIMESTAMP,

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE FAS_DUE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
DUE_NUMBER VARCHAR(64),

CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
DUE_COUNT DOUBLE(12,4),
ACTUAL_COUNT DOUBLE(12,4),

FRE_PART_ID_PAY VARCHAR(64),
FAS_ACCOUNT_ID_PAY VARCHAR(64),

FRE_PART_ID_DUE VARCHAR(64),
FAS_ACCOUNT_ID_DUE VARCHAR(64),

DUE_TIME TIMESTAMP,

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE FAS_PAY(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
PAY_NUMBER VARCHAR(64),
PAY_TYPE VARCHAR(64),
FRE_PART_ID VARCHAR(64),
USER_ID VARCHAR(64),
ORG_ENTITY_ID VARCHAR(64),
APPLY_TIME TIMESTAMP,

PAY_FOR VARCHAR(128),
PAY_TIME TIMESTAMP,
INVOLVE_ORDER_NUMBER VARCHAR(128),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE FAS_PAY_INVOICE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,

FAS_PAY_ID VARCHAR(64),
FRE_INVOICE_ID VARCHAR(64),

DESCN VARCHAR(128),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);