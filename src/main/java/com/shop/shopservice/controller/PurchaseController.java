package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi.sm3WithSM2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JodaTimeConverters.LocalDateTimeToDateConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.Discount;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Plan;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.entity.Purchase;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.service.IAdminBillBookService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IDiscountService;
import com.shop.shopservice.service.INotificationService;
import com.shop.shopservice.service.IPlanService;
import com.shop.shopservice.service.IProductService;
import com.shop.shopservice.service.IPurchaseService;
import com.shop.shopservice.service.ITransactionService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
	private final Logger log = LoggerFactory.getLogger(PurchaseController.class);
	@Autowired
	private IPurchaseService purchaseService;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IDiscountService discountService;

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private ILookUp lookup;

	@Autowired
	private IPlanService planService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IAdminBillBookService adminBillBookService;

	@GetMapping("getall")
	public ResponseEntity<List<Purchase>> getAllPurchasePlan() {
		List<Purchase> purchaseList = purchaseService.getAllPurchasePlan();
		return new ResponseEntity<List<Purchase>>(purchaseList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Purchase> getById(@PathVariable("id") int id) {
		Purchase purchase = purchaseService.getById(id);
		return new ResponseEntity<Purchase>(purchase, HttpStatus.OK);
	}

	@GetMapping("getbyplantype/{planType}")
	public ResponseEntity<List<Purchase>> getPurchasePlanByPlanType(@PathVariable("planType") int planType) {
		List<Purchase> purchaseList = purchaseService.getPurchasePlanByPlanType(planType);
		return new ResponseEntity<List<Purchase>>(purchaseList, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Purchase>> getPurchasePlanByShopId(@PathVariable("shopId") String shopId) {
		List<Purchase> purchaseList = purchaseService.getPurchasePlanByShopId(shopId);
		return new ResponseEntity<List<Purchase>>(purchaseList, HttpStatus.OK);
	}

	@GetMapping("getbyadminid/{adminId}")
	public ResponseEntity<Purchase> getActiveAdminByAdminId(@PathVariable("adminId") int adminId) {
		Purchase purchase = purchaseService.getActiveAdminByAdminId(adminId);
		return new ResponseEntity<Purchase>(purchase, HttpStatus.OK);
	}

//	@Scheduled(fixedRate = 10000)
	@Scheduled(cron = "0 0 0 * * *")
	public void setVailidity() {
		Admin admin = null;
		List<Discount> discountList = discountService.getByDiscountType("SLOT");
		for(int i=0 ;i< discountList.size(); i++) {
			discountList.get(i).setActive(Boolean.TRUE);
			discountService.updateDiscount(discountList.get(i));
		}
		
		Product product = null;
		List<Product> productList = productService.getAllOfferProduct();
		List<Admin> adminList = adminService.getAllActiveAdmin();
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getValidity() != 0 && adminList.get(i).isActive() != Boolean.FALSE) {
				admin = adminList.get(i);
				admin.setValidity(admin.getValidity() - 1);
				admin.setValidityUpdatedOn(new Date());
				if (admin.getValidity() == 0) {
					admin.setActive(Boolean.FALSE);
				}
				adminService.updateAdmin(admin);
			} else if (adminList.get(i).getValidity() == 0 && adminList.get(i).isActive() == Boolean.TRUE) {
				admin = adminList.get(i);
				admin.setActive(Boolean.FALSE);
				admin.setValidityUpdatedOn(new Date());
				adminService.updateAdmin(admin);
			}
		}

		for (int i = 0; i < productList.size(); i++) {
			product = productList.get(i);
			LocalDate offerDate = product.getOfferTo();
			LocalDate nowDate = LocalDate.now();
			long diffInDays = ChronoUnit.DAYS.between(nowDate, offerDate);
			if (diffInDays < 0) {
				float sellingPrice = product.getSellingPrice(), gstPercent = product.getGstPercent();
				int gst = (int) Math.ceil((sellingPrice * gstPercent) / (float) 100);
				product.setOfferActiveInd(Boolean.FALSE);
				product.setOfferPercent(0);
				product.setDiscount(0);
				product.setPrice(sellingPrice);
				product.setGstAmount(gst);
				product.setOldPrice(sellingPrice);
				productService.updateProduct(product);
			}
		}

	}

	@GetMapping("update/vailidity")
	public void setVailidity1() {
		Admin admin = null;
		List<Admin> adminList = adminService.getActiveAdminByShopId(admin.getShopId());
		for (int i = 0; i < adminList.size(); i++) {
			if (adminList.get(i).getValidity() != 0 && adminList.get(i).isActive() != Boolean.FALSE) {
				admin = adminList.get(i);
				admin.setValidity(admin.getValidity() - 1);
				admin.setValidityUpdatedOn(new Date());
				if (admin.getValidity() == 0) {
					admin.setActive(Boolean.FALSE);
				}
				adminService.updateAdmin(admin);
			} else if (adminList.get(i).getValidity() == 0 && adminList.get(i).isActive() == Boolean.TRUE) {
				admin = adminList.get(i);
				admin.setActive(Boolean.FALSE);
				admin.setValidityUpdatedOn(new Date());
				adminService.updateAdmin(admin);
			}
		}
	}

	@GetMapping("date")
	public ResponseEntity<?> calender() {
		Map<String, String> response = new HashMap<String, String>();
		Admin admin = adminService.getAdmin(1);
		SimpleDateFormat ad = new SimpleDateFormat();
		Date date = new Date();

		Product product = null;

		Calendar cal = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay(), date.getHours(),
				date.getMinutes());
		System.out.println(" Date of before adding=" + ad.format(cal.getTime()));
		cal.add(Calendar.DAY_OF_YEAR, 30);
		LocalDateTime expiry = admin.getValidityExpiryDate();
//		LocalDateTime date4 = LocalDateTime.of(expiry.getYear(), expiry.getMonth(), expiry.getDate(), expiry.getHours(), expiry.getMinutes(), expiry.getSeconds());
		LocalDateTime date1 = LocalDateTime.now();
		LocalDateTime date2 = date1.plusDays(30);

		long diffInDays = ChronoUnit.DAYS.between(date1, date1);

		int days = date1.compareTo(date1);

		System.out.println(" Date of after adding=" + ad.format(cal.getTime()));

		response.put("status", "" + ad.format(cal.getTime()));

		return ResponseEntity.ok().body(product);
	}

//	@SuppressWarnings({})
//	@PostMapping("/calculate1")
//	ResponseEntity<Map<String, String>> calculatePlan(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		Map<String, String> response = new HashMap<String, String>();
//
//		if (null != json.get(ServiceConstants.PLAN_ID)) {
//			log.info("Request to create user: {}", json.get(ServiceConstants.PLAN_ID));
//			int adminId = Integer.parseInt(json.get(ServiceConstants.PLAN_ID)),
//					planId = Integer.parseInt(json.get(ServiceConstants.PLAN_ID)), localTransactionId = 0,
//					discountType = 0, discountPercent = 0, discountAmount = 0,
//					paymentMode = Integer.parseInt(json.get(ServiceConstants.PAYMENT_MODE));
//			String transactionId = json.get(ServiceConstants.TRANSACTION_ID);
//			String shopId = json.get(ServiceConstants.SHOP_ID);
//			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
//			int onlinePayment = lookUp.getLookUpId();
//			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
//			int walletPayment = lookUp1.getLookUpId();
//			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
//			int regCompleted = lookUp2.getLookUpId();
//			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
//			int completed = lookUp3.getLookUpId();
//			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
//			int adminUser = lookUp4.getLookUpId();
//			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "PLAN_PURCHASE");
//			int planPurchase = lookUp5.getLookUpId();
//
//			Admin admin = null;
//			Transaction transaction = null;
//			Purchase purchase = null;
//			Plan plan = planService.getActivePlanById(planId);
//			float planAmount = plan.getAmount();
//			int planValidity = plan.getValidity(), planType = plan.getPlanType();
//			boolean result = adminService.checkDeactiveAdminByAdminId(adminId, regCompleted);
//
//			int price = (int) plan.getAmount();
//			int gst = Math.round((price * 18) / 100);
//			int totalAmount = price + gst;
//			if (plan.isDiscount() && null != json.get(ServiceConstants.DISCOUNT_TYPE)
//					&& 0 != Integer.parseInt(json.get(ServiceConstants.DISCOUNT_TYPE))) {
//				LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", json.get(ServiceConstants.DISCOUNT_TYPE));
//				discountType = lookUp6.getLookUpId();
//				discountPercent = Integer.parseInt(lookUp6.getLookUpLabel());
//				discountAmount = Math.round((totalAmount * discountPercent) / 100);
//				totalAmount = totalAmount - discountAmount;
//			}
//
//			if (result) {
//				transaction = new Transaction();
//				admin = adminService.getAdmin(adminId);
//				float wallet = admin.getWallet();
//				boolean paymentDone = false;
//				int adminValidity = admin.getValidity();
//				LocalDateTime validityExpiryDate = admin.getValidityExpiryDate();
//				LocalDateTime dateTime = LocalDateTime.now();
//
//				if (paymentMode == onlinePayment) {
//					transaction.setPaymentMode(onlinePayment);
//					paymentDone = Boolean.TRUE;
//				} else if (paymentMode == walletPayment) {
//					transaction.setPaymentMode(walletPayment);
//
//					if (null != plan) {
//
//						if (wallet > totalAmount + 500) {
//							admin.setWallet(admin.getWallet() - totalAmount);
//							paymentDone = adminService.updateAdmin(admin);
//						} else {
//							paymentDone = Boolean.FALSE;
//							response.put("status", Boolean.FALSE.toString());
//							response.put("description", "Insufficient wallet balance");
//						}
//					}
//				}
//				transaction.setActive(Boolean.TRUE);
//				transaction.setAdminId(adminId);
//				transaction.setShopId(shopId);
//				transaction.setCreatedOn(new Date());
//				transaction.setDeleted(Boolean.FALSE);
//				transaction.setTransactionStatus(completed);
//				transaction.setUserType(adminUser);
//				transaction.setTransactionId(transactionId);
//				transaction.setAmount(totalAmount);
//				transaction.setTransactionType(planPurchase);
//
//				if (paymentDone) {
//					List<Transaction> transactionList = transactionService.getByAdminId(adminId, planPurchase,
//							Boolean.TRUE);
//					if (transactionList.size() > 0) {
//						for (int i = 0; i < transactionList.size(); i++) {
//							transactionList.get(i).setActive(false);
//							transactionService.updateTransaction(transactionList.get(i));
//						}
//					}
//
//					boolean transactionCreate = transactionService.createTransactionForPurchase(transaction);
//					if (transactionCreate) {
//						transactionList = transactionService.getByAdminId(adminId, planPurchase, Boolean.TRUE);
//						if (transactionList.size() > 1) {
//							localTransactionId = transactionList.get(transactionList.size() - 1).getId();
//						} else {
//							localTransactionId = transactionList.get(0).getId();
//						}
//						purchase = new Purchase(adminId, planId, localTransactionId);
//						if (adminValidity > 0) {
//							admin.setValidity(adminValidity + plan.getValidity());
//							admin.setValidityExpiryDate(validityExpiryDate.plusDays(plan.getValidity()));
//							admin.setValidityUpdatedOn(new Date());
//							admin.setPlanPurchasedOn(new Date());
//						} else {
//							admin.setValidity(plan.getValidity());
//							admin.setValidityExpiryDate(dateTime.plusDays(plan.getValidity()));
//							admin.setValidityUpdatedOn(new Date());
//							admin.setPlanPurchasedOn(new Date());
//						}
//
//						if (paymentMode == onlinePayment) {
//							purchase.setPaymentMode(onlinePayment);
//						} else if (paymentMode == walletPayment) {
//							purchase.setPaymentMode(walletPayment);
//						}
//
//						if (plan.isDiscount() && null != json.get(ServiceConstants.DISCOUNT_TYPE)
//								&& 0 != Integer.parseInt(json.get(ServiceConstants.DISCOUNT_TYPE))) {
//							purchase.setDiscount(true);
//							purchase.setDiscountPercent(discountPercent);
//							purchase.setDiscountAmount(discountAmount);
//							purchase.setDiscountType(discountType);
//						}
//
//						purchase.setAdminId(adminId);
//						purchase.setCreatedOn(new Date());
//						purchase.setExpiryDate(new Date());
//						purchase.setPlanAmount((int) planAmount);
//						purchase.setPlanId(planId);
//						purchase.setPlanType(planType);
//						purchase.setPlanValidity(planValidity);
//						purchase.setTransactionId(localTransactionId);
//						purchase.setActive(Boolean.TRUE);
//						purchase.setDeleted(Boolean.FALSE);
//						purchase.setTotalAmount(totalAmount);
//						purchase.setShopId(shopId);
//
//						response.put("adminId", String.valueOf(adminId));
//						if (purchaseService.purchaseExists(purchase.getAdminId())) {
//							Purchase purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
//							if (null != purchase1) {
//								purchase1.setActive(Boolean.FALSE);
//								purchaseService.updatePurchase(purchase1);
//								boolean result1 = purchaseService.createPurchase(purchase);
//								if (result1) {
//									purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
//									transactionList.get(0).setPurchaseId(purchase1.getId());
//									transactionService.updateTransaction(transactionList.get(0));
//									adminService.updateAdmin(admin);
//									response.put("status", Boolean.TRUE.toString());
//									response.put("statue", " Purchase plan created");
//								} else {
//									response.put("status", Boolean.FALSE.toString());
//									response.put("statue", " Purchase plan creation failed");
//								}
//							}
//						} else {
//							boolean result1 = purchaseService.createPurchase(purchase);
//							if (result1) {
//								Purchase purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
//								transactionList.get(0).setPurchaseId(purchase1.getId());
//								transactionService.updateTransaction(transactionList.get(0));
//								adminService.updateAdmin(admin);
//
//								response.put("status", Boolean.TRUE.toString());
//								response.put("statue", " Purchase plan created");
//							} else {
//								response.put("status", Boolean.FALSE.toString());
//								response.put("statue", " Purchase plan creation failed");
//							}
//						}
//
//					} else {
//						response.put("status", Boolean.FALSE.toString());
//						response.put("statue", "Transaction failed");
//					}
//				} else {
//					response.put("status", Boolean.FALSE.toString());
//					response.put("description", "Insufficient wallet balance");
//				}
//
//			} else {
//				response.put("status", Boolean.FALSE.toString());
//				response.put("description", "UserId in not registered.");
//			}
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@SuppressWarnings({})
	@PostMapping("/calculate")
	ResponseEntity<Purchase> planCalculate(@Valid @RequestBody Map<String, String> json, HttpServletRequest request)
			throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.PLAN_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.ADMIN_ID) && null != json.get(ServiceConstants.DISCOUNT_TYPE)) {
			int planId = Integer.parseInt(json.get(ServiceConstants.PLAN_ID)),
					adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID));

			String shopId = json.get(ServiceConstants.SHOP_ID), discountType = json.get(ServiceConstants.DISCOUNT_TYPE);
			Plan plan = planService.getActivePlanById(planId);
			Purchase purchase = null;
			int price = plan.getAmount();
			int planType = plan.getPlanType();
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", discountType);
			int discountPercent = Integer.parseInt(lookUp1.getLookUpLabel());
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			int onlinePayment = lookUp3.getLookUpId();

			float offerAmount = (price * discountPercent) / (float) 100;
			float amount = price - offerAmount;
			float gstAmount = (amount * 18) / (float) 100;
			float totalAmount = amount + gstAmount;
			int gstPercent = 18;
			int paybaleAmount = (int) (Math.ceil(totalAmount));

			purchase = new Purchase();
			purchase.setActive(Boolean.TRUE);
			purchase.setAdminId(adminId);
			purchase.setDeleted(Boolean.FALSE);
			purchase.setDiscountAmount(offerAmount);
			purchase.setDiscountPercent(discountPercent);
			purchase.setCreatedOn(new Date());
			purchase.setDiscountType(discountType);
			purchase.setGstAmount(gstAmount);
			purchase.setGstPercent(gstPercent);
			purchase.setPayableAmount(paybaleAmount);
			purchase.setPaymentMode(onlinePayment);
			purchase.setPlanAmount(price);
			purchase.setPlanId(planId);
			purchase.setAmount(amount);
			purchase.setPlanType(planType);
			purchase.setPlanValidity(plan.getValidity());
			purchase.setShopId(shopId);
			purchase.setTotalAmount(totalAmount);

			return ResponseEntity.ok().body(purchase);

		}

		return ResponseEntity.noContent().build();
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createSalary(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ADMIN_ID) && null != json.get(ServiceConstants.PLAN_ID)
				&& null != json.get(ServiceConstants.TRANSACTION_ID) && null != json.get(ServiceConstants.PAYMENT_MODE)
				&& null != json.get(ServiceConstants.SHOP_ID)) {

			log.info("Request to create user: {}", json.get(ServiceConstants.ADMIN_ID));
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ONLINE_PAYMENT");
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
			LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "PLAN_PURCHASE");
			LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "RECHARGE_NOTIFICATION");

			int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID)), planPurchase = lookUp5.getLookUpId(),
					onlinePayment = lookUp.getLookUpId(), walletPayment = lookUp1.getLookUpId(),
					regCompleted = lookUp2.getLookUpId(), completed = lookUp3.getLookUpId(),
					adminUser = lookUp4.getLookUpId(), rechargeNotification = lookUp7.getLookUpId(),
					planId = Integer.parseInt(json.get(ServiceConstants.PLAN_ID)), localTransactionId = 0,
					discountPercent = 0, payableAmount = 0,
					paymentMode = Integer.parseInt(json.get(ServiceConstants.PAYMENT_MODE));

			float discountAmount = 0, planAmount = 0, totalAmount = 0, amount = 0, gstAmount = 0;

			String transactionId = json.get(ServiceConstants.TRANSACTION_ID), discountType = "",
					shopId = json.get(ServiceConstants.SHOP_ID);
			Calculation calculation = new Calculation();
			Admin admin = null;
			Transaction transaction = null;
			Purchase purchase = null;
			Plan plan = planService.getActivePlanById(planId);

			int planPrice = plan.getAmount(), planValidity = plan.getValidity(), planType = plan.getPlanType();
			boolean result = adminService.checkDeactiveAdminByAdminId(adminId, regCompleted);

			planAmount = (float) plan.getAmount();
			gstAmount = (planAmount * 18) / (float) 100;
			totalAmount = planAmount + gstAmount;
			if (plan.isDiscount() && null != json.get(ServiceConstants.DISCOUNT_TYPE)
					&& 0 != Integer.parseInt(json.get(ServiceConstants.DISCOUNT_TYPE))) {
				LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", json.get(ServiceConstants.DISCOUNT_TYPE));
				discountType = lookUp6.getLookUpLabel();

				discountPercent = Integer.parseInt(lookUp6.getLookUpLabel());
				discountAmount = (totalAmount * discountPercent) / (float) 100;
				amount = planAmount - discountAmount;
				gstAmount = (amount * 18) / (float) 100;
				totalAmount = amount + gstAmount;
			}
			payableAmount = (int) Math.ceil(totalAmount);

			if (result) {
				transaction = new Transaction();
				admin = adminService.getAdmin(adminId);
				float wallet = admin.getWallet(), availableAmount = admin.getAvailableAmount();
				boolean paymentDone = false;
				int adminValidity = admin.getValidity();
				LocalDateTime validityExpiryDate = admin.getValidityExpiryDate();
				LocalDateTime dateTime = LocalDateTime.now();

				if (paymentMode == onlinePayment) {
					transaction.setPaymentMode(onlinePayment);
					paymentDone = Boolean.TRUE;
				} else if (paymentMode == walletPayment) {
					transaction.setPaymentMode(walletPayment);

					List<AdminBillBook> adminBillBookList = adminBillBookService
							.getBillBookByAdminId(String.valueOf(adminId));
					AdminBillBook adminBillBook = adminBillBookList.get(0);

					adminBillBook.setDebit(calculation.DecimalCalculation(adminBillBook.getDebit() + totalAmount));
					adminBillBook.setLastDebitedOn(new Date());
					adminBillBook.setUpdatedOn(new Date());

					if (null != plan) {

						if (availableAmount > totalAmount + 500) {
							admin.setWallet(calculation.DecimalCalculation(admin.getWallet() - totalAmount));
							admin.setAvailableAmount(calculation.DecimalCalculation(admin.getAvailableAmount() - totalAmount));
							paymentDone = adminService.updateAdmin(admin);
							adminBillBookService.updateAdminBillBook(adminBillBook);
						} else {
							paymentDone = Boolean.FALSE;
							response.put("status", Boolean.FALSE.toString());
							response.put("description", "Insufficient wallet balance");
						}
					}
				}
				transaction.setActive(Boolean.TRUE);
				transaction.setAdminId(adminId);
				transaction.setShopId(shopId);
				transaction.setCreatedOn(new Date());
				transaction.setDeleted(Boolean.FALSE);
				transaction.setTransactionStatus(completed);
				transaction.setUserType(adminUser);
				transaction.setTransactionId(transactionId);
				transaction.setAmount(payableAmount);
				transaction.setTotalAmount(payableAmount);
				transaction.setTransactionType(planPurchase);

				if (paymentDone) {
					List<Transaction> transactionList = transactionService.getByAdminId(adminId, planPurchase,
							Boolean.TRUE);
					if (transactionList.size() > 0) {
						for (int i = 0; i < transactionList.size(); i++) {
							transactionList.get(i).setActive(false);
							transactionService.updateTransaction(transactionList.get(i));
						}
					}

					boolean transactionCreate = transactionService.createTransactionForPurchase(transaction);
					if (transactionCreate) {
						transactionList = transactionService.getByAdminId(adminId, planPurchase, Boolean.TRUE);
						if (transactionList.size() > 1) {
							localTransactionId = transactionList.get(transactionList.size() - 1).getId();
						} else {
							localTransactionId = transactionList.get(0).getId();
						}
						purchase = new Purchase(adminId, planId, localTransactionId);
						if (adminValidity > 0) {
							admin.setValidity(adminValidity + plan.getValidity());
							admin.setValidityExpiryDate(validityExpiryDate.plusDays(plan.getValidity()));
							admin.setValidityUpdatedOn(new Date());
							admin.setPlanPurchasedOn(new Date());
						} else {
							admin.setValidity(plan.getValidity());
							admin.setValidityExpiryDate(dateTime.plusDays(plan.getValidity()));
							admin.setValidityUpdatedOn(new Date());
							admin.setPlanPurchasedOn(new Date());
						}

						if (paymentMode == onlinePayment) {
							purchase.setPaymentMode(onlinePayment);
						} else if (paymentMode == walletPayment) {
							purchase.setPaymentMode(walletPayment);
						}

						if (plan.isDiscount() && null != json.get(ServiceConstants.DISCOUNT_TYPE)
								&& 0 != Integer.parseInt(json.get(ServiceConstants.DISCOUNT_TYPE))) {
							purchase.setDiscount(true);
							purchase.setDiscountPercent(discountPercent);
							purchase.setDiscountType(discountType);
						} else {
							purchase.setDiscount(false);
							purchase.setDiscountType(discountType);
						}

						purchase.setAdminId(adminId);
						purchase.setCreatedOn(new Date());
						purchase.setExpiryDate(new Date());
						purchase.setPlanAmount(planAmount);
						purchase.setPlanId(planId);
						purchase.setPlanType(planType);
						purchase.setPlanValidity(planValidity);
						purchase.setTransactionId(localTransactionId);
						purchase.setActive(Boolean.TRUE);
						purchase.setDeleted(Boolean.FALSE);
						purchase.setTotalAmount(totalAmount);
						purchase.setShopId(shopId);
						purchase.setAmount(amount);
						purchase.setDiscountAmount(discountAmount);
						purchase.setGstAmount(gstAmount);
						purchase.setPayableAmount(payableAmount);
						purchase.setGstPercent(18);

						response.put("adminId", String.valueOf(adminId));
						if (purchaseService.purchaseExists(purchase.getAdminId())) {
							Purchase purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
							if (null != purchase1) {
								purchase1.setActive(Boolean.FALSE);
								purchaseService.updatePurchase(purchase1);
								boolean result1 = purchaseService.createPurchase(purchase);
								if (result1) {
									purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
									transactionList.get(0).setPurchaseId(purchase1.getId());
									transactionService.updateTransaction(transactionList.get(0));
									adminService.updateAdmin(admin);
									notificationService.createNotification(purchase.getId(), rechargeNotification);
									response.put("status", Boolean.TRUE.toString());
									response.put("statue", " Purchase plan created");
								} else {
									response.put("status", Boolean.FALSE.toString());
									response.put("statue", " Purchase plan creation failed");
								}
							}
						} else {
							boolean result1 = purchaseService.createPurchase(purchase);
							if (result1) {	
								Purchase purchase1 = purchaseService.getActiveAdminByAdminId(adminId);
								int id = purchase1.getId();
								transactionList.get(0).setPurchaseId(purchase1.getId());
								notificationService.createNotification(id, rechargeNotification);
								transactionService.updateTransaction(transactionList.get(0));
								adminService.updateAdmin(admin);

								response.put("status", Boolean.TRUE.toString());
								response.put("statue", " Purchase plan created");
							} else {
								response.put("status", Boolean.FALSE.toString());
								response.put("statue", " Purchase plan creation failed");
							}
						}

					} else {
						response.put("status", Boolean.FALSE.toString());
						response.put("statue", "Transaction failed");
					}
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Insufficient wallet balance");
				}

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "UserId in not registered.");
			}
		}
		return ResponseEntity.ok().body(response);
	}

//	@PutMapping("/update")
//	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.ID) && null != purchaseService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))){
//			Purchase purchase = purchaseService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
//			
//			if(null != json.get(ServiceConstants.ADMIN_ID)) {
//				int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID).toString());
//				purchase.setAdminId(adminId);
//			}
//			
//			if(null != json.get(ServiceConstants.CREATED_ON)) {
//				Date createdOn = new Date();
//				purchase.setCreatedOn(createdOn);
//			}
//			if(null != json.get(ServiceConstants.EXPIRY_DATE)) {
//				Date expiryDate = new Date();
//				purchase.setExpiryDate(expiryDate);
//			}
//			
//			if(null != json.get(ServiceConstants.PLAN_AMOUNT)) {
//				float planAmount = Float.parseFloat(json.get(ServiceConstants.PLAN_AMOUNT));
//				purchase.setPlanAmount(planAmount);
//				
//			}
//			
//			if(null != json.get(ServiceConstants.PLAN_ID)) {
//				int planId = Integer.parseInt(json.get(ServiceConstants.PLAN_ID))
//					purchase.setPlanId(planId);
//				}
//			if(null != json.get(ServiceConstants.PLAN_TYPE)) {
//				int planType = Integer.parseInt(json.get(ServiceConstants.PLAN_TYPE));
//				purchase.setPlanType(planType);
//			}
//			
//			if(null != json.get)
//			
//			
//		}
//		return ResponseEntity.ok().body(response);
//}
}
