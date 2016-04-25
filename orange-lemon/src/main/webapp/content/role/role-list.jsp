<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title><spring:message code="auth.role.list.title" text="角色管理"/></title>
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
							<div class="caption"><i class="fa fa-user"></i>角色管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='role-input.do'">
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
								<form name="searchForm" method="post" action="role-list.do" class="form-inline">
									    <label for=roleName>名称</label>
		    							<input type="text" id="roleName" name="roleName" value="${param.roleName}" class="form-control">
										<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								</form>
							</article>
							
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="role-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
										 <thead>
									        <tr>
									          <th width="10px" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									          <th class="sorting" name="ROLE_NAME">角色名</th>
									          <th class="sorting" name="ROLE_KEY">角色KEY</th>
									          <th class="sorting" name="STATUS">是否禁用</th>
									          <th class="sorting" name="DESCN">描述</th>
									          <th>操作</th>
									          <th class="sorting" name="CREATE_TIME">创建时间</th>
									        </tr>
									      </thead>
									      <tbody>
									        <c:forEach items="${pageView.results}" var="item">
									        <tr>
									          <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
									          <td>${item.roleName}</td>
									          <td>${item.roleKey}</td>
									          <td>${item.status == 'F'? '禁用' : '正常'}</td>
									          <td>${item.descn}</td>
									          <td>
											    <a href="role-input.do?id=${item.id}" class="btn btn-sm green">编辑</a>
											    <a href="javascript:void(0)" onclick="roleResourceAllocation('${item.id}');" class="btn btn-sm red">资源权限</a>
											    <a href="javascript:void(0)" onclick="roleBpmAllocation('${item.id}');" class="btn btn-sm green">流程权限</a>
											    <a href="javascript:void(0)" onclick="roleReportAllocation('${item.id}');" class="btn btn-sm green">报表权限</a>
											    <a href="javascript:void(0)" onclick="roleQuartzAllocation('${item.id}');" class="btn btn-sm green">任务权限</a>
									          </td>
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
	
	<!-- 资源权限 /.modal -->
	<div class="modal fade bs-modal-lg" id="roleResourceAllocationModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权资源</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="roleResourceAllocationForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#roleResourceAllocationForm').submit()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
	<!-- 流程权限 /.modal -->
	<div class="modal fade bs-modal-lg" id="roleBpmAllocationModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权流程</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="roleBpmAllocationForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#roleBpmAllocationForm').submit()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
	<!-- 报表权限 /.modal -->
	<div class="modal fade bs-modal-lg" id="roleReportAllocationModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权报表</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="roleReportAllocationForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#roleReportAllocationForm').submit()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
	<!-- 任务权限 /.modal -->
	<div class="modal fade bs-modal-lg" id="roleQuartzAllocationModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权任务</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="roleQuartzAllocationForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#roleQuartzAllocationForm').submit()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
	
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
        'roleName': '${param.roleName}'
    },
	selectedItemClass: 'selectedItem',
	gridFormId: 'dynamicGridForm',
	exportUrl: 'role-export.do'
};

var table;

$(function() {
    table = new Table(config);
    table.configPagination('.m-pagination');
    table.configPageInfo('.m-page-info');
    table.configPageSize('.m-page-size');
});

//资源权限分配
function roleResourceAllocation(roleId) {
	var htmlContent = "<input type='hidden' name='roleId' value='"+roleId+"'>";
	htmlContent += "<table class='m-table table-bordered table-hover' >";
	htmlContent += "<thead><tr><th width='10' class='m-table-check'><input type='checkbox' class='selectedItemIdAll'/><th>名称</th><th>资源KEY</th></tr></thead><tbody>";
	$.ajax({
			url:'role-to-allocation-resource.do?roleId=' + roleId,
			type:'post',
			dataType:'json',
			async:true,
			success:function(data){
				var resourcesRole = data["resourcesRole"];
				var resourcesAll = data["resourcesAll"];
				$.each(resourcesAll, function(i, item){
					if(item.resourceType == '目录'){
						htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='resourceId' value='"+item.id + "' ";
						$.each(resourcesRole, function(index, ele){
							if(ele.id == item.id){
								htmlContent += "checked='checked'";
							}
						});
						htmlContent +=  "/></td>"; 
						htmlContent += "<td><li class='fa fa-folder-open'></li>" + item.resourceType + "：" +item.resourceName + "</td>";
						htmlContent += "<td>" + item.resourceKey + "</td>";
						htmlContent += "</tr>";
						$.each(resourcesAll, function(j, menu){
							if(menu.parentResource != null && menu.parentResource.id == item.id && menu.resourceType == '菜单'){
								htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='resourceId' value='"+ menu.id + "' ";
								$.each(resourcesRole, function(num, el){
									if(el.id == menu.id){
										htmlContent += "checked='checked'";
									}
								});
								htmlContent +=  "/></td>"; 
								htmlContent += "<td>&nbsp;&nbsp;<li class='fa fa-th-list'></li>" + menu.resourceType + "：" + menu.resourceName + "</td>";
								htmlContent += "<td>" + menu.resourceKey + "</td>";
								htmlContent += "</tr>";
								$.each(resourcesAll, function(k, button){
									if(button.parentResource != null && button.parentResource.id == menu.id && button.resourceType == '按钮'){
										htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='resourceId' value='"+ button.id + "' ";
										
										$.each(resourcesRole, function(count, e){
											if(e.id == button.id){
												htmlContent += "checked='checked'";
											}
										});
										
										htmlContent +=  "/></td>"; 
										htmlContent += "<td>&nbsp;&nbsp;&nbsp;&nbsp;<li class='fa fa-star'></li>" + button.resourceType + "：" + button.resourceName +"</td>";
										htmlContent += "<td>" + button.resourceKey + "</td>";
										htmlContent += "</tr>";
									}
								});
							}
						});
						
						htmlContent += "<tr><td colspan='4'>&nbsp;</td></tr>";
					}
					
				});
				htmlContent += "</tbody></table>";
				$('#roleResourceAllocationForm').html(htmlContent);
			},
			error:function(){
				
			}
	});
	var margin = (window.screen.availWidth - 800)/2;
	$('#roleResourceAllocationModal').css("margin-left", margin);
	$('#roleResourceAllocationModal').css("width","800px");
	$('#roleResourceAllocationModal').modal();
}

//保存数据
$(function() {
	$("#roleResourceAllocationForm").validate({
        submitHandler: function(form) {
    		$.post('role-done-allocation-resource.do', $('#roleResourceAllocationForm').serialize(), function(data){
        		if(data == 'success' ){
        			alert('授权成功！');
        			$('#roleResourceAllocationModal').modal('hide');
        		}else{
        			alert('授权失败！');
        		}
        	});
        },
        errorClass: 'validate-error'
	});
});

///////////////////////////流程权限分配///////////////////////////////
function roleBpmAllocation(roleId) {
	var htmlContent = "<input type='hidden' name='roleId' value='"+roleId+"'>";
	htmlContent += "<table id='roleBpmAllacationTable' class='m-table table-bordered table-hover' >";
	htmlContent += "<thead><tr><th width='10' class='m-table-check'><input type='checkbox' class='selectedItemIdAll'/><th>流程Key</th><th>流程名</th><th>流程分类</th></tr></thead><tbody>";
	$.ajax({
			url:'role-to-allocation-bpm.do?roleId=' + roleId,
			type:'post',
			dataType:'json',
			async:true,
			success:function(data){
				var bpmRole = data["bpmRole"];
				var bpmAll = data["bpmAll"];
				$.each(bpmAll, function(i, item){
						htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='bpmId' value='"+item.id + "' ";
						
						$.each(bpmRole, function(index, ele){
							if(ele.id == item.id){
								htmlContent += "checked='checked'";
							}
						});
						
						htmlContent +=  "/></td>"; 
						htmlContent += "<td><li class='fa fa-sitemap'></li>&nbsp;&nbsp;" + item.bpmKey + "</td>";
						htmlContent += "<td>" + (item.basisSubstanceType == null ? '' : item.basisSubstanceType.typeName) + "</td>";
						htmlContent += "<td>" + item.bpmConfigCategory.categoryName + "</td>";
						htmlContent += "</tr>";
					
				});
				htmlContent += "</tbody></table>";
				$('#roleBpmAllocationForm').html(htmlContent);
			},
			error:function(){
				
			}
	});
	
	var margin = (window.screen.availWidth - 800)/2;
	$('#roleBpmAllocationModal').css("margin-left", margin);
	$('#roleBpmAllocationModal').css("width","800px");
	$('#roleBpmAllocationModal').modal();
}

//保存数据
$(function() {
	$("#roleBpmAllocationForm").validate({
        submitHandler: function(form) {
    		$.post('role-done-allocation-bpm.do', $('#roleBpmAllocationForm').serialize(), function(data){
        		if(data == 'success' ){
        			alert('授权成功！');
        			$('#roleBpmAllocationModal').modal('hide');
        		}else{
        			alert('授权失败！');
        		}
        	});
        },
        errorClass: 'validate-error'
	});
});

///////////////////////////报表权限分配///////////////////////////////
function roleReportAllocation(roleId) {
	var htmlContent = "<input type='hidden' name='roleId' value='"+roleId+"'>";
	htmlContent += "<table class='m-table table-bordered table-hover' >";
	htmlContent += "<thead><tr><th width='10' class='m-table-check'><input type='checkbox' class='selectedItemIdAll' /><th>类别</th><th>报表名称</th><th>状态</th></tr></thead><tbody>";
	$.ajax({
			url:'role-to-allocation-report.do?roleId=' + roleId,
			type:'post',
			dataType:'json',
			async:true,
			success:function(data){
				var reportTemplateRole = data["reportTemplateRole"];
				var reportTemplateAll = data["reportTemplateAll"];
				$.each(reportTemplateAll, function(i, item){
						htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='reportTemplateId' value='"+item.id + "' ";
						$.each(reportTemplateRole, function(index, ele){
							if(ele.id == item.id){
								htmlContent += "checked='checked'";
							}
						});
						htmlContent +=  "/></td>"; 
						htmlContent += "<td>" + item.reportCategory.categoryName + "</td>";
						htmlContent += "<td>" + item.templateName + "</td>";
						htmlContent += "<td>" + item.status + "</td>";
						htmlContent += "</tr>";
					
				});
				htmlContent += "</tbody></table>";
				$('#roleReportAllocationForm').html(htmlContent);
			},
			error:function(){
			}
	});
	
	var margin = (window.screen.availWidth - 800)/2;
	$('#roleReportAllocationModal').css("margin-left", margin);
	$('#roleReportAllocationModal').css("width","800px");
	$('#roleReportAllocationModal').modal();
}

//保存数据
$(function() {
	$("#roleReportAllocationForm").validate({
        submitHandler: function(form) {
    		$.post('role-done-allocation-report.do', $('#roleReportAllocationForm').serialize(), function(data){
        		if(data == 'success' ){
        			alert('授权成功！');
        			$('#roleReportAllocationModal').modal('hide');
        		}else{
        			alert('授权失败！');
        		}
        	});
        },
        errorClass: 'validate-error'
	});
});


///////////////////////////任务权限分配///////////////////////////////
function roleQuartzAllocation(roleId) {
	var htmlContent = "<input type='hidden' name='roleId' value='"+roleId+"'>";
	htmlContent += "<table class='m-table table-bordered table-hover' >";
	htmlContent += "<thead><tr><th width='10' class='m-table-check'><input type='checkbox' class='selectedItemIdAll' /><th>组名</th><th>任务名称</th><th>任务key</th><th>容器ID</th><th>类别</th><th>方法</th><th>状态</th></tr></thead><tbody>";
	$.ajax({
			url:'role-to-allocation-quartz.do?roleId=' + roleId,
			type:'post',
			dataType:'json',
			async:true,
			success:function(data){
				var quartzTaskRole = data["quartzTaskRole"];
				var quartzTaskAll = data["quartzTaskAll"];
				$.each(quartzTaskAll, function(i, item){
						htmlContent += "<tr><td><input class='selectedItemId a-check' type='checkbox' name='quartzTaskId' value='"+item.id + "' ";
						$.each(quartzTaskRole, function(index, ele){
							if(ele.id == item.id){
								htmlContent += "checked='checked'";
							}
						});
						htmlContent +=  "/></td>"; 
						htmlContent += "<td>" + item.quartzGroup.groupName + "</td>";
						htmlContent += "<td>" + item.taskName + "</td>";
						htmlContent += "<td>" + item.taskKey + "</td>";
						htmlContent += "<td>" + item.springId + "</td>";
						htmlContent += "<td>" + item.beanClass + "</td>";
						htmlContent += "<td>" + item.methodName + "</td>";
						htmlContent += "<td>" + item.status + "</td>";
						htmlContent += "</tr>";
					
				});
				htmlContent += "</tbody></table>";
				$('#roleQuartzAllocationForm').html(htmlContent);
			},
			error:function(){
			}
	});
	
	var margin = (window.screen.availWidth - 800)/2;
	$('#roleQuartzAllocationModal').css("margin-left", margin);
	$('#roleQuartzAllocationModal').css("width","800px");
	$('#roleQuartzAllocationModal').modal();
}

//保存数据
$(function() {
	$("#roleQuartzAllocationForm").validate({
        submitHandler: function(form) {
    		$.post('role-done-allocation-quartz.do', $('#roleQuartzAllocationForm').serialize(), function(data){
        		if(data == 'success' ){
        			alert('授权成功！');
        			$('#roleQuartzAllocationModal').modal('hide');
        		}else{
        			alert('授权失败！');
        		}
        	});
        },
        errorClass: 'validate-error'
	});
});
    </script>
  </body>

</html>
