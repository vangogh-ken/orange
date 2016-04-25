<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>渠道维护信息</title>
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
							<div class="caption"><i class="fa fa-users"></i>渠道维护信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="crm-partner-follow-save.do?operationMode=STORE" class="form-horizontal" onsubmit="return false;">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left: 10%;margin-right: 10%;width: 80%;">
										<thead>
											<tr><th colspan="6">渠道维护信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="crmPartnerId">名称</label>
												</td>
												<td>
													<select name="crmPartnerId" id="crmPartnerId" class="form-control required" onchange="updateLast()">
										  				<option value=""></option>
										  				<c:forEach items="${crmPartners}" var="crmPartner">
										  				<option value="${crmPartner.id}" <c:if test="${item.crmPartner.id == crmPartner.id}">selected="selected"</c:if>>${crmPartner.partnerName}</option>
										  				</c:forEach>
										  			</select>
												</td>
												<td>
													<label class="td-label" for="contactPerson" >联系人</label>
												</td>
												<td>
													<input id="contactPerson" type="text" name="contactPerson" value="${item.contactPerson}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="contactPosition">职位</label>
												</td>
												<td>
													<input id="contactPosition" type="text" name="contactPosition" value="${item.contactPosition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="contactPhone">电话</label>
												</td>
												<td>
													<input id="contactPhone" type="text" name="contactPhone" value="${item.contactPhone}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="lastFollowTime">上次联系时间</label>
												</td>
												<td>
													<input id="lastFollowTime" type="text" name="lastFollowTime" value="${item.lastFollowTime}" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" >
												</td>
												<td>
													<label class="td-label" for="nextTime">下次联系时间</label>
												</td>
												<td>
													<input id="nextTime" type="text" name="nextTime" value="${item.nextTime}" 
													size="40" minlength="2" maxlength="50" class="form-control required datepicker" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="followContent">跟进内容</label>
												</td>
												<td colspan="3">
													<textarea id="followContent" name="followContent" class="form-control required" style="min-height:80px;">${item.descn == null ? '无' : item.followContent}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="chancePlan">后续跟进计划</label>
												</td>
												<td colspan="3">
													<textarea id="chancePlan" name="chancePlan" class="form-control required" style="min-height:80px;">${item.descn == null ? '无' : item.chancePlan}</textarea>
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
            	crmPartnerId: {
   	                remote: {
   	                    url: 'crm-partner-follow-check-follower.do',
   	                    data: {
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	crmPartnerId: {
   	                remote: "该单位跟进人非当前用户"
   	            }
   	        }
        });
    });
    
    //获取当前单位最近一次的跟进信息
    function updateLast(){
    	var crmPartnerId = $('#crmPartnerId').val();
    	$.post('crm-partner-follow-last-follow.do?crmPartnerId=' + crmPartnerId, function(data){
    		if(data != null){
    			$('#lastFollowTime').val(data.currentFollowTime);
    		}
    	});
    }
    
    /* $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'dutyUser',
    		valType:'displayName'
    	});
    });
    
    $(function() {
		new createListPicker({
			title:'客户列表', //标题
			modalId: 'crmCustomerModal',//modalID
			formId:'crmCustomerForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','客户全称', '类型'],//列表头 数组
			tbody:['id', 'nameFull','type'],//字段数据 数组
			tableId:'crmCustomerTable',//表ID
			multiple: false,//是否多选
			canSelect:true,//是否可返回值
			valueType:'nameFull',//需要的取值字段
			valueInput:'customerName'//取值填至何处
		});
	});
    
    $(document).delegate('#nextTime', 'change', function(e) {
    	validateEditDomainForm();
	});
    
    function validateEditDomainForm(){
		var contactTime = new Date($('#contactTime').val().replace('-','/').replace('-','/'));
		var nextTime = new Date($('#nextTime').val().replace('-','/').replace('-','/'));
		//alert(nextTime.getTime()-contactTime.getTime()< 1000*60*60*24*7);
		//alert('nextTime.getDay: ' + nextTime.getDay() + ' contactTime.getDay: ' +  contactTime.getDay());
		if(contactTime.getTime() > nextTime.getTime()){
			$('small').remove();
			$('#nextTime').after("<small style='color:red;'>下次联系时间不能小于当前联系时间</small>");
			return false;
		}else if(nextTime.getTime()-contactTime.getTime()< 1000*60*60*24*7 && nextTime.getDay() > contactTime.getDay()){
			$('small').remove();
			$('#nextTime').after("<small style='color:red;'>下次联系时间只能在下周</small>");
			return false;
		}else{
			$('small').remove();
			return true;
		}
	} */
    </script>
  </body>

</html>
