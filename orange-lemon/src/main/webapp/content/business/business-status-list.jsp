<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>状态管理</title>
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
							<div class="caption"><i class="fa fa-user"></i>状态管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='business-status-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									<button class="btn btn-sm red" onclick="location.href='business-status-input-byprocess.do'">
											流程导入<i class="fa fa-arrows"></i></button>
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-EXPORT">
									</sec:authorize>
								</div>
								<div class="pull-right" >
									<label for="pageSize">每页显示</label>
									<select id="pageSize" class="m-page-size"> 
									    <option value="10">10条</option> 
									    <option value="20">20条</option>
									    <option value="50">50条</option>
									 </select>
								</div>
							</article>
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="business-status-list.do" class="form-inline">
								    <label for="clsId">类型</label>
									<select id="clsId" name="clsId" class="form-control">
										<option value=""></option>
										<c:forEach items="${clses}" var="cls">
											<option value="${cls.id}" <c:if test="${param.clsId == cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
										</c:forEach>
									</select>
								     
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="business-status-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="CLS_ID">所属类型</th>
										        <th class="sorting" name="STATUS">状态</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.businessClass.clsName}</td>
										        <td>${item.status}</td>
										        <td>
										        	<a class="btn btn-sm red" href="business-status-input.do?id=${item.id}">编辑</a>&nbsp;&nbsp;
										        	<a class="btn btn-sm red" onclick="statusConfig('${item.id}')">安全操作设置</a>
										        </td>
										      </tr>
										      </c:forEach>
										    </tbody>
										</table>
									</form>
								</article>
					      	</div>
					   </div>
						    <div class="m-page-info pull-left">
							  共100条记录 显示1到10条记录
							</div>
							<div class="btn-group m-pagination pull-right">
							  <button class="btn red">&lt;</button>
							  <button class="btn red">1</button>
							  <button class="btn red">&gt;</button>
							</div>
						    <div class="m-clear"></div>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- /.modal -->
	<div class="modal fade" id="businessStatusConfigModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">类型安全操作设置</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="businessStatusConfigForm" action="business-status-config-save.do" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm blue" onclick="submitBusinessStatusConfigFormAsync();">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
	
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
	$(function() {
	    App.init(); 
	});
	
	$(function() {
        $('#businessStatusConfigForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
	
	var config = {
	 	    id: 'dynamicGrid',
	 	    pageNo: ${pageView.pageNo},
	 	    pageSize: ${pageView.pageSize},
	 	    totalCount: ${pageView.totalCount},
	 	    resultSize: ${pageView.resultSize},
	 	    pageCount: ${pageView.pageCount},
	 	    orderBy: '${pageView.orderBy == null ? "" : pageView.orderBy}',
	 	    asc: ${pageView.asc},
	 	    params: {
	 	        'clsId': '${param.clsId}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'department-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
	
	//类型安全操作设置
	function statusConfig(id) {
		
		var htmlContent = "<table id='businessStatusConfigTable' class='m-table table-bordered table-hover'>";
		htmlContent += "<thead><tr>";
		htmlContent += "<th>类型</th><th>属性</th><th>状态</th><th>默认&nbsp;&nbsp;/&nbsp;&nbsp;只读</th></tr></thead><tbody>";
		$.ajax({
				url:'${ctx}/business/business-status-config.do?id=' + id,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					$.each(data, function(i, item){
						if(item.attribute != "ID" &&
								item.attribute != "STATUS" &&
								item.attribute != "PRCI_ID" ){
							htmlContent += "<tr>";
							htmlContent += "<input class='selectedItemStatus a-check' type='hidden' name='"+item.id+"_id' value='"+item.id+"' />";
							htmlContent += "<td>" + item.businessAttribute.businessClass.clsName + "</td>";
							htmlContent += "<td>" + item.businessAttribute.name + "</td>";
							htmlContent += "<td>" + item.businessStatus.status + "</td>";
							
							if(item.type == "normal"){
								htmlContent += "<td>";
								htmlContent += "默认<input name='"+item.id+"_type' value='normal' type='radio' checked/>&nbsp;&nbsp;&nbsp;&nbsp;";
								htmlContent += "只读<input name='"+item.id+"_type' value='read' type='radio'/>";
								htmlContent += "</td>";
							}else{
								htmlContent += "<td>";
								htmlContent += "默认<input name='"+item.id+"_type' value='normal' type='radio' />&nbsp;&nbsp;&nbsp;&nbsp;";
								htmlContent += "只读<input name='"+item.id+"_type' value='read' type='radio' checked/>";
								htmlContent += "</td>";
							}
							
							htmlContent += "</tr>";
						}
					});
					htmlContent += "</tbody></table>";
					$('#businessStatusConfigForm').html(htmlContent);
				},
				error:function(){
					
				}
		});
		
		var margin = (window.screen.availWidth - 800)/2;
		$('#businessStatusConfigModal').css("margin-left", margin);
		$('#businessStatusConfigModal').css("width","800px");
		$('#businessStatusConfigModal').modal();
	}
	
	//$('#businessStatusConfigForm').submit();
	function submitBusinessStatusConfigFormAsync(){
		$('#businessStatusConfigModal').modal('hide');
		asyncSubmitForm('business-status-config-save.do', $('#businessStatusConfigForm').serialize());
	}
    </script>

  </body>

</html>
