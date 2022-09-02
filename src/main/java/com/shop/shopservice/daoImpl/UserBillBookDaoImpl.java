package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IUserBillBookDAO;
import com.shop.shopservice.entity.UserBillBook;

@Repository
@Transactional
public class UserBillBookDaoImpl implements IUserBillBookDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<UserBillBook> getAll() {
		List<UserBillBook> userBillBook = entityManager.createNamedQuery("UserBillBook.findAll", UserBillBook.class).getResultList();
		return userBillBook;
	}

	@Override
	public UserBillBook getById(int id) {
		return this.entityManager.find(UserBillBook.class, id);
	}

	@Override
	public boolean userBillBookExists(String userId) {
		UserBillBook  userBillBook = entityManager.createNamedQuery("UserBillBook.findByUserId",UserBillBook.class).setParameter("userId", userId).getResultList().stream().findFirst().orElse(null);;
		return null != userBillBook ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addUserBillBook(UserBillBook userBillBook) {
		entityManager.persist(userBillBook);
		
	}

	@Override
	public void updateUserBillBook(UserBillBook userBillBook) {
		entityManager.merge(userBillBook);
		
	}

	@Override
	public List<UserBillBook> getByShopId(String shopId) {
	List<UserBillBook> userBillBookList = entityManager.createNamedQuery("UserBillBook.findByShopId",UserBillBook.class).setParameter("shopId", shopId).getResultList();
		return  userBillBookList;
	}

	@Override
	public List<UserBillBook> getByUserId(String userId) {
		List<UserBillBook> userBillBookList = entityManager.createNamedQuery("UserBillBook.findBillByUserId", UserBillBook.class).setParameter("userId",userId).getResultList();
		return  userBillBookList;
	}

	@Override
	public List<UserBillBook> getByUserIdAndShopId(String userId, String shopId) {
		List<UserBillBook> userBillBookList = entityManager.createNamedQuery("UserBillBook.findByUserIdAndShopId",UserBillBook.class).setParameter("userId", userId).setParameter("shopId", shopId).getResultList();
		return userBillBookList;
	}


}