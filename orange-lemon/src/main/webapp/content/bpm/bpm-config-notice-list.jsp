<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>通知配置</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>通知配置</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='bpm-config-notice-input.do'">
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
								<form name="searchForm" method="post" action="bpm-config-notice-list.do" class="form-inline">
										<label for="pId">选择流程</label>
									    <select id="pId" name="pId" class="form-control" onchange="updatePd();">
									    	<option value=""></option>
									    	<c:forEach items="${pds}" var="pd">
									    		<option value="${pd.id}" ${param.pId eq pd.id ? 'selected' : ''} >${pd.name}&nbsp;V${pd.version}</option>
									    	</c:forEach>
									    </select>
									    
									    <label for="nodeId">选择节点</label>
									    <select id="nodeId" name="nodeId" class="form-control">
									    	<option value=""></option>
									    	<c:forEach items="${nodes}" var="node">
									    		<option value="${node.id}" pId="${node.pdId}" ${param.nodeId eq node.id ? 'selected' : ''}>${node.tdName}</option>
									    	</c:forEach>
									    </select>
									    
									    <label for="templateId">选择模板</label>
									    <select id="templateId" name="templateId" class="form-control">
									    	<option value=""></option>
									    	<c:forEach items="${templates}" var="template">
									    		<option value="${template.id}" ${param.templateId eq template.id ? 'selected' : ''}>${template.templateName}</option>
									    	</c:forEach>
									    </select>
		    
										<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
									 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="bpm-config-notice-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="TYPE">类型</th>
										        <th class="sorting" name="RECEIVER">接收人</th>
										        <th class="sorting" name="DUE_DATE">超时时间</th>
										        <th class="sorting" name="TEMPLATE_ID">邮件模板</th>
										        <th class="sorting" name="NODE_ID">流程节点</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.type =='TYPE_ARRIVAL'?'任务签收时':item.type =='TYPE_COMPLETE'?'任务签收时':'任务超时时'}</td>
										        <td>${item.receiver}</td>
										        <td>${item.dueDate}</td>
										        <td>${item.bpmMailTemplate.templateName}</td>
										        <td>${item.bpmConfigNode.pdName}: ${item.bpmConfigNode.tdName}</td>
										        <td><a class="btn btn-sm red" href="bpm-config-notice-input.do?id=${item.id}">编辑</a></td>
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
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	function updatePd(){
		var pId = $('#pId').val();
		  
    	if(pId != ''){
    		$('#nodeId option').each(function(i, item){
    			if($(item).attr('pId') != pId){
    				$('#nodeId').val('');
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}
	}
    </script>
 
  </body>

</html>
