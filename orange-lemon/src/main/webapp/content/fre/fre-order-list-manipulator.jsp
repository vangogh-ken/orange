<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>订单管理(操作)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>订单管理(操作)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP">
										<button class="btn btn-sm green" id="addRequire">
											箱需详情</button>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP">
										<button class="btn btn-sm green" id="viewOrderBox">
											箱封详情</button>
										</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
								</div>
								<div class="pull-right" >
									<label for="pageSize" >每页显示</label>
									<select id="pageSize" class="m-page-size" > 
									    <option value="10">10条</option> 
									    <option value="20">20条</option>
									    <option value="50">50条</option>
									 </select>
								</div>
							</article>
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="fre-order-list-manipulator.do" class="form-inline">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-small">
								    
								    <label for="delegatePart">委托单位</label>
								    <input type="text" id="delegatePart" name="delegatePart" value="${param.delegatePart}" class="form-control input-small">
								    
								    <label for="salesman">业务员</label>
								    <input type="text" id="salesman" name="salesman" value="${param.salesman}" class="form-control input-xsmall" >
								    <label for="JZX">集装箱</label>
								    <input type="text" id="JZX" name="JZX" value="${param.JZX}" class="form-control input-xsmall" >
								    <label for="TDH">提单号</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-xsmall" >
								    <!--  
								    <label for="CMHC">船名航次</label>
								    <input type="text" id="CMHC" name="CMHC" value="${param.CMHC}" class="form-control input-xsmall" >
								    -->
								    <label for="orderStatus">订单状态</label>
								    <select id="orderStatus" name="orderStatus" class="form-control input-xsmall">
								    	<option value=""></option>
								    	<option value="审核中" <c:if test="${param.orderStatus == '审核中'}">selected</c:if> >审核中</option>
								    	<option value="已审核" <c:if test="${param.orderStatus == '已审核'}">selected</c:if> >已审核</option>
								    	<option value="确认追回" <c:if test="${param.orderStatus == '确认追回'}">selected</c:if> >确认追回</option>
								    	<option value="已完成" <c:if test="${param.orderStatus == '已完成'}">selected</c:if> >已完成</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="ORDER_NUMBER">订单编号</th>
								                    <th class="sorting" name="ORDER_STATUS">订单状态</th>
								                    <th class="sorting" name="EXPENSE_STATUS">费用状态</th>
								                    <th class="sorting" name="DELEGATE_PART">委托单位</th>
								                    <th class="sorting" name="CARGO_NAME">品名</th>
								                    <th>箱型箱量箱属</th>
								                    <th class="sorting" name="CARGO_OWNER">货主</th>
								                    <th class="sorting" name="SALESMAN">业务员</th>
								                    <th class="sorting" name="MANIPULATOR">操作员</th>
								                    <th class="sorting" name="CARGO_AMOUNT">件数</th>
								                    <th class="sorting" name="CARGO_WEIGHT">重量</th>
								                    <th class="sorting" name="CARGO_CAPACITY">体积</th>
								                    <th class="sorting" name="PLACE_TIME">下单时间</th>
								                    <th class="sorting" name="FINISH_TIME">完成时间</th>
								                    <th class="sorting" name="ORDER_SCOPE">业务归属</th>
								                    <th class="sorting" name="FIRST_TYPE">一级类型</th>
								                    <th class="sorting" name="SECOND_TYPE">二级类型</th>
								                    <th class="sorting" name="THIRD_TYPE">三级类型</th>
								                    <th class="sorting" name="FOURTH_TYPE">四级类型</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="DELEGATE_CONTACT">委托联系人</th>
								                    <th class="sorting" name="COMMISSION">委托书</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="window.open('fre-order-input-manipulator.do?id=${item.id}');" <c:if test="${item.orderStatus == '已完成'}">style="border-bottom:2px solid green;"</c:if> <c:if test="${item.orderStatus == '确认追回'}">style="border-bottom:2px solid red;"</c:if>>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.orderNumber}</td>
								                    <td id="${item.id}orderStatus">${item.orderStatus}</td>
								                    <td id="${item.id}expenseStatus">${item.expenseStatus}</td>
								                    <td>${item.delegatePart}</td>
								                    <td>${item.cargoName}</td>
								                    <td>
								                    	<c:forEach items="${item.freightBoxRequires}" var="freightBoxRequire">
								                    	${freightBoxRequire.boxCount}*${freightBoxRequire.boxType} ${freightBoxRequire.boxBelong}
								                    	</c:forEach>
								                    </td>
								                    <td>${item.cargoOwner}</td>
								                    <td>${item.salesman}</td>
								                    <td>${item.manipulator}</td>
								                    <td>${item.cargoAmount}</td>
								                    <td>${item.cargoWeight}</td>
								                    <td>${item.cargoCapacity}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                     <td>
								                    	<fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>${item.orderScope}</td>
								                    <td>${item.firstType}</td>
								                    <td>${item.secondType}</td>
								                    <td>${item.thirdType}</td>
								                    <td>${item.fourthType}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td>${item.delegateContact}</td>
								                    <td><a href="fre-order-download-commission.do?freightOrderId=${item.id}" target="_blank">委托书</a></td>
								                    <td>${item.descn}</td>
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
	<!-- 箱需-->
	<div class="modal fade" id="requireModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">箱需详情</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
					<form id="requireBtnForm" name="requireBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP">
						<button type="button" class="btn btn-sm green" onclick="releaseRequire();">放箱</button>
						</sec:authorize>
					</form>
				</article>
				<!-- 已有箱需 -->
				<article class="m-widget">
				<form id="requireHasAddForm" action="" method="post" class="m-form-blank"></form>
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
	 	        'orderNumber': '${param.orderNumber}',
	 	        'delegatePart': '${param.delegatePart}',
	 	        'salesman': '${param.salesman}',
	 	        //'manipulator': '${param.manipulator}',
	 	        'orderStatus': '${param.orderStatus}',
 	        	'JZX': '${param.JZX}',
        		'TDH': '${param.TDH}',
       			'CMHC': '${param.CMHC}'
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
	
	//点击模化窗口关闭按钮时刷新页面
	/* $(document).delegate('button', 'click', function(e){
		if($(this).attr('data-dismiss') == 'modal'){
			window.location.href = window.location.href;
		}
	}); */
	
	//查看数据 集装箱列表
	$(document).delegate('#viewOrderBox', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var url = 'fre-order-box-to-view.do?freightOrderId=' + freightOrderId;
			listData(url, '箱封详情', 1000,
					['起始地','终止地', '箱型','箱属','箱况','集装箱来源','箱号','封号', '状态','提箱地'], 
					['freightBoxRequire.beginStation','freightBoxRequire.arriveStation','freightBoxRequire.boxType', 
					 'freightBoxRequire.boxBelong', 'freightBoxRequire.boxCondition','freightBoxRequire.boxSource',
					 'freightBox.boxNumber', 'freightSeal.sealNumber','status','location']
			);
		}
	});
	
	//添加箱需
	$(document).delegate('#addRequire', 'click',function(e){
		addRequire();
	});
	
	function addRequire(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderId = $('.selectedItem:checked').val();
			var orderStatus = $('#' + freightOrderId + 'orderStatus').text();
			$.ajax({
				url:'fre-box-require-to-add-require.do?freightOrderId=' + freightOrderId,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					var hasAddData = data.hasAddData;
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>起始地</th><th>终止地</th><th>集装箱来源</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>状态</th><th>提单号</th></tr></thead><tbody>';
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						html += '<td>' + item.beginStation + '</td>';
						html += '<td>' + item.arriveStation + '</td>';
						html += '<td>' + item.boxSource + '</td>';
						html += '<td>' + item.boxType + '</td>';
						html += '<td>' + item.boxBelong + '</td>';
						html += '<td>' + item.boxCondition + '</td>';
						html += '<td>' + item.boxCount + '</td>';
						html += '<td id="'+ item.id + 'status' +'">' + item.status+ '</td>';
						if(item.status == '未提交'){
							html += '<td ><input id="'+ item.id +'blNo" name="'+ item.id +'" value="'+ (item.blNo == null ? '' : item.blNo) +'" class="form-control required"></td></tr>';
						}else{
							html += '<td>' + item.blNo + '</td></tr>';
						}
					});
					html += "</tbody></table>";
					$('#requireHasAddForm').html(html);
					
					//隐藏或显示
					if(orderStatus == '审核中' || orderStatus == '已审核'){
						$('#requireBtnForm').parent().show();
					}else{
						$('#requireBtnForm').parent().hide();
					}
					
					var margin = (window.screen.availWidth - 1200)/2;
					$('#requireModal').css("margin-left", margin);
					$('#requireModal').css("width","1200px");
					$('#requireModal').modal();
				},
				error:function(){
				}
			});
			return true;
		}
	}
	//放箱，放箱时填写订单号。
	function releaseRequire(){
		var url = 'fre-box-require-done-release-require.do?';
		if($('#requireHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			if($('#requireHasAddForm').valid() && window.confirm('提示: 请在备注填写该箱需的提单号！')){
				var flag = true;
				$('#requireHasAddForm .selectedItemId:checked').each(function(i, item){
					var status = $('#requireHasAddForm #' + $(item).val() + 'status').text();
					if(status == '未提交'){
						if(i == 0){
							url += 'freightBoxRequireId=' + $(item).val();
						}else{
							url += '&freightBoxRequireId=' + $(item).val();
						}
					}else{
						flag = false;
						return false;//跳出循环
					}
				});
				if(flag){
					$.post(url, $('#requireHasAddForm').serialize(), function(data){
						if(data == 'success'){
							alert('放箱成功！');
							addRequire();
						}else{
							alert('放箱失败，请确认箱需状态及提单号是否有重复！');
						}
					});
				}else{
					alert('放箱失败，请确认箱需状态或提单号是否填报！');
				}
			}
		}
	}
	/* $(document).delegate('.selectedItemIdAll', 'click', function(){//扩展为通用
		if($(this).attr('checked') == 'checked'){
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').attr('checked', 'checked');
				});
			});
		}else{
			var table = $(this).parent().parent().parent().parent().find('tbody');
			$.each(table.find('tr'), function(i, item){
				$.each($(item).find('td'), function(n, ele){
					$(ele).children('.selectedItemId').removeAttr('checked');
				});
			});
		}
	}); */
    </script>
  </body>

</html>
