<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>职位管理</title>
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
							<div class="caption"><i class="fa fa-user"></i>职位管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-POSITION-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='position-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-POSITION-LIST-REMOVE">
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
								<form name="searchForm" method="post" action="position-list.do" class="form-inline">
								    <label for="positionName">名称</label>
								    <input type="text" id="positionName" name="positionName" value="${param.positionName}" class="form-control input-medium">
									
									<label for="orgEntityId">组织机构类型</label>
									<select id="orgEntityId" name="orgEntityId" class="form-control">
										<option value=""></option>
										<c:forEach items="${orgEntities}" var="orgEntity">
											<option value="${orgEntity.id}" <c:if test="${param.orgEntityId == orgEntity.id}">selected='selected'</c:if>>${orgEntity.orgEntityName}</option>
										</c:forEach>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="position-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="POSITION_NAME">名称</th>
								                    <th class="sorting" name="GRADE">职级</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">组织机构</th>
								                    <th class="sorting" name="DESCN">描述</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.positionName}</td>
								                    <td>${item.grade==1?'普通':item.grade==2?'主管':item.grade==3?'副经理':item.grade==4?'经理':item.grade==5?'副总经理':'总经理'}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
								                    <td>${item.descn}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="position-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
								                    	<a href="javascript:void(0)" onclick="positionAllocation('${item.id}');" class="btn btn-sm green">分配角色</a>
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
	
	<div class="modal fade" id="positionAllocationModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">授权角色</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="positionAllocationForm" action="position-done-position-role-allocation.do" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#positionAllocationForm').submit()">确认</button>
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
	 	        'positionName': '${param.positionName}',
	 	        'orgEntityId': '${param.orgEntityId}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'position-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
	
	//为职位分配角色
	function positionAllocation(id) {
		var html = '<input type="hidden" name="positionId" value="' + id + '"/>';
		html += '<table class="m-table table-bordered table-hover">';
		html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsRole(this.checked)"/><th>角色名</th><th>角色KEY</th><th>是否禁用</th><th>描述</th></tr></thead><tbody>';
		$.ajax({
				url:'position-to-position-role-allocation.do?positionId=' + id,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					var rolePosition = data["rolePosition"];
					var roleAll = data["roleAll"];
					$.each(roleAll, function(i, item){
						html += "<tr><td><input class='selectedItemRole a-check' type='checkbox' name='roleIds' value='"+item.id+"' ";
						
						$.each(rolePosition, function(index, ele){
							if(ele.id == item.id){
								html += "checked='checked'";
							}
						});
						
						html += "/></td>";
						html += "<td>" + item.roleName + "</td>";
						html += "<td>" + item.roleKey + "</td>";
						var status = item.status == "T" ? "启用" : "禁用";
						html += "<td>" + status + "</td>";
						html += "<td>" + item.descn + "</td></tr>";
					});
					html += "</tbody></table>";
					$('#positionAllocationForm').html(html);
				},
				error:function(){
				}
		});
		
		var margin = (window.screen.availWidth - 800)/2;
		$('#positionAllocationModal').css("margin-left", margin);
		$('#positionAllocationModal').css("width","800px");
		$('#positionAllocationModal').modal();
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
    </script>
  </body>

</html>
