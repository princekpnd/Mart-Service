package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IWithdrawDAO;
import com.shop.shopservice.entity.Withdraw;
import com.shop.shopservice.service.IWithdrawService;
@Transactional
@Repository
public class WithdrawServiceImpl implements IWithdrawService{
	@Autowired
	IWithdrawDAO withdrawDao;

	@Override
	public List<Withdraw> getAllWithdraw() {
		return withdrawDao.getAllWithdraw();
	}

	@Override
	public List<Withdraw> getWithdrawByShopId(String shopId) {
		return withdrawDao.getWithdrawByShopId(shopId);
	}

	@Override
	public List<Withdraw> getWithdrawByUserId(int userId) {
		return withdrawDao.getWithdrawByUserId(userId);
	}

	@Override
	public List<Withdraw> getWithdrawByShopIdAndUserId(String shopId, int userId) {
	return withdrawDao.getWithdrawByShopIdAndUserId(shopId, userId);
	}

	@Override
	public Withdraw getById(int id) {
		return withdrawDao.getById(id);
	}

	@Override
	public boolean updateWithdraw(Withdraw withdraw) {
		 withdrawDao.updateWithdraw(withdraw);
		 return true;
	}

	@Override
	public boolean withdrawExists(String accountNum, String accountHolderName, String bankName) {
		return withdrawDao.withdrawExists(accountNum, accountHolderName, bankName);
	}

	@Override
	public boolean createWithdraw(Withdraw withdraw) {		
			withdrawDao.addWithdraw(withdraw);
			return true;
	}

	@Override
	public List<Withdraw> getActiveWithdraw(String shopId) {
		return withdrawDao.getActiveWithdraw(shopId);
	}

	@Override
	public List<Withdraw> checkActiveWithdraw(int adminId, boolean isActive) {
		return withdrawDao.checkActiveWithdraw(adminId, isActive);
	}
}
