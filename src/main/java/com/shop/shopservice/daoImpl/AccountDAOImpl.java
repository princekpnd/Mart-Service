package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IAccountDAO;
import com.shop.shopservice.entity.Account;
import com.shop.shopservice.entity.Vender;
@Repository
@Transactional

public class AccountDAOImpl implements IAccountDAO{
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	
	@Override
	public List<Account> getAllAccount() {
		List<Account> accountList= entityManager.createNamedQuery("Account.findAll",Account.class).getResultList();
		return accountList;
	}
	
	@Override
	public Account getAccountById(int id) {
		return this.entityManager.find(Account.class, id);
	}
	
	@Override
	public List<Account> getAccountByUserId(int userId) {
		return this.entityManager.createNamedQuery("Account.findAccountByUserId", Account.class).setParameter("adminId", userId).getResultList();
	}
	
	@Override
	public List<Account> getAccountByShopId(String shopId) {
		List<Account> accountList =entityManager.createNamedQuery("Account.findAccountByShopId", Account.class).setParameter("shopId", shopId).getResultList();
		return  accountList;
	}
	
	@Override
	public Account getAccountByBankName(String bankName) {
			return (Account)entityManager.createNamedQuery("Account.findByBankName",Account.class).setParameter("bankName", bankName).getSingleResult();
	}

	@Override
	public List<Account> getAccountByMobileNumber(String mobileNo) {
		return this.entityManager.createNamedQuery("Account.findAccountByMobileNumber", Account.class).setParameter("mobileNo", mobileNo).getResultList();
	}
	
	@Override
	public List<Account> getAccountByAccountNumber(int accountNum) {
		return this.entityManager.createNamedQuery("Account.findAccountByAccountNumber", Account.class).setParameter("accountNum", accountNum).getResultList();
	}

	@Override
	public void updateAccount(Account account) {
	entityManager.merge(account);
	}
	
	@Override
	public boolean accountExists(int userId) {
		Account account = entityManager.createNamedQuery("Account.findByUserId",Account.class).setParameter("adminId", userId).getResultList().stream().findFirst().orElse(null);;
		return null != account ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addAccount(Account account) {
		entityManager.persist(account);
	}

	@Override
	public Account getByVenderId(int venderId) {
		return (Account)entityManager.createNamedQuery("Account.findByVenderId",Account.class).setParameter("venderId", venderId).getSingleResult();
	}

//	@Override
//	public Account getByVenderId(int venderId) {
//		Account account = entityManager.createNamedQuery("Account, findByVenderId",Vender.class).setParameter("venderId", venderId).getSingleResult();
//		return account;
//	}

	
	
}
