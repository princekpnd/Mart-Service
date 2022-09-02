package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IItemDAO;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.service.IItemService;

@Transactional
@Repository
public class ItemServiceImpl implements IItemService{
	@Autowired
	IItemDAO itemDao;

	@Override
	public List<Item> getAll() {
		return itemDao.getAll();
	}

	@Override
	public Item getByName(String name) {
		return itemDao.getByName(name);
	}

	@Override
	public List<Item> getAllProductByShopId(String shopId) {
	return itemDao.getAllProductByShopId(shopId);
	}

	@Override
	public Item getById(int id) {
	return itemDao.getById(id);
	}

	@Override
	public boolean itemExit(String title, String shopId, int brand, int category) {
		return itemDao.itemExit(title, shopId, brand, category);
		
	}

	@Override
	public boolean createItem(Item item) {
	if(itemExit(item.getTitle(), item.getShopId(), item.getBrand(), item.getCategory())) {
		return false;
	}else {
		itemDao.AddItem(item);
		return true;
	}
		
	}

	@Override
	public boolean updateItem(Item item) {
	 itemDao.updateItem(item);
	 return true;
	}

	@Override
	public List<Item> getAllItem(String shopId) {
		return itemDao.getAllItem(shopId);
	}

	@Override
	public Item getByNameAndShopId(String shopId, String name) {
		return itemDao.getByNameAndShopId(shopId, name);
	}

	@Override
	public boolean deleteProduct(int id) {
	return itemDao.deleteProduct(id);
	}

	@Override
	public List<Item> getAllItemByShopId(String shopId) {
	return itemDao.getAllItemByShopId(shopId);
	}

	@Override
	public List<Item> getItemByBrand(int brand) {
		return itemDao.getItemByBrand(brand);
	}

	@Override
	public List<Item> getActiveProduct(String shopId) {
	return itemDao.getActiveProduct(shopId);
	}

	@Override
	public Item getItemByTitle(String title, String shopId) {
		return itemDao.getItemByTitle(title, shopId);
	}

	@Override
	public Item getItemForExcel(int brand, int category, String shopId, String title) {
		return itemDao.getItemForExcel(brand,category,shopId, title);
	}

	@Override
	public Item getDeActiveForExcel(int id) {
	return itemDao.getDeActiveForExcel(id);
	}

	@Override
	public List<Item> getByCategory(int category) {
	return itemDao.getByCategory(category);
	}

	@Override
	public List<Item> getAllOnlineItem(boolean isOnline) {
		return itemDao.getAllOnlineItem(isOnline);
	}

	@Override
	public List<Item> getAllOnlineCategory(int category, boolean isOnline) {
		return itemDao.getAllOnlineCategory(category,isOnline);
	}

	@Override
	public List<Item> getAllOnlineByBrand(int brand, boolean isOnline) {
		return itemDao.getAllOnlineByBrand(brand,isOnline);
	}

	@Override
	public List<Item> getAllOnlineProductByShopId(String shopId, boolean isOnline) {
		return itemDao.getAllOnlineProductByShopId(shopId,isOnline);
	}

	@Override
	public List<Item> getAllItemBySubCategory(int subCategoryId) {
		return itemDao.getAllItemBySubCategory(subCategoryId);
		}

	@Override
	public List<Item> getAllHotSale(boolean hotSell) {
	return itemDao.getAllHotSale(hotSell);
	}

	@Override
	public List<Item> getAllBaggage(boolean baggage) {
	return itemDao.getAllBaggage(baggage);
	}

	@Override
	public List<Item> getAllMilaanOffer(boolean milaanOffer) {
		return itemDao.getAllMilaanOffer(milaanOffer);
	}

	@Override
	public List<Item> getAllAditionlDiscount(boolean aditionalDiscount) {
		return itemDao.getAllAditionlDiscount(aditionalDiscount);
	}

	@Override
	public List<Item> getProductByShopIdAndId(int id, String shopId) {
		return itemDao.getProductByShopIdAndId(id, shopId);
	}

	@Override
	public List<Item> getAllItemBySubCategoryAndOnline(int subCategoryId, boolean isOnline) {
		return itemDao.getAllItemBySubCategoryAndOnline(subCategoryId, isOnline);
	}

	public List<Item> getByCategoryForDelete(int category) {
		return itemDao.getByCategoryForDelete(category);
	}

	


}
