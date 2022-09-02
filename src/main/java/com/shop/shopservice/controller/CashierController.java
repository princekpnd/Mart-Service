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
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Cashier;
import com.shop.shopservice.entity.Offline;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.service.ICashierService;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;

@RestController
@RequestMapping("/api/cashier")
public class CashierController {
	private final Logger log = LoggerFactory.getLogger(CashierController.class);

	@Autowired
	private ICashierService cashierService;
	
	@Autowired
	private IOfflineService offlineService;
	

	@Autowired
	private IOfflineProductListService offlineProductListService;

	@GetMapping("getall")	
	public ResponseEntity<List<Cashier>> getAll() {
		List<Cashier> cashierList = cashierService.getAll();
		return new ResponseEntity<List<Cashier>>(cashierList, HttpStatus.OK);
	}

	@GetMapping("get/{cashierId}")
	public ResponseEntity<Cashier> getById(@PathVariable("cashierId") int cashierId) {
		Cashier cashier = cashierService.getById(cashierId);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	@GetMapping("getdeactive/{cashierId}")
	public ResponseEntity<Cashier> getDeactiveById(@PathVariable("cashierId") int cashierId) {
		Cashier cashier = cashierService.getDeactiveById(cashierId);
		cashier.setActive(Boolean.FALSE);
		cashier.setDeleted(Boolean.TRUE);
		cashierService.updateCashier(cashier);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	@GetMapping("get/byshopid/{shopId}")
	public ResponseEntity<List<Cashier>> getByShopId(@PathVariable("shopId") String shopId){
		List<Cashier> cashierList = cashierService.getByShopId(shopId);
		return new ResponseEntity<List<Cashier>>(cashierList, HttpStatus.OK);
	}
	
	
	@GetMapping("get/allamount/{shopId}/{cashierId}")
	public ResponseEntity<Cashier> getAllAmount(@PathVariable("shopId") String shopId, @PathVariable("cashierId") int cashierId){
		Cashier cashier = cashierService.getAllAmount(shopId,cashierId);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	//Get all details of offline bill
	
	@GetMapping("get/allbill/{shopId}/{cashierId}")
	public ResponseEntity<Cashier> getAllBill(@PathVariable("shopId") String shopId, @PathVariable("cashierId") int cashierId){
		Cashier cashier = cashierService.getAllAmount(shopId,cashierId);
		List<Offline> offlineList = offlineService.getAllAmountByCashierId(shopId, String.valueOf(cashierId));
		cashier.setOffline(offlineList);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	//Get all details of offline bill and its productList
	
	@GetMapping("get/allbilldetails/{shopId}/{cashierId}")
	public ResponseEntity<Cashier> getAllBillDetails(@PathVariable("shopId") String shopId, @PathVariable("cashierId") int cashierId){
		Cashier cashier = cashierService.getAllAmount(shopId,cashierId);
		List<Offline> offlineList = offlineService.getAllAmountByCashierId(shopId, String.valueOf(cashierId));
		for(int i =0; i < offlineList.size() ; i++) {
			int billId = offlineList.get(i).getBillingId();
			List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billId);
			offlineList.get(i).setOfflineProductList(offlineProductList);
			
		}
		cashier.setOffline(offlineList);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	@GetMapping("getdeactiveby/mobilenumber/{mobileNo}")
	public ResponseEntity<Cashier> getDeActiveByMobileNumber(@PathVariable("mobileNo") String mobileNo){
		Cashier cashier = cashierService.getDeActiveByMobileNumber(mobileNo);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	@GetMapping("login/{mobileNo}/{pwd}")
	public ResponseEntity<Cashier> loginCashier(@PathVariable("mobileNo") String mobileNo, @PathVariable("pwd") String pwd){
		Cashier cashier = cashierService.loginCashier(mobileNo,pwd);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	@GetMapping("getby/shopid/mobilenumber/{shopId}/{mobileNo}")
	public ResponseEntity<Cashier> getByMobileNumberAndShopId(@PathVariable("shopId") String shopId, @PathVariable("mobileNo") String mobileNo){
		Cashier cashier = cashierService.getByMobileNumberAndShopId(shopId, mobileNo);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	@GetMapping("getby/mobilenumber/{mobileNo}")
	public ResponseEntity<Cashier> getByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		Cashier cashier = cashierService.getByMobileNumber(mobileNo);
		return new ResponseEntity<Cashier>(cashier, HttpStatus.OK);
	}
	
	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createCashierImformation(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.MOBILE_NUMBER)
				&& null != json.get(ServiceConstants.F_NAME)) {
			Cashier cashier = new Cashier();

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME);
				cashier.setLastName(lastName);
			}

			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = json.get(ServiceConstants.ADHAR_NUM);
				cashier.setAdharNumber(adharNumber);
			}

			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM);
				cashier.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pinCode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				cashier.setPinCode(pinCode);
			}

			if (null != json.get(ServiceConstants.CITY)){
				String city = json.get(ServiceConstants.CITY);
				cashier.setCity(city);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				cashier.setState(state);
			}
			
			if (null != json.get(ServiceConstants.PLAYER_ID)) {
				String playerId = json.get(ServiceConstants.PLAYER_ID);
				cashier.setPlayerId(playerId);
			}

			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				cashier.setCountry(country);
			}

			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				cashier.setPostOffice(postOffice);
			}

			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				cashier.setPoliceStation(policeStation);
			}

			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR);
				cashier.setAvatar(avatar);
			}

			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID);
				cashier.setEmailId(emailId);
			}

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				cashier.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				cashier.setShopName(shopName);
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				cashier.setDistrict(district);
			}

			if (null != json.get(ServiceConstants.GENDER)) {
				int gender = Integer.parseInt(json.get(ServiceConstants.GENDER));
				cashier.setGender(gender);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				cashier.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.PAID)) {
				int paid = Integer.parseInt(json.get(ServiceConstants.PAID));
				cashier.setPaid(paid);
			}

			cashier.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			cashier.setShopId(json.get(ServiceConstants.SHOP_ID));
			cashier.setFristName(json.get(ServiceConstants.F_NAME));
			cashier.setPwd(json.get(ServiceConstants.PWD));
			cashier.setActive(Boolean.TRUE);
			cashier.setDeleted(Boolean.FALSE);
			cashier.setCreatedOn(new Date());

			if (cashierService.cashierExits(cashier.getMobileNo(), cashier.getShopId())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("Discreption", "All ready created with given mobile number"); 

			} else {
				cashierService.createCashier(cashier);
				response.put("status", Boolean.TRUE.toString());
				response.put("Discreption", "Cashier created");
			}
		}

		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/login")
	ResponseEntity<?> validateUser(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.MOBILE_NUMBER) && null != json.get(ServiceConstants.PWD) && null != json.get(ServiceConstants.PLAYER_ID)){
			Cashier cashier = null;
			String playerId = json.get(ServiceConstants.PLAYER_ID);
			 cashier = cashierService.loginCashier(json.get(ServiceConstants.MOBILE_NUMBER), json.get(ServiceConstants.PWD));
				if(null != cashier) {
					cashier.setToken(UUID.randomUUID().toString() + "-" + cashier.getCashierId());
					cashier.setPlayerId(playerId);
					cashierService.updateCashier(cashier);
					cashier.setPwd(Strings.EMPTY);
		//			cashier.setOtp(Integer.parseInt(Strings.EMPTY));
					return ResponseEntity.ok().body(cashier);
				}
			
		}
		return ResponseEntity.ok().body(response);
	}


	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != cashierService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Cashier cashier = cashierService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.F_NAME)) {
				String fristName = json.get(ServiceConstants.F_NAME);
				cashier.setFristName(fristName);
			}

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME);
				cashier.setLastName(lastName);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				cashier.setMobileNo(mobileNo);
				;
			}

			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = json.get(ServiceConstants.ADHAR_NUM);
				cashier.setAdharNumber(adharNumber);
			}

			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM);
				cashier.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pinCode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				cashier.setPinCode(pinCode);
			}

			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				cashier.setCity(city);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				cashier.setState(state);
			}

			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				cashier.setCountry(country);
			}

			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				cashier.setPostOffice(postOffice);
			}

			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				cashier.setPoliceStation(policeStation);
			}

			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR);
				cashier.setAvatar(avatar);
			}

			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID);
				cashier.setEmailId(emailId);
			}

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				cashier.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				cashier.setShopName(shopName);
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				cashier.setDistrict(district);
			}

			if (null != json.get(ServiceConstants.GENDER)) {
				int gender = Integer.parseInt(json.get(ServiceConstants.GENDER));
				cashier.setGender(gender);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				cashier.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.PAID)) {
				int paid = Integer.parseInt(json.get(ServiceConstants.PAID));
				cashier.setPaid(paid);
			}

			if (cashierService.updateCashier(cashier)) {
				response.put("status", Boolean.TRUE.toString());
				response.put("descreption", "Cashier information update");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "Cashier information not update");
			}
		}
		return ResponseEntity.ok().body(response);
	}

}
