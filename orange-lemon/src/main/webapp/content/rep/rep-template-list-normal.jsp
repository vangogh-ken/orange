<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>模板管理(普通)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>模板管理(普通)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='rep-template-input-normal.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
									
									<button class="btn btn-sm green" id="addDataSource">
											添加数据</button>
											
									<button class="btn btn-sm green" id="addParam">
											添加参数</button>
											
									<button class="btn btn-sm green" id="addSet">
											添加设置</button>
											
									<button class="btn btn-sm green" id="releaseReport">
											生成报表</button>
											
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
								<form name="searchForm" method="post" action="rep-template-list-normal.do" class="form-inline">
								    <label for="templateName">模板名称</label>
								    <input type="text" id="templateName" name="templateName" value="${param.templateName}" class="form-control">
								    
								    <label for="templateType">模板类型</label>
								    <select id="templateType" name="templateType" class="form-control required">
								    	<option value=""></option>
										<option value="报表" <c:if test="${param.templateType == '报表' }">selected</c:if>>报表</option>
										<option value="普通" <c:if test="${param.templateType == '普通' }">selected</c:if>>普通</option>
									</select>
								    <!--  
								    <label for="status">状态</label>
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control">
									-->
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="rep-template-remove-normal.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="REP_CATEGORY_ID">类别</th>
								                    <th class="sorting" name="TEMPLATE_NAME">模板名称</th>
								                    <th class="sorting" name="TEMPLATE_TYPE">模板类型</th>
								                    <th class="sorting" name="TEMPLATE_FILE">模板文件</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.reportCategory.categoryName}</td>
								                    <td>${item.templateName}</td>
								                    <td id="${item.id}templateType">${item.templateType}</td>
								                    <td><a href="rep-template-download-template-file.do?reportTemplateId=${item.id}" target="_blank">查看文件</a></td>
								                    <td id="${item.id}status">${item.descn}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<a href="rep-template-input-normal.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<!-- 数据源-->
	<div class="modal fade" id="dataSourceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加数据源</h4>
			</div>
			<!-- 添加数据源-->
			<div class="modal-body">
				<article class="m-widget">
				<form id="dataSourceToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="dataSourceBtnForm" name="dataSourceBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#dataSourceToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm red" onclick="reviseDataSource();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="deleteDataSource();">删除</button>
					</form>
				</article>
			
				<!-- 已有数据源 -->
				<article class="m-widget">
				<form id="dataSourceHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 参数-->
	<div class="modal fade" id="paramModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加参数</h4>
			</div>
			<!-- 添加参数-->
			<div class="modal-body">
				<article class="m-widget">
				<form id="paramToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="paramBtnForm" name="paramBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#paramToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm red" onclick="reviseParam();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="deleteParam();">删除</button>
					</form>
				</article>
			
				<!-- 已有参数 -->
				<article class="m-widget">
				<form id="paramHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 设置-->
	<div class="modal fade" id="setModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加设置</h4>
			</div>
			<!-- 添加设置-->
			<div class="modal-body">
				<article class="m-widget">
				<form id="setToAddForm" method="post" class="m-form-blank"></form>
				</article>
			
				<article class="m-widget">
					<form id="setBtnForm" name="setBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm red" onclick="$('#setToAddForm').submit()">确定</button>
						<button type="button" class="btn btn-sm red" onclick="reviseSet();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="deleteSet();">删除</button>
					</form>
				</article>
			
				<!-- 已有设置 -->
				<article class="m-widget">
				<form id="setHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 报表参数填写 -->
	<div class="modal fade" id="releaseReportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">报表参数</h4>
			</div>
			<!-- 报表参数填写 -->
			<div class="modal-body">
				<article class="m-widget">
				<form id="releaseReportToAddForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#releaseReportToAddForm').submit()">确定</button>
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
	 	        'templateType': '${param.templateType}',
	 	        'templateName': '${param.templateName}'
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
	
	/////////////////////////////////////////添加数据源
	$(document).delegate('#addDataSource', 'click',function(e){
		addDataSource();
	});
	
	function addDataSource(reportDataSourceId){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return;
		}else{
			var reportTemplateId = $('.selectedItem:checked').val();
			var templateType = $('#' + reportTemplateId + 'templateType').text();
			if(templateType != '报表'){
				alert('普通模板不能添加数据源！');
				return;
			}else{
				var flag = true;//是否为修改
				if(reportDataSourceId == undefined || reportDataSourceId == '' || reportDataSourceId == null){
					flag = false;
				}
				var url;
				if(flag){
					url = 'rep-data-source-to-revise-data-source.do?reportTemplateId=' + reportTemplateId + '&reportDataSourceId=' + reportDataSourceId;
				}else{
					url = 'rep-data-source-to-add-data-source.do?reportTemplateId=' + reportTemplateId;
				}
				$.post(url, function(data){
					var hasAddData = data.hasAddData;
					var html = '<input id="reportTemplateId" name="reportTemplateId" type="hidden" value="' + reportTemplateId + '">';
					html += '<input id="id" name="id" type="hidden" value="">';
					html += '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th style="width:150px;">数据源名称</th><th>数据源KEY</th><th style="width:150px;">状态</th></tr></thead><tbody>';
					html += '<tr><td><input id="dataSourceName" name="dataSourceName" class="form-control required"></td>';
					html += '<td><input id="dataSourceKey" name="dataSourceKey" class="form-control required"></td>';
					html += '<td>';
					html += '<select id="status" name="status" class="form-control required"><option value="T">T</option><option value="F">F</option></select>';
					html += '</td></tr>';
					
					html += '<tr><td>查询语句</td>';
					html += '<td colspan="2"><textarea id="sqlText" name="sqlText" class="form-control required" style="min-height:150px;"></textarea></td></tr>';
					html += "</tbody></table>";
					$('#dataSourceToAddForm').html(html);
					
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>数据源名称</th><th>数据源KEY</th><th>状态</th></tr></thead><tbody>';
					
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="reportDataSourceId" value="'+item.id+'" /></td>';
						html += '<td>' + item.dataSourceName + '</td>';
						html += '<td>' + item.dataSourceKey + '</td>';
						html += '<td>' + item.status + '</td>';
					});
					html += "</tbody></table>";
					$('#dataSourceHasAddForm').html(html);
					
					if(flag){
						var reportDataSource = data.reportDataSource;
						$('#dataSourceToAddForm #id').val(reportDataSource.id);
						$('#dataSourceToAddForm #dataSourceName').val(reportDataSource.dataSourceName);
						$('#dataSourceToAddForm #dataSourceKey').val(reportDataSource.dataSourceKey);
						$('#dataSourceToAddForm #status').val(reportDataSource.status);
						$('#dataSourceToAddForm #sqlText').val(reportDataSource.sqlText);
					}else{
						var margin = (window.screen.availWidth - 1000)/2;
						$('#dataSourceModal').css("margin-left", margin);
						$('#dataSourceModal').css("width","1000px");
						$('#dataSourceModal').modal();
					}
				});
			}
		}
	}
	
	$(function() {
		$("#dataSourceToAddForm").validate({
	        submitHandler: function(form) {
				var data = toJsonString('dataSourceToAddForm');
				var url = 'rep-data-source-done-add-data-source.do';
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addDataSource();
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	function reviseDataSource(){
		if($('#dataSourceHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择数据！');
		}else{
			addDataSource($('#dataSourceHasAddForm .selectedItemId:checked').val());
		}
	}
	
	//删除
	function deleteDataSource(){
		if($('#dataSourceHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据再删除！');
		}else{
			var url = 'rep-data-source-done-delete-data-source.do?';
			$('#dataSourceHasAddForm .selectedItemId:checked').each(function(i, item){
				url += 'reportDataSourceId=' + $(item).val() + '&';
			});
			url = url.substring(0, url.length - 1);
			$.post(url, function(data){
				if(data == 'success'){
					addDataSource();
				}else{
					alert('删除出错！');
				}
			});
		}
	}
	
	///////////////////////////////////////////添加参数
	$(document).delegate('#addParam', 'click',function(e){
		addParam();
	});
	
	function addParam(reportParamId){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return;
		}else{
			var reportTemplateId = $('.selectedItem:checked').val();
			var templateType = $('#' + reportTemplateId + 'templateType').text();
			if(templateType != '报表'){
				alert('普通模板不能添加数据源！');
				return;
			}else{
				var flag = true;//是否为修改
				if(reportParamId == undefined || reportParamId == '' || reportParamId == null){
					flag = false;
				}
				var url;
				if(flag){
					url = 'rep-param-to-revise-param.do?reportTemplateId=' + reportTemplateId + '&reportParamId=' + reportParamId;
				}else{
					url = 'rep-param-to-add-param.do?reportTemplateId=' + reportTemplateId;
				}
				$.post(url, function(data){
					var hasAddData = data.hasAddData;
					var html = '<input id="reportTemplateId" name="reportTemplateId" type="hidden" value="' + reportTemplateId + '">';
					html += '<input id="id" name="id" type="hidden" value="">';
					html += '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th>参数名称</th><th>参数字段</th><th>参数类型</th><th>默认值</th><th>是否必须</th><th>是否可选</th><th>词典属性ID</th><th>词典类型ID</th><th>词典过滤ID</th><th>词典字段</th></tr></thead><tbody>';
					html += '<tr><td><input id="paramName" name="paramName" class="form-control required"></td>';
					html += '<td><input id="paramColumn" name="paramColumn" class="form-control required"></td>';
					html += '<td><select id="paramType" name="paramType" class="form-control required" ><option value="VARCHAR">VARCHAR</option><option value="DOUBLE">DOUBLE</option><option value="INT">INT</option><option value="DATETIME">DATETIME</option></select></td>';
					html += '<td><input id="defaultValue" name="defaultValue" class="form-control required"></td>';
					html += '<td><select id="required" name="required" class="form-control required" ><option value="T">是</option><option value="F">否</option></select></td>';
					html += '<td><select id="canSelect" name="canSelect" class="form-control required" ><option value="T">是</option><option value="F">否</option></select></td>';
					html += '<td><input id="vAttrId" name="vAttrId" class="form-control"></td>';
					html += '<td><input id="vClsId" name="vClsId" class="form-control"></td>';
					html += '<td><input id="vColumn" name="vColumn" class="form-control"></td>';
					html += '<td><input id="vFilterId" name="vFilterId" class="form-control"></td></tr>';
					html += "</tbody></table>";
					$('#paramToAddForm').html(html);
					
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>参数名称</th><th>参数字段</th><th>参数类型</th><th>默认值</th><th>是否必须</th><th>是否可选</th><th>词典属性ID</th><th>词典类型ID</th><th>词典过滤ID</th><th>词典字段</th></tr></thead><tbody>';
					
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="reportParamId" value="'+item.id+'" /></td>';
						html += '<td>' + item.paramName + '</td>';
						html += '<td>' + item.paramColumn + '</td>';
						html += '<td>' + item.paramType + '</td>';
						html += '<td>' + item.defaultValue + '</td>';
						html += '<td>' + item.required + '</td>';
						html += '<td>' + item.canSelect + '</td>';
						html += '<td>' + item.vAttrId + '</td>';
						html += '<td>' + item.vClsId + '</td>';
						html += '<td>' + item.vColumn + '</td>';
						html += '<td>' + item.vFilterId + '</td>';
					});
					html += "</tbody></table>";
					$('#paramHasAddForm').html(html);
					
					if(flag){
						var reportParam = data.reportParam;
						$('#paramToAddForm #id').val(reportParam.id);
						$('#paramToAddForm #paramName').val(reportParam.paramName);
						$('#paramToAddForm #paramColumn').val(reportParam.paramColumn);
						$('#paramToAddForm #paramType').val(reportParam.paramType);
						$('#paramToAddForm #defaultValue').val(reportParam.defaultValue);
						$('#paramToAddForm #required').val(reportParam.required);
						$('#paramToAddForm #canSelect').val(reportParam.canSelect);
						$('#paramToAddForm #vAttrId').val(reportParam.vAttrId);
						$('#paramToAddForm #vClsId').val(reportParam.vClsId);
						$('#paramToAddForm #vColumn').val(reportParam.vColumn);
						$('#paramToAddForm #vFilterId').val(reportParam.vFilterId);
					}else{
						var margin = (window.screen.availWidth - 1200)/2;
						$('#paramModal').css("margin-left", margin);
						$('#paramModal').css("width","1200px");
						$('#paramModal').modal();
					}
				});
			}
		}
	}
	
	$(function() {
		$("#paramToAddForm").validate({
	        submitHandler: function(form) {
				var data = toJsonString('paramToAddForm');
				var url = 'rep-param-done-add-param.do';
				var flag = true;
				if($('#paramToAddForm #canSelect').val() == 'T'){
					if($('#paramToAddForm #vAttrId').val() == '' && ($('#paramToAddForm #vClsId').val() == '' || $('#paramToAddForm #vColumn').val() == '')){
						alert('可选值时，必须填写词典属性ID或者词典类型ID及词典字段！');
						flag = false;
					}
				}
				if(flag){
					$.ajax({
		    			type: 'POST',
		    			data: data,
		    			url: url,
		    			contentType: 'application/json',
		    			success:function(data){
		    				addParam();
		    			}
		    		});
				}
	        },
	        errorClass: 'validate-error'
		});
	});
	
	function reviseParam(){
		if($('#paramHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择数据！');
		}else{
			addParam($('#paramHasAddForm .selectedItemId:checked').val());
		}
	}
	
	//删除
	function deleteParam(){
		if($('#paramHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据再删除！');
		}else{
			var url = 'rep-param-done-delete-param.do?';
			$('#paramHasAddForm .selectedItemId:checked').each(function(i, item){
				url += 'reportParamId=' + $(item).val() + '&';
			});
			url = url.substring(0, url.length - 1);
			$.post(url, function(data){
				if(data == 'success'){
					addParam();
				}else{
					alert('删除出错！');
				}
			});
		}
	}
	
	/////////////////////////////////////////////////添加设置
	$(document).delegate('#addSet', 'click',function(e){
		addSet();
	});
	
	function addSet(reportSetId){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return;
		}else{
			var reportTemplateId = $('.selectedItem:checked').val();
			var templateType = $('#' + reportTemplateId + 'templateType').text();
			if(templateType != '报表'){
				alert('普通模板不能添加数据源！');
				return;
			}else{
				var flag = true;//是否为修改
				if(reportSetId == undefined || reportSetId == '' || reportSetId == null){
					flag = false;
				}
				var url;
				if(flag){
					url = 'rep-set-to-revise-set.do?reportTemplateId=' + reportTemplateId + '&reportSetId=' + reportSetId;
				}else{
					url = 'rep-set-to-add-set.do?reportTemplateId=' + reportTemplateId;
				}
				$.post(url, function(data){
					var hasAddData = data.hasAddData;
					var html = '<input id="reportTemplateId" name="reportTemplateId" type="hidden" value="' + reportTemplateId + '">';
					html += '<input id="id" name="id" type="hidden" value="">';
					html += '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th>设置类型</th><th>是否启用</th><th>起始行</th><th>截止行</th><th>起始列</th><th>截止列</th></tr></thead><tbody>';
					html += '<tr><td><select id="setType" name="setType" class="form-control required" ><option value="隐藏重复值(横向)">隐藏重复值(横向)</option><option value="隐藏重复值(纵向)">隐藏重复值(纵向)</option><option value="空值替换">空值替换</option><option value="隐藏零值">隐藏零值</option></select></td>';
					html += '<td><select id="enable" name="enable" class="form-control required" ><option value="T">是</option><option value="F">否</option></select></td>';
					html += '<td><input id="firstRow" name="firstRow" class="form-control"></td>';
					html += '<td><input id="lastRow" name="lastRow" class="form-control"></td>';
					html += '<td><input id="firstColumn" name="firstColumn" class="form-control"></td>';
					html += '<td><input id="lastColumn" name="lastColumn" class="form-control"></td></tr>';
					html += "</tbody></table>";
					$('#setToAddForm').html(html);
					
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>设置类型</th><th>是否启用</th><th>起始行</th><th>截止行</th><th>起始列</th><th>截止列</th></tr></thead><tbody>';
					
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="reportSetId" value="'+item.id+'" /></td>';
						html += '<td>' + item.setType + '</td>';
						html += '<td>' + item.enable + '</td>';
						html += '<td>' + item.firstRow + '</td>';
						html += '<td>' + item.lastRow + '</td>';
						html += '<td>' + item.firstColumn + '</td>';
						html += '<td>' + item.lastColumn + '</td>';
					});
					html += "</tbody></table>";
					$('#setHasAddForm').html(html);
					
					if(flag){
						var reportSet = data.reportSet;
						$('#setToAddForm #id').val(reportSet.id);
						$('#setToAddForm #setType').val(reportSet.setType);
						$('#setToAddForm #enable').val(reportSet.enable);
						$('#setToAddForm #firstRow').val(reportSet.firstRow);
						$('#setToAddForm #lastRow').val(reportSet.lastRow);
						$('#setToAddForm #firstColumn').val(reportSet.firstColumn);
						$('#setToAddForm #lastColumn').val(reportSet.lastColumn);
					}else{
						var margin = (window.screen.availWidth - 1000)/2;
						$('#setModal').css("margin-left", margin);
						$('#setModal').css("width","1000px");
						$('#setModal').modal();
					}
				});
			}
		}
	}
	
	$(function() {
		$("#setToAddForm").validate({
	        submitHandler: function(form) {
				var data = toJsonString('setToAddForm');
				var url = 'rep-set-done-add-set.do';
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addSet();
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	function reviseSet(){
		if($('#setHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择数据！');
		}else{
			addSet($('#setHasAddForm .selectedItemId:checked').val());
		}
	}
	
	//删除
	function deleteSet(){
		if($('#setHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据再删除！');
		}else{
			var url = 'rep-set-done-delete-set.do?';
			$('#setHasAddForm .selectedItemId:checked').each(function(i, item){
				url += 'reportSetId=' + $(item).val() + '&';
			});
			url = url.substring(0, url.length - 1);
			$.post(url, function(data){
				if(data == 'success'){
					addSet();
				}else{
					alert('删除出错！');
				}
			});
		}
	}
	
	///////////////////////////////生成报表
	$(document).delegate('#releaseReport', 'click',function(e){
		releaseReport();
	});
	
	function releaseReport(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return;
		}else{
			var reportTemplateId = $('.selectedItem:checked').val();
			var templateType = $('#' + reportTemplateId + 'templateType').text();
			if(templateType != '报表'){
				alert('普通模板不能生成报表！');
				return;
			}else{
				var	url = 'rep-template-to-release-report.do?reportTemplateId=' + reportTemplateId;
				$.post(url, function(data){
					var reportParams = data.reportParams;
					if(reportParams == null || reportParams.length == 0){
						window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
					}else{
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check">序号</th>';
						html += '<th>参数名</th><th>参数字段</th><th>参数值</th></tr></thead><tbody>';
						$.each(reportParams, function(i, item){
							html += '<tr><td><input type="hidden" name="reportParamId" value="'+item.id+'" />' + (i + 1) + '</td>';
							html += '<td>' + item.paramName + '</td>';
							html += '<td>' + item.paramColumn + '</td>';
							html += '<td><input id="paramValue" name="paramValue" value="'+ item.defaultValue +'" class="form-control"></td>';
						});
						html += "</tbody></table>";
						$('#releaseReportToAddForm').html(html);
						var margin = (window.screen.availWidth - 1000)/2;
						$('#releaseReportModal').css("margin-left", margin);
						$('#releaseReportModal').css("width","1000px");
						$('#releaseReportModal').modal();
					}
				});
			}
		}
	}
	//保存参数
	$(function() {
		$("#releaseReportToAddForm").validate({
	        submitHandler: function(form) {
				$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
					if(data == 'success'){
						$('#releaseReportModal').modal('hide');
    					var reportTemplateId = $('.selectedItem:checked').val();
    					window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
    				}else{
    					alert('保存参数失败！');
    				}
				});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//选择同一个table中的tbody中的元素
	$(document).delegate('.selectedItemIdAll', 'click', function(){
		if($(this).attr('checked') == 'checked'){
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').attr('checked', 'checked');
				});
			});
		}else{
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').removeAttr('checked');
				});
			});
		}
	});
	
	//拼接成json数据类型
	function toJsonString(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '{';
		$.each(fields, function(i, item){
			var value = item.value.replace(/[\r\n]/g, " "); //去除所有的换行符
			if(i == 0){
   				data += '"' + item.name + '":"' + value + '"';
   			}else{
   				data += ',"' + item.name + '":"' + value + '"';
   			}
		});
		data += '}';
		return data;
	}
    </script>
  </body>

</html>
