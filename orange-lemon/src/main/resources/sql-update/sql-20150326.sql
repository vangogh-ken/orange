ALTER TABLE FRE_DELEGATE CHANGE ACTION_ID FRE_ACTION_ID VARCHAR(64);
ALTER TABLE FRE_DELEGATE CHANGE PART_ID FRE_PART_ID VARCHAR(64);
ALTER TABLE FRE_DELEGATE CHANGE ORG_ID ORG_ENTITY_ID VARCHAR(64);

ALTER TABLE FRE_ACTION CHANGE ACTION_TYPE_ID FRE_ACTION_TYPE_ID VARCHAR(64);
ALTER TABLE FRE_ACTION CHANGE MAINTAIN_ID FRE_MAINTAIN_ID VARCHAR(64);

ALTER TABLE FRE_MAINTAIN CHANGE MAINTAIN_TYPE_ID FRE_MAINTAIN_TYPE_ID VARCHAR(64);
ALTER TABLE FRE_MAINTAIN CHANGE ORDER_ID FRE_ORDER_ID VARCHAR(64);

ALTER TABLE FRE_ACTION_TYPE_IDENTITY CHANGE ACTION_TYPE_ID FRE_ACTION_TYPE_ID VARCHAR(64);

ALTER TABLE FRE_MAINTAIN_ACTION CHANGE MAINTAIN_TYPE_ID FRE_MAINTAIN_TYPE_ID VARCHAR(64);
ALTER TABLE FRE_MAINTAIN_ACTION CHANGE ACTION_TYPE_ID FRE_ACTION_TYPE_ID VARCHAR(64);

ALTER TABLE FRE_PRICE DROP BEGIN_STATION;
ALTER TABLE FRE_PRICE DROP ARRIVE_STATION;
