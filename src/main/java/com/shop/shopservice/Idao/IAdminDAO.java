package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;

public interface IAdminDAO {

	List<Admin> getAllAdmin();
	
	List<Admin> getAllActiveAdmin();

	Admin getAdminById(int adminId);

	Admin getAdminByEmailId(String emailId);
	
	List<Admin> getActiveAdminByShopId(String shopId);
	
	boolean checkAdminByEmailId(String emailId);
	
	boolean checkDeactiveAdminByEmailId(String emailId);
	
	boolean checkDeactiveAdminByAdminId(int adminId, int registrationStatus);
	
	boolean checkActiveAdminByShopId(String shopId);
	
	boolean adminAclive(String shopId);

	Admin getAdminByShopId(String shopId);

	List<Admin> getAdminByAdminIdAndShopId(int adminId, String shopId);

	List<Admin> getAdminByFirstName(String firstName);

	List<Admin> getAdminByAdharNumber(int adharNumber);

	Admin getByMobileNumber(String mobileNo);

	void updateAdmin(Admin admin);

	Admin validateAdminByPwd(String emailId, String pwd);
	
	Admin getDeactiveAdmin(String emailId, String pwd);

	Admin validateAdminByDeviceId(String deviceId);

	void deleteAdminDevice(AdminDeviceID ad);

	boolean adminExists(String mobileNo);

	void addAdmin(Admin admin);

	List<Admin> getByShopType(int shopType);
	
	 public void indexAdmin();
	 
	 public List<Admin> searchAdmin( String keyword);
	 
	 Admin getByShopId(String shopId);
	 int getResult();

	int resutl();
	 
//	 public List<Admin> searchAdminByShopId(String shopId, String keyword);
}
