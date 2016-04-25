<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>放箱管理(操作)</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>放箱管理(操作)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
										<button class="btn btn-sm red" id="importBoxSeal">
											导入箱封</button>
										
										<button class="btn btn-sm red" id="exportBoxSeal">
											导出箱封</button> 
											
										<button class="btn btn-sm green" id="chooseBoxSeal">
											选箱配封</button>
											
										<button class="btn btn-sm green" id="doneReleaseBox">
											确定放箱</button>
										
										<button class="btn btn-sm red" id="doneRecallBox">
											取消放箱</button>
												
										<button class="btn btn-sm red" id="backFreightBox">
											退回修改</button>
											
										<button class="btn btn-sm green" id="reviseBoxSource">
											修改来源</button>
											
										<button class="btn btn-sm green" id="viewOrderBox">
											查看箱子</button>
											
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
								<form name="searchForm" method="post" action="fre-box-require-list-manipulator.do" class="form-inline" method="post">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-small">
								    
								    <label for="boxNumber">箱号</label>
								    <input type="text" id="boxNumber" name="boxNumber" value="${param.boxNumber}" class="form-control input-small">
								    
								    <label for="boxType">箱型</label>
								    <input type="text" id="boxType" name="boxType" value="${param.boxType}" class="form-control input-small">
								    
								    <label for="boxBelong">箱属</label>
								    <input type="text" id="boxBelong" name="boxBelong" value="${param.boxBelong}" class="form-control input-small">
								    
								    <label for="TDH">提单号</label>
								    <input type="text" id="TDH" name="TDH" value="${param.TDH}" class="form-control input-small">
								    
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
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic" method="post">
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
	
	<!-- 修改集装箱来源 -->
	<div class="modal fade" id="boxSourceModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">修改来源</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="reviseBoxSourceForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#reviseBoxSourceForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 选箱配封 -->
	<div class="modal fade" id="chooseBoxSealModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">选箱配封</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="max-height:350px;overflow-y: scroll;">
				<form id="chooseBoxSealForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#chooseBoxSealForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!--导入箱封-->  
	<div class="modal fade" id="batchImportModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">导入箱封</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="batchImportForm" class="dropzone-custom" enctype="multipart/form-data" method="post" class="m-form-blank">
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
	 	    	'orderNumber': '${param.orderNumber}',
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
	
	//查看箱子
	$(document).delegate('#viewOrderBox', 'click', function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightBoxRequireId = $('.selectedItem:checked').val();
			var url = 'fre-order-box-byrequireid.do?freightBoxRequireId=' + freightBoxRequireId;
			listData(url, '箱封明细', 1000,
					['起始地','终止地', '箱型','箱属','集装箱号','封号', '提箱地', '状态'], 
					['freightBoxRequire.beginStation','freightBoxRequire.arriveStation','freightBox.boxType', 
					 'freightBox.boxBelong', 'freightBox.boxNumber', 'freightSeal.sealNumber', 'location','freightBoxRequire.status']
			);
		}
	});
	
	//退回修改
	$(document).delegate('#backFreightBox', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-box-require-to-back-require.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '待选箱'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightBoxRequireId=' +　$(item).val();
					}else{
						url += '&freightBoxRequireId=' +　$(item).val();
					}
				}
			});
			if(flag){
				if(window.confirm('确认退回修改吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('退回失败！');
						}else{
							alert('退回成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('退回失败！请确认所选箱需状态！');
				return;
			}
		}
	});
	
	//确定放箱
	$(document).delegate('#doneReleaseBox', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-box-require-done-release-box.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已选箱'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightBoxRequireId=' +　$(item).val();
					}else{
						url += '&freightBoxRequireId=' +　$(item).val();
					}
				}
			});
			if(flag){
				if(window.confirm('确认放箱吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('确认失败！');
						}else{
							alert('确认成功！');
							window.location.href = window.location.href;
						} 
					});
				}
			}else{
				alert('放箱失败！请确认所选箱需状态！');
				return;
			}
		}
	});
	
	//取消放箱
	$(document).delegate('#doneRecallBox', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-box-require-done-recall-box.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已选箱' && status != '已放箱'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightBoxRequireId=' +　$(item).val();
					}else{
						url += '&freightBoxRequireId=' +　$(item).val();
					}
				}
			});
			if(flag){
				if(window.confirm('取消放箱吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('取消失败！');
						}else{
							alert('取消成功！');
							window.location.href = window.location.href;
						} 
					});
				}
			}else{
				alert('取消失败！请确认所选箱需状态！');
				return;
			}
		}
	});
//////////////////////////////////////修改集装箱来源
	$(document).delegate('#reviseBoxSource', 'click',function(e){
		if(reviseBoxSource()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#boxSourceModal').css("margin-left", margin);
			$('#boxSourceModal').css("width","1000px");
			$('#boxSourceModal').modal();
		}
	});
	
	function reviseBoxSource(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightBoxRequireId = $('.selectedItem:checked').val();
			$.post('fre-box-require-to-revise-source.do?freightBoxRequireId=' + freightBoxRequireId, function(data){
				var freightBoxRequire = data.freightBoxRequire;
				var html ='<input id="freightBoxRequireId" name="freightBoxRequireId" type="hidden" value="' + freightBoxRequire.id + '">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>集装箱来源</th></tr></thead><tbody><tr>';
				html += '<td>' + freightBoxRequire.boxType + '</td>';
				html += '<td>' + freightBoxRequire.boxBelong + '</td>';
				html += '<td>' + freightBoxRequire.boxCondition + '</td>';
				html += '<td>' + freightBoxRequire.boxCount + '</td>';
				html += '<td><select id="boxSource" name="boxSource" class="form-control required">';
				html += '<option value="自管箱">自管箱</option><option value="外管箱">外管箱</option>';//<option value="外理箱">外理箱</option>
				html += '</select></td></tr></tbody></table>';
				$('#reviseBoxSourceForm').html(html);
				$('#reviseBoxSourceForm #boxSource').val(freightBoxRequire.boxSource);
			});
			return true;
		}
	}
	
	$(function() {
		$('#reviseBoxSourceForm').validate({
	        submitHandler: function(form) {
	        	$('#boxSourceModal').modal('hide');
    			$.post('fre-box-require-done-revise-source.do', $('#reviseBoxSourceForm').serialize(), function(data){
    				if(data == 'success'){
    					alert('修改成功！');
    					window.location.href = window.location.href;
    				}else{
    					alert('修改失败！');
    				}
    			});
	        },
	        errorClass: 'validate-error'
		});
	});
	
//////////////////////////选箱配封	
	$(document).delegate('#chooseBoxSeal', 'click',function(e){
		if(chooseBoxSeal()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#chooseBoxSealModal').css("margin-left", margin);
			$('#chooseBoxSealModal').css("width","1000px");
			$('#chooseBoxSealModal').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
		}
	});
	
	function chooseBoxSeal(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightBoxRequireId = $('.selectedItem:checked').val();
			var status = $('#' + freightBoxRequireId + 'status').text();
			//if(status == '待选箱'){
			if(true){
				$.post('fre-box-require-to-choose-boxseal.do?freightBoxRequireId=' + freightBoxRequireId, function(data){
					var freightBoxRequire = data.freightBoxRequire;
					var freightOrderBoxs = data.freightOrderBoxs;
					var html ='<input id="freightBoxRequireId" name="freightBoxRequireId" type="hidden" value="' + freightBoxRequire.id + '">';
					html += '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr>';
					//html += '<th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>箱号</th><th>封号</th><th>提箱地</th></tr></thead><tbody>';
					html += '<th>序号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>箱号</th><th>封号</th></tr></thead><tbody>';
					$.each(freightOrderBoxs, function(i, item){
						html += '<tr><td>'+ (i + 1) +'</td><td>' + freightBoxRequire.boxType + '</td>';
						html += '<td>' + freightBoxRequire.boxBelong + '</td>';
						html += '<td>' + freightBoxRequire.boxCondition + '</td>';
						html += '<td>' + freightBoxRequire.boxSource + '</td>';
						if(item.freightBox != null){
							html += '<td><input id="'+ item.id +'boxNumber" name="boxNumber" value="' + item.freightBox.boxNumber + '" class="form-control required"></td>';
						}else{
							html += '<td><input id="'+ item.id +'boxNumber" name="boxNumber" class="form-control required"></td>';
						}
						
						if(item.freightSeal != null){
							html += '<td><input id="'+ item.id +'sealNumber" name="sealNumber" value="' + item.freightSeal.sealNumber + '" class="form-control"></td></tr>';
						}else{
							html += '<td><input id="'+ item.id +'sealNumber" name="sealNumber" class="form-control"></td></tr>';
						}
						
						//html += '<td><input id="'+ item.id +'location" name="location" class="form-control"></td></tr>';
					});
					html += '</tbody></table>';
					$('#chooseBoxSealForm').html(html);
				});
				return true;
			}else{
				alert(status + '状态不能选箱配封！');
				return false;
			}
		}
	}
	
	$(function() {
		$('#chooseBoxSealForm').validate({
	        submitHandler: function(form) {
	        	if(validateNumber()){
	        		$('#chooseBoxSealModal').modal('hide');
	        		$.post('fre-box-require-done-choose-boxseal.do', $('#chooseBoxSealForm').serialize(), function(data){
	        			if(data.result == 'true'){
	    					alert('操作成功！');
	    					window.location.href = window.location.href;
	    				}else{
	    					alert('操作失败！信息: ' + data.message);
	    				}
	    			});
	        	}
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//箱号的校验
	$(document).delegate('#chooseBoxSealForm input[name=boxNumber]','change', function(event){
		var $item = $(this);
		var boxNumber = $item.val();
		$.post('fre-box-to-validate-boxnumber.do?boxNumber=' + boxNumber, function(data){
			if(data != 'success'){
				$item.nextUntil('input').remove();
				$item.after('<span class="validate-error">校验失败</span>');
			}else{
				$item.nextUntil('input').remove();
			}
		});
	});
	
	function validateNumber(){
		var flag = true;
		$('#chooseBoxSealForm input[name=boxNumber]').each(function(i, item){
			$.ajax({
					url:'fre-box-to-validate-boxnumber.do?boxNumber=' + $(item).val(),
					dataType:'text',
					async:false,
					success:function(data){
						if(data != 'success'){
							$(item).nextUntil('input').remove();
							$(item).after('<span class="validate-error">校验失败</span>');
							flag = false;
							return false;
						}
					}
				});
		});
		return flag;
	}
	
	//导入箱封进行选箱配封
	$(document).delegate('#importBoxSeal', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightBoxRequireId = $('.selectedItem:checked').val();
			var status = $('#' + freightBoxRequireId + 'status').text();
			if(status == '待选箱'){
				var url = 'fre-box-require-done-import-boxseal.do?freightBoxRequireId=' + freightBoxRequireId;
				$('#batchImportForm').attr('action', url);
				
				$('#batchImportForm').dropzone({
			        url: url,
			        maxFiles: 1,
			        maxFilesize: 2,//单位是M
			        acceptedFiles: ".xls",//文件后缀名
			        init: function(){
						this.on('success', function(file, result){
							if(result.result == 'true'){
								alert('导入箱封成功！');
								window.location.href = window.location.href;
							}else{
								alert('导入箱封失败！错误: ' + result.message);
								window.location.href = window.location.href;
							}
						});
					}
			    });
				
				var margin = (window.screen.availWidth -800)/2;
				$('#batchImportModal').css("margin-left", margin);
				$('#batchImportModal').css("margin-top", 150);
				$('#batchImportModal').css("width","800px");
				$('#batchImportModal').modal();
			}else{
				alert('请确认箱需状态！');
			}
		}
	});
	
	$(document).delegate('#exportBoxSeal', 'click', function(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightBoxRequireId = $('.selectedItem:checked').val();
			var status = $('#' + freightBoxRequireId + 'status').text();
			if(status == '已选箱' || status == '已放箱'){
				window.open('fre-order-box-to-export-boxseal.do?freightBoxRequireId=' + freightBoxRequireId);
			}else{
				alert(status + '状态下不能导出箱封！');
			}
		}
	});
    </script>
  </body>

</html>
