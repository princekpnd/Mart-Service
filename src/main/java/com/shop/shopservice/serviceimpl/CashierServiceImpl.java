package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.ICashierDAO;
import com.shop.shopservice.Idao.ICasualDAO;
import com.shop.shopservice.entity.Cashier;
import com.shop.shopservice.service.ICashierService;
import com.shop.shopservice.service.ICasualService;

@Repository
@Transactional
public class CashierServiceImpl implements ICashierService{
	@Autowired
	ICashierDAO cashierDao;

	@Override
	public List<Cashier> getAll() {
		return cashierDao.getAll();
	}

	@Override
	public Cashier getById(int cashierId) {
		return cashierDao.getById(cashierId);
	}

	@Override
	public boolean cashierExits(String mobileNo, String shopId) {
		return cashierDao.cashierExits(mobileNo,shopId);
	}

	@Override
	public boolean createCashier(Cashier cashier) {
		if(cashierExits(cashier.getMobileNo(), cashier.getShopId())) {
			return false;
		}else {
			cashierDao.createCashier(cashier);
			return true;
		}
		
	}

	@Override
	public boolean updateCashier(Cashier cashier) {
		 cashierDao.updateCashier(cashier);
		 return true;
	}

	@Override
	public List<Cashier> getByShopId(String shopId) {
		return cashierDao.getByShopId(shopId);
	}

	@Override
	public Cashier getByMobileNumber(String mobileNo) {
		return cashierDao.getByMobileNumber(mobileNo);
	}

	@Override
	public Cashier getByMobileNumberAndShopId(String shopId, String mobileNo) {
		return cashierDao.getByMobileNumberAndShopId(shopId, mobileNo);
	}

	@Override
	public Cashier getDeactiveById(int cashierId) {
	return cashierDao.getDeactiveById(cashierId);
	}

	@Override
	public Cashier loginCashier(String mobileNo, String pwd) {
		return cashierDao.loginCashier(mobileNo,pwd);
	}

	@Override
	public Cashier getDeActiveByMobileNumber(String mobileNo) {
		return cashierDao.getDeActiveByMobileNumber(mobileNo);
	}

	@Override
	public Cashier getAllAmount(String shopId, int cashierId) {
		return cashierDao.getAllAmount(shopId,cashierId);
	}
}
