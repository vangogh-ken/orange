<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>收件箱</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
    <%@include file="/common/header2.jsp"%>
    <div class="page-container" style="margin-left: -35px;">
    	<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper"> <!-- begin page-content-wrapper -->
			<div class="page-content" style="margin-left: 35px;"> <!-- begin page-content-->
				<%@include file="/common/setting.jsp"%>
				<div class="row">
				  <div class="col-md-12">
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-comments"></i>收件箱</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									
									<button class="btn btn-sm green" id="batchReply">
											批量回复</button>
									<button class="btn btn-sm green" id="doneSettleRead">
											标记已读</button>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
										
									<span style="margin-right: 150px;">&nbsp;</span>
									<button class="btn btn-sm yellow" onclick="window.location.href = 'msg-info-list-send.do'" >
										切至发件</button>
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
								<form name="searchForm" method="post" action="msg-info-list-receive.do" class="form-inline">
								    <label for="sendDisplayName">发件人</label>
								    <input type="text" id="sendDisplayName" name="sendDisplayName" value="${param.sendDisplayName}" class="form-control">
								    
								    <label for="msgType">短信类型</label>
								    <select id="msgType" name="msgType" class="form-control">
									  <option value=""></option>
									  <option value="订单业务" ${param.msgType eq '订单任务' ? 'selected' : ''}>订单任务</option>
									  <option value="流程业务" ${param.msgType eq '流程业务' ? 'selected' : ''}>流程业务</option>
									  <option value="普通" ${param.msgType eq '普通' ? 'selected' : ''}>普通</option>
									  <option value="紧急" ${param.msgType eq '紧急' ? 'selected' : ''}>紧急</option>
								    </select>
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
									  <option value=""></option>
									  <option value="未读" ${param.status eq '未读' ? 'selected' : ''}>未读</option>
									  <option value="已读" ${param.status eq '已读' ? 'selected' : ''}>已读</option>
									  <option value="已回复" ${param.status eq '已回复' ? 'selected' : ''}>已回复</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="msg-info-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
										      <tr>
										        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
										        <th class="sorting" name="MSG_TYPE">类型</th>
										        <th class="sorting" name="SEND_USER_ID">发件人</th>
										        <th class="sorting" name="TITLE">标题</th>
										        <th class="sorting" name="CONTENT">内容</th>
										        <th class="sorting" name="CREATE_TIME">发送时间</th>
										        <th class="sorting" name="STATUS">状态</th>
										        <th>操作</th>
										      </tr>
										    </thead>
										
										    <tbody>
										      <c:forEach items="${pageView.results}" var="item">
										      <tr>
										        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
										        <td>${item.msgType}</td>
										        <td>${item.sender.displayName}</td>
										        <td>${item.title}</td>
										        <td>${item.content}</td>
										        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										        <td id="${item.id}status">${item.status}</td>
										        <td>
										        	<c:if test="${item.status eq '未读'}">
										        		<a href="msg-info-mark-read.do?id=${item.id}" class="a-update">标记为已读</a>&nbsp;
										        	</c:if>
										        	<c:choose>
										        		<c:when test="${item.status eq '已回复'}"></c:when>
										        		<c:otherwise>
										        			<a href="msg-info-reply.do?id=${item.id}" class="a-update">回复</a>
										        		</c:otherwise>
										        	</c:choose>
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
	
	<!-- 批量回复 -->
	<div class="modal fade" id="batchReplyModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">批量回复</h4>
			</div>
			<!-- 选择人员 -->
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="batchReplyForm" action="" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="doneBatchReply();">确定</button>
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
	 	        'sendDisplayName': '${param.sendDisplayName}',
	 	        'msgType': '${param.msgType}',
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
	
	//批量回复信息
	$(document).delegate('#batchReply', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return false;
		}else{
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '未读' && status != '已读'){
					flag = false;
					return false;
				}
			});
			
			if(flag){
				var html = '<table class="m-table table-bordered table-hover">';
				html += '<thead><tr><th colspan="4">回复内容</th></tr></thead><tbody>';
				html += '<tr><td><label class="td-label" for="content">内容</label></td><td colspan="3"><textarea id="content" name="content" style="min-height:80px;" class="form-control required" minlength="2" maxlength="128" ></textarea></td></tr>';
				html += '</tbody></table>';
				$('#batchReplyForm').html(html);
				
				var margin = (window.screen.availWidth - 600)/2;
				$('#batchReplyModal').css("margin-left", margin);
				$('#batchReplyModal').css("margin-top", 150);
				$('#batchReplyModal').css("width","600px");
				$('#batchReplyModal').modal();
			}else{
				alert('回复失败，请确认信息状态！');
			}
		}
	});
	
	function doneBatchReply(){
		var flag = true;
		var url = 'msg-info-done-batch-reply.do?';
		$('.selectedItem:checked').each(function(i, item){
			var status = $('#' + $(item).val() + 'status').text();
			if(status != '未读' && status != '已读'){
				flag = false;
				return false;
			}else{
				if(i == 0){
					url += 'outMsgInfoId=' + $(item).val();
				}else{
					url += '&outMsgInfoId=' + $(item).val();
				}
			}
		});
		
		if(flag){
			$.post(url, $('#batchReplyForm').serialize(),function(data){
				if(data == 'success'){
					alert('回复成功！');
					window.location.href = window.location.href;
				}else{
					alert('回复失败！');
				}
			});
		}else{
			alert('回复失败，请确认信息状态！');
		}
	}
	
	
	//标记已读
	$(document).delegate('#doneSettleRead', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'msg-info-done-settle-read.do?';
			var flag = true;
			$('.selectedItem:checked').each(function(i, item){
				var status = $('#' + $(item).val() + 'status').text();
				if(status != '未读'){
					flag = false;
					return false;//退出循环
				}else{
					if(i == 0){
						url += 'outMsgInfoId=' +　$(item).val();
					}else{
						url += '&outMsgInfoId=' +　$(item).val();
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
				alert('提交失败！请确认所选状态！');
				return;
			}
		}
	});
    </script>
  </body>

</html>
