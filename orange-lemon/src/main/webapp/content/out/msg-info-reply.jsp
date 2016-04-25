<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>回复消息</title>
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
							<div class="caption"><i class="fa fa-comments"></i>回复消息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="msgInfoForm" method="post" action="msg-info-reply-save.do?operationMode=STORE" class="form-horizontal">
								<input name="replayMsgId" type="hidden" value="${msgInfo.id}"/>
								<input name="sendUserId" type="hidden" value="${msgInfo.receiver.id}"/>
								<input name="receiveUserId" type="hidden" value="${msgInfo.sender.id}"/>
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">原内容</label>
										<div class="col-md-4">
											 <p class="form-control-static">
												${msgInfo.sender.displayName}: ${msgInfo.content}
											 </p>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">标题</label>
										<div class="col-md-4">
											<input id="title" name="title" type="text" value="回复 ： ${msgInfo.title}" class="form-control required" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">紧急度</label>
										<div class="col-md-4">
											 <select name="msgType" id="msgType" class="form-control">
												  <option value="普通">普通</option>
												  <option value="紧急">紧急</option>
											  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">内容</label>
										<div class="col-md-4">
											<textarea id="content" name="content" class="form-control required" 
												placeholder="内容"></textarea>
										</div>
									</div>
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
        $('#msgInfoForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active" ><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    </script>
  
  </body>

</html>
