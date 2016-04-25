<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>日程管理</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
    <%@include file="/common/header2.jsp"%>
    <div class="page-container" style="margin-left: -35px;">
    	<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper"> <!-- begin page-content-wrapper -->
			<div class="page-content" style="margin-left: 35px;"> <!-- begin page-content-->
				<%@include file="/common/setting.jsp"%>
				<div class="row">
				  <div class="col-md-12">
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-calendar"></i>日程管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').toggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
									<!--  
									<button class="btn btn-sm red" onclick="table.exportExcel();">
										导出<i class="fa fa-share-square-o"></i></button>
									-->
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
								<form name="searchForm" method="post" action="work-cal-info-list.do" class="form-inline">
								    <label for="type">类型</label>
								    <select id="type" name="type" class="form-control">
								    	<option value=""></option>
								    	<option value="RC" <c:if test="${param.type == 'RC'}">selected</c:if> >日程</option>
								    	<option value="JH" <c:if test="${param.type == 'JH'}">selected</c:if> >计划</option>
								    	<option value="RZ" <c:if test="${param.type == 'RZ'}">selected</c:if> >日志</option>
								    </select>
								    
								    <label for="year">年份</label>
								    <select id="year" name="year" class="form-control">
								    	<option value=""></option>
								    	<option value="2015" <c:if test="${param.year == '2015'}">selected</c:if> >2015</option>
								    	<option value="2016" <c:if test="${param.year == '2016'}">selected</c:if> >2016</option>
								    	<option value="2017" <c:if test="${param.year == '2017'}">selected</c:if> >2017</option>
								    </select>
								   
								    <label for="month">月份</label>
								    <select id="month" name="month" class="form-control">
								    	<option value=""></option>
								    	<option value="1" <c:if test="${param.month == '1' or (param.month == null and now.month == '1')}">selected</c:if> >1月份</option>
								    	<option value="2" <c:if test="${param.month == '2' or (param.month == null and now.month == '2')}">selected</c:if> >2月份</option>
								    	<option value="3" <c:if test="${param.month == '3' or (param.month == null and now.month == '3') }">selected</c:if> >3月份</option>
								    	<option value="4" <c:if test="${param.month == '4' or (param.month == null and now.month == '4') }">selected</c:if> >4月份</option>
								    	<option value="5" <c:if test="${param.month == '5' or (param.month == null and now.month == '5') }">selected</c:if> >5月份</option>
								    	<option value="6" <c:if test="${param.month == '6' or (param.month == null and now.month == '6') }">selected</c:if> >6月份</option>
								    	<option value="7" <c:if test="${param.month == '7' or (param.month == null and now.month == '7') }">selected</c:if> >7月份</option>
								    	<option value="8" <c:if test="${param.month == '8' or (param.month == null and now.month == '8') }">selected</c:if> >8月份</option>
								    	<option value="9" <c:if test="${param.month == '9' or (param.month == null and now.month == '9') }">selected</c:if> >9月份</option>
								    	<option value="10" <c:if test="${param.month == '10' or (param.month == null and now.month == '10') }">selected</c:if> >10月份</option>
								    	<option value="11" <c:if test="${param.month == '11' or (param.month == null and now.month == '11') }">selected</c:if> >11月份</option>
								    	<option value="12" <c:if test="${param.month == '12' or (param.month == null and now.month == '12') }">selected</c:if> >12月份</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="work-cal-info-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="TITLE">标题</th>
								                    <th class="sorting" name="TYPE">类型</th>
								                    <th class="sorting" name="START_TIME">开始时间</th>
								                    <th class="sorting" name="END_TIME">结束时间</th>
								                    <th class="sorting" name="CONTENT">内容</th>
								                    <th class="sorting" name="PHASE">时间段</th>
								                    <th class="sorting" name="PRIORITY">优先级</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.title.length() > 10 ? (item.title.substring(0, 9)) : item.title}...</td>
								                    <td>${item.type == 'RC' ? '日程' : item.type == 'JH'? '计划' : item.type == 'RZ' ? '日志':'其他'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td onclick="toShowContent('${item.id}');" style="cursor: pointer;">...</td>
								                    <td>${item.phase == 'AM' ? '上午' : item.phase == 'PM' ? '下午':'晚上'}</td>
								                    <td>${item.priority}</td>
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
	
	<div id="workCalInfo" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">日程信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="workCalInfoForm" action="${ctx}/out/work-cal-info-save.do" method="post" class="m-form-blank" onsubmit="return false;">
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
				<!--  
				<button type="button" class="btn btn-sm red" onclick="$('#workCalInfoForm').submit();">确认</button>
				-->
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
	 	      	'type':'${param.type}',
	 	     	'month':'${param.month}',
	 	        'year':'${param.year}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'work-cal-info-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
	
	function toShowContent(workCalInfoId){
		$.post('work-cal-info-to-show-content.do?workCalInfoId=' + workCalInfoId, function(data){
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
        	
        	var margin = (window.screen.availWidth - 800)/2;
			$('#workCalInfo').css("margin-left", margin);
			$('#workCalInfo').css("margin-top", 80);
			$('#workCalInfo').css("width","800px");
			//$('#workCalInfo').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
			$('#workCalInfo').modal();//单击空白处不会隐藏modal
		});
	}
    </script>
  </body>

</html>
