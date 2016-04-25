<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>组织机构管理</title>
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
							<div class="caption"><i class="fa fa-user"></i>组织机构管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-ORG-ENTITY-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='org-entity-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-ORG-ENTITY-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm green" id="addGaffer">
											分配负责人</button>
											
										<button class="btn btn-sm green" id="addPosition">
											分配职位</button>
											
									<button class="btn btn-sm red" onclick="location.href='org-map-view.do'">
											地图<i class="fa fa-picture-o"></i></button>
											
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
								<form name="searchForm" method="post" action="org-entity-list.do" class="form-inline">
								    <label for="orgEntityName">名称</label>
								    <input type="text" id="orgEntityName" name="orgEntityName" value="${param.orgEntityName}" class="form-control input-medium">
								    
								    <label for="orgTypeId">组织机构类型</label>
									<select id="orgTypeId" name="orgTypeId" class="form-control">
										<option value=""></option>
										<c:forEach items="${orgTypes}" var="orgType">
											<option value="${orgType.id}" <c:if test="${param.orgTypeId == orgType.id}">selected='selected'</c:if>>${orgType.typeName}</option>
										</c:forEach>
									</select>
									
								    <label for="parentOrgId">上级组织</label>
									<select id="parentOrgId" name="parentOrgId" class="form-control">
										<option value=""></option>
										<c:forEach items="${orgEntities}" var="orgEntity">
											<c:if test="${orgEntity.orgType.typeName != '小组'}">
											<option value="${orgEntity.id}" <c:if test="${param.parentOrgId == orgEntity.id}">selected='selected'</c:if>>${orgEntity.orgEntityName}</option>
											</c:if>
										</c:forEach>
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
									<form id="dynamicGridForm" name="dynamicGridForm" action="org-entity-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="ORG_ENTITY_NAME">名称</th>
								                    <th class="sorting" name="TYPE_ID">类型</th>
								                    <th class="sorting" name="PARENT_ORG_ID">上级组织</th>
								                    <th class="sorting" name="DESCN">描述</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">修改时间</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.orgEntityName}</td>
								                    <td>${item.orgType.typeName}</td>
								                    <td>${item.parentOrg.orgEntityName}</td>
								                    <td>${item.descn}</td>
								                    <td id="${item.id}status">${item.status == 'T' ? '已启用' : '已禁用' }</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="org-entity-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<!--组织机构负责人-->
	<div class="modal fade" id="gafferModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">组织机构负责人</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="gafferToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="gafferBtnForm" name="gafferBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#gafferToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm green" onclick="deleteGaffer();">重置</button>
					</form>
				</article>
				<article class="m-widget" style="max-height:300px;overflow-y: scroll;">
				<form id="gafferHasAddForm" action="" method="post" class="m-form-blank" ></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!--职位-->
	<div class="modal fade" id="positionModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">组织机构职位</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="positionToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="positionBtnForm" name="positionBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#positionToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm green" onclick="revisePosition();">修改</button>
						<button type="button" class="btn btn-sm green" onclick="deletePosition();">删除</button>
					</form>
				</article>
				<article class="m-widget" style="max-height:300px;overflow-y: scroll;">
				<form id="positionHasAddForm" action="" method="post" class="m-form-blank" ></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
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
	 	        'parentOrgId': '${param.parentOrgId}',
	 	        'orgEntityName': '${param.orgEntityName}',
	 	       	'status': '${param.status}'
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
	
	
//////////////////////////////////////////////////分配负责人
	//添加
	$(document).delegate('#addGaffer', 'click',function(e){
		if(addGaffer()){
			var margin = (window.screen.availWidth - 800)/2;
			$('#gafferModal').css("margin-left", margin);
			$('#gafferModal').css("width","800px");
			$('#gafferModal').modal();
		}
	});
	
	//添加
	function addGaffer(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var orgEntityId = $('.selectedItem:checked').val();
			var status = $('#' + orgEntityId + 'status').text();
			if(status != '已启用'){
				alert('已禁用的组织机构不能做任何操作！');
				return false;
			}else{
				$.ajax({
					url:'org-entity-to-add-gaffer.do?orgEntityId=' + orgEntityId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						var hasAddData = data.hasAddData;
						var users = data.users;
						var html = '<input id="orgEntityId" type="hidden" value="' + orgEntityId + '">';
						html += '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th>选择用户</th></tr></thead><tbody>';
						html += '<td><select id="userId" name="userId" class="form-control required" >';
						$.each(users, function(i, item){
							if(item.position != null){
								var flag = true;
								$.each(hasAddData, function(j, ele){
									if(ele.id == item.id){
										flag = false;
										return false;
									}
								});
								if(flag){
									html += '<option value="'+ item.id +'">' + (item.position == null ? '无' : item.orgEntity.orgEntityName) + '_' + (item.position == null ? '无' : item.position.positionName) + '_' + item.displayName + '</option>';
								}
							}
						});
						html += '</select></td>';
						html += '</tr>'
						html += "</tbody></table>";
						$('#gafferToAddForm').html(html);
						initSelect2();
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>姓名</th><th>职位</th><th>所属部门</th><th>状态</th></tr></thead><tbody>';
						$.each(hasAddData, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
							html += '<td>' + item.displayName + '</td>';
							html += '<td>' + item.position.positionName + '</td>';
							html += '<td>' + item.orgEntity.orgEntityName + '</td>';
							html += '<td>' + item.status + '</td>';
						});
						html += "</tbody></table>";
						$('#gafferHasAddForm').html(html);
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	//保存数据
	$(function() {
		$("#gafferToAddForm").validate({
	        submitHandler: function(form) {
				var orgEntityId = $('#gafferToAddForm #orgEntityId').val();
				var userId = $('#gafferToAddForm #userId').val();
				var url = 'org-entity-done-add-gaffer.do?orgEntityId=' + orgEntityId + '&userId=' + userId;
				$.post(url, function(){
					addGaffer();
				});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除字段
	function deleteGaffer(){
		var orgEntityId = $('#gafferToAddForm #orgEntityId').val();
		var url = 'org-entity-done-remove-gaffer.do?orgEntityId=' + orgEntityId;
		$.post(url, function(data){
			if(data == 'success'){
				addGaffer();
			}else{
				alert('删除失败！');
			}
		});
	}
	
	
//////////////////////////////////////////////////职位
	//添加
	$(document).delegate('#addPosition', 'click',function(e){
		if(addPosition()){
			var margin = (window.screen.availWidth - 800)/2;
			$('#positionModal').css("margin-left", margin);
			$('#positionModal').css("width","800px");
			$('#positionModal').modal();
		}
	});
	
	//添加
	function addPosition(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var orgEntityId = $('.selectedItem:checked').val();
			var status = $('#' + orgEntityId + 'status').text();
			if(status != '已启用'){
				alert('已禁用的组织机构不能做任何操作！');
				return false;
			}else{
				$.ajax({
					url:'org-entity-to-add-position.do?orgEntityId=' + orgEntityId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						var hasAddData = data.hasAddData;
						var orgEntity = data.orgEntity;
						var html = '<input id="orgEntityId" type="hidden" value="' + orgEntityId + '">';
						html += '<input id="positionId" type="hidden" value="">';
						html += '<table class="m-table table-bordered table-hover">';
						html += '<thead><th>职位名称</th><th>机构内级别</th><th>职级</th><th>状态</th></thead><tbody>';
						html += '<td><input id="positionName" name="positionName" class="form-control required"></td>';
						html += '<td><select id="priority" name="priority" class="form-control required" >';
						html += '<option value="1">1</option><option value="2">2</option>'
						html += '<option value="3">3</option><option value="4">4</option></select></td>';
						html += '<td><select id="grade" name="grade" class="form-control required" >';
						html += '<option value="1">1</option><option value="2">2</option>';
						html += '<option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option></select></td>';
						html += '<td><select id="status" name="status" class="form-control required" >';
						html += '<option value="启用">启用</option><option value="禁用">禁用</option></select></td>';
						html += '</tr>'
						html += "</tbody></table>";
						$('#positionToAddForm').html(html);
						
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>职位名称</th><th>机构内级别</th><th>职级</th><th>所属部门</th><th>状态</th></tr></thead><tbody>';
						$.each(hasAddData, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
							html += '<td>' + item.positionName + '</td>';
							html += '<td>' + item.priority + '</td>';
							html += '<td>' + item.grade + '</td>';
							html += '<td>' + item.orgEntity.orgEntityName + '</td>';
							html += '<td>' + item.status + '</td>';
						});
						html += "</tbody></table>";
						$('#positionHasAddForm').html(html);
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	//保存数据
	$(function() {
		$("#positionToAddForm").validate({
	        submitHandler: function(form) {
				var orgEntityId = $('#positionToAddForm #orgEntityId').val();
				var positionId = $('#positionToAddForm #positionId').val();
				var url = 'org-entity-done-add-position.do?orgEntityId=' + orgEntityId + '&positionId=' + positionId;
				
				var data = toJsonString('positionToAddForm');
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addPosition();
	    			}
	    		});
	        },
	        errorClass: 'validate-error',
	        rules: {
	        	positionName: {
   	                remote: {
   	                    url: 'position-check-positionname.do',
   	                    data: {
   	                        id: function() {
   	                            return $('#positionToAddForm #positionId').val();
   	                        },
   	                     	orgEntityId: function() {
	                            return $('#positionToAddForm #orgEntityId').val();
	                        }
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	positionName: {
   	                remote: "存在重复"
   	            }
   	        }
		});
	});
	
	//删除字段
	function deletePosition(){
		if($('#positionHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
		}else{
			var url = 'org-entity-done-remove-position.do?';
			$('#positionHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'positionId=' + $(item).val();
				}else{
					url += '&positionId=' + $(item).val();
				}
			});
			$.post(url, function(data){
				if(data == 'success'){
					addPosition();
				}else{
					alert('删除失败！');
				}
			});
		}
	}
	
	function revisePosition(){
		if($('#positionHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var positionId = $('#positionHasAddForm .selectedItemId:checked').val();
			var url = 'org-entity-to-revise-position.do?positionId=' + positionId;
			$.post(url, function(data){
				var position = data.position;
				$('#positionToAddForm #positionId').val(position.id);
				$('#positionToAddForm #orgEntityId').val(position.orgEntity.id);
				
				$('#positionToAddForm #positionName').val(position.positionName);
				$('#positionToAddForm #priority').val(position.priority);
				$('#positionToAddForm #grade').val(position.grade);
				$('#positionToAddForm #status').val(position.status);
			});
		}
	}
	
	//拼接成json数据类型
	function toJsonString(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '{';
		$.each(fields, function(i, item){
				if(i == 0){
   				data += '"' + item.name + '":"' + item.value + '"';
   			}else{
   				data += ',"' + item.name + '":"' + item.value + '"';
   			}
		});
		data += '}';
		
		return data;
	}
	//针对数组
	function toJsonStringArray(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '[{';
		$.each(fields, function(i, item){
			if(i == 0){
   				data += '"' + item.name + '":"' + item.value + '"';
   			}else{
   				if(item.name == 'id'){
   					data += '},{"' + item.name + '":"' + item.value + '"';
   				}else{
   					data += ',"' + item.name + '":"' + item.value + '"';
   				}
   			}
		});
		data += '}]';
		
		return data;
	}
	
    </script>
  </body>

</html>
