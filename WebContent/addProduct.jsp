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

    <title>Online Shop | Register</title>
  </head>
  <body style="background: #8ab8dc;">
    <header>
        <%@ include file="header.jsp" %>
    </header>
 	
		<!-- Default form Add product -->
	<form action='/online_shop/addProduct' enctype="multipart/form-data" method='post' class='text-center p-5 container' style='width:650px; margin-top: 50px;'>
	
	    <p class='h4 mb-4' style="color: white;">Add a Product</p>
	
	    <div class='form-row mb-4'>
	        <div class='col'>
	            <!-- Product name -->
	            <input name='productName' type='text' id='productName' class='form-control' placeholder='Product name'>
            </div>
            <div class='col'>
            <!-- Category -->
                <label style="color: white" for="category">Category:</label>
                <select id="category" name="cattegoryProduct">
                  <option value="0">Home</option>
                  <option value="1">Toys</option>
                  <option value="2">Games</option>
                  <option value="3">Clothes</option>
                </select>
            </div>
	    </div>
	
	    <!-- Description -->
	    <textarea name='description' type='text' id='defaultRegisterFormEmail' class='form-control mb-4' placeholder='Description of the product'></textarea>
	
	     <!-- File Button --> 
<div class="form-group">
    <label class="col-md-4 control-label" style="color: white;" for="filebutton">Product Image</label>
    <div class="col-md-4">
      <input style="color: white;" name="image" class="input-file" type="file">
    </div>
  </div>

        <div  style="padding-top: 20px; color: white;">
        Price: <input style=" border-radius: 4px; background: white; text-align: center; width: 20%;" name="price" type="number" min="1" step="any"/> €
    </div>

	    <!-- Sign up button -->
	    <button class='btn btn-warning my-4 btn-block' type='submit'>Add Product</button>
	
	    <hr>
	
	</form>
 
  


	<!-- Default form register -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>