<%@page language="java" pageEncoding="UTF-8"%>
<%

%>
<%@include file="/common/taglibs.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<!-- BEGIN HEADER -->
<div class="header navbar navbar-fixed-top mega-menu">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		
		<!-- BEGIN FLASH MSG -->
		<c:if test="${not empty flashMessages}">
			<div id="m-success-message" style="display:none;">
			  <ul>
			  <c:forEach items="${flashMessages}" var="item">
			    <li>${item}</li>
			  </c:forEach>
			  </ul>
			</div>
		</c:if>
		<!-- END FLASH MSG -->
	
		<!-- BEGIN LOGO -->
		<a class="navbar-brand" href="${ctx}/base/dashboard.do">
			<img src="${ctx}/s2/assets/img/logo.png" alt="Van" class="img-responsive" />
		</a>
		<!-- END LOGO -->
		
		<!-- BEGIN HORIZANTAL MENU -->
		<c:if test="1==1"></c:if>
		<div class="hor-menu hidden-sm hidden-xs">
			<ul class="nav navbar-nav">
				<li class="classic-menu-dropdown active">
					<a href="${ctx}/base/dashboard.do">
						 dashboard
						<span class="selected">
						</span>
					</a>
				</li>
				<li class="mega-menu-dropdown mega-menu-full">
					<a href="#" data-hover="dropdown" data-close-others="true" href="" class="dropdown-toggle">
						 个人事务<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li>
							<!-- Content container to add padding -->
							<div class="mega-menu-content ">
								<div class="row">
									<div class="col-md-7">
										<ul class="col-md-4 mega-menu-submenu">
											<li>
												<h3>日程管理</h3>
											</li>
											<li>
												<a href="${ctx}/out/work-cal-info-list.do">
													<i class="fa fa-angle-right"></i>日程安排
												</a>
											</li>
										</ul>
										<ul class="col-md-4 mega-menu-submenu">
											<li>
												<h3>自动任务</h3>
											</li>
											<li>
												<a href="#">
													<i class="fa fa-angle-right"></i>任务配置
												</a>
											</li>
											<li>
												<a href="#">
													<i class="fa fa-angle-right"></i>调度执行
												</a>
											</li>
										</ul>
										<ul class="col-md-4 mega-menu-submenu">
											<li>
												<h3>信息管理</h3>
											</li>
											<li>
												<a href="${ctx}/out/msg-info-list-receive.do">
													<i class="fa fa-angle-right"></i>站内消息
												</a>
											</li>
											<li>
												<a href="${ctx}/out/msg-info-list-receive.do">
													<i class="fa fa-angle-right"></i>公网邮件
												</a>
											</li>
											<li>
												<a href="${ctx}/out/msg-info-list-receive.do">
													<i class="fa fa-angle-right"></i>手机短信
												</a>
											</li>
										</ul>
									</div>
									<div class="col-md-5">
										<div id="accordion" class="panel-group">
											<div class="panel panel-success">
												<div class="panel-heading">
													<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="collapsed">
														日程管理说明
													</a>
													</h4>
												</div>
												<div id="collapseOne" class="panel-collapse in">
													<div class="panel-body">
														 个人日程、计划、日志管理
													</div>
												</div>
											</div>
											<div class="panel panel-danger">
												<div class="panel-heading">
													<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed">
														 行政管理说明
													</a>
													</h4>
												</div>
												<div id="collapseTwo" class="panel-collapse collapse">
													<div class="panel-body">
														 公司内部行政事务管理
													</div>
												</div>
											</div>
											<div class="panel panel-info">
												<div class="panel-heading">
													<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
														信息管理说明
													</a>
													</h4>
												</div>
												<div id="collapseThree" class="panel-collapse collapse">
													<div class="panel-body">
														 邮件、站内消息、手机短信、移动客户端
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</li>
				<li>
					<a href="">
						邮件
					</a>
				</li>
				<li>
					<a href="${ctx}/disk/disk-info-list.do">
						网盘
					</a>
				</li>
				<li>
					<a href="${ctx}/disk/disk-share-list.do">
						共享
					</a>
				</li>
				<!--  
				<li>
					<span class="hor-menu-search-form-toggler">
						 &nbsp;
					</span>
					<div class="search-form">
						<form class="form-search">
							<div class="input-group">
								<input type="text" placeholder="Search..." class="form-control">
								<div class="input-group-btn">
									<button type="button" class="btn"></button>
								</div>
							</div>
						</form>
					</div>
				</li>
				
				<li>
					<span class="hor-menu-message-form-toggler">
						 &nbsp;
					</span>
					<div class="message-form">
						<form  class="form-search">
							<div class="input-group">
								<input type="text" placeholder="Search..." class="form-control">
							</div>
							<div class="input-group-btn">
								<button type="button" class="btn">发送</button>
							</div>
						</form>
					</div>
				</li>
				 -->
			</ul>
		</div>
		
		<!-- END HORIZANTAL MENU -->
		
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse"> <img src="${ctx}/s2/assets/img/menu-toggler.png" alt="" />
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN NOTIFICATION DROPDOWN -->
			<!--  
			<li class="dropdown" id="header_notification_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
				data-close-others="true"> <i class="fa fa-warning"></i> <span
					class="badge"> 6 </span>
			</a>
				<ul class="dropdown-menu extended notification">
					<li>
						<p>You have 14 new notifications</p>
					</li>
					<li>
						<ul class="dropdown-menu-list scroller" style="height: 250px;">
							<li><a href="#"> <span
									class="label label-sm label-icon label-success"> <i
										class="fa fa-plus"></i>
								</span> New user registered. <span class="time"> Just now </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-danger"> <i
										class="fa fa-bolt"></i>
								</span> Server #12 overloaded. <span class="time"> 15 mins </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-warning"> <i
										class="fa fa-bell-o"></i>
								</span> Server #2 not responding. <span class="time"> 22 mins </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-info"> <i
										class="fa fa-bullhorn"></i>
								</span> Application error. <span class="time"> 40 mins </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-danger"> <i
										class="fa fa-bolt"></i>
								</span> Database overloaded 68%. <span class="time"> 2 hrs </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-danger"> <i
										class="fa fa-bolt"></i>
								</span> 2 user IP blocked. <span class="time"> 5 hrs </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-warning"> <i
										class="fa fa-bell-o"></i>
								</span> Storage Server #4 not responding. <span class="time"> 45
										mins </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-info"> <i
										class="fa fa-bullhorn"></i>
								</span> System Error. <span class="time"> 55 mins </span>
							</a></li>
							<li><a href="#"> <span
									class="label label-sm label-icon label-danger"> <i
										class="fa fa-bolt"></i>
								</span> Database overloaded 68%. <span class="time"> 2 hrs </span>
							</a></li>
						</ul>
					</li>
					<li class="external"><a href="#"> See all notifications <i
							class="m-icon-swapright"></i>
					</a></li>
				</ul></li>
				-->
			<!-- END NOTIFICATION DROPDOWN -->
			
			<!-- BEGIN INBOX DROPDOWN -->
			<li class="dropdown" id="header_inbox_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
				data-close-others="true"> <i class="fa fa-envelope"></i> <span
					class="badge" id="unreadMailsSizeSpan">${unreadMails.size()}</span>
			</a>
				<ul class="dropdown-menu extended inbox">
					<li>
						<p id="unreadMailsSizeP">你有${unreadMails == null ? 0 : unreadMails.size()}封未读邮件</p>
					</li>
					<li>
						<ul id="unreadMailsUl" class="dropdown-menu-list scroller" style="height: 250px;">
							<c:forEach items="${unreadMails}" var="mail" varStatus="varStatus">
							<c:if test="${varStatus.index < 10}">
							<li>
								<a href="${ctx}/out/mail-receive-view.do?id=${mail.id}">
								<span class="photo"><img src="${ctx}/s2/assets/img/avatar1.jpg" alt="" /></span> 
								<span class="subject"> 
								<span class="from">${mail.addressFrom}</span> 
								<span class="time">${mail.status}</span>
								</span> 
								<span class="message">${mail.subject}</span>
								</a>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="external">
					<a href="${ctx}/out/mail-receive-list.do">查看所有<i class="m-icon-swapright"></i></a>
					</li>
				</ul></li>
			<!-- END INBOX DROPDOWN -->
			
			
			<!-- BEGIN INBOX DROPDOWN -->
			<li class="dropdown" id="header_inbox_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
				data-close-others="true"> <i class="fa fa-comments"></i> <span
					class="badge" id="unreadMsgsSizeSpan">${unreadMsgs.size()}</span>
			</a>
				<ul class="dropdown-menu extended inbox">
					<li>
						<p id="unreadMsgsSizeP">你有${unreadMsgs == null ? 0 : unreadMsgs.size()}封未读信息</p>
					</li>
					<li>
						<ul id="unreadMsgsUl" class="dropdown-menu-list scroller" style="height: 250px;">
							<c:forEach items="${unreadMsgs}" var="msg">
								<li>
								<a href="${ctx}/out/msg-info-receive-list.do">
								<span class="photo"><img src="${ctx}/s2/assets/img/avatar1.jpg" alt="" /></span> 
								<span class="subject"> 
								<span class="from">${msg.sender}</span> 
								<span class="time">${msg.type}</span>
								</span> 
								<span class="message">${msg.content}</span>
								</a>
							</li>
							</c:forEach>
						</ul>
					</li>
					<li class="external">
					<a href="${ctx}/out/msg-info-receive-list.do">查看所有<i class="m-icon-swapright"></i></a>
					</li>
				</ul></li>
			<!-- END INBOX DROPDOWN -->
			
			<!-- BEGIN TODO DROPDOWN -->
			<!-- 待处理任务 -->
			<li class="dropdown" id="header_task_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
				data-close-others="true"> <i class="fa fa-tasks"></i> <span
					class="badge" id="handleTasksSizeSpan">${handleTasks.size()}</span>
			</a>
				<ul class="dropdown-menu extended tasks">
					<li>
						<p id="handleTasksSizeP">你有${handleTasks == null ? 0 : handleTasks.size()}个待办任务</p>
					</li>
					<li>
						<ul id="handleTasksUl" class="dropdown-menu-list scroller" style="height: 250px;">
							<c:forEach items="${handleTasks}" var="handleTask" varStatus="varStatus">
							<c:set var="processDefinitionId" value="${handleTask.processDefinitionId}"></c:set>
				            <c:set var="processInstanceId" value="${handleTask.processInstanceId}"></c:set>
				            <c:set var="taskId" value="${handleTask.id}"></c:set>
							<c:if test="${varStatus.index < 6}">
							<li><a href="${ctx}/bpm/workspace-task-handle-input.do?taskId=${handleTask.id}" target="_blank"">
							<span class="task">
							<span class="desc" style="width: 50px;"><%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString())%></span> 
							<span class="percent">${handleTask.name}</span>
							</span> 
							</a>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="external">
						<a href="${ctx}/bpm/workspace-task-handle-list.do">查看所有<i class="m-icon-swapright"></i></a>
					</li>
				</ul></li>
				<!-- 待签收任务 -->
				<li class="dropdown" id="header_task_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
				data-close-others="true"> <i class="fa fa-tasks"></i> <span
					class="badge" id="claimTasksSizeSpan">${claimTasks.size()}</span>
			</a>
				<ul class="dropdown-menu extended tasks">
					<li>
						<p id="claimTasksSizeP">你有${claimTasks == null ? 0 : claimTasks.size()}个待领任务</p>
					</li>
					<li>
						<ul id="claimTasksUl" class="dropdown-menu-list scroller" style="height: 250px;">
							<c:forEach items="${claimTasks}" var="claimTask" varStatus="varStatus">
							<c:set var="processDefinitionId" value="${claimTask.processDefinitionId}"></c:set>
				            <c:set var="processInstanceId" value="${claimTask.processInstanceId}"></c:set>
				            <c:set var="taskId" value="${claimTask.id}"></c:set>
							<c:if test="${varStatus.index < 6}">
							<li><a href="${ctx}/bpm/workspace-task-claim.do?taskId=${claimTask.id}" target="_blank"">
							<span class="task">
							<span class="desc" style="width: 50px;"><%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString())%></span> 
							<span class="percent">${claimTask.name}</span>
							</span> 
							</a>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					<li class="external">
						<a href="${ctx}/bpm/workspace-task-claim-list.do">查看所有<i class="m-icon-swapright"></i></a>
					</li>
				</ul></li>
			<!-- END TODO DROPDOWN -->
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> 
					<img alt="" src="${ctx}/icon/${userSession.userBase.icon}" style="height: 29px;width:29px;">
					<span class="username">${userSession.displayName}</span> 
					<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li>
					<a href="${ctx}/user/user-profile.do">
						<i class="fa fa-user"></i> 个人资料
					</a>
					</li>
					<li>
					<a href="${ctx}/user/change-password-input.do">
						<i class="fa fa-unlock-alt"></i> 修改密码
					</a>
					</li>
					<!--  
					<li><a href="inbox.html">
							<i class="fa fa-envelope"></i> My Inbox <span
							class="badge badge-danger"> 3 </span>
					</a>
					</li>
					<li><a href="#"> <i class="fa fa-tasks"></i> My Tasks <span
							class="badge badge-success"> 7 </span>
					</a>
					</li>
					-->
					<li class="divider"></li>
					<li><a href="${ctx}/base/login.do"><i class="fa fa-key"></i>安全退出</a></li>
				</ul>
			  </li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->