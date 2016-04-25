<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>客户管理</title>
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
							<div class="caption"><i class="fa fa-users"></i>客户管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='crm-customer-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										
										<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER, ROLE_CY-MARKETING">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										</sec:authorize>
										
										<button class="btn btn-sm green" id="toFollowCustomer">
											客户拜访</button>
										<button class="btn btn-sm green" id="toApplyFollow">
											申请跟进</button>
										
										<!-- 管理员和分管副总有权限 -->
										<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER, ROLE_CY-MARKETING">
										<button class="btn btn-sm red" id="toAgreeFollow">
											同意跟进</button>
										<button class="btn btn-sm red" id="toRefuseFollow">
											退回跟进</button>
										</sec:authorize>
											
										<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER, ROLE_CY-MARKETING">	
										<!--  
										<button class="btn btn-sm green" id="batchImport">
											批量导入</button>
											-->
										<button class="btn btn-sm green" id="toBatchExport">
											批量导出</button>
										</sec:authorize>
									
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
							<!-- 设置是否为公司领导对数据进行显示过滤 -->
							<c:set var="isManager" value="${userSession.userName == 'FLI' || userSession.userName =='LYY'}"></c:set>
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="crm-customer-list.do" class="form-inline">
								    <c:if test="${isManager}">
								    <label for="customerType">类型</label>
							    	<select id="customerType" name="customerType" class="form-control required">
										<option value=""></option>
										<option value="公海" <c:if test="${param.customerType == '公海'}">selected</c:if>>公海</option>
										<option value="跟进" <c:if test="${param.customerType == '跟进'}">selected</c:if> >跟进</option>
										<option value="合作" <c:if test="${param.customerType == '合作'}">selected</c:if> >合作</option>
								    </select>
								    </c:if>
								    <label for="customerName">名称</label>
								    <input type="text" id="customerName" name="customerName" value="${param.customerName}" class="form-control">
								    
								    <label for="province">省份</label>
								    <input type="text" id="province" name="province" value="${param.province}" class="form-control input-small">
								    
								    <label for="city">地市</label>
								    <input type="text" id="city" name="city" value="${param.city}" class="form-control input-small">
								    
								    <c:if test="${isManager}">
								    <label for="followerDisplayName">跟进人</label>
								    <input type="text" id="followerDisplayName" name="followerDisplayName" value="${param.followerDisplayName}" class="form-control input-small">
								    </c:if>
								    
								    <label for="typeByContact">选择联系计划</label>
								    <select id="typeByContact" name="typeByContact" class="form-control">
								    	<option value=""></option>
								    	<option value="ContactedToday" <c:if test="${param.typeByContact == 'ContactedToday'}">selected</c:if> >今天已联系</option>
								    	<option value="ContactedWeek" <c:if test="${param.typeByContact == 'ContactedWeek'}">selected</c:if> >本周已联系</option>
										<option value="ContactedMonth" <c:if test="${param.typeByContact == 'ContactedMonth'}">selected</c:if> >本月已联系</option>
										<option value="ToContactToday" <c:if test="${param.typeByContact == 'ToContactToday'}">selected</c:if> >今天需联系</option>
										<option value="ToContactWeek" <c:if test="${param.typeByContact == 'ToContactWeek'}">selected</c:if> >本周需联系</option>
										<option value="ToContactMonth" <c:if test="${param.typeByContact == 'ToContactMonth'}">selected</c:if> >本月需联系</option>
										<option value="CreateInToday" <c:if test="${param.typeByContact == 'CreateInToday'}">selected</c:if> >今天新录入</option>
										<option value="CreateInWeek" <c:if test="${param.typeByContact == 'CreateInWeek'}">selected</c:if> >本周新录入</option>
										<option value="CreateInMonth" <c:if test="${param.typeByContact == 'CreateInMonth'}">selected</c:if> >本月新录入</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control required">
										<option value=""></option>
										<option value="未跟进" <c:if test="${param.status == '未跟进'}">selected</c:if>>未跟进</option>
										<option value="申请中" <c:if test="${param.status == '申请中'}">selected</c:if> >申请中</option>
										<option value="已跟进" <c:if test="${param.status == '已跟进'}">selected</c:if> >已跟进</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="crm-customer-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <c:if test="${isManager}">
								                    <th class="sorting" name="CUSTOMER_Type">类型</th>
								                    </c:if>
								                    <th class="sorting" name="CUSTOMER_NAME">名称</th>
								                    <th class="sorting" name="CUSTOMER_GRADE">等级</th>
								                    <th class="sorting" name="CREDIT_GRADE">信用</th>
								                    <th class="sorting" name="CARGO_CONDITION">经营范围及产品货量</th>
								                    <th class="sorting" name="TRANSPORT_TYPE">运输方式</th>
								                    <th class="sorting" name="ADDRESS">地址</th>
								                    <!--  
								                    <th class="sorting" name="COUNTRY">国别</th>
								                    -->
								                    <th class="sorting" name="PROVINCE">省份</th>
								                    <th class="sorting" name="CITY">城市</th>
								                    <th class="sorting" name="CONTACT_PERSON">联系人</th>
								                    <th class="sorting" name="CONTACT_POSITION">职位</th>
								                    <th class="sorting" name="CONTACT_PHONE">电话</th>
								                    <th class="sorting" name="CONTACT_MAIL">邮箱</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">跟进/申请部门</th>
								                    <th class="sorting" name="USER_ID">跟进/申请人</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <!--  
								                    <th>操作</th>
								                    -->
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <!-- 跟进人可以修改相应信息或者公司领导 -->
								                <tr <c:if test="${item.follower.id == userSession.id || isManager}">ondblclick="location.href = 'crm-customer-input.do?id=${item.id}'" </c:if>
								                	<c:if test="${item.status == '申请中'}">style="border-bottom:2px solid red;"</c:if> >
								                	<c:set var="followerId" value="${item.follower.id}"></c:set>
								                	<c:set var="currentUserId" value="${userSession.id}"></c:set>
								                	<%
								                	pageContext.setAttribute("isSuperiorOrShepherd", 
								                			UserUtil.isSuperiorOrShepherd((String)pageContext.getAttribute("currentUserId"), (String)pageContext.getAttribute("followerId")));
									              	%>
									              	<!-- 公司领导可以看到所有，其他则按照规则 -->
								                	<c:set var="canShow" 
								                		value="${userSession.userName == 'FLI' || userSession.userName =='LYY' || (item.follower.id == userSession.id && item.status == '已跟进') || isSuperiorOrShepherd}"></c:set>
								                	
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <c:if test="${isManager}">
								                    <td>${item.customerType}</td>
								                    </c:if>
								                    <td>${item.customerName}</td>
								                    <td>${!canShow ? '' : item.customerGrade}</td>
								                    <td>${!canShow ? '' : item.creditGrade}</td>
								                    <td>${!canShow ? '' : item.cargoCondition}</td>
								                    <td>${!canShow ? '' : item.transportType}</td>
								                    <td>${!canShow ? '' : item.address}</td>
								                    <td>${!canShow ? '' : item.province}</td>
								                    <td>${!canShow ? '' : item.city}</td>
								                    <td>${!canShow ? '' : item.contactPerson}</td>
								                    <td>${!canShow ? '' : item.contactPosition}</td>
								                    <td>${!canShow ? '' : item.contactPhone}</td>
								                    <td>${!canShow ? '' : item.contactMail}</td>
								                    <td>${!canShow ? '' : item.orgEntity.orgEntityName}</td>
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
	
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量导入</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form class="dropzone" id="batchImportForm" action="crm-customer-to-import-customer.do" enctype="multipart/form-data" method="post" class="m-form-blank">
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
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
	 	    	'customerType': '${param.customerType}',
	 	        'customerName': '${param.customerName}',
	 	        'province': '${param.province}',
	 	        'city': '${param.city}',
	 	        'followerDisplayName': '${param.followerDisplayName}',
	 	        'status': '${param.status}',
	 	        'typeByContact': '${param.typeByContact}'
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
	
	$(document).delegate('#batchImport', 'click',function(e){
		var margin = (window.screen.availWidth -800)/2;
		$('#batchImportModal').css("margin-left", margin);
		$('#batchImportModal').css("width","800px");
		$('#batchImportModal').modal();
	});
	
// 	$(document).delegate('#batchExport', 'click',function(e){
// 		window.open('crm-customer-to-export-customer.do');
// 	});
	
	$(document).delegate('#toBatchExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length > 0){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('crm-customer-to-export-batch-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('客户信息列表.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择一条数据！');
		}
		
	});
	
	
	$(document).delegate('#toFollowCustomer', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length != 1){
			alert('请选择一条数据！');
		}else{
			window.location.href = 'crm-customer-to-follow-customer.do?crmCustomerId=' + $('#dynamicGridForm .selectedItem:checked').val();
		}
	});
	//申请跟进
	$(document).delegate('#toApplyFollow', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-to-apply-follow.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '未跟进'){
					if(i == 0){
						url += 'crmCustomerId=' +　$(item).val();
					}else{
						url += '&crmCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认客户状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认客户状态！');
			}
		}
	});
	
	//同意跟进
	$(document).delegate('#toAgreeFollow', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-to-agree-follow.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '申请中'){
					if(i == 0){
						url += 'crmCustomerId=' +　$(item).val();
					}else{
						url += '&crmCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认客户状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认客户状态！');
			}
		}
	});
	
	//同意跟进
	$(document).delegate('#toRefuseFollow', 'click', function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var flag = true;
			var url = 'crm-customer-to-refuse-follow.do?';
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status == '申请中' || status == '已跟进'){
					if(i == 0){
						url += 'crmCustomerId=' +　$(item).val();
					}else{
						url += '&crmCustomerId=' +　$(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag){
				if(window.confirm('确认提交？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败，请确认客户状态！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败，请确认客户状态！');
			}
		}
	});
    </script>
  </body>

</html>
