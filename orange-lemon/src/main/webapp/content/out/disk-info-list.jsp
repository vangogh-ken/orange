<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>网盘</title>
    <%@include file="/common/s2.jsp"%>
    <link rel="stylesheet" href="${ctx}/s2/assets/jquery-file-upload/css/jquery.fileupload.css">
    <link rel="stylesheet" href="${ctx}/s2/assets/plugins/disk/sprite_list_icon.css">
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
							<div class="caption"><i class="fa fa-cloud"></i>网盘</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<c:if test="${display == 'grid'}">
									<button class="btn btn-sm yellow" onclick="window.location.href = 'disk-info-list.do?display=list&diskDir=${diskDir}'" >
										<li class="fa fa-list"></li>
									</button>
									</c:if>
									<c:if test="${display == 'list'}">
									<button class="btn btn-sm yellow" onclick="window.location.href = 'disk-info-list.do?display=grid&diskDir=${diskDir}'" >
										<li class="fa fa-th-large"></li>
									</button>
									</c:if>
									
									<div class="btn btn-sm green fileinput-button">
										<span>上传文件</span>
										<input type="file" name="file" 
										class="fileupload"
										style="opacity:0;width:60px;height:25px;position:absolute;z-index:1111;margin-top:-25px;cursor: pointer;"
										data-no-uniform="true" data-url="disk-info-done-upload-disk.do" 
										data-form-data='{"diskDir":"${diskDir}"}' >
									</div>
									
									<button class="btn btn-sm green" id="toCreateDir">
										新建目录
									</button>
											
									<button class="btn btn-sm red" onclick="$('#searchAcrticleDisk').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
									<span style="margin-right: 150px;">&nbsp;</span>
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
							
							<article class="m-widget" id="searchAcrticleDisk" style="display:none;">
								<form name="searchForm" method="post" action="disk-info-list.do?display=${display}&diskDir=${currentPath}"" class="form-inline">
								    <label for="fileName">文件名</label>
								    <input type="text" id="fileName" name="fileName" value="${param.fileName}" class="form-control">
								    
								    <label for="fileSuffix">文件类型</label>
								    <input type="text" id="fileSuffix" name="fileSuffix" value="${param.fileSuffix}" class="form-control">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control">
									  <option value=""></option>
									  <option value="active" ${param.status eq 'active' ? 'selected' : ''}>active</option>
									  <option value="trash" ${param.status eq 'trash' ? 'selected' : ''}>trash</option>
								    </select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							
							<article class="m-widget" id="selectedAcrticle" style="display:none;">
								<div class="pull-left">
									<button class="btn btn-sm green" id="doneDownLoadDisk">
											下载</button>
									<button class="btn btn-sm green" id="doneDeleteDisk">
											删除</button>
									<button class="btn btn-sm red" id="toShareDisk">
											创建分享</button>
									<button class="btn btn-sm red" id="recallShareDisk">
											取消分享</button>
									<button class="btn btn-sm red" id="toRenameTo">
											重命名</button>
									<button class="btn btn-sm yellow" id="toCopyTo">
											复制到</button>
									<button class="btn btn-sm yellow" id="toMoveTo">
											移动至</button>
								</div>
							</article>
							<article class="m-widget">
								<c:if test="${diskDir != ''}">
								<a href="disk-info-list-previous.do?diskDir=${diskDir}&display=${display}">
									<label style="cursor:pointer;text-decoration:underline;">上一级</label>
								</a>|
								<!--  
								<a href="disk-info-list.do?display=${display}"><label style="cursor:pointer;">全部</label></a>
								-->
								</c:if>
								
								<%
							    String diskDir = (String) request.getAttribute("diskDir");
								String currentPath = "";
								String[] array = diskDir.split("/");
								for (int i = 0; i < array.length; i++) {
								  String item = array[i];
							      if (i != 0) {
							        currentPath += "/" + item;
							      }
								  pageContext.setAttribute("item", item);
								  pageContext.setAttribute("currentPath", currentPath);
							    %>
							    <a href="disk-info-list.do?display=${display}&diskDir=${currentPath}">
							    	<label style="cursor:pointer;text-decoration:underline;">${item == '' ? '根目录' : item}</label>
							    </a>
							    /
							    <%
							    }
							    %>
							</article>
							<article class="m-widget">
								<c:if test="${display == 'grid'}">
								<div class="row">
								  <c:forEach items="${pageView.results}" var="item" varStatus="status">
									  <div class="col-md-2 text-center">
									    <c:if test="${item.fileSuffix == 'dir'}">
									    <a href="disk-info-list.do?diskDir=${diskDir}/${item.fileName}&display=${display}"><div class="icon-62 icon-62-${item.fileSuffix}"></div></a>
									    <a href="disk-info-list.do?diskDir=${diskDir}/${item.fileName}&display=${display}"><div class="file-62-name">${item.fileName}</div></a>
										</c:if>
									    <c:if test="${item.fileSuffix != 'dir'}">
									    <!--  
									    <a href="disk-info-view.do?id=${item.id}"><div class="icon-62 icon-62-${item.fileSuffix}"></div></a>
									    <a href="disk-info-view.do?id=${item.id}"><div class="file-62-name">${item.fileName}</div></a>
									    -->
									    <a href="javascript:void(0);" onclick="toViewDisk('${item.id}');"><div class="icon-62 icon-62-${item.fileSuffix}"></div></a>
									    <a href="javascript:void(0);" onclick="toViewDisk('${item.id}')"><div class="file-62-name">${item.fileName}</div></a>
										</c:if>
									  </div>
									<c:if test="${status.count % 6 == 0}">
									</div>
									<div class="row">
									</c:if>
								  </c:forEach>
								</div>
								</c:if>
								
								<c:if test="${display == 'list'}">
								<form id="dynamicGridForm" name="dynamicGridForm" action="" class="m-form-dynamic">
									<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
										<thead>
									      <tr>
									        <th width="10" class="m-table-check"><input id="checkAll" type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
									        <th class="sorting" name="FILE_NAME" style="min-width:220px;">文件名</th>
									        <th class="sorting" name="FILE_SIZE">大小</th>
									        <th class="sorting" name="CREATE_TIME">创建时间</th>
									        <th class="sorting" name="MODIFY_TIME">修改时间</th>
									      </tr>
									    </thead>
									
									    <tbody>
									      <c:forEach items="${pageView.results}" var="item">
									      <tr>
									        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
									        <td>
									        	<c:if test="${item.fileSuffix == 'dir'}">
											    &nbsp;
											    <a href="disk-info-list.do?diskDir=${diskDir}/${item.fileName}&display=${display}"><div class="icon-16 icon-16-${item.fileSuffix}">&nbsp;${item.fileName}</div></a>
												</c:if>
												
											    <c:if test="${item.fileSuffix != 'dir'}">
											    <!--  
											    <a href="disk-info-view.do?id=${item.id}" ><div class="icon-16 icon-16-${item.fileSuffix}">&nbsp;${item.fileName}</div></a>
											    -->
											    <li class="fa fa-picture-o" onclick="initQrcode('${item.id}');" style="cursor: pointer;"></li>
											    <a href="javascript:void(0);" onclick="toViewDisk('${item.id}')"><div class="icon-16 icon-16-${item.fileSuffix}">&nbsp;${item.fileName}</div></a>
												</c:if>
											</td>
									        <td>
									        	${item.fileSuffix == 'dir' ? '' : item.fileSize <= 1024 ? item.fileSize : 
									        	item.fileSize <= 1024*1024 ? (item.fileSize - item.fileSize%1024 + 1024)/1024 : 
									        	item.fileSize <= 1024*1024*1024 ? (item.fileSize - item.fileSize%(1024*1024) + 1024*1024)/(1024*1024) : 
									        	item.fileSize <= 1024*1024*1024*1024 ? (item.fileSize - item.fileSize%(1024*1024*1024) + 1024*1024*1024)/(1024*1024*1024) : 
									        	(item.fileSize - item.fileSize%(1024*1024*1024*1024) + 1024*1024*1024*1024)/(1024*1024*1024*1024)}
									        	
									        	${item.fileSuffix == 'dir' ? '' : item.fileSize <= 1024 ? 'B' : 
									        	item.fileSize <= 1024*1024 ? 'KB' : 
									        	item.fileSize <= 1024*1024*1024 ? 'MB' : 
									        	item.fileSize <= 1024*1024*1024*1024 ? 'GB' : 'TB'}
									        </td>
									        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									        <td><fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									      </tr>
									      </c:forEach>
									    </tbody>
									</table>
								</form>
								</c:if>
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
	
	<!-- 文件上传进度 -->
	<div class="modal fade" id="uploadFileProgress" >
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">上传文件</h4>
	      </div>
	      <div class="modal-body">
	        <div class="progress" style="margin-top:6%;margin-left:0;height:30px;width:100%;">
			  <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;font-size:16px;font-weight:600;line-height:25px;">
				<span class="sr-only">0%</span>
			  </div>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button id="uploadFileConfirmButton" class="btn btn-sm red" onclick="location.reload()">确认</button>
	        <button id="uploadFileCancelButton" class="btn btn-sm default" data-dismiss="modal" onclick="location.reload()">关闭</button>
	      </div>
	    </div>
	</div>
	
	<!-- 新建目录 -->
	<div class="modal fade" id="toCreateDirModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">新建目录</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="toCreateDirForm" action="" method="post" class="m-form-blank" style="overflow: hidden;">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-md-3">目录名</label>
								<div class="col-md-9">
									<input name="diskName" value="" size="40" maxlength="10" class="form-control text required">
									<input name="diskDir" value="${diskDir}" type="hidden">
								</div>
							</div>
						</div>
					</div>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="doneCreateDir();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 重命名 -->
	<div class="modal fade" id="toRenameToModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">重命名</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="toRenameToForm" action="" method="post" class="m-form-blank" style="overflow: hidden;">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-md-3">名称</label>
								<div class="col-md-9">
									<input id="fileName" name="fileName" value="" size="40" maxlength="10" class="form-control text required">
									<input id="diskInfoId" name="diskInfoId" value="" type="hidden">
								</div>
							</div>
						</div>
					</div>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="doneRenameTo();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- 共享-->
	<div class="modal fade" id="toShareDiskModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">共享</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="toShareDiskForm" method="post" class="m-form-blank" style="overflow: hidden;">
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm red" onclick="doneShareDisk();">确定</button>
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<!-- qrcode -->
	<div class="modal fade" id="qrcodeModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">二维码</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="max-height: 350px;">
				<form id="qrcodeForm" method="post" class="m-form-blank" style="overflow: hidden;">
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	<%@include file="/common/footer.jsp"%>
	
	
	<script src="${ctx}/s2/assets/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
	<script src="${ctx}/s2/assets/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
	<script src="${ctx}/s2/assets/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
	<!-- qrcode -->
	<script src="${ctx}/s2/assets/plugins/jquery-qrcode/jquery.qrcode.min.js"></script>
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
	 	        'fileName': '${param.fileName}',
	 	        'fileSuffix': '${param.fileSuffix}',
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
	
	//动态表格点击input选择
	$(document).delegate('#dynamicGrid tr', 'click', function(){
		if($('.selectedItem:checked').length > 0){
			$('#selectedAcrticle').show();
		}else{
			$('#selectedAcrticle').hide();
		}
	});
	
	$(document).delegate('#dynamicGrid #checkAll', 'click', function(){
		if($(this).attr('checked') == 'checked'){
			$('.selectedItem').each(function(i, item){
				$(item).attr('checked', 'checked');
			});
		}else{
			$('.selectedItem').each(function(i, item){
				$(item).removeAttr('checked');
			});
		}
		if($('.selectedItem:checked').length > 0){
			$('#selectedAcrticle').show();
		}else{
			$('#selectedAcrticle').hide();
		}
	});
	
	//文件上传
	function generateFileupload(maxLimitedSize) {
	    $('.fileupload').fileupload({
	        dataType: 'json',
	        add: function (e, data) {
				var file = data.files[0];
				if (file.size > maxLimitedSize) {
					alert("文件过大");
				} else {
					data.submit();
				}
	        },
			submit: function (e, data) {
				var $this = $(this);
				data.jqXHR = $this.fileupload('send', data);
				$('.progress-bar').css(
	                'width',
	                '0%'
	            ).html('0%');
				$('#uploadFileConfirmButton').hide();
				//$('#uploadFileProgress').modal('show');
				
				$('#uploadFileProgress').css("margin-left", window.screen.availWidth - 630);
				$('#uploadFileProgress').css("margin-top", window.screen.availHeight-320);
				$('#uploadFileProgress').css("width","600px");
				$('#uploadFileProgress').modal();
				
				return false;
			},
	        done: function (e, data) {
				$('#uploadFileConfirmButton').show();
				// location.reload();
				var t = setTimeout("recall();", 5000)
	        },
			fail: function (e, data) {
				//alert("上传失败");
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('.progress-bar').css(
		                'width', progress + '%'
		            ).html('上传失败');
				
				var t = setTimeout("recall();", 5000)
			},
	        progressall: function (e, data) {
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $('.progress-bar').css(
	                'width',
	                progress + '%'
	            ).html(progress + '%');
	        }
	    });
	}
	
	function recall() {
		$('#uploadFileProgress').modal('hide')
		location.reload();
	}

	$(function () {
		generateFileupload(1024 * 1024 * 1024);
	});
	
	//创建目录
	$(document).delegate('#toCreateDir', 'click', function(){
		var margin = (window.screen.availWidth - 600)/2;
		$('#toCreateDirModal').css("margin-top", 120);
		$('#toCreateDirModal').css("margin-left", margin);
		$('#toCreateDirModal').css("width","600px");
		$('#toCreateDirModal').modal();
	});
	
	function doneCreateDir(){
		$.post('disk-info-done-create-dir.do', $('#toCreateDirForm').serialize(),function(data){
			if(data == 'success'){
				location.reload();
			}else{
				alert('新建失败！');
			}
		});
	}
	
	//下载
	$(document).delegate('#doneDownLoadDisk', 'click', function(){
		if($('.selectedItem:checked').length > 0){
			var url = 'disk-info-done-download-disk.do?'
			$('.selectedItem:checked').each(function(i, item){
				url += 'diskInfoId=' + $(item).val() + '&';
			});
			
			window.open(url.substr(0, url.length - 1));
		}
	});
	
	//删除
	$(document).delegate('#doneDeleteDisk', 'click',function(e){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据!');
			return;
		}else{
			var url = 'disk-info-done-delete-disk.do?';
			$('.selectedItem:checked').each(function(i, item){
				url += 'diskInfoId=' + $(item).val() + '&';
			});
			$.post(url, function(data){
				if(data != 'success'){
					alert('删除失败！');
				}else{
					alert('删除成功！');
					location.reload();
				} 
			});
		}
	});
	
	//重命名
	$(document).delegate('#toRenameTo', 'click', function(){
		if($('.selectedItem:checked').length != 1){
			alert('请选择一条数据！');
		}else{
			$.post('disk-info-to-rename-to.do?diskInfoId=' + $('.selectedItem:checked').val(), function(data){
				var diskInfo = data.diskInfo;
				$('#toRenameToForm #diskInfoId').val(diskInfo.id);
				$('#toRenameToForm #fileName').val(diskInfo.fileName);
				
				var margin = (window.screen.availWidth - 600)/2;
				$('#toRenameToModal').css("margin-top", 120);
				$('#toRenameToModal').css("margin-left", margin);
				$('#toRenameToModal').css("width","600px");
				$('#toRenameToModal').modal();
			});
		}
	});
	
	function doneRenameTo(){
		$.post('disk-info-done-rename-to.do', $('#toRenameToForm').serialize(),function(data){
			if(data == 'success'){
				location.reload();
			}else{
				alert('重命名失败！');
			}
		});
	}
	
	//查看
	function toViewDisk(diskInfoId){
		$.post('disk-info-to-view-disk.do?diskInfoId=' + diskInfoId, function(data){
			//是否可以预览
			if(data.isBrowse){
				$.post('disk-info-to-browse-disk.do?diskInfoId=' + diskInfoId, function(data){
					window.open('/VC/convert?fileName=' + data);
					box.modal('hide');
				});
			}else{
				window.loaction.href = 'disk-info-done-view-disk.do?diskInfoId=' + diskInfoId;
			}
		});
	}
	
	//toCreateShare
	$(document).delegate('#toShareDisk', 'click', function(){
		if($('.selectedItem:checked').length == 0){
			alert('请选择数据！');
		}else{
			$.post('disk-info-to-share-disk.do?diskInfoId=' + $('.selectedItem:checked').val(), function(data){
				var users = data.users;
				var orgEntities = data.orgEntities;
				var html = '<table class="m-table table-bordered table-hover">'
				html +='<thead><tr><th>分享类型</th><th>开始时间</th><th>结束时间</th></tr></thead><tbody>';
				html +='<tr>';
				html +='<td><select id="shareType" name="shareType" onchange="updateShareType();" class="form-control required" >';
				html +='<option value="public">公共</option>';
				html +='<option value="group">群享</option>';
				html +='<option value="private">私享</option>';
				html +='</select></td>';
				html +='<td><input id="shareTime" name="shareTime" class="form-control required datepicker"></td>';
				html +='<td><input id="expireTime" name="expireTime" class="form-control required datepicker"></td>';
				html +='</tr></thead>';

				html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/><th>类型</th><th>名称</th></tr></thead></tbody>';
				
				$.each(users, function(i, item){
					html += '<tr class="userClass" style="display:none;"><td width="10" class="m-table-check"><input class="selectedItemId a-check" name="accessaryId" type="checkbox" value="' + item.id+'" /></td>'
					html += '<td>用户 </td>'
					html += '<td>'+ item.displayName +'</td></tr>';
				});
				$.each(orgEntities, function(i, item){
					html += '<tr class="orgEntityClass" style="display:none;"><td width="10" class="m-table-check"><input class="selectedItemId a-check" name="accessaryId" type="checkbox" value="' + item.id+'" /></td>'
					html += '<td>部门</td>'
					html += '<td>'+ item.orgEntityName +'</td></tr>';
				});
				html += '</tbody></table>';
				
				$('#toShareDiskForm').html(html);
				var margin = (window.screen.availWidth - 600)/2;
				$('#toShareDiskModal').css("margin-top", 60);
				$('#toShareDiskModal').css("margin-left", margin);
				$('#toShareDiskModal').css("width","600px");
				$('#toShareDiskModal').modal();
				
				initDatePicker();//时间格式
			});
		}
	});
	
	function updateShareType(){
		var shareType = $('#toShareDiskForm #shareType').val();
		if('public' == shareType){
			$('#toShareDiskForm .userClass,.orgEntityClass').hide();
		}else if('group' == shareType){
			$('#toShareDiskForm .userClass').hide();
			$('#toShareDiskForm .orgEntityClass').show();
		}else if('private' == shareType){
			$('#toShareDiskForm .orgEntityClass').hide();
			$('#toShareDiskForm .userClass').show();
		}
	}
	
	function doneShareDisk(){
		var flag = false;
		if($('#toShareDiskForm').valid()){
			var shareType = $('#toShareDiskForm #shareType').val();
			if('public' == shareType){
				flag = true;
			}else if('public' != shareType && $('#toShareDiskForm .selectedItemId:checked').length > 0){
				flag = true;
			}
		}
		if(flag){
			var url = 'disk-info-done-share-disk.do?' + $('#dynamicGridForm').serialize() + '&' + $('#toShareDiskForm').serialize()
			$.post(url, function(data){
				$('#toShareDiskModal').modal('hide');
				if(data == 'success'){
					showAsyncMessage('分享成功！');
				}else{
					showAsyncMessage('分享失败！');
				}
			});
		}else{
			alert('请正确填写分享选项！');
		}
	}
	
	$(document).delegate('#searchInput', 'change', function(e) {
		$('#toShareDiskForm table tr').each(function(i, item){
			if($(item).children('td').length > 0){
				$(item).show();
			}
		});
		$('#toShareDiskForm table tr').each(function(i, item){
			if($(item).text().indexOf($('#searchInput').val()) < 0){
				if($(item).children('td').length > 0){
					$(item).hide();
				}
			}
		});
	});
	
	//取消分享
	$(document).delegate('#recallShareDisk', 'click', function(e){
		var url = 'disk-info-recall-share-disk-by-disk-info-id.do?';
		$('.selectedItem:checked').each(function(i, item){
			url += 'diskInfoId=' + $(item).val() + '&';
		});
		
		$.post(url, function(data){
			if(data == 'success'){
				showAsyncMessage('取消成功！');
			}else{
				showAsyncMessage('取消失败！');
			}
		});
	});
	
	function initQrcode(diskInfoId){
		$('#qrcodeForm').empty();
		$('#qrcodeForm').qrcode({
			width:200,
			height:200,
			correctLevel:0,
			text:'${httpCtx}/public/disk-info-done-download-disk.do?diskInfoId=' + diskInfoId
			}
		);
		var margin = (window.screen.availWidth - 250);
		$('#qrcodeModal').css("margin-top", window.screen.availHeight-440);
		$('#qrcodeModal').css("margin-left", margin);
		$('#qrcodeModal').css("width","220px");
		$('#qrcodeModal').modal();
	}
	
	//字符串转UTF-8
	function toUtf8(str) {    
	    var out, i, len, c;    
	    out = "";    
	    len = str.length;    
	    for(i = 0; i < len; i++) {    
	        c = str.charCodeAt(i);    
	        if ((c >= 0x0001) && (c <= 0x007F)) {    
	            out += str.charAt(i);    
	        } else if (c > 0x07FF) {    
	            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
	            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
	            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
	        } else {    
	            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
	            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
	        }    
	    }    
	    return out;    
	}
    </script>
  </body>

</html>
