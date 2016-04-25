<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>类型管理</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>类型管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='business-class-input.do'">
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
								<form name="searchForm" method="post" action="business-class-list.do" class="form-inline">
								    <label for="clsName">类型名称</label>
	    							<input type="text" id="clsName" name="clsName" value="${param.clsName}" class="form-control input-medium">
								    
								    <label for="tableName">库表名称</label>
	    							<input type="text" id="tableName" name="tableName" value="${param.tableName}" class="form-control input-medium">
	    							
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="business-class-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="CLS_NAME">名称</th>
										        <th class="sorting" name="TABLE_NAME">表名</th>
										        <th class="sorting" name="DESCN">描述</th>
										        <th>创建行为</th>
										        <th>修改行为</th>
										        <th>删除行为</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.clsName}</td>
										        <td>${item.tableName}</td>
										        <td>${item.description}</td>
										        <td>${(item.beforeCreateBehaviors!=null and item.afterCreateBehaviors!=null)?'全':item.beforeCreateBehaviors!=null?'前':item.afterCreateBehaviors!=null?'后':'无'}</td>
										        <td>${(item.beforeModifyBehaviors!=null and item.afterModifyBehaviors!=null)?'全':item.beforeModifyBehaviors!=null?'前':item.afterModifyBehaviors!=null?'后':'无'}</td>
										        <td>${(item.beforeDeleteBehaviors!=null and item.afterDeleteBehaviors!=null)?'全':item.beforeDeleteBehaviors!=null?'前':item.afterDeleteBehaviors!=null?'后':'无'}</td>
										        <td>
										        	<a class="btn btn-sm red" href="business-class-input.do?id=${item.id}">编辑</a>
										        	<a class="btn btn-sm red businessClassModal-add-on" urlAttr='${ctx}/business/business-class-attributes.do?id=${item.id}'>所有字段</a>
										        	<a class="btn btn-sm red" href="business-attribute-input.do?clsId=${item.id}">添加字段</a>
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
	 	        'tableName': '${param.tableName}',
	 	        'clsName': '${param.clsName}'
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
			modalId: 'businessClassModal',//modalID
			formId:'businessClassForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['名称','字段', '类型', '值类型','显示顺序','页面显示','列表显示'],//列表头 数组
			tbody:['name','columnName','type', 'valueType', 'displayOrder', 'displayPage', 'displayGrid'],//字段数据 数组
			tableId:'businessClassTable',//表ID
			multiple: true,
			canSelect:false
		});
	});
    </script>
  </body>

</html>
