package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.OfflineProductList;

public interface IOfflineProductListService {
	List<OfflineProductList> getAll();
	
	public OfflineProductList getById(int id);
	
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId);
	
	public OfflineProductList getByofflineCartIdAndProductId(int offlineCartId,String  productId, boolean stockActiveInd);
	
	public OfflineProductList getByProductId(String productId);
	
	public OfflineProductList getDeactiveById(int id);
	
	public Boolean checkExit(int offlineCartId,String productId, String cashierId, boolean stockActiveInd);
	
	public List<OfflineProductList> getByShopId(String shopId);
	
	public List<OfflineProductList> getByShopIdAndCashierId(String cashierId, int offlineCartId);
	//public List<OfflineProductList> getByShopIdAndCashierId(int offlineCartId,String  cashierId);
	
	public boolean offlineProductExists( String productName, String shopId, String brandName);
	
	public boolean offlineProductListExists( String productName, int offlineCartId, String brandName);
	
	public boolean offlineProductCreate(OfflineProductList offlineProductList);
	
	public boolean updateOfflineProductList(OfflineProductList offlineProductList);
	
	public boolean deleteOfflineProductList(int id);
	
	

}
