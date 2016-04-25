<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>提成管理</title>
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
							<div class="caption"><i class="fa fa-anchor"></i>提成管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除提成</button>
									<button class="btn btn-sm red" id="recallSalesmanDeduct">
										取消销售</button>
										
									<button class="btn btn-sm red" id="recallManipulatorDeduct">
										取消操作</button>	
									</sec:authorize>
									
									<button class="btn btn-sm green" id="doneSalesmanDeduct">
										销售提成</button>
									<button class="btn btn-sm green" id="doneManipulatorDeduct">
										操作提成</button>
										
									<button class="btn btn-sm red" id="batchExport">
										批量导出</button>
													
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
								<form name="searchForm" method="post" action="fre-deduct-list.do" class="form-inline">
								    <label for="orderScope">归属</label>
								    <input type="text" id="orderScope" name="orderScope" value="${param.orderScope}" class="form-control input-xsmall">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    <label for="delegatePart">委托单位</label>
								    <input type="text" id="delegatePart" name="delegatePart" value="${param.delegatePart}" class="form-control input-xsmall">
								    
								    <label for="cargoName">品名</label>
								    <input type="text" id="cargoName" name="cargoName" value="${param.cargoName}" class="form-control input-xsmall">
								    
								    <label for="salesman">销售</label>
								    <input type="text" id="salesman" name="salesman" value="${param.salesman}" class="form-control input-xsmall">
								    <label for="service">客服</label>
								    <input type="text" id="service" name="service" value="${param.service}" class="form-control input-xsmall">
								    <label for="manipulator">操作</label>
								    <input type="text" id="manipulator" name="manipulator" value="${param.manipulator}" class="form-control input-xsmall">
								    
								    <hr class="search-input-spliter">
								    
								    <label for="deductType">类型</label>
								    <select id="deductType" name="deductType" class="form-control">
								    	<option value=""></option>
								    	<option value="订单提成" <c:if test="${param.deductType == '订单提成'}">selected</c:if> >订单提成</option>
								    	<option value="标箱提成" <c:if test="${param.deductType == '标箱提成'}">selected</c:if> >标箱提成</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="DEDUCT_PREPARED" <c:if test="${param.status == 'DEDUCT_PREPARED'}">selected</c:if> >预备</option>
								    	<option value="RECONCILE_HAVNT" <c:if test="${param.status == 'RECONCILE_HAVNT'}">selected</c:if> >未销账</option>
								    	<option value="RECONCILE_HAVE" <c:if test="${param.status == 'RECONCILE_HAVE'}">selected</c:if> >已销账</option>
								    	<option value="DEDUCT_HAVE" <c:if test="${param.status == 'DEDUCT_HAVE'}">selected</c:if> >已提成，无修改</option>
								    	<option value="DEDUCT_REVISED" <c:if test="${param.status == 'DEDUCT_REVISED'}">selected</c:if> >已提成，有修改</option>
								    	<option value="DEDUCT_NONE" <c:if test="${param.status == 'DEDUCT_NONE'}">selected</c:if> >无提成</option>
								    	<option value="DEDUCT_SATE" <c:if test="${param.status == 'DEDUCT_SATE'}">selected</c:if> >负提成</option>
								    </select>
								    <label for="settleTime">结算时间</label>
								    <input type="text" id="settleTimeStart" name="settleTimeStart" value="${param.settleTimeStart}" class="form-control datepicker input-xsmall">
								    -
								    <input type="text" id="settleTimeEnd" name="settleTimeEnd" value="${param.settleTimeEnd}" class="form-control datepicker input-xsmall">
								    
								    <label for="reconcileTime">销账时间</label>
								    <input type="text" id="reconcileTimeStart" name="reconcileTimeStart" value="${param.reconcileTimeStart}" class="form-control datepicker input-xsmall">
								    -
								    <input type="text" id="reconcileTimeEnd" name="reconcileTimeEnd" value="${param.reconcileTimeEnd}" class="form-control datepicker input-xsmall">
								    
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-deduct-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="DEDUCT_TYPE">类型</th>
								                    <th class="sorting" name="FRE_ORDER_ID">订单编号</th>
								                    <th>委托单位</th>
								                    <th>业务归属</th>
								                    <th class="sorting" name="ORG_ENTITY_ID">销售部门</th>
								                    <th>销售</th>
								                    <th>客服</th>
								                    <th>操作</th>
								                    <th>箱型箱量箱属</th>
								                    <th class="sorting" name="ORDER_BALANCE">订单收支差</th>
								                    <th class="sorting" name="DEDUCT_BALANCE">提成收支差</th>
								                    <th class="sorting" name="DEDUCT_COUNT_SALESMAN">销售提成</th>
								                    <th class="sorting" name="SETTLE_DONE_SALESMAN">是否结算</th>
								                    <th class="sorting" name="SETTLE_TIME_SALESMAN">应提成时间</th>
								                    
								                    <th class="sorting" name="DEDUCT_COUNT_SERVISE">客服提成</th>
								                    <th class="sorting" name="SETTLE_DONE_SERVISE">是否结算</th>
								                    <th class="sorting" name="SETTLE_TIME_SERVISE">应提成时间</th>
								                    
								                    <th class="sorting" name="DEDUCT_COUNT_MANIPULATOR">操作提成</th>
								                    <th class="sorting" name="SETTLE_DONE_MANIPULATOR">是否结算</th>
								                    <th class="sorting" name="SETTLE_TIME_MANIPULATOR">应提成时间</th>
								                    <th class="sorting" name="DEDUCT_TIME">提成时间</th>
								                    <th class="sorting" name="RECONCILE_TIME">销账时间</th>
								                    <th>下单时间</th>
								                    <th>资金占用天数</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">修改时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.deductType}</td>
								                    <td>${item.freightOrder.orderNumber}</td>
								                    <td>${item.freightOrder.delegatePart}</td>
								                    <td>${item.freightOrder.orderScope}</td>
								                    <td>${item.orgEntity.orgEntityName}</td>
								                    <td>${item.freightOrder.salesman}</td>
								                    <td>${item.freightOrder.receptionist.displayName}</td>
								                    <td>${item.freightOrder.manipulator}</td>
								                    <td>
								                    	<c:forEach items="${item.freightOrder.freightBoxRequires}" var="freightBoxRequire">
								                    	${freightBoxRequire.boxType}*${freightBoxRequire.boxCount} ${freightBoxRequire.boxBelong}
								                    	</c:forEach>
								                    </td>
								                    <td>
								                    	<fmt:formatNumber value="${item.orderBalance}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td>
								                    	<fmt:formatNumber value="${item.deductBalance}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td>
								                    	<fmt:formatNumber value="${item.deductCountSalesman}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td id="${item.id}settleDoneSalesman" valued="${item.settleDoneSalesman}">${item.settleDoneSalesman}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.settleTimeSalesman}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatNumber value="${item.deductCountService}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td id="${item.id}settleDoneService" valued="${item.settleDoneService}">${item.settleDoneService}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.settleTimeService}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    
								                    <td>
								                    	<fmt:formatNumber value="${item.deductCountManipulator}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
								                    </td>
								                    <td id="${item.id}settleDoneManipulator" valued="${item.settleDoneManipulator}">${item.settleDoneManipulator}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.settleTimeManipulator}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.deductTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.reconcileTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.freightOrder.placeTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatNumber 
								                    	value="${(((item.reconcileTime == null ? now.getTime() : item.reconcileTime.getTime()) - item.freightOrder.placeTime.getTime())/(1000*60*60*24))}" 
								                    	pattern="##.0" minFractionDigits="0" >
								                    	</fmt:formatNumber>
								                    </td>
								                    <td id="${item.id}status" valued="${item.status}">
								                    ${item.status == 'DEDUCT_PREPARED' ? '预备' : item.status == 'RECONCILE_HAVNT' ? '未销账' :
								                    item.status == 'RECONCILE_HAVE' ? '已销账' : item.status == 'DEDUCT_HAVE' ? '已提成，无修改' :
								                    item.status == 'DEDUCT_REVISED' ? '已提成，有修改' : item.status == 'DEDUCT_NONE' ? '无提成' : 
								                    item.status == 'DEDUCT_SATE' ? '负提成' : ''}
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
	 	    	'orderScope': '${param.orderScope}',
	 	        'orderNumber': '${param.orderNumber}',
	 	      	'delegatePart': '${param.delegatePart}',
	 	      	'cargoName': '${param.cargoName}',
	 	        'salesman': '${param.salesman}',
	 	        'service': '${param.service}',
	 	        'manipulator': '${param.manipulator}',
	 	        'deductType': '${param.deductType}',
	 	        'settleTimeStart': '${param.settleTimeStart}',
	 	        'settleTimeEnd': '${param.settleTimeEnd}',
	 	        'reconcileTimeStart': '${param.reconcileTimeStart}',
	 	        'reconcileTimeEnd': '${param.reconcileTimeEnd}',
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
	
	//销售提成
	$(document).delegate('#doneSalesmanDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-deduct-done-salesman-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').attr('valued')
				if(status != 'RECONCILE_HAVE'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'freightDeductId=' +　$(item).val();
					}else{
						url += '&freightDeductId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选提成状态！');
				return;
			}
		}
	});
	
	//操作提成
	$(document).delegate('#doneManipulatorDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-deduct-done-manipulator-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var settleDoneManipulator = $('#' + $(item).val() + 'settleDoneManipulator').attr('valued')
				if(settleDoneManipulator != 'DEDUCT_PREPARED'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'freightDeductId=' +　$(item).val();
					}else{
						url += '&freightDeductId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选提成状态！');
				return;
			}
		}
	});
	
	//取消销售提成
	$(document).delegate('#recallSalesmanDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-deduct-recall-salesman-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').attr('valued')
				if(status != 'DEDUCT_HAVE'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'freightDeductId=' +　$(item).val();
					}else{
						url += '&freightDeductId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选提成状态！');
				return;
			}
		}
	});
	
	//取消操作提成
	$(document).delegate('#recallManipulatorDeduct', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-deduct-recall-manipulator-deduct.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var settleDoneManipulator = $('#' + $(item).val() + 'status').attr('valued')
				if(settleDoneManipulator != 'DEDUCT_HAVE'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'freightDeductId=' +　$(item).val();
					}else{
						url += '&freightDeductId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('提交失败！');
					}else{
						alert('提交成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('提交失败！请确认所选提成状态！');
				return;
			}
		}
	});
	
	//批量导出
	$(document).delegate('#batchExport', 'click',function(e){
		if($('#dynamicGridForm .selectedItem:checked').length > 0){
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			$.post('fre-deduct-to-export-deduct-to-file.do', $('#dynamicGridForm').serialize(), function(data){
				window.open('${ctx}/util/down-file.do?fileData=' + data + '&fileName=' + encodeURIComponent('提成列表.xls'));
				box.modal('hide');
			});
		}else{
			alert('请选择数据！');
		}
	});
    </script>
  </body>

</html>
