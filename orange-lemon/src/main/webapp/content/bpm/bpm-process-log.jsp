<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程日志查看</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程日志查看</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic">
									<button type="button" class="btn btn-sm green" style="margin-left: 10px;" onclick="history.back();">返回</button>
									<div class="form-body">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
											<tr>
										  		<th>序号</th>
										  		<th>任务名</th>
										  		<th>任务委托人</th>
										  		<th>签收时间</th>
										  		<th>完成时间</th>
										  	</tr>
										  	</thead>
											  <c:forEach items="${tasks}" var="task" varStatus="status">
											  <tbody style="background-color:#FFFEEE;" class="${status.index % 2 != 0 ? 'odd' : 'even'}">
											  	<tr>
											  		<td>${status.index + 1}</td>
											  		<td>${task.name}</td>
											  		<c:if test="${task.assignee != null}">
											  		<c:set var="taskId" value="${task.id}"></c:set>
											  		<td><%=TaskQueryCache.getAssigneeDisplayName((String)pageContext.getAttribute("taskId"))%></td>
											  		</c:if>
											  		
											  		<c:if test="${task.assignee == null}">
											  		<td>待签收</td>
											  		</c:if>
											  		<td><fmt:formatDate value="${task.claimTime == null ? task.startTime : task.claimTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											  		<td>
											  		<c:choose>
											  			<c:when test="${task.endTime == null}">正在处理中</c:when>
											  			<c:otherwise><fmt:formatDate value='${task.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/></c:otherwise>
											  		</c:choose>
											  		</td>
											  	</tr>
											  </tbody>
											  </c:forEach>
										</table>
										</div>
									</form>
								</article>
					      	</div>
					   </div>
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
    </script>
  </body>

</html>
