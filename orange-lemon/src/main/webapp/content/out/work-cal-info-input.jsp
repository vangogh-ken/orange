<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>日程信息</title>
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
							<div class="caption"><i class="fa fa-calendar"></i>日程信息</div>
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
											<tr><th colspan="6">日程信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="type">类型</label>
												</td>
												<td>
													日程<input type="radio" name="type" value="RC" 
													<c:if test="${item.type == 'RC'}">checked</c:if> >
													计划<input type="radio" name="type" value="JH"
													<c:if test="${item.type == 'RC'}">checked</c:if> >
													日志<input type="radio" name="type" value="RZ"
													<c:if test="${item.type == 'RC'}">checked</c:if> >
												</td>
												<td>
													<label class="td-label" for="title">标题</label>
												</td>
												<td>
													<input id="title" type="text" name="title" value="${item.title}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="startTime">开始时间</label>
												</td>
												<td>
													<input id="startTime" type="text" name="startTime" value="<c:if test="${item.startTime != null}"><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if>" 
													size="40" minlength="2" maxlength="50" class="form-control datepicker required" >
												</td>
												
												<td>
													<label class="td-label" for="endTime">结束时间</label>
												</td>
												<td>
													<input id="endTime" type="text" name="endTime" value="<c:if test="${item.endTime != null}"><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if>" 
													size="40" minlength="2" maxlength="50" class="form-control datepicker required" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="priority">优先级</label>
												</td>
												<td>
													<input id="priority" type="text" name="priority" value="${item.priority}" 
													size="40" minlength="1" maxlength="50" class="form-control number required" >
												</td>
												<td>
													<label class="td-label" for="alertTime">提醒次数</label>
												</td>
												<td>
													<input id="alertTime" type="text" name="alertTime" value="${item.alertTime}" 
													size="40" minlength="1" maxlength="50" class="form-control number required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="content">内容</label>
												</td>
												<td colspan="3">
													<textarea id="content" name="content" class="form-control required"
														style="width:91%;">${item.content}</textarea>
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
    
    $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'uphold',
    		valType:'displayName'
    	});
    });
    </script>
  </body>

</html>
