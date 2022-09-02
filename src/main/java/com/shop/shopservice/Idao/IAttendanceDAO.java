package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Attendance;

public interface IAttendanceDAO {

	List<Attendance> getAllAttendance();

	Attendance getById(int id);

	List<Attendance> getByEmployeeId(String employeeId);

	List<Attendance> getByShopId(String shopId);

	List<Attendance> getByWorkId(String workId);

	boolean attendanceExists(String employeeId);

	boolean attendanceExistsByEmployee(String shopId, String employeeId);

	boolean deleteAttendance(int id);

	void addAttendance(Attendance attendance);

	void updateAttendance(Attendance attendance);

	Attendance getAttendanceByEmployeeId(String employeeId);
}
