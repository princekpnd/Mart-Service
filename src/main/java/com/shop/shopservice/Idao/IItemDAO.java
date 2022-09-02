package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Item;

public interface IItemDAO {
	
	List<Item> getAll();
	
	Item getByName(String name);
	
	List<Item> getByCategory(int category);
	
	List<Item> getByCategoryForDelete(int category);
	
	List<Item> getAllHotSale(boolean hotSell);
	
	List<Item> getAllBaggage(boolean baggage);
	
	List<Item> getAllMilaanOffer(boolean milaanOffer);
	
	List<Item> getProductByShopIdAndId(int id, String shopId);
	
	List<Item> getAllItemBySubCategoryAndOnline(int subCategoryId, boolean isOnline);
	
	List<Item> getAllAditionlDiscount(boolean aditionalDiscount);
	
	List<Item> getAllItemBySubCategory(int subCategoryId);
	public List<Item> getAllOnlineItem(boolean isOnline);
	
	List<Item> getAllOnlineCategory(int category, boolean isOnline);
	
	List<Item> getAllOnlineByBrand(int brand, boolean isOnline);
	
	List<Item> getAllOnlineProductByShopId(String shopId, boolean isOnline);
	
	List<Item> getAllProductByShopId(String shopId);
	
	List<Item> getAllItemByShopId(String shopId);
	
	List<Item> getItemByBrand(int brand);
	
	List<Item> getActiveProduct(String shopId);
	
	Item getItemByTitle(String title, String shopId);
	
	Item getItemForExcel(int brand,int category,String shopId,String  title);
	
	Item getById(int id);
	
	Item getDeActiveForExcel(int id);
	
	List<Item> getAllItem(String shopId);
	
	Item getByNameAndShopId(String shopId,String name);
	
	boolean deleteProduct(int id);
	
	boolean itemExit(String title,String shopId,int brand,int category);
	
	void AddItem(Item item);
	
	public void indexBrand();
	
	public List<Item> searchItem(String keyword);
	
	void updateItem(Item item);

}
