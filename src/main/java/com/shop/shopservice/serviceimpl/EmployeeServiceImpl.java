package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IEmployeeDAO;
import com.shop.shopservice.entity.Employee;
import com.shop.shopservice.service.IEmployeeService;

@Transactional
@Repository

public class EmployeeServiceImpl implements IEmployeeService{
	@Autowired
	IEmployeeDAO employeeDao;
	@Override
	public List<Employee> getAllEmployee() {
		return employeeDao.getAllEmployee();
	}

	@Override
	public Employee getEmployee(int id) {
		return employeeDao.getEmployeeById(id);
	}
	@Override
	public List<Employee> getAllEmployees(int count) {
		return employeeDao.getAllEmployees(count);
	}
	
	@Override
	public List<Employee> getEmployeeByShopId(String shopId) {
		return employeeDao.getEmployeeByShopId(shopId);
	}
	@Override
	public List<Employee> getEmployeeByFirstName(String firstName) {
		return employeeDao.getEmployeeByFirstName(firstName);
	}

	@Override
	public Employee getEmployeeByEmailId(String emailId) {
		return employeeDao.getEmployeeByEmailId(emailId);
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);
		return true;
	}
	
	@Override
	public boolean employeeExists(String employeeId) {
		return employeeDao.employeeExists(employeeId);
	}
	
	public boolean createEmployee(Employee employee) {

		if (employeeExists(employee.getEmployeeId())) {
			return false;
		} else {
			employeeDao.addEmployee(employee);			
			return true;
		}
	}

	@Override
	public Employee getEmployeeByShopId(String shopId, int employeeId) {
		return employeeDao.getEmployeeByShopId(shopId, employeeId);
	}

	@Override
	public Employee getByShopEmployeeId(String shopId, String employeeId) {
		return employeeDao.getByShopEmployeeId(shopId, employeeId);
	}

	@Override
	public Employee getEmployeeByEmployeeId(String employeeId) {
		return employeeDao.getEmployeeByEmployeeId(employeeId);
	}

	@Override
	public boolean deleteEmployee(int id) {
		return employeeDao.deleteEmployee(id);
	}

	
}
