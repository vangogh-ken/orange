package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemIntroduce implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

}
