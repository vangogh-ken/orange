<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>编号前缀信息</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>编号前缀信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessPrefixForm" method="post" action="business-prefix-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
											<select name="clsId" id="clsId" class="form-control required">
												<option value=""></option>
											  	<c:forEach items="${clses}" var="cls">
											  		<option value="${cls.id}" <c:if test="${item.businessClass.id eq cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
											  	</c:forEach>
											 </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">前缀</label>
										<div class="col-md-4">
											<input id="prefix" name="prefix" type="text" value="${item.prefix}" class="form-control required"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">是否包含日期</label>
										<div class="col-md-4">
											是<input name="withDate" type="radio" value="T" <c:if test="${item.withDate == 'T'}">checked</c:if>/>
											&nbsp;&nbsp;
											否<input name="withDate" type="radio" value="F" <c:if test="${item.withDate != 'T'}">checked</c:if> />
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">日期类型</label>
										<div class="col-md-4">
											<select id="datePattern" name="datePattern" class="form-control">
												<option value=""></option>
												<option value="yyyy" <c:if test="${item.datePattern == 'yyyy'}">selected="selected"</c:if>>年</option>
											  	<option value="yyyyMM" <c:if test="${item.datePattern == 'yyyyMM'}">selected="selected"</c:if>>年月</option>
											  	<option value="yyyyMMdd" <c:if test="${item.datePattern == 'yyyyMMdd'}">selected="selected"</c:if>>年月日</option>
											  </select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">数据长度</label>
										<div class="col-md-4">
											<input id="length" name="length" type="text" value="${item.length}" class="form-control required number"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-md-3">是否启用</label>
										<div class="col-md-4">
											启用<input name="status" type="radio" value="T" <c:if test="${item.status == 'T'}">checked</c:if>/>
											&nbsp;&nbsp;
											禁用<input name="status" type="radio" value="F" <c:if test="${item.status != 'T'}">checked</c:if> />
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
        $("#businessPrefixForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
            	clsId: {
   	                remote: {
   	                    url: 'business-prefix-check-id.do',
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
   	        	clsId: {
   	                remote: "已经建立"
   	            }
   	        }
        });
       
    })
   
    </script>
  </body>

</html>
