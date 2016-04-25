package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.van.halley.util.StringUtil;

/**
 * @author ken
 * 货运财务收支明细（每个集装箱对应一条信息或者一条业务可以对应多条数据） dc_finance
 */
public class FreightExpense implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 费用编号
	 */
	private String expenseNumber;
	/**
	 * 收/支
	 */
	private String incomeOrExpense;
	/**
	 * 是否与报价一致
	 */
	private String price;
	/**
	 * 是否与实际金额一致
	 */
	private String actual;
	/**
	 * 费用类型
	 */
	private FreightExpenseType freightExpenseType;
	/**
	 * 发票票种
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 发起费用的单位，如果费用是对内部部门的，也将部门看成外部单位，但也因此必须在此的部门名称要与组织机构中的名称一致
	 */
	private FreightPart freightPartA;
	/**
	 * 收付对象单位，如果费用是对内部部门的，也将部门看成外部单位，但也因此必须在此的部门名称要与组织机构中的名称一致
	 */
	private FreightPart freightPartB;
	/**
	 * 订单
	 */
	private FreightOrder freightOrder;
	/**
	 * 系统报价
	 */
	private FreightPrice freightPrice;
	/**
	 * 相关联的动作
	 */
	private FreightAction freightAction;
	/**
	 * 箱需（如果费用按箱计算，则通过箱需数量计算）
	 */
	//private FreightBoxRequire freightBoxRequire;
	/**
	 * 对账单
	 */
	private FreightStatement freightStatement;
	/**
	 * 计费单位(按订单/按集装箱)
	 */
	private String countUnit;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 汇率
	 */
	private double exchangeRate;
	/**
	 * 预计金额
	 */
	private double predictCount;
	/**
	 * 实际金额
	 */
	private double actualCount;
	/**
	 * 税金
	 */
	private double taxation;
	/**
	 * 预计总额
	 */
	private double predictAmount;
	/**
	 * 实际总额
	 */
	private double actualAmount;
	/**
	 * 提单号
	 */
	private String blNumber;
	/**
	 * 集装箱号
	 */
	private String boxNumber;
	/**
	 * 创建者
	*/
	private User creator; 
	/**
	 * 创建者部门
	 */
	private OrgEntity orgEntity;
	
	private List<FreightOrderBox> freightOrderBoxs;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	public FreightAction getFreightAction() {
		return freightAction;
	}
	public void setFreightAction(FreightAction freightAction) {
		this.freightAction = freightAction;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonIgnore//异步请求时忽略此，否则会循环读取BUG
	public FreightOrder getFreightOrder() {
		return freightOrder;
	}
	public void setFreightOrder(FreightOrder freightOrder) {
		this.freightOrder = freightOrder;
	}
	public String getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public double getPredictCount() {
		return predictCount;
	}
	public void setPredictCount(double predictCount) {
		this.predictCount = predictCount;
	}
	public double getActualCount() {
		return actualCount;
	}
	public void setActualCount(double actualCount) {
		this.actualCount = actualCount;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public FreightStatement getFreightStatement() {
		return freightStatement;
	}
	public void setFreightStatement(FreightStatement freightStatement) {
		this.freightStatement = freightStatement;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public FreightPrice getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(FreightPrice freightPrice) {
		this.freightPrice = freightPrice;
	}
	public FreightExpenseType getFreightExpenseType() {
		return freightExpenseType;
	}
	public void setFreightExpenseType(FreightExpenseType freightExpenseType) {
		this.freightExpenseType = freightExpenseType;
	}
	@JsonIgnore
	public FreightPart getFreightPartA() {
		return freightPartA;
	}
	public void setFreightPartA(FreightPart freightPartA) {
		this.freightPartA = freightPartA;
	}
	public FreightPart getFreightPartB() {
		return freightPartB;
	}
	public void setFreightPartB(FreightPart freightPartB) {
		this.freightPartB = freightPartB;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public String getExpenseNumber() {
		return expenseNumber;
	}
	public void setExpenseNumber(String expenseNumber) {
		this.expenseNumber = expenseNumber;
	}
	public User getCreator() {
		return creator;
	}
	@JsonIgnore
	public void setCreator(User creator) {
		this.creator = creator;
	}
	@JsonIgnore
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public List<FreightOrderBox> getFreightOrderBoxs() {
		return freightOrderBoxs;
	}
	public void setFreightOrderBoxs(List<FreightOrderBox> freightOrderBoxs) {
		this.freightOrderBoxs = freightOrderBoxs;
	}
	//保留两位小数
	public double getTaxation() {
		if("箱".equals(this.countUnit)){
			if(this.getFreightOrderBoxs() == null || this.getFreightOrderBoxs().size() == 0){
				return 0;
			}else{
				double d = (this.predictCount*this.exchangeRate*this.getFasInvoiceType().getTaxRate())*this.getFreightOrderBoxs().size()/(1 + this.getFasInvoiceType().getTaxRate());
				return Double.parseDouble(String.format("%.2f", d));
			}
		}else{
			double d = (this.predictCount*this.exchangeRate*this.getFasInvoiceType().getTaxRate())/(1 + this.getFasInvoiceType().getTaxRate());
			return Double.parseDouble(String.format("%.2f", d));
		}
	}
	public void setTaxation(double taxation) {
		this.taxation = taxation;
	}
	public double getPredictAmount() {
		if("箱".equals(this.countUnit)){
			if(this.getFreightOrderBoxs() == null || this.getFreightOrderBoxs().size() == 0){
				return 0;
			}else{
				double d = this.predictCount*this.getFreightOrderBoxs().size();
				return Double.parseDouble(String.format("%.2f", d));
			}
		}else{
			double d = this.predictCount;
			return Double.parseDouble(String.format("%.2f", d));
		}
	}
	public void setPredictAmount(double predictAmount) {
		this.predictAmount = predictAmount;
	}
	public double getActualAmount() {
		if("箱".equals(this.countUnit)){
			if(this.getFreightOrderBoxs() == null || this.getFreightOrderBoxs().size() == 0){
				return 0;
			}else{
				double d = this.actualCount*this.getFreightOrderBoxs().size();
				return Double.parseDouble(String.format("%.2f", d));
			}
		}else{
			double d = this.actualCount;
			return Double.parseDouble(String.format("%.2f", d));
		}
	}
	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getBlNumber() {
		if(this.getFreightOrder() != null && this.getFreightOrder().getFreightBoxRequires() != null){
			for(FreightBoxRequire freightBoxRequire: this.getFreightOrder().getFreightBoxRequires()){
				if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
					return freightBoxRequire.getBlNo();
				}
			}
		}
		return "";
	}
	public void setBlNumber(String blNumber) {
		this.blNumber = blNumber;
	}
	public String getBoxNumber() {
		StringBuilder text = new StringBuilder();
		if("箱".equals(this.getCountUnit())){
			if(this.getFreightOrderBoxs() != null){
				for(FreightOrderBox freightOrderBox : this.getFreightOrderBoxs()){
					if(freightOrderBox.getFreightBox() != null){
						text.append(freightOrderBox.getFreightBox().getBoxNumber() + ",");
					}
				}
			}
			if(text.lastIndexOf(",") > 0){
				text.deleteCharAt(text.lastIndexOf(","));
			}
			return text.toString();
		}
		
		return "";
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	
}
