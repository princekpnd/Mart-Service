package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IDeliveryDAO;
import com.shop.shopservice.entity.Delivery;

@Repository
@Transactional
public class DeliveryDAOImpl implements IDeliveryDAO {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Delivery> getAllDelivery() {
		List<Delivery> deliveryList = entityManager.createNamedQuery("Delivery.findAll", Delivery.class)
				.getResultList();
		return deliveryList;
	}
	
	@Override
	public Delivery getById(int id) {
		return this.entityManager.find(Delivery.class, id);
	}

	@Override
	public boolean deliveryExits(String shopId, String mobileNo) {
		Delivery deliveryList = entityManager.createNamedQuery("Delivery.exitsDelivery", Delivery.class)
				.setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != deliveryList ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addDelivery(Delivery delivery) {
		entityManager.persist(delivery);
		
	}

	@Override
	public void updateDelivery(Delivery delivery) {
		entityManager.merge(delivery);
		
	}

	@Override
	public Delivery getByShopIdAndMobileNumber(String shopId, String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery.getByShopIdAndMobileNumber",Delivery.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return delivery;
	}

	@Override
	public Delivery getTotal(String shopId, String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery.findTotalAmount",Delivery.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return delivery;
	}

//	@Override
//	public Delivery loginDelivery(String mobileNo, String pwd) {
//		Delivery delivery = entityManager.createNamedQuery("Delivery.loginDelivery",Delivery.class).setParameter("mobileNo", mobileNo).setParameter("pwd", pwd).getSingleResult();
//		return null != delivery ?delivery:null;
//	}
	
	@Override
	public Delivery loginDelivery(String mobileNo, String pwd) {
		Delivery delivery = entityManager.createNamedQuery("Delivery.loginDelivery",Delivery.class).setParameter("mobileNo", mobileNo).setParameter("pwd", pwd).getResultList().stream().findFirst().orElse(null);
//		if(delivery != null) {
//			return	delivery;		
//			}else {
//				return null;
//			}
		return delivery;
	}

	@Override
	public Delivery getDeActiveById(int id) {
	Delivery delivery = entityManager.createNamedQuery("Delivery. getDeActive",Delivery.class).setParameter("id", id).getSingleResult();
		return delivery;
	}

	@Override
	public boolean checkByMobileNumber(String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery. checkByMobileNumber",Delivery.class).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != delivery ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public boolean checkByPwd(String pwd) {
		Delivery delivery = entityManager.createNamedQuery("Delivery. checkByPwd",Delivery.class).setParameter("pwd", pwd).getResultList().stream().findFirst().orElse(null);
		return null != delivery ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public boolean checkByPwdAndMobileNo(String pwd, String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery. checkByPwdAndMobileNumber",Delivery.class).setParameter("pwd", pwd).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != delivery ?Boolean.TRUE:Boolean.FALSE;
	}
}
