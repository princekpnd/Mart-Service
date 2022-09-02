package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;

public interface IItemListDAO {
	
	ItemList getById(int id);
	
	ItemList getAllDetailsById(int id);
	
	ItemList getDeActiveForExcel(int id);
	
	List<ItemList> getProductByProductId(int productId);
	
	List<ItemList> getAllOnlineVariantByShopId(String shopId, boolean isOnline);
	
	ItemList getVariantByProductId(int productId);
	
	ItemList getDeactiveById(int id);
	
	List<ItemList> getByProductIdForDelete(int productId);
	
	List<ItemList> getAllOnlineProductByProductId(int productId,boolean isOnline);
	
	List<ItemList> getAllItemListByShopId(String shopId);
	
	List<ItemList> getByProductId(int productId);
	
	List<ItemList> getAll();
	
	List<ItemList> getByShopId(String shopId);
	
	List<ItemList> getAllOnlineVariant(boolean isOnline);
	
	List<ItemList> getAllHotSellVariant(boolean hotSell);
	
	List<ItemList> getAllBaggageVariant(boolean baggage);
	
	List<ItemList> getAllMilaanOfferVariant(boolean milaanOffer);
	
	List<ItemList> getAllAditionalDiscountVariant(boolean aditionalDiscount);
	
	List<ItemList> getAllByShopId(String shopId);
	
	boolean deleteItemList(int id);
	
	List<ItemList> getByBarcode(String barcode);
	
	void addVariantBySlotNumber(ItemList itemList, String slotNumber);
	
	boolean checkExist(int productId, String shopId, float unitQuantity,String slotList);
	
	ItemList getForExcel(int productId,String shopId, float unitQuantity, String slotList);
	
	boolean itemListExits(String slotList,String shopId,float unitQuantity, float unitSellingPrice,int productId, String barcode, float costPrice);
	
	void addItemList(ItemList itemList);
	
	void updateItemList(ItemList itemList);

}
