<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>在线日志</title>
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
							<div class="caption"><i class="fa fa-user"></i>在线日志</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
									<button class="btn btn-sm red" onclick="downloadLog();">
										后台日志</button>		
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
							<!--  
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="log-login-list.do" class="form-inline">
								    <label for="displayName">用户姓名</label>
								    <input type="text" id="displayName" name="displayName" value="${param.displayName}" class="form-control">
								    
								    <label for="ipAddress">IP地址</label>
								    <input type="text" id="ipAddress" name="ipAddress" value="${param.ipAddress}" class="form-control">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							-->
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="log-login-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th>登陆用户</th>
										        <th>用户IP</th>
										        <th>创建时间</th>
										        <th>最近访问时间</th>
										        <th>超时时间*分钟</th>
										        <th>是否为新创建</th>
										        <th>会话ID</th>
										      </tr>
										    </thead>
										    <tbody>
										      <c:forEach items="${sessions}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.getAttribute('userSession').displayName}</td>
										        <td>${item.getAttribute('LOGIN_IP')}</td>
										        <c:set var="creationTime" value="${item.creationTime}"></c:set>
										        <c:set var="lastAccessedTime" value="${item.lastAccessedTime}"></c:set>
										        <%
									          		pageContext.setAttribute("creationTime", new java.util.Date((Long)pageContext.getAttribute("creationTime")));
										        	pageContext.setAttribute("lastAccessedTime", new java.util.Date((Long)pageContext.getAttribute("lastAccessedTime")));
									            %>
										        <td><fmt:formatDate value="${creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td><fmt:formatDate value="${lastAccessedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td>${item.getMaxInactiveInterval()/60}</td>
										        <td>${item.isNew() == false ? '否' : '是'}</td>
										        <td>${item.id}</td>
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
	 	    pageNo: 1,
	 	    pageSize: ${sessions.size()},
	 	    totalCount: ${sessions.size()},
	 	    resultSize: ${sessions.size()},
	 	    pageCount: 1,
	 	    orderBy: '${pageView.orderBy == null ? "" : pageView.orderBy}',
	 	    asc: 'ASC',
	 	    params: {
	 	        'displayName': '${param.displayName}',
	 	        'ipAddress': '${param.ipAddress}'
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
	}); /* */
	
	function downloadLog(){
		window.open('down-load-admin-log.do');
	}
	
    </script>
  </body>
</html>
