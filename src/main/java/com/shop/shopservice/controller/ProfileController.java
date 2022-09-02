package com.shop.shopservice.controller;
/**

import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.shop.shopservice.entity.Profile;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.service.IProfileService;
import com.shop.shopservice.service.IUserService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	private final Logger log = LoggerFactory.getLogger(ProfileController.class);
	@Autowired
	IProfileService profileService;
	
	@Autowired
	IUserService userService;
	
	
	@GetMapping("getallprofile")
	public ResponseEntity<List<Profile>> findAllProfile() {
		List<Profile> listProfile = profileService.getAllProfle(50);
		return new ResponseEntity<List<Profile>>(listProfile, HttpStatus.OK);
	}
	
	@GetMapping("getallprofile/catagory/{categoryId}")
	public ResponseEntity<List<Profile>> findAllProfileBasedOnCategory(@PathVariable("categoryId") String categoryId) {
		List<Profile> listProfile = new ArrayList<Profile>();
		if (Integer.parseInt(categoryId) == 33) {
			listProfile = profileService.getAllProfle(50);
		} else {
			listProfile = profileService.getAllProfleOnCatagory(Integer.parseInt(categoryId));
		}
		return new ResponseEntity<List<Profile>>(listProfile, HttpStatus.OK);
	}
	
	@GetMapping("getprofile/{userId}")
	public ResponseEntity<Profile> findUserProfile(@PathVariable(ServiceConstants.USERID) String userId) {
		Profile profile = profileService.getProfileByUserId(Integer.parseInt(userId));
		return new ResponseEntity<Profile>(profile, HttpStatus.OK);
	}
	
	  @GetMapping("getallprofile/catagory/{subCategoryId}") public
	  ResponseEntity<List<Profile>>
	  findAllProfileBasedOnSubCategory(@PathVariable("subCategoryId") String
	  subCategoryId) { List<Profile> listProfile =
	  profileService.getAllProfleOnSubCatagory(Integer.parseInt(subCategoryId));
	  return new ResponseEntity<List<Profile>>(listProfile, HttpStatus.OK); }
	 
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createProfile(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create profile for : {}", json.get("userId"));
		Map<String, String> response = new HashMap<String, String>();
		Profile profile = null;
		if (null != profileService.getProfileByUserId(Integer.parseInt(json.get(ServiceConstants.USERID)))) {
			profile = profileService.getProfileByUserId(Integer.parseInt(json.get(ServiceConstants.USERID)));
		} else {
			profile = new Profile();
		}
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_CATAGORY)))
				profile.setCatagory(Integer.parseInt(json.get(ServiceConstants.PROFILE_CATAGORY)));
		if (Strings.isNotEmpty(json.get("SubCatagory")))
				profile.setSubCatagoryList(json.get("SubCatagory"));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_BACKGROUND_EXPERTISE)))
			profile.setBackgroundExpertise(json.get(ServiceConstants.PROFILE_BACKGROUND_EXPERTISE));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_CONSULTATION_PRICE)))
			profile.setConsultationPrice(Integer.parseInt(json.get(ServiceConstants.PROFILE_CONSULTATION_PRICE)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_SUMMERY_DETAILS)))
			profile.setSummeryDetails(json.get(ServiceConstants.PROFILE_SUMMERY_DETAILS));
		if (Strings.isNotEmpty(json.get("articleId")))
			profile.setArticleId(Integer.parseInt(json.get("articleId")));
		if (Strings.isNotEmpty(json.get(ServiceConstants.USERID)))
			profile.setUserId(Integer.parseInt(json.get(ServiceConstants.USERID)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_AVAILABILITY)))
			profile.setAvailability(Integer.parseInt(json.get(ServiceConstants.PROFILE_AVAILABILITY)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_PRIMARY_LANGUANGE)))
			profile.setLanguagePrimary(Integer.parseInt(json.get(ServiceConstants.PROFILE_PRIMARY_LANGUANGE)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_SECONDARY_LANGUANGE)))
			profile.setLanguageSeconday(Integer.parseInt(json.get(ServiceConstants.PROFILE_SECONDARY_LANGUANGE)));
		profile.setServedConsultation(0);
		profile.setAverageResponseTime(0);
		profile.setTotalEarning(0);
		User user = userService.getUser(Integer.parseInt(json.get(ServiceConstants.USERID)));
		profile.setDisplayName(user.getLastName() + " " + user.getFirstName());
		profileService.createProfile(profile);
		response.put("userId", json.get(ServiceConstants.USERID));
		response.put("status", Strings.EMPTY + true);
		response.put("description", "Profile created/updated successfully with given text");
		profile = null;
		return ResponseEntity.ok().body(response);
	}
	
	
	@SuppressWarnings({ })
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> updateProfile(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update profile for : {}", json.get(ServiceConstants.USERID));
		Map<String, String> response = new HashMap<String, String>();
		Profile profile = profileService.getProfileByUserId(Integer.parseInt(json.get("userId")));
		if (null != profile) {
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_CATAGORY)))
				profile.setCatagory(Integer.parseInt(json.get(ServiceConstants.PROFILE_CATAGORY)));
		if (Strings.isNotEmpty(json.get("SubCatagory")))
				profile.setSubCatagoryList(json.get("SubCatagory"));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_BACKGROUND_EXPERTISE)))
			profile.setBackgroundExpertise(json.get(ServiceConstants.PROFILE_BACKGROUND_EXPERTISE));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_CONSULTATION_PRICE)))
			profile.setConsultationPrice(Integer.parseInt(json.get(ServiceConstants.PROFILE_CONSULTATION_PRICE)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_SUMMERY_DETAILS)))
			profile.setSummeryDetails(json.get(ServiceConstants.PROFILE_SUMMERY_DETAILS));
		if (Strings.isNotEmpty(json.get("articleId")))
			profile.setArticleId(Integer.parseInt(json.get("articleId")));
		if (Strings.isNotEmpty(json.get("userId")))
			profile.setUserId(Integer.parseInt(json.get(ServiceConstants.USERID)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_AVAILABILITY)))
			profile.setAvailability(Integer.parseInt(json.get(ServiceConstants.PROFILE_AVAILABILITY)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_PRIMARY_LANGUANGE)))
			profile.setLanguagePrimary(Integer.parseInt(json.get(ServiceConstants.PROFILE_PRIMARY_LANGUANGE)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_SECONDARY_LANGUANGE)))
			profile.setLanguageSeconday(Integer.parseInt(json.get(ServiceConstants.PROFILE_SECONDARY_LANGUANGE)));
		if (Strings.isNotEmpty(json.get(ServiceConstants.PROFILE_TWITTER_HANDLE)))
			profile.setTwitterHandle((json.get(ServiceConstants.PROFILE_TWITTER_HANDLE)));
		profile.setServedConsultation(0);
		profile.setAverageResponseTime(0);
		profile.setTotalEarning(0);
		profileService.updateProfile(profile);
		response.put("userId", json.get(ServiceConstants.USERID));
		response.put("status", Strings.EMPTY + true);
		response.put("description", "Profile created/updated successfully with given text");
		} else {
			response.put("userId", json.get(ServiceConstants.USERID));
			response.put("status", Strings.EMPTY + false);
			response.put("description", "Profile created/updated failed with given text");
		}
		profile = null;
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("findProfileOnAvailibility/{availability}/{count}")
	public ResponseEntity<List<Profile>> findAllFreeProfile(@PathVariable("availability") Integer availability, @PathVariable("count") Integer count) {
		List<Profile> listProfile = new ArrayList<Profile>();
			listProfile = profileService.findProfileOnAvailibility(availability, count);
		return new ResponseEntity<List<Profile>>(listProfile, HttpStatus.OK);
	}
	

}
**/