<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>委托管理(操作级)</title>
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
							<div class="caption"><i class="fa fa-anchor"></i>委托管理(操作级)</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<!--  
									<button class="btn btn-sm red" id="invalidDelegate">
										作废委托<i class="fa fa-arrows-alt"></i></button>
									-->
									<button class="btn btn-sm green" id="toDelegateRecover">
										撤销委托</button>
										
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
											查询<i class="fa fa-chevron-down"></i></button>
											
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-EXPORT">
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
								<form name="searchForm" method="post" action="fre-delegate-list-manipulator.do" class="form-inline">
								    <label for="orderNumber">订单号</label>
								    <input type="text" id="orderNumber" name="orderNumber" value="${param.orderNumber}" class="form-control input-small">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
								    	<option value="" <c:if test="${param.status == ''}">selected</c:if> ></option>
								    	<option value="草稿" <c:if test="${param.status == '草稿'}">selected</c:if> >草稿</option>
								    	<option value="待执行" <c:if test="${param.status == '待执行'}">selected</c:if> >待执行</option>
								    	<option value="预备执行" <c:if test="${param.status == '预备执行'}">selected</c:if> >预备执行</option>
								    	<option value="已执行" <c:if test="${param.status == '已执行'}">selected</c:if> >已执行</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" class="m-form-dynamic" method="post">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th>订单编号</th>
								                    <th class="sorting" name="DELEGATE_NUMBER">委托编号</th>
								                    <th class="sorting" name="FRE_ACTION_ID">动作名称</th>
								                    <th class="sorting" name="PLACE_TIME">下委托时间</th>
								                    <th class="sorting" name="DELEGATE_FILE">委托书</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="USER_ID">处理人</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">修改时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.freightAction.freightMaintain.freightOrder.orderNumber}</td>
								                    <td>${item.delegateNumber}</td>
								                    <td>${item.freightAction.freightActionType.typeName}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.placeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    <a href="fre-delegate-to-view-by-action.do?freightActionId=${item.freightAction.id}" target="_blank" >下载</a>
								                    <!-- 
								                    <a href="${ctx}/base/to-online-view.do?sourceFileName=${item.delegateFile}" target="_blank" >预览</a>
								                    <a href="/VC/convert?fileName=${item.delegateFile}" target="_blank" >预览</a>
								                    -->
								                    <a href="javascript:void(0);" onclick="browseDelegate('${item.freightAction.id}');">预览</a>
								                    </td>
								                    <td id="${item.id}status">${item.status}</td>
								                    <td>${item.owner.displayName}</td>
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
	
	$(document).delegate('#toDelegateRecover', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'fre-delegate-to-delegate-recover.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '已执行' && status != '待执行' && status != '预备执行'){
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
				if(window.confirm('确认撤销委托吗？')){
					$.post(url, function(data){
						if(data != 'success'){
							alert('撤销失败！');
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
    </script>
  </body>

</html>
