package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IPlanDAO;
import com.shop.shopservice.entity.Plan;
import com.shop.shopservice.service.IPlanService;


@Transactional
@Repository
public class PlanServiceImpl implements IPlanService{
	
	@Autowired
	IPlanDAO planDao;

	@Override
	public List<Plan> getAllPlan() {
		return planDao.getAllPlan();
	}

	@Override
	public List<Plan> getByPlanType(int planType) {
		return planDao.getByPlanType(planType);
	}

	@Override
	public Plan getById(int id) {
		return planDao.getById(id);
	}

	@Override
	public boolean updatePlan(Plan plan) {
		planDao.updatePlan(plan);
		return true;
	}

	@Override
	public boolean planExists(int planType) {
	return planDao.planExists(planType);
	}

	@Override
	public boolean createPlan(Plan plan) {
		
		if(planExists(plan.getPlanType())) {
			return false;
		}else {
			planDao.addPlan(plan);
			return true;
		}
		
	}

	@Override
	public List<Plan> getActivePlan(int planType) {
		return planDao.getActivePlan(planType);
	}

	@Override
	public Plan getActivePlanById(int id) {
		return planDao.getActivePlanById(id);
	}

	@Override
	public List<Plan> getAllActivePlan() {
		return planDao.getAllActivePlan();
	}

}