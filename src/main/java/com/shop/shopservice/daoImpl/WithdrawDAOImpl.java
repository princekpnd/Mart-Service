package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IWithdrawDAO;
import com.shop.shopservice.entity.Employee;
import com.shop.shopservice.entity.Withdraw;

@Repository
@Transactional
public class WithdrawDAOImpl implements IWithdrawDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Withdraw getById(int id) {
		return this.entityManager.find(Withdraw.class, id);
	}

	@Override
	public List<Withdraw> getAllWithdraw() {
	List<Withdraw> withdrawList = entityManager.createNamedQuery("Withdrawal.findAll",Withdraw.class).getResultList();
		return withdrawList;
	}

	@Override
	public List<Withdraw> getWithdrawByShopId(String shopId) {
	List<Withdraw> withdrawList= entityManager.createNamedQuery("Withdraw.findByShopId", Withdraw.class).setParameter("shopId", shopId).getResultList();
	return withdrawList;
	}

	@Override
	public List<Withdraw> getWithdrawByUserId(int userId) {
	List<Withdraw> withdrawList = entityManager.createNamedQuery("Withdraw.findByUserId", Withdraw.class).setParameter("userId", userId).getResultList();
		return withdrawList;
	}

	@Override
	public List<Withdraw> getWithdrawByShopIdAndUserId(String shopId, int userId) {
	List<Withdraw> withdrawList = entityManager.createNamedQuery("Withdraw.findByShopIdAndUserId",Withdraw.class).setParameter("shopId", shopId).setParameter("userId", userId).getResultList();
		return withdrawList;
	}

	@Override
	public void updateWithdraw(Withdraw withdraw) {
		entityManager.merge(withdraw);
		
	}

//	@Override
//	public boolean withdrawExists(int accountNum, String accountHolderName, String bankName) {
//		Withdraw withdraw = entityManager.createNamedQuery("Withdraw.exitsWithdraw", Withdraw.class).setParameter("accountNum", accountNum).setParameter("accountHolderName", accountHolderName).setParameter("bankName", bankName).getSingleResult();
//		return null != withdraw ?Boolean.TRUE:Boolean.FALSE ;
//	}
	

	@Override
	public void addWithdraw(Withdraw withdraw) {
		entityManager.persist(withdraw);
		
	}

	@Override
	public List<Withdraw> getActiveWithdraw(String shopId) {
		List<Withdraw> withdrawList = entityManager.createNamedQuery("Withdraw.findActiveWithdraw",Withdraw.class).setParameter("shopId", shopId). getResultList();
		return withdrawList;
	}

	@Override
	public boolean withdrawExists(String accountNum, String accountHolderName, String bankName) {
		Withdraw withdraw = entityManager.createNamedQuery("Withdraw.exitsWithdraw",Withdraw.class).setParameter("accountNum", accountNum).setParameter("accountHolderName", accountHolderName). setParameter("bankName", bankName).getResultList().stream().findFirst().orElse(null);;
		return null != withdraw ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public List<Withdraw> checkActiveWithdraw(int adminId, boolean isActive) {
		List<Withdraw> withdrawList = entityManager.createNamedQuery("Withdraw.checkActiveWithdraw",Withdraw.class).setParameter("adminId", adminId).setParameter("isActive", isActive).getResultList();
		return withdrawList;
	}

	
}
