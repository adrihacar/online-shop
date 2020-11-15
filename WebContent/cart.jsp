<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "entities.ProductBean"
    import = "entities.CartProductBean"
    import = "java.util.List"
    import = "java.util.ArrayList"
    import = "org.apache.commons.codec.binary.StringUtils" 
    import = "org.apache.commons.codec.binary.Base64"
    %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <title>Cart - Online Shop</title>

   <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
   <link href="./resources/bootstrap.min.css" rel="stylesheet">
   <link href="./resources/cart.css" rel="stylesheet">

</head>
<body style="background: #6d2eff;">
<header>
        <div class="container" >
          <nav class="navbar navbar-expand-lg navbar-light bg-light rounded">
            <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
        
            <div class="collapse navbar-collapse" id="navbarsExample09">
              <ul class="navbar-nav mr-auto">
                <li class="nav-item ">
                <form action='/online_shop/dashboard' method='get'>
                  <button class="nav-link" type='submit'>Home </button>
                </form>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="./addProduct.jsp">Add product<span class="sr-only">(current)</span> </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="./user-config.jsp">My user</a>
                </li>
                <li class="nav-item">
			<form action='/online_shop/Catalog' method='get'>
                <button class="nav-link" type='submit'>Catalog</button>
			</form>
            </li>
              </ul>
              <form class="form-inline my-2 my-md-0">
              <div style="padding-right: 20px;">
                <a type="button" class="btn btn-outline-warning" href="./cart">My cart</a>
              </div>
			</form>
			<form class="form-inline my-2 my-md-0" action='/online_shop/Search' method='post'>
                <div>
                <label style="color: white" for="category"></label>
                <select id="category" name="cattegoryProductSearch">
                  <option value="-1">Any</option>
                  <option value="0">Home</option>
                  <option value="1">Toys</option>
                  <option value="2">Games</option>
                  <option value="3">Clothes</option>
                </select>
            </div>
                <input name="sarchText" class="form-control" type="text" placeholder="Search" aria-label="Search">
                <button type="submit" class="btn btn-primary nav-item">Search</button>
              </form>
              
            </div>
          </nav>
    </header>
<div class="cart_section">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-10 offset-lg-1">
                <div class="cart_container">
                    <div class="cart_title" style="color:white">Shopping Cart</div>
                    <div class="cart_items">
                        <ul class="cart_list">
                            <% Object productsObject = request.getAttribute("products");
    	 					List<ProductBean> products = (List<ProductBean>)productsObject;
    	 					Object cartProductsObject = request.getAttribute("cartproducts");
   	 					    List<CartProductBean> cartProducts = (List<CartProductBean>)cartProductsObject;
   	 					    double totalPrice = 0;
         					for(int i = 0; i < products.size(); i++){ %>
                            <li class="cart_item clearfix">
                                <div class="cart_item_image"><img class="bd-placeholder-img card-img-top" src="<% StringBuilder sb = new StringBuilder();
						sb.append("data:image/png;base64,");
						sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(products.get(i).getImage(), false)));
						out.print(sb.toString()); %>"></div>
                                <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                    <div class="cart_item_name cart_info_col">
                                        <div class="cart_item_title">Name</div>
                                        <div class="cart_item_text"><%= products.get(i).getName()%></div>
                                    </div>
                                    <div class="cart_item_quantity cart_info_col">
                                        <div class="cart_item_title">Quantity</div>
                                        <div class="cart_item_text"><%= cartProducts.get(i).getQuantity()%></div>
                                    </div>
                                    <div class="cart_item_price cart_info_col">
                                        <div class="cart_item_title">Price</div>
                                        <div class="cart_item_text"><%= products.get(i).getPrice()%>€</div>
                                    </div>
                                    <div class="cart_item_total cart_info_col">
                                        <div class="cart_item_title">Total</div>
                                        <div class="cart_item_text"><%= cartProducts.get(i).getQuantity() * products.get(i).getPrice()%>€</div>
                                        <% totalPrice = totalPrice + cartProducts.get(i).getQuantity() * products.get(i).getPrice(); %>
                                    </div>
                                </div>
                            </li>
                            <%} %>
                        </ul>
                    </div>
                    <div class="order_total">
                        <div class="order_total_content text-md-right">
                            <div class="order_total_title">Order Total:</div>
                            <div class="order_total_amount"><%= totalPrice %>€</div>
                        </div>
                    </div>
                    <div class="cart_buttons"> <a href="./dashboard" class="btn btn-light">Continue Shopping</a> <a href="./checkout" class="btn btn-warning">Checkout</a> </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>