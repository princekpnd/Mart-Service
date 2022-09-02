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

@Entity
@Table(name = "CASUAL_LEAVE")

@NamedQueries({ 
	@NamedQuery(name = "Casual.findAll",
				query = "SELECT ca FROM Casual ca"),
	@NamedQuery(name = "Casual.findByEmployeeId",
	           query = "SELECT ca FROM Casual ca WHERE ca.employeeId= :employeeId"),
	@NamedQuery(name = "Casual.findCasualByEmployeeId",
               query = "SELECT ca FROM Casual ca WHERE ca.employeeId= :employeeId"),
	@NamedQuery(name = "Casual.findAllByEmployeeId",
               query = "SELECT ca FROM Casual ca WHERE ca.employeeId= :employeeId"),
	@NamedQuery(name ="Casual.findCasualByShopId",
	          query="SELECT ca FROM Casual ca WHERE ca.shopId = :shopId and ca.isActive is TRUE and ca.isDeleted is FALSE"),


})


public class Casual implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "LEAVE_FROM", nullable = false)
	private Date leaveFrom;
	
	@Column(name = "LEAVE_TO", nullable = false)
	private Date leaveTo;
	
	@Column(name = "REASION", nullable = false)
	private String reasion;
	
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private String employeeId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "LEAVE_TYPE", nullable = false)
	private String leaveType;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name ="CREATED_ON", nullable =false)
	private Date createdOn;
	
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
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	public Casual() {
		super();
	}
	public Casual(String shopId,String employeeId) {
		this.shopId= shopId;
		this.employeeId= employeeId;
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
	 * @return the leaveFrom
	 */
	public Date getLeaveFrom() {
		return leaveFrom;
	}

	/**
	 * @param leaveFrom the leaveFrom to set
	 */
	public void setLeaveFrom(Date leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	/**
	 * @return the leaveTo
	 */
	public Date getLeaveTo() {
		return leaveTo;
	}

	/**
	 * @param leaveTo the leaveTo to set
	 */
	public void setLeaveTo(Date leaveTo) {
		this.leaveTo = leaveTo;
	}

	/**
	 * @return the reasion
	 */
	public String getReasion() {
		return reasion;
	}

	/**
	 * @param reasion the reasion to set
	 */
	public void setReasion(String reasion) {
		this.reasion = reasion;
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
	 * @return the leaveType
	 */
	public String getLeaveType() {
		return leaveType;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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
	
	

}
