package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.UserBillBook;

public interface IUserBillBookService {
	List<UserBillBook> getAll();

	public UserBillBook getById(int id);

	public List<UserBillBook> getByShopId(String shopId);

	public List<UserBillBook> getByUserId(String userId);

	public List<UserBillBook> getByUserIdAndShopId(String userId, String shopId);

	public boolean userBillBookExists(String userId);

	public boolean createUserBillBook(UserBillBook userBillBook);

	public boolean updateUserBillBook(UserBillBook userBillBook);
}
