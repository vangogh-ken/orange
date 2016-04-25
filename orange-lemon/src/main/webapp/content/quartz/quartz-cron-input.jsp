<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>调度规则信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>调度规则信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="quartz-cron-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">调度规则信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="cronName">规则名称</label>
												</td>
												<td>
													<input id="cronName" type="text" name="cronName" value="${item.cronName}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="cronExpression">表达式</label>
												</td>
												<td>
													<input id="cronExpression" type="text" name="cronExpression" value="${item.cronExpression}" 
													size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
													vAttrId="36803193-c051-11e5-8832-b870f47f73d5">
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn == null ? '无' : item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>是</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>否</option>
													</select>
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
            	cronName: {
   	                remote: {
   	                    url: 'quartz-cron-check-cron-name.do',
   	                    data: {
   	                        <c:if test="${item.id != null}">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                        </c:if>
   	                    }
   	                }
   	            },
   	         	cronExpression: {
	                remote: {
	                    url: 'quartz-cron-check-cron-expression.do',
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
   	        	cronName: {
   	                remote: "存在重复"
   	            },
   	         	cronExpression: {
	                remote: "重复或错误的表达式"
	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
