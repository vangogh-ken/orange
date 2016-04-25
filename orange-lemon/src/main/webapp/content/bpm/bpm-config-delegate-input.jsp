<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程代理信息</title>
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
							<div class="caption"><i class="fa fa-sitemap"></i>流程代理信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="bpmConfigDelegateForm" method="post" action="bpm-config-delegate-save.do?operationMode=STORE" class="form-horizontal" onsubmit="return false;">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <input id="taskAssigneeId" type="hidden" name="taskAssigneeId" value="${item == null ? userSession.id : item.taskAssignee.id}">
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">代理类型</label>
										<div class="col-md-4">
											<select id="delegateType" name="delegateType" class="form-control required" onchange="validateBpmConfigDelegateForm();">
												<option value="once" <c:if test="${item.delegateType == 'once'}">selected</c:if>>仅一次</option>
												<option value="period" <c:if test="${item.delegateType == 'period'}">selected</c:if>>持续时间</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">BPM流程</label>
										<div class="col-md-4">
										  <select id="bpmConfigBasisId" name="bpmConfigBasisId" class="form-control required" >
										  	<option value=""></option>
										  	<c:forEach items="${bpmConfigBasises}" var="bpmConfigBasis">
										  		<option value="${bpmConfigBasis.id}" <c:if test="${item.bpmConfigBasis.id == bpmConfigBasis.id}">selected</c:if> >${bpmConfigBasis.basisSubstanceType.typeName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">代理人</label>
										<div class="col-md-4">
											<select id="taskAgentId" name="taskAgentId" class="form-control required" >
										  	<c:forEach items="${users}" var="user">
										  		<option value="${user.id}" <c:if test="${item.taskAgent.id == user.id}">selected</c:if> >${user.displayName}</option>
										  	</c:forEach>
										  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">是否启用</label>
										<div class="col-md-4">
											  启用<input type="radio" name="status" value="start" <c:if test="${item.status == 'start' or item.status == 'used'}">checked</c:if> >
											  &nbsp;&nbsp;
											  禁用<input type="radio" name="status" value="forbid" <c:if test="${item.status == 'forbid'}">checked</c:if> >
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">开始时间</label>
										<div class="col-md-4">
											<input id="startTime" name="startTime" class="form-control datepicker" 
											type="text" value="<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/>"/>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">结束时间</label>
										<div class="col-md-4">
											<input id="endTime" name="endTime" class="form-control datepicker" 
											type="text" value="<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/>"/>
										</div>
									</div>
									
								</div>
								<div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-offset-6 col-md-9">
												<button type="button" class="btn green" onclick="if(validateBpmConfigDelegateForm()) $('#bpmConfigDelegateForm').submit();">保存</button>
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
        $('#bpmConfigDelegateForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
            	bpmConfigBasisId: {
	                remote: {
	                    url: 'bpm-config-delegate-check-configbasisid.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        },
	                        </c:if>
	                        taskAssigneeId: function() {
	                            return $('#taskAssigneeId').val();
	                        }
	                    }
	                }
	            }
	        },
	        messages: {
	        	bpmConfigBasisId: {
	                remote: "已存在代理配置"
	            }
	        }
        });
    });
    
    /* $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'delegateUserId',
    		valType:'displayName'
    	});
    }); */
    
    function validateBpmConfigDelegateForm(){
    	var delegateType = $('#delegateType').val();
    	
    	if(delegateType == 'once'){
    		$('#startTime').val('');
    		$('#endTime').val('');
    		$('small').empty();
    		return true;
    	}else if(delegateType == 'period'){
    		if($('#startTime').val() == ''){
    			$('#startTime').after("<small style='color:red;'>不能为空</small>");
    			return false;
    		}
    		if($('#endTime').val() == ''){
    			$('#endTime').after("<small style='color:red;'>不能为空</small>");
    			return false;
    		}
    		
    		var startTime = new Date($('#startTime').val().replace('-','/').replace('-','/')).getTime();
			var endTime = new Date($('#endTime').val().replace('-','/').replace('-','/')).getTime();
			if(startTime > endTime){
				$('#endTime').after("<small style='color:red;'>结束时间不能小于开始时间</small>");
				return false;
			}else{
				$('small').empty();
				return true;
			}
    	}
    }
    
    </script>
  </body>

</html>
