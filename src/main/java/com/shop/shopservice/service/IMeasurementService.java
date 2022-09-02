package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Measurement;

public interface IMeasurementService {
	List<Measurement> getAll();
	
	public Measurement getById(int id);
	
	public Measurement getDeActiveForExcel(int id);
	
	public List<Measurement> getMeasurementByShopId(String shopId);
	
	public Measurement getByTitleAndShopId(String shopId, String title);
	
	public boolean updateMeasurement(Measurement measurement);
	
	public boolean measurementExists(String title, String shopId);
	
	public boolean createMeasurement(Measurement measurement);

}
