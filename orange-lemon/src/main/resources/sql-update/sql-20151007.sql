CREATE TABLE MOTOR_JOURNEY(
ID VARCHAR(64) PRIMARY KEY NOT NULL,
JOURNEY_NUMBER VARCHAR(64),
JOURNEY_TYPE VARCHAR(64),
JOURNEY_ITEM VARCHAR(64),
JOURNEY_TIME TIMESTAMP DEFAULT 0,
DESTINATION VARCHAR(64),

OUT_KEY VARCHAR(64),
OUT_PARK VARCHAR(64),
OUT_TIME TIMESTAMP DEFAULT 0,
OUT_KILOMETER_COUNT INT,
RETURN_KEY VARCHAR(64),
RETURN_PARK VARCHAR(64),
RETURN_TIME TIMESTAMP DEFAULT 0,
RETURN_KILOMETER_COUNT INT,

MOTOR_DRIVER VARCHAR(64),
MOTOR_TRUCK VARCHAR(64),

DESCN VARCHAR(256),
STATUS VARCHAR(64),
CREATE_TIME TIMESTAMP DEFAULT 0,
MODIFY_TIME TIMESTAMP DEFAULT 0,
DISP_INX INT
);

ALTER TABLE MOTOR_DRIVER ADD DRIVER_TYPE VARCHAR(64);