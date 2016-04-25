<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>报表管理(查询)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>报表管理(查询)</div>
						</div>
						<div class="portlet-body ">
							<div class="row" style="margin-bottom: -20px;">
								<div class="col-md-11 page-breadcrumb breadcrumb" style="width:97%;margin-left: 20px;height:40px;margin-bottom: 10px;">
									<h4 style="font-family: 宋体;font-weight: 200;margin-top: 0px;padding-top: 5px;">
									&nbsp;<span class="fa fa-bookmark"></span>报表&nbsp;<small>动态生成报表</small>
									</h4>
								</div>
							</div>
							<hr>
							<div class="row">
								<c:forEach items="${reportTemplates}" var="reportTemplate" varStatus="varStatus" >
										<div class="col-md-3" style="left: 30px;">
							        	<div class="dashboard-stat white" style="border: solid grey 1px;">
											<div class="visual">
												<i class="fa fa-bar-chart-o"></i>
											</div>
											<div class="details">
												<div class="number" style="font-size: 14px;">
														${reportTemplate.templateName}
												</div>
												<div class="desc">
													<a href="javascript:void(0);" onclick="releaseReport('${reportTemplate.id}')">生成报表</a>
												</div>
											</div>
											<a class="more" href="javascript:void(0);">
												 模板 <i class="m-icon-swapright m-icon-white"></i>
											</a>
										</div>
										</div>
										
										<c:if test="${(varStatus.index + 1)%3 == 0 and varStatus.index != 0}">
										<br><br><br><br>
										<hr>
										</c:if>
								</c:forEach>
							</div>
							<hr style="margin-bottom:-10px;">
						</div>
					   </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 数据源
	<div class="modal fade" id="dataSourceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加数据源</h4>
			</div>
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
			
				<article class="m-widget">
				<form id="dataSourceHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	-->
	<!-- 参数
	<div class="modal fade" id="paramModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加参数</h4>
			</div>
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
			
				<article class="m-widget">
				<form id="paramHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	-->
	<!-- 设置
	<div class="modal fade" id="setModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">添加设置</h4>
			</div>
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
			
				<article class="m-widget">
				<form id="setHasAddForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	-->
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
				<button type="button" class="btn btn-sm red" onclick="down()">下载</button>
				
				<button type="button" class="btn btn-sm red" onclick="browse()">预览</button>
			</div>
		</div>
	</div>
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
	$(function() {
	    App.init(); 
	});
	
	/////////////////////////////////////////添加数据源
	/**
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
					html += '<td><input id="defaultValue" name="defaultValue" class="form-control"></td>';
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
	**/
	///////////////////////////////生成报表
	function releaseReport(reportTemplateId){
		var	url = 'rep-template-to-release-report.do?reportTemplateId=' + reportTemplateId;
		$.post(url, function(data){
			var reportParams = data.reportParams;
			if(reportParams == null || reportParams.length == 0){
				window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
			}else{
				var html = '<input type="hidden" id="reportTemplateId" name="reportTemplateId" value="'+ reportTemplateId +'" />';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th width="10" class="m-table-check">序号</th>';
				html += '<th>参数名</th><th>参数字段</th><th>参数值</th></tr></thead><tbody>';
				$.each(reportParams, function(i, item){
					html += '<tr><td><input type="hidden" name="reportParamId" value="'+item.id+'" />' + (i + 1) + '</td>';
					html += '<td>' + item.paramName + '</td>';
					html += '<td>' + item.paramColumn + '</td>';
					html += '<td><input id="paramValue'+ item.paramColumn +'" name="paramValue" value="'+ item.defaultValue +'" class="form-control';
					if(item.required == 'T'){
						html += ' required '; 
					}
					if(item.paramType == 'INT' || item.paramType == 'DOUBLE'){
						html += ' number '; 
					}else if(item.paramType == 'DATETIME'){
						html += ' datepicker '; 
					}
					
					if(item.canSelect == 'T'){
						html += ' dictionary "'; 
						if(item.vAttrId != null){
							html += ' vAttrId="'+ item.vAttrId +'" ';
						}
						
						if(item.vClsId != null){
							html += ' vClsId="'+ item.vClsId +'" ';
						}
						
						if(item.vColumn != null){
							html += ' vColumn="'+ item.vColumn +'" ';
						}
						
						if(item.vFilterId != null){
							html += ' vFilterId="'+ item.vFilterId +'" ';
						}
						
						html += '>';
					}else{
						html += '">';
					}
					html += '</td>';
				});
				html += "</tbody></table>";
				$('#releaseReportToAddForm').html(html);
				initDatePicker();
				var margin = (window.screen.availWidth - 1000)/2;
				$('#releaseReportModal').css("margin-left", margin);
				$('#releaseReportModal').css("width","1000px");
				$('#releaseReportModal').modal();
			}
		});
	}
	//保存参数
	/* $(function() {
		$("#releaseReportToAddForm").validate({
	        submitHandler: function(form) {
				$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
					if(data == 'success'){
						$('#releaseReportModal').modal('hide');
    					var reportTemplateId = $('#releaseReportToAddForm #reportTemplateId').val();
    					window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
    				}else{
    					alert('保存参数失败！');
    				}
				});
	        },
	        errorClass: 'validate-error'
		});
	}); */
	
	function down(){
		if($("#releaseReportToAddForm").validate()){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
				if(data == 'success'){
					$('#releaseReportModal').modal('hide');
					var reportTemplateId = $('#releaseReportToAddForm #reportTemplateId').val();
					window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
				}else{
					alert('保存参数失败！');
				}
				
				box.modal('hide');
			});
		}
	}
	
	function browse(){
		if($("#releaseReportToAddForm").validate()){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
				if(data == 'success'){
					$('#releaseReportModal').modal('hide');
					var reportTemplateId = $('#releaseReportToAddForm #reportTemplateId').val();
					$.post('rep-template-browse-relese-report.do?reportTemplateId=' + reportTemplateId, function(data){
						window.open('/VC/convert?fileName=' + data);
						box.modal('hide');
					});
				}else{
					alert('保存参数失败！');
					box.modal('hide');
				}
			});
		}
	}
    </script>
  </body>

</html>
