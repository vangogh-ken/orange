<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>工作日历规则信息</title>
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
							<div class="caption"><i class="fa fa-calendar"></i>工作日历规则信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="work-cal-rule-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">工作日历规则信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="workCalType">规则类型</label>
												</td>
												<td>
													<select id="workCalTypeId" name="workCalTypeId" class="form-control required">
														<c:forEach items="${workCalTypes}" var="workCalType">
															<option value="${workCalType.id}" <c:if test="${item.workCalType.id == workCalType.id}">selected</c:if>> ${workCalType.name}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="name">名称</label>
												</td>
												<td>
													<input id="name" type="text" name="name" value="${item.name}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="year">年</label>
												</td>
												<td>
													<input id="year" type="text" name="priority" value="${item.year}" 
													size="40" minlength="1" maxlength="50" class="form-control number required" >
												</td>
												<td>
													<label class="td-label" for="week">周</label>
												</td>
												<td>
													<input id="week" type="text" name="week" value="${item.week}" 
													size="40" minlength="1" maxlength="50" class="form-control number required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="workDate">工作时间</label>
												</td>
												<td>
													<input id="workDate" type="text" name="workDate" value="<fmt:formatDate value="${item.workDate}" pattern="yyyy-MM-dd"/>" 
													size="40" minlength="2" maxlength="50" class="form-control datepicker required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="0" <c:if test="${item.status == 0}">selected</c:if>>工作周</option>
														<option value="1" <c:if test="${item.status == 1}">selected</c:if>>假期</option>
														<option value="2" <c:if test="${item.status == 2}">selected</c:if>>假期调为工作日</option>
														<option value="3" <c:if test="${item.status == 3}">selected</c:if>>工作日调为假期</option>
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
