<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>薪等信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>薪等信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="salary-grade-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">薪等信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="quartzTaskId">用户</label>
												</td>
												<td>
													<select id="userId" name="userId" class="form-control required">
														<c:forEach items="${user}" var="users">
														<option value="${user.id}" <c:if test="${item.user.id == user.id}">selected</c:if>>${user.displayName}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="gradeCount">等级</label>
												</td>
												<td>
													<input id="gradeCount" type="text" name="gradeCount" value="${item.gradeCount}"
													size="40" minlength="2" maxlength="50" class="form-control number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="basicSalary">基础工资</label>
												</td>
												<td>
													<input id="basicSalary" type="text" name="basicSalary" value="${item.basicSalary}"
													size="40" minlength="2" maxlength="50" class="form-control number" >
												</td>
												<td>
													<label class="td-label" for="postSalary">岗位工资</label>
												</td>
												<td>
													<input id="postSalary" type="text" name="postSalary" value="${item.postSalary}"
													size="40" minlength="2" maxlength="50" class="form-control number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="startTime">开始时间</label>
												</td>
												<td>
													<input id="startTime" type="text" name="startTime" 
													value="<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
													size="40" minlength="2" maxlength="50" class="form-control datepicker" >
												</td>
												<td>
													<label class="td-label" for="endTime">结束时间</label>
												</td>
												<td>
													<input id="endTime" type="text" name="endTime" value="<fmt:formatDate 
													value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
													size="40" minlength="2" maxlength="50" class="form-control datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<textarea id="descn" name="descn" class="form-control required" maxlength="256" style="min-height: 80px;">${item.descn == null ? '无' : item.descn }</textarea>
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
            	currency: {
   	                remote: {
   	                    url: 'salary-grade-check-starttime.do',
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
   	        	currency: {
   	                remote: "存在重复"
   	            }
   	        }
   	        
        });
    });
    
    </script>
  </body>

</html>
