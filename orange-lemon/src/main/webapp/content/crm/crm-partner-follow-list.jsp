<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>渠道维护管理</title>
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
							<div class="caption"><i class="fa fa-users"></i>渠道维护管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='crm-partner-follow-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
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
								<form name="searchForm" method="post" action="crm-partner-follow-list.do" class="form-inline">
								    <label for="partnerName">客户名称</label>
								    <input type="text" id="partnerName" name="partnerName" value="${param.partnerName}" class="form-control input-medium">
								    
								    <label for="displayName">跟进人</label>
								    <input type="text" id="displayName" name="displayName" value="${param.displayName}" class="form-control input-medium">
								    
								    <label for="status">状态</label>
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control input-medium">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="crm-partner-follow-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="CRM_PARTNER_ID">渠道名称</th>
								                    <th class="sorting" name="CONTACT_PERSON">联系人</th>
								                    <th class="sorting" name="CONTACT_POSITION">职位</th>
								                    <th class="sorting" name="CONTACT_PHONE">电话</th>
								                    <th class="sorting" name="LAST_FOLLOW_TIME">上次跟进时间</th>
								                    <th class="sorting" name="NEXT_FOLLOW_TIME">下次跟进时间</th>
								                    <th class="sorting" name="CURRENT_FOLLOW_TIME">本次跟进时间</th>
								                    <th class="sorting" name="FOLLOW_CONTENT">跟进内容</th>
								                    <th class="sorting" name="CHANCE_PLAN">跟进计划</th>
								                    <th class="sorting" name="CHANCE_SUGGEST">跟进建议</th>
								                    <th class="sorting" name="USER_ID">跟进人</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">部门</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="location.href = 'crm-partner-follow-input.do?id=${item.id}'">
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.crmPartner.partnerName}</td>
								                    <td>${item.contactPerson}</td>
								                    <td>${item.contactPosition}</td>
								                    <td>${item.contactPhone}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.lastFollowTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.nextTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.currentFollowTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.followContent}</td>
								                    <td>${item.chancePlan}</td>
								                    <td>${item.chanceSuggest}</td>
								                    <td>${item.follower.displayName}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
								                    <td>${item.status}</td>
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
	 	        'partnerName': '${param.partnerName}',
	 	        'displayName': '${param.displayName}',
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
