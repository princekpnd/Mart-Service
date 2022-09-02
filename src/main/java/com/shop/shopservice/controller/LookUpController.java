   package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.utils.PropertyBundle;

@RestController
@RequestMapping("/api/lookup")
public class LookUpController {

	@Autowired
	private ILookUp lookup;
	
	@Autowired
	private ILookUpType lookUpType;
	
	@Autowired
	private ILookUp lookUpDao;
	
	@GetMapping("getalllanguage")
	public ResponseEntity<List<LookUp>> findAllLanguage() {
		List<LookUp> languageList = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("LANGUAGE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(languageList, HttpStatus.OK);
	}
	
	@GetMapping("getallslottype")
	public ResponseEntity<List<LookUp>> findAllSlotType() {
		List<LookUp> languageList = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("SLOT_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(languageList, HttpStatus.OK);
	}
	
	
	@GetMapping("getallusertype")
	public ResponseEntity<List<LookUp>> findAllUserType() {
		List<LookUp> userType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("USER_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(userType, HttpStatus.OK);
	}

	@GetMapping("getlookupbyname")
	public ResponseEntity<LookUp> findLookUpByName(@PathVariable("shopId") String shopId,
			@PathVariable("lookupName") String lookupName) {
		LookUp nameType = lookup.findLookUpIdByName(shopId, lookupName);
		return new ResponseEntity<LookUp>(nameType, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<LookUp> findLookUpById(@PathVariable("id") int id) {
		LookUp userType = lookup.findLookUpById(id);
		return new ResponseEntity<LookUp>(userType, HttpStatus.OK);
	}
	
	@GetMapping("getallshoptype")
	public ResponseEntity<List<LookUp>> findAllShopType() {
		List<LookUp> shopType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("SHOP_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(shopType, HttpStatus.OK);
	}
	
//	@GetMapping("getwithdrawstatus")
//	public ResponseEntity<List<LookUp>> findAllWithdrawStatus() {
//		List<LookUp> shopType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("WITHDRAW_STATUS").getLookUpTypeId());
//		return new ResponseEntity<List<LookUp>>(shopType, HttpStatus.OK);
//	}
	
	
	
	@GetMapping("getallwithdrawstatus")
	public ResponseEntity<List<LookUp>> findAllWithdrawStatus() {
		List<LookUp> withdrawStatus = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("WITHDRAW_STATUS").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(withdrawStatus, HttpStatus.OK);
	}
	
	@GetMapping("getallexpectedtime")
	public ResponseEntity<List<LookUp>> findAllExpectedTime() {
		List<LookUp> withdrawStatus = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("EXPECTED_TIME").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(withdrawStatus, HttpStatus.OK);
	}
	
	@GetMapping("getallofflinepaymentmode")
	public ResponseEntity<List<LookUp>> findAllPaymentMode() {
		List<LookUp> withdrawStatus = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("PAYMENT_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(withdrawStatus, HttpStatus.OK);
	}
	
	@GetMapping("getallordertype")
	public ResponseEntity<List<LookUp>> findAllOrderType() {
		List<LookUp> shopType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("ORDER_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(shopType, HttpStatus.OK);
	}
	
	@GetMapping("getallgsttype")
	public ResponseEntity<List<LookUp>> findAllGstType() {
		List<LookUp> gstType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("GST_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(gstType, HttpStatus.OK);
	}
	
	@GetMapping("getallbusinesscategory")
	public ResponseEntity<List<LookUp>> findAllBusinessCategoryType() {
		List<LookUp> businessCategory = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("BUSINESS_CAREGORY").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(businessCategory, HttpStatus.OK);
	}
	
	@GetMapping("getallpaymenttype")
	public ResponseEntity<List<LookUp>> findAllPaymentType() {
		List<LookUp> paymentType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("PAYMENT_MODE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(paymentType, HttpStatus.OK);
	}
	
	@GetMapping("getalldeliverytype")
	public ResponseEntity<List<LookUp>> findAllDeliveryType() {
		List<LookUp> deliveryType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("DELIVERY_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(deliveryType, HttpStatus.OK);
	}
	
	@GetMapping("getallnotificationtype")
	public ResponseEntity<List<LookUp>> findAllNotificationType() {
		List<LookUp> notificationType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("NOTIFICATION_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(notificationType, HttpStatus.OK);
	}
	
	@GetMapping("getallregistrationstatus")
	public ResponseEntity<List<LookUp>> findAllRegistrationStatus() {
		List<LookUp> registrationStatus = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("REGISTRATION_STATUS").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(registrationStatus, HttpStatus.OK);
	}
	
	@GetMapping("getalltransactiontype")
	public ResponseEntity<List<LookUp>> findAllTransactionType() {
		List<LookUp> transactionType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("TRANSACTION_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(transactionType, HttpStatus.OK);
	}
	
	@GetMapping("getallreviewtype")
	public ResponseEntity<List<LookUp>> findAllReviewType() {
		List<LookUp> reviewType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("REVIEW_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(reviewType, HttpStatus.OK);
	}
	
	@GetMapping("getalltransactionstatus")
	public ResponseEntity<List<LookUp>> findAllTransactionStatus() {
		List<LookUp> paymentType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("TRANSACTION_STATUS").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(paymentType, HttpStatus.OK);
	}
	@GetMapping("getallmeasurmenttype")
	public ResponseEntity<List<LookUp>> findAllMeasurmentType() {
		List<LookUp> measurmentType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("MEASURMENT_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(measurmentType, HttpStatus.OK);
	}
	@GetMapping("getallorderstatus")
	public ResponseEntity<List<LookUp>> findAllOrderStatusType(){
		List<LookUp> orderStatusType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("ORDER_STATUS").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(orderStatusType , HttpStatus.OK);
	}
	@GetMapping("getallbrandtype")
	public ResponseEntity<List<LookUp>> findAllBrandType() {
		List<LookUp> brandType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("BRAND").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(brandType, HttpStatus.OK);
	}
	
	@GetMapping("getallmeasurementtype")
	public ResponseEntity<List<LookUp>> findAllMeasurementType() {
		List<LookUp> brandType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("MEASURMENT_TYPE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(brandType, HttpStatus.OK);
	}
	
	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		lookUpDao.indexLookUp();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("getallcatagory")
	public ResponseEntity<List<LookUp>> findAllCatagory() {
		List<LookUp> catagoryList = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("CATAGORY").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(catagoryList, HttpStatus.OK);
	}
	
	@GetMapping("getallgender")
	public ResponseEntity<List<LookUp>> findAllGender() {
		List<LookUp> genderList = lookup
				.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("GENDER").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(genderList, HttpStatus.OK);
	}


	@GetMapping("getallcatagoryforAdmin")
	public ResponseEntity<List<LookUp>> findAllCatagoryforAdmin() {
		List<LookUp> catagoryList = lookup.findLookUpByLookUpTypeForAdmin(lookUpType.findLookUpTypeByName("CATAGORY").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(catagoryList, HttpStatus.OK);
	}
	
	@GetMapping("getallattendance")
	public ResponseEntity<List<LookUp>> findAllAttendanceType() {
		List<LookUp> brandType = lookup.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("ATTENDANCE").getLookUpTypeId());
		return new ResponseEntity<List<LookUp>>(brandType, HttpStatus.OK);
	}
	
	@GetMapping("getallsubcatagory")
	public ResponseEntity<Map<Integer, List<LookUp>>> findAllSubCatagory() {
		Map<Integer, List<LookUp>> subcatagoryList = lookup.findAllCatagory("CATAGORY");
		return new ResponseEntity<Map<Integer, List<LookUp>>>(subcatagoryList, HttpStatus.OK);
	}
	
	@GetMapping("getalllookup")
	public ResponseEntity<Map<String, List<LookUp>>> findAllLookup() {
		Map<String, List<LookUp>> lookup = PropertyBundle.systemMap;
		return new ResponseEntity<Map<String, List<LookUp>>>(lookup, HttpStatus.OK);
	}
	
	@GetMapping("getalllookupforadmin")
	public ResponseEntity<Map<String, List<LookUp>>> findAllLookupForAdmin() {
		Map<String, List<LookUp>> lookup = PropertyBundle.adminSystemMap;
		return new ResponseEntity<Map<String, List<LookUp>>>(lookup, HttpStatus.OK);
	}
	
	@GetMapping("refreshalllookupforadmin")
	public ResponseEntity<String> refreshSystemMap() {
		lookUpDao.refresh();
		return new ResponseEntity<String>("Refreshed Map Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> UpdateLookup(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		if (null != json.get(ServiceConstants.LOOKUPID)) {
			LookUp lookUp = lookUpDao.findLookUpById(Integer.parseInt(json.get(ServiceConstants.LOOKUPID)));
			if(null != lookUp && null != json.get(ServiceConstants.ACTIVATE_IND)) {
				String activateInd = json.get(ServiceConstants.ACTIVATE_IND).toString();
				lookUp.setActive(Boolean.parseBoolean(activateInd));
			}
			if(null != lookUp && null != json.get(ServiceConstants.DELETED_IND)) {
				String deletedInd = json.get(ServiceConstants.DELETED_IND).toString();
				lookUp.setDeleted(Boolean.parseBoolean(deletedInd));
			}
			if(null != lookUp && null != json.get(ServiceConstants.LOOKUPLABEL)) {
				String lookupLabel = json.get(ServiceConstants.LOOKUPLABEL).toString();
				lookUp.setLookUpLabel(lookupLabel);
			}
			if(null != lookUp && null != json.get(ServiceConstants.LOOKUPNAME)) {
				String lookupName = json.get(ServiceConstants.LOOKUPNAME).toString();
				lookUp.setLookUpName(lookupName);
			}
			if(null != lookUp)
			lookUpDao.UpdateLookUpById(lookUp);
			}
		return new ResponseEntity<String>("LookUp Updated Successfully", HttpStatus.OK);
		}
	
	
	@PostMapping("/create")
	public ResponseEntity<String> CreateLookup(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		if (null != json.get(ServiceConstants.LOOKUPNAME) && null != json.get(ServiceConstants.LOOKUPLABEL)
				&& null != json.get(ServiceConstants.LOOKUPCATAGORY)) {
			LookUp lookUp = new LookUp();
			lookUp.setLookUpName(json.get(ServiceConstants.LOOKUPNAME));
			lookUp.setLookUpLabel(json.get(ServiceConstants.LOOKUPLABEL));
			lookUp.setLookUpTypeId(Integer.parseInt(json.get(ServiceConstants.LOOKUPCATAGORY)));
			lookUp.setShopId(json.get(ServiceConstants.SHOP_ID));
			lookUp.setActive(Boolean.TRUE);
			lookUp.setDeleted(Boolean.FALSE);
			lookUpDao.UpdateLookUpById(lookUp);
		}
		return new ResponseEntity<String>("LookUp Created Successfully", HttpStatus.OK);
	}
	
	
}
