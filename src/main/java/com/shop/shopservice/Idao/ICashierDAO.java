package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Cashier;

public interface ICashierDAO {
	
	List<Cashier> getAll();
	
	public Cashier getById(int cashierId);
	
	public List<Cashier> getByShopId(String shopId);
	
	public Cashier getByMobileNumber(String mobileNo);
	
	public Cashier getByMobileNumberAndShopId(String shopId,String  mobileNo);
	
	public Cashier getDeactiveById(int cashierId);
	
	public Cashier loginCashier(String mobileNo, String pwd);
	
	public Cashier getAllAmount(String shopId,int cashierId);
	
	public Cashier getDeActiveByMobileNumber(String mobileNo);
	
	public boolean cashierExits(String mobileNo,String shopId);
	
	void createCashier(Cashier cashier);
	
	void updateCashier(Cashier cashier);

}
