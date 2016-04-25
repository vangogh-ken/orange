<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>公告信息</title>
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
				<div class="row" style="width: 80%;margin-left: 10%;margin-right: 10%;">
				  <div class="col-md-12 blog-tag-data">
				  	<h4>栏目: ${item.cmsCatalog.name}</h4>
				  	<h3 style="text-align: center;">${item.title}</h3>
				  	<!-- 
					<img style="height: 200px;width: 100%;" src="${ctx}/s2/assets/img/gallery/item_img1.jpg" class="img-responsive" alt="">
				  	 -->
				  	<hr>
				  	<div class="news-item-page" style="font-size: 16px;">
				  		<p>
				  		${item.content}
				  		</p>
				  		<p style="text-align: right;">
				  		${item.publisher.position.orgEntity.orgEntityName} &nbsp;${item.publisher.displayName} 
				  		发布于: <fmt:formatDate value="${item.publishTime}" pattern="yyyy/MM/dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
				  	</div>
				  	<hr>
					<div class="media" style="margin-top: -20px;">
					
						<h3>${item.allowComment == 'T'? ' 评论' : '禁止评论'}</h3>
						<c:if test="${item.allowComment == 'T'}">
						<c:forEach var="comment" items="${item.cmsComments}" varStatus="status">
						<hr>
						<a href="#" class="pull-left">
						<img alt="" src="${ctx}/icon/${comment.poster.userBase.icon}" style="height: 60px;width: 60px;" class="media-object">
						</a>
						<div class="media-body">
							<h5 class="media-heading">
							<span>
								${comment.poster.displayName}  发表于: <fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								<a href="#">
								<li class="fa fa-star-o"></li>
								</a>
							</span>
							</h5>
							<p>
								${comment.content}
							</p>
							<hr>
						</div>
						</c:forEach>
						</c:if>
						<hr>
						<c:if test="${item.allowComment == 'T'}">
						<div class="post-comment">
							<h3>发表您自己的见解</h3>
							<form role="form" id="commentPostForm" name="commentPostForm" action="cms-comment-post-save.do?operationMode=STORE" method="post" onsubmit="return beforeSubmit();">
								<input type="hidden" name="cmsArticleId" value="${item.id}" />
								<div class="form-group">
									<label class="control-label">内容
									<span class="required">
										 *
									</span>
									</label>
									<textarea id="content" name="content" class="form-control required" rows="8" minlength="2" maxlength="500"></textarea>
								</div>
								<button class="btn red" type="submit">发布</button>
								<button class="btn red" type="button" onclick="history.back();" >返回</button>
							</form>
						</div>
						</c:if>
					 </div>
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
    
    $(function() {
        $("#commentPostForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    /* function collect(id){
    	showAsyncMessage(id);
    } */
    
    </script>
  </body>

</html>
