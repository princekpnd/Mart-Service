package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;
import com.shop.shopservice.service.IUserService;

@Transactional
@Repository
public class UserServiceImpl implements IUserService {

	@Autowired
	IUsersDAO userDao;

	@Autowired
	EntityManager em;
	@Autowired
	IManagedObject managedObject;

	@Override
	@Transactional
	public boolean createUserSignup(User user) {

		if (usersExists(user.getEmailId())) {
			return false;
		} else {
			userDao.addUser(user);
			user = null;
			return true;
		}
	}

	@Override
	public boolean updateUser(User user) {
		userDao.updateUsers(user);
		return true;
	}

	@Override
	public User getUser(int userId) {
		return userDao.getUsersById(userId);
	}

	@Override
	public User getUserByOtp(String mobileNo, String otp) {
		return userDao.getUserByOtp(mobileNo, otp);
	}

	@Override
	public void activateUser(User user) {
		user.setIsActive(Boolean.TRUE);
		userDao.updateUsers(user);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public boolean createUser(String userId, String emailId, String name, String pwd) {
		User user = new User(emailId, name);
		user.setPwd(new String(new org.springframework.security.crypto.codec.Base64().encode(pwd.getBytes())));
		if (usersExists(user.getEmailId())) {
			return false;
		} else {
//			LookUp objectTypeLookUp = PropertyBundle.systemMap.get("MANAGED_OBJECT_TYPE").stream()
//					.filter(l -> l.getLookUpName().equals("USER")).findFirst().orElse(null);
			user.setIsActive(Boolean.FALSE);
//			ManagedObject mo = new ManagedObject(UUID.randomUUID().toString(), new Date(),
//					createdBy, objectTypeLookUp.getLookUpId());
//			managedObject.createManagedObject(mo);
//			user.setManagementObject(mo.getID());
			userDao.addUser(user);
			return true;
		}
	}

	@Override
	public User loginUser(String emailId, String pwd) {
		// TODO Auto-generated method stub
		return userDao.validateUserByPwd(emailId, pwd);
	}

//	@Override
//	public User loginAdminUser(String emailId, String pwd) {
//		// TODO Auto-generated method stub
//		return userDao.validateAdminUserByPwd(emailId, pwd);
//	}

	@Override
	public User findUser(String userId, String emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loginCasUser(String userId, String emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User validateUserByDeviceId(String deviceId) {
		return userDao.validateUserByDeviceId(deviceId);
	}

	@Override
	public User getUsersByEmailId(String emailId) {
		return userDao.getUsersByEmailId(emailId);
	}

	@Override
	public boolean usersExists(String emailId) {
		return userDao.usersExists(emailId);
	}

//	@Override
//	public int viewWalletBalance(int userId) {
//		return userDao.viewWalletBalance(userId);
//	}
//
//	@Override
//	public boolean updateWalletBalance(int userId, int amount) {
//		return userDao.updateWalletBalance(userId, amount);
//	}

	@Override
	public List<User> getAllUsers(int count) {
		return userDao.getAllUsers(count);
	}

	@Override
	public void deleteUserDevice(UserDeviceID ud) {
		userDao.deleteUserDevice(ud);

	}

	@Override
	public User sendOtp(String mobileNo) {
		return userDao.sendOtp(mobileNo);
	}

	@Override
	public boolean addWishListByProductId(User user, String productId) {
		userDao.addWishListByProductId(user, productId);
		return true;
	}

	@Override
	public List<User> getUserByUserIdAndShopId(int userId, String shopId) {
		return userDao.getUserByUserIdAndShopId(userId , shopId);
	}

	@Override
	public List<User> getByUserIdAndShopId(int userId, String shopId) {
		return userDao.getUserByUserIdAndShopId(userId, shopId);
	}

	@Override
	public boolean userExistByEmail(String emailId) {
		return userDao.userExistByEmail(emailId);
	}

	@Override
	public boolean deleteUser(int userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public User getUserByMobileNumber(String mobileNo) {
		return userDao.getUserByMobileNumber(mobileNo);
	}

	@Override
	public List<User> getUserByShopId(String shopId) {
		return userDao.getUserByShopId(shopId);
	}

	@Override
	public User getUserByOtpAndEmailId(String otp, String emailId) {
		return userDao.getUserByOtpAndEmailId(otp,emailId);
	}

	@Override
	public boolean resendOtp(String emailId) {
		return userDao.resendOtp(emailId);
	}

	@Override
	public boolean checkExitByEmailId(String emailId, String mobileNo) {
	return userDao.checkExitByEmailId(emailId, mobileNo);
	}

	@Override
	public User getByRefralNumber(String refralNumber) {
	return userDao.getByRefralNumber(refralNumber);
	}

	@Override
	public User getActiveUserByMobileNumber(String mobileNo) {
		return userDao.getActiveUserByMobileNumber(mobileNo);
	}

	@Override
	public boolean checkByRefralNumber(String mobileNo) {
		return userDao.checkByRefralNumber(mobileNo);
	}

//	@Override
//	public void followConsultantByUser(User consultant, Integer userId) {
//		userDao.followConsultantByUser(consultant, userId);
//		
//	}

}
