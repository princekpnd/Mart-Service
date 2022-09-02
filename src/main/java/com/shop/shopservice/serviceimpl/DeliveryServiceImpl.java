package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IDeliveryDAO;
import com.shop.shopservice.entity.Delivery;
import com.shop.shopservice.service.IDeliveryService;

@Repository
@Transactional
public class DeliveryServiceImpl implements IDeliveryService{
	@Autowired
	IDeliveryDAO  deliveryDao;

	@Override
	public List<Delivery> getAllDelivery() {
		return deliveryDao.getAllDelivery();
	}

	@Override
	public Delivery getById(int id) {
		return deliveryDao.getById(id);
	}

	@Override
	public boolean deliveryExits(String shopId, String mobileNo) {
	return deliveryDao.deliveryExits(shopId, mobileNo);
	}

	@Override
	public boolean deliveryCreate(Delivery delivery) {
		if(deliveryDao.deliveryExits(delivery.getShopId(), delivery.getMobileNo())) {
			return false;
		}else {
			deliveryDao.addDelivery(delivery);
			return true;
		}
		
	}

	@Override
	public boolean updateDelivery(Delivery delivery) {
		 deliveryDao.updateDelivery(delivery);
		 return true;
	}

	@Override
	public Delivery getByShopIdAndMobileNumber(String shopId, String mobileNo) {
	return deliveryDao.getByShopIdAndMobileNumber(shopId,mobileNo);
	}

	@Override
	public Delivery getTotal(String shopId, String mobileNo) {
	return deliveryDao.getTotal(shopId, mobileNo);
	}

	@Override
	public Delivery loginDelivery(String mobileNo, String pwd) {
		return deliveryDao.loginDelivery(mobileNo,pwd);
	}

	@Override
	public Delivery getDeActiveById(int id) {
		return deliveryDao.getDeActiveById(id);
	}

	@Override
	public boolean checkByMobileNumber(String mobileNo) {
		return deliveryDao.checkByMobileNumber(mobileNo);
	}

	@Override
	public boolean checkByPwd(String pwd) {
		return deliveryDao.checkByPwd(pwd);
	}

	@Override
	public boolean checkByPwdAndMobileNo(String pwd, String mobileNo) {
		return deliveryDao.checkByPwdAndMobileNo(pwd,mobileNo);
	}
	
}
