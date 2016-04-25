<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>调度管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>调度管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="location.href='quartz-job-input.do'">
										新增<i class="fa fa-arrows"></i></button>
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
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
								<form name="searchForm" method="post" action="quartz-job-list.do" class="form-inline">
								    <label for="groupName">组名称</label>
								    <input type="text" id="groupName" name="groupName" value="${param.groupName}" class="form-control">
								    <label for="taskName">任务名称</label>
								    <input type="text" id="taskName" name="taskName" value="${param.taskName}" class="form-control">
								    <label for="concurrent">是否并发</label>
								    <select id="concurrent" name="concurrent" class="form-control required">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.concurrent == 'T' }">selected</c:if>>是</option>
										<option value="F" <c:if test="${param.concurrent == 'F' }">selected</c:if>>否</option>
									</select>
									
									<label for="jobStatus">调度状态</label>
								    <select id="jobStatus" name="status" class="form-control required">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.jobStatus == 'T' }">selected</c:if>>运行</option>
										<option value="F" <c:if test="${param.jobStatus == 'F' }">selected</c:if>>暂停</option>
									</select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control required">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.status == 'T' }">selected</c:if>>可用</option>
										<option value="F" <c:if test="${param.status == 'F' }">selected</c:if>>禁用</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="quartz-job-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting">分组名称</th>
								                    <th class="sorting" name="QUARTZ_TASK_ID">任务名称</th>
								                    <th class="sorting" name="QUARTZ_CRON_ID">规则名称</th>
								                    <th class="sorting" name="QUARTZ_CRON_ID">规则表达式</th>
								                    <th class="sorting" name="JOB_SATUS">调度状态</th>
								                    <th class="sorting" name="CONCURRENT">是否并发</th>
								                    <th class="sorting" name="START_TIME">开始时间</th>
								                    <th class="sorting" name="END_TIME">结束时间</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">更新时间</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.quartzTask.quartzGroup.groupName}</td>
								                    <td>${item.quartzTask.taskName}</td>
								                    <td>${item.quartzCron.cronName}</td>
								                    <td>${item.quartzCron.cronExpression}</td>
								                    <td>
								                    	${item.jobStatus == 'T' ? '运行' : item.jobStatus == 'F' ? '停止' : item.jobStatus == 'P' ? '暂停' : '未激活'}
								                    	<c:choose>
								                    		<c:when test="${item.jobStatus == null}">
								                    			<a href="quartz-job-fire.do?id=${item.id}" class="btn btn-sm red">激活</a>
								                    		</c:when>
								                    		<c:when test="${item.jobStatus == 'T'}">
								                    			<a href="quartz-job-stop.do?id=${item.id}" class="btn btn-sm red">停止</a>
								                    			<a href="quartz-job-pause.do?id=${item.id}" class="btn btn-sm red">暂停</a>
								                    		</c:when>
								                    		<c:when test="${item.jobStatus == 'F'}">
								                    			<a href="quartz-job-start.do?id=${item.id}" class="btn btn-sm red">启动</a>
								                    		</c:when>
								                    		<c:when test="${item.jobStatus == 'P'}">
								                    			<a href="quartz-job-resume.do?id=${item.id}" class="btn btn-sm red">恢复</a>
								                    		</c:when>
								                    	</c:choose>
								                    </td>
								                    <td>${item.concurrent == 'T' ? '是' : '否'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.status == 'T' ? '可用' : '禁用'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="quartz-job-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	        'currency': '${param.currency}',
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
    </script>
  </body>

</html>
