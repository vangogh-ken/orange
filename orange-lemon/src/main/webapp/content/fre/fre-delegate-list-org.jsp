<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>委托管理(部门级)</title>
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
							<div class="caption"><i class="fa fa-anchor"></i>委托管理(部门级)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<!-- 对于给内部部门的委托，则需要填报收支费用之后才能执行完成 
									<button class="btn btn-sm red" id="executeDelegate">
											执行委托</button>
											
									<button class="btn btn-sm red" id="doneBatchExecute">
											批量执行</button>
									
									<button class="btn btn-sm green" id="makeUpActionValue">
											补充数据</button>
									-->
									<button class="btn btn-sm green" id="addExpense">
											费用详情</button>
									<!--  						
									<button class="btn btn-sm red" id="doneDelegateRecover">
											确认撤销</button>
									<button class="btn btn-sm red" id="transferDelegate">
											转出委托</button>
									
									<button class="btn btn-sm green" id="doneMarkDelegate">
											<i class="fa fa-flag"></i></button>
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
								<form name="searchForm" method="post" action="fre-delegate-list-org.do" class="form-inline">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-xsmall">
								    
								    <label for="typeName">类型</label>
								    <input type="text" id="typeName" name="typeName" value="${param.typeName}" class="form-control input-xsmall">
								    
								    <label for="ownerName">执行人</label>
								    <input type="text" id="ownerName" name="ownerName" value="${param.ownerName}" class="form-control input-xsmall">
								    
								    <label for="manipulator">操作员</label>
								    <input type="text" id="manipulator" name="manipulator" value="${param.manipulator}" class="form-control input-xsmall">
								    
								    <label for="JZX">集装箱</label>
								    <input type="text" id="JZX" name="JZX" value="${param.JZX}" class="form-control input-xsmall" >
								    
								    <label for="PM">品名</label>
								    <input type="text" id="PM" name="PM" value="${param.PM}" class="form-control input-xsmall" >
								    
								    <label for="NB">内部</label>
								    <select id="NB" name="NB" class="form-control">
								    	<option value="" <c:if test="${param.NB == ''}">selected</c:if> ></option>
								    	<option value="T" <c:if test="${param.NB == 'T'}">selected</c:if> >是</option>
								    	<option value="F" <c:if test="${param.NB == 'F'}">selected</c:if> >否</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value="" <c:if test="${param.status == ''}">selected</c:if> ></option>
								    	<option value="待执行" <c:if test="${param.status == '待执行'}">selected</c:if> >待执行</option>
								    	<option value="已执行" <c:if test="${param.status == '已执行'}">selected</c:if> >已执行</option>
								    	<option value="预备执行" <c:if test="${param.status == '预备执行'}">selected</c:if> >预备执行</option>
								    	<option value="预备完成" <c:if test="${param.status == '预备完成'}">selected</c:if> >预备完成</option>
								    	<option value="确认撤销" <c:if test="${param.status == '确认撤销'}">selected</c:if> >确认撤销</option>
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
								                    <th>收支差</th>
								                    <th>订单编号</th>
								                    <th>操作员</th>
								                    <th>业务员</th>
								                    <th>品名</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="FRE_ACTION_ID">动作名称</th>
								                    <th class="sorting" name="PLACE_TIME">下委托时间</th>
								                    <th class="sorting" name="DELEGATE_FILE">委托书</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="USER_ID">处理人</th>
								                    <th class="sorting" name="INTERNAL">内部委托</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <input id="${item.id}freightActionId" value="${item.freightAction.id}" type="hidden">
								                    <td>
								                    	<fmt:formatNumber value="${item.freightAction.balance}" pattern="##.##" minFractionDigits="2" > </fmt:formatNumber>
								                    </td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.orderNumber}<c:if test="${item.executeStatus=='T'}"><i class="fa fa-flag"></i></c:if></td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.manipulator}</td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.salesman}</td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.cargoName}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td>${item.freightAction.freightActionType.typeName}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<a href="fre-delegate-to-view-by-action.do?freightActionId=${item.freightAction.id}" target="_blank" >下载</a>
									                    <a href="javascript:void(0);" onclick="browseDelegate('${item.freightAction.id}');">预览</a>
								                    </td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.owner.displayName}</td>
								                    <td id="${item.id}internal">${item.freightAction.internal}</td>
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
	
	<div id="actionFieldModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">动作信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="actionFieldForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<!-- 箱封详情 -->
			<article class="m-widget">
			<form id="orderBoxSelectForm"  style="display: none; "action="" method="post" style="height: 150px;overflow-y: scroll;" class="m-form-blank"></form>
			</article>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button id="actionFieldSubmitBtn" type="button" class="btn btn-sm red" onclick="if(true) $('#actionFieldForm').submit();">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	
	<!-- 费用 -->
	<div class="modal fade" id="expenseModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">费用详情</h4>
			</div>
			<!-- 添加费用 -->
			<div class="modal-body">
				<article class="m-widget">
				<form id="expenseToAddForm" method="post" class="m-form-blank"></form>
				</article>
				<!--  
				<article class="m-widget">
					<form id="expenseBtnForm" name="expenseBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<button type="button" class="btn btn-sm green" onclick="$('#expenseToAddForm').submit()">添加</button>
						<button type="button" class="btn btn-sm green" onclick="if(addByBox()){$('#expenseToAddForm').submit()}">按箱</button>
						<button type="button" class="btn btn-sm red" onclick="deleteExpense();">删除</button>
						<button type="button" class="btn btn-sm red" onclick="reviseExpense();">修改</button>
						<button type="button" class="btn btn-sm green" id="finishExpenseBtn" onclick="finishExpense();">添加完毕</button>
					</form>
				</article>
				-->
				<!-- 箱封详情 -->
				<article class="m-widget">
				<form id="orderBoxSelectForm2"  action="" method="post" style="display:none;max-height:150px;overflow-y:scroll;" class="m-form-blank"></form>
				</article>
				
				<!-- 已有费用 -->
				<article class="m-widget">
				<form id="expenseHasAddForm" action="" method="post" class="m-form-blank" style="max-height: 150px;overflow-y:scroll;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 转出动作 -->
	<div class="modal fade" id="transferDelegateModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">转出委托</h4>
			</div>
			<!-- 选择人员 -->
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="transferDelegateForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="doneTransferDelegate();">确定</button>
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
	 	        'typeName': '${param.typeName}',
	 	      	'ownerName': '${param.ownerName}',
	 	        'manipulator': '${param.manipulator}',
	 	        'JZX': '${param.JZX}',
	 	        'PM': '${param.PM}',
	 	       	'NB': '${param.NB}',
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
	    
	    var href = window.location.href;
     	if(href.indexOf('?') < 0){
     	    var stateObject = {};
  			var title = '';
  			var url = table.buildUrl(table.config.params);
     	    history.pushState(stateObject, title, url);//HTML5新特性，不刷新页面的情况下修改URL地址
     	}
	});
	
	/* $(document).delegate('#makeUpActionValue', 'click',function(e){
		if(makeUpActionValue()){
			var margin = (window.screen.availWidth - 1000)/2;
			$('#expenseModal').css("margin-left", margin);
			$('#expenseModal').css("width","1000px");
			$('#expenseModal').modal();
		}
	});
	
	function makeUpActionValue(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			$.post('fre-action-by-delegateid.do?freightDelegateId=' + $('.selectedItem:checked').val(), function(data){
				if(data.status == '预备执行'){
					addPrime(data.id, false);
				}else{
					alert('没有需要补充的数据！');
				}
			});
		}
	}
	
	function addPrime(freightActionId, readonly){
		bootbox.animate(false);
		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('fre-action-to-prime-action.do?freightActionId=' + freightActionId, function(data){
			var freightAction = data.freightAction;
			if(freightAction.prime != 'T' || freightAction.internal != 'T'){//只有对内的动作才存在补充数据
				alert('没有需要补充的数据！');
				return false;
			}else{
				var freightOrderBoxOfOrder = data.freightOrderBoxOfOrder;
				var freightOrderBoxOfAction = data.freightOrderBoxOfAction;
				
				var freightActionFiledNormal = data.freightActionFiledNormal;
				var freightActionValueNormal = data.freightActionValueNormal;
				
				var freightActionFiledForBox = data.freightActionFiledForBox;
				var freightActionValueForBox = data.freightActionValueForBox;
				var countCol = 0;
				var trCount = 0;
				var canSubmitForm = false;//是否需要填报
				var html = '<input id="freightActionId" name="freightActionId" type="hidden" value="' + freightAction.id + '">';
				html += '<table class="m-table table-input" ><tbody>';
				for(var i=0, len=freightActionFiledNormal.length; i<len; i++){
					var canSelect = freightActionFiledNormal[i].canSelect == 'T' ? true : false;
					var vAttrId = freightActionFiledNormal[i].vAttrId != null ? freightActionFiledNormal[i].vAttrId : '';
					var vClsId = freightActionFiledNormal[i].vClsId != null ? freightActionFiledNormal[i].vClsId : '';
					var vColumn = freightActionFiledNormal[i].vColumn != null ? freightActionFiledNormal[i].vColumn : '';
					var vFilterId = freightActionFiledNormal[i].vFilterId != null ? freightActionFiledNormal[i].vFilterId : '';
					readonly = true;
					var required = 'required';
					var fieldType = freightActionFiledNormal[i].fieldType;
					var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
					var fieldName = freightActionFiledNormal[i].fieldName;
					var fiedlValue = '';
					var fieldColumnId = '';
					for(var j=0, size=freightActionValueNormal.length; j<size; j++){
						if(freightActionFiledNormal[i].id == freightActionValueNormal[j].freightActionField.id){
							fieldColumnId = freightActionValueNormal[j].id;
							
							if(freightActionFiledNormal[i].fieldType == 'INT'){
								fiedlValue = freightActionValueNormal[j].intValue == null? '' : freightActionValueNormal[j].intValue;
							}else if(freightActionFiledNormal[i].fieldType == 'DOUBLE'){
								fiedlValue = freightActionValueNormal[j].doubleValue == null? '' : freightActionValueNormal[j].doubleValue;
							}else if(freightActionFiledNormal[i].fieldType == 'VARCHAR'){
								fiedlValue = freightActionValueNormal[j].stringValue == null? '' : freightActionValueNormal[j].stringValue;
							}else if(freightActionFiledNormal[i].fieldType == 'TEXT'){
								fiedlValue = freightActionValueNormal[j].textValue == null? '' : freightActionValueNormal[j].textValue;
							}else if(freightActionFiledNormal[i].fieldType == 'DATETIME'){
								fiedlValue = freightActionValueNormal[j].dateValue == null? '' : $.fullCalendar.formatDate(new Date(freightActionValueNormal[j].dateValue),'yyyy-MM-dd');
							}else if(freightActionFiledNormal[i].fieldType == 'TIMESTAMP'){
								fiedlValue = freightActionValueNormal[j].dateValue == null? '' : $.fullCalendar.formatDate(new Date(freightActionValueNormal[j].dateValue),'yyyy-MM-dd HH:mm:ss');
							}
							
							break;
						}
					}
					if(trCount%4 == 0){
						html +='<tr>';
					}
					//console.log(fieldName + ' : ' + fiedlValue + ' : ' + (fiedlValue == ''));
					if(fiedlValue == ''){
						readonly = false;//有值的字段都只读
						canSubmitForm = true;//只要有需要填报的内容，则保存按钮就应当显示
					}
					//label
					if(fieldType == 'TEXT'){
						//console.log(trCount);
						if(trCount%4 == 1){
							html += '<td></td><td></td><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 3;
						}else if(trCount%4 == 2){
							html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 2;
						}else if(trCount%4 == 3){
							html += '</tr><tr><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 3;
						}else if(trCount%4 == 0){
							html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 2;
						}
					}else{
						html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
						trCount += 1;
					}

					if(!readonly){
						if(fieldType == 'TEXT'){
							html += '<td colspan="3"><textarea id="' + fieldColumnId + '"name="' + fieldColumnId +'" style="min-height:80px;" class="form-control ' + required + '">'+fiedlValue+'</textarea></td>';
						}else{
							if(i == len-1 && trCount%4 != 0){
								var colspan = 8 - 2* (trCount%4) + 1;
								html += '<td colspan="'+ colspan +'"><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
							}else{
								html += '<td><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
							}
							html += 'value="' + fiedlValue + '" ';
							if(canSelect){
								html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
							}else{
								html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
							}
						}
					}else{
						if(fieldType == 'TEXT'){
							html += '<td colspan="3"><textarea style="min-height:80px;" class="form-control ' + required + '" readonly>'+fiedlValue+'</textarea></td>';
						}else if(i == len-1 && trCount%4 != 0){
							var colspan = 8 - 2* (trCount%4) + 1;
							html += '<td colspan="' + colspan + '">' + fiedlValue + '</td>';
						}else{
							html += '<td>' + fiedlValue + '</td>';
						}
					}
					
					if(trCount%4 == 0){
						html +='</tr>';
					}
				}
				if(html.lastIndexOf('</tr>') != html.length - 5){
					html +='</tr>';
				}
				html += '</tbody></table>';
				//console.log(html);
				$('#actionFieldForm').html(html);
				//if(readonly){
				if(!canSubmitForm){
					$('#actionFieldModal #actionFieldSubmitBtn').hide();
				}else{
					$('#actionFieldModal #actionFieldSubmitBtn').show();
				} 
				//箱封信息
				if(freightOrderBoxOfOrder != null && freightOrderBoxOfOrder.length > 0){
					html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>箱号</th><th>封号</th><th>状态</th></tr></thead><tbody>';
					$.each(freightOrderBoxOfOrder, function(i, item){
						var isChecked = false;
						$.each(freightOrderBoxOfAction, function(j, ele){
							if(item.id == ele.id){
								isChecked = true;
							}
						});
						if(isChecked){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" checked /></td>';
						}else{
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" /></td>';
						}
						html += '<td id="' + item.id + 'countUnit">' + item.freightBoxRequire.boxType + '</td>';
						html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
						html += '<td>' + item.freightBoxRequire.boxCondition + '</td>';
						html += '<td>' + item.freightBoxRequire.boxSource + '</td>';
						html += '<td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
						html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
						html += '<td>' + item.status + '</td>';
					});
					html += "</tbody></table>";
					$('#orderBoxSelectForm').html(html);
					
					//含箱字段信息
					if(freightActionFiledForBox != null &&　freightActionFiledForBox.length > 0
							&& freightActionValueForBox != null && freightActionValueForBox.length > 0){
						var html = '<table class="m-table table-bordered" >';
						html += '<thead><tr><th>箱号</th><th>箱型</th><th>箱属</th><th>封号</th>';
						$.each(freightActionFiledForBox, function(i, item){
							html += '<th>' + item.fieldName + '</th>';
						});
						html += '</tr></thead><tbody>';
						$.each(freightOrderBoxOfAction, function(i, item){
							html += '<tr><td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
							html += '<td>' + item.freightBoxRequire.boxType + '</td>';
							html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
							html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
							$.each(freightActionFiledForBox, function(i, field){
								//初始化字段信息
								var canSelect = field.canSelect == 'T' ? true : false;
								var vAttrId = field.vAttrId != null ? field.vAttrId : '';
								var vClsId = field.vClsId != null ? field.vClsId : '';
								var vColumn = field.vColumn != null ? field.vColumn : '';
								var vFilterId = field.vFilterId != null ? field.vFilterId : '';
								var required = field.required == 'T' ? 'required' : '';
								required = 'required';
								var fieldType = field.fieldType;
								var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
								var fieldName = field.fieldName;
								var fiedlValue = '';
								var valueId = '';
								
								$.each(freightActionValueForBox, function(j, value){
									if(value.freightOrderBox.id == item.id && value.freightActionField.id == field.id){
										valueId = value.id;
										if(fieldType == 'INT'){
											fiedlValue = value.intValue == null? '' : value.intValue;
										}else if(fieldType == 'DOUBLE'){
											fiedlValue = value.doubleValue == null? '' : value.doubleValue;
										}else if(fieldType == 'VARCHAR'){
											fiedlValue = value.stringValue == null? '' : value.stringValue;
										}else if(fieldType == 'TEXT'){
											fiedlValue = value.textValue == null? '' : value.textValue;
										}else if(fieldType == 'DATETIME'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
										}else if(fieldType == 'TIMESTAMP'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd HH:mm:ss');
										}
										
										if(fiedlValue == ''){
											readonly = false;//有值的字段都只读
											canSubmitForm = true;//只要有需要填报的内容，则保存按钮就应当显示
										}
										console.log(readonly);
										if(!readonly){
											html += '<td><input id="' + valueId + '" type="text" name="' + valueId +'" ';
											html += 'value="' + fiedlValue + '" ';
											if(canSelect){
												html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
											}else{
												html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
											}
										}else{
											html += '<td>' + fiedlValue + '</td>';
										}
										return false;//跳出循环
									}
								});
							});
							
							html += '</tr>';
						});
						html += '</tbody></table>';
						$('#actionFieldForm').append(html);
					}
					if(!readonly){
					//if(!canSubmitForm){
						if($('#orderBoxSelectForm table').length > 0){
							if($('#orderBoxSelectForm .selectedItemId:checked').length > 0){
								$('#orderBoxSelectForm').hide(300);//如果有选择的，则说明已经选择过，隐藏
								$('#toPrimeBoxBtn').hide();
								$('#revisePrimeBoxBtn').show();
								
								$('#actionFieldForm').show(300);
								$('#actionFieldSubmitBtn').show();
							}else{
								alert('该动作包含箱封信息，请点击确定，选择具体箱封之后再继续！');
								$('#orderBoxSelectForm').show(300);
								$('#toPrimeBoxBtn').show();
								$('#revisePrimeBoxBtn').hide();
								
								$('#actionFieldForm').hide(300);
								$('#actionFieldSubmitBtn').hide();
							}
						}
					}else{
						$('#orderBoxSelectForm').hide();
						$('#toPrimeBoxBtn').hide();
						$('#revisePrimeBoxBtn').hide();
					}
				}else{
					$('#orderBoxSelectForm').html('');
					$('#toPrimeBoxBtn').hide();
					$('#revisePrimeBoxBtn').hide();
				}
				initDatePicker();//时间格式
				box.modal('hide');
				var margin = (window.screen.availWidth - 1200)/2;
				$('#actionFieldModal').css("margin-left", margin);
				$('#actionFieldModal').css("width","1200px");
				$('#actionFieldModal').modal();
			}
		});
	} */

	/* $(function() {
		$("#actionFieldForm").validate({
	        submitHandler: function(form) {
	            $('#actionFieldModal').modal('hide');
	    		bootbox.animate(false);
	    		var box = bootbox
	    				.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				var freightActionId = $('#actionFieldForm #freightActionId').val();
				if(freightActionId == undefined || freightActionId == ''){
	    			alert('添加节点之前,请先保存业务信息!');
	    			box.modal('hide');
	    			return false;
				}
	    		$.post('fre-action-done-prepare-action.do', $('#actionFieldForm').serialize(), function(data){
	    			if(data == 'success'){
	    				alert('预备完成！');
	    			}else{
	    				alert('预备失败！');
	    			}
    				box.modal('hide');
    				window.location.href = window.location.href;
	    		});
	        },
	        errorClass: 'validate-error'
		});
	});
	
	
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
	} */
	
	//////////////////////////////////////////费用
	//executeDelegate
	//添加费用
	/* $(document).delegate('#executeDelegate', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightDelegateId = $('.selectedItem:checked').val();
			$.post('fre-delegate-by-delegateid.do?freightDelegateId=' + freightDelegateId, function(data){
				var freightDelegate = data.freightDelegate;
				var freightAction = data.freightAction;
				if(freightDelegate.status != '待执行'){
					alert('当前状态，委托不可执行！');
					return false;
				}else if(freightAction.internal == 'F'){
					$.post('fre-delegate-to-execute-delegate.do?freightDelegateId=' + freightDelegateId, function(data){
						if(data == 'success'){
							alert('执行成功！');
							window.location.href = window.location.href;
						}
					});
				}else if(freightAction.internal == 'T'){
					alert('完成此委托之前，请先填写执行此委托收付费用！');
					$('#finishExpenseBtn').show();
					addExpense(freightAction.id);
				}
			});
		}
	}); */
	
	//添加费用
	function addExpense(freightActionId){
		$.ajax({
				url:'fre-expense-to-add-internal-expense.do?freightActionId=' + freightActionId,
				type:'post',
				dataType:'json',
				async:true,
				success:function(data){
					var freightPartA = data.freightPartA;
					var freightPartB = data.freightPartB;//如果是费用是收，则只能通过此部门收取
					var hasAddData = data.hasAddData;//已经保存的费用
					var freightExpenseTypes = data.freightExpenseTypes;//费用类型
					var fasInvoiceTypes = data.fasInvoiceTypes;
					var freightOrderBoxs = data.freightOrderBoxs;

					var html = '<input id="freightPriceId" type="hidden" value="">';
					html += '<input id="forBox" type="hidden" value="F">';//是否按箱添加做标记
					html += '<input id="freightActionId" type="hidden" value="' + freightActionId + '">';
					html += '<input id="freightExpenseId" type="hidden" value="">';
					html += '<table class="m-table table-bordered">';
					html += '<thead><tr>';
					html += '<th>费用名称</th><th>收/付</th><th>票/箱</th><th>收付单位</th><th>票种</th><th>币种</th><th>预计单价</th><th>备注</th></tr></thead><tbody>';
					html += '<tr><td><select id="freightExpenseTypeId" class="form-control required" onchange="updateExpenseType();"><option value=""></option>';
					$.each(freightExpenseTypes, function(i, item){
						html += '<option value="' + item.id + '">' + item.typeName + '</option>';
					});
					html += '</select></td>';
					html += '<td><select id="incomeOrExpense" name="incomeOrExpense" class="form-control required" onchange="updateIncomeOrExpense(\'' + freightPartB.id + '\', \'' + freightPartB.partName + '\');"><option value=""></option><option value="收">收&nbsp;</option><option value="付">付&nbsp;</option></select></td>';
					html += '<td><select id="countUnit" name="countUnit" class="form-control" onchange="updateCountUnit();"><option value=""></option><option value="票">票</option><option value="箱">箱</option></select></td>';
					html += '<td><select id="freightPartId" class="form-control required" onchange="updateFreightPart();"></select></td>';
					html += '<td><select id="fasInvoiceTypeId" class="form-control required">';
					$.each(fasInvoiceTypes, function(i, item){
						html += '<option value="' + item.id + '">' + item.typeName + '</option>';
					});
					html += '</select></td>';
					html += '<td><select id="currency" name="currency" class="form-control required" ><option value="人民币">人民币&nbsp;</option><option value="美元">美元</option></select></td>';
					html += '<td><input id="predictCount" name="predictCount" value="" class="form-control required number" ></td>';
					html += '<td><input id="descn" name="descn" class="form-control" maxlength="32"></td>'
					$('#expenseToAddForm').html(html);
					
					//箱封信息
					if(freightOrderBoxs != null && freightOrderBoxs.length > 0){
						html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll" onclick="updateOrderBox(this);" /></th>';
						html += '<th>箱号</th><th>箱型</th><th>箱属</th><th>箱况</th><th>封号</th><th>集装箱来源</th><th>状态</th></tr></thead><tbody>';
						$.each(freightOrderBoxs, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" onclick="updateOrderBox(this);"/></td>';
							html += '<td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
							html += '<td id="'+ item.id +'countUnit">' + item.freightBoxRequire.boxType + '</td>';
							html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
							html += '<td>' + item.freightBoxRequire.boxCondition + '</td>';
							html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
							html += '<td>' + item.freightBoxRequire.boxSource + '</td>';
							html += '<td>' + item.status + '</td>';
						});
						html += "</tbody></table>";
						$('#orderBoxSelectForm2').html(html);
						$('#orderBoxSelectForm2').hide(300);//修改费用之后，需要隐藏。
					}

					html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
					html += '<th>费用名称</th><th>编号</th><th>收/付</th><th>收付单位</th><th>票种</th><th>票/箱</th><th>汇率</th><th>币种</th><th>预计单价</th><th>预计总额</th><th>折合税金</th><th>状态</th><th>箱号</th><th>相关动作</th><th>备注</th></tr></thead><tbody>';
					
					$.each(hasAddData, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
						html += '<td>' + item.freightExpenseType.typeName + '</td>';
						html += '<td>' + item.expenseNumber + '</td>';
						html += '<td>' + item.incomeOrExpense + '</td>';
						html += '<td>' + (item.freightPartB == null ? '空':item.freightPartB.partName) + '</td>';
						html += '<td>' + item.fasInvoiceType.typeName + '</td>';
						html += '<td>' + item.countUnit + '</td>';
						html += '<td>' + item.exchangeRate + '</td>';
						html += '<td>' + item.currency + '</td>';
						html += '<td>' + item.predictCount + '</td>';
						html += '<td>' + item.predictAmount + '</td>';
						html += '<td>' + item.taxation + '</td>';
						html += '<td>' + item.status + '</td>';
						html += '<td>' + item.boxNumber + '</td>';
						html += '<td>' + (item.freightAction == null ? '无' : item.freightAction.freightActionType.typeName) + '</td>';
						html += '<td>' + item.descn + '</td></tr>';
					});
					html += "</tbody></table>";
					$('#expenseHasAddForm').html(html);
					initSelect2();//使用SELECT2
					
					var margin = (window.screen.availWidth - 1300)/2;
					$('#expenseModal').css("margin-left", margin);
					$('#expenseModal').css("width","1300px");
					$('#expenseModal').modal();
				},
				error:function(){
				}
		});
		return true;
	}
	
	//单独在增加，修改，删除费用
	$(document).delegate('#addExpense', 'click', function(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightDelegateId = $('.selectedItem:checked').val();
			$.post('fre-delegate-by-delegateid.do?freightDelegateId=' + freightDelegateId, function(data){
				var freightDelegate = data.freightDelegate;
				var freightAction = data.freightAction;
				if(freightAction.internal == 'T'){
					$('#finishExpenseBtn').hide();//单独修改费用，则隐藏该按钮
					addExpense(freightAction.id);
				}else{
					alert('非对内委托，无费用！');
				}
			});
		}
	});
	
	//更改费用类型时,获取有该费用类型的报价
	/**
	function updateExpenseType(freightPartId, freightPartName){
		if($('#expenseToAddForm #incomeOrExpense').val() == '收'){
			var html = '<option value=""></option><option value="' + freightPartId + '">' + freightPartName + '</option>';
			$('#expenseToAddForm #freightPartId').html(html);
			return;
		}else{
			var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
			$.post('fre-part-by-expensetypeid.do?freightExpenseTypeId=' + freightExpenseTypeId, function(data){
				var html = '<option value=""></option>';
				$.each(data, function(i, item){
					if(item != null){
						html += '<option value="' + item.id + '">' + item.partName + '</option>';
					}
				});
				$('#expenseToAddForm #freightPartId').html(html);
			});
		}
	}
	
	//更改单位时，获取该单位的该费用的成本
	function updateFreightPart(){
		if($('#expenseToAddForm #incomeOrExpense').val() == '付'){
			var freightPartId = $('#expenseToAddForm #freightPartId').val();
			var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
			var url = 'fre-price-by-partid-expensetypeid.do?freightPartId=' + freightPartId + '&freightExpenseTypeId=' + freightExpenseTypeId;
			$.post(url, function(data){
				if(data != null){
					$('#expenseToAddForm #freightPriceId').val(data.id);
					$('#expenseToAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
					$('#expenseToAddForm #predictCount').val(data.moneyCount);
					$('#expenseToAddForm #exchangeRate').val(data.exchangeRate);
					$('#expenseToAddForm #currency').val(data.currency);
					$('#expenseToAddForm #countUnit').val(data.countUnit);
					$('#expenseToAddForm #descn').val(data.descn);
				}
			});
		}
	}
	//更改计价方式
	function updateCountUnit(){
		var countUnit = $('#expenseToAddForm #countUnit').val();

		if(countUnit == '票'){
			$('#orderBoxSelectForm2').hide(300);
		}else{
			if($('#orderBoxSelectForm2 table').length > 0){
				$('#orderBoxSelectForm2').show(300);
			}else{
				$('#expenseToAddForm #countUnit').val('票')
				alert('尚未放箱，不能添加按箱计费的费用信息！');
			}
		}
	}
	**/
	
	//更改费用类型时，重置信息
	//20150205更新内部单位费用填报
	function updateExpenseType(){
		$('#expenseToAddForm #freightPartId').val('');
		$('#expenseToAddForm #freightPriceId').val('');
		$('#expenseToAddForm #fasInvoiceTypeId').val('');
		$('#expenseToAddForm #predictCount').val('');
		
		$('#expenseToAddForm #countUnit').val('');
		
		$('#expenseToAddForm #freightPartId').html('<option value=""></option>');
		$('#expenseToAddForm #freightPartId').select2('val', '');
		
		$('#orderBoxSelectForm2 .selectedItemId:checked').removeAttr('checked');
		$('#orderBoxSelectForm2 .selectedItemIdAll:checked').removeAttr('checked');
		
		initSelect2();
	}
	
	function updateIncomeOrExpense(freightPartId, freightPartName){
		var incomeOrExpense = $('#expenseToAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '收'){
			var html = '<option value=""></option><option value="' + freightPartId + '">' + freightPartName + '</option>';
			$('#expenseToAddForm #freightPartId').html(html);
			return;
		}else{
			$('#expenseToAddForm #freightPartId').html('<option value=""></option>');//先预置成空，然后再通过成本补充数据；
			initSelect2();
		}
		
		$('#expenseToAddForm #countUnit').val('');
	}
	
	//更改单位时，获取该单位的该费用的成本
	function updateFreightPart(){
		var incomeOrExpense = $('#expenseToAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '付'){//针对付出去的费用才使用成本
			var freightPartId = $('#expenseToAddForm #freightPartId').val();
			var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
			var countUnit = $('#expenseToAddForm #countUnit').val();
			var url;
			if(countUnit == '票'){
				url = 'fre-price-for-add-expense.do?freightPartId=' + freightPartId + '&freightExpenseTypeId=' + freightExpenseTypeId;
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm2 .selectedItemId:checked:first').val() + 'countUnit').text();
				url = 'fre-price-for-add-expense.do?freightPartId=' + freightPartId 
						+ '&freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit;
			}
			
			$.post(url, function(data){
				if(data != null){
					$('#expenseToAddForm #freightPriceId').val(data.id);
					$('#expenseToAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
					$('#expenseToAddForm #predictCount').val(data.moneyCount);
					$('#expenseToAddForm #currency').val(data.currency);
					$('#expenseToAddForm #countUnit').val((data.countUnit == '票' ? '票' : '箱'));
					
					initSelect2();
				}
			});
		}
	}
	
	//更改计价方式
	function updateCountUnit(){
		var countUnit = $('#expenseToAddForm #countUnit').val();
		var incomeOrExpense = $('#expenseToAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '付'){ //收的费用仍然可以按箱
			if(countUnit == '票'){
				$('#orderBoxSelectForm2').hide(300);
				
				var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
				$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId, function(data){
					var html = '<option value=""></option>';
					$.each(data, function(i, item){
						if(item != null){
							html += '<option value="' + item.id + '">' + item.partName + '</option>';
						}
					});
					
					$('#expenseToAddForm #freightPartId').html(html);
					$('#expenseToAddForm #freightPartId').val('');
					$('#expenseToAddForm #freightPartId').select2('val', '');
					
					$('#orderBoxSelectForm2 .selectedItemId:checked').removeAttr('checked');
					$('#orderBoxSelectForm2 .selectedItemIdAll:checked').removeAttr('checked');
					initSelect2();
				});
			}else if(countUnit == '箱'){
				if($('#orderBoxSelectForm2 table').length > 0){
					$('#orderBoxSelectForm2').show(300);
					
					$('#expenseToAddForm #freightPartId').html('<option value=""></option>');
					$('#expenseToAddForm #freightPartId').val('');
					initSelect2();
				}else{
					$('#expenseToAddForm #countUnit').val('票')
					alert('尚未放箱，不能添加按箱计费的费用信息！');
				}
			}
		}else{
			if($('#orderBoxSelectForm2 table').length > 0){
				$('#orderBoxSelectForm2').show(300);
			}else{
				$('#expenseToAddForm #countUnit').val('票')
				alert('尚未放箱，不能添加按箱计费的费用信息！');
			}
		}
	}
	
	//按箱计费之后，选择对应的箱封
	function updateOrderBox(ele){
		var incomeOrExpense = $('#expenseToAddForm #incomeOrExpense').val();
		if(incomeOrExpense == '收'){
			return;
		}
		var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
		var countUnit;
		if($(ele).attr('checked') == 'checked'){
			if($(ele).attr('class') == 'selectedItemIdAll'){
				countUnit = $('#' + $('#orderBoxSelectForm2 .selectedItemId:first').val() + 'countUnit').text();
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm2 .selectedItemId:checked:first').val() + 'countUnit').text();
			}
			 
			if(freightExpenseTypeId != '' && countUnit != ''){
				$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit, function(data){
					var html = '<option value=""></option>';
					$.each(data, function(i, item){
						if(item != null){
							html += '<option value="' + item.id + '">' + item.partName + '</option>';
						}
					});
					$('#expenseToAddForm #freightPartId').html(html);
					$('#expenseToAddForm #freightPartId').val('');
					$('#expenseToAddForm #freightPartId').select2('val', '');
					
					initSelect2();
				});
			}
		}else{
			if($(ele).attr('class') == 'selectedItemIdAll'){
				$('#expenseToAddForm #freightPartId').val('');
				$('#expenseToAddForm #freightPartId').select2('val', '');
				initSelect2();
			}else{
				countUnit = $('#' + $('#orderBoxSelectForm2 .selectedItemId:checked:first').val() + 'countUnit').text();
				if(freightExpenseTypeId != '' && countUnit != ''){
					$.post('fre-part-for-add-expense.do?freightExpenseTypeId=' + freightExpenseTypeId + '&countUnit=' + countUnit, function(data){
						var html = '<option value=""></option>';
						$.each(data, function(i, item){
							if(item != null){
								html += '<option value="' + item.id + '">' + item.partName + '</option>';
							}
						});
						
						$('#expenseToAddForm #freightPartId').html(html);
						$('#expenseToAddForm #freightPartId').val('');
						$('#expenseToAddForm #freightPartId').select2('val', '');
						
						initSelect2();
					});
				}
			}
		}
	}
	
	//按箱添加
	function addByBox(){
		var countUnit = $('#expenseToAddForm #countUnit').val();
		if(countUnit != '箱' || $('#orderBoxSelectForm2 .selectedItemId:checked') == 0){
			alert('按箱添加时，必须关联集装箱！');
			return false;
		}else{
			$('#expenseToAddForm #forBox').val('T');
			return true;
		}
	}
	
	$(function() {
		$("#expenseToAddForm").validate({
	        submitHandler: function(form) {
				var freightActionId = $('#expenseToAddForm #freightActionId').val();
				var freightExpenseTypeId = $('#expenseToAddForm #freightExpenseTypeId').val();
	    		var freightPartId = $('#expenseToAddForm #freightPartId').val();
	    		var freightPriceId = $('#expenseToAddForm #freightPriceId').val();
	    		var fasInvoiceTypeId = $('#expenseToAddForm #fasInvoiceTypeId').val();
	    		var freightExpenseId = $('#expenseToAddForm #freightExpenseId').val();
	    		var forBox = $('#expenseToAddForm #forBox').val();
	    		
	    		var freightOrderBoxId = '';
	    		$('#orderBoxSelectForm2 .selectedItemId:checked').each(function(i, item){
	    			freightOrderBoxId += '&freightOrderBoxId=' + $(item).val();
	    		});
	    		
	    		var countUnit = $('#expenseToAddForm #countUnit').val();
	    		if(countUnit == '票'){
	    			freightOrderBoxId = '';
	    		}else if(freightOrderBoxId == ''){
    				alert('选择按箱时，请选择具体的箱封信息！');
    				return false;
	    		}
	    		
				var data = toJsonString('expenseToAddForm');
				var url = 'fre-expense-done-add-internal-expense.do?freightPartId=' + freightPartId 
						+ '&freightPriceId=' + freightPriceId 
						+ '&freightExpenseTypeId=' + freightExpenseTypeId
						+ '&freightActionId=' + freightActionId
						+ '&fasInvoiceTypeId=' + fasInvoiceTypeId
						+ '&freightExpenseId=' + freightExpenseId
						+ '&forBox=' + forBox //是否按箱添加
						+ freightOrderBoxId;
				$.ajax({
	    			type: 'POST',
	    			data: data,
	    			url: url,
	    			contentType: 'application/json',
	    			success:function(data){
	    				addExpense(freightActionId);
	    			}
	    		});
				
	        },
	        errorClass: 'validate-error'
		});
	});
	
	//删除费用
	function deleteExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-expense-done-remove-expense.do?';
			$('#expenseHasAddForm .selectedItemId:checked').each(function(i, item){
				if(i == 0){
					url += 'freightExpenseId=' +　$(item).val();
				}else{
					url += '&freightExpenseId=' +　$(item).val();
				}
			});
			$.post(url, function(data){
				if(data == 'success'){
					var freightActionId = $('#expenseToAddForm #freightActionId').val();
					addExpense(freightActionId);
				}
			});
		}
	}
	
	//修改费用
	function reviseExpense(){
		if($('#expenseHasAddForm .selectedItemId:checked').length != 1){
			alert('请选择一条数据！');
			return false;
		}else{
			var freightExpenseId = $('#expenseHasAddForm .selectedItemId:checked').val();
			var url = 'fre-expense-to-revise-expense.do?freightExpenseId=' + freightExpenseId;
			$.post(url, function(data){
				$('#expenseToAddForm #freightExpenseId').val(data.id);
				$('#expenseToAddForm #freightExpenseTypeId').val(data.freightExpenseType.id);
				$('#expenseToAddForm #freightPartId').html('<option value="'+data.freightPartB.id+'">'+data.freightPartB.partName+'</option>');
				$('#expenseToAddForm #freightPriceId').val(data.freightPrice == null ? '' : data.freightPrice.id);
				if(data.incomeOrExpense == '付' && data.freightAction != null){
					$('#expenseToAddForm #freightActionId').val(data.freightAction.id);
				}
				if(data.countUnit == '箱'){
					$('#orderBoxSelectForm2').show(300);
					var freightOrderBoxs = data.freightOrderBoxs;
					$('#orderBoxSelectForm2 .selectedItemId').each(function(i, item){
						$(item).removeAttr('checked');
						$.each(freightOrderBoxs, function(n, ele){
							if($(item).val() == ele.id){
								$(item).attr('checked', 'checked');
								$(item).parent().parent().addClass('selected-tr');
							}
						});
					});
				}else{
					$('#orderBoxSelectForm2').hide(300);
				}
				$('#expenseToAddForm #fasInvoiceTypeId').val(data.fasInvoiceType.id);
				$('#expenseToAddForm #incomeOrExpense').val(data.incomeOrExpense);
				$('#expenseToAddForm #predictCount').val(data.predictCount);
				$('#expenseToAddForm #currency').val(data.currency);
				$('#expenseToAddForm #countUnit').val(data.countUnit);
				
				$('#expenseToAddForm select.required').each(function(i, item){
					var value = $(item).val();
					$(item).select2('val', value);
				});
			});
		}
	}
	
	//完成费用的填报，更改委托状态
	function finishExpense(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			if(window.confirm('确定费用填报完毕并执行该委托吗！')){
				$.post('fre-delegate-to-execute-delegate.do?freightDelegateId=' + $('.selectedItem:checked').val(), function(data){
					if(data == 'success'){
						$('#expenseModal').modal('hide');
						alert('执行成功！');
						window.location.href = window.location.href;
					}
				});
			}
		}
	}
	
	$(document).delegate('#doneDelegateRecover', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-delegate-done-delegate-recover.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '确认撤销'){
					flag = false;
				}else{
					if(i == 0){
						url += 'freightDelegateId=' +　$(item).val();
					}else{
						url += '&freightDelegateId=' +　$(item).val();
					}
				}
			});
			if(flag){
				if(window.confirm('确认撤销该委托吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('提交失败！');
						}else{
							alert('提交成功！');
							var href = window.location.href;
							window.location.href = href;
						} 
					});
				}
			}else{
				alert('提交失败！请确认所选委托状态！');
				return;
			}
		}
	});
	//标记为已查阅
	$(document).delegate('#doneMarkDelegate', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-delegate-done-mark-delegate.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				if(i == 0){
					url += 'freightDelegateId=' +　$(item).val();
				}else{
					url += '&freightDelegateId=' +　$(item).val();
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('标记失败！');
					}else{
						alert('标记成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('标记失败！请确认所选委托状态！');
				return;
			}
		}
	});
	//转出委托
	$(document).delegate('#transferDelegate', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '待执行' && status != '预备执行'){
					flag = false;
					return false;
				}
			});
			
			if(flag){
				$.post('${ctx}/user/user-get-coworker.do', function(data){
					var html = '<table class="m-table table-bordered table-hover">';
					html += '<thead><tr><th width="10" class="m-table-check"></th>';
					html += '<th>姓名</th><th>职位</th><th>部门</th></tr></thead><tbody>';
					$.each(data, function(i, item){
						html += '<tr><td><input class="selectedItemId a-check" type="radio" value="'+item.id+'" /></td>';
						html += '<td>' + item.displayName + '</td>';
						html += '<td>' + item.position.positionName + '</td>';
						html += '<td>' + item.orgEntity.orgEntityName + '</td></tr>';
					});
					html += '</tbody></table>';
					$('#transferDelegateForm').html(html);
					var margin = (window.screen.availWidth - 600)/2;
					$('#transferDelegateModal').css("margin-left", margin);
					$('#transferDelegateModal').css("width","600px");
					$('#transferDelegateModal').modal();
				});
			}else{
				alert('转出失败，请确认委托状态！');
			}
		}
	});
	
	function doneTransferDelegate(){
		var flag = true;
		var url = 'fre-delegate-done-transfer-delegate.do?receiverUserId=' + $('#transferDelegateForm .selectedItemId:checked').val();
		$('.selectedItem:checked').each(function(i, item){
			var status = $('#' + $(item).val() + 'status').text();
			if(status != '待执行' && status != '预备执行'){
				flag = false;
				return false;
			}else{
				url += '&freightDelegateId=' + $(item).val();
			}
		});
		
		if(flag){
			$.post(url, function(data){
				if(data == 'success'){
					alert('转出成功！');
					window.location.href = window.location.href;
				}else{
					alert('转出失败！');
				}
			});
		}else{
			alert('转出失败，请确认委托状态！');
		}
	}
	//批量执行，只只针对对外动作才有批量执行
	$(document).delegate('#doneBatchExecute', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-delegate-done-batch-execute.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				var internal = $('#' + $(item).val() + 'internal').text();
				if(status != '待执行' || internal != 'F'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'freightActionId=' +　$('#' + $(item).val() + 'freightActionId').val();
					}else{
						url += '&freightActionId=' +　$('#' + $(item).val() + 'freightActionId').val();
					}
				}
			});
			if(flag){
				$.post(url, function(data){
					if(data != 'success'){
						alert('执行失败！');
					}else{
						alert('执行成功！');
						var href = window.location.href;
						window.location.href = href;
					} 
				});
			}else{
				alert('执行失败！请确认所选委托状态！');
				return;
			}
		}
	});
	
	//预览委托
	function browseDelegate(freightActionId){
		bootbox.animate(false);
   		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		$.post('fre-delegate-to-browse-by-action.do?freightActionId=' + freightActionId, function(data){
			if(data != null){
				window.open('/VC/convert?fileName=' + data.delegateFile);
				box.modal('hide');
			}
		});
	}
	
	//添加费用时，箱封信息标记
	//动态表格点击tr选择
	$(document).delegate('#orderBoxSelectForm2 tr td:not(:has(input))', 'click', function(){
		$(this).parent().parent().find('tr td input').each(function(i, item){
			$(item).removeAttr('checked');
		});
		$(this).parent().find(':first input').attr('checked', 'checked');
		//添加背景标示
		$(this).parent().parent().find('tr').each(function(i, item){
			if($(item).find('td input').attr('checked') == 'checked'){
				$(item).addClass('selected-tr');
			}else{
				$(item).removeClass('selected-tr');
			}
		});
	});
	//动态表格点击input选择
	$(document).delegate('#orderBoxSelectForm2 tr td input', 'click', function(){
		$(this).parent().parent().parent().find('tr').each(function(i, item){
			if($(item).find('td input').attr('checked') == 'checked'){
				$(item).addClass('selected-tr');
			}else{
				$(item).removeClass('selected-tr');
			}
		});
	});
    </script>
  </body>

</html>
