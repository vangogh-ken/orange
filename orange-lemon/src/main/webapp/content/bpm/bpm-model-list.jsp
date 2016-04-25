<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程模型管理</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程模型管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='bpm-model-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
										<button class="btn btn-sm red" onclick="table.exportExcel();">
											导出<i class="fa fa-arrows-alt"></i></button>
											
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
								<form name="searchForm" method="post" action="bpm-model-list.do" class="form-inline">
								    <label for="modelXmlName">xml名</label>
								    <input type="text" id="modelXmlName" name="modelXmlName" value="${param.modelXmlName}" class="form-control">
								    <label for="modelKey">关键词</label>
								    <input type="text" id="modelKey" name="modelKey" value="${param.modelKey}" class="form-control">
								    <label for="modelVersion">版本</label>
								    <input type="text" id="modelVersion" name=modelVersion value="${param.modelVersion}" class="form-control number">
								    
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="bpm-model-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
									        <tr>
									          <th width="10" style="text-indent:0px;text-align:center;"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th class="sorting" name="name">模型名称</th>
									          <th class="sorting" name="key">模型KEY</th>
									          <th class="sorting" name="createTime">创建时间</th>
									          <th class="sorting" name="lastUpdateTime">最近更新时间</th>
									          <th>版本</th>
									          <th>部署ID</th>
									          <th>操作</th>
									        </tr>
									      </thead>
									      <tbody>
									        <c:forEach items="${pageView.results}" var="item">
									        <tr>
									          <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
									          <td>${item.name}</td>
									          <td>${item.key}</td>
									          <td>
									           	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									          </td>
									          <td>
									           	<fmt:formatDate value="${item.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									          </td>
									          <td>${item.version}</td>
									          <td>${item.deploymentId}</td>
									          <td>
									          	<!--  
									            <a href="${ctx}/widgets/modeler/editor.html?id=${item.id}" class="btn btn-sm red" target="_blank">编辑</a>
									            -->
									            <a href="${ctx}/widgets/modeler/modeler.html?modelId=${item.id}" class="btn btn-sm red" target="_blank">编辑</a>
									            <a href="bpm-model-deploy.do?id=${item.id}" class="btn btn-sm red">部署</a>
									            <a href="bpm-model-export.do?id=${item.id}" class="btn btn-sm red">导出</a>
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
	 	        'modelXmlName': '${param.modelXmlName}',
	 	        'modelKey': '${param.modelKey}',
	 	        'modelVersion': '${param.modelVersion}'
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
