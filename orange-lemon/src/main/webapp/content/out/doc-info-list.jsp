<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>文档管理</title>
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
				  	<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-file"></i>文档管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-ADD">
										<button class="btn btn-sm red" onclick="location.href='doc-info-input.do'">
											上传<i class="fa fa-arrows"></i></button>
									</sec:authorize>
									
									<sec:authorize ifAnyGranted="ROLE_AUTH-USER-LIST-REMOVE">
										<button class="btn btn-sm red" onclick="table.removeAll();">
											删除<i class="fa fa-arrows-alt"></i></button>
									</sec:authorize>
									<button class="btn btn-sm red" onclick="$('#sendArticle').slideToggle(200);$('#zipSendArticle').hide(200);">共享</button>
		  							<button class="btn btn-sm red" onclick="$('#zipSendArticle').slideToggle(200);$('#sendArticle').hide(200);">ZIP分发</button>
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
							
							<article id="sendArticle" class="m-widget">
								  <form name="docInfoDeliverForm" method="post" action="doc-info-deliver.do" class="form-inline" onsubmit="return false;">
								    <button class="btn btn-sm red" onclick="deliver();">共享</button>
								    <label for="type">共享方式</label>
								      <select id="sendType" name="sendType" class="form-control" onchange="changeReceiver();">
										<sec:authorize ifAnyGranted="ROLE_DOCINFO-TO-ONE">
											<option value="to-one">个人</option>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_DOCINFO-TO-ORGENTITY">
											<option value="to-orgentity">组织机构</option>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_DOCINFO-TO-UNDERLING">
											<option value="to-underling">下属</option>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_DOCINFO-TO-ALL">
											<option value="to-all">所有人</option>
										</sec:authorize>
									  </select>
									<label for="receiverId">&nbsp;&nbsp;接收对象</label>
									
									<div id="userId" class="input-icon userPicker" style="margin-left:300px;margin-top:-30px;width:200px;">
									  <i class="fa fa-user add-on"></i>
									  <input id="displayVal" type="text" value="" class="form-control">
									  <input id="userIdVal" type="hidden" value="" >
								    </div>
									
									<select id="orgEntityId" class="form-control">
								    	<c:forEach items="${orgEntities}" var="orgEntity">
								    		<option value="${orgEntity.id}" >${orgEntity.orgEntityName}</option>
								    	</c:forEach>
									</select>
								    <input type="hidden" id="selectedItem" name="selectedItem" value="">
								  </form>
							  </article>
							  
							  <article id="zipSendArticle" class="m-widget">
								  <form name="docInfoZipDeliverForm" method="post" action="doc-info-zip-deliver.do?operationMode=STORE" enctype="multipart/form-data" class="form-inline">
								     <label for="muiltFile">上传ZIP文件</label>
								     <input type="file" id="muiltFile" name="muiltFile" class="form-control">
								     
								     <label class="control-label" for="typeId">文档类型</label>
									  <select name="typeId" id="typeId" class="form-control">
									  <c:forEach items="${docTypes}" var="docType">
									  	<option value=""></option>
									  	<option value="${docType.id}">${docType.typeName}</option>
									  </c:forEach>
									  </select>
									<button class="btn btn-sm red" onclick="document.docInfoZipDeliverForm.submit()">确认</button>
								  </form>
							  </article>
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="doc-info-list.do" class="form-inline">
								    <label class="control-label" for="typeId">文档类型</label>
									  <select name="typeId" id="typeId" class="form-control">
									  <c:forEach items="${docTypes}" var="docType">
									  	<option value=""></option>
									  	<option value="${docType.id}" <c:if test="${param.typeId == docType.id }">selected</c:if> >${docType.typeName}</option>
									  </c:forEach>
									  </select>
								    
								    <label for="createTime">创建时间</label>
								    <input type="text" id="createTime" name="createTime" value="${param.createTime}" class="form-control datetimepicker">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="doc-info-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="NAME">文件名称</th>
										        <th class="sorting" name="TYPE">文件类型</th>
										        <th class="sorting" name="PARTICIPATE">是否共享</th>
										        <th class="sorting" name="ETERNAL">是否临时</th>
										        <th class="sorting" name="CREATE_TIME">创建时间</th>
										        <th class="sorting" name="MODIFY_TIME">修改时间</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.docName}</td>
										        <td>${item.docType.typeName}</td>
										        <td>
										        ${item.participate == 'T'?'共享中':'未共享'}
										        <c:if test="${item.participate == 'T'}">
										        	<a href="doc-info-disparticipate.do?id=${item.id}" class="btn btn-sm red">取消</a>
										        </c:if>
										        <c:if test="${item.participate != 'T'}">
										        	<a href="doc-info-participate.do?id=${item.id}" class="btn btn-sm red">共享</a>
										        </c:if>
										        </td>
										        <td>${item.eternal == 'T'?'永久文件':'临时文件'}</td>
										        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td><fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td>
										          	<a class="btn btn-sm red" href="doc-info-down.do?id=${item.id}" >下载</a>
										          	<a class="btn btn-sm red" href="doc-info-input.do?id=${item.id}" >编辑</a>
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
	 	        'filter_LIKES_username': '${param.filter_LIKES_username}',
	 	        'filter_EQI_status': '${param.filter_EQI_status}'
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
	
	$(function(){
		$("#sendArticle").hide();
		$("#orgEntityId").hide();
		$("#zipSendArticle").hide();
		//初始化
		changeReceiver();
	});

	function deliver(){
		var selectedItem = "";
		$(".selectedItem").each(function(i, item){
			var checked = $(item).attr("checked");
			if(checked == "checked"){
				var id = $(item).val();
				selectedItem += id + ";";
			}
		});
		
		if(selectedItem == ""){
			return;
		}else{
			selectedItem = selectedItem.substring(0, selectedItem.length - 1);
			$("#selectedItem").val(selectedItem);
			document.docInfoDeliverForm.submit();
		}
	}

	function changeReceiver(){
		var sendType = $("#sendType").val();
		if(sendType == "to-one"){
			$("#orgEntityId").removeAttr("name");
			$("#orgEntityId").hide();
			
			$("#userIdVal").attr("name", "receiverId");
			$("#userId").show();
		}else if(sendType == "to-orgentity"){
			$("#userIdVal").removeAttr("name");
			$("#userId").hide();
			
			$("#orgEntityId").attr("name","receiverId");
			$("#orgEntityId").show();
		}else{
			$("#orgEntityId").removeAttr("name");
			$("#orgEntityId").hide();
			$("#userIdVal").removeAttr("name");
			$("#userId").hide();
		}
	}
	
	 $(function() {
	    	new createUserPicker({
	    		modalId: 'userPicker',
	    		multiple: true,
	    		url: '${ctx}/user/user-get-all.do',
	    		valInput:'userIdVal',
	    		valType:'id',
	    		display:'displayVal'
	    	});
	    });
    </script>
 
  </body>
</html>