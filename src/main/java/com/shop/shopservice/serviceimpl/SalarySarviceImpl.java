package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ISalaryDAO;
import com.shop.shopservice.entity.Salary;
import com.shop.shopservice.service.ISalaryService;

@Repository
@Transactional
public  class SalarySarviceImpl implements ISalaryService{
	
	@Autowired
	ISalaryDAO salaryDao;
	
	@Override
	public List<Salary> getAllSalary() {
		return salaryDao.getAllSalary();
	}

	@Override
	public List<Salary> getAllSalaryByEmployeeId(String employeeId) {
		return salaryDao.getAllSalaryByEmployeeId(employeeId);
	}
	
	@Override
	public List<Salary> getSalaryByShopId(String shopId) {
		return salaryDao.getSalaryByShopId(shopId);
	}
	
	@Override
	public boolean salaryExists(String employeeId) {
		return salaryDao.salaryExists( employeeId);
	}
	
	public boolean createSalary(Salary salary) {

		if (salaryExists(salary.getEmployeeId())) {
			return false;
		} else {

			salaryDao.addSalary(salary);
			salary = null;
			return true;
		}
	}

	@Override
	public Salary getSalary(String employeeId) {
		return salaryDao.getSalaryByEmployeeId(employeeId) ;
	}


	@Override
	public boolean updateSalary(Salary salary) {
		 salaryDao.updateSalary(salary);
		 return true;
	}

	
}
