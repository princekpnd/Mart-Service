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
@Table(name = "EADDRESS")
@NamedQueries({
	@NamedQuery(name ="EmployeeAddress.findAllEmployeeAddress" ,
			    query = "SELECT ema FROM EmployeeAddress ema"),
	@NamedQuery(name ="EmployeeAddress.findbyShopId",
	            query="SELECT ema FROM EmployeeAddress ema WHERE ema.shopId= :shopId"),
	@NamedQuery(name ="EmployeeAddress.findByEmployeeId",
               query="SELECT ema FROM EmployeeAddress ema WHERE ema.employeeId= :employeeId"),
	@NamedQuery(name ="EmployeeAdderss.createAddressByEmployeeId",
                query="SELECT ema FROM EmployeeAddress ema WHERE ema.employeeId= :employeeId"),
	@NamedQuery(name ="EmployeeAddress.findAddressByEmployeeId",
               query="SELECT ema FROM EmployeeAddress ema WHERE ema.employeeId= :employeeId"),
	
})
public class EmployeeAddress  implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;
	
//	//@NotNull
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    
//    private Employee employee;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
//	/**
//	 * @return the employee
//	 */
//	public Employee getEmployee() {
//		return employee;
//	}
//
//	/**
//	 * @param employee the employee to set
//	 */
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}

	@Column(name = "PIN_CODE", nullable = false)
	private int pinCode;

	@Column(name = "CITY", nullable = false)
	private String city;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "COUNTRY", nullable = false)
	private String country;

	@Column(name = "LATITUDE", nullable = false)
	private String latitude;

	@Column(name = "LONGITUDE", nullable = false)
	private String longitude;

	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;

	@Column(name = "LAND_MARK", nullable = false)
	private String landmark;


	@Column(name = "USER_TYPE", nullable = false)
	private int userType;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "DISTRICT", nullable = false)
	private String district;

	@Column(name = "POST_OFFICE", nullable = false)
	private String postOffice;

	@Column(name = "POLICE_STATION", nullable = false)
	private String policeStation;
	
	@Column(name = "EMPLOYEE_ID", nullable = false)
	private String employeeId;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "STREET", nullable = false)
	private String street;
	
	
	public EmployeeAddress() {
		super();
	}
	
	public EmployeeAddress(String shopId, String employeeId) {
		this.shopId = shopId;
		this.employeeId = employeeId;
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
	 * @return the pinCode
	 */
	public int getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
	 * @return the landmark
	 */
	public String getLandmark() {
		return landmark;
	}

	/**
	 * @param landmark the landmark to set
	 */
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(int userType) {
		this.userType = userType;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the postOffice
	 */
	public String getPostOffice() {
		return postOffice;
	}

	/**
	 * @param postOffice the postOffice to set
	 */
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	/**
	 * @return the policeStation
	 */
	public String getPoliceStation() {
		return policeStation;
	}

	/**
	 * @param policeStation the policeStation to set
	 */
	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
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

	

}
