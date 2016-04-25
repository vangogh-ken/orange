<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>付款申请管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>付款申请管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='fas-pay-input.do'">
											新增<i class="fa fa-arrows"></i></button>
											
										<button class="btn btn-sm red" onclick="if(canRemove()) table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
											
										<button class="btn btn-sm green" id="addReconcile">
											付款销账</button>
											
										<button class="btn btn-sm green" id="confirmPay">
											标记付款</button>
									
										<button class="btn btn-sm green" id="batchExport">
											导出明细</button>
											
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
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
								<form name="searchForm" method="post" action="fas-pay-list.do" class="form-inline">
								    <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="payTime">付款时间</label>
								    <input type="text" id="payTimeStart" name="payTimeStart" value="${param.payTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="payTimeEnd" name="payTimeEnd" value="${param.payTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.status == '未提交' }">selected</c:if> >未提交</option>
								    	<option value="已生成" <c:if test="${param.status == '已生成' }">selected</c:if> >已生成</option>
								    	<option value="已付款" <c:if test="${param.status == '已付款' }">selected</c:if> >已付款</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fas-pay-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="PAY_NUMBER">编号</th>
								                    <th class="sorting" name="FRE_PART_ID">收款单位</th>
								                    <th class="sorting" name="PAY_TYPE">付款类型</th>
								                    <th class="sorting" name="PAY_COUNT_RMB">金额(RMB)</th>
								                    <th class="sorting" name="PAY_COUNT_DOLLAR">金额(USD)</th>
								                    <th class="sorting" name="USER_ID">申请人</th>
								                    <th>申请书</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="PAY_TIME">确认付款时间</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.payNumber}</td>
								                    <td>${item.beneficiary.partName}</td>
								                    <td>${item.payType}</td>
								                    <td>${item.payCountRmb}</td>
								                    <td>${item.payCountDollar}</td>
								                    <td>${item.proposer.displayName}</td>
								                    <td>
								                    	<a href="fas-pay-download.do?fasPayId=${item.id}" target="_blank">下载</a>
								                    	<a href="javascript:void(0);" onclick="browseFasPay('${item.id}');">预览</a>
								                    </td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.status != '已付款' ? null : item.payTime}" pattern="yyyy-MM-dd"/>
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
	
	<!-- 销账 -->
	<div class="modal fade" id="reconcileModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">付款销账</h4>
			</div>
			<div class="modal-body">
				<!-- 银行收款信息 -->
				<article class="m-widget">
				<form id="infoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<!-- 添加销账-->
				<article class="m-widget" style="max-height: 200px;overflow-y: scroll;">
				<form id="reconcileToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="reconcileBtnForm" name="reconcileBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" id="reconcileToAddFormBtn" onclick="$('#reconcileToAddForm').submit();">销账</button>
						<button class="btn btn-sm red" id="deleteReconcileBtn" onclick="deleteReconcile();">取消</button>
					</form>
				</article>
				
				<!-- 已添加销账-->
				<article class="m-widget" style="max-height: 150px;overflow-y: scroll;">
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
	 	        'payTimeStart': '${param.payTimeStart}',
	 	        'payTimeEnd': '${param.payTimeEnd}',
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
	
	$(document).delegate('#batchExport', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var fasPayId = $('.selectedItem:checked').val();
			window.open('fas-reconcile-to-export-reconcile-pay.do?fasPayId=' + fasPayId);
		}
	}); 
	
	//付款确认
	$(document).delegate('#confirmPay', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fas-pay-done-confirm-pay.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已生成'){
					flag = false;
				}else{
					if(i==0){
						url += 'fasPayId=' + $(item).val();
					}else{
						url += '&fasPayId=' + $(item).val();
					}
				}
			});
			
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('标记失败！请确认付款申请状态！');
					}else{
						alert('标记成功！');
					} 
					var href = window.location.href;
					window.location.href = href;
				});
			}else{
				alert('标记失败！请确认付款申请状态！');
			}
		}
	});
	
	//付款
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
			var fasPayId = $('.selectedItem:checked').val();
			var status = $('#' + fasPayId + 'status').text();
			var flag = false;
			if(status == '已付款'){
				alert('该付款申请已经付款！');
				flag = true;
			}
			var url = 'fas-pay-to-add-reconcile.do?fasPayId=' + fasPayId;
			$.post(url, function(data){
				var fasPay = data.fasPay;//银行收款
				var toReconcileInvoices = data.toReconcileInvoices;//待销账或销账中的
				var hasReconcileInvoices = data.hasReconcileInvoices;//销账过的发票
				var fasReconciles = data.fasReconciles;//已关联的销账记录
				
				var html = '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>编号</th><th>付款单位</th><th>付款方式</th><th>付款金额(RMB)</th><th>付款金额(USD)</th><th>申请人</th><th>申请时间</th><th>状态</th></tr></thead>';
				html += '<tbody><tr><td>' + fasPay.payNumber + '</td>';
				html += '<td>' + fasPay.beneficiary.partName + '</td>';
				html += '<td>' + fasPay.payType + '</td>';
				html += '<td>' + fasPay.payCountRmb + '</td>';
				html += '<td>' + fasPay.payCountDollar + '</td>';
				html += '<td>' + fasPay.proposer.displayName + '</td>';
				html += '<td>' + $.fullCalendar.formatDate(new Date(fasPay.applyTime),'yyyy-MM-dd') + '</td>';
				html += '<td>' + fasPay.status + '</td>';
				html += '</tr></tbody></table>';
				$('#infoForm').html(html);
				
				html = '<input id="fasPayId" type="hidden" value="' + fasPayId + '">';
				html += '<input id="freightInvoiceId" type="hidden" value="">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check"></th>';
				html += '<th>单位</th><th>发票号</th><th>票种</th><th>开票时间</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账</th><th>未销账</th><th>状态</th><th>可销账金额</th><th>订单号</th></tr></thead><tbody>';
				if(toReconcileInvoices != null){
					$.each(toReconcileInvoices, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="radio" name="A" value="'+item.id+'" onclick="chooseInvoice();"/></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>'+ $.fullCalendar.formatDate(new Date(item.releaseTime),'yyyy-MM-dd') +'</td>';
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
		var url = 'fas-pay-done-delete-reconcile.do?';
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
					addReconcile();
					alert('取消成功！');
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
				var fasPayId = $('#reconcileToAddForm #fasPayId').val();
				if(freightInvoiceId == undefined || freightInvoiceId == '' ||
						fasPayId == undefined || fasPayId == ''){
	    			alert('请选择重新操作!');
	    			return false;
				}
				$('#reconcileToAddFormBtn').hide();
				var moneyCount = new Number($('#reconcileToAddForm #' + freightInvoiceId + 'toEliminate').val());
				var url = 'fas-pay-done-add-reconcile.do?fasPayId=' + fasPayId + '&freightInvoiceId=' + freightInvoiceId + '&moneyCount=' + moneyCount;
				bootbox.animate(false);
	    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post(url, function(data){
					if(data != 'success'){
    					alert('销账失败，请检查销账金额是否超出！');
    				}else{
    					alert('销账成功！');
    					addReconcile();
    				}
					
					$('#reconcileToAddFormBtn').show();
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
			alert('删除失败，请确认所选付款申请状态！');
		}
		return flag;
	}
	
	//预览付款申请
	function browseFasPay(fasPayId){
		bootbox.animate(false);
   		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('fas-pay-browse.do?fasPayId=' + fasPayId, function(data){
			if(data != null){
				window.open('/VC/convert?fileName=' + data);
				box.modal('hide');
			}
		});
	}
    </script>
  </body>

</html>
