<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>开具税务发票</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>具税务发票</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-invoice-save-release.do?operationMode=STORE" class="form-horizontal">
								  <input id="freightInvoiceId" type="hidden" name="freightInvoiceId" value="${item.id}">
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">具税务发票</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="invoiceType">票种</label>
												</td>
												<td>
													${item.fasInvoiceType.typeName}
												</td>
												<td>
													<label class="td-label" for="freightPart">受票单位</label>
												</td>
												<td>
													${item.freightPart.partName}
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													${item.currency}
												</td>
												<td>
													<label class="td-label" for="exchangeRate">汇率</label>
												</td>
												<td>
													${item.exchangeRate}
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="moneyCount">金额</label>
												</td>
												<td>
													${item.moneyCount}
												</td>
												<td>
													<label class="td-label" for="status">税务发票</label>
												</td>
												<td>
													<select id="fasInvoiceId" name="fasInvoiceId" class="form-control required">
														<c:forEach items="${fasInvoices}" var="fasInvoice">
														<option value="${fasInvoice.id}">${fasInvoice.invoiceNumber}</option>
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
