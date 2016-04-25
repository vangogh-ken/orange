<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>组织机构信息</title>
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
							<div class="caption"><i class="fa fa-user"></i>组织机构信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="orgEntityForm" method="post" action="org-entity-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">名称</label>
										<div class="col-md-4">
											<input id="orgEntityName" type="text" name="orgEntityName" value="${item.orgEntityName}" 
											size="40" minlength="2" maxlength="50" class="form-control required" placeholder="名称">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">组织结构类型</label>
										<div class="col-md-4">
											<select name="typeId" id="typeId" class="form-control required" onchange="updateType();">
											  	<c:forEach items="${types}" var="type">
											  		<option value="${type.id}" orgType="${type.typeName}" <c:if test="${item.orgType.id eq type.id}">selected="selected"</c:if>>${type.typeName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">上级组织</label>
										<div class="col-md-4">
											<select name="parentOrgId" id="parentOrgId" class="form-control required">
											  	<c:forEach items="${orgEntities}" var="orgEntity">
											  		<option value="${orgEntity.id}" orgType="${orgEntity.orgType.typeName}" <c:if test="${item.parentOrg.id eq orgEntity.id}">selected="selected"</c:if>>${orgEntity.orgEntityName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">状态</label>
										<div class="col-md-4">
											<select name="status" id="status" class="form-control required">
											  		<option value="T" <c:if test="${item.status == 'T'}">selected="selected"</c:if>>已启用</option>
											  		<option value="F" <c:if test="${item.status == 'F'}">selected="selected"</c:if>>已禁用</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">描述</label>
										<div class="col-md-4">
											<textarea id="descn" name="descn" 
											size="40" minlength="1" maxlength="128" class="form-control required" placeholder="描述">${item.descn == null ? '无' : item.descn}</textarea>
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
        updateType();
    });
    
    $(function() {
        $("#orgEntityForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    function updateType(){
    	var val = $('#typeId').val();
    	var type;
    	$('#typeId option').each(function(i, item){
    		if($(item).val() == val){
    			type = $(item).attr('orgType');
    		}
    	});
    	
    	if(type == '小组'){
    		$('#parentOrgId option').each(function(i, item){
    			if($(item).attr('orgType') != '部门'){
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}else if(type == '部门'){
    		$('#parentOrgId option').each(function(i, item){
    			if($(item).attr('orgType') != '公司'){
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}
    }
    </script>
  </body>

</html>
