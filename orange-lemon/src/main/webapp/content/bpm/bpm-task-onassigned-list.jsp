<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>任务列表(已签收)</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>任务列表(已签收)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
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
								<form name="searchForm" method="post" action="department-list.do" class="form-inline">
									   <label for="name">名称</label>
									    <input type="text" id="name" name="name" value="${param.name}" class="form-control input-medium">
										<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
									 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="department-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
									        <tr>
									          <th width="10" style="text-indent:0px;text-align:center;"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th>流程名称</th>
									          <th>任务名称</th>
									          <th class="sorting" name="TI.START_TIME_">开始时间</th>
									          <th>任务发送人</th>
									          <th>任务委托人</th>
									          <th>任务所属</th>
									          <th>操作</th>
									        </tr>
									      </thead>
									      <tbody>
									        <c:forEach items="${pageView.results}" var="item">
									         <tr>
									          <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
									          <c:set var="processDefinitionId" value="${item.processDefinitionId}"></c:set>
									          <c:set var="processInstanceId" value="${item.processInstanceId}"></c:set>
									          <c:set var="taskId" value="${item.id}"></c:set>
									          <td><%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString()) %></td>
									          <td>${item.name}</td>
									          <td>
									           	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									          </td>
									          <td>
									          <%=TaskQueryCache.getLastTaskAssignee(
									        		  pageContext.getAttribute("taskId").toString(), 
									        		  pageContext.getAttribute("processInstanceId").toString())
									             %>
									          </td>
									          <td><%=TaskQueryCache.getAssigneeDisplayName((String)pageContext.getAttribute("taskId"))%></td>
									          <td><%=TaskQueryCache.getOwnerDisplayName((String)pageContext.getAttribute("taskId"))%></td>
									          <td>
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
    </script>
 
  </body>

</html>
