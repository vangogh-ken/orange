<%@page language="java" pageEncoding="UTF-8"%>

<%@include file="/common/taglibs.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<!-- BEGIN TOP BAR -->
    <div class="pre-header">
        <div class="container">
            <div class="row">
                <!-- BEGIN TOP BAR LEFT PART -->
                <div class="col-md-6 col-sm-6 additional-shop-info">
                    <ul class="list-unstyled list-inline">
                        <li><i class="fa fa-phone"></i><span>400 111222</span></li>
                        <!-- BEGIN CURRENCIES -->
                        <li class="shop-currencies">
                        	<!--  
                            <a href="javascript:void(0);">$</a>
                            -->
                            <a href="javascript:void(0);" class="current">¥</a>
                        </li>
                        <!-- END CURRENCIES -->
                        <!-- BEGIN LANGS -->
                        <li class="langs-block">
                            <a href="javascript:void(0);" class="current">中文 </a>
                            <div class="langs-block-others-wrapper"><div class="langs-block-others">
                              <a href="javascript:void(0);">Englist</a>
                            </div></div>
                        </li>
                        <!-- END LANGS -->
                    </ul>
                </div>
                <!-- END TOP BAR LEFT PART -->
                <!-- BEGIN TOP BAR MENU -->
                <div class="col-md-6 col-sm-6 additional-nav">
                    <ul class="list-unstyled list-inline pull-right">
                        <li><a href="account.do">我的达林</a></li>
                        <li><a href="wish-list.do">愿望单</a></li>
                        <li><a href="checkout.do">结算</a></li>
                        <li><a href="login.do">登录</a></li>
                    </ul>
                </div>
                <!-- END TOP BAR MENU -->
            </div>
        </div>        
    </div>
    <!-- END TOP BAR -->

    <!-- BEGIN HEADER -->
    <div class="header">
      <div class="container">
        <a class="site-logo" href="index.do"><img src="${ctx}/s_/assets/frontend/layout/img/logos/logo-shop-red.png" alt="Metronic Shop UI"></a>

        <a href="javascript:void(0);" class="mobi-toggler"><i class="fa fa-bars"></i></a>

        <!-- BEGIN CART -->
        <div class="top-cart-block">
          <div class="top-cart-info">
            <a href="javascript:void(0);" class="top-cart-info-count">3 件商品</a>
            <a href="javascript:void(0);" class="top-cart-info-value">¥1260</a>
          </div>
          <i class="fa fa-shopping-cart"></i>
                        
          <div class="top-cart-content-wrapper">
            <div class="top-cart-content">
              <ul class="scroller" style="height: 250px;">
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
                <li>
                  <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/cart-img.jpg" alt="Rolex Classic Watch" width="37" height="34"></a>
                  <span class="cart-content-count">x 1</span>
                  <strong><a href="shop-item.html">Rolex Classic Watch</a></strong>
                  <em>$1230</em>
                  <a href="javascript:void(0);" class="del-goods">&nbsp;</a>
                </li>
              </ul>
              <div class="text-right">
                <a href="cart.do" class="btn btn-default">去购物车</a>
                <a href="checkout.do" class="btn btn-primary">去结算</a>
              </div>
            </div>
          </div>            
        </div>
        <!--END CART -->

        <!-- BEGIN NAVIGATION -->
        <div class="header-navigation">
          <ul>
            <!--  
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                Woman 
              </a>
              <ul class="dropdown-menu">
                <li class="dropdown-submenu">
                  <a href="list.do">Hi Tops <i class="fa fa-angle-right"></i></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="list.do">Second Level Link</a></li>
                    <li><a href="list.do">Second Level Link</a></li>
                    <li class="dropdown-submenu">
                      <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                        Second Level Link 
                        <i class="fa fa-angle-right"></i>
                      </a>
                      <ul class="dropdown-menu">
                        <li><a href="list.do">Third Level Link</a></li>
                        <li><a href="list.do">Third Level Link</a></li>
                        <li><a href="list.do">Third Level Link</a></li>
                      </ul>
                    </li>
                  </ul>
                </li>
                <li><a href="list.do">Running Shoes</a></li>
                <li><a href="list.do">Jackets and Coats</a></li>
              </ul>
            </li>
            -->
            <li><a href="index.do">主页</a></li>
            <li class="dropdown dropdown-megamenu">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                                                      客厅类
              </a>
              <ul class="dropdown-menu">
                <li>
                  <div class="header-navigation-content">
                    <div class="row">
                      <div class="col-md-4 header-navigation-col">
                        <h4>沙发</h4>
                        <ul>
                          <li><a href="list.do">Astro Trainers</a></li>
                          <li><a href="list.do">Basketball Shoes</a></li>
                          <li><a href="list.do">Boots</a></li>
                          <li><a href="list.do">Canvas Shoes</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>电视柜</h4>
                        <ul>
                          <li><a href="list.do">Base Layer</a></li>
                          <li><a href="list.do">Character</a></li>
                          <li><a href="list.do">Chinos</a></li>
                          <li><a href="list.do">Combats</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>茶几</h4>
                        <ul>
                          <li><a href="list.do">Belts</a></li>
                          <li><a href="list.do">Caps</a></li>
                          <li><a href="list.do">Gloves, Hats and Scarves</a></li>
                        </ul>

                        <h4>Clearance</h4>
                        <ul>
                          <li><a href="list.do">Jackets</a></li>
                          <li><a href="list.do">Bottoms</a></li>
                        </ul>
                      </div>
                      <div class="col-md-12 nav-brands">
                        <ul>
                          <li><a href="list.do"><img title="esprit" alt="esprit" src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg"></a></li>
                          <li><a href="list.do"><img title="gap" alt="gap" src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg"></a></li>
                          <li><a href="list.do"><img title="next" alt="next" src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg"></a></li>
                          <li><a href="list.do"><img title="puma" alt="puma" src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg"></a></li>
                          <li><a href="list.do"><img title="zara" alt="zara" src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg"></a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
            
            <li class="dropdown dropdown-megamenu">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                                                      餐厅类
              </a>
              <ul class="dropdown-menu">
                <li>
                  <div class="header-navigation-content">
                    <div class="row">
                      <div class="col-md-4 header-navigation-col">
                        <h4>餐桌</h4>
                        <ul>
                          <li><a href="list.do">Astro Trainers</a></li>
                          <li><a href="list.do">Basketball Shoes</a></li>
                          <li><a href="list.do">Boots</a></li>
                          <li><a href="list.do">Canvas Shoes</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>椅子</h4>
                        <ul>
                          <li><a href="list.do">Base Layer</a></li>
                          <li><a href="list.do">Character</a></li>
                          <li><a href="list.do">Chinos</a></li>
                          <li><a href="list.do">Combats</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>储物架</h4>
                        <ul>
                          <li><a href="list.do">Belts</a></li>
                          <li><a href="list.do">Caps</a></li>
                          <li><a href="list.do">Gloves, Hats and Scarves</a></li>
                        </ul>

                        <h4>Clearance</h4>
                        <ul>
                          <li><a href="list.do">Jackets</a></li>
                          <li><a href="list.do">Bottoms</a></li>
                        </ul>
                      </div>
                      <div class="col-md-12 nav-brands">
                        <ul>
                          <li><a href="list.do"><img title="esprit" alt="esprit" src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg"></a></li>
                          <li><a href="list.do"><img title="gap" alt="gap" src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg"></a></li>
                          <li><a href="list.do"><img title="next" alt="next" src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg"></a></li>
                          <li><a href="list.do"><img title="puma" alt="puma" src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg"></a></li>
                          <li><a href="list.do"><img title="zara" alt="zara" src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg"></a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
            
            <li class="dropdown dropdown-megamenu">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                                                      卧室类
              </a>
              <ul class="dropdown-menu">
                <li>
                  <div class="header-navigation-content">
                    <div class="row">
                      <div class="col-md-4 header-navigation-col">
                        <h4>床</h4>
                        <ul>
                          <li><a href="list.do">Astro Trainers</a></li>
                          <li><a href="list.do">Basketball Shoes</a></li>
                          <li><a href="list.do">Boots</a></li>
                          <li><a href="list.do">Canvas Shoes</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>床垫</h4>
                        <ul>
                          <li><a href="list.do">Base Layer</a></li>
                          <li><a href="list.do">Character</a></li>
                          <li><a href="list.do">Chinos</a></li>
                          <li><a href="list.do">Combats</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>床头柜</h4>
                        <ul>
                          <li><a href="list.do">Belts</a></li>
                          <li><a href="list.do">Caps</a></li>
                          <li><a href="list.do">Gloves, Hats and Scarves</a></li>
                        </ul>

                        <h4>斗柜</h4>
                        <ul>
                          <li><a href="list.do">Jackets</a></li>
                          <li><a href="list.do">Bottoms</a></li>
                        </ul>
                      </div>
                      <div class="col-md-12 nav-brands">
                        <ul>
                          <li><a href="list.do"><img title="esprit" alt="esprit" src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg"></a></li>
                          <li><a href="list.do"><img title="gap" alt="gap" src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg"></a></li>
                          <li><a href="list.do"><img title="next" alt="next" src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg"></a></li>
                          <li><a href="list.do"><img title="puma" alt="puma" src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg"></a></li>
                          <li><a href="list.do"><img title="zara" alt="zara" src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg"></a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
            
            <li class="dropdown dropdown-megamenu">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                                                     书房类
              </a>
              <ul class="dropdown-menu">
                <li>
                  <div class="header-navigation-content">
                    <div class="row">
                      <div class="col-md-4 header-navigation-col">
                        <h4>Footwear</h4>
                        <ul>
                          <li><a href="list.do">Astro Trainers</a></li>
                          <li><a href="list.do">Basketball Shoes</a></li>
                          <li><a href="list.do">Boots</a></li>
                          <li><a href="list.do">Canvas Shoes</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>Clothing</h4>
                        <ul>
                          <li><a href="list.do">Base Layer</a></li>
                          <li><a href="list.do">Character</a></li>
                          <li><a href="list.do">Chinos</a></li>
                          <li><a href="list.do">Combats</a></li>
                        </ul>
                      </div>
                      <div class="col-md-4 header-navigation-col">
                        <h4>Accessories</h4>
                        <ul>
                          <li><a href="list.do">Belts</a></li>
                          <li><a href="list.do">Caps</a></li>
                          <li><a href="list.do">Gloves, Hats and Scarves</a></li>
                        </ul>

                        <h4>Clearance</h4>
                        <ul>
                          <li><a href="list.do">Jackets</a></li>
                          <li><a href="list.do">Bottoms</a></li>
                        </ul>
                      </div>
                      <div class="col-md-12 nav-brands">
                        <ul>
                          <li><a href="list.do"><img title="esprit" alt="esprit" src="${ctx}/s_/assets/frontend/pages/img/brands/esprit.jpg"></a></li>
                          <li><a href="list.do"><img title="gap" alt="gap" src="${ctx}/s_/assets/frontend/pages/img/brands/gap.jpg"></a></li>
                          <li><a href="list.do"><img title="next" alt="next" src="${ctx}/s_/assets/frontend/pages/img/brands/next.jpg"></a></li>
                          <li><a href="list.do"><img title="puma" alt="puma" src="${ctx}/s_/assets/frontend/pages/img/brands/puma.jpg"></a></li>
                          <li><a href="list.do"><img title="zara" alt="zara" src="${ctx}/s_/assets/frontend/pages/img/brands/zara.jpg"></a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
            
            <li><a href="shop-item.html">特惠购</a></li>
            <li><a href="shop-item.html">我定制</a></li>
            <!--  
            <li><a href="shop-item.html">Kids</a></li>
            <li class="dropdown dropdown100 nav-catalogue">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                New
              </a>
              <ul class="dropdown-menu">
                <li>
                  <div class="header-navigation-content">
                    <div class="row">
                      <div class="col-md-3 col-sm-4 col-xs-6">
                        <div class="product-item">
                          <div class="pi-img-wrapper">
                            <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" class="img-responsive" alt="Berry Lace Dress"></a>
                          </div>
                          <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                          <div class="pi-price">$29.00</div>
                          <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                        </div>
                      </div>
                      <div class="col-md-3 col-sm-4 col-xs-6">
                        <div class="product-item">
                          <div class="pi-img-wrapper">
                            <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/model3.jpg" class="img-responsive" alt="Berry Lace Dress"></a>
                          </div>
                          <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                          <div class="pi-price">$29.00</div>
                          <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                        </div>
                      </div>
                      <div class="col-md-3 col-sm-4 col-xs-6">
                        <div class="product-item">
                          <div class="pi-img-wrapper">
                            <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/model7.jpg" class="img-responsive" alt="Berry Lace Dress"></a>
                          </div>
                          <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                          <div class="pi-price">$29.00</div>
                          <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                        </div>
                      </div>
                      <div class="col-md-3 col-sm-4 col-xs-6">
                        <div class="product-item">
                          <div class="pi-img-wrapper">
                            <a href="shop-item.html"><img src="${ctx}/s_/assets/frontend/pages/img/products/model4.jpg" class="img-responsive" alt="Berry Lace Dress"></a>
                          </div>
                          <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                          <div class="pi-price">$29.00</div>
                          <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
            <li class="dropdown active">
              <a class="dropdown-toggle" data-toggle="dropdown" data-target="#" href="javascript:;">
                Pages 
                
              </a>
                
              <ul class="dropdown-menu">
                <li><a href="shop-index.html">Home Default</a></li>
                <li><a href="shop-index-header-fix.html">Home Header Fixed</a></li>
                <li><a href="shop-index-light-footer.html">Home Light Footer</a></li>
                <li><a href="list.do">Product List</a></li>
                <li><a href="shop-search-result.html">Search Result</a></li>
                <li class="active"><a href="shop-item.html">Product Page</a></li>
                <li><a href="shop-shopping-cart-null.html">Shopping Cart (Null Cart)</a></li>
                <li><a href="shop-shopping-cart.html">Shopping Cart</a></li>
                <li><a href="shop-checkout.html">Checkout</a></li>
                <li><a href="shop-about.html">About</a></li>
                <li><a href="shop-contacts.html">Contacts</a></li>
                <li><a href="shop-account.html">My account</a></li>
                <li><a href="shop-wishlist.html">My Wish List</a></li>
                <li><a href="shop-goods-compare.html">Product Comparison</a></li>
                <li><a href="shop-standart-forms.html">Standart Forms</a></li>
                <li><a href="shop-faq.html">FAQ</a></li>
                <li><a href="shop-privacy-policy.html">Privacy Policy</a></li>
                <li><a href="shop-terms-conditions-page.html">Terms &amp; Conditions</a></li>
              </ul>
            </li>
            <li><a href="index.html" target="_blank">Corporate</a></li>
            <li><a href="onepage-index.html" target="_blank">One Page</a></li>
            <li><a href="http://www.keenthemes.com/preview/metronic/theme/templates/admin/ecommerce_index.html" target="_blank">Admin theme</a></li>
			-->
            <!-- BEGIN TOP SEARCH -->
            
            <li class="menu-search">
              <span class="sep"></span>
              <i class="fa fa-search search-btn"></i>
              <div class="search-box">
                <form action="search-result.do">
                  <div class="input-group">
                    <input type="text" placeholder="关键词" class="form-control">
                    <span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                  </div>
                </form>
              </div> 
            </li>
            <!-- END TOP SEARCH -->
          </ul>
        </div>
        <!-- END NAVIGATION -->
      </div>
    </div>
    <!-- Header END -->