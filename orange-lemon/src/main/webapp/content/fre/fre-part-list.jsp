<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>单位管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>单位管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='fre-part-input.do'">
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
								<form name="searchForm" method="post" action="fre-part-list.do" class="form-inline">
								    <label for="DWMC">单位名称</label>
								    <input type="text" id="DWMC" name="DWMC" value="${param.DWMC}" class="form-control">
								    
								    <label for="partType">类型</label>
								    <select id="partType" name="partType" class="form-control">
								    	<option value=""></option>
										<option value="内部部门" <c:if test="${param.partType == '内部部门' }">selected</c:if>>内部部门</option>
										<option value="正常客户" <c:if test="${param.partType == '正常客户' }">selected</c:if>>正常客户</option>
									</select>
									
									<label for="internal">是否映射到内部部门</label>
								    <select id="internal" name="internal" class="form-control">
								    	<option value=""></option>
										<option value="T" <c:if test="${param.internal == 'T' }">selected</c:if>>T</option>
										<option value="F" <c:if test="${param.internal == 'F' }">selected</c:if>>F</option>
									</select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.status == 'T' }">selected</c:if> >启用</option>
								    	<option value="F" <c:if test="${param.status == 'F' }">selected</c:if> >禁用</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-part-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="PART_NAME">单位名称</th>
								                    <th class="sorting" name="PART_TYPE">类型</th>
								                    <th class="sorting" name="INTERNAL">是否映射到部门</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">映射部门</th>
								                    <!--  
								                    <th class="sorting" name="PART_ADDRESS">地址</th>
								                    <th class="sorting" name="PART_CHARGER">负责人</th>
								                    <th class="sorting" name="PART_CONTACT">联系方式</th>
								                    <th class="sorting" name="PART_REG_ADDRESS">注册地</th>
								                    <th class="sorting" name="PART_EXP_ADDRESS">送件地</th>
								                    <th class="sorting" name="PART_FAX">传真</th>
								                    <th class="sorting" name="PART_MAIL">邮件</th>
								                    <th class="sorting" name="ENGAGE_SCOPE">经营范围</th>
								                    <th class="sorting" name="SALE_MAN">业务员</th>
								                    <th class="sorting" name="DELEGATE_STATUS">委托/外委</th>
								                    <th class="sorting" name="PUBLIC_STATUS">是否共享</th>
								                    -->
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="window.location.href = 'fre-part-input.do?id=${item.id}'">
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.partName}</td>
								                    <td>${item.partType}</td>
								                    <td>${item.internal}</td>
								                    <td>${item.orgEntity == null ? '无' : item.orgEntity.orgEntityName}</td>
								                    <!--  
								                    <td>${item.partAddress}</td>
								                    <td>${item.partCharger}</td>
								                    <td>${item.partContact}</td>
								                    <td>${item.partRegAddress}</td>
								                    <td>${item.partExpAddress}</td>
								                    <td>${item.partFax}</td>
								                    <td>${item.partMail}</td>
								                    <td>${item.engageScope}</td>
								                    <td>${item.saleMan}</td>
								                    <td>${item.delegateStatus}</td>
								                    <td>${item.publicStatus}</td>
								                    -->
								                    <td>${item.status}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="fre-part-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量导入</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form class="dropzone" id="batchImportForm" action="fre-part-to-import-part.do" enctype="multipart/form-data" method="post" class="m-form-blank">
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
	 	        'DWMC': '${param.DWMC}',
	 	        'partType': '${param.partType}',
	 	        'internal': '${param.internal}',
	 	        'status': '${param.status}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'fre-part-export.do'
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
		window.open('fre-part-to-export-part.do');
	});
    </script>
  </body>

</html>
