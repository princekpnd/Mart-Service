package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Vender;

public interface IVenderDAO {
	
	List<Vender> getAll();
	
	Vender getById(int id);
	
	Vender getAllDetaisById(int id);
	
	Vender getByMobileNumber(String mobileNo);
	
	List<Vender> getByShopId(String shopId);
	
	void updateVender(Vender vender);
	
	public boolean venderExits(String shopId, String mobileNo);
	
	public boolean checkExits(String shopId, int id);
	
	void createVender(Vender vender);

}
