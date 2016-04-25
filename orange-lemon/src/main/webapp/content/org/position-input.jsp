<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>职位信息</title>
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
							<div class="caption"><i class="fa fa-user"></i>职位信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="positionForm" method="post" action="position-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">名称</label>
										<div class="col-md-4">
											<input id="positionName" type="text" name="positionName" value="${item.positionName}" 
											size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="名称">
										</div>
									</div>
									<!--  
									<div class="form-group">
										<label class="control-label col-md-3">优先级</label>
										<div class="col-md-4">
											 <input id="priority" type="text" name="priority" value="${item.priority}" 
											size="40" minlength="1" maxlength="50" class="form-control number required" placeholder="优先级">
										</div>
									</div>
									-->
									<div class="form-group">
										<label class="control-label col-md-3">组织机构</label>
										<div class="col-md-4">
											<select name="orgEntityId" id="orgEntityId" class="form-control required">
											  	<c:forEach items="${orgEntities}" var="orgEntity">
											  		<option value="${orgEntity.id}" orgType="${orgEntity.orgType.typeName}" <c:if test="${item.orgEntity.id eq orgEntity.id}">selected="selected"</c:if>>${orgEntity.orgEntityName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">职级</label>
										<div class="col-md-4">
											<select name="grade" id="grade" class="form-control required" onchange="updateGrade();">
											  	<option value="1" <c:if test="${item.grade == 1}">selected</c:if>>1(普通级)</option>
											  	<option value="2" <c:if test="${item.grade == 2}">selected</c:if>>2(主管级)</option>
											  	<option value="3" <c:if test="${item.grade == 3}">selected</c:if>>3(副经理)</option>
											  	<option value="4" <c:if test="${item.grade == 4}">selected</c:if>>4(经理级)</option>
											  	<option value="5" <c:if test="${item.grade == 5}">selected</c:if>>5(副总级)</option>
											  	<option value="6" <c:if test="${item.grade == 6}">selected</c:if>>6(总经理)</option>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">描述</label>
										<div class="col-md-4">
											<textarea id="descn" name="descn"
											size="40" minlength="2" maxlength="128" class="form-control required" placeholder="描述">${item.descn}</textarea>
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
    });
    
    $(function() {
        $("#positionForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    })
    
    function updateGrade(){
    	var grade = $('#grade').val();
    	if(grade == 4){
    		$('#orgEntityId option').each(function(i, item){
    			if($(item).attr('orgType') != '小组'){
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}else if(grade == 3){
    		$('#orgEntityId option').each(function(i, item){
    			if($(item).attr('orgType') != '部门'){
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
