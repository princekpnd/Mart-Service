package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICartService;
import com.shop.shopservice.service.ITransactionService;
import com.shop.shopservice.service.IUserService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService UserService;

	@Autowired
	ICartService cartService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;

	@GetMapping("getalltransaction")
	public ResponseEntity<List<Transaction>> getAlltransaction() {
		List<Transaction> transactionList = transactionService.getAllTransaction();
		return new ResponseEntity<List<Transaction>>(transactionList, HttpStatus.OK);
	}
//	
//	@GetMapping("getcash/{transactionId}")
//	public ResponseEntity<Transaction> getCashT
	

	@GetMapping("get/transactionby/userId/shopId/{userId}/{shopId}")
	public ResponseEntity<List<Transaction>> getTransactionByUserIdAndShopId(@PathVariable("userId") int userId,
			@PathVariable("shopId") String shopId) {
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
		int transactionStatus = lookUp.getLookUpId();
		List<Transaction> transactionList = transactionService.getTransactionByUserIdAndShopId(userId, shopId,
				transactionStatus);
		return new ResponseEntity<List<Transaction>>(transactionList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Transaction> getById(@PathVariable("id") Integer id) {
		Transaction transaction = transactionService.getById(id.intValue());
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	@GetMapping("gettransactionbyuserid/{userId}")
	public ResponseEntity<List<Transaction>> getTransactionByUserId(@PathVariable("userId") Integer userId) {
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
		int transactionStatus = lookUp.getLookUpId();
		List<Transaction> transaction = transactionService.getTransactionByUserId(userId.intValue(), transactionStatus);
		return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);
	}

	@GetMapping("gettransactionbyshopid/{shopId}")
	public ResponseEntity<List<Transaction>> getTransactionByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
		int transactionStatus = lookUp.getLookUpId();
		List<Transaction> transactionList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			transactionList = transactionService.getTransactionByShopId(shopId, transactionStatus);
		}
		return new ResponseEntity<List<Transaction>>(transactionList, HttpStatus.OK);
	}

	@GetMapping("gettransactionforuserbyshopid/{shopId}")
	public ResponseEntity<List<Transaction>> getTransactionforUser(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Transaction> transactionList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			transactionList = transactionService.getTransactionForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Transaction>>(transactionList, HttpStatus.OK);
	}

//	@GetMapping("get/transaction/allamount/byshopid/{shopId}")
//	public ResponseEntity<Map<String, String>> getTransactionByShopId1(@PathVariable("shopId") String shopId) {
//		Map<String, String> response = new HashMap<String, String>();
//		Admin admin = adminService.getAdminByShopId(shopId);
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
//		int transactionStatus = lookUp.getLookUpId();
//		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "CASH");
//		int cash = lookUp1.getLookUpId();
//		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
//		int online = lookUp2.getLookUpId();
//
//		List<Transaction> transactionList = null;
//		List<Cart> cartList = null;
//		int totalAmount = 0;
//		transactionList = transactionService.getTransactionByShopId(shopId, transactionStatus);
//		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE){
//			cartList = cartService.getCartByShopIdId(shopId);
//			if (cartList.size() > 0) {
//				for (int i = 0; i < cartList.size(); i++) {
//					int transactionType = cartList.get(i).getTransactionType();
//					if (transactionType == cash && transactionType == online) {
//						int payableAmount = cartList.get(i).getPayableAmount();
//						totalAmount = totalAmount + payableAmount;
//					}
//					transactionList.get(0).setTotalAmount(totalAmount);
//
//				}
//			}
//
////			 transactionService.updateTransaction(transactionList.get(0));
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@GetMapping("get/transaction/{cartId}")
	public ResponseEntity<Transaction> getTransactionforUser(@PathVariable("cartId") int cartId) {
		Transaction transaction = transactionService.getTransactionByCartId(cartId);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	@GetMapping("gettransactionbytransactionid/{transactionId}")
	public ResponseEntity<List<Transaction>> getTransactionByTransactionId(
			@PathVariable("transactionId") String transactionId) {
		List<Transaction> transaction = transactionService.getTransactionByTransactionId(transactionId);
		return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);
	}

	@GetMapping("get/byadminid/transactiontype/{adminId}/{transactionType}/{isActive}")
	public ResponseEntity<List<Transaction>> getTransactionByAdminId(@PathVariable("adminId") int adminId,
			@PathVariable("transactionType") int transactionType, @PathVariable("isActive") boolean isActive) {
//		boolean active = Boolean.parseBoolean(isActive);
		List<Transaction> transaction = transactionService.getByAdminId(adminId, transactionType, isActive);
		return new ResponseEntity<List<Transaction>>(transaction, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.USER_ID) && null != json.get(ServiceConstants.CART_ID)
				&& null != json.get(ServiceConstants.PAYMENT_MODE) && null != json.get(ServiceConstants.AMOUNT)
				&& null != json.get(ServiceConstants.USER_TYPE) && null != json.get(ServiceConstants.CART_ID)
				&& null != json.get(ServiceConstants.TRANSACTION_ID)) {
			int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID)),
					paymentMode = Integer.parseInt(json.get(ServiceConstants.PAYMENT_MODE)),
					cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID)),
					userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE)),
					amount = Integer.parseInt(json.get(ServiceConstants.AMOUNT));
			String shopId = json.get(ServiceConstants.SHOP_ID),
					transactionId = json.get(ServiceConstants.TRANSACTION_ID);
			Transaction transaction = new Transaction(json.get(ServiceConstants.SHOP_ID),
					Integer.parseInt(json.get(ServiceConstants.USER_ID)));

			User user = UserService.getUser(userId);
			Cart cart = cartService.getCart(cartId);
			Admin admin = adminService.getAdminByShopId(shopId);
			if (null != user && null != cart && null != admin){
				transaction.setUserId(userId);
				transaction.setShopId(shopId);
				transaction.setPaymentMode(paymentMode);
				transaction.setAmount(amount);
				transaction.setCreatedOn(new Date());

				transaction.setTransactionId(transactionId);
				transaction.setUserType(userType);
				transaction.setActive(Boolean.TRUE);
				transaction.setDeleted(Boolean.FALSE);
				transaction.setTotalAmount(cart.getTotalAmount());
				transaction.setDues(user.getDues());
				transaction.setCartId(cartId);
				transaction.setPaid(amount);
				transaction.setAdminId(Integer.parseInt(json.get(ServiceConstants.ADMIN_ID)));
				transaction.setTransactionStatus(Integer.parseInt(json.get(ServiceConstants.TRANSACTION_STATUS)));
				transaction.setDiscreption(json.get(json.get(ServiceConstants.DISCREPTION)));
				transaction.setOrderRcptidId(json.get(ServiceConstants.ORDER_RCPTID_ID));

				response.put("shopId", json.get(ServiceConstants.SHOP_ID));
				if (transactionService.transactionExists(transaction.getCartId())) {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Transaction already exist with given Shop Id");
				} else {
					boolean result = transactionService.createTransaction(transaction);
					user.setDues(user.getDues() - amount);
					user.setLastPaid(amount);
					admin.setWallet(admin.getWallet() + amount);
					UserService.updateUser(user);
					adminService.updateAdmin(admin);
					cart.setDues(cart.getDues() - amount);
					cart.setPaid(amount);
					cartService.updateCart(cart);
					response.put("status", Strings.EMPTY + result);
					response.put("description",
							"Product created successfully with given Shop Id , go through your inbox to activate");
				}
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.TRANSACTION_ID)
				&& null != transactionService.getTransaction(json.get(ServiceConstants.TRANSACTION_ID))) {
			Transaction transaction = transactionService.getTransaction(json.get(ServiceConstants.TRANSACTION_ID));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				transaction.setId(id);
			}

			if (null != json.get(ServiceConstants.TRANSACTION_ID)) {
				String transactionId = json.get(ServiceConstants.TRANSACTION_ID);
				System.out.println(transactionId);
				transaction.setTransactionId(transactionId);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				transaction.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.USER_ID)) {
				int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID));
				System.out.println(userId);
				transaction.setUserId(userId);
			}

			if (null != json.get(ServiceConstants.PAYMENT_MODE)) {
				int paymentMode = Integer.parseInt(json.get(ServiceConstants.PAYMENT_MODE));
				System.out.println(paymentMode);
				transaction.setPaymentMode(paymentMode);
			}

			if (null != json.get(ServiceConstants.AMOUNT)) {
				int amount = Integer.parseInt(json.get(ServiceConstants.AMOUNT));
				System.out.println(amount);
				transaction.setAmount(amount);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				System.out.println(isActive);
				transaction.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				System.out.println(isDeleted);
				transaction.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				System.out.println(createdOn);
				transaction.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				System.out.println(userType);
				transaction.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				System.out.println(totalAmount);
				transaction.setTotalAmount(totalAmount);

			}
			if (null != json.get(ServiceConstants.PAID)) {
				int paid = Integer.parseInt(json.get(ServiceConstants.PAID));
				System.out.println(paid);
				transaction.setPaid(paid);
			}

			if (null != json.get(ServiceConstants.DUES)) {
				int dues = Integer.parseInt(json.get(ServiceConstants.DUES));
				System.out.println(dues);
				transaction.setDues(dues);
			}

			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID));
				transaction.setAdminId(adminId);
			}

			if (null != json.get(ServiceConstants.TRANSACTION_STATUS)) {
				int transactionStatus = Integer.parseInt(json.get(ServiceConstants.TRANSACTION_STATUS));
				transaction.setTransactionStatus(transactionStatus);
			}

			if (null != json.get(ServiceConstants.DISCREPTION)) {
				String discreption = json.get(ServiceConstants.DISCREPTION);
				System.out.println(discreption);
				transaction.setDiscreption(discreption);

			}

			if (null != json.get(ServiceConstants.CART_ID)) {
				int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
				System.out.println(cartId);
				transaction.setCartId(cartId);
			}

			if (null != json.get(ServiceConstants.ORDER_RCPTID_ID)) {
				String orderRcptidId = json.get(ServiceConstants.ORDER_RCPTID_ID);
				System.out.println(orderRcptidId);
				transaction.setOrderRcptidId(orderRcptidId);

			}
			transactionService.updateTransaction(transaction);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Transaction updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Transaction doesn't exist with given  userid");
		}

		return ResponseEntity.ok().body(response);

	}
}
