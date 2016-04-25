package com.van.halley.db.persistence.entity;

import java.io.Serializable;


public class BpmConfigNotice implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String type;

    private String receiver;

    private String dueDate;
    
    private BpmMailTemplate bpmMailTemplate;

    private BpmConfigNode bpmConfigNode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public BpmMailTemplate getBpmMailTemplate() {
		return bpmMailTemplate;
	}

	public void setBpmMailTemplate(BpmMailTemplate bpmMailTemplate) {
		this.bpmMailTemplate = bpmMailTemplate;
	}

	public BpmConfigNode getBpmConfigNode() {
		return bpmConfigNode;
	}

	public void setBpmConfigNode(BpmConfigNode bpmConfigNode) {
		this.bpmConfigNode = bpmConfigNode;
	}


}
