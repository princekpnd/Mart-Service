package com.shop.shopservice.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.utils.Calculation;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/otpp")
public class SendOtp {
	private final Logger log = LoggerFactory.getLogger(PurchaseController.class);
	

	@GetMapping("send")
	public ResponseEntity<?> getAllPurchasePlan() {
		int otp =(int) Math.ceil(Math.random() * 10000);
		
		String a = UUID.randomUUID().toString();
		
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	/*-------ROUND FLOAT TO 2 DECIMAL DIGITS--------*/
	
//	public float DecimalCal(float value) {
//		DecimalFormat df = new DecimalFormat("#.##");
//		float data = Float.parseFloat(df.format(value));
//		return data;
//	}
	
	@GetMapping("round")
	public ResponseEntity<?> getRound() {
		DecimalFormat df = new DecimalFormat("#.##");
		
		float total = 0, amount = 0, price = 1, discount = 0;
		
		int offer =10;
		
		Calculation decimalCalculation = new Calculation();
		
		discount = decimalCalculation.DecimalCalculation((price * offer) / 100);
		
		amount = decimalCalculation.DecimalCalculation(price - discount);
		
		
		
		float gstAmt = decimalCalculation.DecimalCalculation((amount * 18) / 100);
		
		total = decimalCalculation.DecimalCalculation(amount + gstAmt);
		 float round = Float.parseFloat(df.format(gstAmt));
		
		
		return new ResponseEntity<>(total, HttpStatus.OK);
	}


}
