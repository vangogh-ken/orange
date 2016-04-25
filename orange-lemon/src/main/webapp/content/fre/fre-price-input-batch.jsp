<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>成本信息(按箱批量)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>成本信息(按箱批量)</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-price-save-batch.do?operationMode=STORE" class="form-horizontal">
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">成本信息(按箱批量)</th></tr>
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
													<label class="td-label" for="moneyCount20">小柜价格</label>
												</td>
												<td>
													<input id="moneyCount20" type="text" name="moneyCount20" value="${item.moneyCount20}" 
													size="40" minlength="1" maxlength="50" class="form-control required number" >
												</td>
												<td>
													<label class="td-label" for="moneyCount40">大柜价格</label>
												</td>
												<td>
													<input id="moneyCount40" type="text" name="moneyCount40" value="${item.moneyCount40}" 
													size="40" minlength="1" maxlength="50" class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													<select id="currency" name="currency" class="form-control required" >
														<option value="人民币" <c:if test="${item.currency == '人民币'}" >selected</c:if>>人民币</option>
														<option value="美元" <c:if test="${item.currency == '美元'}" >selected</c:if>>美元</option>
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
    
    /**
    $(function() {
		new createListPicker({
			title:'单位列表', //标题
			modalId: 'statementPartModal',//modalID
			formId:'statementPartForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','名称'],//列表头 数组
			tbody:['id', 'partName'],//字段数据 数组
			tableId:'statementPartTable',//表ID
			multiple: false,//是否多选
			canSelect:true,//是否可返回值
			valueType:'id',//需要的取值字段
			valueInput:'freightPartId',//取值填至何处
			displayType:'partName',//显示的取值字段
			displayInput:'freightPartName'//显示填至何处
		});
	});
    **/
    </script>
  </body>

</html>
