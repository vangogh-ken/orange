<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>封号信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>封号信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-seal-save-batch.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">封号信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="startSealNumber">起始号</label>
												</td>
												<td>
													<input id="startSealNumber" type="text" name="startSealNumber" value="${item.startSealNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required number" >
												</td>
												<td>
													<label class="td-label" for=endSealNumber>结尾号</label>
												</td>
												<td>
													<input id="endSealNumber" type="text" name="endSealNumber" value="${item.endSealNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required number" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="sealBelong">封属</label>
												</td>
												<td>
													<input id="sealBelong" type="text" name="sealBelong" value="${item.sealBelong}" 
													size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
													vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="sealType">类型</label>
												</td>
												<td>
													<select id="sealType" name="sealType" class="form-control required">
														<option value="海船封"<c:if test="${item.status == '海船封'}">selected</c:if> >海船封</option>
														<option value="铁路封"<c:if test="${item.status == '铁路封'}">selected</c:if> >铁路封</option>
														<option value="其他封"<c:if test="${item.status == '其他封'}">selected</c:if> >其他封</option>
													</select>
												</td>
											</tr>
											<!--  
											<tr>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td colspan="3">
													<select id="status" name="status" class="form-control required">
														<option value="未使用"<c:if test="${item.status == '未使用'}">selected</c:if> >未使用</option>
														<option value="已使用"<c:if test="${item.status == '已使用'}">selected</c:if> >已使用</option>
													</select>
												</td>
											</tr>
											-->
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="3">
													<textarea id="descn" name="descn" class="form-control required" style="min-height: 100px;">${item.descn == null ? '无' : item.descn}</textarea>
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
