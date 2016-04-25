<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>渠道管理</title>
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
							<div class="caption"><i class="fa fa-users"></i>渠道管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='crm-partner-input.do'">
											新增<i class="fa fa-arrows"></i></button>
									
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
											
										<button class="btn btn-sm green" id="batchImport">
											批量导入</button>
											
										<button class="btn btn-sm green" id="batchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="crm-partner-list.do" class="form-inline">
								   <label for="partnerType">类型</label>
							    	<select id="partnerType" name="partnerType" class="form-control required">
							    		<option value="" ></option>
										<option value="外贸海船公司" <c:if test="${param.partnerType == '外贸海船公司'}">selected</c:if>>外贸海船公司</option>
										<option value="内贸海船公司" <c:if test="${param.partnerType == '内贸海船公司'}">selected</c:if> >内贸海船公司</option>
										<option value="江船公司" <c:if test="${param.partnerType == '江船公司'}">selected</c:if> >江船公司</option>
										<option value="拖车公司" <c:if test="${param.partnerType == '拖车公司'}">selected</c:if> >拖车公司</option>
										<option value="报关行" <c:if test="${param.partnerType == '报关行'}">selected</c:if> >报关行</option>
										<option value="上海中转代理" <c:if test="${param.partnerType == '上海中转代理'}">selected</c:if> >上海中转代理</option>
										<option value="订舱代理" <c:if test="${param.partnerType == '订舱代理'}">selected</c:if> >订舱代理</option>
										<option value="铁路承运人" <c:if test="${param.partnerType == '铁路承运人'}">selected</c:if> >铁路承运人</option>
										<option value="报检单位" <c:if test="${param.partnerType == '报检单位'}">selected</c:if> >报检单位</option>
										<option value="港口" <c:if test="${param.partnerType == '港口'}">selected</c:if> >港口</option>
										<option value="快递公司" <c:if test="${param.partnerType == '快递公司'}">selected</c:if> >快递公司</option>
										<option value="政府部门" <c:if test="${param.partnerType == '政府部门'}">selected</c:if> >政府部门</option>
										<option value="堆场及仓库" <c:if test="${param.partnerType == '堆场及仓库'}">selected</c:if> >堆场及仓库</option>
										<option value="其它供应商" <c:if test="${param.partnerType == '其它供应商'}">selected</c:if> >其它供应商</option>
									</select>
								    <label for="partnerName">名称</label>
								    <input type="text" id="partnerName" name="partnerName" value="${param.partnerName}" class="form-control">
								    
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
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="crm-partner-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="PARTNER_Type">类型</th>
								                    <th class="sorting" name="PARTNER_NAME">名称</th>
								                    <th class="sorting" name="PARTNER_GRADE">等级</th>
								                    <th class="sorting" name="ENGAGE_SCOPE">经营</th>
								                    <th class="sorting" name="CORE_ADVANTAGE">优势</th>
								                    <th class="sorting" name="ADDRESS">地址</th>
								                    <th class="sorting" name="COUNTRY">国别</th>
								                    <th class="sorting" name="PROVINCE">省份</th>
								                    <th class="sorting" name="CITY">城市</th>
								                    <th class="sorting" name="CONTACT_PERSON">联系人</th>
								                    <th class="sorting" name="CONTACT_POSITION">职位</th>
								                    <th class="sorting" name="CONTACT_PHONE">电话</th>
								                    <th class="sorting" name="CONTACT_MAIL">邮箱</th>
								                    <th class="sorting" name="FOLLOW_TYPE">跟进方式</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">跟进部门</th>
								                    <th class="sorting" name="USER_ID">跟进人</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <!--  
								                    <th>操作</th>
								                    -->
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="location.href = 'crm-partner-input.do?id=${item.id}'">
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.partnerType}</td>
								                    <td>${item.partnerName}</td>
								                    <td>${item.partnerGrade}</td>
								                    <td>${item.engageScope}</td>
								                    <td>${item.coreAdvantage}</td>
								                    <td>${item.address}</td>
								                    <td>${item.country}</td>
								                    <td>${item.province}</td>
								                    <td>${item.city}</td>
								                    <td>${item.contactPerson}</td>
								                    <td>${item.contactPosition}</td>
								                    <td>${item.contactPhone}</td>
								                    <td>${item.contactMail}</td>
								                    <td>${item.followType}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
								                    <td>${item.follower.displayName}</td>
								                    <td>${item.status}</td>
								                    <!--  
								                    <td>
								                    	<a href="crm-partner-follow-input.do?crmCustomerId=${item.id}" class="btn btn-sm red">联系</a>
													</td>
													-->
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
				<form class="dropzone" id="batchImportForm" action="crm-partner-to-import-partner.do" enctype="multipart/form-data" method="post" class="m-form-blank">
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
	 	        'partnerType': '${param.partnerType}',
	 	        'partnerName': '${param.partnerName}',
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
	
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('crm-partner-to-export-partner.do');
	});
    </script>
  </body>

</html>
