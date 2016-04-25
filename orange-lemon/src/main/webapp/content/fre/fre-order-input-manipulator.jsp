<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/common/meta.jsp"%>
<title>订单信息</title>
<%@include file="/common/s2.jsp"%>
<style type="text/css">
#treeTable thead th .fa{
	cursor: pointer;
}

#treeTable tbody td .fa{
	cursor: pointer;
}

#treeTable #treeNodeInfo{
	font-size:13px;
	font-weight: bold;
	/**text-decoration: underline;**/
}

.ztree .level2 span{
}

.ztree .level3 span {
}

/******每种节点有三种类型的样式*********/
/**Order**/
.ztree li span.button.order-normal_ico_open{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; 
*vertical-align:middle
}
.ztree li span.button.order-normal_ico_close{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; 
*vertical-align:middle
}

.ztree li span.button.order-normal_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; 
*vertical-align:middle
}

/**Maintain**/
.ztree li span.button.maintain-normal_ico_open, 
.ztree li span.button.maintain-normal_ico_close{
margin-right:2px;
height:16px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/circle1.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.maintain-normal_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/circle1.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

/**Action**/
.ztree li span.button.action-1_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/star1.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}
.ztree li span.button.action-2_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/star2.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}
.ztree li span.button.action-3_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/star3.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.action-4_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/star4.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.action-5_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/star5.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.icon04_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/6.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.icon05_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/7.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

.ztree li span.button.icon06_ico_docu{
margin-right:2px; 
background: url(${ctx}/s2/assets/plugins/ztree/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; 
vertical-align:top; *vertical-align:middle
}

</style>
</head>
<body>
	<div class="page-content-wrapper">
		<div class="page-content" style="margin-left: -10px; margin-top: -10px;">
			<div class="row">
				  <div class="col-md-12">
			<section id="m-main-input" style="margin-left:10px;margin-right:0;min-width: 1000px;min-height: 700px;max-height: 1000px;">
				<article class="m-widget">
					<header style="height: 35px; margin-top: 0px; margin-bottom: 5px;padding-top:2px;border: solid #858585 1px;">
							<!-- 操作 -->
							<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-INPUT-DONE-ORDER-RECOVER">
								<c:if test="${item.orderStatus == '确认追回'}">
								<button id="doneOrderRecover" class="btn btn-sm green" style="float: left;margin-right: 10px;">
									确认追回
								</button>
								</c:if>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-INPUT-DATA-TEMPLATE-ADD">
							<button id="toCopyTemplate" class="btn btn-sm green" style="float: left;margin-right: 10px;">
									复制模板
								</button>
							</sec:authorize>
							
							<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-INPUT-DATA-TEMPLATE-COPY">
							<button id="toAddTemplate" class="btn btn-sm green" style="float: left;margin-right: 10px;">
									存为模板
								</button>
							</sec:authorize>
							
							<button onclick="closeWindow();" class="btn btn-sm red"
								style="float: right; margin-left: 10px;">
									关闭<i class="fa fa-power-off"></i>
							</button>
					</header>
					<div class="row">
						<div class="col-md-3">
						<c:if test="${item.orderStatus != null}">
								<form action="" class="form-horizontal">
									<table id="treeTable" class="table-input">
										<thead>
											<tr>
												<th colspan="2">
													<!-- 操作 -->
													<c:if test="${item.orderStatus == '审核中' or item.orderStatus == '已审核'}">
													<sec:authorize ifAnyGranted="ROLE_FRE-ORDER-INPUT-MANAGE-ACTION-MANIPULATOR" >
													<span style="font-size: 14px;">动作</span>
													<i class="fa fa-plus" onclick="addAction();" title="添加动作"></i>
													<i class="fa fa-times" onclick="removeAction();" title="删除动作"></i>
													<i class="fa fa-anchor" onclick="viewDelegate();" title="查看委托"></i>
													<i class="fa fa-twitter" onclick="toExecuteAction();" title="发送委托"></i>
													<i class="fa fa-check-square-o" onclick="doneExecuteAction();" title="完成动作"></i>
													<i class="fa fa-coffee" onclick="prepareDelegate();" title="预备委托"></i>
													
													<i class="fa fa-camera" onclick="browseDelegate();" title="预览委托"></i>
													</sec:authorize>
													</c:if>
													
													<i class="fa fa-text-height" onclick="toggleActionNode();" title="折叠/展开" style="margin-left:80px;"></i><span>&nbsp;</span>
												</th>
											</tr>
										</thead>
										<tbody>
										    <tr>
											  	<td colspan="2" style="background-color: white;">
													<ul id="treeNode" class="ztree">
													</ul>
												</td>
										    </tr>
										    <tr>
											  	<td colspan="2">
													<span id="treeNodeInfo"></span>
												</td>
										    </tr>
									    </tbody>
									</table>
								</form>
								</c:if>
						</div >
						<div class="col-md-9">
							<form id="freightOrderForm" method="post" action="${ctx}/fre/fre-order-save.do" enctype="multipart/form-data" onsubmit="return false" class="form-horizontal" >
								<c:if test="${item.id != null}">
									<input id="id" type="hidden" name="id" value="${item.id}">
								</c:if>
								<c:if test="${item.orderStatus != null}">
									<input id="orderStatus" type="hidden" name="orderStatus" value="${item.orderStatus}">
								</c:if>
								<table class="table-input" >
									<thead>
										<tr>
											<th colspan="6">订单信息</th>
										</tr>
									</thead>
									<tbody>
									    <tr>
										  	<td>
												<label class="td-label" for="orderNumber">业务编号</label>
											</td>
											<td>
												<input id="orderNumber" type="text" name="orderNumber" value="${item.orderNumber}" 
										size="40" minlength="2" maxlength="50" class="form-control" readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="salesman">业务员</label>
										  	</td>
											<td>
												<input id="salesman" type="text" name="salesman" value="${item.salesman}" 
												size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
												vClsId="1c1c16c3-744e-11e4-b790-a4db305e5cc5"
												vColumn="DISPLAY_NAME" vFilterId="b6476915-8722-11e4-84b5-a4db305e5cc5"
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="manipulator">操作员</label>
										  	</td>
											<td>
												<input id="manipulator" type="text" name="manipulator" value="${item.manipulator}" 
												size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
												vClsId="1c1c16c3-744e-11e4-b790-a4db305e5cc5"
												vColumn="DISPLAY_NAME" vFilterId="785abff7-8721-11e4-84b5-a4db305e5cc5"
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
										  </tr>
										  <tr>
											<td>
										  		<label class="td-label" for="orderScope">归属/类型</label>
										  	</td>
											<td>
												<input id="orderScope" type="text" name="orderScope" value="${item.orderScope}" 
												class="form-control required" 
												readonly="readonly">
											</td>
											<td>
												  
												<select id="firstType" name="firstType" class="form-control required" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">disabled="disabled"</c:if>>
													<option value=""></option>
													<option value="外贸" <c:if test="${item.firstType == '外贸'}">selected</c:if> >外贸&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
													<option value="内贸" <c:if test="${item.firstType == '内贸'}">selected</c:if> >内贸</option>
													<option value="其他" <c:if test="${item.firstType == '其他'}">selected</c:if> >其他</option>
												</select>
										  	</td>
											<td>
												 
												<select id="secondType" name="secondType" class="form-control required"
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">disabled="disabled"</c:if>>
												<option value="${item.secondType}">${item.secondType}</option>
												</select>
											</td>
										 	<td>
										 	  
										  		<select id="thirdType" name="thirdType" class="form-control"
										  		<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">disabled="disabled"</c:if>>
												<option value="${item.thirdType}">${item.thirdType}</option>
												</select>
										  	</td>
										  	<td>
										  		  
										  		<select id="fourthType" name="fourthType" class="form-control"
										  		<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">disabled="disabled"</c:if>>
										  		<option value="${item.fourthType}">${item.fourthType}</option>
												</select>
										  	</td>
									    </tr>
										 <tr>
										  	<td>
										  		<label class="td-label" for="delegatePart">委托单位</label>
										  	</td>
											<td>
												<input id="delegatePart" type="text" name="delegatePart" value="${item.delegatePart}" 
												size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
												vClsId="08abd6dd-6baa-11e4-b389-a4db305e5cc5"
												vColumn="PART_NAME" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="delegateNumber">委托编号</label>
										  	</td>
											<td>
												<input id="delegateNumber" type="text" name="delegateNumber" value="${item.delegateNumber}" 
												size="40" minlength="2" maxlength="50" class="form-control" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="delegateContact">委托单位联系人</label>
										  	</td>
											<td>
												<input id="delegateContact" type="text" name="delegateContact" value="${item.delegateContact}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
										</tr>
										 <tr>
										 	<td>
										  		<label class="td-label" for="cargoOwner">货主</label>
										  	</td>
											<td>
												<input id="cargoOwner" type="text" name="cargoOwner" value="${item.cargoOwner}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
										  	<td>
										  		<label class="td-label" for="cargoName">货品名称</label>
										  	</td>
											<td>
												<input id="cargoName" type="text" name="cargoName" value="${item.cargoName}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="commission">委托书</label>
										  	</td>
											<td>
												<c:if test="${item.commission != null && (item.orderStatus != '未提交' and item.orderStatus != '已追回' and item.orderStatus != '已退回')}">
												<a href="fre-order-download-commission.do?freightOrderId=${item.id}" target="_blank">
												${item.delegateNumber == null ? '无委托编号' : item.delegateNumber}
												</a>
												</c:if>
												<c:if test="${item.commission == null or item.orderStatus == '未提交' or item.orderStatus == '已追回' or item.orderStatus == '已退回'}">
												<input id="muiltFile" type="file" name="muiltFile" value="${item.commission}" 
													size="40" minlength="2" maxlength="50" class="form-control input-xsmall <c:if test="${item.commission == null}">required</c:if>" >
												</c:if>
											</td>
										</tr>
										<tr>
										  	<td>
										  		<label class="td-label" for="cargoAmount">件数及包装</label>
										  	</td>
											<td>
												<input id="cargoAmount" type="text" name="cargoAmount" value="${item.cargoAmount}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="cargoWeight">货品重量(KGS)</label>
										  	</td>
											<td>
												<input id="cargoWeight" type="text" name="cargoWeight" value="${item.cargoWeight}" 
												size="40" minlength="2" maxlength="50" class="form-control required number" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
											<td>
										  		<label class="td-label" for="cargoCapacity">货品体积(CEM)</label>
										  	</td>
											<td>
												<input id="cargoCapacity" type="text" name="cargoCapacity" value="${item.cargoCapacity}" 
												size="40" minlength="2" maxlength="50" class="form-control required number" 
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>
											</td>
										</tr>
										<tr>
										  	<td>
										  		<label class="td-label" for="descn">备注</label>
										  	</td>
											<td colspan="5">
												<textarea id="descn" name="descn" maxlength="200" class="form-control required" style="min-height: 100px;"
												<c:if test="${item.orderStatus != null and item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">readonly="readonly"</c:if>>${item.descn}</textarea>
											</td>
										</tr>
										<c:if test="${item.firstType == '外贸'}">
										<tr><td colspan="6" style="background-color:  white;">&nbsp;</td></tr>
										<tr>
										  	<td>
										  		<label class="td-label" for="TDH">提单号</label>
										  	</td>
											<td>
												${KeyData.TDH}
											</td>
											<td>
										  		<label class="td-label" for="QYG">启运港</label>
										  	</td>
											<td>
												${KeyData.QYG}
											</td>
											<td>
										  		<label class="td-label" for="MDG">目的港</label>
										  	</td>
											<td>
												${KeyData.MDG}
											</td>
										</tr>
										</c:if>
									</tbody>
								</table>
							</form>
							<!-- 箱封信息 -->
							<c:if test="${freightOrderBoxs != null && freightOrderBoxs.size() != 0}">
							<span><strong style="font-size: 16px;">箱封信息</strong></span><br>
							<form style="max-height: 200px;overflow-y: scroll;">
							<table class="m-table table-striped table-bordered table-hover">
								<thead>
					                <tr>
					                	<th>序号</th>
					                    <th>箱号</th>
					                    <th>箱型</th>
					                    <th>箱属</th>
					                    <th>箱况</th>
					                    <th>集装箱来源</th>
					                    <th>封号</th>
					                    <th>状态</th>
					                    <th>提箱地</th>
					                    <th>起始地</th>
					                    <th>终止地</th>
					                </tr>
					            </thead>
								<tbody>
								<c:forEach items="${freightOrderBoxs}" var="item" varStatus="varStatus">
					                <tr>
					                	<td>${varStatus.index + 1}</td>
					                    <td>${item.freightBox.boxNumber}</td>
					                    <td>${item.freightBoxRequire.boxType}</td>
					                    <td>${item.freightBoxRequire.boxBelong}</td>
					                    <td>${item.freightBoxRequire.boxCondition}</td>
					                    <td>${item.freightBoxRequire.boxSource}</td>
					                    <td>${item.freightSeal.sealNumber}</td>
					                    <td>${item.status}</td>
					                    <td>${item.location}</td>
					                    <td>${item.freightBoxRequire.beginStation}</td>
					                    <td>${item.freightBoxRequire.arriveStation}</td>
					                </tr>
								</c:forEach>
					            </tbody>
							</table>
							</form>
							</c:if>
						</div>
					</div>
				</article>
			</section>
		</div>
	</div>
</div>
	</div>
	<div id="maintainModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">新增操作范围</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="maintainForm" action="" method="post" class="m-form-blank" onsubmit="return false;">
					<input type="hidden" id="freightOrderId"  value="">
					<input type="hidden" id="parentMaintainId"  value="">
					<input type="hidden" id="freightMaintainTypeIds" value="">
					<input type="hidden" id="displayIndex" name="displayIndex" value="" class="form-control required number" >
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:200px;">
									<label class="td-label" for="maintainName">操作范围</label>
								</td>
								<td>
									<div class="input-icon listPicker right">
									  <i class="fa fa-ellipsis-h maintainTypeModal-add-on" urlAttr='${ctx}/fre/fre-maintain-type-all.do' style="cursor: pointer;"></i>
									  <input type="text" id="freightMaintainTypeName" class="form-control">
								    </div>
								</td>
							</tr>
							<tr>
								<td>
									<label class="td-label" for="descn">基本说明</label>
								</td>
								<td>
									<textarea id="descn" name="descn" 
										style="min-height: 150px;" maxlength="256" class="form-control required">无</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#maintainForm').submit();" >确认</button>
			</div>
		</div>
	</div>
	<!-- 查看或者修改操作范围说明，此说明应当是业务告知给操作员的信息 -->
	<div id="maintainInfoModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">操作范围</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="maintainInfoForm" action="" method="post" class="m-form-blank" onsubmit="return false;">
					<input type="hidden" id="freightMaintainId"  name="freightMaintainId" value="">
					<table class="table-input">
						<tbody>
							<tr>
							<td style="width:200px;">
									<label class="td-label" for="maintainName">操作范围</label>
								</td>
								<td>
									<input type="text" id="freightMaintainTypeName" class="form-control" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td>
									<label class="td-label" for="descn">基本说明</label>
								</td>
								<td>
									<textarea id="descn" name="descn" style="min-height: 150px;" maxlength="256" class="form-control required"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" id="maintainInfoSubmitBtn" onclick="if(true) $('#maintainInfoForm').submit();">确认</button>
			</div>
		</div>
	</div>

	<div id="actionModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">新增动作</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="actionForm" action="" method="post" class="m-form-blank" onsubmit="return false;">
					<input type="hidden" id="freightActionTypeIds" value="">
					<input type="hidden" id="freightMaintainId" value="">
					<input type="hidden" id="displayIndex" name="displayIndex" value="" class="form-control required number" >
					<table class="table-input">
						<tbody>
							<tr>
								<td style="width:200px;">
									<label class="td-label" for="freightActionTypeName">动作类型</label>
								</td>
								<td colspan="3">
									<div class="input-icon listPicker right">
									  <i class="fa fa-ellipsis-h actionTypeModal-add-on" urlAttr='${ctx}/fre/fre-action-type-all.do' style="cursor: pointer;"></i>
									  <input type="text" id="freightActionTypeName" class="form-control">
								    </div>
								</td>
							</tr>
							<tr>
								<td>
									<label class="td-label" for="descn">基本说明</label>
								</td>
								<td>
									<textarea id="descn" name="descn" 
										style="min-height: 100px;" class="form-control required">无</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-sm red" onclick="if(true) $('#actionForm').submit();">确认</button>
			</div>
		</div>
	</div>
	
	<!-- actionField填报界面 -->
	<div id="actionFieldModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">动作信息</h4>
			</div>
			<div class="modal-body">
				<!-- 填报内容 -->
				<article class="m-widget">
				<form id="actionFieldForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
				<!-- 箱封详情 -->
				<article class="m-widget" style="max-height: 450px;overflow-y: scroll;display: none; " >
				<form id="orderBoxSelectForm" method="post" class="m-form-blank"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button id="actionFieldSubmitBtn" type="button" class="btn btn-sm red" onclick="if(true) $('#actionFieldForm').submit();">保存</button>
				<button id="toPrimeBoxBtn" type="button" class="btn btn-sm green" style="display:none;">提交箱封</button>
				<button id="revisePrimeBoxBtn" type="button" class="btn btn-sm green" style="display:none;">修改箱封</button>
			</div>
		</div>
	</div>
	
	<!-- 保存数据模板 -->
	<div id="addDataTemplateModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">模板信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="addDataTemplateForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button id="addDataTemplateSubmitBtn" type="button" class="btn btn-sm red" onclick="if(true) $('#addDataTemplateForm').submit();">保存</button>
			</div>
		</div>
	</div>
	
	<!-- 复制数据模板 -->
	<div id="copyDataTemplateModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">选择模板</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget" style="min-height: 200px;">
				<form id="copyDataTemplateForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button id="copyDataTemplateSubmitBtn" type="button" class="btn btn-sm red" onclick="if(true) $('#copyDataTemplateForm').submit();">保存</button>
			</div>
		</div>
	</div>
	
	<!-- 箱需-->
	<div class="modal fade" id="requireModal" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">订单箱需</h4>
			</div>
			<!-- 添加箱需-->
			<div class="modal-body">
				<article class="m-widget">
				<form id="requireToAddForm" method="post" class="m-form-blank"></form>
				</article>
				<article class="m-widget">
					<form id="requireBtnForm" name="requireBtnForm" method="post" action="" class="form-inline" onsubmit="return false;">
						<!-- 客户服务 -->
						<sec:authorize ifAnyGranted="ROLE_CY-CUSTOMER-SERVICE-EMP">
						<button type="button" class="btn btn-sm red" onclick="$('#requireToAddForm').submit()">添加</button>
						<button type="button" class="btn btn-sm red" onclick="reviseRequire();">修改</button>
						<button type="button" class="btn btn-sm red" onclick="deleteRequire();">删除</button>
						</sec:authorize>
						<!-- 操作 -->
						<sec:authorize ifAnyGranted="ROLE_CY-MANIPULATE-EMP">
						<button type="button" class="btn btn-sm green" onclick="releaseRequire();">放箱</button>
						</sec:authorize>
					</form>
				</article>
				<!-- 已有箱需 -->
				<article class="m-widget">
				<form id="requireHasAddForm" action="" method="post" class="m-form-blank" style="min-height: 200px;overflow-y: scroll;"></form>
				</article>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	
	<%@include file="/common/footer.jsp" %>
	<script type="text/javascript">
		//树对象
		var zTreeObject;
		//被选中的Tid
		var selectTreeTId = '';
		var setting = {
			view: {
				showLine: false
			},
			edit:{
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			async: {
				enable: true,
				url: "${ctx}/fre/fre-order-nodes.do?freightOrderId=${item.id}"
			},
			//点击事件
			callback: {
				onClick: function(event, treeId, treeNode) {
					showInfo(treeNode);
				},
				onDblClick: function(event, treeId, treeNode) {
					showInfo(treeNode);
                    var node = zTreeObject.getNodeByTId(treeNode.tId);
                    //动作且有填报界面
                    if(node.nodeType == 'action' && node.prime == 'T'){
                    	//业务能填写时，则所有都非必须填写，操作填写时，所有都必须填写；
                    	//在未提交，已退回，已追回状态时，业务有所有权限（除动作执行和委托），填与不填写都可；其他状态下只能查看；在已审核，审核中状态时，操作对动作有所有权限，其他时候（除确认追回时）无任何权限；
                    	//操作
                    	<c:if test="${item.orderStatus == '审核中' or item.orderStatus == '已审核'}">
							if(node.status == '已执行' || node.status == '已发送'){
								addPrime(node.id, true, false);
								//toPrimeAction(node.id, false, false);
	                    	}else{
	                    		addPrime(node.id, false, false);
	                    		//toPrimeAction(node.id, false, false);
	                    	}
						</c:if>
						<c:if test="${item.orderStatus != '审核中' and item.orderStatus != '已审核'}">
							addPrime(node.id, true, true);
						</c:if>
                    //操作范围,查看或者修改说明
                    }else if(node.nodeType == 'maintain'){
                    	<c:if test="${item.orderStatus == '未提交' or item.orderStatus == '已退回' or item.orderStatus == '已追回'}">
                    		addFreightMaintainInfo(node.id, false);
                    	</c:if>
                    	<c:if test="${item.orderStatus != '未提交' and item.orderStatus != '已退回' and item.orderStatus != '已追回'}">
                		addFreightMaintainInfo(node.id, true);
                		</c:if>
                	//双击订单节点，隐藏所有动作节点
                    }else if(node.nodeType == 'order'){
                    	var nodes = zTreeObject.getNodesByParam('nodeType', 'maintain', node);
                    	for (var i=0,l=nodes.length; i<l; i++) {
    						var subNodes = zTreeObject.getNodesByParam('nodeType', 'action', nodes[i]);
    						for(var j=0, len=subNodes.length; j<len; j++){
    						} 
    					}
                    }
				},
				onCollapse:function(treeId, treeNode){//折叠
				},
				onExpand:function(treeId, treeNode){//展开
				},
				beforeDrag: function(treeId, treeNodes){
					for (var i=0,l=treeNodes.length; i<l; i++) {
						//订单节点不能拖拽
						if (treeNodes[i].nodeType === 'order') {
							return false;
						}
					}
					return true;
				},
				beforeDrop: function(treeId, treeNodes, targetNode, moveType){
					//订单状态为未提交或业务退回才可以拖动
					var orderStatus = zTreeObject.getNodeByTId('treeNode_1').name.split('_')[1]
					if(orderStatus != '未提交' && orderStatus != '已退回' && orderStatus != '已追回' && orderStatus != '审核中'){
						return false;
					}
					
					if(targetNode.nodeType == 'order'){//目标节点类型为order
						return false;
					}else if(targetNode.nodeType == 'maintain'){//目标节点类型为maintain
						var parentMaintainId = targetNode.parentMaintainId;
						for (var i=0,l=treeNodes.length; i<l; i++) {
							if (treeNodes[i].nodeType == 'maintain' && treeNodes[i].parentMaintainId == parentMaintainId) {
								return true;
							}else{
								return false;
							}
						}
					}else{ //目标节点类型为action
						var parentMaintainId = targetNode.parentMaintainId;
						for (var i=0,l=treeNodes.length; i<l; i++) {
							if (treeNodes[i].nodeType == 'action' && treeNodes[i].parentMaintainId == parentMaintainId) {
								return true;
							}else{
								return false;
							}
						}
					}
				},
				onDrop: function(event, treeId, treeNodes, targetNode, moveType, isCopy){
					for (var i=0,l=treeNodes.length; i<l; i++) {
						var data = 'sourceNodeId=' + treeNodes[i].id + '&targetNodeId=' + targetNode.id + '&nodeType=' + treeNodes[i].nodeType;
						$.post('${ctx}/fre/fre-order-nodes-drop.do', data, function(data){
							init();
						});
					}
				}
			}
		};

		var zNodes =[];
		
		<c:if test="${item.id != null}">
			$(function(){
				init();
			});
		</c:if>
		
		function init(){
			zTreeObject = $.fn.zTree.init($("#treeNode"), setting, zNodes);
			$('#treeNodeInfo').text('');
			var t = $.fn.zTree.getZTreeObj("treeNode");
			t.setting.edit.drag.isCopy = false;
			t.setting.edit.drag.isMove = true;
			t.setting.edit.drag.prev = true;
			t.setting.edit.drag.inner = false;
			t.setting.edit.drag.next = true;
		}
		
		//折叠动作节点
		var expandCount =0;
		function toggleActionNode(){
			var orderNode = zTreeObject.getNodeByTId('treeNode_1');
			var maintainNodes = zTreeObject.getNodesByParam('nodeType', 'maintain', orderNode);
           	for (var i=0,l=maintainNodes.length; i<l; i++) {
           		if(maintainNodes[i].priority == '2'){
           			if(expandCount%2 == 0){
           				zTreeObject.expandNode(maintainNodes[i], false, true, true);
           			}else{
           				zTreeObject.expandNode(maintainNodes[i], true, true, true);
           			}
           		}
           	}
           	expandCount ++;
		}
		
		function showInfo(treeNode){
            selectTreeTId = treeNode.tId;
            if(treeNode.nodeType == 'action'){
            	$('#treeNodeInfo').text(treeNode.name);
            	$('#treeNodeInfo').append(' <br> 是否对内部:' + (treeNode.internal == 'T' ? '是' : '否'));
            	$('#treeNodeInfo').append(' <br> 是否有委托:' + (treeNode.delegate == 'T' ? '是' : '否'));
            	$('#treeNodeInfo').append(' <br> 是否需填报:' + (treeNode.prime == 'T' ? '是' : '否'));
            	$('#treeNodeInfo').append(' <br> 开始时间:' + $.fullCalendar.formatDate(new Date(treeNode.createTime),'yyyy-MM-dd HH:mm:ss'));
            	if(treeNode.status == '已执行'){
            		$('#treeNodeInfo').append(' <br> 完成时间:' + $.fullCalendar.formatDate(new Date(treeNode.modifyTime),'yyyy-MM-dd HH:mm:ss'));
            	}else if(treeNode.status == '已暂停'){
            		$('#treeNodeInfo').append(' <br> 暂停时间:' + $.fullCalendar.formatDate(new Date(treeNode.modifyTime),'yyyy-MM-dd HH:mm:ss'));
            	}
            }else if(treeNode.nodeType == 'maintain'){
            	$('#treeNodeInfo').text(treeNode.name + '_备注信息:');
            	var text = treeNode.descn.split('\n');
            	$.each(text, function(i, item){
            		$('#treeNodeInfo').append('<br>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:12px;font-weight:normal;color:red;font-family:宋体;">'+ item + '</span>');
            	});
            	
            }else{
            	$('#treeNodeInfo').text(treeNode.name);
            }
		}
		/**各种类型的判断
		<c:if test="${item.orderStatus == null or item.orderStatus == '未提交' or item.orderStatus == '已退回' or item.orderStatus == '已追回'}">
		$(document).delegate('#freightOrderForm #firstType', 'change', function(){
			var html = '<option value="' + $('#freightOrderForm #secondType').val() + '"></option>';
			var firstType = $('#freightOrderForm #firstType').val();
			if(firstType == '外贸'){
				html += '<option value="出口">出口</option><option value="进口">进口</option>';
			}else if(firstType == '内贸'){
				html += '<option value="发运">发运</option><option value="到达">到达</option>';
			}if(firstType == '其他'){
				html += '<option value="仓储">仓储</option><option value="配送">配送</option>';
				html += '<option value="关检代办">关检代办</option><option value="集装箱买卖">集装箱买卖</option>';
				html += '<option value="熏蒸">熏蒸</option><option value="场站操作">场站操作</option>';
				html += '<option value="清关送货">关送货</option><option value="船公司送单">船公司送单</option>';
				
				$('#freightOrderForm #thirdType').val('');
				$('#freightOrderForm #thirdType').removeClass('required');
				$('#freightOrderForm #fourthType').val('');
				$('#freightOrderForm #fourthType').removeClass('required');
			}
			if(html != ''){
				var secondType = $('#freightOrderForm #secondType').val();
				$('#freightOrderForm #secondType').html(html);
				$('#freightOrderForm #secondType').addClass('required');
				$('#freightOrderForm #secondType').val(secondType);
			}
		});
		
		$(document).delegate('#freightOrderForm #secondType', 'change', function(){
			var html = '<option value="'+$('#freightOrderForm #thirdType').val()+'"></option>';
			var secondType = $('#freightOrderForm #secondType').val();
			var firstType = $('#freightOrderForm #firstType').val();
			if(secondType == '出口'){
				html += '<option value="整箱">整箱</option><option value="拼箱">拼箱</option>';
				html += '<option value="散杂货">散杂货</option><option value="空运">空运</option>';
				html += '<option value="快递">快递</option><option value="铁路整车">铁路整车</option>';
				html += '<option value="滚装">滚装</option><option value="汽运">汽运</option>';
			}else if(secondType == '进口'){
				html += '<option value="整箱">整箱</option><option value="拼箱">拼箱</option>';
				html += '<option value="散杂货">散杂货</option><option value="空运">空运</option>';
				html += '<option value="快递">快递</option><option value="铁路整车">铁路整车</option>';
				html += '<option value="滚装">滚装</option><option value="汽运">汽运</option>';
			}else if(secondType == '发运'){
				html += '<option value="整箱">整箱</option><option value="拼箱">拼箱</option>';
				html += '<option value="散杂货">散杂货</option><option value="空运">空运</option>';
				html += '<option value="快递">快递</option><option value="铁路整车">铁路整车</option>';
			}else if(secondType == '到达'){
				html += '<option value="整箱">整箱</option><option value="拼箱">拼箱</option>';
				html += '<option value="散杂货">散杂货</option><option value="空运">空运</option>';
				html += '<option value="铁路整车">铁路整车</option>';
			}
			if(firstType !== '其他'){
				if(html != ''){
					var thirdType = $('#freightOrderForm #thirdType').val();
					$('#freightOrderForm #thirdType').html(html);
					$('#freightOrderForm #thirdType').addClass('required');
					$('#freightOrderForm #thirdType').val(thirdType);
				}
			}
		});
		
		$(document).delegate('#freightOrderForm #thirdType', 'change', function(){
			var html = '<option value="'+$('#freightOrderForm #fourthType').val()+'"></option>';
			var thirdType = $('#freightOrderForm #thirdType').val();
			var secondType = $('#freightOrderForm #secondType').val();
			if(secondType == '出口'){
				if(thirdType == '整箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '拼箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '散杂货'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '空运'){
					html += '<option value="国际空运">国际空运</option>';
				}else if(thirdType == '快递'){
					html += '<option value="国际快递">国际快递</option>';
				}else if(thirdType == '铁路整车'){
					html += '<option value="国铁联运">国铁联运</option>';
				}else if(thirdType == '滚装'){
					html += '<option value="陆海联运">陆海联运</option><option value="铁海联运">铁海联运</option>';
				}else if(thirdType == '汽运'){
					html += '<option value="国际公路">国际公路';
				}
				//单独的运输方式
				html += '<option value="陆运">陆运</option><option value="铁运">铁运</option><option value="海运">海运</option>';
				html += '<option value="空运">空运</option><option value="快递">快递</option>';
				if(html != ''){
					var fourthType = $('#freightOrderForm #fourthType').val();
					$('#freightOrderForm #fourthType').html(html);
					$('#freightOrderForm #fourthType').addClass('required');
					$('#freightOrderForm #fourthType').val(fourthType);
				}
			}else if(secondType == '进口'){
				if(thirdType == '整箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '拼箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '散杂货'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '空运'){
					html += '<option value="国际空运">国际空运</option>';
				}else if(thirdType == '快递'){
					html += '<option value="国际快递">国际快递</option>';
				}else if(thirdType == '铁路整车'){
					html += '<option value="国铁联运">国铁联运</option>';
				}else if(thirdType == '滚装'){
					html += '<option value="陆海联运">陆海联运</option><option value="铁海联运">铁海联运</option>';
				}else if(thirdType == '汽运'){
					html += '<option value="国际公路">国际公路';
				}
				//单独的运输方式
				html += '<option value="陆运">陆运</option><option value="铁运">铁运</option><option value="海运">海运</option>';
				html += '<option value="空运">空运</option><option value="快递">快递</option>';
				if(html != ''){
					var fourthType = $('#freightOrderForm #fourthType').val();
					$('#freightOrderForm #fourthType').html(html);
					$('#freightOrderForm #fourthType').addClass('required');
					$('#freightOrderForm #fourthType').val(fourthType);
				}
			}else if(secondType == '发运'){
				if(thirdType == '整箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '拼箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '散杂货'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '空运'){
					html += '<option value="国际空运">国际空运</option>';
				}else if(thirdType == '快递'){
					html += '<option value="国际快递">国际快递</option>';
				}else if(thirdType == '铁路整车'){
					html += '<option value="国铁联运">国铁联运</option>';
				}else if(thirdType == '滚装'){
					html += '<option value="陆海联运">陆海联运</option><option value="铁海联运">铁海联运</option>';
				}else if(thirdType == '汽运'){
					html += '<option value="国际公路">国际公路';
				}
				//单独的运输方式
				html += '<option value="陆运">陆运</option><option value="铁运">铁运</option><option value="海运">海运</option>';
				html += '<option value="空运">空运</option><option value="快递">快递</option>';
				if(html != ''){
					var fourthType = $('#freightOrderForm #fourthType').val();
					$('#freightOrderForm #fourthType').html(html);
					$('#freightOrderForm #fourthType').addClass('required');
					$('#freightOrderForm #fourthType').val(fourthType);
				}
			}else if(secondType == '到达'){
				if(thirdType == '整箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '拼箱'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '散杂货'){
					html += '<option value="铁海联运">铁海联运</option><option value="江海联运">江海联运</option>';
					html += '<option value="陆海联运">陆海联运</option><option value="铁江海联运">铁江海联运</option>';
				}else if(thirdType == '空运'){
					html += '<option value="国际空运">国际空运</option>';
				}else if(thirdType == '快递'){
					html += '<option value="国际快递">国际快递</option>';
				}else if(thirdType == '铁路整车'){
					html += '<option value="国铁联运">国铁联运</option>';
				}else if(thirdType == '滚装'){
					html += '<option value="陆海联运">陆海联运</option><option value="铁海联运">铁海联运</option>';
				}else if(thirdType == '汽运'){
					html += '<option value="国际公路">国际公路';
				}
				
				//单独的运输方式
				html += '<option value="陆运">陆运</option><option value="铁运">铁运</option><option value="海运">海运</option>';
				html += '<option value="空运">空运</option><option value="快递">快递</option>';
				if(html != ''){
					var fourthType = $('#freightOrderForm #fourthType').val();
					$('#freightOrderForm #fourthType').html(html);
					$('#freightOrderForm #fourthType').addClass('required');
					$('#freightOrderForm #fourthType').val(fourthType);
				}
			}
		});
		</c:if>
		**/
		//生成或直接查看委托书
		function viewDelegate(){
			var node = zTreeObject.getNodeByTId(selectTreeTId);
			if(node.nodeType != 'action'){
				alert('请选择动作生成委托书!');
				return false;
			}else if(node.delegate != 'T'){
				alert('该动作没有委托书!');
				return false;
			}else {
				window.open('fre-delegate-to-view-by-action.do?freightActionId=' + node.id);//下载
			}
		}
		//预览委托
		function browseDelegate(){
			var node = zTreeObject.getNodeByTId(selectTreeTId);
			if(node.nodeType != 'action'){
				alert('请选择动作生成委托书!');
				return false;
			}else if(node.delegate != 'T'){
				alert('该动作没有委托书!');
				return false;
			}else {
				bootbox.animate(false);
	    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
				$.post('fre-delegate-to-browse-by-action.do?freightActionId=' + node.id, function(data){
					if(data != null){
						window.open('/VC/convert?fileName=' + data.delegateFile);
						box.modal('hide');
					}
				});
			}
		}
		
		//预委托，让其他部门补充数据
		function prepareDelegate(){
			var node = zTreeObject.getNodeByTId(selectTreeTId);
			if(node.nodeType != 'action'){
				alert('请选择动作节点！');
				return false;
			}else if(node.delegate != 'T'){
				alert('该动作节点没有委托书！');
				return false;
			}else if(node.internal != 'T'){
				alert('委托不是对内部部门，不能预备委托！');
				return false;
			}else if(node.status != '未执行'){
				alert('只有未执行的动作才能预备委托！');
				return false;
			}else if(node.prime != 'T'){
				alert('该动作没有填报内容，不能预备委托！');
				return false;
			}else{
				$.post('fre-action-to-prepare-action.do?freightActionId=' + node.id, function(data){
					if(data == 'success'){
						alert('预备委托成功！');
						init();
					}else{
						alert('预备委托失败！');
					}
				});
			}
		}
		
		//过滤数据
		function filterList(){
			$('#partToAddForm tr').each(function(i, item){
				if($(item).children('td').length > 0){
					$(item).show();
				}
			});
			$('#partToAddForm tr').each(function(i, item){
				if($(item).text().indexOf($('#partSearchForm #partName').val()) < 0){
					if($(item).children('td').length > 0){
						$(item).hide();
					}
				}
			});
		}
		
		//订单数据校验
		$(function() {
			$("#freightOrderForm").validate({
		        submitHandler: function(form) {
		    		bootbox.animate(false);
		    		var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
		    		form.submit();
		        },
		        errorClass: 'validate-error'
			});
		});
		
		//操作数据校验
		$(function() {
			$("#maintainForm").validate({
		        submitHandler: function(form) {
		            $('#maintainModal').modal('hide');
		    		bootbox.animate(false);
		    		var box = bootbox
		    				.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
					var freightOrderId = $('#maintainModal #freightOrderId').val();
					var parentMaintainId = $('#maintainModal #parentMaintainId').val();
					var freightMaintainTypeIds = $('#maintainModal #freightMaintainTypeIds').val();
					
					if(freightOrderId == undefined || freightOrderId == ''){
		    			alert('添加节点之前,请先保存业务信息!');
		    			box.modal('hide');
		    			return false;
					}
					var data = toJsonString('maintainForm');
					var url = 'fre-maintain-save-async.do?freightOrderId=' + freightOrderId + '&freightMaintainTypeIds=' + freightMaintainTypeIds;
					if(parentMaintainId != ''){
						url += '&parentMaintainId=' + parentMaintainId;
					}
		    		$.ajax({
		    			type: 'POST',
		    			data: data,
		    			url: url,
		    			contentType: 'application/json',
		    			success:function(data){
		    				init();
		    				box.modal('hide');
		    			}
		    		});
		        },
		        errorClass: 'validate-error'
			});
		});
		
		//更新操作说明
		$(function() {
			$("#maintainInfoForm").validate({
		        submitHandler: function(form) {
		            $('#maintainInfoModal').modal('hide');
		    		bootbox.animate(false);
		    		var box = bootbox
		    				.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
					$.post('fre-maintain-modify-descn.do', $('#maintainInfoForm').serialize(), function(){
						box.modal('hide');
						init();
					});
		        },
		        errorClass: 'validate-error'
			});
		});
		
		//保存添加动作
		$(function() {
			$("#actionForm").validate({
		        submitHandler: function(form) {
		            $('#actionModal').modal('hide');
		    		bootbox.animate(false);
		    		var box = bootbox
		    				.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
					var freightMaintainId = $('#actionForm #freightMaintainId').val();
		    		var freightActionTypeIds = $('#actionForm #freightActionTypeIds').val();
		    		
					if(freightMaintainId == undefined || freightMaintainId == ''){
		    			alert('请选择正确的操作!');
		    			box.modal('hide');
		    			return false;
					}
					var data = toJsonString('actionForm');
					var url = 'fre-action-done-add-action.do?freightMaintainId=' + freightMaintainId + '&freightActionTypeIds=' + freightActionTypeIds;
					$.ajax({
		    			type: 'POST',
		    			data: data,
		    			url: url,
		    			contentType: 'application/json',
		    			success:function(data){
		    				init();
		    				box.modal('hide');
		    			}
		    		});
		        },
		        errorClass: 'validate-error'
			});
		});
		
		//动作信息保存
		$(function() {
			$("#actionFieldForm").validate({
		        submitHandler: function(form) {
					//此处数据不需要使用json
					var data = $('#actionFieldForm').serialize();
					var url = 'fre-action-done-prime-action.do';
		    		$.post(url, data, function(data){
		    			if(data == 'success'){
		    				alert('保存成功！');
		    				$('#actionFieldModal').modal('hide');
		    			}else{
		    				alert('保存失败！');
		    			}
		    		});
		        },
		        errorClass: 'validate-error'
			});
		});
		//关闭窗口
		function closeWindow() {
			var opener = window.opener;
			if(opener == undefined){
				window.close();
			}else{
				window.opener.location.href = window.opener.location.href;
				window.close();
			}
		}
		//拼接成json数据类型
		function toJsonString(formId){
			var fields = $('#' + formId).serializeArray();
    		var data = '{';
    		$.each(fields, function(i, item){
   				if(i == 0){
       				data += '"' + item.name + '":"' + item.value + '"';
       			}else{
       				data += ',"' + item.name + '":"' + item.value + '"';
       			}
    		});
    		data += '}';
    		
    		return data;
		}
		
		//添加操作
		function addMaintain(){
			//订单数据没有保存
			if($('#freightOrderForm #id').length == 0){
				alert('请首先保存订单数据!');
				return false;
			}else{
				$('#maintainForm #freightOrderId').val($('#freightOrderForm #id').val());
				$('#maintainForm #displayIndex').val(zTreeObject.getNodes()[0].children.length + 1);
				if(selectTreeTId != ''){
					var node = zTreeObject.getNodeByTId(selectTreeTId);
					var nodeType = node.nodeType;
					if(nodeType == 'action'){
						alert('不能在动作节点下添加!');
						return false;
					}else if(nodeType == 'maintain'){
						//订单之下是一级操作，操作之下是二级操作
						var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr');
						if(urlAttr.indexOf('?') < 0){
							urlAttr += '?priority=2';
						}else{
							urlAttr = urlAttr.substring(0, urlAttr.length - 1) + '2'
						}
						
						var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr', urlAttr);
						
						var children = node.children;
						var count = 1;
						if(children != undefined){//如果为undifined说明还没有子节点
							for(var i=0, len = children.length; i<len; i++){
								if(children[i].nodeType == 'maintain'){
									count++;
								}
							}
						}
						
						$('#maintainForm #displayIndex').val(count);
						$('#maintainForm #parentMaintainId').val(node.id);
					}else if(nodeType == 'order'){
						//订单之下是一级操作，操作之下是二级操作
						var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr');
						if(urlAttr.indexOf('?') < 0){
							urlAttr += '?priority=1';
						}else{
							urlAttr = urlAttr.substring(0, urlAttr.length - 1) + '1'
						}
						var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr', urlAttr);
						
						$('#maintainForm #parentMaintainId').val('');//设置parentMaintainId为空
					}
				}else{
					//如果未选中任何节点,应该就直接添加至订单节点,则设置parentMaintainId为空
					//订单之下是一级操作，操作之下是二级操作
					var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr');
					if(urlAttr.indexOf('?') < 0){
						urlAttr += '?priority=1';
					}else{
						urlAttr = urlAttr.substring(0, urlAttr.length - 1) + '1'
					}
					var urlAttr = $('.maintainTypeModal-add-on').attr('urlAttr', urlAttr);
					$('#maintainForm #parentMaintainId').val('');
				}
			}
			
			var margin = (window.screen.availWidth - 800)/2;
			$('#maintainModal').css("margin-left", margin);
			$('#maintainModal').css("width","800px");
			$('#maintainModal').modal();
		}
		
		//删除操作
		function removeMaintain(){
			if(selectTreeTId == ''){
				alert('请首先选择一个操作!');
			}else{
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType == 'order' || nodeType == 'action'){
					alert('请首先选择一个操作!');
				}else{
					if(window.confirm('确定要删除吗?')){
						$.post('fre-maintain-done-remove-maintain.do?freightMaintainId=' + node.id, function(data){
							if(data != 'success'){
								alert('删除失败，请确认是否包含非未执行的动作！');
							}
							init();
						});
					}
				}
			}
		}
		//查看或者修改操作范围信息
		function addFreightMaintainInfo(freightMaintainId, readonly){
			$.post('fre-maintain-by-freightmaintainid.do?freightMaintainId=' + freightMaintainId, function(data){
				$('#maintainInfoForm #descn').val(data.descn);
				$('#maintainInfoForm #freightMaintainId').val(data.id);
				$('#maintainInfoForm #freightMaintainTypeName').val(data.freightMaintainType.typeName);
				if(readonly){
					$('#maintainInfoForm #descn').attr('readonly', 'readonly');
					$('#maintainInfoModal #maintainInfoSubmitBtn').hide();
				}
				
				var margin = (window.screen.availWidth - 800)/2;
				$('#maintainInfoModal').css("margin-left", margin);
				$('#maintainInfoModal').css("width","800px");
				$('#maintainInfoModal').modal();
			});
		}
		//增加动作
		function addAction(){
			if(selectTreeTId == ''){
				alert('请首先选择一个操作!');
				return false;
			}else{
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType == 'order' || nodeType == 'action'){
					alert('请首先选择一个操作!不能直接在动作或者订单节点下直接添加!');
					return false;
				}else{
					var children = node.children;
					var count = 1;
					if(children != undefined){//如果为undifined说明还没有子节点
						for(var i=0, len=children.length; i<len; i++){
							if(children[i].nodeType == 'action'){
								count++;
							}
						}
					}
					
					$('#actionForm #displayIndex').val(count);
					$('#actionForm #freightMaintainId').val(node.id);
					var margin = (window.screen.availWidth - 800)/2;
					$('#actionModal').css("margin-left", margin);
					$('#actionModal').css("width","800px");
					$('#actionModal').modal();
				}
			}
		}
		
		//删除动作
		function removeAction(){
			if(selectTreeTId == ''){
				alert('请首先选择一个动作!');
			}else{
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType == 'order' || nodeType == 'maintain'){
					alert('请首先选择一个动作!');
				}else{
					if(node.status == '未执行'){
						if(window.confirm('确定要删除吗?')){
							$.post('fre-action-done-delete-action.do?freightActionId=' + node.id,function(data){
								init();
							});
						}
					}else{
						alert('只能删除未执行的动作！');
						return;
					}
				}
			}
		}
		
		//选择MAINTAIN类型
		$(function() {
			new createListPicker({
				title:'选择操作', //标题
				modalId: 'maintainTypeModal',//modalID
				formId:'maintainTypeForm',//提交的form
				sumitAction:'xx',//提交的action
				thead:['id','名称', '优先级'],//列表头 数组
				tbody:['id', 'typeName','priority'],//字段数据 数组
				tableId:'maintainTypeTable',//表ID
				multiple: true,//是否多选
				canSelect:true,//是否可返回值
				valueType:'id',//需要的取值字段
				valueInput:'freightMaintainTypeIds',//取值填至何处
				displayType:'typeName',//显示的取值字段
				displayInput:'freightMaintainTypeName'//显示填至何处
			});
		});
		
	//选择ACTION类型
	$(function() {
		new createListPicker({
			title:'选择动作', //标题
			modalId: 'actionTypeModal',//modalID
			formId:'actionTypeForm',//提交的form
			sumitAction:'xx',//提交的action
			thead:['id','名称', '是否对内部','是否需要发送委托','是否有填报界面','处理人'],//列表头 数组
			tbody:['id', 'typeName','internal','delegate','prime', 'assignee.displayName'],//字段数据 数组
			tableId:'actionTypeTable',//表ID
			multiple: true,//是否多选
			canSelect:true,//是否可返回值
			valueType:'id',//需要的取值字段
			valueInput:'freightActionTypeIds',//取值填至何处
			displayType:'typeName',//显示的取值字段
			displayInput:'freightActionTypeName'//显示填至何处
		});
	});
		
	 function addPrime(freightActionId, readonly, ignoreRequire){
		 bootbox.animate(false);
		 var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');	
		 $.post('fre-action-to-prime-action.do?freightActionId=' + freightActionId, function(data){
				var freightAction = data.freightAction;
				var freightOrderBoxOfOrder = data.freightOrderBoxOfOrder;
				var freightOrderBoxOfAction = data.freightOrderBoxOfAction;
				
				var freightActionFiledNormal = data.freightActionFiledNormal;
				var freightActionValueNormal = data.freightActionValueNormal;
				
				var freightActionFiledForBox = data.freightActionFiledForBox;
				var freightActionValueForBox = data.freightActionValueForBox;
				
				if(freightAction.prime != 'T'){
					alert('没有配置填报界面!');
					return false;
				}else if((freightActionFiledNormal != null && freightActionFiledNormal.length != 0)
						|| (freightActionFiledForBox != null && freightActionFiledForBox.length != 0 
								&& (freightOrderBoxOfOrder != null && freightOrderBoxOfOrder.length != 0))){
					var trCount = 0;
					var html = '<input id="freightActionId" name="freightActionId" type="hidden" value="' + freightAction.id + '">';
					html += '<table class="m-table table-input" ><tbody>';
					for(var i=0, len=freightActionFiledNormal.length; i<len; i++){
						var canSelect = freightActionFiledNormal[i].canSelect == 'T' ? true : false;
						var vAttrId = freightActionFiledNormal[i].vAttrId != null ? freightActionFiledNormal[i].vAttrId : '';
						var vClsId = freightActionFiledNormal[i].vClsId != null ? freightActionFiledNormal[i].vClsId : '';
						var vColumn = freightActionFiledNormal[i].vColumn != null ? freightActionFiledNormal[i].vColumn : '';
						var vFilterId = freightActionFiledNormal[i].vFilterId != null ? freightActionFiledNormal[i].vFilterId : '';
						var required = freightActionFiledNormal[i].required == 'T' ? 'required' : '';
						if(ignoreRequire){//如果忽略
							required = '';
						}
						var fieldType = freightActionFiledNormal[i].fieldType;
						var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
						var fieldName = freightActionFiledNormal[i].fieldName;
						var fiedlValue = '';
						var fieldColumnId = '';
						for(var j=0, size=freightActionValueNormal.length; j<size; j++){
							if(freightActionFiledNormal[i].id == freightActionValueNormal[j].freightActionField.id){
								fieldColumnId = freightActionValueNormal[j].id;
								
								if(freightActionFiledNormal[i].fieldType == 'INT'){
									fiedlValue = freightActionValueNormal[j].intValue == null? '' : freightActionValueNormal[j].intValue;
								}else if(freightActionFiledNormal[i].fieldType == 'DOUBLE'){
									fiedlValue = freightActionValueNormal[j].doubleValue == null? '' : freightActionValueNormal[j].doubleValue;
								}else if(freightActionFiledNormal[i].fieldType == 'VARCHAR'){
									fiedlValue = freightActionValueNormal[j].stringValue == null? '' : freightActionValueNormal[j].stringValue;
								}else if(freightActionFiledNormal[i].fieldType == 'TEXT'){
									fiedlValue = freightActionValueNormal[j].textValue == null? '' : freightActionValueNormal[j].textValue;
								}else if(freightActionFiledNormal[i].fieldType == 'DATETIME'){
									fiedlValue = freightActionValueNormal[j].dateValue == null? '' : $.fullCalendar.formatDate(new Date(freightActionValueNormal[j].dateValue),'yyyy-MM-dd');
								}else if(freightActionFiledNormal[i].fieldType == 'TIMESTAMP'){
									fiedlValue = freightActionValueNormal[j].dateValue == null? '' : $.fullCalendar.formatDate(new Date(freightActionValueNormal[j].dateValue),'yyyy-MM-dd HH:mm:ss');
								}
								
								break;
							}
						}
						if(trCount%4 == 0){
							html +='<tr>';
						}
						//label
						if(fieldType == 'TEXT'){
							if(trCount%4 == 1){
								html += '<td></td><td></td><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 3;
							}else if(trCount%4 == 2){
								html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 2;
							}else if(trCount%4 == 3){
								html += '</tr><tr><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 3;
							}else if(trCount%4 == 0){
								html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 2;
							}
						}else{
							html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 1;
						}

						if(!readonly){
							if(fieldType == 'TEXT'){
								html += '<td colspan="3"><textarea id="' + fieldColumnId + '"name="' + fieldColumnId +'" style="min-height:80px;" class="form-control ' + required + '">'+fiedlValue+'</textarea></td>';
							}else{
								if(i == len-1 && trCount%4 != 0){
									var colspan = 8 - 2* (trCount%4) + 1;
									html += '<td colspan="'+ colspan +'"><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
								}else{
									html += '<td><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
								}
								html += 'value="' + fiedlValue + '" ';
								if(canSelect){
									html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
								}else{
									html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
								}
							}
						}else{
							if(fieldType == 'TEXT'){
								html += '<td colspan="3"><textarea style="min-height:80px;" class="form-control ' + required + '" readonly>'+fiedlValue+'</textarea></td>';
							}else if(i == len-1 && trCount%4 != 0){
								var colspan = 8 - 2* (trCount%4) + 1;
								html += '<td colspan="' + colspan + '">' + fiedlValue + '</td>';
							}else{
								html += '<td>' + fiedlValue + '</td>';
							}
						}
						
						if(trCount%4 == 0){
							html +='</tr>';
						}
					}
					if(html.lastIndexOf('</tr>') != html.length - 5){
						html +='</tr>';
					}
					html += '</tbody></table>';
					$('#actionFieldForm').html(html);
					if(readonly){
						$('#actionFieldModal #actionFieldSubmitBtn').hide();
					}else{
						$('#actionFieldModal #actionFieldSubmitBtn').show();
					}
					//箱封信息
					if(freightOrderBoxOfOrder != null && freightOrderBoxOfOrder.length > 0){
						html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>箱号</th><th>封号</th><th>状态</th></tr></thead><tbody>';
						$.each(freightOrderBoxOfOrder, function(i, item){
							var isChecked = false;
							$.each(freightOrderBoxOfAction, function(j, ele){
								if(item.id == ele.id){
									isChecked = true;
								}
							});
							if(isChecked){
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" checked /></td>';
							}else{
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" /></td>';
							}
							html += '<td id="' + item.id + 'countUnit">' + item.freightBoxRequire.boxType + '</td>';
							html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
							html += '<td>' + item.freightBoxRequire.boxCondition + '</td>';
							html += '<td>' + item.freightBoxRequire.boxSource + '</td>';
							html += '<td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
							html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
							html += '<td>' + item.status + '</td>';
						});
						html += "</tbody></table>";
						$('#orderBoxSelectForm').html(html);
						
						//含箱字段信息
						if(freightActionFiledForBox != null &&　freightActionFiledForBox.length > 0
								&& freightActionValueForBox != null && freightActionValueForBox.length > 0){
							var html = '<table id="forBoxTable" class="m-table table-bordered" >';
							html += '<thead><tr><th>箱号</th><th>箱型</th><th>箱属</th><th>封号</th>';
							$.each(freightActionFiledForBox, function(i, item){
								html += '<th>' + item.fieldName + '</th>';
							});
							html += '</tr></thead><tbody>';
							$.each(freightOrderBoxOfAction, function(i, item){
								html += '<tr><td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
								html += '<td>' + item.freightBoxRequire.boxType + '</td>';
								html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
								html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
								$.each(freightActionFiledForBox, function(i, field){
									//初始化字段信息
									var canSelect = field.canSelect == 'T' ? true : false;
									var vAttrId = field.vAttrId != null ? field.vAttrId : '';
									var vClsId = field.vClsId != null ? field.vClsId : '';
									var vColumn = field.vColumn != null ? field.vColumn : '';
									var vFilterId = field.vFilterId != null ? field.vFilterId : '';
									var required = field.required == 'T' ? 'required' : '';
									if(ignoreRequire){//如果忽略
										required = '';
									}
									var fieldType = field.fieldType;
									var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
									var fieldName = field.fieldName;
									var fiedlValue = '';
									var valueId = '';
									
									$.each(freightActionValueForBox, function(j, value){
										if(value.freightOrderBox.id == item.id && value.freightActionField.id == field.id){
											valueId = value.id;
											if(fieldType == 'INT'){
												fiedlValue = value.intValue == null? '' : value.intValue;
											}else if(fieldType == 'DOUBLE'){
												fiedlValue = value.doubleValue == null? '' : value.doubleValue;
											}else if(fieldType == 'VARCHAR'){
												fiedlValue = value.stringValue == null? '' : value.stringValue;
											}else if(fieldType == 'TEXT'){
												fiedlValue = value.textValue == null? '' : value.textValue;
											}else if(fieldType == 'DATETIME'){
												fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
											}else if(fieldType == 'TIMESTAMP'){
												fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd HH:mm:ss');
											}
											
											if(!readonly){
												html += '<td><input id="' + valueId + '" type="text" name="' + valueId +'" ';
												html += 'value="' + fiedlValue + '" fieldId="' + field.id +'" ';
												if(canSelect){
													html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
												}else{
													html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
												}
											}else{
												html += '<td>' + fiedlValue + '</td>';
											}
											return false;//跳出循环
										}
									});
								});
								
								html += '</tr>';
							});
							html += '</tbody></table>';
							$('#actionFieldForm').append(html);
						}
						if(!readonly){
							if($('#orderBoxSelectForm table').length > 0){
								if($('#orderBoxSelectForm .selectedItemId:checked').length > 0){
									$('#orderBoxSelectForm').parent().hide(300);//如果有选择的，则说明已经选择过，隐藏
									$('#toPrimeBoxBtn').hide();
									$('#revisePrimeBoxBtn').show();
									
									$('#actionFieldForm').show(300);
									$('#actionFieldSubmitBtn').show();
								}else{
									alert('该动作包含箱封信息，请点击确定，选择具体箱封之后再继续！');
									$('#orderBoxSelectForm').parent().show(300);
									$('#toPrimeBoxBtn').show();
									$('#revisePrimeBoxBtn').hide();
									
									$('#actionFieldForm').hide(300);
									$('#actionFieldSubmitBtn').hide();
								}
							}
						}else{
							$('#orderBoxSelectForm').parent().hide();
							$('#toPrimeBoxBtn').hide();
							$('#revisePrimeBoxBtn').hide();
						}
					}else{
						$('#orderBoxSelectForm').parent().hide();
						$('#toPrimeBoxBtn').hide();
						$('#revisePrimeBoxBtn').hide();
					}
					initDatePicker();//时间格式
					box.modal('hide');
					var margin = (window.screen.availWidth - 1200)/2;
					$('#actionFieldModal').css("margin-left", margin);
					$('#actionFieldModal').css("width","1200px");
					$('#actionFieldModal').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
				}else{
					alert('没有添加箱需！');
					return false;
				}
			});
		}
	 
	 //优化过的填报方法
	 function toPrimeAction(freightActionId, readonly, ignoreRequire){
		 bootbox.animate(false);
		 var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');	
		 $.post('fre-action-to-prime-action.do?freightActionId=' + freightActionId, function(data){
				var freightAction = data.freightAction;
				var freightOrderBoxOfOrder = data.freightOrderBoxOfOrder;
				var freightOrderBoxOfAction = data.freightOrderBoxOfAction;
				
				var freightActionFiledNormal = data.freightActionFiledNormal;
				var freightActionValueNormal = data.freightActionValueNormal;
				
				var freightActionFiledForBox = data.freightActionFiledForBox;
				var freightActionValueForBox = data.freightActionValueForBox;
				
				//优化过的数据
				var freightActionValueNormalMap = data.freightActionValueNormalMap;
				var freightActionValueForBoxMap = data.freightActionValueForBoxMap;
				
				if(freightAction.prime != 'T'){
					alert('没有配置填报界面!');
					return false;
				}else if((freightActionFiledNormal != null && freightActionFiledNormal.length != 0)
						|| (freightActionFiledForBox != null && freightActionFiledForBox.length != 0 
								&& (freightOrderBoxOfOrder != null && freightOrderBoxOfOrder.length != 0))){
					var trCount = 0;
					var html = '<input id="freightActionId" name="freightActionId" type="hidden" value="' + freightAction.id + '">';
					html += '<table class="m-table table-input" ><tbody>';
					for(var i=0, len=freightActionFiledNormal.length; i<len; i++){
						var canSelect = freightActionFiledNormal[i].canSelect == 'T' ? true : false;
						var vAttrId = freightActionFiledNormal[i].vAttrId != null ? freightActionFiledNormal[i].vAttrId : '';
						var vClsId = freightActionFiledNormal[i].vClsId != null ? freightActionFiledNormal[i].vClsId : '';
						var vColumn = freightActionFiledNormal[i].vColumn != null ? freightActionFiledNormal[i].vColumn : '';
						var vFilterId = freightActionFiledNormal[i].vFilterId != null ? freightActionFiledNormal[i].vFilterId : '';
						var required = freightActionFiledNormal[i].required == 'T' ? 'required' : '';
						if(ignoreRequire){//如果忽略
							required = '';
						}
						var fieldType = freightActionFiledNormal[i].fieldType;
						var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
						var fieldName = freightActionFiledNormal[i].fieldName;
						var fiedlValue = '';
						var fieldColumnId = '';
						
						var value = freightActionValueNormalMap[freightActionFiledNormal[i].id];
						if(fieldType == 'INT'){
							fiedlValue = value.intValue == null? '' : value.intValue;
						}else if(fieldType == 'DOUBLE'){
							fiedlValue = value.doubleValue == null? '' : value.doubleValue;
						}else if(fieldType == 'VARCHAR'){
							fiedlValue = value.stringValue == null? '' : value.stringValue;
						}else if(fieldType == 'TEXT'){
							fiedlValue = value.textValue == null? '' : value.textValue;
						}else if(fieldType == 'DATETIME'){
							fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
						}else if(fieldType == 'TIMESTAMP'){
							fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(fvalue.dateValue),'yyyy-MM-dd HH:mm:ss');
						}
						if(trCount%4 == 0){
							html +='<tr>';
						}
						//label
						if(fieldType == 'TEXT'){
							if(trCount%4 == 1){
								html += '<td></td><td></td><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 3;
							}else if(trCount%4 == 2){
								html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 2;
							}else if(trCount%4 == 3){
								html += '</tr><tr><td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 3;
							}else if(trCount%4 == 0){
								html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
								trCount += 2;
							}
						}else{
							html += '<td><label class="td-label" for="' + fieldColumnId + '">' + fieldName + '</label></td>'
							trCount += 1;
						}

						if(!readonly){
							if(fieldType == 'TEXT'){
								html += '<td colspan="3"><textarea id="' + fieldColumnId + '"name="' + fieldColumnId +'" style="min-height:80px;" class="form-control ' + required + '">'+fiedlValue+'</textarea></td>';
							}else{
								if(i == len-1 && trCount%4 != 0){
									var colspan = 8 - 2* (trCount%4) + 1;
									html += '<td colspan="'+ colspan +'"><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
								}else{
									html += '<td><input id="' + fieldColumnId + '" type="text" name="' + fieldColumnId +'"';
								}
								html += 'value="' + fiedlValue + '" ';
								if(canSelect){
									html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
								}else{
									html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
								}
							}
						}else{
							if(fieldType == 'TEXT'){
								html += '<td colspan="3"><textarea style="min-height:80px;" class="form-control ' + required + '" readonly>'+fiedlValue+'</textarea></td>';
							}else if(i == len-1 && trCount%4 != 0){
								var colspan = 8 - 2* (trCount%4) + 1;
								html += '<td colspan="' + colspan + '">' + fiedlValue + '</td>';
							}else{
								html += '<td>' + fiedlValue + '</td>';
							}
						}
						
						if(trCount%4 == 0){
							html +='</tr>';
						}
					}
					if(html.lastIndexOf('</tr>') != html.length - 5){
						html +='</tr>';
					}
					html += '</tbody></table>';
					$('#actionFieldForm').html(html);
					if(readonly){
						$('#actionFieldModal #actionFieldSubmitBtn').hide();
					}else{
						$('#actionFieldModal #actionFieldSubmitBtn').show();
					}
					//箱封信息
					if(freightOrderBoxOfOrder != null && freightOrderBoxOfOrder.length > 0){
						html = '<table class="m-table table-bordered table-hover" style="padding-bottom: -10px;">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>箱型</th><th>箱属</th><th>箱况</th><th>集装箱来源</th><th>箱号</th><th>封号</th><th>状态</th></tr></thead><tbody>';
						$.each(freightOrderBoxOfOrder, function(i, item){
							var isChecked = false;
							$.each(freightOrderBoxOfAction, function(j, ele){
								if(item.id == ele.id){
									isChecked = true;
								}
							});
							if(isChecked){
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" checked /></td>';
							}else{
								html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="' + item.id + '" /></td>';
							}
							html += '<td id="' + item.id + 'countUnit">' + item.freightBoxRequire.boxType + '</td>';
							html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
							html += '<td>' + item.freightBoxRequire.boxCondition + '</td>';
							html += '<td>' + item.freightBoxRequire.boxSource + '</td>';
							html += '<td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
							html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
							html += '<td>' + item.status + '</td>';
						});
						html += "</tbody></table>";
						$('#orderBoxSelectForm').html(html);
						
						//含箱字段信息
						if(freightActionFiledForBox != null &&　freightActionFiledForBox.length > 0
								&& freightActionValueForBox != null && freightActionValueForBox.length > 0){
							var html = '<table id="forBoxTable" class="m-table table-bordered" >';
							html += '<thead><tr><th>箱号</th><th>箱型</th><th>箱属</th><th>封号</th>';
							$.each(freightActionFiledForBox, function(i, item){
								html += '<th>' + item.fieldName + '</th>';
							});
							html += '</tr></thead><tbody>';
							$.each(freightOrderBoxOfAction, function(i, item){
								html += '<tr><td>' + (item.freightBox == null ? '无' : item.freightBox.boxNumber) + '</td>';
								html += '<td>' + item.freightBoxRequire.boxType + '</td>';
								html += '<td>' + item.freightBoxRequire.boxBelong + '</td>';
								html += '<td>' + (item.freightSeal == null ? '无' : item.freightSeal.sealNumber) + '</td>';
								
								$.each(freightActionFiledForBox, function(i, field){
									//初始化字段信息
									var canSelect = field.canSelect == 'T' ? true : false;
									var vAttrId = field.vAttrId != null ? field.vAttrId : '';
									var vClsId = field.vClsId != null ? field.vClsId : '';
									var vColumn = field.vColumn != null ? field.vColumn : '';
									var vFilterId = field.vFilterId != null ? field.vFilterId : '';
									var required = field.required == 'T' ? 'required' : '';
									if(ignoreRequire){//如果忽略
										required = '';
									}
									var fieldType = field.fieldType;
									var classType = (fieldType == 'INT' || fieldType == 'DOUBLE') ? 'number' : fieldType == 'DATETIME'?'datepicker date ' : fieldType == 'TIMESTAMP' ? 'datetimepicker datetime' : '';
									var fieldName = field.fieldName;
									var fiedlValue = '';
									
									var value = freightActionValueForBoxMap[field.id + item.id];
									var valueId = value.id;
									if(fieldType == 'INT'){
										fiedlValue = value.intValue == null? '' : value.intValue;
									}else if(fieldType == 'DOUBLE'){
										fiedlValue = value.doubleValue == null? '' : value.doubleValue;
									}else if(fieldType == 'VARCHAR'){
										fiedlValue = value.stringValue == null? '' : value.stringValue;
									}else if(fieldType == 'TEXT'){
										fiedlValue = value.textValue == null? '' : value.textValue;
									}else if(fieldType == 'DATETIME'){
										fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
									}else if(fieldType == 'TIMESTAMP'){
										fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(fvalue.dateValue),'yyyy-MM-dd HH:mm:ss');
									}
									if(!readonly){
										html += '<td><input id="' + valueId + '" type="text" name="' + valueId +'" ';
										html += 'value="' + fiedlValue + '" fieldId="' + field.id +'" ';
										if(canSelect){
											html += 'class="form-control ' + required + ' ' + classType + ' dictionary" vAttrId="'+vAttrId+'" vClsId="'+vClsId+'" vColumn="'+vColumn+'" vFilterId="'+vFilterId+'"></td>';
										}else{
											html += 'class="form-control ' + required + ' ' + classType + '" ></td>';
										}
									}else{
										html += '<td>' + fiedlValue + '</td>';
									}
								});
								
								html += '</tr>';
							});
							html += '</tbody></table>';
							$('#actionFieldForm').append(html);
						}
						if(!readonly){
							if($('#orderBoxSelectForm table').length > 0){
								if($('#orderBoxSelectForm .selectedItemId:checked').length > 0){
									$('#orderBoxSelectForm').parent().hide(300);//如果有选择的，则说明已经选择过，隐藏
									$('#toPrimeBoxBtn').hide();
									$('#revisePrimeBoxBtn').show();
									
									$('#actionFieldForm').show(300);
									$('#actionFieldSubmitBtn').show();
								}else{
									alert('该动作包含箱封信息，请点击确定，选择具体箱封之后再继续！');
									$('#orderBoxSelectForm').parent().show(300);
									$('#toPrimeBoxBtn').show();
									$('#revisePrimeBoxBtn').hide();
									
									$('#actionFieldForm').hide(300);
									$('#actionFieldSubmitBtn').hide();
								}
							}
						}else{
							$('#orderBoxSelectForm').parent().hide();
							$('#toPrimeBoxBtn').hide();
							$('#revisePrimeBoxBtn').hide();
						}
					}else{
						$('#orderBoxSelectForm').parent().hide();
						$('#toPrimeBoxBtn').hide();
						$('#revisePrimeBoxBtn').hide();
					}
					initDatePicker();//时间格式
					box.modal('hide');
					var margin = (window.screen.availWidth - 1200)/2;
					$('#actionFieldModal').css("margin-left", margin);
					$('#actionFieldModal').css("width","1200px");
					$('#actionFieldModal').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
				}else{
					alert('没有添加箱需！');
					return false;
				}
			});
	 }
	 
	 $(document).delegate('#toPrimeBoxBtn', 'click', function(e){
		 if($('#orderBoxSelectForm .selectedItemId:checked').length == 0){
	 			alert('至少选择一个或则全部的箱封！');
	 		}else{
	 			$('#toPrimeBoxBtn').hide();//提取对按钮进行处理
				$('#revisePrimeBoxBtn').hide();
				
	 			var freightActionId = $('#actionFieldForm #freightActionId').val();
	 			var url = 'fre-action-to-prime-box.do?freightActionId=' + freightActionId;
	 			$('#orderBoxSelectForm .selectedItemId:checked').each(function(i, item){
		 			url += '&freightOrderBoxId=' + $(item).val();
		 		});
	 			
	 			$.post(url, function(data){
	 				if(data == 'success'){
	 					<c:if test="${item.orderStatus == '未提交' or item.orderStatus == '已追回' or item.orderStatus == '已退回' }">
	 					addPrime(freightActionId, false, true);
	 					</c:if>
	 					
	 					<c:if test="${item.orderStatus == '审核中' or item.orderStatus == '已审核'}">
	 					addPrime(freightActionId, false, false);
	 					</c:if>
	 					//$('#toPrimeBoxBtn').hide();
						$('#revisePrimeBoxBtn').show();
	 				}
	 			});
	 		}
	 });
	 
	 $(document).delegate('#revisePrimeBoxBtn', 'click', function(e){
		 $('#orderBoxSelectForm').parent().show(300);
		 $('#actionFieldForm').hide(300);
		 $('#toPrimeBoxBtn').show();
		 $('#revisePrimeBoxBtn').hide();
		 $('#actionFieldSubmitBtn').hide();
	 });
		 
		//执行，
		//动作状态：未执行，预备委托，预备完成，已发送，已执行；
		//委托状态：操作，预备委托，待执行，已执行
		function toExecuteAction(){
			if(selectTreeTId == ''){
				alert('请首先选择一个动作!');
				return;
			}else{
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType == 'order' || nodeType == 'maintain'){
					alert('请首先选择一个动作!');
					return;
				}else{
					if(node.status == '已发送' || node.status == '已执行'){
						alert('委托已发送，或动作已执行!');
						return;
					}else{
						if(window.confirm('确定发送该动作相关委托吗?')){
							$.post('fre-action-to-execute-action.do?freightActionId=' + node.id,function(data){
								if(data == 'success'){
									init();
								}else{
									alert('发送失败，请生成对应委托！');
								}
							});
						}
					}
				}
			}
		}
		//完成
		function doneExecuteAction(){
			if(selectTreeTId == ''){
				alert('请首先选择一个动作!');
				return;
			}else{
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType == 'order' || nodeType == 'maintain'){
					alert('请首先选择一个动作!');
					return;
				}else{
					if(node.status == '已执行'){
						alert('该动作已经执行!');
						return;
					}else if(node.internal == 'T'){
						alert('该动作对内，不能直接执行!');
						return;
					}else{
						if(window.confirm('确定要执行该动作吗?')){
							$.post('fre-action-done-execute-action.do?freightActionId=' + node.id,function(data){
								if(data == 'success'){
									init();
								}else{
									alert('执行失败，没有生成委托或已分配给其他人处理！');
								}
							});
						}
					}
				}
			}
		}
		
		//选择动作复制未模板
		$(document).delegate('#toAddTemplate', 'click',function(e){
			if(selectTreeTId != ''){
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType != 'action'){
					alert('请选择一个动作！');
					return false;
				}else{
					$.post('fre-data-template-to-add-template.do?freightActionId=' + node.id, function(data){
						var normalField = data.normalField;
						var foxBoxField = data.foxBoxField;
						var normalValue = data.normalValue;
						var foxBoxValue = data.foxBoxValue;
						
						var html = '<table class="m-table table-bordered" >';
						html += '<thead><tr><th colspan="2">模板名称(*必填)</th><th colspan="2"><input id="templateName" name="templateName" type="text" class="form-control required"></th></tr><thead>';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th><th>字段名称</th><th>字段类型</th><th>值</th></tr></thead>';
						html += '<tbody>';
						if(normalField != null &&　normalField.length > 0){
							html += '<tr><th colspan="4">一般信息</th></tr>';
							$.each(normalField, function(i, field){
								$.each(normalValue, function(i, value){
									if(field.id == value.freightActionField.id){
										var fieldType = field.fieldType;
										var fieldValue = '';
										if(fieldType == 'INT'){
											fiedlValue = value.intValue == null? '' : value.intValue;
										}else if(fieldType == 'DOUBLE'){
											fiedlValue = value.doubleValue == null? '' : value.doubleValue;
										}else if(fieldType == 'VARCHAR'){
											fiedlValue = value.stringValue == null? '' : value.stringValue;
										}else if(fieldType == 'TEXT'){
											fiedlValue = value.textValue == null? '' : value.textValue;
										}else if(fieldType == 'DATETIME'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
										}else if(fieldType == 'TIMESTAMP'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd HH:mm:ss');
										}
										
										fieldType = (fieldType == 'VARCHAR' || fieldType == 'TEXT') ? '字符串' : fieldType == 'INT' ? '整数' : fieldType == 'DOUBLE' ? '小数' : fieldType == 'DATETIME'? '日期' : fieldType == 'TIMESTAMP'? '时间': '无';
										
										html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="freightActionValueId" value="'+value.id+'" /></td>';
										html += '<td style="width:150px;">'+ field.fieldName + '</td><td>'+ fieldType +'</td><td>'+ fiedlValue +'</td></tr>';
										return false;
									}
								});
							});
						}
						
						if(foxBoxField != null &&　foxBoxField.length > 0){
							html += '<tr><th colspan="4">含箱信息</th></tr>';
							$.each(foxBoxField, function(i, field){
								$.each(foxBoxValue, function(i, value){
									if(field.id == value.freightActionField.id){
										var fieldType = field.fieldType;
										var fieldValue = '';
										if(fieldType == 'INT'){
											fiedlValue = value.intValue == null? '' : value.intValue;
										}else if(fieldType == 'DOUBLE'){
											fiedlValue = value.doubleValue == null? '' : value.doubleValue;
										}else if(fieldType == 'VARCHAR'){
											fiedlValue = value.stringValue == null? '' : value.stringValue;
										}else if(fieldType == 'TEXT'){
											fiedlValue = value.textValue == null? '' : value.textValue;
										}else if(fieldType == 'DATETIME'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd');
										}else if(fieldType == 'TIMESTAMP'){
											fiedlValue = value.dateValue == null? '' : $.fullCalendar.formatDate(new Date(value.dateValue),'yyyy-MM-dd HH:mm:ss');
										}
										
										fieldType = (fieldType == 'VARCHAR' || fieldType == 'TEXT') ? '字符串' : fieldType == 'INT' ? '整数' : fieldType == 'DOUBLE' ? '小数' : fieldType == 'DATETIME'? '日期' : fieldType == 'TIMESTAMP'? '时间': '无';
										
										html += '<tr><td><input class="selectedItemId a-check" type="checkbox" name="freightActionValueId" value="'+value.id+'" /></td>';
										html += '<td>'+ field.fieldName + '</td><td>'+ fieldType +'</td><td>'+ fiedlValue +'</td></tr>';
										return false;
									}
								});
							});
						}
						
						html += '</tbody></table>';
						$('#addDataTemplateForm').html(html);
						
						var margin = (window.screen.availWidth - 1000)/2;
						$('#addDataTemplateModal').css("margin-left", margin);
						$('#addDataTemplateModal').css("width","1000px");
						$('#addDataTemplateModal').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
					});
				}
			}else{
				alert('请选择一个动作！');
				return false;
			}
		});
		//保存模板信息
		$(function() {
			$("#addDataTemplateForm").validate({
		        submitHandler: function(form) {
		    		$.post('fre-data-template-done-add-template.do', $("#addDataTemplateForm").serialize(), function(data){
		    			if(data == 'success'){
		    				alert('保存成功！');
		    				$('#addDataTemplateModal').modal('hide');
		    			}else{
		    				alert('保存失败！');
		    			}
		    		});
		        },
		        errorClass: 'validate-error',
		        rules: {
		        	templateName: {
	   	                remote: {
	   	                    url: 'fre-data-template-check-templatename.do',
	   	                    data: {
	   	                    }
	   	                }
	   	            }
		        },
		        messages: {
		        	templateName: {
	   	                remote: "存在重复"
	   	            }
	   	        }
			});
		});
		
		//复制数据模板
		$(document).delegate('#toCopyTemplate', 'click',function(e){
			if(selectTreeTId != ''){
				var node = zTreeObject.getNodeByTId(selectTreeTId);
				var nodeType = node.nodeType;
				if(nodeType != 'action'){
					alert('请选择一个动作！');
					return false;
				}else{
					$.post('fre-data-template-to-copy-template.do?freightActionId=' + node.id, function(data){
						var dataTemplates = data.dataTemplates;
						if(dataTemplates == null || dataTemplates.length == 0){
							alert('无该动作对应的数据模板！');
							return false;
						}else{
							var html = '<table class="m-table table-bordered" >';
							html += '<input id="freightActionId" name="freightActionId" value="'+ node.id +'" type="hidden">';
							html += '<theah><tr><th>选择模板</th><th><select id="freightDataTemplateId" name="freightDataTemplateId" class="form-control required"><option value=""></option>';
							
							$.each(dataTemplates, function(i, item){
								html += '<option value="'+ item.id +'">'+ item.templateName +'</option>'
							});
							html += '</select></th></tr></thead>';
							
							$('#copyDataTemplateForm').html(html);
							
							var margin = (window.screen.availWidth - 1000)/2;
							$('#copyDataTemplateModal').css("margin-left", margin);
							$('#copyDataTemplateModal').css("width","1000px");
							$('#copyDataTemplateModal').modal({backdrop: 'static', keyboard: false});//单击空白处不会隐藏modal
						}
					});
				}
			}else{
				alert('请选择一个动作！');
				return false;
			}
		});
		//完成复制
		$(function() {
			$("#copyDataTemplateForm").validate({
		        submitHandler: function(form) {
		        	$('#copyDataTemplateModal').modal('hide');
		        	var freightActionId = $("#copyDataTemplateForm #freightActionId").val();
		        	if(window.confirm('是否将已存在的数据进行覆盖？')){
		        		$.post('fre-data-template-done-copy-template.do?sheathe=T', $("#copyDataTemplateForm").serialize(), function(data){
			    			if(data == 'success'){
			    				alert('复制成功！');
			    				$('#copyDataTemplateModal').modal('hide');
			    				addPrime(freightActionId, false, false);//复制成功之后打开动作界面
			    			}else{
			    				alert('复制失败！');
			    			}
			    		});
		        	}else{
		        		$.post('fre-data-template-done-copy-template.do?sheathe=F', $("#copyDataTemplateForm").serialize(), function(data){
			    			if(data == 'success'){
			    				alert('复制成功！');
			    				$('#copyDataTemplateModal').modal('hide');
			    				addPrime(freightActionId, false, false);//复制成功之后打开动作界面
			    			}else{
			    				alert('复制失败！');
			    			}
			    		});
		        	}
		        },
		        errorClass: 'validate-error'
			});
		});
		
		/* //提交业务
		$(document).delegate('#toOrderAudit', 'click',function(e){
			var freightOrderId = $('#freightOrderForm #id').val();
			if(freightOrderId == ''){
				alert('提交之前请保存订单基本信息和添加相应的操作范围！');
				return;
			}else{
				$.post('fre-order-to-order-audit.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('提交失败！请确认订单状态及确认是否需要先填报箱需信息！');
					}else{
						alert('提交成功！');
					} 
					closeWindow();//关闭窗口
				});
			}
		});
		//追回订单
		$(document).delegate('#toOrderRecover', 'click',function(e){
			var freightOrderId = $('#freightOrderForm #id').val();
			if(freightOrderId == ''){
				alert('提交之前请保存订单基本信息和添加相应的操作范围！');
				return;
			}else{
				$.post('fre-order-to-order-recover.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('追回失败！请确认订单状态！');
					}else{
						alert('追回成功！');
					} 
					closeWindow();//关闭窗口
				});
			}
		}); */
		//确认追回
		$(document).delegate('#doneOrderRecover', 'click',function(e){
			var freightOrderId = $('#freightOrderForm #id').val();
			if(freightOrderId == ''){
				alert('提交之前请保存订单基本信息和添加相应的操作范围！');
				return;
			}else{
				$.post('fre-order-done-order-recover.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('确认失败！请确认订单状态！');
					}else{
						alert('确认成功！');
					} 
					closeWindow();//关闭窗口
				});
			}
		});
		
		//第一行修改值，其他行跟着修改，即便非必须也跟着改。
		$(document).delegate('#actionFieldForm #forBoxTable tbody tr:first .form-control.required', 'change', function(e){
		//$(document).delegate('#actionFieldForm #forBoxTable tbody tr:first .form-control', 'change', function(e){
			var value = $(this).val();
			var fieldId = $(this).attr('fieldId');
			$('#actionFieldForm #forBoxTable tbody tr:not(:first) .form-control.required').each(function(i, item){
			//$('#actionFieldForm #forBoxTable tbody tr:not(:first) .form-control').each(function(i, item){
				if($(item).attr('fieldId') == fieldId){
					$(item).val(value);
					$(item).select2('val', value); 
				}
			});
		});
		
		/* //审核订单
		$(document).delegate('#doneOrderAudit', 'click',function(e){
			var freightOrderId = $('#freightOrderForm #id').val();
			if(freightOrderId == ''){
				alert('请刷新页面之后再进行操作！');
				return;
			}else{
				$.post('fre-order-done-order-audit.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('审核失败！请确认订单状态！');
					}else{
						alert('审核成功！');
					} 
					closeWindow();//关闭窗口
				});
			}
		});
		
		//退回订单
		$(document).delegate('#backOrderAudit', 'click',function(e){
			var freightOrderId = $('#freightOrderForm #id').val();
			if(freightOrderId == ''){
				alert('请刷新页面之后再进行操作！');
				return;
			}else{
				$.post('fre-order-back-order-audit.do?freightOrderId=' +　freightOrderId, function(data){
					if(data != 'success'){
						alert('退回失败！请确认订单状态！');
					}else{
						alert('退回成功！');
					} 
					closeWindow();//关闭窗口
				});
			}
		}); 
		
		//****************拼接多个json对象字符串
		function toJsonStringArray(formId){
			var fields = $('#' + formId).serializeArray();
			var data = '[{';
			$.each(fields, function(i, item){
				if(i == 0){
	   				data += '"' + item.name + '":"' + item.value + '"';
	   			}else{
	   				if(item.name == 'id'){
	   					data += '},{"' + item.name + '":"' + item.value + '"';
	   				}else{
	   					data += ',"' + item.name + '":"' + item.value + '"';
	   				}
	   			}
			});
			data += '}]';
			
			return data;
		}
		*/
		
		$(document).delegate('.selectedItemIdAll', 'click', function(){//扩展为通用
			if($(this).attr('checked') == 'checked'){
				var table = $(this).parent().parent().parent().parent().find('tbody');
				$.each(table.find('tr'), function(i, item){
					$.each($(item).find('td'), function(n, ele){
						$(ele).children('.selectedItemId').attr('checked', 'checked');
					});
				});
			}else{
				var table = $(this).parent().parent().parent().parent().find('tbody');
				$.each(table.find('tr'), function(i, item){
					$.each($(item).find('td'), function(n, ele){
						$(ele).children('.selectedItemId').removeAttr('checked');
					});
				});
			}
		});
		
		
		//点击模化窗口关闭按钮时刷新页面，暂时只针对添加箱需时使用
		$(document).delegate('#requireModal button', 'click', function(e){
			if($(this).attr('data-dismiss') == 'modal'){
				window.location.href = window.location.href;
			}
		});
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~箱需
		//添加箱需
		$(document).delegate('#addRequire', 'click',function(e){
			if(addRequire()){
				var margin = (window.screen.availWidth - 1200)/2;
				$('#requireModal').css("margin-left", margin);
				$('#requireModal').css("width","1200px");
				$('#requireModal').modal();
			}
		});
		
		/* //添加箱需
		function addRequire(){
			if($('#freightOrderForm #id').val() == undefined || $('#freightOrderForm #id').val() == ''){
				alert('找不到订单ID!');
				return false;
			}else{
				var freightOrderId = $('#freightOrderForm #id').val();
				var orderStatus = $('#freightOrderForm #orderStatus').val();
				$.ajax({
					url:'fre-box-require-to-add-require.do?freightOrderId=' + freightOrderId,
					type:'post',
					dataType:'json',
					async:true,
					success:function(data){
						var hasAddData = data.hasAddData;
						var html = '<input id="freightOrderId" type="hidden" value="' + freightOrderId + '">';
						html += '<input id="freightBoxRequireId" type="hidden" value="">';
						html += '<table class="m-table table-bordered">';
						html += '<thead><tr><th>起始地</th><th>终止地</th><th>集装箱来源</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>备注</th></tr></thead><tbody>';
						html += '<tr><td><input id="beginStation" name="beginStation" value="" class="form-control required dictionary" vAttrId="37604991-9612-11e4-b4b0-a4db305e5cc5"></td>';
						html += '<td><input id="arriveStation" name="arriveStation" value="" class="form-control required dictionary" vAttrId="37604991-9612-11e4-b4b0-a4db305e5cc5"></td>';
						html += '<td><select id="boxSource" name="boxSource" class="form-control required" >';
						html += '<option value="自管箱">自管箱</option><option value="外管箱">外管箱</option><option value="外理箱">外理箱</option>';
						html += '</select></td>';
						html += '<td><input id="boxType" name="boxType" class="form-control required dictionary" vAttrId="5a489097-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxBelong" name="boxBelong" class="form-control required dictionary" vAttrId="5a48921a-55d7-11e4-bdcd-a4db305e5cc5"></td>';
						html += '<td><input id="boxCondition" class="form-control required dictionary" vAttrId="5a488f04-55d7-11e4-bdcd-a4db305e5cc5" ></td>';
						html += '<td><input id="boxCount" name="boxCount" class="form-control required number"></td>';
						html += '<td><input id="descn" name="descn" class="form-control"></td>';
						html += '</tr>'
						$('#requireToAddForm').html(html);
						html = '<table class="m-table table-bordered table-hover">';
						html += '<thead><tr><th width="10" class="m-table-check"><input type="checkbox" class="selectedItemIdAll"/></th>';
						html += '<th>起始地</th><th>终止地</th><th>集装箱来源</th><th>箱型</th><th>箱属</th><th>箱况</th><th>数量</th><th>状态</th><th>备注</th></tr></thead><tbody>';
						$.each(hasAddData, function(i, item){
							html += '<tr><td><input class="selectedItemId a-check" type="checkbox" value="'+item.id+'" /></td>';
							html += '<td>' + item.beginStation + '</td>';
							html += '<td>' + item.arriveStation + '</td>';
							html += '<td>' + item.boxSource + '</td>';
							html += '<td>' + item.boxType + '</td>';
							html += '<td>' + item.boxBelong + '</td>';
							html += '<td>' + item.boxCondition + '</td>';
							html += '<td>' + item.boxCount + '</td>';
							html += '<td id="'+ item.id + 'status' +'">' + item.status+ '</td>';
							html += '<td>' + item.descn + '</td>';
						});
						html += "</tbody></table>";
						$('#requireHasAddForm').html(html);
						
						<sec:authorize ifAnyGranted="ROLE_CY-CUSTOMER-SERVICE-EMP" >
						if(orderStatus == '未提交' || orderStatus == '已退回' || orderStatus == '审核中' || orderStatus == '已追回'){
							$('#requireToAddForm').parent().show();
							$('#requireBtnForm').parent().show();
						}else{
							$('#requireToAddForm').parent().hide();
							$('#requireBtnForm').parent().hide();
						}
						</sec:authorize>
					},
					error:function(){
					}
				});
				return true;
			}
		}
		//修改箱需
		function reviseRequire(){
			if($('#requireHasAddForm .selectedItemId:checked').length != 1){
				alert('请选择一条数据！');
				return false;
			}else{
				var freightBoxRequireId = $('#requireHasAddForm .selectedItemId:checked').val();
				var status = $('#requireHasAddForm #' + freightBoxRequireId + 'status').text();
				var orderStatus = $('#freightOrderForm #orderStatus').val();
				if(status == '未提交' || (status == '待选箱' && (orderStatus == '已追回' || orderStatus == '已退回'))){
					var url = 'fre-box-require-to-revise-require.do?freightBoxRequireId=' + freightBoxRequireId;
					$.post(url, function(data){
						var freightBoxRequire = data.freightBoxRequire;
						$('#requireToAddForm #freightBoxRequireId').val(freightBoxRequire.id);
						$('#requireToAddForm #beginStation').val(freightBoxRequire.beginStation);
						$('#requireToAddForm #arriveStation').val(freightBoxRequire.arriveStation);
						$('#requireToAddForm #boxSource').val(freightBoxRequire.boxSource);
						$('#requireToAddForm #boxType').val(freightBoxRequire.boxType);
						$('#requireToAddForm #boxBelong').val(freightBoxRequire.boxBelong);
						$('#requireToAddForm #boxCondition').val(freightBoxRequire.boxCondition);
						$('#requireToAddForm #boxCount').val(freightBoxRequire.boxCount);
						$('#requireToAddForm #descn').val(freightBoxRequire.descn);
					});
				}else{
					alert(status + '状态的箱需不能修改！');
				}
			}
		}
		//保存数据
		$(function() {
			$("#requireToAddForm").validate({
		        submitHandler: function(form) {
					var freightOrderId = $('#requireToAddForm #freightOrderId').val();
					var freightBoxRequireId = $('#requireToAddForm #freightBoxRequireId').val();//修改单条费用时使用
					if(freightOrderId == undefined || freightOrderId == ''){
		    			alert('请重新操作!');
		    			return false;
					}
					//箱况多选，需要单独处理
					var data = toJsonString('requireToAddForm');
					var boxCondition = $('#requireToAddForm #boxCondition').val();
					data = data.substring(0, data.length - 1);
					data += ',"boxCondition":"' + boxCondition + '"}';
					var url = 'fre-box-require-done-add-require.do?freightOrderId=' + freightOrderId + '&freightBoxRequireId=' + freightBoxRequireId;
					$.ajax({
		    			type: 'POST',
		    			data: data,
		    			url: url,
		    			contentType: 'application/json',
		    			success:function(data){
		    				if(data != 'success'){
		    					alert('提交失败！请检查是否已有选箱或放箱！');
		    				}
		    				addRequire();
		    			}
		    		});
		        },
		        errorClass: 'validate-error'
			});
		});
		
		//删除箱需
		function deleteRequire(){
			var url = 'fre-box-require-done-remove-require.do?';
			if($('#requireHasAddForm .selectedItemId:checked').length == 0){
				alert('请选择数据！');
				return false;
			}else{
				var flag = true;
				$('#requireHasAddForm .selectedItemId:checked').each(function(i, item){
					var status = $('#requireHasAddForm #' + $(item).val() + 'status').text();
					if(status == '未提交'){
						if(i == 0){
							url += 'freightBoxRequireId=' + $(item).val();
						}else{
							url += '&freightBoxRequireId=' + $(item).val();
						}
					}else{
						flag = false;
						return false;//跳出循环
					}
				});
				if(flag){
					$.post(url, function(data){
						if(data == 'success'){
							addRequire();
						}else{
							alert('删除失败！');
						}
					});
				}else{
					alert('删除失败，请确认所选箱需状态！');
				}
			}
		} */
		
		//放箱，放箱时填写订单号。
		/**
		function releaseRequire(){
			var url = 'fre-box-require-done-release-require.do?';
			if($('#requireHasAddForm .selectedItemId:checked').length == 0){
				alert('请选择数据！');
				return false;
			}else{
				if($('#requireHasAddForm').valid() && window.confirm('提示: 请在备注填写该箱需的提单号！')){
					var flag = true;
					$('#requireHasAddForm .selectedItemId:checked').each(function(i, item){
						var status = $('#requireHasAddForm #' + $(item).val() + 'status').text();
						//var descn = $('#requireHasAddForm #' + $(item).val() + 'descn').val();
						if(status == '未提交'){
							if(i == 0){
								url += 'freightBoxRequireId=' + $(item).val();
							}else{
								url += '&freightBoxRequireId=' + $(item).val();
							}
						}else{
							flag = false;
							return false;//跳出循环
						}
					});
					if(flag){
						$.post(url, $('#requireHasAddForm').serialize(), function(data){
							if(data == 'success'){
								alert('放箱成功！');
								addRequire();
							}else{
								alert('放箱失败！');
							}
						});
					}else{
						alert('放箱失败，请确认箱需状态或提单号是否填报！');
					}
				}
			}
		}
		**/
		</script>
	</body>
</html>
