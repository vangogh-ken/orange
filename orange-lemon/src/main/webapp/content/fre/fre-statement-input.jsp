<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>对账信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>对账信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-statement-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">对账信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="statementNumber">对账单编号</label>
												</td>
												<td>
													<input id="statementNumber" type="text" name="statementNumber" value="${item.statementNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="freightPartName">收付单位</label>
												</td>
												<td>
												    <select id="freightPartId" name="freightPartId" class="form-control required ">
														<c:forEach items="${freightParts}" var="freightPart">
														<option value="${freightPart.id}" 
															<c:if test="${item.freightPartB.id == freightPart.id }">selected</c:if> >${freightPart.partName}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="incomeOrExpense">收/付</label>
												</td>
												<td>
													<select id="incomeOrExpense" name="incomeOrExpense" class="form-control required">
														<option value="收" <c:if test="${item.incomeOrExpense == '收'}">selected</c:if> >收</option>
														<option value="付" <c:if test="${item.incomeOrExpense == '付'}">selected</c:if> >付</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
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
    </script>
  </body>

</html>
