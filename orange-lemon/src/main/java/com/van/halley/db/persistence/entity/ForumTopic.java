package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumTopic implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String title;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private int hitCount;
    private int postCount;
    private User user;
    private List<ForumPost> forumPosts = new ArrayList<ForumPost>(0);
    public ForumTopic() {
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getHitCount() {
		return hitCount;
	}
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
	public Integer getPostCount() {
		return postCount;
	}
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<ForumPost> getForumPosts() {
		return forumPosts;
	}
	public void setForumPosts(List<ForumPost> forumPosts) {
		this.forumPosts = forumPosts;
	}
	

}
