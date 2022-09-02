package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IAttendanceDAO;
import com.shop.shopservice.entity.Attendance;
@Repository
@Transactional
public class AttendanceDAOImpl implements IAttendanceDAO{
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Attendance> getAllAttendance() {
		List<Attendance> attendanceList= entityManager.createNamedQuery("Attendance.findAll",Attendance.class).getResultList();
		return attendanceList;
	}
	
	@Override
	public List<Attendance> getByEmployeeId(String employeeId) {
		List<Attendance> attendanceList = entityManager.createNamedQuery("Attendance.findByEmployeeId", Attendance.class).setParameter("employeeId", employeeId).getResultList();
		return attendanceList;
	}
	
	
	@Override
	public boolean attendanceExists(String employeeId) {
		Attendance attendance = entityManager.createNamedQuery("Attendance.findByEmployeeId",Attendance.class).setParameter("employeeId", employeeId).getResultList().stream().findFirst().orElse(null);;
		return null != attendance ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public List<Attendance> getByShopId(String shopId) {
		List<Attendance> attendanceList= entityManager.createNamedQuery("Attendance, findByShopId",Attendance.class).setParameter("shopId", shopId).getResultList();
		return attendanceList;
	}
	
	@Override
	public void addAttendance(Attendance attendance) {
		entityManager.persist(attendance);

	}
	
	@Override
	public void updateAttendance(Attendance attendance) {
	entityManager.merge(attendance);
	}

	@Override
	public Attendance getAttendanceByEmployeeId(String employeeId) {
		Attendance  attendance=entityManager.createNamedQuery("Attendance.findAttendanceByEmployeeId",Attendance.class).setParameter("employeeId", employeeId).getSingleResult();
		return attendance;
	}

	@Override
	public boolean attendanceExistsByEmployee(String shopId, String employeeId) {
		Attendance attendance = entityManager.createNamedQuery("Attendance.findByDate",Attendance.class).setParameter("shopId", shopId).setParameter("employeeId", employeeId).getResultList().stream().findFirst().orElse(null);;
		return null != attendance ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public List<Attendance> getByWorkId(String workId) {
		List<Attendance> attendanceList = entityManager.createNamedQuery("Attendance.findByWorkId", Attendance.class).setParameter("workId", workId).getResultList();
		return attendanceList;
	}

	@Override
	public Attendance getById(int id) {
		return this.entityManager.find(Attendance.class, id);
	}

	@Override
	public boolean deleteAttendance(int id) {
		Query query = entityManager.createQuery("delete Attendance where id = " + id);
		query.executeUpdate();
		entityManager.flush();
		return true;
	}
	

}
