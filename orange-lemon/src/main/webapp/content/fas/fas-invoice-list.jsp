<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>发票管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>发票管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_CY-FAS-TAX-EMP">
										<button class="btn btn-sm red" onclick="location.href='fas-invoice-input.do'">
											新增<i class="fa fa-arrows"></i></button>
											
										<button class="btn btn-sm red" onclick="location.href='fas-invoice-input-batch.do'">
											批量新增</button>
									</sec:authorize>
									
									<!--  暂不删除
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
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
								<form name="searchForm" method="post" action="fas-invoice-list.do" class="form-inline">
								    <label for="invoiceNumber_">发票号</label>
								    <input type="text" id="invoiceNumber_" name="invoiceNumber_" value="${param.invoiceNumber_}" class="form-control">
								    
								    <label for="fasInvoiceTypeId">发票票种</label>
								    <select id="fasInvoiceTypeId" name="fasInvoiceTypeId" class="form-control required">
									<option value="">全部</option>
									<c:forEach items="${fasInvoiceTypes}" var="fasInvoiceType">
									<option value="${fasInvoiceType.id}" <c:if test="${param.fasInvoiceTypeId == fasInvoiceType.id}">selected</c:if>>${fasInvoiceType.typeName}</option>
									</c:forEach>
									</select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
										<option value=""></option>
										<option value="可用"<c:if test="${item.param == '可用'}">selected</c:if> >可用</option>
										<option value="已用"<c:if test="${item.param == '已用'}">selected</c:if> >已用</option>
										<option value="已作废"<c:if test="${item.param == '已作废'}">selected</c:if> >已作废</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fas-invoice-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="INVOICE_NUMBER">发票号</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE_ID">票种</th>
								                    <th class="sorting" name="CURRENCY">币种</th>
								                    <th class="sorting" name="EXCHANGE_RATE">汇率</th>
								                    <th class="sorting" name="MONEY_COUNT">金额</th>
								                    <th class="sorting" name="INCOME_OR_EXPENSE">收支</th>
								                    <th class="sorting" name="RELEASE_TIME">开票时间</th>
								                    <th class="sorting" name="RECORD_TIME">录入系统时间</th>
								                    <th class="sorting" name="CLAIM_TIME">领取时间</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.invoiceNumber}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td>${item.currency}</td>
								                    <td>${item.exchangeRate}</td>
								                    <td>${item.moneyCount}</td>
								                    <td>${item.incomeOrExpense}</td>
								                     <td>
								                    	<fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.recordTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.claimTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>${item.status}</td>
								                    <td>
								                    	<a href="fas-invoice-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<!-- 关联人员 -->
	<div class="modal fade" id="actionTypeIdentityModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加动作类型</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
					<form id="actionTypeIdentitySearchForm" name="actionTypeIdentitySearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="displayName">用户名称</label>
						<input type="text" id="displayName" name="displayName" class="form-control">
						<button class="btn btn-sm red" onclick="filterList();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="actionTypeIdentityToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="actionTypeBtnForm" name="actionTypeIdentityBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="submitActionType();">添加</button>
						<button class="btn btn-sm red" onclick="deleteActionType();">删除</button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="actionTypeIdentityHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	        'scope': '${param.scope}',
	 	        'typeName': '${param.typeName}',
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
	
	$(document).delegate('#viewActionField', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-field-by-actiontypeid.do?freightActionTypeId=' + freightActionTypeId;
			listData(url, '字段信息', 1000,
					['字段名','字段(库)','字段类型','是否必须','是否共享'], 
					['fieldName','fieldColumn', 'fieldType' ,'required','participate']
			);
		}
	});
	
	$(document).delegate('#viewActionTypeIdentity', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-type-identity-by-actiontypeid.do?freightActionTypeId=' + freightActionTypeId;
			listData(url, '已委派人员信息', 1000,
					['姓名','职位'], 
					['DISPLAY_NAME','POSITION_NAME']
			);
		}
	});
	
	$(document).delegate('#addActionTypeIdentity', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			window.open('fre-action-type-identity-input.do?freightActionTypeId=' + freightActionTypeId);
		}
	});
	
    </script>
  </body>

</html>
