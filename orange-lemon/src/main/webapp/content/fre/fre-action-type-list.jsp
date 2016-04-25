<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>动作类型管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>动作类型管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='fre-action-type-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										
										<!-- <button class="btn btn-sm red" id="viewActionField">
											字段信息</button> -->
											
										<button class="btn btn-sm green" id="addActionField">
											字段管理</button>
										
										<!-- <button class="btn btn-sm red" id="viewActionTypeIdentity">
											已委派人员</button>
											
										<button class="btn btn-sm red" id="addActionTypeIdentity">
											委派人员</button> -->
										
										<button class="btn btn-sm green" id="addActionIdentity">
											委派人员</button>
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
								<form name="searchForm" method="post" action="fre-action-type-list.do" class="form-inline">
								    <label for="typeName">类型名称</label>
								    <input type="text" id="typeName" name="typeName" value="${param.typeName}" class="form-control">
								    
								    <label for="internal">是否对内</label>
								    <select id="internal" name="internal" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.internal == 'T'}">selected</c:if> >T</option>
										<option value="F" <c:if test="${param.internal == 'F'}">selected</c:if> >F</option>
									</select>
									
									<label for="delegate">是否委托</label>
								    <select id="delegate" name="delegate" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.delegate == 'T'}">selected</c:if> >T</option>
										<option value="F" <c:if test="${param.delegate == 'F'}">selected</c:if> >F</option>
									</select>
									
									<label for="prime">是否填报</label>
								    <select id="prime" name="prime" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.prime == 'T'}">selected</c:if> >T</option>
										<option value="F" <c:if test="${param.prime == 'F'}">selected</c:if> >F</option>
									</select>
									
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
										<option value=""></option>
										<option value="T" <c:if test="${param.status == 'T'}">selected</c:if> >已启用</option>
										<option value="F" <c:if test="${param.status == 'F'}">selected</c:if> >已禁用</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<!--  
							<article class="m-widget" style="max-height: 350px;overflow-y: scroll;">
							-->
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-action-type-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover" >
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="TYPE_NAME">类型名称</th>
								                    <th class="sorting" name="DELEGATE">是否发送委托</th>
								                    <th class="sorting" name="INTERNAL">是否对内</th>
								                    <th class="sorting" name="PRIME">是否有填报内容</th>
								                    <th>处理人</th>
								                    <th class="sorting" name="DELEGATE_TEMPLATE_ID">委托模板</th>
								                    <th class="sorting" name="DESCN">说明</th>
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
								                    <td>${item.typeName}</td>
								                    <td>${item.delegate}</td>
								                    <td>${item.internal}</td>
								                    <td>${item.prime}</td>
								                    <td>${item.assignee == null ? '无' : item.assignee.displayName}</td>
								                    <td>${item.freightDelegateTemplate == null ? '无' : item.freightDelegateTemplate.templateName}</td>
								                    <td>${item.descn}</td>
								                    <td id="${item.id}status">${item.status == 'T'?'已启用':'已禁用'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="fre-action-type-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<!--字段管理-->
	<div class="modal fade" id="actionFieldModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">动作字段</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="actionFieldToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="actionFieldBtnForm" name="actionFieldBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#actionFieldToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm red" onclick="reviseActionField();">修改</button>
						<button type="button" class="btn btn-sm green" onclick="deleteActionField();">删除</button>
					</form>
				</article>
				<article class="m-widget" style="max-height:300px;overflow-y: scroll;">
				<form id="actionFieldHasAddForm" action="" method="post" class="m-form-blank" ></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 关联人员 -->
	<div class="modal fade" id="actionIdentityModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">委派人员</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="actionIdentityToAddForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
				
				<article class="m-widget">
					<form id="actionIdentityBtnForm" name="actionIdentityBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="$('#actionIdentityToAddForm').submit()">添加</button>
						<button class="btn btn-sm red" onclick="deleteActionIdentity();">清除委派</button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="actionIdentityHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	        'typeName': '${param.typeName}',
	 	        'internal': '${param.internal}',
	 	    	'delegate': '${param.delegate}',
	 	    	'prime': '${param.prime}',
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
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	/* $(document).delegate('#viewActionField', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-field-by-actiontypeid.do?freightActionTypeId=' + freightActionTypeId;
			listData(url, '字段信息', 1000,
					['字段名','字段(库)','字段类型','是否必须','是否共享'], 
					['fieldName','fieldColumn', 'fieldType' ,'required','participate']
			);
		}
	}); */
	
	/* $(document).delegate('#viewActionTypeIdentity', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-type-identity-by-actiontypeid.do?freightActionTypeId=' + freightActionTypeId;
			listData(url, '已委派人员信息', 1000,
					['姓名','职位'], 
					['DISPLAY_NAME','POSITION_NAME']
			);
		}
	}); */
	
	/* $(document).delegate('#addActionTypeIdentity', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			window.open('fre-action-type-identity-input.do?freightActionTypeId=' + freightActionTypeId);
		}
	}); */
	//////////////////////////////////////////////////动作字段
	//添加字段
	$(document).delegate('#addActionField', 'click',function(e){
		if(addActionField()){
			var margin = (window.screen.availWidth - 1300)/2;
			$('#actionFieldModal').css("margin-left", margin);
			$('#actionFieldModal').css("width","1300px");
			$('#actionFieldModal').modal();
		}
	});
	
	//添加字段
	function addActionField(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var status = $('#' + freightActionTypeId + 'status').text();
			if(status != '已启用'){
				alert('已禁用的动作不能做任何操作！');
				return false;
			}else{
				$.ajax({
					url:'fre-action-field-to-add-field.do?freightActionTypeId=' + freightActionTypeId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						var hasAddData = data.hasAddData;
						var html = '<input id="freightActionTypeId" type="hidden" value="' + freightActionTypeId + '">';
						html += '<input id="freightActionFieldId" type="hidden" value="">';
						html += '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th>字段名</th><th>字段库</th><th>字段类型</th><th>是否含箱</th><th>是否必须</th><th>是否共享</th><th>是否可选</th><th>状态</th><th>词典属性</th><th>词典类型</th><th>词典字段</th><th>词典过滤</th><th>顺序</th></tr></thead><tbody>';
						html += '<tr><td><input id="fieldName" name="fieldName" value="" class="form-control required"></td>';
						html += '<td><input id="fieldColumn" name="fieldColumn" value="" class="form-control required"></td>';
						html += '<td><select id="fieldType" name="fieldType" class="form-control required" >';
						html += '<option value="VARCHAR">VARCHAR</option><option value="TEXT">TEXT</option><option value="INT">INT</option>';
						html += '<option value="DOUBLE">DOUBLE</option><option value="DATETIME">DATETIME</option><option value="TIMESTAMP">TIMESTAMP</option>';
						html += '</select></td>';
						html += '<td><select id="forBox" name="forBox" class="form-control required" >';
						html += '<option value="T">是</option><option value="F">否</option>';
						html += '</select></td>';
						html += '<td><select id="required" name="required" class="form-control required" >';
						html += '<option value="T">是</option><option value="F">否</option>';
						html += '</select></td>';
						html += '<td><select id="participate" name="participate" class="form-control required" >';
						html += '<option value="T">是</option><option value="F">否</option>';
						html += '</select></td>';
						html += '<td><select id="canSelect" name="canSelect" class="form-control required" >';
						html += '<option value="T">是</option><option value="F">否</option>';
						html += '</select></td>';
						html += '<td><select id="status" name="status" class="form-control required input-xsmall" >';
						html += '<option value="T">是</option><option value="F">否</option>';
						html += '</select></td>';
						html += '<td><input id="vAttrId" name="vAttrId" value="" class="form-control"></td>';
						html += '<td><input id="vClsId" name="vClsId" value="" class="form-control"></td>';
						html += '<td><input id="vColumn" name="vColumn" value="" class="form-control"></td>';
						html += '<td><input id="vFilterId" name="vFilterId" value="" class="form-control"></td>';
						var displayIndex = (hasAddData == null || hasAddData.length == 0) ? 1 : (hasAddData[hasAddData.length -1].displayIndex + 1);
						html += '<td><input id="displayIndex" name="displayIndex" value="' + displayIndex + '" class="form-control required number"></td>';
						html += '</tr>'
						$('#actionFieldToAddForm').html(html);
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>字段名</th><th>字段库</th><th>字段类型</th><th>是否含箱</th><th>是否必须</th><th>是否共享</th><th>是否可选</th><th>状态</th><th>词典属性</th><th>词典类型</th><th>词典字段</th><th>词典过滤</th><th>顺序</th></tr></thead><tbody>';
						$.each(hasAddData, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
							html += '<td>' + item.fieldName + '</td>';
							html += '<td>' + item.fieldColumn + '</td>';
							html += '<td>' + item.fieldType + '</td>';
							html += '<td>' + item.forBox + '</td>';
							html += '<td>' + item.required + '</td>';
							html += '<td>' + item.participate + '</td>';
							html += '<td>' + item.canSelect + '</td>';
							html += '<td>' + item.status + '</td>';
							html += '<td>' + item.vAttrId + '</td>';
							html += '<td>' + item.vClsId + '</td>';
							html += '<td>' + item.vColumn + '</td>';
							html += '<td>' + item.vFilterId + '</td>';
							html += '<td>' + item.displayIndex + '</td></tr>';
						});
						html += "</tbody></table>";
						$('#actionFieldHasAddForm').html(html);
					},
					error:function(){
					}
				});
				return true;
			}
		}
	}
	//修改字段
	function reviseActionField(){
		if($('#actionFieldHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var freightActionFieldId = $('#actionFieldHasAddForm .selectedItemId:checked').val();
			var url = 'fre-action-field-to-revise-field.do?freightActionFieldId=' + freightActionFieldId;
			$.post(url, function(data){
				var freightActionField = data.freightActionField;
				$('#actionFieldToAddForm #freightActionFieldId').val(freightActionField.id);
				$('#actionFieldToAddForm #fieldName').val(freightActionField.fieldName);
				$('#actionFieldToAddForm #fieldColumn').val(freightActionField.fieldColumn);
				$('#actionFieldToAddForm #fieldType').val(freightActionField.fieldType);
				$('#actionFieldToAddForm #forBox').val(freightActionField.forBox);
				$('#actionFieldToAddForm #required').val(freightActionField.required);
				$('#actionFieldToAddForm #participate').val(freightActionField.participate);
				$('#actionFieldToAddForm #canSelect').val(freightActionField.canSelect);
				$('#actionFieldToAddForm #status').val(freightActionField.status);
				$('#actionFieldToAddForm #vAttrId').val(freightActionField.vAttrId);
				$('#actionFieldToAddForm #vClsId').val(freightActionField.vClsId);
				$('#actionFieldToAddForm #vColumn').val(freightActionField.vColumn);
				$('#actionFieldToAddForm #vFilterId').val(freightActionField.vFilterId);
				$('#actionFieldToAddForm #displayIndex').val(freightActionField.displayIndex);
			});
		}
	}
	//保存数据
	$(function() {
		$("#actionFieldToAddForm").validate({
	        submitHandler: function(form) {
				var freightActionTypeId = $('#actionFieldToAddForm #freightActionTypeId').val();
				var freightActionFieldId = $('#actionFieldToAddForm #freightActionFieldId').val();//修改单条费用时使用
				if(freightActionTypeId == undefined || freightActionTypeId == ''){
	    			alert('请重新操作!');
	    			return false;
				}
				var data = toJsonString('actionFieldToAddForm');
				var url = 'fre-action-field-done-add-field.do?freightActionTypeId=' + freightActionTypeId + '&freightActionFieldId=' + freightActionFieldId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addActionField();
	    			}
	    		});
	        },
	        errorClass: 'validate-error',
	        rules: {
            	fieldName: {
   	                remote: {
   	                    url: 'fre-action-field-check-fieldname.do',
   	                    data: {
   	                    	freightActionTypeId: function() {
   	                            return $('#actionFieldToAddForm #freightActionTypeId').val();
   	                        },
   	                        id: function() {
   	                            return $('#actionFieldToAddForm #freightActionFieldId').val();
   	                        }
   	                    }
   	                }
   	            },
   	         	fieldColumn: {
	                remote: {
	                    url: 'fre-action-field-check-fieldcolumn.do',
	                    data: {
	                    	freightActionTypeId: function() {
   	                            return $('#actionFieldToAddForm #freightActionTypeId').val();
   	                        },
   	                     	id: function() {
	                            return $('#actionFieldToAddForm #freightActionFieldId').val();
	                        }
	                    }
	                }
	            }
   	        },
   	        messages: {
   	        	fieldName: {
   	                remote: "存在重复"
   	            },
   	         	fieldColumn: {
	                remote: "存在重复"
	            }
   	        }
		});
	});
	
	//删除字段
	function deleteActionField(){
		var url = 'fre-action-field-done-remove-field.do?';
		if($('#actionFieldHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			$('#actionFieldHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightActionFieldId=' + $(item).val();
				}else{
					url += '&freightActionFieldId=' + $(item).val();
				}
			});
			$.post(url, function(data){
				if(data == 'success'){
					addActionField();
				}else{
					alert('删除失败！');
				}
			});
		}
	}
	
	
	////////////////////////////分配人员
	$(document).delegate('#addActionIdentity', 'click',function(e){
		if(addActionIdentity()){
			var margin = (window.screen.availWidth - 800)/2;
			$('#actionIdentityModal').css("margin-left", margin);
			$('#actionIdentityModal').css("width","800px");
			$('#actionIdentityModal').modal();
		}
	});
	
	//添加字段
	function addActionIdentity(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightActionTypeId = $('.selectedItem:checked').val();
			var status = $('#' + freightActionTypeId + 'status').text();
			if(status != '已启用'){
				alert('已禁用的动作不能做任何操作！');
				return false;
			}else{
				$.ajax({
					url:'fre-action-type-identity-to-add-identity.do?freightActionTypeId=' + freightActionTypeId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						var hasAddData = data.hasAddData;
						var users = data.users;
						var html = '<input id="freightActionTypeId" type="hidden" value="' + freightActionTypeId + '">';
						html += '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th>姓名</th></tr></thead><tbody>';
						html += '<td><select id="userId" name="userId" class="form-control required" >';
						$.each(users, function(i, item){
							if(item.position != null){
								html += '<option value="'+ item.id +'">' + (item.position == null ? '无' : item.orgEntity.orgEntityName) + '_' + item.displayName + '_' + (item.position == null ? '无' : item.position.positionName) + '</option>';
							}
						});
						html += '</select></td>';
						html += '</tr>'
						$('#actionIdentityToAddForm').html(html);
						initSelect2();//初始化选择
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>姓名</th><th>职位名称</tr></thead><tbody>';
						$.each(hasAddData, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.userId+'" /></td>';
							html += '<td>' + item.displayName + '</td>';
							html += '<td>' + item.positionName + '</td>';
						});
						html += "</tbody></table>";
						$('#actionIdentityHasAddForm').html(html);
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
		$("#actionIdentityToAddForm").validate({
	        submitHandler: function(form) {
				var freightActionTypeId = $('#actionIdentityToAddForm #freightActionTypeId').val();
				var userId = $('#actionIdentityToAddForm #userId').val();
				var url = 'fre-action-type-identity-done-add-identity.do?freightActionTypeId=' + freightActionTypeId + '&userId=' + userId;
				$.post(url, function(data){
					addActionIdentity();
				});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除
	function deleteActionIdentity(){
		var freightActionTypeId = $('#actionIdentityToAddForm #freightActionTypeId').val();
		var url = 'fre-action-type-identity-done-remove-identity.do?freightActionTypeId=' + freightActionTypeId;
		$.post(url, function(data){
			if(data == 'success'){
				addActionIdentity();
			}else{
				alert('删除失败！');
			}
		});
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
