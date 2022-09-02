package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IItemListDAO;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.service.IItemListService;

@Transactional
@Repository
public class ItemListServiceImpl implements IItemListService{
	@Autowired
	IItemListDAO itemListDao;

	@Override
	public ItemList getById(int id) {
		return itemListDao.getById(id);
	}

	@Override
	public List<ItemList> getAllItemListByShopId(String shopId) {
	return itemListDao.getAllItemListByShopId(shopId);
	}

	@Override
	public boolean itemListExits(String slotList,String shopId, float unitQuantity, float unitSellingPrice,int productId, String barcode, float costPrice) {
		return itemListDao.itemListExits(slotList, shopId,unitQuantity, unitSellingPrice, productId, barcode, costPrice);
	}

	@Override
	public boolean itemListCreate(ItemList itemList) {
		if(itemListExits(itemList.getSlotList(), itemList.getShopId(),itemList.getUnitQuantity(), itemList.getUnitSellingPrice(),itemList.getProductId(), itemList.getBarcode(), itemList.getCostPrice())) {
			return false;
		}else {
			itemListDao.addItemList(itemList);
			return true;
		}
		
	}

	@Override
	public boolean updateItemList(ItemList itemList) {
	itemListDao.updateItemList(itemList);
	return true;
	}

	@Override
	public List<ItemList> getByProductId(int productId) {
		return itemListDao.getByProductId(productId);
	}

	@Override
	public List<ItemList> getAll() {
		return itemListDao.getAll();
	}

	@Override
	public boolean deleteItemList(int id) {
		return itemListDao.deleteItemList(id);
	}

	@Override
	public boolean addVariantBySlotNumber(ItemList itemList, String slotNumber) {
		itemListDao.addVariantBySlotNumber(itemList,slotNumber);
		return true;
	}

	@Override
	public List<ItemList> getByBarcode(String barcode) {
		return itemListDao.getByBarcode(barcode);
	}

	@Override
	public List<ItemList> getByShopId(String shopId) {
		return itemListDao.getByShopId(shopId);
	}

	@Override
	public List<ItemList> getAllByShopId(String shopId) {
	return itemListDao.getAllByShopId(shopId);
	}

	@Override
	public ItemList getAllDetailsById(int id) {
		return itemListDao.getAllDetailsById(id);
	}

	@Override
	public boolean checkExist(int productId, String shopId, float unitQuantity, String slotList) {
		return itemListDao.checkExist(productId, shopId,unitQuantity,slotList);
		
	}

	@Override
	public ItemList getForExcel(int productId, String shopId, float unitQuantity, String slotList) {
		return itemListDao.getForExcel(productId,shopId,unitQuantity,slotList);
	}

	@Override
	public List<ItemList> getAllOnlineVariant(boolean isOnline) {
		return itemListDao.getAllOnlineVariant(isOnline);
	}

	@Override
	public List<ItemList> getAllHotSellVariant(boolean hotSell) {
		return itemListDao.getAllHotSellVariant(hotSell);
	}

	@Override
	public List<ItemList> getAllBaggageVariant(boolean baggage) {
		return itemListDao.getAllBaggageVariant(baggage);
	}

	@Override
	public List<ItemList> getAllMilaanOfferVariant(boolean milaanOffer) {
		return itemListDao.getAllMilaanOfferVariant(milaanOffer);
	}

	@Override
	public List<ItemList> getAllAditionalDiscountVariant(boolean aditionalDiscount) {
		return itemListDao.getAllAditionalDiscountVariant(aditionalDiscount);
	}

	@Override
	public ItemList getDeActiveForExcel(int id) {
		return itemListDao.getDeActiveForExcel(id);
	}

	@Override
	public List<ItemList> getProductByProductId(int productId) {
		return itemListDao.getProductByProductId(productId);
	}

	@Override
	public List<ItemList> getAllOnlineVariantByShopId(String shopId, boolean isOnline) {
		return itemListDao.getAllOnlineVariantByShopId(shopId,isOnline);
	}

	@Override
	public ItemList getVariantByProductId(int productId) {
		return itemListDao.getVariantByProductId(productId);
		}

	@Override
	public List<ItemList> getAllOnlineProductByProductId(int productId, boolean isOnline) {
	return itemListDao.getAllOnlineProductByProductId(productId,isOnline);
	}

	@Override
	public ItemList getDeactiveById(int id) {
		return itemListDao.getDeactiveById(id);
	}

	@Override
	public List<ItemList> getByProductIdForDelete(int productId) {
		return itemListDao.getByProductIdForDelete(productId);
	}


}
