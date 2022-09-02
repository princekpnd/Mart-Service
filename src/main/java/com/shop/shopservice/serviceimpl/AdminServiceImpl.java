package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IAdminDAO;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.service.IAdminService;
@Transactional
@Repository
public class AdminServiceImpl implements IAdminService{
	
	@Autowired
	IAdminDAO adminDao;
	
	
	@Override
	public List<Admin> getAllAdmin() {
		return adminDao.getAllAdmin();
	}
	

	@Override
	public Admin getAdmin(int adminId) {
		return adminDao.getAdminById(adminId);
	}
	
	@Override
	public Admin getAdminByEmailId(String emailId) {
		return adminDao.getAdminByEmailId(emailId);
	}
	
	@Override
	public Admin getAdminByShopId(String shopId) {
		return adminDao.getAdminByShopId(shopId);
	}
	
	@Override
	public List<Admin> getAdminByFirstName(String firstName) {
		return adminDao.getAdminByFirstName(firstName);
	}
	
	@Override
	public List<Admin> getAdminByAdharNumber(int adharNumber) {
		return adminDao.getAdminByAdharNumber(adharNumber);
	}
	
	@Override
	public boolean updateAdmin(Admin admin) {
		adminDao.updateAdmin(admin);
		return true;
	}
	
	@Override
	public Admin loginAdmin(String emailId, String pwd) {
		return adminDao.validateAdminByPwd(emailId, pwd);
	}
	
	@Override
	public Admin validateAdminByDeviceId(String deviceId) {
		return adminDao.validateAdminByDeviceId(deviceId);
	}
	
	@Override
	public void deleteAdminDevice(AdminDeviceID ad) {
		adminDao.deleteAdminDevice(ad);
		
	}
	
	@Override
	public boolean adminExists(String mobileNo) {
		return adminDao.adminExists(mobileNo);
	}
	
	public boolean createAdmin(Admin admin) {

		if (adminExists(admin.getMobileNo())) {
			return false;
		} else {
//			LookUp objectTypeLookUp = PropertyBundle.systemMap.get("MANAGED_OBJECT_TYPE").stream()
//					.filter(l -> l.getLookUpName().equals("USER")).findFirst().orElse(null);
//			LookUp objectCreatedByLookUp = PropertyBundle.systemMap.get("MANAGED_OBJECT_CREAT").stream()
//					.filter(l -> l.getLookUpName().equals("SELF")).findFirst().orElse(null);
			admin.setActive(Boolean.FALSE);
//			ManagedObject mo = new ManagedObject(UUID.randomUUID().toString(), new Date(),
//					objectCreatedByLookUp.getLookUpId(), objectTypeLookUp.getLookUpId());
//			managedObject.createManagedObject(mo);
	//		user.setManagementObject(mo.getID());
			adminDao.addAdmin(admin);
			admin = null;
			return true;
		}
	}


	@Override
	public List<Admin> getAdminByAdminIdAndShopId(int adminId, String shopId) {
		return adminDao.getAdminByAdminIdAndShopId(adminId, shopId);
	}


	@Override
	public Admin getByMobileNumber(String mobileNo) {
		return adminDao.getByMobileNumber(mobileNo);
	}


	@Override
	public List<Admin> getByShopType(int shopType) {		
		return adminDao.getByShopType(shopType);
	}


	@Override
	public Admin getByShopId(String shopId) {
		return adminDao.getByShopId(shopId);
	}


	@Override
	public Admin getDeactiveAdmin(String emailId, String pwd) {
		return adminDao.getDeactiveAdmin(emailId, pwd);
	}


	@Override
	public boolean checkAdminByEmailId(String emailId) {
		return adminDao.checkAdminByEmailId(emailId);
	}


	@Override
	public boolean checkDeactiveAdminByEmailId(String emailId) {
		return adminDao.checkDeactiveAdminByEmailId(emailId);
	}


	@Override
	public boolean checkDeactiveAdminByAdminId(int adminId, int registrationStatus) {
		return adminDao.checkDeactiveAdminByAdminId(adminId, registrationStatus);
	}


	@Override
	public List<Admin> getActiveAdminByShopId(String shopId) {
		return adminDao.getActiveAdminByShopId(shopId);
	}


	@Override
	public List<Admin> getAllActiveAdmin() {
		return adminDao.getAllActiveAdmin();
	}


	@Override
	public boolean checkActiveAdminByShopId(String shopId) {
		return adminDao.checkActiveAdminByShopId(shopId);
	}


	@Override
	public boolean adminAclive(String shopId) {
		return adminDao.adminAclive(shopId);
	}


	@Override
	public int getResult() {
		return adminDao.getResult();
	}
	
}
