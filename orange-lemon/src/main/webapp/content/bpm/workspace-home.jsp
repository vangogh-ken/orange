<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>发起流程</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <!--  
  <body class="page-header-fixed page-sidebar-fixed">
  -->
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
							<div class="caption"><i class="fa fa-sitemap"></i>发起流程</div>
						</div>
						<div class="portlet-body ">
						<c:forEach items="${categories}" var="category">
						<c:if test="${catagoryIds.contains(category.id)}">
							<div class="row" style="margin-bottom: -20px;">
								<div class="col-md-11 page-breadcrumb breadcrumb" style="width:97%;margin-left: 20px;height:40px;margin-bottom: 10px;">
									<h4 style="font-family: 宋体;font-weight: 200;margin-top: 0px;padding-top: 5px;">
									&nbsp;<span class="fa fa-bookmark" style="font-size: 16px;line-height: 16px;"></span><strong>&nbsp;${category.categoryName}&nbsp;</strong><small>${category.descn}</small>
									</h4>
								</div>
							</div>
							<hr>
							<div class="row">
								<c:set var="varStatus" value="0"></c:set>
								<c:forEach items="${businessesUser}" var="business">
								<c:if test="${business.bpmConfigCategory.id == category.id}">
										<div class="col-md-2" style="left: 30px;">
							        	<div class="dashboard-stat white" style="border: solid grey 1px;">
											<div class="visual">
												<i class="fa fa-sitemap"></i>
											</div>
											<div class="details">
												<div class="number" style="font-size: 14px;">
													<c:forEach items="${pds}" var="pd">
														<c:if test="${business.bpmKey == pd.key}">
															${pd.name}&nbsp;v${pd.version}
														</c:if>
													</c:forEach>
												</div>
												<div class="desc">
													<a href="${ctx}/bpm/workspace-start-by-key.do?key=${business.bpmKey}" target="_blank">发起</a>
												</div>
											</div>
											<a class="more" onclick="showGraphDefinition('${business.bpmKey}')" href="javascript:void(0);">
												 流程图 <i class="m-icon-swapright m-icon-white"></i>
											</a>
										</div>
										</div>
									<c:set var="varStatus" value="${varStatus + 1}"></c:set>
									<c:if test="${varStatus%4 == 0 and varStatus != 0}">
									<br><br><br><br>
									<hr>
									</c:if>
								</c:if>
								</c:forEach>
							</div>
							<hr style="margin-bottom:-10px;">
						</c:if>
						</c:forEach>
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
        //FormSamples.init();
    });
    </script>

  </body>

</html>
