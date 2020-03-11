/*  ---------------------------------------------------
    Template Name: Fashi
    Description: Fashi eCommerce HTML Template
    Author: Colorlib
    Author URI: https://colorlib.com/
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';

(function($) {

	/*------------------
	    Preloader
	--------------------*/
	$(window).on('load', function() {
		$(".loader").fadeOut();
		$("#preloder").delay(200).fadeOut("slow");
	});

	/*------------------
	    Background Set
	--------------------*/
	$('.set-bg').each(function() {
		var bg = $(this).data('setbg');
		$(this).css('background-image', 'url(' + bg + ')');
	});

	/*------------------
		Navigation
	--------------------*/
	$(".mobile-menu").slicknav({
		prependTo : '#mobile-menu-wrap',
		allowParentLinks : true
	});

	/*------------------
	    Hero Slider
	--------------------*/
	$(".hero-items").owlCarousel(
			{
				loop : true,
				margin : 0,
				nav : true,
				items : 1,
				dots : false,
				animateOut : 'fadeOut',
				animateIn : 'fadeIn',
				navText : [ '<i class="ti-angle-left"></i>',
						'<i class="ti-angle-right"></i>' ],
				smartSpeed : 1200,
				autoHeight : false,
				autoplay : true,
			});

	/*------------------
	    Product Slider
	--------------------*/
	$(".product-slider").owlCarousel(
			{
				loop : true,
				margin : 25,
				nav : true,
				items : 4,
				dots : true,
				navText : [ '<i class="ti-angle-left"></i>',
						'<i class="ti-angle-right"></i>' ],
				smartSpeed : 1200,
				autoHeight : false,
				autoplay : true,
				responsive : {
					0 : {
						items : 1,
					},
					576 : {
						items : 2,
					},
					992 : {
						items : 2,
					},
					1200 : {
						items : 3,
					}
				}
			});

	/*------------------
	   logo Carousel
	--------------------*/
	$(".logo-carousel").owlCarousel(
			{
				loop : false,
				margin : 30,
				nav : false,
				items : 5,
				dots : false,
				navText : [ '<i class="ti-angle-left"></i>',
						'<i class="ti-angle-right"></i>' ],
				smartSpeed : 1200,
				autoHeight : false,
				mouseDrag : false,
				autoplay : true,
				responsive : {
					0 : {
						items : 3,
					},
					768 : {
						items : 5,
					}
				}
			});

	/*-----------------------
	   Product Single Slider
	-------------------------*/
	$(".ps-slider").owlCarousel(
			{
				loop : false,
				margin : 10,
				nav : true,
				items : 3,
				dots : false,
				navText : [ '<i class="fa fa-angle-left"></i>',
						'<i class="fa fa-angle-right"></i>' ],
				smartSpeed : 1200,
				autoHeight : false,
				autoplay : true,
			});

	/*------------------
	    CountDown
	--------------------*/
	// For demo preview
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
	var yyyy = today.getFullYear();

	if (mm == 12) {
		mm = '01';
		yyyy = yyyy + 1;
	} else {
		mm = parseInt(mm) + 1;
		mm = String(mm).padStart(2, '0');
	}
	var timerdate = mm + '/' + dd + '/' + yyyy;
	// For demo preview end

	console.log(timerdate);

	// Use this for real timer date
	/* var timerdate = "2020/01/01"; */

	$("#countdown")
			.countdown(
					timerdate,
					function(event) {
						$(this)
								.html(
										event
												.strftime("<div class='cd-item'><span>%D</span> <p>Days</p> </div>"
														+ "<div class='cd-item'><span>%H</span> <p>Hrs</p> </div>"
														+ "<div class='cd-item'><span>%M</span> <p>Mins</p> </div>"
														+ "<div class='cd-item'><span>%S</span> <p>Secs</p> </div>"));
					});

	/*----------------------------------------------------
	 Language Flag js 
	----------------------------------------------------*/
	$(document).ready(function(e) {
		// no use
		try {
			var pages = $("#pages").msDropdown({
				on : {
					change : function(data, ui) {
						var val = data.value;
						if (val != "")
							window.location = val;
					}
				}
			}).data("dd");

			var pagename = document.location.pathname.toString();
			pagename = pagename.split("/");
			pages.setIndexByValue(pagename[pagename.length - 1]);
			$("#ver").html(msBeautify.version.msDropdown);
		} catch (e) {
			// console.log(e);
		}
		$("#ver").html(msBeautify.version.msDropdown);

		// convert
		$(".language_drop").msDropdown({
			roundedBorder : false
		});
		$("#tech").data("dd");
	});
	/*-------------------
		Range Slider
	--------------------- */
	var rangeSlider = $(".price-range"), minamount = $("#minamount"), maxamount = $("#maxamount"), minPrice = rangeSlider
			.data('min'), maxPrice = rangeSlider.data('max');
	rangeSlider.slider({
		range : true,
		min : minPrice,
		max : maxPrice,
		values : [ minPrice, maxPrice ],
		slide : function(event, ui) {
			minamount.val('$' + ui.values[0]);
			maxamount.val('$' + ui.values[1]);
		}
	});
	minamount.val('$' + rangeSlider.slider("values", 0));
	maxamount.val('$' + rangeSlider.slider("values", 1));

	/*-------------------
		Radio Btn
	--------------------- */
	$(".fw-size-choose .sc-item label, .pd-size-choose .sc-item label")
			.on(
					'click',
					function() {
						$(
								".fw-size-choose .sc-item label, .pd-size-choose .sc-item label")
								.removeClass('active');
						$(this).addClass('active');
					});

	/*-------------------
		Nice Select
	--------------------- */
	$('.sorting, .p-show').niceSelect();

	/*------------------
		Single Product
	--------------------*/
	$('.product-thumbs-track .pt').on('click', function() {
		$('.product-thumbs-track .pt').removeClass('active');
		$(this).addClass('active');
		var imgurl = $(this).data('imgbigurl');
		var bigImg = $('.product-big-img').attr('src');
		if (imgurl != bigImg) {
			$('.product-big-img').attr({
				src : imgurl
			});
			$('.zoomImg').attr({
				src : imgurl
			});
		}
	});

	$('.product-pic-zoom').zoom();

	/*-------------------
		Quantity change
	--------------------- */
	// format Number
	function formatNumberString(numberStr) {
		if (typeof numberStr === 'number') {
			numberStr = numberStr.toString();
		}
		return numberStr.replace(/(?=(?:\d{3})+$)(?!^)/g, ',');
	}
	var proQty = $('.pro-qty');
	proQty.prepend('<span class="dec qtybtn">-</span>');
	proQty.append('<span class="inc qtybtn">+</span>');
	proQty
			.on(
					'click',
					'.qtybtn',
					function() {
						var $button = $(this);
						var oldValue = $button.parent().find('input').val();
						var currUrl = /* [[@{/}]] */"";
						var path = window.location.pathname.split("/")[1];
						var inp = $button.parent().find("input");
						var id = inp.attr('id');
						var total = 0;
						if ($button.hasClass('inc')) {
							var newVal = parseFloat(oldValue) + 1;
							$
									.ajax({
										url : currUrl + '/' + path
												+ '/cart/addAjax/' + id
												+ '.html',
										method : 'GET',
										success : function(response) {
											$("#" + id).val(newVal);
											$("#quantitycart" + id)
													.text(newVal);
											var price = $("#price" + id)[0].innerText
													.replace(/,/g, '').split(
															" ")[0];
											$(".subtotal" + id)
													.html(
															formatNumberString(parseFloat(price)
																	* parseFloat(newVal))
																	+ ' VND');
											// $("#subtotalTT" + id).html(
											// formatNumberString(parseFloat(price)
											// * parseFloat(newVal)));
											$("#pricecart" + id)
													.text(
															formatNumberString(parseFloat(price)
																	* parseFloat(newVal)));
											$('#cartInfo > tr')
													.each(
															function(index, div) {
																var getPrice = $(
																		this)
																		.find(
																				'.total-price');
																var totalChild = getPrice[0].innerText
																		.replace(
																				/,/g,
																				'')
																		.split(
																				" ")[0];
																total = parseFloat(total)
																		+ parseFloat(totalChild);
																$(
																		".totalPriceCart")
																		.html(
																				formatNumberString(total)
																						+ ' VND');
																$(
																		"#totalPriceCart")
																		.html(
																				formatNumberString(total)
																						+ ' VND');
															});
											// $('.order-total-price').html(formatNumberString(parseFloat(total)*parseFloat(0.01)+parseFloat(total)));
										}
									})
						} else {
							// Don't allow decrementing below zero
							if (oldValue > 0) {
								var newVal = parseFloat(oldValue) - 1;
								$
										.ajax({
											url : currUrl + '/' + path
													+ '/cart/sub/' + id
													+ '.html',
											method : 'GET',
											success : function(response) {
												$("#" + id).val(newVal);
												$("#quantitycart" + id).text(
														newVal);
												var price = $("#price" + id)[0].innerText
														.replace(/,/g, '')
														.split(" ")[0];
												$(".subtotal" + id)
														.html(
																formatNumberString(parseFloat(price)
																		* parseFloat(newVal))
																		+ ' VND');
												// $("#subtotalTT" + id).html(
												// formatNumberString(parseFloat(price)
												// * parseFloat(newVal)));
												$("#pricecart" + id)
														.text(
																formatNumberString(parseFloat(price)
																		* parseFloat(newVal)));
												$('#cartInfo > tr')
														.each(
																function(index,
																		div) {
																	var getPrice = $(
																			this)
																			.find(
																					'.total-price');
																	var totalChild = getPrice[0].innerText
																			.replace(
																					/,/g,
																					'')
																			.split(
																					" ")[0];
																	total = parseFloat(total)
																			+ parseFloat(totalChild);
																	$(
																			".totalPriceCart")
																			.html(
																					formatNumberString(total)
																							+ ' VND');
																	$(
																			"#totalPriceCart")
																			.html(
																					formatNumberString(total)
																							+ ' VND');
																});
												// $('.order-total-price').html(formatNumberString(parseFloat(total)*parseFloat(0.01)+parseFloat(total)));
											}
										})
								if (newVal <= 0) {
									$("#" + id).remove();
									location.reload();
								}
							} else {
								newVal = 0;
							}
						}
						$button.parent().find('input').val(newVal);
					});

	proQty.on('blur', '.qtyInput', function() {
		var $button = $(this);
		var oldValue = $button.parent().find('input').val();
		var currUrl = /* [[@{/}]] */"";
		var path = window.location.pathname.split("/")[1];
		var inp = $button.parent().find("input");
		var id = inp.attr('id');
		var total = 0;
		if (oldValue > 0) {
			$.ajax({
				url : currUrl + '/' + path + '/cart/add/' + id + '/' + oldValue
						+ '.html',
				method : 'GET',
				success : function(response) {
					$("#" + id).val(oldValue);
					$("#quantitycart" + id).text(oldValue);
					location.reload();
				}
			})
		} else {
			$.ajax({
				url : currUrl + '/' + path + '/cart/remove/' + id + '.html',
				method : 'GET',
				success : function(response) {
					$("#" + id).val(oldValue);
					$("#quantitycart" + id).text(oldValue);
					location.reload();
				}
			})
			$("#" + id).remove();
			location.reload();
		}
	});

})(jQuery);