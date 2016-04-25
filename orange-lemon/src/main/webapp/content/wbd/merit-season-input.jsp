<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>季度考核单</title>
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
									<th colspan="4">季度考核单</th>
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
								  		<table style="width: 100%;table-layout:fixed;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="7">工作能力</td></tr>
								  			<tr class="tr-title"><td>评估要素</td><td colspan="3">评价标准</td><td>分值</td><td>一级评分</td><td>二级评分</td></tr></thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>
								  				专业能力
								  			</td>
								  			<td colspan="3">
								  				<p>
								  				专业进技能扎实，能正确理解工作内容，无需上级详细提示和指导，根据有关情况和外部条件分析问题，分析原因，选用适当的方法开展工作，迅速、适当的处理工作中的问题和临时任务，30分
												</p><p>
												具备胜任本职工作所需的基础知识、专业知识、技能和理论水平，能独立开展工作，偶尔需要指导,24分
												</p><p>
												具备基础的专业知识、工作熟练程度和经验一般，在上级指导下方可开展工作，但需要催促和提醒，21分
												</p><p>
												对本职工作理解不深，专业知识和工作技巧较欠缺，偶尔不能完全理解工作内容或指示，须催促才能完成工作，18分
												</p><p>
												不太理解本职工作，盲目做事，专业知识非常欠缺，在上级详细的指示和指导下工作仍达不到标准，15分
												</p>
								  			</td>
								  			<td>
								  				30分
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
								  				沟通与协调能力
								  			</td>
								  			<td colspan="3">
								  				<p>
								  				善于主动沟通，为顺利完成任务，正确表达自己的看法、意见，说服他人与自己协作配合，乐于倾听，有效反馈，能有效化解矛盾，工作总结和汇报准确真实，20分
												</p><p>
												抓住要点表达工作中的意见，及时响应同事和合作者的沟通请求，使工作顺利开展，能够经常进行工作总结和汇报，16分
												</p><p>
												沟通能力一般，基本能抓住要点，表达尚清晰，偶尔需要多次沟通，间断进行工作总结和汇报，14分
												</p><p>
												沟通能力较差，不能抓住要点，语言欠清晰，但尚能表达意见，有时需要反复沟通，经常不进行工作总结和汇报，12分
												</p><p>
												沟通能力极差，含糊其辞，意见不明，不易明白和理解，反复沟通仍无效果，几乎不进行工作总结和汇报，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				执行能力
								  			</td>
								  			<td colspan="3">
								  				<p>
								  				工作方法合理，对本职工作和上级指示，决议和计划能按质按量完成，工作进度和效果都能达到最优化，30分
												</p><p>对本职工作和上级指示，决议能按计划执行，在时间控制上稍显不足，24分
												</p><p>能够按计划执行任务，对于临时安排的工作理解或反映较慢，在上级督促下能完成，21分
												</p><p>对上级指示、决议和计划执行力较差，常需提醒和督促，很多情况下都无法按质按量完成任务，18分
												</p><p>工作方法不合理，不能正确支配时间，对上级指示、决议和计划执行力很差，在上级提醒和督促下不能按质按量完成任务，15分
												</p>
								  			</td>
								  			<td>
								  				30分
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
								  				创新能力
								  			</td>
								  			<td colspan="3">
								  				<p>工作中能不断提出新的想法、建议、创造性的融合别人的想法和观点，注意规避风险，锐意创新，有创造的工作方法并采取新的行动完成工作目标，20分
												</p><p>工作中能够提出新的想法、建议和工作方法，并有创新意识，16分
												</p><p>能学习新的方法，但思维不够开阔，较少提出新想法、建议和工作方法，14分
												</p><p>工作存在应付现象，按部就班，循规蹈矩，很少提出新想法、建议和工作方法，12分
												</p><p>不思进取，墨守成规，不愿投入精力学习新的工作方法，缺少创新意识，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				<td colspan="5">合计</td>
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
								  	<td colspan="4">
								  		<table style="width: 100%;table-layout:fixed;">
								  			<thead>
								  			<tr class="tr-title"><td colspan="7" >工作态度</td></tr>
								  			<tr class="tr-title"><td>评估要素</td><td colspan="3">评价标准</td><td>分值</td><td>一级评分</td><td>二级评分</td></tr></thead>
								  			<tbody>
								  			<!-- 1 -->
								  			<tr>
								  			<td>
								  				学习态度
								  			</td>
								  			<td colspan="3">
								  				<p>愿意尝试有挑战性的工作任务，经常对自己提出新的要求和目标，愿意承担更大的责任，有清晰的个人发展计划和培训需求，以积极的态度接受工作的有关培训，经常安排个人时间以提高专业技能，20分
												</p><p>能够接受说服尝试有挑战性的工作任务，有较高的自我要求和目标，愿意承担责任，愿意接受与工作有关的培训，有时安排个人时间以提高专业技能，16分
												</p><p>被动接受有挑战性的工作任务，能够接受与工作有关的培训，较少安排个人时间以提高专业技能,14分
												</p><p>不敢接受有挑战性的工作任务和承担更大的责任，以无所谓的态度接受与工作有关的培训，很少安排个人时间以提高专业技能，12分
												</p><p>不愿意尝试有挑战性的工作任务，固步自封，不愿意承担更大的责任，没有个人发展计划，得过且过，以消极的态度接受与工作有关的培训，从不安排利用个人时间以提高专业技能，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				道德品质
								  			</td>
								  			<td colspan="3">
								  				<p>正直廉洁，诚实可靠，言行一致，乐于助人，谦虚谨慎，拥有积极向上的人生观，价值观，且有健康的心态，能坚持真理，修正错误，不歧视他人，注重个人形象,20分
												</p><p>较正直可靠，心态较好，尊重事实，很少有歧视他人的言行，不诋毁他人，16分
												</p><p>没有坚定的正直心，易受利益左右，心态需要调整，偶尔有歧视他人的言行,14分
												</p><p>在工作中会弄虚作假，不够诚信，缺少积极健康的心态，不能坚持原则，有不公正的行为，12分
												</p><p>在工作中常有弄虚作假现象，缺少诚信，自私自利，有不健康心态，不能公平待人，公正处事，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				主动性
								  			</td>
								  			<td colspan="3">
								  				<p>主动找任务，主动找方法，自动完善本职工作和流程，对职务范围以外的工作，如果对于公司来说是有利的，都自动自发去协助,20分
												</p><p>对职务范围内的事能主动按要求完成，16分
												</p><p>按上级要求做事，偶尔需要推动,14分
												</p><p>上级安排的工作有时不能完成，被动做事，经常需要推动，12分
												</p><p>工作很被动，经常抛问题给上级主管，按一下动一下，不按不动，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				责任感
								  			</td>
								  			<td colspan="3">
								  				<p>责任心强，极少有工作差错，能彻底完成任务，勇于承认错误，乐意接纳额外的任务和必要的加班，维护团队利益，共大局出发，以公司利益为重，积极主动承担相应的工作任务和责任,20分
												</p><p>有责任心，能顺利完成任务，乐意承担工作失误责任，不损害公司利益，16分
												</p><p>责任心尚可，能按期完成任务，不推诿责任和损害公司利益,14分
												</p><p>责任心不强，对自己的工作责任有推诿现象，偶尔因自己的工作给团队的利益造成损失，12分
												</p><p>责任心欠缺，不愿意承担责任，为完成自己的工作不顾公司的整体利益，敷衍，无责任心，粗心大意，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				团队精神
								  			</td>
								  			<td colspan="3">
								  				<p>善于与他人合作共事，相互支持，充分发挥各自的优势，保持良好的团队工作氛围，愿意与他人分享工作经验和方法，促进共同成长，推进团队目标达成,20分
												</p><p>能够与他人较顺畅的合作共事和相互支持，能保证团队任务的完成，16分
												</p><p>与他人的合作较难开展，协作支持的过程中，偶尔会有摩擦或不愉快的事情发生，但基本上能保证团队任务的完成,14分
												</p><p>团队合作精神不佳，常出现僵局，对团队任务的完成造成一定的影响，12分
												</p><p>似乎无法与人合作，协调性极差，缺乏团队精神，独断专行，10分
												</p>
								  			</td>
								  			<td>
								  				20分
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
								  				<td colspan="5">合计</td>
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
		
		if($('#BKHR').val() == ''){
			$('#BKHR').val('${userSession.displayName}'); 
			$('#BKHR').attr('readonly', 'readonly');
		}
		if($('#BKHRZW').val() == ''){
			$('#BKHRZW').val('${userSession.position.positionName}');
			$('#BKHRZW').attr('readonly', 'readonly');
		}
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
		
		if($('#BKHRZJ').val() == '0'){
			$('#BKHRZJ').val('${userSession.position.grade}'); 
			$('#BKHRZJ').attr('readonly', 'readonly');
		}
		
		if($('#BKHRSSBM').val() == ''){
			$('#BKHRSSBM').val('${userSession.orgEntity.orgEntityName}');
			$('#BKHRSSBM').attr('readonly', 'readonly');
		}
		
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
		
		$('#YJKHNLFS').attr('readonly', 'readonly');
		$('#YJKHXWFS').attr('readonly', 'readonly');
		
		$('#EJKHNLFS').attr('readonly', 'readonly');
		$('#EJKHXWFS').attr('readonly', 'readonly');
		$('#ZZDF').attr('readonly', 'readonly');
		
		//计算总分
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ == 5)}">
			var count = "${item.YJKHXWFS * 0.5 + item.YJKHNLFS * 0.5}";
			$('#ZZDF').val(count);
		</c:if>
		
		<c:if test="${(item.status=='考核确认' && item.BKHRZJ != 5)}">
			var count = "${item.YJKHXWFS*0.25 + item.YJKHNLFS*0.25 + item.EJKHXWFS*0.25 + item.EJKHNLFS*0.25}";
			$('#ZZDF').val(count);
		</c:if>
	});
	</script>
	
</body>

</html>
