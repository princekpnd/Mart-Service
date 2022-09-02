package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IOfflineDAO;
import com.shop.shopservice.Idao.IPlanDAO;
import com.shop.shopservice.entity.Offline;
import com.shop.shopservice.service.IOfflineService;
import com.shop.shopservice.service.IPlanService;

@Transactional
@Repository
public class OfflineServiceImpl implements IOfflineService{

	@Autowired
	IOfflineDAO  offlineDao;

	@Override
	public List<Offline> getAllOffline() {
		return offlineDao.getAllOffline();
	}

	@Override
	public Offline getByBillId(int billingId) {
		return offlineDao.getByBillId(billingId);
	}

	@Override
	public Offline getAllBillByShopId(String shopId, String cashierId) {
		return offlineDao.getAllBillByShopId(shopId, cashierId);
	}

	@Override
	public List<Offline> getAllBillByUserName(String shopId, String userName) {
		return offlineDao.getAllBillByUserName(shopId, userName);
	}

	@Override
	public boolean offlineExists(String shopId, String productName) {
		return offlineDao.offlineExists(shopId, productName);
	}

	@Override
	public boolean offlineCreate(Offline offline) {
		if(offlineExists(offline.getShopId(), offline.getProductName())) {
			return false;
		}else {
			offlineDao.addOffline(offline);
			return true;
		}
		
	}

	@Override
	public boolean updateOffline(Offline offline) {
		 offlineDao.updateOffline(offline);
		 return true;
	}
	
	@Override
	public Offline getByShopIdAndBillId(String shopId, int billingId) {
	return offlineDao.getByShopIdAndBillId(shopId, billingId);
	}
	
	@Override
	public boolean offlineCheckExists(String shopId, String cashierId, boolean isActive) {
		return offlineDao.offlineCheckExists(shopId, cashierId,isActive);
	}

	@Override
	public boolean offlineCreate2(Offline offline) {
	//	if(null != getAllBillByShopId(offline.getShopId())) {
			if(offlineCheckExists(offline.getShopId(), offline.getCashierId(), true)) {
		return false;
	}else {
		offlineDao.addOffline(offline);
		 return true;
	}

}

	@Override
	public Offline getByShopIdAndMobileNo(String shopId, String mobileNo) {
		return offlineDao.getByShopIdAndMobileNo(shopId,mobileNo);
	}
	
	@Override
	public Offline checkDeactive(int billingId) {
		return offlineDao.checkDeactive(billingId);
	}
	@Override
	public List<Offline> getByShopId(String shopId) {
		return offlineDao.getByShopId(shopId);
	}
	
	@Override
	public List<Offline> getDeactiveByShopId(String shopId) {
		return offlineDao.getDeactiveByShopId(shopId);
	}

	@Override
	public List<Offline> getAllOfflineByShopId(String shopId) {
		return offlineDao.getAllOfflineByShopId(shopId);
	}

	@Override
	public List<Offline> getAllAmountByCashierId(String shopId, String cashierId) {
		return offlineDao.getAllAmountByCashierId(shopId, cashierId);
	}

	@Override
	public boolean cheakBillCreated(int billingId) {
		return offlineDao.cheakBillCreated(billingId);
	}

	@Override
	public List<Offline> getActiveBillByCashierId(String cashierId) {
		return offlineDao.getActiveBillByCashierId(cashierId);
	}
}
