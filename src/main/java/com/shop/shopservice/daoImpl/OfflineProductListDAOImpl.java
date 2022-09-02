package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IOfflineProductListDAO;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.OfflineProductList;

@Repository
@Transactional
public class OfflineProductListDAOImpl implements IOfflineProductListDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OfflineProductList> getAll() {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.getAll", OfflineProductList.class).getResultList();
		return offlineProductList;
	}

	@Override
	public OfflineProductList getById(int id) {
		return this.entityManager.find(OfflineProductList.class, id);
	}

	@Override
	public boolean offlineProductExists(String productName, String shopId, String brandName) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.existProductList", OfflineProductList.class)
				.setParameter("productName", productName).setParameter("shopId", shopId)
				.setParameter("brandName", brandName).getResultList().stream().findFirst().orElse(null);

		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public boolean offlineProductListExists(String productName, int offlineCartId, String brandName) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.existProduct", OfflineProductList.class)
				.setParameter("productName", productName).setParameter("offlineCartId", offlineCartId)
				.setParameter("brandName", brandName).getResultList().stream().findFirst().orElse(null);

		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addOfflineProductList(OfflineProductList offlineProductList) {
		entityManager.persist(offlineProductList);

	}

	@Override
	public void updateOfflineProductList(OfflineProductList offlineProductList) {
		entityManager.merge(offlineProductList);

	}

	@Override
	public List<OfflineProductList> getByShopId(String shopId) {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.findByShopId", OfflineProductList.class)
				.setParameter("shopId", shopId).getResultList();
		return offlineProductList;
	}

	@Override
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId) {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.findByCartId", OfflineProductList.class)
				.setParameter("offlineCartId", offlineCartId).getResultList();
		return offlineProductList;
	}

	@Override
	public Boolean checkExit(int offlineCartId, String productId, String cashierId, boolean stockActiveInd) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.checkexistProductList", OfflineProductList.class)
				.setParameter("offlineCartId", offlineCartId).setParameter("productId", productId).setParameter("cashierId", cashierId).setParameter("stockActiveInd", stockActiveInd).getResultList()
				.stream().findFirst().orElse(null);
		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public boolean deleteOfflineProductList(int id) {
		Query query = entityManager.createQuery("delete OfflineProductList where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

	@Override
	public OfflineProductList getDeactiveById(int id) {
		OfflineProductList offlineProductList = entityManager.createNamedQuery("OfflineProductList.getById",OfflineProductList.class).setParameter("id", id).getSingleResult();
		return offlineProductList;
	}

	@Override
	public OfflineProductList getByProductId(String productId) {
		OfflineProductList offlineProductList = entityManager.createNamedQuery("OfflineProductList.findByProductId",OfflineProductList.class).setParameter("productId", productId).getSingleResult();
		return offlineProductList;
	}

	@Override
	public OfflineProductList getByofflineCartIdAndProductId(int offlineCartId, String productId, boolean stockActiveInd) {
		OfflineProductList offlineProductList = entityManager.createNamedQuery("OfflineProductList.findByCartIdAndProductId", OfflineProductList.class).setParameter("offlineCartId", offlineCartId).setParameter("productId", productId).setParameter("stockActiveInd", stockActiveInd).getSingleResult();
		return offlineProductList;
	}

	@Override
	public List<OfflineProductList> getByShopIdAndCashierId(String cashierId, int offlineCartId) {
		List<OfflineProductList> offlineProductLists = entityManager.createNamedQuery("OfflineProductList.getByCashierId", OfflineProductList.class).setParameter("cashierId", cashierId).setParameter("offlineCartId", offlineCartId).getResultList();
		return offlineProductLists;
	}

//	@Override
//	public List<OfflineProductList> getByShopIdAndCashierId(int offlineCartId, String cashierId) {
//		List<OfflineProductList> offlineProductList = entityManager.createNamedQuery("OfflineProductList.findByCartIdAndCashierId",OfflineProductList.class).setParameter("offlineCartId", offlineCartId).setParameter("cashierId", cashierId).getResultList();
//		return offlineProductList;
//	}

}
