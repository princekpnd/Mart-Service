package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IPlanDAO;
import com.shop.shopservice.entity.Plan;

@Repository
@Transactional
public class PlanDAOImpl implements IPlanDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Plan> getAllPlan() {
		List<Plan> planList = entityManager.createNamedQuery("Plan.findAll", Plan.class).getResultList();
		return planList;
	}

	@Override
	public List<Plan> getByPlanType(int planType) {
		List<Plan> planList = entityManager.createNamedQuery("Plan.findByPlanType", Plan.class)
				.setParameter("planType", planType).getResultList();
		return planList;
	}

	@Override
	public Plan getById(int id) {
		return this.entityManager.find(Plan.class, id);

	}

	@Override
	public void updatePlan(Plan plan) {
		entityManager.merge(plan);

	}

	@Override
	public boolean planExists(int planType) {
		Plan plan = entityManager.createNamedQuery("Plan.planExitByPlanType", Plan.class)
				.setParameter("planType", planType).getResultList().stream().findFirst().orElse(null);
		;
		return null != plan ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addPlan(Plan plan) {
		entityManager.persist(plan);

	}

	@Override
	public List<Plan> getActivePlan(int planType) {
		List<Plan> planList = entityManager.createNamedQuery("Plan.findActivePlan", Plan.class)
				.setParameter("planType", planType).getResultList();
		return planList;
	}

	@Override
	public Plan getActivePlanById(int id) {
		Plan plan = null;
		plan = entityManager.createNamedQuery("Plan.findActivePlanById", Plan.class).setParameter("id", id)
				.getResultList().stream().findFirst().orElse(null);
		return plan;
	}

	@Override
	public List<Plan> getAllActivePlan() {
		List<Plan> planList = entityManager.createNamedQuery("Plan.findAllActive", Plan.class).getResultList();
		return planList;
	}

}
