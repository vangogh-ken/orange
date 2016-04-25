<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>银行账户信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>银行账户信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fas-account-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">银行账户信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="freightPartName">单位名称</label>
												</td>
												<td>
													<div class="input-icon listPicker right">
													  <i class="fa fa-ellipsis-h statementPartModal-add-on" urlAttr='${ctx}/fre/fre-part-all.do' style="cursor: pointer;"></i>
													  <input id="freightPartName" type="text" class="form-control required" value="${item.freightPart.partName}" readonly="readonly">
													  <input id="freightPartId" type="hidden" name="freightPartId" value="${item.freightPart.id}">
												    </div>
												</td>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													<select id="currency" name="currency" class="form-control required ">
														<option value=""></option>
														<option value="人民币" <c:if test="${item.currency == '人民币' }">selected</c:if> >人民币</option>
														<option value="美元" <c:if test="${item.currency == '美元' }">selected</c:if> >美元</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="bankName">银行名称</label>
												</td>
												<td>
													<input id="bankName" type="text" name="bankName" value="${item.bankName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="bankAdress">银行地址</label>
												</td>
												<td>
													<input id="bankAdress" type="text" name="bankAdress" value="${item.bankAdress}" 
													class="form-control required " >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="accountNumber">银行账号</label>
												</td>
												<td colspan="3">
													<input id="accountNumber" type="text" name="accountNumber" value="${item.accountNumber}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>启用</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>禁用</option>
														<option value="公司账户" <c:if test="${item.status == '公司账户' }">selected</c:if>>公司账户</option>
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
   	            },
   	            
   	         	freightPartId: {
	                remote: {
	                    url: 'fas-account-check-currency.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        },
	                        </c:if>
	                        currency: function(){
	                        	 return $('#currency').val();
	                        }
	                    }
	                }
	            },
	            
	            currency: {
	                remote: {
	                    url: 'fas-account-check-currency.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        },
	                        </c:if>
	                        freightPartId: function(){
	                        	 return $('#freightPartId').val();
	                        }
	                    }
	                }
	            }
   	        },
   	        messages: {
   	        	accountNumber: {
   	                remote: "存在重复"
   	            },
   	            
   	         	freightPartId: {
	                remote: "存在重复"
	            },
	            
	            currency: {
   	                remote: "存在重复"
   	            }
   	            
   	        }
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
