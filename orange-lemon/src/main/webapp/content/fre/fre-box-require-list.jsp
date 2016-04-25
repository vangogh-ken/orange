<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>箱需管理(维护)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>箱需管理(维护)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
									<button class="btn btn-sm red" onclick="location.href='fre-box-require-input.do'">
										新增<i class="fa fa-arrows"></i></button>
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
									<!--  	
									<button class="btn btn-sm red" id="addBox">
										选箱<i class="fa fa-chevron-down"></i></button>
									
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										放箱<i class="fa fa-chevron-down"></i></button>
										-->
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
								<form name="searchForm" method="post" action="fre-box-require-list.do" class="form-inline">
								    <label for="boxNumber">箱号</label>
								    <input type="text" id="boxNumber" name="boxNumber" value="${param.boxNumber}" class="form-control">
								    
								    <label for="boxType">箱型</label>
								    <input type="text" id="boxType" name="boxType" value="${param.boxType}" class="form-control">
								    
								    <label for="boxBelong">箱属</label>
								    <input type="text" id="boxBelong" name="boxBelong" value="${param.boxBelong}" class="form-control">
								    
								    <label for="TDH">提单号</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value="" <c:if test="${param.status == ''}">selected</c:if> ></option>
								    	<option value="待选箱" <c:if test="${param.status == '待选箱'}">selected</c:if> >待选箱</option>
								    	<option value="已选箱" <c:if test="${param.status == '已选箱'}">selected</c:if> >已选箱</option>
								    	<option value="已放箱" <c:if test="${param.status == '已放箱'}">selected</c:if> >已放箱</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<!-- 选择集装箱 -->
							<article class="m-widget" id="boxAcrticle">
								<form id="boxForm" name="boxForm" method="post" action="fre-order-box-save-require.do" class="form-inline">
								   	<button class="btn btn-sm red" onclick="document.boxForm.submit()">确定</button>
								    <label for="boxNumber">箱号</label>
								    <div class="input-icon listPicker left" style="margin-left:80px;margin-top:-30px;width:200px;">
									  <i class="fa fa-stop boxItemModal-add-on" urlAttr='${ctx}/fre/fre-box-by-requireid.do' style="cursor: pointer;"></i>
									  <input type="text" id="boxNumberName" value="" class="form-control input-large">
								    </div>
								    <input type="hidden" id="boxIds" name="boxIds" value="" >
								    <input type="hidden" id="freightOrderBoxRequireId" name="freightOrderBoxRequireId" value="" >
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-order-box-require-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="FRE_ORDER_ID">订单</th>
								                    <th>品名</th>
								                    <th>操作员</th>
								                    <th>业务员</th>
								                    <th class="sorting" name="BOX_TYPE">箱型</th>
								                    <th class="sorting" name="BOX_BELONG">箱属</th>
								                    <th class="sorting" name="BOX_CONDITION">箱况</th>
								                    <th class="sorting" name="BOX_COUNT">数量</th>
								                    <th class="sorting" name="BOX_SOURCE">集装箱来源</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="BL_NO">提单号</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                   <td>${item.freightOrder.orderNumber}</td>
								                    <td>${item.freightOrder.cargoName}</td>
								                    <td>${item.freightOrder.manipulator}</td>
								                    <td>${item.freightOrder.salesman}</td>
								                    <td>${item.boxType}</td>
								                    <td>${item.boxBelong}</td>
								                    <td>${item.boxCondition}</td>
								                    <td>${item.boxCount}</td>
								                    <td>${item.boxSource}</td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.blNo}</td>
								                    <td>
								                    	<a href="fre-box-require-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	 	    	'boxNumber': '${param.boxNumber}',
	 	        'boxType': '${param.boxType}',
	 	        'boxBelong': '${param.boxBelong}',
	 	        'TDH': '${param.TDH}',
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
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	$(function(){
		$('#boxAcrticle').hide();
	});
	
	$(document).delegate('#addBox', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return;
		}else{
			var freightOrderBoxRequireId = $('.selectedItem:checked').val();
			var url = $('#boxForm .boxItemModal-add-on').attr('urlAttr');
			if(url.lastIndexOf('?')>0){
				url = url.split('?')[0] + '?freightOrderBoxRequireId=' + freightOrderBoxRequireId;
			}else{
				url = url + '?freightOrderBoxRequireId=' + freightOrderBoxRequireId;
			}
			$('#boxForm .boxItemModal-add-on').attr('urlAttr', url);
			$('#boxForm #freightOrderBoxRequireId').val(freightOrderBoxRequireId);
			$('#boxAcrticle').slideToggle(200);
		}
	});
	
	$(function() {
		new createListPicker({
			title:'集装箱列表', //标题
			modalId: 'boxItemModal',//modalID
			formId:'boxItemForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','箱号', '箱型','箱属', '箱况','来源','空重状态', '进出状态','放箱状态'],//列表头 数组
			tbody:['id', 'boxNumber','boxType', 'boxBelong', 'boxCondition','boxSource', 'emptyStatus', 'inoutStatus', 'putStatus'],//字段数据 数组
			tableId:'boxItemTable',//表ID
			multiple: true,//是否多选
			canSelect:true,//是否可返回值
			valueType:'id',//需要的取值字段
			valueInput:'boxIds',//取值填至何处
			displayType:'boxNumber',//显示的取值字段
			displayInput:'boxNumberName'//显示填至何处
		});
	});
    </script>
  </body>

</html>
