<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>流程图查看</title>
    <%@include file="/common/s2.jsp"%>
    <style type="text/css">
    	.qtip-lable tr td{
    		font-size: 16px;
    		font-family: "宋体"
    	}
    	
    </style>
    
  </head>

  <body>
  	<div class="page-content-wrapper">
  		<div class="page-content" style="margin-left: -10px;margin-top: -10px;">
  			<!-- 流程图 -->
		    <div id="bpmGraph">
			  <div id="bpmGraphImg">
			    <img src="../bpm/bpm-process-definition-png.do?processDefinitionKey=${processDefinitionKey}">
			  </div>
			</div>
  		</div>
  	</div>
    
	<%@include file="/common/footer.jsp"%>
	<script type="text/javascript">
    	$(function(){
    		var url = '../bpm/bpm-process-definiton-trace.do?processDefinitionKey=${processDefinitionKey}';
    		
    		$.ajax({
    			url: url,
    			type:'post',
    			dataType:'json',
    			async:false,
    			success:function(data){
    				var positions = data["positions"];
        			var details = data["details"];
        			
        			for(var activityId in positions){
        				var v = positions[activityId];
        				
        				$('#bpmGraph').append('<div class="activity" id="' + activityId + '"><div/>');
        				var $positionDiv = $('#' + activityId);
        				$positionDiv.css({
        	                position: 'absolute',
        	                left: (v.x + 5),
        	                top: (v.y + 5),
        	                width: (v.width - 2),
        	                height: (v.height - 2),
        	                backgroundColor: 'black',
        	                opacity: 0,
        	                zIndex: $.fn.qtip.zindex - 1
        	            });
        				
        				$positionDiv.data('detail', details[activityId]);
        				
                        $('#' + activityId).qtip({
                            content: function() {
                            	var $activity = $(this);
                            	var detail;
                            	var styleStr = "style='font-size: 14px;font-family: \"黑体\";padding-top:3px;'";
                            	
                               	detail = $activity.data('detail').split("<!!>");
                                var tipContent = "<table class='qtip-table'>";
                                
                                for(var i=0, len = detail.length; i<len; i+=2){
                                	tipContent += "<tr><td " + styleStr + ">";
                                	tipContent += detail[i];
                                	tipContent += "</td><td " + styleStr + ">";
                                	tipContent += detail[i+1] + "<td/></tr>";
                                }
                                tipContent += "</table>";
                                
                                return tipContent;
                            },
                            position: {
                                at: 'bottom left',
                                adjust: {
                                    x: -3
                                }
                            },
                            style: {
                            	width: '100%',
                            	name: 'light'
                            },
                            show:{
                            	when:'hover',
                            	solo:true
                            },
                            hide: false
                        }); 
        			}
    			}
    		});
    		
    		
    	});
    
    </script>
  </body>

</html>
