package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "EMPLOYEE")
@NamedQueries({ 
	@NamedQuery(name = "Employee.findAllEmployee",
				query = "SELECT em FROM Employee em"),
	 @NamedQuery(name="Employee.findByFirstName",
		        query="SELECT em FROM Employee em WHERE em.firstName= :firstName"),
	 @NamedQuery(name="Employee.findByEmail",
		        query="SELECT em FROM Employee em WHERE em.emailId = :emailId"),
	 @NamedQuery(name="Employee.findByEmployeeId",
                query="SELECT em FROM Employee em WHERE em.employeeId = :employeeId"),
	 @NamedQuery(name ="Employee.findByShopId",
	             query ="SELECT em FROM Employee em WHERE em.shopId = :shopId and isActive is TRUE and  isDeleted is FALSE"),
	 @NamedQuery(name ="Employee.findEmployeeByShopId",
                 query ="SELECT em FROM Employee em WHERE em.shopId = :shopId and em.id = :id"),
	 @NamedQuery(name="Employee.findByShopEmployeeId",
                query="SELECT em FROM Employee em WHERE em.shopId = :shopId and em.employeeId = :employeeId"),
	 @NamedQuery(name ="Employee.findEmployeeByEmployeeId",
	             query ="SELECT em FROM Employee em WHERE em.employeeId = :employeeId"),
	})

public class Employee implements Serializable {
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "F_NAME", nullable = false)
	private String firstName;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "L_NAME", nullable = false)
	private String lastName;

	@Column(name = "FATHER", nullable = false)
	private String father;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;

	@Column(name = "ADHAR_AVATAR", nullable = false)
	private String adharAvatar;

	@Column(name = "PAN_NUM", nullable = false)
	private String panNumber;

	@Column(name = "PAN_AVATAR", nullable = false)
	private String panAvatar;

	@Column(name = "SALARY", nullable = false)
	private int salary;

	@Column(name = "SALARY_TYPE", nullable = false)
	private int salaryType;

	@Column(name = "JOINING_DATE", nullable = false)
	private Date joiningDate;


	@Column(name = "WORKING_DAYS", nullable = false)
	private int workingDays;

	@Column(name = "DESIGNATION", nullable = false)
	private String designation;

	@Column(name = "EMPLOYEE_ID", nullable = false)
	private String employeeId;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;

	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "STREET", nullable = false)
	private String street;
	
	
	@Column(name = "LEAVING_DATE", nullable = false)
	private Date leavingDate;
	
	@Column(name = "VOTER_ID", nullable = false)
	private int voterId;
	
	@Column(name = "GENDER", nullable = false)
	private int gender;
	
//	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	private List<EmployeeAddress> employeeAddress;
//	
	public Employee() {
		super();
	}
	
	
	public Employee(String employeeId, String  shopId) {
		this.employeeId = employeeId;
		this.shopId = shopId;
	}
	
	@Transient
	private List<EmployeeAddress> employeeAddress;
	

	/**
	 * @return the employeeAddress
	 */
	public List<EmployeeAddress> getEmployeeAddress() {
		return employeeAddress;
	}


	/**
	 * @param employeeAddress the employeeAddress to set
	 */
	public void setEmployeeAddress(List<EmployeeAddress> employeeAddress) {
		this.employeeAddress = employeeAddress;
	}


	/**
	 * @return the voterId
	 */
	public int getVoterId() {
		return voterId;
	}


	/**
	 * @param voterId the voterId to set
	 */
	public void setVoterId(int voterId) {
		this.voterId = voterId;
	}


	/**
	 * @return the leavingDate
	 */
	public Date getLeavingDate() {
		return leavingDate;
	}


	/**
	 * @param leavingDate the leavingDate to set
	 */
	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}


//	/**
//	 * @return the employeeAddress
//	 */
//	public List<EmployeeAddress> getEmployeeAddress() {
//		return employeeAddress;
//	}
//
//
//	/**
//	 * @param employeeAddress the employeeAddress to set
//	 */
//	public void setEmployeeAddress(List<EmployeeAddress> employeeAddress) {
//		this.employeeAddress = employeeAddress;
//	}


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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the father
	 */
	public String getFather() {
		return father;
	}

	/**
	 * @param father the father to set
	 */
	public void setFather(String father) {
		this.father = father;
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
	 * @return the adharNumber
	 */
	public String getAdharNumber() {
		return adharNumber;
	}

	/**
	 * @param adharNumber the adharNumber to set
	 */
	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	/**
	 * @return the adharAvatar
	 */
	public String getAdharAvatar() {
		return adharAvatar;
	}

	/**
	 * @param adharAvatar the adharAvatar to set
	 */
	public void setAdharAvatar(String adharAvatar) {
		this.adharAvatar = adharAvatar;
	}

	/**
	 * @return the panNumber
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * @param panNumber the panNumber to set
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	/**
	 * @return the panAvatar
	 */
	public String getPanAvatar() {
		return panAvatar;
	}

	/**
	 * @param panAvatar the panAvatar to set
	 */
	public void setPanAvatar(String panAvatar) {
		this.panAvatar = panAvatar;
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
	 * @return the salaryType
	 */
	public int getSalaryType() {
		return salaryType;
	}

	/**
	 * @param salaryType the salaryType to set
	 */
	public void setSalaryType(int salaryType) {
		this.salaryType = salaryType;
	}

	/**
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return joiningDate;
	}

	/**
	 * @param joiningDate the joiningDate to set
	 */
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	

	/**
	 * @return the workingDays
	 */
	public int getWorkingDays() {
		return workingDays;
	}

	/**
	 * @param workingDays the workingDays to set
	 */
	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}


	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}


	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	


	
}
