package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IBankDAO;
import com.shop.shopservice.entity.Bank;
import com.shop.shopservice.service.IBankService;

@Transactional
@Repository


public class BankServiceImpl implements IBankService{
	
	@Autowired
	IBankDAO bankDao;
	
	@Override
	public List<Bank> getAllBank() {
		return bankDao.getAllBank();
	}
	
	@Override
	public Bank getBank(int id) {
		return bankDao.getBankById(id);
	}
	
	@Override
	public boolean updateBank(Bank bank) {
		bankDao.updateBank(bank);
		return true;
	}
	
	@Override
	public boolean bankExists(int accountNum) {
		return bankDao.bankExists(accountNum);
	}
	
	public boolean createBank(Bank bank) {

		if (bankExists(bank.getAccountNum())) {
			return false;
		} else {
			bankDao.addBank(bank);			
			return true;
		}
	}

	@Override
	public List<Bank> getBankByShopId(String shopId) {
		return bankDao.getBankByShopId(shopId);
	}

	@Override
	public boolean deleteBank(int id) {
		return bankDao.deleteBank(id);
	}
	
}
