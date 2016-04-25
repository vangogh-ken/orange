<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>公告文章</title>
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
							<div class="caption"><i class="fa fa-bullhorn"></i>公告文章</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget">
									<div class="row">
										<c:forEach items="${cmsCatalogs}" var="catalog">
										<div class="col-md-12">
											<dl class="frame"
												style="margin-top: 0px; background-color: #f9f9f7; padding: 10px;">
												<div class="portlet" style="margin-bottom: 0px;">
													<div class="portlet-title line">
														<h4>
															<i class="fa fa-edit"></i>${catalog.name}
														</h4>
													</div>
													<div class="portlet-body">
														<div class="scroller" style="height: 170px;"
															data-always-visible="1" data-rail-visible1="1">
															<ul class="feeds">
																<c:forEach items="${cmsArticles}" var="article">
																<c:if test="${article.cmsCatalog.id == catalog.id and article.status =='已发布'}">
																<li>
																	<div class="col1">
																		<div class="cont">
																			<div class="cont-col1">
																				<div class="label label-sm label-info">
																					<i class="fa fa-bullhorn"></i>
																				</div>
																			</div>
																			<div class="cont-col2">
																				<div class="desc">
																					<a href="${ctx}/cms/cms-article-view.do?id=${article.id}">${article.title}</a>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col2">
																		<div class="date" style="color:red; white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-140px; ">
																		${article.publisher.displayName} 发布于: <fmt:formatDate value="${article.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
																		</div>
																	</div>
																</li>
																</c:if>
																</c:forEach>
															</ul>
														</div>
														<!--  
														<div class="scroller-footer">
															<div class="pull-right">
																<a href="#">查看更多<i class="m-icon-swapright m-icon-gray"></i></a> &nbsp;
															</div>
														</div>
														-->
													</div>
												</div>
											</dl>
										</div>	
										</c:forEach>
									</div>
								</article>
					      	</div>
					   </div>
					   <!--  
						    <div class="m-page-info pull-left">
							  共100条记录 显示1到10条记录
							</div>
							<div class="btn-group m-pagination pull-right">
							  <button class="btn red">&lt;</button>
							  <button class="btn red">1</button>
							  <button class="btn red">&gt;</button>
							</div>
						    <div class="m-clear"></div>
						    -->
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
	/**
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
	 	        'name': '${param.name}',
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
	**/
    </script>
  </body>

</html>
