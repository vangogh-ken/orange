<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>月度流程统计</title>
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
				  	<div class="portlet box red">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-bar-chart-o"></i>月度流程统计</div>
						</div>
						<div class="portlet-body">
							<div id="canvasDiv" style="height:100%;"></div>
						</div>
					   </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
    
    <%@include file="/common/footer.jsp"%>
    <script type="text/javascript" src="${ctx}/s2/assets/plugins/ichartjs/ichart.1.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/s2/assets/plugins/echarts/echarts.min.js"></script>
    <script type="text/javascript">
    $(function(){
    	App.init();
    });
    
    var colors = ['#4572a7', '#aa4643', '#89a54e','#80699b', '#3d96ae', '#db843d','#92a8cd','#89a54e'];

    function drawReport(){
      var data = [
    	<c:forEach items="${list}" var="item" varStatus='status'>
    		{name:'${item.name}', value:${item.percent}, color:colors[${status.index%8 + 1}]},
    	</c:forEach>
      ];
      
      //圆饼图
      <c:if test="${type == null or type == '' or type == 'donut'}">
    	  var chart = new iChart.Donut2D({
    			render : 'canvasDiv',
    			data: data,
    			shadow : true,
    			shadow_blur : 6,
    			shadow_color : '#aaaaaa',
    			shadow_offsetx : 0,
    			shadow_offsety : 0,
    			background_color:'#fefefe',
    			offset_angle:-120,//逆时针偏移120度
    			showpercent:true,
    			decimalsnum:2,
    			width : ${width},
    			height : ${height},
    			radius:120,
    			title : {
    				text : '${title}',
    				color : '#3e576f'
    			},
    		
    			center : {
    				text:'100%',
    				color:'#3e576f',
    				shadow:true,
    				shadow_blur : 2,
    				shadow_color : '#557797',
    				shadow_offsetx : 0,
    				shadow_offsety : 0,
    				fontsize : 40
    			},
    			
    			sub_option : {
    				label : {
    					background_color:null,
    					sign:false,//设置禁用label的小图标
    					padding:'0 4',
    					border:{
    						enable:false,
    						color:'#666666'
    					},
    					fontsize:11,
    					fontweight:600,
    					color : '#4572a7'
    				},
    				border : {
    					width : 2,
    					color : '#ffffff'
    				}
    			},
    			
    			footnote : {
    				text : 'VANBPMRP ' + new Date().toLocaleString(),
    				color : '#486c8f',
    				fontsize : 11,
    				padding : '0 38'
    			}
    		}); 
        </c:if>
        
        //柱状图
        <c:if test="${type == 'column'}">
    		var chart = new iChart.Column2D({
    			render : 'canvasDiv',//渲染的Dom目标,canvasDiv为Dom的ID
    			data: data,//绑定数据
    			title : '${title}',//设置标题
    			width : ${width},//设置宽度，默认单位为px
    			height : ${height},//设置高度，默认单位为px
    			shadow:true,//激活阴影
    			shadow_color:'#c7c7c7',//设置阴影颜色
    			
    			coordinate:{//配置自定义坐标轴
    				scale:[{//配置自定义值轴
    					position:'left',//配置左值轴
    					start_scale:${start},//设置开始刻度为0
    					end_scale:${end},//设置结束刻度为26
    					scale_space:${step},//设置刻度间距
    					listeners:{//配置事件
    						parseText:function(t,x,y){//设置解析值轴文本
    							return {text:t+'${unit}'}
    						}
    					}
    				}]
    			},
    			
    			sub_option:{
    				listeners:{
    					parseText:function(r,t){
    						return t+'${unit}';
    					}
    				}
    			},
    			
    			footnote : {
    				text : 'VANBPMRP ' + new Date().toLocaleString(),
    				color : '#486c8f',
    				fontsize : 11,
    				padding : '0 38'
    			}
    		});
        </c:if>
        
        //条形图
        <c:if test="${type =='bar'}">
    	    var chart = new iChart.Bar2D({
    			render: 'canvasDiv',
    			data: data,
    			title: '${title}',
    			showpercent:true,
    			decimalsnum:2,
    			width: ${width},
    			height: ${height},
    			animation: true,
    			
    			coordinate:{
    				scale:[{
    					position:'bottom',
    					start_scale:${start},
    					end_scale:${end},
    					scale_space:${step},
    					listeners:{
    						parseText:function(t,x,y){
    							return {text:t + "${unit}"}
    						}
    					}
    				}]
    			},
    			
    			footnote : {
    				text : 'VANBPMRP ' + new Date().toLocaleString(),
    				color : '#486c8f',
    				fontsize : 11,
    				padding : '0 38'
    			}
    		});
        </c:if>
        
        <c:if test="${type == 'pie'}">
    		var chart = new iChart.Pie2D({
    			render: 'canvasDiv',
    			data: data,
    			title: '${title}',
    			animation:true,
    			showpercent:true,
    			decimalsnum:2,
    			width: ${width},
    			height: ${height},
    			radius:140,
    			legend: {
    				enable : true
    			},
    			
    			sub_option: {
    				label: {
    					background_color:null,
    					sign:false,//设置禁用label的小图标
    					padding:'0 4',
    					border:{
    						enable:false,
    						color:'#666666'
    					},
    					fontsize:11,
    					fontweight:600,
    					color: '#4572a7'
    				},
    				border: {
    					width: 2,
    					color: '#ffffff'
    				}
    			},
    			
    			footnote : {
    				text : 'VANBPMRP ' + new Date().toLocaleString(),
    				color : '#486c8f',
    				fontsize : 11,
    				padding : '0 38'
    			}
    		});
        </c:if>
    	
    	
    	chart.draw();
    }

    $(function () {
    	drawReport();
    });
    </script>
  </body>

</html>
