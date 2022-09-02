package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Casual;

public interface ICasualDAO {

	List<Casual> getAllCasual();

	Casual getCasualById(int id);

	public List<Casual> getCasualByEmployeeId(String employeeId);

	List<Casual> getCasualByShopId(String shopId);

	boolean casualExists(String employeeId);

	void addCasual(Casual casual);

	Casual getByEmployeeId(String employeeId);

	void updateCasual(Casual casual);

}
