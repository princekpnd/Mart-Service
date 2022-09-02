package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.AdminBillBook;

public interface IAdminBillBookService {
	List<AdminBillBook> getAll();

	public AdminBillBook getBillBookById(int id);

	public List<AdminBillBook> getBillBookByAdminId(String adminId);

	public boolean adminBillBookExists(String adminId);

	public boolean createAdminBillBook(AdminBillBook adminBillBook);

	public boolean updateAdminBillBook(AdminBillBook adminBillBook);
}
