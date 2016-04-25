package com.van.halley.db.persistence.entity;

import java.io.Serializable;

/**
 * 集装箱动态
 * @author ken
 */
public class FreightBoxRetrieve implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private FreightBox freightBox;
	private FreightSeal freightSeal;
	/**
	 * 当前箱状态
	 */
	private String boxStatus;
	/**
	 * 进场方式
	 */
	private String enterYardType;
	/**
	 * 出场方式
	 */
	private String leaveYardType;
	/**
	 * 进口提单号
	 */
	private String importBlNumber;
	/**
	 * 出口提单号、放箱提单号
	 */
	private String exportBlNumber;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FreightBox getFreightBox() {
		return freightBox;
	}
	public void setFreightBox(FreightBox freightBox) {
		this.freightBox = freightBox;
	}
	public FreightSeal getFreightSeal() {
		return freightSeal;
	}
	public void setFreightSeal(FreightSeal freightSeal) {
		this.freightSeal = freightSeal;
	}
	
	/**
	 * 到站日期，是否在途，信息状态，箱号，箱属，箱型，空重状态，封号，业务编号，
	 * 业务员，收货单位，收货联系人，收货联系电话，实际收货人，实际收货联系电话，
	 * 发货单位，发货联系人，发货联系电话，货品名称，件数，重量，到站，发站，
	 * 落箱地址，落箱日期，落箱联系人，落箱联系方式，提箱地址，提箱日期，提箱联系人，
	 * 提箱联系方式，提货地址，提货日期，提货联系人 ，提货联系方式，
	 * 装箱地址，装箱日期，装箱联系人，装箱联系方式，卸货地址，卸货日期，卸货联系人，卸货联系方式
	 * 录入日期，录入人员，备注
	 * 
	 * *******/
	
	
}
