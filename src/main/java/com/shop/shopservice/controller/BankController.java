package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Bank;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IBankService;

@RestController
@RequestMapping("/api/bank")

public class BankController {
	private final Logger log = LoggerFactory.getLogger(BankController.class);
	@Autowired
	private IBankService bankService;

	@Autowired
	private IAdminService adminService;

	@GetMapping("getallbank")
	public ResponseEntity<List<Bank>> getAllbank() {
		List<Bank> bankList = bankService.getAllBank();
		return new ResponseEntity<List<Bank>>(bankList, HttpStatus.OK);
	}

	@GetMapping("get")
	public ResponseEntity<?> getloop() {
		Map<Integer, Integer> response = new HashMap<Integer, Integer>();
		int i = 0;
		for (i = 0; i < 10; i++) {
			for (int j = 0; j < i; j++) {
				if (i % 2 == 0) {
					System.out.println(i);
				}

			}

			System.out.println("\t");
		}
		return ResponseEntity.ok().body(i);

	}

//	@GetMapping("getarray")
//	public ResponseEntity<Map<String, String>> addWishListByProductIdTest() {
//		Map<String, String> response = new HashMap<String, String>();
//		String str2 = "hello,computer,34,55,234,122";
//		String[] str3 = str2.split(",");
////		int arr = .toString();
//		response.put("status", str3);
//		return ResponseEntity.ok().body(response);
//	}

	@GetMapping("getarray")
	public ResponseEntity<?> addWishListByProductIdTest() {
		Map<String, String> response = new HashMap<String, String>();
		String str2 = "hello,computer,34,55,234,122";
		String[] array = str2.split(",");
		array = (String[]) ArrayUtils.remove(array, 2);

//		int arr = .toString();
//		response.put("status", str3);
		return ResponseEntity.ok().body(array);
	}

//	@GetMapping("pattern")
//	public ResponseEntity<?> getPattern() { 
//		
//		for (int i = 0; i < 5; i++) // outer loop for number of rows(n) { for (int j=n-i; j>1; j--) //inner loop for
//									// spaces
//		{
//			System.out.print(" "); // print space
//		}
//		int i = 0;
//		for (int j = 0; j <= i; j++) // inner loop for number of columns
//		{
//			System.out.print("* "); // print star
//		}
//
//		System.out.println(); // ending line after each row
//		return ResponseEntity.ok().body(null);
//	}
//	

	@GetMapping("get/{id}")
	public ResponseEntity<Bank> getBankById(@PathVariable("id") Integer id) {
		Bank bank = bankService.getBank(id.intValue());
		return new ResponseEntity<Bank>(bank, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Bank>> getByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Bank> bankList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted()) {
			bankList = bankService.getBankByShopId(shopId);
		}
		return new ResponseEntity<List<Bank>>(bankList, HttpStatus.OK);

	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != bankService.getBank(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Bank bank = bankService.getBank(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME).toString();
				bank.setName(name);
			}

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID).toString());
				bank.setId(id);

			}
			if (null != json.get(ServiceConstants.ACCOUNT_NUM)) {
				int accountNum = Integer.parseInt(json.get(ServiceConstants.ACCOUNT_NUM).toString());
				bank.setAccountNum(accountNum);

			}
			if (null != json.get(ServiceConstants.BANK_NAME)) {
				String bankName = json.get(ServiceConstants.BANK_NAME).toString();
				bank.setBankName(bankName);

			}
			if (null != json.get(ServiceConstants.ADDRESS)) {
				String address = json.get(ServiceConstants.ADDRESS).toString();
				bank.setAddress(address);
			}
			if (null != json.get(ServiceConstants.IFSC)) {
				String ifsc = json.get(ServiceConstants.IFSC).toString();
				bank.setIfsc(ifsc);

			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				bank.setShopId(shopId);
			}

			bankService.updateBank(bank);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Bank updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Bank doesn't exist with given  userid");
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.ACCOUNT_NUM));
		Map<String, String> response = new HashMap<String, String>();
		Bank bank = new Bank(Integer.parseInt(json.get(ServiceConstants.ACCOUNT_NUM)), json.get(ServiceConstants.NAME));

		bank.setAccountNum(Integer.parseInt((json.get(ServiceConstants.ACCOUNT_NUM))));
		bank.setBankName(json.get(ServiceConstants.BANK_NAME));
		bank.setName(json.get(ServiceConstants.NAME));
		bank.setAddress(json.get(ServiceConstants.ADDRESS));
		bank.setIfsc(json.get(ServiceConstants.IFSC));

		response.put("accountNum", json.get(ServiceConstants.ACCOUNT_NUM));
		if (bankService.bankExists(bank.getAccountNum())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Bank already exist with given Account Numbar");
		} else {
			boolean result = bankService.createBank(bank);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Bank created successfully with given account, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteBank(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Bank bank = bankService.getBank(id);

		if (null != bank) {
			boolean result = bankService.deleteBank(id);
			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Brand delete with given id:" + id);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Brand does not exist with given category Id");

		}
		return ResponseEntity.ok().body(response);
	}
}
