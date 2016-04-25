<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程纠正跳转</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程纠正跳转</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="jumpForm" method="post" action="console-jump.do?operationMode=STORE" class="form-horizontal">
								<input id="executionId" type="hidden" name="executionId" value="${executionId}">
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												 <label class="control-label col-md-3" >跳转至任务:</label>
												<div class="col-md-9">
											      <select name="activityId" class="form-control">
												    <c:forEach items="${activityMap}" var="item">
												    <option value="${item.key}">${item.value}</option>
													</c:forEach>
												  </select>
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
	    $('#jumpForm').validate({
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
