<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>开票管理</title>
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
							<div class="caption"><i class="fa fa-dollar"></i>开票管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RELEASE-SINGLE-RELEASE-INVOICE">
									<button class="btn btn-sm red" id="singleReleaseInvoice">
											手动开票</button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RELEASE-BATCH-RELEASE-INVOICE">
									<button class="btn btn-sm red" id="batchReleaseInvoice">
											自动开票</button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RELEASE-REVISE-FAS-INVOICE">
									<button class="btn btn-sm red" id="reviseFasInvoice">
											修改发票</button>
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
								<form name="searchForm" method="post" action="fre-invoice-list-release.do" class="form-inline">
								     <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control input-small">
								    
								    <label for="statementNumber">账单号</label>
								    <input type="text" id="statementNumber" name="statementNumber" value="${param.statementNumber}" class="form-control input-small">
								    
								    <label for="invoiceNumber">发票号</label>
								    <input type="text" id="invoiceNumber" name="invoiceNumber" value="${param.invoiceNumber}" class="form-control input-small">
								    
								    <label for="currency">币种</label>
								    <select id="currency" name="currency" class="form-control">
								    	<option value=""></option>
								    	<option value="人民币" <c:if test="${param.currency == '人民币' }">selected</c:if> >人民币</option>
								    	<option value="美元" <c:if test="${param.currency == '美元' }">selected</c:if> >美元</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="待开票" <c:if test="${param.status == '待开票' }">selected</c:if> >待开票</option>
								    	<option value="已开票" <c:if test="${param.status == '已开票' }">selected</c:if> >已开票</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-invoice-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="INVOICE_NUMBER">发票号</th>
								                    <th class="sorting" name="INVOICE_TYPE">票种</th>
								                    <th class="sorting" name="RELEASE_PART">开票单位</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="MONEY_COUNT">金额</th>
								                    <th class="sorting" name="MONEY_COUNT">折合金额</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="STATEMENT_ID">账单号</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="RELEASE_TIME">开具时间</th>
								                    <th>摘要</th>
								                    <th>账单备注</th>
								                    <!--  
								                    <th class="sorting" name="CLAIM_TIME">领取时间</th>
								                    <th class="sorting" name="RECEIVE_TIME">收到时间</th>
								                    -->
								                    <th>相关订单</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.fasInvoice.invoiceNumber}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td>${item.freightPart.partName}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.moneyCount}</td>
								                    <td>${item.moneyCount*item.exchangeRate}</td>
								                    <td>${item.incomeOrExpense}</td>
								                    <td>${item.freightStatement.statementNumber}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <!--  
								                    <td>
								                    	<fmt:formatDate value="${item.claimTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.eliminateTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    -->
								                    <td>${item.descn}</td>
								                    <td>${item.freightStatement.descn}</td>
								                    <td>
								                    	<c:set var="orderNumbers" value=""></c:set>
								                    	<c:forEach items="${item.freightStatement.freightExpenses}" var="freightExpense" varStatus="varStatus">
								                    	<c:if test="${freightExpense.freightOrder != null}">
								                    		<c:set var="orderNumbers" value="${freightExpense.freightOrder.orderNumber},${orderNumbers}"></c:set>
								                    	</c:if>
								                    	</c:forEach>
								                    	${orderNumbers}
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
	
	<!-- 税务发票修改 -->
	<div class="modal fade" id="reviseFasInvoiceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">发票信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="reviseFasInvoiceForm" action="fre-invoice-revise-fas-invoice.do" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#reviseFasInvoiceForm').submit();">确定</button>
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
	 	        'invoiceNumber': '${param.invoiceNumber}',
	 	        'statementNumber': '${param.statementNumber}',
	 	        'currency': '${param.currency}',
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
	
	$(document).delegate('#batchReleaseInvoice', 'click', function(event){
		if(window.confirm('确定为选中的开票任务开具发票？')){
			$('#dynamicGridForm').attr('action', 'fre-invoice-release-batch.do');
			$('#dynamicGridForm').submit();
		}
	});
	
	$(document).delegate('#singleReleaseInvoice', 'click', function(event){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightInvoiceId = $('.selectedItem:checked').val();
			var url = "fre-invoice-input-release.do?freightInvoiceId=" + freightInvoiceId;
			window.location.href = url;
		}
	});
	
	
	//写错发票之后单独进行修改，仅更新其关联的税务发票。
	
	$(document).delegate('#reviseFasInvoice', 'click',function(e){
		if(reviseFasInvoice()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#reviseFasInvoiceModal').css("margin-left", margin);
			$('#reviseFasInvoiceModal').css("width","1000px");
			$('#reviseFasInvoiceModal').modal();
		}
	});
	
	function reviseFasInvoice(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightInvoiceId = $('.selectedItem:checked').val();
			$.post('fre-invoice-to-revise-fas-invoice.do?freightInvoiceId=' + freightInvoiceId, function(data){
				var freightInvoice = data.freightInvoice;
				var fasInvoices = data.fasInvoices;
				
				var html ='<input id="id" name="id" type="hidden" value="' + freightInvoice.id + '">';
				html += '<table id="reviseFasInvoiceTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>收付单位</th><th>收/付</th><th>金额</th><th>币种</th><th>汇率</th><th>票种</th><th>发票号</th><th>说明</th></tr></thead><tbody><tr>';
				html += '<td>' + freightInvoice.freightPart.partName + '</td>';
				html += '<td>' + freightInvoice.incomeOrExpense + '</td>';
				html += '<td>' + freightInvoice.moneyCount + '</td>';
				html += '<td>' + freightInvoice.currency + '</td>';
				html += '<td>' + freightInvoice.exchangeRate + '</td>';
				html += '<td>' + freightInvoice.fasInvoiceType.typeName + '</td>';
				html += '<td><select id="fasInvoiceId" class="form-control required">';
				$.each(fasInvoices, function(i, item){
					html += '<option value="' + item.id + '"';
					if(freightInvoice.fasInvoice != null && freightInvoice.fasInvoice.id == item.id){
						html += ' selected >' + item.invoiceNumber + '</option>';
					}else{
						html += ' >' + item.invoiceNumber + '</option>';
					}
				});
				html += '</select></td>';
				html += '<td><input id="descn" name="descn" value="' + (freightInvoice.descn == null ? '' : freightInvoice.descn) + '" class="form-control required"></td></tr></tbody></table>';
				$('#reviseFasInvoiceForm').html(html);
			});
			return true;
		}
	}
	
	$(function() {
		$('#reviseFasInvoiceForm').validate({
	        submitHandler: function(form) {
	        	$('#reviseFasInvoiceModal').modal('hide');
	        	//bootbox.animate(false);
    			//var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				var fasInvoiceId = $('#reviseFasInvoiceForm #fasInvoiceId').val();
    			$.ajax({
	    			type: 'POST',
	    			data: toJsonString('reviseFasInvoiceForm'),
	    			url: 'fre-invoice-done-revise-fas-invoice.do?fasInvoiceId=' + fasInvoiceId,
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data == 'success'){
	    					alert('修改成功！');
	    				}
	    				window.location.href = window.location.href;
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
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
    </script>
  </body>

</html>
