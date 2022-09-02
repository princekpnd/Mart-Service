package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ICasualDAO;
import com.shop.shopservice.entity.Casual;
import com.shop.shopservice.service.ICasualService;
@Repository
@Transactional

public class CasualServiceImpl implements ICasualService{
	
	@Autowired
	ICasualDAO casualDao;
	
	@Override
	public List<Casual> getAllCasual() {
		return casualDao.getAllCasual();

}

	@Override
	public List<Casual> getCasualByEmployeeId(String employeeId) {
		return casualDao.getCasualByEmployeeId(employeeId);
	}
	
	@Override
	public List<Casual> getCasualByShopId(String shopId) {
		return casualDao.getCasualByShopId(shopId);
	}
	@Override
	public boolean casualExists(String employeeId) {
		return casualDao.casualExists(employeeId);
	}
	
	public boolean createCasual(Casual casual) {

		if (casualExists(casual. getEmployeeId())) {
			return false;
		} else {

			casualDao.addCasual(casual);
			casual = null;
			return true;
		}
	}

	@Override
	public Casual getCasual(String employeeId) {
		return casualDao.getByEmployeeId(employeeId);
	}

	@Override
	public boolean updateCasual(Casual casual) {
		casualDao.updateCasual(casual);
		return true;
	}

	@Override
	public Casual getCasualById(int id) {
	return	casualDao.getCasualById(id);
	
	}

	
}
