--将 一些之前的动作委托委派给 曾勇刚
UPDATE fre_delegate SET USER_ID='167e56c8-a81e-11e3-8c18-a4db305e5cc5'
WHERE FRE_ACTION_ID 
IN(SELECT ID FROM FRE_ACTION WHERE FRE_ACTION_TYPE_ID 
IN(SELECT ID FROM FRE_ACTION_TYPE WHERE TYPE_NAME IN('空上吊箱','空下吊箱','重上吊箱','重下吊箱','重下重上吊箱')))