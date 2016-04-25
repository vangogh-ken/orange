<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

<head>
<%@include file="/common/meta.jsp"%>
<title>委派人员信息</title>
<%@include file="/common/s2.jsp"%>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<header style="height: 35px; margin-top: 0px; margin-bottom: 5px;padding-top:2px;border: solid #858585 1px;">
							<button id="submitButton" onclick="$('#editDomainForm').submit();" 
							class="btn btn-sm red" style="float: right; margin-left: 10px;">保存<i class="fa fa-save "></i>
							</button>
							<button onclick="closeWindow();" class="btn btn-sm red" style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
						</header>

					<form id="editDomainForm" method="post" action="" onsubmit="return false" class="form-horizontal">
						<c:if test="${item.id != null}">
							<input id="id" type="hidden" name="id" value="${item.id}">
						</c:if>
						<input id="freightActionTypeId" type="hidden" name="freightActionTypeId" value="${item.freightActionTypeId}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">委派人员信息</th>
								</tr>
							</thead>
							<tbody>
							    <tr>
									<td>
										<label class="td-label" for="assigneeUserId">选择用户</label>
									</td>
									<td >
										<select id="assigneeUserId" name="assigneeUserId" class="form-control required">
											<c:forEach items="${users}" var="user">
											<option value="${user.id}"<c:if test="${item.assigneeUserId == user.id}">selected</c:if> >${user.displayName}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<label class="td-label" for="status">状态</label>
									</td>
									<td>
										<select id="status" name="status" class="form-control required">
											<option value="T"<c:if test="${item.status == 'T'}">selected</c:if> >已启用</option>
											<option value="F"<c:if test="${item.status == 'F'}">selected</c:if> >已停用</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<label class="td-label" for="descn">说明</label>
									</td>
									<td colspan="3">
										<textarea id="descn" name="descn" class="form-control required" >${item.descn}</textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</article>

			</section>
		</div>
	</div>
	
	
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
		function closeWindow() {
			var parent = window.opener.location;
			var href = parent.href;
			//处理掉#,避免页面不刷新
			if (href.indexOf('#') >= 0) {
				href = href.substring(0, href.length - 1);
			}
	
			parent.href = href;
			window.close();
		}
		
		//异步保存数据
		$(function() {
			$("#editDomainForm").validate({
		        submitHandler: function(form) {
		    		bootbox.animate(false);
		    		var box = bootbox
		    				.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');

		    		$.post(
		    			'fre-action-type-identity-save-async.do',
		    			$('#editDomainForm').serialize(),
		    			function(data){
		    				if(data == 'success'){
		    					alert('委派成功');
		    				}else{
		    					alert('委派失败');
		    				}
		    				closeWindow();
		    			});
		        },
		        errorClass: 'validate-error'
			});
		});
		
	</script>
</body>

</html>
