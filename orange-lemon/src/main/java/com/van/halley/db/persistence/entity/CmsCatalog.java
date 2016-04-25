package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CmsCatalog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;

    /** null. */
    private CmsCatalog cmsCatalog;

    /** null. */
    private String name;

    /** null. */
    private String code;

    /** null. */
    private String logo;

    /** null. */
    private String type;
    
    /** null. */
    private String priority;

    /** null. */
    private String templateIndex;

    /** null. */
    private String templateList;

    /** null. */
    private String templateDetail;

    /** null. */
    private String keyword;

    /** null. */
    private String description;

    /** . */
    private List<CmsCatalog> cmsCatalogs = new ArrayList<CmsCatalog>(0);

    /** . */
    private List<CmsArticle> cmsArticles = new ArrayList<CmsArticle>(0);

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateIndex() {
		return templateIndex;
	}

	public void setTemplateIndex(String templateIndex) {
		this.templateIndex = templateIndex;
	}

	public String getTemplateList() {
		return templateList;
	}

	public void setTemplateList(String templateList) {
		this.templateList = templateList;
	}

	public String getTemplateDetail() {
		return templateDetail;
	}

	public void setTemplateDetail(String templateDetail) {
		this.templateDetail = templateDetail;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CmsCatalog> getCmsCatalogs() {
		return cmsCatalogs;
	}

	public void setCmsCatalogs(List<CmsCatalog> cmsCatalogs) {
		this.cmsCatalogs = cmsCatalogs;
	}

	public List<CmsArticle> getCmsArticles() {
		return cmsArticles;
	}

	public void setCmsArticles(List<CmsArticle> cmsArticles) {
		this.cmsArticles = cmsArticles;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
