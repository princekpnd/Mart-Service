package com.shop.shopservice.daoImpl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IOfflineDAO;
import com.shop.shopservice.entity.Offline;

@Repository
@Transactional
public class OfflineDAOImpl implements IOfflineDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Offline> getAllOffline() {
		List<Offline> offlineList = entityManager.createNamedQuery("Offline.getAll", Offline.class).getResultList();
		return offlineList;
	}

	@Override
	public Offline getByBillId(int billingId) {
		Offline offline = entityManager.createNamedQuery("Offline.findByBillId", Offline.class)
				.setParameter("billingId", billingId).getSingleResult();
		return offline;
	}

	@Override
	public Offline getAllBillByShopId(String shopId, String cashierId) {
		Offline offline = null;
		offline = entityManager.createNamedQuery("Offline.getByShopId", Offline.class).setParameter("shopId", shopId).setParameter("cashierId", cashierId)
				.getResultList().stream().findFirst().orElse(null);
		return null != offline ? offline : null;
	}

	@Override
	public List<Offline> getAllBillByUserName(String shopId, String userName) {
		List<Offline> offlineList = entityManager.createNamedQuery("Offline.getByUserName", Offline.class)
				.setParameter("shopId", shopId).setParameter("userName", userName).getResultList();
		return offlineList;
	}

	@Override
	public boolean offlineExists(String shopId, String productName) {
		Offline offline = entityManager.createNamedQuery("Offline.offlineExist", Offline.class)
				.setParameter("shopId", shopId).setParameter("productName", productName).getResultList().stream()
				.findFirst().orElse(null);
		return null != offline ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addOffline(Offline offline) {
		entityManager.persist(offline);
	}

	@Override
	public void updateOffline(Offline offline) {
		entityManager.merge(offline);

	}

	@Override
	public Offline getByShopIdAndBillId(String shopId, int billingId) {
		Offline offline = entityManager.createNamedQuery("Offline.findByShopIdAndId", Offline.class)
				.setParameter("shopId", shopId).setParameter("billingId", billingId).getResultList().stream().findFirst()
				.orElse(null);
		return null != offline ? offline : null;
	}

	@Override
	public boolean offlineCheckExists(String shopId, String cashierId, boolean isActive) {
		Offline offline = entityManager.createNamedQuery("Offline.checkExist", Offline.class)
				.setParameter("shopId", shopId).setParameter("cashierId", cashierId).setParameter("isActive", isActive).getResultList().stream().findFirst()
				.orElse(null);
		return null != offline ? Boolean.TRUE : Boolean.FALSE;
	} 

	@Override
	public Offline getByShopIdAndMobileNo(String shopId, String mobileNo) {
		Offline offline = entityManager.createNamedQuery("Offline.findByShopIdAndMobileNo", Offline.class)
				.setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return offline;
	}

	@Override
	public Offline checkDeactive(int billingId) {
		Offline offline = entityManager.createNamedQuery("Offline.cheackDeactive", Offline.class)
				.setParameter("billingId", billingId).getSingleResult();
		return offline;
	}

	@Override
	public List<Offline> getByShopId(String shopId) {
		List<Offline> offlineList = entityManager.createNamedQuery("offline.findAllByShopId", Offline.class)
				.setParameter("shopId", shopId).getResultList();
		return offlineList;
	}
	
	@Override
	public List<Offline> getDeactiveByShopId(String shopId) {
	List<Offline> offlineList = entityManager.createNamedQuery("Offline.getDeactiveByShopId", Offline.class).setParameter("shopId", shopId).getResultList();
		return offlineList;
	}

	@Override
	public List<Offline> getAllOfflineByShopId(String shopId) {
	List<Offline> offlineList = entityManager.createNamedQuery("Offline.getOfflineByShopId",Offline.class).setParameter("shopId", shopId).getResultList();
		return offlineList;
	}

	@Override
	public List<Offline> getAllAmountByCashierId(String shopId, String cashierId) {
		List<Offline> offlineList = entityManager.createNamedQuery("Offline.getAllAmount",Offline.class).setParameter("shopId", shopId).setParameter("cashierId", cashierId).getResultList();
		return offlineList;
	}

	@Override
	public boolean cheakBillCreated(int billingId) {
	Offline offline = entityManager.createNamedQuery("Offline.cheakCreated",Offline.class).setParameter("billingId", billingId).getResultList().stream().findFirst().orElse(null);
	return  null != offline ? Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public List<Offline> getActiveBillByCashierId(String cashierId) {
		List<Offline> offlineList = null;
		Offline offlineList1 = entityManager.createNamedQuery("Offline.getActiveBillByCashierId",Offline.class).setParameter("cashierId", cashierId).getResultList().stream().findFirst().orElse(null);
		
		if(offlineList1 != null) {
			offlineList = new ArrayList<Offline>();
			 offlineList.add(offlineList1);
		}
		
		return offlineList != null ? offlineList : null;
	}

}
