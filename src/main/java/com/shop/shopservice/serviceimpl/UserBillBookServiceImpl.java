package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IUserBillBookDAO;
import com.shop.shopservice.entity.UserBillBook;
import com.shop.shopservice.service.IUserBillBookService;
@Transactional
@Repository
public class UserBillBookServiceImpl implements IUserBillBookService{
	
	@Autowired
	IUserBillBookDAO userBillBookDao;

	@Override
	public List<UserBillBook> getAll() {		
		return userBillBookDao.getAll();
	}

	@Override
	public UserBillBook getById(int id) {
		return userBillBookDao.getById(id);
	}

	@Override
	public boolean userBillBookExists(String userId) {
		return userBillBookDao.userBillBookExists(userId);
	}

	@Override
	public boolean createUserBillBook(UserBillBook userBillBook) {
		if (userBillBookExists(userBillBook.getUserId())) {
			return false;
		} else {

			userBillBookDao.addUserBillBook(userBillBook);
			userBillBook = null;
			return true;
		}
	}

	@Override
	public boolean updateUserBillBook(UserBillBook userBillBook) {
		userBillBookDao.updateUserBillBook(userBillBook);
		return  true;
	}

	@Override
	public List<UserBillBook> getByShopId(String shopId) {
	return userBillBookDao.getByShopId(shopId);
	}

	@Override
	public List<UserBillBook> getByUserId(String userId) {
		return userBillBookDao.getByUserId(userId);
	}

	@Override
	public List<UserBillBook> getByUserIdAndShopId(String userId, String shopId) {
		return userBillBookDao.getByUserIdAndShopId(userId , shopId);
	}
	
}
