<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>报表信息</title>
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
							<div class="caption"><i class="fa fa-bar-chart-o"></i>报表信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="reportConfigForm" method="post" action="report-config-save.do?operationMode=STORE" class="form-horizontal" >
								<c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">名称</label>
												<div class="col-md-9">
													<input id="name" type="text" name="name" value="${item.name}" size="40" minlength="2" maxlength="50" class="form-control required" placeholder="用户名">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">报表key</label>
												<div class="col-md-9">
													<input id="reportKey" type="text" name="reportKey" value="${item.reportKey}" class="form-control required" placeholder="姓名">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">类型</label>
												<div class="col-md-9">
													<select id="type" name="type" class="form-control required">
											    		<option value="bar" ${item.type == 'bar' ? 'selected' : ''}>基本条形图</option>
											    		<option value="column" ${item.type == 'column' ? 'selected' : ''}>基本柱形图</option>
											    		<option value="donut" ${item.type == 'donut' ? 'selected' : ''}>基本圆饼图</option>
											    		<option value="pie" ${item.type == 'pie' ? 'selected' : ''}>基本圆形图</option>
												    </select>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">标题</label>
												<div class="col-md-9">
													<input id="title" type="text" name="title" value="${item.title}" class="form-control required" placeholder="标题">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">数据起始</label>
												<div class="col-md-9">
													<input id="start" name="start" type="text" value="${item.start}" class="form-control required"/>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">数据临界</label>
												<div class="col-md-9">
													<input id="end" name="end" type="text" value="${item.end}" class="form-control required"/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">数据步进</label>
												<div class="col-md-9">
													<input id="step" name="step" type="text" value="${item.step}" class="form-control required"/>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">数据单位</label>
												<div class="col-md-9">
													<input id="unit" name="unit" type="text" value="${item.unit}" class="form-control required"/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">界面宽度</label>
												<div class="col-md-9">
													<input id="width" name="width" type="text" value="${item.width}" class="form-control required"/>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">界面高度</label>
												<div class="col-md-9">
													<input id="height" name="height" type="text" value="${item.height}" class="form-control required"/>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">数据源</label>
												<div class="col-md-9">
													<textarea id="dataSql" name="dataSql" class="form-control required">${item.dataSql}</textarea>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-offset-6 col-md-9">
												<button type="submit" class="btn green">保存</button>
												<button type="button" class="btn default" onclick="history.back();">返回</button>
											</div>
										</div>
										<div class="col-md-6">
										</div>
									</div>
								</div>
							</form>
							<!-- END FORM-->
							</div>
					   </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
    
    <%@include file="/common/footer.jsp"%>
    <script type="text/javascript">
    $(function() {
        App.init();
    });
    
    $(function() {
        $('#reportConfigForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
        
    </script>
  </body>

</html>
