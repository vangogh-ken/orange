<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>任务信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>任务信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="quartz-task-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">任务信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="taskName">任务名称</label>
												</td>
												<td>
													<input id="taskName" type="text" name="taskName" value="${item.taskName}" 
													class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="taskKey">任务KEY</label>
												</td>
												<td>
													<input id="taskKey" type="text" name="taskKey" value="${item.taskKey}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="quartzGroupId">分组</label>
												</td>
												<td>
													<select id="quartzGroupId" name="quartzGroupId" class="form-control required">
														<c:forEach items="${quartzGroups}" var="quartzGroup">
														<option value="${quartzGroup.id}" <c:if test="${item.quartzGroup.id == quartzGroup.id }">selected</c:if>>${quartzGroup.groupName}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<label class="td-label" for="springId">容器内ID</label>
												</td>
												<td>
													<input id="springId" type="text" name="springId" value="${item.springId}" 
													class="form-control" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="beanClass">类型名称</label>
												</td>
												<td>
													<input id="beanClass" type="text" name="beanClass" value="${item.beanClass}" 
													class="form-control" >
												</td>
												<td>
													<label class="td-label" for="methodName">方法名称</label>
												</td>
												<td>
													<input id="methodName" type="text" name="methodName" value="${item.methodName}" 
													class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn == null ? '无' : item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="status">状态</label>
												</td>
												<td>
													<select id="status" name="status" class="form-control required">
														<option value="T" <c:if test="${item.status == 'T' }">selected</c:if>>是</option>
														<option value="F" <c:if test="${item.status == 'F' }">selected</c:if>>否</option>
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
               	taskName: {
      	                remote: {
      	                    url: 'quartz-task-check-task-name.do',
      	                    data: {
      	                        <c:if test="${item.id != null}">
      	                        id: function() {
      	                            return $('#id').val();
      	                        }
      	                        </c:if>
      	                    }
      	                }
      	            },
      	        taskKey: {
   	                remote: {
   	                    url: 'quartz-task-check-task-key.do',
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
  	        	taskName: {
  	                remote: "存在重复"
  	            },
  	          taskKey: {
                	remote: "存在重复"
            	}
  	   		}
        });
    });
    
    </script>
  </body>

</html>
