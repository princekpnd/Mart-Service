package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Salary;

public interface ISalaryService {

	List<Salary> getAllSalary();

	public List<Salary> getAllSalaryByEmployeeId(String employeeId);

	public List<Salary> getSalaryByShopId(String shopId);

	public boolean salaryExists(String employeeId);

	public boolean createSalary(Salary salary);

	public Salary getSalary(String employeeId);

	public boolean updateSalary(Salary salary);

}
