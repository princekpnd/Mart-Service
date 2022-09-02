package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IPurchaseDAO;
import com.shop.shopservice.entity.Bank;
import com.shop.shopservice.entity.Purchase;

@Repository
@Transactional
public class PurchaseDAOImpl implements IPurchaseDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Purchase> getAllPurchasePlan() {
		List<Purchase> purchaseList = entityManager.createNamedQuery("Purchase.findAll", Purchase.class)
				.getResultList();
		return purchaseList;
	}

	@Override
	public List<Purchase> getPurchasePlanByPlanType(int planType) {
		List<Purchase> purchaseList = entityManager.createNamedQuery("Purchase.findPurchasePlan", Purchase.class)
				.setParameter("planType", planType).getResultList();
		return purchaseList;
	}

	@Override
	public List<Purchase> getPurchasePlanByShopId(String shopId) {
		List<Purchase> purchaseList = entityManager.createNamedQuery("Purchase.findByShopId", Purchase.class)
				.setParameter("shopId", shopId).getResultList();
		return purchaseList;
	}

	@Override
	public boolean purchaseExists(int adminId) {
		Purchase purchase = entityManager.createNamedQuery("Purchase.purchaseExits", Purchase.class)
				.setParameter("adminId", adminId).getResultList().stream().findFirst().orElse(null);
		return null != purchase ? Boolean.TRUE : Boolean.FALSE;

	}

	@Override
	public void addPurchase(Purchase purchase) {
		entityManager.persist(purchase);
	}

	@Override
	public Purchase getById(int id) {
		return this.entityManager.find(Purchase.class, id);
	}

	@Override
	public Purchase getActiveAdminByAdminId(int adminId) {
		Purchase purchase = null;
		purchase = entityManager.createNamedQuery("Purchase.purchaseExits", Purchase.class)
				.setParameter("adminId", adminId).getSingleResult();
		return purchase;
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		entityManager.merge(purchase);

		
	}

}
