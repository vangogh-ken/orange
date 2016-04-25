<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>客户联系管理</title>
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
							<div class="caption"><i class="fa fa-users"></i>客户联系管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm green" id="toSuperiorSuggest">
											提交上级</button>
										<!--  
										<button class="btn btn-sm green" id="toShepherdSuggest">
											提交领导</button>
										-->
										<button class="btn btn-sm green" id="toFiling">
											提交归档</button>
										
										<button class="btn btn-sm green" id="toBatchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="crm-customer-follow-list.do" class="form-inline">
								    <label for="crmCustomerName">客户名称</label>
								    <input type="text" id="crmCustomerName" name="crmCustomerName" value="${param.crmCustomerName}" class="form-control">
								    
								    <label for="followerDisplayName">跟进人</label>
								    <input type="text" id="followerDisplayName" name="followerDisplayName" value="${param.followerDisplayName}" class="form-control">
								    
								    <label for="followTime">拜访时间</label>
								    <input type="text" id="currentFollowTimeStart" name="currentFollowTimeStart" value="${param.currentFollowTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="currentFollowTimeEnd" name="currentFollowTimeEnd" value="${param.currentFollowTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control required">
										<option value=""></option>
										<option value="上级建议" <c:if test="${param.status == '上级建议'}">selected</c:if>>上级建议</option>
										<!--  
										<option value="领导建议" <c:if test="${param.status == '领导建议'}">selected</c:if> >领导建议</option>
										-->
										<option value="已归档" <c:if test="${param.status == '已归档'}">selected</c:if> >已归档</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="crm-customer-follow-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="CRM_CUSTOMER_ID">名称</th>
								                    <th class="sorting" name="CONTACT_PERSON">联系人</th>
								                    <th class="sorting" name="CONTACT_POSITION">职位</th>
								                    <th class="sorting" name="CONTACT_PHONE">电话</th>
								                    <th class="sorting" name="LAST_FOLLOW_TIME">上次跟进时间</th>
								                    <th class="sorting" name="NEXT_FOLLOW_TIME">下次跟进时间</th>
								                    <th class="sorting" name="CURRENT_FOLLOW_TIME">本次跟进时间</th>
								                    <th class="sorting" name="FOLLOW_CONTENT">跟进内容</th>
								                    <th class="sorting" name="CHANCE_PLAN">跟进计划</th>
								                    <th class="sorting" name="CHANCE_SUGGEST">跟进建议</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">部门</th>
								                    <th class="sorting" name="USER_ID">跟进人</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <!-- 
								                <c:if test="${(item.follower.id == userSession.id && item.status!='已归档') || (item.status != '未提交' && item.status != '已归档')}"> ondblclick="location.href = 'crm-customer-follow-input.do?id=${item.id}'" </c:if>
								                 -->
								                <tr <c:if test="${(item.follower.id == userSession.id) || (item.status != '未提交')}"> ondblclick="location.href = 'crm-customer-follow-input.do?id=${item.id}'" </c:if>
								                	<c:if test="${item.status == '已归档'}"> style="border-bottom:2px solid green;" </c:if>>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.crmCustomer.customerName}</td>
								                    <td>${item.contactPerson}</td>
								                    <td>${item.contactPosition}</td>
								                    <td>${item.contactPhone}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.lastFollowTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.nextFollowTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.currentFollowTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.followContent}</td>
								                    <td>${item.chancePlan}</td>
								                    <td>${item.chanceSuggest}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
								                    <td>${item.follower.displayName}</td>
								                    <td id="${item.id}status">${item.status}</td>
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
	 	        'crmCustomerName': '${param.crmCustomerName}',
	 	        'followerDisplayName': '${param.followerDisplayName}',
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
	
	//提交上级建议
	$(document).delegate('#toSuperiorSuggest', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-follow-to-superior-suggest.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '未提交'){
					if(i == 0){
						url += 'crmFollowCustomerId=' +　$(item).val();
					}else{
						url += '&crmFollowCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认拜访状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认拜访状态！');
			}
		}
	});
	//提交领导建议
	/**
	$(document).delegate('#toShepherdSuggest', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-follow-to-shepher-suggest.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '上级建议'){
					if(i == 0){
						url += 'crmFollowCustomerId=' +　$(item).val();
					}else{
						url += '&crmFollowCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认拜访状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认拜访状态！');
			}
		}
	});
	**/
	//提交领导建议
	$(document).delegate('#toFiling', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-follow-to-filing.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '上级建议'){
					if(i == 0){
						url += 'crmFollowCustomerId=' +　$(item).val();
					}else{
						url += '&crmFollowCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认拜访状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认拜访状态！');
			}
		}
	});
	//批量导出
	$(document).delegate('#toBatchExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length != 0){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('crm-customer-follow-to-export-batch-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('客户拜访记录.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择一条数据！');
		}
		
	});
    </script>
  </body>

</html>
