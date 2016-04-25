<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>客户访问填报</title>
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
									<th colspan="4">客户拜访填报</th>
								</tr>
							</thead>
							<tbody>
							    <tr class="tr-title"><td colspan="4">填报</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="DWMC">单位名称</label>
								  	</td>
									<td>
										<van:input id="DWMC"
											value="${item.DWMC}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="LXRJZW">联系人及职务</label>
								  	</td>
									<td>
										<van:input id="LXRJZW"
											value="${item.LXRJZW}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="DWDZ">单位地址</label>
								  	</td>
									<td colspan="3">
										<van:input id="DWDZ"
											value="${item.DWDZ}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="BFSJ">拜访时间</label>
								  	</td>
									<td>
										<van:input id="BFSJ"
											value="${item.BFSJ}"
											styleClass="form-control required datepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="ZJBFSJ">最近拜访时间</label>
								  	</td>
									<td>
										<van:input id="ZJBFSJ"
											value="${item.ZJBFSJ}"
											styleClass="form-control required datepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="ZYHWPM">主要货物品名</label>
								  	</td>
									<td>
										<van:input id="ZYHWPM"
											value="${item.ZYHWPM}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="ZYYSFS">主要运输方式</label>
								  	</td>
									<td>
										<van:input id="ZYYSFS"
											value="${item.ZYYSFS}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="YJHL">月均货量</label>
								  	</td>
									<td colspan="3">
										<van:input id="YJHL"
											value="${item.YJHL}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="KHBFJL">客户拜访记录</label>
								  	</td>
									<td colspan="3">
										<van:input id="KHBFJL"
											value="${item.KHBFJL}"
											styleClass="form-control required"
											styleCss="min-height:120px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="XSFXJKFJH">销售机会及开发计划</label>
								  	</td>
									<td colspan="3">
										<van:input id="XSFXJKFJH"
											value="${item.XSFXJKFJH}"
											styleClass="form-control required"
											styleCss="min-height:80px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="TBR">填报人</label>
								  	</td>
									<td>
										<van:input id="TBR"
											value="${item.TBR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="TBSJ">填报时间</label>
								  	</td>
									<td>
										<van:input id="TBSJ"
											value="${item.TBSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr class="tr-title"><td colspan="4">领导确认</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="LDQRR">领导确认人</label>
								  	</td>
									<td>
										<van:input id="LDQRR"
											value="${item.LDQRR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="LDQRSJ">领导确认时间</label>
								  	</td>
									<td>
										<van:input id="LDQRSJ"
											value="${item.LDQRSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								    <td>
								  		<label class="td-label" for="ZDYJ">客户开发建议</label>
								  	</td>
									<td colspan="3">
										<van:input id="ZDYJ"
											value="${item.ZDYJ}"
											styleClass="form-control required"
											styleCss="min-height:80px;"
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
		if($('#TBSJ').val() == ''){
			$('#TBSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#LDQRSJ').val() == ''){
			$('#LDQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#TBR').val() == ''){
			$('#TBR').val('${userSession.displayName}'); 
		}
		if($('#LDQRR').val() == ''){
			$('#LDQRR').val('${userSession.displayName}'); 
		}
	});
	</script>
	
</body>

</html>
