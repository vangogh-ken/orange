<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>主题列表</title>
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
							<div class="caption"><i class="fa fa-bookmark"></i>主题列表</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="location.href='forum-topic-input.do'">
										发表新帖</button>
									
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
								<form name="searchForm" method="post" action="forum-topic-list.do" class="form-inline">
								    <label for="manager">发帖人</label>
								    <input type="text" id="manager" name="manager" value="${param.manager}" class="form-control input-medium">
								     
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="forum-topic-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                    <th class="sorting" name="TITLE" style="width: 400px;">标题</th>
								                    <th class="sorting" name="USER_ID">作者</th>
								                    <th class="sorting" name="POST_COUNT">回复数</th>
								                    <th class="sorting" name="HIT_COUNT">点击数</th>
								                    <th class="sorting" name="CREATE_TIME">发表时间</th>
								                    <th>最后回复</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item"  varStatus="status">
								                <tr class="${status.index % 2 != 0 ? 'odd' : 'even'}">
								                    <td><a href="${ctx}/out/forum-post-input.do?forumTopicId=${item.id}">${item.title}</a></td>
								                    <td>${item.user.displayName}</td>
								                    <td>${item.postCount}</td>
								                    <td>${item.hitCount}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH-mm:ss"/>
								                    </td>
								                    <td>
								                    	<c:forEach items="${item.forumPosts}" var="post" varStatus="status"> 
								                    		<c:if test="${status.last}">
								                    			回复: &nbsp;
								                    			<fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd HH-mm:ss"/>
								                    			&nbsp;${post.user.displayName}
								                    		</c:if>
								                    	</c:forEach>
								                    </td>
								                    <td>
								                    	<a href="${ctx}/out/forum-topic-remove.do?selectedItem=${item.id}">删除</a>
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
	 	        'filter_LIKES_username': '${param.filter_LIKES_username}',
	 	        'filter_EQI_status': '${param.filter_EQI_status}'
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
