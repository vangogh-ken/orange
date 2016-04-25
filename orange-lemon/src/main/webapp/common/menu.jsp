<%@page language="java" pageEncoding="UTF-8" %>
<!-- 侧边菜单 -->
<div class="page-sidebar-wrapper">
	<div class="page-sidebar navbar-collapse collapse">
		<!-- add "navbar-no-scroll" class to disable the scrolling of the sidebar menu -->
		<!-- BEGIN SIDEBAR MENU -->
		<ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">
			<li class="sidebar-toggler-wrapper">
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				<div class="sidebar-toggler hidden-phone">
				</div>
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			</li>
			<li class="sidebar-search-wrapper">
				<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
				<form class="sidebar-search" action="extra_search.html" method="POST">
					<div class="form-container">
						<div class="input-box">
							<a href="javascript:;" class="remove">
							</a>
							<input type="text" placeholder="Search..."/>
							<input type="button" class="submit" value=" "/>
						</div>
					</div>
				</form>
				<!-- END RESPONSIVE QUICK SEARCH FORM -->
			</li>
			<li class="start <c:if test="${currentResource.resourceUrl == '/base/dashboard.do'}" >active</c:if> " >
				<a href="${ctx}/base/dashboard.do">
					<i class="fa fa-home"></i>
					<span class="title">主页</span><span class="selected"></span>
				</a>
			</li>
			
			<c:forEach items="${resources}" var="resource">
				<c:if test="${resource.resourceType == '目录' and resource.resourceName !='主页'}">
					<li id="${resource.id}" <c:if test="${currentResource.resourceUrl != '/base/dashboard.do' and currentResource.parentResource.id == resource.id}">class="active open"</c:if>>
					<a href="javascript:;">
						<i class="fa ${resource.icon == null ? 'fa-home' : resource.icon}"></i>
						<span class="title">${resource.resourceName}</span>
						<span class="arrow <c:if test="${currentResource.resourceUrl != '/base/dashboard.do' and currentResource.parentResource.id == resource.id}">open</c:if>" ></span>
						<span class="selected"></span>
					</a>
					<ul class="sub-menu">
		            	<c:forEach items="${resources}" var="menu">
							<c:if test="${menu.parentResource.id eq resource.id}">
								<sec:authorize ifAnyGranted="ROLE_${menu.resourceKey}">
								<li id="${menu.id}" <c:if test="${currentResource.id == menu.id}">class="active"</c:if>>
									<a href="${ctx}${menu.resourceUrl}">
										<span class="fa ${resource.icon == null ? 'fa-home' : resource.icon}" style="margin-left: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										<span>${menu.resourceName}</span>
									</a>
								</li>
								</sec:authorize>
						 	</c:if>
						</c:forEach>
			         </ul>
			         </li>
		         </c:if>
			</c:forEach>
		</ul>
		<!-- END SIDEBAR MENU -->
	</div>
</div>