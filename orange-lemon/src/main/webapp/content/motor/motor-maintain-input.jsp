<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>维修信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>维修信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="motor-maintain-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">维修信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="motorcadeTruckId">车牌号</label>
												</td>
												<td>
													<select id="motorcadeTruckId" name="motorcadeTruckId" class="form-control required">
														<c:forEach items="${motorcadeTrucks}" var="motorcadeTruck">
														<option value="${motorcadeTruck.id}" <c:if test="${item.motorcadeTruck.id==motorcadeTruck.id}">selected</c:if>>${motorcadeTruck.headstockNumber}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="motorcadeDriverId">司机</label>
												</td>
												<td>
													<select id="motorcadeDriverId" name="motorcadeDriverId" class="form-control required">
														<c:forEach items="${motorcadeDrivers}" var="motorcadeDriver">
														<option value="${motorcadeDriver.id}" <c:if test="${item.motorcadeDriver.id==motorcadeDriver.id}">selected</c:if>>${motorcadeDriver.displayName}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="maintainItem">维修项目</label>
												</td>
												<td colspan="3">
													<input id="maintainItem" type="text" name="maintainItem" value="${item.maintainItem}" 
													size="40" minlength="2" maxlength="20" class="form-control required" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="maintainUnit">计价单位</label>
												</td>
												<td>
													<input id="maintainUnit" type="text" name="maintainUnit" value="${item.maintainUnit}" 
													size="40" minlength="1" maxlength="4" class="form-control required dictionary" 
													vAttrId="6049b457-23b3-11e5-a9c8-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="maintainCount">数量</label>
												</td>
												<td>
													<input id="maintainCount" type="text" name="maintainCount" value="${item.maintainCount}" 
													size="40" minlength="1" maxlength="4" class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="fasInvoiceTypeId">票种</label>
												</td>
												<td>
													<select id="fasInvoiceTypeId" name="fasInvoiceTypeId" class="form-control required">
														<c:forEach items="${fasInvoiceTypes}" var="fasInvoiceType">
														<option value="${fasInvoiceType.id}" <c:if test="${item.fasInvoiceType.id==fasInvoiceType.id}">selected</c:if>>${fasInvoiceType.typeName}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="moneyCount">单价</label>
												</td>
												<td>
													<input id="moneyCount" type="text" name="moneyCount" value="${item.moneyCount}" 
													size="40" minlength="1" maxlength="10" class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="maintainTime">维修时间</label>
												</td>
												<td>
													<input id="maintainTime" type="text" name="maintainTime" value='<fmt:formatDate value="${item.maintainTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>是</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>否</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<textarea id="descn" type="text" name="descn" class="form-control required" 
													style="min-height:80px;"
													size="40" minlength="1" maxlength="64">${item.descn == null ? '无' : item.descn}</textarea>
												</td>
											</tr>
										</tbody>
									</table>
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
        $("#editDomainForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
    			form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    </script>
  </body>

</html>
