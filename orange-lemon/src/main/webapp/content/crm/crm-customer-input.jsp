<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>客户信息</title>
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
							<div class="caption"><i class="fa fa-users"></i>客户信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="crm-customer-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<<table class="table-input" style="margin-left: 10%;margin-right: 10%;width: 80%;">
										<thead>
											<tr><th colspan="6">客户信息</th></tr>
										</thead>
										<!-- 设置是否为公司领导对数据类型进行修改 -->
										<c:set var="isManager" value="${userSession.userName == 'FLI' || userSession.userName =='LYY'}"></c:set>
										<tbody>
											<tr>
												<c:if test="${isManager || item.id == null}">
												<td>
													<label class="td-label" for="customerType">类型</label>
												</td>
												<td>
													<select id="customerType" name="customerType" class="form-control required">
														<option value="公海" <c:if test="${item.customerType == '公海'}">selected</c:if>>公海</option>
														
														<option value="跟进" <c:if test="${item.customerType == '跟进'}">selected</c:if> >跟进</option>
														<option value="合作" <c:if test="${item.customerType == '合作'}">selected</c:if> >合作</option>
														
													</select>
												</td>
												</c:if>
												<td>
													<label class="td-label" for="customerName">名称</label>
												</td>
												<td>
													<input id="customerName" type="text" name="customerName" value="${item.customerName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<c:if test="${isManager || item.id == null}">
											<tr>
												<td>
													<label class="td-label" for="customerGrade">等级</label>
												</td>
												<td>
													<select id="customerGrade" name="customerGrade" class="form-control required">
														<option value="普通客户" <c:if test="${item.customerGrade == '普通客户'}">selected</c:if> >普通客户</option>
														<option value="核心客户" <c:if test="${item.customerGrade == '核心客户'}">selected</c:if>>核心客户</option>
														<option value="重要客户" <c:if test="${item.customerGrade == '重要客户'}">selected</c:if> >重要客户</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="creditGrade">信用</label>
												</td>
												<td>
													<select id="creditGrade" name="creditGrade" class="form-control required">
														<option value="无记录" <c:if test="${item.creditGrade == '无记录'}">selected</c:if> >无记录</option>
														<option value="良好" <c:if test="${item.creditGrade == '良好'}">selected</c:if>>良好</option>
														<option value="一般" <c:if test="${item.creditGrade == '一般'}">selected</c:if> >一般</option>
														<option value="差" <c:if test="${item.creditGrade == '差'}">selected</c:if> >差</option>
													</select>
												</td>
											</tr>
											</c:if>
											<tr>
												<td>
													<label class="td-label" for="cargoCondition">经营范围及产品货量</label>
												</td>
												<td>
													<input id="cargoCondition" type="text" name="cargoCondition" value="${item.cargoCondition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="transportType">运输方式</label>
												</td>
												<td>
													<input id="transportType" type="text" name="transportType" value="${item.transportType}" 
													size="40" minlength="2" maxlength="50" class="form-control required dictionary" 
													vAttrId="2015490c-aa30-11e5-a9f6-b870f47f73d5">
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="address">地址</label>
												</td>
												<td>
													<input id="address" type="text" name="address" value="${item.address}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="country">国别</label>
												</td>
												<td>
													<input id="country" type="text" name="country" value="中国" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="province">省份</label>
												</td>
												<td>
													<select id="province" name="province" class="form-control required">
														<option value="四川" <c:if test="${item.province == '四川'}">selected</c:if> >四川</option>
														<option value="北京" <c:if test="${item.province == '北京'}">selected</c:if> >北京</option>
														<option value="天津" <c:if test="${item.province == '天津'}">selected</c:if> >天津</option>
														<option value="上海" <c:if test="${item.province == '上海'}">selected</c:if> >上海</option>
														<option value="重庆" <c:if test="${item.province == '重庆'}">selected</c:if> >重庆</option>
														<option value="河北省" <c:if test="${item.province == '河北省'}">selected</c:if> >河北省</option>
														<option value="河南省" <c:if test="${item.province == '河南省'}">selected</c:if> >河南省</option>
														<option value="云南省" <c:if test="${item.province == '云南省'}">selected</c:if> >云南省</option>
														<option value="辽宁省" <c:if test="${item.province == '辽宁省'}">selected</c:if> >辽宁省</option>
														<option value="黑龙江省" <c:if test="${item.province == '黑龙江省'}">selected</c:if> >黑龙江省</option>
														<option value="湖南省" <c:if test="${item.province == '湖南省'}">selected</c:if> >湖南省</option>
														<option value="安徽省" <c:if test="${item.province == '安徽省'}">selected</c:if> >安徽省</option>
														<option value="山东省" <c:if test="${item.province == '山东省'}">selected</c:if> >山东省</option>
														<option value="新疆维吾尔自治区" <c:if test="${item.province == '新疆维吾尔自治区'}">selected</c:if> >新疆维吾尔自治区</option>
														<option value="江苏省" <c:if test="${item.province == '江苏省'}">selected</c:if> >江苏省</option>
														<option value="浙江省" <c:if test="${item.province == '浙江省'}">selected</c:if> >浙江省</option>
														<option value="江西省" <c:if test="${item.province == '江西省'}">selected</c:if> >江西省</option>
														<option value="湖北省" <c:if test="${item.province == '湖北省'}">selected</c:if> >湖北省</option>
														<option value="广西壮族自治区" <c:if test="${item.province == '广西壮族自治区'}">selected</c:if> >广西壮族自治区</option>
														<option value="甘肃省" <c:if test="${item.province == '甘肃省'}">selected</c:if> >甘肃省</option>
														<option value="陕西省" <c:if test="${item.province == '陕西省'}">selected</c:if> >陕西省</option>
														<option value="山西省" <c:if test="${item.province == '山西省'}">selected</c:if> >山西省</option>
														<option value="内蒙古民族自治区" <c:if test="${item.province == '内蒙古民族自治区'}">selected</c:if> >内蒙古民族自治区</option>
														<option value="吉林省" <c:if test="${item.province == '吉林省'}">selected</c:if> >吉林省</option>
														<option value="福建省" <c:if test="${item.province == '福建省'}">selected</c:if> >福建省</option>
														<option value="贵州省" <c:if test="${item.province == '贵州省'}">selected</c:if> >贵州省</option>
														<option value="广东省" <c:if test="${item.province == '广东省'}">selected</c:if> >广东省</option>
														<option value="青海省" <c:if test="${item.province == '青海省'}">selected</c:if> >青海省</option>
														<option value="西藏自治区" <c:if test="${item.province == '西藏自治区'}">selected</c:if> >西藏自治区</option>
														<option value="宁夏回族自治区" <c:if test="${item.province == '宁夏回族自治区'}">selected</c:if> >宁夏回族自治区</option>
														<option value="海南省" <c:if test="${item.province == '海南省'}">selected</c:if> >海南省</option>
														<option value="台湾省" <c:if test="${item.province == '台湾省'}">selected</c:if> >台湾省</option>
														
													</select>
												</td>
												<td>
													<label class="td-label" for="city">城市</label>
												</td>
												<td>
													<input id="city" type="text" name="city" value="${item.city}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="contactPerson">联系人</label>
												</td>
												<td>
													<input id="contactPerson" type="text" name="contactPerson" value="${item.contactPerson}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="contactPosition">职位</label>
												</td>
												<td>
													<input id="contactPosition" type="text" name="contactPosition" value="${item.contactPosition}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="contactPhone">电话</label>
												</td>
												<td>
													<input id="contactPhone" type="text" name="contactPhone" value="${item.contactPhone}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
												<td>
													<label class="td-label" for="contactMail">邮箱</label>
												</td>
												<td>
													<input id="contactMail" type="text" name="contactMail" value="${item.contactMail}" 
													size="40" minlength="2" maxlength="50" class="form-control required email" >
												</td>
											</tr>
											<!--  
											<tr>
												<td>
													<label class="td-label" for="followerUserId">跟进人</label>
												</td>
												<td colspan="3">
													<select name="followerUserId" id="followerUserId" class="form-control">
										  				<option value=""></option>
										  				<c:forEach items="${users}" var="user">
										  				<option value="${user.id}" <c:if test="${item.follower.id == user.id}">selected="selected"</c:if>>${user.displayName}</option>
										  				</c:forEach>
										  			</select>
												</td>
											</tr>
											-->
										</tbody>
									</table>
								</div>	
								<div class="form-actions fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="col-md-offset-6 col-md-9">
												<button type="submit" class="btn green">保存</button>
												<button type="button" class="btn default" onclick="history.back();">返回</button>
											</div>
										</div>
										<div class="col-md-6">
										</div>
									</div>
								</div>
							</form>
							<!-- END FORM-->
							</div>
					   </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
    
    <%@include file="/common/footer.jsp"%>
    <script type="text/javascript">
    $(function() {
        App.init();
    });
    
    $(function() {
        $("#editDomainForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error',
            rules: {
            	customerName: {
   	                remote: {
   	                    url: 'crm-customer-check-customername.do',
   	                    data: {
   	                        <c:if test="${item.id != null}">
   	                        id: function() {
   	                            return $('#id').val();
   	                        }
   	                        </c:if>
   	                    }
   	                }
   	            }
   	        },
   	        messages: {
   	        	customerName: {
   	                remote: "已存在"
   	            }
   	        }
        });
    });
    
    /* $(function() {
    	new createUserPicker({
    		modalId: 'userPicker',
    		multiple: false,
    		url: '${ctx}/user/user-get-all.do',
    		valInput:'uphold',
    		valType:'displayName'
    	});
    }); */
    </script>
  </body>

</html>
