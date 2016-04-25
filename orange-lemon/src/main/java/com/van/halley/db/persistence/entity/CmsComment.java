package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CmsComment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;

    /** null. */
    private String title;

    /** null. */
    private String content;

    /** null. */
    private String status;

    /** null. */
    private Date createTime;
    
    /** null. */
    private CmsArticle cmsArticle;

    /** null. */
    private User poster;

    /** . */
    private List<CmsFavorite> cmsFavorites = new ArrayList<CmsFavorite>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}

	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
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


	public List<CmsFavorite> getCmsFavorites() {
		return cmsFavorites;
	}

	public void setCmsFavorites(List<CmsFavorite> cmsFavorites) {
		this.cmsFavorites = cmsFavorites;
	}

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

}
