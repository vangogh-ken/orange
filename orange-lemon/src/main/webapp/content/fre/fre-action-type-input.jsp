<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>动作类型信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>动作类型信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-action-type-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">动作类型信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="typeName">类型名称</label>
												</td>
												<td>
													<input id="typeName" type="text" name="typeName" value="${item.typeName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												
												<td>
													<label class="td-label" for="freightDelegateTemplateId">委托模板</label>
												</td>
												<td>
													<select id="freightDelegateTemplateId" name="freightDelegateTemplateId" class="form-control ">
														<option value="">无</option>
														<c:forEach items="${delegateTemplates}" var="delegateTemplate">
															<option value="${delegateTemplate.id}"<c:if test="${item.freightDelegateTemplate.id == delegateTemplate.id}">selected</c:if> >${delegateTemplate.templateName}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="delegate">是否发送委托</label>
												</td>
												<td>
													<select id="delegate" name="delegate" class="form-control required">
														<option value="T"<c:if test="${item.delegate == 'T'}">selected</c:if> >是</option>
														<option value="F"<c:if test="${item.delegate == 'F'}">selected</c:if> >否</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="internal">是否对内</label>
												</td>
												<td>
													<select id="internal" name="internal" class="form-control required">
														<option value="T"<c:if test="${item.internal == 'T'}">selected</c:if> >是</option>
														<option value="F"<c:if test="${item.internal == 'F'}">selected</c:if> >否</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="prime">是否有填报界面</label>
												</td>
												<td>
													<select id="prime" name="prime" class="form-control required">
														<option value="T"<c:if test="${item.prime == 'T'}">selected</c:if> >是</option>
														<option value="F"<c:if test="${item.prime == 'F'}">selected</c:if> >否</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T"<c:if test="${item.status == 'T'}">selected</c:if> >已启用</option>
														<option value="F"<c:if test="${item.status == 'F'}">selected</c:if> >已停用</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<input id="descn" type="text" name="descn" value="${item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
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
   	                    url: 'fre-action-type-check-typename.do',
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
   	                remote: "该类型已存在"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
