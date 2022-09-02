 package com.shop.shopservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "ADMIN")
@NamedQueries({ 
	@NamedQuery(name = "Admin.findAll",
				query = "SELECT ad FROM Admin ad"),
	@NamedQuery(name ="Admin.findAllActiveAdmin",
	             query ="SELECT ad FROM Admin ad WHERE ad.isActive is TRUE"),
	 @NamedQuery(name="Admin.findByFirstName",
		        query="SELECT ad FROM Admin ad WHERE ad.firstName= :firstName"),
	 @NamedQuery(name="Admin.findByEmail",
		        query="SELECT ad FROM Admin ad WHERE ad.emailId = :emailId"),
	 @NamedQuery(name="Admin.checkByEmail",
     			query="SELECT ad FROM Admin ad WHERE ad.emailId = :emailId and isActive is TRUE"),
	 @NamedQuery(name="Admin.checkDeactiveByEmail",
		         query="SELECT ad FROM Admin ad WHERE ad.emailId = :emailId and isActive is FALSE"),
	 @NamedQuery(name="Admin.findByShop",
		        query="SELECT ad FROM Admin ad WHERE ad.shopId = :shopId and isActive is TRUE and isDeleted is FALSE"),
	 @NamedQuery(name = "Admin.findAdminByAdharNumber",
		        query = "SELECT ad FROM Admin ad WHERE ad.adharNumber = :adharNumber"),
	 @NamedQuery(name="Admin.validatePwd",
                query="SELECT ad FROM Admin ad WHERE ad.emailId = :emailId and ad.pwd = :pwd and ad.isActive is TRUE and ad.isDeleted is FALSE"),
	 @NamedQuery(name="Admin.validateDeactiveAdmin",
     			query="SELECT ad FROM Admin ad WHERE ad.emailId = :emailId and ad.pwd = :pwd and ad.isActive is FALSE and ad.isDeleted is FALSE"),
	 @NamedQuery(name="Admin.findByShopId",
		         query="SELECT ad FROM Admin ad WHERE ad.mobileNo = :mobileNo "),
	 @NamedQuery(name="Admin.findAdminByProductIdAndShopId",
	            query="SELECT ad FROM Admin ad WHERE ad.adminId = :adminId and shopId = :shopId"),
	 @NamedQuery(name="Admin.findByMobileNo",
	             query= "SELECT ad FROM Admin ad WHERE ad.mobileNo = :mobileNo "),
	 @NamedQuery(name="Admin.findByShopType",
     			 query= "SELECT ad FROM Admin ad WHERE ad.shopType = :shopType and ad.isActive is TRUE"),
	 @NamedQuery(name ="Admin.findAdminByShopId",
	              query="SELECT ad FROM Admin ad WHERE ad.shopId = :shopId"),
	 @NamedQuery(name ="Admin.checkDeactiveByAdminId",
	             query = "SELECT ad FROM Admin ad WHERE ad.adminId = :adminId and ad.registrationStatus = :registrationStatus"),
	@NamedQuery(name ="Admin.findActiveAdmin",
	             query ="SELECT ad FROM Admin ad WHERE ad.shopId = :shopId and ad.isActive is TRUE"),
	@NamedQuery(name = "Admin.adminActive",
	            query="SELECT ad FROM Admin ad WHERE ad.shopId = :shopId and ad.isActive is TRUE")
	})

public class Admin implements Serializable {

	private static final long serialVersionUID = 1385794955661915701L;

	@Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int adminId;
	
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "F_NAME", nullable = false)
	private String firstName;

	@Column(name = "L_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "TOKEN", nullable = true)
	private String token;

	@Column(name = "AVATAR", nullable = false)
	private String avatar;

	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;


	@Column(name = "PAN_NUM", nullable = false)
	private String panNumber;

	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "PWD", nullable = false)
	private String pwd;

	
	@Column(name = "PAYMENT_STATUS", nullable = false)
	private boolean paymentStatus;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;

	@Column(name = "SHOP_TYPE", nullable = false)
	private int shopType;

	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;

	@Column(name = "VALIDITY", nullable = true)
	private int validity;

	@Column(name = "WALLET", nullable = false)
	private float wallet;
	
	@Column(name = "AVAILABLE_AMOUNT", nullable = false)
	private float availableAmount;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "GST_NUMBER", nullable = false)
	private String gstNumber ;
	
	@Column(name = "LATITUDE", nullable = false)
	private String latitude ;
	
	@Column(name = "LONGITUDE", nullable = false)
	private String longitude ;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@OneToMany(mappedBy="admin",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<AdminDeviceID> adminDeviceIDList;
	
//	@OneToMany(mappedBy="admin",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	private List<AdminAddress> adminAddress;
	
//	@OneToMany(mappedBy="admin",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	private List<Address> address;
//	
	@Column(name = "Last_Login_Date")
	private Date lastLoginDate;
	
	@Column(name = "OTP", nullable = false)
	private String otp;
	
	@Column(name ="GENDER", nullable = false)
	private int gender;
	
	@Column(name ="REGISTRATION_STATUS", nullable = false)
	private int registrationStatus;
	
	@Column(name ="VALIDITY_EXPIRY_DATE", nullable = false)
	private LocalDateTime validityExpiryDate;
	
	@Column(name ="VALIDITY_UPDATED_ON", nullable = false)
	private Date validityUpdatedOn;
	
	@Column(name ="PLAN_PURCHASED_ON", nullable = false)
	private Date planPurchasedOn;
	
	@Column(name ="REFRAL_CODE", nullable = false)
	private String refralCode;
	
	@Column(name ="LOGED_IN", nullable = false)
	private boolean logedIn;
	
	@Column(name ="PLAYER_ID", nullable = false)
	private String playerId;
	

	public Admin() {
		super();
	}
	
	@Transient
	private List<ShopImage> shopImage;
	
	@Transient
	private List<UserAddress> adminAddress;
	
	
	/**
	 * @return the adminAddress
	 */
	public List<UserAddress> getAdminAddress() {
		return adminAddress;
	}

	/**
	 * @param adminAddress the adminAddress to set
	 */
	public void setAdminAddress(List<UserAddress> adminAddress) {
		this.adminAddress = adminAddress;
	}

	/**
	 * @return the shopImage
	 */
	public List<ShopImage> getShopImage() {
		return shopImage;
	}

	/**
	 * @param shopImage the shopImage to set
	 */
	public void setShopImage(List<ShopImage> shopImage) {
		this.shopImage = shopImage;
	}	

//	public Admin(String shopId, String adharNumber) {
//		this.shopId = shopId;
//		this.adharNumber = adharNumber;
//	}
	
	public Admin(String mobileNo, String pwd) {
		this.mobileNo = mobileNo;
		this.pwd = pwd;
	}

	/**
	 * @return the adminId
	 */
	public int getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(int adminId) {
		this.adminId = adminId;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}



	/**
	 * @return the paymentStatus
	 */
	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	 * @return the shopType
	 */
	public int getShopType() {
		return shopType;
	}

	/**
	 * @param shopType the shopType to set
	 */
	public void setShopType(int shopType) {
		this.shopType = shopType;
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
	 * @return the validity
	 */
	public int getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(int validity) {
		this.validity = validity;
	}

	/**
	 * @return the wallet
	 */
	public float getWallet() {
		return wallet;
	}

	/**
	 * @param wallet the wallet to set
	 */
	public void setWallet(float wallet) {
		this.wallet = wallet;
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
	 * @return the adminDeviceIDList
	 */
	public List<AdminDeviceID> getAdminDeviceIDList() {
		return adminDeviceIDList;
	}

	/**
	 * @param adminDeviceIDList the adminDeviceIDList to set
	 */
	public void setAdminDeviceIDList(List<AdminDeviceID> adminDeviceIDList) {
		this.adminDeviceIDList = adminDeviceIDList;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the gstNumber
	 */
	public String getGstNumber() {
		return gstNumber;
	}

	/**
	 * @param gstNumber the gstNumber to set
	 */
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
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
	 * @return the registrationStatus
	 */
	public int getRegistrationStatus() {
		return registrationStatus;
	}

	/**
	 * @param registrationStatus the registrationStatus to set
	 */
	public void setRegistrationStatus(int registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the validityExpiryDate
	 */
	public LocalDateTime getValidityExpiryDate() {
		return validityExpiryDate;
	}

	/**
	 * @param validityExpiryDate the validityExpiryDate to set
	 */
	public void setValidityExpiryDate(LocalDateTime validityExpiryDate) {
		this.validityExpiryDate = validityExpiryDate;
	}

	/**
	 * @return the validityUpdatedOn
	 */
	public Date getValidityUpdatedOn() {
		return validityUpdatedOn;
	}

	/**
	 * @param validityUpdatedOn the validityUpdatedOn to set
	 */
	public void setValidityUpdatedOn(Date validityUpdatedOn) {
		this.validityUpdatedOn = validityUpdatedOn;
	}

	/**
	 * @return the planPurchasedOn
	 */
	public Date getPlanPurchasedOn() {
		return planPurchasedOn;
	}

	/**
	 * @param planPurchasedOn the planPurchasedOn to set
	 */
	public void setPlanPurchasedOn(Date planPurchasedOn) {
		this.planPurchasedOn = planPurchasedOn;
	}

	/**
	 * @return the refralCode
	 */
	public String getRefralCode() {
		return refralCode;
	}

	/**
	 * @param refralCode the refralCode to set
	 */
	public void setRefralCode(String refralCode) {
		this.refralCode = refralCode;
	}

	/**
	 * @return the logedIn
	 */
	public boolean isLogedIn() {
		return logedIn;
	}

	/**
	 * @param logedIn the logedIn to set
	 */
	public void setLogedIn(boolean logedIn) {
		this.logedIn = logedIn;
	}

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the availableAmount
	 */
	public float getAvailableAmount() {
		return availableAmount;
	}

	/**
	 * @param availableAmount the availableAmount to set
	 */
	public void setAvailableAmount(float availableAmount) {
		this.availableAmount = availableAmount;
	}




//	/**
//	 * @return the address
//	 */
//	public List<Address> getAddress() {
//		return address;
//	}
//
//	/**
//	 * @param address the address to set
//	 */
//	public void setAddress(List<Address> address) {
//		this.address = address;
//	}

	
	

	

	
}
