<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>税务发票信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>税务发票信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fas-invoice-save-batch.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">税务发票信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="fasInvoiceTypeId">票种</label>
												</td>
												<td>
													<select id="fasInvoiceTypeId" name="fasInvoiceTypeId" class="form-control required">
													<c:forEach items="${fasInvoiceTypes}" var="fasInvoiceType">
													<option value="${fasInvoiceType.id }" <c:if test="${item.fasInvoiceType.id == fasInvoiceType.id }">selected</c:if>>${fasInvoiceType.typeName}</option>
													</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="claimTime">领取发票时间</label>
												</td>
												<td>
													<input id="claimTime" type="text" name="claimTime" value="${item.claimTime}" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="startInvoiceNumber">起始发票号</label>
												</td>
												<td>
													<input id="startInvoiceNumber" type="text" name="startInvoiceNumber" value="${item.startInvoiceNumber}" 
													size="40" minlength="2" maxlength="10" class="form-control required number" >
												</td>
												<td>
													<label class="td-label" for="endInvoiceNumber">终止发票号</label>
												</td>
												<td>
													<input id="endInvoiceNumber" type="text" name="endInvoiceNumber" value="${item.endInvoiceNumber}" 
													size="40" minlength="2" maxlength="10" class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<textarea id="descn" name="descn" class="form-control required" style="min-height: 200px;">${item.descn}</textarea>
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
            /**,
            rules: {
            	typeName: {
   	                remote: {
   	                    url: 'fre-action-type-check-typename.do',
   	                    data: {
   	                        <c:if test="${item.id != null}">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                        </c:if>
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	typeName: {
   	                remote: "该类型已存在"
   	            }
   	        }
   	        **/
        });
    });
    
    </script>
  </body>

</html>
