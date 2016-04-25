<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>文档信息</title>
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
				  	<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-file"></i>文档信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="docInfoForm" method="post" action="doc-info-save.do?operationMode=STORE" enctype="multipart/form-data" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">文件类型</label>
										<div class="col-md-4">
										     <select name="typeId" id="typeId" class="form-control required">
											  <c:forEach items="${docTypes}" var="docType">
											  	<option value="${docType.id}" <c:if test="${item.docType.id == docType.id }">selected</c:if> >${docType.typeName}</option>
											  </c:forEach>
											 </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">永久文件</label>
										<div class="col-md-4">
											 是<input name="eternal" type="radio" value="T" <c:if test="${item.eternal == 'T'}">checked</c:if>>&nbsp;&nbsp;&nbsp;
											 否<input name="eternal" type="radio" value="F" <c:if test="${item.eternal == 'F'}">checked</c:if>>
										</div>
									</div>
									
									<c:if test="${item.id == null or item.id == ''}">
									<div class="form-group">
										<label class="control-label col-md-3">上传文件</label>
										<div class="col-md-4">
											 <input id="muiltFile" name="muiltFile" type="file" class="form-control required"/>
										</div>
									</div>
									</c:if>
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
        $('#docInfoForm').validate({
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
