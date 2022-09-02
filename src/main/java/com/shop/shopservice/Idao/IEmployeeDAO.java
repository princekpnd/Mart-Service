package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Employee;

public interface IEmployeeDAO {

	List<Employee> getAllEmployee();

	Employee getEmployeeById(int id);

	List<Employee> getAllEmployees(int count);

	List<Employee> getEmployeeByShopId(String shopId);
	
	Employee getEmployeeByEmployeeId(String employeeId);

	List<Employee> getEmployeeByFirstName(String firstName);

	Employee getEmployeeByEmailId(String emailId);

	void updateEmployee(Employee employee);

	boolean employeeExists(String employeeId);
	
	boolean deleteEmployee(int id);

	void addEmployee(Employee employee);

	public Employee getEmployeeByShopId(String shopId, int employeeId);

	public Employee getByShopEmployeeId(String shopId, String employeeId);
}
