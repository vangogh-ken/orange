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
  <title>search-result</title>
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
            <li class="active">搜索结果</li>
        </ul>
        <!-- BEGIN SIDEBAR & CONTENT -->
        <div class="row margin-bottom-40">
          <!-- BEGIN SIDEBAR -->
          <div class="sidebar col-md-3 col-sm-5">
            <div class="sidebar-filter margin-bottom-25">
              <h2>分类</h2>
              <h3>库存</h3>
              <div class="checkbox-list">
                <label><input type="checkbox">缺货 (3)</label>
                <label><input type="checkbox">有货 (26)</label>
              </div>

              <h3>价格</h3>
              <p>
                <label for="amount">范围:</label>
                <input type="text" id="amount" style="border:0; color:#f6931f; font-weight:bold;">
              </p>
              <div id="slider-range"></div>
            </div>

            <div class="sidebar-products clearfix">
              <h2>最多销量</h2>
              <div class="item">
                <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/k1.jpg" alt="Some Shoes in Animal with Cut Out"></a>
                <h3><a href="shop-item.html">Some Shoes in Animal with Cut Out</a></h3>
                <div class="price">$31.00</div>
              </div>
              <div class="item">
                <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/k4.jpg" alt="Some Shoes in Animal with Cut Out"></a>
                <h3><a href="shop-item.html">Some Shoes in Animal with Cut Out</a></h3>
                <div class="price">$23.00</div>
              </div>
              <div class="item">
                <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/k3.jpg" alt="Some Shoes in Animal with Cut Out"></a>
                <h3><a href="shop-item.html">Some Shoes in Animal with Cut Out</a></h3>
                <div class="price">$86.00</div>
              </div>
            </div>
          </div>
          <!-- END SIDEBAR -->
          <!-- BEGIN CONTENT -->
          <div class="col-md-9 col-sm-7">
            <div class="content-search margin-bottom-20">
              <div class="row">
                <div class="col-md-6">
                  <h1>搜索结果 <em>shoes</em></h1>
                </div>
                <div class="col-md-6">
                  <form action="#">
                    <div class="input-group">
                      <input type="text" placeholder="重新搜索" class="form-control">
                      <span class="input-group-btn">
                        <button class="btn btn-primary" type="submit">搜索</button>
                      </span>
                    </div>
                  </form>
                </div>
              </div>
            </div>
            <div class="row list-view-sorting clearfix">
              <div class="col-md-2 col-sm-2 list-view">
                <a href="javascript:;"><i class="fa fa-th-large"></i></a>
                <a href="javascript:;"><i class="fa fa-th-list"></i></a>
              </div>
              <div class="col-md-10 col-sm-10">
                <div class="pull-right">
                  <label class="control-label">过滤:</label>
                  <select class="form-control input-sm">
                    <option value="#?limit=24" selected="selected">24</option>
                    <option value="#?limit=25">25</option>
                    <option value="#?limit=50">50</option>
                    <option value="#?limit=75">75</option>
                    <option value="#?limit=100">100</option>
                  </select>
                </div>
                <div class="pull-right">
                  <label class="control-label">排序:</label>
                  <select class="form-control input-sm">
                    <option value="#?sort=p.sort_order&amp;order=ASC" selected="selected">Default</option>
                    <option value="#?sort=pd.name&amp;order=ASC">Name (A - Z)</option>
                    <option value="#?sort=pd.name&amp;order=DESC">Name (Z - A)</option>
                    <option value="#?sort=p.price&amp;order=ASC">Price (Low &gt; High)</option>
                    <option value="#?sort=p.price&amp;order=DESC">Price (High &gt; Low)</option>
                    <option value="#?sort=rating&amp;order=DESC">Rating (Highest)</option>
                    <option value="#?sort=rating&amp;order=ASC">Rating (Lowest)</option>
                    <option value="#?sort=p.model&amp;order=ASC">Model (A - Z)</option>
                    <option value="#?sort=p.model&amp;order=DESC">Model (Z - A)</option>
                  </select>
                </div>
              </div>
            </div>
            <!-- BEGIN PRODUCT LIST -->
            <div class="row product-list">
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/model1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/model2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model6.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/model6.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress 2</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
            </div>
            <div class="row product-list">
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model5.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model5.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-new"></div>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model3.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model3.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
            </div>
            <div class="row product-list">
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model7.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model7.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
              <!-- PRODUCT ITEM START -->
              <div class="col-md-4 col-sm-6 col-xs-12">
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/model2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                        <a href="${ctx}/s_/assets/frontend/pages/img/products/model2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                        <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-sale"></div>
                </div>
              </div>
              <!-- PRODUCT ITEM END -->
            </div>
            <!-- END PRODUCT LIST -->
            <!-- BEGIN PAGINATOR -->
            <div class="row">
              <div class="col-md-4 col-sm-4 items-info">Items 1 to 9 of 10 total</div>
              <div class="col-md-8 col-sm-8">
                <ul class="pagination pull-right">
                  <li><a href="javascript:;">&laquo;</a></li>
                  <li><a href="javascript:;">1</a></li>
                  <li><span>2</span></li>
                  <li><a href="javascript:;">3</a></li>
                  <li><a href="javascript:;">4</a></li>
                  <li><a href="javascript:;">5</a></li>
                  <li><a href="javascript:;">&raquo;</a></li>
                </ul>
              </div>
            </div>
            <!-- END PAGINATOR -->
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
<!-- END BODY -->
</html>