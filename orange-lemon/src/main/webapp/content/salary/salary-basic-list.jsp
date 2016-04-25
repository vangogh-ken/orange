<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <title>底薪管理</title>
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
							<div class="caption"><i class="fa fa-align-justify"></i>底薪管理</div>
						</div>
						<div class="portlet-body">
							<article class="m-widget" id="btnAcrticle">
								<div class="pull-left">
									<button class="btn btn-sm red" onclick="location.href='salary-basic-input.do'">
										新增<i class="fa fa-arrows"></i></button>
									<button class="btn btn-sm red" onclick="table.removeAll();">
										删除<i class="fa fa-arrows-alt"></i></button>
									<button class="btn btn-sm red" onclick="$('#searchAcrticle').slideToggle(200);">
										查询<i class="fa fa-chevron-down"></i></button>
								</div>
								<div class="pull-right" >
									<label for="pageSize">每页显示</label>
									<select id="pageSize" class="m-page-size"> 
									    <option value="10">10条</option> 
									    <option value="20">20条</option>
									    <option value="50">50条</option>
									 </select>
								</div>
							</article>
							
							<article class="m-widget" id="searchAcrticle">
								<form name="searchForm" method="post" action="salary-basic-list.do" class="form-inline">
								    <label for="displayName">姓名</label>
								    <input type="text" id="displayName" name="displayName" value="${param.displayName}" class="form-control">
								    <label for="salaryYear">年份</label>
								    <select id="salaryYear" name="salaryYear" class="form-control required">
								    	<option value=""></option>
								    	<option value="2016" <c:if test="${param.salaryYear == '2016' }">selected</c:if>>2016</option>
										<option value="2017" <c:if test="${param.salaryYear == '2017' }">selected</c:if>>2017</option>
									</select>
									
									<label for="salaryMonth">月份</label>
								    <select id="salaryMonth" name="salaryMonth" class="form-control required">
								    	<option value=""></option>
								    	<option value="1" <c:if test="${param.salaryYear == '1' }">selected</c:if>>1</option>
										<option value="2" <c:if test="${param.salaryYear == '2' }">selected</c:if>>2</option>
										<option value="3" <c:if test="${param.salaryYear == '3' }">selected</c:if>>3</option>
										<option value="4" <c:if test="${param.salaryYear == '4' }">selected</c:if>>4</option>
										<option value="5" <c:if test="${param.salaryYear == '5' }">selected</c:if>>5</option>
										<option value="6" <c:if test="${param.salaryYear == '6' }">selected</c:if>>6</option>
										<option value="7" <c:if test="${param.salaryYear == '7' }">selected</c:if>>7</option>
										<option value="8" <c:if test="${param.salaryYear == '8' }">selected</c:if>>8</option>
										<option value="9" <c:if test="${param.salaryYear == '9' }">selected</c:if>>9</option>
										<option value="10" <c:if test="${param.salaryYear == '10' }">selected</c:if>>10</option>
										<option value="11" <c:if test="${param.salaryYear == '11' }">selected</c:if>>11</option>
										<option value="12" <c:if test="${param.salaryYear == '12' }">selected</c:if>>12</option>
									</select>
									
									<label for="computeTime">结算时间</label>
								    <input type="text" id="computeTimeStart" name="computeTimeStart" value="${param.computeTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="computeTimeEnd" name="computeTimeEnd" value="${param.computeTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="confirmTime">确认时间</label>
								    <input type="text" id="confirmTimeStart" name="confirmTimeStart" value="${param.confirmTimeStart}" class="form-control input-xsmall datepicker">
								    -
								    <input type="text" id="confirmTimeEnd" name="confirmTimeEnd" value="${param.confirmTimeEnd}" class="form-control input-xsmall datepicker">
								    
								    <label for="status">状态</label>
								    <select id="status" name="status" class="form-control required">
								    	<option value=""></option>
								    	<option value="T" <c:if test="${param.status == 'T' }">selected</c:if>>已发放</option>
										<option value="F" <c:if test="${param.status == 'F' }">selected</c:if>>未发放</option>
										<option value="N" <c:if test="${param.status == 'N' }">selected</c:if>>未填报</option>
									</select>
									<button class="btn btn-sm red" onclick="document.searchForm.submit()">查询<i class="fa fa-search"></i></button>
								 </form>
							</article>
							<article class="m-widget">
									<form id="dynamicGridForm" name="dynamicGridForm" action="salary-basic-remove.do" class="m-form-dynamic">
										<table id="dynamicGrid" class="m-table table-striped table-bordered table-hover">
											<thead>
								                <tr>
								                	<th class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
								                    <th>姓名</th>
								                    <th>等级</th>
								                    <th class="sorting" name="PREDICT_SALARY">预设薪资</th>
								                    <th class="sorting" name="PREDICT_PAYMENT">应发</th>
								                    <th class="sorting" name="PREDICT_DEBIT">应扣</th>
								                    <th class="sorting" name="ACTUAL_PAYMENT">实付</th>
								                    <th class="sorting" name="SALARY_YEAR">年份</th>
								                    <th class="sorting" name="SALARY_MONTH">月份</th>
								                    <th class="sorting" name="COMPUTE_TIME">结算时间</th>
								                    <th class="sorting" name="CONFIRM_TIME">确认时间</th>
								                    <th class="sorting" name="STATUS">状态</th>
								                    <th class="sorting" name="CREATE_TIME">创建时间</th>
								                    <th class="sorting" name="MODIFY_TIME">更新时间</th>
								                    <th>操作</th>
								                </tr>
								            </thead>
								            <tbody>
											<c:forEach items="${pageView.results}" var="item">
								                <tr>
								                	<td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.id}"></td>
								                    <td>${item.salaryGrade.user.displayName}</td>
								                    <td>${item.salaryGrade.gradeCount}</td>
								                    <td>${item.predictSalary}</td>
								                    <td>${item.predictPayment}</td>
								                    <td>${item.predictDebit}</td>
								                    <td>${item.actualPayment}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.computeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								                    </td>
								                    <td>${item.status == 'N' ? '未填报' : item.status == 'F' ? '未发放' : item.status == 'T' ? '已发放' : ''}</td>
								                    <td>
								                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<fmt:formatDate value="${item.modifyTime}" pattern="yyyy-MM-dd"/>
								                    </td>
								                    <td>
								                    	<a href="salary-basic-input.do?id=${item.id}" class="btn btn-sm red">编辑</a>
								                    </td>
								                </tr>
											</c:forEach>
								            </tbody>
										</table>
									</form>
								</article>
					      	</div>
					   </div>
						    <div class="m-page-info pull-left">
							  共100条记录 显示1到10条记录
							</div>
							<div class="btn-group m-pagination pull-right">
							  <button class="btn red">&lt;</button>
							  <button class="btn red">1</button>
							  <button class="btn red">&gt;</button>
							</div>
						    <div class="m-clear"></div>
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
	
	var config = {
	 	    id: 'dynamicGrid',
	 	    pageNo: ${pageView.pageNo},
	 	    pageSize: ${pageView.pageSize},
	 	    totalCount: ${pageView.totalCount},
	 	    resultSize: ${pageView.resultSize},
	 	    pageCount: ${pageView.pageCount},
	 	    orderBy: '${pageView.orderBy == null ? "" : pageView.orderBy}',
	 	    asc: ${pageView.asc},
	 	    params: {
	 	    	'displayName': '${param.displayName}',
	 	       	'salaryYear': '${param.salaryYear}',
	 	      	'computeTimeStart': '${param.computeTimeStart}',
	 	     	'computeTimeEnd': '${param.computeTimeEnd}',
	 	    	'confirmTimeStart': '${param.confirmTimeStart}',
	 	   		'confirmTimeEnd': '${param.confirmTimeEnd}',
	 	        'status': '${param.status}'
	 	    },
	 		selectedItemClass: 'selectedItem',
	 		gridFormId: 'dynamicGridForm',
	 		exportUrl: 'fre-box-export.do'
	};
	
	var table;
	
	$(function() {
		table = new Table(config);
	    table.configPagination('.m-pagination');
	    table.configPageInfo('.m-page-info');
	    table.configPageSize('.m-page-size');
	});
    </script>
  </body>

</html>
