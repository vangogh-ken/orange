ALTER TABLE FRE_ACTION_VALUE ADD INDEX FRE_ACTION_ID_FRE_ACTION_FIELD_ID (FRE_ACTION_ID, FRE_ACTION_FIELD_ID);

UPDATE fre_action_field SET FOR_BOX='F' WHERE FOR_BOX IS NULL;