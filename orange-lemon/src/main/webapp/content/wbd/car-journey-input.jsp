<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>派车申请单</title>
<%@include file="/common/s2.jsp"%>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<header style="height: 30px; margin-top: 0px; margin-bottom: 5px;border: solid #858585 1px;">
							<c:if test="${item.processInstanceId != null}">
							<button class="btn btn-sm red"
								style="float: left; margin-right: 10px;"
								onclick="showGraph('${item.processInstanceId}');">
									流程<i class="fa fa-sitemap"></i>
							</button>
							</c:if>
							<c:if test="${item.taskId != null}">
								<button onclick="getNextActivityDetails('${item.taskId}');"
									class="btn btn-sm red" style="float: left; margin-right: 10px;">
									发送<i class="fa fa-envelope-o"></i>
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
						</header>
					<form id="basisSubstanceForm" method="post" onsubmit="return false" class="form-horizontal">
						<input id=basisSubstanceId type="hidden" name="basisSubstanceId" value="${item.basisSubstanceId}">
						<input id="taskId" type="hidden" name="taskId" value="${item.taskId}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">派车申请单</th>
								</tr>
							</thead>
							<tbody>
							    <tr class="tr-title"><td colspan="4">基本信息</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="JCR">驾车人</label>
								  	</td>
									<td>
										<van:input id="JCR"
											value="${item.JCR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="CYCL">乘用车辆</label>
								  	</td>
									<td>
										<van:input id="CYCL"
											value="${item.CYCL}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="PCSY">派车事由</label>
								  	</td>
									<td>
										<van:input id="PCSY"
											value="${item.PCSY}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="MDD">目的地</label>
								  	</td>
									<td>
										<van:input id="MDD"
											value="${item.MDD}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SQSJ">申请时间</label>
								  	</td>
									<td>
										<van:input id="SQSJ"
											value="${item.SQSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="YJSJ">预计时间</label>
								  	</td>
									<td>
										<van:input id="YJSJ"
											value="${item.YJSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="4">取车还车</td></tr>
							    <tr>
							    	<td>
								  		<label class="td-label" for="QCYS">取车钥匙</label>
								  	</td>
								  	<td>
										<van:input id="QCYS"
											value="${item.QCYS}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								  	<td>
								  		<label class="td-label" for="QCTFD">取车停放地</label>
								  	</td>
									<td>
										<van:input id="QCTFD"
											value="${item.QCTFD}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
							    <tr>
									<td>
								  		<label class="td-label" for="CCSJ">出车时间</label>
								  	</td>
									<td>
										<van:input id="CCSJ"
											value="${item.CCSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    	<td>
								  		<label class="td-label" for="CCSGLS">出车时公里数</label>
								  	</td>
								  	<td >
										<van:input id="CCSGLS"
											value="${item.CCSGLS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="HCYS">还车钥匙</label>
								  	</td>
									<td>
										<van:input id="HCYS"
											value="${item.HCYS}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="HCTFD">还车停放地</label>
								  	</td>
									<td>
										<van:input id="HCTFD"
											value="${item.HCTFD}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
								</tr>
								<tr>
								  	<td>
								  		<label class="td-label" for="FHSJ">返回时间</label>
								  	</td>
									<td>
										<van:input id="FHSJ"
											value="${item.FHSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="FHSGLS">返回时公里数</label>
								  	</td>
									<td>
										<van:input id="FHSGLS"
											value="${item.FHSGLS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    
							    <tr class="tr-title"><td colspan="4">备注</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="BZ">备注</label>
								  	</td>
									<td colspan="3">
										<van:input id="BZ"
											value="${item.BZ}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											styleCss="min-height:100px;"
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
		if($('#JCR').val() == ''){
			$('#JCR').val('${userSession.displayName}'); 
			$('#JCR').attr('readonly', 'readonly');
		}

		if($('#SQSJ').val() == ''){
			$('#SQSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#SQSJ').attr('readonly', 'readonly');
		}
	});
	</script>
	
</body>

</html>
