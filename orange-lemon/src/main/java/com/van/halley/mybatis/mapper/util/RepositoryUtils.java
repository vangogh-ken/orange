package com.van.halley.mybatis.mapper.util;

import java.util.HashMap;
import java.util.Map;

import com.van.halley.auth.db.persistence.PrivilegeMaster;
import com.van.halley.db.persistence.entity.*;

public class RepositoryUtils {

	public static void main(String[] args) {
		ItemImageDo();
	}
	
	public static void ItemImageDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("imageName", "IMAGE_NAME");
		map.put("imageUrl", "IMAGE_URL");
		map.put("imageRef", "IMAGE_REF");
		map.put("imageFormat", "IMAGE_FORMAT");
		map.put("imageSize", "IMAGE_SIZE");
		map.put("itemSubstanceId", "ITEM_SUBSTANCE_ID");
		map.put("creatorId", "CREATOR_ID");
		map.put("modifierId", "MODIFIER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ItemImage> deal = new MapperCreation<ItemImage>();
		String content = deal.createXml(ItemImage.class, map, "ITEM_IMAGE");
		FileCreation.doCreate("item-image-mapper.xml", content);
		
		DaoCreation.doCreate("ItemImage");
	}
	
	public static void ItemAttributeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("attributeName", "ATTR_NAME");
		map.put("attributeColumn", "ATTR_COLUMN");
		map.put("attributeType", "ATTR_TYPE");
		map.put("isRequired", "IS_REQUIRED");
		map.put("isSelected", "IS_SELECTED");
		map.put("vAttrId", "VATTR_ID");
		map.put("itemCategoryId", "ITEM_CATEGORY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ItemAttribute> deal = new MapperCreation<ItemAttribute>();
		String content = deal.createXml(ItemAttribute.class, map, "ITEM_ATTRIBUTE");
		FileCreation.doCreate("item-attribute-mapper.xml", content);
		
		DaoCreation.doCreate("ItemAttribute");
	}
	
	public static void ItemSubstanceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("itemNumber", "ITEM_NUMBER");
		map.put("itemModel", "ITEM_MODEL");
		map.put("itemName", "ITEM_NAME");
		map.put("itemCategoryId", "ITEM_CATEGORY_ID");
		map.put("itemSupplierId", "ITEM_SUPPLIER_ID");
		map.put("itemManufacturerId", "ITEM_MFRS_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ItemSubstance> deal = new MapperCreation<ItemSubstance>();
		String content = deal.createXml(ItemSubstance.class, map, "ITEM_SUBSTANCE");
		FileCreation.doCreate("item-substance-mapper.xml", content);
		
		DaoCreation.doCreate("ItemSubstance");
	}
	
	public static void ItemCategoryDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("categoryName", "CATEGORY_NAME");
		map.put("categoryIndex", "CATEGORY_INDEX");
		map.put("aboveCategoryId", "ABOVE_CATEGORY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ItemCategory> deal = new MapperCreation<ItemCategory>();
		String content = deal.createXml(ItemCategory.class, map, "ITEM_CATEGORY");
		FileCreation.doCreate("item-category-mapper.xml", content);
		
		DaoCreation.doCreate("ItemCategory");
	}
	
	public static void DiskAclDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("diskShareId", "OUT_DISK_SHARE_ID");
		map.put("accessaryId", "ACCESSARY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<DiskAcl> deal = new MapperCreation<DiskAcl>();
		String content = deal.createXml(DiskAcl.class, map, "OUT_DISK_ACL");
		FileCreation.doCreate("out-disk-acl-mapper.xml", content);
		
		DaoCreation.doCreate("DiskAcl");
	}
	
	public static void DiskShareDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("shareType", "SHARE_TYPE");
		map.put("shareTime", "SHARE_TIME");
		map.put("expireTime", "EXPIRE_TIME");
		map.put("countView", "COUNT_VIEW");
		map.put("countSave", "COUNT_SAVE");
		map.put("countDownload", "COUNT_DOWNLOAD");
		
		map.put("sharer", "SHARER_ID");
		map.put("diskInfo", "OUT_DISK_INFO_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<DiskShare> deal = new MapperCreation<DiskShare>();
		String content = deal.createXml(DiskShare.class, map, "OUT_DISK_SHARE");
		FileCreation.doCreate("out-disk-share-mapper.xml", content);
		
		DaoCreation.doCreate("DiskShare");
	}
	
	public static void DiskInfoDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("fileName", "FILE_NAME");
		map.put("fileSuffix", "FILE_SUFFIX");
		map.put("fileRef", "FILE_REF");
		map.put("fileVersion", "FILE_VERSION");
		map.put("fileDir", "FILE_DIR");
		map.put("fileSize", "FILE_SIZE");
		
		map.put("creator", "CREATOR_ID");
		map.put("modifier", "MODIFIER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<DiskInfo> deal = new MapperCreation<DiskInfo>();
		String content = deal.createXml(DiskInfo.class, map, "OUT_DISK_INFO");
		FileCreation.doCreate("out-disk-info-mapper.xml", content);
		
		DaoCreation.doCreate("DiskInfo");
	}
	
	public static void PrivilegeMasterDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("privilegeType", "PRIVILEGE_TYPE");
		map.put("privilegeMasterId", "PRIVILEGE_MASTER_ID");
		map.put("privilegeMasterValue", "PRIVILEGE_MASTER_VALUE");
		map.put("privilegeAccessId", "PRIVILEGE_ACCESS_ID");
		map.put("privilegeAccessValue", "PRIVILEGE_ACCESS_VALUE");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<PrivilegeMaster> deal = new MapperCreation<PrivilegeMaster>();
		String content = deal.createXml(PrivilegeMaster.class, map, "AUTH_SYS_PRIVILEGE_MASTER");
		FileCreation.doCreate("auth-privilege-master-mapper.xml", content);
		
		DaoCreation.doCreate("PrivilegeMaster");
	}
	
	public static void SalaryBonusDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("predictSalary", "PREDICT_SALARY");
		map.put("predictPayment", "PREDICT_PAYMENT");
		map.put("predictDebit", "PREDICT_DEBIT");
		map.put("actualPayment", "ACTUAL_PAYMENT");
		map.put("salaryYear", "SALARY_YEAR");
		map.put("salaryMonth", "SALARY_MONTH");
		map.put("computeTime", "COMPUTE_TIME");
		map.put("confirmTime", "CONFIRM_TIME");
		
		map.put("salaryGrade", "SALARY_GRADE_ID");
		
		map.put("postBonus", "POST_BONUS");
		map.put("performanceBonus", "PERFORMANCE_BONUS");
		map.put("deductBonus", "DEDUCT_BONUS");
		map.put("executiveBonus", "EXECUTIVE_BONUS");
		map.put("riskBonus", "RISK_BONUS");
		map.put("specialBonus", "SPECIAL_BONUS");
		
		map.put("senioritySubsidy", "SENIORITY_SUBSIDY");
		map.put("hosingSubsidy", "HOSING_SUBSIDY");
		map.put("lunchSubsidy", "LUNCH_SUBSIDY");
		map.put("overtimeSubsidy", "OVERTIME_SUBSIDY");
		map.put("protectSubsidy", "PROTECT_SUBSIDY");
		map.put("executiveDebit", "EXECUTIVE_DEBIT");
		map.put("riskDebit", "RISK_DEBIT");
		map.put("otherDebit", "OTHER_DEBIT");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<SalaryBonus> deal = new MapperCreation<SalaryBonus>();
		String content = deal.createXml(SalaryBonus.class, map, "SALARY_BONUS");
		FileCreation.doCreate("salary-bonus-mapper.xml", content);
		
		DaoCreation.doCreate("SalaryBonus");
	}
	
	public static void SalaryBasicDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("predictSalary", "PREDICT_SALARY");
		map.put("predictPayment", "PREDICT_PAYMENT");
		map.put("predictDebit", "PREDICT_DEBIT");
		map.put("actualPayment", "ACTUAL_PAYMENT");
		map.put("salaryYear", "SALARY_YEAR");
		map.put("salaryMonth", "SALARY_MONTH");
		map.put("computeTime", "COMPUTE_TIME");
		map.put("confirmTime", "CONFIRM_TIME");
		
		map.put("salaryGrade", "SALARY_GRADE_ID");
		
		map.put("endowmentIndividual", "ENDOWMENT_INDIVIDUAL");
		map.put("endowmentCompany", "ENDOWMENT_COMPANY");
		map.put("medicalIndividual", "MEDICAL_INDIVIDUAL");
		map.put("medicalCompany", "MEDICAL_COMPANY");
		map.put("unemploymentIndividual", "UNEMPLOYMENT_INDIVIDUAL");
		map.put("unemploymentCompany", "UNEMPLOYMENT_COMPANY");
		map.put("injuryIndividual", "INJURY_INDIVIDUAL");
		map.put("injuryCompany", "INJURY_COMPANY");
		map.put("maternityIndividual", "MATERNITY_INDIVIDUAL");
		map.put("maternityCompany", "MATERNITY_COMPANY");
		map.put("housingIndividual", "HOSING_INDIVIDUAL");
		map.put("housingCompany", "HOSING_COMPANY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<SalaryBasic> deal = new MapperCreation<SalaryBasic>();
		String content = deal.createXml(SalaryBasic.class, map, "SALARY_BASIC");
		FileCreation.doCreate("salary-basic-mapper.xml", content);
		
		DaoCreation.doCreate("SalaryBasic");
	}
	
	public static void SalaryGradeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("gradeCount", "GRADE_COUNT");
		map.put("basicSalary", "BASIC_SALARY");
		map.put("postSalary", "POST_SALARY");
		map.put("startTime", "START_TIME");
		map.put("endTime", "END_TIME");
		map.put("user", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<SalaryGrade> deal = new MapperCreation<SalaryGrade>();
		String content = deal.createXml(SalaryGrade.class, map, "SALARY_GRADE");
		FileCreation.doCreate("salary-grade-mapper.xml", content);
		
		DaoCreation.doCreate("SalaryGrade");
	}
	
	public static void RoleQuartzDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("roleId", "ROLE_ID");
		map.put("quartzTaskId", "QUARTZ_TASK_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<RoleQuartz> deal = new MapperCreation<RoleQuartz>();
		String content = deal.createXml(RoleQuartz.class, map, "SYS_AUTH_ROLE_QUARTZ");
		FileCreation.doCreate("role-quartz-mapper.xml", content);
		
		DaoCreation.doCreate("RoleQuartz");
	}
	
	public static void QuartzJobDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("jobStatus", "JOB_STATUS");
		map.put("concurrent", "CONCURRENT");
		map.put("quartzTask", "QUARTZ_TASK_ID");
		map.put("quartzCron", "QUARTZ_CRON_ID");
		map.put("owner", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<QuartzJob> deal = new MapperCreation<QuartzJob>();
		String content = deal.createXml(QuartzJob.class, map, "QUARTZ_JOB");
		FileCreation.doCreate("quartz-job-mapper.xml", content);
		
		DaoCreation.doCreate("QuartzJob");
	}
	
	public static void QuartzGroupDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("groupName", "GROUP_NAME");
		map.put("groupKey", "GROUP_KEY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<QuartzGroup> deal = new MapperCreation<QuartzGroup>();
		String content = deal.createXml(QuartzGroup.class, map, "QUARTZ_GROUP");
		FileCreation.doCreate("quartz-group-mapper.xml", content);
		
		DaoCreation.doCreate("QuartzGroup");
	}
	
	public static void QuartzCronDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("cronName", "CRON_NAME");
		map.put("cronExpression", "CRON_EXPRESSION");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<QuartzCron> deal = new MapperCreation<QuartzCron>();
		String content = deal.createXml(QuartzCron.class, map, "QUARTZ_CRON");
		FileCreation.doCreate("quartz-cron-mapper.xml", content);
		
		DaoCreation.doCreate("QuartzCron");
	}
	
	public static void QuartzTaskDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("taskName", "TASK_NAME");
		map.put("taskKey", "TASK_KEY");
		map.put("springId", "SPRING_ID");
		map.put("beanClass", "BEAN_CLASS");
		map.put("methodName", "METHOD_NAME");
		map.put("quartzGroup", "QUARTZ_GROUP_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<QuartzTask> deal = new MapperCreation<QuartzTask>();
		String content = deal.createXml(QuartzTask.class, map, "QUARTZ_TASK");
		FileCreation.doCreate("quartz-task-mapper.xml", content);
		
		DaoCreation.doCreate("QuartzTask");
	}
	
	public static void CrmFollowPartnerDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("contactPerson", "CONTACT_PERSON");
		map.put("contactPosition", "CONTACT_POSITION");
		map.put("contactPhone", "CONTACT_PHONE");
		
		map.put("lastFollowTime", "LAST_FOLLOW_TIME");
		map.put("nextFollowTime", "NEXT_FOLLOW_TIME");
		map.put("currentFollowTime", "CURRENT_FOLLOW_TIME");
		
		map.put("followContent", "FOLLOW_CONTENT");
		map.put("chancePlan", "CHANCE_PLAN");
		map.put("chanceSuggest", "CHANCE_SUGGEST");
		
		map.put("crmPartner", "CRM_PARTNER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("follower", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<CrmPartnerFollow> deal = new MapperCreation<CrmPartnerFollow>();
		String content = deal.createXml(CrmPartnerFollow.class, map, "CRM_PARTNER_FOLLOW");
		FileCreation.doCreate("crm-partner-follow-mapper.xml", content);
		
		DaoCreation.doCreate("CrmPartnerFollow");
	}
	
	public static void CrmFollowCustomerDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("contactPerson", "CONTACT_PERSON");
		map.put("contactPosition", "CONTACT_POSITION");
		map.put("contactPhone", "CONTACT_PHONE");
		
		map.put("lastFollowTime", "LAST_FOLLOW_TIME");
		map.put("nextFollowTime", "NEXT_FOLLOW_TIME");
		map.put("currentFollowTime", "CURRENT_FOLLOW_TIME");
		
		map.put("followContent", "FOLLOW_CONTENT");
		map.put("chancePlan", "CHANCE_PLAN");
		map.put("chanceSuggest", "CHANCE_SUGGEST");
		
		map.put("crmCustomer", "CRM_CUSTOMER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("follower", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<CrmCustomerFollow> deal = new MapperCreation<CrmCustomerFollow>();
		String content = deal.createXml(CrmCustomerFollow.class, map, "CRM_CUSTOMER_FOLLOW");
		FileCreation.doCreate("crm-customer-follow-mapper.xml", content);
		
		DaoCreation.doCreate("CrmCustomerFollow");
	}
	
	public static void CrmPartnerDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("partnerType", "PARTNER_Type");
		map.put("partnerName", "PARTNER_NAME");
		map.put("partnerGrade", "PARTNER_GRADE");
		map.put("engageScope", "ENGAGE_SCOPE");
		map.put("coreAdvantage", "CORE_ADVANTAGE");
		
		map.put("address", "ADDRESS");
		map.put("country", "COUNTRY");
		map.put("province", "PROVINCE");
		map.put("city", "CITY");
		
		map.put("contactPerson", "CONTACT_PERSON");
		map.put("contactPosition", "CONTACT_POSITION");
		map.put("contactPhone", "CONTACT_PHONE");
		map.put("contactMail", "CONTACT_MAIL");
		
		map.put("followType", "FOLLOW_TYPE");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("follower", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<CrmPartner> deal = new MapperCreation<CrmPartner>();
		String content = deal.createXml(CrmPartner.class, map, "CRM_PARTNER");
		FileCreation.doCreate("crm-partner-mapper.xml", content);
		
		DaoCreation.doCreate("CrmPartner");
	}
	
	public static void CrmCustomerDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("customerType", "CUSTOMER_Type");
		map.put("customerName", "CUSTOMER_NAME");
		map.put("customerGrade", "CUSTOMER_GRADE");
		map.put("creditGrade", "CREDIT_GRADE");
		
		map.put("cargoCondition", "CARGO_CONDITION");
		map.put("transportType", "TRANSPORT_TYPE");
		
		map.put("address", "ADDRESS");
		map.put("country", "COUNTRY");
		map.put("province", "PROVINCE");
		map.put("city", "CITY");
		
		map.put("contactPerson", "CONTACT_PERSON");
		map.put("contactPosition", "CONTACT_POSITION");
		map.put("contactPhone", "CONTACT_PHONE");
		map.put("contactMail", "CONTACT_MAIL");
		
		map.put("followType", "FOLLOW_TYPE");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("follower", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<CrmCustomer> deal = new MapperCreation<CrmCustomer>();
		String content = deal.createXml(CrmCustomer.class, map, "CRM_CUSTOMER");
		FileCreation.doCreate("crm-customer-mapper.xml", content);
		
		DaoCreation.doCreate("CrmCustomer");
	}
	
	/*public static void MotorcadeJourneyDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("journeyNumber", "JOURNEY_NUMBER");
		freightBoxMap.put("journeyType", "JOURNEY_TYPE");
		freightBoxMap.put("journeyItem", "JOURNEY_ITEM");
		freightBoxMap.put("journeyTime", "JOURNEY_TIME");
		freightBoxMap.put("destination", "DESTINATION");
		
		freightBoxMap.put("outKey", "OUT_KEY");
		freightBoxMap.put("outPark", "OUT_PARK");
		freightBoxMap.put("outTime", "OUT_TIME");
		freightBoxMap.put("outKilometerCount", "OUT_KILOMETER_COUNT");
		freightBoxMap.put("returnKey", "RETURN_KEY");
		freightBoxMap.put("returnPark", "RETURN_PARK");
		freightBoxMap.put("returnTime", "RETURN_TIME");
		freightBoxMap.put("returnKilometerCount", "RETURN_KILOMETER_COUNT");
		
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		AutoCreateXMLMaper<MotorcadeJourney> deal = new AutoCreateXMLMaper<MotorcadeJourney>();
		String content = deal.createXml(MotorcadeJourney.class, freightBoxMap, "MOTOR_JOURNEY");
		FileCreation.doCreate("motor-journey-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeJourney");
	}
	*/
	public static void MotorcadeWealDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("wealType", "WEAL_TYPE");
		freightBoxMap.put("wealTime", "WEAL_TIME");
		
		freightBoxMap.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER_ID");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeWeal> deal = new MapperCreation<MotorcadeWeal>();
		String content = deal.createXml(MotorcadeWeal.class, freightBoxMap, "MOTOR_WEAL");
		FileCreation.doCreate("motor-weal-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeWeal");
	}
	
	public static void MotorcadeInsuranceDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("insuranceType", "INSURANCE_TYPE");
		freightBoxMap.put("insuranceCompany", "INSURANCE_COMPANY");
		freightBoxMap.put("startTime", "START_TIME");
		freightBoxMap.put("endTime", "END_TIME");
		freightBoxMap.put("purchaseTime", "PURCHASE_TIME");
		
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeInsurance> deal = new MapperCreation<MotorcadeInsurance>();
		String content = deal.createXml(MotorcadeInsurance.class, freightBoxMap, "MOTOR_INSURANCE");
		FileCreation.doCreate("motor-insurance-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeInsurance");
	}
	
	public static void MotorcadeDispatchDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("dispatchNumber", "DISPATCH_NUMBER");
		freightBoxMap.put("orderNumber", "ORDER_NUMBER");
		freightBoxMap.put("delegatePart", "DELEGATE_PART");
		freightBoxMap.put("delegateTime", "DELEGATE_TIME");
		
		freightBoxMap.put("cargoName", "CARGO_NAME");
		freightBoxMap.put("cargoUnit", "CARGO_UNIT");
		freightBoxMap.put("cargoWeight", "CARGO_WEIGHT");
		freightBoxMap.put("cargoCapacity", "CARGO_CAPACITY");
		freightBoxMap.put("boxType", "BOX_TYPE");
		freightBoxMap.put("boxCount", "BOX_COUNT");
		freightBoxMap.put("boxNumber", "BOX_NUMBER");
		
		freightBoxMap.put("pickAddress", "PICK_ADDRESS");
		freightBoxMap.put("pickTime", "PICK_TIME");
		freightBoxMap.put("loadAddress", "LOAD_ADDRESS");
		freightBoxMap.put("loadTime", "LOAD_TIME");
		freightBoxMap.put("returnAddress", "RETURN_ADDRESS");
		freightBoxMap.put("returnTime", "RETURN_TIME");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK");
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER");
		
		freightBoxMap.put("departure", "DEPARTURE");
		freightBoxMap.put("destination", "DESTINATION");
		freightBoxMap.put("dispatchDeduct", "DISPATCH_DEDUCT");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeDispatch> deal = new MapperCreation<MotorcadeDispatch>();
		String content = deal.createXml(MotorcadeDispatch.class, freightBoxMap, "MOTOR_DISPATCH");
		FileCreation.doCreate("motor-dispatch-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeDispatch");
	}
	
	public static void MotorcadeFeeDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("feeType", "FEE_TYPE");
		freightBoxMap.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		
		freightBoxMap.put("motorcadeDispatch", "MOTOR_DISPATCH_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeFee> deal = new MapperCreation<MotorcadeFee>();
		String content = deal.createXml(MotorcadeFee.class, freightBoxMap, "MOTOR_FEE");
		FileCreation.doCreate("motor-fee-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeFee");
	}
	
	public static void MotorcadeMaintainDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("maintainItem", "MAINTAIN_ITEM");
		freightBoxMap.put("maintainUnit", "MAINTAIN_UNIT");
		freightBoxMap.put("maintainCount", "MAINTAIN_COUNT");
		
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("moneyAmount", "MONEY_AMOUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		
		freightBoxMap.put("maintainTime", "MAINTAIN_TIME");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK_ID");
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeMaintain> deal = new MapperCreation<MotorcadeMaintain>();
		String content = deal.createXml(MotorcadeMaintain.class, freightBoxMap, "MOTOR_MAINTAIN");
		FileCreation.doCreate("motor-maintain-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeMaintain");
	}
	
	public static void MotorcadePetrolDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("lubricateCapacity", "LUBRICATE_CAPACITY");
		freightBoxMap.put("lubricateTime", "LUBRICATE_TIME");
		
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		
		freightBoxMap.put("kilometerCount", "KILOMITER_COUNT");
		freightBoxMap.put("fuelConsumptionLast", "FUEL_CONSUMPTION_LAST");
		freightBoxMap.put("fuelConsumptionMonth", "FUEL_CONSUMPTION_MONTH");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK_ID");
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadePetrol> deal = new MapperCreation<MotorcadePetrol>();
		String content = deal.createXml(MotorcadePetrol.class, freightBoxMap, "MOTOR_PETROL");
		FileCreation.doCreate("motor-petrol-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadePetrol");
	}
	
	public static void MotorcadeTollDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("beginStation", "BEGIN_STATION");
		freightBoxMap.put("beginTime", "BEGIN_TIME");
		freightBoxMap.put("arriveStation", "ARRIVE_STATION");
		freightBoxMap.put("arriveTime", "ARRIVE_TIME");
		freightBoxMap.put("totalWeight", "TOTAL_WEIGHT");
		freightBoxMap.put("truckType", "TRUCK_TYPE");
		
		freightBoxMap.put("currency", "CURRENCY");
		freightBoxMap.put("exchangeRate", "EXCHANGE_RATE");
		freightBoxMap.put("moneyCount", "MONEY_COUNT");
		freightBoxMap.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		freightBoxMap.put("motorcadeTruck", "MOTOR_TRUCK_ID");
		freightBoxMap.put("motorcadeDriver", "MOTOR_DRIVER_ID");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeToll> deal = new MapperCreation<MotorcadeToll>();
		String content = deal.createXml(MotorcadeToll.class, freightBoxMap, "MOTOR_TOLL");
		FileCreation.doCreate("motor-toll-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeToll");
	}
	
	public static void MotorcadeTruckDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("truckCategory", "TRUCK_CATEGORY");
		freightBoxMap.put("truckType", "TRUCK_TYPE");
		freightBoxMap.put("headstockNumber", "HEADSTOCK_NUMBER");
		freightBoxMap.put("trailerNumber", "TRAILER_NUMBER");
		freightBoxMap.put("headstockFund", "HEADSTOCK_FUND");
		freightBoxMap.put("trailerFund", "TRAILER_FUND");
		freightBoxMap.put("headstockDepreciation", "HEADSTOCK_DEPRECIATION");
		freightBoxMap.put("trailerDepreciation", "TRAILER_DEPRECIATION");
		
		freightBoxMap.put("manufacturer", "MANUFACTURER");
		freightBoxMap.put("purchaseTime", "PURCHASE_TIME");
		freightBoxMap.put("annualTime", "ANNUAL_TIME");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeTruck> deal = new MapperCreation<MotorcadeTruck>();
		String content = deal.createXml(MotorcadeTruck.class, freightBoxMap, "MOTOR_TRUCK");
		FileCreation.doCreate("motor-truck-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeTruck");
	}
	
	public static void MotorcadeDriverDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("displayName", "DISPLAY_NAME");
		freightBoxMap.put("gender", "GENDER");
		freightBoxMap.put("telephone", "TELEPHONE");
		freightBoxMap.put("address", "ADRESS");
		freightBoxMap.put("drivingNumber", "DRIVING_NUMBER");
		freightBoxMap.put("quasiType", "QUASI_TYPE");
		freightBoxMap.put("registerTime", "REG_TIME");
		freightBoxMap.put("validTime", "VALID_TIME");
		freightBoxMap.put("certification", "CERTIFICATION");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<MotorcadeDriver> deal = new MapperCreation<MotorcadeDriver>();
		String content = deal.createXml(MotorcadeDriver.class, freightBoxMap, "MOTOR_DRIVER");
		FileCreation.doCreate("motor-driver-mapper.xml", content);
		
		DaoCreation.doCreate("MotorcadeDriver");
	}
	
	public static void freightBoxDo(){
		Map<String, String> freightBoxMap = new HashMap<String, String>();
		freightBoxMap.put("id", "ID");
		freightBoxMap.put("boxNumber", "BOX_NUMBER");
		freightBoxMap.put("boxType", "BOX_TYPE");
		freightBoxMap.put("boxBelong", "BOX_BELONG");
		freightBoxMap.put("boxCondition", "BOX_CONDITION");
		freightBoxMap.put("emptyStatus", "EMPTY_STATUS");
		freightBoxMap.put("inoutStatus", "INOUT_STATUS");
		freightBoxMap.put("putStatus", "PUT_STATUS");
		freightBoxMap.put("eventStatus", "EVENT_STATUS");
		freightBoxMap.put("boxSource", "BOX_SOURCE");
		
		freightBoxMap.put("descn", "DESCN");
		freightBoxMap.put("status", "STATUS");
		freightBoxMap.put("createTime", "CREATE_TIME");
		freightBoxMap.put("modifyTime", "MODIFY_TIME");
		freightBoxMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightBox> deal = new MapperCreation<FreightBox>();
		String content = deal.createXml(FreightBox.class, freightBoxMap, "FRE_BOX");
		FileCreation.doCreate("fre-box-mapper.xml", content);
		
		DaoCreation.doCreate("FreightBox");
	}

	
	public static void FreightDeductDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("deductType", "DEDUCT_TYPE");
		map.put("freightOrder", "FRE_ORDER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("balance", "BALANCE");
		map.put("deductCountSalesman", "DEDUCT_COUNT_SALESMAN");
		map.put("settleDoneSalesman", "SETTLE_DONE_SALESMAN");
		map.put("settleTimeSalesman", "SETTLE_TIME_SALESMAN");
		map.put("deductCountService", "DEDUCT_COUNT_SERVISE");
		map.put("settleDoneService", "SETTLE_DONE_SERVISE");
		map.put("settleTimeService", "SETTLE_TIME_SERVISE");
		map.put("deductCountManipulator", "DEDUCT_COUNT_MANIPULATOR");
		map.put("settleDoneManipulator", "SETTLE_DONE_MANIPULATOR");
		map.put("settleTimeManipulator", "SETTLE_TIME_MANIPULATOR");
		map.put("reconcileTime", "RECONCILE_TIME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDeduct> deal = new MapperCreation<FreightDeduct>();
		String content = deal.createXml(FreightDeduct.class, map, "FRE_DEDUCT");
		FileCreation.doCreate("fre-deduct-mapper.xml", content);
		
		DaoCreation.doCreate("FreightDeduct");
	}
	
	public static void ReportCategoryDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("categoryName", "CATEGORY_NAME");
		map.put("priority", "PRIORITY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<ReportCategory> deal = new MapperCreation<ReportCategory>();
		String content = deal.createXml(ReportCategory.class, map, "REP_CATEGORY");
		FileCreation.doCreate("rep-category-mapper.xml", content);
		
		DaoCreation.doCreate("ReportCategory");
	}
	
	public static void RoleReportDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("roleId", "ROLE_ID");
		map.put("reportTemplateId", "REP_TEMPLATE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<RoleReport> deal = new MapperCreation<RoleReport>();
		String content = deal.createXml(RoleReport.class, map, "SYS_AUTH_ROLE_REP");
		FileCreation.doCreate("role-report-mapper.xml", content);
		DaoCreation.doCreate("RoleReport");
	}
	
	public static void OutMsgInfoDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("msgType", "MSG_TYPE");
		map.put("title", "TITILE");
		map.put("content", "CONTENT");
		map.put("sender", "SEND_USER_ID");
		map.put("receiver", "RECEIVE_USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<OutMsgInfo> deal = new MapperCreation<OutMsgInfo>();
		String content = deal.createXml(OutMsgInfo.class, map, "OUT_MSG_INFO");
		FileCreation.doCreate("out-msg-info-mapper.xml", content);
		DaoCreation.doCreate("OutMsgInfo");
	}
	
	public static void BpmConfigDelegateDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("delegateType", "DELEGATE_TYPE");
		map.put("bpmConfigBasis", "BPM_CONF_BASIS_ID");
		map.put("taskAssignee", "TASK_ASSIGNEE_ID");
		map.put("taskAgent", "TASK_AGENT_ID");
		map.put("startTime", "START_TIME");
		map.put("endTime", "END_TIME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmConfigDelegate> deal = new MapperCreation<BpmConfigDelegate>();
		String content = deal.createXml(BpmConfigDelegate.class, map, "BPM_CONF_DELEGATE");
		FileCreation.doCreate("bpm-config-delegate-mapper.xml", content);
		DaoCreation.doCreate("BpmConfigDelegate");
	}
	
	public static void ConstantAuthDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("authType", "AUTH_TYPE");
		map.put("constantName", "CONSTANT_NAME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<ConstantAuth> deal = new MapperCreation<ConstantAuth>();
		String content = deal.createXml(ConstantAuth.class, map, "SYS_AUTH_CONSTANT");
		FileCreation.doCreate("constant-auth-mapper.xml", content);
		DaoCreation.doCreate("ConstantAuth");
	}
	
	public static void BpmConfigAuthDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("authType", "AUTH_TYPE");
		map.put("bpmNodeId", "BPM_NODE_ID");
		map.put("authId", "AUTH_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmConfigAuth> deal = new MapperCreation<BpmConfigAuth>();
		String content = deal.createXml(BpmConfigAuth.class, map, "BPM_CONF_AUTH");
		FileCreation.doCreate("bpm-config-auth-mapper.xml", content);
		DaoCreation.doCreate("BpmConfigAuth");
	}
	
	public static void FasReconcileDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("moneyCount", "MONEY_COUNT");
		map.put("sourceId", "SOURCE_ID");
		map.put("targetId", "TARGET_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FasReconcile> deal = new MapperCreation<FasReconcile>();
		String content = deal.createXml(FasReconcile.class, map, "FAS_RECONCILE");
		FileCreation.doCreate("fas-reconcile-mapper.xml", content);
		
		DaoCreation.doCreate("FasReconcile");
	}
	
	public static void BasisMenuDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("menuName", "MENU_NAME");
		map.put("menuKey", "MENU_KEY");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		map.put("basisApplication", "BASIS_APPLICATION_ID");
		map.put("enable", "ENABLE");
		map.put("parentResource", "PARENT_ID");
		map.put("configPrimeUrl", "CONF_PRIME_URL");
		map.put("configManageUrl", "CONF_MANAGE_URL");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisMenu> deal = new MapperCreation<BasisMenu>();
		String content = deal.createXml(BasisMenu.class, map, "BASIS_MENU");
		FileCreation.doCreate("basis-menu-mapper.xml", content);
		DaoCreation.doCreate("BasisMenu");
	}
	
	public static void BpmConfigNodeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("pdId", "PD_ID");
		map.put("pdKey", "PD_KEY");
		map.put("pdName", "PD_NAME");
		map.put("tdKey", "TD_KEY");
		map.put("tdName", "TD_NAME");
		map.put("tdAssignee", "TD_ASSIGNEE_ID");
		map.put("tdCandidateGroup", "TD_CANDIDATE_GROUP_ID");
		map.put("tdListners", "TD_LISTNERS");
		map.put("dueDate", "DUE_DATE");
		map.put("sourceStatus", "SOURCE_STATUS");
		map.put("targetStatus", "TARGET_STATUS");
		map.put("onCreate", "ON_CREATE");
		map.put("onComplete", "ON_COMPLETE");
		map.put("basisStatus", "BASIS_STATUS_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmConfigNode> deal = new MapperCreation<BpmConfigNode>();
		String content = deal.createXml(BpmConfigNode.class, map, "BPM_CONF_NODE");
		FileCreation.doCreate("bpm-config-node-mapper.xml", content);
		DaoCreation.doCreate("BpmConfigNode");
	}
	
	
	public static void BasisStatusAttributeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("basisStatusId", "BASIS_STATUS_ID");
		map.put("basisAttributeId", "BASIS_ATTR_ID");
		map.put("readonly", "READONLY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisStatusAttribute> deal = new MapperCreation<BasisStatusAttribute>();
		String content = deal.createXml(BasisStatusAttribute.class, map, "BASIS_STATUS_ATTR");
		FileCreation.doCreate("basis-status-attribute-mapper.xml", content);
		DaoCreation.doCreate("BasisStatusAttribute");
	}
	
	public static void BasisSchemaDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("schemaName", "SCHEMA_NAME");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisSchema> deal = new MapperCreation<BasisSchema>();
		String content = deal.createXml(BasisSchema.class, map, "BASIS_SCHEMA");
		FileCreation.doCreate("basis-schema-mapper.xml", content);
		DaoCreation.doCreate("BasisSchema");
	}
	
	public static void BasisApplicationDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("applicationName", "APPLICATION_NAME");
		map.put("readonly", "READONLY");
		map.put("filterText", "FILTER_TEXT");
		map.put("basisSchema", "BASIS_SCHEMA_ID");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisApplication> deal = new MapperCreation<BasisApplication>();
		String content = deal.createXml(BasisApplication.class, map, "BASIS_APPLICATION");
		FileCreation.doCreate("basis-application-mapper.xml", content);
		DaoCreation.doCreate("BasisApplication");
	}
	
	public static void BpmConfigBasisDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("bpmKey", "BPM_KEY");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		map.put("basisApplication", "BASIS_APPLICATION_ID");
		map.put("bpmConfigCategory", "BPM_CONF_CATEGORY_ID");
		map.put("configPrimeUrl", "CONF_PRIME_URL");
		map.put("configManageUrl", "CONF_MANAGE_URL");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmConfigBasis> deal = new MapperCreation<BpmConfigBasis>();
		String content = deal.createXml(BpmConfigBasis.class, map, "BPM_CONF_BASIS");
		FileCreation.doCreate("bpm-config-basis-mapper.xml", content);
		DaoCreation.doCreate("BpmConfigBasis");
	}
	
	public static void OrgEntityIdentityDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("priority", "PRIORITY");
		map.put("orgEntityId", "ORG_ENTITY_ID");
		map.put("userId", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<OrgEntityIdentity> deal = new MapperCreation<OrgEntityIdentity>();
		String content = deal.createXml(OrgEntityIdentity.class, map, "SYS_AUTH_ORG_ENTITY_IDENTITY");
		FileCreation.doCreate("org-entity-identity-mapper.xml", content);
		
		DaoCreation.doCreate("OrgEntityIdentity");
	}
	
	public static void BasisStatusDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("statusText", "STATUS_TEXT");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisStatus> deal = new MapperCreation<BasisStatus>();
		String content = deal.createXml(BasisStatus.class, map, "BASIS_STATUS");
		FileCreation.doCreate("basis-status-mapper.xml", content);
		DaoCreation.doCreate("BasisStatus");
	}
	
	public static void BasisValueDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("stringValue", "STRING_VALUE");
		map.put("textValue", "TEXT_VALUE");
		map.put("doubleValue", "DOUBLE_VALUE");
		map.put("intValue", "INT_VALUE");
		map.put("dateValue", "DATE_VALUE");
		map.put("basisAttribute", "BASIS_ATTR_ID");
		map.put("basisSubstance", "BASIS_SUBSTANCE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisValue> deal = new MapperCreation<BasisValue>();
		String content = deal.createXml(BasisValue.class, map, "BASIS_VALUE");
		FileCreation.doCreate("basis-value-mapper.xml", content);
		DaoCreation.doCreate("BasisValue");
	}
	
	public static void BasisAttributeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("attributeName", "ATTR_NAME");
		map.put("attributeColumn", "ATTR_COLUMN");
		map.put("attributeType", "ATTR_TYPE");
		map.put("required", "REQUIRED");
		map.put("canSelect", "CAN_SELECT");
		map.put("vAttrId", "VATTR_ID");
		map.put("vClsId", "VCLS_ID");
		map.put("vColumn", "VCOLUMN");
		map.put("vFilterId", "VFILTER_ID");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisAttribute> deal = new MapperCreation<BasisAttribute>();
		String content = deal.createXml(BasisAttribute.class, map, "BASIS_ATTR");
		FileCreation.doCreate("basis-attribute-mapper.xml", content);
		DaoCreation.doCreate("BasisAttribute");
	}
	
	public static void BasisSubstanceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("basisSubstanceType", "BASIS_SUBSTANCE_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisSubstance> deal = new MapperCreation<BasisSubstance>();
		String content = deal.createXml(BasisSubstance.class, map, "BASIS_SUBSTANCE");
		FileCreation.doCreate("basis-substance-mapper.xml", content);
		DaoCreation.doCreate("BasisSubstance");
	}
	
	public static void BasisSubstanceTypeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("typeName", "TYPE_NAME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BasisSubstanceType> deal = new MapperCreation<BasisSubstanceType>();
		String content = deal.createXml(BasisSubstanceType.class, map, "BASIS_SUBSTANCE_TYPE");
		FileCreation.doCreate("basis-substance-type-mapper.xml", content);
		DaoCreation.doCreate("BasisSubstanceType");
	}
	
	public static void freightDataTemplateDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("templateName", "TEMPLATE_NAME");
		map.put("freightAction", "FRE_ACTION_ID");
		map.put("owner", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDataTemplate> deal = new MapperCreation<FreightDataTemplate>();
		String content = deal.createXml(FreightDataTemplate.class, map, "FRE_DATA_TEMPLATE");
		FileCreation.doCreate("fre-data-template-mapper.xml", content);
		DaoCreation.doCreate("FreightDataTemplate");
	}
	
	public static void FreightDataTemplateActionValueDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightDataTemplateId", "FRE_DATA_TEMPLATE_ID");
		map.put("freightActionValueId", "FRE_ACTION_VALUE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDataTemplateActionValue> deal = new MapperCreation<FreightDataTemplateActionValue>();
		String content = deal.createXml(FreightDataTemplateActionValue.class, map, "FRE_DATA_TEMPLATE_ACTION_VALUE");
		FileCreation.doCreate("fre-data-template-action-value-mapper.xml", content);
		DaoCreation.doCreate("FreightDataTemplateActionValue");
	}
	
	public static void freightActionValueDo(){
		Map<String, String> freightActionValueMap = new HashMap<String, String>();
		freightActionValueMap.put("id", "ID");
		freightActionValueMap.put("stringValue", "STRING_VALUE");
		freightActionValueMap.put("textValue", "TEXT_VALUE");
		freightActionValueMap.put("doubleValue", "DOUBLE_VALUE");
		freightActionValueMap.put("intValue", "INT_VALUE");
		freightActionValueMap.put("dateValue", "DATE_VALUE");
		
		freightActionValueMap.put("freightActionField", "FRE_ACTION_FIELD_ID");
		freightActionValueMap.put("freightAction", "FRE_ACTION_ID");
		freightActionValueMap.put("freightOrderBox", "FRE_ORDER_BOX_ID");
		
		freightActionValueMap.put("descn", "DESCN");
		freightActionValueMap.put("status", "STATUS");
		freightActionValueMap.put("createTime", "CREATE_TIME");
		freightActionValueMap.put("modifyTime", "MODIFY_TIME");
		freightActionValueMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightActionValue> deal = new MapperCreation<FreightActionValue>();
		String content = deal.createXml(FreightActionValue.class, freightActionValueMap, "FRE_ACTION_VALUE");
		FileCreation.doCreate("fre-action-value-mapper.xml", content);
		
		DaoCreation.doCreate("FreightActionValue");
	}
	
	public static void freightActionFieldDo(){
		Map<String, String> freightActionFieldMap = new HashMap<String, String>();
		freightActionFieldMap.put("id", "ID");
		freightActionFieldMap.put("fieldName", "FIELD_NAME");
		freightActionFieldMap.put("fieldColumn", "FIELD_COLUMN");
		freightActionFieldMap.put("fieldType", "FIELD_TYPE");
		freightActionFieldMap.put("required", "REQUIRED");
		freightActionFieldMap.put("participate", "PARTICIPATE");
		freightActionFieldMap.put("canSelect", "CAN_SELECT");
		freightActionFieldMap.put("vAttrId", "VATTR_ID");
		freightActionFieldMap.put("vClsId", "VCLS_ID");
		freightActionFieldMap.put("vColumn", "VCOLUMN");
		freightActionFieldMap.put("vFilterId", "VFILTER_ID");
		freightActionFieldMap.put("forBox", "FOR_BOX");
		
		freightActionFieldMap.put("freightActionType", "FRE_ACTION_TYPE_ID");
		
		freightActionFieldMap.put("descn", "DESCN");
		freightActionFieldMap.put("status", "STATUS");
		freightActionFieldMap.put("createTime", "CREATE_TIME");
		freightActionFieldMap.put("modifyTime", "MODIFY_TIME");
		freightActionFieldMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightActionField> deal = new MapperCreation<FreightActionField>();
		String content = deal.createXml(FreightActionField.class, freightActionFieldMap, "FRE_ACTION_FIELD");
		FileCreation.doCreate("fre-action-field-mapper.xml", content);
		
		DaoCreation.doCreate("FreightActionField");
	}
	
	public static void FreightActionBoxDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightActionId", "FRE_ACTION_ID");
		map.put("freightOrderBoxId", "FRE_ORDER_BOX_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightActionBox> deal = new MapperCreation<FreightActionBox>();
		String content = deal.createXml(FreightActionBox.class, map, "FRE_ACTION_BOX");
		FileCreation.doCreate("fre-action-box-mapper.xml", content);
		
		DaoCreation.doCreate("FreightActionBox");
	}
	
	
	
	public static void FreightExpenseBoxDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightExpenseId", "FRE_EXPENSE_ID");
		map.put("freightOrderBoxId", "FRE_ORDER_BOX_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightExpenseBox> deal = new MapperCreation<FreightExpenseBox>();
		String content = deal.createXml(FreightExpenseBox.class, map, "FRE_EXPENSE_BOX");
		FileCreation.doCreate("fre-expense-box-mapper.xml", content);
		
		DaoCreation.doCreate("FreightExpenseBox");
	}
	
	public static void PositionRoleDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("positionId", "POSITION_ID");
		map.put("roleId", "ROLE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<PositionRole> deal = new MapperCreation<PositionRole>();
		String content = deal.createXml(PositionRole.class, map, "SYS_AUTH_POSITION_ROLE");
		FileCreation.doCreate("position-role-mapper.xml", content);
		
		DaoCreation.doCreate("PositionRole");
	}
	
	
	
	/*public static void FreightBoxParticularDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightOrderBox", "FRE_ORDER_BOX_ID");
		map.put("weekCount", "WEEK_COUNT");
		map.put("actualWeight", "ACTUAL_WEIGHT");
		map.put("beginStation", "BEGIN_STATION");
		map.put("arriveStation", "ARRIVE_STATION");
		map.put("clearanceAddress", "CLEARANCE_ADDRESS");
		map.put("loadingPort", "LOADING_PORT");
		map.put("destinationPort", "DESTINATION_PORT");
		map.put("destinationCountry", "DESTINATION_COUNTRY");
		map.put("groupConcat", "GROUP_CONCAT");
		map.put("enterType", "ENTER_TYPE");
		map.put("enterTime", "ENTER_TIME");
		map.put("turnTime", "TURN_TIME");
		map.put("arriveTime", "ARRIVE_TIME");
		map.put("collectAddress", "COLLECT_ADDRESS");
		map.put("collectTime", "COLLECT_TIME");
		map.put("stuffAddress", "STUFF_ADDRESS");
		map.put("stuffTime", "STUFF_TIME");
		map.put("consignorPart", "CONSIGNOR_PART");
		map.put("shippingPart", "CONSIGEEN_PART");
		map.put("consigneePart", "SHIPPINT_PART");
		map.put("railSeal", "RAIL_SEAL");
		
		map.put("oceanShipTime", "OCEAN_SHIP_TIME");
		map.put("riverShipTime", "RIVER_SHIP_TIME");
		map.put("oceanShipTurn", "OCEAN_SHIP_TURN");
		map.put("riverShipTurn", "RIVER_SHIP_TURN");
		map.put("oceanShipPart", "OCEAN_SHIP_PART");
		map.put("riverShipPart", "RIVER_SHIP_PART");
		
		map.put("bookPart", "BOOK_PART");
		map.put("valueTnsured", "VALUE_INSURED");
		map.put("delegateExpense", "DELEGATE_EXPENSE");
		map.put("delegateTime", "DELEGATE_TIME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		AutoCreateXMLMaper<FreightBoxParticular> deal = new AutoCreateXMLMaper<FreightBoxParticular>();
		String content = deal.createXml(FreightBoxParticular.class, map, "FRE_BOX_PARTICULAR");
		FileCreation.doCreate("fre-box-particular-mapper.xml", content);
		
		DaoCreation.doCreate("FreightBoxParticular");
	}*/
	
	public static void ReportParamDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("paramName", "PARAM_NAME");
		map.put("paramColumn", "PARAM_COLUMN");
		map.put("paramType", "PARAM_TYPE");
		map.put("defaultValue", "DEAULT_VALUE");
		
		map.put("stringValue", "STRING_VALUE");
		map.put("intValue", "INT_VALUE");
		map.put("doubleValue", "DOUBLE_VALUE");
		map.put("dateValue", "DATE_VALUE");
		
		map.put("required", "REQUIRED");
		map.put("canSelect", "CAN_SELECT");
		map.put("vAttrId", "VATTR_ID");
		map.put("vClsId", "VCLS_ID");
		map.put("vColumn", "VCOLUMN");
		map.put("vFilterId", "VFILTER_ID");
		map.put("reportTemplateId", "REP_TEMPLATE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ReportParam> deal = new MapperCreation<ReportParam>();
		String content = deal.createXml(ReportParam.class, map, "REP_PARAM");
		FileCreation.doCreate("rep-param-mapper.xml", content);
		
		DaoCreation.doCreate("ReportParam");
	}
	
	public static void freightOrderBoxDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("weight", "WEIGHT");
		map.put("capacity", "CAPACITY");
		map.put("freightBox", "FRE_BOX_ID");
		map.put("freightSeal", "FRE_SEAL_ID");
		map.put("freightBoxRequire", "FRE_BOX_REQUIRE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightOrderBox> deal = new MapperCreation<FreightOrderBox>();
		String content = deal.createXml(FreightOrderBox.class, map, "FRE_ORDER_BOX");
		FileCreation.doCreate("fre-order-box-mapper.xml", content);
		
		DaoCreation.doCreate("FreightOrderBox");
	}
	
	public static void FreightBoxRequireDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("boxSource", "BOX_SOURCE");
		map.put("boxType", "BOX_TYPE");
		map.put("boxBelong", "BOX_BELONG");
		map.put("boxCondition", "BOX_CONDITION");
		map.put("boxCount", "BOX_COUNT");
		map.put("beginStation", "BEGIN_STATION");
		map.put("arriveStation", "ARRIVE_STATION");
		map.put("freightOrder", "FRE_ORDER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightBoxRequire> deal = new MapperCreation<FreightBoxRequire>();
		String content = deal.createXml(FreightBoxRequire.class, map, "FRE_BOX_REQUIRE");
		FileCreation.doCreate("fre-box-require-mapper.xml", content);
		
		DaoCreation.doCreate("FreightBoxRequire");
	}
	
	public static void ReportSetDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("setType", "SET_TYPE");
		map.put("enable", "ENABLE");
		map.put("firstRow", "FIRST_ROW");
		map.put("lastRow", "LAST_ROW");
		map.put("firstColumn", "FIRST_COLUMN");
		map.put("lastColumn", "LAST_COLUMN");
		map.put("reportTemplateId", "REP_TEMPLATE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ReportSet> deal = new MapperCreation<ReportSet>();
		String content = deal.createXml(ReportSet.class, map, "REP_SET");
		FileCreation.doCreate("rep-set-mapper.xml", content);
		
		DaoCreation.doCreate("ReportSet");
	}
	
	
	
	public static void ReportDataSourceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("dataSourceName", "DATA_SOURCE_NAME");
		map.put("dataSourceKey", "DATA_SOURCE_KEY");
		map.put("sqlText", "SQL_TEXT");
		map.put("reportTemplateId", "REP_TEMPLATE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ReportDataSource> deal = new MapperCreation<ReportDataSource>();
		String content = deal.createXml(ReportDataSource.class, map, "REP_DATA_SOURCE");
		FileCreation.doCreate("rep-data-source-mapper.xml", content);
		
		DaoCreation.doCreate("ReportDataSource");
	}
	
	public static void ReportIsDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("templateId", "TEMPLATE_ID");
		map.put("templateStream", "TEMPLATE_STREAM");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ReportIs> deal = new MapperCreation<ReportIs>();
		String content = deal.createXml(ReportIs.class, map, "REP_IS");
		FileCreation.doCreate("rep-is-mapper.xml", content);
		
		DaoCreation.doCreate("ReportIs");
	}
	
	public static void ReportTemplateDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("templateName", "TEMPLATE_NAME");
		map.put("templateType", "TEMPLATE_TYPE");
		map.put("templateFile", "TEMPLATE_FILE");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<ReportTemplate> deal = new MapperCreation<ReportTemplate>();
		String content = deal.createXml(ReportTemplate.class, map, "REP_TEMPLATE");
		FileCreation.doCreate("rep-template-mapper.xml", content);
		
		DaoCreation.doCreate("ReportTemplate");
	}
	
	/*public static void FasPayInvoiceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("fasPayId", "FAS_PAY_ID");
		map.put("freightInvoiceId", "FRE_INVOICE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		AutoCreateXMLMaper<FasPayInvoice> deal = new AutoCreateXMLMaper<FasPayInvoice>();
		String content = deal.createXml(FasPayInvoice.class, map, "FAS_PAY_INVOICE");
		FileCreation.doCreate("fas-pay-invoice-mapper.xml", content);
		
		DaoCreation.doCreate("FasPayInvoice");
	}*/
	
	public static void FasPayDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("payType", "PAY_TYPE");
		map.put("payCountRmb", "PAY_COUNT_RMB");
		map.put("payCountDollar", "PAY_COUNT_DOLLAR");
		map.put("fasAccountRmb", "FAS_ACCOUNT_ID_RMB");
		map.put("fasAccountDollar", "FAS_ACCOUNT_ID_DOLLAR");
		
		map.put("beneficiary", "FRE_PART_ID");
		map.put("proposer", "USER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		map.put("applyTime", "APPLY_TIME");
		
		map.put("payFor", "PAY_FOR");
		map.put("involveOrderNumber", "INVOLVE_ORDER_NUMBER");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FasPay> deal = new MapperCreation<FasPay>();
		String content = deal.createXml(FasPay.class, map, "FAS_PAY");
		FileCreation.doCreate("fas-pay-mapper.xml", content);
		
		DaoCreation.doCreate("FasPay");
	}
	public static void FasAccountDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("accountNumber", "ACCOUNT_NUMBER");
		map.put("moneyCount", "MONEY_COUNT");
		map.put("bankName", "BANK_NAME");
		map.put("bankAdress", "BANK_ADDRESS");
		map.put("currency", "CURRENCY");
		
		map.put("freightPart", "FRE_PART_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FasAccount> deal = new MapperCreation<FasAccount>();
		String content = deal.createXml(FasAccount.class, map, "FAS_ACCOUNT");
		FileCreation.doCreate("fas-account-mapper.xml", content);
		
		DaoCreation.doCreate("FasAccount");
	}
	
	public static void FasDueDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("dueCount", "DUE_COUNT");
		map.put("actualCount", "ACTUAL_COUNT");
		
		map.put("payPart", "FRE_PART_ID_PAY");
		map.put("payAccount", "FAS_ACCOUNT_ID_PAY");
		map.put("duePart", "FRE_PART_ID_DUE");
		map.put("dueAccount", "FAS_ACCOUNT_ID_DUE");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FasDue> deal = new MapperCreation<FasDue>();
		String content = deal.createXml(FasDue.class, map, "FAS_DUE");
		FileCreation.doCreate("fas-due-mapper.xml", content);
		
		DaoCreation.doCreate("FasDue");
	}
	
	public static void FreightStatementOffsetDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		
		map.put("offsetType", "OFFSET_TYPE");
		map.put("freightStatementIdA", "FRE_STATEMENT_ID_A");
		map.put("freightStatementIdB", "FRE_STATEMENT_ID_B");
		
		map.put("eliminateCountRmbA", "ELIMINATE_COUNT_RMB_A");
		map.put("eliminateCountRmbB", "ELIMINATE_COUNT_RMB_B");
		map.put("eliminateCountDollarA", "ELIMINATE_COUNT_DOLLAR_A");
		map.put("eliminateCountDollarB", "ELIMINATE_COUNT_DOLLAR_B");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightStatementOffset> deal = new MapperCreation<FreightStatementOffset>();
		String content = deal.createXml(FreightStatementOffset.class, map, "FRE_STATEMENT_OFFSET");
		FileCreation.doCreate("fre-statement-offset-mapper.xml", content);
		
		DaoCreation.doCreate("FreightStatementOffset");
	}
	
	public static void FreightInvoiceOffsetDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		
		map.put("offsetType", "OFFSET_TYPE");
		map.put("freightInvoiceIdA", "FRE_INVOICE_ID_A");
		map.put("freightInvoiceIdB", "FRE_INVOICE_ID_B");
		
		map.put("eliminateCountRmbA", "ELIMINATE_COUNT_RMB_A");
		map.put("eliminateCountRmbB", "ELIMINATE_COUNT_RMB_B");
		map.put("eliminateCountDollarA", "ELIMINATE_COUNT_DOLLAR_A");
		map.put("eliminateCountDollarB", "ELIMINATE_COUNT_DOLLAR_B");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightInvoiceOffset> deal = new MapperCreation<FreightInvoiceOffset>();
		String content = deal.createXml(FreightInvoiceOffset.class, map, "FRE_INVOICE_OFFSET");
		FileCreation.doCreate("fre-invoice-offset-mapper.xml", content);
		
		DaoCreation.doCreate("FreightInvoiceOffset");
	}
	
	public static void FreightAuditRecordDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("targetId", "TARGET_ID");
		map.put("sourceStatus", "SOURCE_STATUS");
		map.put("targetStatus", "TARGET_STATUS");
		map.put("executor", "USER_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightAuditRecord> deal = new MapperCreation<FreightAuditRecord>();
		String content = deal.createXml(FreightAuditRecord.class, map, "FRE_AUDIT_RECORD");
		FileCreation.doCreate("fre-audit-record-mapper.xml", content);
		
		DaoCreation.doCreate("FreightAuditRecord");
	}
	
	/*public static void BusinessSchemaDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("schemaName", "SCHEMA_NAME");
		map.put("businessClass", "BUSI_CLS_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		AutoCreateXMLMaper<BusinessSchema> deal = new AutoCreateXMLMaper<BusinessSchema>();
		String content = deal.createXml(BusinessSchema.class, map, "SYS_BUSI_SCHEMA");
		FileCreation.doCreate("business-schema-mapper.xml", content);
		
		DaoCreation.doCreate("BusinessSchema");
	}
	*/
	public static void FreightNetDayDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("regular", "REGULAR");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("currency", "CURRENCY");
		map.put("freightPart", "FRE_PART_ID");
		map.put("period", "PERIOD");
		map.put("delayMonth", "DELAY_MONTH");
		map.put("regularDay", "REGULAR_DAY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightNetDay> deal = new MapperCreation<FreightNetDay>();
		String content = deal.createXml(FreightNetDay.class, map, "FRE_NET_DAY");
		FileCreation.doCreate("fre-net-day-mapper.xml", content);
		
		DaoCreation.doCreate("FreightNetDay");
	}
	
	public static void FreightOrderDo(){
		Map<String, String> freightOrdeMap = new HashMap<String, String>();
		freightOrdeMap.put("id", "ID");
		freightOrdeMap.put("orderNumber", "ORDER_NUMBER");
		freightOrdeMap.put("salesman", "SALESMAN");
		freightOrdeMap.put("manipulator", "MANIPULATOR");
		freightOrdeMap.put("orderScope", "ORDER_SCOPE");
		
		freightOrdeMap.put("firstType", "FIRST_TYPE");
		freightOrdeMap.put("secondType", "SECOND_TYPE");
		freightOrdeMap.put("thirdType", "THIRD_TYPE");
		freightOrdeMap.put("fourthType", "FOURTH_TYPE");
		freightOrdeMap.put("delegatePart", "DELEGATE_PART");
		freightOrdeMap.put("delegateNumber", "DELEGATE_NUMBER");
		freightOrdeMap.put("delegateContact", "DELEGATE_CONTACT");
		freightOrdeMap.put("commission", "COMMISSION");
		
		freightOrdeMap.put("cargoOwner", "CARGO_OWNER");
		freightOrdeMap.put("cargoName", "CARGO_NAME");
		freightOrdeMap.put("cargoAmount", "CARGO_AMOUNT");
		freightOrdeMap.put("cargoWeight", "CARGO_WEIGHT");
		freightOrdeMap.put("cargoCapacity", "CARGO_CAPACITY");
		freightOrdeMap.put("placeTime", "PLACE_TIME");
		freightOrdeMap.put("finishTime", "FINISH_TIME");
		
		freightOrdeMap.put("hardCoefficient", "HARD_COEFFICIENT");
		freightOrdeMap.put("expenseStatus", "EXPENSE_STATUS");
		freightOrdeMap.put("orderStatus", "ORDER_STATUS");
		
		freightOrdeMap.put("creatorUserId", "CREATOR_USER_ID");
		freightOrdeMap.put("orgEntityId", "ORG_ENTITY_ID");
		
		freightOrdeMap.put("descn", "DESCN");
		freightOrdeMap.put("status", "STATUS");
		freightOrdeMap.put("createTime", "CREATE_TIME");
		freightOrdeMap.put("modifyTime", "MODIFY_TIME");
		freightOrdeMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightOrder> deal = new MapperCreation<FreightOrder>();
		String content = deal.createXml(FreightOrder.class, freightOrdeMap, "FRE_ORDER");
		FileCreation.doCreate("fre-order-mapper.xml", content);
		
		DaoCreation.doCreate("FreightOrder");
	}
	
	public static void FasExchangeRateDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("startTime", "START_TIME");
		map.put("endTime", "END_TIME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FasExchangeRate> deal = new MapperCreation<FasExchangeRate>();
		String content = deal.createXml(FasExchangeRate.class, map, "FAS_EXCHANGE_RATE");
		FileCreation.doCreate("fas-exchange-rate-mapper.xml", content);
		
		DaoCreation.doCreate("FasExchangeRate");
	}
	
	public static void FasInvoiceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("invoiceNumber", "INVOICE_NUMBER");
		map.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("moneyCount", "MONEY_COUNT");
		
		map.put("releaseTime", "RELEASE_TIME");
		map.put("recordTime", "RECORD_TIME");
		map.put("claimTime", "CLAIM_TIME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FasInvoice> deal = new MapperCreation<FasInvoice>();
		String content = deal.createXml(FasInvoice.class, map, "FAS_INVOICE");
		FileCreation.doCreate("fas-invoice-mapper.xml", content);
		
		DaoCreation.doCreate("FasInvoice");
	}
	
	public static void FreightInvoiceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("moneyCount", "MONEY_COUNT");
		map.put("eliminateCount", "ELIMINATE_COUNT");
		map.put("remainCount", "REMAIN_COUNT");
		
		map.put("releaseTime", "RELEASE_TIME");
		map.put("claimTime", "CLAIM_TIME");
		map.put("eliminateTime", "ELIMINATE_TIME");
		
		map.put("effectTime", "EFFECT_TIME");
		map.put("period", "PERIOD");
		
		map.put("freightPart", "FRE_PART_ID");
		map.put("freightStatement", "FRE_STATEMENT_ID");
		map.put("fasInvoice", "FAS_INVOICE_ID");
		map.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		map.put("creator", "USER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightInvoice> deal = new MapperCreation<FreightInvoice>();
		String content = deal.createXml(FreightInvoice.class, map, "FRE_INVOICE");
		FileCreation.doCreate("fre-invoice-mapper.xml", content);
		
		DaoCreation.doCreate("FreightInvoice");
	}
	
	public static void FreightPartDo(){
		Map<String, String> freightPartMap = new HashMap<String, String>();
		freightPartMap.put("id", "ID");
		freightPartMap.put("partName", "PART_NAME");
		freightPartMap.put("partType", "PART_TYPE");
		freightPartMap.put("internal", "INTERNAL");
		freightPartMap.put("partAddress", "PART_ADDRESS");
		freightPartMap.put("partCharger", "PART_CHARGER");
		freightPartMap.put("partContact", "PART_CONTACT");
		freightPartMap.put("partRegAddress", "PART_REG_ADDRESS");
		freightPartMap.put("partExpAddress", "PART_EXP_ADDRESS");
		freightPartMap.put("partFax", "PART_FAX");
		freightPartMap.put("partMail", "PART_MAIL");
		freightPartMap.put("engageScope", "ENGAGE_SCOPE");
		freightPartMap.put("saleMan", "SALE_MAN");
		freightPartMap.put("delegateStatus", "DELEGATE_STATUS");
		freightPartMap.put("publicStatus", "PUBLIC_STATUS");
		
		freightPartMap.put("bankNameRmb", "BANK_NAME_RMB");
		freightPartMap.put("bankAccountRmb", "BANK_ACCOUNT_RMB");
		freightPartMap.put("bankNameDollar", "BANK_NAME_DOLLAR");
		freightPartMap.put("bankAccountDollar", "BANK_ACCOUNT_DOLLAR");
		
		freightPartMap.put("orgEntity", "ORG_ENTITY_ID");
		
		freightPartMap.put("descn", "DESCN");
		freightPartMap.put("status", "STATUS");
		freightPartMap.put("createTime", "CREATE_TIME");
		freightPartMap.put("modifyTime", "MODIFY_TIME");
		freightPartMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightPart> deal = new MapperCreation<FreightPart>();
		String content = deal.createXml(FreightPart.class, freightPartMap, "FRE_PART");
		FileCreation.doCreate("fre-part-mapper.xml", content);
		
		DaoCreation.doCreate("FreightPart");
	}
	
	public static void FreightStatementDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("statementNumber", "STATEMENT_NUMBER");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		map.put("freightPartA", "FRE_PART_ID_A");
		map.put("freightPartB", "FRE_PART_ID_B");
		
		map.put("currencyRmb", "CURRENCY_RMB");
		map.put("exchangeRateRmb", "EXCHANGE_RATE_RMB");
		map.put("moneyCountRmb", "MONEY_COUNT_RMB");
		map.put("eliminateCountRmb", "ELIMINATE_COUNT_RMB");
		map.put("remainCountRmb", "REMAIN_COUNT_RMB");
		
		map.put("currencyDollar", "CURRENCY_DOLLAR");
		map.put("exchangeRateDollar", "EXCHANGE_RATE_DOLLAR");
		map.put("moneyCountDollar", "MONEY_COUNT_DOLLAR");
		map.put("eliminateCountDollar", "ELIMINATE_COUNT_DOLLAR");
		map.put("remainCountDollar", "REMAIN_COUNT_DOLLAR");
		
		map.put("creator", "USER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightStatement> deal = new MapperCreation<FreightStatement>();
		String content = deal.createXml(FreightStatement.class, map, "FRE_STATEMENT");
		FileCreation.doCreate("fre-statement-mapper.xml", content);
		
		DaoCreation.doCreate("FreightStatement");
	}
	
	public static void FreightExpenseDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("expenseNumber", "EXPENSE_NUMBER");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("price", "PRICE");
		map.put("actual", "ACTUAL");
		
		map.put("freightExpenseType", "FRE_EXPENSE_TYPE_ID");
		map.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		map.put("freightPartA", "FRE_PART_ID_A");
		map.put("freightPartB", "FRE_PART_ID_B");
		map.put("freightOrder", "FRE_ORDER_ID");
		map.put("freightPrice", "FRE_PRICE_ID");
		map.put("freightAction", "FRE_ACTION_ID");
		map.put("freightBoxRequire", "FRE_BOX_REQUIRE_ID");
		map.put("freightStatement", "FRE_STATEMENT_ID");
		
		map.put("countUnit", "COUNT_UNIT");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("predictCount", "PREDICT_COUNT");
		map.put("actualCount", "ACTUAL_COUNT");
		
		map.put("creator", "USER_ID");
		map.put("orgEntity", "ORG_ENTITY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightExpense> deal = new MapperCreation<FreightExpense>();
		String content = deal.createXml(FreightExpense.class, map, "FRE_EXPENSE");
		FileCreation.doCreate("fre-expense-mapper.xml", content);
		
		DaoCreation.doCreate("FreightExpense");
	}
	
	public static void FreightPriceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("regular", "REGULAR");
		map.put("actual", "ACTUAL");
		map.put("countUnit", "COUNT_UNIT");
		map.put("beginStation", "BEGIN_STATION");
		map.put("arriveStation", "ARRIVE_STATION");
		map.put("moneyCount", "MONEY_COUNT");
		map.put("actualCount", "ACTUAL_COUNT");
		map.put("currency", "CURRENCY");
		map.put("exchangeRate", "EXCHANGE_RATE");
		map.put("meterType", "METER_TYPE");
		map.put("period", "PERIOD");
		map.put("effectTime", "EFFECT_TIME");
		
		map.put("freightExpenseType", "FRE_EXPENSE_TYPE_ID");
		map.put("fasInvoiceType", "FAS_INVOICE_TYPE_ID");
		map.put("freightPart", "FRE_PART_ID");
		map.put("freightPact", "FRE_PACT_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightPrice> deal = new MapperCreation<FreightPrice>();
		String content = deal.createXml(FreightPrice.class, map, "FRE_PRICE");
		FileCreation.doCreate("fre-price-mapper.xml", content);
		DaoCreation.doCreate("FreightPrice");
	}
	
	public static void FasInvoiceTypeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("typeName", "TYPE_NAME");
		map.put("taxRate", "TAX_RATE");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		MapperCreation<FasInvoiceType> deal = new MapperCreation<FasInvoiceType>();
		String content = deal.createXml(FasInvoiceType.class, map, "FAS_INVOICE_TYPE");
		FileCreation.doCreate("fas-invoice-type-mapper.xml", content);
		
		DaoCreation.doCreate("FasInvoiceType");
	}
	
	
	
	public static void freightSealDo(){
		Map<String, String> freightSeal = new HashMap<String, String>();
		freightSeal.put("id", "ID");
		freightSeal.put("sealNumber", "SEAL_NUMBER");
		freightSeal.put("sealType", "SEAL_TYPE");
		freightSeal.put("sealBelong", "SEAL_BELONG");
		
		freightSeal.put("descn", "DESCN");
		freightSeal.put("status", "STATUS");
		freightSeal.put("createTime", "CREATE_TIME");
		freightSeal.put("modifyTime", "MODIFY_TIME");
		freightSeal.put("displayIndex", "DISP_INX");
		
		MapperCreation<FreightSeal> deal = new MapperCreation<FreightSeal>();
		String content = deal.createXml(FreightSeal.class, freightSeal, "FRE_SEAL");
		FileCreation.doCreate("fre-seal-mapper.xml", content);
		
		DaoCreation.doCreate("FreightSeal");
	}
	
	
	public static void FreightExpenseTypeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("typeName", "TYPE_NAME");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightExpenseType> deal = new MapperCreation<FreightExpenseType>();
		String content = deal.createXml(FreightExpenseType.class, map, "FRE_EXPENSE_TYPE");
		FileCreation.doCreate("fre-expense-type-mapper.xml", content);
		
		DaoCreation.doCreate("FreightExpenseType");
	}
	
	public static void PositionDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("positionName", "POSITION_NAME");
		map.put("priority", "PRIORITY");
		map.put("grade", "GRADE");
		map.put("orgEntity", "ORG_ENTITY_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<Position> deal = new MapperCreation<Position>();
		String content = deal.createXml(Position.class, map, "SYS_AUTH_POSITION");
		FileCreation.doCreate("position-mapper.xml", content);
		
		DaoCreation.doCreate("Position");
	}
	
	public static void ResourceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("resourceName", "RESOURCE_NAME");
		map.put("resourceKey", "RESOURCE_KEY");
		map.put("resourceUrl", "RESOURCE_URL");
		map.put("resourceType", "RESOURCE_TYPE");
		map.put("icon", "ICON");
		map.put("enable", "ENABLE");
		map.put("parentResource", "PARENT_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<Resource> deal = new MapperCreation<Resource>();
		String content = deal.createXml(Resource.class, map, "SYS_AUTH_RESOURCE");
		FileCreation.doCreate("resource-mapper.xml", content);
		
		DaoCreation.doCreate("Resource");
	}
	
	public static void RoleResourceDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("roleId", "ROLE_ID");
		map.put("resourceId", "RESOURCE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<RoleResource> deal = new MapperCreation<RoleResource>();
		String content = deal.createXml(RoleResource.class, map, "SYS_AUTH_ROLE_RESOURCE");
		FileCreation.doCreate("role-resource-mapper.xml", content);
		
		DaoCreation.doCreate("RoleResource");
	}
	
	public static void UserRoleDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("userId", "USER_ID");
		map.put("roleId", "ROLE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<UserRole> deal = new MapperCreation<UserRole>();
		String content = deal.createXml(UserRole.class, map, "SYS_AUTH_USER_ROLE");
		FileCreation.doCreate("user-role-mapper.xml", content);
		
		DaoCreation.doCreate("UserRole");
	}
	
	public static void RoleDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("roleName", "ROLE_NAME");
		map.put("roleKey", "ROLE_KEY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<Role> deal = new MapperCreation<Role>();
		String content = deal.createXml(Role.class, map, "SYS_AUTH_ROLE");
		FileCreation.doCreate("role-mapper.xml", content);
		
		DaoCreation.doCreate("Role");
	}
	
	public static void OrgEntityDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("orgEntityName", "ORG_ENTITY_NAME");
		map.put("orgType", "TYPE_ID");
		map.put("parentOrg", "PARENT_ORG_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<OrgEntity> deal = new MapperCreation<OrgEntity>();
		String content = deal.createXml(OrgEntity.class, map, "SYS_AUTH_ORG_ENTITY");
		FileCreation.doCreate("org-entity-mapper.xml", content);
		
		DaoCreation.doCreate("OrgEntity");
	}
	
	public static void OrgTypeDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("typeName", "TYPE_NAME");
		map.put("priority", "PRIORITY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<OrgType> deal = new MapperCreation<OrgType>();
		String content = deal.createXml(OrgType.class, map, "SYS_AUTH_ORG_TYPE");
		FileCreation.doCreate("org-type-mapper.xml", content);
		
		DaoCreation.doCreate("OrgType");
	}
	
	
	
	public static void BpmConfigCategoryDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("categoryName", "CATEGORY_NAME");
		map.put("priority", "PRIORITY");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmConfigCategory> deal = new MapperCreation<BpmConfigCategory>();
		String content = deal.createXml(BpmConfigCategory.class, map, "BPM_CONF_CATEGORY");
		FileCreation.doCreate("bpm-config-category-mapper.xml", content);
		
		DaoCreation.doCreate("BpmConfigCategory");
	}
	
	public static void BpmMailTemplateDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("templateName", "TEMPLATE_NAME");
		map.put("subject", "SUBJECT");
		map.put("content", "CONTENT");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<BpmMailTemplate> deal = new MapperCreation<BpmMailTemplate>();
		String content = deal.createXml(BpmMailTemplate.class, map, "BPM_MAIL_TEMPLATE");
		FileCreation.doCreate("bpm-mail-template-mapper.xml", content);
		
		DaoCreation.doCreate("BpmMailTemplate");
	}
	
	public static void valueFilterDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("filterText", "FILTER_TEXT");
		map.put("valueClass", "VCLS_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<ValueFilter> deal = new MapperCreation<ValueFilter>();
		String content = deal.createXml(ValueFilter.class, map, "SYS_DIC_VALUE_FILTER");
		FileCreation.doCreate("value-filter-mapper.xml", content);
		
		DaoCreation.doCreate("ValueFilter");
	}
	
	public static void freightActionTypeIdentityDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightActionTypeId", "ACTION_TYPE_ID");
		map.put("assigneeUserId", "ASSIGNEE_USER_ID");
		map.put("candidateGroupId", "CANDIDATE_GROUP_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightActionTypeIdentity> deal = new MapperCreation<FreightActionTypeIdentity>();
		String content = deal.createXml(FreightActionTypeIdentity.class, map, "FRE_ACTION_TYPE_IDENTITY");
		FileCreation.doCreate("fre-action-type-identity-mapper.xml", content);
		DaoCreation.doCreate("FreightActionTypeIdentity");
	}
	
	public static void freightDataTemplateActionFieldDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightDataTemplateId", "DATA_TEMPLATE_ID");
		map.put("freightActionFieldId", "ACTION_FIELD_ID");
		map.put("freightActionId", "ACTION_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDataTemplateActionValue> deal = new MapperCreation<FreightDataTemplateActionValue>();
		String content = deal.createXml(FreightDataTemplateActionValue.class, map, "FRE_DATA_TEMPLATE_ACTION_FIELD");
		FileCreation.doCreate("fre-data-template-action-field-mapper.xml", content);
		DaoCreation.doCreate("FreightDataTemplateActionField");
	}
	
	
	
	public static void freightMaintainActionDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("freightMaintainTypeId", "MAINTAIN_TYPE_ID");
		map.put("freightActionTypeId", "ACTION_TYPE_ID");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightMaintainAction> deal = new MapperCreation<FreightMaintainAction>();
		String content = deal.createXml(FreightMaintainAction.class, map, "FRE_MAINTAIN_ACTION");
		FileCreation.doCreate("fre-maintain-action-mapper.xml", content);
		DaoCreation.doCreate("FreightMaintainAction");
	}
	
	public static void FreightPactDo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "ID");
		map.put("pactNumber", "PACT_NUMBER");
		map.put("pactTitle", "PACT_TITLE");
		map.put("pactType", "PACT_TYPE");
		map.put("incomeOrExpense", "INCOME_OR_EXPENSE");
		map.put("partA", "PART_A");
		map.put("partB", "PART_B");
		map.put("partC", "PART_C");
		map.put("transactor", "TRANSACTOR");
		map.put("signDate", "SIGN_DATE");
		map.put("effectDate", "EFFECT_DATE");
		map.put("cutOffDate", "CUT_OFF_DATE");
		map.put("payType", "PAY_TYPE");
		map.put("defaultRate", "DEFAULT_RATE");
		map.put("defaultSettleDays", "DEFAULT_SETTLE_DAYS");
		map.put("currency", "CURRENCY");
		map.put("moneyCount", "MONEY_COUNT");
		map.put("pactContent", "PACT_CONTENT");
		
		map.put("descn", "DESCN");
		map.put("status", "STATUS");
		map.put("createTime", "CREATE_TIME");
		map.put("modifyTime", "MODIFY_TIME");
		map.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightPact> deal = new MapperCreation<FreightPact>();
		String content = deal.createXml(FreightPact.class, map, "FRE_PACT");
		FileCreation.doCreate("fre-pact-mapper.xml", content);
		
		DaoCreation.doCreate("FreightPact");
	}
	
	
	
	
	
	public static void freightDelegateTemplateDo(){
		Map<String, String> freightDelegateMap = new HashMap<String, String>();
		freightDelegateMap.put("id", "ID");
		freightDelegateMap.put("templateName", "TEMPLATE_NAME");
		freightDelegateMap.put("templateFile", "TEMPLATE_FILE");
		freightDelegateMap.put("regionParam", "REGION_PARAM");
		
		freightDelegateMap.put("descn", "DESCN");
		freightDelegateMap.put("status", "STATUS");
		freightDelegateMap.put("createTime", "CREATE_TIME");
		freightDelegateMap.put("modifyTime", "MODIFY_TIME");
		freightDelegateMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDelegateTemplate> deal = new MapperCreation<FreightDelegateTemplate>();
		String content = deal.createXml(FreightDelegateTemplate.class, freightDelegateMap, "FRE_DELEGATE_TEMPLATE");
		FileCreation.doCreate("fre-delegate-template-mapper.xml", content);
		
		DaoCreation.doCreate("FreightDelegateTemplate");
	}
	
	public static void freightDelegateDo(){
		Map<String, String> freightDelegateMap = new HashMap<String, String>();
		freightDelegateMap.put("id", "ID");
		freightDelegateMap.put("delegateNumber", "DELEGATE_NUMBER");
		freightDelegateMap.put("executeStatus", "EXECUTE_STATUS");
		freightDelegateMap.put("lockStatus", "LOCK_STATUS");
		freightDelegateMap.put("placeTime", "PLACE_TIME");
		freightDelegateMap.put("delegateFile", "DELEGATE_FILE");
		freightDelegateMap.put("freightAction", "ACTION_ID");
		freightDelegateMap.put("freightPart", "PART_ID");
		freightDelegateMap.put("orgEntity", "ORG_ID");
		freightDelegateMap.put("owner", "USER_ID");
		
		
		
		freightDelegateMap.put("descn", "DESCN");
		freightDelegateMap.put("status", "STATUS");
		freightDelegateMap.put("createTime", "CREATE_TIME");
		freightDelegateMap.put("modifyTime", "MODIFY_TIME");
		freightDelegateMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightDelegate> deal = new MapperCreation<FreightDelegate>();
		String content = deal.createXml(FreightDelegate.class, freightDelegateMap, "FRE_DELEGATE");
		FileCreation.doCreate("fre-delegate-mapper.xml", content);
		
		DaoCreation.doCreate("FreightDelegate");
	}
	
	public static void freightMaintainTypeDo(){
		Map<String, String> freightMaintainTypeMap = new HashMap<String, String>();
		freightMaintainTypeMap.put("id", "ID");
		freightMaintainTypeMap.put("typeName", "TYPE_NAME");
		freightMaintainTypeMap.put("priority", "PRIORITY");
		freightMaintainTypeMap.put("descn", "DESCN");
		freightMaintainTypeMap.put("status", "STATUS");
		freightMaintainTypeMap.put("createTime", "CREATE_TIME");
		freightMaintainTypeMap.put("modifyTime", "MODIFY_TIME");
		freightMaintainTypeMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightMaintainType> deal = new MapperCreation<FreightMaintainType>();
		String content = deal.createXml(FreightMaintainType.class, freightMaintainTypeMap, "FRE_MAINTAIN_TYPE");
		FileCreation.doCreate("fre-maintain-type-mapper.xml", content);
		
		DaoCreation.doCreate("FreightMaintainType");
	}
	
	public static void freightOrderDo(){
		Map<String, String> freightOrdeMap = new HashMap<String, String>();
		freightOrdeMap.put("id", "ID");
		freightOrdeMap.put("orderNumber", "ORDER_NUMBER");
		freightOrdeMap.put("orderType", "ORDER_TYPE");
		freightOrdeMap.put("freightType", "FREIGHT_TYPE");
		freightOrdeMap.put("sourceType", "SOURCE_TYPE");
		freightOrdeMap.put("importoutPort", "IMPORT_OUTPORT");
		freightOrdeMap.put("salesman", "SALESMAN");
		freightOrdeMap.put("manipulator", "MANIPULATOR");
		freightOrdeMap.put("auditStatus", "AUDIT_STATUS");
		freightOrdeMap.put("auditor", "AUDITOR");
		freightOrdeMap.put("cargoOwner", "CARGO_OWNER");
		freightOrdeMap.put("delegatePart", "DELEGATE_PART");
		freightOrdeMap.put("delegateNumber", "DELEGATE_NUMBER");
		freightOrdeMap.put("cargoNameCN", "CARGO_NAME_CN");
		freightOrdeMap.put("cargoNameEN", "CARGO_NAME_EN");
		freightOrdeMap.put("cargoUnit", "CARGO_UNIT");
		freightOrdeMap.put("cargoWeight", "CARGO_WEIGHT");
		freightOrdeMap.put("cargoCapacity", "CARGO_CAPACITY");
		freightOrdeMap.put("hardCoefficient", "HARD_COEFFICIENT");
		freightOrdeMap.put("boxUse", "BOX_USE");
		freightOrdeMap.put("lockStatus", "LOCK_STATUS");
		freightOrdeMap.put("maintainBySelf", "MAINTAIN_BY_SELF");
		freightOrdeMap.put("beginStation", "BEGIN_STATION");
		freightOrdeMap.put("arriveStation", "ARRIVE_STATION");
		freightOrdeMap.put("placeTime", "PLACE_TIME");
		freightOrdeMap.put("finishTime", "FINISH_TIME");
		
		freightOrdeMap.put("descn", "DESCN");
		freightOrdeMap.put("status", "STATUS");
		freightOrdeMap.put("createTime", "CREATE_TIME");
		freightOrdeMap.put("modifyTime", "MODIFY_TIME");
		freightOrdeMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightOrder> deal = new MapperCreation<FreightOrder>();
		String content = deal.createXml(FreightOrder.class, freightOrdeMap, "FRE_ORDER");
		FileCreation.doCreate("fre-order-mapper.xml", content);
		
		DaoCreation.doCreate("FreightOrder");
	}
	
	
	
	
	
	public static void freightActionTypeDo(){
		Map<String, String> freightActionTypeMap = new HashMap<String, String>();
		freightActionTypeMap.put("id", "ID");
		freightActionTypeMap.put("typeName", "TYPE_NAME");
		freightActionTypeMap.put("delegate", "DELEGATE");
		freightActionTypeMap.put("internal", "INTERNAL");
		freightActionTypeMap.put("prime", "PRIME");
		freightActionTypeMap.put("scope", "SCOPE");
		freightActionTypeMap.put("freightDelegateTemplate", "DELEGATE_TEMPLATE_ID");
		
		freightActionTypeMap.put("descn", "DESCN");
		freightActionTypeMap.put("status", "STATUS");
		freightActionTypeMap.put("createTime", "CREATE_TIME");
		freightActionTypeMap.put("modifyTime", "MODIFY_TIME");
		freightActionTypeMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightActionType> deal = new MapperCreation<FreightActionType>();
		String content = deal.createXml(FreightActionType.class, freightActionTypeMap, "FRE_ACTION_TYPE");
		FileCreation.doCreate("fre-action-type-mapper.xml", content);
		
		DaoCreation.doCreate("FreightActionType");
	}
	
	public static void freightActionDo(){
		Map<String, String> freightActionMap = new HashMap<String, String>();
		freightActionMap.put("id", "ID");
		freightActionMap.put("delegate", "DELEGATE");
		freightActionMap.put("internal", "INTERNAL");
		freightActionMap.put("prime", "PRIME");
		freightActionMap.put("freightActionType", "ACTION_TYPE_ID");
		freightActionMap.put("freightMaintain", "MAINTAIN_ID");
		freightActionMap.put("descn", "DESCN");
		freightActionMap.put("status", "STATUS");
		freightActionMap.put("createTime", "CREATE_TIME");
		freightActionMap.put("modifyTime", "MODIFY_TIME");
		freightActionMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightAction> deal = new MapperCreation<FreightAction>();
		String content = deal.createXml(FreightAction.class, freightActionMap, "FRE_ACTION");
		FileCreation.doCreate("fre-action-mapper.xml", content);
		
		DaoCreation.doCreate("FreightAction");
	}
	
	public static void valueDictionaryDo(){
		Map<String, String> valueDictionaryMap = new HashMap<String, String>();
		valueDictionaryMap.put("id", "ID");
		valueDictionaryMap.put("valueContent", "VALUE_CONTENT");
		valueDictionaryMap.put("valueAttribute", "VATTR_ID");
		valueDictionaryMap.put("descn", "DESCN");
		valueDictionaryMap.put("status", "STATUS");
		valueDictionaryMap.put("createTime", "CREATE_TIME");
		valueDictionaryMap.put("modifyTime", "MODIFY_TIME");
		valueDictionaryMap.put("displayIndex", "DISP_INX");
		
		MapperCreation<ValueDictionary> deal = new MapperCreation<ValueDictionary>();
		String content = deal.createXml(ValueDictionary.class, valueDictionaryMap, "SYS_DIC_VALUE_DICTIONARY");
		FileCreation.doCreate("value-dictionary-mapper.xml", content);
		
		DaoCreation.doCreate("ValueDictionary");
	}
	
	public static void valueAttributeDo(){
		Map<String, String> valueAttributeMap = new HashMap<String, String>();
		valueAttributeMap.put("id", "ID");
		valueAttributeMap.put("attributeName", "ATTR_NAME");
		valueAttributeMap.put("columnName", "COLUMN_NAME");
		valueAttributeMap.put("valueClass", "VCLS_ID");
		valueAttributeMap.put("descn", "DESCN");
		valueAttributeMap.put("status", "STATUS");
		valueAttributeMap.put("createTime", "CREATE_TIME");
		valueAttributeMap.put("modifyTime", "MODIFY_TIME");
		valueAttributeMap.put("displayIndex", "DISP_INX");
		
		MapperCreation<ValueAttribute> deal = new MapperCreation<ValueAttribute>();
		String content = deal.createXml(ValueAttribute.class, valueAttributeMap, "SYS_DIC_VALUE_ATTR");
		FileCreation.doCreate("value-attribute-mapper.xml", content);
		
		DaoCreation.doCreate("ValueAttribute");
	}
	
	public static void valueClassDo(){
		Map<String, String> valueClassMap = new HashMap<String, String>();
		valueClassMap.put("id", "ID");
		valueClassMap.put("className", "CLS_NAME");
		valueClassMap.put("tableName", "TABLE_NAME");
		valueClassMap.put("descn", "DESCN");
		valueClassMap.put("status", "STATUS");
		valueClassMap.put("createTime", "CREATE_TIME");
		valueClassMap.put("modifyTime", "MODIFY_TIME");
		valueClassMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<ValueClass> deal = new MapperCreation<ValueClass>();
		String content = deal.createXml(ValueClass.class, valueClassMap, "SYS_DIC_VALUE_CLASS");
		FileCreation.doCreate("value-class-mapper.xml", content);
		
		DaoCreation.doCreate("ValueClass");
	}
	
	public static void freightMaintainDo(){
		Map<String, String> freightMaintainMap = new HashMap<String, String>();
		freightMaintainMap.put("id", "ID");
		freightMaintainMap.put("maintainName", "MAINTAIN_NAME");
		freightMaintainMap.put("freightOrder", "ORDER_ID");
		freightMaintainMap.put("parentMaintain", "PARENT_MAINTAIN_ID");
		freightMaintainMap.put("descn", "DESCN");
		freightMaintainMap.put("status", "STATUS");
		freightMaintainMap.put("createTime", "CREATE_TIME");
		freightMaintainMap.put("modifyTime", "MODIFY_TIME");
		freightMaintainMap.put("displayIndex", "DISP_INX");
		
		
		MapperCreation<FreightMaintain> deal = new MapperCreation<FreightMaintain>();
		String content = deal.createXml(FreightMaintain.class, freightMaintainMap, "FRE_MAINTAIN");
		FileCreation.doCreate("fre-maintain-mapper.xml", content);
		
		DaoCreation.doCreate("FreightMaintain");
	}
	
	
	
		
	/*public static void crmPartnerContactDo(){
		Map<String, String> crmPartnerContactMap = new HashMap<String, String>();
		crmPartnerContactMap.put("id", "ID");
		crmPartnerContactMap.put("subject", "SUBJECT");
		crmPartnerContactMap.put("customerName", "CUSTOMER_NAME");
		crmPartnerContactMap.put("dutyUser", "DUTY_USER");
		crmPartnerContactMap.put("contact", "CONTACT");
		crmPartnerContactMap.put("status", "STATUS");
		crmPartnerContactMap.put("priority", "PRIORITY");
		crmPartnerContactMap.put("description", "DESCN");
		crmPartnerContactMap.put("contactTime", "CONTACT_TIME");
		crmPartnerContactMap.put("nextTime", "NEXT_TIME");
		
		AutoCreateXMLMaper<CrmPartnerContact> deal = new AutoCreateXMLMaper<CrmPartnerContact>();
		String content = deal.createXml(CrmPartnerContact.class, crmPartnerContactMap, "OUT_CRM_PARTNER_CONTACT");
		FileCreation.doCreate("crm-partner-contact-mapper.xml", content);
		
		DaoCreation.doCreate("CrmPartnerContact");
	}*/
	
	public static void docInfoDo(){
		Map<String, String> docInfoMap = new HashMap<String, String>();
		docInfoMap.put("id", "ID");
		docInfoMap.put("docName", "DOC_NAME");
		docInfoMap.put("docData", "DOC_DATA");
		docInfoMap.put("createTime", "CREATE_TIME");
		docInfoMap.put("modifyTime", "MODIFY_TIME");
		docInfoMap.put("eternal", "ETERNAL");
		docInfoMap.put("participate", "PARTICIPATE");
		docInfoMap.put("docType", "TYPE_ID");
		docInfoMap.put("owner", "USER_ID");
		
		MapperCreation<DocInfo> deal = new MapperCreation<DocInfo>();
		String content = deal.createXml(DocInfo.class, docInfoMap, "OUT_DOC_INFO");
		FileCreation.doCreate("doc-info-mapper.xml", content);
		
		DaoCreation.doCreate("DocInfo");
	}
	
	public static void docTypeDo(){
		Map<String, String> docTypeMap = new HashMap<String, String>();
		docTypeMap.put("id", "ID");
		docTypeMap.put("typeName", "TYPE_NAME");
		docTypeMap.put("limitDayCount", "LIMIT_DAY_COUNT");
		docTypeMap.put("description", "DESCN");
		docTypeMap.put("createTime", "CREATE_TIME");
		docTypeMap.put("modifyTime", "MODIFY_TIME");
		docTypeMap.put("displayIndex", "DISP_INX");
		
		MapperCreation<DocType> deal = new MapperCreation<DocType>();
		String content = deal.createXml(DocType.class, docTypeMap, "OUT_DOC_TYPE");
		FileCreation.doCreate("doc-type-mapper.xml", content);
		
		DaoCreation.doCreate("DocType");
	}
	
	public static void crmPartnerDo(){
		Map<String, String> crmPartnerMap = new HashMap<String, String>();
		crmPartnerMap.put("id", "ID");
		crmPartnerMap.put("nameFull", "NAME_FULL");
		crmPartnerMap.put("nameShort", "NAME_SHORT");
		crmPartnerMap.put("type", "TYPE");
		crmPartnerMap.put("priorityCooperation", "PRIORITY_COOMP");
		crmPartnerMap.put("localCountry", "LOCAL_COUNTRY");
		crmPartnerMap.put("localProvince", "LOCAL_PROVINCE");
		crmPartnerMap.put("locatCity", "LOCAL_CITY");
		crmPartnerMap.put("localDetail", "LOCAL_DETAIL");
		crmPartnerMap.put("uphold", "UPHOLD");
		crmPartnerMap.put("contact", "CONTACT");
		crmPartnerMap.put("phoneNo", "PHONE_NO");
		crmPartnerMap.put("engageScope", "ENGAGE_SCOPE");
		crmPartnerMap.put("companyUrl", "COMP_URL");
		crmPartnerMap.put("createTime", "CREATE_TIME");
		crmPartnerMap.put("status", "STATUS");
		crmPartnerMap.put("currentCooperation", "CURRENT_COOMP");
		crmPartnerMap.put("contactRecentTime", "CONTACT_RECENT_TIME");
		crmPartnerMap.put("contactNextTime", "CONTACT_NEXT_TIME");
		
		MapperCreation<CrmPartner> deal = new MapperCreation<CrmPartner>();
		String content = deal.createXml(CrmPartner.class, crmPartnerMap, "OUT_CRM_PARTNER_INFO");
		FileCreation.doCreate("crm-partner-mapper.xml", content);
		
		DaoCreation.doCreate("CrmPartner");
	}
	
	public static void crmCustomerDo(){
		Map<String, String> crmCustomerMap = new HashMap<String, String>();
		crmCustomerMap.put("id", "ID");
		crmCustomerMap.put("nameFull", "NAME_FULL");
		crmCustomerMap.put("nameShort", "NAME_SHORT");
		crmCustomerMap.put("type", "TYPE");
		crmCustomerMap.put("priorityCooperation", "PRIORITY_COOMP");
		crmCustomerMap.put("localCountry", "LOCAL_COUNTRY");
		crmCustomerMap.put("localProvince", "LOCAL_PROVINCE");
		crmCustomerMap.put("locatCity", "LOCAL_CITY");
		crmCustomerMap.put("localDetail", "LOCAL_DETAIL");
		crmCustomerMap.put("uphold", "UPHOLD");
		crmCustomerMap.put("contact", "CONTACT");
		crmCustomerMap.put("phoneNo", "PHONE_NO");
		crmCustomerMap.put("engageScope", "ENGAGE_SCOPE");
		crmCustomerMap.put("companyUrl", "COMP_URL");
		crmCustomerMap.put("createTime", "CREATE_TIME");
		crmCustomerMap.put("status", "STATUS");
		crmCustomerMap.put("currentCooperation", "CURRENT_COOMP");
		crmCustomerMap.put("contactRecentTime", "CONTACT_RECENT_TIME");
		crmCustomerMap.put("contactNextTime", "CONTACT_NEXT_TIME");
		
		MapperCreation<CrmCustomer> deal = new MapperCreation<CrmCustomer>();
		String content = deal.createXml(CrmCustomer.class, crmCustomerMap, "OUT_CRM_CUSTOMER_INFO");
		FileCreation.doCreate("crm-customer-mapper.xml", content);
		
		DaoCreation.doCreate("CrmCustomer");
	}
	
	/*public static void businessPrefixDo(){
		Map<String, String> businessPrefixMap = new HashMap<String, String>();
		businessPrefixMap.put("id", "ID");
		businessPrefixMap.put("prefix", "PREFIX");
		businessPrefixMap.put("withDate", "WITH_DATE");
		businessPrefixMap.put("datePattern", "PATTERN_DATE");
		businessPrefixMap.put("length", "LENGTH");
		businessPrefixMap.put("status", "STATUS");
		businessPrefixMap.put("businessClass", "CLS_ID");
		businessPrefixMap.put("displayIndex", "DISP_INX");
		
		AutoCreateXMLMaper<BusinessPrefix> deal = new AutoCreateXMLMaper<BusinessPrefix>();
		String content = deal.createXml(BusinessPrefix.class, businessPrefixMap, "SYS_BUSI_PREFIX");
		FileCreation.doCreate("business-prefix-mapper.xml", content);
		
		DaoCreation.doCreate("BusinessPrefix");
	}*/
	
	/*public static void businessClassDo(){
		Map<String, String> businessClassMap = new HashMap<String, String>();
		businessClassMap.put("id", "ID");
		businessClassMap.put("clsName", "CLS_NAME");
		businessClassMap.put("tableName", "TABLE_NAME");
		businessClassMap.put("description", "DESCN");
		businessClassMap.put("createTime", "CREATE_TIME");
		businessClassMap.put("modifyTime", "MODIFY_TIME");
		
		businessClassMap.put("beforeCreateBehaviors", "BEHAVIOR_CREATE_BEFORE");
		businessClassMap.put("afterCreateBehaviors", "BEHAVIOR_CREATE_AFTER");
		businessClassMap.put("beforeModifyBehaviors", "BEHAVIOR_MODIFY_BEFORE");
		businessClassMap.put("afterModifyBehaviors", "BEHAVIOR_MODIFY_AFTER");
		businessClassMap.put("beforeDeleteBehaviors", "BEHAVIOR_DELETE_BEFORE");
		businessClassMap.put("afterDeleteBehaviors", "BEHAVIOR_DELETE_AFTER");
		businessClassMap.put("displayIndex", "DISP_INX");
		
		AutoCreateXMLMaper<BusinessClass> deal = new AutoCreateXMLMaper<BusinessClass>();
		String content = deal.createXml(BusinessClass.class, businessClassMap, "SYS_BUSI_CLS");
		FileCreation.doCreate("business-class-mapper.xml", content);
		
		DaoCreation.doCreate("BusinessClass");
	}*/
	
	public static void bpmConfigDelegateDo(){
		Map<String, String> bpmConfigDelegateMap = new HashMap<String, String>();
		bpmConfigDelegateMap.put("id", "ID");
		bpmConfigDelegateMap.put("bpmKey", "BPM_KEY");
		bpmConfigDelegateMap.put("userId", "USER_ID");
		bpmConfigDelegateMap.put("delegateUserId", "DELEGATE_USER_ID");
		bpmConfigDelegateMap.put("delegateType", "DELEGATE_TYPE");
		bpmConfigDelegateMap.put("startTime", "START_TIME");
		bpmConfigDelegateMap.put("endTime", "END_TIME");
		bpmConfigDelegateMap.put("status", "STATUS");
		
		MapperCreation<BpmConfigDelegate> deal = new MapperCreation<BpmConfigDelegate>();
		String content = deal.createXml(BpmConfigDelegate.class, bpmConfigDelegateMap, "BPM_CONF_DELEGATE");
		FileCreation.doCreate("bpm-config-delegate-mapper.xml", content);
		
		DaoCreation.doCreate("BpmConfigDelegate");
	}
	
	public static void userBaseDo(){
		Map<String, String> userBaseMap = new HashMap<String, String>();
		userBaseMap.put("id", "ID");
		userBaseMap.put("mailAddress", "MAIL_ADDRESS");
		userBaseMap.put("mailPassword", "MAIL_PASSWORD");
		userBaseMap.put("mailAsync", "MAIL_ASYNC");
		userBaseMap.put("mobile", "MOBILE");
		userBaseMap.put("telephone", "TEL");
		userBaseMap.put("icon", "ICON");
		userBaseMap.put("userId", "USER_ID");
		userBaseMap.put("workStatus", "WORK_STATUS");
		userBaseMap.put("birthDay", "BIRTH_DAY");
		
		MapperCreation<UserBase> deal = new MapperCreation<UserBase>();
		String content = deal.createXml(UserBase.class, userBaseMap, "SYS_AUTH_USER_BASE");
		FileCreation.doCreate("user-base-mapper.xml", content);
		
		DaoCreation.doCreate("UserBase");
	}
	/**
	public static void fasProofAdmitDo(){
		Map<String, String> fasProofAdmitMap = new HashMap<String, String>();
		fasProofAdmitMap.put("id", "ID");
		fasProofAdmitMap.put("subjectType", "SUBJECT_TYPE");
		fasProofAdmitMap.put("proofType", "PROOF_TYPE");
		fasProofAdmitMap.put("sumCount", "SUM_COUNT");
		fasProofAdmitMap.put("unit", "UNIT");
		fasProofAdmitMap.put("createTime", "CREATE_TIME");
		fasProofAdmitMap.put("proofTime", "PROOF_TIME");
		fasProofAdmitMap.put("admitCompany", "ADMIT_COMP");
		fasProofAdmitMap.put("incomeCompany", "INCOME_COMP");
		fasProofAdmitMap.put("handler", "HANDLER");
		fasProofAdmitMap.put("admitUser", "ADMIT_USER");
		fasProofAdmitMap.put("description", "DESCN");
		
		AutoCreateXMLMaper<FasProofAdmit> deal = new AutoCreateXMLMaper<FasProofAdmit>();
		String content = deal.createXml(FasProofAdmit.class, fasProofAdmitMap, "OUT_FAS_PROOF_ADMIT");
		FileCreation.doCreate("fas-proof-admit-mapper.xml", content);
		
		DaoCreation.doCreate("FasProofAdmit");
	}
	
	public static void fasSubjectTypeDo(){
		Map<String, String> fasSubjectTypeMap = new HashMap<String, String>();
		fasSubjectTypeMap.put("id", "ID");
		fasSubjectTypeMap.put("name", "NAME");
		fasSubjectTypeMap.put("priority", "PRIORITY");
		fasSubjectTypeMap.put("displayOrder", "DISP_ORDER");
		fasSubjectTypeMap.put("description", "DESCN");
		
		AutoCreateXMLMaper<FasProofSubject> deal = new AutoCreateXMLMaper<FasProofSubject>();
		String content = deal.createXml(FasProofSubject.class, fasSubjectTypeMap, "OUT_FAS_SUBJECT_TYPE");
		FileCreation.doCreate("fas-subject-type-mapper.xml", content);
		
		DaoCreation.doCreate("FasSubjectType");
	}
	
	public static void fasProofTypeDo(){
		Map<String, String> fasProofTypeMap = new HashMap<String, String>();
		fasProofTypeMap.put("id", "ID");
		fasProofTypeMap.put("name", "NAME");
		fasProofTypeMap.put("priority", "PRIORITY");
		fasProofTypeMap.put("displayOrder", "DISP_ORDER");
		fasProofTypeMap.put("description", "DESCN");
		
		AutoCreateXMLMaper<FasProofType> deal = new AutoCreateXMLMaper<FasProofType>();
		String content = deal.createXml(FasProofType.class, fasProofTypeMap, "OUT_FAS_PROOF_TYPE");
		FileCreation.doCreate("fas-proof-type-mapper.xml", content);
		
		DaoCreation.doCreate("FasProofType");
	}
	*/
	public static void mailReceiveDo(){
		Map<String, String> mailReceiveMap = new HashMap<String, String>();
		mailReceiveMap.put("id", "ID");
		mailReceiveMap.put("addressFrom", "ADDR_FROM");
		mailReceiveMap.put("addressTo", "ADDR_TO");
		mailReceiveMap.put("addressCopy", "ADDR_CC");
		mailReceiveMap.put("subject", "SUBJECT");
		mailReceiveMap.put("content", "CONTENT");
		mailReceiveMap.put("attachment", "ATTACHEMENT");
		mailReceiveMap.put("status", "STATUS");
		mailReceiveMap.put("flag", "FLAG");
		mailReceiveMap.put("createTime", "CREATE_TIME");
		mailReceiveMap.put("receiveTime", "RECEIVE_TIME");
		mailReceiveMap.put("userId", "USER_ID");
		mailReceiveMap.put("messageId", "MSG_ID");
		
		MapperCreation<MailReceive> deal = new MapperCreation<MailReceive>();
		String content = deal.createXml(MailReceive.class, mailReceiveMap, "SYS_MAIL_RECEIVE");
		FileCreation.doCreate("mail-receive-mapper.xml", content);
		
		DaoCreation.doCreate("MailReceive");
	}
	
	public static void mailSendDo(){
		Map<String, String> mailSendMap = new HashMap<String, String>();
		mailSendMap.put("id", "ID");
		mailSendMap.put("addressFrom", "ADDR_FROM");
		mailSendMap.put("addressTo", "ADDR_TO");
		mailSendMap.put("addressCopy", "ADDR_CC");
		mailSendMap.put("subject", "SUBJECT");
		mailSendMap.put("content", "CONTENT");
		mailSendMap.put("attachment", "ATTACHEMENT");
		mailSendMap.put("status", "STATUS");
		mailSendMap.put("flag", "FLAG");
		mailSendMap.put("createTime", "CREATE_TIME");
		mailSendMap.put("sendTime", "SEND_TIME");
		mailSendMap.put("userId", "USER_ID");
		
		MapperCreation<MailSend> deal = new MapperCreation<MailSend>();
		String content = deal.createXml(MailSend.class, mailSendMap, "SYS_MAIL_SEND");
		FileCreation.doCreate("mail-send-mapper.xml", content);
		
		DaoCreation.doCreate("MailSend");
	}
	
	public static void workCalPartDo(){
		Map<String, String> workCalPartMap = new HashMap<String, String>();
		workCalPartMap.put("id", "ID");
		workCalPartMap.put("startTime", "START_TIME");
		workCalPartMap.put("endTime", "END_TIME");
		workCalPartMap.put("shift", "SHIFT");
		workCalPartMap.put("workCalRule", "RULE_ID");
		
		MapperCreation<WorkCalPart> deal = new MapperCreation<WorkCalPart>();
		String content = deal.createXml(WorkCalPart.class, workCalPartMap, "OUT_WORK_CAL_PART");
		FileCreation.doCreate("work-cal-part-mapper.xml", content);
		
		DaoCreation.doCreate("WorkCalPart");
	}
	
	public static void workCalRuleDo(){
		Map<String, String> workCalRuleMap = new HashMap<String, String>();
		workCalRuleMap.put("id", "ID");
		workCalRuleMap.put("name", "NAME");
		workCalRuleMap.put("status", "STATUS");
		workCalRuleMap.put("workDate", "WORK_DATE");
		workCalRuleMap.put("week", "WEEK");
		workCalRuleMap.put("year", "YEAR");
		workCalRuleMap.put("workCalType", "TYPE_ID");
		
		MapperCreation<WorkCalRule> deal = new MapperCreation<WorkCalRule>();
		String content = deal.createXml(WorkCalRule.class, workCalRuleMap, "OUT_WORK_CAL_RULE");
		FileCreation.doCreate("work-cal-rule-mapper.xml", content);
		
		DaoCreation.doCreate("WorkCalRule");
	}
	
	public static void workCalTypeDo(){
		Map<String, String> workCalTypeMap = new HashMap<String, String>();
		workCalTypeMap.put("id", "ID");
		workCalTypeMap.put("name", "NAME");
		workCalTypeMap.put("description", "DESCRIPTION");
		
		MapperCreation<WorkCalType> deal = new MapperCreation<WorkCalType>();
		String content = deal.createXml(WorkCalType.class, workCalTypeMap, "OUT_WORK_CAL_TYPE");
		FileCreation.doCreate("work-cal-type-mapper.xml", content);
		
		DaoCreation.doCreate("WorkCalType");
	}
	
	public static void cmsCommentDo(){
		Map<String, String> cmsCommentMap = new HashMap<String, String>();
		cmsCommentMap.put("id", "ID");
		cmsCommentMap.put("title", "TITLE");
		cmsCommentMap.put("content", "CONTENT");
		cmsCommentMap.put("status", "STATUS");
		cmsCommentMap.put("createTime", "CREATE_TIME");
		cmsCommentMap.put("cmsArticle", "ARTICLE_ID");
		cmsCommentMap.put("poster", "USER_ID");
		
		MapperCreation<CmsComment> deal = new MapperCreation<CmsComment>();
		String content = deal.createXml(CmsComment.class, cmsCommentMap, "OUT_CMS_COMMENT");
		FileCreation.doCreate("cms-comment-mapper.xml", content);
		
		DaoCreation.doCreate("CmsComment");
	}
	
	public static void cmsFavoriteDo(){
		Map<String, String> cmsFavoriteMap = new HashMap<String, String>();
		cmsFavoriteMap.put("id", "ID");
		cmsFavoriteMap.put("subject", "SUBJECT");
		cmsFavoriteMap.put("createTime", "CREATE_TIME");
		cmsFavoriteMap.put("cmsComment", "COMMENT_ID");
		cmsFavoriteMap.put("cmsArticle", "ARTICLE_ID");
		cmsFavoriteMap.put("owner", "USER_ID");
		
		MapperCreation<CmsFavorite> deal = new MapperCreation<CmsFavorite>();
		String content = deal.createXml(CmsFavorite.class, cmsFavoriteMap, "OUT_CMS_FAVORITE");
		FileCreation.doCreate("cms-favorite-mapper.xml", content);
		
		DaoCreation.doCreate("CmsFavorite");
	}
	
	public static void createXML(Map<String, String> columnRelation, String tableName) {
		MapperCreation<CrmCustomer> deal = new MapperCreation<CrmCustomer>();
		String content = deal.createXml(CrmCustomer.class, columnRelation, tableName);
		FileCreation.doCreate("crm-customer-mapper.xml", content);
	}
	
	public static void cmsArticleDo(){
		Map<String, String> cmscmsArticleMap = new HashMap<String, String>();
		cmscmsArticleMap.put("id", "ID");
		cmscmsArticleMap.put("title", "TITLE");
		cmscmsArticleMap.put("shortTitle", "SHORT_TITLE");
		cmscmsArticleMap.put("subTitle", "SUB_TITLE");
		cmscmsArticleMap.put("content", "CONTENT");
		cmscmsArticleMap.put("summary", "SUMMARY");
		cmscmsArticleMap.put("logo", "LOGO");
		cmscmsArticleMap.put("keyword", "KEYWORD");
		cmscmsArticleMap.put("tags", "TAGS");
		cmscmsArticleMap.put("source", "SOURCE");
		cmscmsArticleMap.put("allowComment", "ALLOW_COMMENT");
		cmscmsArticleMap.put("status", "STATUS");
		cmscmsArticleMap.put("publishTime", "PUBLISH_TIME");
		cmscmsArticleMap.put("closeTime", "CLOSE_TIME");
		cmscmsArticleMap.put("type", "TYPE");
		cmscmsArticleMap.put("top", "TOP");
		cmscmsArticleMap.put("weight", "WEIGHT");
		cmscmsArticleMap.put("createTime", "CREATE_TIME");
		cmscmsArticleMap.put("template", "TEMPLATE");
		cmscmsArticleMap.put("viewCount", "VIEW_COUNT");
		cmscmsArticleMap.put("recommendId", "RECOMMEND_ID");
		cmscmsArticleMap.put("recommendStatus", "RECOMMEND_STATUS");
		cmscmsArticleMap.put("publisher", "USER_ID");
		cmscmsArticleMap.put("cmsCatalog", "CATALOG_ID");
		
		
		createXML(cmscmsArticleMap, "OUT_CMS_ARTICLE");
		DaoCreation.doCreate("CmsArticle");
	}
	
	public static void cmsCatalogDo(){
		Map<String, String> cmsCatalogMap = new HashMap<String, String>();
		cmsCatalogMap.put("id", "ID");
		cmsCatalogMap.put("name", "NAME");
		cmsCatalogMap.put("code", "CODE");
		cmsCatalogMap.put("logo", "LOGO");
		cmsCatalogMap.put("type", "TYPE");
		cmsCatalogMap.put("templateIndex", "TEMPLATE_INDEX");
		cmsCatalogMap.put("templateList", "TEMPLATE_LIST");
		cmsCatalogMap.put("templateDetail", "TEMPLATE_DETAIL");
		cmsCatalogMap.put("keyword", "KEYWORD");
		cmsCatalogMap.put("description", "DESCRIPTION");
		cmsCatalogMap.put("cmsCatalog", "PARENT_ID");
		
		
		createXML(cmsCatalogMap, "OUT_CMS_CATALOG");
		DaoCreation.doCreate("CmsCatalog");
	}
	
	public static void workCalInfoDo(){
		Map<String, String> workCalInfoMap = new HashMap<String, String>();
		workCalInfoMap.put("id", "ID");
		workCalInfoMap.put("type", "TYPE");
		workCalInfoMap.put("title", "TITLE");
		workCalInfoMap.put("content", "CONTENT");
		workCalInfoMap.put("description", "DESCN");
		workCalInfoMap.put("startTime", "START_TIME");
		workCalInfoMap.put("endTime", "END_TIME");
		workCalInfoMap.put("priority", "PRIORITY");
		workCalInfoMap.put("alertTime", "ALERT_TIME");
		workCalInfoMap.put("userId", "USER_ID");
		
		createXML(workCalInfoMap, "OUT_WORK_CAL_INFO");
		DaoCreation.doCreate("WorkCalInfo");
	}
}
