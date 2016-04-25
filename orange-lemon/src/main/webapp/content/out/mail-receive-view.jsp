<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>查看邮件</title>
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
							<div class="caption"><i class="fa fa-envelope"></i>查看邮件</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="mail-send-save.do?operationMode=STORE" class="form-horizontal" enctype="multipart/form-data">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-9" >
												<a class="btn btn-sm red" href="mail-reply-input.do?id=${item.id}">回复</a>
												<button type="button" class="btn btn-sm default" onclick="location.href='mail-receive-list.do'">返回</button>
											</div>
										</div>
										<div class="col-md-6">
										</div>
									</div>
								 </div>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<tbody>
											<tr>
												<td style="width: 100px;">
													<label class="td-label" for="name">收件人</label>
												</td>
												<td>
													${item.addressFrom}
												</td>
											</tr>
											<c:if test="${item.addressCopy != null and item.addressCopy !=''}">
											<tr>
												<td>
													<label class="td-label" for="name">抄送人</label>
												</td>
												<td>
													${item.addressCopy}
												</td>
											</tr>
											</c:if>
											<tr>
												<td>
													<label class="td-label" for="code">主题</label>
												</td>
												<td>
													${item.subject}
												</td>
											</tr>
											<!--  
											<tr>
												<td>
													<label class="td-label" for="code">查看附件</label>
												</td>
												<td>
													
												</td>
											</tr>
											-->
											<tr>
												<td>
													<label class="td-label" for="content">正文</label>
												</td>
												<td >
													<textarea style="min-height: 230px;width:100%;">${item.content}</textarea>
												</td>
											</tr>
										</tbody>
									</table>
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
    		multiple: true,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'receivers',
    		valType:'id',
    		display:'receiversDisplay'
    	});
    });
    
    
    function addAttachment(){
    	alert($('#attachment').val());
    }
    
    </script>
  </body>

</html>
