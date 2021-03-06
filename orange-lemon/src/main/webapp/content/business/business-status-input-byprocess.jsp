<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>从流程导入状态</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>从流程导入状态</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="businessStatusForm" method="post" action="business-status-save-byprocess.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								
								<div class="form-body">
									<div class="form-group">
										<label class="control-label col-md-3">选择类型</label>
										<div class="col-md-4">
											<select name="clsId" id="clsId" class="form-control input-xlarge">
											  	<c:forEach items="${clses}" var="cls">
											  		<option value="${cls.id}">${cls.name}</option>
											  	</c:forEach>
											  </select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">选择流程</label>
										<div class="col-md-4">
											<select name="processDefinitionId" id="processDefinitionId" class="form-control input-xlarge">
											  	<c:forEach items="${processDefinitions}" var="pd">
											  		<option value="${pd.id}">${pd.name}</option>
											  	</c:forEach>
											  </select>
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
    
    </script>
  <!--  

    <div class="row-fluid">

    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">类型属性获取</h4>
		</header>
		<div class="content content-inner">

<form id="businessStatusForm" method="post" action="business-status-save-byprocess.do?operationMode=STORE" class="form-horizontal">
  <div class="control-group">
    <label class="control-label" for="clsId">选择Cls类型</label>
	<div class="controls">
	  <select name="clsId" id="clsId" class="input-xxlarge">
	  	<c:forEach items="${clses}" var="cls">
	  		<option value="${cls.id}">${cls.classString}</option>
	  	</c:forEach>
	  </select>
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="processDefinitionId">流程定义</label>
	<div class="controls">
	  <select name="processDefinitionId" id="processDefinitionId" class="input-xxlarge">
	  	<c:forEach items="${processDefinitions}" var="pd">
	  		<option value="${pd.id}">${pd.name}</option>
	  	</c:forEach>
	  </select>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
      <button id="submitButton" class="btn a-submit">保存</button>
      <button type="button" onclick="history.back();" class="btn a-cancel">返回</button>
    </div>
  </div>
</form>
		</div>
      </article>

    </section>
	</div>
-->
  </body>

</html>
