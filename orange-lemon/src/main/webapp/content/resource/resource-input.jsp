<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>资源信息</title>
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
							<div class="caption"><i class="fa fa-user"></i>资源信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="resourceForm" method="post" action="resource-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">资源名称</label>
												<div class="col-md-9">
													<input id="resourceName" type="text" name="resourceName" value="${item.resourceName}" 
													size="40" minlength="2" maxlength="50" class="form-control text required" placeholder="资源名称">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">资源KEY</label>
												<div class="col-md-9">
													<input type="text" name="resourceKey" value="${item.resourceKey}" class="form-control required" placeholder="资源KEY">
												</div>
											</div>
										</div>
									</div>
									<div class="row">	
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">资源URL</label>
												<div class="col-md-9">
													<input type="text" name="resourceUrl" value="${item.resourceUrl}" class="form-control required" placeholder="资源URL">
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">资源类型</label>
												<div class="col-md-9">
													<select id="resourceType" name="resourceType" onclick="updateType();" class="select2me form-control required">
													  	<option value="目录" <c:if test="${item.resourceType eq '目录'}">selected="selected"</c:if>>目录</option>
													  	<option value="菜单" <c:if test="${item.resourceType eq '菜单'}">selected="selected"</c:if>>菜单</option>
													  	<option value="按钮" <c:if test="${item.resourceType eq '按钮'}">selected="selected"</c:if>>按钮</option>
													 </select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">上级资源</label>
												<div class="col-md-9" >
													<select id="parentId" name="parentId" class="required form-control">
														<option value="顶级菜单" type="目录">顶级菜单</option>
														<c:out value="${item.parentResource.id}"></c:out>
														<c:forEach var="key" items="${res}" varStatus="status">
															<c:if test="${key.resourceType == '菜单' or key.resourceType == '目录'}">
																<option value="${key.id}" type="${key.resourceType == '目录' ?'菜单': key.resourceType == '菜单' ? '按钮' : '目录'}" <c:if test="${key.id == item.parentResource.id}">selected="selected"</c:if> >${key.resourceType}:${key.resourceName}</option>
															</c:if>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">是否启用</label>
												<div class="col-md-9">
													<input type="radio" name="enable" value="T" ${item.enable == 'T' ? 'checked' : ''}>
													启用
													<input type="radio" name="enable" value="F" ${item.enable == 'F' ? 'checked' : ''}>
													不启用
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">图标</label>
												<div class="col-md-9">
													<select id="icon" name="icon" class="form-control">
														<option value="fa-cogs" ${item.icon == 'fa-cogs' ? 'selected' : ''}>设置</option>
														<option value="fa-comments" ${item.icon == 'fa-comments' ? 'selected' : ''}>会话</option>
														<option value="fa-truck" ${item.icon == 'fa-truck' ? 'selected' : ''}>车辆</option>
														<option value="fa-th-list" ${item.icon == 'fa-th-list' ? 'selected' : ''}>列表</option>
														<option value="fa-user" ${item.icon == 'fa-user' ? 'selected' : ''}>用户</option>
														<option value="fa-tasks" ${item.icon == 'fa-tasks' ? 'selected' : ''}>任务</option>
														<option value="fa-sort-amount-asc" ${item.icon == 'fa-sort-amount-asc' ? 'selected' : ''}>流程</option>
														<option value="fa-list-ol" ${item.icon == 'fa-list-ol' ? 'selected' : ''}>列表</option>
														<option value="fa-sitemap" ${item.icon == 'fa-sitemap' ? 'selected' : ''}>节点</option>
														<option value="fa-folder-open" ${item.icon == 'fa-folder-open' ? 'selected' : ''}>文件</option>
														<option value="fa-users" ${item.icon == 'fa-users' ? 'selected' : ''}>客户</option>
														<option value="fa-bookmark" ${item.icon == 'fa-bookmark' ? 'selected' : ''}>书签</option>
														<option value="fa-book" ${item.icon == 'fa-book' ? 'selected' : ''}>词典</option>
														<option value="fa-bullhorn" ${item.icon == 'fa-bullhorn' ? 'selected' : ''}>公告</option>
														<option value="fa-bar-chart-o" ${item.icon == 'fa-bar-chart-o' ? 'selected' : ''}>统计</option>
														<option value="fa-tasks" ${item.icon == 'fa-tasks' ? 'selected' : ''}>任务</option>
														<option value="fa-envelope" ${item.icon == 'fa-envelope' ? 'selected' : ''}>信封</option>
														<option value="fa-dollar" ${item.icon == 'fa-dollar' ? 'selected' : ''}>美元</option>
														<option value="fa-text-width" ${item.icon == 'fa-dollar' ? 'selected' : ''}>任务</option>
														
													</select>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">优先级</label>
												<div class="col-md-9">
													<input type="text" name="displayIndex" value="${item.displayIndex}" class="form-control number required" placeholder="优先级">
												</div>
											</div>
										</div>
									</div>
									<div class="row">	
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">描述</label>
												<div class="col-md-9">
													<textarea id="descn" name="descn" class="form-control required" 
														placeholder="描述">${item.descn}</textarea>
												</div>
											</div>
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
		$("#resourceForm").validate({
	        submitHandler: function(form) {
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active" ><div class="bar" style="width: 100%;"></div></div>');
	            form.submit();
	        },
	        errorClass: 'validate-error',
	        rules: {
	        	resourceKey: {
	                remote: {
	                    url: 'resource-check-resourcekey.do',
	                    data: {
	                        <c:if test="${item.id != null}">
	                        id: function() {
	                            return $('#id').val();
	                        }
	                        </c:if>
	                    }
	                }
	            },
	            resourceUrl: {
	                remote: {
	                    url: 'resource-check-resourceurl.do',
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
	        	resourceKey: {
	                remote: "存在重复资源KEY"
	            },
	            resourceUrl: {
	                remote: "存在重复资源URL"
	            }
	        }
	    });
	});
    
    function updateType(){
    	var type = $('#resourceType').val();
    	  
    	if(type != ''){
    		$('#parentId option').each(function(i, item){
    			if($(item).attr('type') != type){
    				$('#parentId').val('');
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
