package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Work;

public interface IWorkDAO {

	List<Work> getAllWork();

	Work getWorkByShopId(String shopId);

	Work getWorkById(int workId);

	void updateWork(Work work);

	boolean workExists(String shopId);

	void addWork(Work work);

	Work getWorkByEmployeeId(String employeeId);

	boolean checkActiveWork(String shopId, String employeeId, boolean active);

	Work getActiveWork(String shopId, String employeeId, boolean active);
}
