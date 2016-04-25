<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>模板信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>模板信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="rep-template-save-normal.do?operationMode=STORE" enctype="multipart/form-data" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">模板信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="reportCategoryId">类别</label>
												</td>
												<td colspan="3">
													<select id="reportCategoryId" name="reportCategoryId" class="form-control required">
													<c:forEach items="${reportCategories}" var="reportCategory">
													<option value="${reportCategory.id }" <c:if test="${item.reportCategory.id == reportCategory.id }">selected</c:if>>${reportCategory.categoryName}</option>
													</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="templateType">模板类型<</label>
												</td>
												<td>
													<select id="templateType" name="templateType" class="form-control required">
														<option value="普通" <c:if test="${item.templateType == '普通' }">selected</c:if>>普通</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="templateName">模板名称</label>
												</td>
												<td>
													<input id="templateName" type="text" name="templateName" 
													value="${item.templateName}" 
													size="40" minlength="2" maxlength="128" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">上传模板文件</label>
												</td>
												<td>
													<input id="muiltFile" name="muiltFile" type="file" class="form-control required"/>
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>启用</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>禁用</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<textarea id="descn" name="descn" minlength="1" maxlength="128" style="min-height: 80px;"
													 class="form-control required">${item.descn == null ? '无' : item.descn}</textarea>
												</td>
											</tr>
										</tbody>
									</table>
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
        $("#editDomainForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
            	typeName: {
   	                remote: {
   	                    url: 'fas-invoice-type-check-typename.do',
   	                    data: {
   	                        <c:if test="${item.id != null}">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                        </c:if>
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	typeName: {
   	                remote: "存在重复"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
