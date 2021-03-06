<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/s_common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->

<head>
  <%@include file="/s_common/meta.jsp"%>
  <title>compare</title>
  <%@include file="/s_common/s.jsp"%>
</head>

<!-- Body BEGIN -->
<body class="ecommerce">
    <%@include file="/s_common/setting.jsp"%>
    <%@include file="/s_common/header.jsp"%>
    
    <div class="main">
      <div class="container">
        <ul class="breadcrumb">
            <li><a href="index.html">主页</a></li>
            <li><a href="">商店</a></li>
            <li class="active">心愿单</li>
        </ul>
        <!-- BEGIN SIDEBAR & CONTENT -->
        <div class="row margin-bottom-40">
          <!-- BEGIN SIDEBAR -->
          <div class="sidebar col-md-3 col-sm-5">
            <ul class="list-group margin-bottom-25 sidebar-menu">
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Ladies</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Kids</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Accessories</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Sports</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Brands</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Electronics</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Home & Garden</a></li>
              <li class="list-group-item clearfix"><a href="shop-product-list.html"><i class="fa fa-angle-right"></i> Custom Link</a></li>
            </ul>
          </div>
          <!-- END SIDEBAR -->

          <!-- BEGIN CONTENT -->
          <div class="col-md-9 col-sm-7">
            <h1>商品比对</h1>
            <div class="goods-page">
              <div class="goods-data compare-goods clearfix">
                <div class="table-wrapper-responsive">                
                  <table summary="Product Details">                  
                    <tr>
                      <td class="compare-info">
                        <p> 2  件商品</p>
                      </td>
                      <td class="compare-item">
                        <a href="javascript:;"><img src="${ctx}/s_/assets/frontend/pages/img/products/model3.jpg" alt="Berry Lace Dress"></a>
                        <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                        <strong class="compare-price"><span>$</span>47.00</strong>
                      </td>
                      <td class="compare-item">
                        <a href="javascript:;"><img src="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" alt="Berry Lace Dress"></a>
                        <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                        <strong class="compare-price"><span>$</span>42.00</strong>
                      </td>
                    </tr>

                    <tr>
                      <th colspan="3">
                        <h2>商品参数</h2>
                      </th>
                    </tr>
                    <tr>
                      <td class="compare-info">
                        Attribute
                      </td>
                      <td class="compare-item">
                        Lorem ipsum
                      </td>
                      <td class="compare-item">
                        Duis autem
                      </td>
                    </tr>
                    <tr>
                      <td class="compare-info">
                        Attribute
                      </td>
                      <td class="compare-item">
                        13.00cm x 0.00cm x 0.00cm
                      </td>
                      <td class="compare-item">
                        13.00cm x 0.00cm x 0.00cm
                      </td>
                    </tr>
                    <tr>
                      <td class="compare-info">
                        Attribute
                      </td>
                      <td class="compare-item">
                        110.00g
                      </td>
                      <td class="compare-item">
                        110.00g
                      </td>
                    </tr>

                    <tr>
                      <th colspan="3">
                        <h2>特点</h2>
                      </th>
                    </tr>
                    <tr>
                      <td class="compare-info">
                        Attribute
                      </td>
                      <td class="compare-item">
                        13 cm
                      </td>
                      <td class="compare-item">
                        –
                      </td>
                    </tr>
                    <tr>
                      <td class="compare-info">
                        Attribute
                      </td>
                      <td class="compare-item">
                        In Stock
                      </td>
                      <td class="compare-item">
                        In Stock
                      </td>
                    </tr>
                    <tr>
                      <td class="compare-info">&nbsp;</td>
                      <td class="compare-item">
                        <a class="btn btn-primary" href="javascript:;">加入购物车</a><br>
                        <a class="btn btn-default" href="javascript:;">删除</a>
                      </td>
                      <td class="compare-item">
                        <a class="btn btn-primary" href="javascript:;">加入购物车</a><br>
                        <a class="btn btn-default" href="javascript:;">删除</a>
                      </td>
                    </tr>
                  </table>
                  <p class="padding-top-20"><strong>Notice:</strong> Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam sit nonummy nibh euismod tincidunt ut laoreet dolore magna aliquarm erat sit volutpat. Nostrud exerci tation ullamcorper suscipit lobortis nisl aliquip commodo consequat. </p>
                </div>
              </div>
            </div>
          </div>
          <!-- END CONTENT -->
        </div>
        <!-- END SIDEBAR & CONTENT -->
      </div>
    </div>

    <!-- BEGIN BRANDS -->
    <div class="brands">
      <div class="container">
            <div class="owl-carousel owl-carousel6-brands">
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/canon.jpg" alt="canon" title="canon"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg" alt="esprit" title="esprit"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg" alt="gap" title="gap"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg" alt="next" title="next"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg" alt="puma" title="puma"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg" alt="zara" title="zara"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/canon.jpg" alt="canon" title="canon"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg" alt="esprit" title="esprit"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg" alt="gap" title="gap"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg" alt="next" title="next"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg" alt="puma" title="puma"></a>
              <a href="shop-product-list.html"><img src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg" alt="zara" title="zara"></a>
            </div>
        </div>
    </div>
    <!-- END BRANDS -->
	<%@include file="/s_common/bottom.jsp"%>
	<%@include file="/s_common/footer.jsp"%>
</body>
</html>