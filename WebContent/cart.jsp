<%@page import="entities.UserCart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="entities.UserCart"
	import="org.apache.commons.codec.binary.StringUtils"
	import="org.apache.commons.codec.binary.Base64"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Cart - Online Shop</title>

<link rel="canonical"
	href="https://getbootstrap.com/docs/4.5/examples/cover/">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link href="./resources/bootstrap.min.css" rel="stylesheet">
<link href="./resources/cart.css" rel="stylesheet">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script type="text/javascript" src="./resources/index.js"></script>
</head>
<body style="background: #8ab8dc;">
<header>
        <%@ include file="header.jsp" %>
    </header>
							<!-- mPopup box -->
							<div id="popup_alert" class="mpopup">
								<!-- mPopup content -->
								<div class="mpopup-content">
									<div class="mpopup-head">
										<h2 class="alert_title">Caution</h2>
									</div>
									<div class="mpopup-main">
										<p style="font-size: 150%;">Your are about to delete this
											product from your cart. Confirm deletion?</p>
									</div>
									<div class="mpopup-foot">
										<button class="button dismissCallButton">No</button>
										<button class="button aceptCallButton">Yes</button>
									</div>
								</div>
							</div>

							<div class="cart_section">
								<div class="container-fluid">
									<div class="row">
										<div class="col-lg-10 offset-lg-1">
											<div class="cart_container">
												<div class="cart_title" style="color: white">Shopping
													Cart</div>
												<div class="cart_items">
													<ul class="cart_list">
														<%
															Object oCart = session.getAttribute("userCart");
															if (oCart == null) {
																request.setAttribute("errorMsg", "Not able to load the cart!");
																RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
																rd.forward(request, response);
															}

															UserCart userCurrentCart = (UserCart) oCart;

															for (int i = 0; i < userCurrentCart.size(); i++) {
														%>
														<li class="cart_item clearfix">
															<div class="cart_item_image">
																<img class="bd-placeholder-img card-img-top"
																	src="<%StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(userCurrentCart.getProductImage(i), false)));
				out.print(sb.toString());%>">
															</div>
															<div
																class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
																<div class="cart_item_name cart_info_col">
																	<div class="cart_item_title">Name</div>
																	<div class="cart_item_text"><%=userCurrentCart.getProductName(i)%></div>
																</div>
																<div class="cart_item_quantity cart_info_col">
																	<div class="cart_item_title">Quantity</div>
																	<div
																		class="d-flex justify-content-between align-items-center">
																		<div class="btn-group" style="">
																			<div class="input-group mb-3"
																				style="margin-top: 27px;">

																				<form action="/online_shop/cart" method="post"
																					id="form<%=userCurrentCart.getProductId(i)%>"
																					style="display: inline-flex;">
																					<input type="hidden" id="action" name="action"
																						value="resetQuantity"> <input
																						type="hidden" id="product" name="product"
																						value="<%=userCurrentCart.getProductId(i)%>">
																					<input type="number" id="quantity" name="quantity"
																						min="1" class="form-control"
																						value="<%=userCurrentCart.getProductQuantity(i)%>"
																						style="width: 7vw;">
																					<div class="input-group-prepend">
																						<button type="submit"
																							form="form<%=userCurrentCart.getProductId(i)%>"
																							value="Submit" name="idProduct"
																							class="btn btn-outline-secondary"
																							id="button-addon1" style=""
																							onclick="alertBeforeDelete()">Update
																							quantity</button>
																					</div>
																				</form>
																			</div>
																		</div>
																		<span class="delete_item"
																			value="<%=userCurrentCart.getProductId(i)%>">
																			X </span>
																	</div>
																</div>
																<div class="cart_item_price cart_info_col">
																	<div class="cart_item_title">Price</div>
																	<div class="cart_item_text"><%=userCurrentCart.getProductPrice(i, UserCart.PRICE_PRECISION)%>€
																	</div>
																</div>
																<div class="cart_item_total cart_info_col">
																	<div class="cart_item_title">Total</div>
																	<div class="cart_item_text"><%=userCurrentCart.getProductTotalCost(i, UserCart.PRICE_PRECISION)%>€
																	</div>
																</div>
															</div>
														</li>
														<%
															}
														%>
													</ul>
												</div>
												<div class="order_total">
													<div class="order_total_content text-md-right">
														<div class="order_total_title">Order Total:</div>
														<div class="order_total_amount"><%=userCurrentCart.getCartPrice(UserCart.PRICE_PRECISION)%>€
														</div>
													</div>
												</div>
												<div class="cart_buttons">
													<a href="./dashboard" class="btn btn-light">Continue
														Shopping</a> <a href="./checkout" class="btn btn-warning">Checkout</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
</body>