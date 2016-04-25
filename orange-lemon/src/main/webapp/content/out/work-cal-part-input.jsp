<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>工作日历时间信息</title>
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
							<div class="caption"><i class="fa fa-calendar"></i>工作日历时间信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="work-cal-info-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">工作日历时间信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="workCalRuleId">日历规则</label>
												</td>
												<td>
													<select id="workCalRuleId" name="workCalRuleId" class="form-control required">
														<c:forEach items="${workCalRules}" var="workCalRule">
															<option value="${workCalRule.id}" <c:if test="${item.workCalRule.id == workCalRule.id}">selected</c:if>> ${workCalRule.name}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="shift">上午/下午</label>
												</td>
												<td>
													<select id="shift" name="shift" class="form-control required">
														<option value="0" <c:if test="${item.shift == 0}">selected</c:if>>上午</option>
														<option value="1" <c:if test="${item.shift == 1}">selected</c:if>>下午</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="startTime">开始时间</label>
												</td>
												<td>
													<select id="startTime" name="startTime" class="form-control required">
														<option value="9:00" <c:if test="${item.startTime == '9:00'}">selected</c:if>>9:00</option>
														<option value="13:00" <c:if test="${item.startTime == '13:00'}">selected</c:if>>13:00</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="endTime">结束时间</label>
												</td>
												<td>
													<select id="endTime" name="endTime" class="form-control required">
														<option value="12:00" <c:if test="${item.endTime == '12:00'}">selected</c:if>>12:00</option>
														<option value="18:00" <c:if test="${item.endTime == '18:00'}">selected</c:if>>18:00</option>
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
            errorClass: 'validate-error'
        });
    });
    
    </script>
  </body>

</html>
