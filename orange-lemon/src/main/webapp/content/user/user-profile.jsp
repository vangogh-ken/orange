<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>更新个人信息</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
	<%@include file="/common/header2.jsp"%>
	<div class="page-container">
		<%@include file="/common/menu.jsp"%>
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<%@include file="/common/setting.jsp"%>
				<div class="row">
					<div class="col-md-12">
						<div class="tabbable tabbable-custom tabbable-full-width">
							<ul class="nav nav-tabs">
								<li><a href="#overView" data-toggle="tab">总览</a></li>
								<li class="active"><a href="#personalInfo" data-toggle="tab">个人信息 </a></li>
								<li><a href="#communication" data-toggle="tab">交流共享 </a></li>
								<li><a href="#help" data-toggle="tab">帮助</a></li>
							</ul>

							<div class="tab-content">
								<div class="tab-pane" id="overView"><h3>PAGE WILL BE COME SOON</h3></div>
								<div class="tab-pane active" id="personalInfo">
									<div class="row">
									  <div class="col-md-8">
											<form id="userForm" method="post" action="user-profile-save.do?operationMode=STORE" class="form-horizontal">
											 <c:if test="${item.id != null}">
											  <input id="id" type="hidden" name="id" value="${item.id}">
											 </c:if>
											 
											  <c:if test="${item.id == null}">
											  <input id="userId" type="hidden" name="userId" value="${userSession.id}">
											 </c:if>
											 
											<div class="form-body">
												<div class="form-group">
													<label class="control-label col-md-3">姓名</label>
													<div class="col-md-4" style="margin-top:8px;font-size: 16px;font-weight: 600;">
														${userSession.displayName}
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">组织机构</label>
													<div class="col-md-4" style="margin-top:8px;font-size: 16px;font-weight: 600;">
														${userSession.position == null ? '无' : userSession.orgEntity.orgEntityName}
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">职位</label>
													<div class="col-md-4" style="margin-top:8px;font-size: 16px;font-weight: 600;">
														${userSession.position == null ? '无' : userSession.position.positionName}
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">职级</label>
													<div class="col-md-4" style="margin-top:8px;font-size: 16px;font-weight: 600;">
														${userSession.position == null ? '无' : userSession.position.grade}
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">邮箱</label>
													<div class="col-md-4">
														<input id="mailAddress" type="text" name="mailAddress" value="${item.mailAddress}" class="form-control required email" >
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">邮箱密码</label>
													<div class="col-md-4">
														<input id="mailPassword" type="password" name="mailPassword" value="${item.mailPassword}" class="form-control required">
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">是否同步接收邮件</label>
													<div class="col-md-4" style="margin-top:8px;font-size: 16px;font-weight: 600;">
														是<input type="radio" name="mailAsync" value="T" <c:if test="${item.mailAsync == 'T'}">checked</c:if> >&nbsp;&nbsp;
														否<input type="radio" name="mailAsync" value="F" <c:if test="${item.mailAsync == 'F'}">checked</c:if>>
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">手机</label>
													<div class="col-md-4">
														<input id="mobile" type="text" name="mobile" value="${item.mobile}" class="form-control number required">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">电话</label>
													<div class="col-md-4">
														<input id="telephone" type="text" name="telephone" value="${item.telephone}" class="form-control required number">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">微信</label>
													<div class="col-md-4">
														<input id="weixinName" type="text" name="weixinName" value="${item.weixinName}" class="form-control required">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3">生日</label>
													<div class="col-md-4">
														<input id="birthDay" type="text" name="birthDay" value="<fmt:formatDate value="${item.birthDay}" pattern="yyyy-MM-dd"/>" class="form-control required datepicker">
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
												</div>
											</div>
										</form>
									  </div>
									  
									  <div class="col-md-4">
									  	<c:if test="${item.id != null and item.icon != null}">
									  		<div class="row">
												<c:if test="${item.id != null}">
												<div class="col-md-6">
												<button type="button" class="btn btn-sm red" onclick="uploadIcon();" style="left:30px;">${item.icon == null ?'上传头像':'上传头像'}</button>
												</div>
												</c:if>
												<div class="col-md-6">
												<a href="#" class="pull-left">
												<!--  
												<img alt="" src="${ctx}/s2/assets/img/blog/9.jpg" style="height: 60px;width: 60px;" class="media-object">
												-->
												<img alt="" src="${ctx}/icon/${userSession.userBase.icon}" style="height: 120px;width: 120px;" class="media-object" data-url="${ctx}/s2/assets/img/blog/9.jpg">
												</a>
												</div>
										  	</div>
										</c:if>
									  	<hr>
									  	<div class="row">
									  		<div class="col-md-6">
											<a href="#" class="pull-left" style="margin-top:77px;">
											<img alt="" src="${ctx}/s2/assets/img/logo-big3.png" style="height: 33px;width: 200px;" class="media-object">
											<button type="button" class="btn btn-sm green" style="margin-top:15px;left:30px;">扫码关注</button>
											</a>
											</div>
											<div class="col-md-6">
											<a href="#" class="pull-left">
											<img alt="" src="${ctx}/common/qrcode_weixin.jpg" style="height: 200px;width: 200px;" class="media-object">
											</a>
											</div>
									  	</div>
									  	<hr>
									  	<!--  
									  	<div class="row">
									  		<div class="col-md-12">
									  		<button type="button" class="btn btn-sm green" style="left:30px;">扫码关注</button>
											</div>
								  		</div>
								  		-->
									  </div>
									</div>
								</div>
	
								<div class="tab-pane" id="communication">
									与他人沟通的方式多种多样，而使用本系统，你可以：
									<ul>
										<li>通过内部论坛，与其他同事进行沟通交流，将你的疑问或者意见展示出来，让大家帮您想办法。</li>
										<li>还有站内短信，随意指定需要接收的人员，一个或者是多个。</li>
										<li>邮件，很重要的一环，不需要再登陆其他页面，只要你配置好的邮件信息，自动为您同步邮件信息，也避免了</li>
										<li>总之，如果遇到自己不能解决的系统问题，请及时反馈给系统管理员。</li>
									</ul>
								</div>
	
								<div class="tab-pane" id="help">
									<ul>
										<li>当您进入某个菜单显示“权限不够”的页面，请及时联系系统管理员，查看是否权限分配正确。</li>
										<li>当您点击某个菜单显示错误号码为“500”的页面，请及时反馈给系统管理员，并详细说明在何种情况下做何种操作之后出现该问题。</li>
										<li>不能够正确填报流程信息，请首先确认是否有该流程的权限和是否已经流转到正确节点。</li>
										<li>总之，如果遇到自己不能解决的系统问题，请及时反馈给系统管理员。</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="userBaseUploadIconModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">上传头像</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="userBaseIconForm" class="dropzone-custom" enctype="multipart/form-data" method="post" class="m-form-blank">
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
		$(function() {
			App.init(); 
		});
		
		$(function() {
	        $("#userForm").validate({
	            submitHandler: function(form) {
	    			bootbox.animate(false);
	    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
	                form.submit();
	            },
	            errorClass: 'validate-error',
		        rules: {
		        	weixinName: {
		                remote: {
		                    url: 'user-check-weixinname.do',
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
		        	weixinName: {
		                remote: "存在重复"
		            }
		        }
	        });
	    })
	
	    function uploadIcon(){
			$('#userBaseIconForm').dropzone({
		        url: 'user-base-icon-save.do',
		        maxFiles: 1,
		        maxFilesize: 0.1,//单位是M
		        acceptedFiles: ".jpg",//文件后缀名
		        init: function(){
		        	this.on('success', function(file, result){
						if(result.result == 'success'){
							alert('更新成功！');
							window.location.href = window.location.href;
						}else{
							alert('更新失败！');
							window.location.href = window.location.href;
						}
					});
				}
		    });
			
			var margin = (window.screen.availWidth - 600)/2;
			$('#userBaseUploadIconModal').css("margin-left", margin);
			$('#userBaseUploadIconModal').css("margin-top", 150);
			$('#userBaseUploadIconModal').css("width","600px");
			$('#userBaseUploadIconModal').modal();
		}
		
		$(document).delegate('button', 'click', function(e){
			if($(this).attr('data-dismiss') == 'modal'){
				window.location.href = window.location.href;
			}
		});
		
		//微信关注
		$(document).delegate('#userInviteWeixin', 'click',function(e){
			$.post('user-invite-weixin.do?', function(data){
				if(data != 'success'){
					alert('邀请失败！');
				}else{
					alert('邀请成功！请查询邮件或微信邀请信息。');
					var href = window.location.href;
					window.location.href = href;
				} 
			});
		});
	</script>
  </body>

</html>
