package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CmsArticle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;

    /** null. */
    private CmsCatalog cmsCatalog;

    /** null. */
    private String title;

    /** null. */
    private String shortTitle;

    /** null. */
    private String subTitle;

    /** null. */
    private String content;

    /** null. */
    private String summary;

    /** null. */
    private String logo;

    /** null. */
    private String keyword;

    /** null. */
    private String tags;

    /** null. */
    private String source;

    /** null. */
    private String allowComment;

    /** null. */
    private String status;

    /** null. */
    private Date publishTime;

    /** null. */
    private Date closeTime;

    /** null. */
    private String type;

    /** null. */
    private String top;

    /** null. */
    private String weight;

    /** null. */
    private Date createTime;

    /** null. */
    private String template;

    /** null. */
    private int viewCount;

    /** null. */
    private String recommendId;

    /** null. */
    private String recommendStatus;

    /** null. */
    private User publisher;

    /** . */
    private List<CmsFavorite> cmsFavorites = new ArrayList<CmsFavorite>(0);

    /** . */
    private List<CmsComment> cmsComments = new ArrayList<CmsComment>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CmsCatalog getCmsCatalog() {
		return cmsCatalog;
	}

	public void setCmsCatalog(CmsCatalog cmsCatalog) {
		this.cmsCatalog = cmsCatalog;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	
	@JsonIgnore
	public List<CmsFavorite> getCmsFavorites() {
		return cmsFavorites;
	}

	public void setCmsFavorites(List<CmsFavorite> cmsFavorites) {
		this.cmsFavorites = cmsFavorites;
	}

	@JsonIgnore
	public List<CmsComment> getCmsComments() {
		return cmsComments;
	}

	public void setCmsComments(List<CmsComment> cmsComments) {
		this.cmsComments = cmsComments;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

}
