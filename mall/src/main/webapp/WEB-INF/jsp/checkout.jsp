<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Checkout</title>
<!--/tags -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script>
		addEventListener("load", function () {
			setTimeout(hideURLbar, 0);
		}, false);

		function hideURLbar() {
			window.scrollTo(0, 1);
		}
	</script>
	<!--//tags -->
	<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
	<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="css/font-awesome.css" rel="stylesheet">
	<!--pop-up-box-->
	<link href="css/popuo-box.css" rel="stylesheet" type="text/css" media="all" />
	<!--//pop-up-box-->
	<!-- price range -->
	<link rel="stylesheet" type="text/css" href="css/jquery-ui1.css">
	<!-- fonts -->
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800" rel="stylesheet">
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	
	<!-- //navigation -->
	<!-- banner-2 -->
	<div class="page-head_agile_info_w3l">

	</div>
	<!-- //banner-2 -->
	<!-- page -->
	<div class="services-breadcrumb">
		<div class="agile_inner_breadcrumb">
			<div class="container">
				<ul class="w3_short">
					<li>
						<a href="index.jsp">Home</a>
						<i>|</i>
					</li>
					<li>Checkout</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
	<!-- checkout page -->
	<div class="privacy">
		<div class="container">
			<!-- tittle heading -->
			<h3 class="tittle-w3l">Checkout
				<span class="heading-style">
					<i></i>
					<i></i>
					<i></i>
				</span>
			</h3>
			<!-- //tittle heading -->
			<div class="checkout-right">
				<h4>Your shopping cart contains:
					<span>${cartsList.size()} Products</span>
				</h4>
				<div class="table-responsive">
					<table class="timetable_sub">
						<thead>
							<tr>
								<th>SL No.</th>
								<th>Product</th>
								<th>Quality</th>
								<th>Product Name</th>

								<th>Price</th>
								<th>Remove</th>
							</tr>
						</thead>
						<c:set var="a" value='${cartsList}'></c:set>
						<c:if test="${a!=null}">
						<c:forEach items="${cartsList}" var="c">
						<tr class="rem${c.pid}">
							<td class="invert">${c.pid}</td>
							<td class="invert-image">
								<a href="#">
									<img src="${c.pimg}" alt=" " class="img-responsive">
								</a>
							</td>
							<td class="invert">
								<div class="quantity">
									<div class="quantity-select">
										<div class="entry value-minus">&nbsp;</div>
										<div class="entry value">
											<span>${c.amount}</span>
										</div>
										<div class="entry value-plus active">&nbsp;</div>
									</div>
								</div>
							</td>
							<td class="invert">${c.pname}</td>
							<td class="invert">$${c.pprice}</td>
							<td class="invert">
								<div class="rem">
									<div id="del" name="${c.pid}" class="close${c.pid}"> </div>
								</div>
							</td>
						</tr>
					</c:forEach>
					</c:if>
					</table>
				</div>
			</div>
			<div class="checkout-left">
				<div class="address_form_agile">
					<h4>Add a new Details</h4>
					<c:set var="u" value='${user}'></c:set>
					<c:if test="${u!=null}">
						<form action="payfor" method="post" class="creditly-card-form agileinfo_form">
							<div class="creditly-wrapper wthree, w3_agileits_wrapper">
								<div class="information-wrapper">
									<div class="first-row">
										<div class="controls">
											<input class="billing-address-name" type="text" name="name" placeholder="Full Name" required="" value="${u.uname}">
										</div>
										<div class="w3_agileits_card_number_grids">
											<div class="w3_agileits_card_number_grid_left">
												<div class="controls">
													<input type="text" placeholder="Mobile Number" name="number" required="" value="${u.tel}">
												</div>
											</div>
											<div class="w3_agileits_card_number_grid_right">
												<div class="controls">
													<input type="text" placeholder="Landmark" name="landmark" required="" value="${u.addr}">
												</div>
											</div>
											<div class="clear"> </div>
										</div>
										<div class="controls">
											<input type="text" placeholder="Town/City" name="city" required="">
										</div>
										<div class="controls">
											<select class="option-w3ls">
												<option>Select Address type</option>
												<option>Office</option>
												<option>Home</option>
												<option>Commercial</option>
	
											</select>
										</div>
									</div>
									<button class="submit check_out">Delivery to this Address</button>
								</div>
							</div>
						</form>
						<div class="checkout-right-basket">
							<a href="payment.jsp">Make a Payment
								<span class="fa fa-hand-o-right" aria-hidden="true"></span>
							</a>
						</div>
					</c:if>
					
					<c:if test="${u==null}">
						<form action="payfor" method="post" class="creditly-card-form agileinfo_form">
							<div class="creditly-wrapper wthree, w3_agileits_wrapper">
								<div class="information-wrapper">
									<div class="first-row">
										<div class="controls">
											<input class="billing-address-name" type="text" name="name" placeholder="Full Name" required="">
										</div>
										<div class="w3_agileits_card_number_grids">
											<div class="w3_agileits_card_number_grid_left">
												<div class="controls">
													<input type="text" placeholder="Mobile Number" name="number" required="">
												</div>
											</div>
											<div class="w3_agileits_card_number_grid_right">
												<div class="controls">
													<input type="text" placeholder="Landmark" name="landmark" required="">
												</div>
											</div>
											<div class="clear"> </div>
										</div>
										<div class="controls">
											<input type="text" placeholder="Town/City" name="city" required="">
										</div>
										<div class="controls">
											<select class="option-w3ls">
												<option>Select Address type</option>
												<option>Office</option>
												<option>Home</option>
												<option>Commercial</option>
	
											</select>
										</div>
									</div>
									<button class="submit check_out">Delivery to this Address</button>
								</div>
							</div>
						</form>
						<div class="checkout-right-basket">
							<a href="payment.jsp">Make a Payment
								<span class="fa fa-hand-o-right" aria-hidden="true"></span>
							</a>
						</div>
					</c:if>
					
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!-- //checkout page -->
	<!-- newsletter -->
	<div class="footer-top">
		<div class="container-fluid">
			<div class="col-xs-8 agile-leftmk">
				<h2>Get your Groceries delivered from local stores</h2>
				<p>Free Delivery on your first order!</p>
				<form action="#" method="post">
					<input type="email" placeholder="E-mail" name="email" required="">
					<input type="submit" value="Subscribe">
				</form>
				<div class="newsform-w3l">
					<span class="fa fa-envelope-o" aria-hidden="true"></span>
				</div>
			</div>
			<div class="col-xs-4 w3l-rightmk">
				<img src="images/tab3.png" alt=" ">
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<!-- //newsletter -->
	
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- js-files -->
	<!-- jquery -->
	<script src="js/jquery-2.1.4.min.js"></script>
	<!-- //jquery -->

	<!-- popup modal (for signin & signup)-->
	<script src="js/jquery.magnific-popup.js"></script>
	<script>
		$(document).ready(function () {
			$('.popup-with-zoom-anim').magnificPopup({
				type: 'inline',
				fixedContentPos: false,
				fixedBgPos: true,
				overflowY: 'auto',
				closeBtnInside: true,
				preloader: false,
				midClick: true,
				removalDelay: 300,
				mainClass: 'my-mfp-zoom-in'
			});

		});
	</script>
	<!-- Large modal -->
	<!-- <script>
		$('#').modal('show');
	</script> -->
	<!-- //popup modal (for signin & signup)-->

	<!-- cart-js -->
	<script src="js/minicart.js"></script>
	<script>
		paypalm.minicartk.render(); //use only unique class names other than paypal1.minicart1.Also Replace same class name in css and minicart.min.js

		paypalm.minicartk.cart.on('checkout', function (evt) {
			var items = this.items(),
				len = items.length,
				total = 0,
				i;

			// Count the number of each item in the cart
			for (i = 0; i < len; i++) {
				total += items[i].get('quantity');
			}

			if (total < 3) {
				alert('The minimum order quantity is 3. Please add more to your shopping cart before checking out');
				evt.preventDefault();
			}
		});
	</script>
	<!-- //cart-js -->
	
	<!--quantity-->
	<script>
		$('.value-plus').on('click', function () {
			var divUpd = $(this).parent().find('.value'),
				newVal = parseInt(divUpd.text(), 10) + 1;
			divUpd.text(newVal);
		});

		$('.value-minus').on('click', function () {
			var divUpd = $(this).parent().find('.value'),
				newVal = parseInt(divUpd.text(), 10) - 1;
			if (newVal >= 1) divUpd.text(newVal);
		});
	</script>
	<!--quantity-->
	<c:forEach items="${cartsList}" var="c">
	<script>
		$(document).ready(function (c) {
			$('.close${c.pid}').on('click', function (c) {
				var pid = $("#del").attr("name");
				$.ajax({
					type: 'POST',
					url: 'del',
					data: 'id=' + pid,
					cache: false, //不缓存此页面   
				});	
				$('.rem${c.pid}').fadeOut('slow', function (c) {
					$('.rem${c.pid}').remove();
				});
			});
		});
	</script>
	</c:forEach>
	
	<!--//quantity-->

	<!-- password-script -->
	<script>
		window.onload = function () {
			document.getElementById("password1").onchange = validatePassword;
			document.getElementById("password2").onchange = validatePassword;
		}

		function validatePassword() {
			var pass2 = document.getElementById("password2").value;
			var pass1 = document.getElementById("password1").value;
			if (pass1 != pass2)
				document.getElementById("password2").setCustomValidity("Passwords Don't Match");
			else
				document.getElementById("password2").setCustomValidity('');
			//empty string means no validation error
		}
	</script>
	<!-- //password-script -->

	<!-- smoothscroll -->
	<script src="js/SmoothScroll.min.js"></script>
	<!-- //smoothscroll -->

	<!-- start-smooth-scrolling -->
	<script src="js/move-top.js"></script>
	<script src="js/easing.js"></script>
	<script>
		jQuery(document).ready(function ($) {
			$(".scroll").click(function (event) {
				event.preventDefault();

				$('html,body').animate({
					scrollTop: $(this.hash).offset().top
				}, 1000);
			});
		});
	</script>
	<!-- //end-smooth-scrolling -->

	<!-- smooth-scrolling-of-move-up -->
	<script>
		$(document).ready(function () {
			/*
			var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
			};
			*/
			$().UItoTop({
				easingType: 'easeOutQuart'
			});

		});
	</script>
	<!-- //smooth-scrolling-of-move-up -->

	<!-- for bootstrap working -->
	<script src="js/bootstrap.js"></script>
	<!-- //for bootstrap working -->
	<!-- //js-files -->
</body>
</html>
