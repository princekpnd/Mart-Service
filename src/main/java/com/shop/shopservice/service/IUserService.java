package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;

public interface IUserService {

	public boolean createUserSignup(User user);

	public boolean updateUser(User user);

	public User getUser(int userId);
	
	public User getUserByMobileNumber(String mobileNo);
	
	public User getActiveUserByMobileNumber(String mobileNo);

	public List<User> getUserByUserIdAndShopId(int userId, String shopId);

	public void activateUser(User user);

	public boolean createUser(String userId, String emailId, String name, String pwd);

	public boolean usersExists(String emailId);

	public boolean userExistByEmail(String emailId);

	public User loginUser(String emailId, String pwd);

	public User findUser(String userId, String emailId);

	public User loginCasUser(String userId, String emailId);

	User validateUserByDeviceId(String deviceId);

	public User getUsersByEmailId(String emailId);

	public List<User> getByUserIdAndShopId(int userId, String shopId);

	public User sendOtp(String mobileNo);

	public void deleteUserDevice(UserDeviceID ud);

	public User getUserByOtp(String mobileNo, String otp);
	
	public boolean  resendOtp(String emailId);
	
	public User getByRefralNumber(String refralNumber);
	
	public User getUserByOtpAndEmailId(String otp, String emailId);

//	int viewWalletBalance(int userId);
//	boolean updateWalletBalance(int userId, int amount);
	List<User> getAllUsers(int count);
	
	List<User> getUserByShopId(String shopId);

//	public User loginAdminUser(String emailId, String pwd);
//	public void followConsultantByUser(User consultant, Integer userId);
	public boolean addWishListByProductId(User user, String productId);
	
	public boolean checkByRefralNumber(String mobileNo);
	
	public boolean checkExitByEmailId(String emailId,String mobileNo);

	public boolean deleteUser(int userId);
}
