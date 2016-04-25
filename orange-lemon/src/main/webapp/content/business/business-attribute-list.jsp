<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>属性管理</title>
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
							<div class="caption"><i class="fa fa-cogs"></i>属性管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='business-attribute-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									<button class="btn btn-sm red" onclick="location.href='business-attribute-input-byclass.do'">
											从类型获取</button>
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
								<form name="searchForm" method="post" action="business-attribute-list.do" class="form-inline">
								   <label for="clsId">类型</label>
									<select id="clsId" name="clsId" class="form-control">
										<c:forEach items="${clses }" var="cls">
											<option value="${cls.id}" <c:if test="${param.clsId == cls.id}">selected="selected"</c:if>>${cls.clsName}</option>
										</c:forEach>
									</select>
									
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="business-attribute-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="NAME">属性名</th>
										        <th class="sorting" name="COLUMN_">字段</th>
										        <th class="sorting" name="TYPE">数据类型</th>
										        <th class="sorting" name="VAL_TYPE">值类型</th>
										        <th class="sorting" name="ATTR_GROUP_ID">所属分组</th>
										        <th class="sorting" name="CLS_ID">类型名</th>
										        <th class="sorting" name="DISP_ORDER">显示顺序</th>
										        <th class="sorting" name="DISP_PAGE">是否页面显示</th>
										        <th class="sorting" name="DISP_GRID">是否表格显示</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.name}</td>
										        <td>${item.columnName}</td>
										        <td>${item.type}</td>
										        <td>${item.valueType}</td>
										        <td>${item.attributeGroup == null? (item.valueType == 'MAKEGROUP'? '分组对象':'未分组'): item.attributeGroup.name}</td>
										        <td>${item.businessClass.clsName}</td>
										        <td>${item.displayOrder}</td>
										        <td>
										        ${item.displayPage != 'T' ? '隐藏':'显示'}
										        |<a class="btn btn-sm red" href="business-attribute-display-page.do?id=${item.id}">${item.displayPage != 'T' ? '显示':'取消'}</a>
										        </td>
										        <td>
										        ${item.displayGrid != 'T' ? '隐藏':'显示'}
										        |<a class="btn btn-sm red" href="business-attribute-display-grid.do?id=${item.id}">${item.displayGrid != 'T' ? '显示':'取消'}</a>
										        </td>
										        <td>
										        	<a class="btn btn-sm red" href="business-attribute-input.do?id=${item.id}">编辑</a>
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
	});
	
    </script>
  </body>

</html>
