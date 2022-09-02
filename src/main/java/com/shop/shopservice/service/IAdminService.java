package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;

public interface IAdminService {
	List<Admin> getAllAdmin();
	
	List<Admin> getAllActiveAdmin();

	public Admin getAdmin(int adminId);

	public List<Admin> getAdminByFirstName(String firstName);
	
	public List<Admin> getActiveAdminByShopId(String shopId);

	public List<Admin> getAdminByAdminIdAndShopId(int adminId, String shopId);

	public Admin getAdminByEmailId(String emailId);
	
	public boolean adminAclive(String shopId);

	public boolean checkAdminByEmailId(String emailId);
	
	public boolean checkActiveAdminByShopId(String shopId);

	public boolean checkDeactiveAdminByEmailId(String emailId);

	public boolean checkDeactiveAdminByAdminId(int adminId, int registrationStatus);

	public Admin getDeactiveAdmin(String emailId, String pwd);

	public Admin getAdminByShopId(String shopId);

	public Admin getByMobileNumber(String mobileNo);

	public List<Admin> getAdminByAdharNumber(int adharNumber);

	public boolean updateAdmin(Admin admin);

	public Admin loginAdmin(String emailId, String pwd);

	Admin validateAdminByDeviceId(String deviceId);

	public void deleteAdminDevice(AdminDeviceID ad);

	public boolean adminExists(String mobileNo);

	public boolean createAdmin(Admin admin);

	public List<Admin> getByShopType(int shopType);

	public Admin getByShopId(String shopId);
	
	int getResult();
}
