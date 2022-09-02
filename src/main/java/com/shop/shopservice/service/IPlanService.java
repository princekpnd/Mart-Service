package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Plan;

public interface IPlanService {
	List<Plan> getAllPlan();
	
	List<Plan> getAllActivePlan();
	 
	 public Plan getById(int id);
	 
	 public Plan getActivePlanById(int id);
	 
	 public List<Plan> getByPlanType(int planType);
	 
	 public List<Plan> getActivePlan(int planType);
	 
	 public boolean updatePlan(Plan plan);
	 
	 public boolean planExists(int planType);
	 
	 public boolean createPlan(Plan plan);
}
