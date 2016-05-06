package com.van.orange.apple.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class ItemAttribute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 字段名
	 */
	private String attributeName;
	/**
	 * 字段
	 */
	private String attributeColumn;
	/**
	 * 字段类型
	 */
	private String attributeType;
	/**
	 * 是否必须
	 */
	private String isRequired;
	/**
	 * 是否为可选值
	 */
	private String isSelect;
	/**
	 * 
	 */
	private String vAttrId;
	/**
	 * 类型
	 */
	private String itemCategoryId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

}
