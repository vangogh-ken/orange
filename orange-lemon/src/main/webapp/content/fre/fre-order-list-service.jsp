<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>订单管理(客服)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>订单管理(客服)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-ADD">
										<button class="btn btn-sm red" onclick="window.open('fre-order-input-service.do')">
											新增</button>
										<button class="btn btn-sm green" id="toAddOrderByCopy">
											复制新增</button>
										<button class="btn btn-sm green" id="toCopyExpense">
											复制费用</button>
										</sec:authorize>
									
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-REMOVE">
										<button class="btn btn-sm red" onclick="if(validBatchForRemove()){table.removeAll();}">
											删除</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-DONE-ORDER-AUDIT">
										<button class="btn btn-sm green" id="doneOrderAudit">审核订单</button>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BACK-ORDER-AUDIT">
										<button class="btn btn-sm green" id="backOrderAudit">退回订单</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-FINISH-ORDER">
										<button class="btn btn-sm yellow" id="finishOrder">完成订单</button>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-INVALID-ORDER">
										<button class="btn btn-sm yellow" id="invalidOrder">作废订单</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-DONE-EXPENSE-AUDIT">
										<button class="btn btn-sm green" id="doneExpenseAudit">审核费用</button>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BACK-EXPENSE-AUDIT">
										<button class="btn btn-sm green" id="backExpenseAudit">退回费用</button>
										</sec:authorize>
										<button class="btn btn-sm green" id="finishExpense">添加完毕</button>
										
										<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP,ROLE_CY-CUSTOMER-SERVICE-EMP,ROLE_CY-ORDER-AUDIT-EMP">
										<button class="btn btn-sm red" id="addRequire">
											箱需详情</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-ORDER-BOX-VIEW">
										<button class="btn btn-sm red" id="viewOrderBox">
											箱封详情</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-EXPENSE-ADD">
										<button class="btn btn-sm green" id="addExpense">
											添加费用</button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-EXPENSE-VIEW">
										<button class="btn btn-sm green" id="viewExpense">
											费用详情</button>
										</sec:authorize>	
									
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
								</div>
								<div class="pull-right" >
									<label for="pageSize" >每页显示</label>
									<select id="pageSize" class="m-page-size" > 
									    <option value="10">10条</option> 
									    <option value="20">20条</option>
									    <option value="50">50条</option>
									 </select>
								</div>
							</article>
							
							<article class="m-widget" id="searchAcrticle" >
								<form name="searchForm" method="post" action="fre-order-list-service.do" class="form-inline">
								     <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    
								    <label for="cargoName">品名</label>
								    <input type="text" id="cargoName" name="cargoName" value="${param.cargoName}" class="form-control input-xsmall">
								    
								    <label for="delegatePart">单位</label>
								    <input type="text" id="delegatePart" name="delegatePart" value="${param.delegatePart}" class="form-control input-xsmall">
								    
								    <label for="delegateNumber">编号</label>
								    <input type="text" id="delegateNumber" name="delegateNumber" value="${param.delegateNumber}" class="form-control input-xsmall">
								     
								    <label for="salesman">业务</label>
								    <input type="text" id="salesman" name="salesman" value="${param.salesman}" class="form-control input-xsmall" >
								    <hr class="search-input-spliter">
								    <!-- 
								    <label for="manipulator">操作员</label>
								    <input type="text" id="manipulator" name="manipulator" value="${param.manipulator}" class="form-control input-xsmall" >
								    -->
								    <label for="JZX">箱号</label>
								    <input type="text" id="JZX" name="JZX" value="${param.JZX}" class="form-control input-xsmall" >
								    
								    <label for="XS">箱属</label>
								    <input type="text" id="XS" name="XS" value="${param.XS}" class="form-control input-xsmall" >
								    
								    <label for="TDH">提单</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-xsmall" >
								    <!--  
								    <label for="CMHC">船次</label>
								    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control input-xsmall" >
								    -->
								    <label for="orderStatus">订单状态</label>
								    <select id="orderStatus" name="orderStatus" class="form-control input-xsmall">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.orderStatus == '未提交'}">selected</c:if> >未提交</option>
								    	<option value="审核中" <c:if test="${param.orderStatus == '审核中'}">selected</c:if> >审核中</option>
								    	<option value="已审核" <c:if test="${param.orderStatus == '已审核'}">selected</c:if> >已审核</option>
								    	<option value="已退回" <c:if test="${param.orderStatus == '已退回'}">selected</c:if> >已退回</option>
								    	<option value="已追回" <c:if test="${param.orderStatus == '已追回'}">selected</c:if> >已追回</option>
								    	<option value="已作废" <c:if test="${param.orderStatus == '已作废'}">selected</c:if> >已作废</option>
								    	<option value="已完成" <c:if test="${param.orderStatus == '已完成'}">selected</c:if> >已完成</option>
								    </select>
								    
								    <label for="expenseStatus">费用状态</label>
								    <select id="expenseStatus" name="expenseStatus" class="form-control input-xsmall">
								    	<option value=""></option>
								    	<option value="未添加" <c:if test="${param.expenseStatus == '未添加'}">selected</c:if> >未添加</option>
								    	<option value="添加中" <c:if test="${param.expenseStatus == '添加中'}">selected</c:if> >添加中</option>
								    	<option value="添加完毕" <c:if test="${param.expenseStatus == '添加完毕'}">selected</c:if> >添加完毕</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-order-remove-service.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BALANCE">
								                    <th>收支差</th>
								                    </sec:authorize>
								                    <!--  
								                    <th class="sorting">收入折合</th>
								                    <th class="sorting">收入税金</th>
								                    <th class="sorting">支出折合</th>
								                    <th class="sorting">支出税金</th>
								                    -->
								                    <th class="sorting" name="ORDER_NUMBER">订单编号</th>
								                    <th class="sorting" name="ORDER_STATUS">订单状态</th>
								                    <th class="sorting" name="EXPENSE_STATUS">费用状态</th>
								                    <th class="sorting" name="DELEGATE_PART">委托单位</th>
								                    <th class="sorting" name="CARGO_NAME">品名</th>
								                    <th>箱型箱量箱属</th>
								                    <th>费用情况</th>
								                    <th class="sorting" name="CARGO_OWNER">货主</th>
								                    <th class="sorting" name="SALESMAN">业务员</th>
								                    <th class="sorting" name="MANIPULATOR">操作员</th>
								                    <th class="sorting" name="CARGO_AMOUNT">件数</th>
								                    <th class="sorting" name="CARGO_WEIGHT">重量</th>
								                    <th class="sorting" name="CARGO_CAPACITY">体积</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="ORDER_SCOPE">业务归属</th>
								                     <th class="sorting" name="FIRST_TYPE">一级类型</th>
								                    <th class="sorting" name="SECOND_TYPE">二级类型</th>
								                    <th class="sorting" name="THIRD_TYPE">三级类型</th>
								                    <th class="sorting" name="FOURTH_TYPE">四级类型</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="DELEGATE_CONTACT">委托联系人</th>
								                    <th class="sorting" name="COMMISSION">委托书</th>
								                    <th class="sorting" name="PLACE_TIME">下单时间</th>
								                    <th class="sorting" name="FINISH_TIME">完成时间</th>
								                    <th class="sorting" name="DEFICIT_REASON">亏损原因</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="window.open('fre-order-input-service.do?id=${item.id}');" <c:if test="${item.orderStatus == '已完成'}">style="border-bottom:2px solid green;"</c:if> <c:if test="${item.orderStatus == '已追回'}">style="border-bottom:2px solid red;"</c:if>>
								                	<!-- 保留此信息在填写亏损情况说明时使用  -->
								                    <span id="${item.id}incomeAmount" style="display:none;">${item.incomeAmount}</span>
								                    <span id="${item.id}incomeTax" style="display:none;">${item.incomeTax}</span>
								                    <span id="${item.id}paymentAmount" style="display:none;">${item.paymentAmount}</span>
								                    <span id="${item.id}paymentTax" style="display:none;">${item.paymentTax}</span>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BALANCE">
								                    <td id="${item.id}balance">${item.balance}</td>
								                    </sec:authorize>
								                    <td>${item.orderNumber}</td>
								                    <td id="${item.id}orderStatus">${item.orderStatus}</td>
								                    <td id="${item.id}expenseStatus">${item.expenseStatus}</td>
								                    <td>${item.delegatePart}</td>
								                    <td>${item.cargoName}</td>
								                    <td>
								                    	<c:forEach items="${item.freightBoxRequires}" var="freightBoxRequire">
								                    	${freightBoxRequire.boxType}*${freightBoxRequire.boxCount} ${freightBoxRequire.boxBelong}
								                    	</c:forEach>
								                    </td>
								                    <td>
								                    	<c:set var="status1" value="0"></c:set>
								                    	<c:set var="status2" value="0"></c:set>
								                    	<c:set var="status3" value="0"></c:set>
								                    	<c:forEach items="${item.freightExpenses}" var="freightExpense">
								                    	<c:if test="${freightExpense.status == '未提交'}">
								                    		<c:set var="status1" value="${status1 + 1}"></c:set>
								                    	</c:if>
								                    	<c:if test="${freightExpense.status == '审核中'}">
								                    		<c:set var="status2" value="${status2 + 1}"></c:set>
								                    	</c:if>
								                    	<c:if test="${freightExpense.status == '已审核'}">
								                    		<c:set var="status3" value="${status3 + 1}"></c:set>
								                    	</c:if>
								                    	</c:forEach>
								                    	${status1}*未/${status2}*审/${status3}*已 
								                    </td>
								                    <td>${item.cargoOwner}</td>
								                    <td>${item.salesman}</td>
								                    <td>${item.manipulator}</td>
								                    <td>${item.cargoAmount}</td>
								                    <td>${item.cargoWeight}</td>
								                    <td>${item.cargoCapacity}</td>
								                    <td>${item.descn}</td>
								                    <td>${item.orderScope}</td>
								                    <td>${item.firstType}</td>
								                    <td>${item.secondType}</td>
								                    <td>${item.thirdType}</td>
								                    <td>${item.fourthType}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td>${item.delegateContact}</td>
								                    <td><a href="fre-order-download-commission.do?freightOrderId=${item.id}" target="_blank">委托书</a></td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.deficitReason}</td>
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
	
	<!-- 箱需-->
	<div class="modal fade" id="requireModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">订单箱需</h4>
			</div>
			<!-- 添加箱需-->
			<div class="modal-body">
				<article class="m-widget">
				<form id="requireToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="requireBtnForm" name="requireBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<!-- 客户服务 -->
						<sec:authorize ifAnyGranted="ROLE_CY-CUSTOMER-SERVICE-EMP">
						<button type="button" class="btn btn-sm red" onclick="$('#requireToAddForm').submit()">添加</button>
						<button type="button" class="btn btn-sm red" onclick="reviseRequire();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="deleteRequire();">删除</button>
						</sec:authorize>
						<!-- 操作
						<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP">
						<button type="button" class="btn btn-sm green" onclick="releaseRequire();">放箱</button>
						</sec:authorize>
						 -->
					</form>
				</article>
			
				<!-- 已有箱需 -->
				<article class="m-widget">
				<form id="requireHasAddForm" action="" method="post" class="m-form-blank" style="min-height: 200px;overflow-y: scroll;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 费用 -->
	<div class="modal fade" id="expenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">订单费用</h4>
			</div>
			<!-- 添加费用 -->
			<div class="modal-body">
				<article class="m-widget">
				<form id="expenseAddForm" action="fre-expense-save-async.do" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="expenseBtnForm" name="expenseBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#expenseAddForm').submit()">添加</button>
						<button type="button" class="btn btn-sm red" onclick="if(addByBox()){$('#expenseAddForm').submit()}">按箱</button>
						<button type="button" class="btn btn-sm green" onclick="deleteExpense();">删除</button>
						<button type="button" class="btn btn-sm green" onclick="reviseExpense();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="toExpenseAudit();">提交审核</button>
						<button type="button" class="btn btn-sm red" onclick="finishExpense();">添加完毕</button>
						<span id="expenseCount" style="margin-left:600px;font-size: 14px;font-weight: 600;"></span>
					</form>
				</article>
				
				<!-- 箱封详情 -->
				<article class="m-widget">
				<form id="orderBoxSelectForm" action="" method="post" style="display:none;max-height:250px;overflow-y:scroll;" class="m-form-blank"></form>
				</article>
				
				<!-- 已有费用 -->
				<article class="m-widget" >
				<form id="expenseHasAddForm" action="" method="post" style="max-height:330px;overflow-y: scroll;" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	
	<!-- 费用添加完结，如果净差额小于0，则需要填写情况说明 -->
	<div class="modal fade" id="finishExpenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">亏损原因</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="orderInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				<article class="m-widget">
				<form id="finishExpenseForm" action="" method="post" class="m-form-blank">
					<input id="freightOrderId" name="freightOrderId" type="hidden">
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:200px;">
									<label class="td-label" for="deficitReason">亏损原因</label>
								</td>
								<td>
									<textarea id="deficitReason" name="deficitReason" style="min-height: 100px;" maxlength="256" class="form-control required"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#finishExpenseForm').submit();">确定</button>
			</div>
		</div>
	</div>
	
	<!-- 复制新增-->
	<div class="modal fade" id="toAddOrderByCopyModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">复制订单</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="toAddOrderByCopyForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#toAddOrderByCopyForm').submit();">确定</button>
			</div>
		</div>
	</div>
	
	<!-- 复制费用-->
	<div class="modal fade" id="toCopyExpenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">复制费用</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="toCopyExpenseForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#toCopyExpenseForm').submit();">确定</button>
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
	 	        'orderNumber': '${param.orderNumber}',
	 	        'cargoName': '${param.cargoName}',
	 	        'delegatePart': '${param.delegatePart}',
	 	        'delegateNumber': '${param.delegateNumber}',
	 	        'salesman': '${param.salesman}',
	 	        'JZX': '${param.JZX}',
	 	        'XS': '${param.XS}',
	 	        'TDH': '${param.TDH}',
	 	        'CMHC': '${param.CMHC}',
	 	        'orderStatus': '${param.orderStatus}',
	 	        'expenseStatus': '${param.expenseStatus}'
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
	
	//提交业务
	/***
	$(document).delegate('#toOrderAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '未提交' && orderStatus != '已退回'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-to-order-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	***/
	//业务审核完成
	$(document).delegate('#doneOrderAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '审核中'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-done-order-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	//业务审核退回
	$(document).delegate('#backOrderAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '审核中'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-back-order-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	//提交财务
	$(document).delegate('#toExpenseAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus != '添加中'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-to-expense-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	//财务审核
	$(document).delegate('#doneExpenseAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus != '添加中' && expenseStatus != '添加完毕'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-done-expense-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	//费用审核
	$(document).delegate('#backExpenseAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus != '添加中'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-back-expense-audit.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	function finishExpense(){
		var freightOrderId = $('#expenseAddForm #freightOrderId').val();
		var expenseStatus = $('#' + freightOrderId + 'expenseStatus').text();
		if(expenseStatus != '添加中'){
			alert('提交失败！请确认费用状态！');
			return;
		}else{
			if(window.confirm('确认费用已经全部添加完毕了吗？')){
				$('#expenseModal').modal('hide');
				$.post('fre-order-finish-expense.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('此订单净差额小于0！请填写情况说明！');
						var balance = $('#' + freightOrderId + 'balance').text();
						var incomeAmount = $('#' + freightOrderId + 'incomeAmount').text();
						var incomeTax = $('#' + freightOrderId + 'incomeTax').text();
						var paymentAmount = $('#' + freightOrderId + 'paymentAmount').text();
						var paymentTax = $('#' + freightOrderId + 'paymentTax').text();
						
						var html = '<table id="orderInfoTable" class="m-table table-striped table-bordered table-hover">';
						html += '<thead><tr>';
						html += '<th>收支差</th><th>收入折合</th><th>收入税金</th><th>支出折合</th><th>支出税金</th></tr></thead><tbody>';
						html += '<tr><td>'+balance+'</td><td>'+incomeAmount+'</td><td>'+incomeTax+'</td><td>'+paymentAmount+'</td><td>'+paymentTax+'</td></tr></tbody></table>';
						$('#orderInfoForm').html(html);
						
						$('#finishExpenseForm #freightOrderId').val(freightOrderId);
						var margin = (window.screen.availWidth - 1000)/2;
						$('#finishExpenseModal').css("margin-left", margin);
						$('#finishExpenseModal').css("width","1000px");
						$('#finishExpenseModal').modal();
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}
		}
	}
	
	//费用添加完结
	$(document).delegate('#finishExpense', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-order-finish-expense.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var balance = new Number($('#' + $(item).val() + 'balance').text());
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus !='添加中' || balance < 0){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightOrderId=' +　$(item).val();
					}else{
						url += '&freightOrderId=' +　$(item).val();
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
				alert('提交失败！请确认订单状态和收支差是否都大于0！');
				return;
			}
		}
	});
	/**
	$(document).delegate('#finishExpense', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus != '添加中'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-finish-expense.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('此订单净差额小于0！请填写情况说明！');
							var freightOrderId = $(item).val();
							var balance = $('#' + freightOrderId + 'balance').text();
							var incomeAmount = $('#' + freightOrderId + 'incomeAmount').text();
							var incomeTax = $('#' + freightOrderId + 'incomeTax').text();
							var paymentAmount = $('#' + freightOrderId + 'paymentAmount').text();
							var paymentTax = $('#' + freightOrderId + 'paymentTax').text();
							
							var html = '<table id="orderInfoTable" class="m-table table-striped table-bordered table-hover">';
							html += '<thead><tr>';
							html += '<th>收支差</th><th>收入折合</th><th>收入税金</th><th>支出折合</th><th>支出税金</th></tr></thead><tbody>';
							html += '<tr><td>'+balance+'</td><td>'+incomeAmount+'</td><td>'+incomeTax+'</td><td>'+paymentAmount+'</td><td>'+paymentTax+'</td></tr></tbody></table>';
							$('#orderInfoForm').html(html);
							
							$('#finishExpenseForm #freightOrderId').val(freightOrderId);
							var margin = (window.screen.availWidth - 1000)/2;
							$('#finishExpenseModal').css("margin-left", margin);
							$('#finishExpenseModal').css("width","1000px");
							$('#finishExpenseModal').modal();
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			});
		}
	});
	**/
	//保存亏损情况说明
	$(function() {
		$("#finishExpenseForm").validate({
	        submitHandler: function(form) {
				$.post('fre-order-done-add-deficitreason.do', $("#finishExpenseForm").serialize(), function(data){
					$('#finishExpenseModal').modal('hide');
					if(data != 'success'){
						alert('提交失败！请确认订单状态！');
					}else{
						alert('提交成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				})
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//提交完成
	$(document).delegate('#finishOrder', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '已审核'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-finish-order.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	//作废，未提交，已退回，已追回，且相关费用没有对账，则可进行
	$(document).delegate('#invalidOrder', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择数据!');
			return;
		}else{
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '未提交' && orderStatus != '已退回' && orderStatus != '已追回'){
					alert('提交失败！请确认订单状态！');
					return;
				}else{
					$.post('fre-order-invalid-order.do?freightOrderId=' +　$(item).val(), function(data){
						if(data != 'success'){
							alert('提交失败！请确认订单状态！');
						}else{
							alert('提交成功！');
						} 
						var href = window.location.href;
						window.location.href = href;
					});
				}
			});
		}
	});
	
	//查看数据 箱需列表
	$(document).delegate('#viewRequire', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var url = 'fre-box-require-to-view.do?freightOrderId=' + freightOrderId;
			listData(url, '箱需明细', 1000,
					['起始地','终止地', '集装箱来源', '箱型','箱属','箱量','状态','说明'], 
					['beginStation','arriveStation','boxSource', 'boxType', 'boxBelong', 'boxCount', 'status', 'descn']
			);
		}
	});
	
	//查看箱封
	$(document).delegate('#viewOrderBox', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var url = 'fre-order-box-to-view.do?freightOrderId=' + freightOrderId;
			listData(url, '箱封详情', 1000,
					['起始地','终止地', '箱型','箱属','箱况','集装箱来源','箱号','封号', '状态','提箱地'], 
					['freightBoxRequire.beginStation','freightBoxRequire.arriveStation','freightBoxRequire.boxType', 
					 'freightBoxRequire.boxBelong', 'freightBoxRequire.boxCondition','freightBoxRequire.boxSource',
					 'freightBox.boxNumber', 'freightSeal.sealNumber','status','location']
			);
		}
	});
	
	$(document).delegate('#viewExpense', 'click', function(e){
		viewExpense();
	});
	
	//查看费用
	function viewExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var expenseStatus = $('#' + freightOrderId + 'expenseStatus').text();
			if(expenseStatus == '未添加'){
				alert('尚未添加任何费用！');
				return false;
			}else{
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$('#expenseModal #expenseAddForm').parent().hide();
				$('#expenseModal #expenseBtnForm button').hide();//只隐藏按钮
				$('#expenseModal #orderBoxSelectForm').parent().hide();
				$.ajax({
					url:'fre-expense-to-view.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async: true,
					success:function(data){
						var hasAddData = data.hasAddData;//已经保存的费用
						var freightOrder = data.freightOrder;
						$('#expenseBtnForm #expenseCount').text("订单号: " + freightOrder.orderNumber + (hasAddData == null? '  共计 0 条费用' : ('  共计 ' + hasAddData.length + ' 条费用')));
						//已添加的费用
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>折合税金</th><th>状态</th><th>相关动作</th><th>备注</th></tr></thead><tbody>';
						var countIncome = 0;
						$.each(hasAddData, function(i, item){
							if(item.incomeOrExpense == '收'){
								countIncome++;
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
								html += '<td>' + item.freightExpenseType.typeName + '</td>';
								html += '<td>' + item.incomeOrExpense + '</td>';
								html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
								html += '<td>' + item.fasInvoiceType.typeName + '</td>';
								html += '<td>' + item.countUnit + '</td>';
								html += '<td>' + item.exchangeRate + '</td>';
								html += '<td>' + item.currency + '</td>';
								html += '<td>' + item.predictCount + '</td>';
								html += '<td>' + item.predictAmount + '</td>';
								html += '<td>' + item.taxation + '</td>';
								html += '<td>' + item.status + '</td>';
								html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td>';
								html += '<td>' + item.descn + '</td></tr>';
							}
						});
						if(countIncome > 0){
							html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
						}
						var countExpense = 0;
						$.each(hasAddData, function(i, item){
							if(item.incomeOrExpense == '付'){
								countExpense++;
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
								html += '<td>' + item.freightExpenseType.typeName + '</td>';
								html += '<td>' + item.incomeOrExpense + '</td>';
								html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
								html += '<td>' + item.fasInvoiceType.typeName + '</td>';
								html += '<td>' + item.countUnit + '</td>';
								html += '<td>' + item.exchangeRate + '</td>';
								html += '<td>' + item.currency + '</td>';
								html += '<td>' + item.predictCount + '</td>';
								html += '<td>' + item.predictAmount + '</td>';
								html += '<td>' + item.taxation + '</td>';
								html += '<td>' + item.status + '</td>';
								html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td>';
								html += '<td>' + item.descn + '</td></tr>';
							}
						});
						if(countExpense > 0){
							html += '<tr><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+freightOrder.paymentAmount+'</td><td>小计:'+freightOrder.paymentTax+'</td><td></td><td></td><td></td></tr>';
						}
						if(countIncome>0 || countExpense>0){
							html += '<tr><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="2"></td><td colspan="2">财务税差:'+freightOrder.fasTaxBalance+'</td><td colspan="2">销售税差:'+freightOrder.saleTaxBalance+'</td><td>收支差:'+freightOrder.balance+'</td><td>小计:'+(Math.round((freightOrder.paymentTax - freightOrder.incomeTax)*100)/100)+'</td><td></td><td></td><td></td></tr>';
						}
						
						html += "</tbody></table>";
						$('#expenseHasAddForm').css('max-height', '430px');
						$('#expenseHasAddForm').html(html);
						box.modal('hide');
						
						var margin = (window.screen.availWidth - 1300)/2;
						$('#expenseModal').css("margin-left", margin);
						$('#expenseModal').css("width","1300px");
						$('#expenseModal').modal();
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~费用
	//添加费用
	$(document).delegate('#addExpense', 'click',function(e){
		addExpense();
	});
	
	//添加费用
	function addExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var expenseStatus = $('#' + freightOrderId + 'expenseStatus').text();
			if(expenseStatus != '未添加' && expenseStatus != '添加中'){
				alert('费用已确认添加完毕不能再添加！');
				return false;
			}else{
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.ajax({
					url:'fre-expense-to-add-normal-expense.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async: true,
					success:function(data){
						var hasAddData = data.hasAddData;//已经保存的费用
						var freightActions = data.freightActions;//订单的所有动作
						var freightExpenseTypes = data.freightExpenseTypes;//费用名称
						var fasInvoiceTypes = data.fasInvoiceTypes;//发票票种
						var freightOrder = data.freightOrder;
						var freightOrderBoxs = data.freightOrderBoxs;//箱封
						
						$('#expenseBtnForm #expenseCount').text("订单号: " + freightOrder.orderNumber + (hasAddData == null? '  未添加费用' : ('  已添加 ' + hasAddData.length + ' 条费用')));
						var html = '<input id="freightOrderId" type="hidden" value="' + freightOrderId + '">';
						html += '<input id="forBox" type="hidden" value="F">';//是否按箱添加做标记
						html += '<input id="freightPriceId" type="hidden" value="">';
						html += '<input id="freightExpenseId" type="hidden" value="">';//用于修改单条费用时使用
						html += '<table class="m-table table-striped table-bordered">';
						html += '<thead><tr>';
						html += '<th>费用名称</th><th>收/付</th><th>票/箱&nbsp;&nbsp;</th><th>收付单位</th><th>票种</th><th>币种</th><th>预计单价</th><th>相关动作</th><th>备注</th></tr></thead><tbody>';
						html += '<tr><td><select id="freightExpenseTypeId" class="form-control required" onchange="updateExpenseType();"><option value=""></option>';
						$.each(freightExpenseTypes, function(i, item){
							html += '<option value="' + item.id + '"> ' + item.typeName + ' </option>';
						});
						html += '</select></td>';
						html += '<td><select id="incomeOrExpense" name="incomeOrExpense" class="form-control required" onchange="updateIncomeOrExpense();"><option value=""></option><option value="付">付&nbsp;</option><option value="收">收&nbsp;</option></select></td>';
						html += '<td><select id="countUnit" name="countUnit" class="form-control" onchange="updateCountUnit();"><option value=""></option><option value="票">票</option><option value="箱">箱</option></select></td>';
						html += '<td><select id="freightPartId" class="form-control required" onchange="updateFreightPart();"><option value=""></option>';
						html += '</select></td>';
					    
					    html += '<td><select id="fasInvoiceTypeId" value="" class="form-control required"><option value=""></option>';
						$.each(fasInvoiceTypes, function(i, item){
							html += '<option value="' + item.id + '">' + item.typeName + '</option>';
						});
						html += '</select></td>';
						html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
						html += '<td><input id="predictCount" name="predictCount" value="" class="form-control required number" ></td>';
						
						html += '<td><select id="freightActionId" class="form-control required"><option value=""></option>';
						$.each(freightActions, function(i, item){
							html += '<option value="' + item.id + '">' + item.freightActionType.typeName + '</option>';
						});
						html += '</select></td>';
						html += '<td><input id="descn" name="descn" class="form-control" maxlength="32"></td>'
						$('#expenseAddForm').html(html);
						
						//箱封信息
						if(freightOrderBoxs != null && freightOrderBoxs.length > 0){
							html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
							html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll" onclick="updateOrderBox(this);"/></th>';
							html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>封号</th><th>集装箱来源</th><th>状态</th></tr></thead><tbody>';
							$.each(freightOrderBoxs, function(i, item){
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" onclick="updateOrderBox(this);"/></td>';
								html += '<td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
								html += '<td id="' + item.id + 'countUnit">' + item.freightBoxRequire.boxType + '</td>';
								html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
								html += '<td>' + item.freightBoxRequire.boxCondition + '</td>';
								html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
								html += '<td>' + item.freightBoxRequire.boxSource + '</td>';
								html += '<td>' + item.status + '</td>';
							});
							html += "</tbody></table>";
							$('#orderBoxSelectForm').html(html);
							$('#orderBoxSelectForm').hide(300);//修改费用之后，需要隐藏。
						}
						
						//已添加的费用
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>箱量</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>折合税金</th><th>状态</th><th>相关动作</th><th>备注</th></tr></thead><tbody>';
						var countIncome = 0;
						$.each(hasAddData, function(i, item){
							if(item.incomeOrExpense == '收'){
								countIncome++;
								html += '<tr style="height:18px;"><td><input class="selectedItemId a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
								html += '<td style="font-size:12px;">' + item.freightExpenseType.typeName + '</td>';
								html += '<td style="font-size:12px;">' + item.incomeOrExpense + '</td>';
								html += '<td style="font-size:12px;">' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
								html += '<td style="font-size:12px;">' + item.fasInvoiceType.typeName + '</td>';
								if(item.countUnit == '票'){
									html += '<td style="font-size:12px;"> </td>';
								}else{
									if(item.freightOrderBoxs.length == 0){
										html += '<td style="font-size:12px;"> </td>';
									}else{
										var xxxl = '';
										var xx = '';
										var xl = 0;
										$.each(item.freightOrderBoxs, function(n, ele){
											if(xx == ''){
												xx = ele.freightBoxRequire.boxType;
												xl ++;
											}else{
												if(xx == ele.freightBoxRequire.boxType){
													xl ++;
												}else{
													xxxl += xl + '*' + xx + ';';
													xx = ele.freightBoxRequire.boxType;
													xl = 1;
												}
											}
										});
										xxxl += xx + '*' + xl;
										html += '<td style="font-size:12px;">' + xxxl + '</td>';
									}
								}
								
								html += '<td style="font-size:12px;">' + item.countUnit + '</td>';
								html += '<td style="font-size:12px;">' + item.exchangeRate + '</td>';
								html += '<td style="font-size:12px;">' + item.currency + '</td>';
								html += '<td style="font-size:12px;">' + item.predictCount + '</td>';
								if(item.predictAmount != 0){
									html += '<td style="font-size:12px;">' + item.predictAmount + '</td>';
								}else{//按箱费用为0，说明没有关联任何箱子，则标注
									html += '<td style="font-size:12px;background-color:red;">' + item.predictAmount + '</td>';
								}
								html += '<td style="font-size:12px;">' + item.taxation + '</td>';
								html += '<td style="font-size:12px;" id="'+ item.id +'status">' + item.status + '</td>';
								html += '<td style="font-size:12px;">' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td>';
								html += '<td style="font-size:12px;">' + item.descn + '</td></tr>';
							}
						});
						if(countIncome > 0){
							html += '<tr style="height:18px;"><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
						}
						var countExpense = 0;
						$.each(hasAddData, function(i, item){
							if(item.incomeOrExpense == '付'){
								countExpense++;
								html += '<tr style="height:18px;"><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
								html += '<td style="font-size:12px;">' + item.freightExpenseType.typeName + '</td>';
								html += '<td style="font-size:12px;">' + item.incomeOrExpense + '</td>';
								html += '<td style="font-size:12px;">' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
								html += '<td style="font-size:12px;">' + item.fasInvoiceType.typeName + '</td>';
								
								if(item.countUnit == '票'){
									html += '<td style="font-size:12px;"> </td>';
								}else{
									if(item.freightOrderBoxs.length == 0){
										html += '<td style="font-size:12px;"> </td>';
									}else{
										var xxxl = '';
										var xx = '';
										var xl = 0;
										$.each(item.freightOrderBoxs, function(n, ele){
											if(xx == ''){
												xx = ele.freightBoxRequire.boxType;
												xl ++;
											}else{
												if(xx == ele.freightBoxRequire.boxType){
													xl ++;
												}else{
													xxxl += xl + '*' + xx + ';';
													xx = ele.freightBoxRequire.boxType;
													xl = 1;
												}
											}
										});
										xxxl += xl + '*' + xx;
										html += '<td style="font-size:12px;">' + xxxl + '</td>';
									}
								}
								
								html += '<td style="font-size:12px;">' + item.countUnit + '</td>';
								html += '<td style="font-size:12px;">' + item.exchangeRate + '</td>';
								html += '<td style="font-size:12px;">' + item.currency + '</td>';
								html += '<td style="font-size:12px;">' + item.predictCount + '</td>';
								if(item.predictAmount != 0){
									html += '<td style="font-size:12px;">' + item.predictAmount + '</td>';
								}else{//按箱费用为0，说明没有关联任何箱子，则标注
									html += '<td style="background-color:red;font-size:12px;">' + item.predictAmount + '</td>';
								}
								html += '<td style="font-size:12px;">' + item.taxation + '</td>';
								html += '<td style="font-size:12px;" id="'+ item.id +'status">' + item.status + '</td>';
								html += '<td style="font-size:12px;" >' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td>';
								html += '<td style="font-size:12px;">' + item.descn + '</td></tr>';
							}
						});
						if(countExpense > 0){
							html += '<tr style="height:18px;"><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+freightOrder.paymentAmount+'</td><td>小计:'+freightOrder.paymentTax+'</td><td></td><td></td><td></td><td></td></tr>';
						}
						if(countIncome>0 || countExpense>0){
							html += '<tr style="height:18px;"><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="2"></td><td colspan="2">财务税差:'+freightOrder.fasTaxBalance+'</td><td colspan="2">销售税差:'+freightOrder.saleTaxBalance+'</td><td>收支差:'+freightOrder.balance+'</td><td>小计:'+(Math.round((freightOrder.paymentTax - freightOrder.incomeTax)*100)/100)+'</td><td></td><td></td><td></td></tr>';
						}
						
						html += "</tbody></table>";
						$('#expenseHasAddForm').html(html);
						initSelect2();
						box.modal('hide');
						var margin = (window.screen.availWidth - 1300)/2;
						$('#expenseModal').css("margin-left", margin);
						$('#expenseModal').css("width","1300px");
						$('#expenseModal').modal(); 
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	//更改费用类型时，重置信息
	function updateExpenseType(){
		$('#expenseAddForm #freightPartId').val('');
		$('#expenseAddForm #freightPriceId').val('');
		$('#expenseAddForm #fasInvoiceTypeId').val('');
		$('#expenseAddForm #predictCount').val('');
		
		$('#expenseAddForm #countUnit').val('');
		
		$('#expenseAddForm #freightPartId').html('<option value=""></option>');
		$('#expenseAddForm #freightPartId').select2('val', '');
		
		$('#orderBoxSelectForm .selectedItemId:checked').removeAttr('checked');
		$('#orderBoxSelectForm .selectedItemIdAll:checked').removeAttr('checked');
		
		initSelect2();
	}
	
	function updateIncomeOrExpense(){
		var incomeOrExpense = $('#expenseAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '收'){
			var url = 'fre-part-all.do';
			$.post(url, function(data){
				var html = '<option value=""></option>';
				$.each(data, function(i, item){
					if(item != null){
						html += '<option value="' + item.id + '">' + item.partName + '</option>';
					}
				});
				$('#expenseAddForm #freightPartId').html(html);
				initSelect2();//使用SELECT2
			});
		}else{
			$('#expenseAddForm #freightPartId').html('<option value=""></option>');//先预置成空，然后再通过成本补充数据；
			initSelect2();
		}
		
		$('#expenseAddForm #countUnit').val('');
	}
	
	//更改单位时，获取该单位的该费用的成本
	function updateFreightPart(){
		var incomeOrExpense = $('#expenseAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '付'){//针对付出去的费用才使用成本
			var freightPartId = $('#expenseAddForm #freightPartId').val();
			var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
			var countUnit = $('#expenseAddForm #countUnit').val();
			var url;
			if(countUnit == '票'){
				url = 'fre-price-for-add-expense.do?freightPartId=' + freightPartId + '&freightExpenseTypeId=' + freightExpenseTypeId;
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm .selectedItemId:checked:first').val() + 'countUnit').text();
				url = 'fre-price-for-add-expense.do?freightPartId=' + freightPartId 
						+ '&freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit;
			}
			
			$.post(url, function(data){
				if(data != null){
					$('#expenseAddForm #freightPriceId').val(data.id);
					$('#expenseAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
					$('#expenseAddForm #predictCount').val(data.moneyCount);
					$('#expenseAddForm #currency').val(data.currency);
					$('#expenseAddForm #countUnit').val((data.countUnit == '票' ? '票' : '箱'));
					
					initSelect2();
					
					if(data.regular == 'T'){
						$('#expenseAddForm #fasInvoiceTypeId').attr('disabled', 'disabled');
						$('#expenseAddForm #predictCount').attr('readonly', 'readonly');
						$('#expenseAddForm #currency').attr('disabled', 'disabled');
					}else{
						$('#expenseAddForm #fasInvoiceTypeId').removeAttr('disabled');
						$('#expenseAddForm #predictCount').removeAttr('readonly');
						$('#expenseAddForm #currency').removeAttr('disabled');
					}
				}
			});
		}
	}
	
	//更改计价方式
	function updateCountUnit(){
		var countUnit = $('#expenseAddForm #countUnit').val();
		var incomeOrExpense = $('#expenseAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '付'){
			if(countUnit == '票'){
				$('#orderBoxSelectForm').hide(300);
				var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
				$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId, function(data){
					var html = '<option value=""></option>';
					$.each(data, function(i, item){
						if(item != null){
							html += '<option value="' + item.id + '">' + item.partName + '</option>';
						}
					});
					
					$('#expenseAddForm #freightPartId').html(html);
					$('#expenseAddForm #freightPartId').val('');
					$('#expenseAddForm #freightPartId').select2('val', '');
					
					$('#orderBoxSelectForm .selectedItemId:checked').removeAttr('checked');
					$('#orderBoxSelectForm .selectedItemIdAll:checked').removeAttr('checked');
					initSelect2();
				});
			}else if(countUnit == '箱'){
				if($('#orderBoxSelectForm table').length > 0){
					$('#orderBoxSelectForm').show(300);
					
					$('#expenseAddForm #freightPartId').html('<option value=""></option>');
					$('#expenseAddForm #freightPartId').val('');
					initSelect2();
				}else{
					$('#expenseAddForm #countUnit').val('票')
					alert('尚未放箱，不能添加按箱计费的费用信息！');
				}
			}
		}else if(incomeOrExpense == '收'){ //收的费用仍然可以按箱
			if(countUnit == '票'){
				$('#orderBoxSelectForm').hide(300);
				$('#orderBoxSelectForm .selectedItemId:checked').removeAttr('checked');
				$('#orderBoxSelectForm .selectedItemIdAll:checked').removeAttr('checked');
			}else if(countUnit == '箱'){
				if($('#orderBoxSelectForm table').length > 0){
					$('#orderBoxSelectForm').show(300);
				}else{
					$('#expenseAddForm #countUnit').val('票')
					alert('尚未放箱，不能添加按箱计费的费用信息！');
				}
			}
		}
	}
	
	//按箱计费之后，选择对应的箱封，则需从后台取相应成本信息的数据
	function updateOrderBox(ele){
		var incomeOrExpense = $('#expenseAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '收'){//如果费用为收，则单位，成本都不重新加载
			return;
		};
		var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
		var countUnit;
		if($(ele).attr('checked') == 'checked'){
			if($(ele).attr('class') == 'selectedItemIdAll'){
				countUnit = $('#' + $('#orderBoxSelectForm .selectedItemId:first').val() + 'countUnit').text();
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm .selectedItemId:checked:first').val() + 'countUnit').text();
			}
			 
			if(freightExpenseTypeId != '' && countUnit != ''){
				$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit, function(data){
					var html = '<option value=""></option>';
					$.each(data, function(i, item){
						if(item != null){
							html += '<option value="' + item.id + '">' + item.partName + '</option>';
						}
					});
					
					//var freightPartId = $('#expenseAddForm #freightPartId').val();
					$('#expenseAddForm #freightPartId').html(html);
					$('#expenseAddForm #freightPartId').val('');
					$('#expenseAddForm #freightPartId').select2('val', '');
					//if(freightPartId != ''){
						//$('#expenseAddForm #freightPartId').val(freightPartId);
					//}
					
					initSelect2();
				});
			}
		}else{
			if($(ele).attr('class') == 'selectedItemIdAll'){
				$('#expenseAddForm #freightPartId').val('');
				$('#expenseAddForm #freightPartId').select2('val', '');
				initSelect2();
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm .selectedItemId:checked:first').val() + 'countUnit').text();
				if(freightExpenseTypeId != '' && countUnit != ''){
					$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit, function(data){
						var html = '<option value=""></option>';
						$.each(data, function(i, item){
							if(item != null){
								html += '<option value="' + item.id + '">' + item.partName + '</option>';
							}
						});
						
						$('#expenseAddForm #freightPartId').html(html);
						$('#expenseAddForm #freightPartId').val('');
						$('#expenseAddForm #freightPartId').select2('val', '');
						
						initSelect2();
					});
				}
			}
		}
		//如果有单位，修改此处则也可获取成本
		//if($('#expenseAddForm #freightPartId').val() != ''){
			//updateFreightPart();
		//}
	}
	//按箱添加
	function addByBox(){
		var countUnit = $('#expenseAddForm #countUnit').val();
		if(countUnit != '箱' || $('#orderBoxSelectForm .selectedItemId:checked') == 0){
			alert('按箱添加时，必须关联集装箱！');
			return false;
		}else{
			$('#expenseAddForm #forBox').val('T');
			return true;
		}
	}
	
	$(function() {
		$("#expenseAddForm").validate({
	        submitHandler: function(form) {
				var freightOrderId = $('#expenseAddForm #freightOrderId').val();
				var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
				var freightActionId = $('#expenseAddForm #freightActionId').val();
	    		var freightPartId = $('#expenseAddForm #freightPartId').val();
	    		var freightPriceId = $('#expenseAddForm #freightPriceId').val();
	    		var fasInvoiceTypeId = $('#expenseAddForm #fasInvoiceTypeId').val();
	    		var freightExpenseId = $('#expenseAddForm #freightExpenseId').val();//修改单条费用时使用
	    		var forBox = $('#expenseAddForm #forBox').val();//是否按箱添加的标记
	    		if(freightOrderId == '' || freightExpenseTypeId == '' || fasInvoiceTypeId == '' || freightPartId == ''){
	    			return false;//取消保存
	    		}
	    		//箱封
	    		var freightOrderBoxId = '';
	    		$('#orderBoxSelectForm .selectedItemId:checked').each(function(i, item){
	    			freightOrderBoxId += '&freightOrderBoxId=' + $(item).val();
	    		});
	    		
	    		var countUnit = $('#expenseAddForm #countUnit').val();
	    		if(countUnit == '票'){
	    			freightOrderBoxId = '';
	    		}else if(freightOrderBoxId == ''){
    				alert('选择按箱时，请选择具体的箱封信息！');
    				return false;
	    		}
	    		if($('#expenseAddForm #incomeOrExpense').val() == '付' && freightActionId == ''){
	    			alert('所付费用必须关联对应的动作！');
	    			return false;
	    		}else if($('#expenseAddForm #incomeOrExpense').val() == '付' && freightPriceId == ''){
	    			alert('必须顺序添加信息，该费用未能正确关联成本！');
	    			return false;
	    		}else if($('#expenseAddForm #incomeOrExpense').val() == '收'){
	    			freightActionId = '';
	    			freightPriceId = '';//收的费用不需要关联成本
	    		}
	    		//去除禁用，否则数据无法提交
	    		$('#expenseAddForm #fasInvoiceTypeId').removeAttr('disabled');
				$('#expenseAddForm #predictCount').removeAttr('readonly');
				$('#expenseAddForm #currency').removeAttr('disabled');
				
				var data = toJsonString('expenseAddForm');
				var url = 'fre-expense-done-add-normal-expense.do?freightOrderId=' 
						+ freightOrderId + '&freightPartId=' + freightPartId 
						+ '&freightPriceId=' + freightPriceId 
						+ '&freightExpenseTypeId=' + freightExpenseTypeId
						+ '&freightActionId=' + freightActionId
						+ '&fasInvoiceTypeId=' + fasInvoiceTypeId
						+ '&freightExpenseId=' + freightExpenseId
						+ '&forBox=' + forBox
						+ freightOrderBoxId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addExpense();
	    			}
	    		});
				
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除费用
	function deleteExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			var flag = true;
			var url = 'fre-expense-done-remove-expense.do?';
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '未提交' || status == '审核中' || status == '已退回' || status == '已审核'){//只有这几种状态的费用可以删除
					if(i == 0){
						url += 'freightExpenseId=' + $(item).val();
					}else{
						url += '&freightExpenseId=' + $(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post(url, function(data){
					if(data == 'success'){
						addExpense();
					}else{
						alert('删除失败！');
					}
					box.modal('hide');
				});
			}else{
				alert('删除失败！请确认费用状态！');
			}
		}
	}
	
	//提交费用
	function toExpenseAudit(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择需要提交审核的费用！');
			return false;
		}else{
			var url = 'fre-expense-to-expense-audit.do?';
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightExpenseId=' + $(item).val();
				}else{
					url += '&freightExpenseId=' + $(item).val();
				}
			});
			$.post(url, function(data){
				if(data == 'success'){
					alert('提交成功！');
					addExpense();
				}else{
					alert('提交失败！');
				}
			});
		}
	}
	
	//修改费用
	function reviseExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var freightExpenseId = $('#expenseHasAddForm .selectedItemId:checked').val();
			var status = $('#' + freightExpenseId + 'status').text();
			//只有这几种状态的费用可以修改
			if(status == '未提交' || status == '审核中' || status == '已退回' || status == '已审核'){
				var url = 'fre-expense-to-revise-expense.do?freightExpenseId=' + freightExpenseId;
				$.post(url, function(data){
					$('#expenseAddForm #freightExpenseId').val(data.id);
					$('#expenseAddForm #freightExpenseTypeId').val(data.freightExpenseType.id);
					$('#expenseAddForm #freightPartId').html('<option value="'+data.freightPartB.id+'">'+data.freightPartB.partName+'</option>');
					$('#expenseAddForm #freightPriceId').val(data.freightPrice == null ? '' : data.freightPrice.id);
					if(data.incomeOrExpense == '付' && data.freightAction != null){
						$('#expenseAddForm #freightActionId').val(data.freightAction.id);
					}
					
					if(data.countUnit == '箱'){
						$('#orderBoxSelectForm').show(300);
						var freightOrderBoxs = data.freightOrderBoxs;
						$('#orderBoxSelectForm .selectedItemId').each(function(i, item){
							$(item).removeAttr('checked');
							$.each(freightOrderBoxs, function(n, ele){
								if($(item).val() == ele.id){
									$(item).attr('checked', 'checked');
								}
							});
						});
					}else{
						$('#orderBoxSelectForm').hide(300);
					}
					
					$('#expenseAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
					$('#expenseAddForm #incomeOrExpense').val(data.incomeOrExpense);
					$('#expenseAddForm #predictCount').val(data.predictCount);
					$('#expenseAddForm #currency').val(data.currency);
					$('#expenseAddForm #countUnit').val(data.countUnit);
					$('#expenseAddForm #descn').val(data.descn);
					
					$('#expenseAddForm select.required').each(function(i, item){
						var value = $(item).val();
						$(item).select2('val', value);
					});
				});
			}else{
				alert(status + '状态的费用不能修改！');
			}
		}
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~箱需
	//添加箱需
	$(document).delegate('#addRequire', 'click',function(e){
		if(addRequire()){
			var margin = (window.screen.availWidth - 1200)/2;
			$('#requireModal').css("margin-left", margin);
			$('#requireModal').css("width","1200px");
			$('#requireModal').modal();
		}
	});
	
	//添加箱需
	function addRequire(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var orderStatus = $('#' + freightOrderId + 'orderStatus').text();
			$.ajax({
				url:'fre-box-require-to-add-require.do?freightOrderId=' + freightOrderId,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					var hasAddData = data.hasAddData;
					var html = '<input id="freightOrderId" type="hidden" value="' + freightOrderId + '">';
					html += '<input id="freightBoxRequireId" type="hidden" value="">';
					html += '<table class="m-table table-bordered">';
					html += '<thead><tr><th>起始地</th><th>终止地</th><th>集装箱来源</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th></tr></thead><tbody>';
					html += '<tr><td><input id="beginStation" name="beginStation" value="" class="form-control required dictionary" vAttrId="37604991-9612-11e4-b4b0-a4db305e5cc5"></td>';
					html += '<td><input id="arriveStation" name="arriveStation" value="" class="form-control required dictionary" vAttrId="37604991-9612-11e4-b4b0-a4db305e5cc5"></td>';
					html += '<td><select id="boxSource" name="boxSource" class="form-control required" >';
					html += '<option value="自管箱">自管箱</option><option value="外管箱">外管箱</option><option value="外理箱">外理箱</option>';
					html += '</select></td>';
					html += '<td><input id="boxType" name="boxType" class="form-control required dictionary" vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5"></td>';
					html += '<td><input id="boxBelong" name="boxBelong" class="form-control required dictionary" vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5"></td>';
					html += '<td><input id="boxCondition" class="form-control required dictionary" vAttrId="5a488f04-55d7-11e4-bdcd-a4db305e5cc5" ></td>';
					html += '<td><input id="boxCount" name="boxCount" class="form-control required number"></td>';
					//html += '<td><input id="descn" name="descn" class="form-control"></td>';
					html += '</tr>'
					$('#requireToAddForm').html(html);
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>起始地</th><th>终止地</th><th>集装箱来源</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>状态</th><th>提单号</th></tr></thead><tbody>';
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						html += '<td>' + item.beginStation + '</td>';
						html += '<td>' + item.arriveStation + '</td>';
						html += '<td>' + item.boxSource + '</td>';
						html += '<td>' + item.boxType + '</td>';
						html += '<td>' + item.boxBelong + '</td>';
						html += '<td>' + item.boxCondition + '</td>';
						html += '<td>' + item.boxCount + '</td>';
						html += '<td id="'+ item.id + 'status' +'">' + item.status+ '</td>';
						html += '<td>' + item.blNo + '</td>';
					});
					html += "</tbody></table>";
					$('#requireHasAddForm').html(html);
					
					<sec:authorize ifAnyGranted="ROLE_CY-CUSTOMER-SERVICE-EMP" >
					if(orderStatus == '未提交' || orderStatus == '已退回' || orderStatus == '审核中' || orderStatus == '已追回'){
						$('#requireToAddForm').parent().show();
						$('#requireBtnForm').parent().show();
					}else{
						$('#requireToAddForm').parent().hide();
						$('#requireBtnForm').parent().hide();
					}
					</sec:authorize>
				},
				error:function(){
				}
			});
			return true;
		}
	}
	//修改箱需
	function reviseRequire(){
		if($('#requireHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var freightBoxRequireId = $('#requireHasAddForm .selectedItemId:checked').val();
			var status = $('#requireHasAddForm #' + freightBoxRequireId + 'status').text();
			var freightOrderId = $('.selectedItem:checked').val();
			var orderStatus = $('#' + freightOrderId + 'orderStatus').text();
			if(status == '未提交' || (status == '待选箱' && (orderStatus == '已追回' || orderStatus == '已退回'))){//已追回，待选箱的箱需都可进行修改
				var url = 'fre-box-require-to-revise-require.do?freightBoxRequireId=' + freightBoxRequireId;
				$.post(url, function(data){
					var freightBoxRequire = data.freightBoxRequire;
					$('#requireToAddForm #freightBoxRequireId').val(freightBoxRequire.id);
					$('#requireToAddForm #beginStation').val(freightBoxRequire.beginStation);
					$('#requireToAddForm #arriveStation').val(freightBoxRequire.arriveStation);
					$('#requireToAddForm #boxSource').val(freightBoxRequire.boxSource);
					$('#requireToAddForm #boxType').val(freightBoxRequire.boxType);
					$('#requireToAddForm #boxBelong').val(freightBoxRequire.boxBelong);
					$('#requireToAddForm #boxCondition').val(freightBoxRequire.boxCondition);
					$('#requireToAddForm #boxCount').val(freightBoxRequire.boxCount);
					$('#requireToAddForm #blNo').val(freightBoxRequire.blNo);
				});
			}else{
				alert(status + '状态的箱需不能修改！');
			}
		}
	}
	//保存数据
	$(function() {
		$("#requireToAddForm").validate({
	        submitHandler: function(form) {
				var freightOrderId = $('#requireToAddForm #freightOrderId').val();
				var freightBoxRequireId = $('#requireToAddForm #freightBoxRequireId').val();//修改单条费用时使用
				if(freightOrderId == undefined || freightOrderId == ''){
	    			alert('请重新操作!');
	    			return false;
				}
				//箱况多选，需要单独处理
				var data = toJsonString('requireToAddForm');
				var boxCondition = $('#requireToAddForm #boxCondition').val();
				data = data.substring(0, data.length - 1);
				data += ',"boxCondition":"' + boxCondition + '"}';
				var url = 'fre-box-require-done-add-require.do?freightOrderId=' + freightOrderId + '&freightBoxRequireId=' + freightBoxRequireId;
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data != 'success'){
	    					alert('提交失败！请检查是否已有选箱或放箱或禁止修改的限制！');
	    				}
	    				addRequire();
	    				box.modal('hide');
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除箱需
	function deleteRequire(){
		var url = 'fre-box-require-done-remove-require.do?';
		if($('#requireHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			var flag = true;
			$('#requireHasAddForm .selectedItemId:checked').each(function(i, item){
				var status = $('#requireHasAddForm #' + $(item).val() + 'status').text();
				if(status == '未提交'){
					if(i == 0){
						url += 'freightBoxRequireId=' + $(item).val();
					}else{
						url += '&freightBoxRequireId=' + $(item).val();
					}
				}else{
					flag = false;
					return false;//跳出循环
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data == 'success'){
						addRequire();
					}else{
						alert('删除失败！');
					}
				});
			}else{
				alert('删除失败，请确认所选箱需状态！');
			}
		}
	}
	
	function releaseRequire(){
		var url = 'fre-box-require-to-release-require.do?';
		if($('#requireHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			var flag = true;
			$('#requireHasAddForm .selectedItemId:checked').each(function(i, item){
				var status = $('#requireHasAddForm #' + $(item).val() + 'status').text();
				if(status == '未提交'){
					if(i == 0){
						url += 'freightBoxRequireId=' + $(item).val();
					}else{
						url += '&freightBoxRequireId=' + $(item).val();
					}
				}else{
					flag = false;
					return false;//跳出循环
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data == 'success'){
						alert('放箱成功！');
						addRequire();
					}else{
						alert('放箱失败！');
					}
				});
			}else{
				alert('放箱失败，请确认所选箱需状态！');
			}
		}
	}
	
	//拼接成json数据类型
	function toJsonString(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '{';
		$.each(fields, function(i, item){
			if(item.name != undefined && item.name != 'undefined'){
				if(i == 0){
	   				data += '"' + item.name + '":"' + item.value + '"';
	   			}else{
	   				data += ',"' + item.name + '":"' + item.value + '"';
	   			}
			}
		});
		data += '}';
		
		return data;
	}
	
	//批量检查是否可删除
	function validBatchForRemove(){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '未提交' && orderStatus != '已退回' && orderStatus != '已追回'){
					flag = false;
				}
			});
			if(!flag){
				alert('删除失败！请确认所选订单和费用状态！');
			}
			return flag;
		}
	}
	
	
	//复制新增订单
	$(document).delegate('#toAddOrderByCopy', 'click',function(e){
		toAddOrderByCopy();
	});
	
	function toAddOrderByCopy(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			var freightOrderId = $('.selectedItem:checked').val();
			$.post('fre-order-to-add-order-by-copy.do?freightOrderId=' + freightOrderId,function(data){
				var freightOrder = data.freightOrder;
				var freightExpenses = data.freightExpenses;
				var freightBoxRequires = data.freightBoxRequires;
				var freightMaintains = data.freightMaintains;
				var freightActions = data.freightActions;
				var freightActionValues = data.freightActionValues;
				//箱需信息
				var html = '<input id="freightOrderId" name="freightOrderId" type="hidden" value="' + freightOrderId + '">';
				html += '<table id="requireTable" class="m-table table-striped table-bordered table-hover">';
				html += '<thead><tr><th colspan="8">箱需信息</th></tr><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
				html += '<th>起始地</th><th>目的地</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>提单号</th></tr></thead><tbody>';
				$.each(freightBoxRequires, function(i, item){
					html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="freightBoxRequireId" value="'+item.id+'" /></td>';
					html += '<td>' + item.beginStation + '</td>';
					html += '<td>' + item.arriveStation + '</td>';
					html += '<td>' + item.boxType + '</td>';
					html += '<td>' + item.boxBelong + '</td>';
					html += '<td>' + item.boxCondition + '</td>';
					html += '<td>' + item.boxCount + '</td>';
					html += '<td>' + item.blNo + '</td></tr>';
				});
				html += "</tbody></table>";
				html += '<table id="expenseTable" class="m-table table-striped table-bordered table-hover">';
				html += '<thead><tr><th colspan="12">费用信息</th></tr><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
				html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱</th><th>箱需</th><th>汇率</th><th>币种</th><th>预计金额</th><th>折合税金</th><th>相关动作</th></tr></thead><tbody>';
				//费用信息
				var countIncome = 0;
				$.each(freightExpenses, function(i, item){
					if(item.incomeOrExpense == '收'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countIncome++;
						
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="freightExpenseId" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightExpenseType.typeName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.countUnit + '</td>';
						html += '<td>' + (item.freightBoxRequire == null ?'无': (item.freightBoxRequire.boxCount + '*' + item.freightBoxRequire.boxType)) + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.predictCount + '</td>';
						html += '<td>' + item.taxation + '</td>';
						html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td></tr>';
					}
				});
				if(countIncome > 0){
					html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
				}
				var countExpense = 0;
				$.each(freightExpenses, function(i, item){
					if(item.incomeOrExpense == '付'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countExpense++;
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="freightExpenseId" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightExpenseType.typeName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.countUnit + '</td>';
						html += '<td>' + (item.freightBoxRequire == null ?'无': (item.freightBoxRequire.boxCount + '*' + item.freightBoxRequire.boxType)) + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.predictCount + '</td>';
						html += '<td>' + item.taxation + '</td>';
						html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td></tr>';
					}
				});
				if(countExpense > 0){
					html += '<tr><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+freightOrder.paymentAmount+'</td><td>小计:'+freightOrder.paymentTax+'</td><td></td><td></td></tr>';
				}
				if(countIncome>0 || countExpense>0){
					html += '<tr><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="6"></td><td>总计:'+freightOrder.balance+'</td><td>小计:'+(Math.round((freightOrder.paymentTax - freightOrder.incomeTax)*100)/100)+'</td><td></td><td></td></tr>';
				}
				html += "</tbody></table>";
				//动作信息
				html += '<table id="actionValueTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr><th colspan="6">动作信息</th><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/>';
				html += '<th>属性名称</th><th>属性字段</th><th>值</th><th>所属动作</th><th>操作名称</th></tr></thead><tbody>';
				
				//var count = 0;
				$.each(freightActions, function(i, item){
					var values = freightActionValues[item.id];
					if(values != null){
						//count ++;
						$.each(values, function(n, ele){
							if(n == 0){
								html += '<tr style="border-top:2px solid black;"><td>';
							}else{
								html += '<tr><td>';
							}
							
							html += '<input class="selectedItemId a-check" type="checkbox" name="freightActionValueId" value="' + ele.id + '" />';
							html += '</td>';
							html += '<td>' + ele.freightActionField.fieldName + '</td>';
							html += '<td>' + ele.freightActionField.fieldColumn + '</td>';
							
							var fiedlValue = '';
							if(ele.freightActionField.fieldType == 'INT'){
								fiedlValue = ele.intValue == null? '' : ele.intValue;
							}else if(ele.freightActionField.fieldType == 'DOUBLE'){
								fiedlValue = ele.doubleValue == null? '' : ele.doubleValue;
							}else if(ele.freightActionField.fieldType == 'VARCHAR'){
								fiedlValue = ele.stringValue == null? '' : ele.stringValue;
							}else if(ele.freightActionField.fieldType == 'TEXT'){
								fiedlValue = ele.textValue == null? '' : ele.textValue;
							}else if(ele.freightActionField.fieldType == 'DATETIME'){
								fiedlValue = ele.dateValue == null? '' : $.fullCalendar.formatDate(new Date(ele.dateValue),'yyyy-MM-dd');
							}else if(ele.freightActionField.fieldType == 'TIMESTAMP'){
								fiedlValue = ele.dateValue == null? '' : $.fullCalendar.formatDate(new Date(ele.dateValue),'yyyy-MM-dd HH:mm:ss');
							}
							html += '<td>' + fiedlValue + '</td>';
							html += '<td>' + item.freightActionType.typeName + '</td>';
							html += '<td>' + item.freightMaintain.freightMaintainType.typeName + '</td></tr>';
						});
					}
				});
				
				html += "</tbody></table>";
				$('#toAddOrderByCopyForm').html(html);
				
				box.modal('hide');
				var margin = (window.screen.availWidth - 1200)/2;
				$('#toAddOrderByCopyModal').css("margin-left", margin);
				$('#toAddOrderByCopyModal').css("width","1200px");
				$('#toAddOrderByCopyModal').modal();
			});
		}
	}
	
	$(function() {
		$("#toAddOrderByCopyForm").validate({
	        submitHandler: function(form) {
	        	$('#toAddOrderByCopyModal').modal('hide');
	        	bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post('fre-order-done-add-order-by-copy.do', $("#toAddOrderByCopyForm").serialize(), function(data){
					if(data != null && data != 'error'){
						box.modal('hide');
						window.open('../fre/fre-order-input-service.do?id=' + data);
					}
				});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//复制费用
	$(document).delegate('#toCopyExpense', 'click',function(e){
		toCopyExpense();
	});
	
	function toCopyExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			var freightOrderId = $('.selectedItem:checked').val();
			$.post('fre-order-to-copy-expense.do?freightOrderId=' + freightOrderId, function(data){
				var sourceOrder = data.sourceOrder;
				var freightOrders = data.freightOrders;
				var freightExpenses = data.freightExpenses;
				
				var html = '<input id="sourceOrderId" name="sourceOrderId" type="hidden" value="' + sourceOrder.id + '">';
				//目标订单
				html += '<table class="m-table table-striped table-bordered table-hover" >';
				html += '<thead><tr><th colspan="2">覆盖箱子*</th><th colspan="4"><select id="sheatheAllBox" name="sheatheAllBox" class="form-control required"><option value="T">是</option><option value="F">否</option></select></th></tr>';
				html += '<tr><th colspan="8">目标订单</th></tr><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
				html += '<th>订单编号</th><th>货物名称</th><th>委托单位</th><th>订单状态</th><th>费用状态</th></tr></thead><tbody>';
				$.each(freightOrders, function(i, item){
					html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="targetOrderId" value="'+item.id+'" /></td>';
					html += '<td>' + item.orderNumber + '</td>';
					html += '<td>' + item.cargoName + '</td>';
					html += '<td>' + item.delegatePart + '</td>';
					html += '<td>' + item.orderStatus + '</td>';
					html += '<td>' + item.expenseStatus + '</td></tr>';
				});
				html += "</tbody></table>";
				//费用信息
				html += '<table class="m-table table-striped table-bordered table-hover" >';
				html += '<thead><tr><th colspan="12">费用信息</th></tr><tr><th width="10" class="m-table-check"><input type="checkbox" checked="checked" class="selectedItemIdAll"/></th>';
				html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱</th><th>箱需</th><th>汇率</th><th>币种</th><th>预计金额</th><th>折合税金</th><th>相关动作</th></tr></thead><tbody>';
				var countIncome = 0;
				$.each(freightExpenses, function(i, item){
					if(item.incomeOrExpense == '收'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countIncome++;
						
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" checked="checked" name="freightExpenseId" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightExpenseType.typeName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.countUnit + '</td>';
						html += '<td>' + (item.freightBoxRequire == null ?'无': (item.freightBoxRequire.boxCount + '*' + item.freightBoxRequire.boxType)) + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.predictCount + '</td>';
						html += '<td>' + item.taxation + '</td>';
						html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td></tr>';
					}
				});
				if(countIncome > 0){
					html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+sourceOrder.incomeAmount+'</td><td>小计:'+sourceOrder.incomeTax+'</td><td></td><td></td></tr>';
				}
				var countExpense = 0;
				$.each(freightExpenses, function(i, item){
					if(item.incomeOrExpense == '付'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countExpense++;
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" checked="checked" name="freightExpenseId" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightExpenseType.typeName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.countUnit + '</td>';
						html += '<td>' + (item.freightBoxRequire == null ?'无': (item.freightBoxRequire.boxCount + '*' + item.freightBoxRequire.boxType)) + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.predictCount + '</td>';
						html += '<td>' + item.taxation + '</td>';
						html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td></tr>';
					}
				});
				if(countExpense > 0){
					html += '<tr><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+sourceOrder.paymentAmount+'</td><td>小计:'+sourceOrder.paymentTax+'</td><td></td><td></td></tr>';
				}
				if(countIncome>0 || countExpense>0){
					html += '<tr><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="6"></td><td>总计:'+sourceOrder.balance+'</td><td>小计:'+(Math.round((sourceOrder.paymentTax - sourceOrder.incomeTax)*100)/100)+'</td><td></td><td></td></tr>';
				}
				html += "</tbody></table>";
				$('#toCopyExpenseForm').html(html);
				
				box.modal('hide');
				var margin = (window.screen.availWidth - 1200)/2;
				$('#toCopyExpenseModal').css("margin-left", margin);
				$('#toCopyExpenseModal').css("width","1200px");
				$('#toCopyExpenseModal').modal();
			});
		}
	}
	
	$(function() {
		$("#toCopyExpenseForm").validate({
	        submitHandler: function(form) {
				if($('#toCopyExpenseForm input:checked[name="targetOrderId"]').length == 0 ||
						$('#toCopyExpenseForm input:checked[name="freightExpenseId"]').length == 0){
					alert('请至少选择一条相关数据！');
				}else{
					$('#toCopyExpenseModal').modal('hide');
					$.post('fre-order-done-copy-expense.do', $("#toCopyExpenseForm").serialize(), function(data){
						if(data == 'success'){
							alert('复制成功！');
						}else{
							alert('复制失败！');
						}
						window.location.href = window.location.href;
					});
				}
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//同一个table中的tbody中的元素，使用checkbox选择
	$(document).delegate('.selectedItemIdAll', 'click', function(){//扩展为通用
		if($(this).attr('checked') == 'checked'){
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').attr('checked', 'checked');
				});
			});
		}else{
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').removeAttr('checked');
				});
			});
		}
	});
    </script>
  </body>

</html>
