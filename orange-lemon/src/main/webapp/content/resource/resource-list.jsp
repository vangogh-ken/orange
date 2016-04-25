<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>资源管理</title>
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
							<div class="caption"><i class="fa fa-user"></i>资源管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='resource-input.do'">
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
								<form id="searchForm" name="searchForm" method="post" action="resource-list.do" class="form-inline">
									   <label for="resourceName">资源名称</label>
									    <input type="text" id="resourceName" name="resourceName" value="${param.resourceName}" class="form-control">
									   
									   <label for="resourceType">资源类型</label>
									    <select id="resourceType" name="resourceType" class="form-control" onchange="updateResourceType();">
									    	<option value=""></option>
									    	<option value="目录" <c:if test="${param.resourceType == '目录'}">selected="selected"</c:if> >目录</option>
									    	<option value="菜单" <c:if test="${param.resourceType == '菜单'}">selected="selected"</c:if> >菜单</option>
									    	<option value="按钮" <c:if test="${param.resourceType == '按钮'}">selected="selected"</c:if> >按钮</option>
									    </select>
									    
									    <label for="parentId">上级资源名称</label>
									    <select id="parentId" name="parentId" class="form-control">
									    	<option value=""></option>
									    	<c:forEach items="${resourceAll}" var="res">
									    		<c:if test="${res.resourceType=='目录' or res.resourceType=='菜单'}">
									    		<option value="${res.id}" resourceType="${res.resourceType}" <c:if test="${param.parentId == res.id}">selected</c:if>> ${res.resourceType}:${res.resourceName}</option>
									    		</c:if>
									    	</c:forEach>
									    </select>
									    
									    <label for="enable">是否禁用</label>
									    <select id="enable" name="enable" class="form-control">
									    	<option value=""></option>
									    	<option value="T" <c:if test="${param.enable == 'T'}">selected="selected"</c:if> >启用</option>
									    	<option value="F" <c:if test="${param.enable == 'F'}">selected="selected"</c:if> >禁用</option>
									    </select>
										<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="resource-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
										 <thead>
									        <tr>
									          <th width="20px" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th class="sorting" name="RESOURCE_NAME">资源名</th>
									          <th class="sorting" name="RESOURCE_TYPE">资源类型</th>
									          <th class="sorting" name="DISP_INX">优先级</th>
									          <th class="sorting" name="PARENT_ID">上级资源</th>
									          <th class="sorting" name="ENABLE">是否可用</th>
									          <th width="50">操作</th>
									          <th class="sorting" name="RESOURCE_KEY">资源KEY</th>
									          <th class="sorting" name="RESOURCE_URL">资源地址</th>
									          <th class="sorting" name="CREATE_TIME">创建时间</th>
									        </tr>
									      </thead>
									      <tbody>
									        <c:forEach items="${pageView.results}" var="item">
									        <tr>
									          <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
									          <td>${item.resourceName}</td>
									          <td>${item.resourceType}</td>
									          <td>${item.displayIndex}</td>
									          <td>${item.parentResource == null ? '顶级菜单' : item.parentResource.resourceName}</td>
									          <td>${item.enable == 'T' ?  '可用' : '禁用'} |
												<c:if test="${item.enable != 'T'}">
													<a class="btn btn-sm green" href="${ctx}/resource/resource-to-enable.do?id=${item.id}">激活</a>
												</c:if>
												<c:if test="${item.enable != 'F'}">
													<a class="btn btn-sm green" href="${ctx}/resource/resource-to-disable.do?id=${item.id}">禁用</a>
												</c:if>
											  </td>
											  <td>
									            <a href="resource-input.do?id=${item.id}" class="btn btn-sm green">编辑</a>&nbsp;
									          </td>
									          <td>${item.resourceKey}</td>
									          <td>${item.resourceUrl}</td>
									          <td>
									          	<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/>
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
       	'resourceName': '${param.resourceName}',
        'resourceType': '${param.resourceType}',
        'parentId': '${param.parentId}',
        'enable': '${param.enable}'
    },
	selectedItemClass: 'selectedItem',
	gridFormId: 'dynamicGridForm',
	exportUrl: 'resource-export.do'
};

var table;

$(function() {
    table = new Table(config);
    table.configPagination('.m-pagination');
    table.configPageInfo('.m-page-info');
    table.configPageSize('.m-page-size');
});

$(function(){
	updateResourceType();
});

function updateResourceType(){
	var type = $('#resourceType').val();
	if(type != ''){
		if(type == '目录'){
			$('#parentId option').each(function(i, item){
				$('#parentId').val('');
				$(item).hide();
			});
			return false;
		}else if(type == '菜单'){
			type = '目录';
		}else if(type == '按钮'){
			type = '菜单';
		}
		
		$('#parentId option').each(function(i, item){
			if($(item).attr('resourceType') == type){
				$(item).css('display', 'block');
			}else{
				$(item).hide();
			}
		});
		$('#parentId').val('');
	}
}
    </script>
  </body>

</html>
