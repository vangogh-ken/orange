ALTER TABLE SYS_AUTH_USER CHANGE REG_TIME REG_TIME TIMESTAMP DEFAULT 0;
--提高费用读取效率
ALTER TABLE FRE_EXPENSE_BOX ADD INDEX FRE_EXPENSE_ID (FRE_EXPENSE_ID);
--提高动作数据读取效率
ALTER TABLE FRE_ACTION_BOX ADD INDEX FRE_ACTION_ID (FRE_ACTION_ID);