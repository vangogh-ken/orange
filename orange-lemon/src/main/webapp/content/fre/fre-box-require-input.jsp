<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>箱需信息(维护)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>箱需信息(维护)</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-box-require-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <input id="freightOrderId" type="hidden" name="freightOrderId" value="${item.freightOrder.id}">
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">箱需信息(订单:${item.freightOrder == null ? '' : item.freightOrder.orderNumber})</th></tr>
										</thead>
										<tbody>
											<c:if test="${item.status != '已选箱' &&  item.status != '已放箱'}">
											<tr>
												<td>
													<label class="td-label" for="beginStation">发站</label>
												</td>
												<td>
													<input id="beginStation" type="text" name="beginStation" value="${item.beginStation}" 
													vAttrId="5a4875ab-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
												<td>
													<label class="td-label" for="arriveStation">到站</label>
												</td>
												<td>
													<input id="arriveStation" type="text" name="arriveStation" value="${item.arriveStation}" 
													vAttrId="5a4875ab-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="boxType">箱型</label>
												</td>
												<td>
													<input id="boxType" type="text" name="boxType" value="${item.boxType}" 
													vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
												<td>
													<label class="td-label" for="boxBelong">箱属</label>
												</td>
												<td>
													<input id="boxBelong" type="text" name="boxBelong" value="${item.boxBelong}" 
													vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
											</tr>
											</c:if>
											<tr>
												<c:if test="${item.status != '已选箱' &&  item.status != '已放箱'}">
												<td>
													<label class="td-label" for="boxCondition">箱况</label>
												</td>
												<td>
													<input id="boxCondition" type="text" name="boxCondition" value="${item.boxCondition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												</c:if>
												<!--  
												<td>
													<label class="td-label" for="boxCount">数量</label>
												</td>
												<td>
													<input id="boxCount" type="text" name="boxCount" value="${item.boxCount}" 
													size="40" minlength="1" maxlength="2" class="form-control required number" >
												</td>
												-->
												<td>
													<label class="td-label" for="blNo">提单号</label>
												</td>
												<td>
													<input id="blNo" type="text" name="blNo" value="${item.blNo}" 
													size="40" minlength="1" maxlength="64" class="form-control required">
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
