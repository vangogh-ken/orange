<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>菜单管理</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>菜单管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='business-menu-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
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
								<form name="searchForm" method="post" action="business-menu-list.do" class="form-inline">
								    <label for="manager">类型</label>
								    <input type="text" id="manager" name="manager" value="${param.manager}" class="form-control input-medium">
								     
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="business-menu-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
										  <thead>
											<tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="NAME">名称</th>
										        <th class="sorting" name="RKEY">菜单KEY</th>
										        <th class="sorting" name="ENABLE">是否可用</th>
										        <th class="sorting" name="CLS_ID">类型</th>
										        <th class="sorting" name="CONTROLLER_ID">类型控制</th>
										        <th class="sorting" name="PARENT_ID">上级资源</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.name}</td>
										        <td>${item.resourceKey}</td>
										        <td>${item.enable == 'T' ?  '可用' : '不可用'} |
													<c:if test="${item.enable != 'T'}">
														<a class="btn btn-sm red" href="${ctx}/business/business-menu-to-enable.do?id=${item.id}">激活</a>
													</c:if>
													<c:if test="${item.enable != 'F'}">
														<a class="btn btn-sm default" href="${ctx}/business/business-menu-to-disable.do?id=${item.id}">禁用</a>
													</c:if>
												</td>
										        <td>${item.businessClass.clsName}</td>
										        <td>${item.businessController.name}</td>
										        <td>${item.parentResource.name}</td>
										        <td>
										        	<a class="btn btn-sm red" href="business-menu-input.do?id=${item.id}">编辑</a>
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
	 	        'filter_LIKES_username': '${param.filter_LIKES_username}',
	 	        'filter_EQI_status': '${param.filter_EQI_status}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'department-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
	
	$(function() {
		new createListPicker({
			title:'类型属性列表', //标题
			listTitle:'属性列表',//列表标题
			modalId: 'businessClassModal',//modalID
			formId:'businessClassForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['名称','字段', '类型'],//列表头 数组
			tbody:['name','columnName','type'],//字段数据 数组
			tableId:'businessClassTable',//表ID
			multiple: true
		});
	});
    </script>
  
  </body>

</html>
