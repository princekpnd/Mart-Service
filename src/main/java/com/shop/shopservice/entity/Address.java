package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Indexed
@Table(name = "Address")
          @NamedQueries({ @NamedQuery(name = "Address.findAllAddress", 
                        query = "SELECT add FROM Address add"),
//          @NamedQuery(name ="Adderss.findByUserId",
//                       query = "SELECT add FROM Address add WHERE add.userId =:userId"),
          @NamedQuery(name ="Address.findByUserId",
                        query= "SELECT add FROM Address add WHERE add.userId = :userId"),
          @NamedQuery(name ="Address.findByShopId",
                        query="SELECT add FROM Address add WHERE add.shopId= :shopId"),
          @NamedQuery(name ="Address.findAddressByShopId",
                        query = "SELECT add FROM Address add WHERE add.shopId =:shopId  and add.isActive is TRUE and add.isDeleted is FALSE "),
          @NamedQuery(name ="Address.findByUserIdAndShopId",
                         query= "SELECT add FROM Address add WHERE add.userId = :userId and add.shopId = :shopId"),
          @NamedQuery(name ="Address.findDefaultAddress",
                       query= "SELECT add FROM Address add WHERE add.userId = :userId  and add.defaultAddress is TRUE"),
        
	})

public class Address implements Serializable {

	private static final long serialVersionUID = 1385794955661915701L;
	

	//@NotNull
//			@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	        @JoinColumn(name = "USER_ID", nullable = false)
//		    @OnDelete(action = OnDeleteAction.CASCADE)
//	        @JsonIgnore
	        
	        
	        
//	        private Admin admin;
	//		private User user;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
//	@Column(name ="ADMIN_ID",nullable = false)
//	private int adminId;
	
	@Column(name = "PIN_CODE", nullable = false)
	private int pinCode;
	
	@Field(store = Store.NO)
	@NotNull
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
	

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "STREET", nullable = false)
	private String street;
	

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

	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "POST_OFFICE", nullable = false)
	private String postOffice;

	@Column(name = "POLICE_STATION", nullable = false)
	private String policeStation;
	
	@Column(name ="DEFAULT_ADDRESS", nullable = false)
	private boolean defaultAddress;

	public Address() {
		super();
	}

	public Address(String shopId, String userId) {
		this.shopId = shopId;
		this.userId = userId;
	}

	
	
//	/**
//	 * @return the user
//	 */
//	public User getUser() {
//		return user;
//	}
//
//	/**
//	 * @param user the user to set
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}

//	/**
//	 * @return the admin
//	 */
//	public Admin getAdmin() {
//		return admin;
//	}
//
//	/**
//	 * @param admin the admin to set
//	 */
//	public void setAdmin(Admin admin) {
//		this.admin = admin;
//	}
//
//	/**
//	 * @return the adminId
//	 */
//	public int getAdminId() {
//		return adminId;
//	}
//
//	/**
//	 * @param adminId the adminId to set
//	 */
//	public void setAdminId(int adminId) {
//		this.adminId = adminId;
//	}
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @return the defaultAddress
	 */
	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	/**
	 * @param defaultAddress the defaultAddress to set
	 */
	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

//	/**
//	 * @return the userId
//	 */
//	public int getUserId() {
//		return userId;
//	}
//
//	/**
//	 * @param userId the userId to set
//	 */
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}

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
}
