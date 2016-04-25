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
						<c:forEach items="${reportCategories}" var="category">
						<c:if test="${catagoryIds.contains(category.id)}">
							<div class="row" style="margin-bottom: -20px;">
								<div class="col-md-11 page-breadcrumb breadcrumb" style="width:97%;margin-left: 20px;height:40px;margin-bottom: 10px;">
									<h4 style="font-family: 宋体;font-weight: 200;margin-top: 0px;padding-top: 5px;">
									&nbsp;<span class="fa fa-bookmark" style="font-size: 16px;line-height: 16px;"></span><strong>&nbsp;${category.categoryName}&nbsp;</strong><small>${category.descn}</small>
									</h4>
								</div>
							</div>
							<hr>
							<div class="row">
								<c:set var="varStatus" value="0"></c:set>
								<c:forEach items="${reportTemplates}" var="reportTemplate" >
								<c:if test="${reportTemplate.reportCategory.id == category.id}">
										<div class="col-md-2" style="left: 30px;">
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
										<c:set var="varStatus" value="${varStatus + 1}"></c:set>
										<c:if test="${varStatus%4 == 0 and varStatus != 0}">
										<br><br><br><br>
										<hr>
										</c:if>
									</c:if>
								</c:forEach>
							</div>
							<hr style="margin-bottom:-10px;">
							</c:if>
							</c:forEach>
						</div>
					   </div>
				    </div>
				</div>
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
				<button type="button" class="btn btn-sm red" onclick="down()">下载</button>
				<button type="button" class="btn btn-sm red" onclick="browse()">预览</button>
				<button id="graphBtn" style="display:none;" type="button" class="btn btn-sm green" onclick="graph()">图形</button>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="releaseReportModal2" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">生成报表</h4>
			</div>
			<form id="releaseReportToAddForm2" method="post" class="m-form-blank">
			<input type="hidden" id="reportTemplateId" name="reportTemplateId" value="">
			</form>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="downNoneParams()">下载</button>
				<button type="button" class="btn btn-sm red" onclick="browseNoneParams()">预览</button>
				<button type="button" class="btn btn-sm red" onclick="browseNoneParams()">预览</button>
			</div>
		</div>
	</div>
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
	$(function() {
	    App.init(); 
	});

	///////////////////////////////生成报表
	function releaseReport(reportTemplateId){
		var	url = 'rep-template-to-release-report.do?reportTemplateId=' + reportTemplateId;
		$.post(url, function(data){
			var reportParams = data.reportParams;
			var reportTemplate = data.reportTemplate;
			if(reportParams == null || reportParams.length == 0){
				//无参数报表也可预览和下载
				$('#releaseReportToAddForm2 #reportTemplateId').val(reportTemplateId);
				var margin = (window.screen.availWidth - 600)/2;
				$('#releaseReportModal2').css("margin-left", margin);
				$('#releaseReportModal2').css("margin-top", 200);
				$('#releaseReportModal2').css("width","600px");
				$('#releaseReportModal2').modal();
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
				if(reportTemplate.graphUrl != null && reportTemplate.graphUrl.indexOf('/content/rep-graph') > -1){
					$('#releaseReportModal #graphBtn').show();
				}else{
					$('#releaseReportModal #graphBtn').hide();
				}
				initDatePicker();
				var margin = (window.screen.availWidth - 800)/2;
				$('#releaseReportModal').css("margin-left", margin);
				$('#releaseReportModal').css("margin-top", 150);
				$('#releaseReportModal').css("width","800px");
				$('#releaseReportModal').modal();
			}
		});
	}
	
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
			$('#releaseReportModal').modal('hide');
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
				if(data == 'success'){
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
	
	//图形报表
	function graph(){
		if($("#releaseReportToAddForm").validate()){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('rep-param-set-param-value.do', $('#releaseReportToAddForm').serialize(), function(data){
				if(data == 'success'){
					$('#releaseReportModal').modal('hide');
					var reportTemplateId = $('#releaseReportToAddForm #reportTemplateId').val();
					window.open('rep-template-graph-relese-report.do?reportTemplateId=' + reportTemplateId);
					box.modal('hide');
				}else{
					alert('保存参数失败！');
					box.modal('hide');
				}
			});
		}
	}
	
	//无参数
	function downNoneParams(){
		$('#releaseReportModal2').modal('hide');
		var reportTemplateId = $('#releaseReportToAddForm2 #reportTemplateId').val();
		bootbox.animate(false);
		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		window.open('rep-template-done-relese-report.do?reportTemplateId=' + reportTemplateId);
		box.modal('hide');
	}
	
	function browseNoneParams(){
		$('#releaseReportModal2').modal('hide');
		var reportTemplateId = $('#releaseReportToAddForm2 #reportTemplateId').val();
		bootbox.animate(false);
		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('rep-template-browse-relese-report.do?reportTemplateId=' + reportTemplateId, function(data){
			window.open('/VC/convert?fileName=' + data);
			box.modal('hide');
		});
	}
	
    </script>
  </body>

</html>
