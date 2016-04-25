<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>账单填报(商务)</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
    <%@include file="/common/header2.jsp"%>
    <div class="page-container">
    	<%@include file="/common/menu.jsp"%>
    	<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper"> <!-- begin page-content-wrapper -->
			<div class="page-content"> <!-- begin page-content-->
				<%@include file="/common/setting.jsp"%>
				<div class="row">
				  <div class="col-md-12">
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-align-justify"></i>账单填报(商务)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='fre-statement-input-commerce.do'">
											新增<i class="fa fa-arrows"></i></button>
									
										<button class="btn btn-sm red" onclick="if(canRemove()) table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm green" id="viewExpense" >
											费用明细</button>
										<button class="btn btn-sm green" id="addExpense">
											添加费用</button>
											
										<button class="btn btn-sm red" id="exportExpense">
											导出费用</button>
										<button class="btn btn-sm red" id="exportExpenseSplit">
											导出费用</button>
											
										<button class="btn btn-sm green" id="toStatementAudit">
											提交复核</button>
										<button class="btn btn-sm green" id="doneRecallStatement">
											取消复核</button>
										  
										<button class="btn btn-sm red" id="viewInvoice" >
											发票明细</button>
										<!-- 
										<button class="btn btn-sm red" id="addInvoice">
											添加开票</button>
										-->
										<button class="btn btn-sm red" id="toAddNumber">
											添加票号</button>
										
										<button class="btn btn-sm green" id="applyInvoiceBtn">
											申请开票</button>
										<button class="btn btn-sm green" id="applyPayBtn">
											申请付款</button>
										<button class="btn btn-sm red" id="statementOffset">
											账单冲抵</button>
										<button class="btn btn-sm red" id="invalidStatement">
											作废账单</button>
											
								</div>
								<div class="pull-right" >
									<label for="pageSize">每页显示</label>
									<select id="pageSize" class="m-page-size"> 
									    <option value="10">10条</option> 
									    <option value="20">20条</option>
									    <option value="50">50条</option>
									 </select>
								</div>
							</article>
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="fre-statement-list-commerce.do" class="form-inline">
									<label for="statementNumber">账单号</label>
								    <input type="text" id="statementNumber" name="statementNumber" value="${param.statementNumber}" class="form-control">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control">
								    <label for="partName">单位名称</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="incomeOrExpense">收支</label>
								    <select id="incomeOrExpense" name="incomeOrExpense" class="form-control">
								    	<option value=""></option>
								    	<option value="收" <c:if test="${param.incomeOrExpense == '收' }">selected</c:if> >收</option>
								    	<option value="付" <c:if test="${param.incomeOrExpense == '付' }">selected</c:if> >付</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.status == '未提交' }">selected</c:if> >未提交</option>
								    	<option value="冲抵过" <c:if test="${param.status == '冲抵过' }">selected</c:if> >冲抵过</option>
								    	<option value="待开票" <c:if test="${param.status == '待开票' }">selected</c:if> >待开票</option>
								    	<option value="开票中" <c:if test="${param.status == '开票中' }">selected</c:if> >开票中</option>
								    	<option value="已开票" <c:if test="${param.status == '已开票' }">selected</c:if> >已开票</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
								    	<option value="付款审核中" <c:if test="${param.status == '付款审核中' }">selected</c:if> >付款审核中</option>
								    	<option value="已冲抵销账" <c:if test="${param.status == '已冲抵销账' }">selected</c:if> >已冲抵销账</option>
								    	<option value="已作废" <c:if test="${param.status == '已作废' }">selected</c:if> >已作废</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-statement-remove-commerce.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="STATEMENT_NUMBER">账单号</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="FRE_PART_ID">单位</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE_ID">票种</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="MONEY_COUNT_RMB">金额(RMB)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_RMB">已开票(RMB)</th>
								                    <th class="sorting" name="REMAIN_COUNT_RMB">未开票(RMB)</th>
								                    <th class="sorting" name="MONEY_COUNT_DOLLAR">金额(USD)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_DOLLAR">已开票(USD)</th>
								                    <th class="sorting" name="REMAIN_COUNT_DOLLAR">未开票(USD)</th>
								                    <th class="sorting" name="DESCN">备注</th>
								                    <th>相关订单</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.statementNumber}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.freightPart.partName}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td id="${item.id}incomeOrExpense">${item.incomeOrExpense}</td>
								                    <td>${item.moneyCountRmb}</td>
								                    <td id="${item.id}eliminateCountRmb">${item.eliminateCountRmb}</td>
								                    <td id="${item.id}remainCountRmb">${item.remainCountRmb}</td>
								                    <td>${item.moneyCountDollar}</td>
								                    <td id="${item.id}eliminateCountDollar">${item.eliminateCountDollar}</td>
								                    <td id="${item.id}remainCountDollar">${item.remainCountDollar}</td>
								                    <td>${item.descn}</td>
								                    <td>
								                    	<c:set var="orderNumbers" value=""></c:set>
								                    	<c:forEach items="${item.freightExpenses}" var="freightExpense" varStatus="varStatus">
								                    	<c:if test="${freightExpense.freightOrder != null}">
								                    		<c:set var="orderNumbers" value="${freightExpense.freightOrder.orderNumber},${orderNumbers}"></c:set>
								                    	</c:if>
								                    	</c:forEach>
								                    	${orderNumbers}
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                </tr>
											</c:forEach>
								            </tbody>
										</table>
									</form>
								</article>
					      	</div>
					   </div>
						    <div class="m-page-info pull-left">
							  共100条记录 显示1到10条记录
							</div>
							<div class="btn-group m-pagination pull-right">
							  <button class="btn red">&lt;</button>
							  <button class="btn red">1</button>
							  <button class="btn red">&gt;</button>
							</div>
						    <div class="m-clear"></div>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 关联费用 -->
	<div class="modal fade" id="expenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">关联费用</h4>
			</div>
			<div class="modal-body">
				<!-- 对账单信息 -->
				<article class="m-widget" >
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				<article class="m-widget">
					<form id="expenseSearchForm" name="expenseSearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="DDH">订单号</label>
					    <input type="text" id="DDH" name="DDH" value="${param.DDH}" class="form-control input-xsmall">
					    
					    <label for="PM">品名</label>
					    <input type="text" id="PM" name="PM" value="${param.PM}" class="form-control input-xsmall">
					    
					    <label for="WTDW">委托单位</label>
					    <input type="text" id="WTDW" name="WTDW" value="${param.WTDW}" class="form-control input-xsmall">
					    
					    <label for="FYMC">费用名称</label>
					    <input type="text" id="FYMC" name="FYMC" value="${param.FYMC}" class="form-control input-xsmall">
					    
					    <label for="XH">箱号</label>
					    <input type="text" id="XH" name="XH" value="${param.XH}" class="form-control input-xsmall">
					    
					    <label for="XS">箱属</label>
					    <select id="XS" name="XS" class="form-control">
					    	<option value=""></option>
							<option value="MSK" <c:if test="${param.XS == 'MSK' }">selected</c:if>>MSK</option>
							<option value="MSC" <c:if test="${param.XS == 'MSC' }">selected</c:if>>MSC</option>
							<option value="CMA" <c:if test="${param.XS == 'CMA' }">selected</c:if>>CMA</option>
							<option value="HPL" <c:if test="${param.XS == 'HPL' }">selected</c:if>>HPL</option>
							<option value="HANJIN" <c:if test="${param.XS == 'HANJIN' }">selected</c:if>>HANJIN</option>
							<option value="OOCL" <c:if test="${param.XS == 'OOCL' }">selected</c:if>>OOCL</option>
							<option value="APL" <c:if test="${param.XS == 'APL' }">selected</c:if>>APL</option>
							<option value="NYK" <c:if test="${param.XS == 'NYK' }">selected</c:if>>NYK</option>
							<option value="TSL" <c:if test="${param.XS == 'TSL' }">selected</c:if>>TSL</option>
							<option value="MOL" <c:if test="${param.XS == 'MOL' }">selected</c:if>>MOL</option>
							<option value="MCC" <c:if test="${param.XS == 'MCC' }">selected</c:if>>MCC</option>
							<option value="ZIM" <c:if test="${param.XS == 'ZIM' }">selected</c:if>>ZIM</option>
							<option value="COSCO" <c:if test="${param.XS == 'COSCO' }">selected</c:if>>COSCO</option>
							<option value="CSCL" <c:if test="${param.XS == 'CSCL' }">selected</c:if>>CSCL</option>
							<option value="HMM" <c:if test="${param.XS == 'HMM' }">selected</c:if>>HMM</option>
							<option value="PIL" <c:if test="${param.XS == 'PIL' }">selected</c:if>>PIL</option>
							<option value="UASC" <c:if test="${param.XS == 'UASC' }">selected</c:if>>UASC</option>
							<option value="CCL" <c:if test="${param.XS == 'CCL' }">selected</c:if>>CCL</option>
							<option value="EMC" <c:if test="${param.XS == 'EMC' }">selected</c:if>>EMC</option>
							<option value="K-LINE" <c:if test="${param.XS == 'K-LINE' }">selected</c:if>>K-LINE</option>
							<option value="SINO-TRANS" <c:if test="${param.XS == 'SINO-TRANS' }">selected</c:if>>SINO-TRANS</option>
							<option value="SITC" <c:if test="${param.XS == 'SITC' }">selected</c:if>>SITC</option>
							<option value="YML" <c:if test="${param.XS == 'YML' }">selected</c:if>>YML</option>
							<option value="WHL" <c:if test="${param.XS == 'WHL' }">selected</c:if>>WHL</option>
							<option value="KMTC" <c:if test="${param.XS == 'KMTC' }">selected</c:if>>KMTC</option>
							<option value="海华" <c:if test="${param.XS == '海华' }">selected</c:if>>海华</option>
							<option value="德铭箱" <c:if test="${param.XS == '德铭箱' }">selected</c:if>>德铭箱</option>
							<option value="长航箱" <c:if test="${param.XS == '长航箱' }">selected</c:if>>长航箱</option>
							<option value="太平洋箱" <c:if test="${param.XS == '太平洋箱' }">selected</c:if>>太平洋箱</option>
							<option value="民生箱" <c:if test="${param.XS == '民生箱' }">selected</c:if>>民生箱</option>
							<option value="长锦" <c:if test="${param.XS == '长锦' }">selected</c:if>>长锦</option>
							<option value="中谷新良" <c:if test="${param.XS == '中谷新良' }">selected</c:if>>中谷新良</option>
							<option value="承达箱" <c:if test="${param.XS == '承达箱' }">selected</c:if>>承达箱</option>
						</select>
													
					    <label for="TDH">提单号</label>
					    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-xsmall">
					    
					    <!--  
					    <label for="CMHC">船名航次</label>
					    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control input-xsmall">
					    -->
					    
					    <label for="NB">NB</label>
					    <select id="NB" name="NB" class="form-control">
					    	<option ></option>
							<option value="F" <c:if test="${param.XS == 'F' }">selected</c:if>>否</option>
							<option value="T" <c:if test="${param.XS == 'T' }">selected</c:if>>是</option>
						</select>
					    
						<button class="btn btn-sm red" onclick="addExpense();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				<article class="m-widget">
					<form id="expenseBtnForm" name="expenseBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="doneAddExpense();">添加</button>
						<button class="btn btn-sm red" onclick="doneDeleteExpense();">删除</button>
						&nbsp;&nbsp;
						<button class="btn btn-sm green" onclick="toReviseExpense();">修改</button>
						<button class="btn btn-sm green" onclick="doneReviseExpense();">保存</button>
						<button class="btn btn-sm red" onclick="toggleExpense();" style="float:right;margin-right:20px;">切换</button>
						<span id="toggleInfo" style="float:right;margin-right:20px;font-size: 14px;font-weight: 600;"></span>
					</form>
				</article>
				
				<article class="m-widget" style="min-height:200px;max-height:330px; overflow-y: scroll;">
				<form id="expenseToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				<article class="m-widget" style="min-height:200px;max-height:330px; overflow-y: scroll;display:none;">
				<form id="expenseHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 开票任务 
	<div class="modal fade" id="invoiceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">开票任务</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" >
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget" >
				<form id="invoiceToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="invoiceBtnForm" name="invoiceBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#invoiceToAddForm').submit();">添加</button>
						<button class="btn btn-sm red" onclick="deleteInvoice();">删除</button>
					</form>
				</article>
				
				<article class="m-widget" style="max-height: 180px;overflow-y: scroll;">
				<form id="invoiceHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	-->
	<!-- 付款票号 -->
	<div class="modal fade" id="invoiceNumberModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加票号</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" >
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget" >
				<form id="invoiceNumberToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="invoiceNumberBtnForm" name="invoiceNumberBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#invoiceNumberToAddForm').submit();">添加</button>
						<button class="btn btn-sm red" onclick="doneDeleteNumber();">删除</button>
					</form>
				</article>
				
				<article class="m-widget" style="max-height: 180px;overflow-y: scroll;">
				<form id="invoiceNumberHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 申请开票 -->
	<div class="modal fade" id="applyReleaseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">申请开票</h4>
			</div>
			<div class="modal-body">
				<!-- 对账单信息 -->
				<article class="m-widget">
				<form id="statementInfoFormForApplyRelease" action="" method="post" class="m-form-blank"></form>
				</article>
				<!-- 添加说明 -->
				<article class="m-widget">
				<form id="applyReleaseForm" action="" method="post" class="m-form-blank">
					<input id="freightStatementId" name="freightStatementId" type="hidden">
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:200px;">
									<label class="td-label" for="descn">备注信息</label>
								</td>
								<td>
									<textarea id="descn" name="descn" style="min-height: 100px;" maxlength="256" class="form-control required"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#applyReleaseForm').submit();">确定</button>
			</div>
		</div>
	</div>
	
	<!-- 申请付款 -->
	<div class="modal fade" id="applyPayModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">申请付款</h4>
			</div>
			<div class="modal-body">
				<!-- 对账单信息 -->
				<article class="m-widget">
				<form id="statementInfoFormForApplyPay" action="" method="post" class="m-form-blank"></form>
				</article>
				<!-- 添加说明 -->
				<article class="m-widget">
				<form id="applyPayForm" action="" method="post" class="m-form-blank">
					<input id="freightStatementId" name="freightStatementId" type="hidden">
					<table class="table-input">
						<tbody>
							<tr>
								<td>
									<label class="td-label" for="releaseTime">发票开票时间</label>
								</td>
								<td>
									<input id="releaseTime" name="releaseTime"  class="form-control required datepicker">
								</td>
							</tr>
							<tr>
								<td style="width:150px;">
									<label class="td-label" for="descn">备注信息</label>
								</td>
								<td colspan="3">
									<textarea id="descn" name="descn" style="min-height: 100px;" maxlength="256" class="form-control required"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#applyPayForm').submit();">确定</button>
			</div>
		</div>
	</div>
	
	<!-- 账单冲抵-->
	<div class="modal fade" id="statementOffsetModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">账单冲抵</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
				<form id="statementOffsetToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="statementOffsetBtnForm" name="statementOffsetBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="doneStatementOffsetCareCurrency();">同币冲抵</button>
						<button class="btn btn-sm red" onclick="doneStatementOffsetIgnoreCurrency();">折后冲抵</button>
						<button class="btn btn-sm green" onclick="deleteStatementOffset();">取消冲抵</button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="statementOffsetHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
	$(function() {
	    App.init(); 
	});
	
	var config = {
	 	    id: 'dynamicGrid',
	 	    pageNo: ${pageView.pageNo},
	 	    pageSize: ${pageView.pageSize},
	 	    totalCount: ${pageView.totalCount},
	 	    resultSize: ${pageView.resultSize},
	 	    pageCount: ${pageView.pageCount},
	 	    orderBy: '${pageView.orderBy == null ? "" : pageView.orderBy}',
	 	    asc: ${pageView.asc},
	 	    params: {
	 	    	'partName': '${param.partName}',
	 	        'statementNumber': '${param.statementNumber}',
	 	        'orderNumber': '${param.orderNumber}',
	 	        'incomeOrExpense': '${param.incomeOrExpense}',
	 	        'status': '${param.status}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'fre-box-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	//点击模化窗口关闭按钮时刷新页面
	$(document).delegate('button', 'click', function(e){
		if($(this).attr('data-dismiss') == 'modal'){
			window.location.href = window.location.href;
		}
	});

/////////////////////费用
	$(document).delegate('#addExpense', 'click', function(e){
		if(addExpense()){
			var margin = (window.screen.availWidth - 1300)/2;
			$('#expenseModal').css("margin-left", margin);
			$('#expenseModal').css("width","1300px");
			$('#expenseModal').modal();
		}
	});
	
	function addExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#'+$('.selectedItem:checked').val()+'status').text();
			var eliminateCountRmb = new Number($('#'+$('.selectedItem:checked').val()+'eliminateCountRmb').text());
			var eliminateCountDollar = new Number($('#'+$('.selectedItem:checked').val()+'eliminateCountDollar').text());
			//审核中、未提交可以继续修改费用  || (status == '已审核' && eliminateCountRmb == 0 && eliminateCountDollar == 0)
			if(status == '未提交' || status == '审核中'){
				var freightStatementId = $('.selectedItem:checked').val();
				var url = 'fre-statement-to-add-expense-commerce.do?freightStatementId=' + freightStatementId;
				$.post(url, $('#expenseSearchForm').serialize(), function(data){
					var freightStatement = data.freightStatement;
					var toAddData = data.toAddData;//可选的费用
					var hasAddData = data.hasAddData;//已关联的费用
					
					var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>发票票种</th><th>金额(RMB)</th><th>已开票(RMB)</th><th>未开票(RMB)</th><th>金额(USD)</th><th>已开票(USD)</th><th>未开票(USD)</th></tr></thead>';
					html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
					html += '<td>'+freightStatement.freightPart.partName+'</td>';
					html += '<td>'+freightStatement.incomeOrExpense+'</td>';
					html += '<td>'+freightStatement.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightStatement.moneyCountRmb+'</td>';
					html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
					html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
					html += '<td>'+freightStatement.moneyCountDollar+'</td>';
					html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
					html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
					html += '</tr></tbody></table>';
					$('#expenseModal #statementInfoForm').html(html);
					
					var htmlList = '<input id="freightStatementId" name="freightStatementId" type="hidden" value="'+ freightStatementId +'">';
					htmlList += '<table  class="m-table table-bordered table-hover">';
					htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					htmlList += '<th>费用编号</th><th>费用名称</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>实际单价</th><th>实际总额</th><th>提单号</th><th>箱号</th>';
					htmlList += '</tr></thead><tbody>';
					var orderNumber = '';//根据orderNumber统计小计
					var amountRmb = 0;
					var amountDollar = 0;
					$.each(toAddData, function(i, item){
						if(orderNumber == ''){
							orderNumber = item.expenseNumber.substr(0, 17);
							amountRmb = 0;
							amountDollar = 0;
							if(item.currency == '人民币'){
								amountRmb += item.actualAmount;
							}else if(item.currency == '美元'){
								amountDollar += item.actualAmount;
							}
						}else{
							if(item.expenseNumber.indexOf(orderNumber) >= 0){
								if(item.currency == '人民币'){
									amountRmb += item.actualAmount;
								}else if(item.currency == '美元'){
									amountDollar += item.actualAmount;
								}
							}else{//添加统计列
								htmlList += '<tr style="background-color: #FAEBD7"><td colspan="5" align="right">'+ orderNumber.substr(0, 14) +'</td><td>小计: </td><td>人民币</td><td>' + amountRmb +'</td><td></td><td>' + amountDollar + '</td><td colspan="2"></td></tr>';
								orderNumber = item.expenseNumber.substr(0, 17);
								amountRmb = 0;
								amountDollar = 0;
								if(item.currency == '人民币'){
									amountRmb += item.actualAmount;
								}else if(item.currency == '美元'){
									amountDollar += item.actualAmount;
								}
							}
						}
						
						if(item.freightPrice != null && item.freightPrice.actual != null && (item.freightPrice.actual == 'T' || item.freightPrice.actual == 'F')){
							htmlList += '<tr style="border-bottom:2px solid green;"><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						}else{
							htmlList += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						}
						htmlList += '<td>' + item.expenseNumber + '</td>';
						htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
						htmlList += '<td>' + item.countUnit + '</td>';
						htmlList += '<td>' + item.exchangeRate + '</td>';
						htmlList += '<td>' + item.currency + '</td>';
						htmlList += '<td>' + item.predictCount + '</td>';
						htmlList += '<td>' + item.predictAmount + '</td>';
						htmlList += '<td id="'+ item.id +'TD">' + item.actualCount + '</td>';
						htmlList += '<td>' + item.actualAmount + '</td>';
						htmlList += '<td>' + item.blNumber + '</td>';
						htmlList += '<td>' + item.boxNumber + '</td></tr>';
					});
					//末尾添加统计列
					htmlList += '<tr style="background-color: #FAEBD7"><td colspan="5" align="right">'+ orderNumber.substr(0, 14) +'</td><td>小计: </td><td>人民币</td><td>' + amountRmb +'</td><td>美元</td><td>' + amountDollar + '</td><td colspan="2"></td></tr>';
					htmlList += "</tbody></table>";
					$('#expenseToAddForm').html(htmlList);
					
					htmlList = '';//重置
					htmlList += '<table  class="m-table table-bordered table-hover">';
					htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					htmlList += '<th>费用编号</th><th>费用名称</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>实际单价</th><th>实际总额</th><th>提单号</th><th>箱号</th>';
					htmlList += '</tr></thead><tbody>';
					orderNumber = '';//根据orderNumber统计小计
					amountRmb = 0;
					amountDollar = 0;
					$.each(hasAddData, function(i, item){
						if(orderNumber == ''){
							orderNumber = item.expenseNumber.substr(0, 17);
							amountRmb = 0;
							amountDollar = 0;
							if(item.currency == '人民币'){
								amountRmb += item.actualAmount;
							}else if(item.currency == '美元'){
								amountDollar += item.actualAmount;
							}
						}else{
							if(item.expenseNumber.indexOf(orderNumber) >= 0){
								if(item.currency == '人民币'){
									amountRmb += item.actualAmount;
								}else if(item.currency == '美元'){
									amountDollar += item.actualAmount;
								}
							}else{//添加统计列
								htmlList += '<tr style="background-color: #FAEBD7"><td colspan="5" align="right">'+ orderNumber.substr(0, 14) +'</td><td>小计: </td><td>人民币</td><td>' + amountRmb +'</td><td></td><td>' + amountDollar + '</td><td colspan="2"></td></tr>';
								orderNumber = item.expenseNumber.substr(0, 17);
								amountRmb = 0;
								amountDollar = 0;
								if(item.currency == '人民币'){
									amountRmb += item.actualAmount;
								}else if(item.currency == '美元'){
									amountDollar += item.actualAmount;
								}
							}
						}
						
						if(item.freightPrice != null && item.freightPrice.actual != null && (item.freightPrice.actual == 'T' || item.freightPrice.actual == 'F')){
							htmlList += '<tr style="border-bottom:2px solid green;"><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						}else{
							htmlList += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						}
						htmlList += '<td>' + item.expenseNumber + '</td>';
						htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
						htmlList += '<td>' + item.countUnit + '</td>';
						htmlList += '<td>' + item.exchangeRate + '</td>';
						htmlList += '<td>' + item.currency + '</td>';
						htmlList += '<td>' + item.predictCount + '</td>';
						htmlList += '<td>' + item.predictAmount + '</td>';
						htmlList += '<td>' + item.actualCount + '</td>';
						htmlList += '<td>' + item.actualAmount + '</td>';
						htmlList += '<td>' + item.blNumber + '</td>';
						htmlList += '<td>' + item.boxNumber + '</td></tr>';
					});
					//末尾添加统计列
					htmlList += '<tr style="background-color: #FAEBD7"><td colspan="5" align="right">'+ orderNumber.substr(0, 14) +'</td><td>小计: </td><td>人民币</td><td>' + amountRmb +'</td><td>美元</td><td>' + amountDollar + '</td><td colspan="2"></td></tr>';
					htmlList += "</tbody></table>";
					$('#expenseHasAddForm').html(htmlList);
					alert('查询数据成功！');
				});
				return true;
			}else{
				alert(status + '状态中的账单不能添加费用！');
				return false;
			}
		}
	}
	
	
	//费用界面切换
	function toggleExpense(){
		$('#expenseModal #expenseToAddForm').parent().toggle();
		$('#expenseModal #expenseHasAddForm').parent().toggle(); 
		if(($('#expenseModal #expenseToAddForm').parent().css('display') == 'none')){ 
			$('#expenseModal #expenseBtnForm #toggleInfo').text('已添加费用')
		}else{
			$('#expenseModal #expenseBtnForm #toggleInfo').text('可添加费用')
		}
	}
	$(document).delegate('#viewExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-expense-get-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '关联费用', 1300,
					['编号', '费用名称','收/付','收付单位','票/箱','汇率','币种','预计单价','预计总额','实际单价','实际总额','状态'], 
					['expenseNumber', 'freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','predictAmount','actualCount','actualAmount','status']
			);
		}
	});
	
	/////////////////////开票
	/* $(document).delegate('#addInvoice', 'click', function(e){
		addInvoice();
	});
	
	function addInvoice(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#' + $('.selectedItem:checked').val() + 'status').text();
			var incomeOrExpense = $('#' + $('.selectedItem:checked').val() + 'incomeOrExpense').text();
			
			var remainCountRmb = new Number($('#'+$('.selectedItem:checked').val()+'remainCountRmb').text());
			var remainCountDollar = new Number($('#'+$('.selectedItem:checked').val()+'remainCountDollar').text());
			if((remainCountRmb == 0 && remainCountDollar == 0) || status != '待开票' || incomeOrExpense != '收'){
				alert('已开票完毕或' + status + '状态中的账单不能添加开票任务！');
				return false;
			}else{
				var freightStatementId = $('.selectedItem:checked').val();
				var url = 'fre-invoice-to-invoice.do?freightStatementId=' + freightStatementId;
				$.post(url, function(data){
					var freightStatement = data.freightStatement;//对账单信息
					if(freightStatement.status == '异常锁定'){
						alert('异常锁定中的账单不能做任何操作！');
						return false;
					}
					var hasAddInvoice = data.hasAddInvoice;//已关联的费用
					
					var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>发票票种</th><th>金额(RMB)</th><th>已开票(RMB)</th><th>未开票(RMB)</th><th>金额(USD)</th><th>已开票(USD)</th><th>未开票(USD)</th></tr></thead>';
					html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
					html += '<td>'+freightStatement.freightPart.partName+'</td>';
					html += '<td>'+freightStatement.incomeOrExpense+'</td>';
					html += '<td>'+freightStatement.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightStatement.moneyCountRmb+'</td>';
					html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
					html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
					html += '<td>'+freightStatement.moneyCountDollar+'</td>';
					html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
					html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
					//html += '<td>'+freightStatement.descn+'</td>';
					//暂不添加摘要信息
					html += '</tr><tr><th>备注信息</th><td colspan="9"><textarea  style="min-height:60px;width:98%;" readonly>'+freightStatement.descn+'</textarea></td></tr></tbody></table>';
					//html += '</tr></tbody></table>';
					$('#invoiceModal #statementInfoForm').html(html);
					
					html = '<input id="freightStatementId" type="hidden" value="' + freightStatementId + '">';
					html += '<input id="freightPartId" type="hidden" value="' + freightStatement.freightPart.id + '">';
					html += '<input id="fasInvoiceTypeId" type="hidden" value="' + freightStatement.fasInvoiceType.id + '">';
					html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>开票单位</th><th>发票种类</th><th>币种</th><th>金额</th><th>摘要</th></tr></thead><tbody>';
					//开票单位根据对账单单位
					html += '<tr>';
					html += '<td>' + freightStatement.freightPart.partName + '</td>';
					html += '<td>' + freightStatement.fasInvoiceType.typeName + '</td>';
					html += '<td><select id="currency" name="currency" class="form-control required" onchange="updateCurrency();"><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
					html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" minlength="2" maxlength="7"></td>';
					html += '<td><input id="descn" name="descn" value="" class="form-control required" ></td>';
					html += '</tr>'
					html += "</tbody></table>";
					$('#invoiceToAddForm').html(html);
					//自动将金额加载
					var remainCountRmb = new Number($('#statementInfoTable #remainCountRmb').text());
					if(remainCountRmb <= 1000000){
						$('#invoiceToAddForm #moneyCount').val(remainCountRmb);
					}else{
						$('#invoiceToAddForm #moneyCount').val(1000000);
					}
					
					html = '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsInvoice(this.checked)"/></th>';
					html += '<th>开票单位</th><th>收/付</th><th>发票种类</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th>';
					html += '</tr></thead><tbody>';
					$.each(hasAddInvoice, function(i, item){
						html += '<tr><td><input class="selectedItemInvoice a-check" type="checkbox" name="invoiceIds" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.descn + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#invoiceHasAddForm').html(html);
					
					var margin = (window.screen.availWidth - 1200)/2;
					$('#invoiceModal').css("margin-left", margin);
					$('#invoiceModal').css("width","1200px");
					$('#invoiceModal').modal();
				});
			}
		}
		return true;
	}
	//根据币种不同，自动将对应金额加载到开票金额中
	function updateCurrency(){
		var remainCountRmb = new Number($('#statementInfoTable #remainCountRmb').text());
		var remainCountDollar = new Number($('#statementInfoTable #remainCountDollar').text());
		var currency = $('#invoiceToAddForm #currency').val();
		if(currency == '人民币'){
			if(remainCountRmb <= 1000000){
				$('#invoiceToAddForm #moneyCount').val(remainCountRmb);
			}else{
				$('#invoiceToAddForm #moneyCount').val(1000000);
			}
		}else if(currency == '美元'){
			if(remainCountDollar <= 1000000){
				$('#invoiceToAddForm #moneyCount').val(remainCountDollar);
			}else{
				$('#invoiceToAddForm #moneyCount').val(1000000);
			}
		}
	} */
	
	$(document).delegate('#viewInvoice', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-invoice-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '关联发票', 1000,
					['发票号','票种','单位','收/付','汇率','币种','金额','状态','开票时间'], 
					['fasInvoice.invoiceNumber','fasInvoiceType.typeName', 'freightPart.partName' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount','status','releaseTime']
			);
		}
	});
	
/* 	function toggleSelectedItemsInvoice(isChecked) {
		$(".selectedItemInvoice").each(function(index, el) {
			$(el).prop("checked", isChecked);
			if ($(el).parent()[0].tagName != 'SPAN' && $(el).parent()[0].tagName != 'span') {
				return;
			}
			if (isChecked) {
				$(el).parent().addClass("checked");
			} else {
				$(el).parent().removeClass("checked");
			}
		});
	} */
	
	//提交费用
	function doneAddExpense(){
		if($('#expenseToAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return;
		}else{
			var freightStatementId = $('#expenseToAddForm #freightStatementId').val();
			var url = 'fre-statement-done-add-expense.do?freightStatementId=' + freightStatementId;
			$('#expenseToAddForm .selectedItemId:checked').each(function(i, item){
				url += '&freightExpenseId=' + $(item).val();
			});
			$.post(url, function(data){
				if(data == 'success'){
					addExpense();
				}
			});
		}
	}
	
	function doneDeleteExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return;
		}else{
			var url = 'fre-statement-done-delete-expense.do?';
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightExpenseId=' + $(item).val();
				}else{
					url += '&freightExpenseId=' + $(item).val();
				}
			});
			$.post(url, function(data){
				if(data == 'success'){
					addExpense();
				}
			});
		}
	}
	
	//对实际费用进行修改	
	function toReviseExpense(){
		if($('#expenseToAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightExpenseId = $('#expenseToAddForm .selectedItemId:checked').val();
			var actualCount = $('#expenseToAddForm #' + freightExpenseId + 'TD').text();
			$('#expenseToAddForm #' + freightExpenseId + 'TD').html('<input id="'+ freightExpenseId +'actualCount" name="actualCount" value="' + actualCount + '" class="form-control required number">');
		}
	}
	
	//对实际费用进行修改
	function doneReviseExpense(){
		if($('#expenseToAddForm .selectedItemId:checked').length == 1 && $('#expenseToAddForm').valid()){
			var freightExpenseId = $('#expenseToAddForm .selectedItemId:checked').val();
        	var actualCount = $('#expenseToAddForm #' + freightExpenseId + 'actualCount').val();
        	if(actualCount != undefined){
        		var url = 'fre-statement-done-revise-expense.do?freightExpenseId=' + freightExpenseId + '&actualCount=' + actualCount;
            	$.post(url, function(data){
    				if(data == 'success'){
    					addExpense();
    				}else{
    					alert('修改失败！');
    				}
    			});
        	}else{
        		alert('修改失败！');
        	}
		}else{
			alert('修改失败！');
		}
	}
	
	//拼接成json数据类型
	function toJsonString(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '{';
		$.each(fields, function(i, item){
				if(i == 0){
   				data += '"' + item.name + '":"' + item.value + '"';
   			}else{
   				data += ',"' + item.name + '":"' + item.value + '"';
   			}
		});
		data += '}';
		
		return data;
	}
	
	//申请开票，确认账单信息，并填写备注；此处不采取批量，因为需要填写说明。
	$(document).delegate('#applyInvoiceBtn', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据再申请开票');
			return false;
		}else{
			var status = $('#' + $('.selectedItem:checked').val() + 'status').text();
			var incomeOrExpense = $('#' + $('.selectedItem:checked').val() + 'incomeOrExpense').text();
			var remainCountRmb = new Number($('#' + $('.selectedItem:checked').val() + 'remainCountRmb').text());
			var remainCountDollar = new Number($('#' + $('.selectedItem:checked').val() + 'remainCountDollar').text());
			
			if((remainCountRmb == 0 && remainCountDollar == 0) || (status !='冲抵过' && status != '未提交') || incomeOrExpense != '收'){
				alert('已冲抵或已开票或' + status + '状态中的账单不能申请开票任务！');
				return false;
			}else{
				$.post('fre-statement-prepare-release.do?freightStatementId=' + $('.selectedItem:checked').val(), function(data){
					if(data.status == '异常锁定'){
						alert('异常锁定中的账单不能做任何操作！');
						return false;
					}
					//收款单才存在申请开票
					if(data.incomeOrExpense=='收' && (data.status == '未提交' || data.status == '冲抵过' || data.status == '财务退回') 
							&& (data.remainCountRmb == data.moneyCountRmb || data.remainCountDollar == data.moneyCountDollar)
							&& (data.moneyCountRmb != 0 || data.moneyCountDollar != 0)){
						
						var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
						html += '<thead><tr>';
						html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>金额(RMB)</th><th>已开票金额(RMB)</th><th>未开票金额(RMB)</th><th>金额(USD)</th><th>已开票金额(USD)</th><th>未开票金额(USD)</th><th>摘要</th></tr></thead>';
						html += '<tbody><tr><td>'+data.statementNumber+'</td>';
						html += '<td>'+data.freightPart.partName+'</td>';
						html += '<td>'+data.incomeOrExpense+'</td>';
						html += '<td>'+data.moneyCountRmb+'</td>';
						html += '<td>'+data.eliminateCountRmb+'</td>';
						html += '<td>'+data.remainCountRmb+'</td>';
						html += '<td>'+data.moneyCountDollar+'</td>';
						html += '<td>'+data.eliminateCountDollar+'</td>';
						html += '<td>'+data.remainCountDollar+'</td>';
						html += '<td>'+data.descn+'</td>';
						html += '</tr></tbody></table>';
						$('#statementInfoFormForApplyRelease').html(html);
						$('#applyReleaseForm #freightStatementId').val(data.id);
						
						var margin = (window.screen.availWidth - 1200)/2;
						$('#applyReleaseModal').css("margin-left", margin);
						$('#applyReleaseModal').css("width","1200px");
						$('#applyReleaseModal').modal();
					}else{
						alert('此账单不能申请开票，请确认账单状态和是否已添加费用！');
						return false;
					}
				});
			}
		}
	});
	//保存申请开票说明
	$(function() {
		$("#applyReleaseForm").validate({
	        submitHandler: function(form) {
	    		$.post('fre-statement-to-release.do', $('#applyReleaseForm').serialize(), function(data){
	        		if(data == 'success' ){
	        			alert('申请成功！');
	        			$('#applyReleaseModal').modal('hide');
	        			window.location.href = window.location.href;
	        		}else{
	        			alert('申请失败！');
	        		}
	        	});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//作废账单
	$(document).delegate('#invalidStatement', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-statement-invalid-statement.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '未提交'){
					flag = true;
				}else{
					url += 'freightStatementId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				if(window.confirm('确定要作废该订单吗？')){
					url = url.substring(0, url.length - 1);
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败！请确认所选账单状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败！请确认所选账单状态！');
			}
		}
	});
	
	//付款发票号填写
	$(document).delegate('#toAddNumber', 'click', function(e){
		toAddNumber();
	});
	
	function toAddNumber(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#' + $('.selectedItem:checked').val() + 'status').text();
			var incomeOrExpense = $('#' + $('.selectedItem:checked').val() + 'incomeOrExpense').text();
			var remainCountRmb = new Number($('#' + $('.selectedItem:checked').val() + 'remainCountRmb').text());
			var remainCountDollar = new Number($('#' + $('.selectedItem:checked').val() + 'remainCountDollar').text());
			//此处取消金额的限制，只要是已审核就可以修改发票。
			//if((remainCountRmb == 0 && remainCountDollar == 0) || status != '已审核' || incomeOrExpense != '付'){
			if((status != '已审核' && status != '冲抵过')  || incomeOrExpense != '付'){//对于冲抵过的账单处理
				alert('已填写完毕或' + status + '状态中的账单不能添加发票！');
				return false;
			}else{
				var freightStatementId = $('.selectedItem:checked').val();
				var url = 'fre-invoice-to-add-number.do?freightStatementId=' + freightStatementId;
				$.post(url, function(data){
					var freightStatement = data.freightStatement;//对账单信息
					if(freightStatement.status == '异常锁定'){
						alert('异常锁定中的账单不能做任何操作！');
						return false;
					}
					var hasAddInvoice = data.hasAddInvoice;//已关联的费用
					
					var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>发票票种</th><th>金额(RMB)</th><th>已开票(RMB)</th><th>未开票(RMB)</th><th>金额(USD)</th><th>已开票(USD)</th><th>未开票(USD)</th></tr></thead>';
					html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
					html += '<td>'+freightStatement.freightPart.partName+'</td>';
					html += '<td>'+freightStatement.incomeOrExpense+'</td>';
					html += '<td>'+freightStatement.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightStatement.moneyCountRmb+'</td>';
					html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
					html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
					html += '<td>'+freightStatement.moneyCountDollar+'</td>';
					html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
					html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
					html += '</tr><tr><th>备注信息</th><td colspan="9"><textarea  style="min-height:60px;width:98%;" readonly>'+freightStatement.descn+'</textarea></td></tr></tbody></table>';
					$('#invoiceNumberModal #statementInfoForm').html(html);
					
					html = '<input id="freightStatementId" type="hidden" value="' + freightStatementId + '">';
					html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>开票单位</th><th>票种</th><th>发票号</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th></tr></thead><tbody>';
					//开票单位根据对账单单位
					html += '<tr>';
					html += '<td>' + freightStatement.freightPart.partName + '</td>';
					html += '<td>' + freightStatement.fasInvoiceType.typeName + '</td>';
					html += '<td><input id="fasInvoiceNumber" value="" class="form-control" minlength="5" placeholder="不填写自动添加虚拟发票号"></td>';
					html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
					html += '<td><input id="exchangeRate" name="exchangeRate" value="" class="form-control number" minlength="1" maxlength="6" placeholder="不填写自动添加默认汇率"></td>';
					html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" minlength="1" maxlength="10"></td>';
					html += '<td><input id="descn" name="descn" value="" class="form-control required" ></td>';
					html += '</tr>'
					html += "</tbody></table>";
					$('#invoiceNumberToAddForm').html(html);
					
					html = '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>开票单位</th><th>收/付</th><th>发票种类</th><th>发票号</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th>';
					html += '</tr></thead><tbody>';
					$.each(hasAddInvoice, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.descn + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#invoiceNumberHasAddForm').html(html);
					
					var margin = (window.screen.availWidth - 1200)/2;
					$('#invoiceNumberModal').css("margin-left", margin);
					$('#invoiceNumberModal').css("width","1200px");
					$('#invoiceNumberModal').modal();
				});
			}
		}
		return true;
	}
	
	//删除发票号
	function doneDeleteNumber(){
		if($('#invoiceNumberHasAddForm .selectedItemId:checked')){
			var url = 'fre-invoice-done-delete-number.do?';
			$('#invoiceNumberHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightInvoiceId=' + $(item).val();
				}else{
					url += '&freightInvoiceId=' + $(item).val();
				}
			});
			
			$.post(url, function(data){
				if(data == 'success'){
					toAddNumber();
				}
			});
		}else{
			alert('请选择数据！');
		}
	}
	
	//提交发票信息
	$(function() {
		$("#invoiceNumberToAddForm").validate({
	        submitHandler: function(form) {
				var freightStatementId = $('#invoiceNumberToAddForm #freightStatementId').val();
				var fasInvoiceNumber = $('#invoiceNumberToAddForm #fasInvoiceNumber').val();
				if(freightStatementId == undefined || freightStatementId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				//填报金额
				if(($('#invoiceNumberToAddForm #moneyCount').val() == 0 || $('#invoiceNumberToAddForm #moneyCount').val() < 0)|| 
						($('#invoiceNumberToAddForm #currency').val() == '人民币' && $('#invoiceNumberToAddForm #moneyCount').val() > new Number($('#invoiceNumberModal #statementInfoForm #remainCountRmb').text()))
						|| ($('#invoiceNumberToAddForm #currency').val() == '美元' && $('#invoiceNumberToAddForm #moneyCount').val() > new Number($('#invoiceNumberModal #statementInfoForm #remainCountDollar').text()))){
					alert('开票金额不能大于未开票金额及不能为小于或等于0！');
					return false;
				}else{
					var data = toJsonString('invoiceNumberToAddForm');
					var url = 'fre-invoice-done-add-number.do?freightStatementId=' + freightStatementId
							+ '&fasInvoiceNumber=' + fasInvoiceNumber;
					$.ajax({
		    			type: 'POST',
		    			data: data,
		    			url: url,
		    			contentType: 'application/json',
		    			success:function(data){
		    				toAddNumber();
		    			}
		    		});
				}
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//申请付款，确认账单信息，并填写备注；此处不采取批量，因为需要填写说明。此处备注信息和时间应该是相应开票的。
	$(document).delegate('#applyPayBtn', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据再申请开票');
			return false;
		}else{
			var status = $('#' + $('.selectedItem:checked').val() + 'status').text();
			var incomeOrExpense = $('#' + $('.selectedItem:checked').val() + 'incomeOrExpense').text();
			if((status != '已审核' && status != '冲抵过') || incomeOrExpense != '付'){
				alert(status + '状态中的账单不能申请付款！');
				return false;
			}else{
				$.post('fre-statement-prepare-pay.do?freightStatementId=' + $('.selectedItem:checked').val(), function(data){
					if(data.status == '异常锁定'){
						alert('异常锁定中的账单不能做任何操作！');
						return false;
					}
					//申请付款，为付，状态为已审核，未开票金额都为0.
					if(data.incomeOrExpense=='付' 
							&& (data.status == '已审核' || data.status == '冲抵过')//新增冲抵账单的处理
							&& data.remainCountRmb == 0 && data.remainCountDollar == 0
							&& (data.moneyCountRmb != 0 || data.moneyCountDollar != 0)){
						
						var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
						html += '<thead><tr>';
						html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>金额(RMB)</th><th>已开票金额(RMB)</th><th>未开票金额(RMB)</th><th>金额(USD)</th><th>已开票金额(USD)</th><th>未开票金额(USD)</th><th>摘要</th></tr></thead>';
						html += '<tbody><tr><td>'+data.statementNumber+'</td>';
						html += '<td>'+data.freightPart.partName+'</td>';
						html += '<td>'+data.incomeOrExpense+'</td>';
						html += '<td>'+data.moneyCountRmb+'</td>';
						html += '<td>'+data.eliminateCountRmb+'</td>';
						html += '<td>'+data.remainCountRmb+'</td>';
						html += '<td>'+data.moneyCountDollar+'</td>';
						html += '<td>'+data.eliminateCountDollar+'</td>';
						html += '<td>'+data.remainCountDollar+'</td>';
						html += '<td>'+data.descn+'</td>';
						html += '</tr></tbody></table>';
						$('#statementInfoFormForApplyPay').html(html);
						$('#applyPayForm #freightStatementId').val(data.id);
						
						var margin = (window.screen.availWidth - 1200)/2;
						$('#applyPayModal').css("margin-left", margin);
						$('#applyPayModal').css("width","1200px");
						$('#applyPayModal').modal();
					}else{
						alert('此账单不能申请付款，请确认账单状态和是否还有未开票的金额！');
						return false;
					}
				});
			}
		}
	});
	
	//保存申请付款说明
	$(function() {
		$("#applyPayForm").validate({
	        submitHandler: function(form) {
	        	$('#applyPayModal').modal('hide');
	        	//bootbox.animate(false);
	    		//var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
	    		$.post('fre-statement-to-pay.do', $('#applyPayForm').serialize(), function(data){
	        		if(data == 'success' ){
	        			alert('申请成功！');
	        			window.location.href = window.location.href;
	        		}
	        	});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//账单冲抵///////////////////////////////////////////////////////////////////////
	$(document).delegate('#statementOffset', 'click', function(e){
		statementOffset();
	});
	
	function statementOffset(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#'+$('.selectedItem:checked').val()+'status').text();
			var eliminateCountRmb = new Number($('#'+$('.selectedItem:checked').val()+'eliminateCountRmb').text());
			var eliminateCountDollar = new Number($('#'+$('.selectedItem:checked').val()+'eliminateCountDollar').text());
			//非已开票的才可冲抵
			if((eliminateCountRmb != 0 || eliminateCountDollar != 0 || status != '冲抵过') && status != '未提交' && status != '冲抵过'){
				alert('已开过票或' + status + '状态中的账单不能冲抵！');
				return false;
			}else{
				var freightStatementId = $('.selectedItem:checked').val();
				var url = 'fre-statement-to-statement-offset.do?freightStatementId=' + freightStatementId;
				$.post(url, function(data){
					var toOffsetStatement = data.toOffsetStatement;//可冲抵账单
					var hasOffsetStatement = data.hasOffsetStatement;//已冲抵账单
					var freightStatement = data.freightStatement;//账单信息
					
					var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>账单号</th><th>收付单位</th><th>收/付</th><th>发票票种</th><th>金额(RMB)</th><th>已开票(RMB)</th><th>未开票(RMB)</th><th>金额(USD)</th><th>已开票(USD)</th><th>未开票(USD)</th></tr></thead>';
					html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
					html += '<td>'+freightStatement.freightPart.partName+'</td>';
					html += '<td>'+freightStatement.incomeOrExpense+'</td>';
					html += '<td>'+freightStatement.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightStatement.moneyCountRmb+'</td>';
					html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
					html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
					html += '<td>'+freightStatement.moneyCountDollar+'</td>';
					html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
					html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
					///html += '</tr><tr><th>备注信息</th><td colspan="9"><textarea  style="min-height:60px;width:98%;" readonly>'+freightStatement.descn+'</textarea></td></tr></tbody></table>';
					html += '</tr></tbody></table>';
					$('#statementOffsetModal #statementInfoForm').html(html);
					
					html = '<input id="sourceStatementId" type="hidden" value="' + freightStatementId + '">';
					html += '<table id="statementTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr><th></th>';
					html += '<th>账单号</th><th>收/付</th><th>收付单位</th><th>金额(RMB)</th><th>已开票金额(RMB)</th><th>未开票金额(RMB)</th><th>金额(USD)</th><th>已开票金额(USD)</th><th>未开票金额(USD)</th>';
					html += '</tr></thead><tbody>';
					$.each(toOffsetStatement, function(i, item){
						html += '<tr><td><input class="selectedItemStatement a-check" type="radio" name="freightStatementId" value="'+item.id+'" /></td>';
						html += '<td>' + item.statementNumber + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPart == null ? '空':item.freightPart.partName) + '</td>';
						html += '<td>' + item.moneyCountRmb + '</td>';
						html += '<td>' + item.eliminateCountRmb + '</td>';
						html += '<td>' + item.remainCountRmb + '</td>';
						html += '<td>' + item.moneyCountDollar + '</td>';
						html += '<td>' + item.eliminateCountDollar + '</td>';
						html += '<td>' + item.remainCountDollar + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#statementOffsetToAddForm').html(html);
					
					html = '<table id="statementTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr><th></th>';
					html += '<th>账单号</th><th>收/付</th><th>收付单位</th><th>金额(RMB)</th><th>已开票金额(RMB)</th><th>未开票金额(RMB)</th><th>金额(USD)</th><th>已开票金额(USD)</th><th>未开票金额(USD)</th>';
					html += '</tr></thead><tbody>';
					$.each(hasOffsetStatement, function(i, item){
						html += '<tr><td><input class="selectedItemStatementToDelete a-check" type="radio" name="freightStatementId" value="'+item.id+'" /></td>';
						html += '<td>' + item.statementNumber + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPart == null ? '空':item.freightPart.partName) + '</td>';
						html += '<td>' + item.moneyCountRmb + '</td>';
						html += '<td>' + item.eliminateCountRmb + '</td>';
						html += '<td>' + item.remainCountRmb + '</td>';
						html += '<td>' + item.moneyCountDollar + '</td>';
						html += '<td>' + item.eliminateCountDollar + '</td>';
						html += '<td>' + item.remainCountDollar + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#statementOffsetHasAddForm').html(html);
					
					var margin = (window.screen.availWidth - 1200)/2;
					$('#statementOffsetModal').css("margin-left", margin);
					$('#statementOffsetModal').css("width","1200px");
					$('#statementOffsetModal').modal();
				});
			}
			return true;
		}
	}
	
	function doneStatementOffsetCareCurrency(){
		var sourceStatementId = $('#statementOffsetToAddForm #sourceStatementId').val();
		var targetStatementId = $('#statementOffsetToAddForm .selectedItemStatement:checked').val();
		if(sourceStatementId == '' || targetStatementId == ''
			|| sourceStatementId == undefined || targetStatementId == undefined){
			alert('请选择需要冲抵的账单！')
			return;
		}else{
			$.post('fre-statement-done-statement-offset-care-currency.do?sourceStatementId=' 
					+　sourceStatementId + '&targetStatementId=' + targetStatementId, function(data){
				if(data == 'success'){
					statementOffset();
				}else{
					alert('冲抵账单失败。');
				}
				
			});
		}
	}
	
	function doneStatementOffsetIgnoreCurrency(){
		var sourceStatementId = $('#statementOffsetToAddForm #sourceStatementId').val();
		var targetStatementId = $('#statementOffsetToAddForm .selectedItemStatement:checked').val();
		if(sourceStatementId == '' || targetStatementId == ''
			|| sourceStatementId == undefined || targetStatementId == undefined){
			alert('请选择需要冲抵的账单！')
			return;
		}else{
			$.post('fre-statement-done-statement-offset-ignore-currency.do?sourceStatementId=' 
					+　sourceStatementId + '&targetStatementId=' + targetStatementId, function(data){
				if(data == 'success'){
					statementOffset();
				}else{
					alert('冲抵账单失败。');
				}
				
			});
		}
	}
	
	//取消冲抵
	function deleteStatementOffset(){
		var sourceStatementId = $('#statementOffsetToAddForm #sourceStatementId').val();
		var targetStatementId = $('#statementOffsetHasAddForm .selectedItemStatementToDelete:checked').val();
		if(sourceStatementId == '' || targetStatementId == '' 
				|| sourceStatementId == undefined || targetStatementId == undefined){
			alert('请选择需要取消冲抵的账单！')
			return;
		}else{
			if(window.confirm('确认取消冲抵销账吗？')){
				$.post('fre-statement-delete-statement-offset.do?sourceStatementId=' 
						+　sourceStatementId + '&targetStatementId=' + targetStatementId, function(data){
					if(data == 'success'){
						statementOffset();
					}else{
						alert('取消冲抵失败，请确认是否已开票！');
					}
				});
			}
		}
	}
	//提交复核
	$(document).delegate('#toStatementAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-statement-to-statement-audit.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var incomeOrExpense = $('#' + $(item).val() + 'incomeOrExpense').text();
				if(incomeOrExpense == '收'){
					flag = false;
					return false;
				}
				if(status != '未提交' && status != '已退回'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightStatementId=' +　$(item).val();
					}else{
						url += '&freightStatementId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选账单状态及是否为付款账单！');
				return;
			}
		}
	});
	//取消复核
	$(document).delegate('#doneRecallStatement', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-statement-done-recall-statement.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var incomeOrExpense = $('#' + $(item).val() + 'incomeOrExpense').text();
				var eliminateCountRmb = new Number($('#' + $(item).val() + 'eliminateCountRmb').text());
				var eliminateCountDollar = new Number($('#' + $(item).val() + 'eliminateCountDollar').text());
				if(incomeOrExpense == '收'){
					flag = false;
					return false;
				}
				if(status != '已审核' || eliminateCountRmb != 0 || eliminateCountDollar != 0){//取消复核时，需要没有添加任何发票
					flag = false;
				}else{
					if(i == 0){
						url += 'freightStatementId=' +　$(item).val();
					}else{
						url += '&freightStatementId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('取消失败！');
					}else{
						alert('取消成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('取消失败！请确认所选账单状态及是否为付款账单！');
				return;
			}
		}
	});
	
	
	//判断是否可删除
	function canRemove(){
		var flag = true;
		$('.selectedItem:checked').each(function(i, item){
			var status = $('#' + $(item).val() + 'status').text();
			if(status != '未提交'){
				flag = false;
				return false;
			}
		});
		
		if(!flag){
			alert('删除失败，请确认所选付款申请状态！');
		}
		return flag;
	}
	
	$(document).delegate('#exportExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			window.open('fre-expense-to-export-expense-statement.do?freightStatementId=' + $('.selectedItem:checked').val());
		}
	});
	
	$(document).delegate('#exportExpenseSplit', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			window.open('fre-expense-to-export-expense-split.do?freightStatementId=' + $('.selectedItem:checked').val());
		}
	});
    </script>
  </body>
</html>
