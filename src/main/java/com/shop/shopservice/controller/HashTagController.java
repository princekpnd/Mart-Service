package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.IHashtagDAO;
import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.entity.Hashtag;

@RestController
@RequestMapping("/api/hashtag")
public class HashTagController {

	private final Logger log = LoggerFactory.getLogger(HashTagController.class);

	@Autowired
	IHashtagDAO hashTagDao;
	
	@Autowired
	IUsersDAO userDao;
	
	@GetMapping("getall")
	public ResponseEntity<List<Hashtag>> findAllHashtag() {
		List<Hashtag> listHashtag = hashTagDao.listHashtag();
		return new ResponseEntity<List<Hashtag>>(listHashtag, HttpStatus.OK);
	}
	
	@GetMapping("hello")
	public String hello () {
		return "Hello Avinash";
	}
	
	@PostMapping("/create")
	ResponseEntity<Hashtag> createHashtag(@Valid @RequestBody Hashtag hashtag, HttpServletRequest request,
			@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.info("Request to create Hashtag: {}", deviceId);
		if (null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId)) {
			hashTagDao.addHashtag(hashtag);
			hashtag.setCreatedOn(new Date());
		}
		return ResponseEntity.ok().body(hashtag);
	}
	
	@PutMapping("/update")
	ResponseEntity<Hashtag> updateHashtag(@Valid @RequestBody Hashtag hashtag,
			HttpServletRequest request,@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.info("Request to create Hashtag: {}", deviceId);
		if(null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId))
		hashTagDao.updateHashtag(hashtag);
		return ResponseEntity.ok().body(hashtag);
	}
	
	@GetMapping("getbycatagory/{catagory}")
	public ResponseEntity<List<Hashtag>> findAllHashtagByCatagory(@PathVariable("catagory") int catagory) {
		List<Hashtag> listHashtag = hashTagDao.findHashtagByCatagory(catagory);
		return new ResponseEntity<List<Hashtag>>(listHashtag, HttpStatus.OK);
	}
	
	@GetMapping("gethello/{name}/{sub}")
	public String helloName(@PathVariable("name") int name, @PathVariable("sub") int sub) {
		int sum=name+sub;
		return "Hello " + sum;
	}
	@GetMapping("getHis/{greater}/{smaller}")
	public String helloName1(@PathVariable("greater") int greater, @PathVariable("smaller") int smaller) {
		String result = "";
		String result1 = "";
		if(greater > smaller) {		
			result = "Greater number is " + greater;
			result1 = "Smaller number is " + smaller;
		} else {
			result = result + "Greater number is " + smaller;
			result1 = "Smaller number is " + greater;			
		}
		
		return result + "\n" + result1;		
	}

//@GetMapping("getHis/{greater}/{smaller}")
//public String helloName2(@PathVariable("greater") int greater) {
//	String result = "";
//
//	for(int i=0;i>10;i++)
//	{
//		return "result="+i;
//	}
}