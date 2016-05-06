package com.van.orange.apple.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 
	 */
	private String categoryName;
	/**
	 * 
	 */
	private int categoryIndex;
	/**
	 * 
	 */
	private String aboveCategoryId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

}
