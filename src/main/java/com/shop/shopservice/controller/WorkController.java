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
import com.shop.shopservice.entity.Attendance;
import com.shop.shopservice.entity.Work;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IAttendanceService;
import com.shop.shopservice.service.IWorkService;

@RestController
@RequestMapping("/api/work")
public class WorkController {
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IWorkService workService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IAttendanceService attendanceService;
		
	@GetMapping("getallwork")
	public ResponseEntity<List<Work>> getAllwork() {
		List<Work> workList = workService.getAllWork();
		return new ResponseEntity<List<Work>>(workList, HttpStatus.OK);
	}
	
	@GetMapping("get/{workId}")
	public ResponseEntity<Work> getWorkById(@PathVariable("workId") Integer workId) {
		Work work = workService.getWorkById(workId.intValue());
		return new ResponseEntity<Work>(work, HttpStatus.OK);
	}
	@GetMapping("getworkbyemployeeId/{employeeId}")
	public ResponseEntity<Work> getWorkByEmployeeId(@PathVariable("employeeId") String employeeId){
		Work work= workService.getWorkByEmployeeId(employeeId);
		String workId  = null;
		List<Attendance> attendanceList = attendanceService.getByWorkId(workId);
//		work.get(0).setAttendance(attendanceList);
		return new ResponseEntity<Work>(work,HttpStatus.OK);
	}
	
	@GetMapping("getworkbyshopId/{shopId}")
	public ResponseEntity<Work> getWorkByShopId(@PathVariable("shopId") String shopId){
		Admin admin =adminService.getAdminByShopId(shopId);
		Work work =null;
		if(admin !=null && admin.isActive()==Boolean.TRUE && admin.isDeleted()==Boolean.FALSE) {
		 work= workService.getWorkByShopId(shopId);
		}
		return new ResponseEntity<Work>(work,HttpStatus.OK);
	}
	
	@GetMapping("checkwork/activeemployee/{shopId}/{employeeId}")
	public ResponseEntity<Map<String ,String>> checkActiveEmployee(@PathVariable("shopId") String shopId, @PathVariable("employeeId") String employeeId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean active = Boolean.TRUE;
		
		boolean result = workService.checkActiveWork(shopId, employeeId, active);
		
		if(result) {
	     response.put("status", Boolean.TRUE.toString());
	     response.put("description", "Work updated");
     }
        else {
	         response.put("status", Boolean.FALSE.toString());
	         response.put("description",
			         "Work doesn't exist with given  userid");
     }
            return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping("getwork/activeemployee/{shopId}/{employeeId}")
	public ResponseEntity<Work> getActiveEmployee(@PathVariable("shopId") String shopId, @PathVariable("employeeId") String employeeId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean active = Boolean.TRUE;		
		Work work = workService.getActiveWork(shopId, employeeId, active);		
		return new ResponseEntity<Work>(work, HttpStatus.OK);		
	}
//	@GetMapping("getworkbyshopid/{shopId}")
//	public ResponseEntity<List<Work>> getWorkByShopId(@PathVariable("shopId") String shopId){
//		Work workList =  workService.getWorkByShopId(shopId);
//		return new ResponseEntity<List<Work>>(workList,HttpStatus.OK);
//		
//	}
	
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateWork(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID) && null != workService.getWorkById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Work	work = workService.getWorkById(Integer.parseInt(json.get(ServiceConstants.ID)));
			
			if(null != json.get(ServiceConstants.ID)) {
				int id =Integer.parseInt( json.get(ServiceConstants.ID).toString());
				work.setId(id);				
				}
			
			if(null != json.get(ServiceConstants.ABSENT)) {
				int absent =Integer.parseInt( json.get(ServiceConstants.ABSENT).toString());
				work.setAbsent(absent);				
				}
			

			if(null != json.get(ServiceConstants.PRESENT)) {
				int present =Integer.parseInt( json.get(ServiceConstants.PRESENT).toString());
				work.setPresent(present);				
				}
			if( null !=json.get(ServiceConstants.TOTAL_SALARY)) {
				int totalSalary = Integer.parseInt(json.get(ServiceConstants.TOTAL_SALARY));
				System.out.println(totalSalary);
				work.setTotalSalary(totalSalary);
			}
			
			if(null != json.get(ServiceConstants.BONUS)) {
				int bonus =Integer.parseInt( json.get(ServiceConstants.BONUS).toString());
				work.setBonus(bonus);				
				}
			
			if(null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date(json.get(ServiceConstants.CREATED_ON).toString());
				work.setCreatedOn(createdOn);				
				}
			
			if(null != json.get(ServiceConstants.ADVANCE_SALARY)) {
				int advanceSalary =Integer.parseInt( json.get(ServiceConstants.ADVANCE_SALARY).toString());
				work.setAdvanceSalary(advanceSalary);				
				}
			
			if(null != json.get(ServiceConstants.SALARY)) {
				int salary =Integer.parseInt( json.get(ServiceConstants.SALARY).toString());
				work.setSalary(salary);				
				}
			
			if(null != json.get(ServiceConstants.SALARY_TYPE)) {
				int salaryType =Integer.parseInt( json.get(ServiceConstants.SALARY_TYPE).toString());
				work.setSalaryType(salaryType);				
				}
			

			if(null != json.get(ServiceConstants.LEAVE_FROM)) {
				Date leaveFrom = new Date(json.get(ServiceConstants.LEAVE_FROM).toString());
				work.setLeaveFrom(leaveFrom);				
				}
			
			if(null != json.get(ServiceConstants.LEAVE_TO)) {
				Date leaveTo = new Date(json.get(ServiceConstants.LEAVE_TO).toString());
				work.setLeaveTo(leaveTo);				
				}
			
			if(null != json.get(ServiceConstants.LEAVE_TYPE)) {
				int leaveType =Integer.parseInt( json.get(ServiceConstants.LEAVE_TYPE).toString());
				work.setLeaveType(leaveType);				
				}
			

			if(null != json.get(ServiceConstants.INCENTIVE)) {
			  int incentive =Integer.parseInt(( json.get(ServiceConstants.INCENTIVE).toString()));
				work.setIncentive(incentive);				
				}
			
			if(null != json.get(ServiceConstants.ATTENDANCE)) {
				int attendance = Integer.parseInt(json.get(ServiceConstants.ATTENDANCE).toString());
				work.setAttendance(attendance);				
				}
			
			if(null != json.get(ServiceConstants.EMPLOYEE_ID)) {
				String employeeId = json.get(ServiceConstants.EMPLOYEE_ID).toString();
				work.setEmployeeId(employeeId);				
				}
			if(null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				work.setShopId(shopId);				
				}
			if(null != json.get(ServiceConstants.UPDATED_ON)) {
				Date updatedOn = new Date();
				work.setUpdatedOn(updatedOn);
			}
			
			if(null != json.get(ServiceConstants.DEDUCTION)) {
				int deduction =Integer.parseInt( json.get(ServiceConstants.DEDUCTION).toString());
				work.setDeduction(deduction);
				
			}
			if(null != json.get(ServiceConstants.PAYMENT_STATUS)) {
				boolean paymentStatus =Boolean.parseBoolean(json.get(ServiceConstants.PAYMENT_STATUS).toString());
				work.setPaymentStatus(paymentStatus);
			}
			if(null != json.get(ServiceConstants.PAID_SALARY)) {
				int paidSalary = Integer.parseInt(json.get(ServiceConstants.PAID_SALARY).toString());
				work.setPaidSalary(paidSalary);
			}
			if(null != json.get(ServiceConstants.DUES_SALARY)) {
				int duesSalary =Integer.parseInt(json.get(ServiceConstants.DUES_SALARY).toString());
				work.setDuesSalary(duesSalary);
				}
			
				
			
			    workService.updateWork(work);
			     response.put("status", Boolean.TRUE.toString());
			     response.put("description", "Work updated");
		     }
		        else {
			         response.put("status", Boolean.FALSE.toString());
			         response.put("description",
					         "Work doesn't exist with given  userid");
		     }
		            return ResponseEntity.ok().body(response);
			           }
	
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Work work = new Work(json.get(ServiceConstants.SHOP_ID),(json.get(ServiceConstants.EMPLOYEE_ID)));
			
			work.setAbsent(Integer.parseInt(json.get(ServiceConstants.ABSENT)));
			work.setPresent(Integer.parseInt(json.get(ServiceConstants.PRESENT)));
			work.setBonus(Integer.parseInt((json.get(ServiceConstants.BONUS))));
//			work.setCreatedOn(new Date(json.get(ServiceConstants.CREATED_ON)));
			work.setAdvanceSalary(Integer.parseInt(json.get(ServiceConstants.ADVANCE_SALARY)));
			work.setSalary(Integer.parseInt(json.get(ServiceConstants.SALARY)));
//			work. setLeaveFrom(new Date(json.get(ServiceConstants.LEAVE_FROM)));
//			work. setLeaveTo(new Date(json.get(ServiceConstants.LEAVE_TO)));
			work.setLeaveType(Integer.parseInt((json.get(ServiceConstants.LEAVE_TYPE))));
			work.setIncentive(Integer.parseInt(json.get(ServiceConstants.INCENTIVE)));
			work.setAttendance(Integer.parseInt(json.get(ServiceConstants.ATTENDANCE)));
			work.setEmployeeId(json.get(ServiceConstants.EMPLOYEE_ID));
			work.setShopId(json.get(ServiceConstants.SHOP_ID));
			work.setTotalSalary(Integer.parseInt(json.get(ServiceConstants.TOTAL_SALARY)));
			work.setUpdatedOn(new Date());
			work.setPaymentStatus(Boolean.parseBoolean(json.get(ServiceConstants.PAYMENT_STATUS)));
			work.setPaidSalary(Integer.parseInt(json.get(ServiceConstants.PAID_SALARY)));
			work.setDuesSalary(Integer.parseInt(json.get(ServiceConstants.DUES_SALARY)));
			work.setDeduction(Integer.parseInt(json.get(ServiceConstants.DEDUCTION)));
			
			
			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
			if (workService.workExists(work.getShopId())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Work already exist with given Shop Id");
			} else {
				boolean result = workService.createWork(work);
				response.put("status", Strings.EMPTY + result);
				response.put("description",
						"Work created successfully with given Shop Id , go through your inbox to activate");
			}
			return ResponseEntity.ok().body(response);
		}
}
