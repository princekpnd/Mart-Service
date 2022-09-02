package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IMeasurementDAO;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.service.IMeasurementService;

@Transactional
@Repository
public class MeasurementServiceImpl implements IMeasurementService{
	@Autowired
	IMeasurementDAO  measurementDao;

	@Override
	public List<Measurement> getAll() {
		return measurementDao.getAll();
	}

	@Override
	public List<Measurement> getMeasurementByShopId(String shopId) {
		return measurementDao.getMeasurementByShopId(shopId);
	}

	@Override
	public Measurement getById(int id) {
		return measurementDao.getById(id);
	}

	@Override
	public boolean updateMeasurement(Measurement measurement) {
		measurementDao.updateMeasurement(measurement);
		return true;
	}

	@Override
	public boolean measurementExists(String title, String shopId) {
		return measurementDao.measurementExists(title, shopId);
		 
	}

	@Override
	public boolean createMeasurement(Measurement measurement) {
		if(measurementExists(measurement.getTitle(), measurement.getShopId())) {
			return false;
		}else {
			measurementDao.addMeasurement(measurement);			
			return true;
		}
		
	}

	@Override
	public Measurement getByTitleAndShopId(String shopId, String title) {
		return measurementDao.getByTitleAndShopId(shopId, title);
	}

	@Override
	public Measurement getDeActiveForExcel(int id) {
		return measurementDao.getDeActiveForExcel(id);
	}

}
