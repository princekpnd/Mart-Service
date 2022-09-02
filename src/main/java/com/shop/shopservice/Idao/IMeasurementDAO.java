package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Measurement;

public interface IMeasurementDAO {
	List<Measurement> getAll();
	
	Measurement getById(int id);
	
	Measurement getDeActiveForExcel(int id);
	
	Measurement getByTitleAndShopId(String shopId, String title);
	List<Measurement> getMeasurementByShopId(String shopId);
	public void updateMeasurement(Measurement measurement);
	
	 boolean measurementExists(String title , String shopId);
	
	void addMeasurement(Measurement measurement);

}
