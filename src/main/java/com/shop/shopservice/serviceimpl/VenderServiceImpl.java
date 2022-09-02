package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IVenderDAO;
import com.shop.shopservice.entity.Vender;
import com.shop.shopservice.service.IVenderService;

@Transactional
@Repository
public class VenderServiceImpl implements IVenderService{
	@Autowired
	IVenderDAO  venderDao;

	@Override
	public List<Vender> getAll() {
	return venderDao.getAll();
	}

	@Override
	public boolean venderExits(String shopId, String mobileNo) {
	return venderDao.venderExits(shopId, mobileNo);
	}

	@Override
	public boolean createVender(Vender vender) {
		if(venderExits(vender.getShopId(), vender.getMobileNo())) {
			return false;
		}else {
			venderDao.createVender(vender);
			return true;
		}
		
	}

	@Override
	public Vender getById(int id) {
	return  venderDao.getById(id);
	}

	@Override
	public boolean updateVender(Vender vender) {
		venderDao.updateVender(vender);
		return  true;
	}

	@Override
	public List<Vender> getByShopId(String shopId) {
		return venderDao.getByShopId(shopId);
	}

	@Override
	public Vender getAllDetaisById(int id) {
		return venderDao.getAllDetaisById(id);
	}

	@Override
	public Vender getByMobileNumber(String mobileNo) {
		return venderDao.getByMobileNumber(mobileNo);
	}

	@Override
	public boolean checkExits(String shopId, int id) {
		return venderDao.checkExits(shopId, id);
	}

}
