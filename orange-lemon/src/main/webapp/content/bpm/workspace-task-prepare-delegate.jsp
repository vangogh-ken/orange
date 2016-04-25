<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>转办任务</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>协办任务</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="delegateForm" method="post" action="workspace-task-delegate.do?operationMode=STORE" class="form-horizontal">
								<input id="taskId" type="hidden" name="taskId" value="${param.taskId}">
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												 <label class="control-label col-md-3" style="padding-top:25px;">代理人</label>
												<!-- <div class="col-md-9">
											      <div class="input-icon userPicker">
													  <span class="add-on" style="padding:2px;cursor: pointer;"><i class="fa fa-user"></i></span>
													  <input type="text" id="attorney" name="attorney" class="form-control required">
												  </div>
												 </div> -->
												  
												 <div class="col-md-4">
													<select id="attorney" name="attorney" class="form-control required" >
													<option value=""></option>
												  	<c:forEach items="${users}" var="user">
												  		<option value="${user.id}" >${user.displayName}</option>
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
    
    /* $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'attorney',
    		valType:'displayName'
    	});
    }); */
	
	$(function() {
	    $('#delegateForm').validate({
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
		  <h4 class="title">转办任务</h4>
		</header>

		<div class="content content-inner">

<form id="delegateForm" method="post" action="workspace-task-delegate.do" class="form-horizontal">
  <input id="taskId" type="hidden" name="taskId" value="${param.taskId}">
  
  <div class="control-group">
    <label class="control-label">代理人</label>
	<div class="controls">
      <div class="input-append userPicker">
		<input style="width: 185px;" type="text" id="attorney" name="attorney" class="input-medium" value="" required="required">
		<span class="add-on" style="padding:2px;cursor: pointer;"><i class="icon-user"></i></span>
      </div>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
      <button id="submitButton" type="submit" class="btn">保存</button>&nbsp;
      <button type="button" onclick="history.back();" class="btn">返回</button>
    </div>
  </div>
</form>
        </div>
      </article>

    </section>
	</div>
	
	
	<script type="text/javascript">
	$(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'attorney',
    		valType:'displayName'
    	});
    });
	
	$(function() {
	    $('#delegateForm').validate({
	        submitHandler: function(form) {
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
	            form.submit();
	        },
	        errorClass: 'validate-error'
	    });
	});
    </script>
 -->
  </body>

</html>
