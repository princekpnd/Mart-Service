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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.EmployeeAddress;
import com.shop.shopservice.service.IEmployeeAddressService;

@RestController
@RequestMapping("/api/employeeaddress")

public class EmployeeAddressController {

	private final Logger log = LoggerFactory.getLogger(EmployeeAddressController.class);
	@Autowired
	private IEmployeeAddressService employeeAddressService;

	@GetMapping("getallemployeeaddress")
	public ResponseEntity<List<EmployeeAddress>> getAllEmployeeAddress() {
		List<EmployeeAddress> employeeAddressList = employeeAddressService.getAllEmployeeAddress();
		return new ResponseEntity<List<EmployeeAddress>>(employeeAddressList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<EmployeeAddress> getById(@PathVariable("id") Integer id) {
		EmployeeAddress employeeAddress = employeeAddressService.getById(id.intValue());
		return new ResponseEntity<EmployeeAddress>(employeeAddress, HttpStatus.OK);
	}

	@GetMapping("getaddressbyshopid/{shopId}")
	public ResponseEntity<List<EmployeeAddress>> getAddressByShopId(@PathVariable("shopId") String shopId) {
		List<EmployeeAddress> employeeAddressList = employeeAddressService.getAddressByShopId(shopId);
		return new ResponseEntity<List<EmployeeAddress>>(employeeAddressList, HttpStatus.OK);
	}

//	@GetMapping("getaddressbyemployeeid/{employeeId}")
//	public ResponseEntity<List<EmployeeAddress>> getAddressByEmployeeId(@PathVariable("employeeId") String employeeId) {
//		List<EmployeeAddress> employeeAddressList = employeeAddressService.getAddressByEmployeeId(employeeId);
//		return new ResponseEntity<List<EmployeeAddress>>(employeeAddressList, HttpStatus.OK);
//	}
	
	@GetMapping("getaddressbyemployeeid/{employeeId}")
	public ResponseEntity<List<EmployeeAddress>> getAddressByEmployeeId4(@PathVariable("employeeId") String employeeId) {
		List<EmployeeAddress> employeeAddressList = employeeAddressService.getAddressByEmployeeId(employeeId);
		return new ResponseEntity<List<EmployeeAddress>>(employeeAddressList, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAttendance(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.CITY));
		Map<String, String> response = new HashMap<String, String>();
		EmployeeAddress employeeAddress = new EmployeeAddress((json.get(ServiceConstants.SHOP_ID)),
				(json.get(ServiceConstants.EMPLOYEE_ID)));

		employeeAddress.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
		employeeAddress.setEmployeeId(json.get(ServiceConstants.EMPLOYEE_ID));
		employeeAddress.setCountry(json.get(ServiceConstants.COUNTRY));
		employeeAddress.setCity(json.get(ServiceConstants.CITY));
		employeeAddress.setState(json.get(ServiceConstants.STATE));
		employeeAddress.setShopId(json.get(ServiceConstants.SHOP_ID));
		employeeAddress.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
		employeeAddress.setActive(Boolean.TRUE);
		employeeAddress.setDeleted(Boolean.FALSE);
		employeeAddress.setCreatedOn(new Date());

		if (null != json.get(ServiceConstants.POST_OFFICE)) {
			employeeAddress.setPostOffice(json.get(ServiceConstants.POST_OFFICE));
		}
		if (null != json.get(ServiceConstants.POLICE_STATION)) {
			employeeAddress.setPoliceStation(json.get(ServiceConstants.POLICE_STATION));
		}
		if (null != json.get(ServiceConstants.PIN_CODE)) {
			employeeAddress.setPinCode(Integer.parseInt(json.get(ServiceConstants.PIN_CODE)));
		}
		if (null != json.get(ServiceConstants.LONGITUDE)) {
			employeeAddress.setLongitude(json.get(ServiceConstants.LONGITUDE));
		}
		if (null != json.get(ServiceConstants.LATITUDE)) {
			employeeAddress.setLatitude(json.get(ServiceConstants.LATITUDE));
		}
		if (null != json.get(ServiceConstants.LAND_MARK)) {
			employeeAddress.setLandmark(json.get(ServiceConstants.LAND_MARK));
		}
		if (null != json.get(ServiceConstants.DISTRICT)) {
			employeeAddress.setDistrict(json.get(ServiceConstants.DISTRICT));
		}
		if (null != json.get(ServiceConstants.STREET)) {
			employeeAddress.setStreet(json.get(ServiceConstants.STREET));
		}
		if (null != json.get(ServiceConstants.NAME)) {
			employeeAddress.setName(json.get(ServiceConstants.NAME));
		}

		response.put("employeeId", json.get(ServiceConstants.EMPLOYEE_ID));
		if (employeeAddressService.employeeAddressExists(employeeAddress.getEmployeeId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Employee address already exist with given employeeId");
		} else {
			boolean result = employeeAddressService.createEmployeeAddress(employeeAddress);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Address created successfully with given userId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAddress(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != employeeAddressService.getEmployeeAddress(json.get(ServiceConstants.EMPLOYEE_ID))) {
			EmployeeAddress employeeAddress = employeeAddressService
					.getEmployeeAddress(json.get(ServiceConstants.EMPLOYEE_ID));

			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				System.out.println(userType);
				employeeAddress.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				System.out.println(state);
				employeeAddress.setState(state);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				employeeAddress.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				System.out.println(postOffice);
				employeeAddress.setPostOffice(postOffice);
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				System.out.println(policeStation);
				employeeAddress.setPoliceStation(policeStation);
			}
			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pincode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				System.out.println(pincode);
				employeeAddress.setPinCode(pincode);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				System.out.println(mobileNo);
				employeeAddress.setMobileNo(mobileNo);

			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				System.out.println(isActive);
				employeeAddress.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.LONGITUDE)) {
				String longitude = json.get(ServiceConstants.LONGITUDE);
				System.out.println(longitude);
				employeeAddress.setLongitude(longitude);
			}
			if (null != json.get(ServiceConstants.LATITUDE)) {
				String latitude = json.get(ServiceConstants.LATITUDE);
				System.out.println(latitude);
				employeeAddress.setLatitude(latitude);
			}
			if (null != json.get(ServiceConstants.LAND_MARK)) {
				String landmark = json.get(ServiceConstants.LAND_MARK);
				System.out.println(landmark);
				employeeAddress.setLandmark(landmark);
			}
			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				System.out.println(district);
				employeeAddress.setDistrict(district);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				System.out.println(isDeleted);
				employeeAddress.setDeleted(isDeleted);
			}
//			if(null != json.get(ServiceConstants.CREATED_ON)) {
//				Date createdOn =new Date();
//				System.out.println(createdOn);
//				employeeAddress.setCreatedOn(createdOn);				
//				}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				System.out.println(country);
				employeeAddress.setCountry(country);
			}

			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				System.out.println(city);
				employeeAddress.setCity(city);
			}

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				System.out.println(name);
				employeeAddress.setName(name);
			}

			if (null != json.get(ServiceConstants.STREET)) {
				String street = json.get(ServiceConstants.STREET);
				System.out.println(street);
				employeeAddress.setStreet(street);
			}

			employeeAddressService.updateEmployeeAddress(employeeAddress);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Employee address updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Employee address doesn't exist with given  employeeid");
		}

		return ResponseEntity.ok().body(response);

	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		EmployeeAddress employeeAddress = employeeAddressService.getById(id);
		// Employee employee = null;
		// boolean employee = employeeService.deleteEmployee(id);
//		if(null != employee)
		if (null != employeeAddress) {
			boolean result = employeeAddressService.deleteEmployeeAddress(id);
			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "EmployeeAddress delete with given employeeId:" + id);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "EmployeeAddress does not exist with given Employee Id");

		}
		return ResponseEntity.ok().body(response);
	}

}
