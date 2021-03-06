ALTER TABLE SYS_AUTH_USER_BASE ADD WX_NAME VARCHAR(64);
ALTER TABLE SYS_AUTH_USER ADD HIRE_TIME TIMESTAMP DEFAULT 0;
ALTER TABLE SYS_AUTH_USER ADD FIRE_TIME TIMESTAMP DEFAULT 0;

ALTER TABLE FRE_ORDER_BOX DROP WEIGHT;
ALTER TABLE FRE_ORDER_BOX DROP CAPACITY;
ALTER TABLE FRE_ORDER_BOX CHANGE DESCN LOCATION VARCHAR(128);
ALTER TABLE FRE_ORDER_BOX ADD DESCN VARCHAR(256);

ALTER TABLE FRE_BOX_REQUIRE CHANGE DESCN BL_NO VARCHAR(128);
ALTER TABLE FRE_BOX_REQUIRE ADD DESCN VARCHAR(256);

ALTER TABLE REP_TEMPLATE ADD BEAN_CLASS VARCHAR(64);
ALTER TABLE REP_TEMPLATE ADD METHOD_NAME VARCHAR(64);

ALTER TABLE FRE_EXPENSE_TYPE ADD REVENUE VARCHAR(64);

ALTER TABLE FRE_DEDUCT CHANGE BALANCE DEDUCT_BALANCE DOUBLE(12, 4);
ALTER TABLE FRE_DEDUCT ADD ORDER_BALANCE DOUBLE(12, 4);

UPDATE FRE_EXPENSE_TYPE SET REVENUE='T' WHERE REVENUE IS NULL;