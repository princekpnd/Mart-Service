package com.shop.shopservice.daoImpl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IVenderDAO;
import com.shop.shopservice.entity.Vender;

@Repository
@Transactional
public class VenderDAOImpl implements IVenderDAO{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Vender> getAll() {
		List<Vender> venderList = entityManager.createNamedQuery("Vender.findAll",Vender.class).getResultList();
		return venderList;
	}

	@Override
	public boolean venderExits(String shopId, String mobileNo) {
	Vender vender = entityManager.createNamedQuery("Vender.venderExits",Vender.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getResultList().stream()
			.findFirst().orElse(null);
	;
	return null != vender ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void createVender(Vender vender) {
	entityManager.persist(vender);
		
	}

	@Override
	public Vender getById(int id) {
		Vender vender = entityManager.createNamedQuery("Vender.findById",Vender.class).setParameter("id", id).getSingleResult();
		return vender;
	}

	@Override
	public void updateVender(Vender vender) {
		entityManager.merge(vender);
		
	}

	@Override
	public List<Vender> getByShopId(String shopId) {
		List<Vender> venderList = entityManager.createNamedQuery("Vender.findByShopId",Vender.class).setParameter("shopId", shopId).getResultList();
		return venderList;
	}

	@Override
	public Vender getAllDetaisById(int id) {
		Vender vender = entityManager.createNamedQuery("Vender.findAllDetailsId",Vender.class).setParameter("id", id).getSingleResult();
		return vender;
	}

	@Override
	public Vender getByMobileNumber(String mobileNo) {
		Vender vender = entityManager.createNamedQuery("Vender.findByMobileNumber",Vender.class).setParameter("mobileNo", mobileNo).getSingleResult();
		return vender;
	}

	@Override
	public boolean checkExits(String shopId, int id) {
		Vender vender = entityManager.createNamedQuery("Vender.checkExits",Vender.class).setParameter("shopId", shopId).setParameter("id", id).getResultList().stream()
				.findFirst().orElse(null);
		;
		return null != vender ? Boolean.TRUE : Boolean.FALSE;
	}

	
}
