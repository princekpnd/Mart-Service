package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Work;

public interface IWorkService {

	List<Work> getAllWork();

	public Work getWorkByShopId(String shopId);

	public Work getWorkById(int workId);

	public boolean updateWork(Work work);

	public boolean workExists(String shopId);

	public boolean createWork(Work work);

	public boolean checkActiveWork(String shopId, String employeeId, boolean active);

	public Work getActiveWork(String shopId, String employeeId, boolean active);

	public Work getWorkByEmployeeId(String employeeId);

}
