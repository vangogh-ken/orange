<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>修改密码</title>
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
							<div class="caption"><i class="fa fa-unlock-alt"></i>修改密码</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="userForm" method="post" action="change-password-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">原密码</label>
										<div class="col-md-4">
											<input id="oldPassword" name="oldPassword" type="password" value="" class="form-control required" maxlength="20">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">新密码</label>
										<div class="col-md-4">
											<input id="newPassword" name="newPassword" type="password" value="" class="form-control required" maxlength="20">
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">密码确认</label>
										<div class="col-md-4">
											<input id="confirmPassword" name="confirmPassword" type="password" value="" equalTo="#newPassword" class="form-control required">
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
        $("#userForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    })
    
    </script>
  </body>

</html>
