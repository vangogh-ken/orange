<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>销账管理</title>
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
							<div class="caption"><i class="fa fa-dollar"></i>销账管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RECONCILE-ADD-RECONCILE">
									<button class="btn btn-sm red" id="addReconcile">
											销账</button>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RECONCILE-INVALID-RECONCILE">		
										<button class="btn btn-sm red" id="invalidInvoice">
											作废</button>
									</sec:authorize>	
									
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-RECONCILE-OFFSET-RECONCILE">	
										<button class="btn btn-sm red" id="offsetInvoice">
											发票冲抵</button>
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
								<form name="searchForm" method="post" action="fre-invoice-list-reconcile.do" class="form-inline">
								    <label for="partName">发票号</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="partType">开票单位</label>
								    <input type="text" id="partType" name="partType" value="${param.partType}" class="form-control">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="待销账" <c:if test="${param.status == '待销账' }">selected</c:if> >待销账</option>
								    	<option value="销账中" <c:if test="${param.status == '销账中' }">selected</c:if> >销账中</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
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
								                    <th class="sorting" name="ELIMINATE_COUNT">已销账金额</th>
								                    <th class="sorting" name="REMAIN_COUNT">未销账金额</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="RELEASE_TIME">开具时间</th>
								                    <th class="sorting" name="CLAIM_TIME">领取时间</th>
								                    <th class="sorting" name="RECEIVE_TIME">收到时间</th>
								                    <th class="sorting" name="EFFECT_TIME">截止时间</th>
								                    <th class="sorting" name="STATEMENT_ID">对账单号</th>
								                    <th class="sorting" name="STATUS">状态</th>
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
								                    <td id="${item.id}eliminateCount">${item.eliminateCount}</td>
								                    <td>${item.remainCount}</td>
								                    <td>${item.incomeOrExpense}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd"/>
								                    </td> 
								                    <td>
								                    	<fmt:formatDate value="${item.claimTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.eliminateTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.effectTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.freightStatement.statementNumber}</td>
								                    <td id="${item.id}status">${item.status}</td>
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
	
	<!-- 销账 -->
	<div class="modal fade" id="reconcileModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加销账</h4>
			</div>
			<div class="modal-body">
				<!-- 发票任务信息 -->
				<article class="m-widget">
				<form id="invoiceInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<!-- 添加销账-->
				<article class="m-widget">
				<form id="reconcileToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="reconcileBtnForm" name="reconcileBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#reconcileToAddForm').submit();">添加</button>
						<button class="btn btn-sm red" onclick="deleteReconcile();">删除</button>
					</form>
				</article>
				
				<!-- 已添加销账-->
				<article class="m-widget">
				<form id="reconcileHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	
	
	$(document).delegate('#addReconcile', 'click', function(event){
		if(addReconcile()){
			var margin = (window.screen.availWidth - 1200)/2;
			$('#reconcileModal').css("margin-left", margin);
			$('#reconcileModal').css("width","1200px");
			$('#reconcileModal').modal();
		}
	});
	
	function addReconcile(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightInvoiceId = $('.selectedItem:checked').val();
			var url = '${ctx}/fas/fas-reconcile-by-freightinvoice.do?freightInvoiceId=' + freightInvoiceId;
			$.post(url, function(data){
				var freightInvoice = data.freightInvoice;//开票任务信息
				var hasAddReconcile = data.hasAddReconcile;//已关联的销账记录
				var fasAccounts = data.fasAccounts;
				
				var html = '<table id="invoiceInfoTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>发票号</th><th>票种</th><th>收付单位</th><th>收/付</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th><th>摘要</th></tr></thead>';
				html += '<tbody><tr><td>'+freightInvoice.fasInvoice.invoiceNumber+'</td>';
				html += '<td>'+freightInvoice.fasInvoiceType.typeName+'</td>';
				html += '<td>'+freightInvoice.freightPart.partName+'</td>';
				html += '<td>'+freightInvoice.incomeOrExpense+'</td>';
				html += '<td id="currency">'+freightInvoice.currency+'</td>';
				html += '<td>'+freightInvoice.exchangeRate+'</td>';
				html += '<td>'+freightInvoice.moneyCount+'</td>';
				html += '<td>'+freightInvoice.eliminateCount+'</td>';
				html += '<td id="remainCount">'+freightInvoice.remainCount+'</td>';
				html += '<td>'+freightInvoice.descn+'</td>';
				html += '</tr></tbody></table>';
				$('#invoiceInfoForm').html(html);
				
				html = '<input id="freightInvoiceId" type="hidden" value="' + freightInvoiceId + '">';
				html += '<table id="reconcileTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>销账单位</th><th>销账类型</th><th>币种</th><th>金额</th><th>收支银行</th><th>摘要</th></tr></thead><tbody>';
				//开票单位根据对账单单位
				html += '<tr>';
				html += '<td><input id="reconcilePart" name="reconcilePart" value="' + freightInvoice.freightPart.partName + '" class="form-control required" readonly></td>';
				html += '<td><select id="reconcileType" name="reconcileType" class="form-control required" ><option value="银行转账">银行转账&nbsp;</option><option value="现金">现金</option></option><option value="支票">支票</option></select></td>';
				html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
				html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" minlength="2" ></td>';
				
				html += '<td><select id="fasAccountId" value="" class="form-control required"><option value=""></option>';
				$.each(fasAccounts, function(i, item){
					html += '<option value="' + item.id + '">'+  item.currency + '_' + item.bankName + '_' + item.accountNumber + '</option>';
				});
				html += '</select></td>';
				
				html += '<td><input id="descn" name="descn" value="" class="form-control required" ></td>';
				html += '</tr>'
				html += "</tbody></table>";
				
				$('#reconcileToAddForm').html(html);
				
				html = '';//重置
				html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsReconcile(this.checked)"/></th>';
				html += '<th>销账单位</th><th>销账类型</th><th>汇率</th><th>币种</th><th>金额</th><th>收支银行</th><th>摘要</th>';
				html += '</tr></thead><tbody>';
				$.each(hasAddReconcile, function(i, item){
					html += '<tr><td><input class="selectedItemReconcile a-check" type="checkbox" name="reconcileIds" value="'+item.id+'" /></td>';
					html += '<td>' + item.reconcilePart + '</td>';
					html += '<td>' + item.reconcileType + '</td>';
					html += '<td>' + item.exchangeRate + '</td>';
					html += '<td>' + item.currency + '</td>';
					html += '<td>' + item.moneyCount + '</td>';
					html += '<td>' + item.fasAccount.bankName + '</td>';
					html += '<td>' + item.descn + '</td></tr>';
				});
				html += "</tbody></table>";
				$('#reconcileHasAddForm').html(html);
			});
		}
		
		return true;
	}
	
	$(document).delegate('#viewReconcile', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightStatementId = $('.selectedItem:checked').val();
			var url = 'fre-reconcile-by-freightinvoiceid.do?freightInvoiceId=' + freightInvoiceId;
			listData(url, '已关联发票', 1000,
					['发票号','票种','单位','收/付','汇率','币种','金额'], 
					['fasInvoice.invoiceNumber','invoiceType', 'releasePart' ,'incomeOrExpense', 'exchangeRate', 'currency','moneyCount']
			);
		}
	});
	
	function toggleSelectedItemsReconcile(isChecked) {
		$(".selectedItemReconcile").each(function(index, el) {
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
	
	
	function deleteReconcile(){
		var reconcileIds = '';
		$('#reconcileHasAddForm .selectedItemReconcile:checked').each(function(i, item){
			reconcileIds += $(item).val() + ",";
		});
		if(reconcileIds != ''){
			reconcileIds = reconcileIds.substring(0, reconcileIds.length - 1);
		}else{
			return false;//如果没有选择任何费用，则返回了
		}
		var url = '${ctx}/fas/fas-reconcile-remove-async.do?reconcileIds=' + reconcileIds;
		$.post(url, function(data){
			if(data == 'success'){
				addReconcile();
			}
		});
	}
	
	
	//提交销账信息
	$(function() {
		$("#reconcileToAddForm").validate({
	        submitHandler: function(form) {
				var freightInvoiceId = $('#reconcileToAddForm #freightInvoiceId').val();
				var fasAccountId = $('#reconcileToAddForm #fasAccountId').val();
				if(freightInvoiceId == undefined || freightInvoiceId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				
				//如果销账币种一致，只需要比对其金额与未销账金额；如果不一致，则都按汇率兑换至人民币进行比对；
				//直接通过后台抛出异常进行数据回滚提示，因为汇率都直接从系统中取得，此处取不到汇率，所以无法计算
				 /* var reconcileCurrency = $('#reconcileToAddForm #currency').val();
				 var invoiceCurrency = $('#invoiceInfoForm #currency').val();
				
				 var reconcilExchangeRate = $('#reconcileToAddForm #exchangeRate').val();
				 var invoiceExchangeRate = $('#invoiceInfoForm #exchangeRate').val();
				
				 var invoiceRemainCount = new Number($('#invoiceInfoForm #remainCount').text());
				 var reconcileMoneyCount  = $('#reconcileToAddForm #moneyCount').val();
				
				if(invoiceCurrency == reconcileCurrency){
					if(reconcileMoneyCount > invoiceRemainCount){
						alert('销账金额不能大于账单未销账金额！');
						return false;
					}
				}else{
					if(reconcileMoneyCount * reconcilExchangeRate > invoiceRemainCount * invoiceExchangeRate){
						alert('销账金额不能大于账单未销账金额！');
						return false;
					}
				} */
				
				var data = toJsonString('reconcileToAddForm');
				var url = '${ctx}/fas/fas-reconcile-save-to-freightinvoice.do?freightInvoiceId=' + freightInvoiceId + '&fasAccountId=' + fasAccountId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data != 'success'){
	    					alert('销账失败，请检查销账金额是否超出！');
	    				}
	    				addReconcile();
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
	
	//作废，将影响到该开票涉及到的账单，其他开票，税务发票，费用。最终费用恢复到未提交状态。
	$(document).delegate('#invalidInvoice', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-invalid-invoice.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var eliminateCount = new Number($('#' + $(item).val() + 'eliminateCount').text());
				if(status != '待销账' || eliminateCount != 0){
					//alert('提交失败！请确认所选销账任务状态！');
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				if(window.confirm('此处作废会涉及到其他待销账，账单，发票和费用。确定要作废吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败！请确认相关待销账任务是否已经销过，或则状态不为待销账！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败！请确认所选销账任务状态和是否已经销账过！');
			}
		}
	});
    </script>
  </body>

</html>
