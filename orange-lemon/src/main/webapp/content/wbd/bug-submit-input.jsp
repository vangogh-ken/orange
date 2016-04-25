<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>系统问题报告单</title>
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
									<th colspan="4">系统问题报告单</th>
								</tr>
							</thead>
							<tbody>
							    <tr class="tr-title"><td colspan="4">提出申请</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="BGR">报告人</label>
								  	</td>
									<td>
										<van:input id="BGR"
											value="${item.BGR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="BGSJ">报告时间</label>
								  	</td>
									<td>
										<van:input id="BGSJ"
											value="${item.BGSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							  	<tr>
								  	<td>
								  		<label class="td-label" for="GNHWTMS">功能或问题描述</label>
								  	</td>
									<td colspan="3">
										<van:input id="GNHWTMS"
											value="${item.GNHWTMS}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
						    	</tr>
						    	<tr class="tr-title"><td colspan="4">上级核实</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SJHSR">核实人</label>
								  	</td>
									<td>
										<van:input id="SJHSR"
											value="${item.SJHSR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="SJHSSJ">核实时间</label>
								  	</td>
									<td>
										<van:input id="SJHSSJ"
											value="${item.SJHSSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="SJHSYJ">核实意见</label>
								  	</td>
									<td colspan="3">
										<van:input id="SJHSYJ"
											value="${item.SJHSYJ}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
						    	</tr>
							    
							    <tr class="tr-title"><td colspan="4">分管领导核实</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="FGLDHSR">核实人</label>
								  	</td>
									<td>
										<van:input id="FGLDHSR"
											value="${item.FGLDHSR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="FGLDHSSJ">核实时间</label>
								  	</td>
									<td>
										<van:input id="FGLDHSSJ"
											value="${item.FGLDHSSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="FGLDHSYJ">核实意见</label>
								  	</td>
									<td colspan="3">
										<van:input id="FGLDHSYJ"
											value="${item.FGLDHSYJ}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
						    	</tr>
						    	
						    	<tr class="tr-title"><td colspan="4">管理员核实</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="XTGLYHSR">核实人</label>
								  	</td>
									<td>
										<van:input id="XTGLYHSR"
											value="${item.XTGLYHSR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="XTGLYHSSJ">核实时间</label>
								  	</td>
									<td>
										<van:input id="XTGLYHSSJ"
											value="${item.XTGLYHSSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="XTGLYHSYJ">核实意见</label>
								  	</td>
									<td colspan="3">
										<van:input id="XTGLYHSYJ"
											value="${item.XTGLYHSYJ}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
						    	</tr>
						    	
						    	<tr class="tr-title"><td colspan="4">问题处理</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="CLR">处理人</label>
								  	</td>
									<td>
										<van:input id="CLR"
											value="${item.CLR}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="CLSJ">处理时间</label>
								  	</td>
									<td>
										<van:input id="CLSJ"
											value="${item.CLSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="CLJG">处理结果</label>
								  	</td>
									<td colspan="3">
										<van:input id="CLJG"
											value="${item.CLJG}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
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
		if($('#BGSJ').val() == ''){
			$('#BGSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#BGSJ').attr('readonly', 'readonly');
		}
		
		if($('#SJHSSJ').val() == ''){
			$('#SJHSSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#SJHSSJ').attr('readonly', 'readonly');
		}
		
		if($('#FGLDHSSJ').val() == ''){
			$('#FGLDHSSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#FGLDHSSJ').attr('readonly', 'readonly');
		}
		
		if($('#XTGLYHSSJ').val() == ''){
			$('#XTGLYHSSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss'));
			$('#XTGLYHSSJ').attr('readonly', 'readonly');
		}
		
		if($('#CLSJ').val() == ''){
			$('#CLSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#CLSJ').attr('readonly', 'readonly');
		}
		
		if($('#BGR').val() == ''){
			$('#BGR').val('${userSession.displayName}'); 
			$('#BGR').attr('readonly', 'readonly');
		}
		if($('#SJHSR').val() == ''){
			$('#SJHSR').val('${userSession.displayName}'); 
			$('#SJHSR').attr('readonly', 'readonly');
		}
		if($('#FGLDHSR').val() == ''){
			$('#FGLDHSR').val('${userSession.displayName}'); 
			$('#FGLDHSR').attr('readonly', 'readonly');
		}
		if($('#XTGLYHSR').val() == ''){
			$('#XTGLYHSR').val('${userSession.displayName}'); 
			$('#XTGLYHSR').attr('readonly', 'readonly');
		}
		if($('#CLR').val() == ''){
			$('#CLR').val('${userSession.displayName}');
			$('#CLR').attr('readonly', 'readonly');
		}
	});
	</script>
	
</body>

</html>
