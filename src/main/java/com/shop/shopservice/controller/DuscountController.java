package com.shop.shopservice.controller;
import java.net.URISyntaxException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Discount;
import com.shop.shopservice.service.IDiscountService;
import java.util.Timer;
@RestController
@RequestMapping("/api/discount")
public class DuscountController {
	private final Logger log = LoggerFactory.getLogger(DuscountController.class);
	
	
	@Autowired
	private IDiscountService discountService;
	
	@GetMapping("getall")
	public ResponseEntity<List<Discount>> getAll(){
		List<Discount> discountList = discountService.getAll();
		return new ResponseEntity<List<Discount>>(discountList, HttpStatus.OK);
	}
	
	@GetMapping("get/{discountId}")
	public ResponseEntity<Discount> getById(@PathVariable("discountId") int discountId){
		Discount discount = discountService.getById(discountId);
		return new ResponseEntity<Discount>(discount, HttpStatus.OK);
	}
	
	@GetMapping("get/byoffertype/{offerType}")
	public ResponseEntity<List<Discount>> getByDiscountType(@PathVariable("offerType") String offerType){
		List<Discount> discountList = discountService.getByDiscountType(offerType);
		return new ResponseEntity<List<Discount>>(discountList, HttpStatus.OK);
	}
	
	@GetMapping("getBy/lable/{lable}")
	public ResponseEntity<Discount> getByLable(@PathVariable("lable") String lable){
		Discount discount = discountService.getByLable(lable);
		return new ResponseEntity<Discount>(discount, HttpStatus.OK);
	}
//	@GetMapping("getallslot/{offerType}")
//	public ResponseEntity<List<Discount>> getByOfferType(@PathVariable("offerType") String offerType){
//		List<Discount> discountList = discountService.getByDiscountType(offerType);
//		return new ResponseEntity<List<Discount>>(discountList, HttpStatus.OK);
//	}
	
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if(null != json.get(ServiceConstants.ID) && null != discountService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Discount discount = discountService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			discount.setCount(Integer.parseInt(json.get(json.get(ServiceConstants.ID))));
		
			discountService.updateDiscount(discount);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Account updated");
		}
		return ResponseEntity.ok().body(response);
	}
	
//	@GetMapping("gettime")
//	public ResponseEntity<List<Discount>> getByDiscountType(){
//		Timer time = new Timer(); // Instantiate Timer Object
//		ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
//		time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs
//
//		//for demo only.
//		for (int i = 0; i <= 5; i++) {
//			System.out.println("Execution in Main Thread...." + i);
//			Thread.sleep(2000);
//			if (i == 5) {
//				System.out.println("Application Terminates");
//				System.exit(0);
//			}
//		}
//	}
	
	
//	@SuppressWarnings({})
//	@PostMapping("/create")
//	ResponseEntity<Map<String, String>> createAttendance(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.CITY));
//		Map<String, String> response = new HashMap<String, String>();
//		Discount discount = new Discount();
//		discount.setActive(Boolean.TRUE);
//		discount.setCreatedOn(new Date());
//		discount.setDeleted(Boolean.FALSE);
//		discount.setImage(json.get(ServiceConstants.IMAGE));
//		discount.setLable(json.get(ServiceConstants.LABLE));
//		discount.setName(json.get(ServiceConstants.NAME));
//		discount.setOfferType(json.get(ServiceConstants.OFFER_TYPE));
//		response.put("discountId", json.get(ServiceConstants.DISCOUNT_ID));
//		
//		return ResponseEntity.ok().body(response);
//	}
	
}

