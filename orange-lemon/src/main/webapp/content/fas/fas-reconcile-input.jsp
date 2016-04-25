<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>销账记录信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>销账记录信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-box-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">销账记录信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="boxSource">集装箱来源</label>
												</td>
												<td colspan="3">
													<select id="boxSource" name="boxSource" class="form-control required">
														<option value="自有箱" <c:if test="${item.boxSource == '自有箱' }">selected</c:if>>自有箱</option>
														<option value="外借箱" <c:if test="${item.boxSource == '外借箱' }">selected</c:if>>外借箱</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="boxNumber">箱号</label>
												</td>
												<td>
													<input id="boxNumber" type="text" name="boxNumber" value="${item.boxNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="boxType">箱型</label>
												</td>
												<td>
													<input id="boxType" type="text" name="boxType" value="${item.boxType}" 
													vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="boxBelong">箱属</label>
												</td>
												<td>
													<input id="boxBelong" type="text" name="boxBelong" value="${item.boxBelong}" 
													vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5"
													class="form-control required dictionary" >
												</td>
												<td>
													<label class="td-label" for="boxCondition">箱况</label>
												</td>
												<td>
													<input id="boxCondition" type="text" name="boxCondition" value="${item.boxCondition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn}" 
													size="40" minlength="1" maxlength="2" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>是</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>否</option>
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
            errorClass: 'validate-error',
            rules: {
            	boxNumber: {
   	                remote: {
   	                    url: 'fre-box-check-boxnumber.do',
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
   	        	boxNumber: {
   	                remote: "集装箱号校验失败或存在重复"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
