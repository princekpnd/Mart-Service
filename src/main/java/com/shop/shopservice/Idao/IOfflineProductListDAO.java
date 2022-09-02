package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.OfflineProductList;

public interface IOfflineProductListDAO {
	
	List<OfflineProductList> getAll();
	
	List<OfflineProductList> getAllProductByCartId(int offlineCartId);
	
	Boolean checkExit(int offlineCartId,String productId, String cashierId, boolean stockActiveInd);
	
	OfflineProductList getById(int id);
	
	OfflineProductList getByProductId(String productId);
	
	OfflineProductList getByofflineCartIdAndProductId(int offlineCartId, String productId, boolean stockActiveInd);
	
	
	//List<OfflineProductList> getByShopIdAndCashierId(int offlineCartId, String cashierId);
	
	List<OfflineProductList> getByShopIdAndCashierId(String cashierId, int offlineCartId);
	
	boolean offlineProductExists(String productName, String shopId, String brandName);
	
	boolean offlineProductListExists(String productName, int offlineCartId, String brandName);
	
	void addOfflineProductList(OfflineProductList offlineProductList);
	
	void updateOfflineProductList(OfflineProductList offlineProductList);
	
	List<OfflineProductList> getByShopId(String shopId);
	
	boolean deleteOfflineProductList(int id);
	
	OfflineProductList getDeactiveById(int id);

}
