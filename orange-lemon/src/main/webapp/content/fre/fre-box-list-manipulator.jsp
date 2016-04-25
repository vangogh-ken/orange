<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>集装箱管理(操作)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>集装箱管理(操作)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="location.href='fre-box-input-manipulator.do'">
										新增<i class="fa fa-arrows"></i></button>
								
									<button class="btn btn-sm red" onclick="if(canRemove()){ table.removeAll();} else{alert('只能删除状态为可用的集装箱！');}">
										删除<i class="fa fa-arrows-alt"></i></button>
											
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
								<form name="searchForm" method="post" action="fre-box-list-manipulator.do" class="form-inline">
								    <label for="boxNumber">箱号</label>
								    <input type="text" id="boxNumber" name="boxNumber" value="${param.boxNumber}" class="form-control">
								    
								    <label for="boxType">箱型</label>
								    <input type="text" id="boxType" name="boxType" value="${param.boxType}" class="form-control">
								    
								    <label for="boxBelong">箱属</label>
								    <input type="text" id="boxBelong" name="boxBelong" value="${param.boxBelong}" class="form-control">
								    
								    <label for="boxSource">集装箱来源</label>
								    <select id="boxSource" name="boxSource" class="form-control">
								    	<option value="" <c:if test="${param.boxSource == ''}">selected</c:if> ></option>
								    	<option value="外理箱" <c:if test="${param.boxSource == '外理箱'}">selected</c:if> >外理箱</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value="" <c:if test="${param.status == ''}">selected</c:if> ></option>
								    	<option value="可用" <c:if test="${param.status == '可用'}">selected</c:if> >可用</option>
								    	<option value="已选箱" <c:if test="${param.status == '已选箱'}">selected</c:if> >已选箱</option>
								    	<option value="已放箱" <c:if test="${param.status == '已放箱'}">selected</c:if> >已放箱</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-box-remove-manipulator.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="BOX_NUMBER">箱号</th>
								                    <th class="sorting" name="BOX_TYPE">箱型</th>
								                    <th class="sorting" name="BOX_BELONG">箱属</th>
								                    <th class="sorting" name="BOX_CONDITION">箱况</th>
								                    <th class="sorting" name="BOX_SOURCE">集装箱来源</th>
								                    <th class="sorting" name="EVENT_STATUS">事件状态</th>
								                    <!--  
								                    <th class="sorting" name="EMPTY_STATUS">空重状态</th>
								                    <th class="sorting" name="INOUT_STATUS">进出状态</th>
								                    <th class="sorting" name="PUT_STATUS">放箱状态</th>
								                    -->
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.boxNumber}</td>
								                    <td>${item.boxType}</td>
								                    <td>${item.boxBelong}</td>
								                    <td>${item.boxCondition}</td>
								                    <td>${item.boxSource}</td>
								                    <td>${item.eventStatus}</td>
								                    <!--  
								                    <td>${item.emptyStatus}</td>
								                    <td>${item.inoutStatus}</td>
								                    <td>${item.putStatus}</td>
								                    -->
								                    <td>${item.descn}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>
								                    	<a href="fre-box-input-manipulator.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	    	'boxSource': '${param.boxSource}',
	 	    	'boxNumber': '${param.boxNumber}',
	 	        'boxType': '${param.boxType}',
	 	        'boxBelong': '${param.boxBelong}',
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
	
	//校验集装箱是否能被删除，只能删除可用的集装箱。
	function canRemove(){
		var flag = true;
		$('#dynamicGridForm .selectedItem:checked').each(function(i, item){
			if($('#' + $(item).val() + 'status').text() != '可用'){
				flag = false;
				return false;
			}
		});
		
		return flag;
	}
    </script>
  </body>

</html>
