<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>付款账单(复核)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>付款账单(复核)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm green" id="viewExpense" >
										费用明细</button>
									<button class="btn btn-sm red" id="doneStatementAudit">
										审核通过</button>
									<button class="btn btn-sm red" id="backStatementAudit">
										审核退回</button>
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
								<form name="searchForm" method="post" action="fre-statement-list-audit.do" class="form-inline">
								    <label for="partName">单位名称</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="partType">类型</label>
								    <input type="text" id="partType" name="partType" value="${param.partType}" class="form-control">
								    
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
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="STATEMENT_NUMBER">对账单号</th>
								                    <th class="sorting" name="FRE_PART_ID">收付单位</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE">票种</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="MONEY_COUNT_RMB">金额(RMB)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_RMB">已开票金额(RMB)</th>
								                    <th class="sorting" name="REMAIN_COUNT_RMB">未开票金额(RMB)</th>
								                    <th class="sorting" name="MONEY_COUNT_DOLLAR">金额(USD)</th>
								                    <th class="sorting" name="ELIMINATE_COUNT_DOLLAR">已开票金额(USD)</th>
								                    <th class="sorting" name="REMAIN_COUNT_DOLLAR">未开票金额(USD)</th>
								                    <th class="sorting" name="STATUS">状态</th>
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
			listData(url, '已关联费用', 1000,
					['编号', '费用名称','收/付','收付单位','票/箱','汇率','币种','预计单价','预计总额','实际单价','实际总额'], 
					['expenseNumber', 'freightExpenseType.typeName','incomeOrExpense', 'freightPartB.partName', 'countUnit', 'exchangeRate','currency','predictCount','predictAmount','actualCount','actualAmount']
			);
		}
	});
	
	//审核账单 
	$(document).delegate('#doneStatementAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-statement-done-statement-audit.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '审核中' && status != '锁定中'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightStatementId=' +　$(item).val();
					}else{
						url += '&freightStatementId=' +　$(item).val();
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
	//审核退回
	$(document).delegate('#backStatementAudit', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-statement-back-statement-audit.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '审核中' && status != '锁定中'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightStatementId=' +　$(item).val();
					}else{
						url += '&freightStatementId=' +　$(item).val();
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
				alert('退回失败！请确认所选账单状态！');
				return;
			}
		}
	});
    </script>
  </body>

</html>
