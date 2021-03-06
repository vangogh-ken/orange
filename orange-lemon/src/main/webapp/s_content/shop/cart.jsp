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
  <title>cart</title>
  <%@include file="/s_common/s.jsp"%>
</head>

<!-- Body BEGIN -->
<body class="ecommerce">
   <%@include file="/s_common/setting.jsp"%>
   <%@include file="/s_common/header.jsp"%>

    <div class="main">
      <div class="container">
        <!-- BEGIN SIDEBAR & CONTENT -->
        <div class="row margin-bottom-40">
          <!-- BEGIN CONTENT -->
          <div class="col-md-12 col-sm-12">
            <h1>购物车</h1>
            <div class="goods-page">
              <div class="goods-data clearfix">
                <div class="table-wrapper-responsive">
                <table summary="Shopping cart">
                  <tr>
                    <th class="goods-page-image">物品</th>
                    <th class="goods-page-description">描述</th>
                    <th class="goods-page-ref-no">型号</th>
                    <th class="goods-page-quantity">数量</th>
                    <th class="goods-page-price">单价</th>
                    <th class="goods-page-total" colspan="2">小计</th>
                  </tr>
                  <tr>
                    <td class="goods-page-image">
                      <a href="javascript:;"><img src="${ctx}/s_/assets/frontend/pages/img/products/model3.jpg" alt="Berry Lace Dress"></a>
                    </td>
                    <td class="goods-page-description">
                      <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                      <p><strong>Item 1</strong> - Color: Green; Size: S</p>
                      <em>More info is here</em>
                    </td>
                    <td class="goods-page-ref-no">
                      javc2133
                    </td>
                    <td class="goods-page-quantity">
                      <div class="product-quantity">
                          <input id="product-quantity" type="text" value="1" readonly class="form-control input-sm">
                      </div>
                    </td>
                    <td class="goods-page-price">
                      <strong><span>$</span>47.00</strong>
                    </td>
                    <td class="goods-page-total">
                      <strong><span>$</span>47.00</strong>
                    </td>
                    <td class="del-goods-col">
                      <a class="del-goods" href="javascript:;">&nbsp;</a>
                    </td>
                  </tr>
                  <tr>
                    <td class="goods-page-image">
                      <a href="javascript:;"><img src="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" alt="Berry Lace Dress"></a>
                    </td>
                    <td class="goods-page-description">
                      <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                      <p><strong>Item 1</strong> - Color: Green; Size: S</p>
                      <em>More info is here</em>
                    </td>
                    <td class="goods-page-ref-no">
                      javc2133
                    </td>
                    <td class="goods-page-quantity">
                      <div class="product-quantity">
                          <input id="product-quantity2" type="text" value="1" readonly class="form-control input-sm">
                      </div>
                    </td>
                    <td class="goods-page-price">
                      <strong><span>$</span>47.00</strong>
                    </td>
                    <td class="goods-page-total">
                      <strong><span>$</span>47.00</strong>
                    </td>
                    <td class="del-goods-col">
                      <a class="del-goods" href="javascript:;">&nbsp;</a>
                    </td>
                  </tr>
                </table>
                </div>

                <div class="shopping-total">
                  <ul>
                    <li>
                      <em>价格</em>
                      <strong class="price"><span>$</span>47.00</strong>
                    </li>
                    <li>
                      <em>运费</em>
                      <strong class="price"><span>$</span>3.00</strong>
                    </li>
                    <li class="shopping-total-price">
                      <em>总计</em>
                      <strong class="price"><span>$</span>50.00</strong>
                    </li>
                  </ul>
                </div>
              </div>
              <button class="btn btn-default" type="submit">继续购物<i class="fa fa-shopping-cart"></i></button>
              <button class="btn btn-primary" type="submit">去结算<i class="fa fa-check"></i></button>
            </div>
          </div>
          <!-- END CONTENT -->
        </div>
        <!-- END SIDEBAR & CONTENT -->

        <!-- BEGIN SIMILAR PRODUCTS -->
        <div class="row margin-bottom-40">
          <div class="col-md-12 col-sm-12">
            <h2>推荐商品</h2>
            <div class="owl-carousel owl-carousel4">
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-new"></div>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress2</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k3.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k3.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress3</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k4.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k4.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress4</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-sale"></div>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress5</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="${ctx}/s_/assets/frontend/pages/img/products/k2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="${ctx}/s_/assets/frontend/pages/img/products/k2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress6</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- END SIMILAR PRODUCTS -->
      </div>
    </div>
    <%@include file="/s_common/bottom.jsp"%>
	<%@include file="/s_common/footer.jsp"%>
</body>
<!-- END BODY -->
</html>