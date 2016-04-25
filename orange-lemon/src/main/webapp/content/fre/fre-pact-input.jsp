<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>合同信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>合同信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-pact-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">合同信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="pactNumber">合同编号</label>
												</td>
												<td>
													<input id="pactNumber" type="text" name="pactNumber" value="${item.pactNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="pactTitle">合同名称</label>
												</td>
												<td>
													<input id="pactTitle" type="text" name="pactTitle" value="${item.pactTitle}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="pactType">合同类型</label>
												</td>
												<td>
													<input id="pactType" type="text" name="pactType" value="${item.pactType}" 
													class="form-control required dictionary" 
													vAttrId="5a48785a-55d7-11e4-bdcd-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="partA">甲方</label>
												</td>
												<td>
													<input id="partA" type="text" name="partA" value="${item.partA}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="partB">乙方</label>
												</td>
												<td>
													<input id="partB" type="text" name="partB" value="${item.partB}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="partC">丙方</label>
												</td>
												<td>
													<input id="partC" type="text" name="partC" value="${item.partC}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="signDate">签字日期</label>
												</td>
												<td>
													<input id="signDate" type="text" name="signDate" 
													value="<fmt:formatDate value="${item.signDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="effectDate">生效日期</label>
												</td>
												<td>
													<input id="effectDate" type="text" name="effectDate" 
													value="<fmt:formatDate value="${item.effectDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control required datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="cutOffDate">截止日期</label>
												</td>
												<td>
													<input id="cutOffDate" type="text" name="cutOffDate" 
													value="<fmt:formatDate value="${item.cutOffDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="transactor">经办人</label>
												</td>
												<td>
													<input id="transactor" type="text" name="transactor" value="${item.transactor}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="payType">结算方式</label>
												</td>
												<td>
													<input id="payType" type="text" name="payType" value="${item.payType}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="defaultSettleDays">账期</label>
												</td>
												<td>
													<input id="defaultSettleDays" type="text" name="defaultSettleDays" value="${item.defaultSettleDays}" 
													class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td colspan="3">
													<select id="status" name="status" class="form-control required">
														<option value="有效" <c:if test="${item.status == '有效' }">selected</c:if>>有效</option>
														<option value="已过期" <c:if test="${item.status == '已过期' }">selected</c:if>>已过期</option>
														<option value="已作废" <c:if test="${item.status == '已作废' }">selected</c:if>>已作废</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="pactContent">备注</label>
												</td>
												<td colspan="3">
													<textarea id="pactContent" name="pactContent" class="form-control required" style="min-height:200px;">${item.pactContent}</textarea>
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
