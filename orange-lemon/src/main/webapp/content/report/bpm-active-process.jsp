<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>BPM流程使用统计信息</title>
    <%@include file="/common/s2.jsp"%>

    <link rel="stylesheet" href="${ctx}/s/jqplot/jquery.jqplot.min.css" type="text/css" media="screen" />
    <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="../s/jqplot/excanvas.min.js"></script><![endif]-->
	<script type="text/javascript" src="${ctx}/s/jqplot/jquery.jqplot.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.barRenderer.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.pointLabels.min.js"></script>
	<script type="text/javascript" src="${ctx}/s/jqplot/plugins/jqplot.pieRenderer.min.js"></script>

	<script type="text/javascript">

function drawPie() {
  var data = [
	<c:forEach items="${list}" var="item">
		["${item.name}", ${item.percent}],
	</c:forEach>
		[]
  ];
  var plot1 = jQuery.jqplot ('pie', [data],
    {
      seriesDefaults: {
        // Make this a pie chart.
          renderer: jQuery.jqplot.PieRenderer,
          rendererOptions: {
          // Put data labels on the pie slices.
          // By default, labels show the percentage of the slice.
          showDataLabels: true
        }
      },
      legend: {show:true, location: 'e'}
    }
  );
}

$(function () {
	drawPie();
});
    </script>
  </head>

  <body>

	<div class="row-fluid">

	<!-- start of main -->
    <section id="m-main" class="span10">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">BPM流程使用统计信息</h4>
		</header>

		<div class="content content-inner">
		  <div id="pie" style="height:300px;"></div>
		</div>

      </article>

    </section>
	<!-- end of main -->
	</div>

  </body>

</html>
