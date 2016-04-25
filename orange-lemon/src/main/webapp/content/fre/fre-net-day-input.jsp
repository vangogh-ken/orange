<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>账期信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>账期信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-net-day-save.do?operationMode=STORE" class="form-horizontal" onsubmit="return false;">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="6">账期信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="freightPartName">账期单位</label>
												</td>
												<td>
													<div class="input-icon listPicker right">
													  <i class="fa fa-ellipsis-h statementPartModal-add-on" urlAttr='${ctx}/fre/fre-part-all.do' style="cursor: pointer;"></i>
													  <input id="freightPartName" type="text" class="form-control required" value="${item.freightPart.partName}" readonly="readonly">
													  <input id="freightPartId" type="hidden" name="freightPartId" value="${item.freightPart.id}">
												    </div>
												</td>
												<td>
													<label class="td-label" for="incomeOrExpense">收/付</label>
												</td>
												<td>
													<select id="incomeOrExpense" name="incomeOrExpense" class="form-control required">
														<option value=""></option>
														<option value="收" <c:if test="${item.incomeOrExpense == '收'}">selected</c:if> >收</option>
														<option value="付" <c:if test="${item.incomeOrExpense == '付'}">selected</c:if> >付</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="currency">币种</label>
												</td>
												<td>
													<select id="currency" name="currency" class="form-control required">
														<option value=""></option>
														<option value="人民币" <c:if test="${item.currency == '人民币'}">selected</c:if> >人民币</option>
														<option value="美元" <c:if test="${item.currency == '美元'}">selected</c:if> >美元</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="regular">是否为固定账期</label>
												</td>
												<td>
													<select id="regular" name="regular" class="form-control required">
														<option value="F" <c:if test="${item.regular == 'F'}">selected</c:if> >否</option>
														<option value="T" <c:if test="${item.regular == 'T'}">selected</c:if> >是</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="period">账期天数</label>
												</td>
												<td>
													<select id="period" name="period" class="form-control number">
														<option value="0"></option>
														<option value="15" <c:if test="${item.period == 15}">selected</c:if> >15</option>
														<option value="30" <c:if test="${item.period == 30}">selected</c:if> >30</option>
														<option value="45" <c:if test="${item.period == 45}">selected</c:if> >45</option>
														<option value="60" <c:if test="${item.period == 60}">selected</c:if> >60</option>
														<option value="90" <c:if test="${item.period == 90}">selected</c:if> >90</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="delayMonth">延迟月份数</label>
												</td>
												<td>
													<select id="delayMonth" name="delayMonth" class="form-control number">
														<option value="0"></option>
														<option value="1" <c:if test="${item.delayMonth == 1}">selected</c:if> >1</option>
														<option value="2" <c:if test="${item.delayMonth == 2}">selected</c:if> >2</option>
														<option value="3" <c:if test="${item.delayMonth == 3}">selected</c:if> >3</option>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="regularDay">固定日期</label>
												</td>
												<td>
													<select id="regularDay" name="regularDay" class="form-control number">
														<option value="0"></option>
														<option value="10" <c:if test="${item.regularDay == 10}">selected</c:if> >10</option>
														<option value="15" <c:if test="${item.regularDay == 15}">selected</c:if> >15</option>
														<option value="20" <c:if test="${item.regularDay == 20}">selected</c:if> >20</option>
														<option value="30" <c:if test="${item.regularDay == 30}">selected</c:if> >30</option>
													</select>
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
													<textarea id="descn" name="descn" class="form-control required" maxlength="256" style="min-height: 80px;">${item.descn == null ? '无' : item.descn }</textarea>
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
                if(($('#regular').val() == 'T' && $('#delayMonth').val() != '' && $('#regularDay').val() != '')
                		|| ($('#regular').val() == 'F' && $('#period').val() != '')){
                	form.submit();
                }else{
                	box.modal('hide');
                	alert('规则：\n固定账期必须填延迟月份数和固定日期。\n 非固定账期必须填写账期天数。');
                	return;
                }
            },
            errorClass: 'validate-error',
            rules: {
            	currency: {
   	                remote: {
   	                    url: 'fre-net-day-check-currency.do',
   	                    data: {
   	                        <c:if test="${item.id != null}">
   	                        id: function() {
   	                            return $('#id').val();
   	                        },
   	                        </c:if>
   	                     	freightPartId: function() {
	                            return $('#freightPartId').val();
	                        },
	                        incomeOrExpense: function() {
	                            return $('#incomeOrExpense').val();
	                        },
   	                    }
   	                }
   	            },
   	            
   	         	incomeOrExpense: {
	                remote: {
	                    url: 'fre-net-day-check-currency.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        },
	                        </c:if>
	                     	freightPartId: function() {
	                            return $('#freightPartId').val();
	                        },
	                        currency: function() {
	                            return $('#currency').val();
	                        },
	                    }
	                }
	            }
   	        },
   	        messages: {
   	        	currency: {
   	                remote: "存在重复"
   	            },
   	         	incomeOrExpense: {
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
