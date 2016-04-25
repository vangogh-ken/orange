<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>司机信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>司机信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="motor-driver-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">司机信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="displayName">姓名</label>
												</td>
												<td>
													<input id="displayName" type="text" name="displayName" value="${item.displayName}" 
													size="40" minlength="2" maxlength="10" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="gender">性别</label>
												</td>
												<td>
													<select id="gender" name="gender" class="form-control required">
														<option value="男" <c:if test="${item.status == '男' }">selected</c:if>>男</option>
														<option value="女" <c:if test="${item.status == '女' }">selected</c:if>>女</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="telephone">电话</label>
												</td>
												<td>
													<input id="telephone" type="text" name="telephone" value="${item.telephone}" 
													size="40" minlength="11" maxlength="11" class="form-control required number" >
												</td>
												<td>
													<label class="td-label" for="address">地址</label>
												</td>
												<td>
													<input id="address" type="text" name="address" value="${item.address}" 
													size="40" minlength="2" maxlength="64" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="drivingNumber">驾驶证号</label>
												</td>
												<td>
													<input id="drivingNumber" type="text" name="drivingNumber" value="${item.drivingNumber}" 
													size="40" minlength="18" maxlength="18" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="quasiType">准驾车型</label>
												</td>
												<td>
													<input id="quasiType" type="text" name="quasiType" value="${item.quasiType}" 
													size="40" minlength="2" maxlength="10" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="registerTime">登记日期</label>
												</td>
												<td>
													<input id="registerTime" type="text" name="registerTime" 
													value='<fmt:formatDate value="${item.registerTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="validTime">审核日期</label>
												</td>
												<td>
													<input id="validTime" type="text" name="validTime" 
													value='<fmt:formatDate value="${item.validTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control required datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="certification">发证机关</label>
												</td>
												<td>
													<input id="certification" type="text" name="certification" value="${item.certification}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="已转正" <c:if test="${item.status == '已转正' }">selected</c:if>>已转正</option>
														<option value="试用期" <c:if test="${item.status == '试用期' }">selected</c:if>>试用期</option>
														<option value="已离职" <c:if test="${item.status == '已离职' }">selected</c:if>>已离职</option>
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
            	displayName: {
   	                remote: {
   	                    url: 'motor-driver-check-displayname.do',
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
   	        	displayName: {
   	                remote: "存在重复"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
