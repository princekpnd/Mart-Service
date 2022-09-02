package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Cashier;

public interface ICashierService {
	
	List<Cashier> getAll();
	
	public Cashier getById(int cashierId);
	
	public Cashier getDeactiveById(int cashierId);
	
	public Cashier loginCashier(String mobileNo,String pwd);
	
	public List<Cashier> getByShopId(String shopId);
	
	public Cashier getAllAmount(String shopId,int cashierId);
	
	public Cashier getDeActiveByMobileNumber(String mobileNo);
	
	public Cashier getByMobileNumberAndShopId(String shopId,String  mobileNo);
	
	public Cashier getByMobileNumber(String mobileNo);
	
	public boolean cashierExits(String mobileNo, String shopId);
	
	public boolean createCashier(Cashier cashier);
	
	public boolean updateCashier(Cashier cashier);

}
