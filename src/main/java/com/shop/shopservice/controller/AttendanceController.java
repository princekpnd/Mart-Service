package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.shop.shopservice.entity.Attendance;
import com.shop.shopservice.entity.Employee;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Work;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IAttendanceService;
import com.shop.shopservice.service.IEmployeeService;
import com.shop.shopservice.service.IWorkService;

@RestController
@RequestMapping("/api/attendance")

public class AttendanceController {
	@Autowired
	private IAttendanceService attendanceService;
	@Autowired
	private IWorkService workService;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private ILookUp lookup;
	
	@Autowired
	private IEmployeeService employeeService;

	private final Logger log = LoggerFactory.getLogger(AttendanceController.class);

	@GetMapping("getallattendance")
	public ResponseEntity<List<Attendance>> getAllattendance() {
		List<Attendance> attendanceList = attendanceService.getAllAttendance();
		return new ResponseEntity<List<Attendance>>(attendanceList, HttpStatus.OK);
	}
	
	@GetMapping("getbyid/{id}")
	public ResponseEntity<Attendance> getById(@PathVariable("id") Integer id){
		Attendance attendance = attendanceService.getById(id.intValue());
		return new ResponseEntity<Attendance>(attendance, HttpStatus.OK);
		
	}

	@GetMapping("get/{employeeId}")
	public ResponseEntity<List<Attendance>> getAttendanceByEmployeeId(@PathVariable("employeeId") String employeeId) {
		List<Attendance> attendance = attendanceService.getByEmployeeId(employeeId);
		return new ResponseEntity<List<Attendance>>(attendance, HttpStatus.OK);
	}
	
	@GetMapping("getworkid/{workId}")
	public ResponseEntity<List<Attendance>> getAttendanceByWorkId(@PathVariable("workId") String workId){
		List<Attendance> attendanceList = attendanceService.getByWorkId(workId);
		return new ResponseEntity<List<Attendance>>(attendanceList, HttpStatus.OK);
	}
	

	@GetMapping("getbydate")
	public ResponseEntity<Map<String, String>> getAttendanceByDate() throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		Date today = null;
		List<Attendance> attendanceList = null;
		LocalDate date = LocalDate.now();

		String shopId = "AVI123", employeeId = "1";
		boolean attendance = attendanceService.attendanceExistsByEmployee(shopId, employeeId);

		if (attendance) {
			attendanceList = attendanceService.getByEmployeeId(employeeId);
			if (Integer.parseInt(attendanceList.get(0).getCreatedOn().toString().replace("-", "")) == Integer
					.parseInt(date.toString().replace("-", ""))) {
				int a = Integer.parseInt(date.toString().replace("-", ""));
				response.put("status", "" + date + a);
				response.put("description", " date.");
			} else {
				response.put("status", "False" + attendanceList.get(0).getCreatedOn().toString().substring(0, 4) + " / "
						+ date.toString().substring(0, 4));
				response.put("description", "Attendance already exist with given employeeId and date.");
			}

		} else {
			response.put("status", Strings.EMPTY + attendance + attendanceList);
			response.put("description", "Attendance does not exist.");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Attendance>> getAttendanceByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Attendance> attendanceList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {

			attendanceList = attendanceService.getByShopId(shopId);
		}
		return new ResponseEntity<List<Attendance>>(attendanceList, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createAttendance(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.EMPLOYEE_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.EMPLOYEE_ID) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.ATTENDANCE)) {
			Attendance attendance = new Attendance(json.get(ServiceConstants.EMPLOYEE_ID),
					(json.get(ServiceConstants.SHOP_ID)));
			Work work = new Work(json.get(ServiceConstants.SHOP_ID),(json.get(ServiceConstants.EMPLOYEE_ID)));
			String employeeId = json.get(ServiceConstants.EMPLOYEE_ID), shopId = json.get(ServiceConstants.SHOP_ID);
			int attendance1 = Integer.parseInt(json.get(ServiceConstants.ATTENDANCE));
			LocalDate date = LocalDate.now();
			
			LookUp lookUp = lookup.findLookUpById(attendance1);
			Employee employee = employeeService.getEmployeeByShopId(shopId, Integer.parseInt(employeeId));
			int salary = employee.getSalary();
			int salaryType = employee.getSalaryType(), workingDays = employee.getWorkingDays();
			LookUp absent = lookup.findLookUpIdByName("MILAAN", "ABSENT");
			LookUp present = lookup.findLookUpIdByName("MILAAN", "PRESENT");
			
			int perdaySalary = salary/workingDays;
			

			attendance.setEmployeeId(employeeId);
			attendance.setShopId(shopId);
			attendance.setAttendance(attendance1);
			attendance.setCreatedOn(new Date());
			attendance.setWorkId(json.get(ServiceConstants.WORK_ID));
			
			
			if(present.getLookUpId() == attendance1) {
				work.setPresent(1);
				work.setTotalSalary(perdaySalary);
			}
			
			if(absent.getLookUpId() == attendance1) {
				work.setAbsent(1);			
			}
			
			work.setSalary(salary);
			work.setSalaryType(salaryType);
			work.setUpdatedOn(new Date());
			work.setCreatedOn(new Date());
			work.setActive(true);
			work.setDeleted(false);
			

			response.put("employeeId", json.get(ServiceConstants.EMPLOYEE_ID));
			if (attendanceService.attendanceExistsByEmployee(attendance.getShopId(), attendance.getEmployeeId())) {
				List<Attendance> attendance2 = attendanceService.getByEmployeeId(employeeId);
				Attendance attendance3 = attendance2.get(attendance2.size() - 1);
				int today = Integer.parseInt(date.toString().replace("-", "")),
						attendanceDate = Integer.parseInt(attendance3.getCreatedOn().toString().replace("-", ""));
				if(today == attendanceDate) {
					work = workService.getActiveWork(shopId, employeeId, Boolean.TRUE);
					if(present.getLookUpId() == attendance3.getAttendance() && absent.getLookUpId() == attendance1) {
						work.setPresent(work.getPresent() - 1);
						work.setAbsent(work.getAbsent() + 1);
						work.setTotalSalary(work.getTotalSalary() - perdaySalary);
						work.setUpdatedOn(new Date());
						workService.updateWork(work);
						
						attendance3.setAttendance(attendance1);
					 	attendanceService.updateAttendance(attendance3);
					 	response.put("status", Boolean.TRUE.toString());
						response.put("description", "Attendance updated with given employeeId");
					} else if(absent.getLookUpId() == attendance3.getAttendance() && present.getLookUpId() == attendance1) {
						work.setPresent(work.getPresent() + 1);
						work.setAbsent(work.getAbsent() - 1);
						work.setTotalSalary(work.getTotalSalary() + perdaySalary);
						work.setUpdatedOn(new Date());
						workService.updateWork(work);
						
						attendance3.setAttendance(attendance1);
					 	attendanceService.updateAttendance(attendance3);
					 	response.put("status", Boolean.TRUE.toString());
						response.put("description", "Attendance updated with given employeeId");	
					}
				 					
				} else {
					work = workService.getActiveWork(shopId, employeeId, Boolean.TRUE);
					if(present.getLookUpId() == attendance1) {
						work.setPresent(work.getPresent() + 1);	
						work.setTotalSalary(work.getTotalSalary() + perdaySalary);			
					}
					
					if(absent.getLookUpId() == attendance1) {
						work.setAbsent(work.getAbsent() + 1);			
					}
					work.setUpdatedOn(new Date());
					workService.updateWork(work);
					
					boolean result = attendanceService.createAttendance(attendance);
					response.put("status", Strings.EMPTY + result);
					response.put("description",
							"Attendance created successfully with given employeeId.");
				}				
			} else {
				boolean workResult  = workService.createWork(work); 
				boolean result = attendanceService.createAttendance(attendance);
				response.put("status", Strings.EMPTY + result);
				response.put("description",
						"Attendance created successfully with given employeeId.");
				
			}
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.EMPLOYEE_ID)
				&& null != attendanceService.getAttendance(json.get(ServiceConstants.EMPLOYEE_ID))) {
			Attendance attendance = attendanceService.getAttendance(json.get(ServiceConstants.EMPLOYEE_ID));
			// if (null != json.get(ServiceConstants.EMPLOYEE_ID) && null !=
			// attendanceService.getAttendance(json.get(ServiceConstants.EMPLOYEE_ID))) {
			// Attendance Attendance =
			// attendanceService.getAttendance(json.get(ServiceConstants.EMPLOYEE_ID));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				attendance.setId(id);

			}

			if (null != json.get(ServiceConstants.EMPLOYEE_ID)) {
				String employeeId = (json.get(ServiceConstants.EMPLOYEE_ID));
				System.out.println(employeeId);
				attendance.setEmployeeId(employeeId);

			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = (json.get(ServiceConstants.SHOP_ID));
				System.out.println(shopId);
				attendance.setShopId(shopId);
			}
			if(null != json.get(ServiceConstants.WORK_ID)) {
				String workId = json.get(ServiceConstants.WORK_ID);
				attendance.setWorkId(workId);
			}

//				if(null !=json.get(ServiceConstants.CREATED_ON)) {
//					Date createdOn =new Date(json.get(ServiceConstants.CREATED_ON).toString());
//					System.out.println(createdOn);
//					attendance.setCreatedOn(createdOn);
//				}
//				
//				if(null != json.get(ServiceConstants.ATTENDANCE)) {
//					int  attendance = Integer.parseInt(json.get(ServiceConstants.ATTENDANCE));
//					System.out.println( attendance);
//					attendance.setAttendance( attendance);
//					
//					}

			attendanceService.updateAttendance(attendance);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Attendance updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Attendance doesn't exist with given employeeId or userid");

		}
		return ResponseEntity.ok().body(response);

	}
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteAttendance(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Attendance attendance = attendanceService.getById(id);
		
		if(null != attendance) {
			boolean result = attendanceService.deleteAttendance(id);
			if(result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description","Attendance delete with given id:" +id);
			}
			}else {
				response.put("status",Boolean.FALSE.toString());
				response.put("description", "Attendance does not exist with given  Id");
			
		}
		return ResponseEntity.ok().body(response);
	}

}
