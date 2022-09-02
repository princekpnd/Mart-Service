package com.shop.shopservice.service;
import java.util.List;
import com.shop.shopservice.entity.Attendance;

public interface IAttendanceService {

	List<Attendance> getAllAttendance();

	List<Attendance> getByEmployeeId(String employeeId);

	Attendance getById(int id);

	List<Attendance> getByShopId(String shopId);

	public List<Attendance> getByWorkId(String workId);

	public boolean attendanceExistsByEmployee(String shopId, String employeeId);

	public boolean attendanceExists(String employeeId);

	public boolean createAttendance(Attendance attendance);

	public boolean updateAttendance(Attendance attendance);

	public boolean deleteAttendance(int id);

	public Attendance getAttendance(String employeeId);

}
