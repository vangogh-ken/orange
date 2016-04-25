<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>客户拜访信息</title>
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
							<div class="caption"><i class="fa fa-users"></i>客户拜访信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="crm-customer-follow-save.do?operationMode=STORE" class="form-horizontal" onsubmit="return false;">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <c:set var="followerStatus" value="${(item.status == null || item.status == '未提交') && (item.follower == null || userSession.id = item.follower.id)}"></c:set>
								 <c:set var="suggestStatus" value="${item.status == '上级建议' && userSession.id != item.follower.id}"></c:set>
								 <div class="form-body">
									<table class="table-input" style="margin-left: 10%;margin-right: 10%;width: 80%;">
										<thead>
											<tr><th colspan="6">客户拜访信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="crmCustomerId">名称</label>
												</td>
												<td>
													<select name="crmCustomerId" id="crmCustomerId" class="form-control required" onchange="updateLast()" 
														<c:if test="${!followerStatus}">readonly="readonly"</c:if>>
										  				<option value="${item.crmCustomer.id}">${item.crmCustomer.customerName}</option>
										  			</select>
												</td>
												<td>
													<label class="td-label" for="contactPerson" >联系人</label>
												</td>
												<td>
													<input id="contactPerson" type="text" name="contactPerson" value="${item.contactPerson}" 
													size="40" minlength="2" maxlength="50" class="form-control required" 
													<c:if test="${!followerStatus}">readonly="readonly"</c:if>>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="contactPosition">职位</label>
												</td>
												<td>
													<input id="contactPosition" type="text" name="contactPosition" value="${item.contactPosition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" 
													<c:if test="${!followerStatus}">readonly="readonly"</c:if>>
												</td>
												<td>
													<label class="td-label" for="contactPhone">电话</label>
												</td>
												<td>
													<input id="contactPhone" type="text" name="contactPhone" value="${item.contactPhone}" 
													size="40" minlength="2" maxlength="50" class="form-control required" 
													<c:if test="${!followerStatus}">readonly="readonly"</c:if>>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="currentFollowTime">本次联系时间</label>
												</td>
												<td colspan="3">
													<input id="currentFollowTime" type="text" name="currentFollowTime" value="<fmt:formatDate value="${item.currentFollowTime == null ? now : item.currentFollowTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" 
													<c:if test="${!followerStatus}">disabled="disabled"</c:if>>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="lastFollowTime">上次联系时间</label>
												</td>
												<td>
													<input id="lastFollowTime" type="text" name="lastFollowTime" value="<fmt:formatDate value="${item.lastFollowTime == null ? now : item.lastFollowTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" 
													<c:if test="${!followerStatus}">disabled="disabled"</c:if>>
												</td>
												<td>
													<label class="td-label" for="nextFollowTime">下次联系时间</label>
												</td>
												<td>
													<input id="nextFollowTime" type="text" name="nextFollowTime" value="<fmt:formatDate value="${item.nextFollowTime == null ? now : item.nextFollowTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" 
													<c:if test="${!followerStatus}">disabled="disabled"</c:if>>
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="followContent">跟进内容</label>
												</td>
												<td colspan="3">
													<textarea id="followContent" name="followContent" class="form-control required" style="min-height:80px;" 
													<c:if test="${!followerStatus}">readonly="readonly"</c:if>>${item.followContent == null ? '无' : item.followContent}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="chancePlan">后续跟进计划</label>
												</td>
												<td colspan="3">
													<textarea id="chancePlan" name="chancePlan" class="form-control required" style="min-height:80px;" 
													<c:if test="${!followerStatus}">readonly="readonly"</c:if>>${item.chancePlan == null ? '无' : item.chancePlan}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="chanceSuggest">跟进建议</label>
												</td>
												<td colspan="3">
													<textarea id="chanceSuggest" name="chanceSuggest" class="form-control required" style="min-height:80px;" 
													<c:if test="${!suggestStatus}">readonly="readonly"</c:if> >${item.chanceSuggest == null ? '无' : item.chanceSuggest}</textarea>
												</td>
											</tr>
										</tbody>
									</table>
								</div>	
								<div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-offset-6 col-md-9">
												<!--  
												<button type="button" class="btn green" onclick="if(validateEditDomainForm()) $('#editDomainForm').submit();">保存</button>
												-->
												<c:if test="${item.status != '已归档'}">
												<button type="submit" class="btn green">保存</button>
												</c:if>
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
            /**
            ,
            rules: {
            	crmCustomerId: {
   	                remote: {
   	                    url: 'crm-customer-follow-check-follower.do',
   	                    data: {
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	crmCustomerId: {
   	                remote: "该单位跟进人非当前用户"
   	            }
   	        }
   	        **/
        });
    });
    
    //获取当前单位最近一次的跟进信息
    function updateLast(){
    	var crmCustomerId = $('#crmCustomerId').val();
    	$.post('crm-customer-follow-last-follow.do?crmCustomerId=' + crmCustomerId, function(data){
    		if(data != null){
    			$('#lastFollowTime').val(data.currentFollowTime);
    		}
    	});
    }
    </script>
  </body>

</html>
