package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IWorkDAO;
import com.shop.shopservice.entity.Work;
import com.shop.shopservice.service.IWorkService;

@Transactional
@Repository


public class WorkServiceImpl  implements IWorkService{
	@Autowired
	IWorkDAO workDao;
	
	@Override
	public List<Work> getAllWork() {
		return workDao.getAllWork();
	}
	
	@Override
	public Work getWorkByShopId(String shopId) {
		return workDao.getWorkByShopId(shopId);
	}
	@Override
	public Work getWorkById(int workId) {
		return workDao.getWorkById(workId);
	}
	
	@Override
	public boolean updateWork(Work work) {
		workDao.updateWork(work);
		return true;
	}
	
	@Override
	public boolean workExists(String shopId) {
		return workDao.workExists(shopId);
	}
	
	
	public boolean createWork(Work work) {

		if (workExists(work.getShopId())) {
			return false;
		} else {
			workDao.addWork(work);			
			return true;
		}
	}
	@Override
	public Work getWorkByEmployeeId(String employeeId) {
		return workDao.getWorkByEmployeeId(employeeId);
	}

	@Override
	public boolean checkActiveWork(String shopId, String employeeId, boolean active) {
		return workDao.checkActiveWork(shopId, employeeId, active);
	}
	
	@Override
	public Work getActiveWork(String shopId, String employeeId, boolean active) {
		return workDao.getActiveWork(shopId, employeeId, active);
	}
	
}
