package com.shop.shopservice.serviceimpl;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IAttendanceDAO;
import com.shop.shopservice.entity.Attendance;
import com.shop.shopservice.service.IAttendanceService;

@Repository
@Transactional

public class AttendanceServiceImpl implements IAttendanceService {

	@Autowired
	IAttendanceDAO attendanceDao;

	@Override
	public List<Attendance> getAllAttendance() {
		return attendanceDao.getAllAttendance();
	}

	@Override
	public List<Attendance> getByEmployeeId(String employeeId) {
		return attendanceDao.getByEmployeeId(employeeId);
	}

	@Override
	public boolean attendanceExists(String employeeId) {
		return attendanceDao.attendanceExists(employeeId);
	}

	@Override
	public List<Attendance> getByShopId(String shopId) {
		return attendanceDao.getByShopId(shopId);

	}

	public boolean createAttendance(Attendance attendance) {
		attendanceDao.addAttendance(attendance);
		attendance = null;
		return true;
	}

	@Override
	public boolean updateAttendance(Attendance attendance) {
		attendanceDao.updateAttendance(attendance);
		return true;
	}

	@Override
	public Attendance getAttendance(String employeeId) {
		return attendanceDao.getAttendanceByEmployeeId(employeeId);
	}

	@Override
	public boolean attendanceExistsByEmployee(String shopId, String employeeId) {
		return attendanceDao.attendanceExistsByEmployee(shopId, employeeId);
	}

	@Override
	public List<Attendance> getByWorkId(String workId) {
		return attendanceDao.getByWorkId(workId);
	}

	@Override
	public Attendance getById(int id) {
		return attendanceDao.getById(id);
	}

	@Override
	public boolean deleteAttendance(int id) {
		return attendanceDao.deleteAttendance(id);
	}

}
