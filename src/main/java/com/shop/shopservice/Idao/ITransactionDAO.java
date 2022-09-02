package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Transaction;

public interface ITransactionDAO {

	List<Transaction> getAllTransaction();

	List<Transaction> getTransactionByUserId(int userId, int transactionStatus);

	Transaction getById(int id);
	
	List<Transaction> getAllAmount(String shopId);

	List<Transaction> getTransactionByShopId(String shopId, int transactionStatus);

	List<Transaction> getTransactionByTransactionId(String transactionId);
	
	List<Transaction> getByAdminId(int adminId, int transactionType, boolean isActive);

	Transaction getTransactionByCartId(int cartId);

	List<Transaction> getTransactionForUserByShopId(String shopId);

	boolean transactionExists(int cartId);

	void addTransaction(Transaction transaction);

	Transaction getByTransactionId(String transactionId);

	void updateTransaction(Transaction transaction);
	
	List<Transaction> getTransactionByUserIdAndShopId(int userId,String shopId, int transactionStatus);

}
