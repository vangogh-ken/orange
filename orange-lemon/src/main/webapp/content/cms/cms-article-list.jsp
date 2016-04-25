<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>公告管理</title>
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
							<div class="caption"><i class="fa fa-bullhorn"></i>公告管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='cms-article-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
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
								<form name="searchForm" method="post" action="cms-article-list.do" class="form-inline">
								    <label for="catalogName">类别</label>
								    <input type="text" id="catalogName" name="catalogName" value="${param.catalogName}" class="form-control">
								    
								    <label for="title">标题</label>
								    <input type="text" id="title" name="title" value="${param.title}" class="form-control">
								    
									<label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value="" <c:if test="${param.status == ''}">selected</c:if> ></option>
								    	<option value="待发布" <c:if test="${param.status == '待发布'}">selected</c:if> >待发布</option>
								    	<option value="已发布" <c:if test="${param.status == '已发布'}">selected</c:if> >已发布</option>
								    </select>
									
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="cms-article-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="CATALOG_ID">栏目</th>
								                    <th class="sorting" name="TITLE">标题</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="USER_ID">发布人</th>
								                    <th class="sorting" name="TOP">是否置顶</th>
								                    <th class="sorting" name="ALLOW_COMMENT">是否允许评论</th>
								                    <th class="sorting" name="PUBLISH_TIME">创建时间</th>
								                    <th class="sorting" name="CREATE_TIME">发布时间</th>
								                    <th class="sorting" name="CLOSE_TIME">关闭时间</th>
								                    <th class="sorting" name="VIEW_COUNT">点击量</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.cmsCatalog.name}</td>
								                    <td>${item.title}</td>
								                    <td>
								                    ${item.status == null ? '未发布':item.status}|
								                    <c:if test="${item.status != '已发布' }">
								                    <a href="cms-article-publish.do?id=${item.id}">发布</a>
								                    </c:if>
								                    <c:if test="${item.status == '已发布' }">
								                    <a href="cms-article-unpublish.do?id=${item.id}">取消发布</a>
								                    </c:if>
								                    </td>
								                    <td>${item.publisher.displayName}</td>
								                    <td>${item.top}</td>
								                    <td>
								                    ${item.allowComment == 'T'? '是':'否'}|
								                    <c:if test="${item.allowComment != 'T' }">
								                    <a href="cms-article-allow-comment.do?id=${item.id}">开放评论</a>
								                    </c:if>
								                    <c:if test="${item.allowComment == 'T' }">
								                    <a href="cms-article-unallow-comment.do?id=${item.id}">关闭评论</a>
								                    </c:if>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH-mm:ss"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.publishTime}" pattern="yyyy-MM-dd HH-mm:ss"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.closeTime}" pattern="yyyy-MM-dd HH-mm:ss"/>
								                    </td>
								                    <td>${item.viewCount}</td>
								                    <td>
								                    	<a href="cms-article-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	        'catalogName': '${param.catalogName}',
	 	        'title': '${param.title}',
	 	        'status': '${param.status}'
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
