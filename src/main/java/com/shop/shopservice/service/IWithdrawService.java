package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Withdraw;

public interface IWithdrawService {
	List<Withdraw> getAllWithdraw();
	
	public Withdraw getById(int id);
	
	public List<Withdraw> getWithdrawByShopId(String shopId);
	
	public List<Withdraw> getWithdrawByUserId(int userId);
	
	public List<Withdraw> checkActiveWithdraw(int adminId, boolean isActive);
	
	public List<Withdraw> getActiveWithdraw(String shopId);
	
	public List<Withdraw> getWithdrawByShopIdAndUserId(String shopId, int userId);
	
	public boolean updateWithdraw(Withdraw withdraw);
	
	public boolean withdrawExists(String accountNum, String accountHolderName , String bankName);
	
	public boolean createWithdraw(Withdraw withdraw);
	
	

}
