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
		<div class="page-content"
			style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<form id="commonBusinessForm" method="post" action="${ctx}/common/common-business-save.do?operationMode=STORE" class="form-horizontal">
						<header style="height: 30px; margin-top: -10px; margin-bottom: 10px;">
							<c:if test="${taskId != null}">
							<button type="button" class="btn default" onclick="showGraph('${item.PRCI_ID}');">
								流程<li class="fa fa-sitemap"></li>
							</button>
							</c:if>
							<button type="button" class="btn default" onclick="window.close();" >
								关闭<i class="fa fa-power-off"></i>
							</button>
						</header>

						<c:if test="${item.ID != null}">
							<input id="ID" type="hidden" name="ID" value="${item.ID}">
						</c:if>
						<input id="taskId" type="hidden" name="taskId" value="${taskId}">
						<input id="clsId" type="hidden" name="clsId" value="${clsId}">
						<input id="controllerId" type="hidden" name="controllerId" value="${controllerId}">

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
									<c:if test="${attribute.attributeGroup.id == group.id and attribute.valueType != 'MAKEGROUP'}">
										<c:choose>
											<c:when test="${attributeCount %2 == 0}">
											<tr>
											<td><label class="td-label" for="${attribute.columnName}">${attribute.name}</label></td>
											<td>
											<c:choose>
												<c:when test="${attribute.type == 'VARCHAR'}">
							  					${item[attribute.columnName]}
							  					</c:when>
												<c:when test="${attribute.type == 'TIMESTAMP'}">
													<fmt:formatDate value="${item[attribute.columnName]}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</c:when>
		
												<c:when test="${attribute.type == 'TEXT'}">
													<textarea>${item[attribute.columnName]}</textarea>
												</c:when>
		
												<c:when test="${attribute.type == 'INT'}">
							  					${item[attribute.columnName]}
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
							  					${item[attribute.columnName]}
							  					</c:when>
												<c:when test="${attribute.type == 'TIMESTAMP'}">
													<fmt:formatDate value="${item[attribute.columnName]}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</c:when>
		
												<c:when test="${attribute.type == 'TEXT'}">
													<textarea>${item[attribute.columnName]}</textarea>
												</c:when>
		
												<c:when test="${attribute.type == 'INT'}">
							  					${item[attribute.columnName]}
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
	<%@include file="/common/footer.jsp"%>
</body>

</html>
