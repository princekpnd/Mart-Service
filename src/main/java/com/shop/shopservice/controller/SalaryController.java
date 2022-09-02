package com.shop.shopservice.controller;

import java.net.URISyntaxException;
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
import com.shop.shopservice.entity.Salary;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ISalaryService;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
	private final Logger log = LoggerFactory.getLogger(ReviewController.class);
	@Autowired
	private ISalaryService salaryService;
	
	@Autowired
	private IAdminService adminService;
	
	
	@GetMapping("getallsalary")
	public ResponseEntity<List<Salary>> getAllSalary() {
		List<Salary> salaryList = salaryService.getAllSalary();
		return new ResponseEntity<List<Salary>>(salaryList, HttpStatus.OK);
	}
	
	@GetMapping("get/{employeeId}")
	public ResponseEntity<List<Salary>> getAllSalaryByEmployeeId(@PathVariable("employeeId") String employeeId){
		List<Salary> salaryList =salaryService.getAllSalaryByEmployeeId(employeeId);
		return new ResponseEntity<List<Salary>>(salaryList, HttpStatus.OK);
		}
	@GetMapping("getsalaybyshopid/{shopId}")
	public ResponseEntity<List<Salary>> getSalaryByShopId(@PathVariable("shopId") String shopId){
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Salary> salaryList =null;
		if(admin !=null && admin.isActive()==Boolean.TRUE && admin.isDeleted()== Boolean.FALSE) {
		 salaryList =salaryService.getSalaryByShopId(shopId);
		}
		return new ResponseEntity<List<Salary>>(salaryList,HttpStatus.OK);
	}
	
	
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createSalary(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
	    Salary salary = new Salary(json.get(ServiceConstants.EMPLOYEE_ID),(json.get(ServiceConstants.SHOP_ID)));
	    
	    salary. setEmployeeId( json.get(ServiceConstants.EMPLOYEE_ID));
		salary.setAdvanceSalary(Integer.parseInt(json.get(ServiceConstants.ADVANCE_SALARY)));
		salary.setDeduction(Integer.parseInt(json.get(ServiceConstants.DEDUCTION)));
		salary.setShopId(json.get(ServiceConstants.SHOP_ID));
		salary.setSalary(Integer.parseInt(json.get(ServiceConstants.SALARY)));
		salary.setTotalSalary(Integer.parseInt(json.get(ServiceConstants.TOTAL_SALARY)));
		salary.setDeleted(Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED)));
		salary.setActive(Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE)));
		
		response.put("EMPLOYEE_ID", json.get(ServiceConstants.EMPLOYEE_ID));
		if (salaryService.salaryExists(salary.getEmployeeId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "employeeID already exist with given shopId");
		} else {
			boolean result = salaryService.createSalary(salary);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Salary created successfully with given employeeId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);

	}
	
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateSalary(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.EMPLOYEE_ID) && null != salaryService.getSalary(json.get(ServiceConstants.EMPLOYEE_ID))) {
			Salary salary = salaryService.getSalary(json.get(ServiceConstants.EMPLOYEE_ID));
			
			
			if(null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				salary.setId(id);
				
				}
			if(null !=json.get(ServiceConstants.EMPLOYEE_ID)) {
				String employeeId = json.get(ServiceConstants.EMPLOYEE_ID);
                 System.out.println(employeeId);
                 salary.setEmployeeId(employeeId);
			}
			
			if(null!= json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive =Boolean.parseBoolean( json.get(ServiceConstants.IS_ACTIVE));
				System.out.println(isActive);
				salary.setActive(isActive);
			}
			if(null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted =Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				System.out.println(isDeleted);
				salary.setDeleted(isDeleted);
			}
//			if(null !=json.get(ServiceConstants.SALARY)) {
//				String salary= Float.parseFloat(json.get(ServiceConstants.SALARY));
//				System.out.println(salary);
//				salary.setSalary(salary);
//			}
			
			if(null !=json.get(ServiceConstants.ADVANCE_SALARY)) {
				int advanceSalary= Integer.parseInt(json.get(ServiceConstants.ADVANCE_SALARY));
				System.out.println(advanceSalary);
				salary. setAdvanceSalary(advanceSalary);
			}
			

			if(null !=json.get(ServiceConstants.TOTAL_SALARY)) {
			  int totalSalary= Integer.parseInt(json.get(ServiceConstants.TOTAL_SALARY));
				System.out.println(totalSalary);
				salary. setTotalSalary(totalSalary);
			}
			
			if(null !=json.get(ServiceConstants.SHOP_ID)) {
				String shopId= (json.get(ServiceConstants.SHOP_ID));
				System.out.println(shopId);
				salary. setShopId(shopId);
			}
			
			salaryService.updateSalary(salary);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Salary updated");
				}
		else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description",
					"Salary doesn't exist with given employeeId");
		}


		return ResponseEntity.ok().body(response);
	
		}			
			


}
