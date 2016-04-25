package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class CmsFavorite implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;
    
    /** null. */
    private String subject;

    /** null. */
    private Date createTime;

    /** null. */
    private CmsComment cmsComment;

    /** null. */
    private CmsArticle cmsArticle;

    /** null. */
    private User owner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CmsComment getCmsComment() {
		return cmsComment;
	}

	public void setCmsComment(CmsComment cmsComment) {
		this.cmsComment = cmsComment;
	}

	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}

	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
