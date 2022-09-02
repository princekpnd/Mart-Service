package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IBankDAO;
import com.shop.shopservice.entity.Bank;

@Repository
@Transactional

public class BankDAOImpl implements IBankDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	@Override
	public List<Bank> getAllBank() {
		List<Bank> profileList= entityManager.createNamedQuery("Bank.findAll",Bank.class).getResultList();
		return profileList;
	}
	
	@Override
	public Bank getBankById(int id) {
		return this.entityManager.find(Bank.class, id);
	}
	@Override
	public void updateBank(Bank bank) {
	entityManager.merge(bank);
	}
	
	@Override
	public boolean bankExists(int accountNum) {
		Bank bank = entityManager.createNamedQuery("Bank.findByUserAccountNumber",Bank.class).setParameter("accountNum", accountNum).getResultList().stream().findFirst().orElse(null);;
		return null != bank ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addBank(Bank bank) {
		entityManager.persist(bank);
	}

	@Override
	public List<Bank> getBankByShopId(String shopId) {
	List<Bank> bankList = entityManager.createNamedQuery("Bank.findByShopId",Bank.class).setParameter("ShopId", shopId).getResultList();
		return bankList;
	}

	@Override
	public boolean deleteBank(int id) {
		Query query = entityManager.createQuery("delete Bank where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

}
