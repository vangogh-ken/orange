<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>邮件收件管理</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body class="page-header-fixed">
    <%@include file="/common/header2.jsp"%>
    <div class="page-container" style="margin-left: -35px;">
    	<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper"> <!-- begin page-content-wrapper -->
			<div class="page-content"> <!-- begin page-content-->
				<%@include file="/common/setting.jsp"%>
				<div class="row">
				  <div class="col-md-12">
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-envelope"></i>邮件收件管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
											
									<span style="margin-right: 200px;">&nbsp;</span>
									<button class="btn btn-sm yellow" onclick="window.location.href = 'mail-receive-list.do'" >
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
								<form name="searchForm" method="post" action="mail-receive-list.do" class="form-inline">
								    <label for="name">主题</label>
								    <input type="text" id="subject" name="subject" value="${param.subject}" class="form-control input-medium">
								    
								    <label for="status">发件人</label>
								    <input type="text" id="addressFrom" name="addressFrom" value="${param.addressFrom}" class="form-control input-medium">
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="mail-receive-remove.do" class="m-form-dynamic" method="POST">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    
								                    <th class="sorting" name="ADDR_FROM">&nbsp;&nbsp;发件人</th>
								                    <th class="sorting" name="SUBJECT" style="min-width: 300px;">主题</th>
								                    <th class="sorting" name="RECEIVE_TIME">收件时间</th>
								                    <th class="sorting" name="CREATE_TIME">收至时间</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr ondblclick="location.href = 'mail-receive-view.do?id=${item.id}'">
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td><li class="fa ${item.status == '未读' ? 'fa-envelope':item.status == '已读' ? 'fa-envelope-o':item.status == '已回复' ? 'fa-mail-reply':'fa-envelope'}"></li>
								                    ${item.addressFrom}</td>
								                    <td>${item.subject}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
	 	        'subject': '${param.subject}',
	 	        'addressFrom': '${param.addressFrom}'
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
		$('.table-input  tbody tr').dblclick(function(){
        	//alert($(item).html());
        	alert(1);
        });
	});
    </script>
  </body>

</html>
