package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Account;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.Vender;
import com.shop.shopservice.service.IAccountService;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.IVenderService;

@RestController
@RequestMapping("/api/vender")
public class VenderController {
	private final Logger log = LoggerFactory.getLogger(VenderController.class);
	@Autowired
	private IVenderService venderService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private ISlotService slotService;
	LocalData localData = new LocalData();
	@GetMapping("getall")
	public ResponseEntity<List<Vender>> getAll() {
		List<Vender> venderList = venderService.getAll();
		return new ResponseEntity<List<Vender>>(venderList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Vender> getById(@PathVariable("id") int id) {
		Vender vender = venderService.getById(id);
		List<Slot> slotList = slotService.getByVenderId(id);
		for (int i = 0; i < slotList.size(); i++) {
		vender.setSlotList(slotList);
		}
		return new ResponseEntity<Vender>(vender, HttpStatus.OK);
	}

	@GetMapping("getbymobilenumber/{mobileNo}")
	public ResponseEntity<Vender> getByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		Vender vender = venderService.getByMobileNumber(mobileNo);
		int venderId = vender.getId();
		List<Slot> slotList = slotService.getByVenderId(venderId);
		for (int i = 0; i < slotList.size(); i++) {
			vender.setSlotList(slotList);
		}
		return new ResponseEntity<Vender>(vender, HttpStatus.OK);
	}

	@GetMapping("getdeactive/{id}")
	public ResponseEntity<Vender> getDeActiveById(@PathVariable("id") int id) {
		Vender vender = venderService.getById(id);
		vender.setDeleted(Boolean.TRUE);
		vender.setActive(Boolean.FALSE);
		venderService.updateVender(vender);
		return new ResponseEntity<Vender>(vender, HttpStatus.OK);
	}
	
	@GetMapping("check/{shopId}/{id}")
	public ResponseEntity<Map<String, String>> getAllDetaisById(@PathVariable("shopId") String shopId, @PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		boolean result = venderService.checkExits(shopId, id);
		if(result) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Vender created");
		}else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Vender not created");
		}
		return ResponseEntity.ok().body(response);
	}
	

	@GetMapping("getalldetails/{id}")
	public ResponseEntity<Vender> getAllDetaisById(@PathVariable("id") int id) {
		Vender vender = venderService.getAllDetaisById(id);
		int accountId = vender.getAccountId();
		Account account = accountService.getByVenderId(accountId);
		vender.setAccount(account);
      
		return new ResponseEntity<Vender>(vender, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Vender>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Vender> venderListLocal = localData.getVenderList();
		if(venderListLocal.size() <= 0) {
			List<Vender> venderList = venderService.getByShopId(shopId);
			venderListLocal = localData.setVenderList(venderList);
		}
		return new ResponseEntity<List<Vender>>(venderListLocal, HttpStatus.OK);
	}
	
	@GetMapping("shopidandslotnumber/{shopId}/{slotNumber}")
	public ResponseEntity<Vender> getByShopIdAndSlotNumber(@PathVariable("shopId") String shopId, @PathVariable("slotNumber") int slotNumber) {
	     Slot slotList = slotService.getSlotNumberAndShopId(slotNumber, shopId);	   
		Vender vender = venderService.getById(slotList.getVenderId());
		return new ResponseEntity<Vender>(vender, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createWithdraw(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.COMPANY_NAME) && null != json.get(ServiceConstants.MOBILE_NUMBER) && null != json.get(ServiceConstants.SHOP_ID)) {
			Vender vender = new Vender();
			
			vender.setCompanyName(json.get(ServiceConstants.COMPANY_NAME));
			vender.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			vender.setGstType(Integer.parseInt(json.get(ServiceConstants.GST_TYPE)));
			vender.setAddress(json.get(ServiceConstants.ADDRESS));
			vender.setBusinessCategory(Integer.parseInt(json.get(ServiceConstants.BUSINESS_CATEGORY)));
			vender.setShopId(json.get(ServiceConstants.SHOP_ID));
			vender.setActive(Boolean.TRUE);
			vender.setCreatedOn(new Date());
			vender.setDeleted(Boolean.FALSE);
			if (null != json.get(ServiceConstants.F_NAME)) {
				vender.setFristName(json.get(ServiceConstants.F_NAME)); 
			}
			if (null != json.get(ServiceConstants.CITY)) {
				vender.setCity(json.get(ServiceConstants.CITY));
			}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				vender.setCountry(json.get(ServiceConstants.COUNTRY));
			}
			if (null != json.get(ServiceConstants.DESCRIPTION)) {
				vender.setDescription(json.get(ServiceConstants.DESCRIPTION));
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				vender.setDistrict(json.get(ServiceConstants.DISTRICT));
			}
			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				vender.setAdharNumber(json.get(ServiceConstants.ADHAR_NUM));
			}
			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				vender.setEmailId(json.get(ServiceConstants.EMAIL_ID));
			}
			if (null != json.get(ServiceConstants.LAND_MARK)) {
				vender.setLandMark(json.get(ServiceConstants.LAND_MARK));
			}
			if (null != json.get(ServiceConstants.L_NAME)) {
				vender.setLastName(json.get(ServiceConstants.L_NAME));
			}
			if (null != json.get(ServiceConstants.PIN_CODE) && json.get(ServiceConstants.PIN_CODE) != "") {
				vender.setPinCode(Integer.parseInt(json.get(ServiceConstants.PIN_CODE)));
			}
			if (null != json.get(ServiceConstants.PAN_NUM)) {
				vender.setPanNumber(json.get(ServiceConstants.PAN_NUM));
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				vender.setPoliceStation(json.get(ServiceConstants.POLICE_STATION));
			}
			if (null != json.get(ServiceConstants.STATE)) {
				vender.setState(json.get(ServiceConstants.STATE));
			}
			if (null != json.get(ServiceConstants.GST_NUMBER)) {
				vender.setGstNumber(json.get(ServiceConstants.GST_NUMBER));
			}
			if (null != json.get(ServiceConstants.PURCHASE_AMOUNT)) {
				vender.setPurchaseAmount(Integer.parseInt(json.get(ServiceConstants.PURCHASE_AMOUNT)));
			}
			
			if(null != json.get(ServiceConstants.DUES)) {
				vender.setDues(Float.parseFloat(json.get(ServiceConstants.DUES)));
			}
			
			if(null != json.get(ServiceConstants.PAID)) {
				vender.setPaid(Float.parseFloat(json.get(ServiceConstants.PAID)));
			}

			if (venderService.venderExits(vender.getShopId(),vender.getMobileNo())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("Mobile number", vender.getMobileNo());
				response.put("description", "All ready created");
			} else {
				venderService.createVender(vender);
				localData.setVenderList( new ArrayList<Vender>());
				response.put("status", Boolean.TRUE.toString());
				response.put("venderId", String.valueOf(vender.getId()));
				response.put("Name", vender.getCompanyName());
				response.put("description", "Vender created");
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != venderService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Vender vender = venderService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			if (null != json.get(ServiceConstants.F_NAME)) {
				String fName = json.get(ServiceConstants.F_NAME);
				vender.setFristName(fName);
			}

			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = json.get(ServiceConstants.ADHAR_NUM);
				vender.setAdharNumber(adharNumber);
			}

			if (null != json.get(ServiceConstants.IGST_AMOUNT)) {
				float igstAmount = Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT));
				vender.setIgstAmount(igstAmount);
			}

			if (null != json.get(ServiceConstants.SGST_AMOUNT)) {
				float sgstAmount = Float.parseFloat(json.get(ServiceConstants.SGST_AMOUNT));
				vender.setSgstAmount(sgstAmount);
			}

			if (null != json.get(ServiceConstants.CGST_AMOUNT)) {
				float cgstAmount = Float.parseFloat(json.get(ServiceConstants.CGST_AMOUNT));
				vender.setCgstAmount(cgstAmount);
			}

			if (null != json.get(ServiceConstants.UTGST_AMOUNT)) {
				float utgstAmount = Float.parseFloat(json.get(ServiceConstants.UTGST_AMOUNT));
				vender.setUtgstAmount(utgstAmount);
			}

			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				vender.setCity(city);
			}
			
			if (null != json.get(ServiceConstants.ADDRESS)) {
				String address = json.get(ServiceConstants.ADDRESS);
				vender.setAddress(address);
			}

			if (null != json.get(ServiceConstants.COMPANY_NAME)) {
				String companyName = json.get(ServiceConstants.COMPANY_NAME);
				vender.setCompanyName(companyName);
			}

			if (null != json.get(ServiceConstants.DISCREPTION)) {
				String description = json.get(ServiceConstants.DISCREPTION);
				vender.setDescription(description);
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				vender.setDistrict(district);
			}

			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID);
				vender.setEmailId(emailId);
			}

			if (null != json.get(ServiceConstants.GST_NUMBER)) {
				String gstNumber = json.get(ServiceConstants.GST_NUMBER);
				vender.setGstNumber(gstNumber);
			}

			if (null != json.get(ServiceConstants.LAND_MARK)) {
				String landMark = json.get(ServiceConstants.LAND_MARK);
				vender.setLandMark(landMark);
			}

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME);
				vender.setLastName(lastName);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				vender.setMobileNo(mobileNo);
			}

			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM);
				vender.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				vender.setPoliceStation(policeStation);
			}

			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				vender.setPostOffice(postOffice);
			}

			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				vender.setState(state);
			}

			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pinCode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				vender.setPinCode(pinCode);
			}

			if (null != json.get(ServiceConstants.DUES)) {
				float dues = Float.parseFloat(json.get(ServiceConstants.DUES));
				vender.setDues(dues);
			}

			if (null != json.get(ServiceConstants.PAID)) {
				float paid = Float.parseFloat(json.get(ServiceConstants.PAID));
				vender.setPaid(paid);
			}

			boolean update = venderService.updateVender(vender);
			if (update) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Vender update");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Vender not update");
			}
		}
		return ResponseEntity.ok().body(response);
	}

}
