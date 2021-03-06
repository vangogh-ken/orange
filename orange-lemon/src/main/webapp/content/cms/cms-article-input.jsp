<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>公告信息</title>
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
							<div class="caption"><i class="fa fa-bullhorn"></i>公告信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="cms-article-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input">
										<thead>
											<tr><th colspan="4">公告信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td style="width: 120px;">
													<label class="td-label" for="name">栏目</label>
												</td>
												<td>
													<select id="cmsCatalogId" name="cmsCatalogId" class="form-control required">
														<option></option>
														<c:forEach items="${catalogs}" var="catalog">
														<option value="${catalog.id}" <c:if test="${catalog.id == item.cmsCatalog.id}">selected='selected'</c:if> >${catalog.name}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="title">是否允许评论</label>
												</td>
												<td>
													&nbsp;
													<input type="radio" name="allowComment" value="T" <c:if test="${item.allowComment == 'T'}">checked = 'checked'</c:if> >是
													&nbsp;&nbsp;&nbsp;
													<input type="radio" name="allowComment" value="F" <c:if test="${item.allowComment != 'T'}">checked = 'checked'</c:if> >否
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="title">标题</label>
												</td>
												<td>
													<input id="title" type="text" name="title" value="${item.title}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="content">内容</label>
												</td>
												<td>
													<textarea id="content" name="content" class="form-control required">${item.content}</textarea>
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
    
    var editor = CKEDITOR.replace('content');
	// editorObj.config.filebrowserImageUploadUrl = dir + "core/connector/" + ckfinder.ConnectorLanguage + "/connector." + ckfinder.ConnectorLanguage + "?command=QuickUpload&type=" + ( imageType || 'Images' ) ;
	editor.config.filebrowserImageUploadUrl = "${ctx}/cms/cms-article-image-upload.do";
    </script>
  </body>

</html>
