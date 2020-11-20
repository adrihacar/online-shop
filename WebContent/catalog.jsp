<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import = "entities.ProductBean"
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
    <title>Your Catalog - Online shop</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="./resources/album.css" rel="stylesheet">

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
                <li class="nav-item">
                  <a class="nav-link" href="./addProduct.jsp">Add product</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="./user-config.jsp">My user</a>
                </li>
                <li class="nav-item active">
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
<main role="main" class="back-box">

  <section class="jumbotron text-center back-box" style="color: white;">
    <div class="container">
      <h1>Your Products</h1> 
    </div>
  </section>

  <div class="album py-5 bg-light back-box">
    <div class="container back-box">
     <%
     Object productsObject = request.getAttribute("products");
     if(productsObject == null) {
		request.setAttribute("errorMsg", "Not able to load the products!");	
		RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
		rd.forward(request, response);
	 }
	 List<ProductBean> products = (List<ProductBean>)productsObject;
     if(request.getAttribute("sold").equals("false")){ %>
        <form action='/online_shop/Catalog' method='get'>
           <button type='submit' name="sold" value="true" class="btn btn-dark">Show sold</button>
        </form>
     <% } else { %>
     	<form action='/online_shop/Catalog' method='get'>
           <button type='submit' name="sold" value="false" class="btn btn-dark">Show on sale</button>
        </form>
     <% } %>
     
      <div class="row">
      <% for(int i = 0; i < products.size(); i++){ %>
        <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" src="<% StringBuilder sb = new StringBuilder();
						sb.append("data:image/png;base64,");
						sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(products.get(i).getImage(), false)));
						out.print(sb.toString()); %>">
						 <div class="card-body">
            <% if(request.getAttribute("sold") != null && request.getAttribute("sold").equals("true")){ %>
            	<h4 style="color: red; text-align: end;">SOLD</h4>
            <% } %>
              <h4><%= products.get(i).getName() %></h4>
              <p class="card-text"><%= products.get(i).getDescription() %></p>
              <h5 style="text-align: end"><%= products.get(i).getPrice() %> €</h5>
              <div class="d-flex justify-content-between align-items-center">
			  <form action='/online_shop/editProduct' method='get'>
			  		<input type="hidden" id="idProduct" name="idProduct" value="<%=products.get(i).getId()%>">
                	<button class="btn btn-warning" type="submit" id="button-addon1">Editar</button>
		      </form>
                <form action='/online_shop/deleteProduct' method='post'>
                	<input type="hidden" id="idProduct" name="idProduct" value="<%=products.get(i).getId()%>">
                    <button type='submit' type="button" class="close" aria-label="Close">
 			<span aria-hidden="true" style="color=red">Delete ×</span>
	            </button>
		    </form>
                </div>
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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
      <script>window.jQuery || document.write('<script src=".resources/jquery.slim.min.js"><\/script>')</script><script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
</html>