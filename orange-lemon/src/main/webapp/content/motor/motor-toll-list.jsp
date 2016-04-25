<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>过路费管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>过路费管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='motor-toll-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
										<button class="btn btn-sm green" id="batchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="motor-toll-list.do" class="form-inline">
								    <label for="headstockNumber">车牌号</label>
								    <input type="text" id="headstockNumber" name="headstockNumber" value="${param.headstockNumber}" class="form-control input-xsmall">
								    
								    <label for="displayName">司机</label>
								    <input type="text" id="displayName" name="displayName" value="${param.displayName}" class="form-control input-xsmall">
								    
								    <label for="beginStation">进站</label>
								    <input type="text" id="beginStation" name="beginStation" value="${param.beginStation}" class="form-control input-xsmall">
								    <label for="arriveStation">出站</label>
								    <input type="text" id="arriveStation" name="arriveStation" value="${param.arriveStation}" class="form-control input-xsmall">
								    
								    <label for="beginTime">进站时间</label>
								    <input type="text" id="beginTimeStart" name="beginTimeStart" value="${param.beginTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="beginTimeEnd" name="beginTimeEnd" value="${param.beginTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="arriveTimeStart">出站时间</label>
								    <input type="text" id="arriveTimeStart" name="arriveTimeStart" value="${param.arriveTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="arriveTimeEnd" name="arriveTimeEnd" value="${param.arriveTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="status">状态</label>
								    <input type="text" id="status" name="status" value="${param.status}" class="form-control input-xsmall">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="motor-toll-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="MOTOR_TRUCK_ID">车牌号</th>
								                    <th class="sorting" name="MOTOR_DRIVER_ID">司机</th>
								                    <th class="sorting" name="TRUCK_TYPE">车型</th>
								                    <th class="sorting" name="TOTAL_WEIGHT">总重</th>
								                    <th class="sorting" name="BEGIN_STATION">进站</th>
								                    <th class="sorting" name="BEGIN_TIME">进站时间</th>
								                    <th class="sorting" name="ARRIVE_STATION">出站</th>
								                    <th class="sorting" name="ARRIVE_TIME">出站时间</th>
								                    <th class="sorting" name="MONEY_COUNT">金额</th>
								                    <th class="sorting" name="FAS_INVOICE_TYPE_ID">票种</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.motorcadeTruck.headstockNumber}</td>
								                    <td>${item.motorcadeDriver.displayName}</td>
								                    <td>${item.truckType}</td>
								                    <td>${item.totalWeight}</td>
								                    <td>${item.beginStation}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.beginTime}" pattern="yyyy-MM-dd HH:mm"/>
								                    </td>
								                    <td>${item.arriveStation}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.arriveTime}" pattern="yyyy-MM-dd HH:mm"/>
								                    </td>
								                    <td>${item.moneyCount}</td>
								                    <td>${item.fasInvoiceType.typeName}</td>
								                    <td>${item.descn}</td>
								                    <td>${item.status}</td>
								                    <td>
								                    	<a href="motor-toll-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	    	'headstockNumber': '${param.headstockNumber}',
	 	        'displayName': '${param.displayName}',
	 	        'beginStation': '${param.beginStation}',
	 	        'arriveStation': '${param.arriveStation}',
	 	        'beginTimeStart': '${param.beginTimeStart}',
	 	        'beginTimeEnd': '${param.beginTimeEnd}',
	 	        'arriveTimeStart': '${param.arriveTimeStart}',
	 	        'arriveTimeEnd': '${param.arriveTimeEnd}',
	 	        'status': '${param.status}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'fre-box-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
	
	//批量导出
	$(document).delegate('#batchExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length > 0){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('motor-toll-to-export-toll-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('过路费列表.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择数据！');
		}
	});
    </script>
  </body>

</html>
