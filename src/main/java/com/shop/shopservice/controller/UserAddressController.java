package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.shop.shopservice.Idao.IUserAddressDAO;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IUserAddressService;

@RestController
@RequestMapping("/api/useraddress")
public class UserAddressController {
	private final Logger log = LoggerFactory.getLogger(UserAddressController.class);

	@Autowired
	IUserAddressService userAddressService;

	@Autowired
	IUserAddressDAO userAddressDao;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private ILookUp lookup;

	@GetMapping("getalluseraddress")
	public ResponseEntity<List<UserAddress>> getAllUserAddress() {
		List<UserAddress> userAddressList = userAddressService.getAllUserAddress();
		return new ResponseEntity<List<UserAddress>>(userAddressList, HttpStatus.OK);
	}

	@GetMapping("getbyuserid/{userId}")
	public ResponseEntity<List<UserAddress>> getAddressByUserId(@PathVariable("userId") String userId) {
		List<UserAddress> userAddressList = userAddressService.getAddressByUserId(userId);
		return new ResponseEntity<List<UserAddress>>(userAddressList, HttpStatus.OK);
	}

	@GetMapping("getuseraddressbyshopid/{shopId}")
	public ResponseEntity<List<UserAddress>> getAddressByShopId(@PathVariable("shopId") String shopId) {
		List<UserAddress> userAddressList = userAddressService.getAddressByShopId(shopId);
		return new ResponseEntity<List<UserAddress>>(userAddressList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		userAddressDao.indexUserAddress();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<UserAddress>> searchAllUserBykeyword(@PathVariable("keyword") String keyword) {
		List<UserAddress> result = new ArrayList<UserAddress>();
		result = userAddressDao.searchUserAddress(keyword);
		return new ResponseEntity<List<UserAddress>>(result, HttpStatus.OK);
	}
	

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.USER_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.LATITUDE) && null != json.get(ServiceConstants.LONGITUDE)) {
			
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_IMAGE");
			int registrationStatus = lookUp.getLookUpId();

			UserAddress userAddress = new UserAddress(json.get(ServiceConstants.USER_ID),
					json.get(ServiceConstants.SHOP_ID));

			userAddress.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
			userAddress.setUserId(json.get(ServiceConstants.USER_ID));
			userAddress.setState(json.get(ServiceConstants.STATE));
			userAddress.setShopId(json.get(ServiceConstants.SHOP_ID));
			userAddress.setPinCode(Integer.parseInt(json.get(ServiceConstants.PIN_CODE)));
			userAddress.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			userAddress.setLongitude(json.get(ServiceConstants.LONGITUDE));
			userAddress.setLatitude(json.get(ServiceConstants.LATITUDE));
			userAddress.setDistrict(json.get(ServiceConstants.DISTRICT));
			userAddress.setDeleted(Boolean.FALSE);
			userAddress.setCreatedOn(new Date());
			userAddress.setCity(json.get(ServiceConstants.CITY));
			userAddress.setActive(Boolean.TRUE);
			userAddress.setName(json.get(ServiceConstants.NAME));

			if (null != json.get(ServiceConstants.STREET)) {
				userAddress.setStreet(json.get(ServiceConstants.STREET));
			}
			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				userAddress.setPostOffice(json.get(ServiceConstants.POST_OFFICE));
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				userAddress.setPoliceStation(json.get(ServiceConstants.POLICE_STATION));
			}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				userAddress.setCountry(json.get(ServiceConstants.COUNTRY));
			}
			if (null != json.get(ServiceConstants.LAND_MARK)) {
				userAddress.setLandmark(json.get(ServiceConstants.LAND_MARK));
			}

			Admin admin = adminService.getByShopId(userAddress.getShopId());
			admin.setLatitude(userAddress.getLatitude());
			admin.setLongitude(userAddress.getLongitude());
			admin.setRegistrationStatus(registrationStatus);
			adminService.updateAdmin(admin);

			response.put("userId", json.get(ServiceConstants.USER_ID));
			if (userAddressService.userAddressExists(userAddress.getUserId())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Account already exist with given userId");
			} else {
				boolean result = userAddressService.createUserAddress(userAddress);
				response.put("status", Strings.EMPTY + result);
				response.put("description",
						"UserAddress created successfully with given userAddress, go through your inbox to activate");
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateUserAddress(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != userAddressService.getUserAddress(json.get(ServiceConstants.USER_ID))) {
			UserAddress userAddress = userAddressService.getUserAddress(json.get(ServiceConstants.USER_ID));

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				System.out.println(userType);
				userAddress.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				System.out.println(state);
				userAddress.setState(state);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				userAddress.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				System.out.println(postOffice);
				userAddress.setPostOffice(postOffice);
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				System.out.println(policeStation);
				userAddress.setPoliceStation(policeStation);
			}
			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pincode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				System.out.println(pincode);
				userAddress.setPinCode(pincode);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				System.out.println(mobileNo);
				userAddress.setMobileNo(mobileNo);

			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				System.out.println(isActive);
				userAddress.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.LONGITUDE)) {
				String longitude = json.get(ServiceConstants.LONGITUDE);
				System.out.println(longitude);
				userAddress.setLongitude(longitude);
			}
			if (null != json.get(ServiceConstants.LATITUDE)) {
				String latitude = json.get(ServiceConstants.LATITUDE);
				System.out.println(latitude);
				userAddress.setLatitude(latitude);
			}
			if (null != json.get(ServiceConstants.LAND_MARK)) {
				String landmark = json.get(ServiceConstants.LAND_MARK);
				System.out.println(landmark);
				userAddress.setLandmark(landmark);
			}
			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				System.out.println(district);
				userAddress.setDistrict(district);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				System.out.println(isDeleted);
				userAddress.setDeleted(isDeleted);
			}
//			if(null != json.get(ServiceConstants.CREATED_ON)) {
//				Date createdOn =new Date();
//				System.out.println(createdOn);
//				address.setCreatedOn(createdOn);				
//				}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				System.out.println(country);
				userAddress.setCountry(country);
			}

			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				System.out.println(city);
				userAddress.setCity(city);
			}

			if (null != json.get(ServiceConstants.STREET)) {
				String street = json.get(ServiceConstants.STREET);
				System.out.println(street);
				userAddress.setStreet(street);

			}

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				System.out.println(name);
				userAddress.setName(name);

			}

			userAddressService.updateUserAddress(userAddress);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "UserAddress updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "UserAddress doesn't exist with given shopId or userid");
		}

		return ResponseEntity.ok().body(response);

	}
}
