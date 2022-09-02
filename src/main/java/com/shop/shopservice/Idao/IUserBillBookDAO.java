package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.UserBillBook;

public interface IUserBillBookDAO {
	List<UserBillBook> getAll();
	
	UserBillBook getById(int id);
	
	List<UserBillBook> getByShopId(String shopId);
	
	List<UserBillBook> getByUserId(String userId);
	
	List<UserBillBook> getByUserIdAndShopId(String userId ,String shopId);
	
	boolean userBillBookExists(String userId);
	
	void addUserBillBook(UserBillBook userBillBook);
	
	void updateUserBillBook(UserBillBook userBillBook);
}
