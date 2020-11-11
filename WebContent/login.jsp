<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Online shop| Login</title>
  </head>
  <body oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
  <!-- Default form login -->
	<form action='/online_shop/login' method='post' class="text-center border border-light p-5 container" style="width:450px; margin-top: 100px;">
	
	    <p class="h4 mb-4">Sign in</p>
		<%
		String status = request.getParameter("status");
	
	    if(status!=null && status.equalsIgnoreCase("error")){%>
	  	 <div class="alert alert-danger" role="alert">Email or password not correct</div>
		<!-- <h3 style='color:red;margin:auto;'>Email or password incorrect</h3> -->
		 <%} %> 
	    <!-- Email -->
	    <input type="email" name="Email" id="defaultLoginFormEmail" class="form-control mb-4" placeholder="E-mail">
	
	    <!-- Password -->
	    <input type="password" name="Password" id="defaultLoginFormPassword" class="form-control mb-4" placeholder="Password">
	
	    <div class="d-flex justify-content-around">
	        <div>
	            <!-- Forgot password -->
	            <a>Forgot password?</a>
	        </div>
	    </div>
	
	    <!-- Sign in button -->
	    <button class="btn btn-info btn-block my-4" type="submit">Sign in</button>
	
	    <!-- Register -->
	    <p>Not a member?
	        <a href="register.jsp">Register</a>
	    </p>
	</form>
	
	
	
	<!-- Default form login -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>