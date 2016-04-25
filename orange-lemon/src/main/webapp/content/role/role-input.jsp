<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>编辑角色</title>
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
							<div class="caption"><i class="fa fa-user"></i>角色信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="roleForm" method="post" action="role-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">角色名称</label>
												<div class="col-md-9">
													<input id="roleName" type="text" name="roleName" value="${item.roleName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" placeholder="角色名称">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">角色Key</label>
												<div class="col-md-9">
													<input type="text" name="roleKey" value="${item.roleKey}" class="form-control required"  placeholder="角色KEY">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">是否启用</label>
												<div class="col-md-9">
													<input type="radio" name="status" value="T" ${item.status == 'T' ? 'checked' : ''}>
													启用
													<input type="radio" name="status" value="F" ${item.status == 'F' ? 'checked' : ''}>
													不启用
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">描述</label>
												<div class="col-md-9">
													<textarea id="descn" name="descn" class="form-control required" placeholder="描述">${item.descn}</textarea>
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
        $("#roleForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
                name: {
                    remote: {
                        url: 'role-check-name.do',
                        data: {
                            <c:if test="${item.id != null}">
                            id: function() {
                                return $('#id').val();
                            }
                            </c:if>
                        }
                    }
                },
                roleKey: {
                    remote: {
                        url: 'role-check-rolekey.do',
                        data: {
                            <c:if test="${item.id != null}">
                            id: function() {
                                return $('#id').val();
                            }
                            </c:if>
                        }
                    }
                }
                
            },
            messages: {
                name: {
                    remote: "存在重复!"
                },
                roleKey: {
                    remote: "存在重复!"
                }
            }
        });
    })
    </script>
  </body>

</html>
