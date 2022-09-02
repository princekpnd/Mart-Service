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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.IAddressDAO;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.service.IAddressService;
import com.shop.shopservice.service.IAdminService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IAddressDAO addressDao;

	private final Logger log = LoggerFactory.getLogger(AddressController.class);

	@GetMapping("getalladdress")
	public ResponseEntity<List<Address>> getAllAdderss() {
		List<Address> addressList = addressService.getAllAddress();
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);

	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable("id") Integer id){
	        Address address = addressService.getAddress(id.intValue());
	        return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

	@GetMapping("getby/userid/{userId}")
	public ResponseEntity<List<Address>> getAllAddressByUserId(@PathVariable("userId") String userId) {
		List<Address> addressList = addressService.getAllAddressByUserId(userId);
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
	}
	


	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Address>> getAllAddressByShopId(@PathVariable("shopId") String shopId) {
		List<Address> addressList = addressService.getAllAddressByShopId(shopId);
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
	}
	
	@GetMapping("getdefaultaddress/{userId}")
	public ResponseEntity<Address> getDefaultAddress(@PathVariable("userId")  String userId){
		Address address = addressService.getDefaultAddress(userId);
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	

	
	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		addressDao.indexAddress();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Address>> searchAllAddressBykeyword(@PathVariable("keyword") String keyword) {
		List<Address> result = new ArrayList<Address>();
		result = addressDao.searchAddress(keyword);
		return new ResponseEntity<List<Address>>(result, HttpStatus.OK);
	}
	
	@GetMapping("change/defaultaddress/{userId}/{id}")
	public ResponseEntity<Address> changeDefaultAddress(@PathVariable("userId")  String userId, @PathVariable("id")  String id){
		Address address = addressService.getDefaultAddress(userId);
		Address address1 = addressService.getAddress(Integer.parseInt(id));
		if (null != address && null != address1) {
			address.setDefaultAddress(Boolean.FALSE);
			addressService.updateAddress(address);
			address1.setDefaultAddress(Boolean.TRUE);
			addressService.updateAddress(address1);
		}
		return new ResponseEntity<Address>(address1, HttpStatus.OK);
	}
	
	@GetMapping("getbyuseridandshopid/{userId}/{shopId}")
	public ResponseEntity<List<Address>> getAddressByUserIdAndShopId(@PathVariable("userId") String userId,@PathVariable("shopId") String shopId){
		List<Address> addressList = addressService.getAddressByUserIdAndShopId(userId,shopId);
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
	}

	@GetMapping("getactivebyshopid/{shopId}")
	public ResponseEntity<List<Address>> getAddressByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Address> addressList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			addressList = addressService.getAddressByShopId(shopId);
		}
		return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAttendance(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.CITY));
		Map<String, String> response = new HashMap<String, String>();
		Address address = new Address(json.get(ServiceConstants.SHOP_ID), (json.get(ServiceConstants.USER_ID)));

		address.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
		address.setUserId(json.get(ServiceConstants.USER_ID));
		address.setState(json.get(ServiceConstants.STATE));
		address.setShopId(json.get(ServiceConstants.SHOP_ID));
		address.setPinCode(Integer.parseInt(json.get(ServiceConstants.PIN_CODE)));
		address.setCountry(json.get(ServiceConstants.COUNTRY));
		address.setCity(json.get(ServiceConstants.CITY));
		address.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
		address.setDistrict(json.get(ServiceConstants.DISTRICT));
		address.setStreet(json.get(ServiceConstants.STREET));
		address.setActive(Boolean.TRUE);
		address.setDeleted(Boolean.FALSE);
		address.setCreatedOn(new Date());
		address.setName(json.get(ServiceConstants.NAME));
		address.setDefaultAddress(Boolean.TRUE);
		
		if(null != json.get(ServiceConstants.POST_OFFICE)) {
			address.setPostOffice(json.get(ServiceConstants.POST_OFFICE));
		}
		if(null != json.get(ServiceConstants.POLICE_STATION)) {
			address.setPoliceStation(json.get(ServiceConstants.POLICE_STATION));
		}
		
		if (null != json.get(ServiceConstants.LONGITUDE)) {
			address.setLongitude(json.get(ServiceConstants.LONGITUDE));
		}

		if (null != json.get(ServiceConstants.LATITUDE)) {
			address.setLatitude(json.get(ServiceConstants.LATITUDE));
		}
		if(null != json.get(ServiceConstants.LAND_MARK)) {
			address.setLandmark(json.get(ServiceConstants.LAND_MARK));
		}
		response.put("userId", json.get(ServiceConstants.USER_ID));
		if (addressService.addressExists(address.getUserId())) {
			Address address2 = addressService.getDefaultAddress(json.get(ServiceConstants.USER_ID));
			address2.setDefaultAddress(Boolean.FALSE);
			addressService.updateAddress(address2);
			boolean result = addressService.createAddress(address);
			response.put("status", Strings.EMPTY + result);
			response.put("description", "Address created successfully with given userId.");
		} else {
			boolean result = addressService.createAddress(address);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Address created successfully with given userId.");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAddress(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != addressService.getAddress(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Address address = addressService.getAddress(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				System.out.println(userType);
				address.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				System.out.println(state);
				address.setState(state);
			}
			
			if (null != json.get(ServiceConstants.STREET)) {
				String street = json.get(ServiceConstants.STREET);
				System.out.println(street);
				address.setStreet(street);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				address.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				System.out.println(postOffice);
				address.setPostOffice(postOffice);
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				System.out.println(policeStation);
				address.setPoliceStation(policeStation);
			}
			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pincode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				System.out.println(pincode);
				address.setPinCode(pincode);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				System.out.println(mobileNo);
				address.setMobileNo(mobileNo);

			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				System.out.println(isActive);
				address.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.LONGITUDE)) {
				String longitude = json.get(ServiceConstants.LONGITUDE);
				System.out.println(longitude);
				address.setLongitude(longitude);
			}
			if (null != json.get(ServiceConstants.LATITUDE)) {
				String latitude = json.get(ServiceConstants.LATITUDE);
				System.out.println(latitude);
				address.setLatitude(latitude);
			}
			if (null != json.get(ServiceConstants.LAND_MARK)) {
				String landmark = json.get(ServiceConstants.LAND_MARK);
				System.out.println(landmark);
				address.setLandmark(landmark);
			}
			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				System.out.println(district);
				address.setDistrict(district);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				System.out.println(isDeleted);
				address.setDeleted(isDeleted);
			}
//			if(null != json.get(ServiceConstants.CREATED_ON)) {
//				Date createdOn =new Date();
//				System.out.println(createdOn);
//				address.setCreatedOn(createdOn);				
//				}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				System.out.println(country);
				address.setCountry(country);
			}

			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				System.out.println(city);
				address.setCity(city);
			}
			
			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				System.out.println(name);
				address.setName(name);
			}
			
			if(null != json.get(ServiceConstants.DEFAULT_ADDRESS)) {
				boolean defaultAddress = Boolean.parseBoolean(json.get(ServiceConstants.DEFAULT_ADDRESS).toString());
				address.setDefaultAddress(defaultAddress);
			}
			addressService.updateAddress(address);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Address updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Address doesn't exist with given shopId or userid");
		}

		return ResponseEntity.ok().body(response);

	}
	

	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteAddress(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Address address = addressService.getAddress(id);
		
		if(null != address) {
			boolean result = addressService.deleteAddress(id);
			if(result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description","Address delete with given id:" +id);
			}
			}else {
				response.put("status",Boolean.FALSE.toString());
				response.put("description", "Address does not exist with given address Id");
			
		}
		return ResponseEntity.ok().body(response);
	}

}
