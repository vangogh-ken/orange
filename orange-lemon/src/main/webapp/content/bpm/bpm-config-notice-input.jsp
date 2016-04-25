<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>任务通知信息</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>任务通知信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="bpmConfigNoticeForm" method="post" action="bpm-config-notice-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">通知类型</label>
										<div class="col-md-4">
											<select id="type" name="type" class="form-control required">
										  		<option value="TYPE_ARRIVAL" <c:if test="${item.type == 'TYPE_ARRIVAL'}">selected="selected"</c:if>>任务到达</option>
										  		<option value="TYPE_COMPLETE" <c:if test="${item.type == 'TYPE_COMPLETE'}">selected="selected"</c:if>>任务完成</option>
										  		<option value="TYPE_TIMEOUT" <c:if test="${item.type == 'TYPE_TIMEOUT'}">selected="selected"</c:if>>任务超时</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">接收人</label>
										<div class="col-md-4">
											<input id="receiver" name="receiver" type="text" value="${item.receiver}"  class="form-control required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">持续时间</label>
										<div class="col-md-4">
											<input id="dueDate" name="dueDate" type="text" value="${item.dueDate}" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">流程节点</label>
										<div class="col-md-4">
											<select id="nodeId" name="nodeId" class="form-control required">
											  	<c:forEach items="${nodes}" var="node">
											  		<option value="${node.id}" <c:if test="${item.bpmConfigNode.id == node.id}">selected="selected"</c:if>>${node.pdName} : ${node.tdName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">邮件模板</label>
										<div class="col-md-4">
											<select id="templateId" name="templateId" class="form-control required">
											  	<c:forEach items="${templates}" var="template">
											  		<option value="${template.id}" <c:if test="${item.bpmMailTemplate.id == template.id}">selected="selected"</c:if>>${template.templateName}</option>
											  	</c:forEach>
											  </select>
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
        $('#bpmConfigNoticeForm').validate({
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
