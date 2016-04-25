<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>银行收款管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>银行收款管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<!-- 出纳收款 -->
									<sec:authorize ifAnyGranted="ROLE_CY-FAS-CASHIER-EMP">
									<button class="btn btn-sm red" onclick="location.href='fas-due-input.do'">
										新增<i class="fa fa-arrows"></i></button>
									<button class="btn btn-sm red" onclick="if(canRemove()) table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm green" id="doneConfirmDue">
										确定收款</button>
									<button class="btn btn-sm green" id="doneRecallDue">
										取消收款</button>
									</sec:authorize>
									
									<!-- 会计销账 -->
									<sec:authorize ifAnyGranted="ROLE_CY-FAS-ACCOUNTANT-EMP">
									<button class="btn btn-sm red" id="addReconcile">
										收款销账</button>
									<button class="btn btn-sm green" id="doneForceReconcile">
										强制销账</button>
									</sec:authorize>
									
									<!-- 管理员 -->
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
									<button class="btn btn-sm green" id="doneRecallReconcile">
										取消销账</button>
									</sec:authorize>
									
									<button class="btn btn-sm green" id="batchExportReconcile">
										导出明细</button>
											
									<button class="btn btn-sm green" id="batchExportDetails">
										导出数据</button>
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
								<form name="searchForm" method="post" action="fas-due-list.do" class="form-inline">
								    <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control input-xsmall">
								    
								    <label for="currency">币种</label>
								    <select id="currency" name="currency" class="form-control">
								    	<option value=""></option>
								    	<option value="人民币" <c:if test="${param.currency == '人民币' }">selected</c:if> >人民币</option>
								    	<option value="美元" <c:if test="${param.currency == '美元' }">selected</c:if> >美元</option>
								    </select>
								    
								    <label for="dueCount">金额</label>
								    <input type="text" id="dueCountStart" name="dueCountStart" value="${param.dueCountStart}" class="form-control input-xsmall number">
								    -
								    <input type="text" id="dueCountEnd" name="dueCountEnd" value="${param.dueCountEnd}" class="form-control input-xsmall number">
								    
								    <label for="dueTime">收款时间</label>
								    <input type="text" id="dueTimeStart" name="dueTimeStart" value="${param.dueTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="dueTimeEnd" name="dueTimeEnd" value="${param.dueTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.status == '未提交' }">selected</c:if> >未提交</option>
								    	<option value="已确认" <c:if test="${param.status == '已确认' }">selected</c:if> >已确认</option>
								    	<option value="销账中" <c:if test="${param.status == '销账中' }">selected</c:if> >销账中</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fas-due-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="DUE_NUMBER">编号</th>
								                    <th class="sorting" name="FRE_PART_ID_PAY">付款单位</th>
								                    <th class="sorting" name=FAS_ACCOUNT_ID_PAY>付款账号</th>
								                    <th class="sorting" name="FAS_ACCOUNT_ID_DUE">我方收款账号</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="DUE_COUNT">收据金额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">确认金额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">已销账金额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">未销账金额</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="DUE_TIME">银行收款时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.dueNumber}</td>
								                    <td>${item.payPart.partName}</td>
								                    <td>${item.payAccount.accountNumber}</td>
								                    <td>${item.dueAccount.accountNumber}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.dueCount}</td>
								                    <td>${item.actualCount}</td>
								                    <td>${item.eliminateCount}</td>
								                    <td>${item.remainCount}</td>
								                    <td>${item.descn}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.dueTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <!--  
								                    <td>
								                    	<a href="fas-due-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
								                    </td>
								                    -->
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
				<h4 class="modal-title">收款销账</h4>
			</div>
			<div class="modal-body">
				<!-- 银行收款信息 -->
				<article class="m-widget">
				<form id="infoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				<article class="m-widget">
					<form id="reconcileSearchForm" name="reconcileSearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="partName">单位</label>
					    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
					    <label for="invoiceNumber">发票号</label>
					    <input type="text" id="invoiceNumber" name="invoiceNumber" value="${param.invoiceNumber}" class="form-control">
						<button class="btn btn-sm red" onclick="addReconcile();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				
				<!-- 添加销账-->
				<article class="m-widget">
				<form id="reconcileToAddForm" action="" method="post" class="m-form-blank" style="max-height:150px;overflow-y:scroll; "></form>
				</article>
				
				<article class="m-widget">
					<form id="reconcileBtnForm" name="reconcileBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#reconcileToAddForm').submit();">销账</button>
						<button class="btn btn-sm red" id="deleteReconcileBtn" onclick="deleteReconcile();">取消</button>
					</form>
				</article>
				
				<!-- 已添加销账-->
				<article class="m-widget">
				<form id="reconcileHasAddForm" action="" method="post" class="m-form-blank" style="max-height:150px;overflow-y:scroll;"></form>
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
	 	        'currency': '${param.currency}',
	 	        'dueCountStart': '${param.dueCountStart}',
	 	       	'dueCountEnd': '${param.dueCountEnd}',
	 	      	'dueTimeStart': '${param.dueTimeStart}',
	 	     	'dueTimeEnd': '${param.dueTimeEnd}',
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
	
	//导出销账明细
	$(document).delegate('#batchExportReconcile', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var fasDueId = $('.selectedItem:checked').val();
			window.open('fas-reconcile-to-export-reconcile-due.do?fasDueId=' + fasDueId);
		}
	}); 
	//导出具体数据
	$(document).delegate('#batchExportDetails', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var url = 'fas-due-to-export-details-due.do?';
			$('.selectedItem:checked').each(function(i, item){
				if(i == 0){
					url += 'fasDueId=' + $(item).val();
				}else{
					url += '&fasDueId=' + $(item).val();
				}
			});
			
			window.open(url);
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
			var fasDueId = $('.selectedItem:checked').val();
			var status = $('#' + fasDueId + 'status').text();
			var flag = false;
			if(status != '已确认' && status != '销账中'){
				alert('该收款尚未确认或已全部销账！');
				flag = true;
			}
			var url = 'fas-due-to-add-reconcile.do?fasDueId=' + fasDueId;
			$.post(url, $('#reconcileSearchForm').serialize(), function(data){
				var fasDue = data.fasDue;//银行收款
				var toReconcileInvoices = data.toReconcileInvoices;//待销账或销账中的
				var hasReconcileInvoices = data.hasReconcileInvoices;//销账过的发票
				var fasReconciles = data.fasReconciles;//已关联的销账记录
				
				var html = '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>编号</th><th>收款单位</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th><th>状态</th></tr></thead>';
				html += '<tbody><tr><td>'+fasDue.dueNumber+'</td>';
				html += '<td>' + fasDue.payPart.partName + '</td>';
				html += '<td>' + fasDue.currency + '</td>';
				html += '<td>' + fasDue.exchangeRate + '</td>';
				html += '<td>' + fasDue.actualCount + '</td>';
				html += '<td>' + fasDue.eliminateCount + '</td>';
				html += '<td>' + fasDue.remainCount + '</td>';
				html += '<td>' + fasDue.status + '</td>';
				html += '</tr></tbody></table>';
				$('#infoForm').html(html);
				
				html = '<input id="fasDueId" type="hidden" value="' + fasDueId + '">';
				html += '<input id="freightInvoiceId" type="hidden" value="">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check"></th>';
				html += '<th>单位</th><th>发票号</th><th>开票时间</th><th>票种</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账</th><th>未销账</th><th>状态</th><th>可销账金额</th><th>订单号</th></tr></thead><tbody>';
				if(toReconcileInvoices != null){
					$.each(toReconcileInvoices, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="radio" name="A" value="'+item.id+'" onclick="chooseInvoice();"/></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
						html += '<td>'+ $.fullCalendar.formatDate(new Date(item.releaseTime),'yyyy-MM-dd') +'</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>'+ item.currency +'</td>';
						html += '<td>'+ item.exchangeRate +'</td>';
						html += '<td>'+ item.moneyCount +'</td>';
						html += '<td>'+ item.eliminateCount +'</td>';
						html += '<td id="'+ item.id +'remainCount">'+ item.remainCount +'</td>';
						html += '<td>'+ item.status +'</td>';
						html += '<td><input id="'+ item.id +'toEliminate" value="'+ item.remainCount +'" class="form-control number required"></td>';
						html += '<td>'+ item.involveOrderNumber +'</td>';
						html += '</tr>'
					});
				}
				html += "</tbody></table>";
				$('#reconcileToAddForm').html(html);
				
				html = '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
				html += '<th>单位</th><th>发票号</th><th>票种</th><th>发票金额</th><th>已销账</th><th>未销账</th><th>币种</th><th>汇率</th><th>销账金额</th><th>销账时间</th>';
				html += '</tr></thead><tbody>';
				if(fasReconciles != null){
					$.each(fasReconciles, function(i, item){
						$.each(hasReconcileInvoices, function(j, ele){
							if(item.sourceId == ele.id){
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
								html += '<td>' + ele.freightPart.partName + '</td>';
								html += '<td>' + ele.fasInvoice.invoiceNumber + '</td>';
								html += '<td>' + ele.fasInvoiceType.typeName + '</td>';
								html += '<td>' + ele.moneyCount + '</td>';
								html += '<td>' + ele.eliminateCount + '</td>';
								html += '<td>' + ele.remainCount + '</td>';
								html += '<td>' + item.currency + '</td>';
								html += '<td>' + item.exchangeRate + '</td>';
								html += '<td>' + item.moneyCount + '</td>';
								html += '<td>' + $.fullCalendar.formatDate(new Date(item.createTime),'yyyy-MM-dd') + '</td></tr>';
							}
						});
					});
				}
				html += "</tbody></table>";
				$('#reconcileHasAddForm').html(html);
				
				if(flag){
					$('#reconcileBtnForm').hide();
				}
			});
		}
		
		return true;
	}
	//选择具体
	function chooseInvoice(){
		var freightInvoiceId = $('#reconcileToAddForm .selectedItemId:checked').val();
		$('#reconcileToAddForm #freightInvoiceId').val(freightInvoiceId);
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
	
	function deleteReconcile(){
		var url = 'fas-due-done-delete-reconcile.do?';
		if($('#reconcileHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			$('#reconcileHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'fasReconcileId=' + $(item).val();
				}else{
					url += '&fasReconcileId=' + $(item).val();
				}
			});
			$('#deleteReconcileBtn').hide();
			bootbox.animate(false);
    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post(url, function(data){
				if(data == 'success'){
					alert('取消成功！');
					addReconcile();
				}else{
					alert('取消失败！');
				}
				$('#deleteReconcileBtn').show();
				box.modal('hide');
			});
		}
	}
	
	//提交销账信息
	$(function() {
		$("#reconcileToAddForm").validate({
	        submitHandler: function(form) {
				var freightInvoiceId = $('#reconcileToAddForm #freightInvoiceId').val();
				var fasDueId = $('#reconcileToAddForm #fasDueId').val();
				if(freightInvoiceId == undefined || freightInvoiceId == '' ||
						fasDueId == undefined || fasDueId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				
				var moneyCount = new Number($('#reconcileToAddForm #' + freightInvoiceId + 'toEliminate').val());
				var remainCount = new Number($('#reconcileToAddForm #' + freightInvoiceId + 'remainCount').text());
				if(remainCount - moneyCount < 0){
					alert('可销账金额已大于未销账金额，可销账金额需大于等于未销账金额！');
					return false;
				}
				var url = 'fas-due-done-add-reconcile.do?fasDueId=' + fasDueId + '&freightInvoiceId=' + freightInvoiceId + '&moneyCount=' + moneyCount;
				
				bootbox.animate(false);
	    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post(url, function(data){
					if(data != 'success'){
    					alert('销账失败，请检查销账金额是否超出！');
    				}else{
    					alert('销账成功！');
    					addReconcile();
    				}
					
					box.modal('hide');
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
	
	//点击模化窗口关闭按钮时刷新页面
	$(document).delegate('button', 'click', function(e){
		if($(this).attr('data-dismiss') == 'modal'){
			window.location.href = window.location.href;
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
			alert('删除失败，请确认所选收款状态！');
		}
		return flag;
	}
	
	//确认收款
	$(document).delegate('#doneConfirmDue', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fas-due-done-confirm-due.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '未提交'){
					flag = false;
				}else{
					if(i==0){
						url += 'fasDueId=' + $(item).val();
					}else{
						url += '&fasDueId=' + $(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('确认失败！请确认收款状态！');
					}else{
						alert('确认成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				});
			}else{
				alert('确认失败！请确认收款状态！');
			}
		}
	});
	
	//取消确认
	$(document).delegate('#doneRecallDue', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fas-due-done-recall-due.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已确认'){
					flag = false;
				}else{
					if(i==0){
						url += 'fasDueId=' + $(item).val();
					}else{
						url += '&fasDueId=' + $(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('取消失败！请确认收款状态！');
					}else{
						alert('取消成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				});
			}else{
				alert('取消失败！请确认收款状态！');
			}
		}
	});
	
	//强制销账
	$(document).delegate('#doneForceReconcile', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fas-due-done-force-reconcile.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '销账中'){//只针对销账中的收款
					flag = false;
				}else{
					if(i==0){
						url += 'fasDueId=' + $(item).val();
					}else{
						url += '&fasDueId=' + $(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('销账失败！请确认收款状态！');
					}else{
						alert('销账成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				});
			}else{
				alert('销账失败！请确认收款状态！');
			}
		}
	});
	
	//取消销账
	$(document).delegate('#doneRecallReconcile', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fas-due-done-recall-reconcile.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已销账'){//只针对销账中的收款
					flag = false;
				}else{
					if(i==0){
						url += 'fasDueId=' + $(item).val();
					}else{
						url += '&fasDueId=' + $(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('销账失败！请确认收款状态！');
					}else{
						alert('销账成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				});
			}else{
				alert('销账失败！请确认收款状态！');
			}
		}
	});
    </script>
  </body>

</html>
