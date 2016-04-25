<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>应付款台账</title>
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
							<div class="caption"><i class="fa fa-dollar"></i>应付款台账</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-PAY-INVOICE-PAY-AUDIT">
									<button class="btn btn-sm green" id="doneInvoicePayAudit">
											付款初审</button>
									<button class="btn btn-sm green" id="backInvoicePayAudit">
											初审退回</button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-PAY-INVOICE-PAY-REHEAR">
									<button class="btn btn-sm red" id="doneInvoicePayRehear">
											付款复审</button>
									<button class="btn btn-sm red" id="backInvoicePayRehear">
											复审退回</button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_FRE-INVOICE-LIST-PAY-INVOICE-PAY-EVENTIDE">
									<button class="btn btn-sm green" id="doneInvoicePayEventide">
											付款终审</button>
									<button class="btn btn-sm green" id="backInvoicePayEventide">
											终审退回</button>
									</sec:authorize>
									
									<button class="btn btn-sm red" id="backInvoicePay">
											取消审核</button>
									<button class="btn btn-sm red" id="invoiceOffset">
											开票冲抵</button>
											
									<button class="btn btn-sm green" id="toBatchoffset">
											批量冲抵</button>
									<button class="btn btn-sm green" id="deleteBatchoffset">
											取消批量</button>
											
									<button class="btn btn-sm red" id="batchExport">
											批量导出</button>
									<button class="btn btn-sm red" id="forecastExport">
											预报导出</button>
									<button class="btn btn-sm green" id="toBatchOffsetExport">
											冲抵导出</button>
									<button class="btn btn-sm green" id="viewExpense" >
											关联费用</button>	
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
								<form id="searchForm" name="searchForm" method="post" action="fre-invoice-list-pay.do" class="form-inline">
								    <label for="partName">单位</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control input-xsmall">
								    
								    <label for="invoiceNumber">发票号</label>
								    <input type="text" id="invoiceNumber" name="invoiceNumber" value="${param.invoiceNumber}" class="form-control input-xsmall">
								    
								    <label for="currency">币种</label>
								    <select id="currency" name="currency" class="form-control">
								    	<option value=""></option>
								    	<option value="人民币" <c:if test="${param.currency == '人民币' }">selected</c:if> >人民币</option>
								    	<option value="美元" <c:if test="${param.currency == '美元' }">selected</c:if> >美元</option>
								    </select>
								    
								    <label for="netDayTime">账期时间</label>
								    <input type="text" id="netDayTimeStart" name="netDayTimeStart" value="${param.netDayTimeStart}" class="form-control datepicker input-xsmall">
								    <input type="text" id="netDayTimeEnd" name="netDayTimeEnd" value="${param.netDayTimeEnd}" class="form-control datepicker input-xsmall">
									
									<label for="releaseTime">开票时间</label>
								    <input type="text" id="releaseTimeStart" name="releaseTimeStart" value="${param.releaseTimeStart}" class="form-control datepicker input-xsmall">
								    <input type="text" id="releaseTimeEnd" name="releaseTimeEnd" value="${param.releaseTimeEnd}" class="form-control datepicker input-xsmall">
								    			    
								    <hr class="search-input-spliter">
								    
								    <label for="orderNumber">订单</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    <label for="salesman">业务员</label>
								    <input type="text" id="salesman" name="salesman" value="${param.salesman}" class="form-control input-xsmall">
								    <label for="orgEntityName">部门</label>
								    <input type="text" id="orgEntityName" name="orgEntityName" value="${param.orgEntityName}" class="form-control input-xsmall">
								    <label for="delayDays">超期天数</label>
								    <input type="text" id="delayDays" name="delayDays" value="${param.delayDays}" class="form-control input-xsmall number">
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="待销账" <c:if test="${param.status == '待销账' }">selected</c:if> >待销账</option>
								    	<option value="销账中" <c:if test="${param.status == '销账中' }">selected</c:if> >销账中</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
								    	<option value="冲抵过" <c:if test="${param.status == '冲抵过' }">selected</c:if> >冲抵过</option>
								    	<option value="已冲抵销账" <c:if test="${param.status == '已冲抵销账' }">selected</c:if> >已冲抵销账</option>
								    	<option value="已批量冲抵销账" <c:if test="${param.status == '已批量冲抵销账' }">selected</c:if> >已批量冲抵销账</option>
								    	<option value="付款初审中" <c:if test="${param.status == '付款初审中' }">selected</c:if> >付款初审中</option>
								    	<option value="付款复审中" <c:if test="${param.status == '付款复审中' }">selected</c:if> >付款复审中</option>
								    	<option value="付款终审中" <c:if test="${param.status == '付款终审中' }">selected</c:if> >付款终审中</option>
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
								                    <th class="sorting" name="FRE_PART_ID">开票单位</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="FAS_INVOICE_ID">发票号</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE_ID">票种</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="MONEY_COUNT">金额</th>
								                    <th class="sorting" name="MONEY_COUNT">折合金额</th>
								                    <th class="sorting" name="REMAIN_COUNT">未销账</th>
								                    <th class="sorting" name="ELIMINATE_COUNT">已销账</th>
								                    <th class="sorting" name="STATEMENT_ID">账单号</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="RELEASE_TIME">开具时间</th>
								                    <th class="sorting" name="EFFECT_TIME">账期时间</th>
								                    <th>相关订单</th>
								                    <th>业务员</th>
								                    <th>付款备注</th>
								                    <th>账单备注</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td id="${item.id}partName">${item.freightPart.partName}</td>
								                    <td>${item.incomeOrExpense}</td>
								                    <td>${item.fasInvoice.invoiceNumber}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.moneyCount}</td>
								                    <td>
								                    	<fmt:formatNumber value="${item.moneyCount*item.exchangeRate}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td>${item.remainCount}</td>
								                    <td>${item.eliminateCount}</td>
								                    <td>${item.freightStatement == null &&  item.fasInvoice.invoiceNumber.endsWith('-CD') ? '批量冲抵生成' : item.freightStatement.statementNumber}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.effectTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<c:set var="orderNumbers" value=""></c:set>
								                    	<c:forEach items="${item.freightStatement.freightExpenses}" var="freightExpense" varStatus="varStatus">
								                    	<c:if test="${freightExpense.freightOrder != null}">
								                    		<c:set var="orderNumbers" value="${freightExpense.freightOrder.orderNumber},${orderNumbers}"></c:set>
								                    	</c:if>
								                    	</c:forEach>
								                    	${orderNumbers}
								                    </td>
								                    <td>
								                    	<c:set var="salesmans" value=""></c:set>
								                    	<c:forEach items="${item.freightStatement.freightExpenses}" var="freightExpense" varStatus="varStatus">
								                    	<c:if test="${freightExpense.freightOrder != null && !salesmans.contains(freightExpense.freightOrder.salesman)}">
								                    		<c:set var="salesmans" value="${freightExpense.freightOrder.salesman},${salesmans}"></c:set>
								                    	</c:if>
								                    	</c:forEach>
								                    	${salesmans}
								                    </td>
								                    <td>${item.descn}</td>
								                    <td>${item.freightStatement.descn}</td>
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
	
	<!-- 开票冲抵-->
	<div class="modal fade" id="invoiceOffsetModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">开票冲抵</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="invoiceInfoForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget" style="max-height:150px;overflow-y:scroll; ">
				<form id="invoiceOffsetToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="invoiceOffsetBtnForm" name="invoiceOffsetBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="doneInvoiceOffsetCareCurrency();">同币冲抵</button>
						<button class="btn btn-sm red" onclick="doneInvoiceOffsetIgnoreCurrency();">折后冲抵</button>
						<button class="btn btn-sm green" onclick="deleteInvoiceOffset();">取消冲抵</button>
					</form>
				</article>
				
				<article class="m-widget" style="max-height:150px;overflow-y:scroll; ">
				<form id="invoiceOffsetHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 批量冲抵-->
	<div class="modal fade" id="toBatchoffsetModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量冲抵</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
					<form id="toBatchoffsetBtnForm" name="invoiceOffsetBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="doneBatchoffset('careCurrencyBatch');">同币冲抵</button>
						<button class="btn btn-sm red" onclick="doneBatchoffset('ignoreCurrencyBatch');">折后冲抵</button>
					</form>
				</article>
				<article class="m-widget" style="max-height:350px;overflow-y:scroll;">
				<form id="toBatchoffsetToAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	    	'salesman': '${param.salesman}',
	 	    	'orgEntityName': '${param.orgEntityName}',
	 	    	'delayDays': '${param.delayDays}',
	 	    	'orderNumber': '${param.orderNumber}',
	 	    	'partName': '${param.partName}',
	 	        'invoiceNumber': '${param.invoiceNumber}',
	 	        'currency': '${param.currency}',
	 	        'netDayTimeStart': '${param.netDayTimeStart}',
	 	        'netDayTimeEnd': '${param.netDayTimeEnd}',
	 	        'releaseTimeStart': '${param.releaseTimeStart}',
	 	        'releaseTimeEnd': '${param.releaseTimeEnd}',
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
	
	//批量导出
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('fre-invoice-to-export-invoice-pay.do?' + $('#searchForm').serialize());
	});
	
	//预报导出
	$(document).delegate('#forecastExport', 'click',function(e){
		bootbox.animate(false);
		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('fre-invoice-to-export-forecast-to-file.do', $('#dynamicGridForm').serialize(), function(data){
			window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('应付款预报列表.xls'));
			box.modal('hide');
		});
	});
	
	$(document).delegate('#toBatchOffsetExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length == 1){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('fre-invoice-to-export-batch-offset-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('开票冲抵列表.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择一条数据！');
		}
		
	});
	
	//付款审核//////////////////////////////////////////////////////////////////////////////////////////////
	//付款初审
	$(document).delegate('#doneInvoicePayAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-done-invoice-pay-audit.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款初审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//初审退回
	$(document).delegate('#backInvoicePayAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-back-invoice-pay-audit.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款初审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//付款复审
	$(document).delegate('#doneInvoicePayRehear', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-done-invoice-pay-rehear.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款复审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//复审退回
	$(document).delegate('#backInvoicePayRehear', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-back-invoice-pay-rehear.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款复审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//付款终审
	$(document).delegate('#doneInvoicePayEventide', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-done-invoice-pay-eventide.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款终审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//终审退回
	$(document).delegate('#backInvoicePayEventide', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-back-invoice-pay-eventide.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '付款终审中'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！请确认所选付款任务状态！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选付款任务状态！');
			}
		}
	});
	
	//开票冲抵///////////////////////////////////////////////////////////////////////
	$(document).delegate('#invoiceOffset', 'click', function(e){
		invoiceOffset();
	});
	
	function invoiceOffset(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var status = $('#'+$('.selectedItem:checked').val()+'status').text();
			if(status != '待销账' && status != '冲抵过'){
				alert(status + '状态中的开票不能冲抵！');
				return false;
			}else{
				var freightInvoiceId = $('.selectedItem:checked').val();
				var url = 'fre-invoice-to-invoice-offset.do?freightInvoiceId=' + freightInvoiceId;
				$.post(url, function(data){
					var toOffsetInvoice = data.toOffsetInvoice;//可冲抵账单
					var hasOffsetInvoice = data.hasOffsetInvoice;//已冲抵账单
					var freightInvoice = data.freightInvoice;//账单信息
					
					var html = '<table id="invoiceInfoTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th>收付单位</th><th>收/付</th><th>发票号</th><th>发票票种</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th></tr></thead>';
					html += '<tbody><tr><td>'+freightInvoice.freightPart.partName+'</td>';
					html += '<td>'+freightInvoice.incomeOrExpense+'</td>';
					html += '<td>'+freightInvoice.fasInvoice.invoiceNumber+'</td>';
					html += '<td>'+freightInvoice.fasInvoiceType.typeName+'</td>';
					html += '<td>'+freightInvoice.currency+'</td>';
					html += '<td id="exchangeRate">'+freightInvoice.exchangeRate+'</td>';
					html += '<td>'+freightInvoice.moneyCount+'</td>';
					html += '<td>'+freightInvoice.eliminateCount+'</td>';
					html += '<td id="remainCount">'+freightInvoice.remainCount+'</td>';
					html += '</tr></tbody></table>';
					$('#invoiceOffsetModal #invoiceInfoForm').html(html);
					
					html = '<input id="sourceInvoiceId" type="hidden" value="' + freightInvoiceId + '">';
					html += '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					html += '<th width="20"></th>';
					html += '<th>收付单位</th><th>收/付</th><th>发票号</th><th>发票票种</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th></tr></thead>';
					html += '</tr></thead><tbody>';
					$.each(toOffsetInvoice, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="radio" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.eliminateCount + '</td>';
						html += '<td>' + item.remainCount + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#invoiceOffsetToAddForm').html(html);
					
					html = '<table id="invoiceTable" class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>收付单位</th><th>收/付</th><th>发票号</th><th>发票票种</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th></tr></thead>';
					html += '</tr></thead><tbody>';
					$.each(hasOffsetInvoice, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightPart.partName + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.eliminateCount + '</td>';
						html += '<td>' + item.remainCount + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#invoiceOffsetHasAddForm').html(html);
					
					var margin = (window.screen.availWidth - 1200)/2;
					$('#invoiceOffsetModal').css("margin-left", margin);
					$('#invoiceOffsetModal').css("width","1200px");
					$('#invoiceOffsetModal').modal();
				});
			}
			return true;
		}
	}
	
	function doneInvoiceOffsetCareCurrency(){
		if($('#invoiceOffsetToAddForm .selectedItemId:checked').length == 1){
			var sourceInvoiceId = $('#invoiceOffsetToAddForm #sourceInvoiceId').val();
			var targetInvoiceId = $('#invoiceOffsetToAddForm .selectedItemId:checked').val();
			if(window.confirm('确认进行冲抵销账吗？')){
				$.post('fre-invoice-done-invoice-offset-care-currency.do?sourceInvoiceId=' 
						+　sourceInvoiceId + '&targetInvoiceId=' + targetInvoiceId, function(data){
					if(data == 'success'){
						invoiceOffset();
					}else{
						alert('冲抵开票失败！');
					}
					
				});
			}
		}else{
			alert('请选择一条数据！');
		}
	}
	
	function doneInvoiceOffsetIgnoreCurrency(){
		if($('#invoiceOffsetToAddForm .selectedItemId:checked').length == 1){
			var sourceInvoiceId = $('#invoiceOffsetToAddForm #sourceInvoiceId').val();
			var targetInvoiceId = $('#invoiceOffsetToAddForm .selectedItemId:checked').val();
			if(window.confirm('确认进行冲抵销账吗？')){
				$.post('fre-invoice-done-invoice-offset-ignore-currency.do?sourceInvoiceId=' 
						+　sourceInvoiceId + '&targetInvoiceId=' + targetInvoiceId, function(data){
					if(data == 'success'){
						invoiceOffset();
					}else{
						alert('冲抵开票失败！');
					}
					
				});
			}
		}else{
			alert('请选择一条数据！');
		}
	}
	
	//取消冲抵
	function deleteInvoiceOffset(){
		if($('#invoiceOffsetHasAddForm .selectedItemId:checked').length == 1){
			var sourceInvoiceId = $('#invoiceOffsetToAddForm #sourceInvoiceId').val();
			var targetInvoiceId = $('#invoiceOffsetHasAddForm .selectedItemId:checked').val();
			if(window.confirm('确认取消冲抵销账吗？')){
				$.post('fre-invoice-delete-invoice-offset.do?sourceInvoiceId=' 
						+　sourceInvoiceId + '&targetInvoiceId=' + targetInvoiceId, function(data){
					if(data == 'success'){
						invoiceOffset();
					}else{
						alert('取消冲抵失败！');
					}
				});
			}
		}else{
			alert('请选择一条数据！');
		}
	}
	
	//取消付款审核
	$(document).delegate('#backInvoicePay', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = false;
			var url = 'fre-invoice-back-invoice-pay.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '待销账'){
					flag = true;
				}else{
					url += 'freightInvoiceId=' +　$(item).val() + '&';
				}
			});
			if(!flag){
				url = url.substring(0, url.length - 1);
				$.post(url, function(data){
					if(data != 'success'){
						alert('取消失败！请确认所选付款任务状态！');
					}else{
						alert('取消成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('取消失败！请确认所选付款任务状态！');
			}
		}
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
	
	
	//批量冲抵
	$(document).delegate('#toBatchoffset', 'click', function(e){
		toBatchoffset();
	});
	
	function toBatchoffset(){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
		}else{
			var flag = true;
			var url = 'fre-invoice-to-batch-offset.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '待销账'){
					flag = false;
					return false;
				}else{
					if(i == 0){
						url += 'freightInvoiceId=' + $(item).val();
					}else{
						url += '&freightInvoiceId=' + $(item).val();
					}
				}
			});
			
			if(flag){
				$.post(url, function(data){
					var result = data.result;//确定是否可冲抵
					var freightInvoices = data.freightInvoices;//可冲抵
					if(result == 'true'){
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>收付单位</th><th>收/付</th><th>发票号</th><th>发票票种</th><th>币种</th><th>汇率</th><th>金额</th><th>已销账金额</th><th>未销账金额</th></tr></thead>';
						html += '</tr></thead><tbody>';
						$.each(freightInvoices, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
							html += '<td>' + item.freightPart.partName + '</td>';
							html += '<td>' + item.incomeOrExpense + '</td>';
							html += '<td>' + item.fasInvoice.invoiceNumber + '</td>';
							html += '<td>' + item.fasInvoiceType.typeName + '</td>';
							html += '<td>' + item.currency + '</td>';
							html += '<td>' + item.exchangeRate + '</td>';
							html += '<td>' + item.moneyCount + '</td>';
							html += '<td>' + item.eliminateCount + '</td>';
							html += '<td>' + item.remainCount + '</td></tr>';
						});
						html += "</tbody></table>";
						$('#toBatchoffsetToAddForm').html(html);
						
						var margin = (window.screen.availWidth - 1200)/2;
						$('#toBatchoffsetModal').css("margin-left", margin);
						$('#toBatchoffsetModal').css("width","1200px");
						$('#toBatchoffsetModal').modal();
					}else{
						alert('请确认应付款状态和单位是否一致！');
					}
				});
			}else{
				alert('请确认应付款状态和单位是否一致！');
			}
		}
	}
	
	function doneBatchoffset(offsetType){
		if($('.selectedItem:checked').length ==0 || $('#toBatchoffsetToAddForm .selectedItemId:checked').length == 0 ){
			alert('请选择数据！');
		}else{
			var url = 'fre-invoice-done-batch-offset.do?offsetType=' + offsetType;
			$('.selectedItem:checked').each(function(i, item){
				url += '&freightInvoiceIdsOut=' + $(item).val();
			});
			
			$('#toBatchoffsetToAddForm .selectedItemId:checked').each(function(i, item){
				url += '&freightInvoiceIdsIn=' + $(item).val();
			});
			bootbox.animate(false);
    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post(url, function(data){
				if(data.result == 'true'){
					var text = '批量冲抵成功！新生成开票信息：\r\n';
					$.each(data.resultInvoice, function(i, item){
						text += item.incomeOrExpense + ': ' + item.fasInvoice.invoiceNumber + '\n';
					});
					
					alert(text);
					window.location.href = window.location.href;
				}else{
					alert('批量冲抵失败，确认收付款是否将完全冲抵！');
				}
				box.modal('hide');
			});
		}
	}
	
	$(document).delegate('#deleteBatchoffset', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择数据!');
			return;
		}else{
			var freightInvoiceId = $('.selectedItem:checked').val();
			var status = $('#' + freightInvoiceId + 'status').text();
			if(status != '待销账'){
				alert('请确认该开票状态！');
			}else{
				var url = 'fre-invoice-delete-batch-offset.do?freightInvoiceId=' + freightInvoiceId;
				$.post(url, function(data){
					if(data != 'success'){
						alert('取消失败！请确认所选付款任务状态！');
					}else{
						alert('取消成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}
		}
	});
	
	//查看相关账单明细
	$(document).delegate('#viewExpense', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightInvoiceId = $('.selectedItem:checked').val();
			var url = 'fre-expense-get-byinvoiceid.do?freightInvoiceId=' + freightInvoiceId;
			listData(url, '关联费用', 1000,
					['编号', '费用名称','收/付','收付单位','票/箱','汇率','币种','预计单价','预计总额','实际单价','实际总额'], 
					['expenseNumber', 'freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','predictAmount','actualCount','actualAmount']
			);
		}
	});
    </script>
  </body>

</html>
