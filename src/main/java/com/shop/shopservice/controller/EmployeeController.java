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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Employee;
import com.shop.shopservice.entity.EmployeeAddress;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IEmployeeAddressService;
import com.shop.shopservice.service.IEmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IEmployeeAddressService employeeAddressService;

	@GetMapping("getallemployee")
	public ResponseEntity<List<Employee>> getAllemployee() {
		List<Employee> employeeList = employeeService.getAllEmployee();
		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@GetMapping("get/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
		Employee employee = employeeService.getEmployee(employeeId.intValue());
		List<EmployeeAddress>  employeeAddressList = employeeAddressService.getAddressByEmployeeId(employeeId.toString());
		employee.setEmployeeAddress(employeeAddressList);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@GetMapping("getbyemployeeid/{employeeId}")
	public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable("employeeId") String employeeId){
		Employee employee = employeeService.getEmployeeByEmployeeId(employeeId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@GetMapping("get/byshopid/{shopId}/{employeeId}")
	public ResponseEntity<Employee> getEmployeeByShopId(@PathVariable("shopId") String shopId, @PathVariable("employeeId") int employeeId) {
		Employee employee = employeeService.getEmployeeByShopId(shopId, employeeId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}



	@GetMapping("getemployeebyname/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
		List<Employee> employeeList = employeeService.getEmployeeByFirstName(firstName);
		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@GetMapping("getemployeebyshopId/{shopId}")
	public ResponseEntity<List<Employee>> getEmployeeByShopId(@PathVariable("shopId") String shopId) {
		List<Employee> employeeList = null;
		Admin admin = adminService.getAdminByShopId(shopId);
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			employeeList = employeeService.getEmployeeByShopId(shopId);
		}
		return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
	}

	@GetMapping("getbyemail/{emailId}")
	public ResponseEntity<Employee> getEmployeeByEmailId(@PathVariable("emailId") String emailId) {
		Employee employee = employeeService.getEmployeeByEmailId(emailId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	
	

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateEmployee(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != employeeService.getEmployee(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Employee employee = employeeService.getEmployee(Integer.parseInt(json.get(ServiceConstants.ID)));
			// if (null != json.get(ServiceConstants.SHOP_ID) && null !=
			// employeeService.getEmployee(Integer.parseInt(json.get(ServiceConstants.SHOP_ID))))
			// {
			// Employee mployee =
			// employeeService.getEmployee(Integer.parseInt(json.get(ServiceConstants.SHOP_ID)));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID).toString());
				employee.setId(id);
			}
			if (null != json.get(ServiceConstants.F_NAME)) {
				String firstName = json.get(ServiceConstants.F_NAME).toString();
				employee.setFirstName(firstName);
			}
		

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME).toString();
				employee.setLastName(lastName);
			}

			if (null != json.get(ServiceConstants.FATHER)) {
				String father = json.get(ServiceConstants.FATHER).toString();
				employee.setFather(father);
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				employee.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = json.get(ServiceConstants.ADHAR_NUM).toString();
				employee.setAdharNumber(adharNumber);
			}

			if (null != json.get(ServiceConstants.ADHAR_AVATAR)) {
				String adharAvatar = json.get(ServiceConstants.ADHAR_AVATAR).toString();
				employee.setAdharAvatar(adharAvatar);
			}

			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM).toString();
				employee.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.PAN_AVATAR)) {
				String panAvatar = json.get(ServiceConstants.PAN_AVATAR).toString();
				employee.setPanAvatar(panAvatar);
			}

			if (null != json.get(ServiceConstants.SALARY)) {
				int salary = Integer.parseInt(json.get(ServiceConstants.SALARY).toString());
				employee.setSalary(salary);
			}

			if (null != json.get(ServiceConstants.SALARY_TYPE)) {
				int salaryType = Integer.parseInt(json.get(ServiceConstants.SALARY_TYPE).toString());
				employee.setSalaryType(salaryType);
			}

			if (null != json.get(ServiceConstants.JOINING_DATE)) {
				Date joiningDate = new Date(json.get(ServiceConstants.JOINING_DATE).toString());
				System.out.println(joiningDate);
				employee.setJoiningDate(joiningDate);
			}

			if (null != json.get(ServiceConstants.WORK_DAYS)) {
				int workingDays = Integer.parseInt(json.get(ServiceConstants.WORK_DAYS));
				employee.setWorkingDays(workingDays);
			}

			if (null != json.get(ServiceConstants.DESIGNATION)) {
				String designation = json.get(ServiceConstants.DESIGNATION).toString();
				employee.setDesignation(designation);
			}

			if (null != json.get(ServiceConstants.EMPLOYEE_ID)) {
				String employeeId = json.get(ServiceConstants.EMPLOYEE_ID).toString();
				employee.setEmployeeId(employeeId);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER).toString();
				employee.setMobileNo(mobileNo);
			}

			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID).toString();
				employee.setEmailId(emailId);
			}

//				if(null != json.get(ServiceConstants.CREATED_ON)) {
//					Date createdOn =new Date(json.get(ServiceConstants.CREATED_ON).toString());
//					employee.setCreatedOn(createdOn);				
//					}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
				employee.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
				employee.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.STREET)) {
				String street = json.get(ServiceConstants.STREET).toString();
				employee.setStreet(street);
			}

			if (null != json.get(ServiceConstants.LEAVING_DATE)) {
				Date leavingDate = new Date();
				System.out.println(leavingDate);
				employee.setLeavingDate(leavingDate);
			}
			if (null != json.get(ServiceConstants.VOTER_ID)) {
				int voterId = Integer.parseInt(json.get(ServiceConstants.VOTER_ID));
				System.out.println(voterId);
				employee.setVoterId(voterId);
			}
			
			if(null != json.get(ServiceConstants.GENDER)) {
				int gender = Integer.parseInt(json.get(ServiceConstants.GENDER).toString());
				employee.setGender(gender);
			}

			employeeService.updateEmployee(employee);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Employee updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Employee doesn't exist with given  userid");
		}

		return ResponseEntity.ok().body(response);
	}
	

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createEmployee(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.EMPLOYEE_ID));
		Map<String, String> response = new HashMap<String, String>();
		Employee employee = new Employee(json.get(ServiceConstants.EMPLOYEE_ID), json.get(ServiceConstants.SHOP_ID));
		
		String employeeId = json.get(ServiceConstants.EMPLOYEE_ID), shopId = json.get(ServiceConstants.SHOP_ID);

		employee.setFirstName(json.get(ServiceConstants.F_NAME));
		employee.setLastName(json.get(ServiceConstants.L_NAME));
		employee.setFather(json.get(ServiceConstants.FATHER));
		employee.setShopId(json.get(ServiceConstants.SHOP_ID));
		employee.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
		
		employee.setSalary(Integer.parseInt(json.get(ServiceConstants.SALARY)));
		employee.setSalaryType(Integer.parseInt(json.get(ServiceConstants.SALARY_TYPE)));
		employee.setWorkingDays(Integer.parseInt(json.get(ServiceConstants.WORK_DAYS)));
		employee.setDesignation(json.get(ServiceConstants.DESIGNATION));
		employee.setEmployeeId(json.get(ServiceConstants.EMPLOYEE_ID));
		
		employee.setStreet(json.get(ServiceConstants.STREET));
		employee.setGender(Integer.parseInt(json.get(ServiceConstants.GENDER)));
		
		if (null != json.get(ServiceConstants.EMAIL_ID)) {
			employee.setEmailId(json.get(ServiceConstants.EMAIL_ID));
		}
		
		if (null != json.get(ServiceConstants.VOTER_ID)) {
			employee.setVoterId(Integer.parseInt(json.get(ServiceConstants.VOTER_ID)));
		}
		
		if (null != json.get(ServiceConstants.ADHAR_NUM)) {
			employee.setAdharNumber(json.get(ServiceConstants.ADHAR_NUM));
		}
		
		if (null != json.get(ServiceConstants.PAN_NUM)) {
			employee.setPanNumber(json.get(ServiceConstants.PAN_NUM));
		}

		employee.setJoiningDate(new Date());
		employee.setCreatedOn(new Date());
		employee.setDeleted(Boolean.FALSE);
		employee.setActive(Boolean.TRUE);

		response.put("employeeId", json.get(ServiceConstants.EMPLOYEE_ID));
		if (employeeService.employeeExists(employee.getEmployeeId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Employee already exist with given Employee Id");
		} else {
			boolean result = employeeService.createEmployee(employee);
			Employee emp = employeeService.getByShopEmployeeId(shopId, employeeId);
			response.put("id", Strings.EMPTY + emp.getId());
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Employee created successfully with given Employee Id,");
		}
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Employee employee = employeeService.getEmployee(id);
		
		if(null != employee) {
			boolean result = employeeService.deleteEmployee(id);
			if(result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description","Employee delete with given employeeId:" +id);
			}
			}else {
				response.put("status",Boolean.FALSE.toString());
				response.put("description", "Employee does not exist with given Employee Id");
			
		}
		return ResponseEntity.ok().body(response);
	}

}
