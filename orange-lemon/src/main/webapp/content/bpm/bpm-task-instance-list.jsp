<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>任务实例管理</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>任务实例管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left" >
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
								<form name="searchForm" method="post" action="bpm-task-instance-list.do" class="form-inline">
									 <label for="processDefinitionName">流程名称</label>
							    	<input type="text" id="processDefinitionName" name="processDefinitionName" value="${param.processDefinitionName}" class="form-control input-xsmall">
							    	
							    	<label for="taskName">任务名称</label>
							    	<input type="text" id="taskName" name="taskName" value="${param.taskName}" class="form-control input-xsmall">
							    
							    	<label for="startUserName">发起人</label>
							    	<input type="text" id="startUserName" name="startUserName" value="${param.startUserName}" class="form-control input-xsmall">
							    	
							    	<label for="assigneeUserName">委托人</label>
							    	<input type="text" id="assigneeUserName" name="assigneeUserName" value="${param.assigneeUserName}" class="form-control input-xsmall">
							    	
							    	<label for="ownerUserName">所属人</label>
							    	<input type="text" id="ownerUserName" name="ownerUserName" value="${param.ownerUserName}" class="form-control input-xsmall">
									
									<label for="assigned">是否签收</label>
									<select id="assigned" name="assigned" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.assigned == 'T'}">selected</c:if>>是</option>
										<option value="F" <c:if test="${param.assigned == 'F'}">selected</c:if>>否</option>
									</select>
									
									<label for="finished">是否完成</label>
									<select id="finished" name="finished" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.finished == 'T'}">selected</c:if>>是</option>
										<option value="F" <c:if test="${param.finished == 'F'}">selected</c:if>>否</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
									        <tr>
									          <th width="10" style="text-indent:0px;text-align:center;"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th class="sorting" name="TI.PROC_DEF_ID_">流程名称</th>
									          <th class="sorting" name="TI.NAME_">任务名称</th>
									          <th class="sorting" name="TI.START_TIME_">开始时间</th>
									          <th>任务发送人</th>
									          <th class="sorting" name="TI.ASSIGNEE_">任务委托人</th>
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
									          <td><%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString())%></td>
									          <td>${item.name}</td>
									          <td>
									           	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									          </td>
									          <td>
									          <%=TaskQueryCache.getLastTaskAssignee(
									        		  pageContext.getAttribute("taskId").toString(), 
									        		  pageContext.getAttribute("processInstanceId").toString())%>
									          </td>
											  <td><%=TaskQueryCache.getAssigneeDisplayName((String)pageContext.getAttribute("taskId"))%></td>
									          <td><%=TaskQueryCache.getOwnerDisplayName((String)pageContext.getAttribute("taskId"))%></td>
									          <td>
									          	<a class="btn btn-sm red" href="bpm-view-business.do?processInstanceId=${item.processInstanceId}" target="_blank">查看</a>
									          	<a class="btn btn-sm red" onclick="showGraph('${item.processInstanceId}')">流程图</a>
									          	<a class="btn btn-sm red" href="console-prepare-jump.do?executionId=${item.executionId}">纠正跳转</a>
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
	 	        'processDefinitionName': '${param.processDefinitionName}',
	 	        'taskName': '${param.taskName}',
	 	        'startUserName': '${param.startUserName}',
		 	    'assigneeUserName': '${param.assigneeUserName}',
		 	    'ownerUserName': '${param.ownerUserName}',
			 	'assigned': '${param.assigned}',
			 	'finished': '${param.finished}'
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
