package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IAdminBillBookDAO;
import com.shop.shopservice.Idao.IAdminDAO;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminBillBook;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.service.IAdminBillBookService;
import com.shop.shopservice.service.IAdminService;
@Transactional
@Repository
public class AdminBillBookServiceImpl implements IAdminBillBookService{
	
	@Autowired
	IAdminBillBookDAO adminBillBookDao;

	@Override
	public List<AdminBillBook> getAll() {		
		return adminBillBookDao.getAll();
	}

	@Override
	public boolean adminBillBookExists(String adminId) {
		return adminBillBookDao.adminBillBookExists(adminId);
	}

	@Override
	public boolean createAdminBillBook(AdminBillBook adminBillBook) {
		if (adminBillBookExists(adminBillBook.getAdminId())) {
			return false;
		} else {

		   adminBillBookDao.addAdminBillBook(adminBillBook);
		   adminBillBook = null;
			return true;
		}
	}

	@Override
	public boolean updateAdminBillBook(AdminBillBook adminBillBook) {
		adminBillBookDao.updateAdminBillBook(adminBillBook);
		return true;
	}

	@Override
	public AdminBillBook getBillBookById(int id) {
	return	adminBillBookDao.getBillBookById(id);
		
	}

	@Override
	public List<AdminBillBook> getBillBookByAdminId(String adminId) {
	return adminBillBookDao.getBillBookByAdminId(adminId);
	}
	
}
