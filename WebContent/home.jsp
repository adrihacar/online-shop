<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    //If the user_id is already set it means that the user has been redirected to home from the error page.
    //Thus since the user is already logged, he is redirected again to the dashboard 
    if(session.getAttribute("user_id")!= null) response.sendRedirect("/online_shop/dashboard");
    %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Home - Online shop</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="./resources/cover.css" rel="stylesheet">

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
  <body class="text-center">
    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
  <header class="masthead mb-auto">
    <div class="inner">
      <h3 class="masthead-brand">Online Shop</h3>
      <nav class="nav nav-masthead justify-content-center">
        <a class="nav-link active" href="#">Home</a>
        <a class="nav-link" href="./register.jsp">Register</a>
        <a class="nav-link" href="./login.jsp">LogIn</a>
      </nav>
    </div>
  </header>

  <main role="main" class="inner cover">
    <h1 class="cover-heading">Online Shop</h1>
    <p class="lead">Welcome to the best online shop of the internet. Please log in or register to watch all our produrts, buy and sell.</p>
    <p class="lead">
      <a href="./login.jsp" class="btn btn-lg btn-primary">Log In</a>
      <a href="./register.jsp" class="btn btn-lg btn-outline-warning">Sign Up</a>
    </p>
  </main>

  <footer class="mastfoot mt-auto">
    <div class="inner">
      <p>Online shop</p>
    </div>
  </footer>
</div>
</body>
</html>
