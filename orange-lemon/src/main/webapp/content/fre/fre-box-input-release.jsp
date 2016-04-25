<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>集装箱信息(箱管)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>集装箱信息(箱管)</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-box-save-release.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">集装箱信息(箱管)</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="boxSource">集装箱来源</label>
												</td>
												<td>
													<select id="boxSource" name="boxSource" class="form-control required">
														<option value="自管箱" <c:if test="${item.boxSource == '自管箱' }">selected</c:if>>自管箱</option>
														<option value="外管箱" <c:if test="${item.boxSource == '外管箱' }">selected</c:if>>外管箱</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="boxNumber">箱号</label>
												</td>
												<td>
													<input id="boxNumber" type="text" name="boxNumber" value="${item.boxNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
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
													<label class="td-label" for="boxCondition">箱况</label>
												</td>
												<td >
													<input id="boxCondition" type="text" name="boxCondition" value="${item.boxCondition}" 
													size="40" minlength="1" maxlength="50" class="form-control required dictionary" 
													vAttrId="5a488f04-55d7-11e4-bdcd-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="eventStatus">事件状态</label>
												</td>
												<td>
													<input id="eventStatus" type="text" name="eventStatus" value="${item.eventStatus}" 
													size="40" minlength="1" maxlength="50" class="form-control required dictionary" 
													vAttrId="8ba9c0d8-13ca-11e5-927a-a4db305e5cc5">
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td colspan="3">
													<select id="status" name="status" class="form-control required">
														<option value="可用" <c:if test="${item.status == '可用' }">selected</c:if>>可用</option>
														<option value="已用" <c:if test="${item.status == '已用' }">selected</c:if>>已用</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明<br>*如为EDI内容，请在此填写提单号</label>
												</td>
												<td colspan="3">
													<textarea id="descn" name="descn" class="form-control required" style="min-height:80px;">${item.descn == null ? '无' : item.descn}</textarea>
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
