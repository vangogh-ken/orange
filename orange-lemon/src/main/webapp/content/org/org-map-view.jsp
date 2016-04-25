<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="/common/meta.jsp"%>
    <title>组织机构信息地图</title>
    <%@include file="/common/s2.jsp"%>
    <link href="${ctx}/s2/assets/plugins/ecotree/ECOTree.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
     #orgEntityForm ul{
     list-style-type: none;
     }
    /**
    #orgEntityForm ul{
    	list-style-type: none;
    	margin: 0;
    	width: 100%;
    	font-size: 18px;
    	font-family:'华文仿宋';
    	font-weight:500;
    	cursor: pointer;
    	display:inline;
    	float: left;"
    }
    
    #orgEntityForm ul li{
    	width: 160px;
    	float: left;
    }
    
    #orgEntityForm ul li ul{
    	list-style-type: none;
    	margin: 0;
    	width: 300px;
    }
    
    #orgEntityForm ul li ul li{
    	width: 160px;
    	float: left;
    }
    
    
    #orgEntityForm ul li ul li ul{
    	list-style-type: none;
    	margin: 0;
    	width: 100%;
    }
    
    #orgEntityForm ul li ul li ul li{
    	width: 180px;
    	float: left;
    }
    **/
    #orgEntityForm{
    	font-size: 18px;
    	font-family:'华文仿宋';
    	font-weight:500;
    	cursor: pointer;
    }
    .parentOrgEntity{
    	position:relative;
    	margin: 20px;
    	padding:20px;
    	width:200px;
    	min-height:400px;
    	text-align: center;
    	border: 1px solid gray;
    }
    .subOrgEntity{
    	position:relative;
    	margin-left:250px;
    	margin: 20px;
    	padding:20px;
    	text-align: center;
    	border: 1px solid gray;
    }
    </style>
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
							<div class="caption"><i class="fa fa-user"></i>组织机构信息地图</div>
						</div>
						<div class="portlet-body">
						<!-- BEGIN FORM-->
							<form id="orgEntityForm" method="post" action="org-entity-save.do?operationMode=STORE" class="form-horizontal">
								  <div id="orgEntityMap">
								  
								  </div>
								  <!--  
								  <ul>
								  <c:forEach items="${orgEntities}" var="orgEntity">
								  	<c:if test="${orgEntity.orgType.typeName=='公司'}">
								  	<li onclick="$('#${orgEntity.id}').toggle(400);"><span class="fa fa-home"></span>${orgEntity.orgEntityName}</li>
								  	<li id="${orgEntity.id}"> 
								  		<ul >
									  		<c:forEach items="${orgEntities}" var="orgEntityChild">
									  		<c:if test="${orgEntityChild.parentOrg.id==orgEntity.id and orgEntityChild.orgType.typeName =='部门'}">
									  			<li onclick="$('#${orgEntityChild.id}').toggle(400);"><span class="fa fa-th"></span>${orgEntityChild.orgEntityName}</li>
									  			<li id="${orgEntityChild.id}">
									  			<ul>
										  			<c:forEach items="${orgEntities}" var="orgEntitySubChild">
											  		<c:if test="${orgEntitySubChild.parentOrg.id==orgEntityChild.id and orgEntitySubChild.orgType.typeName =='小组'}">
											  		<li><span class="fa fa-maxcdn"></span>${orgEntitySubChild.orgEntityName}</li>
											  		</c:if>
											  		</c:forEach>
										  		</ul>
									  			</li>
									  		</c:if>
									  		</c:forEach>
								  		</ul>
								  	</li>
								  	</c:if>
								  </c:forEach>								 
								  </ul>
								  -->
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
    <script src="${ctx}/s2/assets/plugins/ecotree/ECOTree.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var myTree = null;			
    function CreateTree() {
    	myTree = new ECOTree('myTree','orgEntityMap');	
    	myTree.config.colorStyle = ECOTree.CS_LEVEL;
    	myTree.config.nodeFill = ECOTree.NF_FLAT;
    	myTree.config.selectMode = ECOTree.SL_NONE;	
    	myTree.config.iRootOrientation = ECOTree.RO_TOP;
    	myTree.config.iNodeJustification = ECOTree.NJ_TOP;
    	//是否允许给节点加链接，是否允许给节点加图片
    	myTree.config.useTarget = false;                
    	myTree.config.useImg = true;       

   　　		//设置节点的大小和间隔
    	myTree.config.defaultNodeWidth = 95;
    	myTree.config.defaultNodeHeight = 140;
    	myTree.config.iSubtreeSeparation = 50;
    	myTree.config.iSiblingSeparation = 15;
    	myTree.config.iLevelSeparation = 30;

    	//此处通过从数据库或其它地方读取节点信息，生成添加节点的代码
    	//参数前三位是必须的；
    	//第一位是本节点id，
    	//第二位是父节点id、根节点的父节点为-1，
    	//第三位为节点文本；
    	//第四位为节点上显示的图片/照片、图片放到img下并在数据库中记录名称即可，未设参数则取默认图片；
    	//第五位为超链接、最好是访问统一程序传入本节点id；
    	//第六、七位为节点的个性化宽、高。
    	myTree.add('01',-1,'总裁','./img/0.jpg','http://www.jq-school.com');
    	
    	myTree.add('02','01','技术副总裁','./img/1.jpg');
    	myTree.add('03','01','总裁助理','./img/2.jpg','http://www.jq-school.com',95,130);
    	myTree.add('04','01','分公司','./img/3.jpg','http://www.jq-school.com',95,130);

    	myTree.add('0201','02','技术经理','./img/4.jpg','http://www.jq-school.com',95,130);
    	myTree.add('0202','02','技术员','./img/5.jpg','http://www.jq-school.com',95,130);
    	myTree.add('0301','03','秘书','./img/5.jpg','http://www.jq-school.com',95,130);
    	myTree.add('0302','03','助理','./img/6.jpg','http://www.jq-school.com',95,130);
    	myTree.add('0401','04','总经理','./img/6.jpg','http://www.jq-school.com',95,130);
    	myTree.add('0402','04','财务','./img/7.jpg','http://www.jq-school.com',95,130);

    	myTree.UpdateTree();
    }		

    
    $(function() {
        App.init();
        //updateType();
        CreateTree();
    });
    
    $(function() {
        $("#orgEntityForm").validate({
            submitHandler: function(form) {
    			bootbox.animate(false);
    			var box = bootbox.dialog('<div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div>');
                form.submit();
            },
            errorClass: 'validate-error'
        });
    });
    /**
    function updateType(){
    	var val = $('#typeId').val();
    	var type;
    	$('#typeId option').each(function(i, item){
    		if($(item).val() == val){
    			type = $(item).attr('orgType');
    		}
    	});
    	
    	if(type == '小组'){
    		$('#parentOrgId option').each(function(i, item){
    			if($(item).attr('orgType') != '部门'){
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}else if(type == '部门'){
    		$('#parentOrgId option').each(function(i, item){
    			if($(item).attr('orgType') != '公司'){
    				$(item).hide();
    			}else{
    				$(item).show();
    			}
    		});
    	}
    }
    **/
    </script>
  </body>

</html>
