<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>属性信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>属性信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessAttributeForm" method="post" action="business-attribute-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
											<select name="clsId" id="clsId" class="form-control required">
											  	<c:forEach items="${clses}" var="cls">
											  		<option value="${cls.id}" <c:if test="${item.businessClass.id eq cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									
									<c:if test="${item.businessClass != null and item.valueType != null and item.valueType != 'MAKEGROUP' and item.valueType != '' }">
									<div class="form-group">
										<label class="control-label col-md-3">选择分组</label>
										<div class="col-md-4">
											<select name="groupId" id="groupId" class="form-control">
											  	<c:forEach items="${groups}" var="group">
											  		<option value="${group.id}" <c:if test="${item.attributeGroup.id eq group.id}">selected="selected"</c:if>>${group.name}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>	
									</c:if>
									
									<div class="form-group">
										<label class="control-label col-md-3">属性名</label>
										<div class="col-md-4">
											<input id="name" name="name" type="text" value="${item.name}" class="form-control required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">数据库字段</label>
										<div class="col-md-4">
											<input id="columnName" name="columnName" type="text" value="${item.columnName}" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">数据类型</label>
										<div class="col-md-4">
											<select id="type" name="type" class="form-control">
											  	<option value="VARCHAR" <c:if test="${item.type == 'VARCHAR'}">selected="selected"</c:if>>字符串</option>
											  	<option value="TIMESTAMP" <c:if test="${item.type == 'TIMESTAMP'}">selected="selected"</c:if>>时间（年月日 时分秒）</option>
											  	<option value="DATE" <c:if test="${item.type == 'DATE'}">selected="selected"</c:if>>时间（年月日）</option>
											  	<option value="INT" <c:if test="${item.type == 'INT'}">selected="selected"</c:if>>数字</option>
											  	<option value="TEXT" <c:if test="${item.type == 'TEXT'}">selected="selected"</c:if>>文本</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">值类型</label>
										<div class="col-md-4">
											<select id="valueType" name="valueType" class="form-control">
											  	<option value="NORMAL" <c:if test="${item.valueType == 'NORMAL'}">selected="selected"</c:if>>普通类型</option>
											  	<option value="MAKEGROUP" <c:if test="${item.valueType == 'MAKEGROUP'}">selected="selected"</c:if>>属性分组</option>
											  	<option value="USERPICKER" <c:if test="${item.valueType == 'USERPICKER'}">selected="selected"</c:if>>用户选择</option>
											  	<option value="ROLEPICKER" <c:if test="${item.valueType == 'ROLEPICKER'}">selected="selected"</c:if>>角色选择</option>
											  	<option value="SELECTOR" <c:if test="${item.valueType == 'SELECTOR'}">selected="selected"</c:if>>选择类型</option>
											  	<option value="ENUMERATION" <c:if test="${item.valueType == 'ENUMERATION'}">selected="selected"</c:if>>枚举类型</option>
											  	<option value="STRINGPICKER" <c:if test="${item.valueType == 'STRINGPICKER'}">selected="selected"</c:if>>字符串选择</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">数据长度</label>
										<div class="col-md-4">
											<input id="length" name="length" type="text" value="${item.length}" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">行宽</label>
										<div class="col-md-4">
											<input id="rowCount" name="rowCount" type="text" value="${(item.rowCount == null or item.rowCount == '' or item.rowCount == 0)? 1:item.rowCount}" class="form-control"/>
										</div>
									</div>
									
									<c:if test="${item.id != null}">
									<div class="form-group">
										<label class="control-label col-md-3">显示顺序</label>
										<div class="col-md-4">
											<input id="displayOrder" name="displayOrder" type="text" value="${item.displayOrder}" class="form-control"/>
										</div>
									</div>
									</c:if>
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
        $("#businessAttributeForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
           	
            rules: {
           		name: {
   	                remote: {
   	                    url: 'business-attribute-check-name.do',
   	                    data: {
   	                    	clsId: function() {
   	                            return $('#clsId').val();
   	                        }
   	                        <c:if test="${item.id != null}">
   	                        ,
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                        </c:if>
   	                    }
   	                }
   	            },
   	         columnName: {
   	                remote: {
   	                    url: 'business-attribute-check-columnName.do',
   	                    data: {
   	                    	clsId: function() {
   	                            return $('#clsId').val();
   	                        }
   	                        <c:if test="${item.id != null}">
   	                        ,
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
   	         	columnName: {
   	                remote: "存在重复"
   	            }
   	        }
   	     
        });
       
    })
   
    </script>
  </body>

</html>
