<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>BPM可用操作信息</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>BPM可用操作信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="bpmOperationTypeForm" method="post" action="bpm-operation-type-save.do?operationMode=STORE" class="form-horizontal">
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
										<label class="control-label col-md-3">类型</label>
										<div class="col-md-4">
											<select id="type" name="type" class="form-control required">
											  	<option value="userTask" <c:if test="${item.type == 'userTask'}">selected='selected'</c:if>>用户任务</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">请求地址</label>
										<div class="col-md-4">
											<select id="url" name="url" class="form-control required">
											  	<option value="workspace-task-handle-input.do" 
											  		<c:if test="${item.url == 'workspace-task-handle-input.do'}">selected='selected'</c:if>>处理任务</option>
											  		
											  	<option value="workspace-task-prepare-delegate.do" 
											  		<c:if test="${item.url == 'workspace-task-prepare-delegate.do'}">selected='selected'</c:if>>转办任务</option>
											  		
											  	<option value="workspace-task-prepare-assist.do" 
											  		<c:if test="${item.url == 'workspace-task-prepare-assist.do'}">selected='selected'</c:if>>协办任务</option>
											  		
											  	<option value="workspace-task-rollback.do" 
											  		<c:if test="${item.url == 'workspace-task-rollback.do'}">selected='selected'</c:if>>驳回任务</option>
											  		
											  	<option value="workspace-change-sign.do" 
											  		<c:if test="${item.url == 'workspace-change-sign.do'}">selected='selected'</c:if>>加减签任务</option>
											  		
											  	<option value="workspace-task-view-business-bytask.do" 
											  		<c:if test="${item.url == 'workspace-task-view-business-bytask.do'}">selected='selected'</c:if>>查看业务数据</option>
											  		
											  	<option value="other" 
											  		<c:if test="${item.url == 'other'}">selected='selected'</c:if>>其他特殊地址</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">是否新建页面</label>
										<div class="col-md-4">
											<select id="type" name="blank" class="form-control required">
											  	<option value="T" <c:if test="${item.blank == 'T'}">selected='selected'</c:if>>是</option>
											  	<option value="F" <c:if test="${item.blank == 'F'}">selected='selected'</c:if>>否</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">说明</label>
										<div class="col-md-4">
											<textarea id="description" name="description" class="form-control required">${item.description}</textarea>
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
        $('#bpmOperationTypeForm').validate({
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
		  <h4 class="title">BPM节点可用操作编辑</h4>
		</header>
		<div class="content content-inner">

<form id="bpmOperationTypeForm" method="post" action="bpm-operation-type-save.do?operationMode=STORE" class="form-horizontal">
  <c:if test="${item.id != null}">
  	<input id="id" type="hidden" name="id" value="${item.id}">
  </c:if>
  
  <div class="control-group">
    <label class="control-label" for="name">操作名称</label>
	<div class="controls">
	  <input id="name" name="name" type="text" value="${item.name}" class="text required"/>
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="type">使用类型</label>
	<div class="controls">
	  <select id="type" name="type" class="text required">
	  	<option value="userTask" <c:if test="${item.type == 'userTask'}">selected='selected'</c:if>>用户任务</option>
	  </select>
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="url">请求地址</label>
	<div class="controls">
	  <select id="url" name="url" class="text required">
	  	<option value="workspace-task-handle-input.do" 
	  		<c:if test="${item.url == 'workspace-task-handle-input.do'}">selected='selected'</c:if>>处理任务</option>
	  		
	  	<option value="workspace-task-prepare-delegate.do" 
	  		<c:if test="${item.url == 'workspace-task-prepare-delegate.do'}">selected='selected'</c:if>>转办任务</option>
	  		
	  	<option value="workspace-task-prepare-assist.do" 
	  		<c:if test="${item.url == 'workspace-task-prepare-assist.do'}">selected='selected'</c:if>>协办任务</option>
	  		
	  	<option value="workspace-task-rollback.do" 
	  		<c:if test="${item.url == 'workspace-task-rollback.do'}">selected='selected'</c:if>>驳回任务</option>
	  		
	  	<option value="workspace-change-sign.do" 
	  		<c:if test="${item.url == 'workspace-change-sign.do.do'}">selected='selected'</c:if>>加减签任务</option>
	  </select>
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="description">说明</label>
	<div class="controls">
	  <textarea id="description" name="description" class="text required">${item.description}</textarea>
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
