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
							<div class="caption"><i class="fa fa-calendar"></i>日程管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='work-cal-info-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
									<!-- 
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').show(200);$('#searchByUserAcrticle').hide(200);$('#searchByOrgAcrticle').hide(200);">
											简单查询<i class="fa fa-chevron-down"></i></button>
									 -->		
										<button class="btn btn-sm red" onclick="$('#searchByUserAcrticle').show(200);$('#searchAcrticle').hide(200);$('#searchByOrgAcrticle').hide(200);">
											按个人<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm red" onclick="$('#searchByOrgAcrticle').show(200);$('#searchByUserAcrticle').hide(200);$('#searchAcrticle').hide(200);">
											按部门<i class="fa fa-chevron-down"></i></button>

										<button class="btn btn-sm red" onclick="table.exportExcel();">
											导出<i class="fa fa-share-square-o"></i></button>
											
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
							<!-- 简单查询
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="work-cal-info-list.do" class="form-inline">
								    <label for="type">类型</label>
								    <select id="type" name="type" class="form-control">
								    	<option value="RC" <c:if test="${param.type == 'RC'}">selected</c:if> >日程</option>
								    	<option value="JH" <c:if test="${param.type == 'JH'}">selected</c:if> >计划</option>
								    	<option value="RZ" <c:if test="${param.type == 'RZ'}">selected</c:if> >日志</option>
								    </select>
								    
								    <label for="startTime">日程时间</label>
								    <input type="text" id="startTime" name="startTime" value="${param.startTime}" class="form-control datepicker">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							 -->
							<!-- 按照部门查询 -->
							<article class="m-widget" id="searchByOrgAcrticle" <c:if test="${param.searchType != 'byorg'}"> style="display:none;"</c:if>>
								<form name="searchByOrgForm" method="post" action="work-cal-info-list.do" class="form-inline">
								    <input type="hidden" name="searchType" value="byorg">
								    <label for="orgId">部门</label>
								    <select id="orgId" name="orgId" class="form-control input-medium">
								    	<option value=""></option>
								    	<c:forEach items="${orgEntities}" var="orgEntity">
								    	<option value="${orgEntity.id}" <c:if test="${param.orgId == orgEntity.id}">selected</c:if> >${orgEntity.orgType.typeName}_${orgEntity.orgEntityName}</option>
								    	</c:forEach>
								    </select>
								    
								    <label for="type">类型</label>
								    <select id="type" name="type" class="form-control">
								    	<option value="RC" <c:if test="${param.type == 'RC'}">selected</c:if> >日程</option>
								    	<option value="JH" <c:if test="${param.type == 'JH'}">selected</c:if> >计划</option>
								    	<option value="RZ" <c:if test="${param.type == 'RZ'}">selected</c:if> >日志</option>
								    </select>
								    
								    <label for="year">年份</label>
								    <select id="year" name="year" class="form-control">
								    	<option value=""></option>
								    	<option value="2014" <c:if test="${param.year == '2014'}">selected</c:if> >2014</option>
								    	<option value="2015" <c:if test="${param.year == '2015'}">selected</c:if> >2015</option>
								    	<option value="2016" <c:if test="${param.year == '2016'}">selected</c:if> >2016</option>
								    </select>
								    <c:out value="${param.month}"></c:out>
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
								    
									<button class="btn btn-sm red" onclick="document.searchByOrgForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							
							<!-- 按照个人查询 -->
							<article class="m-widget" id="searchByUserAcrticle" <c:if test="${param.searchType != 'byuser'}"> style="display:none;"</c:if> >
								<form name="searchByUserForm" method="post" action="work-cal-info-list.do" class="form-inline">
									<input type="hidden" name="searchType" value="byuser">
								    <label for="userId">员工</label>
								    <select id="userId" name="userId" class="form-control input-medium">
								    	<option value=""></option>
								    	<c:forEach items="${users}" var="user">
								    	<option value="${user.id}" <c:if test="${param.userId == user.id}">selected</c:if> >${user.displayName}</option>
								    	</c:forEach>
								    </select>
								    
								    <label for="type">类型</label>
								    <select id="type" name="type" class="form-control">
								    	<option value="RC" <c:if test="${param.type == 'RC'}">selected</c:if> >日程</option>
								    	<option value="JH" <c:if test="${param.type == 'JH'}">selected</c:if> >计划</option>
								    	<option value="RZ" <c:if test="${param.type == 'RZ'}">selected</c:if> >日志</option>
								    </select>
								    
								    <label for="year">年份</label>
								    <select id="year" name="year" class="form-control">
								    	<option value=""></option>
								    	<option value="2014" <c:if test="${param.year == '2014'}">selected</c:if> >2014</option>
								    	<option value="2015" <c:if test="${param.year == '2015'}">selected</c:if> >2015</option>
								    	<option value="2016" <c:if test="${param.year == '2016'}">selected</c:if> >2016</option>
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
								    
									<button class="btn btn-sm red" onclick="document.searchByUserForm.submit()">查询<i class="fa fa-search"></i></button>
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
								                    <th class="sorting" name="PHASE">时间段</th>
								                    <th class="sorting" name="PRIORITY">优先级</th>
								                    <th class="sorting" name="ALERT_TIME">提醒次数</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.title}</td>
								                    <td>${item.type == 'RC' ? '日程' : item.type == 'JH'? '计划' : item.type == 'RZ' ? '日志':'其他'}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.phase == 'AM' ? '上午' : item.phase == 'PM' ? '下午':'晚上'}</td>
								                    <td>${item.priority}</td>
								                    <td>${item.alertTime}</td>
								                    <td>
								                    	<a href="work-cal-info-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	        'userId':'${param.userId}',
	 	       	'orgId':'${param.orgId}',
	 	      	'type':'${param.type}',
	 	     	'month':'${param.month}',
	 	        'year':'${param.year}',
	 	        'searchType':'${param.searchType}'
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
	
	 /* $(function() {
	    	new createUserPicker({
	    		modalId: 'userPicker',
	    		multiple: false,
	    		url: '${ctx}/user/user-get-all.do',
	    		valInput:'userId',
	    		valType:'id',
	    		display:'displayName'
	    	});
	    }); */
	 
	 /* $(function(){
		 if($('#searchByOrgAcrticle').length > 0){
     		var flag = true;
     		$('#searchByOrgAcrticle input').each(function(i, item){
     			var val = $(item).val();
     			if(val != ''){
     				flag = false;
     			}
     		});
     		
 			$('#searchByOrgAcrticle select').each(function(i, item){
 				var val = $(item).val();
     			if(val != ''){
     				flag = false;
     			}
     		});
 			
 			if(flag == true){
 				$('#searchByOrgAcrticle').hide();
 			}
     	}
		 
		 if($('#searchByUserAcrticle').length > 0){
     		var flag = true;
     		$('#searchByUserAcrticle input').each(function(i, item){
     			var val = $(item).val();
     			if(val != ''){
     				flag = false;
     			}
     		});
     		
 			$('#searchByUserAcrticle select').each(function(i, item){
 				var val = $(item).val();
     			if(val != ''){
     				flag = false;
     			}
     		});
 			
 			if(flag == true){
 				$('#searchByUserAcrticle').hide();
 			}
     	}
	 }); */
    </script>
  </body>

</html>
