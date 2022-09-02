package com.shop.shopservice.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SALARY")

@NamedQueries({ 
	@NamedQuery(name = "Salary.findAll",
				query = "SELECT sa FROM Salary sa"),
	@NamedQuery(name="Salary.findSalaryByEmployeeId",
                query="SELECT sa FROM Salary sa WHERE sa.employeeId = :employeeId"),
	@NamedQuery(name = "Salary.findByEmployeeId",
	            query="SELECT sa FROM Salary sa WHERE sa.employeeId = :employeeId"),
	@NamedQuery(name = "Salary.findAllSalary",
                query="SELECT sa FROM Salary sa WHERE sa.employeeId = :employeeId"),
	@NamedQuery(name ="Salary.findSalaryByShopId",
	            query="SELECT sa FROM Salary sa WHERE sa.shopId = :shopId and  sa.isDeleted is FALSE and sa.isActive is TRUE ")

})


public class Salary implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private  String employeeId;
	
	@Column(name = "ADVANCE_SALARY", nullable = false)
	private  int advanceSalary;
	
	
	@Column(name = "TOTAL_SALARY", nullable = false)
	private  int totalSalary;
	
	@Column(name = "SHOP_ID", nullable = false)
	private  String shopId;
	

	@Column(name = "SALARY", nullable = false)
	private  int salary;
	
	@Column(name = "DEDUCTION", nullable = false)
	private  int deduction;
	
	@Column(name ="IS_DELETED" ,nullable = false)
	private boolean isDeleted;
	
	@Column(name ="IS_ACTIVE" ,nullable = false)
	private boolean isActive;
	
	public Salary() {
		super();
	}
	public Salary(String employeeId,String shopId) {
		this.employeeId=employeeId;
		this.shopId= shopId;
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
	 * @return the advanceSalary
	 */
	public int getAdvanceSalary() {
		return advanceSalary;
	}

	/**
	 * @param advanceSalary the advanceSalary to set
	 */
	public void setAdvanceSalary(int advanceSalary) {
		this.advanceSalary = advanceSalary;
	}

	/**
	 * @return the totalSalary
	 */
	public int getTotalSalary() {
		return totalSalary;
	}

	/**
	 * @param totalSalary the totalSalary to set
	 */
	public void setTotalSalary(int totalSalary) {
		this.totalSalary = totalSalary;
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
	 * @return the salary
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * @return the deduction
	 */
	public int getDeduction() {
		return deduction;
	}

	/**
	 * @param deduction the deduction to set
	 */
	public void setDeduction(int deduction) {
		this.deduction = deduction;
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
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	 
	
}
