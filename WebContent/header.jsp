<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="./resources/album.css" rel="stylesheet">
</head>
<body>
<div class="container" >
          <nav class="navbar navbar-expand-lg navbar-light bg-light rounded">
            <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
        
            <div class="collapse navbar-collapse" id="navbarsExample09">
              <ul class="navbar-nav mr-auto">
                <li class="nav-item ">
                  <a class="btn btn-outline-primary" type="button" href="/online_shop/dashboard" class="nav-link" type='submit'>Online Shop</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="./addProduct.jsp">Add product</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="./Catalog">My products</a>
            </li>
            <li class="nav-item">
                  <a class="nav-link" href="./purchases">My purchases</a>
            </li>
            <li class="nav-item">
                  <a class="nav-link" href="./chatroom">My chats</a>
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
               <div style="padding-left: 25px;">
                  <a class="btn btn-outline-info" href="./editUser">My user</a>
                </div>
                <div style="padding-left: 5px;">
                  <a class="btn btn-outline-danger" href="./logout">Log out</a>
                </div>
            </div>
          </nav>
     </div>
</body>
</html>