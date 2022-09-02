package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ICasualDAO;
import com.shop.shopservice.entity.Casual;
@Repository
@Transactional
public class CasualDAOImpl implements ICasualDAO{
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Casual> getAllCasual() {
		List<Casual> casualList= entityManager.createNamedQuery("Casual.findAll",Casual.class).getResultList();
		return casualList;
	}

	@Override
	public List<Casual> getCasualByEmployeeId(String employeeId) {
	List<Casual> casualList= entityManager.createNamedQuery("Casual.findByEmployeeId",Casual.class).setParameter("employeeId", employeeId).getResultList();
		return casualList;
	}
	@Override
	public List<Casual> getCasualByShopId(String shopId) {
		List<Casual> casualList = entityManager.createNamedQuery("Casual.findCasualByShopId",Casual.class).setParameter("shopId", shopId).getResultList();
		return casualList ;
	}
	
	@Override
	public boolean casualExists(String employeeId) {
		Casual casual = entityManager.createNamedQuery("Casual.findCasualByEmployeeId",Casual.class).setParameter("employeeId", employeeId).getResultList().stream().findFirst().orElse(null);;
		return null != casual ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addCasual(Casual casual) {
		entityManager.persist(casual);

	}

	@Override
	public Casual getByEmployeeId(String employeeId) {
	 Casual casual = entityManager.createNamedQuery("Casual.findAllByEmployeeId",Casual.class).setParameter("employeeId", employeeId).getSingleResult();
	 return casual;
		
	}

	@Override
	public void updateCasual(Casual casual) {
		entityManager.merge(casual);
		
	}

	@Override
	public Casual getCasualById(int id) {
		return this.entityManager.find(Casual.class, id);
	}

	

}
