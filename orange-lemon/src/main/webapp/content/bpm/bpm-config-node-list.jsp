<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程节点配置</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程节点配置</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
										
									<button class="btn btn-sm green" id="configStatus">
											设置状态</button>
									<button class="btn btn-sm green" id="configAuth">
											设置权限</button>
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
								<form name="searchForm" method="post" action="bpm-config-node-list.do" class="form-inline">
								    <label for="pdId">选择流程</label>
								    <select id="pdId" name="pdId" class="form-control">
								    		<option value=""></option>
								    	<c:forEach items="${pds}" var="pd">
								    		<option value="${pd.id}" ${param.pdId == pd.id ? 'selected' : ''}>${pd.key}:${pd.name},版本:V${pd.version}</option>
								    	</c:forEach>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="bpm-config-node-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="PD_KEY">流程KEY</th>
										        <th class="sorting" name="PD_NAME">流程名称</th>
										        <th class="sorting" name="TD_KEY">任务KEY</th>
										        <th class="sorting" name="TD_NAME">名称</th>
										        <th class="sorting" name="SOURCE_STATUS">运行时</th>
										        <th class="sorting" name="TARGET_STATUS">完成时</th>
										        <th class="sorting" name="BASIS_STATUS_id">类型状态</th>
										        <th class="sorting" name="ON_CREATE">创建时操作</th>
										        <th class="sorting" name="ON_COMPLETE">完成时操作</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.pdKey}</td>
										        <td>${item.pdName}</td>
										        <td>${item.tdKey}</td>
										        <td>${item.tdName}</td>
										        <td>${item.sourceStatus}</td>
										        <td>${item.targetStatus}</td>
										        <td>${item.basisStatus == null ? '无' : item.basisStatus.statusText}</td>
										        <td>${(item.onCreate != null and item.onCreate != '') ?'有':'无'}</td>
										        <td>${(item.onComplete != null and item.onComplete != '') ?'有':'无'}</td>
										        <td>
										        	<a class="btn btn-sm red" href="bpm-config-node-input.do?id=${item.id}">编辑</a>
										        	<a class="btn btn-sm green" onclick="nodeOperationConfig('${item.id}');" >设置操作</a>
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
	
	<div class="modal fade" id="bpmConfigNodeModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">设置可用操作</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="bpmConfigNodeForm" action="bpm-config-node-operation-save.do" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#bpmConfigNodeForm').submit()">确认</button>
			</div>
		</div>
	</div>
	<!-- 设置节点对应的状态 -->
	<div class="modal fade" id="configStatusModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">设置状态</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="configStatusForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#configStatusForm').submit()">确认</button>
			</div>
		</div>
	</div>
	
	<!-- 设置节点对应的权限 -->
	<div class="modal fade" id="configAuthModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">设置权限</h4>
			</div>
			
			<div class="modal-body">
				<article class="m-widget">
				<button type="button" class="btn btn-sm green" onclick="$('#configAuthForm').submit()">确认</button>
				<button type="button" class="btn btn-sm green" onclick="deleteAuth();">删除</button>
				</article>
				
				<article class="m-widget">
				<form id="configAuthForm" action="" method="post" class="m-form-blank"></form>
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
	 	    	'pdId': '${param.pdId}'
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
	
	//操作设置
	function nodeOperationConfig(bpmConfigNodeId) {
		//var htmlContent = "<input type='hidden' name='id' value='"+id+"'/>";
		var htmlContent = "<table id='bpmConfigNodeTable' class='m-table table-bordered table-hover'>";
		htmlContent += "<thead><tr>";
		//htmlContent += "<th width='10' class='m-table-check'>";
		//htmlContent += "<input type='checkbox' onchange='toggleSelectedItemsStatus(this.checked)'/>";
		htmlContent += "<th>节点名称</th><th>操作类型</th><th>命令</th><th>可用&nbsp;&nbsp;/&nbsp;&nbsp;不可用</th></tr></thead><tbody>";
		$.ajax({
				url:'bpm-config-node-to-config-operation.do?bpmConfigNodeId=' + bpmConfigNodeId,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					$.each(data, function(i, item){
							htmlContent += "<tr>";
							htmlContent += "<input class='selectedItemStatus a-check' type='hidden' name='"+item.id+"_id' value='"+item.id+"' />";
							htmlContent += "<td>" + item.bpmConfigNode.tdName + "</td>";
							htmlContent += "<td>" + item.bpmOperationType.name + "</td>";
							htmlContent += "<td>" + item.command + "</td>";
							
							if(item.enable == "T"){
								htmlContent += "<td>";
								htmlContent += "可用<input name='"+item.id+"_enable' value='T' type='radio' checked/>&nbsp;&nbsp;&nbsp;&nbsp;";
								htmlContent += "不可用<input name='"+item.id+"_enable' value='F' type='radio'/>";
								htmlContent += "</td>";
							}else{
								htmlContent += "<td>";
								htmlContent += "可用<input name='"+item.id+"_enable' value='T' type='radio' />&nbsp;&nbsp;&nbsp;&nbsp;";
								htmlContent += "不可用<input name='"+item.id+"_enable' value='F' type='radio' checked/>";
								htmlContent += "</td>";
							}
							
							htmlContent += "</tr>";
					});
					htmlContent += "</tbody></table>";
					$('#bpmConfigNodeForm').html(htmlContent);
				},
				error:function(){
					
				}
		});
		
		var margin = (window.screen.availWidth - 800)/2;
		$('#bpmConfigNodeModal').css("margin-left", margin);
		$('#bpmConfigNodeModal').css("width","800px");
		$('#bpmConfigNodeModal').modal();
	}
	
	//设置状态
	$(document).delegate('#configStatus', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var bpmConfigNodeId = $('.selectedItem:checked').val();
			$.post('bpm-config-node-to-config-status.do?bpmConfigNodeId=' + bpmConfigNodeId, function(data){
				var basisStatuses = data.basisStatuses;
				if(basisStatuses != null && basisStatuses.length > 0){
					var html = '<input id="bpmConfigNodeId" name="bpmConfigNodeId" type="hidden" value="'+ bpmConfigNodeId +'">';
					html += '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"></th><th>类型名称</th><th>状态名称</th></tr></thead></tbody>';
					$.each(basisStatuses, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" name="basisStatusId" type="radio" value="' + item.id+'" /></td>'
						html += '<td>'+ item.basisSubstanceType.typeName +'</td><td>'+ item.statusText +'</td></tr>';
					});
					html += '<tbody></table>';
					$('#configStatusForm').html(html);
					var margin = (window.screen.availWidth - 800)/2;
					$('#configStatusModal').css("margin-left", margin);
					$('#configStatusModal').css("width","800px");
					$('#configStatusModal').modal();
				}else{
					alert('当前节点所属流程尚未设置信封信息！');
				}
			});
		}
	});
	
	$(function() {
		$("#configStatusForm").validate({
	        submitHandler: function(form) {
	        	$.post('bpm-config-node-done-config-status.do', $('#configStatusForm').serialize(), function(data){
	        		if(data == 'success'){
	        			alert('设置成功！');
	        			window.location.href = window.location.href;
	        		}else{
	        			alert('设置失败！');
	        		}
	        	});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	
	//设置权限
	$(document).delegate('#configAuth', 'click',function(e){
		configAuth();
	});
	function configAuth(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var bpmConfigNodeId = $('.selectedItem:checked').val();
			$.post('bpm-config-node-to-config-auth.do?bpmConfigNodeId=' + bpmConfigNodeId, function(data){
				var users = data.users;//用户
				var roles = data.roles;//角色
				var positions = data.positions;//职位
				var constantAuthes = data.constantAuthes;//常量
				var html = '<input id="bpmConfigNodeId" name="bpmConfigNodeId" type="hidden" value="'+ bpmConfigNodeId +'">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th colspan="2">权限类型</th><th>选择</th></tr></thead></tbody>';
				html += '<tr><td colspan="2"><select id="authType" name="authType" class="form-control required" onchange="updateAuthType();">';
				html += '<option value=""></option><option value="U">用户</option><option value="R">角色</option><option value="P">职位</option><option value="C">常量</option>';
				html += '</select></td>';
				html += '<td><select id="authId" name="authId" class="form-control required"></select></td></tr>';
				
				$.each(users, function(i, item){
					html += '<tr><td style="width:20px;"><input class="selectedItemId a-check" name="authIdHasAdd" type="radio" value="' + item.id+'" /></td>'
					html += '<td>用户 </td>'
					html += '<td>'+ item.displayName +'</td></tr>';
				});
				$.each(roles, function(i, item){
					html += '<tr><td style="width:20px;"><input class="selectedItemId a-check" name="authIdHasAdd" type="radio" value="' + item.id+'" /></td>'
					html += '<td>角色</td>'
					html += '<td>'+ item.roleName +'</td></tr>';
				});
				$.each(positions, function(i, item){
					html += '<tr><td style="width:20px;"><input class="selectedItemId a-check" name="authIdHasAdd" type="radio" value="' + item.id+'" /></td>'
					html += '<td>职位</td>'
					html += '<td>'+ item.positionName +'</td></tr>';
				});
				$.each(constantAuthes, function(i, item){
					html += '<tr><td style="width:20px;"><input class="selectedItemId a-check" name="authIdHasAdd" type="radio" value="' + item.id+'" /></td>'
					html += '<td>常量</td>'
					html += '<td>'+ item.constantName +'</td></tr>';
				});
				
				html += '<tbody></table>';
				$('#configAuthForm').html(html);
				
				var margin = (window.screen.availWidth - 800)/2;
				$('#configAuthModal').css("margin-left", margin);
				$('#configAuthModal').css("width","800px");
				$('#configAuthModal').modal();
			});
		}
	}
	
	function updateAuthType(){
		var authType = $('#authType').val();
		if(authType != ''){
			var url = '';
			if(authType == 'U'){
				url = '../user/user-get-all.do';
			}else if(authType == 'R'){
				url = '../role/role-get-all.do';
			}else if(authType == 'P'){
				url = '../org/position-get-all.do';
			}else if(authType == 'C'){
				url = '../auth/constant-auth-get-all.do';
			}
			
			$.post(url, function(data){
				if(data != null && data.length > 0){
					var html = '';
					if(authType == 'U'){
						$.each(data, function(i, item){
							html += '<option value="'+ item.id +'">'+ item.displayName +'</option>';
						});
					}else if(authType == 'R'){
						$.each(data, function(i, item){
							html += '<option value="'+ item.id +'">'+ item.roleName +'</option>';
						});
					}else if(authType == 'P'){
						$.each(data, function(i, item){
							html += '<option value="'+ item.id +'">'+ item.positionName +'</option>';
						});
					}else if(authType == 'C'){
						$.each(data, function(i, item){
							html += '<option value="'+ item.id +'">'+ item.constantName +'</option>';
						});
					}
					
					$('#authId').html(html);
					$('#authId').select2();
					$('#authId').select2('val', '');
				}
			});
		}
	}
	
	$(function() {
		$("#configAuthForm").validate({
	        submitHandler: function(form) {
	        	$.post('bpm-config-node-done-config-auth.do', $('#configAuthForm').serialize(), function(data){
	        		if(data == 'success'){
	        			alert('设置成功！');
	        			configAuth();
	        		}else{
	        			alert('设置失败！');
	        		}
	        	});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	function deleteAuth(){
		var bpmConfigNodeId = $('#configAuthForm #bpmConfigNodeId').val();
		var authId = $('#configAuthForm .selectedItemId:checked').val();
		$.post('bpm-config-node-done-delete-auth.do?bpmConfigNodeId=' + bpmConfigNodeId + '&authId=' + authId, function(data){
			if(data != 'success'){
				alert('删除失败！');
				configAuth();
			}else{
				configAuth();
			}
		});
	}
    </script>
  
  </body>

</html>
