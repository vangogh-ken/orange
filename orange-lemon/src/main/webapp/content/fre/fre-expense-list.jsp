<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>费用管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>费用管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='fre-expense-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										<button class="btn btn-sm green" id="batchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="fre-expense-list.do" class="form-inline">
								    <label for="orderNumber">订单号*</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control">
								    
								    <label for="freightPartName">收付单位*</label>
								    <input type="text" id="freightPartName" name="freightPartName" value="${param.freightPartName}" class="form-control">
								    
								    <label for="incomeOrExpenses">收支</label>
								    <select id="incomeOrExpenses" name="incomeOrExpenses" class="form-control">
								    	<option value=""></option>
								    	<option value="INCOME" <c:if test="${param.incomeOrExpenses == 'INCOME' }">selected</c:if> >收入</option>
								    	<option value="EXPENSE" <c:if test="${param.incomeOrExpenses == 'EXPENSE' }">selected</c:if> >支出</option>
								    </select>
								    <label for="NB">NB</label>
								    <select id="NB" name="NB" class="form-control">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.NB == 'T' }">selected</c:if> >T</option>
								    	<option value="F" <c:if test="${param.NB == 'F' }">selected</c:if> >F</option>
								    </select>
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未对账" <c:if test="${param.status == '未对账' }">selected</c:if> >未对账</option>
								    	<option value="已对账" <c:if test="${param.status == '已对账' }">selected</c:if> >已对账</option>
								    	<option value="未开票" <c:if test="${param.status == '未开票' }">selected</c:if> >未开票</option>
								    	<option value="已开票" <c:if test="${param.status == '已开票' }">selected</c:if> >已开票</option>
								    	<option value="未销账" <c:if test="${param.status == '未销账' }">selected</c:if> >未销账</option>
								    	<option value="已销账" <c:if test="${param.status == '已销账' }">selected</c:if> >已销账</option>
								    </select>
								    
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-expense-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="EXPENSE_NUMBER">费用编号</th>
								                    <th class="sorting" name="FRE_EXPENSE_TYPE_ID">费用名称</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收/付</th>
								                    <th class="sorting" name="FRE_ORDER_ID">订单号</th>
								                    <th class="sorting" name="FRE_PART_ID_B">收付单位</th>
								                    <th class="sorting" name="FRE_PRICE_ID">系统报价</th>
								                    <th class="sorting" name="STATEMENT_ID">对账号</th>
								                    <th class="sorting" name="COUNT_UNIT">计费单位</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="PREDICT_COUNT">预计金额</th>
								                    <th class="sorting" name="ACTUAL_COUNT">实际金额</th>
								                    <th class="sorting" name="PRICE">是否与报价一致</th>
								                    <th class="sorting" name="ACTUAL">是否与实际一致</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.expenseNumber}</td>
								                    <td>${item.freightExpenseType.typeName}</td>
								                    <td>${item.incomeOrExpense}</td>
								                    <td>${item.freightOrder.orderNumber}</td>
								                    <td>${item.freightPartB.partName}</td>
								                    <td>${item.freightPrice.moneyCount}</td>
								                    <td>${item.freightStatement.statementNumber}</td>
								                    <td>${item.countUnit}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.predictCount}</td>
								                    <td>${item.actualCount}</td>
								                    <td>${item.price}</td>
								                    <td>${item.actual}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<a href="fre-expense-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	        'NB': '${param.NB}',
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
	
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('fre-expense-to-export-expense.do');
	});
    </script>
  </body>

</html>
