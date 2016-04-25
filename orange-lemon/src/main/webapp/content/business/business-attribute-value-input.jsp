<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>属性值信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>属性值信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessAttributeValueForm" method="post" action="business-attribute-value-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
											<select id="clsId" class="form-control required" onchange="updateCls();">
											  	<c:forEach items="${clses}" var="cls">
											  		<option value="${cls.id}" <c:if test="${item.businessAttribute.businessClass.id == cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
											  	</c:forEach>
											 </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">选择属性</label>
										<div class="col-md-4">
											<select name="attrId" id="attrId" class="form-control required">
											  	<c:forEach items="${attributes}" var="attribute">
											  		<option value="${attribute.id}" clsId="${attribute.businessClass.id}" <c:if test="${item.businessAttribute.id == attribute.id}">selected="selected"</c:if>>${attribute.businessClass.clsName}: ${attribute.name}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">值类型</label>
										<div class="col-md-4">
											<select id="valueType" name="valueType" class="form-control required">
											  	<option value="input" <c:if test="${item.valueType == 'input'}">selected="selected"</c:if>>普通值型</option>
											  	<option value="select" <c:if test="${item.valueType == 'select'}">selected="selected"</c:if>>选择值型</option>
											  	<option value="textarea" <c:if test="${item.valueType == 'textarea'}">selected="selected"</c:if>>文本值型</option>
											  	<option value="checkbox" <c:if test="${item.valueType == 'checkbox'}">selected="selected"</c:if>>选择框值型</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">值</label>
										<div class="col-md-4">
											 <input id="valueContent" type="text" name="valueContent" value="${item.valueContent}" 
											size="40" minlength="1" maxlength="50" class="form-control required" placeholder="值">
										</div>
									</div>
									
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
        //updateCls();
    });
    
    $(function() {
        $("#businessAttributeValueForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    })
    
    function updateCls(){
		var clsId = $('#clsId').val();
		  
    	if(clsId != ''){
    		$('#attrId option').each(function(i, item){
    			if($(item).attr('clsId') != clsId){
    				$('#attrId').val('');
    				$(item).css('display', 'none');
    			}else{
    				$(item).css('display', 'block');
    			}
    		});
    	}
	}
    </script>
  </body>

</html>
