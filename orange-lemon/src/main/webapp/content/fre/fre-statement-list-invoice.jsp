<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>收款开票</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>收款开票</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
									<button class="btn btn-sm green" id="viewExpense" >
											费用明细</button>
									<button class="btn btn-sm green" id="viewInvoice" >
											发票明细</button>
									<button class="btn btn-sm red" id="toAddInvoice">
											添加开票</button>
									<button class="btn btn-sm red" id="backInvoiceStatement">
											退回申请</button>		
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
								<form name="searchForm" method="post" action="fre-statement-list-invoice.do" class="form-inline">
								    <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control input-small">
								    
								    <label for="statementNumber">账单号</label>
								    <input type="text" id="statementNumber" name="statementNumber" value="${param.statementNumber}" class="form-control input-small">
								    
								    <label for="fasInvoiceTypeId">票种</label>
								    <select id="fasInvoiceTypeId" name="fasInvoiceTypeId" class="form-control input-small">
								    	<option value=""></option>
								    	<c:forEach items="${fasInvoiceTypes}" var="fasInvoiceType">
								    	<option value="${fasInvoiceType.id}" <c:if test="${param.fasInvoiceTypeId == fasInvoiceType.id }">selected</c:if> >${fasInvoiceType.typeName}</option>
								    	</c:forEach>
								    </select>
								    
								    <label for="moneyCountDollar">美元</label>
								    <input type="text" id="moneyCountDollarStart" name="moneyCountDollarStart" value="${param.moneyCountDollarStart}" class="form-control input-xsmall number">
								    -
								    <input type="text" id="moneyCountDollarEnd" name="moneyCountDollarEnd" value="${param.moneyCountDollarEnd}" class="form-control input-xsmall number">
								    
								    <label for="moneyCountRmb">人民币</label>
								    <input type="text" id="moneyCountRmbStart" name="moneyCountRmbStart" value="${param.moneyCountRmbStart}" class="form-control input-xsmall number">
								    -
								    <input type="text" id="moneyCountRmbEnd" name="moneyCountRmbEnd" value="${param.moneyCountRmbEnd}" class="form-control input-xsmall number">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="待开票" <c:if test="${param.status == '待开票' }">selected</c:if> >待开票</option>
								    	<option value="开票中" <c:if test="${param.status == '开票中' }">selected</c:if> >开票中</option>
								    	<option value="已开票" <c:if test="${param.status == '已开票' }">selected</c:if> >已开票</option>
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
								                    <th class="sorting" name="STATEMENT_NUMBER">账单号</th>
								                    <th class="sorting" name="FRE_PART_ID">单位</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE">票种</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="MONEY_COUNT_RMB">金额(RMB)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_RMB">已开票金额(RMB)</th>
								                    <th class="sorting" name="REMAIN_COUNT_RMB">未开票金额(RMB)</th>
								                    <th class="sorting" name="MONEY_COUNT_DOLLAR">金额(USD)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_DOLLAR">已开票金额(USD)</th>
								                    <th class="sorting" name="REMAIN_COUNT_DOLLAR">未开票金额(USD)</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="USER_ID">申请人</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">部门</th>
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
								                    <td id="${item.id}incomeOrExpense">${item.incomeOrExpense}</td>
								                    <td>${item.moneyCountRmb}</td>
								                    <td id="${item.id}eliminateCountRmb">${item.eliminateCountRmb}</td>
								                    <td id="${item.id}remainCountRmb">${item.remainCountRmb}</td>
								                    <td>${item.moneyCountDollar}</td>
								                    <td id="${item.id}eliminateCountDollar">${item.eliminateCountDollar}</td>
								                    <td id="${item.id}remainCountDollar">${item.remainCountDollar}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.creator.displayName}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
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
	
	<!-- 开票任务 -->
	<div class="modal fade" id="invoiceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">开票任务</h4>
			</div>
			<div class="modal-body">
				<!-- 对账单信息 -->
				<article class="m-widget" >
				<form id="statementInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<!-- 添加开票-->
				<article class="m-widget" >
				<form id="invoiceToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="invoiceBtnForm" name="invoiceBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#invoiceToAddForm').submit();">添加</button>
						<button class="btn btn-sm red" onclick="doneDeleteInvoice();">删除</button>
					</form>
				</article>
				
				<!-- 已添加开票-->
				<article class="m-widget" style="max-height: 180px;overflow-y: scroll;">
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
	 	        'statementNumber': '${param.statementNumber}',
	 	        'fasInvoiceTypeId': '${param.fasInvoiceTypeId}',
	 	       	'moneyCountDollarStart': '${param.moneyCountDollarStart}',
	 	      	'moneyCountDollarEnd': '${param.moneyCountDollarEnd}',
	 	     	'moneyCountRmbStart': '${param.moneyCountRmbStart}',
	 	    	'moneyCountRmbEnd': '${param.moneyCountRmbEnd}',
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
	
	$(document).delegate('#viewExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-expense-get-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '关联费用', 1200,
					['编号', '费用名称','收/付','收付单位','票/箱','汇率','币种','预计单价','预计总额','实际单价','实际总额'], 
					['expenseNumber','freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','predictAmount','actualCount','actualAmount']
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
			listData(url, '关联发票', 1000,
					['发票号','票种','单位','收/付','汇率','币种','金额','状态','开票时间'], 
					['fasInvoice.invoiceNumber','fasInvoiceType.typeName', 'freightPart.partName' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount','status','releaseTime']
			);
		}
	});
	
/////////////////////开票
	$(document).delegate('#toAddInvoice', 'click', function(e){
		toAddInvoice();
	});
	
	function toAddInvoice(){
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
				$.post(url, $('#searchFormExpense').serialize(), function(data){
					var freightStatement = data.freightStatement;//对账单信息
					if(freightStatement.status == '异常锁定'){
						alert('异常锁定中的账单不能做任何操作！');
						return false;
					}
					var hasAddInvoice = data.hasAddInvoice;//已关联的费用
					
					var html = '<table id="statementInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>对账单号</th><th>收付单位</th><th>收/付</th><th>发票票种</th><th>金额(RMB)</th><th>已开票(RMB)</th><th>未开票(RMB)</th><th>金额(USD)</th><th>折合金额</th><th>已开票(USD)</th><th>未开票(USD)</th></tr></thead>';
					html += '<tbody><tr><td>'+freightStatement.statementNumber+'</td>';
					html += '<td>'+freightStatement.freightPart.partName+'</td>';
					html += '<td>'+freightStatement.incomeOrExpense+'</td>';
					html += '<td>'+freightStatement.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightStatement.moneyCountRmb+'</td>';
					html += '<td>'+freightStatement.eliminateCountRmb+'</td>';
					html += '<td id="remainCountRmb">'+freightStatement.remainCountRmb+'</td>';
					html += '<td>'+freightStatement.moneyCountDollar+'</td>';
					html += '<td>'+(freightStatement.moneyCountDollar*freightStatement.exchangeRateDollar)+'</td>';
					html += '<td>'+freightStatement.eliminateCountDollar+'</td>';
					html += '<td id="remainCountDollar">'+freightStatement.remainCountDollar+'</td>';
					html += '</tr><tr><th>备注信息</th><td colspan="10"><textarea  style="min-height:60px;width:98%;" readonly>'+freightStatement.descn+'</textarea></td></tr></tbody></table>';
					$('#invoiceModal #statementInfoForm').html(html);
					
					html = '<input id="freightStatementId" type="hidden" value="' + freightStatementId + '">';
					html += '<input id="fasInvoiceTypeId" type="hidden" value="' + freightStatement.fasInvoiceType.id + '">';
					html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>开票单位</th><th>发票种类</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th></tr></thead><tbody>';
					//开票单位根据对账单单位
					html += '<tr>';
					html += '<td>' + freightStatement.freightPart.partName + '</td>';
					html += '<td>' + freightStatement.fasInvoiceType.typeName + '</td>';
					html += '<td><select id="currency" name="currency" class="form-control required" onchange="updateCurrency();"><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
					html += '<td><input id="exchangeRate" name="exchangeRate" value="" class="form-control number" minlength="1" maxlength="6" placeholder="不填写自动添加默认汇率"></td>';
					html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" minlength="1" maxlength="10"></td>';
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
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>开票单位</th><th>收/付</th><th>发票种类</th><th>币种</th><th>汇率</th><th>金额</th><th>摘要</th>';
					html += '</tr></thead><tbody>';
					$.each(hasAddInvoice, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
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
	}
	
	$(document).delegate('#viewInvoice', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-invoice-bystatementid.do?freightStatementId=' + freightStatementId;
			listData(url, '已关联发票', 1000,
					['发票号','票种','单位','收/付','汇率','币种','金额'], 
					['fasInvoice.invoiceNumber','fasInvoiceType.typeName', 'freightPart.partName' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount']
			);
		}
	});
	
	function doneDeleteInvoice(){
		if($('#invoiceHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
		}else{
			var url = 'fre-invoice-done-delete-invoice.do?';
			$('#invoiceHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightInvoiceId=' + $(item).val();
				}else{
					url += '&freightInvoiceId=' + $(item).val();
				}
			});
			
			$.post(url, function(data){
				if(data == 'success'){
					toAddInvoice();
				}else{
					alert('取消失败！');
				}
			});
		}
	} 
	
	//提交发票信息
	$(function() {
		$("#invoiceToAddForm").validate({
	        submitHandler: function(form) {
				var freightStatementId = $('#invoiceToAddForm #freightStatementId').val();
				var fasInvoiceTypeId = $('#invoiceToAddForm #fasInvoiceTypeId').val();
				if(freightStatementId == undefined || freightStatementId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				if(($('#invoiceToAddForm #currency').val() == '人民币' && $('#invoiceToAddForm #moneyCount').val() > new Number($('#invoiceModal #statementInfoForm #remainCountRmb').text()))
						|| ($('#invoiceToAddForm #currency').val() == '美元' && $('#invoiceToAddForm #moneyCount').val() > new Number($('#invoiceModal #statementInfoForm #remainCountDollar').text()))){
					alert('开票金额不能大于账单未开票金额！');
					return false;
				}
				var data = toJsonString('invoiceToAddForm');
				var url = 'fre-invoice-done-add-invoice.do?freightStatementId=' + freightStatementId
						+ '&fasInvoiceTypeId=' + fasInvoiceTypeId;
				bootbox.animate(false);
	    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				toAddInvoice();
	    				box.modal('hide');
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
	//退回申请
	$(document).delegate('#backInvoiceStatement', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-statement-back-invoice-statement.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '待开票' && status != '已开票'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightStatementId=' +　$(item).val();
					}else{
						url += '&freightStatementId=' +　$(item).val();
					}
				}
			});
			if(flag && window.confirm('确认退回吗？如果已开票，则发票将自动作废。')){
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
				alert('退回失败！请确认所选账单状态！');
				return;
			}
		}
	});
    </script>
  </body>

</html>
