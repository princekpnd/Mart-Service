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
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Review;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	private final Logger log = LoggerFactory.getLogger(ReviewController.class);
	@Autowired
	private IReviewService reviewService;
	
	@Autowired
	private IAdminService adminService;
	
	
	@GetMapping("getallreview")
	public ResponseEntity<List<Review>> getAllreview() {
		List<Review> reviewList = reviewService.getAllReview();
		return new ResponseEntity<List<Review>>(reviewList, HttpStatus.OK);
	}
	
	@GetMapping("getreviewbyshopid/{shopId}")
	public ResponseEntity<List<Review>> getReviewByShopId(@PathVariable("shopId") String shopId ){
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Review> reviewList = null;
		if(admin !=null && admin.isActive()==Boolean.TRUE && admin.isDeleted()==Boolean.FALSE) {
		 reviewList = reviewService.getReviewByShopId( shopId);
		}
		return new ResponseEntity<List<Review>>(reviewList,HttpStatus.OK);
	}
	@GetMapping("getreviewforuserbyshopid/{shopId}")
	public ResponseEntity<List<Review>> getReviewForUser(@PathVariable("shopId") String shopId){
		       Admin admin = adminService.getAdminByShopId(shopId);
		       List<Review> reviewList = null;
		   if(admin !=null && admin.isActive()== Boolean.TRUE && admin.isDeleted()==Boolean.FALSE) {    
		reviewList = reviewService.getReviewForUserByShopId(shopId);
		   }
		return new ResponseEntity<List<Review>> (reviewList,HttpStatus.OK);
	}
	
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createReview(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Review review = new Review(json.get(ServiceConstants.PRODUCT_ID),(json.get(ServiceConstants.SHOP_ID)));
		
		review.setProductId( json.get(ServiceConstants.PRODUCT_ID));
		review.setShopId(json.get(ServiceConstants.SHOP_ID));
		review.setUserId(json.get(ServiceConstants.USER_ID));
		review.setComment(json.get(ServiceConstants.COMMENT));
		review. setName(json.get(ServiceConstants.NAME));
		review. setCreatedOn(new Date());
		
		response.put("PRODUCT_ID", json.get(ServiceConstants.PRODUCT_ID));
		if (reviewService.reviewExists(review.getProductId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "ProductID already exist with given shopId");
		} else {
			boolean result = reviewService.createReview(review);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Review created successfully with given shopId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);

	}
	
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateReview(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.PRODUCT_ID) && null != reviewService.getReview(json.get(ServiceConstants.PRODUCT_ID))) {
			Review review = reviewService.getReview(json.get(ServiceConstants.PRODUCT_ID));

			
			if(null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				review.setId(id);
				
				}
			if(null !=json.get(ServiceConstants.PRODUCT_ID)) {
				String productId =(json.get(ServiceConstants.PRODUCT_ID));
				System.out.println(productId);
				review.setProductId(productId);
			}
			if(null !=json.get(ServiceConstants.NAME)) {
				String name =(json.get(ServiceConstants.NAME));
				System.out.println(name);
				review.setName(name);
			}
			if(null !=json.get(ServiceConstants.SHOP_ID)) {
				String shopId =(json.get(ServiceConstants.SHOP_ID));
				System.out.println(shopId);
				review.setShopId(shopId);
			}
			if(null !=json.get(ServiceConstants.USER_ID)) {
				String userId =(json.get(ServiceConstants.USER_ID));
				System.out.println(userId);
				review.setUserId(userId);
			}
			if(null !=json.get(ServiceConstants.COMMENT)) {
				String comment = (json.get(ServiceConstants.COMMENT));
				System.out.println(comment);
				review.setComment(comment);
			
			}
			if(null !=json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn =new Date();
				System.out.println(createdOn);
				review.setCreatedOn(createdOn);
				
			}
			
			reviewService.updateReview(review);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Review updated");
				}
		else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description",
					"Review doesn't exist with given ShopId");
		}


		return ResponseEntity.ok().body(response);
	
		}			
}
