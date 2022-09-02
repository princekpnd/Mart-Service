package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Casual;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Work;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICasualService;
import com.shop.shopservice.service.IWorkService;

@RestController
@RequestMapping("/api/casual")

public class CasualController {
	private final Logger log = LoggerFactory.getLogger(CasualController.class);
	@Autowired
	private ICasualService casualService;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private ILookUp lookup;
	
	@Autowired
	private IWorkService workService;
	
	@GetMapping("getallcasual")
	public ResponseEntity<List<Casual>> getAllcasual() {
		List<Casual> casualList = casualService.getAllCasual();
		return new ResponseEntity<List<Casual>>(casualList, HttpStatus.OK);
	}
	
	@GetMapping("get/id")
	public ResponseEntity<Casual> getCasualById(@PathVariable("id") int id){
		Casual casual = casualService.getCasualById(id);
		return new ResponseEntity<Casual>(casual, HttpStatus.OK);
		
	}

	@GetMapping("get/{employeeId}")
	public ResponseEntity<List<Casual>> getCasualByEmployeeId(@PathVariable("employeeId") String employeeId) {
		List<Casual> casualList = casualService.getCasualByEmployeeId(employeeId);
		return new ResponseEntity<List<Casual>>(casualList, HttpStatus.OK);
	}

	@GetMapping("getcasualbyshopid/{shopId}")
	public ResponseEntity<List<Casual>> getCasualByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Casual> casualList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			casualList = casualService.getCasualByShopId(shopId);
		}
		return new ResponseEntity<List<Casual>>(casualList, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createCasul(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Casual casual = new Casual(json.get(ServiceConstants.SHOP_ID), (json.get(ServiceConstants.EMPLOYEE_ID)));
		if (null != json.get(ServiceConstants.LEAVE_FROM) && null != json.get(ServiceConstants.LEAVE_TO)
				&& null != json.get(ServiceConstants.EMPLOYEE_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.LEAVE_TYPE) && null != json.get(ServiceConstants.SALARY) 
				&& null != json.get(ServiceConstants.SALARY_TYPE)) {
			String shopId = json.get(ServiceConstants.SHOP_ID) ,employeeId=json.get(ServiceConstants.EMPLOYEE_ID);
			int salary = Integer.parseInt(json.get(ServiceConstants.SALARY)), paidSalary = Integer.parseInt(json.get(ServiceConstants.PAID_SALARY));
			int salaryType = Integer.parseInt(json.get(ServiceConstants.SALARY_TYPE)), leaveType = Integer.parseInt(json.get(ServiceConstants.LEAVE_TYPE));
		
			Work work = null;
			boolean result = workService.checkActiveWork(shopId, employeeId, Boolean.TRUE);
			if(result) {
				work = workService.getActiveWork(shopId, employeeId, Boolean.TRUE);
			} else {
				work = new Work(json.get(ServiceConstants.SHOP_ID),(json.get(ServiceConstants.EMPLOYEE_ID)));
				work.setCreatedOn(new Date());
				work.setSalary(salary);
				work.setSalaryType(salaryType);
				work.setEmployeeId(employeeId);
				work.setShopId(shopId);
				work.setActive(Boolean.TRUE);
				work.setDeleted(Boolean.FALSE);
				work.setUpdatedOn(new Date());
				work.setPaymentStatus(Boolean.FALSE);
		//		work.setPaidSalary(paidSalary);
				workService.createWork(work);
				
				work = workService.getActiveWork(shopId, employeeId, Boolean.TRUE);
			}
			

			try {
				casual.setLeaveFrom(new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.LEAVE_FROM)));

				casual.setLeaveTo(new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.LEAVE_TO)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int fromDate = Integer.parseInt(json.get(ServiceConstants.LEAVE_FROM.substring(8, 10))), toDate =Integer.parseInt(json.get(ServiceConstants.LEAVE_TO.substring(8, 10)));
			int numberOfDayes = toDate - fromDate;
			int salary1 = (salary/30) * numberOfDayes;
			
			if( null != json.get(ServiceConstants.REASION)) {
			casual.setReasion(json.get(ServiceConstants.REASION));
			}
			casual.setEmployeeId(json.get(ServiceConstants.EMPLOYEE_ID));
			casual.setShopId(json.get(ServiceConstants.SHOP_ID));
			casual.setLeaveType(json.get(ServiceConstants.LEAVE_TYPE));
			casual.setActive(Boolean.TRUE);
			casual.setDeleted(Boolean.FALSE);
			casual.setCreatedOn(new Date());
			
			LookUp absent = lookup.findLookUpIdByName(shopId, "ABSENT");
			LookUp present = lookup.findLookUpIdByName(shopId, "PRESENT");
			
			if(present.getLookUpId() == leaveType) {
				work.setPresent(work.getPresent() + numberOfDayes);
				work.setTotalSalary(work.getTotalSalary() + salary1);
				work.setDuesSalary(work.getDuesSalary() + salary1);
				work.setUpdatedOn(new Date());
				workService.updateWork(work);
				casualService.createCasual(casual);
				
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Casual already exist with given employeeId");
				
			} else if(absent.getLookUpId() == leaveType) {
				work.setAbsent(work.getAbsent() + numberOfDayes);
				work.setUpdatedOn(new Date());
				workService.updateWork(work);
				casualService.createCasual(casual);
				
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Casual already exist with given employeeId");
			}

			response.put("EMPLOYEE_ID", json.get(ServiceConstants.EMPLOYEE_ID));
			if (casualService.casualExists(casual.getEmployeeId())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Casual already exist with given employeeId");
			} else {
				boolean result1 = casualService.createCasual(casual);
				response.put("status", Strings.EMPTY + result);
				response.put("description",
						"Casual_leave created successfully with given employeeId, go through your inbox to activate");
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.SHOP_ID) && null != casualService.getCasual(json.get(ServiceConstants.SHOP_ID))) {
//			Casual casual = casualService.getCasual(json.get(ServiceConstants.SHOP_ID)));
		if (null != json.get(ServiceConstants.EMPLOYEE_ID)
				&& null != casualService.getCasual(json.get(ServiceConstants.EMPLOYEE_ID))) {
			Casual casual = casualService.getCasual(json.get(ServiceConstants.EMPLOYEE_ID));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				casual.setId(id);
			}

			if (null != json.get(ServiceConstants.LEAVE_FROM)) {
				Date leaveFrom = new Date();
				System.out.println(leaveFrom);
				casual.setLeaveFrom(leaveFrom);
			}

			if (null != json.get(ServiceConstants.REASION)) {
				String reasion = (json.get(ServiceConstants.REASION));
				System.out.println(reasion);
				casual.setReasion(reasion);
			}

			if (null != json.get(ServiceConstants.LEAVE_TO)) {
				Date leaveTo = new Date();
				System.out.println(leaveTo);
				casual.setLeaveTo(leaveTo);
			}
			if (null != json.get(ServiceConstants.LEAVE_TYPE)) {
				String leaveType = json.get(ServiceConstants.LEAVE_TYPE);
				System.out.println(leaveType);
				casual.setLeaveType(leaveType);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				casual.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = (Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE)));
				System.out.println(isActive);
				casual.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = (Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED)));
				System.out.println(isDeleted);
				casual.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.EMPLOYEE_ID)) {
				String employeeId = json.get(ServiceConstants.EMPLOYEE_ID);
				System.out.println(employeeId);
				casual.setEmployeeId(employeeId);
			}
			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				casual.setCreatedOn(createdOn);
			}

			casualService.updateCasual(casual);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Casual_leave updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Casual_leave doesn't exist with given employeeId or userid");
		}

		return ResponseEntity.ok().body(response);

	}
}
