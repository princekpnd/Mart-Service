package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Purchase;

public interface IPurchaseDAO {
	
	List<Purchase> getAllPurchasePlan();
	
	Purchase getById(int id);
	
	List<Purchase> getPurchasePlanByPlanType(int planType);
	
	Purchase getActiveAdminByAdminId(int adminId);
	
	List<Purchase> getPurchasePlanByShopId(String shopId);
	
	 boolean purchaseExists(int adminId);
	 
	 void updatePurchase(Purchase purchase);
	 
	 void addPurchase(Purchase purchase);

}
