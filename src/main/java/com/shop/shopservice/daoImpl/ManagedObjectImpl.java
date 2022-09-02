package com.shop.shopservice.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.entity.ManagedObject;
@Repository
@Transactional
public class ManagedObjectImpl implements IManagedObject {
	@PersistenceContext	
	private EntityManager em;
	@Override
	public boolean isManagedObjectExist(int id) {
		return null != em.find(ManagedObject.class, id)?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void createManagedObject(ManagedObject mo) {
		em.persist(mo);
		mo = null;
	}

	public ManagedObject getManagedObject(int id) {
		return em.find(ManagedObject.class, id);
	}
}
