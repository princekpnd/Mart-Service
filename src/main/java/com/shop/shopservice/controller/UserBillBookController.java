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
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.UserBillBook;
import com.shop.shopservice.service.IUserBillBookService;

@RestController
@RequestMapping("/api/userbillbook")
public class UserBillBookController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserBillBookService userBillBookService;

	@GetMapping("getallbillbook")
	public ResponseEntity<List<UserBillBook>> getAllBillBook() {
		List<UserBillBook> userBillBook = userBillBookService.getAll();
		return new ResponseEntity<List<UserBillBook>>(userBillBook, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<UserBillBook> getById(@PathVariable("id") Integer id) {
		UserBillBook userBillBook = userBillBookService.getById(id);
		return new ResponseEntity<UserBillBook>(userBillBook, HttpStatus.OK);

	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<UserBillBook>> getByShopId(@PathVariable("shopId") String shopId) {
		List<UserBillBook> userBillBook = userBillBookService.getByShopId(shopId);
		return new ResponseEntity<List<UserBillBook>>(userBillBook, HttpStatus.OK);

	}

	@GetMapping("getbyuserid/{userId}")
	public ResponseEntity<List<UserBillBook>> getByUserId(@PathVariable("userId") String userId) {
		List<UserBillBook> userBillBook = userBillBookService.getByUserId(userId);
		return new ResponseEntity<List<UserBillBook>>(userBillBook, HttpStatus.OK);
	}

	@GetMapping("get/byuserid/shopid/{userId}/{shopId}")
	public ResponseEntity<List<UserBillBook>> getByUserIdAndShopId(@PathVariable("userId") String userId,
			@PathVariable("shopId") String shopId) {
		List<UserBillBook> userBillBook = userBillBookService.getByUserIdAndShopId(userId, shopId);
		return new ResponseEntity<List<UserBillBook>>(userBillBook, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createUserBillBook(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		UserBillBook userBillBook = new UserBillBook(json.get(ServiceConstants.SHOP_ID),
				json.get(ServiceConstants.USER_ID));

		userBillBook.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
		userBillBook.setUserId(json.get(ServiceConstants.USER_ID));
		userBillBook.setShopId(json.get(ServiceConstants.SHOP_ID));
		userBillBook.setLastDebitedOn(new Date());
		userBillBook.setLastCreditedOn(new Date());
		userBillBook.setDeleted(Boolean.FALSE);
		userBillBook.setDebit(Integer.parseInt(json.get(ServiceConstants.DEBIT)));
		userBillBook.setCreatedOn(new Date());
		userBillBook.setActive(Boolean.FALSE);

		response.put("userId", json.get(ServiceConstants.USER_ID));
		response.put("id", json.get(ServiceConstants.ID));
		response.put("shopId", json.get(ServiceConstants.SHOP_ID));
		if (userBillBookService.userBillBookExists(userBillBook.getUserId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "User bill book already exist with given user Id");
		} else {
			boolean result = userBillBookService.createUserBillBook(userBillBook);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"UserBillBook created successfully with given userId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateUserBillBook(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != userBillBookService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			UserBillBook userBillBook = userBillBookService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				userBillBook.setUserType(userType);
			}
			if (null != json.get(ServiceConstants.USER_ID)) {
				String userId = json.get(ServiceConstants.USER_ID);
				userBillBook.setUserId(userId);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				userBillBook.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.LAST_CREDITED_ON)) {
				Date lastCreditedOn = new Date();
				userBillBook.setLastCreditedOn(lastCreditedOn);
			}
			if (null != json.get(ServiceConstants.LAST_DEBITED_ON)) {
				Date lastDebitedOn = new Date();
				userBillBook.setLastDebitedOn(lastDebitedOn);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				userBillBook.setDeleted(isDeleted);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				userBillBook.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.DEBIT)) {
				int debit = Integer.parseInt(json.get(ServiceConstants.DEBIT));
				userBillBook.setDebit(debit);
			}
			if (null != json.get(ServiceConstants.CREDIT)) {
				int credit = Integer.parseInt(json.get(ServiceConstants.CREDIT));
				userBillBook.setCredit(credit);
			}

			userBillBookService.updateUserBillBook(userBillBook);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "User bill book updated");
		} else {

			response.put("status", Boolean.FALSE.toString());
			response.put("description", "User bill book doesn't exist with given  userid");
		}

		return ResponseEntity.ok().body(response);
	}

}
