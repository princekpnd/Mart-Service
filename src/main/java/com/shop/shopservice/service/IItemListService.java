package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.ItemList;

public interface IItemListService {
ItemList getById(int id);

ItemList getDeActiveForExcel(int id);

ItemList getDeactiveById(int id);

List<ItemList> getAllOnlineVariantByShopId(String shopId, boolean isOnline);

List<ItemList> getAll();

List<ItemList> getAllItemListByShopId(String shopId);

List<ItemList> getProductByProductId(int productId);

ItemList getVariantByProductId(int productId);

List<ItemList> getAllOnlineVariant(boolean isOnline);

List<ItemList> getAllOnlineProductByProductId(int productId, boolean isOnline);

List<ItemList> getAllHotSellVariant( boolean hotSell);

List<ItemList> getAllBaggageVariant(boolean baggage);

List<ItemList> getAllMilaanOfferVariant(boolean milaanOffer);

List<ItemList> getAllAditionalDiscountVariant(boolean aditionalDiscount);

public List<ItemList> getByBarcode(String barcode);

public ItemList getAllDetailsById(int id);

public ItemList getForExcel(int productId,String shopId,float unitQuantity,String slotList);

public List<ItemList> getByProductId(int productId);

public List<ItemList> getByProductIdForDelete(int productId);

public List<ItemList> getByShopId(String shopId);

public List<ItemList> getAllByShopId(String shopId);

public boolean addVariantBySlotNumber(ItemList itemList, String slotNumber);

public boolean checkExist(int productId, String shopId,float unitQuantity, String slotList);

public boolean itemListExits(String slotList, String shopId, float unitQuantity, float unitSellingPrice,int productId, String barcode,float costPrice );

public boolean itemListCreate(ItemList itemList);

public boolean updateItemList(ItemList itemList);

public boolean deleteItemList(int id);
}
