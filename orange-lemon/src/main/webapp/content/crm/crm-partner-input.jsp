<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>渠道信息</title>
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
							<div class="caption"><i class="fa fa-users"></i>渠道信息</div>
						</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
							<form id="editDomainForm" method="post" action="crm-partner-save.do?operationMode=STORE" class="form-horizontal">
								 <c:if test="${item.id != null}">
								  <input id="id" type="hidden" name="id" value="${item.id}">
								 </c:if>
								 <div class="form-body">
									<table class="table-input" style="margin-left: 10%;margin-right: 10%;width: 80%;">
										<thead>
											<tr><th colspan="6">渠道信息</th></tr>
										</thead>
										<tbody>
											<tr>
												<td>
													<label class="td-label" for="partnerType">类型</label>
												</td>
												<td>
													<select id="partnerType" name="partnerType" class="form-control required">
														<option value="外贸海船公司" <c:if test="${item.partnerType == '外贸海船公司'}">selected</c:if>>外贸海船公司</option>
														<option value="内贸海船公司" <c:if test="${item.partnerType == '内贸海船公司'}">selected</c:if> >内贸海船公司</option>
														<option value="江船公司" <c:if test="${item.partnerType == '江船公司'}">selected</c:if> >江船公司</option>
														<option value="拖车公司" <c:if test="${item.partnerType == '拖车公司'}">selected</c:if> >拖车公司</option>
														<option value="报关行" <c:if test="${item.partnerType == '报关行'}">selected</c:if> >报关行</option>
														<option value="上海中转代理" <c:if test="${item.partnerType == '上海中转代理'}">selected</c:if> >上海中转代理</option>
														<option value="订舱代理" <c:if test="${item.partnerType == '订舱代理'}">selected</c:if> >订舱代理</option>
														<option value="铁路承运人" <c:if test="${item.partnerType == '铁路承运人'}">selected</c:if> >铁路承运人</option>
														<option value="报检单位" <c:if test="${item.partnerType == '报检单位'}">selected</c:if> >报检单位</option>
														<option value="港口" <c:if test="${item.partnerType == '港口'}">selected</c:if> >港口</option>
														<option value="快递公司" <c:if test="${item.partnerType == '快递公司'}">selected</c:if> >快递公司</option>
														<option value="政府部门" <c:if test="${item.partnerType == '政府部门'}">selected</c:if> >政府部门</option>
														<option value="堆场及仓库" <c:if test="${item.partnerType == '堆场及仓库'}">selected</c:if> >堆场及仓库</option>
														<option value="其它供应商" <c:if test="${item.partnerType == '其它供应商'}">selected</c:if> >其它供应商</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="partnerName">名称</label>
												</td>
												<td>
													<input id="partnerName" type="text" name="partnerName" value="${item.partnerName}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											
											<tr>
												<td>
													<label class="td-label" for="partnerGrade">等级</label>
												</td>
												<td>
													<select id="partnerGrade" name="partnerGrade" class="form-control required">
														<option value="核心渠道" <c:if test="${item.partnerGrade == '核心渠道'}">selected</c:if>>核心渠道</option>
														<option value="重要常用渠道" <c:if test="${item.partnerGrade == '重要常用渠道'}">selected</c:if> >重要常用渠道</option>
														<option value="重要不常用渠道" <c:if test="${item.partnerGrade == '重要不常用渠道'}">selected</c:if> >重要不常用渠道</option>
														<option value="普通渠道" <c:if test="${item.partnerGrade == '普通渠道'}">selected</c:if> >普通渠道</option>
													</select>
												</td>
												<td>
													<label class="td-label" for="engageScope">经营范围</label>
												</td>
												<td>
													<input id="engageScope" type="text" name="engageScope" value="${item.engageScope}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
												</td>
											</tr>
											<tr>
												<td>
													<label class="td-label" for="coreAdvantage">核心优势</label>
												</td>
												<td colspan="3">
													<input id="coreAdvantage" type="text" name="coreAdvantage" value="${item.coreAdvantage}" 
													size="40" minlength="2" maxlength="50" class="form-control required" >
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
            	partnerName: {
   	                remote: {
   	                    url: 'crm-partner-check-partnername.do',
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
   	        	partnerName: {
   	                remote: "已存在"
   	            }
   	        }
        });
    });
    </script>
  </body>

</html>
