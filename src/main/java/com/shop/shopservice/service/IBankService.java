package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Bank;

public interface IBankService {

	List<Bank> getAllBank();

	public List<Bank> getBankByShopId(String shopId);

	public boolean updateBank(Bank bank);

	public Bank getBank(int id);

	public boolean bankExists(int accountNum);

	public boolean createBank(Bank bank);

	public boolean deleteBank(int id);

}
