package com.shop.shopservice.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.ITransactionDAO;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.service.ITransactionService;

@Repository
@Transactional
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	ITransactionDAO transactionDao;

	@Override
	public List<Transaction> getAllTransaction() {
		return transactionDao.getAllTransaction();
	}

	@Override
	public List<Transaction> getTransactionByUserId(int userId, int transactionStatus) {
		return transactionDao.getTransactionByUserId(userId, transactionStatus);
	}

	@Override
	public List<Transaction> getTransactionByShopId(String shopId, int transactionStatus) {
		return transactionDao.getTransactionByShopId(shopId, transactionStatus);
	}

	@Override
	public List<Transaction> getTransactionByTransactionId(String transactionId) {
		return transactionDao.getTransactionByTransactionId(transactionId);
	}

	@Override
	public List<Transaction> getTransactionForUserByShopId(String shopId) {
		return transactionDao.getTransactionForUserByShopId(shopId);
	}

	@Override
	public boolean transactionExists(int cartId) {
		return transactionDao.transactionExists(cartId);
	}

	public boolean createTransaction(Transaction transaction) {

		if (transactionExists(transaction.getCartId())) {
//			Transaction transaction2 = getTransactionByCartId(transaction.getCartId());
//			if (transaction2.getPaymentMode() == transaction.getPaymentMode()) {
				transactionDao.updateTransaction(transaction);
				return true;
//			}
		} else {
			transactionDao.addTransaction(transaction);
			return true;
		}
	}

	@Override
	public List<Transaction> getTransactionByUserIdAndShopId(int userId, String shopId, int transactionStatus) {
		return transactionDao.getTransactionByUserIdAndShopId(userId, shopId, transactionStatus);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		transactionDao.updateTransaction(transaction);
		return true;
	}

	@Override
	public Transaction getTransaction(String transactionId) {
		return transactionDao.getByTransactionId(transactionId);
	}

	@Override
	public Transaction getTransactionByCartId(int cartId) {
		return transactionDao.getTransactionByCartId(cartId);
	}

	@Override
	public Transaction getById(int id) {
		return transactionDao.getById(id);
	}

	@Override
	public boolean createTransactionForPurchase(Transaction transaction) {
		List<Transaction> transactionList = transactionDao.getByAdminId(transaction.getAdminId(),
				transaction.getTransactionType(), Boolean.TRUE);
		if (transactionList.size() > 0) {
			return false;
		} else {
			transactionDao.addTransaction(transaction);
			return true;
		}
	}

	@Override
	public List<Transaction> getByAdminId(int adminId, int transactionType, boolean isActive) {
		return transactionDao.getByAdminId(adminId, transactionType, isActive);
	}

	@Override
	public boolean createTransactionForWithdraw(Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Transaction> getAllAmount(String shopId) {
		return transactionDao.getAllAmount(shopId);
	}

}
