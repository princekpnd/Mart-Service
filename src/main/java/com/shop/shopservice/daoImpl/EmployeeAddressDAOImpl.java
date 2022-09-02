package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IEmployeeAddressDAO;
import com.shop.shopservice.entity.EmployeeAddress;

@Repository
@Transactional
public class EmployeeAddressDAOImpl   implements IEmployeeAddressDAO{
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<EmployeeAddress> getAllEmployeeAddress() {
		List<EmployeeAddress> employeeAddressList = entityManager.createNamedQuery("EmployeeAddress.findAllEmployeeAddress",EmployeeAddress.class).getResultList();
		return employeeAddressList;
	}

	@Override
	public List<EmployeeAddress> getAddressByShopId(String shopId) {
		List<EmployeeAddress> employeeAddressList = entityManager.createNamedQuery("EmployeeAddress.findbyShopId",EmployeeAddress.class).setParameter("shopId", shopId).getResultList();
		return employeeAddressList;
	}

	@Override
	public List<EmployeeAddress> getAddressByEmployeeId(String employeeId) {
		List<EmployeeAddress> employeeAddressList = entityManager.createNamedQuery("EmployeeAddress.findByEmployeeId",EmployeeAddress.class).setParameter("employeeId", employeeId).getResultList();
		return employeeAddressList;
	}

	@Override
	public boolean employeeAddressExists(String employeeId) {
		EmployeeAddress employeeAddress = entityManager.createNamedQuery("EmployeeAdderss.createAddressByEmployeeId",EmployeeAddress.class).setParameter("employeeId", employeeId).getResultList().stream().findFirst().orElse(null);
			return null != employeeAddress ?Boolean.TRUE:Boolean.FALSE;
		
	}

	@Override
	public void addEmployeeAddress(EmployeeAddress employeeAddress) {
			entityManager.persist(employeeAddress);
			
		}

	@Override
	public EmployeeAddress getEmployeeAddress(String employeeId) {
		EmployeeAddress employeeAddress = entityManager.createNamedQuery("EmployeeAddress.findAddressByEmployeeId",EmployeeAddress.class).setParameter("employeeId", employeeId).getSingleResult();
		return employeeAddress;
	}

	@Override
	public void updateEmployeeAddress(EmployeeAddress employeeAddress) {
		entityManager.merge(employeeAddress);
		
	}

	@Override
	public EmployeeAddress getById(int id) {     
		return this.entityManager.find(EmployeeAddress.class, id);
	}

	@Override
	public boolean deleteEmployeeAddress(int id) {
		Query query = entityManager.createQuery("delete EmployeeAddress where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}
	
	}

