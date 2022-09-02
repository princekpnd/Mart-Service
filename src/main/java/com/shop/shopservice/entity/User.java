package com.shop.shopservice.entity;

import java.io.Serializable;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.shop.shopservice.utils.PrePersistUtil;

/**
 * @author Avinash
 *
 */

@Entity
@Indexed
@Table(name = "USER")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByMobile", query = "SELECT u FROM User u WHERE u.emailId = :emailId"),
		@NamedQuery(name = "User.validatePwd", query = "SELECT u FROM User u WHERE u.emailId = :email and u.pwd = :pwd and u.isDeleted is FALSE or u.mobileNo = :email and u.pwd = :pwd and u.isDeleted is FALSE"),
		@NamedQuery(name = "User.findByOtp", query = "SELECT u FROM User u WHERE u.mobileNo = :mobileNo and u.otp = :otp"),
		@NamedQuery(name = "User.findOtpByMobileNumber", query = "SELECT u FROM User u WHERE u.mobileNo = :mobileNo"),
		@NamedQuery(name = "User.findByUserIdAndShopId", query ="SELECT u FROM User u WHERE u.userId = :userId and u.shopId = :shopId"),
		@NamedQuery(name = "User.findByMobileNumber", query ="SELECT u FROM User u WHERE u.mobileNo = :mobileNo and isActive is FALSE"),
		@NamedQuery(name = "User.findByEmail", query ="SELECT u FROM User u WHERE u.emailId = :emailId"),
        @NamedQuery(name ="User.findByShopId",query ="SELECT u FROM User u WHERE u.shopId = :shopId"),
        @NamedQuery(name="User.getByOtpAndEmail", query ="SELECT u FROM User u WHERE u.otp = :otp and u.emailId = :emailId"),
        @NamedQuery(name ="User.getByOtpAndResendOtp",query ="SELECT u FROM User u WHERE u.emailId = :emailId"),
        @NamedQuery(name ="User.checkExitByEmailId", query ="SELECT u FROM User u WHERE u.emailId = :emailId or u.mobileNo = :mobileNo") ,
        @NamedQuery(name ="User.getByRefralNumber", query ="SELECT u FROM User u WHERE u.refralNumber = :refralNumber"),
        @NamedQuery(name ="User.getByUserActiveByMobileNumber", query ="SELECT u FROM User u WHERE u.mobileNo = :mobileNo and isActive is TRUE"),
        @NamedQuery(name ="User.findByRefralNumber", query ="SELECT u FROM User u WHERE u.mobileNo = :mobileNo")
//    @NamedQuery(name="User.addWishList",
//                query="SELECT uFROM User u WHERE u.userId = :userId and u.productId = :productId"),

})
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1385794955661915701L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int userId;

	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "F_NAME", nullable = false)
	private String firstName;

	
	@Column(name = "L_NAME", nullable = false)
	private String lastName;

	@Column(name = "USER_TYPE", nullable = false)
	private int userType;

	@Column(name = "UNAME", nullable = false)
	private String name;

	@Column(name = "TOKEN", nullable = false)
	private String token;

	@Column(name = "WISH_COUNT", nullable = false)
	private int wishCount;

	@Transient
	private String displayName;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;

	@Column(name = "WISH_LIST", nullable = false)
	private String wishList;

	@Column(name = "ACTIVE_IND", nullable = false)
	private boolean isActive;

	@Column(name = "PWD", nullable = false)
	private String pwd;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "OTP", nullable = false)
	private String otp;
	
	@Column(name = "DUES", nullable = false)
	private float dues;
	
	@Column(name = "LAST_PAID", nullable = false)
	private float LastPaid;
	
	@Column(name = "LAST_PAID_ON", nullable = false)
	private Date LastPaidOn;
	
	@Column(name = "DUES_STATUS", nullable = false)
	private boolean duesStatus;
	
	@Column(name = "AVATAR", nullable = false)
	private String avatar;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "GENDER", nullable = false)
	private int gender;
	
	@Column(name = "WALLET_BALANCE", nullable = false)
	private float walletBalance;
	
	@Column(name = "PLAYER_ID", nullable = false)
	private String  playerId;
	
	@Column(name = "LOGED_IN", nullable = false)
	private boolean logedIn;
	
	@Column(name = "REFRAL_NUMBER", nullable = false)
	private String refralNumber;
	
	@Column(name = "IS_FIRST_ORDER", nullable = false)
	private boolean isFirstOrder;
	
	
	
	@Transient
	private List<Address> address;

	

	/**
	 * @return the address
	 */
	public List<Address> getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(List<Address> address) {
		this.address = address;
	}

	/**
	 * @return the walletBalance
	 */
	public float getWalletBalance() {
		return walletBalance;
	}

	/**
	 * @param walletBalance the walletBalance to set
	 */
	public void setWalletBalance(float walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Column(name = "LAST_LOGIN_DATE", nullable = false)
	private Date lastLoginDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserDeviceID> userDeviceIDList;

	
	/**
	 * @return the userDeviceIDList
	 */
	public List<UserDeviceID> getUserDeviceIDList() {
		return userDeviceIDList;
	}

	/**
	 * @param userDeviceIDList the userDeviceIDList to set
	 */
	public void setUserDeviceIDList(List<UserDeviceID> userDeviceIDList) {
		this.userDeviceIDList = userDeviceIDList;
	}

	

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
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
	 * @return the wishList
	 */
	public String getWishList() {
		return wishList;
	}

	/**
	 * @param wishList the wishList to set
	 */
	public void setWishList(String wishList) {
		this.wishList = wishList;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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

	public int getUserId() {
		return userId;
	}

	public User() {
		super();
	}

	public User(String mobileNo, String name) {
		this.mobileNo = mobileNo;
		this.name = name;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getDisplayName() {
		return name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the userType
	 */

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

	@PrePersist
	void preInsert() {
		PrePersistUtil.pre(this);
	}

	/**
	 * @return the wishCount
	 */
	public int getWishCount() {
		return wishCount;
	}

	/**
	 * @param wishCount the wishCount to set
	 */
	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the dues
	 */
	public float getDues() {
		return dues;
	}

	/**
	 * @param dues the dues to set
	 */
	public void setDues(float dues) {
		this.dues = dues;
	}

	/**
	 * @return the lastPaid
	 */
	public float getLastPaid() {
		return LastPaid;
	}

	/**
	 * @param lastPaid the lastPaid to set
	 */
	public void setLastPaid(float lastPaid) {
		LastPaid = lastPaid;
	}

	/**
	 * @return the lastPaidOn
	 */
	public Date getLastPaidOn() {
		return LastPaidOn;
	}

	/**
	 * @param lastPaidOn the lastPaidOn to set
	 */
	public void setLastPaidOn(Date lastPaidOn) {
		LastPaidOn = lastPaidOn;
	}

	/**
	 * @return the duesStatus
	 */
	public boolean isDuesStatus() {
		return duesStatus;
	}

	/**
	 * @param duesStatus the duesStatus to set
	 */
	public void setDuesStatus(boolean duesStatus) {
		this.duesStatus = duesStatus;
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

	public String getRefralNumber() {
		return refralNumber;
	}

	public void setRefralNumber(String refralNumber) {
		this.refralNumber = refralNumber;
	}

	public boolean isFirstOrder() {
		return isFirstOrder;
	}

	public void setFirstOrder(boolean isFirstOrder) {
		this.isFirstOrder = isFirstOrder;
	}
	

}
