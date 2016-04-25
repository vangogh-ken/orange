<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>节点配置</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>节点配置</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="bpmConfigNodeForm" method="post" action="bpm-config-node-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								<input id="pdId" name="pdId" type="hidden" value="${item.pdId}"/>
								<div class="form-body">
										**流程任务节点信息** 流程名称:${item.pdName} 任务名称：${item.tdName}
									<br><br>
									<div class="form-group">
										<label class="control-label col-md-3">任务运行时状态</label>
										<div class="col-md-4">
											<input id="sourceStatus" name="sourceStatus" type="text" value="${item.sourceStatus}" class="form-control required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">任务完成后状态</label>
										<div class="col-md-4">
											<input id="targetStatus" name="targetStatus" type="text" value="${item.targetStatus}" class="form-control required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">创建时行为</label>
										<div class="col-md-4">
											<textarea id="onCreate" name="onCreate" 
											class="form-control" <c:if test="${item.onCreate != null and item.onCreate != ''}">style="min-height:200px;"</c:if>>${item.onCreate}</textarea>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">完成时行为</label>
										<div class="col-md-4">
											<textarea id="onComplete" name="onComplete" 
											class="form-control" <c:if test="${item.onComplete != null and item.onComplete != ''}">style="min-height:200px;"</c:if>>${item.onComplete}</textarea>
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
        $('#bpmConfigNodeForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    /* $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'taskDefinitionAssignee',
    		valType:'displayName'
    	});
    });
    
    $(function() {
    	new createRolePicker({
    		modalId: 'rolePicker',
    		multiple: true,
    		url: '${ctx}/role/role-all.do',
    		valInput:'taskDefinitionCandidateGroups',
    		valType:'roleName'
    	});
    }); */
    </script>
  </body>

</html>
