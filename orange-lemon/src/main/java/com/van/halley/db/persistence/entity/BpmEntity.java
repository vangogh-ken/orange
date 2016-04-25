package com.van.halley.db.persistence.entity;


/**
 * 可用于流程的实体类的抽象类
 *
 */
public abstract class BpmEntity{
	public abstract String getID();
	public abstract void setID(String ID);
	
	public abstract String getSTATUS();
	public abstract void setSTATUS(String STATUS);
	
	public abstract String getPRCI_ID();
	public abstract void setPRCI_ID(String PRCI_ID);
}
