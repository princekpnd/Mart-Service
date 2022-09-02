package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IAccountDAO;
import com.shop.shopservice.entity.Account;
import com.shop.shopservice.service.IAccountService;
@Transactional
@Repository

public class AccountServiceImpl implements IAccountService{
	
	@Autowired
	IAccountDAO accountDao;
	
	@Override
	public List<Account> getAllAccount() {
		return accountDao.getAllAccount();
	}
	
	@Override
	public Account getAccount(int id) {
		return accountDao.getAccountById(id);
	}

	
	@Override
	public List<Account> getAccountByUserId(int userId) {
		return accountDao.getAccountByUserId(userId);
	}
	
	@Override
	public Account getAccountByBankName(String bankName) {
		return accountDao.getAccountByBankName(bankName);
	}
	@Override
	public List<Account> getAccountByShopId(String shopId) {
		return accountDao.getAccountByShopId(shopId);
	}
	

	@Override
	public List<Account> getAccountByMobileNumber(String mobileNo) {
		return accountDao.getAccountByMobileNumber(mobileNo);
	}
	
	@Override
	public List<Account> getAccountByAccountNumber(int accountNum) {
		return accountDao.getAccountByAccountNumber(accountNum);
	}

	@Override
	public boolean updateAccount(Account account) {
		accountDao.updateAccount(account);
		return true;
	}
	
	@Override
	public boolean accountExists(int userId) {
		return accountDao.accountExists(userId);
	}
	
	public boolean createAccount(Account account) {

		if (accountExists(account.getAdminId())) {
			return false;
		} else {
			accountDao.addAccount(account);			
			return true;
		}
	}

	@Override
	public Account getByVenderId(int venderId) {
		return accountDao.getByVenderId(venderId);
	}

	

}
