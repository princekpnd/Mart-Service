package com.shop.shopservice.serviceimpl;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IPurchaseDAO;
import com.shop.shopservice.entity.Purchase;
import com.shop.shopservice.service.IPurchaseService;

@Transactional
@Repository
public class PurchaseServiceImpl  implements IPurchaseService{

	@Autowired
	IPurchaseDAO purchaseDao;

	@Override
	public List<Purchase> getAllPurchasePlan() {
		return purchaseDao.getAllPurchasePlan();
	}

	@Override
	public List<Purchase> getPurchasePlanByPlanType(int planType) {
		return purchaseDao.getPurchasePlanByPlanType(planType);
	}

	@Override
	public List<Purchase> getPurchasePlanByShopId(String shopId) {
		return purchaseDao.getPurchasePlanByShopId(shopId);
	}

	@Override
	public boolean purchaseExists(int adminId) {
		return  purchaseDao.purchaseExists(adminId);
	}

	@Override
	public boolean createPurchase(Purchase purchase) {
//		if(purchaseExists(purchase.getAdminId())) {
//			return false;
//		}else {
			purchaseDao.addPurchase(purchase);
			purchase = null;
			return true;
//		}
		
	}

	@Override
	public Purchase getById(int id) {
		return purchaseDao.getById(id);
	}

	@Override
	public Purchase getActiveAdminByAdminId(int adminId) {
		return purchaseDao.getActiveAdminByAdminId(adminId);
	}

	@Override
	public boolean updatePurchase(Purchase purchase) {
		purchaseDao.updatePurchase(purchase);
		return true;
	}

	

}
