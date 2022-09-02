package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Account;

public interface IAccountDAO {

	List<Account> getAllAccount();

	Account getAccountById(int id);

	List<Account> getAccountByUserId(int userId);
	
	Account getByVenderId(int venderId);

	Account getAccountByBankName(String bankName);

	List<Account> getAccountByShopId(String shopId);

	List<Account> getAccountByMobileNumber(String mobileNo);

	List<Account> getAccountByAccountNumber(int accountNum);

	void updateAccount(Account account);

	boolean accountExists(int userId);

	void addAccount(Account account);

}
