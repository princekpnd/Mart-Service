package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Transaction;

public interface ITransactionService {
	
	public List<Transaction> getTransactionByUserIdAndShopId(int userId,String shopId, int transactionStatus);

	List<Transaction> getAllTransaction();
	
	List<Transaction> getAllAmount(String shopId);

	List<Transaction> getTransactionByUserId(int userId, int transactionStatus);

	Transaction getById(int id);

	List<Transaction> getTransactionForUserByShopId(String shopId);

	List<Transaction> getTransactionByShopId(String shopId, int transactionStatus);

	List<Transaction> getTransactionByTransactionId(String transactionId);
	
	List<Transaction> getByAdminId(int adminId, int transactionType, boolean isActive);

	public Transaction getTransactionByCartId(int cartId);

	public boolean transactionExists(int cartId);

	public boolean createTransaction(Transaction transaction);
	
	public boolean createTransactionForPurchase(Transaction transaction);
	
	public boolean createTransactionForWithdraw(Transaction transaction);

	public Transaction getTransaction(String transactionId);

	public boolean updateTransaction(Transaction transaction);

}
