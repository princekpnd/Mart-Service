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
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Plan;
import com.shop.shopservice.service.IPlanService;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
	private final Logger log = LoggerFactory.getLogger(PlanController.class);
	
	@Autowired
	private IPlanService planService;
	
	@Autowired
	private ILookUp lookup;

	@GetMapping("getallplan")
	public ResponseEntity<List<Plan>> getAllPlan() {
		List<Plan> planList = planService.getAllPlan();
		return new ResponseEntity<List<Plan>>(planList, HttpStatus.OK);
	}
	
	@GetMapping("getallactiveplan")
	public ResponseEntity<List<Plan>> getAllActivePlan() {
		List<Plan> planList = planService.getAllActivePlan();
		return new ResponseEntity<List<Plan>>(planList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Plan> getById(@PathVariable("id") Integer id) {
		Plan plan = planService.getById(id);
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "YEARLY");
//		int yearly = lookUp.getLookUpId();
//		plan.setPlanType(yearly);
//		planService.updatePlan(plan);
		
		return new ResponseEntity<Plan>(plan, HttpStatus.OK);
	}
	
	@GetMapping("getactiveplanbyid/{id}")
	public ResponseEntity<Plan> getActivePlanById(@PathVariable("id") int id){
		Plan plan = null;
		plan = planService.getActivePlanById(id);
		return new ResponseEntity<Plan>(plan, HttpStatus.OK);
	}

//	@GetMapping("get/byplantype/{planType}")
//	public ResponseEntity<List<Plan>> getByPlanType(@PathVariable("planType") int planType) {
//		List<Plan> planList = planService.getByPlanType(planType);
//		return new ResponseEntity<List<Plan>>(planList, HttpStatus.OK);
//	}
	
	@GetMapping("get/byplantype/{planType}")
	public ResponseEntity<List<Plan>> getByPlanType(@PathVariable("planType") int planType) {
		List<Plan> planList = planService.getByPlanType(planType);
		return new ResponseEntity<List<Plan>>(planList, HttpStatus.OK);
	}
	
	@GetMapping("getactiveplan/{planType}")
	public ResponseEntity<List<Plan>> getActivePlan(@PathVariable("planType") int planType){
		List<Plan> planList = planService.getActivePlan(planType);
		return new ResponseEntity<List<Plan>>(planList, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdatePlan(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID) && null != json.get(ServiceConstants.PLAN_TYPE)) {
			Plan plan = planService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.PLAN_TYPE)) {
				int planType = Integer.parseInt(json.get(ServiceConstants.PLAN_TYPE));
				plan.setPlanType(planType);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE));
				plan.setActive(isActive);

			}
			if (null != json.get(ServiceConstants.VALIDITY)) {
				int validity = Integer.parseInt(json.get(ServiceConstants.VALIDITY).toString());
				plan.setValidity(validity);
			}
			if (null != json.get(ServiceConstants.AMOUNT)) {
				int amount = Integer.parseInt(json.get(ServiceConstants.AMOUNT).toString());
				plan.setAmount(amount);
			}
			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				plan.setCreatedOn(createdOn);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
				plan.setDeleted(isDeleted);
			}
			
			if(null != json.get(ServiceConstants.DISCOUNT)) {
				int discount = Integer.parseInt(json.get(ServiceConstants.DISCOUNT));
				plan.setDiscount(discount);
			}

			planService.updatePlan(plan);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Account updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Account doesn't exist with given email or userid");
		}

		return ResponseEntity.ok().body(response);

	}
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Plan plan = new Plan(Integer.parseInt(json.get(ServiceConstants.PLAN_TYPE)));
		
		plan.setPlanType(Integer.parseInt(json.get(ServiceConstants.PLAN_TYPE)));
		plan.setAmount(Integer.parseInt(json.get(ServiceConstants.AMOUNT)));
		plan.setCreatedOn(new Date());
		plan.setActive(Boolean.TRUE);
		plan.setDeleted(Boolean.FALSE);
		plan.setValidity(Integer.parseInt(json.get(ServiceConstants.VALIDITY)));
		plan.setDiscount(Integer.parseInt(json.get(ServiceConstants.DISCOUNT)));
		
		response.put("planType", json.get(ServiceConstants.PLAN_TYPE));
		if (planService.planExists(plan.getPlanType())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Plan already exist with given PlanType");
		} else {
			boolean result = planService.createPlan(plan);
			response.put("status", Strings.EMPTY + result);
//			response.put("planType",String.valueOf(plan.getPlanType()));
//			response.put("amount",String.valueOf(plan.getAmount()));
			response.put("description",
					"Plan created successfully with given plan type , go through your inbox to activate");
		}

		return ResponseEntity.ok().body(response);
	}
	
}