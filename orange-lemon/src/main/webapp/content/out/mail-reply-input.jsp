<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>回复邮件</title>
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
							<div class="caption"><i class="fa fa-envelope"></i>回复邮件</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="mail-send-to.do?operationMode=STORE" class="form-horizontal" enctype="multipart/form-data">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-9" >
												<button type="submit" class="btn btn-sm green">发送</button>
												<button type="button" class="btn btn-sm default" onclick="location.href='mail-receive-list.do'">返回</button>
											</div>
										</div>
										<div class="col-md-6">
										</div>
									</div>
								 </div>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<tbody>
											<tr>
												<td style="width: 100px;">
													<label class="td-label" for="name">收件人</label>
												</td>
												<td >
													<input type="text" id="addressTo" name="addressTo" value="${replyMail.addressFrom}" class="form-control required">
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="code">主题</label>
												</td>
												<td>
													<input type="text" id="subject" name="subject" value="RE:${replyMail.subject}" class="form-control required">
												</td>
											</tr>
											<!--  
											<tr>
												<td>
													<label class="td-label" for="code">添加附件</label>
												</td>
												<td>
													<input id="attachment" type="file" name="attachment" class="form-control required" onchange="addAttachment();">
												</td>
											</tr>
											-->
											<tr>
												<td>
													<label class="td-label" for="content">正文</label>
												</td>
												<td >
													<textarea id="content" name="content" class="form-control required"
														style="min-height:250px;">
														<br>
														<hr>
														${replyMail.subject}
													</textarea>
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
    
    var editor = CKEDITOR.replace('content');
	// editorObj.config.filebrowserImageUploadUrl = dir + "core/connector/" + ckfinder.ConnectorLanguage + "/connector." + ckfinder.ConnectorLanguage + "?command=QuickUpload&type=" + ( imageType || 'Images' ) ;
	editor.config.filebrowserImageUploadUrl = "${ctx}/cms/cms-article-image-upload.do";
    
    </script>
  </body>

</html>
