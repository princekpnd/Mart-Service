package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IAdminBillBookDAO;
import com.shop.shopservice.entity.AdminBillBook;
@Repository
@Transactional
public class AdminBillBookDaoImpl implements IAdminBillBookDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<AdminBillBook> getAll() {
		List<AdminBillBook> adminBillBook = entityManager.createNamedQuery("AdminBillBook.findAll", AdminBillBook.class).getResultList();
		return adminBillBook;
	}

	@Override
	public boolean adminBillBookExists(String adminId) {
		AdminBillBook  adminBillBook = entityManager.createNamedQuery("AdminBillBook.findByBillByAdminId",AdminBillBook.class).setParameter("adminId", adminId).getResultList().stream().findFirst().orElse(null);;
		return null != adminBillBook ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addAdminBillBook(AdminBillBook adminBillBook) {
		entityManager.persist(adminBillBook);
	}

	@Override
	public void updateAdminBillBook(AdminBillBook adminBillBook) {
		entityManager.merge(adminBillBook);
		
	}

	@Override
	public AdminBillBook getBillBookById(int id) {
        return this.entityManager.find(AdminBillBook.class, id);
	}

	@Override
	public List<AdminBillBook> getBillBookByAdminId(String adminId) {
		List<AdminBillBook> adminBillBook = entityManager.createNamedQuery("AdminBillBook.findByAdminId",AdminBillBook.class).setParameter("adminId", adminId).getResultList();
		return  adminBillBook;
	}

	
}