<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>请假申请单</title>
<%@include file="/common/s2.jsp"%>

<style type="text/css">

.table-input tr{
	height:100px !important;
}

.table-input tr th{
	height:100px !important;
	font-size:40px !important;
	font-weight:bolder !important;
}

.table-input tr{
	height:100px !important;
}

.table-input tr td{
	height:100px !important;
	font-size:40px !important;
	font-weight:bolder !important;
}

.table-input tr td label,input,select{
	height:100px !important;
	font-size:40px !important;
	font-weight:bolder !important;
}

.table-input tr td textarea{
	height:200px !important;
	font-size:40px !important;
	font-weight:bolder !important;
}

.btn-lg{
	height:100px !important;
	font-size:48px !important;
	font-weight:bolder !important;
	width: 100%;
}
</style>

</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input" style="margin-left:0;margin-right:0;">
				<article class="m-widget">
					<header style="height: 100px; margin-top: 0px; margin-bottom: 5px;border: solid #858585 1px;">
							<c:if test="${item.taskId != null}">
								<button onclick="if(validBeforeSubmit()) completeTaskWithCertain('usertask2');"
									class="btn btn-lg red" style="float: left; margin-right: 10px;">
									发送
								</button>
     						</c:if>
							
							<!-- 
							<i class="fa fa-envelope-o"></i> 
							<c:if test="${item.processInstanceId != null}">
							<button class="btn btn-sm red"
								style="float: left; margin-right: 10px;"
								onclick="showGraph('${item.processInstanceId}');">
									流程<i class="fa fa-sitemap"></i>
							</button>
							</c:if>
							
							<c:if test="${item.taskId != null}">
								<button id="submitButton" onclick="$('#basisSubstanceForm').submit();" 
								class="btn btn-sm red" style="float: right; margin-left: 10px;">
									保存<i class="fa fa-save "></i>
								</button>
							</c:if>
							<button onclick="closeWindow();" class="btn btn-sm red"
								style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
							-->
						</header>
					<form id="basisSubstanceForm" method="post" onsubmit="return false" class="form-horizontal">
						<input id=basisSubstanceId type="hidden" name="basisSubstanceId" value="${item.basisSubstanceId}">
						<input id="taskId" type="hidden" name="taskId" value="${item.taskId}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">请假申请单</th>
								</tr>
							</thead>
							<tbody>
							    <tr class="tr-title"><td colspan="2">提出申请</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SQR">申请人</label>
								  	</td>
									<td>
										<van:input id="SQR"
											value="${item.SQR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="QJSJ">申请时间</label>
								  	</td>
									<td>
										<van:input id="SQSJ"
											value="${item.SQSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="QJLX">请假类型</label>
								  	</td>
									<td>
										<van:input id="QJLX"
											value="${item.QJLX}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="QJSY">请假事由</label>
								  	</td>
									<td>
										<van:input id="QJSY"
											value="${item.QJSY}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SQR">开始时间</label>
								  	</td>
									<td>
										<van:input id="KSSJ"
											value="${item.KSSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="JSSJ">结束时间</label>
								  	</td>
									<td>
										<van:input id="JSSJ"
											value="${item.JSSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="2">上级审批</td></tr>
							    <tr>
							    	<td>
								  		<label class="td-label" for="SJSPYJ">上级审批意见</label>
								  	</td>
								  	<td >
										<van:input id="SJSPYJ"
											value="${item.SJSPYJ}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SJSPR">上级审批人</label>
								  	</td>
									<td>
										<van:input id="SJSPR"
											value="${item.SJSPR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="SJSPSJ">上级审批时间</label>
								  	</td>
									<td>
										<van:input id="SJSPSJ"
											value="${item.SJSPSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr class="tr-title"><td colspan="2">领导审批</td></tr>
							    <tr>
							    	<td>
								  		<label class="td-label" for="LDSPYJ">领导审批意见</label>
								  	</td>
								  	<td>
										<van:input id="LDSPYJ"
											value="${item.LDSPYJ}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="LDSPR">领导审批人</label>
								  	</td>
									<td>
										<van:input id="LDSPR"
											value="${item.LDSPR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="LDSPSJ">领导审批时间</label>
								  	</td>
									<td>
										<van:input id="LDSPSJ"
											value="${item.LDSPSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="2">人事确认</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="RSQRR">人事确认人</label>
								  	</td>
									<td>
										<van:input id="RSQRR"
											value="${item.RSQRR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="RSQRSJ">人事确认时间</label>
								  	</td>
									<td>
										<van:input id="RSQRSJ"
											value="${item.RSQRSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="2">销假确认</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="XJQRR">销假确认人</label>
								  	</td>
									<td>
										<van:input id="XJQRR"
											value="${item.XJQRR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="XJQRSJ">销假确认时间</label>
								  	</td>
									<td>
										<van:input id="XJQRSJ"
											value="${item.XJQRSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
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
	<script src="${ctx}/s2/assets/van/custom/js/bpmhandle.js" type="text/javascript" ></script>
	<script type="text/javascript">
	$(function(){
		if($('#SQSJ').val() == ''){
			$('#SQSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#SJSPSJ').val() == ''){
			$('#SJSPSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#LDSPSJ').val() == ''){
			$('#LDSPSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#RSQRSJ').val() == ''){
			$('#RSQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#XJQRSJ').val() == ''){
			$('#XJQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#SQR').val() == ''){
			$('#SQR').val('${userSession.displayName}'); 
		}
		if($('#SJSPR').val() == ''){
			$('#SJSPR').val('${userSession.displayName}'); 
		}
		if($('#LDSPR').val() == ''){
			$('#LDSPR').val('${userSession.displayName}'); 
		}
		if($('#RSQRR').val() == ''){
			$('#RSQRR').val('${userSession.displayName}'); 
		}
		if($('#XJQRR').val() == ''){
			$('#XJQRR').val('${userSession.displayName}'); 
		}
	});
	
	function validBeforeSubmit(){
		return true;
	}
	</script>
	
</body>

</html>
