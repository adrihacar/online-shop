<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <a class="navbar-brand" style="color: black;">Online Shop</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
        
            <div class="collapse navbar-collapse" id="navbarsExample09">
              <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                  <a class="nav-link" href="./dashboard.jsp">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="./addProduct.jsp">Add product</a>
              </li>
                <li class="nav-item">
                  <a class="nav-link" href="./user-config.jsp">My user</a>
                </li>
              </ul>
              <form class="form-inline my-2 my-md-0">
                <div style="padding-right: 20px;">
                  <a type="button" class="btn btn-outline-warning" href="./user-config.jsp">My cart</a>
                </div>
                <input class="form-control" type="text" placeholder="Search" aria-label="Search">
                <a href="./search.jsp" class="btn btn-primary nav-item">Search</a>
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
                            <li class="cart_item clearfix">
                                <div class="cart_item_image"><img src="https://res.cloudinary.com/dxfq3iotg/image/upload/v1560924153/alcatel-smartphones-einsteiger-mittelklasse-neu-3m.jpg" alt=""></div>
                                <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                    <div class="cart_item_name cart_info_col">
                                        <div class="cart_item_title">Name</div>
                                        <div class="cart_item_text">PRODUCT NAME</div>
                                    </div>
                                    <div class="cart_item_quantity cart_info_col">
                                        <div class="cart_item_title">Quantity</div>
                                        <div class="cart_item_text">3</div>
                                    </div>
                                    <div class="cart_item_price cart_info_col">
                                        <div class="cart_item_title">Price</div>
                                        <div class="cart_item_text">500€</div>
                                    </div>
                                    <div class="cart_item_total cart_info_col">
                                        <div class="cart_item_title">Total</div>
                                        <div class="cart_item_text">1500€</div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="order_total">
                        <div class="order_total_content text-md-right">
                            <div class="order_total_title">Order Total:</div>
                            <div class="order_total_amount">1500€</div>
                        </div>
                    </div>
                    <div class="cart_buttons"> <a href="./dashboard.jsp" class="btn btn-light">Continue Shopping</a> <a href="./checkout.html" class="btn btn-warning">Checkout</a> </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>