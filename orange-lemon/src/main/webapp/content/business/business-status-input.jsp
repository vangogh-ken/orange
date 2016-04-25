<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>状态编辑</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>车辆信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessStatusForm" method="post" action="business-status-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
										  <select name="clsId" id="clsId" class="form-control">
										  	<c:forEach items="${clses}" var="cls">
										  		<option value="${cls.id}" <c:if test="${cls.id eq item.businessClass.id}">selected='selected'</c:if>>${cls.clsName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">状态</label>
										<div class="col-md-4">
											 <input id="status" type="text" name="status" value="${item.status}" 
											size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="状态">
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
        $('#businessStatusForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    </script>
  <!--  

    <div class="row-fluid">

    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">状态编辑</h4>
		</header>
		<div class="content content-inner">

<form id="businessStatusForm" method="post" action="business-status-save.do?operationMode=STORE" class="form-horizontal">
  <c:if test="${item.id != null}">
  	<input id="id" type="hidden" name="id" value="${item.id}">
  </c:if>
  
  <div class="control-group">
    <label class="control-label" for="clsId">选择Cls类型</label>
	<div class="controls">
	  <select name="clsId" id="clsId" class="input-xxxlarge">
	  	<c:forEach items="${clses}" var="cls">
	  		<option value="${cls.id}" <c:if test="${cls.id eq item.businessClass.id}">selected='selected'</c:if>>${cls.name}: ${cls.classString} </option>
	  	</c:forEach>
	  </select>
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="status">状态</label>
	<div class="controls">
	  <input id="status" name="status" type="text" value="${item.status}"/>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
      <button id="submitButton" class="btn a-submit">保存</button>
      <button type="button" onclick="history.back();" class="btn a-cancel">返回</button>
    </div>
  </div>
</form>
		</div>
      </article>

    </section>
	</div>
-->
  </body>

</html>
