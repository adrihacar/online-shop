<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "entities.UserBean"
    %>
<!doctype html>
<html lang="es">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Online Shop | Edit User</title>
  </head>
  <body>
	  
 	
		<!-- Default form register -->
	<form action='/online_shop/editUser' method='post' class='text-center border border-light p-5 container' style='width:650px; margin-top: 100px;'>
	
	    <p class='h4 mb-4'>Edit User</p>
		<%
		Object object = request.getAttribute("user");
		UserBean user = (UserBean) object;
		
		%>
		
		
	    <!-- E-mail -->
	    <input name='Email' value="<%=user.getEmail()%>" type='hidden' id='defaultRegisterFormEmail' class='form-control mb-4' placeholder='E-mail'>
		
		<%=user.getEmail()%>"
		
	    <div class='form-row mb-4'>
	        <div class='col'>
	            <!-- First name -->
	            <input name='Name' value="<%=user.getName()%>" type='text' id='defaultRegisterFormFirstName' class='form-control' placeholder='First name'>
	        </div>
	        <div class='col'>
	            <!-- Last name -->
	            <input name='Surname' value="<%=user.getSurname()%>" type='text' id='defaultRegisterFormLastName' class='form-control' placeholder='Last name'>
	        </div>
	    </div>
	
	    <!-- Password -->
	    <input name='Password' type='password' id='defaultRegisterFormPassword' class='form-control' placeholder='Password' aria-describedby='defaultRegisterFormPasswordHelpBlock'>
	    <small iultRegisterFormPaEmaillpBlock'emailss='form-text text-muted mb-4'>
	        At least 8 characters and 1 digit
	    </small>
	    
	     <!-- New Password -->
	    <input name='newPassword' type='password' id='defaultRegisterFormNewPassword' class='form-control' placeholder='newPassword' aria-describedby='defaultRegisterFormPasswordHelpBlock'>
	    
	     <!-- Location -->
		<input name='Location' value="<%= user.getLocation()%>" type='text' id='defaultRegisterFormLocation' class='form-control mb-4' placeholder='Location'>
		
	    <!-- Sign up button -->
	    <button class='btn btn-info my-4 btn-block' type='submit'>Save</button>
	
	    <hr>
	
	    <!-- Terms of service -->
	    <p>By clicking
	        <em>Sign up</em> you agree to our
	        <a href='' target='_blank'>terms of service</a>
	
	</form>
 
  }


	<!-- Default form register -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>