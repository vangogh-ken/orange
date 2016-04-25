<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>派单信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>派单信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="motor-dispatch-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="8">派单信息</th></tr>
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
													class="form-control required dictionary" 
													vClsId="8f72a194-2382-11e5-a9c8-a4db305e5cc5"
													vColumn="DISPLAY_NAME">
												</td>
												<td>
													<label class="td-label" for="dispatchNumber">派单号</label>
												</td>
												<td>
													<input id="dispatchNumber" type="text" name="dispatchNumber" value="${item.dispatchNumber}" 
													class="form-control" readonly="readonly">
												</td>
												<td>
													<label class="td-label" for="orderNumber">订单号</label>
												</td>
												<td>
													<input id="orderNumber" type="text" name="orderNumber" value="${item.orderNumber}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="delegatePart">委托单位</label>
												</td>
												<td>
													<input id="delegatePart" type="text" name="delegatePart" value="${item.delegatePart}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="delegateTime">委托日期</label>
												</td>
												<td>
													<input id="delegateTime" type="text" name="delegateTime" 
													value='<fmt:formatDate value="${item.delegateTime}" pattern="yyyy-MM-dd"/>' 
													class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="cargoName">品名</label>
												</td>
												<td>
													<input id="cargoName" type="text" name="cargoName" value="${item.cargoName}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="cargoUnit">件数</label>
												</td>
												<td>
													<input id="cargoUnit" type="text" name="cargoUnit" value="${item.cargoUnit}" 
													class="form-control" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="cargoWeight">重量</label>
												</td>
												<td>
													<input id="cargoWeight" type="text" name="cargoWeight" value="${item.cargoWeight}" 
													class="form-control number" >
												</td>
												<td>
													<label class="td-label" for="cargoCapacity">体积</label>
												</td>
												<td>
													<input id="cargoCapacity" type="text" name="cargoCapacity" value="${item.cargoCapacity}" 
													class="form-control number" >
												</td>
												<td>
													<label class="td-label" for="boxType">箱型</label>
												</td>
												<td>
													<input id="boxType" type="text" name="boxType" value="${item.boxType}" 
													class="form-control required dictionary" 
													vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="boxCount">箱量</label>
												</td>
												<td>
													<input id="boxCount" type="text" name="boxCount" value="${item.boxCount}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="boxNumber">箱号</label>
												</td>
												<td colspan="3">
													<input id="boxNumber" type="text" name="boxNumber" value="${item.boxNumber}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="pickAddress">提箱地址</label>
												</td>
												<td>
													<input id="pickAddress" type="text" name="pickAddress" value="${item.pickAddress}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="pickTime">提箱时间</label>
												</td>
												<td>
													<input id="pickTime" type="text" name="pickTime" 
													value="<fmt:formatDate value="${item.pickTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
													class="form-control required datetimepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="loadAddress">装/卸箱地址</label>
												</td>
												<td>
													<input id="loadAddress" type="text" name="loadAddress" value="${item.loadAddress}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="loadTime">装/卸箱时间</label>
												</td>
												<td>
													<input id="loadTime" type="text" name="loadTime" 
													value="<fmt:formatDate value="${item.loadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
													class="form-control required datetimepicker" >
												</td>
												<td>
													<label class="td-label" for="returnAddress">还箱地址</label>
												</td>
												<td>
													<input id="returnAddress" type="text" name="returnAddress" value="${item.returnAddress}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="returnTime">还箱时间</label>
												</td>
												<td>
													<input id="returnTime" type="text" name="returnTime" 
													value="<fmt:formatDate value="${item.returnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
													class="form-control required datetimepicker" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="departure">出发地</label>
												</td>
												<td>
													<input id="departure" type="text" name="departure" value="${item.departure}" 
													class="form-control required dictionary" 
													vAttrId="3585f1ee-2eb8-11e5-a9c8-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="destination">目的地</label>
												</td>
												<td>
													<input id="destination" type="text" name="destination" value="${item.destination}" 
													class="form-control required dictionary" 
													vAttrId="602ba41f-247f-11e5-a9c8-a4db305e5cc5">
												</td>
												<td>
													<label class="td-label" for="dispatchDeduct">提成金额</label>
												</td>
												<td colspan="3">
													<input id="dispatchDeduct" type="text" name="dispatchDeduct" value="${item.dispatchDeduct}" 
													class="form-control required number" >
												</td>
											</tr>
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
   	                    url: 'motor-dispatch-to-validate-boxnumber.do',
   	                    data: {
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	boxNumber: {
   	                remote: "校验失败"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
