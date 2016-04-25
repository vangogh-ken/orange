<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>工作日历信息</title>
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
				  	<div class="portlet box grey">
						<div class="portlet-title">
							<div class="caption"><i class="fa fa-calendar"></i>工作日历信息</div>
						</div>
						<div class="portlet-body form">
							<article class="m-widget" id="workCalAcrticle">
							</article>
						</div>
					  </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
    
    <%@include file="/common/footer.jsp"%>
    <script src="${ctx}/s2/assets/van/custom/js/work-calendar.js" type="text/javascript" ></script>
    <script type="text/javascript">
    $(function() {
        App.init();
    });
    
    $(function() {
    	$.getJSON('${ctx}/out/work-cal-get.do', function(data){
    		var workCalendar = new WorkCalendar();
        	workCalendar.render('#workCalAcrticle');
        	workCalendar.activeByWeek(data.weeks);
        	workCalendar.markHolidays(data.holidays);
        	workCalendar.markWorkdays(data.workdays);
        	workCalendar.markExtrdays(data.extrdays);
    	});
    });
    
    </script>
  </body>

</html>
