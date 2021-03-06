<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>单位信息</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>单位信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="fre-part-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left:10%;width:80%">
										<thead>
											<tr><th colspan="6">单位信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="partName">名称</label>
												</td>
												<td>
													<input id="partName" type="text" name="partName" value="${item.partName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="boxType">类型</label>
												</td>
												<td>
													<input id="partType" type="text" name="partType" value="${item.partType == null? '正常单位' : item.partType}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="internal">是否为公司部门</label>
												</td>
												<td>
													<select id="internal" name="internal" class="form-control required">
														<option value="F" <c:if test="${item.internal == 'F' }">selected</c:if>>否</option>
														<option value="T" <c:if test="${item.internal == 'T' }">selected</c:if>>是</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="orgEntityId">映射部门</label>
												</td>
												<td>
													<select id="orgEntityId" name="orgEntityId" class="form-control">
														<option value="" >无</option>
														<c:forEach items="${orgEntities }" var="orgEntity">
														<option value="${orgEntity.id}" <c:if test="${orgEntity.id == item.orgEntity.id }">selected</c:if>>${orgEntity.orgEntityName}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="descn">说明</label>
												</td>
												<td>
													<input id="descn" type="text" name="descn" value="${item.descn}" 
													size="40" minlength="1" maxlength="128" class="form-control required" >
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
            	partName: {
   	                remote: {
   	                    url: 'fre-part-check-partname.do',
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
   	        	partName: {
   	                remote: "已存在"
   	            }
   	        }
        });
    });
    
    </script>
  </body>

</html>
