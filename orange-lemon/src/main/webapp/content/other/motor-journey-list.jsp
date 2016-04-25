<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>派车管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>派车管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" onclick="location.href='motor-journey-input.do'">
											新增<i class="fa fa-arrows"></i></button>
										
										<button class="btn btn-sm red" onclick="if(canRemove()) {table.removeAll();} else {alert('所选数据不能删除！');}">
											删除<i class="fa fa-arrows-alt"></i></button>
										
										<button class="btn btn-sm green" id="doneConfirmJourney">
											确认提交</button>
										<button class="btn btn-sm green" id="doneRecallJourney">
											取消确认</button>
												
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
										<sec:authorize ifAnyGranted="ROLE_CY-SYS-MANAGER">
										
										<button class="btn btn-sm green" id="batchImport">
											批量导入</button>
										<button class="btn btn-sm green" id="batchExport">
											批量导出</button>
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
								<form name="searchForm" method="post" action="motor-journey-list.do" class="form-inline">
								    <label for="motorcadeDriver">司机</label>
								    <input type="text" id="motorcadeDriver" name="motorcadeDriver" value="${param.motorcadeDriver}" class="form-control input-xsmall">
								    
								    <label for="motorcadeTruck">车牌号</label>
								    <input type="text" id="motorcadeTruck" name="motorcadeTruck" value="${param.motorcadeTruck}" class="form-control input-xsmall">
								    
								    <label for="journeyNumber">编号</label>
								    <input type="text" id="journeyNumber" name="journeyNumber" value="${param.journeyNumber}" class="form-control input-xsmall">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="未提交" <c:if test="${param.status == '未提交' }">selected</c:if> >未提交</option>
								    	<option value="已确认" <c:if test="${param.status == '已确认' }">selected</c:if> >已确认</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="motor-journey-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="MOTOR_TRUCK">车牌</th>
								                    <th class="sorting" name="MOTOR_DRIVER">司机</th>
								                    <th class="sorting" name="JOURNEY_NUMBER">编号</th>
								                    <th class="sorting" name="JOURNEY_TIME">时间</th>
								                    <th class="sorting" name="JOURNEY_ITEM">事由</th>
								                    <th class="sorting" name="DESTINATION">目的地</th>
								                    <th class="sorting" name="PREDICT_TIME">预计还车时间</th>
								                    <th class="sorting" name="OUT_KEY">取钥匙</th>
								                    <th class="sorting" name="OUT_PARK">取车地</th>
								                    <th class="sorting" name="OUT_TIME">出车时间</th>
								                    <th class="sorting" name="OUT_KILOMETER_COUNT">出车时公里数</th>
								                    <th class="sorting" name="RETURN_KEY">放钥匙</th>
								                    <th class="sorting" name="RETURN_PARK">还车地</th>
								                    <th class="sorting" name="RETURN_TIME">还车时间</th>
								                    <th class="sorting" name="RETURN_KILOMETER_COUNT">还车时公里数</th>
								                    <th class="sorting" name="DESCN">说明</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr <c:if test="${item.status == '未提交'}"> ondblclick="window.location.href='motor-journey-input.do?id=${item.id}'" </c:if> <c:if test="${item.status == '已完成'}">style="border-bottom:2px solid green;"</c:if>>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.motorcadeTruck}</td>
								                    <td>${item.motorcadeDriver}</td>
								                    <td>${item.journeyNumber}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.journeyTime}" pattern="yy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.journeyItem}</td>
								                    <td>${item.destination}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.predictTime}" pattern="yy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.outKey}</td>
								                    <td>${item.outPark}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.outTime}" pattern="yy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.outKilometerCount}</td>
								                    
								                    <td>${item.returnKey}</td>
								                    <td>${item.returnPark}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.returnTime}" pattern="yy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.returnKilometerCount}</td>
								                    <td>${item.descn}</td>
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
	
	<!-- 复制新增-->
	<div class="modal fade" id="toAddDispatchByCopyModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">复制订单</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="toAddDispatchByCopyForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="$('#toAddDispatchByCopyForm').submit();">确定</button>
			</div>
		</div>
	</div>
	
	
	<!-- 批量导入 -->
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量导入</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form class="dropzone" id="batchImportForm" action="fas-invoice-type-to-import-invoice-type.do" enctype="multipart/form-data" method="post" class="m-form-blank">
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
	 	        'motorcadeDriver': '${param.motorcadeDriver}',
	 	        'motorcadeTruck': '${param.motorcadeTruck}',
	 	        'journeyNumber': '${param.journeyNumber}',
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
	
	$(document).delegate('#batchImport', 'click',function(e){
		var margin = (window.screen.availWidth -800)/2;
		$('#batchImportModal').css("margin-left", margin);
		$('#batchImportModal').css("width","800px");
		$('#batchImportModal').modal();
	});
	
	$(document).delegate('#batchExport', 'click',function(e){
		window.open('fas-invoice-type-to-export-invoice-type.do');
	});
	
	//点击模化窗口关闭按钮时刷新页面
	$(document).delegate('button', 'click', function(e){
		if($(this).attr('data-dismiss') == 'modal'){
			window.location.href = window.location.href;
		}
	});
	
	//判断其是否可以删除
	function canRemove(){
		if($('.selectedItem:checked').length > 0){
			var url = 'motor-journey-to-remove-journey.do?';
			$('.selectedItem:checked').each(function(i, item){
				if(i == 0){
					url += 'motorcadeJourneyId=' + $(item).val();
				}else{
					url += '&motorcadeJourneyId=' + $(item).val();
				}
			});
			var flag = true;
			$.ajax({
		          type : 'POST',  
		          url : url,  
		          async : false,  
		          success : function(data){
		        	  flag = data == 'success';
		          }  
		     });
			
			return flag;
		}else{
			alert('请选择数据！');
			return false;
		}
	}
	
	//确认
	$(document).delegate('#doneConfirmJourney', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'motor-journey-done-confirm-journey.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '未提交'){
					flag = false;
				}else{
					if(i == 0){
						url += 'motorcadeJourneyId=' +　$(item).val();
					}else{
						url += '&motorcadeJourneyId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('确认失败！');
					}else{
						alert('确认成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('确认失败！请确认所选派单状态！');
				return;
			}
		}
	});
	
	//取消确认
	$(document).delegate('#doneRecallJourney', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'motor-journey-done-recall-journey.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已确认'){
					flag = false;
				}else{
					if(i == 0){
						url += 'motorcadeJourneyId=' +　$(item).val();
					}else{
						url += '&motorcadeJourneyId=' +　$(item).val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('确认失败！');
					}else{
						alert('确认成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('确认失败！请确认所选派单状态！');
				return;
			}
		}
	});
	//复制新增订单
	/* $(document).delegate('#toAddDispatchByCopy', 'click',function(e){
		toAddDispatchByCopy();
	});
	
	function toAddDispatchByCopy(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
			var motorcadeDispatchId = $('.selectedItem:checked').val();
			$.post('motor-dispatch-to-add-dispatch-by-copy.do?motorcadeDispatchId=' + motorcadeDispatchId, function(data){
				var motorcadeDispatch = data.motorcadeDispatch;
				var motorcadeFees = data.motorcadeFees;
				var html = '<input id="motorcadeDispatchId" name="motorcadeDispatchId" type="hidden" value="' + motorcadeDispatchId + '">';
				html += '<table class="m-table table-striped table-bordered table-hover">';
				html += '<thead><tr><th colspan="12">费用信息</th></tr><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
				html += '<th>费用名称</th><th>收/付</th><th>票种</th><th>价格</th><th>备注</th></tr></thead><tbody>';
				//费用信息
				var countIncome = 0;
				$.each(motorcadeFees, function(i, item){
					if(item.incomeOrExpense == '收'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countIncome++;
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="motorcadeFeeId" value="'+item.id+'" /></td>';
						html += '<td>' + item.feeType + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.descn + '</td></tr>';
					}
				});
				if(countIncome > 0){
					//html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
				}
				var countExpense = 0;
				$.each(motorcadeFees, function(i, item){
					if(item.incomeOrExpense == '付'){// && item.countUnit == '票' 取消 按箱的费用不能复制
						countExpense++;
						countIncome++;
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="motorcadeFeeId" value="'+item.id+'" /></td>';
						html += '<td>' + item.feeType + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.moneyCount + '</td>';
						html += '<td>' + item.descn + '</td></tr>';
					}
				});
				if(countExpense > 0){
					//html += '<tr><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+freightOrder.paymentAmount+'</td><td>小计:'+freightOrder.paymentTax+'</td><td></td><td></td></tr>';
				}
				if(countIncome>0 || countExpense>0){
					//html += '<tr><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="6"></td><td>总计:'+freightOrder.balance+'</td><td>小计:'+(Math.round((freightOrder.paymentTax - freightOrder.incomeTax)*100)/100)+'</td><td></td><td></td></tr>';
				}
				html += "</tbody></table>";
				$('#toAddDispatchByCopyForm').html(html);
				
				box.modal('hide');
				var margin = (window.screen.availWidth - 1000)/2;
				$('#toAddDispatchByCopyModal').css("margin-left", margin);
				$('#toAddDispatchByCopyModal').css("width","1000px");
				$('#toAddDispatchByCopyModal').modal();
			});
		}
	}
	
	$(function() {
		$("#toAddDispatchByCopyForm").validate({
	        submitHandler: function(form) {
	        	$('#toAddDispatchByCopyModal').modal('hide');
	        	bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post('motor-dispatch-done-add-dispatch-by-copy.do', $("#toAddDispatchByCopyForm").serialize(), function(data){
					if(data != null && data != 'error'){
						box.modal('hide');
						//window.open('../fre/fre-order-input-service.do?id=' + data);
						window.location.href = 'motor-dispatch-input.do?id=' + data;
					}
				});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//添加费用
	$(document).delegate('#toAddFee', 'click',function(e){
		toAddFee();
	});
	
	//添加费用
	function toAddFee(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var motorcadeDispatchId = $('.selectedItem:checked').val();
			if(false){
				alert('费用已确认添加完毕不能再添加！');
				return false;
			}else{
				$.post('motor-dispatch-to-add-fee.do?motorcadeDispatchId=' + motorcadeDispatchId, function(data){
					var hasAddData = data.hasAddData;//已经保存的费用
					var fasInvoiceTypes = data.fasInvoiceTypes;//发票票种
					var motorcadeDispatch = data.motorcadeDispatch;//发票票种
					
					$('#toAddFeeBtnForm #feeCount').text("派单号: " + motorcadeDispatch.dispatchNumber + (hasAddData == null? '  未添加费用' : ('  已添加 ' + hasAddData.length + ' 条费用')));
					var html = '<input id="motorcadeDispatchId" type="hidden" value="' + motorcadeDispatchId + '">';
					html += '<input id="motorcadeFeeId" type="hidden" value="">';//用于修改单条费用时使用
					html += '<table class="m-table table-striped table-bordered">';
					html += '<thead><tr>';
					html += '<th>费用名称</th><th>收/付</th><th>票种</th><th>价格</th><th>备注</th></tr></thead><tbody>';
					html += '<td><input id="feeType" name="feeType" value="" class="form-control required dictionary" vAttrId="8251e56c-23a6-11e5-a9c8-a4db305e5cc5"></td>';
					html += '<td><select id="incomeOrExpense" name="incomeOrExpense" class="form-control required" ><option value=""></option><option value="付">付&nbsp;</option><option value="收">收&nbsp;</option></select></td>';
				    html += '<td><select id="fasInvoiceTypeId" class="form-control required">';
					$.each(fasInvoiceTypes, function(i, item){
						html += '<option value="' + item.id + '">' + item.typeName + '</option>';
					});
					html += '</select></td>';
					html += '<td><input id="moneyCount" name="moneyCount" value="" class="form-control required number" ></td>';
					html += '<td><input id="descn" name="descn" class="form-control" maxlength="32"></td>'
					$('#toAddFeeForm').html(html);
					
					//已添加的费用
					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>费用名称</th><th>收/付</th><th>票种</th><th>价格</th><th>备注</th></tr></thead><tbody>';
					var countIncome = 0;
					$.each(hasAddData, function(i, item){
						if(item.incomeOrExpense == '收'){
							countIncome++;
							html += '<tr"><td><input class="selectedItemId a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
							html += '<td>' + item.feeType + '</td>';
							html += '<td>' + item.incomeOrExpense + '</td>';
							html += '<td>' + item.fasInvoiceType.typeName + '</td>';
							html += '<td>' + item.moneyCount + '</td>';
							html += '<td>' + item.descn + '</td></tr>';
						}
					});
					if(countIncome > 0){
						//html += '<tr><td colspan="2"></td><td>共'+countIncome+'条</td><td colspan="6"></td><td>折合:'+freightOrder.incomeAmount+'</td><td>小计:'+freightOrder.incomeTax+'</td><td></td><td></td></tr>';
					}
					var countExpense = 0;
					$.each(hasAddData, function(i, item){
						if(item.incomeOrExpense == '付'){
							countIncome++;
							html += '<t style="height:18px;"><td><input class="selectedItemId a-check" type="checkbox" name="expenseIds" value="'+item.id+'" /></td>';
							html += '<td>' + item.feeType + '</td>';
							html += '<td>' + item.incomeOrExpense + '</td>';
							html += '<td>' + item.fasInvoiceType.typeName + '</td>';
							html += '<td>' + item.moneyCount + '</td>';
							html += '<td>' + item.descn + '</td></tr>';
						}
					});
					if(countExpense > 0){
						//html += '<tr style="height:18px;"><td colspan="2"></td><td>共'+countExpense+'条</td><td colspan="6"></td><td>折合:'+freightOrder.paymentAmount+'</td><td>小计:'+freightOrder.paymentTax+'</td><td></td><td></td><td></td></tr>';
					}
					if(countIncome>0 || countExpense>0){
						//html += '<tr style="height:18px;"><td colspan="2"></td><td>共'+(countIncome + countExpense)+'条</td><td colspan="6"></td><td>总计:'+freightOrder.balance+'</td><td>小计:'+(Math.round((freightOrder.paymentTax - freightOrder.incomeTax)*100)/100)+'</td><td></td><td></td><td></td></tr>';
					}
					
					html += "</tbody></table>";
					$('#hasAddFeeForm').html(html);
					initSelect2();
					var margin = (window.screen.availWidth - 1000)/2;
					$('#toAddFeeModal').css("margin-left", margin);
					$('#toAddFeeModal').css("width","1000px");
					$('#toAddFeeModal').modal(); 
				});
			}
		}
	}
	
	$(function() {
		$("#toAddFeeForm").validate({
	        submitHandler: function(form) {
				var motorcadeDispatchId = $('#toAddFeeForm #motorcadeDispatchId').val();
	    		var fasInvoiceTypeId = $('#toAddFeeForm #fasInvoiceTypeId').val();
	    		var motorcadeFeeId = $('#toAddFeeForm #motorcadeFeeId').val();//修改单条费用时使用
	    		if(motorcadeDispatchId == '' || fasInvoiceTypeId == ''){
	    			return false;//取消保存
	    		}
				
				var data = toJsonString('toAddFeeForm');
				var url = 'motor-dispatch-done-add-fee.do?motorcadeDispatchId=' + motorcadeDispatchId 
						+ '&fasInvoiceTypeId=' + fasInvoiceTypeId
						+ '&motorcadeFeeId=' + motorcadeFeeId; 
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				toAddFee();
	    			}
	    		});
				
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除费用
	function toDeleteFee(){
		if($('#hasAddFeeForm .selectedItemId:checked').length == 0){
			alert('请选择数据！');
			return false;
		}else{
			var flag = true;
			var url = 'motor-fee-done-delete-fee.do?';
			$('#hasAddFeeForm .selectedItemId:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(true){
					if(i == 0){
						url += 'motorcadeFeeId=' + $(item).val();
					}else{
						url += '&motorcadeFeeId=' + $(item).val();
					}
				}else{
					flag = false;
				}
			});
			if(flag && window.confirm('确定要删除吗？')){
				$.post(url, function(data){
					if(data == 'success'){
						toAddFee();
					}else{
						alert('删除失败！');
					}
				});
			}else{
				alert('删除失败！请确认费用状态！');
			}
		}
	}
	
	function toReviseFee(){
		if($('#hasAddFeeForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			$.post('motor-fee-to-revise-fee.do?motorcadeFeeId=' + $('#hasAddFeeForm .selectedItemId:checked').val(), function(data){
				var motorcadeFee = data.motorcadeFee;
				$("#toAddFeeForm #motorcadeFeeId").val(motorcadeFee.id);
				$("#toAddFeeForm #motorcadeDispatchId").val(motorcadeFee.motorcadeDispatch.id);
				$("#toAddFeeForm #fasInvoiceTypeId").val(motorcadeFee.fasInvoiceType.id);
				$("#toAddFeeForm #feeType").val(motorcadeFee.feeType);
				$("#toAddFeeForm #incomeOrExpense").val(motorcadeFee.incomeOrExpense);
				$("#toAddFeeForm #moneyCount").val(motorcadeFee.moneyCount);
				$("#toAddFeeForm #descn").val(motorcadeFee.descn);
			});
		}
	}
	
	//预览
	function browseDelegate(motorcadeDispatchId){
		bootbox.animate(false);
   		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('motor-dispatch-to-browse.do?motorcadeDispatchId=' + motorcadeDispatchId, function(data){
			if(data != null){
				window.open('/VC/convert?fileName=' + data);
				box.modal('hide');
			}
		});
	}
	//拼接成json数据类型
	function toJsonString(formId){
		var fields = $('#' + formId).serializeArray();
		var data = '{';
		$.each(fields, function(i, item){
				if(i == 0){
   				data += '"' + item.name + '":"' + item.value + '"';
   			}else{
   				data += ',"' + item.name + '":"' + item.value + '"';
   			}
		});
		data += '}';
		return data;
	}
	
	
	 */
    </script>
  </body>

</html>
