<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>对账管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>对账管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='fre-statement-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm red" id="viewExpense" >
											费用<i class="fa fa-search"></i></button>
											
										<button class="btn btn-sm red" id="addExpense">
											添加费用</button>
										
										<button class="btn btn-sm red" id="viewInvoice" >
											发票<i class="fa fa-search"></i></button>
											
										<button class="btn btn-sm red" id="addInvoice">
											添加发票</button>
										
										<button class="btn btn-sm red" id="compensateStatement">
											账单冲抵</button>
											
										<button class="btn btn-sm red" id="compensateInvoice">
											发票冲抵</button>
										
										<button class="btn btn-sm red" id="applyToReleaseByStatement">
											申请开票</button>
											
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-EXPORT">
									</sec:authorize>
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
								<form name="searchForm" method="post" action="fre-statement-list.do" class="form-inline">
								    <label for="partName">单位名称</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="partType">类型</label>
								    <input type="text" id="partType" name="partType" value="${param.partType}" class="form-control">
								    
								    <label for="status">状态</label>
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-statement-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="STATEMENT_NUMBER">对账单号</th>
								                    <th class="sorting" name="FRE_PART_ID">收付单位</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSES">收/付</th>
								                    <th class="sorting" name="MONEY_COUNT_RMB">应收/付金额(RMB)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_RMB">已开票金额(RMB)</th>
								                    <th class="sorting" name="REMAIN_COUNT_RMB">未开票金额(RMB)</th>
								                    <th class="sorting" name="MONEY_COUNT_DOLLAR">应收/付金额(USD)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_DOLLAR">已开票金额(USD)</th>
								                    <th class="sorting" name="REMAIN_COUNT_DOLLAR">未开票金额(USD)</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.statementNumber}</td>
								                    <td>${item.freightPart.partName}</td>
								                    <td>${item.incomeOrExpense}</td>
								                    <td>${item.moneyCountRmb}</td>
								                    <td>${item.eliminateCountRmb}</td>
								                    <td>${item.remainCountRmb}</td>
								                    <td>${item.moneyCountDollar}</td>
								                    <td>${item.eliminateCountDollar}</td>
								                    <td>${item.remainCountDollar}</td>
								                    <td>${item.status}</td>
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
				<h4 class="modal-title">添加费用</h4>
			</div>
			<div class="modal-body">
				<!--  这个查询不需要, 因为获取费用的时候已经自动进行过滤了
				<article class="m-widget">
					<form id="expenseSearchForm" name="expenseSearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="partName">单位名称</label>
						<input type="text" id="partName" name="partName" class="form-control">
						<label for="incomeOrExpenses">收/付</label>
						<input type="text" id=incomeOrExpenses name="incomeOrExpenses" class="form-control">
						<button class="btn btn-sm red" onclick="getExpenseList();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				 -->
				<article class="m-widget">
				<form id="expenseToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="expenseBtnForm" name="expenseBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="submitExpense();">添加</button>
						<button class="btn btn-sm red" onclick="deleteExpense();">删除</button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="expenseHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 关联发票 -->
	<div class="modal fade" id="invoiceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加发票</h4>
			</div>
			<div class="modal-body">
				<!-- 对账单信息 -->
				<article class="m-widget">
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<!-- 添加发票-->
				<article class="m-widget">
				<form id="invoiceToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="invoiceBtnForm" name="invoiceBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#invoiceToAddForm').submit();">添加</button>
						<button class="btn btn-sm red" onclick="deleteInvoice();">删除</button>
					</form>
				</article>
				
				<!-- 已添加发票-->
				<article class="m-widget">
				<form id="invoiceHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	        'partType': '${param.partType}',
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
	});

/////////////////////费用
	$(document).delegate('#addExpense', 'click', function(e){
		if(addExpense()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#expenseModal').css("margin-left", margin);
			$('#expenseModal').css("width","1000px");
			$('#expenseModal').modal();
		}
	});
	
	function addExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-expense-by-statementid.do?freightStatementId=' + freightStatementId;
			$.post(url, $('#searchFormExpense').serialize(), function(data){
				var toAddData = data.toAddData;//可选的费用
				var hasAddData = data.hasAddData;//已关联的费用
				
				var htmlList = '<table id="expenseTable" class="m-table table-bordered table-hover">';
				htmlList += '<thead><tr>';
				htmlList += '<th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsExpense(this.checked)"/></th>';
				htmlList += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计金额</th><th>实际金额</th>';
				htmlList += '</tr></thead><tbody>';
				$.each(toAddData, function(i, item){
					htmlList += '<tr><td><input class="selectedItemExpense a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
					htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
					htmlList += '<td>' + item.incomeOrExpense + '</td>';
					htmlList += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
					htmlList += '<td>' + item.countUnit + '</td>';
					htmlList += '<td>' + item.exchangeRate + '</td>';
					htmlList += '<td>' + item.currency + '</td>';
					htmlList += '<td>' + item.predictCount + '</td>';
					htmlList += '<td>' + item.actualCount + '</td></tr>';
				});
				htmlList += "</tbody></table>";
				$('#expenseToAddForm').html(htmlList);
				
				htmlList = '';//重置
				htmlList += '<table id="expenseTable" class="m-table table-bordered table-hover">';
				htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsExpenseToDelete(this.checked)"/></th>';
				htmlList += '<th>费用名称</th><th>收/付</th><th>收付单位</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计金额</th><th>实际金额</th>';
				htmlList += '</tr></thead><tbody>';
				$.each(hasAddData, function(i, item){
					htmlList += '<tr><td><input class="selectedItemExpenseToDelete a-check" type="checkbox" name="invoiceIds" value="'+item.id+'" /></td>';
					htmlList += '<td>' + item.freightExpenseType.typeName + '</td>';
					htmlList += '<td>' + item.incomeOrExpense + '</td>';
					htmlList += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
					htmlList += '<td>' + item.countUnit + '</td>';
					htmlList += '<td>' + item.exchangeRate + '</td>';
					htmlList += '<td>' + item.currency + '</td>';
					htmlList += '<td>' + item.predictCount + '</td>';
					htmlList += '<td>' + item.actualCount + '</td></tr>';
				});
				htmlList += "</tbody></table>";
				$('#expenseHasAddForm').html(htmlList);
			});
		}
		return true;
	}
	
	$(document).delegate('#viewExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-expense-get-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '已关联费用', 1000,
					['费用名称','收/付','收付单位','票/箱','汇率','币种','预计金额','实际金额'], 
					['freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','actualCount']
			);
		}
	});
	
/////////////////////发票
	$(document).delegate('#addInvoice', 'click', function(e){
		if(addInvoice()){
			var margin = (window.screen.availWidth - 1200)/2;
			$('#invoiceModal').css("margin-left", margin);
			$('#invoiceModal').css("width","1200px");
			$('#invoiceModal').modal();
		}
	});
	
	function addInvoice(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-invoice-list-async.do?freightStatementId=' + freightStatementId;
			$.post(url, $('#searchFormExpense').serialize(), function(data){
				var freightStatement = data.freightStatement;//对账单信息
				var hasAddInvoice = data.hasAddInvoice;//已关联的费用
				
				var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>金额(RMB)</th><th>已开票金额(RMB)</th><th>未开票金额(RMB)</th><th>金额(USD)</th><th>已开票金额(USD)</th><th>未开票金额(USD)</th><th>摘要</th></tr></thead>';
				html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
				html += '<td>'+freightStatement.freightPart.partName+'</td>';
				html += '<td>'+freightStatement.incomeOrExpense+'</td>';
				html += '<td>'+freightStatement.moneyCountRmb+'</td>';
				html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
				html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
				html += '<td>'+freightStatement.moneyCountDollar+'</td>';
				html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
				html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
				html += '<td>'+freightStatement.descn+'</td>';
				html += '</tr></tbody></table>';
				$('#statementInfoForm').html(html);
				
				html = '<input id="freightStatementId" type="hidden" value="">';
				html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>开票单位</th><th>发票种类</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th></tr></thead><tbody>';
				//开票单位根据对账单单位
				html += '<tr>';
				html += '<td><input id="releasePart" name="releasePart" value="' + freightStatement.freightPart.partName + '" class="form-control required" readonly></td>';
				html += '<td><input id="invoiceType" name="invoiceType" value="" class="form-control required dictionary" vAttrId="5a487450-55d7-11e4-bdcd-a4db305e5cc5"></td>';
				html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
				html += '<td><input id="exchangeRate" name="exchangeRate" value="" class="form-control required number" ></td>';
				html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" minlength="1" maxlength="5"></td>';
				html += '<td><input id="descn" name="descn" value="" class="form-control required" ></td>';
				html += '</tr>'
				html += "</tbody></table>";
				
				$('#invoiceToAddForm').html(html);
				$('#invoiceToAddForm #freightStatementId').val(freightStatementId);
				
				html = '';//重置
				html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsInvoice(this.checked)"/></th>';
				html += '<th>开票单位</th><th>发票种类</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th>';
				html += '</tr></thead><tbody>';
				$.each(hasAddInvoice, function(i, item){
					html += '<tr><td><input class="selectedItemInvoice a-check" type="checkbox" name="invoiceIds" value="'+item.id+'" /></td>';
					html += '<td>' + item.releasePart + '</td>';
					html += '<td>' + item.invoiceType + '</td>';
					html += '<td>' + item.currency + '</td>';
					html += '<td>' + item.exchangeRate + '</td>';
					html += '<td>' + item.moneyCount + '</td>';
					html += '<td>' + item.descn + '</td></tr>';
				});
				html += "</tbody></table>";
				
				$('#invoiceHasAddForm').html(html);
			});
		}
		
		return true;
	}
	
	$(document).delegate('#viewInvoice', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-invoice-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '已关联发票', 1000,
					['发票号','票种','收付单位','收/付','汇率','币种','金额'], 
					['fasInvoice.invoiceNumber','invoiceType', 'releasePart' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount']
			);
		}
	});
	
	
	function toggleSelectedItemsExpense(isChecked) {
		$(".selectedItemExpense").each(function(index, el) {
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
	
	function toggleSelectedItemsInvoice(isChecked) {
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
	}
	
	//提交费用
	function submitExpense(){
		var expenseIds = '';
		$('#expenseToAddForm .selectedItemExpense:checked').each(function(i, item){
			expenseIds += $(item).val() + ",";
		});
		
		if(expenseIds != ''){
			expenseIds = expenseIds.substring(0, expenseIds.length - 1);
		}else{
			return false;//如果没有选择任何费用，则返回了
		}
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}
		var freightStatementId = $('.selectedItem:checked').val();
		var url = 'fre-expense-add-tostatement.do?expenseIds=' + expenseIds + '&freightStatementId=' + freightStatementId;
		$.post(url, function(data){
			if(data == 'success'){
				addExpense();
			}
		});
	}
	
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
		var url = 'fre-expense-remove-bystatement-async.do?expenseIds=' + expenseIds;
		$.post(url, function(data){
			if(data == 'success'){
				addExpense();
			}
		});
	}
	
	
	function deleteInvoice(){
		var invoiceIds = '';
		$('#invoiceHasAddForm .selectedItemInvoice:checked').each(function(i, item){
			invoiceIds += $(item).val() + ",";
		});
		if(invoiceIds != ''){
			invoiceIds = invoiceIds.substring(0, invoiceIds.length - 1);
		}else{
			return false;//如果没有选择任何费用，则返回了
		}
		var url = 'fre-invoice-remove-async.do?invoiceIds=' + invoiceIds;
		$.post(url, function(data){
			if(data == 'success'){
				addInvoice();
			}
		});
	}
	
	
	//提交发票信息
	$(function() {
		$("#invoiceToAddForm").validate({
	        submitHandler: function(form) {
				var freightStatementId = $('#invoiceToAddForm #freightStatementId').val();
				if(freightStatementId == undefined || freightStatementId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				if(($('#invoiceToAddForm #currency').val() == '人民币' && $('#invoiceToAddForm #moneyCount').val() > new Number($('#remainCountRmb').text()))
						|| ($('#invoiceToAddForm #currency').val() == '美元' && $('#invoiceToAddForm #moneyCount').val() > new Number($('#remainCountDollar').text()))){
					alert('开票金额不能大于账单未开票金额！');
					return false;
				}
				var data = toJsonString('invoiceToAddForm');
				var url = 'fre-invoice-save-async.do?freightStatementId=' + freightStatementId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addInvoice();
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
	
	//申请开票
	$(document).delegate('#applyToReleaseByStatement', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			return false;
		}else{
			$('#dynamicGridForm').attr('action', 'fre-statement-release.do');
			$('#dynamicGridForm').submit();
		}
	});
	
    </script>
  </body>

</html>
