<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>成本信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>成本信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-price-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">成本信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="freightExpenseTypeId">价格名称</label>
												</td>
												<td>
													<select id="freightExpenseTypeId" name="freightExpenseTypeId" class="form-control required">
													<c:forEach items="${freightExpenseTypes}" var="freightExpenseType">
													<option value="${freightExpenseType.id }" <c:if test="${item.freightExpenseType.id == freightExpenseType.id }">selected</c:if>>${freightExpenseType.typeName}</option>
													</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="regular">是否为固定金额</label>
												</td>
												<td>
													<select id="regular" name="regular" class="form-control required" style="min-width: 250px;">
														<option value="T" <c:if test="${item.regular == 'T' }">selected</c:if>>是</option>
														<option value="F" <c:if test="${item.regular == 'F' }">selected</c:if>>否</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="freightPartId">报价单位</label>
												</td>
												<td>
													  
													<select id="freightPartId" name="freightPartId" class="form-control required">
													<c:forEach items="${freightParts}" var="freightPart">
													<option value="${freightPart.id }" <c:if test="${item.freightPart.id == freightPart.id or param.freightPartId == freightPart.id}">selected</c:if>>${freightPart.partName}</option>
													</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="boxCondition">关联合同</label>
												</td>
												<td>
													<select id="freightPactId" name="freightPactId" class="form-control">
													<option value="">无</option>
													<c:forEach items="${freightPacts}" var="freightPact">
													<option value="${freightPact.id }" <c:if test="${item.freightPact.id == freightPact.id }">selected</c:if>>${freightPact.pactNumber}</option>
													</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="moneyCount">系统价格</label>
												</td>
												<td>
													<input id="moneyCount" type="text" name="moneyCount" value="${item.moneyCount}" 
													size="40" minlength="2" maxlength="50" class="form-control required number" >
												</td>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													<select id="currency" name="currency" class="form-control required" >
														<option value="人民币" <c:if test="${item.currency == '人民币'}" >selected</c:if>>人民币</option>
														<option value="美元" <c:if test="${item.currency == '美元'}" >selected</c:if>>美元</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="countUnit">计价单位</label>
												</td>
												<td>
													<select id="countUnit" name="countUnit" class="form-control required" >
														<option value="票" <c:if test="${item.countUnit == '票'}" >selected</c:if>>票</option>
														<option value="20'GP" <c:if test="${item.countUnit == '20\\'GP'}" >selected</c:if>>20'GP</option>
														<option value="40'GP" <c:if test="${item.countUnit == '40\\'GP'}" >selected</c:if>>40'GP</option>
														<option value="40'HC" <c:if test="${item.countUnit == '40\\'HC'}" >selected</c:if>>40'HC</option>
														<option value="20'HC" <c:if test="${item.countUnit == '20\\'HC'}" >selected</c:if>>20'HC</option>
														<option value="20'FR" <c:if test="${item.countUnit == '20\\'FR'}" >selected</c:if>>20'FR</option>
														<option value="40'FR" <c:if test="${item.countUnit == '40\\'FR'}" >selected</c:if>>40'FR</option>
														<option value="20'RF" <c:if test="${item.countUnit == '20\\'RF'}" >selected</c:if>>20'RF</option>
														<option value="40'RF" <c:if test="${item.countUnit == '40\\'RF'}" >selected</c:if>>40'RF</option>
														<option value="45'HC" <c:if test="${item.countUnit == '45\\'HC'}" >selected</c:if>>45'HC</option>
														<option value="20'OT" <c:if test="${item.countUnit == '20\\'OT'}" >selected</c:if>>20'OT</option>
														<option value="40'OT" <c:if test="${item.countUnit == '40\\'OT'}" >selected</c:if>>40'OT</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="fasInvocieTypeId">发票票种</label>
												</td>
												<td>
													<select id="fasInvoiceTypeId" name="fasInvoiceTypeId" class="form-control required">
													<c:forEach items="${fasInvoiceTypes}" var="fasInvoiceType">
													<option value="${fasInvoiceType.id }" <c:if test="${item.fasInvoiceType.id == fasInvoiceType.id }">selected</c:if>>${fasInvoiceType.typeName}</option>
													</c:forEach>
													</select>
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
