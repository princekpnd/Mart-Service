package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IWorkDAO;
import com.shop.shopservice.entity.Work;

@Repository
@Transactional


public class WorkDAOImpl implements IWorkDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<Work> getAllWork() {
		List<Work> workList= entityManager.createNamedQuery("Work.findAll",Work.class).getResultList();
		return workList;
	}
	
	@Override
	public Work    getWorkByShopId(String shopId) {
		Work work =entityManager.createNamedQuery("Work.findWorkByShopId",Work.class).setParameter("shopId", shopId).getSingleResult();
		return work;
	}

	@Override
	public Work getWorkById(int workId) {
		return this.entityManager.find(Work.class, workId);
	}
	
	@Override
	public void updateWork(Work work) {
	entityManager.merge(work);
	}
	
	@Override
	public boolean workExists(String shopId) {
		Work work = entityManager.createNamedQuery("Work.findByShopId",Work.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);;
		return null != work ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addWork(Work work) {
		entityManager.persist(work);
	}

	@Override
	public Work getWorkByEmployeeId(String employeeId) {
		return (Work)entityManager.createNamedQuery("Work findByEmployeeId",Work.class).setParameter("employeeId", employeeId).getSingleResult();
		
	}

	@Override
	public boolean checkActiveWork(String shopId, String employeeId, boolean active) {
		Work work = entityManager.createNamedQuery("Work.findActiveEmployee",Work.class).setParameter("shopId", shopId).setParameter("employeeId", employeeId).setParameter("active", active).getResultList().stream().findFirst().orElse(null);
		return null != work ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public Work getActiveWork(String shopId, String employeeId, boolean active) {
		Work work = entityManager.createNamedQuery("Work.findActiveEmployee",Work.class).setParameter("shopId", shopId).setParameter("employeeId", employeeId).setParameter("active", active).getSingleResult();
		return work;
	}	
	
}
