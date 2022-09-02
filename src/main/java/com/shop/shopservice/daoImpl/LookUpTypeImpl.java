package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.entity.LookUpType;
@Repository
public class LookUpTypeImpl implements ILookUpType{

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<LookUpType> findAllLookUpType() {
		return (List<LookUpType>)entityManager.createNamedQuery("LookUpType.findAll", LookUpType.class).getResultList();
	}

	@Override
	public LookUpType findLookUpTypeByName(String name) {
		LookUpType result = null;
		try {
			TypedQuery<LookUpType> tq = entityManager.createQuery("from LookUpType WHERE lookUpTypeName=:lookUpTypeName",
					LookUpType.class);
			result = tq.setParameter("lookUpTypeName", name).getSingleResult();
		} catch (NoResultException noresult) {
			// if there is no result
		} catch (NonUniqueResultException notUnique) {
			// if more than one result
		}
		return result;
	}	

}
