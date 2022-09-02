package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "ATTENDANCE")

@NamedQueries({ 
	@NamedQuery(name = "Attendance.findAll",
				query = "SELECT at FROM Attendance at"),
	@NamedQuery(name = "Attendance.findByEmployeeId",
	            query = "SELECT at FROM Attendance at WHERE at.employeeId = :employeeId"),
	@NamedQuery(name = "Attendance.findAttendanceByEmployeeId",
                query = "SELECT at FROM Attendance at WHERE at.employeeId = :employeeId"),
	@NamedQuery(name = "Attendance.findByDate",
    			query = "SELECT at FROM Attendance at WHERE at.shopId = :shopId and at.employeeId = :employeeId"),
	@NamedQuery(name = "Attendance, findByShopId",
                query = "SELECT at FROM Attendance at WHERE at.shopId = :shopId and at.isActive is TRUE and at.isDeleted is FALSE"),
	@NamedQuery(name = "Attendance.findByWorkId",
	            query ="SELECT at FROM Attendance at WHERE at.workId = :workId"),
})

public class Attendance implements Serializable{
	
private static final long serialVersionUID = 1385794955661915701L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private String employeeId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "ATTENDANCE", nullable = false)
	private int attendance;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name ="IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name ="WORK_ID", nullable = false)
	private String workId;
	
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isDelete
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Attendance() {
		super();
	}

	public Attendance(String employeeId,String shopId) {
		this.employeeId = employeeId;
		this.shopId = shopId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	
	

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the shopId
	 */
	public String getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the attendance
	 */
	public int getAttendance() {
		return attendance;
	}

	/**
	 * @param attendance the attendance to set
	 */
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	/**
	 * @return the workId
	 */
	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId the workId to set
	 */
	public void setWorkId(String workId) {
		this.workId = workId;
	}

	
	

}
