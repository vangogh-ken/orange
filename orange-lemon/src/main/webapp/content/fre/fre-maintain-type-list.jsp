<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>操作类型管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>操作类型管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='fre-maintain-type-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" id="viewActionType" >
											关联动作</button>
											
										<button class="btn btn-sm red" id="addActionType">
											管理动作</button>
											
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
								<form name="searchForm" method="post" action="fre-maintain-type-list.do" class="form-inline">
								    <label for="typeName">类型名称</label>
								    <input type="text" id="typeName" name="typeName" value="${param.typeName}" class="form-control">
								    
								    <label for="priority">优先级</label>
								    <select id="priority" name="priority" class="form-control">
										<option value=""></option>
										<option value="1"<c:if test="${param.priority == '1'}">selected</c:if> >1</option>
										<option value="2"<c:if test="${param.priority == '2'}">selected</c:if> >2</option>
									</select>
									
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
										<option value="T"<c:if test="${item.param == 'T'}">selected</c:if> >已启用</option>
										<option value="F"<c:if test="${item.param == 'F'}">selected</c:if> >已停用</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-maintain-type-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="TYPE_NAME">类型名称</th>
								                    <th class="sorting" name="PRIORITY">优先级</th>
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
								                    <td>${item.priority}</td>
								                    <td>${item.descn}</td>
								                    <td>${item.status == 'T'?'已启用':'已停用'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="fre-maintain-type-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	<!-- 关联动作类型 -->
	<div class="modal fade" id="actionTypeModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加动作类型</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
					<form id="actionTypeSearchForm" name="actionTypeSearchForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<label for="typeName">类型名称</label>
						<input type="text" id="typeName" name="typeName" class="form-control">
						<button class="btn btn-sm red" onclick="filterList();">查询<i class="fa fa-search"></i></button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="actionTypeToAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
				
				<article class="m-widget">
					<form id="actionTypeBtnForm" name="actionTypeBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button class="btn btn-sm red" onclick="submitActionType();">添加</button>
						<button class="btn btn-sm red" onclick="deleteActionType();">删除</button>
					</form>
				</article>
				
				<article class="m-widget">
				<form id="actionTypeHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	        'priority': '${param.priority}',
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
	});
	
	$(document).delegate('#addActionType', 'click', function(e){
		if(addActionType()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#actionTypeModal').css("margin-left", margin);
			$('#actionTypeModal').css("width","1000px");
			$('#actionTypeModal').modal();
		}
	});
	
	function addActionType(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightMaintainTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-type-by-maintaintypeid.do?freightMaintainTypeId=' + freightMaintainTypeId;
			$.post(url, function(data){
				var toAddData = data.toAddData;//可选的费用
				var hasAddData = data.hasAddData;//已关联的费用
				
				var htmlList = '<table id="actionTypeTable" class="m-table table-bordered table-hover">';
				htmlList += '<thead><tr>';
				htmlList += '<th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsActionType(this.checked)"/></th>';
				htmlList += '<th>类型名称</th><th>是否发送委托</th><th>是否对内</th><th>是否有填报内容</th><th>状态</th>';
				htmlList += '</tr></thead><tbody>';
				$.each(toAddData, function(i, item){
					htmlList += '<tr style="display:none;"><td><input class="selectedItemActionType a-check" type="checkbox" name="actionTypeIds" value="'+item.id+'" /></td>';
					htmlList += '<td>' + item.typeName + '</td>';
					htmlList += '<td>' + item.delegate + '</td>';
					htmlList += '<td>' + item.internal + '</td>';
					htmlList += '<td>' + item.prime + '</td>';
					htmlList += '<td>' + item.status + '</td></tr>';
				});
				htmlList += "</tbody></table>";
				$('#actionTypeToAddForm').html(htmlList);
				
				htmlList = '';//重置
				htmlList += '<table id="actionTypeHasAddTable" class="m-table table-bordered table-hover">';
				htmlList += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" onchange="toggleSelectedItemsActionTypeToDelete(this.checked)"/></th>';
				htmlList += '<th>类型名称</th><th>是否发送委托</th><th>是否对内</th><th>是否有填报内容</th><th>状态</th>';
				htmlList += '</tr></thead><tbody>';
				$.each(hasAddData, function(i, item){
					htmlList += '<tr><td><input class="selectedItemActionTypeToDelete a-check" type="checkbox" name="actionTypeIds" value="'+item.id+'" /></td>';
					htmlList += '<td>' + item.typeName + '</td>';
					htmlList += '<td>' + item.delegate + '</td>';
					htmlList += '<td>' + item.internal + '</td>';
					htmlList += '<td>' + item.prime + '</td>';
					htmlList += '<td>' + item.status + '</td></tr>';
				});
				htmlList += "</tbody></table>";
				$('#actionTypeHasAddForm').html(htmlList);
			});
		}
		return true;
	}
	
	//过滤数据
	function filterList(){
		$('#actionTypeToAddForm tr').each(function(i, item){
			if($(item).children('td').length > 0){
				$(item).show();
			}
		});
		$('#actionTypeToAddForm tr').each(function(i, item){
			if($(item).text().indexOf($('#actionTypeSearchForm #typeName').val()) < 0){
				if($(item).children('td').length > 0){
					$(item).hide();
				}
			}
		});
	}
	
	$(document).delegate('#viewActionType', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightMaintainTypeId = $('.selectedItem:checked').val();
			var url = 'fre-action-type-get-maintaintypeid.do?freightMaintainTypeId=' + freightMaintainTypeId;
			listData(url, '已关联动作', 1000,
					['类型名称','是否发送委托','是否对内','是否有填报内容','状态'], 
					['typeName','delegate','internal','prime','status']
			);
		}
	});
	
	function toggleSelectedItemsActionType(isChecked) {
		$(".selectedItemActionType:visible").each(function(index, el) {//因为有其他隐藏的,在此必须要过滤
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
	
	function toggleSelectedItemsActionTypeToDelete(isChecked) {
		$(".selectedItemActionTypeToDelete").each(function(index, el) {
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
	
	//提交费用
	function submitActionType(){
		var actionTypeIds = '';
		$('#actionTypeToAddForm .selectedItemActionType:checked').each(function(i, item){
			actionTypeIds += $(item).val() + ",";
		});
		
		if(actionTypeIds != ''){
			actionTypeIds = actionTypeIds.substring(0, actionTypeIds.length - 1);
		}else{
			return false;//如果没有选择，则返回了
		}
		
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}
		var freightMaintainTypeId = $('.selectedItem:checked').val();
		var url = 'fre-action-type-add-tomaintaintype.do?actionTypeIds=' + actionTypeIds + '&freightMaintainTypeId=' + freightMaintainTypeId;
		$.post(url, function(data){
			if(data == 'success'){
				addActionType();
			}
		});
	}
	
	function deleteActionType(){
		var actionTypeIds = '';
		$('#actionTypeHasAddForm .selectedItemActionTypeToDelete:checked').each(function(i, item){
			actionTypeIds += $(item).val() + ",";
		});
		if(actionTypeIds != ''){
			actionTypeIds = actionTypeIds.substring(0, actionTypeIds.length - 1);
		}else{
			return false;//如果没有选择，则返回了
		}
		var freightMaintainTypeId = $('.selectedItem:checked').val();
		var url = 'fre-action-type-remove-bymaintaintype.do?actionTypeIds=' + actionTypeIds + '&freightMaintainTypeId=' + freightMaintainTypeId;
		$.post(url, function(data){
			if(data == 'success'){
				addActionType();
			}
		});
	}
    </script>
  </body>

</html>
