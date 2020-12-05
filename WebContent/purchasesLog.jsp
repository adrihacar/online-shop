<%@page import="entities.UserCart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "entities.UserCart"    
    import = "java.util.List"
    import = "java.util.ArrayList"
    import = "entities.UserCart"
    import = "org.apache.commons.codec.binary.StringUtils" 
    import = "org.apache.commons.codec.binary.Base64"
    %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <title>Purchases - Online Shop</title>

   <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
   <link href="./resources/bootstrap.min.css" rel="stylesheet">
   <link href="./resources/cart.css" rel="stylesheet">
   <style type="text/css">
   		.empty_cart {
   			font-size: x-large;
   		}
   
   </style>
</head>
<body style="background: #8ab8dc;">
<header>
        <%@ include file="header.jsp" %>
    </header>
<div class="cart_section">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-10 offset-lg-1">
                <div class="cart_container">
                    <div class="cart_title" style="color:white">Your purchases</div>
                    <div class="cart_items">
                    <%
                    	Object purchasesObj = request.getAttribute("purchases");                    		
                        if(purchasesObj == null) {
                		   request.setAttribute("errorMsg", "Not able to load the purchased products!");	
                		   RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
                		   rd.forward(request, response);
                        }
                    	List<UserCart> purchases = (ArrayList<UserCart>) purchasesObj;
                    	if(!purchases.isEmpty()){                    		
                    		
                    		for(int i = 0; i < purchases.size(); i++){ 
                        		UserCart cart = purchases.get(i);%>                    		
                        	                    		
                        		<div class="order_total">
                                <div class="order_total_content text-md-right" style="display: inline-block;">
            					<div class="order_total_title">Date:</div>
            					<div class="order_total_amount margin_right" style="margin-right: 26px;"><%=cart.getDateToString()%></div>
            					<div class="order_total_title">Order Total:</div>
            					<div class="order_total_amount margin_right" style="margin-right: 26px;"><%=cart.getCartPrice(UserCart.PRICE_PRECISION) %>€</div>
            					<div class="order_total_title">Shipping Address:</div>
            						<div class="order_total_amount margin_right" style="margin-right: 26px;"><%=cart.getAddress() %></div>
            	</div>
                            </div>
    		
                        		 <ul class="cart_list">
                                <% for(int j = 0; j < cart.size(); j++){ %>
                                <li class="cart_item clearfix">
                                    <div class="cart_item_image"><img class="bd-placeholder-img card-img-top" src="<% StringBuilder sb = new StringBuilder();
    						sb.append("data:image/png;base64,");
    						sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(cart.getProductImage(j), false)));
    						out.print(sb.toString()); %>"></div>
                                    <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Name</div>
                                            <div class="cart_item_text"><%= cart.getProductName(j)%></div>
                                        </div>
                                        <div class="cart_item_quantity cart_info_col">
                                            <div class="cart_item_title">Quantity</div>
                                            <div class="cart_item_text"><%= cart.getProductQuantity(j)%></div>
                                        </div>
                                        <div class="cart_item_price cart_info_col">
                                            <div class="cart_item_title">Price</div>
                                            <div class="cart_item_text"><%= cart.getProductPrice(j, UserCart.PRICE_PRECISION)%>€</div>
                                        </div>
                                        <div class="cart_item_total cart_info_col">
                                            <div class="cart_item_title">Total</div>
                                            <div class="cart_item_text"><%= cart.getProductTotalCost(j, UserCart.PRICE_PRECISION)%>€</div>                                        
                                        </div>
                                    </div>
                                </li>
                                <%} //End of inner loop%>
                            </ul>                    		                    		
                        	<% 	
                        	}//End of outter loop
                    	}else {%>
                    		<p class="empty_cart">You have not purchased any item yet.</p>
                    	    <p class="empty_cart" >Checkout the feed to find the perfect product for you</p></div>
                    		
                    	<%}%>
                    	

                    </div>                    
                    <div class="cart_buttons"> <a href="./dashboard" class="btn btn-light">Continue Shopping</a> </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>