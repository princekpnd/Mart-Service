package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.ITransactionDAO;
import com.shop.shopservice.entity.Transaction;

@Repository
@Transactional
public class TransactionDAOImpl implements ITransactionDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	
	@Override
	public List<Transaction> getAllTransaction() {
		List<Transaction> profileList= entityManager.createNamedQuery("Transaction.findAll",Transaction.class).getResultList();
		return profileList;
	}
	
	@Override
	public List<Transaction> getTransactionByUserId(int userId, int transactionStatus) {
		return this.entityManager.createNamedQuery("Transaction.findTransactionByUserId", Transaction.class).setParameter("userId", userId).setParameter("transactionStatus", transactionStatus).getResultList();
	}
	
	@Override
	public List<Transaction> getTransactionForUserByShopId(String shopId) {
		List<Transaction> transactionList = entityManager.createNamedQuery("Transaction.findTransactionForUserByShopId",Transaction.class).setParameter("shopId", shopId).getResultList();
		return transactionList;
	}
	
	@Override
	public List<Transaction> getTransactionByShopId(String shopId, int transactionStatus) {
		return this.entityManager.createNamedQuery("Transaction.findTransactionByShopId", Transaction.class).setParameter("shopId", shopId).setParameter("transactionStatus", transactionStatus).getResultList();
	}
	@Override
	public List<Transaction> getTransactionByTransactionId(String transactionId) {
		return this.entityManager.createNamedQuery("Transaction.findTransactionByTransactionId", Transaction.class).setParameter("transactionId", transactionId).getResultList();
	}
	
	@Override
	public boolean transactionExists(int cartId) {
		Transaction transaction = entityManager.createNamedQuery("Transaction.findByCartId",Transaction.class).setParameter("cartId", cartId).getResultList().stream().findFirst().orElse(null);;
		return null != transaction ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addTransaction(Transaction transaction) {
		entityManager.persist(transaction);
	}
	
	@Override
	public Transaction getByTransactionId(String transactionId) {
		return this.entityManager.find(Transaction.class, transactionId);
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		entityManager.merge(transaction);
		
	}
	
	@Override
	public List<Transaction> getTransactionByUserIdAndShopId(int userId, String shopId, int transactionStatus) {
	List<Transaction> transactionList = entityManager.createNamedQuery("Transaction.findByUserIdAndShopId",Transaction.class).setParameter("userId", userId).setParameter("shopId", shopId).setParameter("transactionStatus", transactionStatus).getResultList();
		return transactionList;
	}

	@Override
	public Transaction getTransactionByCartId(int cartId) {
		Transaction transaction = entityManager.createNamedQuery("Transaction.findByTransactionCartId", Transaction.class).setParameter("cartId", cartId).getSingleResult();
		return transaction;
	}

	@Override
	public Transaction getById(int id) {
		return this.entityManager.find(Transaction.class, id);
	}

	@Override
	public List<Transaction> getByAdminId(int adminId, int transactionType, boolean isActive) {
		List<Transaction> transaction = null;
		transaction = entityManager.createNamedQuery("Transaction.findByAdminId", Transaction.class).setParameter("adminId", adminId).setParameter("transactionType", transactionType).setParameter("isActive", isActive).getResultList();
		return transaction;
	}

	@Override
	public List<Transaction> getAllAmount(String shopId) {
	List<Transaction> transactionList = entityManager.createNamedQuery("Transaction.findAllAmount",Transaction.class).setParameter("shopId", shopId).getResultList();
		return transactionList;
	}

//	@Override
//	public boolean transactionExists(int cartId) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
	
	

	
}
