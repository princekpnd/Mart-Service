package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Withdraw;

public interface IWithdrawDAO {
	
	List<Withdraw> getAllWithdraw();
	
	List<Withdraw> getWithdrawByShopId(String shopId);
	
	List<Withdraw> getWithdrawByUserId(int userId);
	
	List<Withdraw> getWithdrawByShopIdAndUserId(String shopId,int userId);
	
	List<Withdraw> getActiveWithdraw(String shopId);
	
	List<Withdraw> checkActiveWithdraw(int adminId, boolean isActive);
	
	Withdraw getById(int id);
	
	void updateWithdraw(Withdraw withdraw);
	
	boolean withdrawExists(String accountNum, String accountHolderName, String bankName);
	
	void addWithdraw(Withdraw withdraw);

}
