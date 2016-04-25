package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ForumPost implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String forumTopicId;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private User user;
    private Integer priority;

    
    public ForumPost() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getForumTopicId() {
		return forumTopicId;
	}

	public void setForumTopicId(String forumTopicId) {
		this.forumTopicId = forumTopicId;
	}


}
