<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>菜单信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>菜单信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessMenuForm" method="post" action="business-menu-save.do?operationMode=STORE" class="form-horizontal">
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
										<label class="control-label col-md-3">资源Key</label>
										<div class="col-md-4">
											<input id="resourceKey" name="resourceKey" type="text" value="${item.resourceKey}" 
												class="form-control text required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
										  <select name="clsId" id="clsId" class="form-control required" onchange="updateCls();">
										  	<option></option>
										  	<c:forEach items="${clses}" var="cls">
										  		<option value="${cls.id}" <c:if test="${cls.id eq item.businessClass.id}">selected='selected'</c:if>>${cls.clsName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">选择控制</label>
										<div class="col-md-4">
										  <select name="controllerId" id="controllerId" class="form-control required">
										  	<c:forEach items="${controllers}" var="controller">
										  		<option value="${controller.id}" clsId="${controller.businessClass.id}" <c:if test="${controller.id eq item.businessController.id}">selected='selected'</c:if>>${controller.name}:${controller.businessClass.clsName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">上级资源</label>
										<div class="col-md-4">
										  <select name="resourceId" id="resourceId" class="form-control required">
										  	<c:forEach items="${reses}" var="resource">
										  	<c:if test="${resource.type == '目录'}">
										  		<option value="${resource.id}" <c:if test="${resource.id eq item.parentResource.id}">selected='selected'</c:if>>${resource.type}:${resource.name}</option>
										  	</c:if>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">管理页面</label>
										<div class="col-md-4">
											<input id="listUrl" name="listUrl" type="text" value="${item.listUrl}" 
												class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">填报页面</label>
										<div class="col-md-4">
											<input id="inputUrl" name="inputUrl" type="text" value="${item.inputUrl}" 
												class="form-control"/>
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
        $('#businessMenuForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    function updateCls(){
    	var clsId = $('#clsId').val();
  
    	if(clsId != ''){
    		$('#controllerId option').each(function(i, item){
    			if($(item).attr('clsId') != clsId){
    				$('#controllerId').val('');
    				$(item).css('display', 'none');
    			}else{
    				$(item).css('display', 'block');
    			}
    		});
    	}
    }
    </script>
  </body>

</html>
