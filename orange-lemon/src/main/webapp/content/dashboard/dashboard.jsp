<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>dashboard</title>
<%@include file="/common/s2.jsp"%>
</head>
<body class="page-header-fixed">
	<%@include file="/common/header2.jsp"%>
	<div class="page-container">
		<%@include file="/common/menu.jsp"%>
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<%@include file="/common/setting.jsp"%>
				<div class="row">
					<div class="col-md-8">
						<div class="tabbable tabbable-custom boxless tabbable-reversed">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#calendar_row" data-toggle="tab" onclick="$('#cmsActicles').removeClass('active');$('#formTopics').removeClass('active');$('#calendar').addClass('active');">工作日历
								</a></li>
								<li><a href="#cmsActicles" data-toggle="tab">公告 
								<c:if test="${cmsArticles.size() > 0 or cmsArticles[0].publishTime < now  }">
								<span class="badge badge-important">new</span></c:if>
								</a></li>
								<li><a href="#formTopics" data-toggle="tab">论坛
								<span class="badge badge-important">new</span></a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="calendar">
									<div class="portlet box blue calendar">
										<div class="portlet-body light-grey">
											<div class="row">
												<div class="col-md-12 col-sm-12">
													<table style="font-size:smaller;color:#545454;">
														<tbody>
															<tr>
																<td class="legendColorBox">
																	<div style="border:1px solid #ccc;padding:1px;margin-top:12px;"><div style="width:6px;height:0;border:6px solid rgb(209,38,16);overflow:hidden;"></div></div>
																</td>
																<td class="legendLabel">
																	<div class="checkbox" style="padding-left: 30px;">
																		<label><input type="checkbox" checked="checked" value="RC" name="isrc" id="isrc">我的日程</label>
																	</div>
																</td>
																<td class="legendColorBox" style="padding-left: 30px;">
																	<div style="border:1px solid #ccc;padding:1px;margin-top:12px;"><div style="width:6px;height:0;border:6px solid #3a87ad;overflow:hidden;"></div></div>
																</td>
																<td class="legendLabel">
																	<div class="checkbox" style="padding-left: 30px;">
																		<label><input type="checkbox" checked="checked" value="JH" name="isjh" id="isjh">工作计划</label>
																	</div>
																</td>
																<td class="legendColorBox" style="padding-left: 30px;">
																	<div style="border:1px solid #ccc;padding:1px;margin-top:12px;"><div style="width:6px;height:0;border:6px solid green;overflow:hidden;"></div></div>
																</td>
																<td class="legendLabel">
																	<div class="checkbox" style="padding-left: 30px;">
																		<label><input type="checkbox" checked="checked" value="RZ" name="isrz" id="isrz">工作日志</label>
																	</div>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<!-- 
											<div class="row">
												<div class="col-md-12 col-sm-12">
													<div id="calendar" class="has-toolbar"></div>
												</div>
											</div>
											-->
										</div>
									</div>
								</div>
								<div class="tab-pane" id="cmsActicles">
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
																				<div class="desc" style="font-size:14px;">
																					<a href="${ctx}/cms/cms-article-view.do?id=${article.id}" title="点击查看">${article.title}</a>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col2">
																		<div class="date" style="color:red; font-size:14px;white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-140px; ">
																		${article.publisher.displayName} 发布于:<fmt:formatDate value="${article.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
																		</div>
																	</div>
																</li>
																</c:if>
																</c:forEach>
															</ul>
														</div>
														<div class="scroller-footer">
															<div class="pull-right">
																<a href="${ctx}/cms/cms-article-list-view.do?cmsCatalogId=${catalog.id}">查看更多<i class="m-icon-swapright m-icon-gray"></i></a> &nbsp;
															</div>
														</div>
													</div>
												</div>
											</dl>
										</div>	
										</c:forEach>
									</div>
								</div>
								<div class="tab-pane" id="formTopics">
									<div class="row">
										<div class="col-md-12">
											<ul class="timeline">
											<c:forEach items="${forumTopics}" var="topic" varStatus="varStatus">
												<li class="timeline-${(varStatus.index%5 + 1) == 1? 'blue':(varStatus.index%5 + 1) == 2?'yellow':(varStatus.index%5 + 1) == 3?'purple':(varStatus.index%5 + 1) == 4?'red':'grey'}">
													<div class="timeline-time">
														<span class="date" style="font-size: 14px;">
															 <fmt:formatDate value="${topic.createTime}" pattern="yyyy-MM-dd"/>
														</span>
														<span class="time" style="font-size: 25px;">
															 <fmt:formatDate value="${topic.createTime}" pattern="HH:mm:ss"/>
														</span>
													</div>
													<div class="timeline-icon">
														<i class="fa fa-trophy"></i>
													</div>
													<div class="timeline-body">
														<h4>${topic.user.displayName}:   ${topic.title}</h4>
														<div class="timeline-content" style="height:50px;">
															<img class="timeline-img pull-left" src="${ctx}/s2/assets/img/blog/${varStatus.index%5 + 1}.jpg" alt="">
															${topic.content}
														</div>
														<div class="timeline-footer">
															<a href="${ctx}/out/forum-post-input.do?forumTopicId=${topic.id}" class="nav-link pull-right">
																 查看<i class="m-icon-swapright m-icon-white"></i>
															</a>
														</div>
													</div>
												</li>
											</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<h4 class="ng-binding" style="overflow-y:hidden;margin-top: 0px;margin-bottom: -5px;">
						<img src="${ctx}/icon/${userSession.userBase.icon}" style="height: 60px;width:60px;margin-right:-15px;" align="absmiddle">&nbsp;
						<c:if test="${now.getHours() < 12}">上午好</c:if><c:if test="${now.getHours() > 12}">下午好</c:if><c:if test="${now.getHours() == 12}">中午好</c:if>！ <fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />
						</h4>
						<hr style="margin-bottom: 3px;">
						<div class="tabbable tabbable-custom boxless tabbable-reversed">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#handleTasks"
									data-toggle="tab">待办任务<span class="badge badge-important">${handleTasks.size()}</span></a></li>
								<li><a href="#claimTasks" data-toggle="tab">待领任务<span class="badge badge-important">${claimTasks.size()}</span></a></li>
								<li><a href="#startProcesses" data-toggle="tab">新起流程<span class="badge badge-important">${processes.size()}</span></a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="handleTasks">
									<div class="scroller" style="height: 354px;" data-always-visible="1" data-rail-visible1="1">
										<ul class="feeds">
											<c:forEach items="${handleTasks}" var="handleTask">
											<li>
												<a href="#">
													<div class="col1">
														<div class="cont">
															<div class="cont-col1">
																<div class="label label-sm label-success">                        
																	<i class="fa fa-sitemap"></i>
																</div>
															</div>
															<div class="cont-col2">
																<div class="desc">
																	<a href="${ctx}/bpm/workspace-task-handle-input.do?taskId=${handleTask.id}" target="_blank" title="点击处理">
																	<c:set var="processDefinitionId" value="${handleTask.processDefinitionId}"></c:set>
																	<%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString())%>
																	-
																	${handleTask.name}
																	</a>
																</div>
															</div>
														</div>
													</div>
													<div class="col2">
														<div class="date" style="color:red; font-size:14px;white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-60px; ">
															<fmt:formatDate value="${handleTask.createTime}" pattern="MM-dd HH:mm:ss"/>
														</div>
													</div>
												</a>
											</li>
											</c:forEach>
										</ul>
									</div>
									<div class="scroller-footer">
										<div class="pull-right">
											<a href="${ctx}/bpm/workspace-task-handle-list.do">查看更多<i class="m-icon-swapright m-icon-gray"></i></a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="claimTasks">
									<div class="scroller" style="height: 354px;" data-always-visible="1" data-rail-visible1="1">
										<ul class="feeds">
											<c:forEach items="${claimTasks}" var="claimTask">
											<li>
												<a href="#" title="点击查看">
													<div class="col1">
														<div class="cont">
															<div class="cont-col1">
																<div class="label label-sm label-success">                        
																	<i class="fa fa-sitemap"></i>
																</div>
															</div>
															<div class="cont-col2">
																<div class="desc">
																	<a href="${ctx}/bpm/workspace-task-claim-list.do">
																	<c:set var="processDefinitionId" value="${claimTask.processDefinitionId}"></c:set>
																	<%=ProcessDefinitionCache.getProcessName(pageContext.getAttribute("processDefinitionId").toString())%>
																	-
																	${claimTask.name}
																	</a>
																</div>
															</div>
														</div>
													</div>
													<div class="col2">
														<div class="date" style="color:red; font-size:14px;white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-60px; ">
															<fmt:formatDate value="${claimTask.createTime}" pattern="MM-dd HH:mm:ss"/>
														</div>
													</div>
												</a>
											</li>
											</c:forEach>
										</ul>
									</div>
									<div class="scroller-footer">
										<div class="pull-right">
											<a href="${ctx}/bpm/workspace-task-claim-list.do">查看更多<i class="m-icon-swapright m-icon-gray"></i></a>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="startProcesses">
									<div class="scroller" style="height: 354px;" data-always-visible="1" data-rail-visible1="1">
										<ul class="feeds">
											<c:forEach items="${processes}" var="process">
											<li>
												<a href="#">
													<div class="col1">
														<div class="cont">
															<div class="cont-col1">
																<div class="label label-sm label-success">                        
																	<i class="fa fa-sitemap"></i>
																</div>
															</div>
															<div class="cont-col2">
																<div class="desc">
																	<c:forEach items="${pds}" var="pd">
																		<c:if test="${process.bpmKey == pd.key}">
																			${pd.name}&nbsp;v${pd.version}
																		</c:if>
																	</c:forEach>
																</div>
															</div>
														</div>
													</div>
													<div class="col2">
														<div class="date" style="font-size:14px;white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-60px; ">
															<a href="${ctx}/bpm/workspace-start-by-key.do?key=${process.bpmKey}" target="_blank" style="color:red;"  title="点击启动">发起</a>
														</div>
													</div>
												</a>
											</li>
											</c:forEach>
										</ul>
									</div>
									<div class="scroller-footer">
										<div class="pull-right">
											<a href="${ctx}/bpm/workspace-home.do">查看更多<i class="m-icon-swapright m-icon-gray"></i></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!---->
				<!-- 
				<div class="row" style="margin-top:-60px;">
					<c:forEach items="${cmsCatalogs}" var="catalog">
					<div class="col-md-6">
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
																<a href="#">${article.publisher.displayName}: ${article.title}</a>
															</div>
														</div>
													</div>
												</div>
												<div class="col2">
													<div class="date" style="color:red; white-space: nowrap; width: 120px;margin-right: 50px;margin-left:-60px; ">
													<fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</div>
												</div>
											</li>
											</c:if>
											</c:forEach>
										</ul>
									</div>
									<div class="scroller-footer">
										<div class="pull-right">
											<a href="#">查看更多<i class="m-icon-swapright m-icon-gray"></i></a> &nbsp;
										</div>
									</div>
								</div>
							</div>
						</dl>
					</div>	
					</c:forEach>
				</div>
				 -->
			</div>
		</div>
	</div>
	
	<!-- /.modal -->
	<div id="workCalInfo" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">日程信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="workCalInfoForm" action="${ctx}/out/work-cal-info-save.do" method="post" class="m-form-blank" onsubmit="return false;">
					<input type="hidden" id="isDashboard" name="isDashboard" value="T">
					<input id="id" type="hidden" name="id" value="">
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:80px;">
									<label class="td-label" for="type">类型</label>
								</td>
								<td colspan="3" style="margin-top:8px;font-size: 16px;font-weight: 600;">
									日程<input id="typeRC"  type="radio" name="type" value="RC">&nbsp;&nbsp;
									计划<input id="typeJH" type="radio" name="type" value="JH">&nbsp;&nbsp;
									日志<input id="typeRZ" type="radio" name="type" value="RZ">
								</td>
							</tr>
							<tr>
								<td style="width:80px;">
									<label class="td-label" for="startTime">开始时间</label>
								</td>
								<td>
									<input id="startTime" type="text" name="startTime" value="" 
									size="40" minlength="2" maxlength="50" class="form-control datepicker required" >
								</td>
								<td style="width:80px;">
									<label class="td-label" for="endTime">结束时间</label>
								</td>
								<td>
									<input id="endTime" type="text" name="endTime" value="" 
									size="40" minlength="2" maxlength="50" class="form-control datepicker required" >
								</td>
							</tr>
							<tr>
								<td>
									<label class="td-label" for="content">工作内容</label>
								</td>
								<td colspan="3">
									<textarea id="content" name="content" 
										style="min-height: 180px;" class="form-control required"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#workCalInfoForm').submit();">确认</button>
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
			
			$(function(){
				var date = new Date();
				var d = date.getDate();
				var m = date.getMonth();
				var y = date.getFullYear();
				
				var calendar = $('#calendar').fullCalendar({
					header: {
						left: 'prev,next today',
						center: 'title',
						right: 'month, basicWeek, basicDay'
					},
					titleFormat:{ 
					    month: 'yyyy年MMMM',
					    week: "yyyy 年  MMM d日{'&#8212;'[yyyy] MMM d日}", 
					    day: 'yyyy年 MMM d日 dddd'
					},
					aspectRatio: 1.35,
					selectable: true,
					selectHelper: true,
					currentTimezone: 'Asia/Beijing',
					handleWindowResize:true,
					buttonText:{
						prev:     '<span class="fc-text-arrow">‹</span>',
						next:     '<span class="fc-text-arrow">›</span>',
						prevYear: '去年',
						nextYear: '明年',
						today:    '今天',
						month:    '月',
						week:     '周',
						day:      '日'
						},
						//defaultView:'basicWeek',
					select: function(startDate, endDate, allDay) {
							var start =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd');
							var end =$.fullCalendar.formatDate(endDate,'yyyy-MM-dd');
						/* $.fancybox({
							'type':'ajax',
							'href':'/lemon/default/management/security/schedule/scheduleDetail.do?action=add&start='+start+'&end='+end+'&status=0'
						}); */
					},
					monthNames:['1月','2月', '3月', '4月', '5月', '6月', '7月','8月', '9月', '10月', '11月', '12月'],
					monthNamesShort:['1月','2月', '3月', '4月', '5月', '6月', '7月','8月', '9月', '10月', '11月', '12月'],
					dayNames:['星期日', '星期一', '星期二', '星期三','星期四', '星期五', '星期六'],
					dayNamesShort:['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
					editable: true,
					events: function(start, end, callback) {
				       $.ajax({
				            url: '${ctx}/out/work-cal-info-all.do',
				            dataType: 'json',
				            type: 'post',
				            
				            data: {
				                isrc: $("#isrc").attr('checked') == 'checked'? 'T':'F',
				                isjh: $("#isjh").attr('checked') == 'checked'? 'T':'F',
				                isrz: $("#isrz").attr('checked') == 'checked'? 'T':'F'
				                /**
				            	isrc: 'F',
				                isjh: 'F',
				                isrz: 'T'
				            	**/
				            },
				            success: function(data) {
				            	var events = [];
				                for(var i=0, len=data.length; i<len; i++){
 									var obj = new Object();
 									obj.id = data[i].id;
 									obj.title = data[i].title == null ? (data[i].content.substring(0, 6) + '...') : data[i].title;
 									//var title = data[i].title == null ? (data[i].content.substring(0, 6) + '...') : data[i].title;
 									//obj.title = title == null ? (data[i].content.substring(0, 6) + '...') : title;
 									obj.start = new Date(data[i].startTime);
 									obj.end =  new Date(data[i].endTime);
 									if(data[i].type == 'RC'){
 										obj.backgroundColor = App.getLayoutColorCode('red');
 									}else if(data[i].type == 'JH'){
 										obj.backgroundColor = App.getLayoutColorCode('blue');
 									}else if(data[i].type == 'RZ'){
 										obj.backgroundColor = App.getLayoutColorCode('green');
 									}
 									events.push(obj);
 								}
				                callback(events);
				            }
				        }); 
				    },
					eventClick: function(event) {//打开方法的事件 编辑内容页面在此编写
						showModal(event);
					},
					eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {
						//只能删除今天的日程
						$.post('${ctx}/out/work-cal-info-drop.do', {id: event.id}, function(msg){
							if(msg!="success"){
								//alert(msg);
								revertFunc();
							}else{
								$('#calendar').fullCalendar('refetchEvents');
							}
						});
			    	},
					
					eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
			    	},
			    	dayClick: function(date, allDay, jsEvent, view) {
			    		if(jsEvent == null || jsEvent.start == undefined){
			    			jsEvent.start = date;
			    		}
			    		showModal(jsEvent);
			    	}
					
				});
				
				$("#isrc").click(function(){
					$('#calendar').fullCalendar('refetchEvents');
				});
				$("#isjh").click(function(){
					$('#calendar').fullCalendar('refetchEvents');
				});
				$("#isrz").click(function(){
					$('#calendar').fullCalendar('refetchEvents');
				});
				
				$(".sidebar-toggler").click(function(){ 
					setTimeout(function(){
							var view = $('#calendar').fullCalendar('getView');
							$('#calendar').fullCalendar('changeView','agendaWeek');
							$('#calendar').fullCalendar('changeView',view.name);
						},500);
					});
				
			});
			
			var modalBak;
			$(function(){
				modalBak = $('#workCalInfo').html();
			});
			
			function showModal(event){
				//此处避免第二次打开modal时,丢失表单数据
				if($('#workCalInfo .validate-error').length > 0 ){
					$('#workCalInfo').html('');
					$('#workCalInfo').html(modalBak);
					//addValidateForWorkCalForm();取消校验
				}
				
				$('small').empty();
				$('#workCalInfoForm #startTime').val('');
				$('#workCalInfoForm #endTime').val('');
				$('#workCalInfoForm #title').val('');
				$('#workCalInfoForm #content').val('');
				$('#workCalInfoForm #description').val('');
				$('#workCalInfoForm #id').val('');
				$('#workCalInfoForm :radio[name="type"]').each(function(i, item){
					$(item).removeAttr('checked');
				});
            	
            	//$('.validate-error').text('');
            	//$('.validate-error').remove();
            	
				$('#workCalInfoForm').attr('action', '${ctx}/out/work-cal-info-save.do');
				if(event.end == null || event.end == undefined){
					event.end = event.start;
				}
				$('#workCalInfoForm #startTime').val($.fullCalendar.formatDate((event.start == null ? new Date() : event.start),'yyyy-MM-dd'));
				$('#workCalInfoForm #endTime').val($.fullCalendar.formatDate((event.end == null ? new Date() : event.end),'yyyy-MM-dd'));
				$('#workCalInfoForm #title').val(event.title == null ? '' : event.title);
				
				if(event != null && event.id != undefined){
					$.ajax({
			            url: '${ctx}/out/work-cal-info-get.do',
			            dataType: 'json',
			            type: 'post',
			           // async: false,
			            data: {
			                id: event.id
			            },
			            success: function(data) {
			            	$('#workCalInfoForm #id').val(data.id);
			            	$('#workCalInfoForm #startTime').val($.fullCalendar.formatDate(new Date(data.startTime),'yyyy-MM-dd'));
							$('#workCalInfoForm #endTime').val($.fullCalendar.formatDate(new Date(data.endTime),'yyyy-MM-dd'));
			            	$('#workCalInfoForm #title').val(data.title);
			            	$('#workCalInfoForm #content').val(data.content);
			            	$('#workCalInfoForm #description').val(data.description);
			            	$('#workCalInfoForm :radio[name="type"]').each(function(i, item){
								if($(item).val() == data.type){
									$(item).attr('checked', 'checked');
								}
							});
			            	
			            	$('#workCalInfoForm #phase option').each(function(i, item){
								if($(item).val() == data.phase){
									$(item).attr('selected', 'selected');
								}
							});
			            }
			        });
			    }else{
			    	//如果是新增则设置默认类型
			    	$('#workCalInfoForm :radio[name="type"]').each(function(i, item){
			    		if($(item).val() == 'RC'){
			    			$(item).attr('checked', 'checked');
			    		}
					});
			    }
				
				var margin = (window.screen.availWidth - 800)/2;
				$('#workCalInfo').css("margin-left", margin);
				$('#workCalInfo').css("margin-top", 100);
				$('#workCalInfo').css("width","800px");
				$('#workCalInfo').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
			}
				
				
			
			$(function() {
				addValidateForWorkCalForm();
		    });
			
			function addValidateForWorkCalForm(){
				$("#workCalInfoForm").validate({
		            submitHandler: function(form) {
		            	$('#workCalInfo').modal('hide');
		    			bootbox.animate(false);
		    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		                form.submit();
		            },
		            errorClass: 'validate-error',
		            rules: {
			            phase: {
			                remote: {
			                    url: '${ctx}/out/work-cal-info-check-phase.do',
			                    data: {
			                        id: function() {
			                            return $('#workCalInfoForm #id').val();
			                        },
			                        startTime: function() {
			                            return $('#startTime').val();
			                        },
			                        type: function(){
			                        	if($('#typeRC').attr('checked') == 'checked'){
			                        		return $('#typeRC').val();
			                        	}else if($('#typeJH').attr('checked') == 'checked'){
			                        		return $('#typeJH').val();
			                        	}
			                        	else if($('#typeRZ').attr('checked') == 'checked'){
			                        		return $('#typeRZ').val();
			                        	}
			                        }
			                    }
			                }
			            }
			        },
			        messages: {
			        	phase: {
			                remote: "此日程类型下已有此时间段数据"
			            }
			        }
		        });
			}
			/**
			function getWorkCalInfoType(){
				$('#workCalInfoForm :radio[name="type"]').each(function(i, item){
					if($(item).attr('checked') == 'checked'){
						return $(item).val();
					}
				});
			}
			
			$(document).delegate('#workCalInfoForm #endTime', 'change', function(e) {
				validateWorkCalForm();//取消校验
			});
			**/
			function validateWorkCalForm(){
				var startTime = new Date($('#workCalInfoForm #startTime').val().replace('-','/').replace('-','/')).getTime();
				var endTime = new Date($('#workCalInfoForm #endTime').val().replace('-','/').replace('-','/')).getTime();
				var currentTime = new Date($.fullCalendar.formatDate(new Date(),'yyyy/MM/dd')).getTime();
				//说明打开的是当天之后的日程,因此提示不能添加未来的日程信息
				if(startTime > endTime){
					$('small').empty();
					//$('#workCalInfoForm #endTime').after("<small style='color:red;'>结束时间不能小于开始时间</small>");
					$('#workCalInfoForm #endTime').after("<small style='color:red;'>不能修改或添加未来的日程信息</small>");
					return false;
				//说明打开的时当天之前的数据,则不能保存
				}else if(startTime < currentTime){
					$('small').empty();
					$('#workCalInfoForm #endTime').after("<small style='color:red;'>不能修改或添加之前的日程信息</small>");
					return false;
				}else{
					$('small').empty();
					return true;
				}
			}
			
		</script>
</body>

</html>
