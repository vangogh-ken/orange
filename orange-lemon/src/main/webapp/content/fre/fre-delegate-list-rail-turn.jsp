<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>请班列计划管理</title>
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
							<div class="caption"><i class="fa fa-anchor"></i>请班列计划管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" id="executeDelegate">
										执行委托</button>
									<button class="btn btn-sm red" id="doneBatchExecute">
										批量执行</button>
										
									<button class="btn btn-sm green" id="batchExport">
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
								<form name="searchForm" method="post" action="fre-delegate-list-rail-turn.do" class="form-inline">
								    <label for="orderNumber">订单编号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control">
								    
								    <label for="freightActionTypeId">选择班列</label>
								    <select id="freightActionTypeId" name="freightActionTypeId" class="form-control">
								    	<option value="308bc224-8db1-11e4-b4b5-b870f47f73d5" <c:if test="${param.freightActionTypeId == '308bc224-8db1-11e4-b4b5-b870f47f73d5'}">selected</c:if> >城厢-上海</option>
								    	
								    	<option value="edb47a9a-1dfb-11e5-94e2-a4db305e5cc5" <c:if test="${param.freightActionTypeId == 'edb47a9a-1dfb-11e5-94e2-a4db305e5cc5'}">selected</c:if> >万州-眉山</option>
								    	<option value="a0330eb8-cd11-11e4-b0b8-a4db305e5cc5" <c:if test="${param.freightActionTypeId == 'a0330eb8-cd11-11e4-b0b8-a4db305e5cc5'}">selected</c:if> >眉山-万州</option>
								    	<option value="d0c01b89-8daf-11e4-b4b5-b870f47f73d5" <c:if test="${param.freightActionTypeId == 'd0c01b89-8daf-11e4-b4b5-b870f47f73d5'}">selected</c:if> >城厢-万州</option>
								    	<option value="f7dd6924-1dfb-11e5-94e2-a4db305e5cc5" <c:if test="${param.freightActionTypeId == 'f7dd6924-1dfb-11e5-94e2-a4db305e5cc5'}">selected</c:if> >普兴-万州</option>
								    	<option value="00861730-2899-11e5-9449-b870f47f73d5" <c:if test="${param.freightActionTypeId == '00861730-2899-11e5-9449-b870f47f73d5'}">selected</c:if> >城厢-果园</option>
								    	<option value="04e2b8b5-2899-11e5-9449-b870f47f73d5" <c:if test="${param.freightActionTypeId == '04e2b8b5-2899-11e5-9449-b870f47f73d5'}">selected</c:if> >果园-城厢</option>
								    	
								    	<option value="0daf9474-8db0-11e4-b4b5-b870f47f73d5" <c:if test="${param.freightActionTypeId == '0daf9474-8db0-11e4-b4b5-b870f47f73d5'}">selected</c:if> >万州-城厢</option>
								    	<option value="fd40eb05-1dfb-11e5-94e2-a4db305e5cc5" <c:if test="${param.freightActionTypeId == 'fd40eb05-1dfb-11e5-94e2-a4db305e5cc5'}">selected</c:if> >万州-普兴</option>
								    </select>
								    
								    <label for="turnTime">班列日期</label>
								    <input type="text" id="turnTime" name="turnTime" value="${param.turnTime}" class="form-control datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option ></option>
								    	<option value="待执行" <c:if test="${param.status == '待执行'}">selected</c:if> >待执行</option>
								    	<option value="已执行" <c:if test="${param.status == '已执行'}">selected</c:if> >已执行</option>
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
								                    <th>订单编号</th>
								                    <th>操作员</th>
								                    <th>业务员</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="FRE_ACTION_ID">动作名称</th>
								                    <th class="sorting" name="PLACE_TIME">下委托时间</th>
								                    <th class="sorting" name="DELEGATE_FILE">委托书</th>
								                    <th class="sorting" name="INTERNAL">是否对内</th>
								                    <th class="sorting" name="USER_ID">处理人</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">修改时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <input id="${item.id}freightActionId" value="${item.freightAction.id}" type="hidden">
								                    <td>${item.freightAction.freightMaintain.freightOrder.orderNumber}</td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.manipulator}</td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.salesman}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td id="${item.id}typeName">${item.freightAction.freightActionType.typeName}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<a href="fre-delegate-to-view-by-action.do?freightActionId=${item.freightAction.id}" target="_blank" >下载</a>
								                    	<a href="/VC/convert?fileName=${item.delegateFile}" target="_blank" >预览</a>
								                    </td>
								                    <td id="${item.id}internal">${item.freightAction.internal}</td>
								                    <td>${item.owner.displayName}</td>
								                    <td id="${item.id}status">${item.status}</td>
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
	 	        'orderNumber': '${param.orderNumber}',
	 	        'freightActionTypeId': '${param.freightActionTypeId}',
	 	        'turnTime': '${param.turnTime}',
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
	
	//添加费用
	$(document).delegate('#executeDelegate', 'click',function(e){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据!');
			return false;
		}else{
			var freightDelegateId = $('.selectedItem:checked').val();
			$.post('fre-delegate-by-delegateid.do?freightDelegateId=' + freightDelegateId, function(data){
				var freightDelegate = data.freightDelegate;
				var freightAction = data.freightAction;
				if(freightDelegate.status == '已执行'){
					alert('委托已经执行！');
					return false;
				}else if(freightAction.internal == 'F'){
					$.post('fre-delegate-to-execute-delegate.do?freightDelegateId=' + freightDelegateId, function(data){
						if(data == 'success'){
							alert('执行成功！');
							window.location.href = window.location.href;
						}
					});
				}
			});
		}
	});
	
	//批量导出班列
	$(document).delegate('#batchExport', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var typeName = '';
			var url = 'fre-delegate-to-export-delegate-rail.do?';
			var freightActionTypeId = $('#searchAcrticle #freightActionTypeId').val();
			var turnTime = $('#searchAcrticle #turnTime').val();
			var flag = true;
			if(turnTime == ''){
				alert('批量导出必须在查询中填写班列时间才能进行！');
				flag = false;
			}
			$('.selectedItem:checked').each(function(i, item){
				if(typeName == ''){
					typeName = $('#' + $(item).val() + 'typeName').text();
				}
				if(typeName != $('#' + $(item).val() + 'typeName').text()){
					alert('只能批量导出同种类型班列计划！');
					flag =  false;
				}
				if(i == 0){
					url += 'freightDelegateId=' + $(item).val();
				}else{
					url += '&freightDelegateId=' + $(item).val();
				}
			});
			url += '&turnTime=' + turnTime + '&freightActionTypeId=' + freightActionTypeId;
			if(flag){
				window.open(url);
			}
		}
	});
	
	
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
    </script>
  </body>

</html>
