package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class BpmMailTemplate implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String templateName;

    private String subject;

    private String content;
    
    private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;


    public BpmMailTemplate() {
    }

    public BpmMailTemplate(String templateName, String subject, String content) {
        this.templateName = templateName;
        this.subject = subject;
        this.content = content;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

}
