package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Casual;

public interface ICasualService {

	List<Casual> getAllCasual();

	public Casual getCasualById(int id);

	public List<Casual> getCasualByEmployeeId(String employeeId);

	public List<Casual> getCasualByShopId(String shopId);

	public boolean casualExists(String employeeId);

	public boolean createCasual(Casual casual);

	public Casual getCasual(String employeeId);

	public boolean updateCasual(Casual casual);

}
