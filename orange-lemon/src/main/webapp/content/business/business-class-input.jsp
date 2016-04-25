<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>类型信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>类型信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessClassForm" method="post" action="business-class-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<table class="table-input" style="margin-left:20%;width:60%">
										<thead>
											<tr><th colspan="4">类型信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="clsName">类型名称</label>
												</td>
												<td>
													 <input id="clsName" type="text" name="clsName" value="${item.clsName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="subject">表名</label>
												</td>
												<td>
													<input id="tableName" type="text" name="tableName" value="${item.tableName}" onchange="updateTableName();"
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="description">描述</label>
												</td>
												<td colspan="3">
													 <input id="description" type="text" name="description" value="${item.description}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="beforeCreateBehaviors">创建之前行为</label>
												</td>
												<td colspan="3">
													 <textarea id="beforeCreateBehaviors" name="beforeCreateBehaviors" class="form-control" 
														<c:if test="${item.beforeCreateBehaviors != null and item.beforeCreateBehaviors != ''}">style="min-height:200px;"</c:if>>${item.beforeCreateBehaviors}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="afterCreateBehaviors">创建之后行为</label>
												</td>
												<td colspan="3">
													 <textarea id="afterCreateBehaviors" name="afterCreateBehaviors" class="form-control" 
														<c:if test="${item.afterCreateBehaviors != null and item.afterCreateBehaviors != ''}">style="min-height:200px;"</c:if>>${item.afterCreateBehaviors}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="beforeModifyBehaviors">修改之前行为</label>
												</td>
												<td colspan="3">
													 <textarea id="beforeModifyBehaviors" name="beforeModifyBehaviors" class="form-control" <c:if test="${item.beforeModifyBehaviors != null and item.beforeModifyBehaviors != ''}">style="min-height:200px;"</c:if>>${item.beforeModifyBehaviors}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="afterModifyBehaviors">修改之后行为</label>
												</td>
												<td colspan="3">
													 <textarea id="afterModifyBehaviors" name="afterModifyBehaviors" class="form-control" 
														<c:if test="${item.afterModifyBehaviors != null and item.afterModifyBehaviors != ''}">style="min-height:200px;"</c:if>>${item.afterModifyBehaviors}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="beforeDeleteBehaviors">删除之前行为</label>
												</td>
												<td colspan="3">
													 <textarea id="beforeDeleteBehaviors" name="beforeDeleteBehaviors" class="form-control" 
														<c:if test="${item.beforeDeleteBehaviors != null and item.beforeDeleteBehaviors != ''}">style="min-height:200px;"</c:if>>${item.beforeDeleteBehaviors}</textarea>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="afterDeleteBehaviors">删除之后行为</label>
												</td>
												<td colspan="3">
													 <textarea id="afterDeleteBehaviors" name="afterDeleteBehaviors" class="form-control" 
														<c:if test="${item.afterDeleteBehaviors != null and item.afterDeleteBehaviors != ''}">style="min-height:200px;"</c:if>>${item.afterDeleteBehaviors}</textarea>
												</td>
											</tr>
										</tbody>
									</table>
									<!-- 
									<div class="form-group">
										<label class="control-label col-md-3">名称</label>
										<div class="col-md-4">
											<input id="clsName" type="text" name="clsName" value="${item.clsName}" 
											size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="名称">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">表名</label>
										<div class="col-md-4">
											 <input id="tableName" type="text" name="tableName" value="${item.tableName}" 
											onchange="updateTableName();" size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="表名">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">描述</label>
										<div class="col-md-4">
											<textarea id="description" name="description" class="form-control required">${item.description}</textarea>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">完成时行为</label>
										<div class="col-md-4">
											<textarea id="beforeCreateBehaviors" name="beforeCreateBehaviors" 
											class="form-control" <c:if test="${item.beforeCreateBehaviors != null and item.beforeCreateBehaviors != ''}">style="min-height:200px;"</c:if>>${item.beforeCreateBehaviors}</textarea>
										</div>
									</div>
									 -->
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
        $('#businessClassForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
           		name: {
   	                remote: {
   	                    url: 'business-class-check-name.do',
   	                    data: {
   	                    	<c:if test="${item.id !=null }">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                    	</c:if>
   	                    }
   	                }
   	            },
   	         tableName: {
   	                remote: {
   	                    url: 'business-class-check-tableName.do',
   	                    data: {
   	                    	<c:if test="${item.id !=null }">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                    	</c:if>
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	name: {
   	                remote: "存在重复"
   	            },
   	         	tableName: {
   	                remote: "存在重复"
   	            }
   	        }
        });
    });
    
   	function updateTableName(){
   		var val = $('#tableName').val();
   		if(val.indexOf('WBD_') < 0){
   			$('#tableName').val('WBD_' + val);
   		}
   	}
    </script>
  
  </body>

</html>
