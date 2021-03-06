<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>月度绩效考核单</title>
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
									<th colspan="4">月度绩效考核单</th>
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
								  		<table style="width:100%;">
								  			<!-- 副总级 -->
								  			<c:if test="${(item.status == '草稿' && userSession.position.grade == 5) || (item.status != '草稿' && item.BKHRZJ == 5)}">
								  			<thead>
								  			<tr class="tr-title"><td colspan="7">工作业绩（副总级）</td></tr>
								  			<tr class="tr-title"><td>项目</td><td colspan="2">完成情况描述</td><td colspan="2">分值标准</td><td>单项评价</td><td>评分</td></tr>
								  			</thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
									  			<td style="width:80px;height:50px;">
									  				月度工作业绩（包括月度重点工作计划及临时专项工作完成情况）(60分)
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIPJBZ1"
												value="${item.KPIPJBZ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIFZBZ1"
												value="${item.KPIFZBZ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPJ1"
												value="${item.KPIYJPJ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPF1"
												value="${item.KPIYJPF1}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
											</tr>
											<!-- 2 -->
											<tr>
												<td>
									  				团队建设情况（成果）（20分）
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIPJBZ2"
												value="${item.KPIPJBZ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIFZBZ2"
												value="${item.KPIFZBZ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPJ2"
												value="${item.KPIYJPJ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
									  			<td>
									  				<van:input id="KPIYJPF2"
												value="${item.KPIYJPF2}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
											</tr>
											<!-- 3 -->
											<tr>
												<td>
									  				突出业绩及改进事项（20分）
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIPJBZ3"
												value="${item.KPIPJBZ3}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIFZBZ3"
												value="${item.KPIFZBZ3}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPJ3"
												value="${item.KPIYJPJ3}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
									  			<td>
									  				<van:input id="KPIYJPF3"
												value="${item.KPIYJPF3}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
								  			</tr>
								  			<tr>
								  				<td colspan="6">合计</td>
								  				<td>
								  					<van:input id="YJKHYJFS"
											value="${item.YJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  			</tr>
								  			</tbody>
								  			</c:if>
								  			
								  			<!-- 后勤类 -->
								  			<c:if test="${(item.status=='草稿' && (userSession.orgEntity.orgEntityName == '行政及人力资源部' || userSession.orgEntity.orgEntityName == '财务部')) || (item.status!='草稿' && (item.BKHRSSBM == '行政及人力资源部' || item.BKHRSSBM == '财务部'))}">
								  			<thead>
								  			<tr class="tr-title"><td colspan="9">工作业绩（后勤类）</td></tr>
								  			<tr class="tr-title"><td>项目</td><td colspan="2">完成情况描述</td><td colspan="2">分值标准</td><td>单项评价</td><td>一级评分</td><td>单项评价</td><td>二级评分</td></tr>
								  			</thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
									  			<td style="width:80px;height:50px;">
									  				月度工作业绩（包括月度重点工作计划及临时专项工作完成情况）(60分)
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIPJBZ1"
												value="${item.KPIPJBZ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIFZBZ1"
												value="${item.KPIFZBZ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPJ1"
												value="${item.KPIYJPJ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
									  			<td>
									  				<van:input id="KPIYJPF1"
												value="${item.KPIYJPF1}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIEJPJ1"
												value="${item.KPIEJPJ1}"
												styleClass="form-control required"
												styleCss="min-height:180px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIEJPF1"
												value="${item.KPIEJPF1}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
											</tr>
											<!-- 2 -->
											<tr>
												<td>
									  				突出业绩及改进事项（40分）
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIPJBZ2"
												value="${item.KPIPJBZ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
									  			</td>
									  			<td colspan="2">
									  				<van:input id="KPIFZBZ2"
												value="${item.KPIFZBZ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIYJPJ2"
												value="${item.KPIYJPJ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
									  			<td>
									  				<van:input id="KPIYJPF2"
												value="${item.KPIYJPF2}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIEJPJ2"
												value="${item.KPIEJPJ2}"
												styleClass="form-control required"
												styleCss="min-height:100px;"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
												<td>
									  				<van:input id="KPIEJPF2"
												value="${item.KPIEJPF2}"
												styleClass="form-control required"
												taskId="${item.taskId}"
												basisSubstanceId="${item.basisSubstanceId}"
												/>
												</td>
											</tr>
								  			<tr>
								  				<td colspan="7">合计</td>
								  				<td>
								  					<van:input id="YJKHYJFS"
											value="${item.YJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  				<td>
								  				<van:input id="EJKHYJFS"
											value="${item.EJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  			</tr>
								  			</tbody>
								  			</c:if>
								  			
								  			<!-- 普通 -->
								  			<c:if test="${(item.status=='草稿' && userSession.position.grade != 5 && userSession.orgEntity.orgEntityName != '行政及人力资源部' && userSession.orgEntity.orgEntityName != '财务部') || (item.status !='草稿' && item.BKHRZJ != 5 && item.BKHRSSBM != '行政及人力资源部' && item.BKHRSSBM != '财务部')}">
								  			<thead>
								  			<tr class="tr-title"><td colspan="8">工作业绩（业务类）</td></tr>
								  			<tr class="tr-title"><td>KPI指标</td><td>工作自评</td><td>分值</td><td>分值标准</td><td>单项评价</td><td>一级评分</td><td>单项评价</td><td>二级评分</td></tr>
								  			</thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB1"
											value="${item.KPIZB1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ1"
											value="${item.KPIPJBZ1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ1"
											value="${item.KPIFZ1}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			
								  			<td >
								  				<van:input id="KPIFZBZ1"
											value="${item.KPIFZBZ1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ1"
											value="${item.KPIYJPJ1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
												
								  			<td>
								  				<van:input id="KPIYJPF1"
											value="${item.KPIYJPF1}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ1"
											value="${item.KPIEJPJ1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF1"
											value="${item.KPIEJPF1}"
											styleClass="form-control required number "
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<!-- 2 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB2"
											value="${item.KPIZB2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ2"
											value="${item.KPIPJBZ2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ2"
											value="${item.KPIFZ2}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="KPIFZBZ2"
											value="${item.KPIFZBZ2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ2"
											value="${item.KPIYJPJ2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF2"
											value="${item.KPIYJPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ2"
											value="${item.KPIEJPJ2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF2"
											value="${item.KPIEJPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<!-- 3 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB3"
											value="${item.KPIZB3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ3"
											value="${item.KPIPJBZ3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ3"
											value="${item.KPIFZ3}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="KPIFZBZ3"
											value="${item.KPIFZBZ3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ3"
											value="${item.KPIYJPJ3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF3"
											value="${item.KPIYJPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ3"
											value="${item.KPIEJPJ3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF3"
											value="${item.KPIEJPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<!-- 4 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB4"
											value="${item.KPIZB4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ4"
											value="${item.KPIPJBZ4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ4"
											value="${item.KPIFZ4}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="KPIFZBZ4"
											value="${item.KPIFZBZ4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ4"
											value="${item.KPIYJPJ4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF4"
											value="${item.KPIYJPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ4"
											value="${item.KPIEJPJ4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF4"
											value="${item.KPIEJPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 5 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB5"
											value="${item.KPIZB5}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ5"
											value="${item.KPIPJBZ5}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ5"
											value="${item.KPIFZ5}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="KPIFZBZ5"
											value="${item.KPIFZBZ5}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ5"
											value="${item.KPIYJPJ5}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF5"
											value="${item.KPIYJPF5}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ5"
											value="${item.KPIEJPJ5}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF5"
											value="${item.KPIEJPF5}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 6 -->
								  			<tr>
								  			<td>
								  				<van:input id="KPIZB6"
											value="${item.KPIZB6}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIPJBZ6"
											value="${item.KPIPJBZ6}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ6"
											value="${item.KPIFZ6}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td >
								  				<van:input id="KPIFZBZ6"
											value="${item.KPIFZBZ6}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ6"
											value="${item.KPIYJPJ6}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF6"
											value="${item.KPIYJPF6}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ6"
											value="${item.KPIEJPJ6}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF6"
											value="${item.KPIEJPF6}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<tr>
								  				<td colspan="6">合计</td>
								  				<td>
								  					<van:input id="YJKHYJFS"
											value="${item.YJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  				<td>
								  					<van:input id="EJKHYJFS"
											value="${item.EJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  			</tr>
								  			</tbody>
								  			</c:if>
								  		</table>
								  	</td>
								  </tr>
								  <tr>
								  	<td colspan="4">
								  		<table style="width: 100%;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="5">工作行为</td></tr>
								  			<tr class="tr-title"><td>评估要素</td><td>评价标准</td><td>分值</td><td>一级评分</td><td>二级评分</td></tr></thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>
								  				遵守公司各项规章制度及出勤情况
								  			</td>
								  			<td>
								  				严格遵守公司各项规章制度，没有任何违纪现象发生
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJXWPF1"
											value="${item.YJXWPF1}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJXWPF1"
											value="${item.EJXWPF1}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 2 -->
								  			<tr>
								  			<td>
								  				行为规范、仪容仪表
								  			</td>
								  			<td>
								  				具有良好的工作形象，言语、行为得体，待人真诚、富有工作激情
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJXWPF2"
											value="${item.YJXWPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJXWPF2"
											value="${item.EJXWPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 3 -->
								  			<tr>
								  			<td>
								  				实际工作经验及解决岗位问题的能力
								  			</td>
								  			<td>
								  				能妥善处理和解决本岗位出现的各种问题
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJXWPF3"
											value="${item.YJXWPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJXWPF3"
											value="${item.EJXWPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 4 -->
								  			<tr>
								  			<td>
								  				善于学习，提高自身的知识水平和技能
								  			</td>
								  			<td>
								  				爱学习，通过不同的学习方式来提高自己的专业知识水平和能力
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJXWPF4"
											value="${item.YJXWPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJXWPF4"
											value="${item.EJXWPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 5 -->
								  			<tr>
								  			<td>
								  				工作主动性、积极性与敬业度
								  			</td>
								  			<td>
								  				工作管理有条不紊，热爱自己的本职工作，积极主动，勿需上级过多的指导与安排
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJXWPF5"
											value="${item.YJXWPF5}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJXWPF5"
											value="${item.EJXWPF5}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<tr>
								  				<td colspan="3">合计</td>
								  				<td>
								  					<van:input id="YJKHXWFS"
											value="${item.YJKHXWFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  				<td>
								  					<van:input id="EJKHXWFS"
											value="${item.EJKHXWFS}"
											styleClass="form-control required number"
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
								  	<td colspan="4">
								  		<table style="width: 100%;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="5" >工作能力</td></tr>
								  			<tr class="tr-title"><td>评估要素</td><td>评价标准</td><td>分值</td><td>一级评分</td><td>二级评分</td></tr></thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>
								  				专业知识
								  			</td>
								  			<td>
								  				熟练掌握本岗位所具备的专业知识
								  			</td>
								  			<td>
								  				0-30
								  			</td>
								  			<td>
								  				<van:input id="YJNLPF1"
											value="${item.YJNLPF1}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJNLPF1"
											value="${item.EJNLPF1}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 2 -->
								  			<tr>
								  			<td>
								  				分析判断能力
								  			</td>
								  			<td>
								  				能迅速对客观环境做出较为正确的判断，并能灵活运用到实际工作中
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJNLPF2"
											value="${item.YJNLPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJNLPF2"
											value="${item.EJNLPF2}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 3 -->
								  			<tr>
								  			<td>
								  				沟通能力
								  			</td>
								  			<td>
								  				能较清晰的表达自己的思想和想法，有效地化解矛盾
								  			</td>
								  			<td>
								  				0-30
								  			</td>
								  			<td>
								  				<van:input id="YJNLPF3"
											value="${item.YJNLPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJNLPF3"
											value="${item.EJNLPF3}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 4 -->
								  			<tr>
								  			<td>
								  				灵活应变能力
								  			</td>
								  			<td>
								  				应对客观环境的变化，能灵活采取相应的措施
								  			</td>
								  			<td>
								  				0-20
								  			</td>
								  			<td>
								  				<van:input id="YJNLPF4"
											value="${item.YJNLPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="EJNLPF4"
											value="${item.EJNLPF4}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<tr>
								  				<td colspan="3">合计</td>
								  				<td>
								  					<van:input id="YJKHNLFS"
											value="${item.YJKHNLFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  				<td>
								  					<van:input id="EJKHNLFS"
											value="${item.EJKHNLFS}"
											styleClass="form-control required number"
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
								  		<label class="td-label" for="ZZDF">最终得分</label>
								  	</td>
									<td>
										<van:input id="ZZDF"
											value="${item.ZZDF}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="KHDJ">考核等级</label>
								  	</td>
									<td>
										<van:input id="KHDJ"
											value="${item.KHDJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="YJKHR">一级考核人</label>
								  	</td>
									<td>
										<van:input id="YJKHR"
											value="${item.YJKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="YJKHSJ">一级考核时间</label>
								  	</td>
									<td>
										<van:input id="YJKHSJ"
											value="${item.YJKHSJ}"
											styleClass="form-control required datetimepicker"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
							    </tr>
							    <tr>
								  	<td>
								  		<label class="td-label" for="EJKHR">二级考核人</label>
								  	</td>
									<td>
										<van:input id="EJKHR"
											value="${item.EJKHR}"
											styleClass="form-control required"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
									</td>
									<td>
								  		<label class="td-label" for="EJKHSJ">二级考核时间</label>
								  	</td>
									<td>
										<van:input id="EJKHSJ"
											value="${item.EJKHSJ}"
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
		if($('#YJKHSJ').val() == ''){
			$('#YJKHSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#EJKHSJ').val() == ''){
			$('#EJKHSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#KHQRSJ').val() == ''){
			$('#KHQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
		}
		
		if($('#BKHR').val() == ''){
			$('#BKHR').val('${userSession.displayName}'); 
		}
		if($('#BKHRZW').val() == ''){
			$('#BKHRZW').val('${userSession.position.positionName}'); 
		}
		if($('#YJKHR').val() == ''){
			$('#YJKHR').val('${userSession.displayName}'); 
		}
		if($('#EJKHR').val() == ''){
			$('#EJKHR').val('${userSession.displayName}'); 
		}
		if($('#KHQRR').val() == ''){
			$('#KHQRR').val('${userSession.displayName}'); 
		}
		
		if($('#BKHRZJ').val() == '0'){
			$('#BKHRZJ').val('${userSession.position.grade}'); 
		}
		
		if($('#BKHRSSBM').val() == ''){
			$('#BKHRSSBM').val('${userSession.orgEntity.orgEntityName}'); 
		}
		
		//业绩考核总分
		/* $("input[id*='KPIYJPF']").change(function(){
			var count = new Number($('#KPIYJPF1').val()) +
							new Number($('#KPIYJPF2').val()) +
							new Number($('#KPIYJPF3').val()) +
							new Number($('#KPIYJPF4').val()) +
							new Number($('#KPIYJPF5').val()) +
							new Number($('#KPIYJPF6').val());
			$('#YJKHYJFS').val(count);
		});
		
		$("input[id*='KPIEJPF']").change(function(){
			var count = new Number($('#KPIEJPF1').val()) +
							new Number($('#KPIEJPF2').val()) +
							new Number($('#KPIEJPF3').val()) +
							new Number($('#KPIEJPF4').val()) +
							new Number($('#KPIEJPF5').val()) +
							new Number($('#KPIEJPF6').val());
			$('#EJKHYJFS').val(count);
		}); */
		
		//行为考核总分
		$("input[id*='YJXWPF']").change(function(){
			var count = new Number($('#YJXWPF1').val()) +
							new Number($('#YJXWPF2').val()) +
							new Number($('#YJXWPF3').val()) +
							new Number($('#YJXWPF4').val()) +
							new Number($('#YJXWPF5').val());
			$('#YJKHXWFS').val(count);
		});
		
		$("input[id*='EJXWPF']").change(function(){
			var count = new Number($('#EJXWPF1').val()) +
							new Number($('#EJXWPF2').val()) +
							new Number($('#EJXWPF3').val()) +
							new Number($('#EJXWPF4').val()) +
							new Number($('#EJXWPF5').val());
			$('#EJKHXWFS').val(count);
		});
		
		//能力考核总分
		$("input[id*='YJNLPF']").change(function(){
			var count = new Number($('#YJNLPF1').val()) +
							new Number($('#YJNLPF2').val()) +
							new Number($('#YJNLPF3').val()) +
							new Number($('#YJNLPF4').val());
			$('#YJKHNLFS').val(count);
		});
		
		$("input[id*='EJNLPF']").change(function(){
			var count = new Number($('#EJNLPF1').val()) +
							new Number($('#EJNLPF2').val()) +
							new Number($('#EJNLPF3').val()) +
							new Number($('#EJNLPF4').val());
			$('#EJKHNLFS').val(count);
		});
		
		$('#YJKHYJFS').attr('readonly', 'readonly');
		$('#YJKHXWFS').attr('readonly', 'readonly');
		$('#YJKHNLFS').attr('readonly', 'readonly');
		$('#EJKHYJFS').attr('readonly', 'readonly');
		$('#EJKHXWFS').attr('readonly', 'readonly');
		$('#EJKHNLFS').attr('readonly', 'readonly');
		$('#BKHR').attr('readonly', 'readonly');
		$('#BKHRZW').attr('readonly', 'readonly');
		$('#BKHRZJ').attr('readonly', 'readonly');
		$('#BKHRSSBM').attr('readonly', 'readonly');
		$('#ZZDF').attr('readonly', 'readonly');
		
		//副总级
		<c:if test="${(item.status=='草稿' && userSession.position.grade == 5) || (item.status!='草稿' && item.BKHRZJ == 5)}">
			$("input[id*='KPIYJPF']").change(function(){
				var count = new Number($('#KPIYJPF1').val()) +
								new Number($('#KPIYJPF2').val()) +
								new Number($('#KPIYJPF3').val());
								
				$('#YJKHYJFS').val(count);
			});
			
			$("input[id*='KPIEJPF']").change(function(){
				var count = new Number($('#KPIEJPF1').val()) +
								new Number($('#KPIEJPF2').val()) +
								new Number($('#KPIEJPF3').val());
				$('#EJKHYJFS').val(count);
			});
		
		</c:if>
		//后勤类
		<c:if test="${(item.status=='草稿' && userSession.position.grade !=5 && (userSession.orgEntity.orgEntityName == '行政及人力资源部' || userSession.orgEntity.orgEntityName == '财务部')) || (item.status != '草稿' && (item.BKHRSSBM == '行政及人力资源部' || item.BKHRSSBM == '财务部'))}">
			$("input[id*='KPIYJPF']").change(function(){
				var count = new Number($('#KPIYJPF1').val()) +
								new Number($('#KPIYJPF2').val());
				$('#YJKHYJFS').val(count);
			});
			
			$("input[id*='KPIEJPF']").change(function(){
				var count = new Number($('#KPIEJPF1').val()) +
								new Number($('#KPIEJPF2').val());
				$('#EJKHYJFS').val(count);
			});
		
		</c:if>
		//普通
		<c:if test="${(item.status=='草稿' && userSession.position.grade != 5 && userSession.orgEntity.orgEntityName != '行政及人力资源部' && userSession.orgEntity.orgEntityName != '财务部') || (item.status!='草稿' && item.status !='草稿' && item.BKHRZJ != 5 && item.BKHRSSBM != '行政及人力资源部' && item.BKHRSSBM != '财务部')}">
			$("input[id*='KPIYJPF']").change(function(){
				var count = new Number($('#KPIYJPF1').val()) +
								new Number($('#KPIYJPF2').val()) +
								new Number($('#KPIYJPF3').val()) +
								new Number($('#KPIYJPF4').val()) +
								new Number($('#KPIYJPF5').val()) +
								new Number($('#KPIYJPF6').val());
				$('#YJKHYJFS').val(count);
			});
			
			$("input[id*='KPIEJPF']").change(function(){
				var count = new Number($('#KPIEJPF1').val()) +
								new Number($('#KPIEJPF2').val()) +
								new Number($('#KPIEJPF3').val()) +
								new Number($('#KPIEJPF4').val()) +
								new Number($('#KPIEJPF5').val()) +
								new Number($('#KPIEJPF6').val());
				$('#EJKHYJFS').val(count);
			});
		</c:if>
		
		//计算总分
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ == 5)}">
			var count = "${item.YJKHYJFS*0.6 + item.YJKHXWFS*0.25 + item.YJKHNLFS*0.15}";
			$('#ZZDF').val(count);
		</c:if>
		
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ == 1)}">
			var count = "${(item.YJKHYJFS*0.6 + item.YJKHXWFS*0.25 + item.YJKHNLFS*0.15)*0.6 + (item.EJKHYJFS*0.6 + item.EJKHXWFS*0.25 + item.EJKHNLFS*0.15)*0.4}";
			$('#ZZDF').val(count);
		</c:if>
		
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ != 1 && item.BKHRZJ != 5)}">
			var count = "${(item.YJKHYJFS*0.6 + item.YJKHXWFS*0.25 + item.YJKHNLFS*0.15)*0.5 + (item.EJKHYJFS*0.6 + item.EJKHXWFS*0.25 + item.EJKHNLFS*0.15)*0.5}";
			$('#ZZDF').val(count);
		</c:if>
	});
	</script>
	
</body>

</html>
