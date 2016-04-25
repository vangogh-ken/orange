package com.van.halley.db.persistence.entity;

import java.io.Serializable;

public class BpmConfigOperation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String enable;
	private String command;
	private BpmConfigNode bpmConfigNode;
	private BpmOperationType bpmOperationType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BpmConfigNode getBpmConfigNode() {
		return bpmConfigNode;
	}
	public void setBpmConfigNode(BpmConfigNode bpmConfigNode) {
		this.bpmConfigNode = bpmConfigNode;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public BpmOperationType getBpmOperationType() {
		return bpmOperationType;
	}
	public void setBpmOperationType(BpmOperationType bpmOperationType) {
		this.bpmOperationType = bpmOperationType;
	}

}
