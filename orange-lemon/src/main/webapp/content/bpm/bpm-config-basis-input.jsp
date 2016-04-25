<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>BPM流程信封配置</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>BPM流程信封配置</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="bpmConfigBusinessForm" method="post" action="bpm-config-basis-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">流程Key</label>
										<div class="col-md-4">
										  <select name="bpmKey" id="bpmKey" class="form-control required">
										  	<c:forEach items="${keys}" var="key">
										  		<option value="${key}" <c:if test="${item.bpmKey eq key}">selected="selected"</c:if>>${key}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">填报页面</label>
										<div class="col-md-4">
											<input id="configPrimeUrl" name="configPrimeUrl" class="form-control" type="text" value="${item.configPrimeUrl}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">管理页面</label>
										<div class="col-md-4">
											<input id="configManageUrl" name="configManageUrl" class="form-control" type="text" value="${item.configManageUrl}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">流程类别</label>
										<div class="col-md-4">
										  <select name="categoryId" id="categoryId" class="form-control required" >
										  	<option></option>
										  	<c:forEach items="${categories}" var="category">
										  		<option value="${category.id}" <c:if test="${item.bpmConfigCategory.id eq category.id}">selected="selected"</c:if>>${category.categoryName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">类型</label>
										<div class="col-md-4">
										  <select name="basisSubstanceTypeId" id="basisSubstanceTypeId" class="form-control required" onchange="updateCls();">
										  	<option value=""></option>
										  	<c:forEach items="${basisSubstanceTypes}" var="basisSubstanceType">
										  		<option value="${basisSubstanceType.id}" <c:if test="${item.basisSubstanceType.id eq basisSubstanceType.id}">selected="selected"</c:if>>${basisSubstanceType.typeName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">应用</label>
										<div class="col-md-4">
										  <select name="basisApplicationId" id="basisApplicationId" class="form-control required">
										  	<c:forEach items="${basisApplications}" var="basisApplication">
										  		<option value="${basisApplication.id}" clsId="${basisApplication.basisSubstanceType.id}" <c:if test="${item.basisApplication.id eq basisApplication.id}">selected="selected"</c:if>> ${basisApplication.basisSubstanceType.typeName}: ${basisApplication.applicationName}</option>
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
        $('#bpmConfigBusinessForm').validate({
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
