CREATE TABLE MOTOR_DRIVER(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
DISPLAY_NAME VARCHAR(64),
GENDER VARCHAR(64),
TELEPHONE VARCHAR(64),
ADRESS VARCHAR(64),

DRIVING_NUMBER VARCHAR(64),
QUASI_TYPE VARCHAR(64),
CERTIFICATION VARCHAR(64),
REG_TIME TIMESTAMP DEFAULT 0,
VALID_TIME TIMESTAMP DEFAULT 0,

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_TRUCK(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
TRUCK_CATEGORY VARCHAR(64),
TRUCK_TYPE VARCHAR(64),
HEADSTOCK_NUMBER VARCHAR(64),
TRAILER_NUMBER VARCHAR(64),

HEADSTOCK_FUND DOUBLE(12,4),
TRAILER_FUND DOUBLE(12,4),

HEADSTOCK_DEPRECIATION DOUBLE(12,4),
TRAILER_DEPRECIATION DOUBLE(12,4),

MANUFACTURER VARCHAR(64),
PURCHASE_TIME TIMESTAMP DEFAULT 0,
ANNUAL_TIME TIMESTAMP DEFAULT 0,

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_TOLL(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
BEGIN_STATION VARCHAR(64),
BEGIN_TIME TIMESTAMP DEFAULT 0,
ARRIVE_STATION VARCHAR(64),
ARRIVE_TIME TIMESTAMP DEFAULT 0,
TOTAL_WEIGHT DOUBLE(12,4),
TRUCK_TYPE VARCHAR(64),

CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),
MOTOR_TRUCK_ID VARCHAR(64),
MOTOR_DRIVER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_PETROL(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
LUBRICATE_CAPACITY DOUBLE(12,4),
LUBRICATE_TIME TIMESTAMP DEFAULT 0,
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),

KILOMITER_COUNT DOUBLE(12,4),
FUEL_CONSUMPTION_LAST DOUBLE(12,4),
FUEL_CONSUMPTION_MONTH DOUBLE(12,4),

MOTOR_TRUCK_ID VARCHAR(64),
MOTOR_DRIVER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_MAINTAIN(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
MAINTAIN_TIME TIMESTAMP DEFAULT 0,
MAINTAIN_ITEM VARCHAR(64),
MAINTAIN_UNIT VARCHAR(64),
MAINTAIN_COUNT DOUBLE(12,4),
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
MONEY_AMOUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),

MOTOR_TRUCK_ID VARCHAR(64),
MOTOR_DRIVER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);


CREATE TABLE MOTOR_FEE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
FEE_TYPE VARCHAR(64),
INCOME_OR_EXPENSE VARCHAR(64),
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),

MOTOR_DISPATCH_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_DISPATCH(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
MOTOR_TRUCK VARCHAR(64),
MOTOR_DRIVER VARCHAR(64),

DISPATCH_NUMBER VARCHAR(64),
ORDER_NUMBER VARCHAR(64),
DELEGATE_PART VARCHAR(64),
DELEGATE_TIME TIMESTAMP DEFAULT 0,
CARGO_NAME VARCHAR(64),
CARGO_UNIT VARCHAR(64),
CARGO_WEIGHT DOUBLE(12,4),
CARGO_CAPACITY DOUBLE(12,4),
BOX_TYPE VARCHAR(64),
BOX_COUNT INT,
BOX_NUMBER VARCHAR(64),

PICK_ADDRESS VARCHAR(64),
PICK_TIME TIMESTAMP DEFAULT 0,
LOAD_ADDRESS VARCHAR(64),
LOAD_TIME TIMESTAMP DEFAULT 0,
RETURN_ADDRESS VARCHAR(64),
RETURN_TIME TIMESTAMP DEFAULT 0,

DEPARTURE VARCHAR(64),
DESTINATION VARCHAR(64),
DISPATCH_DEDUCT DOUBLE(12,4),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

CREATE TABLE MOTOR_INSURANCE(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
INSURANCE_TYPE VARCHAR(64),
INSURANCE_COMPANY VARCHAR(64),
START_TIME TIMESTAMP DEFAULT 0,
END_TIME TIMESTAMP DEFAULT 0,
PURCHASE_TIME TIMESTAMP DEFAULT 0,

CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),
MOTOR_TRUCK_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);

--福利
CREATE TABLE MOTOR_WEAL(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
WEAL_TYPE VARCHAR(64),
WEAL_TIME TIMESTAMP DEFAULT 0,
INCOME_OR_EXPENSE VARCHAR(64),
CURRENCY VARCHAR(64),
EXCHANGE_RATE DOUBLE(12,4),
MONEY_COUNT DOUBLE(12,4),
FAS_INVOICE_TYPE_ID VARCHAR(64),

MOTOR_TRUCK_ID VARCHAR(64),
MOTOR_DRIVER_ID VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP,
MODIFY_TIME TIMESTAMP,
DISP_INX INT
);