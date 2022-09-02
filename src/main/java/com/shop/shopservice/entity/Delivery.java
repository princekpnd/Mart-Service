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

@Entity
@Table(name = "DELIVERY")
@NamedQueries({ 
	@NamedQuery(name = "Delivery.findAll",
			   query = "SELECT de FROM Delivery de WHERE de.isActive is TRUE"),
	@NamedQuery(name ="Delivery.exitsDelivery",
	           query ="SELECT de FROM Delivery de WHERE de.shopId = :shopId and de.mobileNo = :mobileNo"),
	@NamedQuery(name ="Delivery.getByShopIdAndMobileNumber",
			   query ="SELECT de FROM Delivery de WHERE de.shopId = :shopId and de.mobileNo = :mobileNo"),
	@NamedQuery(name ="Delivery.findTotalAmount",
			   query ="SELECT de FROM Delivery de WHERE de.shopId = :shopId and de.mobileNo = :mobileNo"),
	@NamedQuery(name ="Delivery.loginDelivery",
			    query ="SELECT de FROM Delivery de WHERE de.mobileNo = :mobileNo and de.pwd = :pwd"),
	@NamedQuery(name ="Delivery. getDeActive",
			   query ="SELECT de FROM Delivery de WHERE de.id = :id"),
	@NamedQuery(name ="Delivery. checkByMobileNumber",
			  query ="SELECT de FROM Delivery de WHERE de.mobileNo = :mobileNo" ),
	@NamedQuery(name ="Delivery. checkByPwd",
			 query ="SELECT de FROM Delivery de WHERE de.pwd = :pwd" ),
	@NamedQuery(name ="Delivery. checkByPwdAndMobileNumber",
			 query ="SELECT de FROM Delivery de WHERE de.pwd = :pwd and de.mobileNo = :mobileNo")
	})
public class Delivery implements Serializable {
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "FRIST_NAME", nullable = false)
	private String fristName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;
	
	@Column(name = "PAN_PAN", nullable = false)
	private String panNumber;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "CART_ID", nullable = false)
	private int cartId;
	
	@Column(name = "PIN_CODE", nullable = false)
	private int pinCode;
	
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Column(name = "STATE", nullable = false)
	private String state;
	
	@Column(name = "COUNTRY", nullable = false)
	private String country;
	
	@Column(name = "POST_OFFICE", nullable = false)
	private String postOffice;
	
	@Column(name = "POLICE_STATION", nullable = false)
	private String policeStation;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "AVATAR", nullable = false)
	private String avatar;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "GENDER", nullable = false)
	private int gender;
	
	@Column(name = "DISTRICT", nullable = false)
	private String district;
	
	@Column(name ="TOTAL_AMOUNT", nullable = false)
	private int totalAmount;
	
	@Column(name ="DUES", nullable = false)
	private int dues;
	
	@Column(name ="PAID", nullable = false)
	private int paid;
	
	@Column(name ="PWD", nullable = false)
	private String pwd;
	
	@Column(name ="PLAYER_ID", nullable = false)
	private String playerId;
	
	@Column(name ="TOKEN", nullable = false)
	private String token;
	
	
	@Transient
	private List<Cart> cartList;
	
	@Transient
	private List<ProductList> productList;
	
	public Delivery() {
		super();
	}
	
	public Delivery(String mobileNo, String shopId) {
		this.mobileNo = mobileNo;
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
	 * @return the fristName
	 */
	public String getFristName() {
		return fristName;
	}

	/**
	 * @param fristName the fristName to set
	 */
	public void setFristName(String fristName) {
		this.fristName = fristName;
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
	 * @return the cartId
	 */
	public int getCartId() {
		return cartId;
	}

	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(int cartId) {
		this.cartId = cartId;
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
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	 * @return the cartList
	 */
	public List<Cart> getCartList() {
		return cartList;
	}

	/**
	 * @param cartList the cartList to set
	 */
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	/**
	 * @return the totalAmount
	 */
	public int getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the productList
	 */
	public List<ProductList> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductList> productList) {
		this.productList = productList;
	}

	/**
	 * @return the dues
	 */
	public int getDues() {
		return dues;
	}

	/**
	 * @param dues the dues to set
	 */
	public void setDues(int dues) {
		this.dues = dues;
	}

	/**
	 * @return the paid
	 */
	public int getPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(int paid) {
		this.paid = paid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
	
}
