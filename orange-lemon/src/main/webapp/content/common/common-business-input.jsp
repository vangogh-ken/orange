<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>${title}</title>
<%@include file="/common/s2.jsp"%>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<header style="height: 30px; margin-top: 0px; margin-bottom: 5px;border: solid #858585 1px;">
							<c:if test="${taskId != null }">
							<button class="btn btn-sm red"
								style="float: left; margin-right: 10px;"
								onclick="showGraph('${item.PRCI_ID}');">
									流程<i class="fa fa-sitemap"></i>
							</button>
							</c:if>
							<c:if test="${taskId != null }">
								<button onclick="getNextActivityDetails('${taskId}');"
									class="btn btn-sm red" style="float: left; margin-right: 10px;">
									发送<i class="fa fa-envelope-o"></i>
								</button>
     						</c:if>

							<c:if test="${readonly == null}">
								<button id="submitButton" onclick="$('#commonBusinessForm').submit();" 
								class="btn btn-sm red" style="float: right; margin-left: 10px;">
									保存<i class="fa fa-save "></i>
								</button>
							</c:if>
							<button onclick="closeWindow();" class="btn btn-sm red"
								style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
						</header>

					<form id="commonBusinessForm" method="post" action="${ctx}/common/common-business-save.do?operationMode=STORE" 
						onsubmit="return false" class="form-horizontal">
						
						<c:if test="${item.ID != null}">
							<input id="ID" type="hidden" name="ID" value="${item.ID}">
						</c:if>
						<input id="taskId" type="hidden" name="taskId" value="${taskId}">
						<input id="USER_ID" type="hidden" name="USER_ID" value="${userSession.id}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">${title}</th>
								</tr>
							</thead>
							<c:forEach items="${attributes}" var="group">
								<c:if test="${group.valueType == 'MAKEGROUP'  and group.columnName != 'ID' and group.columnName != 'STATUS' and group.columnName != 'PRCI_ID'}">
								<tbody>
								<tr><td colspan="4">${group.name}</td></tr>
								<!-- 计算每个分组的td数量 -->
								<c:set var="attributeCount" value="0"></c:set>
								<c:set var="isEnd" value="0"></c:set>
								
								<c:forEach items="${attributes}" var="attribute" varStatus="varStatus">
									<c:if test="${attribute.attributeGroup.id == group.id and attribute.valueType != 'MAKEGROUP' and attribute.displayPage == 'T'}">
										<c:choose>
											<c:when test="${attributeCount %2 == 0}">
											<tr>
											<td><label class="td-label" for="${attribute.columnName}">${attribute.name}</label></td>
											<td>
											<c:choose>
											<c:when test="${attribute.type == 'VARCHAR'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>

											<c:when test="${attribute.type == 'TIMESTAMP'}">
												<van:timestamp id="${attribute.columnName}"
													name="${attribute.columnName}"
													value="${item[attribute.columnName]}"
													status="${item.STATUS}" 
													clsId="${clsId}"
													controllerId="${controllerId}"
													taskId="${taskId}"
													pattern="yyyy-MM-dd HH:mm:ss" styleClass="form-control required" />
											</c:when>

											<c:when test="${attribute.type == 'DATE'}">
												<van:timestamp id="${attribute.columnName}"
													name="${attribute.columnName}"
													value="${item[attribute.columnName]}"
													status="${item.STATUS}" 
													clsId="${clsId}"
													controllerId="${controllerId}"
													taskId="${taskId}"
													pattern="yyyy-MM-dd" styleClass="form-control required" />
											</c:when>

											<c:when test="${attribute.type == 'TEXT'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>

											<c:when test="${attribute.type == 'INT'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>
											</c:choose>
											</td>
											<c:set var="attributeCount" value="${attributeCount + 1}"></c:set>
											</c:when>
											
											<c:when test="${attributeCount %2 != 0}">
											<td><label class="td-label" for="${attribute.columnName}">${attribute.name}</label></td>
											<td>
											<c:choose>
											<c:when test="${attribute.type == 'VARCHAR'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>

											<c:when test="${attribute.type == 'TIMESTAMP'}">
												<van:timestamp id="${attribute.columnName}"
													name="${attribute.columnName}"
													value="${item[attribute.columnName]}"
													status="${item.STATUS}" 
													clsId="${clsId}"
													controllerId="${controllerId}"
													taskId="${taskId}"
													pattern="yyyy-MM-dd HH:mm:ss" styleClass="form-control required" />
											</c:when>

											<c:when test="${attribute.type == 'DATE'}">
												<van:timestamp id="${attribute.columnName}"
													name="${attribute.columnName}"
													value="${item[attribute.columnName]}"
													status="${item.STATUS}" 
													clsId="${clsId}"
													controllerId="${controllerId}"
													taskId="${taskId}"
													pattern="yyyy-MM-dd" styleClass="form-control required" />
											</c:when>

											<c:when test="${attribute.type == 'TEXT'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>

											<c:when test="${attribute.type == 'INT'}">
												<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</c:when>
											</c:choose>
											</td>
											</tr>
											<c:set var="attributeCount" value="${attributeCount + 1}"></c:set>
											</c:when>
										</c:choose>
										
										
									</c:if>
									<c:if test="${attributeCount %2 != 0 and varStatus.last}">
									<td></td><td></td></tr>
									</c:if>
								</c:forEach>
								
								</c:if>
								</tbody>
							</c:forEach>
						</table>
					</form>
				</article>

			</section>
		</div>
	</div>
	
	<!-- /.modal -->
	<div class="modal fade" id="businessTaskCompleteModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">发送任务</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="businessTaskCompleteForm" action="${ctx}/bpm/bpm-business-transact-next" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm blue" onclick="completeTask();">发送</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->


	<%@include file="/common/footer.jsp"%>
	<script src="${ctx}/s2/assets/van/custom/js/bpmhandle.js" type="text/javascript" ></script>
	
	
</body>

</html>
