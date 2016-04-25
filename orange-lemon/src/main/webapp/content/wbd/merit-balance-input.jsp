<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>月度收支差考核单</title>
<%@include file="/common/s2.jsp"%>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<section id="m-main-input">
				<article class="m-widget">
					<header style="height: 30px; margin-top: 0px; margin-bottom: 5px;border: solid #858585 1px;">
							<c:if test="${item.processInstanceId != null}">
							<button class="btn btn-sm red"
								style="float: left; margin-right: 10px;"
								onclick="showGraph('${item.processInstanceId}');">
									流程<i class="fa fa-sitemap"></i>
							</button>
							</c:if>
							<c:if test="${item.taskId != null}">
								<button onclick="getNextActivityDetails('${item.taskId}');"
									class="btn btn-sm red" style="float: left; margin-right: 10px;">
									发送<i class="fa fa-envelope-o"></i>
								</button>
     						</c:if>
							<c:if test="${item.taskId != null}">
								<button id="submitButton" onclick="$('#basisSubstanceForm').submit();" 
								class="btn btn-sm red" style="float: right; margin-left: 10px;">
									保存<i class="fa fa-save "></i>
								</button>
							</c:if>
							<button onclick="closeWindow();" class="btn btn-sm red"
								style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
						</header>
					<form id="basisSubstanceForm" method="post" onsubmit="return false" class="form-horizontal">
						<input id=basisSubstanceId type="hidden" name="basisSubstanceId" value="${item.basisSubstanceId}">
						<input id="taskId" type="hidden" name="taskId" value="${item.taskId}">
						<table class="table-input">
							<thead>
								<tr>
									<th colspan="4">月度收支差考核单</th>
								</tr>
							</thead>
							<tbody>
							    <tr>
								  	<td>
								  		<label class="td-label" for="BKHR">被考核人</label>
								  	</td>
									<td>
										<van:input id="BKHR"
											value="${item.BKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="BKHRZW">被考核人职位</label>
								  	</td>
									<td>
										<van:input id="BKHRZW"
											value="${item.BKHRZW}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="BKHRZJ">被考核人职级</label>
								  	</td>
									<td>
										<van:input id="BKHRZJ"
											value="${item.BKHRZJ}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="BKHRSSBM">被考核人所属部门</label>
								  	</td>
									<td>
										<van:input id="BKHRSSBM"
											value="${item.BKHRSSBM}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
									<td>
								  		<label class="td-label" for="KHNF">考核年份</label>
								  	</td>
									<td>
										<van:input id="KHNF"
											value="${item.KHNF}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="KHYF">考核月份</label>
								  	</td>
									<td>
										<van:input id="KHYF"
											value="${item.KHYF}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
							    	<td colspan="4">
								  		<table style="width:100%;;table-layout:fixed;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="11">收支差</td></tr>
								  			<tr><td style="height: 120px;">月份</td><td>月度计划目标任务（万元）</td><td>当月实际完成任务（万元）</td><td>当月实际完成比率（%）</td><td>当月超额完成比率（%）</td><td>累计应完成任务（万元）</td><td>累计实际完成任务（万元）</td><td>累计实际完成比率（%）</td><td>累计超额完成比率（%）</td><td>考核分数</td><td>考核标准</td></tr>
								  			</thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>1月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW1"
											value="${item.YDJHMBRW1}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW1"
											value="${item.DYSJWCRW1}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL1"
											value="${item.DYSJWCBL1}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL1"
											value="${item.DYCEWCBL1}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW1"
											value="${item.LJYWCRW1}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW1"
											value="${item.LJSJWCRW1}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL1"
											value="${item.LJSJWCBL1}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL1"
											value="${item.LJCEWCBL1}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS1"
											value="${item.KHFS1}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td rowspan="12">
								  				按照2016年更新版本业务人员和业务管理人员收支差考核标准进行评分
								  				<!--  
								  				当月收支差和累计收支差都完成，并且都超额完成20%，90分；当月收支差指标完成，累计收支差进度也完成，80分；两项指标，有一项完成，另一项未完成，70分；两项指标，都未完成，60分；两项指标，都未完成，并且累计进度指标滞后20%及以上，50分。
								  				-->
											</td>
								  			</tr>
								  			
								  			<!-- 2 -->
								  			<tr>
								  			<td>2月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW2"
											value="${item.YDJHMBRW2}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW2"
											value="${item.DYSJWCRW2}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL2"
											value="${item.DYSJWCBL2}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL2"
											value="${item.DYCEWCBL2}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW2"
											value="${item.LJYWCRW2}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW2"
											value="${item.LJSJWCRW2}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL2"
											value="${item.LJSJWCBL2}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL2"
											value="${item.LJCEWCBL2}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS2"
											value="${item.KHFS2}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 3 -->
								  			<tr>
								  			<td>3月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW3"
											value="${item.YDJHMBRW3}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW3"
											value="${item.DYSJWCRW3}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL3"
											value="${item.DYSJWCBL3}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL3"
											value="${item.DYCEWCBL3}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW3"
											value="${item.LJYWCRW3}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW3"
											value="${item.LJSJWCRW3}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL3"
											value="${item.LJSJWCBL3}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL3"
											value="${item.LJCEWCBL3}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS3"
											value="${item.KHFS3}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 4 -->
								  			<tr>
								  			<td>4月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW4"
											value="${item.YDJHMBRW4}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW4"
											value="${item.DYSJWCRW4}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL4"
											value="${item.DYSJWCBL4}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL4"
											value="${item.DYCEWCBL4}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW4"
											value="${item.LJYWCRW4}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW4"
											value="${item.LJSJWCRW4}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL4"
											value="${item.LJSJWCBL4}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL4"
											value="${item.LJCEWCBL4}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS4"
											value="${item.KHFS4}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 5 -->
								  			<tr>
								  			<td>5月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW5"
											value="${item.YDJHMBRW5}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW5"
											value="${item.DYSJWCRW5}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL5"
											value="${item.DYSJWCBL5}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL5"
											value="${item.DYCEWCBL5}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW5"
											value="${item.LJYWCRW5}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW5"
											value="${item.LJSJWCRW5}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL5"
											value="${item.LJSJWCBL5}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL5"
											value="${item.LJCEWCBL5}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS5"
											value="${item.KHFS5}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 6 -->
								  			<tr>
								  			<td>6月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW6"
											value="${item.YDJHMBRW6}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW6"
											value="${item.DYSJWCRW6}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL6"
											value="${item.DYSJWCBL6}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL6"
											value="${item.DYCEWCBL6}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW6"
											value="${item.LJYWCRW6}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW6"
											value="${item.LJSJWCRW6}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL6"
											value="${item.LJSJWCBL6}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL6"
											value="${item.LJCEWCBL6}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS6"
											value="${item.KHFS6}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 7 -->
								  			<tr>
								  			<td>7月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW7"
											value="${item.YDJHMBRW7}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW7"
											value="${item.DYSJWCRW7}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL7"
											value="${item.DYSJWCBL7}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL7"
											value="${item.DYCEWCBL7}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW7"
											value="${item.LJYWCRW7}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW7"
											value="${item.LJSJWCRW7}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL7"
											value="${item.LJSJWCBL7}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL7"
											value="${item.LJCEWCBL7}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS7"
											value="${item.KHFS7}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 8 -->
								  			<tr>
								  			<td>8月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW8"
											value="${item.YDJHMBRW8}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW8"
											value="${item.DYSJWCRW8}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL8"
											value="${item.DYSJWCBL8}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL8"
											value="${item.DYCEWCBL8}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW8"
											value="${item.LJYWCRW8}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW8"
											value="${item.LJSJWCRW8}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL8"
											value="${item.LJSJWCBL8}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL8"
											value="${item.LJCEWCBL8}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS8"
											value="${item.KHFS8}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 9 -->
								  			<tr>
								  			<td>9月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW9"
											value="${item.YDJHMBRW9}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW9"
											value="${item.DYSJWCRW9}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL9"
											value="${item.DYSJWCBL9}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL9"
											value="${item.DYCEWCBL9}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW9"
											value="${item.LJYWCRW9}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW9"
											value="${item.LJSJWCRW9}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL9"
											value="${item.LJSJWCBL9}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL9"
											value="${item.LJCEWCBL9}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS9"
											value="${item.KHFS9}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 10 -->
								  			<tr>
								  			<td>10月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW10"
											value="${item.YDJHMBRW10}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW10"
											value="${item.DYSJWCRW10}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL10"
											value="${item.DYSJWCBL10}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL10"
											value="${item.DYCEWCBL10}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW10"
											value="${item.LJYWCRW10}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW10"
											value="${item.LJSJWCRW10}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL10"
											value="${item.LJSJWCBL10}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL10"
											value="${item.LJCEWCBL10}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS10"
											value="${item.KHFS10}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 11 -->
								  			<tr>
								  			<td>11月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW11"
											value="${item.YDJHMBRW11}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW11"
											value="${item.DYSJWCRW11}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL11"
											value="${item.DYSJWCBL11}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL11"
											value="${item.DYCEWCBL11}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW11"
											value="${item.LJYWCRW11}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW11"
											value="${item.LJSJWCRW11}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL11"
											value="${item.LJSJWCBL11}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL11"
											value="${item.LJCEWCBL11}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS11"
											value="${item.KHFS11}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 12 -->
								  			<tr>
								  			<td>12月份</td>
								  			<td>
								  				<van:input id="YDJHMBRW12"
											value="${item.YDJHMBRW12}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCRW12"
											value="${item.DYSJWCRW12}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYSJWCBL12"
											value="${item.DYSJWCBL12}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="DYCEWCBL12"
											value="${item.DYCEWCBL12}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJYWCRW12"
											value="${item.LJYWCRW12}"
											styleClass="form-control number"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="LJSJWCRW12"
											value="${item.LJSJWCRW12}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="LJSJWCBL12"
											value="${item.LJSJWCBL12}"
											styleClass="form-control"
											
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="LJCEWCBL12"
											value="${item.LJCEWCBL12}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KHFS12"
											value="${item.KHFS12}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			</tbody>
								  		</table>
								  	</td>
								  </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="KHR">考核人</label>
								  	</td>
									<td>
										<van:input id="KHR"
											value="${item.KHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="KHSJ">考核时间</label>
								  	</td>
									<td>
										<van:input id="KHSJ"
											value="${item.KHSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr class="tr-title"><td colspan="4">考核确认</td></tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="KHQRR">考核确认人</label>
								  	</td>
									<td>
										<van:input id="KHQRR"
											value="${item.KHQRR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="KHQRSJ">考核确认时间</label>
								  	</td>
									<td>
										<van:input id="KHQRSJ"
											value="${item.KHQRSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							</tbody>
						</table>
					</form>
				</article>
			</section>
		</div>
	</div>

	<%@include file="/common/footer.jsp"%>
	<script src="${ctx}/s2/assets/van/custom/js/bpmhandle.js" type="text/javascript" ></script>
	<script type="text/javascript">
	$(function(){
		if($('#KHSJ').val() == ''){
			$('#KHSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#KHSJ').attr('readonly', 'readonly');
		}
		
		if($('#KHQRSJ').val() == ''){
			$('#KHQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss'));
			$('#KHQRSJ').attr('readonly', 'readonly');
		}
		
		if($('#BKHR').val() == ''){
			$('#BKHR').val('${userSession.displayName}'); 
			$('#BKHR').attr('readonly', 'readonly');
		}
		if($('#BKHRZW').val() == ''){
			$('#BKHRZW').val('${userSession.position.positionName}'); 
			$('#BKHRZW').attr('readonly', 'readonly');
		}
		if($('#KHR').val() == ''){
			$('#KHR').val('${userSession.displayName}'); 
			$('#KHR').attr('readonly', 'readonly');
		}
		if($('#KHQRR').val() == ''){
			$('#KHQRR').val('${userSession.displayName}');
			$('#KHQRR').attr('readonly', 'readonly');
		}
		
		if($('#BKHRZJ').val() == '0'){
			$('#BKHRZJ').val('${userSession.position.grade}'); 
			$('#BKHRZJ').attr('readonly', 'readonly');
		}
		
		if($('#BKHRSSBM').val() == ''){
			$('#BKHRSSBM').val('${userSession.orgEntity.orgEntityName}'); 
			$('#BKHRSSBM').attr('readonly', 'readonly');
		}
	});
	
	//load last time data
	<c:if test="${item.status == '数据汇总'}">
	$(function(){
		var KHNF = new Number('${item.KHNF}');
		var KHYF = new Number('${item.KHYF}');
		var BKHR = '${item.BKHR}';
		var basisSubstanceId = $('#basisSubstanceId').val();
		var filterText = '';
		
		if(KHYF == 1){
			filterText += ' KHYF=12 AND KHNF=' + (KHNF - 1) + ' AND BKHR=\'' + BKHR + '\'';
		}else{
			filterText += ' KHYF=' + (KHYF - 1) + ' AND KHNF=KHNF AND BKHR=\'' + BKHR + '\'';
		}
		
		var data = '{"basisSubstanceId":"' + basisSubstanceId + '", "filterText" : "' + filterText + '"}'
		$.post('${ctx}/basis/basis-substance-get-value-substance.do?basisSubstanceId=' + basisSubstanceId + '&filterText=' + filterText, function(data){
		//$.post('${ctx}/basis/basis-substance-get-value-substance?basisSubstanceId=' + basisSubstanceId + '&filterText=' + filterText, function(data){
			if(data != null){
				for(var k in data){
					$('#' + k).val(data[k]);
				}
			}
		});
	});
	</c:if>
	</script>
	
</body>

</html>
