package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Purchase;
import com.shop.shopservice.entity.Review;

public interface IPurchaseService {

	List<Purchase> getAllPurchasePlan();
	
	public Purchase getById(int id);
	
	public List<Purchase> getPurchasePlanByPlanType(int planType);
	
	public List<Purchase> getPurchasePlanByShopId(String shopId);
	
	public Purchase getActiveAdminByAdminId(int adminId);
	
	 public boolean purchaseExists(int adminId);
	 
	 public boolean createPurchase(Purchase purchase);
	 
	 public boolean updatePurchase(Purchase purchase);
}
