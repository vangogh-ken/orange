<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>成本管理(客服)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>成本管理(客服)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<!--  
										<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
											<button class="btn btn-sm red" onclick="location.href='fre-price-input.do'">
												新增<i class="fa fa-arrows"></i></button>
										</sec:authorize>
										
										<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
											<button class="btn btn-sm red" onclick="table.removeAll();">
												删除<i class="fa fa-arrows-alt"></i></button>
										</sec:authorize>
										<button class="btn btn-sm red" onclick="location.href='fre-price-input-batch.do'">
												按箱批量</button>
												
										<button class="btn btn-sm green" id="batchImport">
												批量导入</button>
												
											<button class="btn btn-sm green" id="batchExport">
												批量导出</button>
									-->
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
								<form name="searchForm" method="post" action="fre-price-list-service.do" class="form-inline">
								    <label for="partName">单位名称</label>
								    <input type="text" id="partName" name="partName" value="${param.partName}" class="form-control">
								    
								    <label for="expenseTypeName">费用类型</label>
								    <input type="text" id="expenseTypeName" name="expenseTypeName" value="${param.expenseTypeName}" class="form-control">
								    
								    <label for="pactNumber">相关合同编号</label>
								    <input type="text" id="pactNumber" name="pactNumber" value="${param.pactNumber}" class="form-control">
								    <!--  
								    <label for="status">状态</label>
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control">
								    -->
								    <label for="countUnit">计价方式</label>
								    <select id="countUnit" name="countUnit" class="form-control">
								    	<option value=""></option>
								    	<option value="票" <c:if test="${param.countUnit == '票' }">selected</c:if> >票</option>
								    	<option value="20'FR" <c:if test="${param.countUnit == '20&#39FR' }">selected</c:if> >20'FR</option>
								    	<option value="20'GP" <c:if test="${param.countUnit == '20&#39GP' }">selected</c:if> >20'GP</option>
								    	<option value="20'HC" <c:if test="${param.countUnit == '20&#39HC' }">selected</c:if> >20'HC</option>
								    	<option value="20'OT" <c:if test="${param.countUnit == '20&#39OT' }">selected</c:if> >20'OT</option>
								    	<option value="20'RF" <c:if test="${param.countUnit == '20&#39RF' }">selected</c:if> >20'RF</option>
								    	<option value="40'FR" <c:if test="${param.countUnit == '40&#39FR' }">selected</c:if> >40'FR</option>
								    	<option value="40'GP" <c:if test="${param.countUnit == '40&#39GP' }">selected</c:if> >40'GP</option>
								    	<option value="40'HC" <c:if test="${param.countUnit == '40&#39HC' }">selected</c:if> >40'HC</option>
								    	<option value="40'OT" <c:if test="${param.countUnit == '40&#39OT' }">selected</c:if> >40'OT</option>
								    	<option value="40'RF" <c:if test="${param.countUnit == '40&#39RF' }">selected</c:if> >40'RF</option>
								    	<option value="45'HC" <c:if test="${param.countUnit == '45&#39HC' }">selected</c:if> >45'HC</option>
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
								                    <th class="sorting" name="FRE_PART_ID">单位</th>
								                    <th class="sorting" name="FRE_EXPENSE_TYPE_ID">价格名称</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE_ID">发票票种</th>
								                    <th class="sorting" name="REGULAR">是否为固定费用</th>
								                    <th class="sorting" name="COUNT_UNIT">计价单位</th>
								                    <th class="sorting" name="MONEY_COUNT">金额</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <!--
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                    -->
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.freightPart.partName}</td>
								                    <td>${item.freightExpenseType.typeName}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td>${item.regular == 'T'?'是':'否'}</td>
								                    <td>${item.countUnit}</td>
								                    <td>${item.moneyCount}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <!--
								                    <td>${item.exchangeRate}</td> 
								                    <td>${item.status}</td>
								                    <td>
								                    	<a href="fre-price-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量导入</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form class="dropzone" id="batchImportForm" action="fre-price-to-import-price.do" enctype="multipart/form-data" method="post" class="m-form-blank">
				</form>
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
	 	        'expenseTypeName': '${param.expenseTypeName}',
	 	        'pactNumber': '${param.pactNumber}',
	 	        'countUnit': "${param.countUnit}",
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
	
	$(document).delegate('#batchImport', 'click',function(e){
		var margin = (window.screen.availWidth -800)/2;
		$('#batchImportModal').css("margin-left", margin);
		$('#batchImportModal').css("width","800px");
		$('#batchImportModal').modal();
	});
	
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('fre-price-to-export-price.do');
	});
    </script>
  </body>

</html>
