<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>控制信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>控制信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessControllerForm" method="post" action="business-controller-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">名称</label>
										<div class="col-md-4">
											<input id="name" type="text" name="name" value="${item.name}" 
											size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="名称">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
											  <select name="clsId" id="clsId" class="form-control">
											  	<c:forEach items="${clses}" var="cls">
											  		<option value="${cls.id}" <c:if test="${item.businessClass.id eq cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">过滤条件</label>
										<div class="col-md-4">
											<input id="filter" name="filter" type="text" value="${item.filter}" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">读写权限</label>
										<div class="col-md-4">
											  只读<input name="controlDetails" type="radio" value="read" <c:if test="${item.controlDetails =='read'}">checked="checked"</c:if> />
											  默认<input name="controlDetails" type="radio" value="normal" <c:if test="${item.controlDetails =='normal'}">checked="checked"</c:if> />
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
        $('#businessControllerForm').validate({
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
