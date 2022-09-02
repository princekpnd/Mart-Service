package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IOfflineDAO;
import com.shop.shopservice.Idao.IOfflineProductListDAO;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;

@Transactional
@Repository
public class OfflineProductListServiceImpl implements IOfflineProductListService {
	@Autowired
	IOfflineProductListDAO offlineProductListDao;

	@Override
	public List<OfflineProductList> getAll() {
		return offlineProductListDao.getAll();
	}

	@Override
	public OfflineProductList getById(int id) {
		return offlineProductListDao.getById(id);
	}

	@Override
	public boolean offlineProductExists(String productName, String shopId, String brandName) {
		return offlineProductListDao.offlineProductExists(productName, shopId, brandName);
	}

	@Override
	public boolean offlineProductListExists(String productName, int offlineCartId, String brandName) {
		return offlineProductListDao.offlineProductListExists(productName, offlineCartId, brandName);
	}

	@Override
	public boolean offlineProductCreate(OfflineProductList offlineProductList) {
		if (null != offlineProductList.getProductId()) {
			if (checkExit(offlineProductList.getOfflineCartId(), offlineProductList.getProductId(), offlineProductList.getCashierId(), offlineProductList.isStockActiveInd())) {
				return false;
			} else {
				offlineProductListDao.addOfflineProductList(offlineProductList);
				return true;
			}
		} else {
			offlineProductListDao.addOfflineProductList(offlineProductList);
			return true;
		}

	}

	@Override
	public boolean updateOfflineProductList(OfflineProductList offlineProductList) {
		offlineProductListDao.updateOfflineProductList(offlineProductList);
		return true;
	}

	@Override
	public List<OfflineProductList> getByShopId(String shopId) {
		return offlineProductListDao.getByShopId(shopId);
	}

	@Override
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId) {
		return offlineProductListDao.getAllProductByCartId(offlineCartId);
	}

	@Override
	public Boolean checkExit(int offlineCartId, String productId, String cashierId, boolean stockActiveInd) {
		return offlineProductListDao.checkExit(offlineCartId, productId, cashierId, stockActiveInd);
	}

	@Override
	public boolean deleteOfflineProductList(int id) {
		return offlineProductListDao.deleteOfflineProductList(id);
	}

	@Override
	public OfflineProductList getDeactiveById(int id) {
		return offlineProductListDao.getDeactiveById(id);
	}

	@Override
	public OfflineProductList getByProductId(String productId) {
		return offlineProductListDao.getByProductId(productId);
	}

	@Override
	public OfflineProductList getByofflineCartIdAndProductId(int offlineCartId, String productId, boolean stockActiveInd) {
		return offlineProductListDao.getByofflineCartIdAndProductId(offlineCartId, productId, stockActiveInd);
	}

//	@Override
//	public List<OfflineProductList> getByShopIdAndCashierId(int offlineCartId, String cashierId) {
//		return offlineProductListDao.getByShopIdAndCashierId(offlineCartId, cashierId);
//	}

	@Override
	public List<OfflineProductList> getByShopIdAndCashierId(String cashierId, int offlineCartId) {
	return offlineProductListDao.getByShopIdAndCashierId(cashierId,offlineCartId);
	}

}
