package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Delivery;

public interface IDeliveryDAO {
	List<Delivery> getAllDelivery();
	
	Delivery getById(int id);
	
	boolean deliveryExits(String shopId,String mobileNo);
	
	boolean checkByMobileNumber(String mobileNo);
	
	boolean checkByPwd(String pwd);
	
	boolean checkByPwdAndMobileNo(String pwd, String mobileNo);
	
	void addDelivery(Delivery delivery);
	
	void updateDelivery(Delivery delivery);
	
	Delivery getByShopIdAndMobileNumber(String shopId, String mobileNo);
	
	Delivery getTotal(String shopId,String mobileNo);
	
	Delivery loginDelivery(String mobileNo, String pwd);
	
	Delivery getDeActiveById(int id);

}
