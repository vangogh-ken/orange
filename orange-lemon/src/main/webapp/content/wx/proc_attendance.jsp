<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>考勤补卡申请单</title>
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
									发送</i>
								</button>
     						</c:if>
     						<!-- 
     						<i class="fa fa-envelope-o"> 
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
					<form id="basisSubstanceForm" method="post" onsubmit="return false;" class="form-horizontal">
						<input id=basisSubstanceId type="hidden" name="basisSubstanceId" value="${item.basisSubstanceId}">
						<input id="taskId" type="hidden" name="taskId" value="${item.taskId}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="2">考勤补卡申请单</th>
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
								  		<label class="td-label" for="KSRQ">开始日期</label>
								  	</td>
									<td>
										<van:input id="KSRQ"
											value="${item.KSRQ}"
											styleClass="form-control required datepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="ZZRQ">终止日期</label>
								  	</td>
									<td>
										<van:input id="ZZRQ"
											value="${item.ZZRQ}"
											styleClass="form-control required datepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
									<td>
								  		<label class="td-label" for="YY">原因</label>
								  	</td>
									<td>
										<van:input id="YY"
											value="${item.YY}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="4">上级审批</td></tr>
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
							    <tr class="tr-title"><td colspan="4">人事确认</td></tr>
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
			$('#SQSJ').attr('readonly', 'readonly');
		}
		
		if($('#SJSPSJ').val() == ''){
			$('#SJSPSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss'));
			$('#SJSPSJ').attr('readonly', 'readonly');
		}
		
		
		if($('#RSQRSJ').val() == ''){
			$('#RSQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#RSQRSJ').attr('readonly', 'readonly');
		}
		
		
		if($('#SQR').val() == ''){
			$('#SQR').val('${userSession.displayName}'); 
			$('#SQR').attr('readonly', 'readonly');
		}
		if($('#SJSPR').val() == ''){
			$('#SJSPR').val('${userSession.displayName}'); 
			$('#SJSPR').attr('readonly', 'readonly');
		}
		if($('#RSQRR').val() == ''){
			$('#RSQRR').val('${userSession.displayName}');
			$('#RSQRR').attr('readonly', 'readonly');
		}
	});
	
	//在提交时进行表单数据的校验
	function validBeforeSubmit(){
		if($("#basisSubstanceForm").valid()){
			//申请时间在一周以内
			var status = "${item.status}";
			if(status == '草稿'){
				var sqsj = new Date($('#SQSJ').val().replace(/-/g, '-'));
				var rq = new Date($('#ZZRQ').val().replace(/-/g, '-'));
				if((sqsj.getTime() - rq.getTime()) > 1000 * 60 * 60 * 24 * 7){
					alert('只能申请一周之前的考勤！');
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	//默认开始日期与终止日期一致
	$(document).delegate('#KSRQ', 'change', function(event){
		$('#ZZRQ').val($(this).val());
	});
	
	</script>
	
</body>

</html>
