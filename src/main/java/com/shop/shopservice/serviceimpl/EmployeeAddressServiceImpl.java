package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IEmployeeAddressDAO;
import com.shop.shopservice.entity.EmployeeAddress;
import com.shop.shopservice.service.IEmployeeAddressService;
@Transactional
@Repository
public class EmployeeAddressServiceImpl implements IEmployeeAddressService{
	
	@Autowired
	IEmployeeAddressDAO employeeAddressDao;

	@Override
	public List<EmployeeAddress> getAllEmployeeAddress() {
		return employeeAddressDao.getAllEmployeeAddress();
	}

	@Override
	public List<EmployeeAddress> getAddressByShopId(String shopId) {
		return employeeAddressDao.getAddressByShopId(shopId);
	}

	@Override
	public List<EmployeeAddress> getAddressByEmployeeId(String employeeId) {
		return employeeAddressDao.getAddressByEmployeeId(employeeId);
	}

	@Override
	public boolean employeeAddressExists(String employeeId) {
		return employeeAddressDao.employeeAddressExists(employeeId);
	}

	@Override
	public boolean createEmployeeAddress(EmployeeAddress employeeAddress) {
		if (employeeAddressExists(employeeAddress.getEmployeeId())) {
			return false;
		} else {

			employeeAddressDao.addEmployeeAddress(employeeAddress);
			employeeAddress = null;
			return true;
		}
	}

	@Override
	public EmployeeAddress getEmployeeAddress(String employeeId) {
		return employeeAddressDao.getEmployeeAddress(employeeId);
	}

	@Override
	public boolean updateEmployeeAddress(EmployeeAddress employeeAddress) {
		employeeAddressDao.updateEmployeeAddress(employeeAddress);
		return true;
	}

	@Override
	public EmployeeAddress getById(int id) {
	return employeeAddressDao.getById(id);
	}

	@Override
	public boolean deleteEmployeeAddress(int id) {
		return employeeAddressDao.deleteEmployeeAddress(id);
	}
	
}
