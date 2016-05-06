package com.van.orange.apple.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.van.halley.db.persistence.entity.User;

public class ItemImage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String imageName;
	/**
	 * 
	 */
	private String imageUrl;
	/**
	 * 
	 */
	private String imageRef;
	/**
	 * 
	 */
	private String imageFormat;
	/**
	 * 
	 */
	private long imageSize;
	/**
	 * 
	 */
	private User creator;
    /**
     * 
     */
    private User modifier;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
}
