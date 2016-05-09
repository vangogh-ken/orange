package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemStock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 
	 */
	private String itemSubstanceId;
	/**
	 * 
	 */
	private int totalCount;
	/**
	 * 
	 */
	private int soledCount;
	/**
	 * 
	 */
	private int stockCount;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

}
