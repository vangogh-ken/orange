<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>月度流程用量</title>
    <%@include file="/common/s2.jsp"%>
  </head>
  <body>
  	<div class="page-content-wrapper">
  		<div class="page-content" style="margin-left: -10px;margin-top: -10px;">
  			<div id="repContent" style="min-height:500px;height:100%;width:100%;"></div>
		</div>
	</div>
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript" src="${ctx}/s2/assets/plugins/echarts/echarts.min.js"></script>
	<script type="text/javascript">
	
	$(function(){
		$.post('rep-template-graph-parse-report.do?reportTemplateId=${reportTemplateId}', function(data){
			var title = new Array();
			var values = new Array();
			
			$.each(data.PROC, function(i, item){
				title.push(item.PROC_NAME);
				var key = {};
				key.name = item.PROC_NAME;
				key.value = item.PROC_COUNT;
				values.push(key);
			});
			
			var option = {
				    title : {
				        text: data.TJNF + '年' + data.TJYF + '月度流程用量统计',
				        subtext: '流程数量',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: title
				    },
				    series : [
				        {
				            name: '使用量',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: values,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
			
			var myChart = echarts.init($('#repContent')[0]);	
			myChart.setOption(option);
		});
		
	});
    </script>
  </body>

</html>
