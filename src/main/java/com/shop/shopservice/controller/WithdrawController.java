package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.shop.shopservice.entity.Account;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.entity.Withdraw;
import com.shop.shopservice.service.IAccountService;
import com.shop.shopservice.service.IAdminBillBookService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.INotificationService;
import com.shop.shopservice.service.ITransactionService;
import com.shop.shopservice.service.IWithdrawService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IWithdrawService withdrawService;
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private IAdminBillBookService adminBillBookService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private ILookUp lookup;

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private ILookUpType lookUpType;

	@GetMapping("getall")
	public ResponseEntity<List<Withdraw>> getAllWithdraw() {
		List<Withdraw> withdrawList = withdrawService.getAllWithdraw();
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Withdraw> getById(@PathVariable("id") int id) {
		Withdraw withdraw = withdrawService.getById(id);
		return new ResponseEntity<Withdraw>(withdraw, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Withdraw>> getWithdrawByShopId(@PathVariable("shopId") String shopId) {
		List<Withdraw> withdrawList = withdrawService.getWithdrawByShopId(shopId);
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);
	}

	@GetMapping("getbyuserid/{userId}")
	public ResponseEntity<List<Withdraw>> getWithdrawByShopId(@PathVariable("userId") int userId) {
		List<Withdraw> withdrawList = withdrawService.getWithdrawByUserId(userId);
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);
	}

	@GetMapping("getbyadminid/{adminId}/{isActive}")
	public ResponseEntity<List<Withdraw>> checkActiveWithdraw(@PathVariable("adminId") int adminId,
			@PathVariable("isActive") boolean isActive) {
		List<Withdraw> withdrawList = withdrawService.checkActiveWithdraw(adminId, isActive);
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);
	}

	@GetMapping("getbyuserandshop/{shopId}/{userId}")
	public ResponseEntity<List<Withdraw>> getWithdrawByShopIdAndUserId(@PathVariable("shopId") String shopId,
			@PathVariable("userId") int userId) {
		List<Withdraw> withdrawList = withdrawService.getWithdrawByShopIdAndUserId(shopId, userId);
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);

	}

	@GetMapping("getactivewithdraw/{shopId}")
	public ResponseEntity<List<Withdraw>> getActiveWithdraw(@PathVariable("shopId") String shopId) {
		List<Withdraw> withdrawList = withdrawService.getActiveWithdraw(shopId);
		return new ResponseEntity<List<Withdraw>>(withdrawList, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateWork(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != withdrawService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Withdraw withdraw = withdrawService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.ACCOUNT_HOLDER_NAME)) {
				String accountHolderName = json.get(ServiceConstants.ACCOUNT_HOLDER_NAME);
				withdraw.setAccountHolderName(accountHolderName);
			}

			if (null != json.get(ServiceConstants.ACCOUNT_NUM)) {
				String accountNum = json.get(ServiceConstants.ACCOUNT_NUM).toString();
				withdraw.setAccountNum(accountNum);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
				withdraw.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.BANK_NAME)) {
				String bankName = json.get(ServiceConstants.BANK_NAME).toString();
				withdraw.setBankName(bankName);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				withdraw.setShopId(shopId);
			}

			if (null != json.get(ServiceConstants.USER_ID)) {
				int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID).toString());
				withdraw.setUserId(userId);
			}
			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				withdraw.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
				withdraw.setDeleted(isDeleted);
			}
			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID).toString());
				withdraw.setAdminId(adminId);
			}

			if (null != json.get(ServiceConstants.WITHDRAW_BALANCE)) {
				int withdrawBalance = Integer.parseInt(json.get(ServiceConstants.WITHDRAW_BALANCE).toString());
				withdraw.setWithdrawBalance(withdrawBalance);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER).toString();
				withdraw.setMobileNo(mobileNo);
			}

			if (null != json.get(ServiceConstants.ACCOUNT_ID)) {
				int accountId = Integer.parseInt(json.get(ServiceConstants.ACCOUNT_ID).toString());
				withdraw.setAccountId(accountId);
			}

			withdrawService.updateWithdraw(withdraw);
			response.put("status", Boolean.TRUE.toString());
			response.put("status", "Withdraw updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("status", "Withdraw in not update");
		}

		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createWithdraw(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.ACCOUNT_NUM));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.ADMIN_ID)
				&& null != json.get(ServiceConstants.WITHDRAW_BALANCE)
				&& null != json.get(ServiceConstants.ACCOUNT_ID)) {
			
			int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID)),
					accountId = Integer.parseInt(json.get(ServiceConstants.ACCOUNT_ID));
			String shopId = json.get(ServiceConstants.SHOP_ID);
			boolean checkAdmin = adminService.checkActiveAdminByShopId(shopId);
			Calculation calculation = new Calculation();
			if (checkAdmin) {
				List<AdminBillBook> adminBillBookList =adminBillBookService.getBillBookByAdminId(String.valueOf(adminId));
				AdminBillBook adminBillBook = adminBillBookList.get(0);
				Admin admin = adminService.getAdminByShopId(shopId);
				Account account = accountService.getAccount(accountId);
				Transaction transaction = new Transaction();
				String accountNumber = account.getAccountNum();
				int withdrawBalance = Integer.parseInt(json.get(ServiceConstants.WITHDRAW_BALANCE));
				String accountHolderName = account.getAccountHolderName();
				String bankName = account.getBankName();
						

				Withdraw withdraw = new Withdraw(json.get(ServiceConstants.ACCOUNT_NUM),
						(json.get(ServiceConstants.BANK_NAME)), (json.get(ServiceConstants.ACCOUNT_HOLDER_NAME)));

				LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ADMIN");
				int userType = lookUp.getLookUpId();
				LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "WALLET_PAYMENT");
				int paymentMode = lookUp1.getLookUpId();
				LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "WITHDRAW_PENDING");
				int withdrawStatus = lookUp2.getLookUpId();
				LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "WITHDRAW_REQUEST");
				int transactionType = lookUp3.getLookUpId();
				LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "COMPLETED");
				int transactionStatus = lookUp4.getLookUpId();
				LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "WITHDRAW_NOTIFICATION");
				int withdrawNotification = lookUp5.getLookUpId();

				float adminWallet = admin.getWallet(), availableAmount = admin.getAvailableAmount();
				int securityBalance = 500;
				float maximumRequestBalance = availableAmount - securityBalance;
				if (withdrawBalance <= maximumRequestBalance) {
					admin.setWallet(calculation.DecimalCalculation(adminWallet - withdrawBalance));
					admin.setAvailableAmount(calculation.DecimalCalculation(availableAmount - withdrawBalance));
					String order_rcptid_11 = Strings.EMPTY;
					order_rcptid_11 = UUID.randomUUID().toString().substring(0, 10);
					
					
					adminBillBook.setDebit(adminBillBookList.get(0).getDebit() + withdrawBalance);
					adminBillBook.setLastDebitedOn(new Date());
					adminBillBook.setUpdatedOn(new Date());
				

					Boolean paymentDone = adminService.updateAdmin(admin);
					if (paymentDone) {
						
						transaction.setActive(Boolean.TRUE);
						transaction.setDeleted(Boolean.FALSE);
						transaction.setAdminId(adminId);
						transaction.setAmount( withdrawBalance);
						transaction.setTotalAmount( withdrawBalance);
						transaction.setCreatedOn(new Date());
						transaction.setOrderRcptidId(order_rcptid_11);
						transaction.setPaymentMode(paymentMode);
						transaction.setShopId(shopId);
						transaction.setUserType(userType);
						transaction.setTransactionType(transactionType);
						transaction.setTransactionStatus(transactionStatus);
						 transaction.setTransactionId(order_rcptid_11);
						List<Transaction> checkTransaction = transactionService.getByAdminId(adminId, transactionType,
								Boolean.TRUE);
						if (null != checkTransaction) {
							for (int i = 0; i < checkTransaction.size(); i++) {
								checkTransaction.get(i).setActive(Boolean.FALSE);
								transactionService.updateTransaction(checkTransaction.get(i));
							}
						}

						boolean transactionCreate = transactionService.createTransactionForPurchase(transaction);
						if (transactionCreate) {
							List<Transaction> activeTransaction = transactionService.getByAdminId(adminId,
									transactionType, Boolean.TRUE);
							int localTransactionId = activeTransaction.get(0).getId();
							withdraw.setAccountHolderName(account.getAccountHolderName());
							withdraw.setAccountNum(String.valueOf(accountNumber));
							withdraw.setActive(Boolean.TRUE);

							withdraw.setAdminId(adminId);
							withdraw.setBankName(account.getBankName());
							withdraw.setAccountId(accountId);
							withdraw.setWithdrawStatus(withdrawStatus);
							withdraw.setMobileNo(account.getMobileNo());
							withdraw.setRequestTransactionId(localTransactionId);

							withdraw.setCreatedOn(new Date());
							withdraw.setDeleted(Boolean.FALSE);

							withdraw.setMobileNo(account.getMobileNo());

							withdraw.setShopId(shopId);
							
							withdraw.setWithdrawBalance(withdrawBalance);

							List<Withdraw> withdrawList = withdrawService.checkActiveWithdraw(adminId, Boolean.TRUE);
							if (null != withdrawList) {
								for (int i = 0; i < withdrawList.size(); i++) {
									withdrawList.get(i).setActive(Boolean.FALSE);
									withdrawService.updateWithdraw(withdrawList.get(i));

								}
							}
							boolean result = withdrawService.createWithdraw(withdraw);
							if (result) {
								boolean updateBillBook= adminBillBookService.updateAdminBillBook(adminBillBook);
								List<Withdraw> withdrawList1 = withdrawService.checkActiveWithdraw(adminId,
										Boolean.TRUE);
								int id = withdrawList1.get(0).getId();
								transaction.setWithdrawId(withdraw.getId());
								notificationService.createNotification(withdraw.getId(), withdrawNotification);
								
								response.put("status", Strings.EMPTY + result);
								response.put("description",
										"Withdraw created successfully with given account number , go through your inbox to activate");
							} else {
								admin = adminService.getAdmin(adminId);
								admin.setWallet(calculation.DecimalCalculation(admin.getWallet() + withdrawBalance));
								admin.setAvailableAmount(calculation.DecimalCalculation(admin.getAvailableAmount() + withdrawBalance));								
								adminService.updateAdmin(admin);
								response.put("status", Boolean.FALSE.toString());
								response.put("Descreption", "your request failed");

							}

							response.put("accountNum", String.valueOf(account.getAccountNum()));
						} else {
							admin = adminService.getAdmin(adminId);
							admin.setWallet(calculation.DecimalCalculation(admin.getWallet() + withdrawBalance));
							admin.setAvailableAmount(calculation.DecimalCalculation(admin.getAvailableAmount() + withdrawBalance));
							adminService.updateAdmin(admin);
							response.put("status", Boolean.FALSE.toString());
							response.put("Descreption", "your payment failed");
						}
					} else {
						response.put("status", Boolean.FALSE.toString());
						response.put("Descreption", "your payment failed");
					}
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("descreption", "Your wallet balance is not sufficient");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("discription", "Your account details is invalid");
			}
		}
		return ResponseEntity.ok().body(response);
	}

}
