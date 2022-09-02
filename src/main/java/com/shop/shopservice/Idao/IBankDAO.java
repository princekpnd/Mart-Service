package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Bank;

public interface IBankDAO {

	List<Bank> getAllBank();

	List<Bank> getBankByShopId(String shopId);

	Bank getBankById(int id);

	void updateBank(Bank bank);

	boolean bankExists(int accountNum);

	void addBank(Bank bank);

	boolean deleteBank(int id);

}
