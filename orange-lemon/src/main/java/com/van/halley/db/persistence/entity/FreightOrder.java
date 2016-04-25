package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.van.halley.fre.util.FreightVersionUtil;

/**
 * @author Think
 * 业务订单基本信息
 */
public class FreightOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 业务员
	 */
	private String salesman;
	/**
	 * 操作员
	 */
	private String manipulator;
	/**
	 * 业务归属
	 */
	private String orderScope;
	/**
	 * 一级类型
	 */
	private String firstType;
	/**
	 * 二级类型
	 */
	private String secondType;
	/**
	 * 三级类型
	 */
	private String thirdType;
	/**
	 * 四级类型
	 */
	private String fourthType;
	/**
	 * 委托单位(客户)
	 */
	private String delegatePart;
	/**
	 * 委托号(客户给的委托)
	 */
	private String delegateNumber;
	/**
	 * 委托联系人
	 */
	private String delegateContact;
	/**
	 * 委托书,在系统中就只委托书文件数据
	 */
	private String commission;
	/**
	 * 货主
	 */
	private String cargoOwner;
	/**
	 * 货品名称
	 */
	private String cargoName;
	/**
	 * 货物规格
	 */
	private String cargoAmount;
	/**
	 * 货品重量
	 */
	private double cargoWeight;
	/**
	 * 货品体积
	 */
	private double cargoCapacity;
	/**
	 * 下单时间
	 */
	private Date placeTime;
	/**
	 * 完结时间
	 */
	private Date finishTime;
	/**
	 * 亏损原因
	 */
	private String deficitReason;
	/**
	 * 费用状态
	 */
	private String expenseStatus;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 创建者ID,只供查询统计使用因此只需要存入ID
	*/ 
	private String creatorUserId;
	/**
	 * 创建者部门ID
	*/
	private String orgEntityId;
	/**
	 * 客服员
	 */
	private User receptionist;
	/**
	 * 下单员
	 */
	//TODO
	private User placeman;
	/**
	 * 箱需
	 */
	private List<FreightBoxRequire> freightBoxRequires;
	/**
	 * 与业务关联的费用（仅业务添加）
	 */
	private List<FreightExpense> freightExpenses;
	/**
	 * 收入折合
	 */
	private double incomeAmount;
	/**
	 * 收入税金
	 */
	private double incomeTax;
	/**
	 * 支出折合
	 */
	private double paymentAmount;
	/**
	 * 支出税金
	 */
	private double paymentTax;
	/**
	 * 应收RMB
	 */
	private double incomeRmb;
	/**
	 * 应收美元
	 */
	private double incomeDollar;
	/**
	 * 应付RMB
	 */
	private double paymentRmb;
	/**
	 * 应付美元
	 */
	private double paymentDollar;
	/**
	 * 折合差额（总）
	 */
	private double balance;
	/**
	 * 销售税差
	 */
	private double saleTaxBalance;
	/**
	 * 财务税差
	 */
	private double fasTaxBalance;
	
	/**
	 * 营收收入
	 */
	private double revenueAmount;
	/**
	 * 非营收收入
	 */
	private double unrevenueAmount;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	/*public FreightOrder(){
		double countIncomeAmount = 0;
		double countExpenseAmount = 0;
		double taxationIncomeAmount = 0;
		double taxationExpenseAmount = 0;
		List<FreightExpense>  list = this.getFreightExpenses();
		if(list == null || list.isEmpty()){
			this.setIncomeAmount(0);
			this.setPaymentAmount(0);
			this.setIncomeTax(0);
			this.setPaymentTax(0);
			this.setBalance(0);
		}else{
			for(FreightExpense item : list){
				double amount = 0;
				double taxation = 0;
				if("箱".equals(item.getCountUnit())){
					amount = item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size();
					taxation = amount - item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size()/(1 + item.getFasInvoiceType().getTaxRate());
				}else{
					amount = item.getPredictCount()*item.getExchangeRate();
					taxation = amount - item.getPredictCount()*item.getExchangeRate()/(1 + item.getFasInvoiceType().getTaxRate());
				}
				
				if("付".equals(item.getIncomeOrExpense())){
					countExpenseAmount += amount;
					taxationExpenseAmount += taxation;
				}else{
					countIncomeAmount += amount;
					taxationIncomeAmount += taxation;
				}
			}
			
			this.setIncomeAmount(countIncomeAmount);
			this.setPaymentAmount(countExpenseAmount);
			this.setIncomeTax(taxationIncomeAmount);
			this.setPaymentTax(taxationExpenseAmount);
			this.setBalance(countIncomeAmount - countExpenseAmount - taxationIncomeAmount + taxationExpenseAmount);
		}
	}*/
	
	public double getIncomeAmount() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public double getIncomeTax() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					d += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public double getPaymentAmount() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("付".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate();
				}
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public double getPaymentTax() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("付".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					d += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public double getBalance() {
		//提高效率，自行计算
		//double d = getIncomeAmount() - getPaymentAmount() - getIncomeTax() + getPaymentTax();
		//return Double.parseDouble(String.format("%.2f", d));
		double d = 0;
		double out = 0;//进项税
		double in = 0;//销项税
		List<FreightExpense>  list = this.getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense item : list){
			if("付".equals(item.getIncomeOrExpense())){
				if("箱".equals(item.getCountUnit())){
					d += 0 - (item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size())/(1 + item.getFasInvoiceType().getTaxRate());
					
					out += item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size()*item.getFasInvoiceType().getTaxRate() / (1 + item.getFasInvoiceType().getTaxRate());
				}else if("票".equals(item.getCountUnit())){
					d += 0 - (item.getPredictCount()*item.getExchangeRate())/(1 + item.getFasInvoiceType().getTaxRate());
					
					out += item.getPredictCount()*item.getExchangeRate()*item.getFasInvoiceType().getTaxRate() / (1 + item.getFasInvoiceType().getTaxRate());
				}
			}else if("收".equals(item.getIncomeOrExpense())){
				if("箱".equals(item.getCountUnit())){
					d += (item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size())/(1 + item.getFasInvoiceType().getTaxRate());
					
					in += item.getPredictCount()*item.getExchangeRate()*item.getFreightOrderBoxs().size()*item.getFasInvoiceType().getTaxRate() / (1 + item.getFasInvoiceType().getTaxRate());
				}else if("票".equals(item.getCountUnit())){
					d += (item.getPredictCount()*item.getExchangeRate())/(1 + item.getFasInvoiceType().getTaxRate());
					
					in += item.getPredictCount()*item.getExchangeRate()*item.getFasInvoiceType().getTaxRate() / (1 + item.getFasInvoiceType().getTaxRate());
				}
			}
		}
		
		//20160201之后的订单收支差进项税减半
		if(!FreightVersionUtil.assertV1(placeTime)){
			d = d - (out > in ? (out - in) * 0.5 : 0);
		}
		
		return Double.parseDouble(String.format("%.2f", d));
	}
	
	public double getRevenueAmount() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense()) && "T".equals(freightExpense.getFreightExpenseType().getRevenue())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	
	public double getUnrevenueAmount() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense()) && !"T".equals(freightExpense.getFreightExpenseType().getRevenue())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount()*freightExpense.getExchangeRate();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	
	@JsonIgnore
	public List<FreightExpense> getFreightExpenses() {
		return freightExpenses;
	}
	public void setFreightExpenses(List<FreightExpense> freightExpenses) {
		this.freightExpenses = freightExpenses;
	}
	public String getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getManipulator() {
		return manipulator;
	}
	public void setManipulator(String manipulator) {
		this.manipulator = manipulator;
	}
	public String getOrderScope() {
		return orderScope;
	}
	public void setOrderScope(String orderScope) {
		this.orderScope = orderScope;
	}
	public String getFirstType() {
		return firstType;
	}
	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}
	public String getSecondType() {
		return secondType;
	}
	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}
	public String getThirdType() {
		return thirdType;
	}
	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}
	public String getFourthType() {
		return fourthType;
	}
	public void setFourthType(String fourthType) {
		this.fourthType = fourthType;
	}
	public String getDelegatePart() {
		return delegatePart;
	}
	public void setDelegatePart(String delegatePart) {
		this.delegatePart = delegatePart;
	}
	public String getDelegateNumber() {
		return delegateNumber;
	}
	public void setDelegateNumber(String delegateNumber) {
		this.delegateNumber = delegateNumber;
	}
	public String getDelegateContact() {
		return delegateContact;
	}
	public void setDelegateContact(String delegateContact) {
		this.delegateContact = delegateContact;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getCargoOwner() {
		return cargoOwner;
	}
	public void setCargoOwner(String cargoOwner) {
		this.cargoOwner = cargoOwner;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getCargoAmount() {
		return cargoAmount;
	}
	public void setCargoAmount(String cargoAmount) {
		this.cargoAmount = cargoAmount;
	}
	public double getCargoWeight() {
		return cargoWeight;
	}
	public void setCargoWeight(double cargoWeight) {
		this.cargoWeight = cargoWeight;
	}
	public double getCargoCapacity() {
		return cargoCapacity;
	}
	public void setCargoCapacity(double cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}
	public Date getPlaceTime() {
		return placeTime;
	}
	public void setPlaceTime(Date placeTime) {
		this.placeTime = placeTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getDeficitReason() {
		return deficitReason;
	}
	public void setDeficitReason(String deficitReason) {
		this.deficitReason = deficitReason;
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
	public String getCreatorUserId() {
		return creatorUserId;
	}
	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	public String getOrgEntityId() {
		return orgEntityId;
	}
	public void setOrgEntityId(String orgEntityId) {
		this.orgEntityId = orgEntityId;
	}
	public List<FreightBoxRequire> getFreightBoxRequires() {
		return freightBoxRequires;
	}
	public void setFreightBoxRequires(List<FreightBoxRequire> freightBoxRequires) {
		this.freightBoxRequires = freightBoxRequires;
	}
	public User getReceptionist() {
		return receptionist;
	}
	public void setReceptionist(User receptionist) {
		this.receptionist = receptionist;
	}
	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public void setIncomeTax(double incomeTax) {
		this.incomeTax = incomeTax;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public void setPaymentTax(double paymentTax) {
		this.paymentTax = paymentTax;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setRevenueAmount(double revenueAmount) {
		this.revenueAmount = revenueAmount;
	}
	public void setUnrevenueAmount(double unrevenueAmount) {
		this.unrevenueAmount = unrevenueAmount;
	}
	public double getSaleTaxBalance() {
		double in = 0;
		double out = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return 0;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					in += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					in += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}else{
				if("箱".equals(freightExpense.getCountUnit())){
					out += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					out += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}
		}
		
		double d = out - in;
		
		if(!FreightVersionUtil.assertV1(placeTime)){
			d = d > 0 ? d/2 : d;
		}
		
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setSaleTaxBalance(double saleTaxBalance) {
		this.saleTaxBalance = saleTaxBalance;
	}
	public double getFasTaxBalance() {
		double in = 0;
		double out = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return 0;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense())){
				if("箱".equals(freightExpense.getCountUnit())){
					in += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					in += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}else{
				if("箱".equals(freightExpense.getCountUnit())){
					out += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate()*freightExpense.getFreightOrderBoxs().size())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}else{
					out += (freightExpense.getPredictCount()*freightExpense.getExchangeRate()
							*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
				}
			}
		}
		
		double d = 0;
		if(!FreightVersionUtil.assertV1(placeTime)){
			d = (out - in) > 0 ? (out - in)/2 : d;
		}
		
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setFasTaxBalance(double fasTaxBalance) {
		this.fasTaxBalance = fasTaxBalance;
	}
	public double getIncomeRmb() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense()) && "人民币".equals(freightExpense.getCurrency())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setIncomeRmb(double incomeRmb) {
		this.incomeRmb = incomeRmb;
	}
	public double getIncomeDollar() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("收".equals(freightExpense.getIncomeOrExpense()) && "美元".equals(freightExpense.getCurrency())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setIncomeDollar(double incomeDollar) {
		this.incomeDollar = incomeDollar;
	}
	public double getPaymentRmb() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("付".equals(freightExpense.getIncomeOrExpense()) && "人民币".equals(freightExpense.getCurrency())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setPaymentRmb(double paymentRmb) {
		this.paymentRmb = paymentRmb;
	}
	public double getPaymentDollar() {
		double d = 0;
		List<FreightExpense>  list = getFreightExpenses();
		if(list == null || list.isEmpty()){
			return d;
		}
		for(FreightExpense freightExpense : list){
			if("付".equals(freightExpense.getIncomeOrExpense()) && "美元".equals(freightExpense.getCurrency())){
				if("箱".equals(freightExpense.getCountUnit())){
					d += freightExpense.getPredictCount()*freightExpense.getFreightOrderBoxs().size();
				}else{
					d += freightExpense.getPredictCount();
				}
				
			}
		}
		return Double.parseDouble(String.format("%.2f", d));
	}
	public void setPaymentDollar(double paymentDollar) {
		this.paymentDollar = paymentDollar;
	}
	
	
	
}
