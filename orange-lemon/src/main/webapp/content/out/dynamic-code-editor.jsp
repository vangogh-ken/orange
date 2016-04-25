<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>动态代码编辑</title>
    <%@include file="/common/s2.jsp"%>
    <script type="text/javascript">
    $(function(){
    	var javaEditor = CodeMirror.fromTextArea(document.getElementById("content"), {
    		styleActiveLine: true,//line选择是是否加亮
    		lineNumbers: true,//是否显示行数
    		lineWrapping: true,//是否自动换行
            matchBrackets: true,
            mode: "text/x-java"
          });
          var mac = CodeMirror.keyMap.default == CodeMirror.keyMap.macDefault;
          CodeMirror.keyMap.default[(mac ? "Cmd" : "Ctrl") + "-Space"] = "autocomplete";

    });
    </script>
  </head>

  <body>

    <div class="row-fluid">

	<!-- start of main -->
    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">动态代码编辑</h4>
		</header>
		<div class="content content-inner">

<form id="msgInfoForm" method="post" action="msginfo-save.do?operationMode=STORE" class="form-horizontal">
  
  <div class="control-group">
    <label class="control-label" for="title">标题</label>
	<div class="controls">
	  <input id="title" name="title" type="text" value="" class="text required" />
    </div>
  </div>
  
  <div class="control-group">
    <label class="control-label" for="content">内容</label>
	<div class="controls">
		<textarea id="content" name="content" class="text required span9" style="height:400px;"></textarea>
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
