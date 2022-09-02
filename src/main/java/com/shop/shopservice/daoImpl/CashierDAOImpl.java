package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.ICashierDAO;
import com.shop.shopservice.Idao.ICasualDAO;
import com.shop.shopservice.entity.Cashier;

@Repository
@Transactional
public class CashierDAOImpl implements ICashierDAO{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Cashier> getAll() {
		List<Cashier> cashierList = entityManager.createNamedQuery("Cashier.getAll",Cashier.class).getResultList();
		return cashierList;
	}

	@Override
	public Cashier getById(int cashierId) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.getById",Cashier.class).setParameter("cashierId", cashierId).getSingleResult();
		return cashier;
	}

	@Override
	public boolean cashierExits(String mobileNo, String shopId) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.chrekCashier",Cashier.class).setParameter("mobileNo", mobileNo).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);
		return null != cashier ?Boolean.TRUE:Boolean.FALSE;
	
	}

	@Override
	public void createCashier(Cashier cashier) {
		entityManager.persist(cashier);
		
	}

	@Override
	public void updateCashier(Cashier cashier) {
	entityManager.merge(cashier);
		
	}

	@Override
	public List<Cashier> getByShopId(String shopId) {
		List<Cashier> cashierList = entityManager.createNamedQuery("Cashier.getByShopId",Cashier.class).setParameter("shopId", shopId).getResultList();
		return cashierList;
	}

	@Override
	public Cashier getByMobileNumber(String mobileNo) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.findByMobileNumber",Cashier.class).setParameter("mobileNo", mobileNo).getSingleResult();
		return cashier;
	}

	@Override
	public Cashier getByMobileNumberAndShopId(String shopId, String mobileNo) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.chrekCashier",Cashier.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return cashier;
	}

	@Override
	public Cashier getDeactiveById(int cashierId) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.getAllDeactive",Cashier.class).setParameter("cashierId", cashierId).getSingleResult();
		return cashier;
	}

	@Override
	public Cashier loginCashier(String mobileNo, String pwd) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.loginCashier",Cashier.class).setParameter("mobileNo", mobileNo).setParameter("pwd", pwd).getSingleResult();
		return cashier;
	}

	@Override
	public Cashier getDeActiveByMobileNumber(String mobileNo) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.daactiveCashier",Cashier.class).setParameter("mobileNo", mobileNo).getSingleResult();
		return cashier;
	}

	@Override
	public Cashier getAllAmount(String shopId, int  cashierId) {
		Cashier cashier = entityManager.createNamedQuery("Cashier.getAllAmount",Cashier.class).setParameter("shopId", shopId).setParameter("cashierId", cashierId).getSingleResult();
		return cashier;
	}
}
