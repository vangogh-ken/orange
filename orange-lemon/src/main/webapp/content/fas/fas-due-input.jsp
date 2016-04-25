<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>银行收款信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>银行收款信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fas-due-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">银行收款信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="payPartId">付款单位</label>
												</td>
												<td>
												    <select id="payPartId" name="payPartId" class="form-control required " onchange="updatePayAccount();">
														<c:forEach items="${payParts}" var="payPart">
														<option value="${payPart.id}" 
															<c:if test="${item.payPart.id == payPart.id }">selected</c:if> >${payPart.partName}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="payAccountId">付款账户</label>
												</td>
												<td>
													<select id="payAccountId" name="payAccountId" class="form-control input-medium">
														<option value=""></option>
														<c:if test="${item.payAccount != null}">
															<option value="${item.payAccount.id}">${item.payAccount.currency}_${item.payAccount.accountNumber}</option>
														</c:if>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													<select id="currency" name="currency" class="form-control required">
														<option value="人民币" <c:if test="${item.currency == '人民币' }">selected</c:if> >人民币</option>
														<option value="美元" <c:if test="${item.currency == '美元' }">selected</c:if> >美元</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="dueCount">金额</label>
												</td>
												<td>
													<input id="dueCount" type="text" name="dueCount" value="${item.dueCount}" 
													class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="exchangeRate">汇率</label>
												</td>
												<td>
													<input id="exchangeRate" type="text" name="exchangeRate" value="${item.exchangeRate == null ? 0 : item.exchangeRate}" 
													class="form-control number" placeholder="默认为系统汇率">
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<input id="status" type="text" name="status" value="${item.status == null ? '未提交' : item.status}"  readonly="readonly"
													class="form-control required " >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="dueAccountId">我方收款账户</label>
												</td>
												<td>
													<select id="dueAccountId" name="dueAccountId" class="form-control required ">
														<c:forEach items="${ownAccounts}" var="ownAccount">
														<option value="${ownAccount.id}" 
															<c:if test="${item.dueAccount.id == ownAccount.id }">selected</c:if> >
															${ownAccount.currency}_${ownAccount.accountNumber}_${ownAccount.bankName}
															</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="dueTime">银行收款时间</label>
												</td>
												<td>
													<input id="dueTime" type="text" name="dueTime" 
													value="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													class="form-control required datepicker" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
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
            errorClass: 'validate-error'
            /**,
            rules: {
            	accountNumber: {
   	                remote: {
   	                    url: 'fas-account-check-accountnumber.do',
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
   	        	accountNumber: {
   	                remote: "存在重复"
   	            }
   	        }
   	        **/
        });
    });
    
    /**
    $(function() {
		new createListPicker({
			title:'单位列表', //标题
			modalId: 'payPartModal',//modalID
			formId:'payPartForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','名称'],//列表头 数组
			tbody:['id', 'partName'],//字段数据 数组
			tableId:'payPartTable',//表ID
			multiple: false,//是否多选
			canSelect:true,//是否可返回值
			valueType:'id',//需要的取值字段
			valueInput:'payPartId',//取值填至何处
			displayType:'partName',//显示的取值字段
			displayInput:'freightPartName'//显示填至何处
		});
	});
    **/
    function updatePayAccount(){
    	var url = 'fas-account-get-by-part.do?payPartId=' + $('#payPartId').val();
    	$.post(url, function(data){
    		var html = '';
    		if(data != null){
    			$.each(data, function(i, item){
    				html += '<option value="' + item.id + '">' + item.currency + '_' + item.accountNumber + '</option>'
    			});	
    			
    			$('#payAccountId').html(html);
    		}
    	});
    }
    </script>
  </body>

</html>
