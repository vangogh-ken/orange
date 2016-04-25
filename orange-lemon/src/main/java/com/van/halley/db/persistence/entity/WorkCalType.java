package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * 工作日历类型
 */
public class WorkCalType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    /** null. */
    private String name;
    
    private String description;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WorkCalRule> getWorkcalRules() {
		return workcalRules;
	}

	public void setWorkcalRules(Set<WorkCalRule> workcalRules) {
		this.workcalRules = workcalRules;
	}

	/** . */
    private Set<WorkCalRule> workcalRules = new HashSet<WorkCalRule>(0);

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
