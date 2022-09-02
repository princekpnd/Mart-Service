package com.shop.shopservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesReaderState.OrdRange;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.Delivery;
import com.shop.shopservice.entity.Discount;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.PaymentOrder;
import com.shop.shopservice.entity.ProductList;
import com.shop.shopservice.entity.Review;
import com.shop.shopservice.entity.ShopImage;
import com.shop.shopservice.entity.SlotTime;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserBillBook;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IAddressService;
import com.shop.shopservice.service.IAdminBillBookService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICartService;
import com.shop.shopservice.service.IDeliveryService;
import com.shop.shopservice.service.IDiscountService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.INotificationService;
import com.shop.shopservice.service.IProductListService;
import com.shop.shopservice.service.IProductService;
import com.shop.shopservice.service.IReviewService;
import com.shop.shopservice.service.IShopImageService;
import com.shop.shopservice.service.ISlotTimeService;
import com.shop.shopservice.service.ITransactionService;
import com.shop.shopservice.service.IUserBillBookService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	ICartService cartService;

	@Autowired
	private IDiscountService discountService;

	@Autowired
	private IDeliveryService deliveryService;

	@Autowired
	private ISlotTimeService slotTimeService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IShopImageService shopImageService;

	@Autowired
	private IReviewService reviewService;

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IAdminBillBookService adminBillBookService;

	@Autowired
	private IProductListService productListService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IAddressService addressService;

	@Autowired
	private IItemListService itemListService;
	@Autowired
	private IItemService itemService;

	@Autowired
	private IVariantStockService variantStockService;

	@Autowired
	private IUserBillBookService userBillBookService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;
	LocalData localData = new LocalData();
	/* _____________________________POST SERVICE_____________________________ */

	/*
	 * ---------------------------CART CREATE---------------------------*
	 * Creating/updating cart and adding product to cart and Creating productList
	 * for adding product to cart.
	 */

	@GetMapping("get/acceptedlist/{shopId}")
	public ResponseEntity<List<Cart>> getAcceptCartList(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
//		int accept = orderStatus.getLookUpId();
		List<Cart> cartList = cartService.getAcceptCartList(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/placedList/{shopId}")
	public ResponseEntity<List<Cart>> getAllPlacedCartList(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PLACED");
		List<Cart> cartList = cartService.getAllPlacedCartList(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/packedlist/{shopId}")
	public ResponseEntity<List<Cart>> getAllPackedCartList(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PACKED");
		List<Cart> cartList = cartService.getAllPackedCartList(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/delivered/deliveryboy/{shopId}")
	public ResponseEntity<List<Cart>> getAllDeliveredCart(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
		LookUp deliveryType = lookup.findLookUpIdByName("MILAAN", "BY_DELIVERY_BOY");
		List<Cart> cartList = cartService.getAllDeliveredCart(shopId, orderStatus.getLookUpId(),
				deliveryType.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}
	@GetMapping("get/payment/{orderId}")
	public ResponseEntity<?> getByOrderId(@PathVariable("orderId") String orderId){
		
		RazorpayClient razorpay;
		ObjectMapper mapper = new ObjectMapper();
		
		
//		Order order =null;
		PaymentOrder paymentOrder = new PaymentOrder();
		Order order = new Order(null);
//		JSONObject order = new JSONObject();
		try {
			
//			razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
			razorpay = new RazorpayClient(ServiceConstants.RAZORPAY_KEY, ServiceConstants.RAZORPAY_SECRET);
			
		 order =razorpay.Orders.fetch(orderId);
		 paymentOrder = mapper.readValue(order.toString(), PaymentOrder.class);
//		paymentOrder.setAmount(order.get);
		System.out.println(razorpay.Orders.fetch(orderId));
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Order order = razorpay.orders.fetch(orderId);
//		order =razorpay.orders.fetch(orderId);
//		System.out.println(razorpay);
		return new ResponseEntity<>(paymentOrder, HttpStatus.OK);
	}

	@GetMapping("get/delivered/{shopId}")
	public ResponseEntity<List<Cart>> getAllDeliverdCartByDBoy(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		List<Cart> cartList = cartService.getAllDeliverdCartByDBoy(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/details/fordelivery/{shopId}/{dBoyNumber}")
	public ResponseEntity<List<Cart>> getAllInformationForDeliveryBoy(@PathVariable("shopId") String shopId,
			@PathVariable("dBoyNumber") String dBoyNumber) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		List<Cart> cartList = cartService.getAllInformationForDeliveryBoy(shopId, dBoyNumber,
				orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/reject/{shopId}")
	public ResponseEntity<List<Cart>> getAllRejectedCart(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "REJECTED");
		List<Cart> cartList = cartService.getAllRejectedCartByDBoy(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("check/exitslot/{slotTime}")
	public ResponseEntity<Map<String, String>> checkSlotTime(@PathVariable("slotTime") String slotTime) {
		Map<String, String> response = new HashMap<String, String>();
		boolean cart = cartService.checkSlotTime(slotTime);
		if (cart) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "all ready created");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Not created");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getbyslotdate/{slotTime}")
	public ResponseEntity<List<Cart>> getBySlotDate(@PathVariable("slotTime") String slotTime) {
		List<Cart> cartList = cartService.getBySlotDate(slotTime);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("getby/slotdateanddate/{slotTime}/{slotDate}")
	public ResponseEntity<List<Cart>> getBySlotTimeAndDate(@PathVariable("slotTime") String slotTime,
			@PathVariable("slotDate") String slotDate) {
		slotDate = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("yyyy/MM/dd");
		try {
			slotDate = sdfr.format(slotDate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		List<Cart> cartList = cartService.getBySlotDateAndTime(slotTime, slotDate);

		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("getdate")
	public String getDate() {

//		DateFormat df = new SimpleDateFormat();
//		 LocalDate givenDate = LocalDate.parse(date);
//		 String dateToString
//         = givenDate.toString();

		Cart cart = cartService.getCart(3);
		String date = "2022-03-04";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String str = dateFormat.format(date);
		return str;

	}

//	@GetMapping("getbyslotdate/{slotTime}")
//	public ResponseEntity<List<Cart>> getBySlotDateTest(@PathVariable("slotTime") String slotTime, @PathVariable("slotTime") String slotDate) {
//	 slotDate = null;
//		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
//		try {
//			slotDate = sdfr.format(slotDate);
//		} catch (Exception ex) {
//			System.out.println(ex);
//		}
//		List<Cart> cartList = cartService.getBySlotDateAndTime(slotTime,slotDate); 
//		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
//	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createCart(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		log.info("Request to create user: {}", json.get(ServiceConstants.USER_ID));
		/* -----------------CHECKING MENDATORY VARIABLES----------------- */
		if (null != json.get(ServiceConstants.USER_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.PRODUCT_ID)
				&& null != json.get(ServiceConstants.PRODUCT_QUANTITY)) {

			/* -----------------VARIABLE INITIALIZATION----------------- */

			LookUp deActvLookup = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");

			int orderStatus = deActvLookup.getLookUpId(), payableAmount = 0,
					otp = (int) Math.ceil(Math.random() * 10000),
					productQuantity = Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY));

			float gstAmount = 0, discount = 0, price = 0, totalAmount = 0, deliveryCharge = 0, bundleDiscount = 0,
					productPrice = 0;

			String shopId = json.get(ServiceConstants.SHOP_ID), userId = json.get(ServiceConstants.USER_ID),
					productId = json.get(ServiceConstants.PRODUCT_ID);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate offerTo = null, offerFrom = null;

			Cart cart = null;
			List<Cart> cartList = null;
			// Product product = null;
			ItemList product = null;
			Item item = null;
			Admin admin = adminService.getAdminByShopId(shopId);
			ProductList productList = null;
			Calculation calculation = new Calculation();
			List<ShopImage> shopImage = shopImageService.getShopImageByShopId(shopId);
			// String shop

			product = itemListService.getById(Integer.parseInt(productId));
			item = itemService.getById(product.getProductId());
			User user = UserService.getUser(Integer.parseInt(userId));
//			if(product.isGstIncluded()) {
//				price = product.getSellingPrice() * productQuantity;
//				gstAmount = product.getGstAmount() * productQuantity;
//			}
			productPrice = product.getUnitSellingPrice();
			int itemId = product.getProductId();
			if (product.getStock() >= productQuantity) {

				boolean result = cartService.cartExists(userId, shopId, orderStatus);
				if (result) {
					cartList = cartService.getCartByUserId(userId, shopId, orderStatus);
					cart = cartList.get(0);

					price = calculation.DecimalCalculation(product.getUnitSellingPrice() * productQuantity);
					gstAmount = calculation.DecimalCalculation(product.getGstAmount() * productQuantity);
					discount = calculation.DecimalCalculation(product.getMrp() - product.getUnitSellingPrice());
					totalAmount = calculation.DecimalCalculation((product.getUnitSellingPrice() * productQuantity));
					bundleDiscount = calculation.DecimalCalculation(product.getBundleCustomerDiscount());
					payableAmount = (int) Math.ceil(cart.getTotalAmount() + totalAmount);
					if (payableAmount < 499 && payableAmount > 0) {
						deliveryCharge = 20;
						payableAmount = (int) Math.ceil(cart.getTotalAmount() + totalAmount + deliveryCharge);
					}

					boolean productList1 = productListService.getProductByProductId(productId, cart.getCartId());

					if (productList1) {

					} else {
						productList = new ProductList(cart.getCartId(), shopId);

						productList.setUserId(userId);
						productList.setShopId(shopId);
						productList.setMrp(product.getMrp());
						productList.setProductQuantity(productQuantity);
						productList.setProductName(product.getName());
						productList.setProductId(json.get(ServiceConstants.PRODUCT_ID));
						productList.setItemId(itemId);
						productList.setPrice(price);
						productList.setOffersAvailable(product.isOfferActiveInd());
						productList.setTotalAmount(totalAmount);
						productList.setPackSize(product.getUnitQuantity());
						productList.setBarcode(product.getBarcode());
						productList.setHsnCode(product.getHsnCode());
//						if(!item.getProductImage().equals("")) {
//							productList.setProductImage(item.getProductImage());
//						}
						if (null != item.getProductImage() && Strings.isNotBlank(item.getProductImage())) {
							productList.setProductImage(item.getProductImage());
						}
//						response.put("Image", item.getProductImage());

//						if (product.isOfferActiveInd()) {
//							offerFrom = product.getOfferFrom();
//							offerTo = product.getOfferTo();
//							productList.setOffer((int)(product.getCustomerSingleOffer()));
//							productList.setDiscount(discount);
//							productList.setOfferFrom(offerFrom);
//							productList.setOfferTo(offerTo);
//							productList.setBundleDiscount(bundleDiscount);
//						}

						productList.setOffer((int) (product.getCustomerSingleOffer()));
						productList.setDiscount(discount);
						productList.setBundleDiscount(bundleDiscount);

						productList.setOldPrice(product.getMrp());
						productList.setDeliveryCharge(deliveryCharge);
						productList.setMeasurement(product.getMeasurement());
						productList.setGstAmount(gstAmount);
						productList.setDeleted(Boolean.FALSE);
						productList.setCreatedOn(new Date());
						productList.setCartId(cart.getCartId());
						productList.setActive(Boolean.TRUE);
						productList.setDateOfManufacturing(product.getDateOfManufacturing());
						productList.setDateOfExpire(product.getDateOfExpire());
						productList.setGstPercent(product.getGstPercent());
						productList.setCustomerBundleOffer(product.getCustomerBundleOffer());
						productList.setCustomerSingleOffer(product.getCustomerSingleOffer());
						productList.setBundleQuantity(product.getBundleQuantity());
						productList.setBundlePrice(product.getBundlePrice());
						productList.setUnitSellingPrice(product.getUnitSellingPrice());

						cart.setTotalAmount(calculation.DecimalCalculation(cart.getTotalAmount() + totalAmount));
						cart.setGstAmount(calculation.DecimalCalculation(cart.getGstAmount() + gstAmount));
						cart.setDues(calculation.DecimalCalculation(cart.getDues() + payableAmount));
						cart.setPayableAmount(payableAmount);
						cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() + discount));
						cart.setBundleDiscount(
								calculation.DecimalCalculation(cart.getBundleDiscount() + bundleDiscount));
						cart.setDeliveryCharge(deliveryCharge);
						cart.setPrice(calculation.DecimalCalculation(cart.getPrice() + price));
						cart.setShopName(admin.getShopName());
						cart.setUserName(user.getName());
						cart.setMobileNo(user.getMobileNo());
						cart.setMrp(calculation.DecimalCalculation(cart.getMrp() + product.getMrp()));

						boolean finalResult = cartService.updateCart(cart);

						boolean result1 = productListService.createProductList(productList);
						response.put("status", Strings.EMPTY + result1);
						response.put("description",
								"ProductList created successfully with given Shop Id , go through your inbox to activate");
					}

				} else {
					cart = new Cart(userId, shopId);
					gstAmount = calculation.DecimalCalculation(product.getGstAmount() * productQuantity);
//					discount = calculation.DecimalCalculation(product.getCustomerSingleOffer() * productQuantity);
					discount = calculation.DecimalCalculation(product.getMrp() - product.getUnitSellingPrice());
					totalAmount = calculation.DecimalCalculation((product.getUnitSellingPrice() * productQuantity));
					bundleDiscount = calculation.DecimalCalculation(product.getBundleCustomerDiscount());
					payableAmount = (int) Math.ceil(totalAmount);
					if (payableAmount < 499 && payableAmount > 0) {
						deliveryCharge = 20;
						payableAmount = (int) Math.ceil(totalAmount + deliveryCharge);
					}

					price = calculation.DecimalCalculation(product.getUnitSellingPrice() * productQuantity);

					cart.setTotalAmount(totalAmount);
					cart.setOffline(Boolean.FALSE);
					cart.setGstAmount(gstAmount);
					cart.setCreatedOn(new Date());
					cart.setOrderStatus(orderStatus);
					cart.setDues(payableAmount);
					cart.setPaid(0);
					cart.setShopId(shopId);
					cart.setUserId(userId);
					cart.setMrp(product.getMrp());
					cart.setActive(Boolean.TRUE);
					cart.setDeleted(Boolean.FALSE);
					// cart.setCurrentAddress(currentAddress);
					cart.setDiscount(discount);
					cart.setBundleDiscount(bundleDiscount);
					cart.setDeliveryCharge(deliveryCharge);
					cart.setShopName(admin.getShopName());
					cart.setUserName(user.getName());
					cart.setMobileNo(user.getMobileNo());
					cart.setUserImage(user.getAvatar());
					cart.setAdminId(String.valueOf(admin.getAdminId()));
					cart.setPayableAmount(payableAmount);
					cart.setOtp(otp);
					cart.setPrice(price);
					cart.setInsideShop(Boolean.FALSE);
					cart.setProductReturn(Boolean.FALSE);

					boolean finalResult = cartService.createCart(cart);

					if (finalResult) {
						result = cartService.cartExists(userId, shopId, orderStatus);
						if (result) {
							cartList = cartService.getCartByUserId(userId, shopId, orderStatus);
							cart = cartList.get(0);
							productList = new ProductList(cart.getCartId(), shopId);

							productList.setUserId(userId);
							productList.setShopId(shopId);
							productList.setProductQuantity(productQuantity);
							productList.setProductName(product.getName());
							productList.setProductId(productId);
							productList.setItemId(itemId);
							productList.setPrice(product.getUnitSellingPrice() * productQuantity);
							productList.setOffersAvailable(product.isOfferActiveInd());
//							if (product.isOfferActiveInd()) {
//
//								productList.setOffer(product.getOfferPercent());
//								productList.setOfferFrom(product.getOfferFrom());
//								productList.setOfferTo(product.getOfferTo());
//								productList.setDiscount(discount);
//								productList.setBundleDiscount(bundleDiscount);
//							}

							productList.setOffer((int) (product.getCustomerSingleOffer()));
							productList.setDiscount(discount);
							productList.setBundleDiscount(bundleDiscount);

							if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
								productList.setDeliveryCharge(
										Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE)));
							}
							productList.setOldPrice(product.getMrp() * productQuantity);
							productList.setMeasurement(product.getMeasurement());
							productList.setGstAmount(product.getGstAmount());
							productList.setDeleted(Boolean.FALSE);
							productList.setMrp(product.getMrp());
							productList.setCreatedOn(new Date());
							productList.setCartId(cart.getCartId());
							productList.setActive(Boolean.TRUE);
							productList.setTotalAmount(totalAmount);
							productList.setPackSize(product.getUnitQuantity());
							productList.setDateOfManufacturing(product.getDateOfManufacturing());
							productList.setDateOfExpire(product.getDateOfExpire());
							productList.setStock(product.getStock() - productQuantity);
							productList.setGstPercent(product.getGstPercent());
							productList.setBarcode(product.getBarcode());
							productList.setHsnCode(product.getHsnCode());
							productList.setCustomerBundleOffer(product.getCustomerBundleOffer());
							productList.setCustomerSingleOffer(product.getCustomerSingleOffer());
							productList.setBundleQuantity(product.getBundleQuantity());
							productList.setBundlePrice(product.getBundlePrice());
							productList.setUnitSellingPrice(product.getUnitSellingPrice());
							if (null != item.getProductImage() && Strings.isNotBlank(item.getProductImage())) {
								productList.setProductImage(item.getProductImage());
							}

							boolean result1 = productListService.createProductList(productList);
							response.put("status", Strings.EMPTY + result1);
							response.put("description", "ProductList created successfully with given Shop ID.");
						}
					}
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product is out of stock");
			}

		}
		return ResponseEntity.ok().body(response);

	}

	/* _____________________________GET SERVICE_____________________________ */

	/* _____________________________PUT SERVICE_____________________________ */

	/*----------------------INCREASE CART PRODUCT----------------------
	 * Here increasing product list quantity and updating cart data.
	 * */

	@PutMapping("cartincrease/{cartId}/{productId}")
	public ResponseEntity<Map<String, String>> getCartByCartIdAndProductId(@PathVariable("cartId") int cartId,
			@PathVariable("productId") String productId) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getCart(cartId);
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		// Product product =
		// productService.getProduct(Integer.parseInt(productId));//itemlist
		ItemList product = itemListService.getById(Integer.parseInt(productId));
		Calculation calculation = new Calculation();
		int quantity = productList.getProductQuantity() + 1;
		float price = product.getUnitSellingPrice() * quantity, gstAmount = product.getGstAmount() * quantity,
				singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),

				deliveryCharge = 0, totalAmount = 0, bundleDiscount = product.getBundleCustomerDiscount(),
				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
				mrp = quantity * product.getMrp();
		int payableAmount;

//		if (product.getBundleQuantity() <= quantity) {
//			price = product.getBundlePrice() * quantity;
//			gstAmount = product.getBundleGstAmount() * quantity;
//			discount = product.getCustomerBundleOffer() * quantity;
//			bundleDiscount = product.getBundleCustomerDiscount() * quantity;
//		}
		if (product.getStock() > quantity) {
			if (bundleQuantity <= quantity) {
				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
				discount = bundleDiscount1 * quantity;
				totalAmount = calculation.DecimalCalculation(oneBundlePrice * quantity);
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			} else {
				discount = singleDiscount * quantity;
				totalAmount = price;
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			}

			cart.setTotalAmount(
					calculation.DecimalCalculation(cart.getTotalAmount() - productList.getPrice() + totalAmount));
			cart.setGstAmount(
					calculation.DecimalCalculation(cart.getGstAmount() - productList.getGstAmount() + gstAmount));
			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - productList.getDiscount() + discount));
			cart.setBundleDiscount(calculation
					.DecimalCalculation(cart.getBundleDiscount() - productList.getBundleDiscount() + bundleDiscount));
			cart.setPayableAmount(payableAmount);
			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice() + totalAmount));
			cart.setDues(calculation.DecimalCalculation(cart.getDues() - productList.getTotalAmount() + payableAmount));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
			cart.setDeliveryCharge(deliveryCharge);

			boolean updateCart = cartService.updateCart(cart);

			if (updateCart) {
				productList.setProductQuantity(quantity);
				productList.setPrice(calculation.DecimalCalculation(totalAmount));
				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
				productList.setDiscount(calculation.DecimalCalculation(discount));
				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
				productList.setMrp(calculation.DecimalCalculation(mrp));

				boolean updateProductList = productListService.updateProductList(productList);

				if (updateProductList) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Product updated");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product does not updated");
				}

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product does not updated");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product is out of stock");
		}

		return ResponseEntity.ok().body(response);
	}

	/*----------------------DECREASE CART PRODUCT----------------------
	 * Here decreasing product list quantity and updating cart data.
	 * */

	@PutMapping("cartdecrease/{cartId}/{productId}")
	public ResponseEntity<Map<String, String>> cartDecrease(@PathVariable("cartId") int cartId,
			@PathVariable("productId") String productId) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getCart(cartId);
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		ItemList product = itemListService.getById(Integer.parseInt(productId));
		Calculation calculation = new Calculation();
		int quantity = productList.getProductQuantity() - 1;
		float price = product.getUnitSellingPrice() * quantity, gstAmount = product.getGstAmount() * quantity,
				totalAmount = 0, singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
				bundleDiscount = product.getBundleCustomerDiscount() * quantity, deliveryCharge = 0,
				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
				mrp = quantity * product.getMrp();
		int payableAmount = 0;

//		if (product.getBundleQuantity() <= quantity) {
//			price = product.getBundlePrice() * quantity;
//			gstAmount = product.getBundleGstAmount() * quantity;
//			discount = product.getCustomerBundleOffer() * quantity;
//			bundleDiscount = product.getBundleCustomerDiscount() * quantity;
//		}
		if (productList.getProductQuantity() > 1) {
			if (bundleQuantity <= quantity) {
				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
				discount = bundleDiscount1 * quantity;
				totalAmount = calculation.DecimalCalculation(oneBundlePrice * quantity);
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			} else {
				discount = singleDiscount * quantity;
				totalAmount = price;
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			}

			cart.setTotalAmount(
					calculation.DecimalCalculation(cart.getTotalAmount() - productList.getPrice() + totalAmount));
			cart.setGstAmount(
					calculation.DecimalCalculation(cart.getGstAmount() - productList.getGstAmount() + gstAmount));
			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - productList.getDiscount() + discount));
			cart.setBundleDiscount(calculation
					.DecimalCalculation(cart.getBundleDiscount() - productList.getBundleDiscount() + bundleDiscount));
			cart.setPayableAmount(payableAmount);
			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice() + totalAmount));
			cart.setDues(calculation.DecimalCalculation(cart.getDues() - productList.getTotalAmount() + payableAmount));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
			cart.setDeliveryCharge(deliveryCharge);
			boolean updateCart = cartService.updateCart(cart);

			if (updateCart) {
				productList.setProductQuantity(quantity);
				productList.setPrice(calculation.DecimalCalculation(totalAmount));
				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
				productList.setDiscount(calculation.DecimalCalculation(discount));
				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
				productList.setMrp(calculation.DecimalCalculation(mrp));

				boolean updateProductList = productListService.updateProductList(productList);

				if (updateProductList) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Product updated");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product does not updated");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product does not updated");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product does not decrease");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("productreturn/{cartId}/{productId}")
	public ResponseEntity<Map<String, String>> productReturn(@PathVariable("cartId") int cartId,
			@PathVariable("productId") String productId) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getCart(cartId);
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		ItemList product = itemListService.getById(Integer.parseInt(productId));
		Calculation calculation = new Calculation();
//		int returnQuantity = (int) productList.getReturnQuantity() + 1,
		int returnQuantity = 1,
				quantity = productList.getProductQuantity() - returnQuantity;
		float price = product.getUnitSellingPrice() * quantity, gstAmount = product.getGstAmount() * quantity,
				totalAmount = 0, singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
				bundleDiscount = product.getBundleCustomerDiscount() * quantity, deliveryCharge = 0,
				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
				mrp = quantity * product.getMrp();
		int payableAmount = 0;

		if (quantity >= 0) {

			if (bundleQuantity <= quantity) {
				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
				discount = bundleDiscount1 * quantity;
				totalAmount = calculation.DecimalCalculation(oneBundlePrice * quantity);
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			} else {
				discount = singleDiscount * quantity;
				totalAmount = price;
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			}

			cart.setTotalAmount(
					calculation.DecimalCalculation(cart.getTotalAmount() - productList.getPrice() + totalAmount));
			cart.setGstAmount(
					calculation.DecimalCalculation(cart.getGstAmount() - productList.getGstAmount() + gstAmount));
			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - productList.getDiscount() + discount));
			cart.setBundleDiscount(calculation
					.DecimalCalculation(cart.getBundleDiscount() - productList.getBundleDiscount() + bundleDiscount));
			cart.setPayableAmount(payableAmount);
			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice() + totalAmount));
			cart.setDues(calculation.DecimalCalculation(cart.getDues() - productList.getTotalAmount() + payableAmount));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
			cart.setDeliveryCharge(deliveryCharge);
			cart.setProductReturn(Boolean.TRUE);
			boolean updateCart = cartService.updateCart(cart);

			if (updateCart) {
				productList.setProductQuantity(quantity);
				productList.setPrice(calculation.DecimalCalculation(totalAmount));
				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
				productList.setDiscount(calculation.DecimalCalculation(discount));
				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
				productList.setMrp(calculation.DecimalCalculation(mrp));
				productList.setReturnStatus(Boolean.TRUE);
				//productList.setReturnQuantity(returnQuantity);
				productList.setReturnQuantity(productList.getReturnQuantity() +returnQuantity);

				boolean updateProductList = productListService.updateProductList(productList);

				if (updateProductList) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Product updated");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product does not updated");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product does not updated");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product does not decrease");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("returnadd/{cartId}/{productId}")
	public ResponseEntity<Map<String, String>> returnAdd(@PathVariable("cartId") int cartId,
			@PathVariable("productId") String productId) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getCart(cartId);
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		// Product product =
		// productService.getProduct(Integer.parseInt(productId));//itemlist
		ItemList product = itemListService.getById(Integer.parseInt(productId));
		Calculation calculation = new Calculation();
		int returnQuantity = (int) productList.getReturnQuantity() - 1,
//				int returnQuantity = 1,
				quantity = productList.getProductQuantity() + 1;
		float price = product.getUnitSellingPrice() * quantity, gstAmount = product.getGstAmount() * quantity,
				singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),

				deliveryCharge = 0, totalAmount = 0, bundleDiscount = product.getBundleCustomerDiscount(),
				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
				mrp = quantity * product.getMrp();
		int payableAmount;

//		if (product.getBundleQuantity() <= quantity) {
//			price = product.getBundlePrice() * quantity;
//			gstAmount = product.getBundleGstAmount() * quantity;
//			discount = product.getCustomerBundleOffer() * quantity;
//			bundleDiscount = product.getBundleCustomerDiscount() * quantity;
//		}
		if (returnQuantity >= 0) {
			if (bundleQuantity <= quantity) {
				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
				discount = bundleDiscount1 * quantity;
				totalAmount = calculation.DecimalCalculation(oneBundlePrice * quantity);
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			} else {
				discount = singleDiscount * quantity;
				totalAmount = price;
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499 && payableAmount > 0) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			}

			cart.setTotalAmount(
					calculation.DecimalCalculation(cart.getTotalAmount() - productList.getPrice() + totalAmount));
			cart.setGstAmount(
					calculation.DecimalCalculation(cart.getGstAmount() - productList.getGstAmount() + gstAmount));
			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - productList.getDiscount() + discount));
			cart.setBundleDiscount(calculation
					.DecimalCalculation(cart.getBundleDiscount() - productList.getBundleDiscount() + bundleDiscount));
			cart.setPayableAmount(payableAmount);
			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice() + totalAmount));
			cart.setDues(calculation.DecimalCalculation(cart.getDues() - productList.getTotalAmount() + payableAmount));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
			cart.setDeliveryCharge(deliveryCharge);
			if (0 == returnQuantity) {
				cart.setProductReturn(Boolean.FALSE);
			}
			boolean updateCart = cartService.updateCart(cart);

			if (updateCart) {
			productList.setProductQuantity(quantity);
				productList.setPrice(calculation.DecimalCalculation(totalAmount));
				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
				productList.setDiscount(calculation.DecimalCalculation(discount));
				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
				productList.setMrp(calculation.DecimalCalculation(mrp));
				productList.setReturnQuantity(returnQuantity);
				if (0 == returnQuantity) {
					productList.setReturnStatus(Boolean.FALSE);

				}
				boolean updateProductList = productListService.updateProductList(productList);

				if (updateProductList) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Product updated");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product does not updated");
				}

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product does not updated");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product does not decrease");
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getalldate")
	public String convertStringToDate() {
		Cart cart = cartService.getCart(1);
		Date indate = cart.getSlotDate();
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		/*
		 * you can also use DateFormat reference instead of SimpleDateFormat like this:
		 * DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		 */
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return dateString;
	}

	

	/*----------------------PLACE ORDER----------------------
	 * Here updating cart status and creating transaction.
	 * */

//	// Use LocalData Storage in commented format
//	@PutMapping("placeorder")
//	ResponseEntity<Map<String, String>> placeOrder(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to update user: {}", json.get(ServiceConstants.CART_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.TRANSACTION_TYPE) && null != json.get(ServiceConstants.ORDER_TYPE)
//				&& null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.ADDRESS_ID)
//				&& null != json.get(ServiceConstants.SLOT_DATE) && null != json.get(ServiceConstants.INSIDE_SHOP)
//				&& null != json.get(ServiceConstants.SLOT_TIME)) {
//			boolean callData = false;
//			VariantStock variantStock = null;
//			int addressId = Integer.parseInt(json.get(ServiceConstants.ADDRESS_ID));
//			int transactionType = Integer.parseInt(json.get(ServiceConstants.TRANSACTION_TYPE)),
//					orderType = Integer.parseInt(json.get(ServiceConstants.ORDER_TYPE)),
//					cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
//			String slotTime = json.get(ServiceConstants.SLOT_TIME);
//			boolean insideShop = Boolean.parseBoolean(json.get(ServiceConstants.INSIDE_SHOP));
////			Discount discount = discountService.getByLable(slotTime);
////			if (discount.getTotalCount() > discount.getCount()) {
//
//				List<ProductList> productList = productListService.getProductListCartId(cartId);
//				for (int i = 0; i < productList.size(); i++) {
//					try {
//
//						// productService.getProduct(Integer.parseInt(productList.get(i).getProductId()));//itemlist
//						ItemList product = itemListService.getById(Integer.parseInt(productList.get(i).getProductId()));
//						if (product.getStock() >= productList.get(i).getProductQuantity()) {
//							callData = true;
//						}
//					} catch (Exception e) {
//						callData = false;
//					}
//				}
//
//				if (callData) {
//
//					Cart cart = cartService.getCart(cartId);
//					String shopId = cart.getShopId();
//
//					Admin admin = adminService.getAdminByShopId(shopId);
//					LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PLACED");
//					LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
//					LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "CASH");
//					LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
//					LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "TRANS_PENDING");
//					LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "SHOPING");
//					LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION");
//					LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
//					LookUp lookUp8 = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");
//
//					int transactionType1 = lookUp5.getLookUpId(), deliveryType = lookUp8.getLookUpId();
//					int placed = lookUp.getLookUpId();
//					int onlinePayment = lookUp1.getLookUpId();
//					int cash = lookUp2.getLookUpId();
//					int userType = lookUp3.getLookUpId();
//					int userId = Integer.parseInt(cart.getUserId());
//					int transactionStatus = lookUp4.getLookUpId();
//					int adminId = admin.getAdminId();
//					int orderNotification = lookUp6.getLookUpId();
//					int shipped = lookUp7.getLookUpId();
//					Calculation calculation = new Calculation();
//					Address address = addressService.getAddress(addressId);
//					cart.setCurrentAddress(address.getCity());
//					cart.setLatitude(address.getLatitude());
//					cart.setLongitude(address.getLongitude());
//
//					String mobileNo = address.getMobileNo();
//					String userName = address.getName();
//					int payableAmount = cart.getPayableAmount();
//					Transaction transaction = null;
//					if (transactionService.transactionExists(cartId)) {
//						transaction = transactionService.getTransactionByCartId(cartId);
//					} else {
//						transaction = new Transaction(shopId, userId);
//					}
//					String order_rcptid_11 = Strings.EMPTY;
//					order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);
//					AdminBillBook adminBillBook = null;
//					if (transactionType == onlinePayment) {
//
//						cart.setTransactionType(onlinePayment);
//						cart.setOrderType(orderType);
//						cart.setAddressId(addressId);
//						cart.setMobileNo(mobileNo);
//						cart.setUserName(userName);
//						cart.setInsideShop(insideShop);
//						cart.setSlotTime(slotTime);
//						cart.setOrderDate(new Date());
//
//						try {
//							cart.setSlotDate(
//									new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_DATE)));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//
////					try {
////						cart.setSlotTime(
////								new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_TIME)));
////					} catch (ParseException e) {
////						e.printStackTrace();
////					}
//
//						Order order = null;
//						try {
//
//							JSONObject orderRequest = new JSONObject();
//							orderRequest.put("amount", cart.getPayableAmount() * 100); // amount in the smallest
//																						// currency
//																						// unit
//							orderRequest.put("currency", ServiceConstants.DEFAULT_ORDER_CURR);
//							orderRequest.put("receipt", order_rcptid_11);
//							orderRequest.put("payment_capture", Boolean.FALSE);
//							order = new RazorpayClient(ServiceConstants.RAZORPAY_KEY,
//									ServiceConstants.RAZORPAY_SECRET).Orders.create(orderRequest);
//							System.out.println(String.valueOf("Order Id" + order.get("id")));
//
//							response.put("orderId", order.get("id"));
//						} catch (RazorpayException e) {
//							// Handle Exception
//							System.out.println(e.getMessage());
//						}
//
//						transaction.setUserId(userId);
//						transaction.setShopId(shopId);
//						transaction.setPaymentMode(onlinePayment);
//						transaction.setAmount(payableAmount);
//						transaction.setCreatedOn(new Date());
//						transaction.setOrderRcptidId(order_rcptid_11);
//
//						transaction.setUserType(userType);
//						transaction.setActive(Boolean.TRUE);
//						transaction.setDeleted(Boolean.FALSE);
//						transaction.setTotalAmount(cart.getPayableAmount());
//						transaction.setDues(cart.getPayableAmount());
//						transaction.setCartId(cartId);
//						transaction.setAdminId(adminId);
//						transaction.setTransactionStatus(transactionStatus);
//						transaction.setTransactionType(transactionType1);
//
//						transactionService.createTransaction(transaction);
//						Transaction transaction1 = transactionService.getTransactionByCartId(cartId);
//
//						boolean result = cartService.updateCart(cart);
////						discount.setCount(discount.getCount() + 1);
////						if (discount.getCount() >= discount.getTotalCount()) {
////							discount.setActive(Boolean.FALSE);
////						}
////						boolean updateDiscount = discountService.updateDiscount(discount);
//						if (result) {
//							// notificationService.createNotification(cartId, orderNotification);
//							response.put("status", String.valueOf(true));
//							response.put("transactionId", String.valueOf(transaction1.getId()));
//							response.put("description", "Please complete transaction to place order.");
//						} else {
//							response.put("status", String.valueOf(false));
//							response.put("description", "Your placed order has been failed.");
//						}
//
//					} else if (transactionType == cash) {
//						if (insideShop) {
//							cart.setDeliveryType(deliveryType);
//							cart.setShippingDate(new Date());
//							cart.setDeliveryDate(new Date());
//							cart.setOrderStatus(shipped);
//						} else {
//							cart.setOrderStatus(placed);
//						}
//						cart.setTransactionType(cash);
//						cart.setInsideShop(insideShop);
//						cart.setOrderType(orderType);
//						cart.setDues(cart.getPayableAmount());
//						cart.setAddressId(addressId);
//						cart.setMobileNo(mobileNo);
//						cart.setUserName(userName);
//						cart.setSlotTime(slotTime);
//
//						transaction.setUserId(userId);
//						transaction.setShopId(shopId);
//						transaction.setPaymentMode(cash);
//						transaction.setAmount(payableAmount);
//						transaction.setCreatedOn(new Date());
//						transaction.setOrderRcptidId(order_rcptid_11);
//						transaction.setUserType(userType);
//						transaction.setActive(Boolean.TRUE);
//						transaction.setDeleted(Boolean.FALSE);
//						transaction.setTotalAmount(cart.getPayableAmount());
//						transaction.setDues(cart.getPayableAmount());
//						transaction.setCartId(cartId);
//						transaction.setAdminId(adminId);
//						transaction.setTransactionStatus(transactionStatus);
//						transaction.setTransactionType(transactionType1);
//
//						transactionService.createTransaction(transaction);
//						try {
//							cart.setSlotDate(
//									new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_DATE)));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						cart.setTransactionId(String.valueOf(transaction.getId()));
//						boolean result = cartService.updateCart(cart);
////						discount.setCount(discount.getCount() + 1);
////						if (discount.getCount() >= discount.getTotalCount()) {
////							discount.setActive(Boolean.FALSE);
////						}
////						boolean updateDiscount = discountService.updateDiscount(discount);
//						List<ProductList> productList1 = productListService.getProductListCartId(cartId);
//
////					List<ItemList> localItemList = localData.getVariantData();
////					if (localItemList.size() <= 0) {
////						List<ItemList> itemList = itemListService.getByShopId(shopId);
////						localItemList = localData.setVariantData(itemList);
////					}
//
//						if (productList1.size() > 0) {
//							for (int i = 0; i < productList1.size(); i++) {
//								int productId = Integer.parseInt(productList1.get(i).getProductId()),
//										productQuantity = productList1.get(i).getProductQuantity();
//
//								ItemList product = itemListService.getById(productId);
//
////							for(int j=0; j< localItemList.size(); j++) {
////								if(localItemList.get(j).getId() == productId) {
////									localItemList.get(j).setStock(localItemList.get(j).getStock() - productQuantity);
////								}
////								localData.setVariantData(localItemList);
////							}
//								product.setStock(product.getStock() - productQuantity);
//
//								itemListService.updateItemList(product);
//
//								variantStock = variantStockService.getById(productId);
//								if (variantStock.getCurrentStock() >= productQuantity) {
//									variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
//									variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
//								}
//								boolean updateStock = variantStockService.updateStock(variantStock);
//							}
//						}
//
////					if (productList1.size() > 0) {
////						for (int j = 0; j < productList1.size(); j++) {
////							int productId = Integer.parseInt(productList1.get(j).getProductId());
////							int productQuantity = productList1.get(j).getProductQuantity();
////							variantStock = variantStockService.getById(productId);
////							if (variantStock.getCurrentStock() >= productQuantity) {
////								variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
////								variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
////							}
////							boolean updateStock = variantStockService.updateStock(variantStock);
////							if (updateStock) {
////								response.put("status", Boolean.TRUE.toString());
////								response.put("description", " variant stock is update");
////							} else {
////								response.put("status", Boolean.FALSE.toString());
////								response.put("description", " variant stock is not update");
////							}
////						}
////						
////					} else {
////						response.put("status", Boolean.FALSE.toString());
////						response.put("description", " variant current stock is less then unitQuantity");
////					}
//
//						if (result) {
//							notificationService.createNotification(cartId, orderNotification);
//							response.put("status", String.valueOf(true));
//							response.put("description", "Your order placed sucessfully.");
//
//						} else {
//							response.put("status", String.valueOf(false));
//							response.put("description", "Your placed order has been failed.");
//						}
//					}
//
//				} else {
//					response.put("status", Boolean.FALSE.toString());
//					response.put("descerption", "Some product is out of stock");
//				}
////			} else {
////				response.put("status", Boolean.FALSE.toString());
////				response.put("descerption", "This slot has been exited limite");
////			}
//		}
//		return ResponseEntity.ok().body(response);
//	}
	@PutMapping("razorpay")
	ResponseEntity<Map<String, String>> razorPayOrder(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException, RazorpayException {
		log.info("Request to update user: {}", json.get(ServiceConstants.CART_ID));
		Map<String, String> response = new HashMap<String, String>();
		if(null != json.get(ServiceConstants.PAYABLE_AMOUNT) && null != json.get(ServiceConstants.CART_ID)) {
		//	RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			Cart cart = cartService.getCart(cartId);
			JSONObject orderRequest = new JSONObject();
			Order order = null;
			String order_rcptid_11 = Strings.EMPTY;
			order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);
			//JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", cart.getPayableAmount() * 100); // amount in the smallest
																		// currency
																		// unit
			orderRequest.put("currency", ServiceConstants.DEFAULT_ORDER_CURR);
			orderRequest.put("receipt", order_rcptid_11);
			orderRequest.put("payment_capture", Boolean.FALSE);
			order = new RazorpayClient(ServiceConstants.RAZORPAY_KEY,
					ServiceConstants.RAZORPAY_SECRET).Orders.create(orderRequest);
			System.out.println(String.valueOf("Order Id" + order.get("id")));

			response.put("orderId", order.get("id"));
			response.put("orderId1", order.get("id"));
		
		}
		return ResponseEntity.ok().body(response);
	}
	// Use LocalData Storage in commented format
	@PutMapping("placeorder")
	ResponseEntity<Map<String, String>> placeOrder(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.CART_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.TRANSACTION_TYPE) && null != json.get(ServiceConstants.ORDER_TYPE)
				&& null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.ADDRESS_ID)
				&& null != json.get(ServiceConstants.SLOT_DATE) && null != json.get(ServiceConstants.INSIDE_SHOP)
				&& null != json.get(ServiceConstants.SLOT_TIME)) {
			boolean callData = false;
			VariantStock variantStock = null;
			int addressId = Integer.parseInt(json.get(ServiceConstants.ADDRESS_ID));
			int transactionType = Integer.parseInt(json.get(ServiceConstants.TRANSACTION_TYPE)),
					orderType = Integer.parseInt(json.get(ServiceConstants.ORDER_TYPE)),
					cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			int slotTime = Integer.parseInt(json.get(ServiceConstants.SLOT_TIME));
			boolean insideShop = Boolean.parseBoolean(json.get(ServiceConstants.INSIDE_SHOP));
			List<SlotTime> slotTimeList = slotTimeService.getBySlotTime(slotTime);
			SlotTime slotTime1 = new SlotTime();
			boolean slotFound = false;
//				Discount discount = discountService.getById(Integer.parseInt(slotTime));
			LocalDate date = LocalDate.parse(json.get(ServiceConstants.SLOT_DATE));
			for (int i = 0; i < slotTimeList.size(); i++) {
				LocalDate slotDate = slotTimeList.get(i).getSlotDate();
				int diff = date.compareTo(slotDate);
				if (diff == 0) {
					slotFound = true;
					slotTime1 = slotTimeList.get(i);
				}
			}

			if (!slotFound) {
				slotTime1.setActive(true);
				slotTime1.setCreatedOn(new Date());
				slotTime1.setDeleted(false);
				slotTime1.setSlotCount(0);
				slotTime1.setSlotDate(date);
				slotTime1.setSlotTime(slotTime);
				slotTime1.setTotalCount(10);
				slotTimeService.createSlotTime(slotTime1);
			}

//				if (slotTime1.getTotalCount() > slotTime1.getSlotCount()) {

			List<ProductList> productList = productListService.getProductListCartId(cartId);
			for (int i = 0; i < productList.size(); i++) {
				try {

					// productService.getProduct(Integer.parseInt(productList.get(i).getProductId()));//itemlist
					ItemList product = itemListService.getById(Integer.parseInt(productList.get(i).getProductId()));
					if (product.getStock() >= productList.get(i).getProductQuantity()) {
						callData = true;
					}
				} catch (Exception e) {
					callData = false;
				}
			}

			if (callData) {

				Cart cart = cartService.getCart(cartId);
				String shopId = cart.getShopId();
				int userId = Integer.parseInt(cart.getUserId());

//						Related to walletBalance operation

				User user = UserService.getUser(userId);
				String refralNumber = user.getRefralNumber();
				int cartPayableAmount = cart.getPayableAmount();

				if (null != refralNumber && user.isFirstOrder()) {
					User firstUser = UserService.getActiveUserByMobileNumber(refralNumber);

//					user.setFirstOrder(Boolean.TRUE);
					if (cartPayableAmount >= 500) {
						firstUser.setWalletBalance(firstUser.getWalletBalance() + 40);
						UserService.updateUser(firstUser);
					}
				}

				if (cartPayableAmount >= 2500 && user.isFirstOrder()) {
					user.setWalletBalance(user.getWalletBalance() + 150);
					UserService.updateUser(user);
				}
				if (user.isFirstOrder()) {
					user.setFirstOrder(Boolean.FALSE);
					UserService.updateUser(user);
				}

				Admin admin = adminService.getAdminByShopId(shopId);
				LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PLACED");
				LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
				LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "CASH");
				LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
				LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "TRANS_PENDING");
				LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "SHOPING");
				LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION");
				LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
				LookUp lookUp8 = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");

				int transactionType1 = lookUp5.getLookUpId(), deliveryType = lookUp8.getLookUpId();
				int placed = lookUp.getLookUpId();
				int onlinePayment = lookUp1.getLookUpId();
				int cash = lookUp2.getLookUpId();
				int userType = lookUp3.getLookUpId();

				int transactionStatus = lookUp4.getLookUpId();
				int adminId = admin.getAdminId();
				int orderNotification = lookUp6.getLookUpId();
				int shipped = lookUp7.getLookUpId();
				Calculation calculation = new Calculation();
				Address address = addressService.getAddress(addressId);
				cart.setCurrentAddress(address.getCity());
				cart.setLatitude(address.getLatitude());
				cart.setLongitude(address.getLongitude());

				String mobileNo = address.getMobileNo();
				String userName = address.getName();
				int payableAmount = cart.getPayableAmount();
				Transaction transaction = null;
				if (transactionService.transactionExists(cartId)) {
					transaction = transactionService.getTransactionByCartId(cartId);
				} else {
					transaction = new Transaction(shopId, userId);
				}
				String order_rcptid_11 = Strings.EMPTY;
				order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);
				AdminBillBook adminBillBook = null;
				if (transactionType == onlinePayment) {

					cart.setTransactionType(onlinePayment);
					cart.setOrderType(orderType);
					cart.setAddressId(addressId);
					cart.setMobileNo(mobileNo);
					cart.setUserName(userName);
					cart.setInsideShop(insideShop);
					cart.setSlotTime(String.valueOf(slotTime));
					cart.setOrderDate(new Date());

					try {
						cart.setSlotDate(
								new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_DATE)));
					} catch (ParseException e) {
						e.printStackTrace();
					}

//						try {
//							cart.setSlotTime(
//									new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_TIME)));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}

					Order order = null;
					try {

						JSONObject orderRequest = new JSONObject();
						orderRequest.put("amount", cart.getPayableAmount() * 100); // amount in the smallest
																					// currency
																					// unit
						orderRequest.put("currency", ServiceConstants.DEFAULT_ORDER_CURR);
						orderRequest.put("receipt", order_rcptid_11);
						orderRequest.put("payment_capture", Boolean.FALSE);
						order = new RazorpayClient(ServiceConstants.RAZORPAY_KEY,
								ServiceConstants.RAZORPAY_SECRET).Orders.create(orderRequest);
						System.out.println(String.valueOf("Order Id" + order.get("id")));

						response.put("orderId", order.get("id"));
					} catch (RazorpayException e) {
						// Handle Exception
						System.out.println(e.getMessage());
					}

					transaction.setUserId(userId);
					transaction.setShopId(shopId);
					transaction.setPaymentMode(onlinePayment);
					transaction.setAmount(payableAmount);
					transaction.setCreatedOn(new Date());
					transaction.setOrderRcptidId(order_rcptid_11);

					transaction.setUserType(userType);
					transaction.setActive(Boolean.TRUE);
					transaction.setDeleted(Boolean.FALSE);
					transaction.setTotalAmount(cart.getPayableAmount());
					transaction.setDues(cart.getPayableAmount());
					transaction.setCartId(cartId);
					transaction.setAdminId(adminId);
					transaction.setTransactionStatus(transactionStatus);
					transaction.setTransactionType(transactionType1);

					transactionService.createTransaction(transaction);
					Transaction transaction1 = transactionService.getTransactionByCartId(cartId);

					boolean result = cartService.updateCart(cart);
					slotTime1.setSlotCount(slotTime1.getSlotCount() + 1);
					if (slotTime1.getSlotCount() >= slotTime1.getTotalCount()) {
						slotTime1.setActive(Boolean.FALSE);
					}
					boolean updateDiscount = slotTimeService.updateSlotTime(slotTime1);
					if (result) {
						// notificationService.createNotification(cartId, orderNotification);
						response.put("status", String.valueOf(true));
						response.put("transactionId", String.valueOf(transaction1.getId()));
						response.put("description", "Please complete transaction to place order.");
					} else {
						response.put("status", String.valueOf(false));
						response.put("description", "Your placed order has been failed.");
					}

				} else if (transactionType == cash) {
					if (insideShop) {
						cart.setDeliveryType(deliveryType);
						cart.setShippingDate(new Date());
						cart.setDeliveryDate(new Date());
						cart.setOrderStatus(shipped);
					} else {
						cart.setOrderStatus(placed);
					}
					cart.setTransactionType(cash);
					cart.setInsideShop(insideShop);
					cart.setOrderType(orderType);
					cart.setDues(cart.getPayableAmount());
					cart.setAddressId(addressId);
					cart.setMobileNo(mobileNo);
					cart.setUserName(userName);
					cart.setSlotTime(String.valueOf(slotTime));

					transaction.setUserId(userId);
					transaction.setShopId(shopId);
					transaction.setPaymentMode(cash);
					transaction.setAmount(payableAmount);
					transaction.setCreatedOn(new Date());
					transaction.setOrderRcptidId(order_rcptid_11);
					transaction.setUserType(userType);
					transaction.setActive(Boolean.TRUE);
					transaction.setDeleted(Boolean.FALSE);
					transaction.setTotalAmount(cart.getPayableAmount());
					transaction.setDues(cart.getPayableAmount());
					transaction.setCartId(cartId);
					transaction.setAdminId(adminId);
					transaction.setTransactionStatus(transactionStatus);
					transaction.setTransactionType(transactionType1);

					transactionService.createTransaction(transaction);
					try {
						cart.setSlotDate(
								new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_DATE)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cart.setTransactionId(String.valueOf(transaction.getId()));
					boolean result = cartService.updateCart(cart);
					slotTime1.setSlotCount(slotTime1.getSlotCount() + 1);
					if (slotTime1.getSlotCount() >= slotTime1.getTotalCount()) {
						slotTime1.setActive(Boolean.FALSE);
					}
					boolean updateDiscount = slotTimeService.updateSlotTime(slotTime1);
					List<ProductList> productList1 = productListService.getProductListCartId(cartId);

//						List<ItemList> localItemList = localData.getVariantData();
//						if (localItemList.size() <= 0) {
//							List<ItemList> itemList = itemListService.getByShopId(shopId);
//							localItemList = localData.setVariantData(itemList);
//						}

					if (productList1.size() > 0) {
						for (int i = 0; i < productList1.size(); i++) {
							int productId = Integer.parseInt(productList1.get(i).getProductId()),
									productQuantity = productList1.get(i).getProductQuantity();

							ItemList product = itemListService.getById(productId);

//								for(int j=0; j< localItemList.size(); j++) {
//									if(localItemList.get(j).getId() == productId) {
//										localItemList.get(j).setStock(localItemList.get(j).getStock() - productQuantity);
//									}
//									localData.setVariantData(localItemList);
//								}
							product.setStock(product.getStock() - productQuantity);

							itemListService.updateItemList(product);

							variantStock = variantStockService.getById(productId);
							if (variantStock.getCurrentStock() >= productQuantity) {
								variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
								variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
							}
							boolean updateStock = variantStockService.updateStock(variantStock);
						}
					}

//						if (productList1.size() > 0) {
//							for (int j = 0; j < productList1.size(); j++) {
//								int productId = Integer.parseInt(productList1.get(j).getProductId());
//								int productQuantity = productList1.get(j).getProductQuantity();
//								variantStock = variantStockService.getById(productId);
//								if (variantStock.getCurrentStock() >= productQuantity) {
//									variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
//									variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
//								}
//								boolean updateStock = variantStockService.updateStock(variantStock);
//								if (updateStock) {
//									response.put("status", Boolean.TRUE.toString());
//									response.put("description", " variant stock is update");
//								} else {
//									response.put("status", Boolean.FALSE.toString());
//									response.put("description", " variant stock is not update");
//								}
//							}
//							
//						} else {
//							response.put("status", Boolean.FALSE.toString());
//							response.put("description", " variant current stock is less then unitQuantity");
//						}

					if (result) {
						notificationService.createNotification(cartId, orderNotification);
						response.put("status", String.valueOf(true));
						response.put("description", "Your order placed sucessfully.");

					} else {
						response.put("status", String.valueOf(false));
						response.put("description", "Your placed order has been failed.");
					}
				}

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("descerption", "Some product is out of stock");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("descerption", "This slot has been exited limite");
		}
//			}
		return ResponseEntity.ok().body(response);
	}

//use local storage data and comment it
	@PostMapping("completpayment")
	ResponseEntity<Map<String, String>> completePayment(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.TRANSACTION_ID)
				&& null != json.get(ServiceConstants.RZPAY_TRANSECTION_ID)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID)), transactionAmount = 0;
			String transactionId = json.get(ServiceConstants.TRANSACTION_ID),
					rzpayTransactionId = json.get(ServiceConstants.RZPAY_TRANSECTION_ID);
			Transaction transaction = transactionService.getById(Integer.parseInt(transactionId));
			Cart cart = cartService.getCart(cartId);
			String shopId = cart.getShopId();
			Admin admin = adminService.getAdminByShopId(shopId);
			UserBillBook userBillBook = null;
			AdminBillBook adminBillBook = null;
			transactionAmount = cart.getPayableAmount();

			Calculation calculation = new Calculation();
			int userId = Integer.parseInt(cart.getUserId());
			User user = UserService.getUser(userId);
			log.info("Request to complete payment by user: {}", json.get(ServiceConstants.MOBILE_NUMBER));

			if (null != user && user.getIsActive() && null != transactionId && !Strings.isEmpty(rzpayTransactionId)) {
				LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PLACED");
				LookUp lookUp_shipped = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
				LookUp lookUp_self = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");
				LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
				LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
				int placed = lookUp.getLookUpId(), shipped = lookUp_shipped.getLookUpId(),
						deliveryType = lookUp_self.getLookUpId(), transactionStatus = lookUp2.getLookUpId(),
						orderNotification = lookUp3.getLookUpId();
				cart.setTransactionId(transactionId);
				cart.setPaid(calculation.DecimalCalculation(transactionAmount));
				cart.setDues(calculation.DecimalCalculation(cart.getDues() - transactionAmount));
				cart.setOrderDate(new Date());

				if (cart.isInsideShop()) {
					cart.setDeliveryType(deliveryType);
					cart.setShippingDate(new Date());
					cart.setDeliveryDate(new Date());
					cart.setOrderStatus(shipped);
				} else {
					cart.setOrderStatus(placed);
				}

				transaction.setAmount(calculation.DecimalCalculation(transactionAmount));
				transaction.setTransactionStatus(transactionStatus);
				transaction.setTotalAmount(calculation.DecimalCalculation(transactionAmount));
				transaction.setTransactionId(rzpayTransactionId);
				transaction.setDues(0);
				transaction.setPaid(calculation.DecimalCalculation(transactionAmount));
				transaction.setDiscreption("payment done of " + transactionAmount + " by user " + user.getMobileNo()
						+ " with order id " + transaction.getCartId() + "and transaction ID is" + rzpayTransactionId);
				boolean transactionDone = transactionService.updateTransaction(transaction);
				if (transactionDone) {
					admin.setWallet(calculation.DecimalCalculation(admin.getWallet() + transactionAmount));
					adminService.updateAdmin(admin);

					cartService.updateCart(cart);
					notificationService.createNotification(cartId, orderNotification);

					String adminId = String.valueOf(admin.getAdminId());

					if (adminBillBookService.adminBillBookExists(adminId)) {
						List<AdminBillBook> adminBillBookList = adminBillBookService.getBillBookByAdminId(adminId);
						adminBillBook = adminBillBookList.get(0);
						adminBillBook.setCredit(
								calculation.DecimalCalculation(adminBillBook.getCredit() + transactionAmount));
						adminBillBook.setLastCreditedOn(new Date());
						adminBillBook.setUpdatedOn(new Date());
						adminBillBookService.updateAdminBillBook(adminBillBook);

					} else {
						LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
						int userType = lookUp1.getLookUpId();
						adminBillBook = new AdminBillBook(adminId, shopId);

						adminBillBook.setCredit(calculation.DecimalCalculation(transactionAmount));
						adminBillBook.setLastCreditedOn(new Date());
						adminBillBook.setActive(Boolean.TRUE);
						adminBillBook.setAdminId(adminId);
						adminBillBook.setCreatedOn(new Date());
						adminBillBook.setDeleted(Boolean.FALSE);
						adminBillBook.setShopId(shopId);
						adminBillBook.setUserType(userType);
						adminBillBook.setUpdatedOn(new Date());

						adminBillBookService.createAdminBillBook(adminBillBook);
					}
					if (userBillBookService.userBillBookExists(String.valueOf(userId))) {
						List<UserBillBook> userBillBookList = userBillBookService.getByUserId(String.valueOf(userId));
						userBillBook = userBillBookList.get(0);
						userBillBook
								.setDebit(calculation.DecimalCalculation(userBillBook.getDebit() + transactionAmount));
						userBillBook.setLastDebitedOn(new Date());
						userBillBook.setUpdatedOn(new Date());
						userBillBookService.updateUserBillBook(userBillBook);

					} else {
						LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
						int userType = lookUp1.getLookUpId();
						userBillBook = new UserBillBook(String.valueOf(userId), shopId);

						userBillBook.setActive(Boolean.TRUE);
						userBillBook.setCreatedOn(new Date());
						userBillBook.setDebit(calculation.DecimalCalculation(transactionAmount));
						userBillBook.setLastDebitedOn(new Date());
						userBillBook.setShopId(shopId);
						userBillBook.setUserId(String.valueOf(userId));
						userBillBook.setUserType(userType);
						userBillBook.setUpdatedOn(new Date());

						userBillBookService.createUserBillBook(userBillBook);
					}
					List<ItemList> localItemList = localData.getVariantData();
					if (localItemList.size() <= 0) {
						List<ItemList> itemList = itemListService.getByShopId(shopId);
						localItemList = localData.setVariantData(itemList);
					}

					List<ProductList> productList = productListService.getProductListCartId(cartId);
					if (productList.size() > 0) {
						for (int i = 0; i < productList.size(); i++) {
							int productId = Integer.parseInt(productList.get(i).getProductId()),
									productQuantity = productList.get(i).getProductQuantity();

							ItemList product = itemListService.getById(productId);

							product.setStock(product.getStock() - productQuantity);
							for (int j = 0; j < localItemList.size(); j++) {
								if (localItemList.get(j).getId() == productId) {
									localItemList.get(j).setStock(localItemList.get(j).getStock() - productQuantity);
								}

							}
							itemListService.updateItemList(product);
							localData.setVariantData(localItemList);

							VariantStock variantStock = variantStockService.getById(productId);
							if (variantStock.getCurrentStock() >= productQuantity) {
								variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
								variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
							}
							boolean updateStock = variantStockService.updateStock(variantStock);
						}

					}

				} else {
					response.put("status", String.valueOf(false));
					response.put("description", "Your order does not been placed due to transaction failed.");
				}
			}
			response.put("status", String.valueOf(true));
			response.put("description", "Your order placed sucessfully.");
		} else {
			response.put("status", String.valueOf(false));
			response.put("description", "Your order does not place.");
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("completpayment1")
	ResponseEntity<Map<String, String>> completePayment1(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.RZPAY_TRANSECTION_ID)
				&& null != json.get(ServiceConstants.TRANSACTION_ID)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			String rzpayTransactionId = json.get(ServiceConstants.RZPAY_TRANSECTION_ID),
					transactionId = json.get(ServiceConstants.TRANSACTION_ID);
			Transaction transaction = transactionService.getTransaction(transactionId);
			Cart cart = cartService.getCart(cartId);
			String shopId = cart.getShopId();
			String userId = cart.getUserId();
			float dues = cart.getDues();
			float totalAmount = cart.getTotalAmount();
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PLACED");
			LookUp lookUp_shipped = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
			LookUp lookUp_self = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
			int placed = lookUp.getLookUpId(), shipped = lookUp_shipped.getLookUpId(),
					deliveryType = lookUp_self.getLookUpId(), transactionStatus = lookUp2.getLookUpId(),
					orderNotification = lookUp3.getLookUpId(), admin1 = lookUp4.getLookUpId(),
					customer = lookUp5.getLookUpId();
			Admin admin = adminService.getAdminByShopId(shopId);
			int adminId = admin.getAdminId();
			List<AdminBillBook> adminBillBookList = adminBillBookService.getBillBookByAdminId(String.valueOf(adminId));
			AdminBillBook adminBillBook = adminBillBookList.get(0);
			User user = UserService.getUser(Integer.parseInt(userId));
			List<UserBillBook> userBillBookList = userBillBookService.getByUserId(userId);
			UserBillBook userBillBook = userBillBookList.get(0);
			adminBillBook = new AdminBillBook(String.valueOf(adminId), shopId);
			cart.setActive(Boolean.TRUE);
			cart.setOrderStatus(shipped);
			cart.setShippingDate(new Date());
			cart.setCreatedOn(new Date());
			cart.setDues(cart.getDues() - dues);
			cart.setPaid(totalAmount);
			cartService.updateCart(cart);

			transaction.setAmount(totalAmount);
			transaction.setOrderRcptidId(rzpayTransactionId);
			transaction.setPaymentMode(transactionStatus);
			transaction.setTransactionId(transactionId);
			transactionService.updateTransaction(transaction);

			if (null != admin) {
				admin.setWallet(admin.getWallet() + totalAmount);
				adminService.updateAdmin(admin);
			}
			if (adminBillBookService.adminBillBookExists(String.valueOf(adminId))) {
				adminBillBook.setDebit(adminBillBook.getDebit() + totalAmount);
				adminBillBook.setUpdatedOn(new Date());
				adminBillBook.setLastDebitedOn(new Date());
				adminBillBookService.updateAdminBillBook(adminBillBook);
			} else {

				adminBillBook.setActive(Boolean.TRUE);
				adminBillBook.setAdminId(String.valueOf(adminId));
				adminBillBook.setCreatedOn(new Date());
				adminBillBook.setDebit(totalAmount);
				adminBillBook.setDeleted(Boolean.FALSE);
				adminBillBook.setLastDebitedOn(new Date());
				adminBillBook.setShopId(shopId);
				adminBillBook.setUpdatedOn(new Date());
				adminBillBook.setUserType(admin1);
				adminBillBookService.createAdminBillBook(adminBillBook);

			}
			if (null != user) {
				user.setWalletBalance(user.getWalletBalance() - totalAmount);
				UserService.updateUser(user);
			}
			if (userBillBookService.userBillBookExists(userId)) {
				userBillBook.setCredit(userBillBook.getCredit() + totalAmount);
				userBillBook.setLastCreditedOn(new Date());
				userBillBook.setUpdatedOn(new Date());
				userBillBookService.updateUserBillBook(userBillBook);
			} else {
				userBillBook.setActive(Boolean.TRUE);
				userBillBook.setCreatedOn(new Date());
				userBillBook.setCredit(userBillBook.getCredit() + totalAmount);
				userBillBook.setDeleted(Boolean.FALSE);
				userBillBook.setLastCreditedOn(new Date());
				userBillBook.setShopId(shopId);
				userBillBook.setUpdatedOn(new Date());
				userBillBook.setUserId(userId);
				userBillBook.setUserType(customer);
			}

			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Complete Payment");
		} else {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Complete Failed");
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("order/accept/{cartId}/{shopId}")
	public ResponseEntity<Cart> getCombinedOrderStatus(@PathVariable("cartId") int cartId,
			@PathVariable("shopId") String shopId) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getOrderStatus(cartId, shopId);
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION");
		int accepted = lookUp.getLookUpId(), orderNotification = lookUp1.getLookUpId();
		if (null != cart) {
			cart.setOrderStatus(accepted);

			int userId = Integer.parseInt(cart.getUserId());
			User user = UserService.getUser(userId);
			user.setDues(user.getDues() + cart.getTotalAmount());
			UserService.updateUser(user);
		}
		boolean update = cartService.updateCart(cart);
		if (update) {
			notificationService.createNotification(cartId, orderNotification);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Cart updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Cart did not updated");
		}
		// return ResponseEntity.ok().body(response);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PostMapping("order/return")
	public ResponseEntity<Cart> cartReturnStatus(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.CART_ID));
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = null;

		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.DESCRIPTION)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			String shopId = json.get(ServiceConstants.SHOP_ID);
			String description = json.get(ServiceConstants.DESCRIPTION);
			Calculation calculation = new Calculation();
			cart = cartService.getOrderStatus(cartId, shopId);
			String userId = cart.getUserId();
			User user = UserService.getUser(Integer.parseInt(userId));
			Admin admin = adminService.getAdminByShopId(shopId);
			int adminId = admin.getAdminId();
			List<AdminBillBook> adminBillBookList = null;
			AdminBillBook adminBillBook = null;
			List<UserBillBook> userBillBookList = null;
			UserBillBook userBillBook = null;
			Transaction transaction = null;
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RETURN");
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "CASH");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
			LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "USER_DENIED");
			LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
			int transactionMode = lookUp5.getLookUpId();
			int orderStatus = lookUp.getLookUpId();
			int transactionStatus = lookUp1.getLookUpId();
			int userType = lookUp2.getLookUpId();
			int online = lookUp3.getLookUpId();
			int cash = lookUp4.getLookUpId();
			int transactionType = lookUp6.getLookUpId();
			int orderNotification = lookUp7.getLookUpId();
			if (null != cart) {
				int payableAmount = cart.getPayableAmount();
				cart.setOrderStatus(orderStatus);

				cart.setDescription(description);
				List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
				if (productList.size() < 0) {
					for (int i = 0; i > productList.size(); i++) {
						String productId = productList.get(i).getProductId();
						int productQuantity = productList.get(i).getProductQuantity();
						ItemList variant = itemListService.getById(Integer.parseInt(productId));
						variant.setStock(variant.getStock() + productQuantity);

						VariantStock variantStock = variantStockService.getByVariantId(Integer.parseInt(productId));
						variantStock.setCurrentStock(variantStock.getCurrentStock() + productQuantity);
						variantStock.setStock(variantStock.getStock() + productQuantity);
						variantStockService.updateStock(variantStock);
					}
				}

				if (cart.getTransactionType() == online) {
					adminBillBookList = adminBillBookService.getBillBookByAdminId(String.valueOf(adminId));
					userBillBookList = userBillBookService.getByUserId(userId);
					String order_rcptid_11 = Strings.EMPTY;
					order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);
					userBillBook = userBillBookList.get(0);
					adminBillBook = adminBillBookList.get(0);
					transaction = new Transaction();

					adminBillBook.setDebit(adminBillBook.getDebit() + payableAmount);
					adminBillBook.setUpdatedOn(new Date());
					adminBillBook.setLastDebitedOn(new Date());
					adminBillBookService.updateAdminBillBook(adminBillBook);

					userBillBook.setCredit(userBillBook.getCredit() + payableAmount);
					userBillBook.setUpdatedOn(new Date());
					userBillBook.setLastCreditedOn(new Date());
					userBillBookService.updateUserBillBook(userBillBook);

					admin.setWallet(admin.getWallet() - payableAmount);
					adminService.updateAdmin(admin);

					user.setWalletBalance(user.getWalletBalance() + payableAmount);
					user.setDues(user.getDues() - payableAmount);
					UserService.updateUser(user);

					transaction.setActive(Boolean.TRUE);
					transaction.setAdminId(adminId);
					transaction.setAmount(payableAmount);
					transaction.setCartId(cartId);
					transaction.setCreatedOn(new Date());
					transaction.setDeleted(Boolean.FALSE);
					transaction.setDiscreption(description);
					transaction.setOrderRcptidId(order_rcptid_11);
					transaction.setDues(0);
					transaction.setPaid(payableAmount);
					transaction.setPaymentMode(transactionMode);
					transaction.setShopId(shopId);
					transaction.setTotalAmount(payableAmount);
					// transaction.setTransactionId(transactionId);
					transaction.setTransactionStatus(transactionStatus);
					transaction.setTransactionType(transactionType);
					transaction.setUserId(Integer.parseInt(userId));
					transaction.setUserType(userType);
					// transaction.setWithdrawId(withdrawId);
					transactionService.createTransaction(transaction);

				}
				boolean updateCart = cartService.updateCart(cart);
				notificationService.createNotification(cartId, orderNotification);
				if (updateCart) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "cart update.");

				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "cart not update.");
				}

			}

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "please give correct information");
		}

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

//use localData and commeant it
	@PostMapping("order/reject")
	public ResponseEntity<Cart> cartRejectStatus(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.CART_ID));
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = null;
		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.DESCRIPTION)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			String shopId = json.get(ServiceConstants.SHOP_ID);
			String description = json.get(ServiceConstants.DESCRIPTION);
			Calculation calculation = new Calculation();

			cart = cartService.getOrderStatus(cartId, shopId);
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REJECTED");
			int orderStatus = lookUp.getLookUpId();
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			int transactionStatus = lookUp1.getLookUpId();
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			int userType = lookUp2.getLookUpId();
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
			int transactionMode = lookUp3.getLookUpId();
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "ADMIN_REJECTED");
			LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
			int online = lookUp4.getLookUpId(), transactionType = lookUp5.getLookUpId(),
					orderNotification = lookUp6.getLookUpId();

			UserBillBook userBillBook = null;
			AdminBillBook adminBillBook = null;
			List<AdminBillBook> adminBillBookList = null;
			List<UserBillBook> userBillBookList = null;
			User user = null;
			Admin admin = null;
			Transaction transaction = null;

			if (null != cart) {
				admin = adminService.getAdminByShopId(shopId);
				cart.setOrderStatus(orderStatus);
				cart.setDescription(description);

				int userId = Integer.parseInt(cart.getUserId());
				int adminId = admin.getAdminId();
				int totalAmount = cart.getPayableAmount();
				String order_rcptid_11 = Strings.EMPTY;
				order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);

				List<ItemList> localItemList = localData.getVariantData();
				if (localItemList.size() <= 0) {
					List<ItemList> itemList = itemListService.getByShopId(shopId);
					localItemList = localData.setVariantData(itemList);
				}

				List<ProductList> productList = productListService.getProductListCartId(cartId);
				if (productList.size() > 0) {
					for (int i = 0; i < productList.size(); i++) {
						int productId = Integer.parseInt(productList.get(i).getProductId()),
								productQuantity = productList.get(i).getProductQuantity();

						// Product product = productService.getProduct(productId);
						ItemList product = itemListService.getById(productId);

						product.setStock(product.getStock() + productQuantity);
						for (int j = 0; j < localItemList.size(); j++) {
							if (localItemList.get(j).getId() == productId) {
								localItemList.get(j).setStock(localItemList.get(j).getStock() + productQuantity);
							}

						}

						itemListService.updateItemList(product);
						localData.setVariantData(localItemList);

						VariantStock variantStock = variantStockService.getById(productId);
//						if (variantStock.getCurrentStock() >= productQuantity) {
						variantStock.setCurrentStock(variantStock.getCurrentStock() + productQuantity);
						variantStock.setSoldStock(variantStock.getSoldStock() - productQuantity);
//						}
						boolean updateStock = variantStockService.updateStock(variantStock);
					}
				}

				if (cart.getTransactionType() == online) {
					adminBillBookList = adminBillBookService.getBillBookByAdminId(String.valueOf(adminId));
					adminBillBook = adminBillBookList.get(0);
					userBillBookList = userBillBookService.getByUserId(String.valueOf(userId));
					userBillBook = userBillBookList.get(0);
					transaction = new Transaction(shopId, userId);

					user = UserService.getUser(userId);
					user.setDues(calculation.DecimalCalculation(user.getDues() - totalAmount));
					user.setWalletBalance(calculation.DecimalCalculation(user.getWalletBalance() + totalAmount));
					UserService.updateUser(user);

					admin = adminService.getAdmin(adminId);
					admin.setWallet(calculation.DecimalCalculation(admin.getWallet() - totalAmount));
					adminService.updateAdmin(admin);

					adminBillBook.setDebit(calculation.DecimalCalculation(adminBillBook.getDebit() + totalAmount));
					adminBillBook.setUpdatedOn(new Date());
					adminBillBook.setLastDebitedOn(new Date());
					adminBillBookService.updateAdminBillBook(adminBillBook);

					userBillBook.setCredit(calculation.DecimalCalculation(userBillBook.getCredit() + totalAmount));
					userBillBook.setLastDebitedOn(new Date());
					userBillBook.setUpdatedOn(new Date());
					userBillBookService.updateUserBillBook(userBillBook);

					transaction.setUserId(userId);
					transaction.setShopId(shopId);
					transaction.setPaymentMode(transactionMode);
					transaction.setAmount(totalAmount);
					transaction.setCreatedOn(new Date());
					transaction.setOrderRcptidId(order_rcptid_11);
					transaction.setTransactionId(Strings.EMPTY);
					transaction.setUserType(userType);
					transaction.setActive(Boolean.TRUE);
					transaction.setDeleted(Boolean.FALSE);
					transaction.setTotalAmount(totalAmount);
					transaction.setDues(0);
					transaction.setCartId(cartId);
					transaction.setPaid(cart.getPayableAmount());
					transaction.setAdminId(adminId);
					transaction.setTransactionStatus(transactionStatus);
					transaction.setDiscreption(description);
					transaction.setTransactionType(transactionType);

					transactionService.createTransaction(transaction);
				}

				boolean update = cartService.updateCart(cart);
				notificationService.createNotification(cartId, orderNotification);
				if (update) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Cart updated");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Cart did not updated");
				}
			}

		}
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@GetMapping("packedorder/{cartId}")
	ResponseEntity<Map<String, String>> packedOrder(@PathVariable("cartId") int cartId) {
		Map<String, String> response = new HashMap<String, String>();

		Cart cart = cartService.getCart(cartId);
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PACKED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
		int packed = lookUp.getLookUpId(), orderNotification = lookUp1.getLookUpId();
		cart = cartService.getCart(cartId);
		cart.setOrderStatus(packed);

		boolean result1 = cartService.updateCart(cart);
		notificationService.createNotification(cartId, orderNotification);
		if (result1) {
			response.put("status", String.valueOf(true));
			response.put("description", "Your order placed sucessfully.");

		} else {
			response.put("status", String.valueOf(false));
			response.put("description", "Your placed order has been failed.");
		}

		return ResponseEntity.ok().body(response);
	}

//	@GetMapping("packedorder1/{cartId}")
//	ResponseEntity<Map<String, String>> packedOrder5(@PathVariable("cartId") int cartId) {
//		Map<String, String> response = new HashMap<String, String>();
//
//		Cart cart = cartService.getCart(cartId);
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "PACKED");
//		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
//		int packed = lookUp.getLookUpId(), orderNotification = lookUp1.getLookUpId();
//		cart = cartService.getCart(cartId);
//		cart.setOrderStatus(packed);
//		List<ProductList> productList = productListService.getProductListCartId(cartId);
//		if (productList.size() > 0) {
//			for (int j = 0; j < productList.size(); j++) {
//				int productId = Integer.parseInt(productList.get(j).getProductId());
//				int productQuantity = productList.get(j).getProductQuantity();
//			VariantStock variantStock = variantStockService.getById(productId);
////				if (variantStock.getCurrentStock() >= productQuantity) {
//					variantStock.setCurrentStock(variantStock.getCurrentStock() + productQuantity);
//					variantStock.setSoldStock(variantStock.getSoldStock() + productQuantity);
////				}
//				boolean updateStock = variantStockService.updateStock(variantStock);
//				if (updateStock) {
//					response.put("status", Boolean.TRUE.toString());
//					response.put("description", " variant stock is update");
//				} else {
//					response.put("status", Boolean.FALSE.toString());
//					response.put("description", " variant stock is not update");
//				}
//			}
//			
//		} else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", " variant current stock is less then unitQuantity");
//		}
//		boolean result1 = cartService.updateCart(cart);
//		notificationService.createNotification(cartId, orderNotification);
//		if (result1) {
//			response.put("status", String.valueOf(true));
//			response.put("description", "Your order placed sucessfully.");
//
//		} else {
//			response.put("status", String.valueOf(false));
//			response.put("description", "Your placed order has been failed.");
//		}
//
//		return ResponseEntity.ok().body(response);
//	}

	@PostMapping("order/shipped")
	ResponseEntity<Map<String, String>> shippedOrder(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.DELIVERY_TYPE)
				&& null != json.get(ServiceConstants.DELIVERY_DATE)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID)),
					deliveryType = Integer.parseInt(json.get(ServiceConstants.DELIVERY_TYPE));

			Date deliveryDate = null;

			Cart cart = cartService.getCart(cartId);
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "BY_DELIVERY_BOY");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COURIER");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION");
			int shipped = lookUp.getLookUpId();
			int byDeliveryBoy = lookUp1.getLookUpId();
			int courier = lookUp3.getLookUpId();
			int orderNotification = lookUp4.getLookUpId();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

			cart.setOrderStatus(shipped);
			cart.setDeliveryType(deliveryType);
//			cart.setDeliveryTime(json.get(ServiceConstants.DELIVERY_TIME));
			cart.setShippingDate(new Date());
			try {
				deliveryDate = formater.parse(json.get(ServiceConstants.DELIVERY_DATE));
				cart.setDeliveryDate(deliveryDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (deliveryType == byDeliveryBoy) {
				cart.setDeliveryTime(json.get(ServiceConstants.DELIVERY_TIME));
				String dBoyName = json.get(ServiceConstants.D_BOY_NAME),
						dBoyNumber = json.get(ServiceConstants.D_BOY_NUMBER);
				cart.setdBoyName(dBoyName);
				cart.setdBoyNumber(dBoyNumber);
				if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
					int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
					cart.setDeliveryCharge(deliveryCharge);
				}

			} else if (deliveryType == courier) {
//				cart.setDeliveryTime(deliveryTime);
				String shippingId = json.get(ServiceConstants.SHIPPING_ID),
						courierName = json.get(ServiceConstants.COURIER_NAME);
				cart.setCourierName(courierName);
				cart.setShippingId(shippingId);
				if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
					int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
					cart.setDeliveryCharge(deliveryCharge);
				}
			}

			boolean result1 = cartService.updateCart(cart);
			if (result1) {
				notificationService.createNotification(cartId, orderNotification);
				response.put("status", String.valueOf(true));
				response.put("description", "Your order shipped sucessfully.");
			} else {
				response.put("status", String.valueOf(false));
				response.put("description", "Your shipped order has been failed.");
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("order/delivered/{cartId}/{otp}")
	ResponseEntity<Map<String, String>> deliveredOrder(@PathVariable("cartId") int cartId,
			@PathVariable("otp") int otp) {
		Map<String, String> response = new HashMap<String, String>();
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "BY_DELIVERY_BOY");
		int byDeliveryBoy = lookUp1.getLookUpId();
		Cart cart = cartService.getCart(cartId);
		int cartDeliveryType = cart.getDeliveryType();
		int payableAmount = cart.getPayableAmount();
		String shopId = cart.getShopId();
		if (byDeliveryBoy == cartDeliveryType) {
			String dBoyNumber = cart.getdBoyNumber();
			Delivery delivery = deliveryService.getTotal(shopId, dBoyNumber);
			delivery.setTotalAmount(delivery.getTotalAmount() + payableAmount);
			delivery.setDues(delivery.getDues() + payableAmount);
			deliveryService.updateDelivery(delivery);
		}

		if (otp == cart.getOtp()) {
			Calculation calculation = new Calculation();
			String userId = cart.getUserId();

			Admin admin = adminService.getAdminByShopId(shopId);
			int transactionType = cart.getTransactionType();
			int adminId = admin.getAdminId();
			AdminBillBook adminBillBook = null;
			UserBillBook userBillBook = null;

			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "CASH");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
			int userType = lookUp4.getLookUpId();
			int delivered = lookUp.getLookUpId();
			int cash = lookUp2.getLookUpId();
			int completed = lookUp3.getLookUpId();
			int onlinePay = lookUp5.getLookUpId();
			int orderNotification = lookUp6.getLookUpId();

			if (null != cart) {
				if (transactionType == onlinePay) {
					cart.setOrderStatus(delivered);
					cart.setActive(Boolean.FALSE);

					// int payableAmount = cart.getPayableAmount();
					admin = adminService.getAdminByShopId(shopId);
					admin.setAvailableAmount(
							calculation.DecimalCalculation(admin.getAvailableAmount() + payableAmount));
					adminService.updateAdmin(admin);
				} else {
					cart.setOrderStatus(delivered);
					cart.setPaid(cart.getPayableAmount());
					cart.setDues(calculation.DecimalCalculation(cart.getDues() - cart.getPayableAmount()));
					cart.setActive(Boolean.FALSE);

					Transaction transaction = transactionService.getTransactionByCartId(cartId);
					float totalAmount = cart.getPayableAmount(), amount = cart.getTotalAmount();
					String receiptId = Strings.EMPTY;
					receiptId = UUID.randomUUID().toString();

					transaction.setTransactionStatus(completed);
					transaction.setDiscreption("payment done of " + totalAmount + " by user " + cart.getMobileNo()
							+ " with order id " + transaction.getCartId() + "and transaction ID is" + receiptId);

					boolean createTransaction = transactionService.updateTransaction(transaction);
				}
				boolean result1 = cartService.updateCart(cart);
				notificationService.createNotification(cartId, orderNotification);
				if (result1) {
					response.put("status", String.valueOf(true));
					response.put("description", "Your order delivered sucessfully.");
				} else {
					response.put("status", String.valueOf(false));
					response.put("description", "Your delivered order has been failed.");
				}
			}
		} else {
			response.put("status", String.valueOf(false));
			response.put("description", "Your otp is not valid.");
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("order/denied")
	ResponseEntity<Map<String, String>> deniedOrder(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.COMMENT) && null != json.get(ServiceConstants.CART_ID)
				&& null != json.get(ServiceConstants.DENIED)) {
			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
			String comment = json.get(ServiceConstants.COMMENT);
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "DENIED");
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "CART_REVIEW");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
			LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "USER_DENIED");
			LookUp lookUp8 = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION ");
			Calculation calculation = new Calculation();
			int denied = lookUp.getLookUpId();
			int cartReview = lookUp1.getLookUpId();
			int received = lookUp2.getLookUpId();
			int onlinePay = lookUp3.getLookUpId();
			int completed = lookUp4.getLookUpId();
			int adminUser = lookUp5.getLookUpId();
			int refund = lookUp6.getLookUpId();
			int orderNotification = lookUp8.getLookUpId();
			int transactionType = lookUp7.getLookUpId();
			boolean deniedType = Boolean.parseBoolean(json.get(ServiceConstants.DENIED));
			Admin admin = null;
			User user = null;
			AdminBillBook adminBillBook = null;
			UserBillBook userBillBook = null;
			List<AdminBillBook> adminBillBookList = null;
			List<UserBillBook> userBillBookList = null;

			Cart cart = cartService.getCart(cartId);
			if (null != cart) {
				String userId = cart.getUserId();
				String shopId = cart.getShopId();
				int paymentType = cart.getTransactionType();

				if (deniedType) {
					cart.setOrderStatus(denied);
					if (paymentType == onlinePay) {
						int payableAmount = cart.getPayableAmount();
						admin = adminService.getAdminByShopId(shopId);
						int adminId = admin.getAdminId();
						admin.setWallet(calculation.DecimalCalculation(admin.getWallet() - payableAmount));
						adminService.updateAdmin(admin);

						adminBillBookList = adminBillBookService.getBillBookByAdminId(String.valueOf(adminId));
						adminBillBook = adminBillBookList.get(0);
						adminBillBook
								.setDebit(calculation.DecimalCalculation(adminBillBook.getDebit() + payableAmount));
						adminBillBook.setLastDebitedOn(new Date());
						adminBillBook.setUpdatedOn(new Date());
						boolean update = adminBillBookService.updateAdminBillBook(adminBillBook);
						if (update) {

							user = UserService.getUser(Integer.parseInt(userId));
							user.setWalletBalance(
									calculation.DecimalCalculation(user.getWalletBalance() + payableAmount));
							UserService.updateUser(user);

							userBillBookList = userBillBookService.getByUserId(userId);
							userBillBook = userBillBookList.get(0);
							userBillBook.setCredit(
									calculation.DecimalCalculation(userBillBook.getCredit() + cart.getTotalAmount()));
							userBillBook.setLastCreditedOn(new Date());
							userBillBook.setUpdatedOn(new Date());
							boolean updateUserBillBook = userBillBookService.updateUserBillBook(userBillBook);
							if (updateUserBillBook) {
								Transaction transaction = new Transaction(shopId, Integer.parseInt(userId));
								String orderRcptidId = cart.getTransactionId();

								transaction.setActive(Boolean.TRUE);
								transaction.setAdminId(adminId);
								transaction.setAmount(cart.getTotalAmount());
								transaction.setCartId(cartId);
								transaction.setCreatedOn(new Date());
								transaction.setDeleted(Boolean.FALSE);
								transaction.setDiscreption(comment);
								transaction.setOrderRcptidId(orderRcptidId);
								transaction.setPaid(payableAmount);
								transaction.setPaymentMode(refund);
								transaction.setTransactionType(transactionType);
								transaction.setShopId(shopId);
								transaction.setTotalAmount(payableAmount);
								transaction.setTransactionStatus(completed);
								transaction.setUserId(Integer.parseInt(userId));
								transaction.setUserType(adminUser);

								transactionService.createTransaction(transaction);
								response.put("description", "Your order denied sucessfully.");
							}
						}
					}
				} else {
					cart.setOrderStatus(received);
//					if (paymentType == onlinePay) {
//						int payableAmount = cart.getPayableAmount();
//						admin = adminService.getAdminByShopId(shopId);
//						admin.setAvailableAmount(admin.getAvailableAmount() + payableAmount);
//						adminService.updateAdmin(admin);
//					}
//					response.put("description", "Your order received sucessfully.");
				}
				cart.setReview(comment);

				Review review = new Review(userId, shopId);
				String name = cart.getUserName();

				review.setActive(Boolean.TRUE);
				review.setCartId(cartId);
				review.setComment(comment);
				review.setName(name);
				review.setReviewType(cartReview);
				review.setShopId(shopId);
				review.setUserId(userId);
				review.setDeleted(Boolean.FALSE);
				review.setCreatedOn(new Date());

				reviewService.createReview(review);

				boolean result1 = cartService.updateCart(cart);
				if (result1) {
					notificationService.createNotification(cartId, orderNotification);
					response.put("status", String.valueOf(true));

				} else {
					response.put("status", String.valueOf(false));
					response.put("description", "Your denied order has been failed.");
				}
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("order/shipped1")
	ResponseEntity<Map<String, String>> shippedOrder1(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.DELIVERY_TYPE)
				&& null != json.get(ServiceConstants.DELIVERY_DATE)) {
			int cartId = Integer.parseInt((json.get(ServiceConstants.CART_ID)));
			int deliveryType = Integer.parseInt(json.get(ServiceConstants.DELIVERY_TYPE));
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:a");
			Cart cart = cartService.getCart(cartId);
			Date deliveryDate = null;
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "BY_DELIVERY_BOY");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COURIER");
			int shipped = lookUp.getLookUpId();
			int deliveryBoy = lookUp1.getLookUpId();
			int courier = lookUp3.getLookUpId();
			int selfDelivery = lookUp2.getLookUpId();
			if (cart != null) {
				cart.setOrderStatus(shipped);
				cart.setActive(Boolean.TRUE);
				cart.setOrderStatus(shipped);

				cart.setShippingDate(new Date());
				try {
					deliveryDate = formater.parse(json.get(ServiceConstants.DELIVERY_DATE));
					cart.setDeliveryDate(deliveryDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (deliveryType == deliveryBoy) {
					// Date shippingDate = new Date();
					cart.setDeliveryType(deliveryBoy);
					String deliveryBoyName = json.get(ServiceConstants.D_BOY_NAME);
					String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);

					cart.setdBoyName(deliveryBoyName);
					cart.setMobileNo(mobileNo);
					if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
						int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
						cart.setDeliveryCharge(deliveryCharge);
						boolean result = cartService.updateCart(cart);
						if (result) {
							response.put("Status", Boolean.TRUE.toString());
							response.put("Descreption", "the courier is delivered by delivery boy");
						} else {
							response.put("Status", Boolean.FALSE.toString());
							response.put("Descreption", "the courier is not delivered by delivery boy");
						}
					}
				} else if (deliveryType == courier) {
					cart.setDeliveryType(courier);
					String courierName = json.get(ServiceConstants.COURIER_NAME);
					String shippingId = json.get(ServiceConstants.SHIPPING_ID);
					cart.setCourierName(courierName);
					cart.setDeliveryDate(deliveryDate);
					cart.setShippingId(shippingId);
					if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
						int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
						cart.setDeliveryCharge(deliveryCharge);
					} else {
						cart.setDeliveryType(selfDelivery);
					}

				}
				boolean result = cartService.updateCart(cart);
				if (result) {
					response.put("Status", Boolean.TRUE.toString());
					response.put("Descreption", " Your order is shipping");
				} else {
					response.put("Status", Boolean.FALSE.toString());
					response.put("Descreption", " Your order is not shipping");
				}
			} else {
				response.put("Status", Boolean.FALSE.toString());
				response.put("Descreption", " Cart is empty");
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getallcart")
	public ResponseEntity<List<Cart>> getAllCart() {
		List<Cart> cartList = cartService.getAllCart();
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("getcart/bydBoyNumber/{dBoyNumber}/{shopId}")
	public ResponseEntity<List<Cart>> getCartByDeliveryBoyNumber(@PathVariable("dBoyNumber") String dBoyNumber,
			@PathVariable("shopId") String shopId) {
		List<Cart> cartList = cartService.getCartByDeliveryBoyNumber(dBoyNumber, shopId);
		for (int i = 0; i < cartList.size(); i++) {
			int cartId = cartList.get(i).getCartId();
			List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
			cartList.get(i).setProductList(productList);
		}

		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("getdeactivecartbyuseridandshopid/{userId}/{shopId}")
	public ResponseEntity<List<Cart>> getDeactiveCartByUserIdAndShopId(@PathVariable("userId") int userId,
			@PathVariable("shopId") String shopId) {
		List<Cart> cartList = cartService.getDeactiveCartByUserIdAndShopId(userId, shopId);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable("cartId") Integer cartId) {
		Cart cart = cartService.getCart(cartId.intValue());
		int id = cart.getAddressId();
		Address address = addressService.getAddress(id);
		cart.setAddress(address);
		List<ProductList> productList = productListService.getProductListCartId(cartId);
		cart.setProductList(productList);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@GetMapping("get/productlist/{cartId}")
	public ResponseEntity<Cart> getProductListByCartId(@PathVariable("cartId") Integer cartId) {
		Cart cart = cartService.getCart(cartId.intValue());
//		int id = cart.getAddressId();
//		Address address = addressService.getAddress(id);
//		cart.setAddress(address);
		List<ProductList> productList = productListService.getProductListCartId(cartId);
		cart.setProductList(productList);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@GetMapping("getcartfororderbycartid/{cartId}")
	public ResponseEntity<List<Cart>> getCartForOrderByCartId(@PathVariable("cartId") int cartId) {
		List<Cart> cartList = cartService.getCartForOrderByCartId(cartId);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

// use localData and comments it
	@GetMapping("getcartfor/orderbycartid/{cartId}")
	public ResponseEntity<List<Cart>> getCartForOrderByCartId1(@PathVariable("cartId") int cartId) {
		List<Cart> cartList = cartService.getCartForOrderByCartId(cartId);
		List<Cart> cartList1 = null;
		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		int dective = lookup1.getLookUpId();
		int orderStatus = cartList.get(0).getOrderStatus();
		if (orderStatus == dective) {
			Cart cart = cartList.get(0);
			String userId = cart.getUserId();
			String shopId = cart.getShopId();
			cartList1 = cartService.combinedOrderForAdmin(shopId, orderStatus);
			Address address = addressService.getDefaultAddress(userId);
			cart.setAddress(address);
			List<ItemList> localItemList = localData.getVariantData();
			if (localItemList.size() <= 0) {
				List<ItemList> itemList = itemListService.getByShopId(shopId);
				localItemList = localData.setVariantData(itemList);
			}
			List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
			for (int i = 0; i < productList.size(); i++) {
				int quantity = productList.get(i).getProductQuantity();
				String productId = productList.get(i).getProductId();

				ItemList product = itemListService.getById(Integer.parseInt(productId));
				product.setStock(product.getStock() - quantity);
				for (int j = 0; j < localItemList.size(); j++) {
					if (localItemList.get(j).getId() == Integer.parseInt(productId)) {
						localItemList.get(j).setStock(localItemList.get(j).getStock() - quantity);
					}

				}
				itemListService.updateItemList(product);
				localData.setVariantData(localItemList);

				VariantStock variantStock = variantStockService.getById(Integer.parseInt(productId));
				if (variantStock.getCurrentStock() >= quantity) {
					variantStock.setCurrentStock(variantStock.getCurrentStock() - quantity);
					variantStock.setSoldStock(variantStock.getSoldStock() + quantity);
				}
				boolean updateStock = variantStockService.updateStock(variantStock);
			}
			cart.setProductList(productList);

		}
		return new ResponseEntity<List<Cart>>(cartList1, HttpStatus.OK);
	}

	@GetMapping("/get/cartby/shopid/userid/{shopId}/{userId}")
	public ResponseEntity<?> getCartByShopIdUserId(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		int orderStatus = lookup1.getLookUpId();
		List<Cart> cart = null;
		cart = cartService.getCartByUserId(userId, shopId, orderStatus);
		if (null != cart && cart.size() > 0) {
			int cartId = cart.get(0).getCartId();
			List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
			if (productList.size() > 0) {
				for (int i = 0; i < productList.size(); i++) {
					// Product product =
					// productService.getProduct(Integer.parseInt(productList.get(i).getProductId()));
					ItemList product = itemListService.getById(Integer.parseInt(productList.get(i).getProductId()));
					productList.get(i).setCurrentStock(product.getStock());

				}
				cart.get(0).setProductList(productList);
			} else {
				cart.get(0).setPrice(0);
				cart.get(0).setDues(0);
				cart.get(0).setTotalAmount(0);
				cart.get(0).setPayableAmount(0);
				cart.get(0).setGstAmount(0);
				cart.get(0).setDiscount(0);
				cart.get(0).setMrp(0);
				cartService.updateCart(cart.get(0));
			}
		}
		return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
	}

	@GetMapping("/get/cartbyall/shopid/userid/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> getCartByShopIdUserId5(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		Map<String, String> response = new HashMap<String, String>();
		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		int orderStatus = lookup1.getLookUpId();
		List<Cart> cart = null;
		cart = cartService.getCartByUserId(userId, shopId, orderStatus);
		if (null != cart) {
			int cartId = cart.get(0).getCartId();
			List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
			if (productList.size() > 0) {
				for (int i = 0; i < productList.size(); i++) {
					// Product product =
					// productService.getProduct(Integer.parseInt(productList.get(i).getProductId()));
					ItemList product = itemListService.getById(Integer.parseInt(productList.get(i).getProductId()));
					productList.get(i).setCurrentStock(product.getStock());

				}
				cart.get(0).setProductList(productList);
			} else {
				cart.get(0).setPrice(0);
				cart.get(0).setDues(0);
				cart.get(0).setTotalAmount(0);
				cart.get(0).setPayableAmount(0);
				cart.get(0).setGstAmount(0);
				cart.get(0).setDiscount(0);
				cart.get(0).setMrp(0);
				cartService.updateCart(cart.get(0));
			}
		} else {
			response.put("Status", Boolean.FALSE.toString());
			response.put("Descreption", " Your order is not shipping");

		}
		return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
	}

//	@GetMapping("/get/combined/cartby/userid/{userId}")
//	public ResponseEntity<List<Cart>> getCombinedCart(@PathVariable("userId") String userId) {
//		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
//		int orderStatus = lookup1.getLookUpId();
//		List<Cart> cartList = null;
//		List<Cart> cart = null;
//		Cart cart1 = null;
//		List<ProductList> productList = null;
//		List<ProductList> productList1 = null;
//		int gstAmount = 0, totalAmount = 0, dues = 0, discount = 0;
//		cart = cartService.getCartByUserIdAndOrderStatus(userId, orderStatus);
//
//		if (null != cart) {
//			productList = productListService.getProductListCartId(cart.get(0).getCartId());
//			productList.clear();
//			cartList = cartService.getCartByUserIdAndOrderStatus(userId, orderStatus);
//			cartList.clear();
//			cartList.add(cart.get(0));
//			for (int i = 0; i < cart.size(); i++) {
//				gstAmount = gstAmount + cart.get(i).getGstAmount();
//				totalAmount = totalAmount + cart.get(i).getTotalAmount();
//				dues = dues + cart.get(i).getDues();
//				discount = discount + cart.get(i).getDiscount();
//				int cartId = cart.get(i).getCartId();
//				productList1 = productListService.getProductListCartId(cartId);
//
//				if (productList1.size() > 0) {
//					for (int j = 0; j < productList1.size(); j++) {
//						productList.add(productList1.get(j));
//					}
//				}
//			}
//			cartList.get(0).setGstAmount(gstAmount);
//			cartList.get(0).setTotalAmount(totalAmount);
//			cartList.get(0).setDiscount(discount);
//			cartList.get(0).setDues(dues);
//			cartList.get(0).setProductList(productList);
//
//		}
//		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
//	}

	@GetMapping("/get/all/cartby/userid/{userId}")
	public ResponseEntity<List<Cart>> getAllCart(@PathVariable("userId") String userId) {
		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		int orderStatus = lookup1.getLookUpId();
		List<Cart> cart = null;

		List<ProductList> productList = null;
		cart = cartService.getCartByUserIdAndOrderStatus(userId, orderStatus);

		if (null != cart) {
			for (int i = 0; i < cart.size(); i++) {
				int cartId = cart.get(i).getCartId();
				productList = productListService.getProductListCartId(cartId);
				if (productList.size() > 0) {
					for (int j = 0; j < productList.size(); j++) {
						ItemList product = itemListService.getById(Integer.parseInt(productList.get(i).getProductId()));
//						Product product = productService
//								.getProduct(Integer.parseInt(productList.get(j).getProductId()));
						productList.get(j).setCurrentStock(product.getStock());

					}
					cart.get(i).setProductList(productList);

				} else {
					cart.get(i).setPrice(0);
					cart.get(i).setDues(0);
					cart.get(i).setTotalAmount(0);
					cart.get(i).setPayableAmount(0);
					cart.get(i).setGstAmount(0);
					cart.get(i).setDiscount(0);
					cart.get(i).setMrp(0);
					cart.get(i).setDeliveryCharge(0);
					cartService.updateCart(cart.get(i));
				}
			}
		}
		return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
	}

//	@GetMapping("getallamount/{shopId}")
//	public ResponseEntity<List<Cart>> getAllAmount(@PathVariable("shopId") String shopId){
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
//		int online = lookUp.getLookUpId();
//		List<Cart> cartList = cartService.getAllAmount(shopId);
//		if()
//		for(int i=0; i> cartList.size(); i++) {
//			int totalAmount = cartList.get(i).getPayableAmount();
//			cartList.get(i).setPayableAmount(cartList.get(i).getPayableAmount() +totalAmount );
//		}
//		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
//	}

	@GetMapping("getcartbyuserid/{userId}")
	public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable("userId") String userId) {
		List<Cart> cartList = cartService.getCartByUserId(userId);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/byshopId/{shopId}")
	public ResponseEntity<List<Cart>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Cart> cartList = cartService.getCartByShopIdId(shopId);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("getallproductlist/byshopid/{shopId}")
	public ResponseEntity<?> getProductListByCartId(@PathVariable("shopId") String shopId) {
		Map<String, String> response = new HashMap<String, String>();
		List<Cart> cartList = cartService.getAllCartByShopId(shopId);
		List<Cart> cartList1 = new ArrayList<Cart>();
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		int received = lookUp.getLookUpId();
		int delivered = lookUp1.getLookUpId();
		int successful = lookUp2.getLookUpId();
		List<ProductList> productList = null;
		for (int i = 0; i < cartList.size(); i++) {
			int orderStatus = cartList.get(i).getOrderStatus();

			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
				int cartId = cartList.get(i).getCartId();
				productList = productListService.getProductListCartId(cartId);
				cartList.get(i).setProductList(productList);
				cartList1.add(cartList.get(i));
			}

		}
		return new ResponseEntity<>(cartList1, HttpStatus.OK);
	}

	@GetMapping("getallcart/totalamount/{shopId}")
	public ResponseEntity<?> getProductListByCartIdTest(@PathVariable("shopId") String shopId) {
		Map<String, String> response = new HashMap<String, String>();
		List<Cart> cartList = cartService.getAllCartByShopId(shopId);
		List<Cart> cartList1 = new ArrayList<Cart>();
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		int received = lookUp.getLookUpId();
		int delivered = lookUp1.getLookUpId();
		int successful = lookUp2.getLookUpId();
		List<ProductList> productList = null;
		int totalAmount = 0;
		LocalDate localDate = null;
		LocalDate nowDate = LocalDate.now();
		for (int i = 0; i < cartList.size(); i++) {
			int orderStatus = cartList.get(i).getOrderStatus();
			Date date = cartList.get(i).getSlotDate();
			localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
				cartList1.add(cartList.get(i));
				if (localDate.compareTo(nowDate) == 0) {
					// cartList1.add(cartList.get(i));
					totalAmount = totalAmount + cartList.get(i).getPayableAmount();
				}

			}
//			cartList.get(i).setProductList(productList);
		}
		response.put("status", Boolean.TRUE.toString());
		response.put("totalAmount", String.valueOf(totalAmount));
		response.put("date", String.valueOf(localDate));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("get/totalamount/byshopId/{shopId}")
	public ResponseEntity<Map<String, String>> getAllCartByShopId(@PathVariable("shopId") String shopId) {
		Map<String, String> response = new HashMap<String, String>();
		List<Cart> cartList = cartService.getCartByShopIdId(shopId);
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		int received = lookUp.getLookUpId();
		int delivered = lookUp1.getLookUpId();
		int successful = lookUp2.getLookUpId();
		int totalAmount = 0;
		for (int i = 0; i < cartList.size(); i++) {
			int orderStatus = cartList.get(i).getOrderStatus();
			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
				int payableAmount = cartList.get(i).getPayableAmount();
				totalAmount = totalAmount + payableAmount;

			}
		}
		response.put("status", Boolean.TRUE.toString());
		response.put("totalPayableAmount", String.valueOf(totalAmount));
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}

//	@GetMapping("get/totalamount/test/byshopId/{shopId}")
//	public ResponseEntity<Map<String, String>> getAllCartByShopIdTest(@PathVariable("shopId") String shopId) {
//		Map<String, String> response = new HashMap<String, String>();
//		List<Cart> cartList = cartService.getCartByShopIdId(shopId);
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
//		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
//		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
//		int received = lookUp.getLookUpId();
//		int delivered = lookUp1.getLookUpId();
//		int successful = lookUp2.getLookUpId();
//		int totalAmount = 0;
//		Date input = new Date();
//
//		LocalDate date = LocalDate.now();
//		for (int i = 0; i < cartList.size(); i++) {
//			Date todayDate = cartList.get(i).getSlotDate();
//			LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(todayDate));
//			int orderStatus = cartList.get(i).getOrderStatus();
//			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
////				if (localDate.compareTo(date) == 1) {
//					int payableAmount = cartList.get(i).getPayableAmount();
//					totalAmount = totalAmount + payableAmount;
////				}
//
//			}
//		}
//		response.put("status", Boolean.TRUE.toString());
//		response.put("totalPayableAmount", String.valueOf(totalAmount));
//		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
//	}

	@GetMapping("getoffline/{shopId}/{orderStatus}")
	public ResponseEntity<List<Cart>> getOfflineCart(@PathVariable("shopId") String shopId,
			@PathVariable("orderStatus") int orderStatus) {
		List<Cart> cartList = cartService.getOfflineCart(shopId, orderStatus);
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/accepted/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> acceptedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/user/order/{userId}")
	public ResponseEntity<List<Cart>> getOrderForUser(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		List<Cart> cartList = cartService.orderDetailForUser(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/rejected/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> rejectedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "REJECTED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/packed/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> packedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PACKED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/shipped/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> shippedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/delivered/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> deliveredOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/successful/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> successfulOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/placed/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> placedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PLACED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/denied/order/{shopId}/{userId}")
	public ResponseEntity<List<Cart>> deniedOrder(@PathVariable("shopId") String shopId,
			@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DENIED");
		List<Cart> cartList = cartService.orderDetails(shopId, userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/combined/accepted/order/{userId}")
	public ResponseEntity<List<Cart>> combinedAcceptOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/rejected/order/{userId}")
	public ResponseEntity<List<Cart>> combinedRejectedOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "REJECTED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/packed/order/{userId}")
	public ResponseEntity<List<Cart>> combinedPackedOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PACKED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/shipped/order/{userId}")
	public ResponseEntity<List<Cart>> combinedShippedOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/delivered/order/{userId}")
	public ResponseEntity<List<Cart>> combinedDeliveredOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/placed/order/{userId}")
	public ResponseEntity<List<Cart>> combinedPlacedOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PLACED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/placed/order/foradmin/{shopId}")
	public ResponseEntity<List<Cart>> combinedPlacedOrderForAdmin(@PathVariable("shopId") String shopId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "PLACED");
		List<Cart> cartList = cartService.combinedOrderForAdmin(shopId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

	@GetMapping("get/combined/denied/order/{userId}")
	public ResponseEntity<List<Cart>> combinedDeniedOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "DENIED");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/combined/successful/order/{userId}")
	public ResponseEntity<List<Cart>> combinedSuccessfulOrder(@PathVariable("userId") String userId) {
		LookUp orderStatus = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		List<Cart> cartList = cartService.combinedOrderDetails(userId, orderStatus.getLookUpId());
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);

	}

	@GetMapping("get/bycartid/shopip/{cartId}/{shopId}")
	public ResponseEntity<Cart> getByCartIdShopId(@PathVariable("cartId") int cartId,
			@PathVariable("shopId") String shopId) {
		Cart cart = cartService.getOrderStatus(cartId, shopId);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

// use localData and comment it
	@GetMapping("orderstatus/{cartId}/{shopId}/{orderStatus}")
	public ResponseEntity<List<ProductList>> getOrderStatus(@PathVariable("cartId") int cartId,
			@PathVariable("shopId") String shopId, @PathVariable("orderStatus") int orderStatus) {
		Map<String, String> response = new HashMap<String, String>();
		Cart cart = cartService.getOrderStatus(cartId, shopId);
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "PLACED");
		int accepted = lookUp.getLookUpId();
		int placed = lookUp1.getLookUpId();
		List<ProductList> productList = null;
		ItemList product = null;

		List<ItemList> localItemList = localData.getVariantData();
		if (localItemList.size() <= 0) {
			List<ItemList> itemList = itemListService.getByShopId(shopId);
			localItemList = localData.setVariantData(itemList);
		}
		if (orderStatus == accepted) {
			cart.setOrderStatus(accepted);
			cart.setActive(Boolean.FALSE);
			cart.setDeleted(Boolean.TRUE);
			productList = productListService.getProductListCartId(cartId);
			for (int i = 0; i < productList.size(); i++) {
				int productId = Integer.parseInt(productList.get(i).getProductId());
				int productQuantity = (int) productList.get(i).getProductQuantity();
				product = itemListService.getById(productId);

				product.setStock(product.getStock() - productQuantity);
				for (int j = 0; j < localItemList.size(); j++) {
					if (localItemList.get(j).getId() == productId) {
						localItemList.get(j).setStock(localItemList.get(0).getStock() - productQuantity);
					}
					localData.setVariantData(localItemList);
				}
				itemListService.updateItemList(product);

				VariantStock variantStock = variantStockService.getById(productId);
				if (variantStock.getCurrentStock() >= productQuantity) {
					variantStock.setCurrentStock(variantStock.getCurrentStock() - productQuantity);
					variantStock.setSoldStock(variantStock.getSoldStock() - productQuantity);
				}
				boolean updateStock = variantStockService.updateStock(variantStock);
			}

			int userId = Integer.parseInt(cart.getUserId());
			User user = UserService.getUser(userId);
			user.setDues(user.getDues() + cart.getTotalAmount());
			UserService.updateUser(user);
		} else if (orderStatus == placed) {
			cart.setOrderStatus(placed);
			cart.setActive(Boolean.FALSE);
			cart.setDeleted(Boolean.TRUE);
		} else {
			cart.setOrderStatus(orderStatus);
		}

		boolean update = cartService.updateCart(cart);
		if (update) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Cart updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Cart did not updated");
		}
		// return ResponseEntity.ok().body(response);
		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
	}

//	@PostMapping("order/denied")
//	ResponseEntity<Map<String, String>> deniedOrder(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.COMMENT) && null != json.get(ServiceConstants.CART_ID) && null != json.get(ServiceConstants.DENIED)) {
//			int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
//			String comment = json.get(ServiceConstants.COMMENT);
//			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "DENIED");
//			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
//			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "CART_REVIEW");
//
//			int denied = lookUp.getLookUpId();
//			int received = lookUp2.getLookUpId();
//			int cartReview = lookUp1.getLookUpId();
//			boolean deniedType = Boolean.parseBoolean(json.get(ServiceConstants.DENIED));
//
//			Cart cart = cartService.getCart(cartId);
//			if (null != cart) {
//				String userId = cart.getUserId();
//				String shopId = cart.getShopId();
//				if(deniedType) {
//					cart.setOrderStatus(denied);
//				} else {
//					cart.setOrderStatus(received);
//				}
//				cart.setReview(comment);
//
//				Review review = new Review(userId, shopId);
//				String name = cart.getUserName();
//
//				review.setActive(Boolean.TRUE);
//				review.setCartId(cartId);
//				review.setComment(comment);
//				review.setName(name);
//				review.setReviewType(cartReview);
//				review.setShopId(shopId);
//				review.setUserId(userId);
//				review.setDeleted(Boolean.FALSE);
//				review.setCreatedOn(new Date());
//
//				reviewService.createReview(review);
//				boolean result1 = cartService.updateCart(cart);
//				if (result1) {
//					response.put("status", String.valueOf(true));
//					response.put("description", "Your order denied sucessfully.");
//				} else {
//					response.put("status", String.valueOf(false));
//					response.put("description", "Your denied order has been failed.");
//				}
//			}
//		}
//		return ResponseEntity.ok().body(response);
//	}

//	@GetMapping("deliveredorder/{cartId}")
//	ResponseEntity<Map<String, String>> deliveredOrder(@PathVariable("cartId") int cartId) {
//		Map<String, String> response = new HashMap<String, String>();
//
//		Cart cart = cartService.getCart(cartId);
//		String userId = cart.getUserId();
//		String shopId = cart.getShopId();
//
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
//		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "CART_REVIEW");
//		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "CASH");
//		LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
//		LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "FAILD");
//		int delivered = lookUp.getLookUpId();
//		int cartReview = lookUp1.getLookUpId();
//		int paymentMode = lookUp2.getLookUpId();
//		int completed = lookUp3.getLookUpId();
//		int faild = lookUp4.getLookUpId();
//		cart = cartService.getCart(cartId);
//		cart.setOrderStatus(delivered);
//
//		Review review = new Review(userId, shopId);
//		String comment = review.getComment();
//		String name = review.getName();
//		int reviewType = review.getReviewType();
//
//		review.setActive(Boolean.TRUE);
//		review.setCartId(cartId);
//		review.setComment(comment);
//		review.setName(name);
//		review.setReviewType(reviewType);
//		review.setShopId(shopId);
//		review.setUserId(userId);
//		review.setDeleted(Boolean.FALSE);
//		review.setCreatedOn(new Date());
//
//		Transaction transaction = new Transaction(shopId, Integer.parseInt(userId));
//
//		int amount = transaction.getAmount();
//		int userType = transaction.getUserType(), adminId = transaction.getAdminId(),
//				transactionStatus = transaction.getTransactionStatus();
//		float dues = transaction.getDues(), totalAmount = transaction.getTotalAmount();
//		String transactionId = transaction.getTransactionId(), discreption = transaction.getDiscreption(),
//				orderRcptidId = transaction.getOrderRcptidId();
//		
//	
//
//		transaction.setUserId(Integer.parseInt(userId));
//		transaction.setTransactionId(transactionId);
//		transaction.setCreatedOn(new Date());
//		transaction.setActive(Boolean.TRUE);
//		transaction.setAdminId(adminId);
//		transaction.setDeleted(Boolean.FALSE);
//		transaction.setDiscreption(discreption);
//		transaction.setDues(dues);
//		transaction.setOrderRcptidId(orderRcptidId);
//		transaction.setPaid(cart.getTotalAmount());
//		transaction.setShopId(shopId);
//		transaction.setTotalAmount(totalAmount);
//		transaction.setTransactionStatus(transactionStatus);
//		transaction.setAmount(amount);
//	
//		boolean update1 = transactionService.updateTransaction(transaction);
//		          
//		boolean update = reviewService.updateReview(review);
//
//		boolean result1 = cartService.updateCart(cart);
//		if (result1) {
//			response.put("status", String.valueOf(true));
//			response.put("description", "Your order delivered sucessfully.");
//
//		} else {
//			response.put("status", String.valueOf(false));
//			response.put("description", "Your delivered order has been failed.");
//		}
//
//		return ResponseEntity.ok().body(response);
//	}

	@GetMapping("getcartforuserbyshopid/{shopId}")
	public ResponseEntity<List<Cart>> getCartForUserByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Cart> cartList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			cartList = cartService.getCartForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Cart>>(cartList, HttpStatus.OK);
	}

//	@GetMapping("getcartfor/delivery/{shopId}/{dBoyNumber}")
//	public ResponseEntity<List<Cart>> getAllCartForDelivery(@PathVariable("shopId") String shopId, @PathVariable("dBoyNumber") String dBoyNumber){
//		List<Cart> cartList = null ;
//			cartList = cartService.getCartByDeliveryBoyNumber(dBoyNumber, shopId);
//			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
//			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
//			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
//			int received = lookUp.getLookUpId();
//			int delivered = lookUp1.getLookUpId();
//			int successful = lookUp2.getLookUpId();
//			List<Cart> cartList1 = new ArrayList<Cart>();
//			
//			for(int i = 0; i< cartList.size(); i++) {
//				int orderStatus = cartList.get(i).getOrderStatus();					
//				if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
////					cartList = cartService.getAllCartForDelivery(shopId, dBoyNumber, orderStatus);
//					cartList1.add(cartList.get(i));
//			}		
//				
//				}
//		
//		return new ResponseEntity<List<Cart>>(cartList1, HttpStatus.OK);
//	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCart(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != cartService.getCart(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Cart cart = cartService.getCart(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID).toString());
				cart.setCartId(id);
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				cart.setShopId(shopId);
			}

			if (null != json.get(ServiceConstants.USER_ID)) {
				String userId = json.get(ServiceConstants.USER_ID).toString();
				cart.setUserId(userId);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				cart.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT).toString());
				cart.setGstAmount(gstAmount);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt((json.get(ServiceConstants.TOTAL_AMOUNT).toString()));
				cart.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.TRANSACTION_ID)) {
				String transactionId = json.get(ServiceConstants.TRANSACTION_ID).toString();
				cart.setTransactionId(transactionId);
			}

			if (null != json.get(ServiceConstants.DUES)) {
				int dues = Integer.parseInt((json.get(ServiceConstants.DUES).toString()));
				cart.setDues(dues);
			}
			if (null != json.get(ServiceConstants.PAID)) {
				int paid = Integer.parseInt(json.get(ServiceConstants.PAID).toString());
				cart.setPaid(paid);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				System.out.println(createdOn);
				cart.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.IS_OFFLINE)) {
				boolean isOffline = Boolean.parseBoolean(json.get(ServiceConstants.IS_OFFLINE).toString());
				cart.setOffline(isOffline);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT).toString());
				System.out.println(gstAmount);
				cart.setGstAmount(gstAmount);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT).toString());
				System.out.println(totalAmount);
				cart.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.TRANSACTION_ID)) {
				String transactionId = json.get(ServiceConstants.TRANSACTION_ID).toString();
				System.out.println(transactionId);
				cart.setTransactionId(transactionId);
			}

			if (null != json.get(ServiceConstants.DUES)) {
				int dues = Integer.parseInt(json.get(ServiceConstants.DUES).toString());
				System.out.println(dues);
				cart.setDues(dues);
			}
			if (null != json.get(ServiceConstants.PAID)) {
				int paid = Integer.parseInt(json.get(ServiceConstants.PAID).toString());
				System.out.println(paid);
				cart.setPaid(paid);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean((json.get(ServiceConstants.IS_ACTIVE).toString()));
				System.out.println(isActive);
				cart.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean((json.get(ServiceConstants.IS_DELETED).toString()));
				System.out.println(isDeleted);
				cart.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.ORDER_STATUS)) {
				int orderStatus = Integer.parseInt((json.get(ServiceConstants.ORDER_STATUS).toString()));
				System.out.println(orderStatus);
				cart.setOrderStatus(orderStatus);
			}

			if (null != json.get(ServiceConstants.TRANSACTION_TYPE)) {
				int transactionType = Integer.parseInt(json.get(ServiceConstants.TRANSACTION_TYPE).toString());
				System.out.println(transactionType);
				cart.setTransactionType(transactionType);
			}
			if (null != json.get(ServiceConstants.ORDER_DATE)) {
				Date orderDate = new Date();
				System.out.println(orderDate);
				cart.setOrderDate(orderDate);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean((json.get(ServiceConstants.IS_ACTIVE).toString()));
				cart.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean((json.get(ServiceConstants.IS_DELETED).toString()));
				cart.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.ORDER_TYPE)) {
				int orderType = Integer.parseInt(json.get(ServiceConstants.ORDER_TYPE));
				cart.setOrderType(orderType);
			}

			if (null != json.get(ServiceConstants.ADDRESS_ID)) {
				int addressId = Integer.parseInt(json.get(ServiceConstants.ADDRESS_ID));
				cart.setAddressId(addressId);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				cart.setMobileNo(mobileNo);
			}
			if (null != json.get(ServiceConstants.USER_NAME)) {
				String userName = json.get(ServiceConstants.USER_NAME);
				cart.setUserName(userName);
			}

			if (null != json.get(ServiceConstants.SLOT_DATE)) {
				Date slotDate = new Date();
				cart.setSlotDate(slotDate);
			}

			if (null != json.get(ServiceConstants.DISCREPTION)) {
				String discreption = json.get(ServiceConstants.DISCREPTION);
				cart.setDescription(discreption);
			}

			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				String adminId = json.get(ServiceConstants.ADMIN_ID);
				cart.setAdminId(adminId);
			}

			cartService.updateCart(cart);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Cart updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Cart doesn't exist with given  userid");
		}
		return ResponseEntity.ok().body(response);
	}

}
