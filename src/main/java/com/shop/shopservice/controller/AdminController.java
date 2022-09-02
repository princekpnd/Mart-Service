package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.metadata.ValidateUnwrappedValue;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.IAdminDAO;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.entity.Distance;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.ShopImage;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IShopImageService;
import com.shop.shopservice.service.IUserAddressService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final Logger log = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private IAdminService adminService;

	@Autowired
	private IShopImageService shopImageService;

	@Autowired
	IUserAddressService userAddressService;

	@Autowired
	IAdminDAO adminDao;

	@Autowired
	private ILookUp lookup;

	@GetMapping("getalladmin")
	public ResponseEntity<List<Admin>> getAlladmin() {
		List<Admin> adminList = adminService.getAllAdmin();
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}
	
	@GetMapping("getallactiveadmin")
	public ResponseEntity<List<Admin>> getAllActiveAdmin() {
		List<Admin> adminList = adminService.getAllActiveAdmin();
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}
	
	@GetMapping("test")
	public int result() {
		int admin = adminService.getResult();
		return admin;
		
	}

	@GetMapping("getalladmin/with/address")
	public ResponseEntity<List<Admin>> getAlladminWithAddress() {
		List<Admin> adminList = adminService.getAllAdmin();
		if (adminList.size() > 0) {
			for (int adminIndex = 0; adminIndex < adminList.size(); adminIndex++) {
				String userId = String.valueOf(adminList.get(adminIndex).getAdminId());
				List<UserAddress> userAddress = userAddressService.getAddressByUserId(userId);
				if (userAddress.size() > 0) {
					adminList.get(adminIndex).setAdminAddress(userAddress);
				}
			}
		}
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}

//	private JsonElement callAndParse(String endpoint) {
//		URL url;
//		try {
//			url = new URL(endpoint);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLine;
//			StringBuffer content = new StringBuffer();
//			while ((inputLine = in.readLine()) != null) {
//				content.append(inputLine);
//			}
//			in.close();
//			con.disconnect();
//			return new JsonParser().parse(content.toString());
//		} catch (IOException e) {
//			JsonObject error = new JsonObject();
//			error.add("error", new JsonPrimitive(e.getMessage()));
//			return error;
//		}
//	}

	@GetMapping("getbylocation/{latitude}/{longitude}")
	public ResponseEntity<?> getAllAdminWithLocation(@PathVariable("latitude") double latitude,
			@PathVariable("longitude") double longitude) {
		Map<String, String> response = new HashMap<String, String>();
		List<UserAddress> userAddressList = userAddressService.getAllUserAddress();
		double response1 = 0;
		List<Distance> responseList = new ArrayList<Distance>();
		List<Admin> adminList = new ArrayList<Admin>();
		Distance tempList = new Distance();

		for (int i = 0; i < userAddressList.size(); i++) {
			double destLat = Double.parseDouble(userAddressList.get(i).getLatitude());
			double destLong = Double.parseDouble(userAddressList.get(i).getLongitude());
			if (destLat > 1 && destLong > 1) {
				DistanceController distanceController = new DistanceController();
				response1 = distanceController.Distance(latitude, longitude, destLat, destLong);
				if (0 != destLat || 0 != destLong) {
					Distance temp = new Distance();

					temp.setText(String.valueOf(response1));
					temp.setValue(userAddressList.get(i).getShopId());
					responseList.add(temp);
				}
			}
		}

		if (responseList.size() > 0) {
			for (int i = 0; i < responseList.size(); i++) {
				for (int j = i + 1; j < responseList.size(); j++) {
					double old = Double.parseDouble(responseList.get(i).getText()),
							newData = Double.parseDouble(responseList.get(j).getText());
					if (newData < old) {
						tempList = responseList.get(i);
						responseList.set(i, responseList.get(j));
						responseList.set(j, tempList);
					}
				}
				Admin admin = adminService.getAdminByShopId(responseList.get(i).getValue());
				if (null != admin) {
					List<UserAddress> userAddress = userAddressService
							.getAddressByShopId(responseList.get(i).getValue());
					admin.setAdminAddress(userAddress);
					adminList.add(admin);
				}
			}
		}
		return new ResponseEntity<>(adminList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		adminDao.indexAdmin();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Admin>> searchAllUserBykeyword(@PathVariable("keyword") String keyword) {
		List<Admin> result = new ArrayList<Admin>();
		result = adminDao.searchAdmin(keyword);
		return new ResponseEntity<List<Admin>>(result, HttpStatus.OK);
	}

	@GetMapping("search/{keyword}/{latitude}/{longitude}")
	public ResponseEntity<List<Admin>> searchAllAdminByLocation(@PathVariable("keyword") String keyword,
			@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
		List<Admin> result = new ArrayList<Admin>();
		result = adminDao.searchAdmin(keyword);
		Map<String, String> response = new HashMap<String, String>();
		List<UserAddress> userAddressList = null;
		double response1 = 0;
		List<Distance> responseList = new ArrayList<Distance>();
		List<Admin> adminList = new ArrayList<Admin>();
		Distance tempList = new Distance();

		if (result.size() > 0) {

			for (int i = 0; i < result.size(); i++) {
				userAddressList = userAddressService.getAddressByShopId(result.get(i).getShopId());
				if (userAddressList.size() > 0) {
					double destLat = Double.parseDouble(userAddressList.get(0).getLatitude());
					double destLong = Double.parseDouble(userAddressList.get(0).getLongitude());
					DistanceController distanceController = new DistanceController();
					response1 = distanceController.Distance(latitude, longitude, destLat, destLong);

					if (response1 > 0) {
						Distance temp = new Distance();

						temp.setText(String.valueOf(response1));
						temp.setValue(userAddressList.get(0).getShopId());
						responseList.add(temp);
					}
				}
			}
			if (responseList.size() > 0) {
				for (int i = 0; i < responseList.size(); i++) {
					for (int j = i + 1; j < responseList.size(); j++) {
						double old = Double.parseDouble(responseList.get(i).getText()),
								newData = Double.parseDouble(responseList.get(j).getText());
						if (newData < old) {
							tempList = responseList.get(i);
							responseList.set(i, responseList.get(j));
							responseList.set(j, tempList);
						}
					}
					Admin admin = adminService.getAdminByShopId(responseList.get(i).getValue());
					if (null != admin) {
						List<UserAddress> userAddress = userAddressService
								.getAddressByShopId(responseList.get(i).getValue());
						admin.setAdminAddress(userAddress);
						adminList.add(admin);
					}
				}
			}
		}

		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}

	@GetMapping("get/{adminId}")
	public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") Integer adminId) {
		Admin admin = adminService.getAdmin(adminId.intValue());
		if(null != admin) {
			return new ResponseEntity<Admin>(admin,HttpStatus.OK);
		}
		return null;
	}
	
	@GetMapping("get/activeadminbyshopid/{shopId}")
	public  ResponseEntity<List<Admin>> getActiveAdminByShopId(@PathVariable("shopId") String shopId){
		List<Admin> adminList = adminService.getActiveAdminByShopId(shopId);
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}
	@GetMapping("get/checkactiveadminbyshopid/{shopId}")
	public  ResponseEntity<Map<String, String>> checkActiveAdminByShopId(@PathVariable("shopId") String shopId){
		Map<String, String> response = new HashMap<String, String>();
		Boolean adminList = adminService.checkActiveAdminByShopId(shopId);
		response.put("status", adminList.toString());
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}
	
//	@GetMapping("get/adminactive/{shopId}/{isActive}")
//	public  ResponseEntity<Boolean> adminActive(@PathVariable("shopId") String shopId, @PathVariable("isActive") boolean isActive){
//	//	boolean isActive = true;
//		boolean adminList = adminService.adminAclive(shopId, isActive);
//		return new ResponseEntity<Boolean>(adminList,HttpStatus.OK);
//	}
	
	@GetMapping("get/adminactive/{shopId}")
	public  ResponseEntity<Map<String, String>> adminActive(@PathVariable("shopId") String shopId){
		Map<String, String> response = new HashMap<String,String>();
		Boolean adminList = adminService.adminAclive(shopId);
		if(adminList) {
			response.put("status",Boolean.TRUE.toString());
			response.put("descreption", "Admin is Active");
		}else {
			response.put("status",Boolean.FALSE.toString());
			response.put("descreption", "Admin is not Active");
		}
		
		return new ResponseEntity<Map<String, String>>(response,HttpStatus.OK);
	}

	@GetMapping("getbyemail/{emailId}")
	public ResponseEntity<Admin> getAdminByEmailId(@PathVariable("emailId") String emailId) {
		Admin admin = adminService.getAdminByEmailId(emailId);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}
	
	@GetMapping("checkemail/{emailId}")
	public ResponseEntity<Map<String, String>> checkAdminByEmailId(@PathVariable("emailId") String emailId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean admin = adminService.checkAdminByEmailId(emailId);
		if(admin) {
			response.put("status", String.valueOf(admin));
		} else {
			response.put("status", String.valueOf(admin));
		}
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("checkemail1/{emailId}")
	public ResponseEntity<MyResponse> checkAdminByEmailId23(@PathVariable("emailId") String emailId) {
		Map<String, String> response = new HashMap<String, String>();
		MyResponse myResponse = new MyResponse();
		boolean admin = adminService.checkAdminByEmailId(emailId);
		if(admin) {
//			response.myResponse.setDescription("created");
//			response.put("status", String.valueOf(admin));
//			response.put("value", emailId);
//			response.put("status", HttpStatus.ACCEPTED.toString());
//			response.put("Des", "ACCEPTED");
//			array.addAll(array);
//			response.put("Data", array.toString());
			myResponse.setData(admin);
			myResponse.setDescription("Good");
			myResponse.setStatus("400");
			
		
		} else {
//			response.put("status", String.valueOf(admin));
//			response.put("value", emailId);
//			response.put("status", HttpStatus.NOT_ACCEPTABLE.toString());
//			response.put("Des", "NOT_ACCEPTABLE");
			myResponse.setData("data");
			myResponse.setDescription("Not Good");
			myResponse.setStatus("500");
		
		}
		return new ResponseEntity<MyResponse>(myResponse, HttpStatus.OK);
	}
	
	

	@GetMapping("cheakdeactiveemail/{emailId}")
	public ResponseEntity<Map<String, String>> checkDeactiveAdminByEmail(@PathVariable("emailId") String emailId){
		Map<String, String> response= new HashMap<String, String>();
		boolean admin = adminService.checkDeactiveAdminByEmailId(emailId);
		if(admin) {
			response.put("status", String.valueOf(admin));
		}else {
			response.put("status", String.valueOf(admin));
		}
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("cheakdeactiveid/{adminId}/{registrationStatus}")
	public ResponseEntity<Map<String, String>> checkDeactiveAdminByAdminId(@PathVariable("adminId")  int adminId, @PathVariable("registrationStatus") int registrationStatus){
		Map<String, String> response= new HashMap<String, String>();
		boolean admin = adminService.checkDeactiveAdminByAdminId(adminId,registrationStatus );
		if(admin) {
			response.put("status", String.valueOf(admin));
		}else {
			response.put("status", String.valueOf(admin));
		}
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}

	@GetMapping("getadminbyshopid/{shopId}")
	public ResponseEntity<Admin> getAdminByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = null;
		admin = adminService.getAdminByShopId(shopId);
		if (null != admin) {
			List<UserAddress> userAddressList = userAddressService.getAddressByShopId(shopId);
			admin.setAdminAddress(userAddressList);
		}
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@GetMapping("getbymobilenumber/{mobileNo}")
	public ResponseEntity<Admin> getByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		Admin admin = adminService.getByMobileNumber(mobileNo);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@GetMapping("getbyshopId/{shopId}")
	public ResponseEntity<Admin> getByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getByShopId(shopId);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@GetMapping("getadminbyname/{firstName}")
	public ResponseEntity<List<Admin>> getAdminByFirstName(@PathVariable("firstName") String firstName) {
		List<Admin> adminList = adminService.getAdminByFirstName(firstName);
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}

	@GetMapping("getadmin/byadminid/shopid/{adminId}/{shopId}")
	public ResponseEntity<List<Admin>> getAdminByAdminIdAndShopId(@PathVariable("adminId") int adminId,
			@PathVariable("shopId") String shopId) {
		List<Admin> adminList = adminService.getAdminByAdminIdAndShopId(adminId, shopId);
		List<ShopImage> shopImageList = shopImageService.getShopImageByShopIdAndProductId(shopId, adminId);
		adminList.get(0).setShopImage(shopImageList);
		List<UserAddress> userAddressList = userAddressService.getAddressByShopId(shopId);
		adminList.get(0).setAdminAddress(userAddressList);
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}

	@GetMapping("get/shopby/shoptype/{shoptype}")
	public ResponseEntity<List<Admin>> getByShopType(@PathVariable("shoptype") int shoptype) {
		List<Admin> adminList = adminService.getByShopType(shoptype);
		return new ResponseEntity<List<Admin>>(adminList, HttpStatus.OK);
	}

	@GetMapping("getadminbyadharnumber/{adharNumber}")
	public ResponseEntity<List<Admin>> getAdminByAdharNumber(@PathVariable("adharNumber") Integer adharNumber) {
		List<Admin> admin = adminService.getAdminByAdharNumber(adharNumber.intValue());
		return new ResponseEntity<List<Admin>>(admin, HttpStatus.OK);
	}
	
	@GetMapping("validate/deactiveadmin/{emailId}/{pwd}")
	public ResponseEntity<Admin> getAdminByAdharNumber(@PathVariable("emailId") String emailId, @PathVariable("pwd") String pwd ) {
		Admin admin = adminService.getDeactiveAdmin(emailId, pwd);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAdmin(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != adminService.getAdmin(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Admin admin = adminService.getAdmin(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID).toString());
				admin.setAdminId(id);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				admin.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.F_NAME)) {
				String firstName = json.get(ServiceConstants.F_NAME).toString();
				admin.setFirstName(firstName);
			}

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME).toString();
				admin.setLastName(lastName);
			}

			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR).toString();
				admin.setAvatar(avatar);
			}

			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = (json.get(ServiceConstants.ADHAR_NUM).toString());
				admin.setAdharNumber(adharNumber);
			}

			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM).toString();
				admin.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER).toString();
				admin.setMobileNo(mobileNo);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				admin.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.PWD)) {
				String pwd = json.get(ServiceConstants.PWD).toString();
				admin.setPwd(pwd);
			}

			if (null != json.get(ServiceConstants.PAYMENT_STATUS)) {
				boolean paymentStatus = Boolean.parseBoolean(json.get(ServiceConstants.PAYMENT_STATUS).toString());
				admin.setPaymentStatus(paymentStatus);
			}

			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME).toString();
				admin.setShopName(shopName);
			}

			if (null != json.get(ServiceConstants.SHOP_TYPE)) {
				int shopType = Integer.parseInt(json.get(ServiceConstants.SHOP_TYPE).toString());
				admin.setShopType(shopType);
			}

			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID).toString();
				admin.setEmailId(emailId);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
				admin.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
				admin.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.OTP)) {
				String otp = json.get(ServiceConstants.OTP).toString();
				admin.setOtp(otp);
			}

			if (null != json.get(ServiceConstants.GST_NUMBER)) {
				String gstNumber = json.get(ServiceConstants.GST_NUMBER).toString();
				admin.setGstNumber(gstNumber);
			}
			if (null != json.get(ServiceConstants.VALIDITY)) {
				int validity = Integer.parseInt(json.get(ServiceConstants.VALIDITY).toString());
				admin.setValidity(validity);
			}

			if (null != json.get(ServiceConstants.GENDER)) {
				int gender = Integer.parseInt(json.get(ServiceConstants.GENDER).toString());
				admin.setGender(gender);

			}

			if (null != json.get(ServiceConstants.LATITUDE)) {
				String latitude = json.get(ServiceConstants.LATITUDE).toString();
				admin.setLatitude(latitude);
			}

			if (null != json.get(ServiceConstants.LONGITUDE)) {
				String longitude = json.get(ServiceConstants.LONGITUDE).toString();
				admin.setLongitude(longitude);
			}

			if (null != json.get(ServiceConstants.REGISTRATION_STATUS)) {
				int registrationStatus = Integer.parseInt(json.get(ServiceConstants.REGISTRATION_STATUS).toString());
				admin.setRegistrationStatus(registrationStatus);
			}
			
			if(null != json.get(ServiceConstants.DESCRIPTION)) {
				String description = json.get(ServiceConstants.DESCRIPTION).toString();
				admin.setDescription(description);
			}
			
			if(null != json.get(ServiceConstants.PLAYER_ID)) {
				String playerId = json.get(ServiceConstants.PLAYER_ID);
				admin.setPlayerId(playerId);
			}
			
			

			adminService.updateAdmin(admin);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Admin updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Admin doesn't exist with given  userid");
		}

		return ResponseEntity.ok().body(response);

	}

	@PostMapping("/login")
	ResponseEntity<?> validateAdmin(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		Map<String, String> response = new HashMap<String, String>();
		Admin result = null;
		if (null != json.get(ServiceConstants.EMAIL_ID) && null != json.get(ServiceConstants.PWD)) {
			log.info("Request to validate admin: {}", json.get(ServiceConstants.EMAIL_ID));
			String emailId = json.get(ServiceConstants.EMAIL_ID), pwd = json.get(ServiceConstants.PWD);
			
			result = adminService.loginAdmin(emailId, pwd);
			if (null != json.get(ServiceConstants.DEVICE) && json.get(ServiceConstants.DEVICE).length() > 10) {
				AdminDeviceID dvice = result.getAdminDeviceIDList().stream()
						.filter(ad -> json.get(ServiceConstants.DEVICE).equals(ad.getDeviceId())).findAny()
						.orElse(null);
				if (null == dvice) {
					dvice = new AdminDeviceID();
					dvice.setAdmin(result);
					dvice.setDeviceId(json.get(ServiceConstants.DEVICE));
					result.getAdminDeviceIDList().add(dvice);
				}
			}
		} else if (result == null && null != json.get(ServiceConstants.DEVICE)
				&& json.get(ServiceConstants.DEVICE).length() > 10) {
			result = adminService.validateAdminByDeviceId(json.get(ServiceConstants.DEVICE));
		}
		if (null != result) {
			result.setLastLoginDate(new Date());
			adminService.updateAdmin(result);
			result.setPwd(Strings.EMPTY);
			result.setAdminDeviceIDList(null);
			return ResponseEntity.ok().body(result);
		} else if(adminService.checkAdminByEmailId(json.get(ServiceConstants.EMAIL_ID))){
			Admin deactiveAdmin = adminService.getDeactiveAdmin(json.get(ServiceConstants.EMAIL_ID), json.get(ServiceConstants.PWD));
			if(null != deactiveAdmin) {
				response.put("status", String.valueOf(true));
				response.put("registrationStatus", String.valueOf(deactiveAdmin.getRegistrationStatus()));
				response.put("description", "User created but registration is not completed.");				
			} else {
				response.put("status", String.valueOf(false));
				response.put("description", "Invalid password.");				
			}
			
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.noContent().build();
		}

	}

	@PostMapping("/logout")
	ResponseEntity<?> logoutAdmin(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		Admin result = null;
		AdminDeviceID dvice = null;
		if (null != json.get(ServiceConstants.EMAIL_ID)) {
			log.info("Request to logout user: {}", json.get(ServiceConstants.EMAIL_ID));
			result = adminService.getAdminByEmailId(json.get(ServiceConstants.EMAIL_ID));
			if (null != json.get(ServiceConstants.DEVICE) && json.get(ServiceConstants.DEVICE).length() > 10) {
				dvice = result.getAdminDeviceIDList().stream()
						.filter(ad -> json.get(ServiceConstants.DEVICE).equals(ad.getDeviceId())).findAny()
						.orElse(null);
				if (null != dvice) {
					adminService.deleteAdminDevice(dvice);
				}
			}
		}
		if (null != result && null != dvice) {
			return ResponseEntity.ok().body("Logout successfully");
		} else {
			return ResponseEntity.noContent().build();
		}

	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAdmin(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		

		Admin admin = new Admin(json.get(ServiceConstants.MOBILE_NUMBER), json.get(ServiceConstants.PWD));
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_ADDRESS");
		int registrationStatus = lookUp.getLookUpId();
		
		

//			admin.setShopId(json.get(ServiceConstants.SHOP_ID));
		admin.setFirstName(json.get(ServiceConstants.F_NAME));
		admin.setLastName(json.get(ServiceConstants.L_NAME));
//			admin.setAvatar(json.get(ServiceConstants.AVATAR));
		if (null != json.get(ServiceConstants.ADHAR_NUM)) {
			admin.setAdharNumber((json.get(ServiceConstants.ADHAR_NUM)));
		}
		if (null != json.get(ServiceConstants.PAN_NUM)) {
			admin.setPanNumber(json.get(ServiceConstants.PAN_NUM));
		}
		admin.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));

//			String createron = json.get(ServiceConstants.CREATED_ON).toString();	
//			LocalDate localdate = LocalDate.parse(createron);
//			ZoneId defaultZoneId = ZoneId.systemDefault();
//			Date date = Date.from(localdate.atStartOfDay(defaultZoneId).toInstant());
//			admin.setCreatedOn(date);
//			

		admin.setPwd(new String(new Base64().encode(json.get(ServiceConstants.PWD).getBytes())));
//			admin. setPaymentStatus(Boolean.parseBoolean((json.get(ServiceConstants. CREDIT))));
		admin.setShopName(json.get(ServiceConstants.SHOP_NAME));
		admin.setShopType(Integer.parseInt(json.get(ServiceConstants.SHOP_TYPE)));
//			admin.setEmailId(json.get(ServiceConstants.EMAIL_ID));
		admin.setActive(Boolean.TRUE);
		admin.setDeleted(Boolean.FALSE);
		admin.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
		admin.setLogedIn(Boolean.FALSE);
//			admin.setOtp(json.get(ServiceConstants.OTP));
		admin.setCreatedOn(new Date());
		admin.setDescription(json.get(ServiceConstants.DESCRIPTION));
		if (null != json.get(ServiceConstants.GST_NUMBER)) {
			admin.setGstNumber(json.get(ServiceConstants.GST_NUMBER));
		}
		admin.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
//			admin.setValidity(Integer.parseInt(json.get(ServiceConstants.VALIDITY)));
		admin.setGender(Integer.parseInt(json.get(ServiceConstants.GENDER)));
		admin.setPlanPurchasedOn(new Date());
		admin.setValidityExpiryDate( LocalDateTime.now());
		admin.setValidityUpdatedOn(new Date());

		response.put("mobileNo", json.get(ServiceConstants.MOBILE_NUMBER));
		if (adminService.adminExists(admin.getMobileNo())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Admin already exist with given mobile number");
		} else {
			boolean result = adminService.createAdmin(admin);

			if (result && adminService.adminExists(admin.getMobileNo())) {
				Admin shop = adminService.getByMobileNumber(admin.getMobileNo());
				String emailId = admin.getFirstName().toLowerCase() + shop.getAdminId() + "@milaan.com",
						shopId = "MILAAN" + admin.getShopType() + shop.getAdminId();

				shop.setEmailId(emailId);
				shop.setShopId(shopId);
				shop.setRegistrationStatus(registrationStatus);

				adminService.updateAdmin(shop);

				int adminId = shop.getAdminId();

				response.put("adminId", Strings.EMPTY + adminId);
				response.put("emailId", Strings.EMPTY + shop.getEmailId());
				response.put("shopId", Strings.EMPTY + shop.getShopId());
				response.put("description",
						"Admin created successfully with given account, go through your inbox to activate");

			}
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Admin created successfully with given account, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}
	
	


}
