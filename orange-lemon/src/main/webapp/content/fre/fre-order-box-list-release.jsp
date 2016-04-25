<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>箱封管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>箱封管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm green" id="reviseFreightSeal">
										修改封号</button>
										
									<button class="btn btn-sm green" id="reviseFreightBox">
										修改箱号</button>
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
								<form name="searchForm" method="post" action="fre-order-box-list-release.do" class="form-inline">
									<label for="orderNumber">订单号</label>
								    <input type="text" id="boxNumber" name="orderNumber" value="${param.orderNumber}" class="form-control">
								    
								    <label for="boxNumber">箱号</label>
								    <input type="text" id="boxNumber" name="boxNumber" value="${param.boxNumber}" class="form-control">
								    
								    <label for="boxType">箱型</label>
								    <input type="text" id="boxType" name="boxType" value="${param.boxType}" class="form-control">
								    
								    <label for="boxBelong">箱属</label>
								    <input type="text" id="boxBelong" name="boxBelong" value="${param.boxBelong}" class="form-control">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value=""></option>
								    	<option value="已选箱" <c:if test="${param.status == '已选箱' }">selected</c:if> >已选箱</option>
								    	<option value="已放箱" <c:if test="${param.status == '已放箱' }">selected</c:if> >已放箱</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="fre-order-box-remove.do" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th class="sorting" name="FRE_BOX_REQUIRE_ID">订单号</th>
								                    <th class="sorting" name="FRE_BOX_ID">箱号</th>
								                    <th class="sorting" name="FRE_BOX_ID">类型</th>
								                    <th class="sorting" name="FRE_BOX_ID">箱属</th>
								                    <th class="sorting" name="FRE_BOX_ID">箱况</th>
								                    <th class="sorting" name="FRE_SEAL_ID">封号</th>
								                    <th>集装箱来源</th>
								                    <th class="sorting" name="DESCN">说明(提箱地址)</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <!--  
								                    <th>操作</th>
								                    -->
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.freightBoxRequire.freightOrder.orderNumber}</td>
								                    <td>${item.freightBox.boxNumber}</td>
								                    <td>${item.freightBox.boxType}</td>
								                    <td>${item.freightBox.boxBelong}</td>
								                    <td>${item.freightBox.boxCondition}</td>
								                    <td>${item.freightSeal.sealNumber}</td>
								                    <td>${item.freightBox.boxSource}</td>
								                    <td>${item.descn}</td>
								                    <td>${item.status}</td>
								                    <!--  
								                    <td>
								                    	<a href="fre-order-box-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
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
	
	<!-- 封号修改 -->
	<div class="modal fade" id="freightSealModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">箱封信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="freightSealReviseForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#freightSealReviseForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 箱号修改 -->
	<div class="modal fade" id="freightBoxModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">箱封信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="freightBoxReviseForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#freightBoxReviseForm').submit();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	
	<!-- 配铁路封 -->
	<div class="modal fade" id="railSealModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">箱封信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="railSealReviseForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#railSealReviseForm').submit();">确定</button>
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
	 	        'boxType': "${param.boxType}",
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
	
	//////////////////////////////////////修改封号
	$(document).delegate('#reviseFreightSeal', 'click',function(e){
		if(reviseFreightSeal()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#freightSealModal').css("margin-left", margin);
			$('#freightSealModal').css("width","1000px");
			$('#freightSealModal').modal();
		}
	});
	
	function reviseFreightSeal(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderBoxId = $('.selectedItem:checked').val();
			$.post('fre-order-box-to-revise-seal.do?freightOrderBoxId=' + freightOrderBoxId, function(data){
				var freightOrderBox = data.freightOrderBox;
				var freightSeals = data.freightSeals;
				
				var html ='<input id="id" name="id" type="hidden" value="' + freightOrderBox.id + '">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>封号</th><th>说明</th></tr></thead><tbody><tr>';
				html += '<td>' + freightOrderBox.freightBox.boxNumber + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxType + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxBelong + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxCondition + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxSource + '</td>';
				html += '<td><select id="freightSealId" class="form-control required">';
				$.each(freightSeals, function(i, item){
					html += '<option value="' + item.id + '"';
					if(freightOrderBox.freightSeal != null && freightOrderBox.freightSeal.id == item.id){
						html += ' selected >' + item.sealNumber + '</option>';
					}else{
						html += ' >' + item.sealNumber + '</option>';
					}
				});
				html += '</select></td>';
				html += '<td><input id="descn" name="descn" value="' + (freightOrderBox.descn == null ? '' : freightOrderBox.descn) + '" class="form-control required"></td></tr></tbody></table>';
				$('#freightSealReviseForm').html(html);
			});
			return true;
		}
	}
	
	$(function() {
		$('#freightSealReviseForm').validate({
	        submitHandler: function(form) {
	        	$('#freightSealModal').modal('hide');
				var freightSealId = $('#freightSealReviseForm #freightSealId').val();
    			$.ajax({
	    			type: 'POST',
	    			data: toJsonString('freightSealReviseForm'),
	    			url: 'fre-order-box-done-revise-seal.do?freightSealId=' + freightSealId,
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data == 'success'){
	    					alert('修改成功！');
	    				}
	    				window.location.href = window.location.href;
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
//////////////////////////////////////修改箱号
	$(document).delegate('#reviseFreightBox', 'click',function(e){
		if(reviseFreightBox()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#freightBoxModal').css("margin-left", margin);
			$('#freightBoxModal').css("width","1000px");
			$('#freightBoxModal').modal();
		}
	});
	
	function reviseFreightBox(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderBoxId = $('.selectedItem:checked').val();
			$.post('fre-order-box-to-revise-box.do?freightOrderBoxId=' + freightOrderBoxId, function(data){
				var freightOrderBox = data.freightOrderBox;
				var freightBoxs = data.freightBoxs;
				
				var html ='<input id="id" name="id" type="hidden" value="' + freightOrderBox.id + '">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>选择箱号</th><th>说明</th></tr></thead><tbody><tr>';
				html += '<td>' + freightOrderBox.freightBox.boxNumber + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxType + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxBelong + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxCondition + '</td>';
				html += '<td>' + freightOrderBox.freightBox.boxSource + '</td>';
				html += '<td><select id="freightBoxId" class="form-control required">';
				$.each(freightBoxs, function(i, item){
					html += '<option value="' + item.id + '"';
					if(freightOrderBox.freightBox != null && freightOrderBox.freightBoxid == item.id){
						html += ' selected >' + item.boxNumber + '</option>';
					}else{
						html += ' >' + item.boxNumber + '</option>';
					}
				});
				html += '</select></td>';
				html += '<td><input id="descn" name="descn" value="' + (freightOrderBox.descn == null ? '' : freightOrderBox.descn) + '" class="form-control required"></td></tr></tbody></table>';
				$('#freightBoxReviseForm').html(html);
			});
			return true;
		}
	}
	
	$(function() {
		$('#freightBoxReviseForm').validate({
	        submitHandler: function(form) {
	        	$('#freightBoxModal').modal('hide');
				var freightBoxId = $('#freightBoxReviseForm #freightBoxId').val();
    			$.ajax({
	    			type: 'POST',
	    			data: toJsonString('freightBoxReviseForm'),
	    			url: 'fre-order-box-done-revise-box.do?freightBoxId=' + freightBoxId,
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data == 'success'){
	    					alert('修改成功！');
	    				}
	    				window.location.href = window.location.href;
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	
	/////////////////////////////配铁路封号
	/***
	
	$(document).delegate('#reviseRailSeal', 'click',function(e){
		if(reviseRailSeal()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#railSealModal').css("margin-left", margin);
			$('#railSealModal').css("width","1000px");
			$('#railSealModal').modal();
		}
	});
	
	function reviseRailSeal(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightOrderBoxId = $('.selectedItem:checked').val();
			$.post('fre-order-box-to-modify-rail-seal.do?freightOrderBoxId=' + freightOrderBoxId, function(data){
				var freightBoxParticular = data.freightBoxParticular;
				var html ='<input id="id" name="id" type="hidden" value="' + freightBoxParticular.id + '">';
				html += '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr>';
				html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>铁路封号</th></tr></thead><tbody><tr>';
				html += '<td>' + freightBoxParticular.freightOrderBox.freightBox.boxNumber + '</td>';
				html += '<td>' + freightBoxParticular.freightOrderBox.freightBox.boxType + '</td>';
				html += '<td>' + freightBoxParticular.freightOrderBox.freightBox.boxBelong + '</td>';
				html += '<td>' + freightBoxParticular.freightOrderBox.freightBox.boxCondition + '</td>';
				html += '<td><input id="railSeal" name="railSeal" value="' + (freightBoxParticular.railSeal == null ? '' : freightBoxParticular.railSeal) + '" class="form-control required"></td></tr></tbody></table>';
				$('#railSealReviseForm').html(html);
			});
			return true;
		}
	}
	
	$(function() {
		$('#railSealReviseForm').validate({
	        submitHandler: function(form) {
	        	$('#railSealModal').modal('hide');
	        	bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
    			$.ajax({
	    			type: 'POST',
	    			data: toJsonString('railSealReviseForm'),
	    			url: 'fre-order-box-done-modify-rail-seal.do',
	    			contentType: 'application/json',
	    			success:function(data){
	    				if(data == 'success'){
	    					alert('修改成功！');
	    				}
	    				window.location.href = window.location.href;
	    			}
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	**/
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
	
    </script>
  </body>

</html>
