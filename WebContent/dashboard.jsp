<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import = "entities.ProductBean"
    import = "java.util.List"
    import = "java.util.ArrayList"
    import = "org.apache.commons.codec.binary.StringUtils" 
    import = "org.apache.commons.codec.binary.Base64"
    
    %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Dashboard - Online shop</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="./resources/album.css" rel="stylesheet">
	<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script type="text/javascript" src="./resources/index-dashboard.js"></script>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
  <body>
  
	<header style="background: #8ab8dc">
		<%@ include file="header.jsp" %>
    </header>

<main role="main" class="back-box">

  <section class="jumbotron text-center back-box" style="color: white;">
    <div class="container">
      <h1>Online Shop</h1>
      <p class="lead">Welcome to the best online shop. Here you can look for the product you want.</p>
    </div>
  </section>

  <div class="album py-5 bg-light back-box">
    <div class="container back-box" style="max-width: 1000px;">

      <div class="row">
      <% Object productsObject = request.getAttribute("products");
    		  if(productsObject == null) {
    				request.setAttribute("errorMsg", "Not able to load the products!");	
    				RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
    				rd.forward(request, response);
    			 }
    	 List<ProductBean> products = (List<ProductBean>)productsObject;
         for(int i = 0; i < products.size(); i++){ %>
        <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" src="<% StringBuilder sb = new StringBuilder();
						sb.append("data:image/png;base64,");
						sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(products.get(i).getImage(), false)));
						out.print(sb.toString()); %>">
            <div class="card-body">
              <h4><%= products.get(i).getName() %></h4>
              <p class="card-text"><%= products.get(i).getDescription() %></p>
              <h5 style="text-align: end"><%= products.get(i).getPrice() %> €</h5>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <div class="input-group mb-3">

                    <form style="display: flex;" action='/online_shop/cart' method='post' id="form<%=products.get(i).getId()%>">
                      <input type="hidden" id="action" name="action" value="addToCart">
                      <input type="hidden" id="product" name="product" value="<%=products.get(i).getId()%>">
                      <input style="width: 35%;" type="number" id="quantity" name="quantity" min="1" class="form-control" placeholder="" value="1">
                      <div class="input-group-prepend">
                        <button type="submit" form="form<%=products.get(i).getId()%>" value="Submit" name="idProduct" class="btn btn-outline-secondary" type="button" id="button-addon1">Add to cart</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
              <form id="formChat<%=products.get(i).getSeller()%>" action="/online_shop/chatroom" method="post">
              	<input type="hidden" id="sendTo" name="sendTo" value="<%=products.get(i).getSeller()%>">
              	<!--  <button style="width: 100%;" class="btn btn-outline-secondary openChat" value="<%=products.get(i).getSeller()%>">Chat with the seller</button>-->
              	<button type="submit" form="formChat<%=products.get(i).getSeller()%>" class="btn btn-outline-secondary openChat" >Chat with the seller</button>
              </form>              
            </div>
          </div>
        </div>
        <% } %>
      </div>
    </div>
  </div>

</main>

<footer class="page-footer mt-auto text-center">
  <div class="inner">
    <p>Online shop</p>
  </div>
</footer>
</html>
