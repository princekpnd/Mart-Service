package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Vender;

public interface IVenderService {
	
	List<Vender> getAll();
	
	public Vender getById(int id);
	
	public Vender getAllDetaisById(int id);
	
	public Vender getByMobileNumber(String mobileNo);
	
	public List<Vender> getByShopId(String shopId);
	
	public boolean venderExits(String shopId, String mobileNo);
	
	public boolean createVender(Vender vender);
	
	public boolean updateVender(Vender vender);
	
	public boolean checkExits(String shopId, int id);

}
