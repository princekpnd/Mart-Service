package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Salary;

public interface ISalaryDAO {

	List<Salary> getAllSalary();

	List<Salary> getAllSalaryByEmployeeId(String employeeId);

	List<Salary> getSalaryByShopId(String shopId);

	boolean salaryExists(String employeeId);

	void addSalary(Salary salary);

	Salary getSalaryByEmployeeId(String employeeId);

	void updateSalary(Salary salary);

}
