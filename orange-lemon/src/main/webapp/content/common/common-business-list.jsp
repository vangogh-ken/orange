<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>通用管理</title>
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
							<div class="caption"><i class="fa fa-tasks"></i>通用管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<a class="btn btn-sm red" href="common-business-input.do?clsId=${clsId}&controllerId=${controllerId}" target="_blank">
											新增<i class="fa fa-plus"></i></a>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-times"></i></button>
									</sec:authorize>
										<button class="btn btn-sm red" onclick="$('#searchAcrticle').show();$('#searchAdvancedAcrticle').hide();">
											查询<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm red" onclick="$('#searchAdvancedAcrticle').show();$('#searchAcrticle').hide();">
											高级查询<i class="fa fa-chevron-down"></i></button>
										
										<button class="btn btn-sm red" onclick="importDataByCsv();">
											导入<i class="fa fa-arrow-up"></i></button>
										
										<button class="btn btn-sm red" onclick="">
											导出<i class="fa fa-arrow-down"></i></button>
											
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
								<form id="searchForm" name="searchForm" method="post" action="common-business-list.do?clsId=${clsId}&controllerId=${controllerId}" class="form-inline">
								    <input type="hidden" id="filter" name="filter" value="">
								    <c:forEach items="${attributes}" var="attr" varStatus="varStatus">
						        	<c:if test="${attr.columnName != 'ID' and attr.columnName != 'PRCI_ID' and attr.columnName != 'USER_ID' and attr.columnName != 'DISP_INX' and attr.valueType != 'MAKEGROUP' and varStatus.index < 7}">
						        		<label for="${attr.columnName}" class="search-attr">${attr.name}</label>
						        		<input type="text" id="${attr.columnName}" name="${attr.columnName}" value="${param[attr.columnName]}" class="form-control search-val <c:if test="${attr.type == 'TIMESTAMP'}">datepicker</c:if> ">
						        	</c:if>
						        	</c:forEach>
									<button class="btn btn-sm red" onclick="collectSearchConditon()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							
							<article class="m-widget" id="searchAdvancedAcrticle">
								<form id="searchAdvanceForm" name="searchAdvanceForm" method="post" action="common-business-list.do?clsId=${clsId}&controllerId=${controllerId}" class="form-inline" onsubmit="return false;">
								    <input type="hidden" id="filter" name="filter" value="">
								    <div class="row">
									    <div class="col-md-6 condition">
										    <select class="form-control condition-attr">
										    	<option value=""></option>
										    <c:forEach items="${attributes}" var="attr" varStatus="varStatus">
								        	<c:if test="${attr.columnName != 'ID' and attr.columnName != 'PRCI_ID' and attr.columnName != 'USER_ID' and attr.valueType != 'MAKEGROUP'}">
								        		<option value="${attr.columnName}" attrType="${attr.type}">${attr.name}</option>
								        	</c:if>
								        	</c:forEach>
								        	</select>
								        	<select class="form-control condition-type">
								        		<option value="LIKE">相似</option>
								        		<option value="=">等于</option>
								        		<option value="<>">不等于</option>
								        		<option value=">">大于</option>
								        		<option value="<">小于</option>
								        	</select>
								        	<input id="" value="" class="form-control condition-val">
								        	<br>
							        	</div>
							        	
							        	<div class="col-md-12">
							        		<br>
							        		<button class="btn btn-sm red" onclick="addCondition();">添加<i class="fa fa-arrows"></i></button>
							        		<button class="btn btn-sm red" onclick="resetCondition();">重置<i class="fa fa-arrows"></i></button>
											<button class="btn btn-sm red" onclick="collectAdvanceConditon();">查询<i class="fa fa-search"></i></button>
							        	</div>
						        	</div>
								 </form>
							</article>
							
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="common-business-remove.do?clsId=${clsId}&controllerId=${controllerId}" class="m-form-dynamic">
										<input id="clsId" type="hidden" name="clsId" value="${clsId}">
										<input id="controllerId" type="hidden" name="controllerId" value="${controllerId}">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <c:forEach items="${attributes}" var="attr">
										        	<c:if test="${attr.columnName != 'ID' and attr.columnName != 'PRCI_ID' and attr.valueType != 'MAKEGROUP' and attr.displayGrid =='T'}">
										        		<th class="sorting" name="${attr.columnName}">${attr.name}</th>
										        	</c:if>
										        </c:forEach>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.ID}"></td>
										        
										        <c:forEach items="${attributes}" var="attr">
										        	<c:if test="${attr.columnName != 'ID' and attr.columnName != 'PRCI_ID' and attr.valueType != 'MAKEGROUP' and attr.displayGrid != null and attr.displayGrid !='F'}">
										        		<td>${item[attr.columnName]}</td>
										        	</c:if>
										        </c:forEach>
										        <td>
										        	<c:if test="${item.PRCI_ID != null and item.PRCI_ID != ''}">
										        		<a class="btn btn-sm red" onclick="showGraph('${item.PRCI_ID}');">流程图</a>
										        	</c:if>
										        	<a class="btn btn-sm red" href="common-business-input.do?id=${item.ID}&clsId=${clsId}&controllerId=${controllerId}" target="_blank">编辑</a>
										        	<a class="btn btn-sm red" href="common-business-view.do?id=${item.ID}&clsId=${clsId}&controllerId=${controllerId}" target="_blank">查看</a>
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
	<script src="${ctx}/s2/assets/van/custom/js/import-data.js" type="text/javascript" ></script>
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
	 	    	'clsId': '${clsId}',
	 	        'controllerId': '${controllerId}'
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
	
	//普通查询收集查询条件
	function collectSearchConditon(){
		var attrs = $('.search-attr');
		var vals = $('.search-val');
		var filter = '';
		for(var i=0, len = attrs.length; i<len; i++){
			var attr = $(attrs.get(i)).attr('for');
			var val = $(vals.get(i)).val();
			if(attr != '' && val != ''){
				//filter += attr + ' = \'' + val + '\' AND ';
				filter += attr + ' LIKE \'%' + val + '%\' AND ';
			}
		}
		
		if(filter != ''){
			filter = filter.substring(0, filter.length-4);
		}
		$('#searchForm #filter').val(filter);
		document.searchForm.submit();
	}
	/**********************高级查询*********************************/
	//选择不同字段对相关属性进行改变:改变type的选项, 添加时间class
	$(document).delegate('.condition-attr', 'change', function(e){
		var currentAttr = $(this);
		var value = currentAttr.val();
		currentAttr.children().each(function(i, item){
			if($(item).val() == value){
				var attrType = $(item).attr('attrType');
				//var conditionType = currentAttr.nextUntil('.condition-attr').filter('.condition-type'); 
				//和下面一句等价
				var conditionType = currentAttr.next('.condition-type:first');
				conditionType.val('');
				if(attrType == 'TIMESTAMP'){
					currentAttr.nextUntil('.condition-attr').filter('.condition-val').addClass('datepicker');
					conditionType.children().each(function(n, ele){
						if($(ele).val() != '<' && $(ele).val() != '>' && $(ele).val() != '='){
							$(ele).hide();
						}else{
							$(ele).show();
						}
					});
				}else{
					currentAttr.nextUntil('.condition-attr').filter('.condition-val').removeClass('datepicker');
					conditionType.children().each(function(n, ele){
						if($(ele).val() != '<' && $(ele).val() != '>'){
							$(ele).show();
						}else{
							$(ele).hide();
						}
					});
				}
			}
			
			$('.condition .datepicker').datepicker({
        		format:'yyyy-mm-dd',
        		autoclose: true,
                todayBtn: true,
                language: 'zh'
        	});
		});
	});
	
	//添加查询条件
	function addCondition(){
		$('.condition:last').after($('.condition:first').clone());
	}
	//重置查询条件
	function resetCondition(){
		$('.condition:not(:first)').remove();
	}
	//高级查询收集查询条件
	function collectAdvanceConditon(){
		var attrs = $('.condition-attr');
		var types = $('.condition-type');
		var vals = $('.condition-val');
		var filter = '';
		for(var i=0, len = attrs.length; i<len; i++){
			var attr = $(attrs.get(i)).val();
			var type = $(types.get(i)).val();
			var val = $(vals.get(i)).val();
			if(attr != '' && val != ''){
				if(type == 'LIKE'){
					filter += attr + ' LIKE \'%' + val + '%\' AND ';
				}else if(type == '='){
					filter += attr + ' = \'' + val + '\' AND ';
				}else if(type == '<>'){
					filter += attr + ' <> \'' + val + '\' AND ';
				}else if(type == '>'){
					filter += attr + ' > \'' + val + '\' AND ';
				}else if(type == '<'){
					filter += attr + ' < \'' + val + '\' AND ';
				}
			}
		}
		
		if(filter != ''){
			filter = filter.substring(0, filter.length-4);
		}
		$('#searchAdvanceForm #filter').val(filter);
		document.searchAdvanceForm.submit();
	}
    </script>
  </body>

</html>
