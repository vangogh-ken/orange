<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>派车信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>派车信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="motor-journey-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="8">派车信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="motorcadeTruck">车牌号</label>
												</td>
												<td>
													<input id="motorcadeTruck" type="text" name="motorcadeTruck" value="${item.motorcadeTruck}" 
													class="form-control required dictionary" 
													vClsId="869797eb-2382-11e5-a9c8-a4db305e5cc5"
													vColumn="HEADSTOCK_NUMBER">
												</td>
												<td>
													<label class="td-label" for="motorcadeDriver">司机</label>
												</td>
												<td>
													<input id="motorcadeDriver" type="text" name="motorcadeDriver" value="${item.motorcadeDriver}" 
													class="form-control" readonly="readonly">
												</td>
												<td>
													<label class="td-label" for="predictTime">预计还车时间</label>
												</td>
												<td colspan="3">
													<input id="predictTime" type="text" name="predictTime" 
													value='<fmt:formatDate value="${item.predictTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' 
													class="form-control required datetimepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="journeyNumber">编号</label>
												</td>
												<td>
													<input id="journeyNumber" type="text" name="journeyNumber" value="${item.journeyNumber}" 
													class="form-control" readonly="readonly">
												</td>
												<td>
													<label class="td-label" for="journeyTime">时间</label>
												</td>
												<td>
													<input id="journeyTime" type="text" name="journeyTime" 
													value='<fmt:formatDate value="${item.journeyTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control " readonly="readonly">
												</td>
												<td>
													<label class="td-label" for="destination">目的地</label>
												</td>
												<td>
													<input id="destination" type="text" name="destination" value="${item.destination}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="journeyItem">事由</label>
												</td>
												<td>
													<input id="journeyItem" type="text" name="journeyItem" value="${item.journeyItem}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="outKey">取钥匙地</label>
												</td>
												<td>
													<input id="outKey" type="text" name="outKey" value="${item.outKey}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="outPark">取车地</label>
												</td>
												<td>
													<input id="outPark" type="text" name="outPark" value="${item.outPark}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="outTime">出车时间</label>
												</td>
												<td>
													<input id="outTime" type="text" name="outTime" 
													value='<fmt:formatDate value="${item.outTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' 
													class="form-control required datetimepicker" >
												</td>
												<td>
													<label class="td-label" for="outKilometerCount">出车时公里数</label>
												</td>
												<td>
													<input id="outKilometerCount" type="text" name="outKilometerCount" value="${item.outKilometerCount}" 
													class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="returnKey">放钥匙地</label>
												</td>
												<td>
													<input id="returnKey" type="text" name="returnKey" value="${item.returnKey}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="returnPark">还车地</label>
												</td>
												<td>
													<input id="returnPark" type="text" name="returnPark" value="${item.returnPark}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="returnTime">还车时间</label>
												</td>
												<td>
													<input id="returnTime" type="text" name="returnTime" 
													value='<fmt:formatDate value="${item.returnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' 
													class="form-control required datetimepicker" >
												</td>
												<td>
													<label class="td-label" for="returnKilometerCount">还车时公里数</label>
												</td>
												<td>
													<input id="returnKilometerCount" type="text" name="returnKilometerCount" value="${item.returnKilometerCount}" 
													class="form-control number" >
												</td>
											</tr>
											<tr>
											<tr>
												<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td colspan="7">
													<textarea id="descn" type="text" name="descn" class="form-control required" 
													style="min-height:80px;"
													size="40" minlength="1" maxlength="128">${item.descn == null ? '无' : item.descn}</textarea>
												</td>
											</tr>
											</tr>
										</tbody>
									</table>
								</div>	
								<div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-offset-6 col-md-9">
												<c:if test="{item.status == null or item.status == '未提交'}"></c:if>
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
            	motorcadeTruck: {
   	                remote: {
   	                    url: 'motor-journey-check-truck.do',
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
   	        	motorcadeTruck: {
   	                remote: "该车暂不能借出"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
