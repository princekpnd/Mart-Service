package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.EmployeeAddress;

public interface IEmployeeAddressService {
	public List<EmployeeAddress> getAllEmployeeAddress();

	public List<EmployeeAddress> getAddressByShopId(String shopId);

	public EmployeeAddress getById(int id);

	public List<EmployeeAddress> getAddressByEmployeeId(String employeeId);

	public EmployeeAddress getEmployeeAddress(String employeeId);

	public boolean employeeAddressExists(String employeeId);

	public boolean createEmployeeAddress(EmployeeAddress employeeAddress);

	public boolean updateEmployeeAddress(EmployeeAddress employeeAddress);

	public boolean deleteEmployeeAddress(int id);

}
