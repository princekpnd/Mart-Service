package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Offline;

public interface IOfflineDAO {
	
	List<Offline> getAllOffline();
	
	Offline getByBillId(int billingId);
	
	Offline getAllBillByShopId(String shopId, String cashierId);
	
	List<Offline> getAllBillByUserName(String shopId, String userName);
	
	boolean offlineExists(String shopId, String productName);
	boolean cheakBillCreated(int billingId);
	
	List<Offline> getActiveBillByCashierId(String cashierId);
	
	void addOffline(Offline offline);
	
	List<Offline> getByShopId(String shopId);
	
	List<Offline> getDeactiveByShopId(String shopId);
	
	List<Offline> getAllOfflineByShopId(String shopId);
	
	List<Offline> getAllAmountByCashierId(String shopId, String cashierId);
	
	Offline checkDeactive(int billingId);
	
	Offline getByShopIdAndMobileNo(String shopId,String mobileNo);
	
	void updateOffline(Offline offline);

	Offline getByShopIdAndBillId(String shopId,int billingId);
	
	boolean offlineCheckExists(String shopId, String cashierId, boolean isActive);
}
