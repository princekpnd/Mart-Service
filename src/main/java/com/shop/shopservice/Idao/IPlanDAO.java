package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Plan;

public interface IPlanDAO {
	List<Plan> getAllPlan();
	
	List<Plan> getAllActivePlan();
	
	List<Plan> getByPlanType(int planType);
	Plan getById(int id);
	void updatePlan(Plan plan);
	
	boolean planExists(int planType);
	
	void addPlan(Plan plan);
	
	List<Plan> getActivePlan(int planType);
	
	Plan getActivePlanById(int id);
}
