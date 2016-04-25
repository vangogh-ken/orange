<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>帖子信息</title>
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
				  <div class="col-md-9 blog-tag-data" style="margin-top: -20px;">
				  	<h3>${forumTopic.title}</h3>
					<img style="height: 200px;width: 100%;" src="${ctx}/s2/assets/img/gallery/item_img1.jpg" class="img-responsive" alt="">
				  	<hr>
				  	<div class="news-item-page">
				  		<p>
				  		${forumTopic.user.displayName} 发表于: <fmt:formatDate value="${forumTopic.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
				  		</p>
				  		${forumTopic.content}
				  	</div>
				  	<hr>
					<div class="media" style="margin-top: -20px;">
						<h3>评论</h3>
						<c:forEach var="post" items="${forumTopic.forumPosts}" varStatus="status">
						<hr>
						<a href="#" class="pull-left">
						<img alt="" src="${ctx}/icon/${post.user.userBase.icon}" style="height: 60px;width: 60px;" class="media-object">
						</a>
						<div class="media-body">
							<h5 class="media-heading">
							<span>
								${post.user.displayName}  发表于: <fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								<a href="#">
									 回复
								</a>
							</span>
							</h5>
							<p>
								${post.content}
							</p>
							<hr>
						</div>
						</c:forEach>
						<hr>
						<div class="post-comment">
								<h3>发表您自己的见解</h3>
								<form role="form" id="forumPostForm" name="forumPostForm" action="forum-post-save.do?operationMode=STORE" method="post" onsubmit="return beforeSubmit();">
									<input type="hidden" name="forumTopicId" value="${forumTopic.id}" />
									<div class="form-group">
										<label class="control-label">内容
										<span class="required">
											 *
										</span>
										</label>
										<textarea id="content" name="content" class="form-control required" rows="8" minlength="2" maxlength="500"></textarea>
									</div>
									<button class="btn red" type="submit">发布</button>
									<button class="btn red" type="button" onclick="history.back();">返回</button>
								</form>
						</div>
					 </div>
				    </div>
				    <div class="col-md-3" style="margin-top: -20px;">
				    	<h3>最近主题</h3>
						<div class="top-news">
							<c:forEach items="${forumTopics}" var="topic" varStatus="varStatus">
							<a href="${ctx}/out/forum-post-input.do?forumTopicId=${topic.id}" class="btn ${(varStatus.index%5 + 1) == 1? 'blue':(varStatus.index%5 + 1) == 2?'yellow':(varStatus.index%5 + 1) == 3?'purple':(varStatus.index%5 + 1) == 4?'red':'green'}">
								<span style="font-size: 14px;">
									 ${topic.user.displayName}: ${topic.title}
								</span>
								<em><fmt:formatDate value="${topic.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></em>
								<em>
								<i class="fa fa-tags"></i>
								${topic.content}
								</em>
								<i class="fa fa-bookmark-o top-news-icon"></i>
							</a>
							</c:forEach>
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
        $("#forumPostForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active" ><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    })
    </script>
  </body>

</html>
