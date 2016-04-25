<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>车辆管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>车辆管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='motor-truck-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
										<button class="btn btn-sm green" id="batchImport">
											批量导入</button>
										<button class="btn btn-sm green" id="batchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="motor-truck-list.do" class="form-inline">
								    <label for="truckType">车型</label>
								    <input type="text" id="truckType" name="truckType" value="${param.truckType}" class="form-control input-small">
								    
								    <label for="headstockNumber">车牌号</label>
								    <input type="text" id="headstockNumber" name="headstockNumber" value="${param.headstockNumber}" class="form-control input-small">
								    
								    <label for="purchaseTime">购进时间</label>
								    <input type="text" id="purchaseTime" name="purchaseTime" value="${param.purchaseTime}" class="form-control input-small datepicker">
								    
								    <label for="annualTime">年审时间</label>
								    <input type="text" id="annualTime" name="annualTime" value="${param.annualTime}" class="form-control input-small datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
										<option value="T" <c:if test="${param.status == 'T' }">selected</c:if>>启用</option>
										<option value="F" <c:if test="${param.status == 'F' }">selected</c:if>>禁用</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="motor-truck-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="TRUCK_CATEGORY">类别</th>
								                    <th class="sorting" name="TRUCK_TYPE">车型</th>
								                    <th class="sorting" name="HEADSTOCK_NUMBER">拖车号</th>
								                    <th class="sorting" name="TRAILER_NUMBER">挂车号</th>
								                    <th class="sorting" name="HEADSTOCK_FUND">拖车购车额</th>
								                    <th class="sorting" name="TRAILER_FUND">挂车购车额</th>
								                    <th class="sorting" name="HEADSTOCK_DEPRECIATION">拖车月折旧</th>
								                    <th class="sorting" name="TRAILER_DEPRECIATION">挂车月折旧</th>
								                    <th class="sorting" name="MANUFACTURER">生产厂家</th>
								                    <th class="sorting" name="PURCHASE_TIME">购进时间</th>
								                    <th class="sorting" name="ANNUAL_TIME">年审时间</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.truckCategory}</td>
								                    <td>${item.truckType}</td>
								                    <td>${item.headstockNumber}</td>
								                    <td>${item.trailerNumber}</td>
								                    <td>${item.headstockFund}</td>
								                    <td>${item.trailerFund}</td>
								                    <td>${item.headstockDepreciation}</td>
								                    <td>${item.trailerDepreciation}</td>
								                    <td>${item.manufacturer}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.purchaseTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.annualTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.descn}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<a href="motor-truck-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量导入</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form class="dropzone" id="batchImportForm" action="fas-invoice-type-to-import-invoice-type.do" enctype="multipart/form-data" method="post" class="m-form-blank">
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
	 	        'truckType': '${param.truckType}',
	 	        'licenseNumber': '${param.licenseNumber}',
	 	        'purchaseTime': '${param.purchaseTime}',
	 	        'annualTime': '${param.annualTime}',
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
	
	$(document).delegate('#batchImport', 'click',function(e){
		var margin = (window.screen.availWidth -800)/2;
		$('#batchImportModal').css("margin-left", margin);
		$('#batchImportModal').css("width","800px");
		$('#batchImportModal').modal();
	});
	
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('motor-truck-to-export-truck.do');
	});
    </script>
  </body>

</html>
