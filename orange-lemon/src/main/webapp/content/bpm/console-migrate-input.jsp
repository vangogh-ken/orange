<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程数据迁移</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程数据迁移</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editForm" method="post" action="console-migrate-save.do?operationMode=STORE" class="form-horizontal">
								<input id="processInstanceId" type="hidden" name="processInstanceId" value="${processInstanceId}">
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												 <label class="control-label col-md-3" >选择流程定义:</label>
												<div class="col-md-9">
											      <select id="processDefinitionId" name="processDefinitionId" class="form-control">
												    <c:forEach items="${processDefinitions}" var="item">
												    <option value="${item.id}">${item.name}:${item.key}:V${item.version}</option>
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
	    $('#editForm').validate({
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

    <section id="m-main" class="span10" style="float:right">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">迁移</h4>
		</header>

		<div class="content content-inner">

<form id="demoForm" method="post" action="console-migrateSave.do" class="form-horizontal">
  <input id="demo_id" type="hidden" name="processInstanceId" value="${processInstanceId}">
  <div class="control-group">
    <label class="control-label">流程定义</label>
	<div class="controls">
	  <select name="processDefinitionId">
	    <c:forEach items="${processDefinitions}" var="item">
	    <option value="${item.id}">${item.id}</option>
		</c:forEach>
	  </select>
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button id="submitButton" type="submit" class="btn">保存</button>
	  &nbsp;
      <button type="button" onclick="history.back();" class="btn">返回</button>
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
