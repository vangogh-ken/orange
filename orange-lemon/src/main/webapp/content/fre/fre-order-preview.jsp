<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>订单总览</title>
<%@include file="/common/s2.jsp"%>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<header style="height: 35px; margin-top: 0px; margin-bottom: 5px;padding-top:2px;border: solid #858585 1px;">
							<button id="addDataTemplateActionField" class="btn btn-sm red" style="float: left; ">
							数据&nbsp;<i class="fa fa-save "></i>
							</button>
							
							<button id="submitButton" onclick="$('#freightDataTemplateForm').submit();" 
							class="btn btn-sm red" style="float: right; margin-left: 10px;">保存<i class="fa fa-save "></i>
							</button>
							<button onclick="closeWindow();" class="btn btn-sm red" style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
						</header>

					<form id="freightDataTemplateForm" method="post" action="" onsubmit="return false" class="form-horizontal">
						<c:if test="${item.id != null}">
							<input id="id" type="hidden" name="id" value="${item.id}">
						</c:if>
						<input id="freightOrderId" type="hidden" name="freightOrderId" value="${item.freightOrder.id == null? param.freightOrderId : item.freightOrder.id}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">数据模板信息</th>
								</tr>
							</thead>
							<tbody>
							    <tr>
								  	<td>
										<label class="td-label" for="templateName">模板名称</label>
									</td>
									<td colspan="3">
										<input id="templateName" type="text" name="templateName" value="${item.templateName}" 
										size="40" minlength="2" maxlength="50" class="form-control required" >
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
		
	</script>
</body>

</html>
