<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>文档类型信息</title>
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
							<div class="caption"><i class="fa fa-file"></i>文档类型信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="doc-type-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left: 20%;margin-right: 20%;width: 60%;">
										<thead>
											<tr><th colspan="2">文档类型信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="typeName">类型名称</label>
												</td>
												<td>
													<input id="typeName" type="text" name="typeName" value="${item.typeName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="limitDayCount">临时文件保存天数</label>
												</td>
												<td>
													<input id="limitDayCount" type="text" name="limitDayCount" value="${item.limitDayCount}" 
													size="40" minlength="1" maxlength="50" class="form-control required number" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="subject">描述</label>
												</td>
												<td colspan="3">
													<input id="description" type="text" name="description" value="${item.description}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
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
            errorClass: 'validate-error'
        });
    });
    
    $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'dutyUser',
    		valType:'displayName'
    	});
    });
    
    $(function() {
		new createListPicker({
			title:'客户列表', //标题
			modalId: 'crmCustomerModal',//modalID
			formId:'crmCustomerForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','客户全称', '类型'],//列表头 数组
			tbody:['id', 'nameFull','type'],//字段数据 数组
			tableId:'crmCustomerTable',//表ID
			multiple: false,//是否多选
			canSelect:true,//是否可返回值
			valueType:'nameFull',//需要的取值字段
			valueInput:'customerName'//取值填至何处
		});
	});
    </script>
  </body>

</html>
