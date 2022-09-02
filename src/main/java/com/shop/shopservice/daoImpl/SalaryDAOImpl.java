package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ISalaryDAO;
import com.shop.shopservice.entity.Salary;

@Repository
@Transactional

public class SalaryDAOImpl  implements ISalaryDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Salary> getAllSalary() {
		List<Salary> salaryList= entityManager.createNamedQuery("Salary.findAll",Salary.class).getResultList();
		return salaryList;
	}

	@Override
	public List<Salary> getAllSalaryByEmployeeId(String employeeId) {
		List<Salary> salaryList= entityManager.createNamedQuery("Salary.findSalaryByEmployeeId",Salary.class).setParameter("employeeId", employeeId).getResultList();
		return salaryList;
	}
	
	@Override
	public List<Salary> getSalaryByShopId(String shopId) {
		List<Salary> salaryList = entityManager.createNamedQuery("Salary.findSalaryByShopId",Salary.class).setParameter("shopId", shopId).getResultList();
		return salaryList;
	}
	
	@Override
	public boolean salaryExists(String employeeId) {
		Salary salary = entityManager.createNamedQuery("Salary.findByEmployeeId",Salary.class).setParameter("employeeId", employeeId).getResultList().stream().findFirst().orElse(null);;
		return null != salary ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addSalary(Salary salary) {
		entityManager.persist(salary);
}

	@Override
	public Salary getSalaryByEmployeeId(String employeeId) {
		Salary salary = entityManager.createNamedQuery("Salary.findAllSalary",Salary.class).setParameter("employeeId", employeeId).getSingleResult();
		return salary;
	}
	
	@Override
	public void updateSalary(Salary salary) {
		entityManager.merge(salary);
		
	}

	
}