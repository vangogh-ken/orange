<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>费用核对(商务)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>费用核对(商务)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" id="reviseActualCount">
											修改费用</button>
									<button class="btn btn-sm red" id="reviseBatchActualCount">
											批量修改</button>
											
									<button class="btn btn-sm green" id="specialExpenseConfirm">
											标记特殊</button>
									<button class="btn btn-sm green" id="specialExpenseRecall">
											标记取消</button>
									<!-- 财务副经理和管理员 -->
									<sec:authorize ifAnyGranted="ROLE_CY-FAS-MANAGER-P,ROLE_CY-SYS-MANAGER">
									<button class="btn btn-sm green" id="specialExpenseCreate">
											特殊处理</button>
									</sec:authorize>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
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
								<form name="searchForm" method="post" action="fre-expense-list-valid-commerce.do" class="form-inline">
									<label for="typeName">费用</label>
								    <input type="text" id="typeName" name="typeName" value="${param.typeName}" class="form-control input-xsmall">
								    
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    
								    <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control input-xsmall">
								    
								    <label for="boxNumber">箱号</label>
								    <input type="text" id="boxNumber" name="boxNumber" value="${param.boxNumber}" class="form-control input-xsmall">
								    
								    <label for="TDH">提单号</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-xsmall">
								    <!--  
								    <label for="CMHC">船名航次</label>
								    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control input-xsmall">
								    -->
								    <label for="NB">NB</label>
								    <select id="NB" name="NB" class="form-control">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.NB == 'T' }">selected</c:if> >T</option>
								    	<option value="F" <c:if test="${param.NB == 'F' }">selected</c:if> >F</option>
								    </select>
								    
								    <label for="incomeOrExpense">收支</label>
								    <select id="incomeOrExpense" name="incomeOrExpense" class="form-control">
								    	<option value=""></option>
								    	<option value="收" <c:if test="${param.incomeOrExpense == '收' }">selected</c:if> >收</option>
								    	<option value="付" <c:if test="${param.incomeOrExpense == '付' }">selected</c:if> >付</option>
								    </select>
								    
								    <label for="TS">TS</label>
								    <select id="TS" name="TS" class="form-control">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.TS == 'T' }">selected</c:if> >已标记</option>
								    	<option value="F" <c:if test="${param.TS == 'F' }">selected</c:if> >已处理</option>
								    	<option value="TS" <c:if test="${param.TS == 'TS' }">selected</c:if> >新生成</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="审核中" <c:if test="${param.status == '审核中' }">selected</c:if> >审核中</option>
								    	<option value="已审核" <c:if test="${param.status == '已审核' }">selected</c:if> >已审核</option>
								    	<option value="已对账" <c:if test="${param.status == '已对账' }">selected</c:if> >已对账</option>
								    	<option value="异常费用" <c:if test="${param.status == '异常费用' }">selected</c:if> >异常费用</option>
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
								                    <th class="sorting" name="EXPENSE_NUMBER">费用编号</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="FRE_EXPENSE_TYPE_ID">费用名称</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE">票种</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="FRE_ORDER_ID">订单号</th>
								                    <th class="sorting" name="FRE_PART_ID_B">收付单位</th>
								                    <th class="sorting" name="FRE_STATEMENT_ID">账单号</th>
								                    <th class="sorting" name="COUNT_UNIT">计价</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="PREDICT_COUNT">预计金额</th>
								                    <th class="sorting" name="PREDICT_COUNT">预计总额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">实际金额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">实际总额</th>
								                    <th class="sorting" name="FRE_PRICE_ID">系统报价</th>
								                    <th class="sorting" name="PRICE">特殊费用</th>
								                    <th>实收付</th>
								                    <th class="sorting" name="PRICE">是否与报价一致</th>
								                    <th class="sorting" name="ACTUAL">是否与实际一致</th>
								                    <th>提单号</th>
								                    <th>箱号</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr <c:if test="${item.status == '异常费用'}">style="border-bottom:2px solid red;"</c:if> <c:if test="${(item.status == '已审核' || item.status == '已对账') && item.freightPrice.actual != null && (item.freightPrice.actual == 'T' || item.freightPrice.actual == 'F')}">style="border-bottom:2px solid green;"</c:if>>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.expenseNumber}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.freightExpenseType.typeName}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td id="${item.id}incomeOrExpense">${item.incomeOrExpense}</td>
								                    <td>${item.freightOrder == null ? (item.expenseNumber.contains('-TS-') ? '特殊费用' : '内部费用') : item.freightOrder.orderNumber}</td>
								                    <td>${item.freightPartB.partName}</td>
								                    <td>${item.freightStatement.statementNumber}</td>
								                    <td>${item.countUnit}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.predictCount}</td>
								                    <td>${item.predictAmount}</td>
								                    <td>${item.actualCount}</td>
								                    <td>${item.actualAmount}</td>
								                    <td>${item.freightPrice.moneyCount}</td>
								                    <td id="${item.id}special">${item.freightPrice.actual}</td>
								                    <td>${item.freightPrice.actualCount}</td>
								                    <td>${item.price}</td>
								                    <td>${item.actual}</td>
								                    <td>${item.blNumber}</td>
								                    <td>${item.boxNumber}</td>
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
	
	<!-- 费用修改 -->
	<div class="modal fade" id="expenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">费用</h4>
			</div>
			<!-- 添加费用 -->
			<div class="modal-body">
				<article class="m-widget">
				<form id="expenseReviseForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#expenseReviseForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 特殊费用标记 -->
	<div class="modal fade" id="specialExpenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">特殊费用</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="specialExpenseForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#specialExpenseForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 批量费用修改 -->
	<div class="modal fade" id="expenseBatchModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量修改费用</h4>
			</div>
			<!-- 修改费用 -->
			<div class="modal-body">
				<article class="m-widget">
				<form id="expenseBatchReviseActualForm" action="" method="post" class="m-form-blank">
					<table class="table-input">
						<tbody>
							<tr>
								<td><label for="actualCount">实际费用</label></td>
								<td><input id="actualCount" name="actualCount" value="" class="form-control required number"></td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#expenseBatchReviseActualForm').submit();">确定</button>
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
	 	    	'typeName': '${param.typeName}',
	 	        'orderNumber': '${param.orderNumber}',
	 	        'partName': '${param.partName}',
	 	        'boxNumber': '${param.boxNumber}',
	 	        'TDH': '${param.TDH}',
	 	        'CMHC': '${param.CMHC}',
	 	        'NB': '${param.NB}',
	 	        'incomeOrExpense': '${param.incomeOrExpense}',
	 	        'TS': '${param.TS}',
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
	
	//财务审核，对实际费用进行修改	
	$(document).delegate('#reviseActualCount', 'click',function(e){
		reviseActualCount();
	});
	//财务审核，对实际费用进行修改	
	function reviseActualCount(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightExpenseId = $('.selectedItem:checked').val();
			var flag = false;
			$.post('fre-expense-by-expenseid.do?freightExpenseId=' + freightExpenseId, function(data){
				if(data.status != '已审核'){
					alert('不能修改此费用！');
					flag =  false;
				}else{
					var html ='<input id="id" name="id" type="hidden" value="' + freightExpenseId + '">';
					html += '<table id="expenseReviseTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱&nbsp;&nbsp;</th><th>箱需</th><th>汇率</th><th>币种</th><th>预计金额</th><th>实际金额</th><th>相关动作</th></tr></thead><tbody><tr>';
					html += '<td>' + data.freightExpenseType.typeName + '</td>';
					html += '<td>' + data.incomeOrExpense + '</td>';
					html += '<td>' + (data.freightPartB == null ? '空':data.freightPartB.partName) + '</td>';
					html += '<td>' + data.countUnit + '</td>';
					html += '<td>' + (data.freightBoxRequire == null ?'无': (data.freightBoxRequire.boxCount + '箱  ' + data.freightBoxRequire.arriveStation + '_' + data.freightBoxRequire.arriveStation)) + '</td>';
					html += '<td>' + data.exchangeRate + '</td>';
					html += '<td>' + data.currency + '</td>';
					//html += '<td>' + data.predictCount + '</td>';
					html += '<td><input id="predictCount" name="predictCount" value="' + data.predictCount + '" class="form-control required number" readonly></td>';
					html += '<td><input id="actualCount" name="actualCount" value="' + data.actualCount + '" class="form-control required number"></td>';
					html += '<td>' + (data.freightAction == null ? '无' : (data.freightAction.freightMaintain.freightMaintainType.typeName + '>' + data.freightAction.freightActionType.typeName)) + '</td></tr></tbody></table>';
					$('#expenseReviseForm').html(html);
					
					var margin = (window.screen.availWidth - 1000)/2;
					$('#expenseModal').css("margin-left", margin);
					$('#expenseModal').css("width","1000px");
					$('#expenseModal').modal();
				}
			});
			return flag;
		}
	}
	//财务审核，对实际费用进行修改
	$(function() {
		$('#expenseReviseForm').validate({
	        submitHandler: function(form) {
	        	$('#expenseModal').modal('hide');
	        	bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.ajax({
	    			type: 'POST',
	    			data: toJsonString('expenseReviseForm'),
	    			url: 'fre-expense-revise-actual.do',
	    			contentType: 'application/json',
	    			success:function(data){
	    				window.location.href = window.location.href;
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//对特殊费用进行标记	
	$(document).delegate('#specialExpenseConfirm', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
		}else{
			var flag = true;
			var url = 'fre-expense-to-batch-special.do';
			var data = '';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var incomeOrExpense = $('#' + $(item).val() + 'incomeOrExpense').text();
				if(incomeOrExpense == '付' && status == '已审核'){
					if(i == 0){
						data += 'freightExpenseId=' + $(item).val();
					}else{
						data += '&freightExpenseId=' + $(item).val();
					}
				}else{
					flag = false;
					return false;
				}
			});
			
			if(flag){
				$.post(url, data, function(data){
					var result = data.result;
					if(result == 'success'){
						var freightExpense = data.freightExpense;
						var html = '<table id="expenseReviseTable" class="m-table table-bordered table-hover">';
						html += '<thead><tr>';
						html += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计金额</th><th>实际金额</th><th>成本实际金额</th></tr></thead><tbody><tr>';
						html += '<td>' + freightExpense.freightExpenseType.typeName + '</td>';
						html += '<td>' + freightExpense.incomeOrExpense + '</td>';
						html += '<td>' + (freightExpense.freightPartB.partName) + '</td>';
						html += '<td>' + freightExpense.countUnit + '</td>';
						html += '<td>' + freightExpense.exchangeRate + '</td>';
						html += '<td>' + freightExpense.currency + '</td>';
						html += '<td>' + freightExpense.predictCount + '</td>';
						html += '<td>' + freightExpense.actualCount + '</td>';
						html += '<td><input id="actualCount" name="actualCount" value="" class="form-control required number"></td>';
						html += '</tr></tbody></table>';
						$('#specialExpenseForm').html(html);
						
						var margin = (window.screen.availWidth - 1000)/2;
						$('#specialExpenseModal').css("margin-left", margin);
						$('#specialExpenseModal').css("margin-top", 100);
						$('#specialExpenseModal').css("width","1000px");
						$('#specialExpenseModal').modal();
					}else{
						alert('请确认所选费用状态、收付、单位、票种、金额等是否一致！');
					}
				});
			}else{
				alert('请确认所选费用状态、收付、单位、票种、金额等是否一致！');
			}
		}
	});
	
	$(function() {
		$('#specialExpenseForm').validate({
	        submitHandler: function(form) {
	        	$('#specialExpenseModal').modal('hide');
	        	bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
    			var data = '';
    			$('.selectedItem:checked').each(function(i, item){
   					if(i == 0){
   						data += 'freightExpenseId=' + $(item).val();
   					}else{
   						data += '&freightExpenseId=' + $(item).val();
   					}
    			});
    			data += '&actualCount=' + $('#specialExpenseForm #actualCount').val();
    			
    			$.post('fre-expense-done-batch-special.do', data, function(data){
    				if(data == 'success'){
    					alert('标记成功');
    					$('#specialExpenseModal').modal('hide');
    					window.location.href = window.location.href;
    				}else{
    					alert('标记失败');
    				}
    			});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//批量修改
	$(document).delegate('#reviseBatchActualCount', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择一条数据!');
			return false;
		}else{
			//先判断是否符合批量修改的要求：同单位，同费用，同收付，同状态，业务核对的费用必然是没有通过审批的费用
			$.post('fre-expense-valid-batch.do', $('#dynamicGridForm').serialize(), function(data){
				if(data == 'success'){
					var margin = (window.screen.availWidth - 600)/2;
					$('#expenseBatchModal').css("margin-left", margin);
					$('#expenseBatchModal').css("margin-top", 100);
					$('#expenseBatchModal').css("width","600px");
					$('#expenseBatchModal').modal();
				}else{
					alert('所选择的费用不能批量修改！');
				}
			});
		}
	});
	//提交批量修改
	$(function() {
		$('#expenseBatchReviseActualForm').validate({
	        submitHandler: function(form) {
	        	$('#expenseBatchModal').modal('hide');
	        	//bootbox.animate(false);
    			//var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
    			$.post('fre-expense-revise-actual-batch.do', 
    					$('#dynamicGridForm').serialize() + '&' + $('#expenseBatchReviseActualForm').serialize(),
    					function(data){
    				if(data == 'success'){
    					alert('批量修改成功！');
    					window.location.href = window.location.href;
    				}else{
    					//bootbox.modal('hide');
    					alert('批量修改失败！');
    				}
    			});
	        },
	        errorClass: 'validate-error'
		});
	});
	
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
	
	//生成特殊费用，只有已经账的费用才能生成特殊费用。
	$(document).delegate('#specialExpenseCreate', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'fre-expense-create-expense-special.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var special = $('#' + $(item).val() + 'special').text();
				if(status == '已对账' && special == 'T'){
					if(i == 0){
						url += 'freightExpenseId=' +　$(item).val();
					}else{
						url += '&freightExpenseId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认该费用生成特殊费用？')){
					bootbox.animate(false);
	    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
					$.post(url, function(data){
						if(data != 'success'){
							alert('生成失败！请确认费用状态！');
						}else{
							alert('生成成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('特殊处理失败，请确认费用状态！');
			}
		}
	});
	//取消特殊费用的标记，只有在已审核的费用，尚未对账，尚未做特殊费用处理的才能取消标记
	$(document).delegate('#specialExpenseRecall', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'fre-expense-done-recall-special.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var special = $('#' + $(item).val() + 'special').text();
				if(status == '已审核' && special == 'T'){
					if(i == 0){
						url += 'freightExpenseId=' +　$(item).val();
					}else{
						url += '&freightExpenseId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认取消该费用的特殊标记？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('取消失败！请确认费用状态！');
						}else{
							alert('取消成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('取消特殊标记失败，请确认费用状态！');
			}
		}
	});
    </script>
  </body>

</html>
