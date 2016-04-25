<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>订单管理(查询)</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
    <%@include file="/common/header2.jsp"%>
    <div class="page-container">
    	<%@include file="/common/menu.jsp"%>
    	<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper"> <!-- begin page-content-wrapper -->
			<div class="page-content" > <!-- begin page-content-->
				<%@include file="/common/setting.jsp"%>
				<div class="row">
				  <div class="col-md-12">
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-align-justify"></i>订单管理(查询)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<sec:authorize ifAnyGranted="ROLE_CY-ORDER-AUDIT-EMP">
										<button class="btn btn-sm red" id="doneOrderAudit">审核订单</button>
										<button class="btn btn-sm red" id="backOrderAudit">退回订单</button>
										<button class="btn btn-sm green" id="doneExpenseAudit">审核费用</button>
										<button class="btn btn-sm green" id="backExpenseAudit">退回费用</button>
										</sec:authorize>
										
										<!-- 财务部 -->
										<sec:authorize ifAnyGranted="ROLE_CY-FAS-MANAGER-P, ROLE_CY-FAS-MANAGER-G, ROLE_ADMIN">
										<button class="btn btn-sm green" id="recallCompleteExpenseWithDeduct">取消完毕</button>
										</sec:authorize>
										
										<!-- 订单审核员 -->
										<sec:authorize ifAnyGranted="ROLE_CY-ORDER-AUDIT-EMP, ROLE_ADMIN">
										<button class="btn btn-sm green" id="recallCompleteExpenseWithoutDeduct">取消完毕</button>
										</sec:authorize>
										
										<button class="btn btn-sm red" id="viewRequire">
											箱需详情</button>
										
										<button class="btn btn-sm red" id="viewOrderBox">
											箱封详情</button>
										
										<button class="btn btn-sm green" id="viewExpense">
											费用详情</button>
										
										<button class="btn btn-sm green" id="toBatchOrderExport">
											批量导出</button>	 
									
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
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="fre-order-list-query.do" class="form-inline">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    
								    <label for="delegatePart">单位</label>
								    <input type="text" id="delegatePart" name="delegatePart" value="${param.delegatePart}" class="form-control input-xsmall">
								    
								    <label for="salesman">业务</label>
								    <input type="text" id="salesman" name="salesman" value="${param.salesman}" class="form-control input-xsmall" >
								    
								    <label for="manipulator">操作姓名</label>
								    <input type="text" id="manipulator" name="manipulator" value="${param.manipulator}" class="form-control input-xsmall" >
								    
								    <label for="JZX">集装箱号</label>
								    <input type="text" id="JZX" name="JZX" value="${param.JZX}" class="form-control input-xsmall" >
								    
								    <hr class="search-input-spliter">
								    
								    <label for="TDH">提单号</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-xsmall" >
								    <label for="DZ">到站</label>
								    <input type="text" id="DZ" name="DZ" value="${param.DZ}" class="form-control input-xsmall" >
								    <label for="FZ">发站</label>
								    <input type="text" id="FZ" name="FZ" value="${param.FZ}" class="form-control input-xsmall" >
								    <!--  
								    <label for="CMHC">船次</label>
								    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control input-xsmall" >
								    -->
								    <label for="orderStatus">订单状态</label>
								    <select id="orderStatus" name="orderStatus" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.orderStatus == '未提交'}">selected</c:if> >未提交</option>
								    	<option value="审核中" <c:if test="${param.orderStatus == '审核中'}">selected</c:if> >审核中</option>
								    	<option value="锁定中" <c:if test="${param.orderStatus == '锁定中'}">selected</c:if> >锁定中</option>
								    	<option value="已审核" <c:if test="${param.orderStatus == '已审核'}">selected</c:if> >已审核</option>
								    	<option value="已退回" <c:if test="${param.orderStatus == '已退回'}">selected</c:if> >已退回</option>
								    	<option value="已追回" <c:if test="${param.orderStatus == '已追回'}">selected</c:if> >已追回</option>
								    	<option value="已作废" <c:if test="${param.orderStatus == '已作废'}">selected</c:if> >已作废</option>
								    	<option value="已完成" <c:if test="${param.orderStatus == '已完成'}">selected</c:if> >已完成</option>
								    </select>
								    
								    <label for="expenseStatus">费用状态</label>
								    <select id="expenseStatus" name="expenseStatus" class="form-control">
								    	<option value=""></option>
								    	<option value="未添加" <c:if test="${param.expenseStatus == '未添加'}">selected</c:if> >未添加</option>
								    	<option value="添加中" <c:if test="${param.expenseStatus == '添加中'}">selected</c:if> >添加中</option>
								    	<option value="添加完毕" <c:if test="${param.expenseStatus == '添加完毕'}">selected</c:if> >添加完毕</option>
								    	
								    	<option value="审核中" <c:if test="${param.expenseStatus == '审核中'}">selected</c:if> >审核中</option>
								    	<option value="已审核" <c:if test="${param.expenseStatus == '已审核'}">selected</c:if> >已审核</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BALANCE">
								                    <th class="sorting">收支差</th>
								                    </sec:authorize>
								                    <th class="sorting" name="ORDER_NUMBER">订单编号</th>
								                    <th class="sorting" name="ORDER_STATUS">订单状态</th>
								                    <th class="sorting" name="EXPENSE_STATUS">费用状态</th>
								                    <th class="sorting" name="SALESMAN">业务员</th>
								                    <th class="sorting" name="MANIPULATOR">操作员</th>
								                    <th class="sorting" name="ORDER_SCOPE">业务归属</th>
								                     <th class="sorting" name="FIRST_TYPE">一级类型</th>
								                    <th class="sorting" name="SECOND_TYPE">二级类型</th>
								                    <th class="sorting" name="THIRD_TYPE">三级类型</th>
								                    <th class="sorting" name="FOURTH_TYPE">四级类型</th>
								                    <th class="sorting" name="DELEGATE_PART">委托单位</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="DELEGATE_CONTACT">委托联系人</th>
								                    <th class="sorting" name="COMMISSION">委托书</th>
								                    <th class="sorting" name="CARGO_OWNER">货主</th>
								                    <th class="sorting" name="CARGO_NAME">品名</th>
								                    <th>箱型箱量箱属</th>
								                    <th class="sorting" name="CARGO_AMOUNT">件数</th>
								                    <th class="sorting" name="CARGO_WEIGHT">重量</th>
								                    <th class="sorting" name="CARGO_CAPACITY">体积</th>
								                    <th class="sorting" name="PLACE_TIME">下单时间</th>
								                    <th class="sorting" name="FINISH_TIME">完成时间</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="window.open('fre-order-input-audit.do?id=${item.id}');">
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <sec:authorize ifAnyGranted="ROLE_FRE-ORDER-LIST-BALANCE">
								                    <td id="${item.id}balance">${item.balance}</td>
								                    </sec:authorize>
								                    <td>${item.orderNumber}</td>
								                    <td id="${item.id}orderStatus">${item.orderStatus}</td>
								                    <td id="${item.id}expenseStatus">${item.expenseStatus}</td>
								                    <td>${item.salesman}</td>
								                    <td>${item.manipulator}</td>
								                    <td>${item.orderScope}</td>
								                    <td>${item.firstType}</td>
								                    <td>${item.secondType}</td>
								                    <td>${item.thirdType}</td>
								                    <td>${item.fourthType}</td>
								                    <td>${item.delegatePart}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td>${item.delegateContact}</td>
								                    <td><a href="fre-order-download-commission.do?freightOrderId=${item.id}" target="_blank">委托书</a></td>
								                    <td>${item.cargoOwner}</td>
								                    <td>${item.cargoName}</td>
								                    <td>
								                    	<c:forEach items="${item.freightBoxRequires}" var="freightBoxRequire">
								                    	${freightBoxRequire.boxCount}*${freightBoxRequire.boxType} ${freightBoxRequire.boxBelong}
								                    	</c:forEach>
								                    </td>
								                    <td>${item.cargoAmount}</td>
								                    <td>${item.cargoWeight}</td>
								                    <td>${item.cargoCapacity}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.descn}</td>
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
				<form id="requireAddForm" action="fre-require-save-async.do" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="requireBtnForm" name="requireBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#requireAddForm').submit()">新增</button>
						<button type="button" class="btn btn-sm red" onclick="deleteRequire();">删除</button>
					</form>
				</article>
			
				<!-- 已有箱需 -->
				<article class="m-widget">
				<form id="requireHasAddForm" action="" method="post" class="m-form-blank"></form>
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
						<!--  
						<button type="button" class="btn btn-sm red" onclick="$('#expenseAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm red" onclick="deleteExpense();">删除</button>
						<button type="button" class="btn btn-sm red" onclick="toExpenseAudit();">提交</button>
						<button type="button" class="btn btn-sm red" onclick="reviseExpense();">修改</button>
						<button type="button" class="btn btn-sm green" onclick="finishExpense();">添加完毕</button>
						 
						<button type="button" class="btn btn-sm green" onclick="doneExpenseAuditSingle();">审核通过</button>
						<button type="button" class="btn btn-sm green" onclick="backExpenseAuditSingle();">费用退回</button>
						-->
						<span id="expenseCount" style="margin-left:700px;font-size: 14px;font-weight: 600;"></span>
					</form>
				</article>
			
				<!-- 已有费用 -->
				<article class="m-widget" >
				<form id="expenseHasAddForm" action="" method="post" style="max-height: 300px;overflow-y: scroll;" class="m-form-blank"></form>
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
				<h4 class="modal-title">订单情况说明</h4>
			</div>
			<div class="modal-body">
				<!-- 订单信息 -->
				<article class="m-widget">
				<form id="orderInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				<!-- 添加说明 -->
				<article class="m-widget">
				<form id="finishExpenseForm" action="" method="post" class="m-form-blank">
					<input id="freightOrderId" name="freightOrderId" type="hidden">
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:200px;">
									<label class="td-label" for="descn">情况说明</label>
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
				<button type="button" class="btn btn-sm red" onclick="$('#finishExpenseForm').submit();">确定</button>
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
	 	        'delegatePart': '${param.delegatePart}',
	 	        'salesman': '${param.salesman}',
	 	        'manipulator': '${param.manipulator}',
	 	        'orderStatus': '${param.orderStatus}',
	 	        'expenseStatus': '${param.expenseStatus}',
	 	        'JZX': '${param.JZX}',
    		    'TDH': '${param.TDH}',
   			    'CMHC': '${param.CMHC}',
   			 	'DZ': '${param.DZ}',
   				'FZ': '${param.FZ}'
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
	
	//业务审核完成
	$(document).delegate('#doneOrderAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-order-done-order-audit.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '审核中' && orderStatus != '锁定中'){
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
						alert('审核失败！');
					}else{
						alert('审核成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('审核失败！请确认所选订单状态！');
				return;
			}
		}
	});
	
	//业务审核退回
	$(document).delegate('#backOrderAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-order-back-order-audit.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '审核中' && orderStatus != '锁定中'){
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
						alert('退回失败！');
					}else{
						alert('退回成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('退回失败！请确认所选订单状态！');
				return;
			}
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
							alert('审核失败！请确认订单状态！');
						}else{
							alert('审核成功！');
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
							alert('退回失败！请确认订单状态！');
						}else{
							alert('退回成功！');
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
				$.post('fre-order-modify-descn.do', $("#finishExpenseForm").serialize(), function(data){
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
	
	//作废
	$(document).delegate('#invalidOrder', 'click',function(e){
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
					['起始地','终止地', '集装箱来源', '箱型','箱属','箱量','状态','提单号'], 
					['beginStation','arriveStation','boxSource', 'boxType', 'boxBelong', 'boxCount', 'status', 'blNo']
			);
		}
	});
	
	//查看数据 集装箱列表
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
				//$('#expenseModal #expenseBtnForm').parent().hide();
				$('#expenseModal #orderBoxSelectForm').parent().hide();
				$.ajax({
					url:'fre-expense-to-view.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async: true,
					success:function(data){
						var hasAddData = data.hasAddData;//已经保存的费用
						var freightOrder = data.freightOrder;
						//var freightOrderBoxs = data.freightOrderBoxs;//箱封
						//$('#expenseBtnForm #expenseCount').text(hasAddData == null? '尚未添加费用' : ('已添加 ' + hasAddData.length + ' 条费用'));
						//箱封信息
						/* if(freightOrderBoxs != null && freightOrderBoxs.length > 0){
							html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
							html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
							html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>封号</th><th>集装箱来源</th><th>状态</th></tr></thead><tbody>';
							$.each(freightOrderBoxs, function(i, item){
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" onclick="updateOrderBox();"/></td>';
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
						} */
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
								html += '<td id="'+item.id+'status">' + item.status + '</td>';
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
								html += '<td id="'+item.id+'status">' + item.status + '</td>';
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
		if(addExpense()){
			var margin = (window.screen.availWidth - 1200)/2;
			$('#expenseModal').css("margin-left", margin);
			$('#expenseModal').css("width","1200px");
			$('#expenseModal').modal();
		}
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
				$.ajax({
					url:'${ctx}/fre/fre-expense-by-orderid.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async: true,
					success:function(data){
						var expenseData = data.expenseData;//已经保存的费用
						var requireData = data.requireData;//箱需
						var freightActions = data.freightActions;//订单的所有动作
						var expenseTypes = data.expenseTypes;//费用名称
						var fasInvoiceTypes = data.fasInvoiceTypes;//发票票种
						var freightOrder = data.freightOrder;
						
						$('#expenseBtnForm #expenseCount').text(expenseData == null? '尚未添加费用' : ('已添加 ' + expenseData.length + ' 条费用'));
						
						var html = '<input id="freightOrderId" type="hidden" value="">';
						html += '<input id="freightPriceId" type="hidden" value="">';
						html += '<input id="freightExpenseId" type="hidden" value="">';//用于修改单条费用时使用
						html += '<table id="expenseTable" class="m-table table-striped table-bordered table-hover">';
						html += '<thead><tr>';
						html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱&nbsp;&nbsp;</th><th>箱需(按箱计费必须有箱需)</th><th>币种</th><th>预计金额</th><th>相关动作</th></tr></thead><tbody>';
						html += '<tr><td><select id="freightExpenseTypeId" class="form-control required" onchange="updateExpenseType();"><option value=""></option>';
						$.each(expenseTypes, function(i, item){
							html += '<option value="' + item.id + '">' + item.typeName + '</option>';
						});
						html += '</select></td>';
						html += '<td><select id="incomeOrExpense" name="incomeOrExpense" class="form-control required" ><option value="收">收&nbsp;</option><option value="付">付&nbsp;</option></select></td>';
					   
						html += '<td><select id="freightPartId" class="form-control required" onchange="updateFreightPart();"><option value=""></option>';
					    html += '</select></td>';
					    
					    html += '<td><select id="fasInvoiceTypeId" value="" class="form-control required"><option value=""></option>';
						$.each(fasInvoiceTypes, function(i, item){
							html += '<option value="' + item.id + '">' + item.typeName + '</option>';
						});
						html += '</select></td>';
						
					    html += '<td><select id="countUnit" name="countUnit" class="form-control" ><option value="票">票</option><option value="箱">箱</option></select></td>';
						
						html += '<td><select id="freightBoxRequireId" value="" class="form-control required"><option value=""></option>';
						$.each(requireData, function(i, item){
							html += '<option value="' + item.id + '">' + item.boxCount + '*' + item.boxType + '</option>';
						});
						html += '</select></td>';
						//html += '<td><input id="exchangeRate" name="exchangeRate" value="" class="form-control required" ></td>';
						html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
						html += '<td><input id="predictCount" name="predictCount" value="" class="form-control required number" ></td>';
						
						html += '<td><select id="freightActionId" class="form-control required"><option value=""></option>';
						$.each(freightActions, function(i, item){
							html += '<option value="' + item.id + '">' + item.freightActionType.typeName + '</option>';
						});
						html += '</select></td>';
						
						html += '</tr>'
						$('#expenseAddForm').html(html);
						//vAttrDblClick();//初始化电子词典
						$('#expenseAddForm #freightOrderId').val(freightOrderId);
						
						html = '<table id="expenseTable" class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsExpenseToDelete(this.checked)"/></th>';
						html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱</th><th>箱需</th><th>汇率</th><th>币种</th><th>预计金额</th><th>折合税金</th><th>状态</th><th>相关动作</th></tr></thead><tbody>';
						
						var countIncome = 0;
						$.each(expenseData, function(i, item){
							if(item.incomeOrExpense == '收'){
								countIncome++;
								html += '<tr><td><input class="selectedItemExpenseToDelete a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
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
								html += '<td>' + item.status + '</td>';
								html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td></tr>';
							}
						});
						if(countIncome > 0){
							html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
						}
						var countExpense = 0;
						$.each(expenseData, function(i, item){
							if(item.incomeOrExpense == '付'){
								countExpense++;
								html += '<tr><td><input class="selectedItemExpenseToDelete a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
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
								html += '<td>' + item.status + '</td>';
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
						$('#expenseHasAddForm').html(html);
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	//更改费用类型时,获取有该费用类型的报价
	function updateExpenseType(){
		var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
		$.post('fre-part-by-expensetypeid.do?freightExpenseTypeId=' + freightExpenseTypeId, function(data){
			var html = '<option value=""></option>';
			$.each(data, function(i, item){
				html += '<option value="' + item.id + '">' + item.partName + '</option>';
			});
			
			$('#expenseAddForm #freightPartId').html(html);
		});
	}
	//更改单位时，获取该单位的该费用的成本
	function updateFreightPart(){
		var freightPartId = $('#expenseAddForm #freightPartId').val();
		var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
		var url = 'fre-price-by-partid-expensetypeid.do?freightPartId=' + freightPartId + '&freightExpenseTypeId=' + freightExpenseTypeId;
		$.post(url, function(data){
			$('#expenseAddForm #freightPriceId').val(data.id);
			$('#expenseAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
			$('#expenseAddForm #predictCount').val(data.moneyCount);
			//$('#expenseAddForm #exchangeRate').val(data.exchangeRate);汇率后台自动添加
			$('#expenseAddForm #currency').val(data.currency);
			$('#expenseAddForm #countUnit').val(data.countUnit);
		});
	}
	
	$(function() {
		$("#expenseAddForm").validate({
	        submitHandler: function(form) {
				var freightOrderId = $('#expenseAddForm #freightOrderId').val();
				var freightExpenseTypeId = $('#expenseAddForm #freightExpenseTypeId').val();
				var freightActionId = $('#expenseAddForm #freightActionId').val();
	    		var freightPartId = $('#expenseAddForm #freightPartId').val();
	    		var freightPriceId = $('#expenseAddForm #freightPriceId').val();
	    		var freightBoxRequireId = $('#expenseAddForm #freightBoxRequireId').val();
	    		var fasInvoiceTypeId = $('#expenseAddForm #fasInvoiceTypeId').val();
	    		var freightExpenseId = $('#expenseAddForm #freightExpenseId').val();//修改单条费用时使用
	    		
	    		var countUnit = $('#expenseAddForm #countUnit').val();
	    		if(countUnit == '票'){
	    			freightBoxRequireId = '';//选择按票，则直接让其为空
	    		}else if(freightBoxRequireId == undefined || freightBoxRequireId == ''){
    				alert('选择按箱时，必须选择箱需！');
    				return false;
	    		}
	    		if($('#expenseAddForm #incomeOrExpense').val() == '付' && freightActionId == ''){
	    			alert('所付费用必须关联对应的动作！');
	    			return false;
	    		}else if($('#expenseAddForm #incomeOrExpense').val() == '收'){
	    			freightActionId = '';
	    		}
	    		
				if(freightOrderId == undefined || freightOrderId == ''){
	    			alert('请重新操作！');
	    			return false;
				}
				var data = toJsonString('expenseAddForm');
				var url = 'fre-expense-save-async.do?freightOrderId=' 
						+ freightOrderId + '&freightPartId=' + freightPartId 
						+ '&freightBoxRequireId=' + freightBoxRequireId 
						+ '&freightPriceId=' + freightPriceId 
						+ '&freightExpenseTypeId=' + freightExpenseTypeId
						+ '&freightActionId=' + freightActionId
						+ '&fasInvoiceTypeId=' + fasInvoiceTypeId
						+ '&freightExpenseId=' + freightExpenseId;
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
		var expenseIds = '';
		$('#expenseHasAddForm .selectedItemExpenseToDelete:checked').each(function(i, item){
			expenseIds += $(item).val() + ",";
		});
		if(expenseIds != ''){
			expenseIds = expenseIds.substring(0, expenseIds.length - 1);
		}else{
			return false;//如果没有选择任何费用，则返回了
		}
		var url = 'fre-expense-remove-byorder-async.do?expenseIds=' + expenseIds;
		$.post(url, function(data){
			if(data == 'success'){
				addExpense();
			}
		});
	}
	
	//提交费用
	function toExpenseAudit(){
		if($('#expenseHasAddForm .selectedItemExpenseToDelete:checked').length == 0){
			alert('请选择需要提交审核的费用！');
			return false;
		}else{
			var freightExpenseIds = '';
			$('#expenseHasAddForm .selectedItemExpenseToDelete:checked').each(function(i, item){
				freightExpenseIds += $(item).val() + ",";
			});
			freightExpenseIds = freightExpenseIds.substring(0, freightExpenseIds.length - 1);
			var url = 'fre-expense-to-expense-audit.do?freightExpenseIds=' + freightExpenseIds;
			$.post(url, function(data){
				if(data == 'success'){
					alert('提交成功！');
					addExpense();
				}
			});
		}
	}
	
	//修改费用
	function reviseExpense(){
		if($('#expenseHasAddForm .selectedItemExpenseToDelete:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var freightExpenseId = $('#expenseHasAddForm .selectedItemExpenseToDelete:checked').val();
			var url = 'fre-expense-revise.do?freightExpenseId=' + freightExpenseId;
			$.post(url, function(data){
				if(addExpense()){
					$('#expenseAddForm #freightExpenseId').val(data.id);
					$('#expenseAddForm #freightExpenseTypeId').val(data.freightExpenseType.id);
					$('#expenseAddForm #freightPartId').html('<option value="'+data.freightPartB.id+'">'+data.freightPartB.partName+'</option>');
					$('#expenseAddForm #freightPriceId').val(data.freightPrice.id);
					if(data.incomeOrExpense == '付' && data.freightAction != null){
						$('#expenseAddForm #freightActionId').val(data.freightAction.id);
					}
					if(data.countUnit == '箱'){
						$('#expenseAddForm #freightBoxRequireId').html('<option value="'+data.freightBoxRequire.id+'">'+data.freightBoxRequire.boxCount + '*' + data.freightBoxRequire.boxType+'</option>');
					}
					$('#expenseAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
					$('#expenseAddForm #incomeOrExpense').val(data.incomeOrExpense);
					$('#expenseAddForm #predictCount').val(data.predictCount);
					$('#expenseAddForm #currency').val(data.currency);
					$('#expenseAddForm #countUnit').val(data.countUnit);
				}
			});
		}
	}
	
	//选择需要删除的费用
	function toggleSelectedItemsExpenseToDelete(isChecked) {
		$(".selectedItemExpenseToDelete").each(function(index, el) {
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
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~箱需
	//添加箱需
	$(document).delegate('#addRequire', 'click',function(e){
		if(addRequire()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#requireModal').css("margin-left", margin);
			$('#requireModal').css("width","1000px");
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
			if(orderStatus != '未提交' && orderStatus != '已退回'){
				alert('非未提交或已退回的订单不能添加箱需！');
				return false;
			}else{
				$.ajax({
					url:'${ctx}/fre/fre-box-require-get-byorderid.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						
						var html = '<input id="freightOrderId" type="hidden" value="">';
						html += '<table id="requireTable" class="m-table table-bordered table-hover">';
						html += '<thead><tr><th>起始地</th><th>目的地</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>说明</th></tr></thead><tbody>';
						html += '<tr><td><input id="beginStation" name="beginStation" value="" class="form-control required dictionary" vAttrId="5a4875ab-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="arriveStation" name="arriveStation" value="" class="form-control required dictionary" vAttrId="5a4875ab-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxType" name="boxType" value="" class="form-control required dictionary" vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxBelong" name="boxBelong" value="" class="form-control required dictionary" vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxCondition" name="boxCondition" value="" class="form-control required dictionary" vAttrId="5a488f04-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxCount" name="boxCount" value="" class="form-control required number"></td>';
						html += '<td><input id="descn" name="descn" value="" class="form-control required"></td>';
						html += '</tr>'
						
						$('#requireAddForm').html(html);
						vAttrDblClick();//初始化电子词典
						$('#freightOrderId').val(freightOrderId);
						
						var htmlList = '<table id="requireTable" class="m-table table-bordered table-hover">';
						htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsRequireToDelete(this.checked)"/></th>';
						htmlList += '<th>起始地</th><th>目的地</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>说明</th></tr></thead><tbody>';
						
						$.each(data, function(i, item){
							htmlList += '<tr><td><input class="selectedItemRequireToDelete a-check" type="checkbox" name="requireIds" value="'+item.id+'" /></td>';
							htmlList += '<td>' + item.beginStation + '</td>';
							htmlList += '<td>' + item.arriveStation + '</td>';
							htmlList += '<td>' + item.boxType + '</td>';
							htmlList += '<td>' + item.boxBelong + '</td>';
							htmlList += '<td>' + item.boxCondition + '</td>';
							htmlList += '<td>' + item.boxCount + '</td>';
							htmlList += '<td>' + item.descn + '</td>';
						});
						htmlList += "</tbody></table>";
						$('#requireHasAddForm').html(htmlList);
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	
	$(function() {
		$("#requireAddForm").validate({
	        submitHandler: function(form) {
				var freightOrderId = $('#requireAddForm #freightOrderId').val();
				if(freightOrderId == undefined || freightOrderId == ''){
	    			alert('请重新操作!');
	    			return false;
				}
				var data = toJsonString('requireAddForm');
				var url = 'fre-box-require-save-async.do?freightOrderId=' + freightOrderId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addRequire();
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除箱需
	function deleteRequire(){
		var requireIds = '';
		$('#requireHasAddForm .selectedItemRequireToDelete:checked').each(function(i, item){
			requireIds += $(item).val() + ",";
		});
		if(requireIds != ''){
			requireIds = requireIds.substring(0, requireIds.length - 1);
		}else{
			return false;//如果没有选择任何费用，则返回了
		}
		var url = 'fre-box-require-remove-byorder-async.do?requireIds=' + requireIds;
		$.post(url, function(data){
			if(data == 'success'){
				addRequire();
			}
		});
	}
	//选择需要删除的箱需
	function toggleSelectedItemsRequireToDelete(isChecked) {
		$(".selectedItemRequireToDelete").each(function(index, el) {
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
	
	
	//批量检查是否可删除
	function validBatchForRemove(){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var orderStatus = $('#' + $(item).val() + 'orderStatus').text();
				if(orderStatus != '未提交' && orderStatus != '已退回'){
					alert('删除失败！请确认所选订单状态！');
					flag = false;
				}
			});
			return flag;
		}
	}
	
	//费用审核
	function doneExpenseAuditSingle(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-expense-done-expense-audit.do?';
			var flag = true;
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '审核中' && status != '审核中(一般异常)' && status != '初审中(特殊异常)' && status != '初审中(特殊费用)'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightExpenseId=' +　$(item).val();
					}else{
						url += '&freightExpenseId=' +　$(item).val();
					}
				}
			});
			if(flag){
					$.post(url, function(data){
						if(data != 'success'){
							alert('审核失败！');
						}else{
							alert('审核成功！');
							viewExpense();
						} 
					});
			}else{
				alert('审核失败！请确认所选费用状态！');
				return;
			}
		}
	}
	
	
	//费用退回
	function backExpenseAuditSingle(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-expense-back-expense-audit.do?';
			var flag = true;
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '审核中' && status != '审核中(一般异常)' && status != '初审中(特殊异常)' && status != '初审中(特殊费用)'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightExpenseId=' +　$(item).val();
					}else{
						url += '&freightExpenseId=' +　$(item).val();
					}
				}
			});
			if(flag){
					$.post(url, function(data){
						if(data != 'success'){
							alert('退回失败！');
						}else{
							alert('退回成功！');
							viewExpense();
						} 
					});
			}else{
				alert('退回失败！请确认所选费用状态！');
				return;
			}
		}
	}
	
	
	$(document).delegate('#toBatchOrderExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length > 0){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('fre-order-to-export-batch-order-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('订单收支差列表.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择一条数据！');
		}
		
	});
	
	//取消费用添加完毕
	$(document).delegate('#recallCompleteExpenseWithDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-order-recall-complete-expense-with-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus !='添加完毕'){
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
				alert('提交失败！请确认订单状态！');
				return;
			}
		}
	});
	
	//取消费用添加完毕
	$(document).delegate('#recallCompleteExpenseWithoutDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-order-recall-complete-expense-without-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var expenseStatus = $('#' + $(item).val() + 'expenseStatus').text();
				if(expenseStatus !='添加完毕'){
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
				alert('提交失败！请确认订单状态！');
				return;
			}
		}
	});
    </script>
  </body>

</html>
