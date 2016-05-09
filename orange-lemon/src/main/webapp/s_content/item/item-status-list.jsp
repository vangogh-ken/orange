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
										<!-- 
										<button class="btn btn-sm red" onclick="location.href='business-status-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									<button class="btn btn-sm red" onclick="location.href='business-status-input-byprocess.do'">
											流程导入<i class="fa fa-arrows"></i></button>
											 -->
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
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
								<form name="searchForm" method="post" action="basis-status-list.do" class="form-inline">
								    <label for="basisSubstanceTypeId">类型</label>
									<select id="basisSubstanceTypeId" name="basisSubstanceTypeId" class="form-control">
										<option value=""></option>
										<c:forEach items="${basisSubstanceTypes}" var="basisSubstanceType">
											<option value="${basisSubstanceType.id}" <c:if test="${param.basisSubstanceTypeId == basisSubstanceType.id}">selected="selected"</c:if>>${basisSubstanceType.typeName}</option>
										</c:forEach>
									</select>
								     
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="basis-status-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="BASIS_SUBSTANCE_TYPE_ID">所属类型</th>
										        <th class="sorting" name="STATUS_TEXT">状态名称</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.basisSubstanceType.typeName}</td>
										        <td>${item.statusText}</td>
										        <td>
										        	<a class="btn btn-sm red" href="basis-status-input.do?id=${item.id}">编辑</a>
										        	<a class="btn btn-sm green" onclick="statusConfig('${item.id}')">安全操作设置</a>
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
	<div class="modal fade" id="basisStatusConfigModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">类型安全操作设置</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="basisStatusConfigForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#basisStatusConfigForm').submit();">确认</button>
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
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	//类型安全操作设置
	function statusConfig(basisStatusId) {
		var html = '<table class="m-table table-bordered table-hover">';
		html += '<input name="' + basisStatusId + '" value="' + basisStatusId + '" type="hidden"/>';
		html += "<thead><tr>";
		html += "<th>类型</th><th>状态</th><th>属性</th><th>默认&nbsp;&nbsp;/&nbsp;&nbsp;只读</th></tr></thead><tbody>";
		$.ajax({
				url:'basis-status-to-config-attribute.do?basisStatusId=' + basisStatusId,
				type:'post',
				dataType:'json',
				success:function(data){
					var basisStatus = data.basisStatus;
					var basisAttributes = data.basisAttributes;
					var basisStatusAttributes = data.basisStatusAttributes;
					$.each(basisAttributes, function(i, item){
						html += "<tr>";
						html += "<td>" + item.basisSubstanceType.typeName + "</td>";
						html += "<td>" + basisStatus.statusText + "</td>";
						html += "<td>" + item.attributeName + "</td>";
						$.each(basisStatusAttributes, function(j, ele){
							if(ele.basisAttributeId == item.id){
								if(ele.readonly == 'T'){
									html += "<td>";
									html += "默认<input name='"+ ele.id +"' value='F' type='radio' />&nbsp;&nbsp;&nbsp;&nbsp;";
									html += "只读<input name='"+ ele.id +"' value='T' type='radio' checked/>";
									html += "</td>";
								}else{
									html += "<td>";
									html += "默认<input name='"+ ele.id +"' value='F' type='radio' checked/>&nbsp;&nbsp;&nbsp;";
									html += "只读<input name='"+ ele.id +"' value='T' type='radio' />";
									html += "</td>";
								}
							}
						});
						
						html += "</tr>";
					});
					html += "</tbody></table>";
					$('#basisStatusConfigForm').html(html);
				},
				error:function(){
					
				}
		});
		
		var margin = (window.screen.availWidth - 800)/2;
		$('#basisStatusConfigModal').css("margin-left", margin);
		$('#basisStatusConfigModal').css("width","800px");
		$('#basisStatusConfigModal').modal();
	}
	
	$(function() {
		$("#basisStatusConfigForm").validate({
	        submitHandler: function(form) {
				$.post('basis-status-done-config-attribute.do', $("#basisStatusConfigForm").serialize(), function(data){
					if(data == 'success'){
						alert('配置成功！');
						$('#basisStatusConfigModal').modal('hide');
					}else{
						alert('配置失败！');
					}
				});
	        },
	        errorClass: 'validate-error'
		});
	});
    </script>
  </body>
</html>
