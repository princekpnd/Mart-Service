package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.AdminBillBook;

public interface IAdminBillBookDAO {
	List<AdminBillBook> getAll();

	public AdminBillBook getBillBookById(int id);

	public List<AdminBillBook> getBillBookByAdminId(String adminId);

	boolean adminBillBookExists(String adminId);

	void addAdminBillBook(AdminBillBook adminBillBook);

	void updateAdminBillBook(AdminBillBook adminBillBook);

}
