<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>用户管理</title>
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
							<div class="caption"><i class="fa fa-user"></i>用户管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='user-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
									<button class="btn btn-sm red" onclick="table.exportExcel();">
											导出<i class="fa fa-share-square-o"></i></button>
													
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
								<form name="searchForm" method="post" action="user-list.do" class="form-inline">
									    <label for="userName">账号</label>
									    <input type="text" id="userName" name="userName" value="${param.userName}"
									    	class="form-control">
									    
									    <label for="displayName">姓名</label>
									    <input type="text" id="displayName" name="displayName" value="${param.displayName}"
									    	class="form-control">
									    	
									    <label for="orgEntityName">组织机构</label>
									    <input type="text" id="orgEntityName" name="orgEntityName" value="${param.orgEntityName}"
									    	class="form-control">
									    
									    <label for="status">状态</label>
									    <select id="status" name="status" class="form-control">
										  <option value=""></option>
										  <option value="T" ${param.status == 'T' ? 'selected="selected"' : ''}>启用</option>
										  <option value="F" ${param.status == 'F' ? 'selected="selected"' : ''}>禁用</option>
									    </select>
										<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
									 </form>
							</article>
							
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="user-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th class="m-table-check">
										        	<input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked);">
										        </th>
										        <th class="sorting" name="USER_NAME">用户名</th>
										        <th class="sorting" name="DISPLAY_NAME">姓名</th>
										        <th class="sorting" name="POSITION_ID">职位</th>
										        <th>职级</th>
										        <th>组织机构</th>
										        <th class="sorting" name="STATUS">状态</th>
										        <th class="sorting" name="REG_TIME">录入时间</th>
										        <th class="sorting" name="HIRE_TIME">入职时间</th>
										        <th class="sorting" name="FIRE_TIME">离职时间</th>
										        <th>操作</th>
										      </tr>
										    </thead>
											<tbody>
											<c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}" style="border-color: red;"></td>
										        <td>${item.userName}</td>
										        <td>${item.displayName}</td>
										        <td>${item.position.positionName}</td>
										        <td>${item.position.grade}</td>
										        <td>${item.orgEntity == null ? '' : item.orgEntity.orgEntityName}</td>
										        <td>
										        	<c:if test="${item.status == 'T'}" >启用</c:if>
										        	<c:if test="${item.status != 'T'}" >禁用</c:if>
										        </td>
										        <td>
										        	<fmt:formatDate value="${item.regTime}" pattern="yyyy-MM-dd"/>
										        </td>
										        <td>
										        	<fmt:formatDate value="${item.hireTime}" pattern="yyyy-MM-dd"/>
										        </td>
										        <td>
										        	<fmt:formatDate value="${item.fireTime}" pattern="yyyy-MM-dd"/>
										        </td>
										        <td>
										          <a href="user-input.do?id=${item.id}" class="btn btn-sm green">编辑</a>
										          <a href="user-reset-password.do?id=${item.id}" class="btn btn-sm red">重置密码</a>
										          <!--  
										          <a href="javascript:void(0)" onclick="userAllocation('${item.id}');" class="btn btn-sm green">分配角色</a>
										          -->
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
	
	<!-- /.modal -->
	<div class="modal fade" id="userAllocationModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权角色</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="userAllocationForm" action="user-role-allocation-save.do" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#userAllocationForm').submit()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
    <%@include file="/common/footer.jsp"%>

<script type="text/javascript">

 $(function() {
     $('#userAllocationForm').validate({
         submitHandler: function(form) {
        	$('#userAllocationModal').modal('hide');
 			bootbox.animate(false);
 			var box = bootbox.dialog('<div class="progress progress-striped active" ><div class="bar" style="width: 100%;"></div></div>');
             form.submit();
         },
         errorClass: 'validate-error'
     });
 });


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
 	        'userName': '${param.userName}',
 	        'displayName': '${param.displayName}',
 	        'orgEntityName': '${param.orgEntityName}',
 	        'status': '${param.status}'
 	    },
 		selectedItemClass: 'selectedItem',
 		gridFormId: 'dynamicGridForm',
 		exportUrl: 'user-export.do'
};

var table;
$(function() {
	table = new Table(config);
    table.configPagination('.m-pagination');
    table.configPageInfo('.m-page-info');
    table.configPageSize('.m-page-size');
});

function userAllocation(id) {
	var htmlContent = "<input type='hidden' name='userId' value='"+id+"'/><table id='userAllacationTable' class='m-table table-bordered table-hover'>";
	htmlContent += "<thead><tr><th width='10' class='m-table-check'><input type='checkbox' onchange='toggleSelectedItemsRole(this.checked)'/><th>角色名</th><th>是否禁用</th><th>描述</th></tr></thead><tbody>";
	$.ajax({
			url:'${ctx}/role/role-all.do?userId=' + id,
			type:'post',
			dataType:'json',
			async:true,
			success:function(data){
				var roleUser = data["roleUser"];
				var roleAll = data["roleAll"];
				$.each(roleAll, function(i, item){
					htmlContent += "<tr><td><input class='selectedItemRole a-check' type='checkbox' name='roleIds' value='"+item.id+"' ";
					
					$.each(roleUser, function(index, ele){
						if(ele.id == item.id){
							htmlContent += "checked='checked'";
						}
					});
					
					htmlContent += "/></td>";
					htmlContent += "<td>" + item.roleName + "</td>";
					var status = item.status == "T" ? "启用" : "禁用";
					htmlContent += "<td>" + status + "</td>";
					htmlContent += "<td>" + item.descn + "</td></tr>";
				});
				htmlContent += "</tbody></table>";
				$('#userAllocationForm').html(htmlContent);
			},
			error:function(){
			}
	});
	
	var margin = (window.screen.availWidth - 800)/2;
	$('#userAllocationModal').css("margin-left", margin);
	$('#userAllocationModal').css("width","800px");
	$('#userAllocationModal').modal();
}

function toggleSelectedItemsRole(isChecked) {
	$(".selectedItemRole").each(function(index, el) {
		$(el).prop("checked", isChecked);
		if ($(el).parent()[0].tagName != 'SPAN' && $(el).parent()[0].tagName != 'span') {
			return;
		}
		if (isChecked) {
			$(el).parent().addClass("checked");
		} else {
			$(el).parent().removeClass("checked");
		}
	});
}

/* function submitUserAllocationFormAsync(){
	$('#userAllocationModal').modal('hide');
	asyncSubmitForm('user-allocation-save.do', $('#userAllocationForm').serialize());
	window.location.href = window.location.href;
} */

//$('#dynamicGridForm').dragTable({limitWidth:20}); 
    </script>
  </body>

</html>
