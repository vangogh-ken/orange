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
													<i class="fa fa-camera" onclick="browseDelegate();" title="预览委托"></i><span>&nbsp;</span>
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
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="manipulator">操作员</label>
										  	</td>
											<td>
												<input id="manipulator" type="text" name="manipulator" value="${item.manipulator}" 
												size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
												vClsId="1c1c16c3-744e-11e4-b790-a4db305e5cc5"
												vColumn="DISPLAY_NAME" vFilterId="785abff7-8721-11e4-84b5-a4db305e5cc5"
												readonly="readonly">
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
												disabled="disabled">
													<option value=""></option>
													<option value="外贸" <c:if test="${item.firstType == '外贸'}">selected</c:if> >外贸&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
													<option value="内贸" <c:if test="${item.firstType == '内贸'}">selected</c:if> >内贸</option>
													<option value="其他" <c:if test="${item.firstType == '其他'}">selected</c:if> >其他</option>
												</select>
										  	</td>
											<td>
												 
												<select id="secondType" name="secondType" class="form-control required"
												disabled="disabled">
												<option value="${item.secondType}">${item.secondType}</option>
												</select>
											</td>
										 	<td>
										 	  
										  		<select id="thirdType" name="thirdType" class="form-control"
										  		disabled="disabled">
												<option value="${item.thirdType}">${item.thirdType}</option>
												</select>
										  	</td>
										  	<td>
										  		  
										  		<select id="fourthType" name="fourthType" class="form-control"
										  		disabled="disabled">
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
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="delegateNumber">委托编号</label>
										  	</td>
											<td>
												<input id="delegateNumber" type="text" name="delegateNumber" value="${item.delegateNumber}" 
												size="40" minlength="2" maxlength="50" class="form-control" 
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="delegateContact">委托单位联系人</label>
										  	</td>
											<td>
												<input id="delegateContact" type="text" name="delegateContact" value="${item.delegateContact}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												readonly="readonly">
											</td>
										</tr>
										 <tr>
										 	<td>
										  		<label class="td-label" for="cargoOwner">货主</label>
										  	</td>
											<td>
												<input id="cargoOwner" type="text" name="cargoOwner" value="${item.cargoOwner}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												readonly="readonly">
											</td>
										  	<td>
										  		<label class="td-label" for="cargoName">货品名称</label>
										  	</td>
											<td>
												<input id="cargoName" type="text" name="cargoName" value="${item.cargoName}" 
												size="40" minlength="2" maxlength="50" class="form-control required" 
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="commission">委托书</label>
										  	</td>
											<td>
												<c:if test="${item.commission != null}">
												<a href="fre-order-download-commission.do?freightOrderId=${item.id}" target="_blank">
												${item.delegateNumber == null ? '无委托编号' : item.delegateNumber}
												</a>
												</c:if>
												<c:if test="${item.commission == null}">
												<input id="muiltFile" type="file" name="muiltFile" value="${item.commission}" 
													size="40" minlength="2" maxlength="50" class="form-control <c:if test="${item.commission == null}">required</c:if>" >
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
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="cargoWeight">货品重量(KGS)</label>
										  	</td>
											<td>
												<input id="cargoWeight" type="text" name="cargoWeight" value="${item.cargoWeight}" 
												size="40" minlength="2" maxlength="50" class="form-control required number" 
												readonly="readonly">
											</td>
											<td>
										  		<label class="td-label" for="cargoCapacity">货品体积(CEM)</label>
										  	</td>
											<td>
												<input id="cargoCapacity" type="text" name="cargoCapacity" value="${item.cargoCapacity}" 
												size="40" minlength="2" maxlength="50" class="form-control required number" 
												readonly="readonly">
											</td>
										</tr>
										<tr>
										  	<td>
										  		<label class="td-label" for="descn">备注</label>
										  	</td>
											<td colspan="5">
												<textarea id="descn" name="descn" class="form-control required" style="min-height: 100px;"
												readonly="readonly">${item.descn}</textarea>
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
	<!-- actionField填报界面 -->
	<div id="actionFieldModal" class="modal fade" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">动作信息</h4>
			</div>
			<div class="modal-body">
				<article class="m-widget">
				<form id="actionFieldForm" action="" method="post" class="m-form-blank" onsubmit="return false;"></form>
				</article>
			</div>
			<!-- 箱封详情 -->
			<article class="m-widget">
			<form id="orderBoxSelectForm" action="" method="post" style="height: 450px;overflow-y: scroll;" class="m-form-blank"></form>
			</article>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm default" data-dismiss="modal">关闭</button>
				<button id="actionFieldSubmitBtn" type="button" class="btn btn-sm red" onclick="if(true) $('#actionFieldForm').submit();">保存</button>
				<button id="toPrimeBoxBtn" type="button" class="btn btn-sm green" style="display:none;">提交箱封</button>
				<button id="revisePrimeBoxBtn" type="button" class="btn btn-sm green" style="display:none;">修改箱封</button>
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
                    	addPrime(node.id, true, false);//业务员只能查看
                    //操作范围,查看或者修改说明
                    }else if(node.nodeType == 'maintain'){
                		//业务员查看
                		addFreightMaintainInfo(node.id, true);
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
		
		//预委托，让其他部门补充数据
		function prepareDelegate(){
			var node = zTreeObject.getNodeByTId(selectTreeTId);
			if(node.nodeType != 'action'){
				alert('请选择动作节点预备委！');
				return false;
			}else if(node.delegate != 'T'){
				alert('该动作节点没有委托书！');
				return false;
			}else if(node.internal != 'T'){
				alert('委托不是对内部部门，不能预备委托！');
				return false;
			}else if(node.status != '未执行'){
				alert('该动作已经执行，不能预备委托！');
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
		
	 function addPrime(freightActionId, readonly, ignoreRequire){
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
							//console.log(trCount);
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
					//console.log(html);
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
						//console.log($('#actionFieldForm').html());
						if(!readonly){
							if($('#orderBoxSelectForm table').length > 0){
								if($('#orderBoxSelectForm .selectedItemId:checked').length > 0){
									$('#orderBoxSelectForm').hide(300);//如果有选择的，则说明已经选择过，隐藏
									$('#toPrimeBoxBtn').hide();
									$('#revisePrimeBoxBtn').show();
									
									$('#actionFieldForm').show(300);
									$('#actionFieldSubmitBtn').show();
								}else{
									alert('该动作包含箱封信息，请点击确定，选择具体箱封之后再继续！');
									$('#orderBoxSelectForm').show(300);
									$('#toPrimeBoxBtn').show();
									$('#revisePrimeBoxBtn').hide();
									
									$('#actionFieldForm').hide(300);
									$('#actionFieldSubmitBtn').hide();
								}
							}
						}else{
							$('#orderBoxSelectForm').hide();
							$('#toPrimeBoxBtn').hide();
							$('#revisePrimeBoxBtn').hide();
						}
					}else{
						$('#orderBoxSelectForm').html('');
						$('#toPrimeBoxBtn').hide();
						$('#revisePrimeBoxBtn').hide();
					}
					initDatePicker();//时间格式
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
				//下载
				window.open('fre-delegate-to-view-by-action.do?freightActionId=' + node.id);
			}
		}
		
		function browseDelegate(){
			var node = zTreeObject.getNodeByTId(selectTreeTId);
			if(node.nodeType != 'action'){
				alert('请选择动作生成委托书!');
				return false;
			}else if(node.delegate != 'T'){
				alert('该动作没有委托书!');
				return false;
			}else {
				//预览
				$.post('fre-delegate-to-browse-by-action.do?freightActionId=' + node.id, function(data){
					if(data != null){
						window.open('/VC/convert?fileName=' + data.delegateFile);
					}
				});
			}
		}
		</script>
	</body>
</html>
