<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>操作日志</title>
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
							<div class="caption"><i class="fa fa-user"></i>操作日志</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
									
									<button class="btn btn-sm red" onclick="downloadLog();">
										后台日志</button>
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
								<form name="searchForm" method="post" action="log-operation-list.do" class="form-inline">
								    <label for="displayName">操作用户</label>
								    <input type="text" id="displayName" name="displayName" value="${param.displayName}" class="form-control">
								    
								    <label for="type">操作类型</label>
								    <input type="text" id="type" name="type" value="${param.type}" class="form-control">
								    
								    <label for="module">操作内容</label>
								    <input type="text" id="module" name="module" value="${param.module}" class="form-control">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="log-operation-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										         <th class="sorting" name="USER_ID">操作人</th>
										        <th class="sorting" name="TYPE">操作类型</th>
										        <th class="sorting" name="MODULE">操作内容</th>
										        <th class="sorting" name="ACTION_TIME">执行时间(毫秒)</th>
										        <th class="sorting" name="CREATE_TIME">创建时间</th>
										        <th class="sorting" name="IP">IP地址</th>
										        <th class="sorting" name="PRIMARY_KEY">主键</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.user.displayName}</td>
										        <td>${item.type}</td>
										        <td>${item.module}</td>
										        <td>${item.actionTime}</td>
										        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td>${item.ipAddress}</td>
										        <td>${item.primaryKey}</td>
										        <td>&nbsp;</td>
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
	 	        'displayName': '${param.displayName}',
	 	        'type': '${param.type}',
	 	        'module': '${param.module}'
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
	
	
	function downloadLog(){
		window.open('down-load-admin-log.do');
	}
    </script>
 
  </body>

</html>
