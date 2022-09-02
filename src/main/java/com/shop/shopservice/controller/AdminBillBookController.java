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

import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.service.IAdminBillBookService;

@RestController
@RequestMapping("/api/adminbillbook")
public class AdminBillBookController {
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IAdminBillBookService adminBillBookService;

	@GetMapping("getall")
	public ResponseEntity<List<AdminBillBook>> getAll() {
		List<AdminBillBook> adminBillBookList = adminBillBookService.getAll();
		return new ResponseEntity<List<AdminBillBook>>(adminBillBookList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<AdminBillBook> getById(@PathVariable("id") Integer id) {
		AdminBillBook adminBillBook = adminBillBookService.getBillBookById(id);
//		if (null != adminBillBook) {
//			return new ResponseEntity<AdminBillBook>(adminBillBook, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<AdminBillBook>(HttpStatus.ALREADY_REPORTED);
//		}
		return new ResponseEntity<AdminBillBook>(adminBillBook, HttpStatus.OK);
	}

	@GetMapping("getbyadminid/{adminId}")
	public ResponseEntity<List<AdminBillBook>> getBillBookByAdminId(@PathVariable("adminId") String adminId) {
		List<AdminBillBook> adminBillBookList = adminBillBookService.getBillBookByAdminId(adminId);
		return new ResponseEntity<List<AdminBillBook>>(adminBillBookList, HttpStatus.OK);
	}

	@GetMapping("getbyadminidby/{adminId}")
	public ResponseEntity<MyResponse> getBillBookByAdminIdTest(@PathVariable("adminId") String adminId) {
		MyResponse response = new MyResponse();
		List<AdminBillBook> adminBillBookList = adminBillBookService.getBillBookByAdminId(adminId);
		if (null != adminBillBookList) {
			response.setStatus(ResponseConstants.SUCCESS_MSG[0]);
			response.setDescription(ResponseConstants.SUCCESS_MSG[1]);
			response.setData(adminBillBookList);
		} else {
			response.setStatus(ResponseConstants.NULL_MSG[0]);
			response.setDescription(ResponseConstants.NULL_MSG[1]);
			response.setData(adminBillBookList);
		}

		return new ResponseEntity<MyResponse>(response, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAdminBillBook(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		AdminBillBook adminBillBook = new AdminBillBook(json.get(ServiceConstants.ADMIN_ID),
				json.get(ServiceConstants.SHOP_ID));

		adminBillBook.setAdminId(json.get(ServiceConstants.ADMIN_ID));
		adminBillBook.setCreatedOn(new Date());
		adminBillBook.setCredit(Integer.parseInt(json.get(ServiceConstants.CREDIT)));
		adminBillBook.setDebit(Integer.parseInt(json.get(ServiceConstants.DEBIT)));
		adminBillBook.setActive(Boolean.TRUE);
		adminBillBook.setDeleted(Boolean.FALSE);
		adminBillBook.setLastCreditedOn(new Date());
		adminBillBook.setLastDebitedOn(new Date());
		adminBillBook.setUpdatedOn(new Date());
		adminBillBook.setShopId(json.get(ServiceConstants.SHOP_ID));
		adminBillBook.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));

		response.put("adminId", json.get(ServiceConstants.ADMIN_ID));
		if (adminBillBookService.adminBillBookExists(adminBillBook.getAdminId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Admin bill book already exist with given admin Id");
		} else {
			boolean result = adminBillBookService.createAdminBillBook(adminBillBook);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Bank created successfully with given account, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAdminBillBook(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != adminBillBookService.getBillBookById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			AdminBillBook adminBillBook = adminBillBookService
					.getBillBookById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				adminBillBook.setShopId(shopId);

			}

			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				String adminId = json.get(ServiceConstants.ADMIN_ID);
				adminBillBook.setAdminId(adminId);

			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				adminBillBook.setCreatedOn(createdOn);

			}

			if (null != json.get(ServiceConstants.CREDIT)) {
				int credit = Integer.parseInt(json.get(ServiceConstants.CREDIT));
				adminBillBook.setCredit(credit);
			}

			if (null != json.get(ServiceConstants.DEBIT)) {
				int debit = Integer.parseInt(json.get(ServiceConstants.DEBIT));
				adminBillBook.setDebit(debit);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				adminBillBook.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				adminBillBook.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				adminBillBook.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.LAST_CREDITED_ON)) {
				Date lastCreditedOn = new Date();
				adminBillBook.setLastCreditedOn(lastCreditedOn);
			}

			if (null != json.get(ServiceConstants.LAST_DEBITED_ON)) {
				Date lastDebitedOn = new Date();
				adminBillBook.setLastDebitedOn(lastDebitedOn);
			}

			if (null != json.get(ServiceConstants.UPDATED_ON)) {
				Date updatedOn = new Date();
				adminBillBook.setUpdatedOn(updatedOn);
			}

			adminBillBookService.updateAdminBillBook(adminBillBook);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Admin bill book updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Admin bill book doesn't exist with given  user id");
		}

		return ResponseEntity.ok().body(response);
	}

}
