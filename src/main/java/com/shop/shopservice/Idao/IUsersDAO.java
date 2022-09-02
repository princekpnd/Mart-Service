package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;

public interface IUsersDAO {

	List<User> getAllUsers(int count);

	void addUser(User user);

	void updateUsers(User User);

	void deleteUsers(int userId);

	boolean usersExists(String emailId);

	boolean userExistByEmail(String emailId);

	User getUsersById(int userId);

	User getUserByOtp(String mobileNo, String otp);

	public List<User> getUserByUserIdAndShopId(int userId, String shopId);
	
	public User getUserByMobileNumber(String mobileNo);
	
	public List<User> getUserByShopId(String shopId);
	
	public User getUserByOtpAndEmailId(String otp,String emailId);
	
	boolean resendOtp(String emailId);
	
	boolean checkExitByEmailId(String emailId,String  mobileNo);
	
	boolean checkByRefralNumber(String mobileNo);
	
	User getByRefralNumber(String refralNumber);
	
	User getActiveUserByMobileNumber(String mobileNo);

	User getUsersByEmailId(String emailId);

	User validateUserByDeviceId(String deviceId);

	User validateUserByPwd(String email, String pwd);

	void deleteUserDevice(UserDeviceID ud);

	User sendOtp(String mobileNo);

	void addWishListByProductId(User user, String productId);

//	User validateAdminUserByPwd(String email,String pwd);
//	int viewWalletBalance(int userId);
//	boolean updateWalletBalance(int userId, int amount);
//	public void followConsultantByUser(User consultant, Integer userId);
	boolean deleteUser(int userId);

	public void indexUser();

	public List<User> searchUser(String keyword);

}
