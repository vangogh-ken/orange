<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>用户信息</title>
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
							<div class="caption"><i class="fa fa-user"></i>用户信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="userForm" method="post" action="user-save.do?operationMode=STORE" class="form-horizontal" >
								<c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">用户名</label>
												<div class="col-md-9">
													<input id="userName" type="text" name="userName" value="${item.userName}" size="40" minlength="2" maxlength="50" class="form-control required" placeholder="用户名">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">姓名</label>
												<div class="col-md-9">
													<input id="displayName" type="text" name="displayName" value="${item.displayName}" class="form-control required" placeholder="姓名">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<c:if test="${item == null || item.password == null}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">密码</label>
												<div class="col-md-9">
													<input type="password" id="password" name="password" value="11111111" class="form-control required" placeholder="密码">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">密码确认</label>
												<div class="col-md-9">
													<input type="password" size="40" maxlength="10" value="11111111" equalTo="#password" class="form-control text required" placeholder="密码">
												</div>
											</div>
										</div>
										</c:if>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">职位</label>
												<div class="col-md-9">
													<select id="positionId" name="positionId" class="form-control required">
														<c:forEach items="${positions}" var="position">
														<!-- 
															<option value="${position.id}" <c:if test="${item.position.id == position.id}">selected="selected"</c:if>>${position.orgEntity.orgEntityName}(级别):${position.grade==1?'总经理':position.grade==2?'副总级':position.grade==3?'主管/经理':'普通员工'}:${position.positionName}</option>
															 -->
															 <option value="${position.id}" <c:if test="${item.position.id == position.id}">selected="selected"</c:if>>${position.orgEntity.orgEntityName}:${position.positionName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">是否启用</label>
												<div class="col-md-9">
													是<input type="radio" name="status" value="T" ${item.status == 'T' ? 'checked' : ''} >
													&nbsp;&nbsp;&nbsp;&nbsp;
													否<input type="radio" name="status" value="F" ${item.status == 'F' ? 'checked' : ''} >
												</div>
											</div>
										</div>
									</div>
									<div class="row">		
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">入职时间</label>
												<div class="col-md-9">
													<input id="hireTime" type="text" name="hireTime" 
													value="<fmt:formatDate value="${item.hireTime == null ? now : item.hireTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													class="form-control required datepicker" >
												</div>
											</div>
										</div>
										<c:if test="${item != null && item.id != null}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">离职时间</label>
												<div class="col-md-9">
													<input id="fireTime" type="text" name="fireTime" 
													value="<fmt:formatDate value="${item.fireTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													class="form-control datepicker" >
												</div>
											</div>
										</div>
										</c:if>
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
		$("#userForm").validate({
	        submitHandler: function(form) {
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active" ><div class="bar" style="width: 100%;"></div></div>');
	            form.submit();
	        },
	        errorClass: 'validate-error',
	        rules: {
	            userName: {
	                remote: {
	                    url: 'user-check-username.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        }
	                        </c:if>
	                    }
	                }
	            },
	            displayName: {
	                remote: {
	                    url: 'user-check-displayname.do',
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
	            userName: {
	                remote: "存在重复账号"
	            },
	            displayName: {
	                remote: "存在重复姓名"
	            }
	        }
	    });
	});
        
    </script>
  </body>

</html>
