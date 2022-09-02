package com.shop.shopservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.ProductList;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.service.ICartService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IProductListService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/productlist")

public class ProductListController {

	private final Logger log = LoggerFactory.getLogger(ProductListController.class);

	@Autowired
	private IProductListService productListService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IItemListService itemListService;

	@Autowired
	ICartService cartService;

	@GetMapping("getallproductlist")
	public ResponseEntity<List<ProductList>> getAllProductList() {
		List<ProductList> productList = productListService.getAllProductList();
		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<ProductList> getProductListById(@PathVariable("id") int id) {
		ProductList productList = productListService.getProductListById(id);
		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);
	}
//	@GetMapping("getbyid")
////	public int getarrayList(){
//		public ArrayList<Integer> getarrayList(){
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		ArrayList<Integer> list2 = new ArrayList<Integer>();
//		ArrayList<Integer> list3 = new ArrayList<Integer>();
//		list.add(100);
//		list.add(70);
//		
//		list.add(60);
//		list.add(80);
//		list2.add(100);
//		list2.add(200);
//		list2.add(300);
//		list3.addAll(list2);
//		list3.addAll(list);
//		Collections.sort(list3);
//		list3.set(5, 420);
//		list3.remove(5);
//		
//int ass = list3.get(5);
//		return list3;
//	}

	@GetMapping("getbyid")
	public ResponseEntity<List<Cart>> getarrayList() {
		List<Cart> cartList = cartService.getAllCart();
		List<ProductList> productList = productListService.getAllProductList();
		List<Cart> list2 = new ArrayList<Cart>();
		list2.add(cartList.set(3, cartList.get(2)));
		return new ResponseEntity<List<Cart>>(list2, HttpStatus.OK);
	}

	@GetMapping("getbylinklist")
	public HashSet<String> getarrayList1() {
//	public String getarrayList1() {
//		LinkedList<String> ll = new LinkedList<>(); 
		HashSet<String> list = new HashSet<String>();
//		 LinkedHashSet<String> hs = new LinkedHashSet<String>();
		// TreeSet<String> hs = new TreeSet<String>();
		// HashMap<Integer, String> hs = new HashMap<Integer, String>();
		// LinkedList<Integer> hs = new LinkedList<Integer>();

		list.add("23");
		list.add("32");
		list.add("32");
		list.add("66");
		list.add("90");

//		hs.put(3, "hii");
//		hs.put(3, "hii1");
//		hs.put(4, "hii1 fgg yyyhh hh h jg rtr eee ee erttt yyy");
//		hs.put(5, " ");
//		 Iterator<String> itr = hs.iterator();
//	        while (itr.hasNext()) {
//	            System.out.println(itr.next());
//	        }

		return list;
	}

	@GetMapping("getbystack")
	public ResponseEntity<List<ProductList>> getarrayList4() {
		List<ProductList> productList = productListService.getAllProductList();
		Stack<ProductList> list = new Stack<ProductList>();

		list.add(productList.set(0, productList.get(0)));

		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductlistbyuserid/{userId}")
	public ResponseEntity<List<ProductList>> getProductListByUserId(@PathVariable("userId") String userId) {
		List<ProductList> productList = productListService.getProductListByUserId(userId);
		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductlistbyshopid/{shopId}/{cartId}")
	public ResponseEntity<List<ProductList>> getProductListByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("cartId") String cartId) {
		List<ProductList> productList = productListService.getProductListByShopId(shopId);
		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
	}

//	@GetMapping("getproductlistbycartid/{cartId}")
//	public ResponseEntity<List<ProductList>> getProductListByShopId(@PathVariable("cartId") String cartId) {
//		List<ProductList> productList = productListService.getProductListByShopId(shopId);
//		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);
//	}

	@GetMapping("getproductlistbyproductid/{productId}/{cartId}")
	public ResponseEntity<Boolean> getProductListByUserIdAndShopId(@PathVariable("productId") String productId,
			@PathVariable("cartId") int cartId) {
		boolean productList = productListService.getProductByProductId(productId, cartId);
		return new ResponseEntity<Boolean>(productList, HttpStatus.OK);

	}

//	@GetMapping("get/return/{productId}/{cartId}/{returnQuantity}")
//	public ResponseEntity<ProductList> getReturnInforamation(@PathVariable("productId") String productId,
//			@PathVariable("cartId") int cartId, @PathVariable("returnQuantity") float returnQuantity) {
//		Calculation calculation = new Calculation();
//		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
//		ItemList product = itemListService.getById(Integer.parseInt(productId));
//		Cart cart = cartService.getCart(cartId);
//		float price = product.getUnitSellingPrice() * returnQuantity,
//				gstAmount = product.getGstAmount() * returnQuantity, totalAmount = 0,
//				singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
//				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
//				bundleDiscount = product.getBundleCustomerDiscount() * returnQuantity, deliveryCharge = 0,
//				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
//				mrp = returnQuantity * product.getMrp();
//		int payableAmount = 0;
//		if (productList.getProductQuantity() == 0) {
//			if (bundleQuantity <= returnQuantity) {
//				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
//				discount = bundleDiscount1 * returnQuantity;
//				totalAmount = calculation.DecimalCalculation(oneBundlePrice * returnQuantity);
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math
//							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
//				}
//			} else {
//				discount = singleDiscount * returnQuantity;
//				totalAmount = price;
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math
//							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
//				}
//			}
//
//			cart.setTotalAmount(
//					calculation.DecimalCalculation(cart.getTotalAmount() - productList.getPrice() + totalAmount));
//			cart.setGstAmount(
//					calculation.DecimalCalculation(cart.getGstAmount() - productList.getGstAmount() + gstAmount));
//			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - productList.getDiscount() + discount));
//			cart.setBundleDiscount(calculation
//					.DecimalCalculation(cart.getBundleDiscount() - productList.getBundleDiscount() + bundleDiscount));
//			cart.setPayableAmount(payableAmount);
//			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice() + totalAmount));
//			cart.setDues(calculation.DecimalCalculation(cart.getDues() - productList.getTotalAmount() + payableAmount));
//			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
//			cart.setDeliveryCharge(deliveryCharge);
//			boolean updateCart = cartService.updateCart(cart);
//
//			if (updateCart) {
//				productList.setProductQuantity(productList.getProductQuantity() - (int) returnQuantity);
//				productList.setPrice(calculation.DecimalCalculation(totalAmount));
//				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
//				productList.setDiscount(calculation.DecimalCalculation(discount));
//				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
//				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
//				productList.setMrp(calculation.DecimalCalculation(mrp));
//
//				boolean updateProductList = productListService.updateProductList(productList);
//			}
//		}
//
//		productListService.updateProductList(productList);
//		itemListService.updateItemList(product);
//		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);
//	}

	@GetMapping("get/return/{productId}/{cartId}/{returnQuantity}")
	public ResponseEntity<ProductList> getReturnInforamation(@PathVariable("productId") String productId,
			@PathVariable("cartId") int cartId, @PathVariable("returnQuantity") float returnQuantity) {
		Calculation calculation = new Calculation();
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		ItemList product = itemListService.getById(Integer.parseInt(productId));
		Cart cart = cartService.getCart(cartId);
		float price = product.getUnitSellingPrice() * returnQuantity,
				gstAmount = product.getGstAmount() * returnQuantity, totalAmount = 0,
				singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
				bundleDiscount = product.getBundleCustomerDiscount() * returnQuantity, deliveryCharge = 0,
				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
				mrp = returnQuantity * product.getMrp();
		int payableAmount = 0;
		int productQuantity = productList.getProductQuantity();
		if (productQuantity > 0) {
			if (bundleQuantity <= returnQuantity) {
				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
				discount = bundleDiscount1 * returnQuantity;
				totalAmount = calculation.DecimalCalculation(oneBundlePrice * returnQuantity);
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499) {
					deliveryCharge = 20;
					payableAmount = (int) Math
							.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount + deliveryCharge);
				}
			} else {
				discount = singleDiscount * returnQuantity;
				totalAmount = price;
				payableAmount = (int) Math.ceil(cart.getTotalAmount() - productList.getPrice() + totalAmount);
				if (payableAmount < 499) {
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
			cart.setDues(calculation.DecimalCalculation(
					cart.getDues() - productList.getTotalAmount() + payableAmount - deliveryCharge));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp() + mrp));
			cart.setDeliveryCharge(deliveryCharge);
			boolean updateCart = cartService.updateCart(cart);

			if (updateCart) {
				productList.setProductQuantity(productList.getProductQuantity() - (int) returnQuantity);
				productList.setPrice(calculation.DecimalCalculation(totalAmount));
				productList.setGstAmount(calculation.DecimalCalculation(gstAmount));
				productList.setDiscount(calculation.DecimalCalculation(discount));
				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp()));
				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount));
				productList.setMrp(calculation.DecimalCalculation(mrp));

				boolean updateProductList = productListService.updateProductList(productList);
			}
		}

//		productListService.updateProductList(productList);
//		itemListService.updateItemList(product);
		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);
	}

//	@GetMapping("get/returntest/{productId}/{cartId}/{returnQuantity}")
//	public ResponseEntity<ProductList> getReturnInforamationtest(@PathVariable("productId") String productId,
//			@PathVariable("cartId") int cartId, @PathVariable("returnQuantity") float returnQuantity) {
//		Calculation calculation = new Calculation();
//		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
//		ItemList product = itemListService.getById(Integer.parseInt(productId));
//		Cart cart = cartService.getCart(cartId);
//		String userId = cart.getUserId();
//		User user = UserService.getUser(Integer.parseInt(userId));
//		float price = product.getUnitSellingPrice() * returnQuantity,
//				gstAmount = product.getGstAmount() * returnQuantity, totalAmount = 0,
////				singleDiscount = product.getMrp() - product.getUnitSellingPrice(),
//				discount = 0,
//				singleDiscount = calculation.DecimalCalculation(product.getCustomerSingleOffer() * returnQuantity),
////				discount5 = calculation.DecimalCalculation(product.getMrp() - product.getUnitSellingPrice()),
//				mainDiscount = calculation.DecimalCalculation(product.getCustomerSingleOffer() * returnQuantity),
////				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
//				bundleDiscount = product.getBundleCustomerDiscount() * returnQuantity, deliveryCharge = 0,
//				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
//				mrp = returnQuantity * product.getMrp(), 
//				cartDiscount = mainDiscount;
//
//		int payableAmount = 0;
//		int productQuantity = productList.getProductQuantity();
//		if (returnQuantity <= productQuantity) {
//			if (bundleQuantity <= returnQuantity) {
//				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
//				discount = productList.getCustomerBundleOffer() * returnQuantity;
//				totalAmount = calculation.DecimalCalculation(oneBundlePrice * returnQuantity);
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() - totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math.ceil(cart.getTotalAmount() - totalAmount);
//				}
//			} else {
//				discount = singleDiscount * returnQuantity;
//				totalAmount = price;
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() - totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math.ceil(cart.getTotalAmount() - totalAmount);
//				}
//			}
//
//			cart.setTotalAmount(calculation.DecimalCalculation(cart.getTotalAmount() - totalAmount));
//			cart.setGstAmount(calculation.DecimalCalculation(cart.getGstAmount() - gstAmount));
////			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - bundleDiscount1));
//			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - cartDiscount));
//			cart.setBundleDiscount(calculation.DecimalCalculation(cart.getBundleDiscount() - bundleDiscount));
////			cart.setPayableAmount(cart.getPayableAmount() - payableAmount);
//			cart.setPayableAmount(cart.getPayableAmount() - (int) totalAmount);
//			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - totalAmount));
//			cart.setDues(calculation.DecimalCalculation(cart.getDues() - totalAmount));
//			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - mrp));
//			cart.setDeliveryCharge(deliveryCharge);
//			boolean updateCart = cartService.updateCart(cart);
//			if (cart.getTotalAmount() == 0) {
//				cart.setDeliveryCharge(0);
//				cart.setPayableAmount(0);
//				cartService.updateCart(cart);
//			}
//			if (updateCart) {
//				productList.setProductQuantity(productList.getProductQuantity() - (int) returnQuantity);
//				productList.setPrice(calculation.DecimalCalculation(productList.getPrice() - totalAmount));
//				productList.setGstAmount(calculation.DecimalCalculation(productList.getGstAmount() - gstAmount));
//				productList.setDiscount(calculation.DecimalCalculation(productList.getDiscount() - discount));
//				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp() - mrp));
//				productList.setTotalAmount(calculation.DecimalCalculation(productList.getTotalAmount() - totalAmount));
//				productList.setMrp(calculation.DecimalCalculation(productList.getMrp() - mrp));
//				productList.setReturnQuantity(productList.getReturnQuantity() + returnQuantity);
//				productList.setReturnStatus(Boolean.TRUE);
//
//				boolean updateProductList = productListService.updateProductList(productList);
//				if (productList.getProductQuantity() == 0) {
//					productList.setActive(Boolean.FALSE);
//					productListService.updateProductList(productList);
//				}
//
//			}
//			user.setWalletBalance(user.getWalletBalance() + price);
//			UserService.updateUser(user);
//		}
//		product.setStock(product.getStock() + (int) returnQuantity);
//		itemListService.updateItemList(product);
//		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);
//	}

//	@GetMapping("get/return1/{productId}/{cartId}/{returnQuantity}")
//	public ResponseEntity<ProductList> getReturnInforamationTest(@PathVariable("productId") String productId,
//			@PathVariable("cartId") int cartId, @PathVariable("returnQuantity") float returnQuantity) {
//		Calculation calculation = new Calculation();
//		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
//		ItemList product = itemListService.getById(Integer.parseInt(productId));
//		Cart cart = cartService.getCart(cartId);
//		float price = product.getUnitSellingPrice() * returnQuantity,
//				gstAmount = product.getGstAmount() * returnQuantity, totalAmount = 0,
//				singleDiscount = product.getMrp() - product.getUnitSellingPrice(), discount = 0,
//				bundleDiscount1 = product.getMrp() - (product.getBundlePrice() / product.getBundleQuantity()),
//				bundleDiscount = product.getBundleCustomerDiscount() * returnQuantity, deliveryCharge = 0,
//				productBundlePrice = product.getBundlePrice(), bundleQuantity = product.getBundleQuantity(),
//				mrp = returnQuantity * product.getMrp();
//		int payableAmount = 0;
//		int productQuantity = productList.getProductQuantity();
//		if (productQuantity >= 0) {
//			if (bundleQuantity <= returnQuantity) {
//				float oneBundlePrice = calculation.DecimalCalculation(productBundlePrice / bundleQuantity);
//				discount = bundleDiscount1 * returnQuantity;
//				totalAmount = calculation.DecimalCalculation(oneBundlePrice * returnQuantity);
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() - totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math
//							.ceil(cart.getTotalAmount() - totalAmount + deliveryCharge);
//				}
//			} else {
//				discount = singleDiscount * returnQuantity;
//				totalAmount = price;
//				payableAmount = (int) Math.ceil(cart.getTotalAmount() -  totalAmount);
//				if (payableAmount < 499) {
//					deliveryCharge = 20;
//					payableAmount = (int) Math
//							.ceil(cart.getTotalAmount() - totalAmount + deliveryCharge);
//				}
//			}
//
//			cart.setTotalAmount(
//					calculation.DecimalCalculation(cart.getTotalAmount() - totalAmount));
//			cart.setGstAmount(
//					calculation.DecimalCalculation(cart.getGstAmount() -  gstAmount));
//			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() -  discount));
//			cart.setBundleDiscount(calculation
//					.DecimalCalculation(cart.getBundleDiscount() - bundleDiscount));
//			cart.setPayableAmount(payableAmount);
//			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() -  totalAmount));
//			cart.setDues(calculation.DecimalCalculation(cart.getDues() -  payableAmount- deliveryCharge));
//			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() -  mrp));
//			cart.setDeliveryCharge(deliveryCharge);
//			boolean updateCart = cartService.updateCart(cart);
//
//			if (updateCart) {
//				productList.setProductQuantity(productList.getProductQuantity() - (int) returnQuantity);
//				productList.setPrice(calculation.DecimalCalculation(totalAmount - product.getUnitSellingPrice()));
//				productList.setGstAmount(calculation.DecimalCalculation(gstAmount - product.getGstAmount()));
//				productList.setDiscount(calculation.DecimalCalculation(discount- singleDiscount));
//				productList.setOldPrice(calculation.DecimalCalculation(product.getMrp() - mrp)); 
//				productList.setTotalAmount(calculation.DecimalCalculation(totalAmount- product.getUnitSellingPrice()));
//				productList.setMrp(calculation.DecimalCalculation(mrp- mrp));
//
//
//				boolean updateProductList = productListService.updateProductList(productList);
//			}
//		}
//
////		productListService.updateProductList(productList);
////		itemListService.updateItemList(product);
//		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);
//	}

	@GetMapping("getproductlistbyproductidandcartid/{productId}/{cartId}")
	public ResponseEntity<ProductList> getProductByProductIdAndCartId(@PathVariable("productId") String productId,
			@PathVariable("cartId") int cartId) {
		ProductList productList = productListService.getProductByProductIdAndCartId(productId, cartId);
		return new ResponseEntity<ProductList>(productList, HttpStatus.OK);

	}

	@GetMapping("getbycartid/{cartId}")
	public ResponseEntity<List<ProductList>> getProductByProductListCartId(@PathVariable("cartId") int cartId) {
		List<ProductList> productList = productListService.getProductListCartId(cartId);
		return new ResponseEntity<List<ProductList>>(productList, HttpStatus.OK);

	}

//	@PutMapping("/update")
//	ResponseEntity<Map<String, String>> UpdateCart(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.ID)
//				&& null != productListService.getProductList(Integer.parseInt(json.get(ServiceConstants.ID)))) {
//			ProductList productList = productListService
//					.getProductList(Integer.parseInt(json.get(ServiceConstants.ID)));
//
//			if (null != json.get(ServiceConstants.ID)) {
//				int id = Integer.parseInt(json.get(ServiceConstants.ID).toString());
//				productList.setId(id);
//			}
//			if (null != json.get(ServiceConstants.SHOP_ID)) {
//				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
//				productList.setShopId(shopId);
//			}
//			if (null != json.get(ServiceConstants.USER_ID)) {
//				String userId = json.get(ServiceConstants.USER_ID).toString();
//				productList.setUserId(userId);
//			}
//			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
//				String productId = json.get(ServiceConstants.PRODUCT_ID).toString();
//				productList.setProductId(productId);
//			}
//			if (null != json.get(ServiceConstants.PRODUCT_NAME)) {
//				String productName = json.get(ServiceConstants.PRODUCT_NAME).toString();
//				productList.setProductName(productName);
//			}
//			if (null != json.get(ServiceConstants.CART_ID)) {
//				int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID).toString());
//				productList.setCartId(cartId);
//			}
//			if (null != json.get(ServiceConstants.CREATED_ON)) {
//				Date createdOn = new Date();
//				productList.setCreatedOn(createdOn);
//			}
//			if (null != json.get(ServiceConstants.PRODUCT_QUANTITY)) {
//				int productQuantity = Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY).toString());
//				productList.setProductQuantity(productQuantity);
//			}
//			if (null != json.get(ServiceConstants.PRICE)) {
//				int price = Integer.parseInt(json.get(ServiceConstants.PRICE).toString());
//				productList.setPrice(price);
//			}
//			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
//				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
//				productList.setActive(isActive);
//			}
//			if (null != json.get(ServiceConstants.IS_DELETED)) {
//				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
//				productList.setDeleted(isDeleted);
//			}
//			if (null != json.get(ServiceConstants.OFFERS_AVAILABLE)) {
//				boolean offersAvailable = Boolean.parseBoolean(json.get(ServiceConstants.OFFERS_AVAILABLE));
//				productList.setOffersAvailable(offersAvailable);
//
//			}
//			if (null != json.get(ServiceConstants.OLD_PRICE)) {
//				int oldPrice = Integer.parseInt(json.get(ServiceConstants.OLD_PRICE).toString());
//				productList.setOldPrice(oldPrice);
//			}
//			if (null != json.get(ServiceConstants.DISCOUNT)) {
//				int discount = Integer.parseInt(json.get(ServiceConstants.DISCOUNT).toString());
//				productList.setDiscount(discount);
//			}
//			if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
//				int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE).toString());
//				productList.setDeliveryCharge(deliveryCharge);
//			}
//			if (null != json.get(ServiceConstants.OFFER)) {
//				int offer = Integer.parseInt(json.get(ServiceConstants.OFFER).toString());
//				productList.setOffer(offer);
//			}
//			if (null != json.get(ServiceConstants.OFFER_TO)) {
//				LocalDate offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO).toString());
//				productList.setOfferTo(offerTo);
//			}
//			if (null != json.get(ServiceConstants.OFFER_FROM)) {
//				Date offerFrom = new Date(json.get(ServiceConstants.OFFER_FROM).toString());
//				productList.setOfferFrom(offerFrom);
//			}
//			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
//				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT).toString());
//				productList.setGstAmount(gstAmount);
//			}
//			if (null != json.get(ServiceConstants.MEASUREMENT)) {
//				String measurement = json.get(ServiceConstants.MEASUREMENT).toString();
//				productList.setMeasurement(measurement);
//			}
//
//			productListService.updateProductList(productList);
//			response.put("status", Boolean.TRUE.toString());
//			response.put("Discreptino", "productList updated");
//		} else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("Discreption", "Product doesn't exist with given  id");
//		}
//		return ResponseEntity.ok().body(response);
//	}
//
//	@SuppressWarnings({})
//	@PostMapping("/create")
//	ResponseEntity<Map<String, String>> createProductList(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		ProductList productList = new ProductList(Integer.parseInt(json.get(ServiceConstants.CART_ID)),
//				json.get(ServiceConstants.SHOP_ID));
//
//		productList.setUserId(json.get(ServiceConstants.USER_ID));
//		productList.setShopId(json.get(ServiceConstants.SHOP_ID));
//		productList.setProductQuantity(Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY)));
//		productList.setProductName(json.get(ServiceConstants.PRODUCT_NAME));
//		productList.setProductId(json.get(ServiceConstants.PRODUCT_ID));
//		productList.setPrice(Integer.parseInt(json.get(ServiceConstants.PRICE)));
//		LocalDate offerTo = null;
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		try {
//			System.out.println("Date" + json.get(ServiceConstants.OFFER_TO));
//			offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO), formatter);
//			productList.setOfferTo(offerTo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		productList.setOffersAvailable(Boolean.parseBoolean(json.get(ServiceConstants.OFFERS_AVAILABLE)));
//		if (Boolean.parseBoolean(json.get(ServiceConstants.OFFERS_AVAILABLE))) {
//			productList.setOldPrice(Integer.parseInt(json.get(ServiceConstants.OLD_PRICE)));
//			productList.setOffer(Integer.parseInt(json.get(ServiceConstants.OFFER)));
//			productList.setDiscount(Integer.parseInt(json.get(ServiceConstants.DISCOUNT)));
//		}
//
//		if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
//			productList.setDeliveryCharge(Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE)));
//		}
//
//		productList.setOfferFrom(new Date());
//		productList.setMeasurement(json.get(ServiceConstants.MEASUREMENT));
//		productList.setGstAmount(Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT)));
//		productList.setDeleted(Boolean.FALSE);
//		productList.setCreatedOn(new Date());
//		productList.setCartId(Integer.parseInt(json.get(ServiceConstants.CART_ID)));
//		productList.setActive(Boolean.TRUE);
//
//		response.put("shopId", json.get(ServiceConstants.SHOP_ID));
//		if (productListService.productListExists(json.get(ServiceConstants.PRODUCT_ID))) {
//			response.put("FALSE", Boolean.FALSE.toString());
//			response.put("discreption", "Product already exist with given Shop Id");
//
//		} else {
//			boolean result = productListService.createProductList(productList);
//			response.put("status", Strings.EMPTY + result);
//			response.put("description",
//					"ProductList created successfully with given Shop Id , go through your inbox to activate");
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@DeleteMapping("delete/{productListId}")
	ResponseEntity<Map<String, String>> deleteProductList(@PathVariable("productListId") int productListId) {
		Map<String, String> response = new HashMap<String, String>();
		ProductList productList = productListService.getProductListById(productListId);
		int cartId = productList.getCartId();
		Cart cart = cartService.getCart(cartId);
		Calculation calculation = new Calculation();
		if (null != productList && null != cart) {
			float gstAmount = productList.getGstAmount(), totalAmount = 0, deliveryCharge = 0,
					discount = productList.getDiscount();
			totalAmount = calculation.DecimalCalculation(productList.getPrice());

			int payableAmount = (int) Math.ceil(calculation.DecimalCalculation(cart.getTotalAmount() - totalAmount));

			if (payableAmount < 499 && payableAmount > 0) {
				deliveryCharge = 20;
				payableAmount = (int) Math
						.ceil(calculation.DecimalCalculation(cart.getTotalAmount() - totalAmount + deliveryCharge));
			}

			cart.setTotalAmount(calculation.DecimalCalculation(cart.getTotalAmount() - totalAmount));
			cart.setDues(calculation.DecimalCalculation(cart.getDues() - totalAmount));
			cart.setDiscount(calculation.DecimalCalculation(cart.getDiscount() - discount));
			cart.setPayableAmount(payableAmount);
			cart.setGstAmount(calculation.DecimalCalculation(cart.getGstAmount() - gstAmount));
			cart.setPrice(calculation.DecimalCalculation(cart.getPrice() - productList.getPrice()));
			cart.setMrp(calculation.DecimalCalculation(cart.getMrp() - productList.getMrp()));
			cart.setDeliveryCharge(deliveryCharge);

			boolean result = productListService.deleteProductList(productListId);
			if (result) {
				cartService.updateCart(cart);
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Product deleted with given product Id :-" + productListId);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product does not exist with given product Id");
		}
		return ResponseEntity.ok().body(response);
	}

}
