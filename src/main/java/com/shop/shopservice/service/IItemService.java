package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Item;

public interface IItemService {
	
	List<Item> getAll();
	
	List<Item> getProductByShopIdAndId(int id, String shopId);
	
	List<Item> getByCategory(int category);
	
	List<Item> getByCategoryForDelete(int category);
	
	List<Item> getAllHotSale(boolean hotSell);
	
	List<Item> getAllBaggage(boolean baggage);
	
	List<Item> getAllMilaanOffer(boolean milaanOffer);
	
	List<Item> getAllAditionlDiscount(boolean aditionalDiscount);
	
	List<Item> getAllItemBySubCategory(int subCategoryId);
	
	List<Item> getAllItemBySubCategoryAndOnline(int subCategoryId,boolean isOnline);
	
	List<Item> getAllOnlineCategory(int category, boolean isOnline);
	
	List<Item> getAllOnlineByBrand(int brand,boolean isOnline);
	
	List<Item> getAllOnlineProductByShopId(String shopId, boolean isOnline);
	
	Item getById(int id);
	
	Item getDeActiveForExcel(int id);
	
	public Item getItemByTitle(String title, String shopId);
	
	public Item getItemForExcel(int brand,int category,String shopId,String  title);
	
	public Item getByName(String name);
	
	public  List<Item> getAllOnlineItem(boolean isOnline);
	
	public List<Item> getActiveProduct(String shopId);
	
	public List<Item> getItemByBrand(int brand);
	
	public List<Item> getAllItemByShopId(String shopId);
	
	public List<Item> getAllItem(String shopId);
	
	public Item getByNameAndShopId(String shopId, String  name);
	
	public List<Item> getAllProductByShopId(String shopId);
	
	public boolean itemExit(String title, String shopId,int brand,int category);
	
	public boolean createItem(Item item);
	
	public boolean updateItem(Item item);
	
	public boolean deleteProduct(int id);

	

}
