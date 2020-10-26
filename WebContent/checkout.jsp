<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <title>Checkout - Online Shop</title>

   <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/cover/">
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
   <link href="./resources/bootstrap.min.css" rel="stylesheet">
   <link href="form-validation.css" rel="stylesheet">

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
              <a type="button" class="btn btn-outline-warning" href="./cart.jsp">My cart</a>
            </div>
            <input class="form-control" type="text" placeholder="Search" aria-label="Search">
            <a href="./search.jsp" class="btn btn-primary nav-item">Search</a>
          </form>
        </div>
      </nav>
</header>

  <body style="background: #6d2eff; color: azure;">
    <div class="container">
  <div class="py-5 text-center">
    <h2>Checkout</h2>
    <p class="lead">Let's buy what you want! Please, give us the necesary information to make those products yours.</p>
  </div>

  <div class="row">
    <div class="col-md-4 order-md-2 mb-4" style="color: black;">
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span style="color: yellow;">Your cart</span>
      </h4>
      <ul class="list-group mb-3">
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">Product name</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$12</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">Second product</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$8</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">Third item</h6>
            <small class="text-muted">Brief description</small>
          </div>
          <span class="text-muted">$5</span>
        </li>
        
        <li class="list-group-item d-flex justify-content-between">
          <span>Total (USD)</span>
          <strong>$20</strong>
        </li>
      </ul>

   <form METHOD="POST" ACTION="checkout.jsp">
    </div>
    <div class="col-md-8 order-md-1">
      <h3 class="mb-3">User Information</h3>
      <form class="needs-validation" novalidate>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">First name</label>
            <br>
            <input NAME="FirstName" type="text" class="form-control" id="firstName" placeholder="" value="Input Name" required>
            <div class="invalid-feedback">
              Valid first name is required.
            </div>
          </div>
          <div class="col-md-6 mb-3">
            <label for="lastName">Last name</label>
            <input NAME="lastName" type="text" class="form-control" id="lastName" placeholder="" value="Input Surname" required>
            <div class="invalid-feedback">
              Valid last name is required.
            </div>
          </div>
        </div>

        <div class="mb-3">
          <label for="email">Email</label>
          <input NAME="eMail" type="email" class="form-control" id="email" value="Input email">
          <div class="invalid-feedback">
            Please enter a valid email address for shipping updates.
          </div>
        </div>

        <div class="mb-3">
          <label for="address">Address</label>
          <input NAME="address" type="text" class="form-control" id="address" placeholder="Enter here your address" required>
          <div class="invalid-feedback">
            Please enter your shipping address.
          </div>
        </div>

        <div class="row">
          <div class="col-md-5 mb-3">
            <label for="country">Country</label>
            <input NAME="country" type="text" class="form-control" id="country" value="Input country" required>
            <div class="invalid-feedback">
              Please select a valid country.
            </div>
        </div>
        </div>
        

        <h4 class="mb-3">Payment</h4>

        <div class="d-block my-3">
          <div class="custom-control custom-radio">
            <input NAME="credit" id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked required>
            <label class="custom-control-label" style="position:initial;" for="credit">Credit card</label>
          </div>
          <div class="custom-control custom-radio">
            <input NAME="debit" id="debit" name="paymentMethod" type="radio" class="custom-control-input" required>
            <label class="custom-control-label" style="position:initial;" for="debit">Debit card</label>
          </div>
          <div class="custom-control custom-radio">
            <input NAME="payPal" id="paypal" name="paymentMethod" type="radio" class="custom-control-input" required>
            <label class="custom-control-label" style="position:initial;" for="paypal">PayPal</label>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="cc-name">Name on card</label>
            <input NAME="nameCard" type="text" class="form-control" id="cc-name" placeholder="" required>
            <small style="color: black;">Full name as displayed on card</small>
            <div class="invalid-feedback">
              Name on card is required
            </div>
          </div>
          <div class="col-md-6 mb-3">
            <label for="cc-number">Credit card number</label>
            <input NAME="numberCard" type="text" class="form-control" id="cc-number" placeholder="" required>
            <div class="invalid-feedback">
              Credit card number is required
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-3 mb-3">
            <label for="cc-expiration">Expiration</label>
            <input NAME="expirationDate" type="text" class="form-control" id="cc-expiration" placeholder="" required>
            <div class="invalid-feedback">
              Expiration date required
            </div>
          </div>
          <div class="col-md-3 mb-3">
            <label for="cc-cvv">CVV</label>
            <input NAME="cvv" type="text" class="form-control" id="cc-cvv" placeholder="" required>
            <div class="invalid-feedback">
              Security code required
            </div>
          </div>
        </div>
        <hr class="mb-4">
        <INPUT TYPE=Submit Value = "Send" href="log.jsp" class="btn btn-warning btn-lg btn-block" type="submit">Checkout</INPUT>
      </form>
    </div>
  </div>
  </form>

  <footer class="my-5 pt-5 text-muted text-center text-small">
    <p class="mb-1">Online Shop</p>
    
  </footer>
</div>

</html>