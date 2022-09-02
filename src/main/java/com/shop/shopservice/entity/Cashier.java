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
@Table(name ="CASHIER")
@NamedQueries({ 
	@NamedQuery(name = "Cashier.getAll",
			query = "SELECT ca FROM Cashier ca"),
	@NamedQuery(name = "Cashier.getById",
			query = "SELECT ca FROM Cashier ca WHERE ca.cashierId = :cashierId"),
	@NamedQuery(name= "Cashier.chrekCashier", 
			query = "SELECT ca FROM Cashier ca WHERE ca.mobileNo = :mobileNo and ca.shopId =:shopId"),
	@NamedQuery(name ="Cashier.getByShopId",
			query = "SELECT ca FROM Cashier ca WHERE ca.shopId = :shopId"),
	@NamedQuery(name ="Cashier.findByMobileNumber",
			query = "SELECT ca FROM Cashier ca WHERE ca.mobileNo = :mobileNo"),
	@NamedQuery(name ="Cashier.getAllDeactive",
			query = "SELECT ca FROM Cashier ca WHERE ca.cashierId = :cashierId"),
	@NamedQuery(name = "Cashier.loginCashier",
		   query = "SELECT ca FROM Cashier ca WHERE ca.mobileNo = :mobileNo and ca.pwd = :pwd"),
	@NamedQuery(name ="Cashier.daactiveCashier",
		  query = "SELECT ca FROM Cashier ca WHERE ca.mobileNo = :mobileNo and isActive is false"),
	@NamedQuery(name ="Cashier.getAllAmount",
		  query = "SELECT ca FROM Cashier ca WHERE ca.cashierId = :cashierId and ca.shopId = :shopId" )
})
public class Cashier implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int cashierId;
	
	@Column(name = "FRIST_NAME", nullable = false)
	private String fristName;
	
	@Column(name = "L_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;
	
	@Column(name = "PAN_NUM", nullable = false)
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
	
	@Column(name ="TOKEN", nullable = false)
	private String token;
	
	@Column(name = "OTP", nullable = false)
	private int otp;
	
	@Column(name = "PLAYER_ID", nullable = false)
	private String playerId;
	
	@Transient
	private List<Offline> offline;
	
	public Cashier() {
		super();
	}

	public int getCashierId() {
		return cashierId;
	}

	public void setCashierId(int cashierId) {
		this.cashierId = cashierId;
	}

	public String getFristName() {
		return fristName;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostOffice() {
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getDues() {
		return dues;
	}

	public void setDues(int dues) {
		this.dues = dues;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public List<Offline> getOffline() {
		return offline;
	}

	public void setOffline(List<Offline> offline) {
		this.offline = offline;
	}
}
