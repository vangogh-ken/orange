ALTER TABLE REP_DATA_SOURCE CHANGE CREATE_TIME CREATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP; 
ALTER TABLE REP_DATA_SOURCE CHANGE MODIFY_TIME MODIFY_TIME TIMESTAMP ON UPDATE CURRENT_TIMESTAMP; 