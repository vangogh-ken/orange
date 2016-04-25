<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>账单填报(客服)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>账单填报(客服)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='fre-statement-input-service.do'">
											新增<i class="fa fa-arrows"></i></button>
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										<button class="btn btn-sm green" id="viewExpense" >
											费用明细</button>
										<button class="btn btn-sm green" id="toAddExpense">
											添加费用</button>
										<button class="btn btn-sm red" id="viewInvoice" >
											发票明细</button>
										<button class="btn btn-sm red" id="applyReleaseBtn">
											申请开票</button>
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
								<form name="searchForm" method="post" action="fre-statement-list-service.do" class="form-inline">
								    <label for="statementNumber">账单号</label>
								    <input type="text" id="statementNumber" name="statementNumber" value="${param.statementNumber}" class="form-control">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control">
								    <label for="partName">单位名称</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.status == '未提交' }">selected</c:if> >未提交</option>
								    	<option value="冲抵过" <c:if test="${param.status == '冲抵过' }">selected</c:if> >冲抵过</option>
								    	<option value="待开票" <c:if test="${param.status == '待开票' }">selected</c:if> >待开票</option>
								    	<option value="开票中" <c:if test="${param.status == '开票中' }">selected</c:if> >开票中</option>
								    	<option value="已开票" <c:if test="${param.status == '已开票' }">selected</c:if> >已开票</option>
								    	<option value="待销账" <c:if test="${param.status == '待销账' }">selected</c:if> >待销账</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
								    	<option value="已冲抵销账" <c:if test="${param.status == '已冲抵销账' }">selected</c:if> >已冲抵销账</option>
								    	<option value="已作废" <c:if test="${param.status == '已作废' }">selected</c:if> >已作废</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-statement-remove-service.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="STATEMENT_NUMBER">账单号</th>
								                    <th class="sorting" name="FRE_PART_ID">收付单位</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE">票种</th>
								                    <th class="sorting" name="STATUS">状态</th>
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
								                    <td>${item.freightPart.partName}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td id="${item.id}status">${item.status}</td>
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
				<article class="m-widget">
					<form id="expenseSearchForm" name="expenseSearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="DDH">订单号</label>
					    <input type="text" id="DDH" name="DDH" value="${param.DDH}" class="form-control">
					    
					    <label for="FYMC">费用名称</label>
					    <input type="text" id="FYMC" name="FYMC" value="${param.FYMC}" class="form-control">
					    
					    <label for="XH">箱号</label>
					    <input type="text" id="XH" name="XH" value="${param.XH}" class="form-control">
					    
					    <label for="TDH">提单号</label>
					    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control">
					    
					    <label for="CMHC">船名航次</label>
					    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control">
					    
						<button class="btn btn-sm red" onclick="toAddExpense();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				
				<article class="m-widget" style="height: 170px; overflow-y: scroll;">
				<form id="expenseToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="expenseBtnForm" name="expenseBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="doneAddExpense();">添加</button>
						<button class="btn btn-sm red" onclick="doneDeleteExpense();">删除</button>
					</form>
				</article>
				
				<article class="m-widget" style="height: 160px; overflow-y: scroll;">
				<form id="expenseHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	$(document).delegate('#toAddExpense', 'click', function(e){
		if(toAddExpense()){
			var margin = (window.screen.availWidth - 1200)/2;
			$('#expenseModal').css("margin-left", margin);
			$('#expenseModal').css("width","1200px");
			$('#expenseModal').modal();
		}
	});
	
	function toAddExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#'+$('.selectedItem:checked').val()+'status').text();
			if(status != '未提交'){
				alert(status + '状态中的账单不能添加费用！');
				return false;
			}else{
				var freightStatementId = $('.selectedItem:checked').val();
				var url = 'fre-statement-to-add-expense-service.do?freightStatementId=' + freightStatementId;
				$.post(url, $('#expenseSearchForm').serialize(), function(data){
					var toAddData = data.toAddData;//可选的费用
					var hasAddData = data.hasAddData;//已关联的费用
					
					var htmlList = '<table id="expenseTable" class="m-table table-bordered table-hover">';
					htmlList += '<thead><tr>';
					htmlList += '<th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					htmlList += '<th>费用编号</th><th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>实际单价</th><th>实际总额</th>';
					htmlList += '</tr></thead><tbody>';
					$.each(toAddData, function(i, item){
						htmlList += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						htmlList += '<td>' + item.expenseNumber + '</td>';
						htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
						htmlList += '<td>' + item.incomeOrExpense + '</td>';
						htmlList += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						htmlList += '<td>' + item.countUnit + '</td>';
						htmlList += '<td>' + item.exchangeRate + '</td>';
						htmlList += '<td>' + item.currency + '</td>';
						htmlList += '<td>' + item.predictCount + '</td>';
						htmlList += '<td>' + item.predictAmount + '</td>';
						htmlList += '<td>' + item.actualCount + '</td>';
						htmlList += '<td>' + item.actualAmount + '</td></tr>';
					});
					htmlList += "</tbody></table>";
					$('#expenseToAddForm').html(htmlList);
					
					htmlList = '';//重置
					htmlList += '<table id="expenseTable" class="m-table table-bordered table-hover">';
					htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					htmlList += '<th>费用编号</th><th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>实际单价</th><th>实际总额</th>';
					htmlList += '</tr></thead><tbody>';
					$.each(hasAddData, function(i, item){
						htmlList += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						htmlList += '<td>' + item.expenseNumber + '</td>';
						htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
						htmlList += '<td>' + item.incomeOrExpense + '</td>';
						htmlList += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						htmlList += '<td>' + item.countUnit + '</td>';
						htmlList += '<td>' + item.exchangeRate + '</td>';
						htmlList += '<td>' + item.currency + '</td>';
						htmlList += '<td>' + item.predictCount + '</td>';
						htmlList += '<td>' + item.predictAmount + '</td>';
						htmlList += '<td>' + item.actualCount + '</td>';
						htmlList += '<td>' + item.actualAmount + '</td></tr>';
					});
					htmlList += "</tbody></table>";
					$('#expenseHasAddForm').html(htmlList);
				});
			}
			return true;
		}
	}
	
	$(document).delegate('#viewExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-expense-get-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '关联费用', 1200,
					['编号', '费用名称','收/付','收付单位','票/箱','汇率','币种','预计单价','预计总额','实际单价','实际总额'], 
					['expenseNumber', 'freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','predictAmount','actualCount','actualAmount']
			);
		}
	});

	$(document).delegate('#viewInvoice', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-invoice-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '已关联发票', 1000,
					['发票号','票种','收付单位','收/付','汇率','币种','金额','状态','开票时间'], 
					['fasInvoice.invoiceNumber','fasInvoiceType.typeName', 'freightPart.partName' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount','status', 'releaseTime']
			);
		}
	});
	
	//提交费用
	function doneAddExpense(){
		if($('#expenseToAddForm .selectedItemId:checked').length > 0){
			var url = 'fre-statement-done-add-expense.do?freightStatementId=' + $('.selectedItem:checked').val();
			$('#expenseToAddForm .selectedItemId:checked').each(function(i, item){
				url += '&freightExpenseId=' + $(item).val();
			});
			
			$.post(url, function(data){
				if(data == 'success'){
					toAddExpense();
				}
			});
		}else{
			alert('请选择数据！');
		}
	}
	
	function doneDeleteExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length > 0){
			var url = 'fre-statement-done-delete-expense.do?';
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i ==0 ){
					url += 'freightExpenseId=' + $(item).val();
				}else{
					url += '&freightExpenseId=' + $(item).val();
				}
				
			});
			
			$.post(url, function(data){
				if(data == 'success'){
					toAddExpense();
				}
			});
		}else{
			alert('请选择数据！');
		}
	}
	
	//申请开票，确认账单信息，并填写备注；此处不采取批量，因为需要填写说明。
	$(document).delegate('#applyReleaseBtn', 'click', function(e){
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
	        	$('#applyReleaseModal').modal('hide');
	        	bootbox.animate(false);
	    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
	    		$.post('fre-statement-to-release.do', $('#applyReleaseForm').serialize(), function(data){
	        		if(data == 'success' ){
	        			alert('申请成功！');
	        			window.location.href = window.location.href;
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
	
    </script>
  </body>

</html>
