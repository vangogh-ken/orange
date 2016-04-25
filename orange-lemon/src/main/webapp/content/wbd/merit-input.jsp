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
								  		<table style="width:100%;table-layout: fixed;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="14">工作业绩</td></tr>
								  			<tr class="tr-title"><td colspan="3">考核指标</td><td colspan="3">工作自评</td><td>分值</td><td colspan="3">分值标准</td><td>单项评价</td><td>一级评分</td><td>单项评价</td><td>二级评分</td></tr>
								  			</thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td colspan="3">
								  				<van:input id="KPIZB1"
											value="${item.KPIZB1}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td >
								  			<td colspan="3">
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
								  			
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIZB2"
											value="${item.KPIZB2}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
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
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIZB3"
											value="${item.KPIZB3}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
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
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIZB4"
											value="${item.KPIZB4}"
											styleClass="form-control required"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
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
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIZB5"
											value="${item.KPIZB5}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ5"
											value="${item.KPIFZBZ5}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ5"
											value="${item.KPIYJPJ5}"
											styleClass="form-control"
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
											styleClass="form-control"
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
								  			<td colspan="3">
								  				<van:input id="KPIZB6"
											value="${item.KPIZB6}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
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
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ6"
											value="${item.KPIFZBZ6}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ6"
											value="${item.KPIYJPJ6}"
											styleClass="form-control "
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
											styleClass="form-control "
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
								  			
								  			<!-- 7 -->
								  			<tr>
								  			<td colspan="3">
								  				<van:input id="KPIZB7"
											value="${item.KPIZB7}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIPJBZ7"
											value="${item.KPIPJBZ7}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ7"
											value="${item.KPIFZ7}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ7"
											value="${item.KPIFZBZ7}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ7"
											value="${item.KPIYJPJ7}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF7"
											value="${item.KPIYJPF7}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ7"
											value="${item.KPIEJPJ7}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF7"
											value="${item.KPIEJPF7}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 8 -->
								  			<tr>
								  			<td colspan="3">
								  				<van:input id="KPIZB8"
											value="${item.KPIZB8}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIPJBZ8"
											value="${item.KPIPJBZ8}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ8"
											value="${item.KPIFZ8}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ8"
											value="${item.KPIFZBZ8}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ8"
											value="${item.KPIYJPJ8}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF8"
											value="${item.KPIYJPF8}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ8"
											value="${item.KPIEJPJ8}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF8"
											value="${item.KPIEJPF8}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 9 -->
								  			<tr>
								  			<td colspan="3">
								  				<van:input id="KPIZB9"
											value="${item.KPIZB9}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIPJBZ9"
											value="${item.KPIPJBZ9}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ9"
											value="${item.KPIFZ9}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ9"
											value="${item.KPIFZBZ9}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ9"
											value="${item.KPIYJPJ9}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF9"
											value="${item.KPIYJPF9}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ9"
											value="${item.KPIEJPJ9}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF9"
											value="${item.KPIEJPF9}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			
								  			<!-- 10 -->
								  			<tr>
								  			<td colspan="3">
								  				<van:input id="KPIZB10"
											value="${item.KPIZB10}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIPJBZ10"
											value="${item.KPIPJBZ10}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIFZ10"
											value="${item.KPIFZ10}"
											styleClass="form-control"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td colspan="3">
								  				<van:input id="KPIFZBZ10"
											value="${item.KPIFZBZ10}"
											styleClass="form-control"
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
											<td>
								  				<van:input id="KPIYJPJ10"
											value="${item.KPIYJPJ10}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIYJPF10"
											value="${item.KPIYJPF10}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			<td>
								  				<van:input id="KPIEJPJ10"
											value="${item.KPIEJPJ10}"
											styleClass="form-control "
											styleCss="min-height:100px;"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
											</td>
								  			<td>
								  				<van:input id="KPIEJPF10"
											value="${item.KPIEJPF10}"
											styleClass="form-control number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  			</td>
								  			</tr>
								  			<tr>
								  				<td colspan="10">合计</td>
								  				<td colspan="2">
								  					<van:input id="YJKHYJFS"
											value="${item.YJKHYJFS}"
											styleClass="form-control required number"
											taskId="${item.taskId}"
											basisSubstanceId="${item.basisSubstanceId}"
											/>
								  				</td>
								  				<td colspan="2">
								  					<van:input id="EJKHYJFS"
											value="${item.EJKHYJFS}"
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
		if($('#BKHR').val() == ''){
			$('#BKHR').val('${userSession.displayName}'); 
		}
		$('#BKHR').attr('readonly', 'readonly');
		
		if($('#BKHRZW').val() == ''){
			$('#BKHRZW').val('${userSession.position.positionName}'); 
		}
		
		$('#BKHRZW').attr('readonly', 'readonly');
		
		if($('#BKHRZJ').val() == '0'){
			$('#BKHRZJ').val('${userSession.position.grade}'); 
		}
		
		$('#BKHRZJ').attr('readonly', 'readonly');
		
		if($('#BKHRSSBM').val() == ''){
			$('#BKHRSSBM').val('${userSession.orgEntity.orgEntityName}');
		}
		
		$('#BKHRSSBM').attr('readonly', 'readonly');
		
		if($('#YJKHR').val() == ''){
			$('#YJKHR').val('${userSession.displayName}'); 
			$('#YJKHR').attr('readonly', 'readonly');
		}
		if($('#EJKHR').val() == ''){
			$('#EJKHR').val('${userSession.displayName}'); 
			$('#EJKHR').attr('readonly', 'readonly');
		}
		if($('#KHQRR').val() == ''){
			$('#KHQRR').val('${userSession.displayName}'); 
			$('#KHQRR').attr('readonly', 'readonly');
		}
		
		if($('#YJKHSJ').val() == ''){
			$('#YJKHSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#YJKHSJ').attr('readonly', 'readonly');
		}
		
		if($('#EJKHSJ').val() == ''){
			$('#EJKHSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#EJKHSJ').attr('readonly', 'readonly');
		}
		
		if($('#KHQRSJ').val() == ''){
			$('#KHQRSJ').val($.fullCalendar.formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')); 
			$('#KHQRSJ').attr('readonly', 'readonly');
		}
		
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
		$('#ZZDF').attr('readonly', 'readonly');
		
		$("input[id*='KPIYJPF']").change(function(){
			var count = new Number($('#KPIYJPF1').val()) +
							new Number($('#KPIYJPF2').val()) +
							new Number($('#KPIYJPF3').val()) +
							new Number($('#KPIYJPF4').val()) +
							new Number($('#KPIYJPF5').val()) +
							new Number($('#KPIYJPF6').val()) +
							new Number($('#KPIYJPF7').val()) +
							new Number($('#KPIYJPF8').val()) +
							new Number($('#KPIYJPF9').val()) +
							new Number($('#KPIYJPF10').val());
			$('#YJKHYJFS').val(count);
		});
		
		$("input[id*='KPIEJPF']").change(function(){
			var count = new Number($('#KPIEJPF1').val()) +
							new Number($('#KPIEJPF2').val()) +
							new Number($('#KPIEJPF3').val()) +
							new Number($('#KPIEJPF4').val()) +
							new Number($('#KPIEJPF5').val()) +
							new Number($('#KPIEJPF6').val()) +
							new Number($('#KPIEJPF7').val()) +
							new Number($('#KPIEJPF8').val()) +
							new Number($('#KPIEJPF9').val()) +
							new Number($('#KPIEJPF10').val());
			$('#EJKHYJFS').val(count);
		});
		
		//计算总分
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ == 5)}">
			var count = "${item.YJKHYJFS}";
			$('#ZZDF').val(count);
		</c:if>
		
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ != 5)}">
			var count = "${(item.YJKHYJFS + item.EJKHYJFS)*0.5}";
			$('#ZZDF').val(count);
		</c:if>
	});
	</script>
	
</body>

</html>
