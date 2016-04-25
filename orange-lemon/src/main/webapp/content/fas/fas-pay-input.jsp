<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>付款申请书信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>付款申请书信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fas-pay-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <input id="userId" type="hidden" name="userId" value="${userSession.id}">
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">付款申请书信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="freightPartId">收款单位</label>
												</td>
												<td>
													<select id="freightPartId" name="freightPartId" class="form-control required">
														<c:forEach items="${freightParts}" var="freightPart">
															<option value="${freightPart.id}">${freightPart.partName}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="payType">付款方式</label>
												</td>
												<td>
													<select id="payType" name="payType" class="form-control required">
														<option value="电汇">电汇</option>
														<option value="转账">转账</option>
														<option value="现金">现金</option>
														<option value="承兑汇票">承兑汇票</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="proposerName">申请人</label>
												</td>
												<td>
													<input id="proposerName" type="text" value="${userSession.displayName}" 
													readonly="readonly"class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="descn">申请时间</label>
												</td>
												<td>
													<input id="applyTime" type="text" name="applyTime" 
													value="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													class="form-control required" readonly="readonly">
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="payFor">付款内容</label>
												</td>
												<td colspan="3">
													<textarea id="payFor" name="payFor" class="form-control required" style="min-height:120px;">${item.payFor}</textarea>
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
