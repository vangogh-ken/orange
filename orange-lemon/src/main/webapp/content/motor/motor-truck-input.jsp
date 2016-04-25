<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>车辆信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>车辆信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="motor-truck-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">车辆信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="truckCategory">类别</label>
												</td>
												<td>
													<input id="truckCategory" type="text" name="truckCategory" value="${item.truckCategory}" 
													size="40" minlength="1" maxlength="50" class="form-control required dictionary" 
													vAttrId="1c5e8ff0-2619-11e5-a9c8-a4db305e5cc5"
													>
												</td>
												<td>
													<label class="td-label" for="truckType">车型</label>
												</td>
												<td>
													<input id="truckType" type="text" name="truckType" value="${item.truckType}" 
													size="40" minlength="1" maxlength="50" class="form-control required " 
													>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="headstockNumber">拖车号</label>
												</td>
												<td>
													<input id="headstockNumber" type="text" name="headstockNumber" value="${item.headstockNumber}" 
													class="form-control required " 
													>
												</td>
												<td>
													<label class="td-label" for="trailerNumber">挂车号</label>
												</td>
												<td>
													<input id="trailerNumber" type="text" name="trailerNumber" value="${item.trailerNumber}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="headstockFund">拖车购车额</label>
												</td>
												<td>
													<input id="headstockFund" type="text" name="headstockFund" value="${item.headstockFund}" 
													class="form-control required number" 
													>
												</td>
												<td>
													<label class="td-label" for="trailerFund">挂车购车额</label>
												</td>
												<td>
													<input id="trailerFund" type="text" name="trailerFund" value="${item.trailerFund}" 
													class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="headstockDepreciation">拖车月折旧</label>
												</td>
												<td>
													<input id="headstockDepreciation" type="text" name="headstockDepreciation" value="${item.headstockDepreciation}" 
													class="form-control required number" 
													>
												</td>
												<td>
													<label class="td-label" for="trailerDepreciation">挂车月折旧</label>
												</td>
												<td>
													<input id="trailerDepreciation" type="text" name="trailerDepreciation" value="${item.trailerDepreciation}" 
													class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for=purchaseTime>购进时间</label>
												</td>
												<td>
													<input id="purchaseTime" type="text" name="purchaseTime"
													value='<fmt:formatDate value="${item.purchaseTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="annualTime">年审时间</label>
												</td>
												<td>
													<input id="annualTime" type="text" name="annualTime"
													value='<fmt:formatDate value="${item.annualTime}" pattern="yyyy-MM-dd"/>'
													class="form-control required datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="manufacturer">生产厂家</label>
												</td>
												<td>
													<input id="manufacturer" type="text" name="manufacturer" value="${item.manufacturer}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>启用</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>禁用</option>
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
            errorClass: 'validate-error',
            rules: {
            	licenseNumber: {
   	                remote: {
   	                    url: 'motor-truck-check-licensenumber.do',
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
   	        	licenseNumber: {
   	                remote: "存在重复"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
