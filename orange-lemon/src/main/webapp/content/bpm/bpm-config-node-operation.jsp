<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>配置节点操作</title>
    <%@include file="/common/s2.jsp"%>
     <script type="text/javascript">
    $(function() {
        $('#bpmConfigNodeForm').validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    
    $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'taskDefinitionAssignee',
    		valType:'displayName'
    	});
    });
    
    $(function() {
    	new createRolePicker({
    		modalId: 'rolePicker',
    		multiple: true,
    		url: '${ctx}/role/role-all.do',
    		valInput:'taskDefinitionCandidateGroups',
    		valType:'roleName'
    	});
    });
    </script>
  </head>

  <body>

    <div class="row-fluid">

	<!-- start of main -->
    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">配置节点操作</h4>
		</header>
		<div class="content content-inner">

<form id="bpmConfigNodeForm" method="post" action="bpm-config-node-save.do?operationMode=STORE" class="form-horizontal">
  <c:if test="${item.id != null}">
  	<input id="id" type="hidden" name="id" value="${item.id}">
  </c:if>
  
  <input id="processDefinitionId" name="processDefinitionId" type="hidden" value="${item.processDefinitionId}"/>
  <div class="control-group">
    <label class="control-label" for="processDefinitionKey">流程Key</label>
	<div class="controls">
		<!-- 
	  <input id="processDefinitionKey" name="processDefinitionKey" type="text" value="${item.processDefinitionKey}" readonly="readonly"/>
	   -->
	   ${item.processDefinitionKey}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="processDefinitionName">流程名称</label>
	<div class="controls">
	<!-- 
	  <input id="processDefinitionName" name="processDefinitionName" type="text" value="${item.processDefinitionName}" readonly="readonly"/>
	   -->
	   ${item.processDefinitionName}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="taskDefinitionKey">任务Key</label>
	<div class="controls">
	<!--  
	  <input id="taskDefinitionKey" name="taskDefinitionKey" type="text" value="${item.taskDefinitionKey}" readonly="readonly"/>
	  -->
	  ${item.taskDefinitionKey}
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="taskDefinitionName">任务名称</label>
	<div class="controls">
	<!-- 
	  <input id="taskDefinitionName" name="taskDefinitionName" type="text" value="${item.taskDefinitionName}" readonly="readonly"/>
	   -->
	   ${item.taskDefinitionName}
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="taskDefinitionAssignee">任务委托人</label>
	<div class="controls">
		<div class="input-append userPicker">
		  	<input style="width: 185px;" id="taskDefinitionAssignee" name="taskDefinitionAssignee" type="text" value="${item.taskDefinitionAssignee}" />
		   <!-- 
		  	<input type="hidden" name="attorney" class="input-medium" value="">
		  	<input type="text" style="width: 185px;" value="">
		  	-->
		  	<span class="add-on" style="padding:2px;cursor: pointer;"><i class="icon-user"></i></span>
	  	</div>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="taskDefinitionCandidateGroups">任务候选组</label>
	<div class="controls">
    	<div class="input-append rolePicker">
		  	<input style="width: 185px;" id="taskDefinitionCandidateGroups" name="taskDefinitionCandidateGroups" type="text" value="${item.taskDefinitionCandidateGroups}"/>
		   <!-- 
		  	<input type="hidden" name="attorney" class="input-medium" value="">
		  	<input type="text" style="width: 185px;" value="">
		  	-->
		  	<span class="add-on" style="padding:2px;cursor: pointer;"><i class="icon-user"></i></span>
	  	</div>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="taskDefinitionListners">任务监听器</label>
	<div class="controls">
	  <input id="taskDefinitionListners" name="taskDefinitionListners" type="text" value="${item.taskDefinitionListners}"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="status">任务运行时状态</label>
	<div class="controls">
	  <input id="status" name="status" type="text" value="${item.status}"/>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
      <button id="submitButton" class="btn a-submit">保存</button>
      <button type="button" onclick="history.back();" class="btn a-cancel">返回</button>
    </div>
  </div>
</form>
		</div>
      </article>

    </section>
	<!-- end of main -->
	</div>

  </body>

</html>
