<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程定义管理</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程定义管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm green" onclick="$('#deployArticle').slideToggle(200);">
										部署流程</button>
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
								<form name="searchForm" method="post" action="bpm-process-definition-list.do" class="form-inline">
								    <label for="processDefinitionName">名称</label>
								    <input type="text" id="processDefinitionName" name="processDefinitionName" value="${param.processDefinitionName}" class="form-control">
								    
								    <label for="processDefinitionKey">关键词</label>
								    <input type="text" id="processDefinitionKey" name="processDefinitionKey" value="${param.processDefinitionKey}" class="form-control">
								    
								    <label for="processDefinitionVersion">版本</label>
								    <input type="text" id="processDefinitionVersion" name="processDefinitionVersion" value="${param.processDefinitionVersion}" class="form-control number">
								    
								    <label for="suspended">是否挂起</label>
									<select id="suspended" name="suspended" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.suspended == 'T'}">selected</c:if>>是</option>
										<option value="F" <c:if test="${param.suspended == 'F'}">selected</c:if>>否</option>
									</select>
									
									<label for="latest">是否最新</label>
									<select id="latest" name="latest" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.latest == 'T'}">selected</c:if>>是</option>
										<option value="F" <c:if test="${param.latest == 'F'}">selected</c:if>>否</option>
									</select>
								    
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							
							<article class="m-widget" id="deployArticle">
								<br>
								<form name="deployForm" id="deployForm" method="post" action="bpm-process-definition-deploy-byfile.do" class="form-inline" enctype="multipart/form-data">
								    <label for="muiltFile">添加附件</label>
								    <input type="file" id=muiltFile name="muiltFile" value="" class="form-control input-medium required">
									<button class="btn btn-sm green">部署</button>
								</form>
							</article>
							
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="bpm-process-definition-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
									        <tr>
									          <th width="10" style="text-indent:0px;text-align:center;"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th>名称</th>
									          <th>流程KEY</th>
									          <th>版本信息</th>
									          <th>XML文件</th>
									          <th>PNG图片</th>
									          <th>挂起或激活</th>
									          <th>操作</th>
									        </tr>
									      </thead>
									      <tbody>
									        <c:forEach items="${pageView.results}" var="item">
									        <tr>
									          <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.deploymentId};${item.id}"></td>
												<td>${item.name}</td>
												<td>${item.key}</td>
												<td>${item.version}</td>
									           	<td>
									           		<a class="btn btn-sm red" target="_blank" href='${ctx}/bpm/bpm-process-definition-resource.do?id=${item.id}&resourceType=XML'>BPMN20.XML文件</a>
									           	</td>
												<td>
													<a class="btn btn-sm red" target="_blank" href='${ctx}/bpm/bpm-process-definition-resource.do?id=${item.id}&resourceType=PNG'>PNG流程图</a>
												</td>
												<td>${item.suspended == false ? '激活' : '挂起'} |
												<c:if test="${item.suspended}">
													<a class="btn btn-sm red" href="${ctx}/bpm/bpm-process-definition-to-active.do?id=${item.id}">激活</a>
												</c:if>
												<c:if test="${!item.suspended}">
													<a class="btn btn-sm red" href="${ctx}/bpm/bpm-process-definition-to-suspend.do?id=${item.id}">挂起</a>
												</c:if>
												</td>
									           <td>
									               <a class="btn btn-sm red" href='${ctx}/bpm/bpm-process-definition-convert.do?id=${item.id}'>转换为Model</a>
									               <a class="btn btn-sm red" href='${ctx}/bpm/console-update-process-byxml.do?processDefinitionId=${item.id}'>更新</a>
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
	 	        'processDefinitionKey': '${param.processDefinitionKey}',
	 	        'processDefinitionVersion': '${param.processDefinitionVersion}',
	 	        'suspended': '${param.suspended}',
	 	        'latest': '${param.latest}'
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
	
	$(function(){
		$("#deployArticle").hide();
	});
	
	$(function() {
        $('#deployForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    </script>
  
  </body>

</html>
