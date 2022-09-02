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
@Table(name="vender")
@NamedQueries({ @NamedQuery(name = "Vender.findAll",
               query = "SELECT ve FROM Vender ve "),
	@NamedQuery(name ="Vender.venderExits",
			    query = "SELECT ve FROM Vender ve WHERE ve.shopId = :shopId and ve.mobileNo = :mobileNo"),
    @NamedQuery(name ="Vender.findById",
    		query = "SELECT ve FROM Vender ve WHERE ve.id = :id"),
    @NamedQuery(name ="Vender.findByShopId",
    		 query = "SELECT ve FROM Vender ve WHERE ve.shopId = :shopId and isActive is TRUE"),
    @NamedQuery(name ="Vender.findAllDetailsId",
    		query = "SELECT ve FROM Vender ve WHERE ve.id = :id"),
    @NamedQuery(name ="Vender.findByMobileNumber",
    		query = "SELECT ve FROM Vender ve WHERE ve.mobileNo = :mobileNo"),
    @NamedQuery(name ="Vender.checkExits",
    		query = "SELECT ve FROM Vender ve WHERE ve.shopId = :shopId and ve.id = :id")
	})
public class Vender implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "F_NAME", nullable = false)
	private String fristName;
	
	@Column(name = "L_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "EMAIL_ID", nullable = false)
	private String emailId;
	
	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name = "GST_NUMBER", nullable = false)
	private String gstNumber;
	
	@Column(name = "PIN_CODE", nullable = false)
	private int pinCode;
	
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Column(name = "STATE", nullable = false)
	private String state;
	
	@Column(name = "COUNTRY", nullable = false)
	private String country;
	
	@Column(name = "LAND_MARK", nullable = false)
	private String landMark;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
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
	
//	@Column(name = "ADMIN_ID", nullable = false)
//	private String adminId;
	
	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;
	
	@Column(name = "PAN_NUM", nullable = false)
	private String panNumber;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
//	@Column(name = "BILL_NUMBER", nullable = false)
//	private String billNumber;
//	
//	@Column(name = "BILLING_DATE", nullable = false)
//	private Date billingDate;
	
	@Column(name = "PURCHASE_AMOUNT", nullable = false)
	private int purchaseAmount;
	
	@Column(name = "BUSINESS_CATEGORY", nullable = false)
	private int businessCategory;
	
	@Column(name = "GST_TYPE", nullable = false)
	private int gstType;
	
//	@Column(name = "VARIANT_ID", nullable = false)
//	private int variantId;
	
	@Column(name ="ACCOUNT_ID", nullable = false)
	private int accountId;
	
	@Column(name ="CGST_AMOUNT", nullable = false)
	private float cgstAmount;
	
	@Column(name ="SGST_AMOUNT", nullable = false)
	private float sgstAmount;
	
	@Column(name ="IGST_AMOUNT", nullable = false)
	private float igstAmount;
	
	@Column(name ="UTGST_AMOUNT", nullable = false)
	private float utgstAmount;
	
	@Column(name ="PAID", nullable = false)
	private float paid;
	
	@Column(name ="DUES", nullable = false)
	private float dues;
	
	@Column(name ="ADDRESS", nullable = false)
	private String address;
	
	
	
	@Transient
	private Account account;
	
	@Transient
	private List<ItemList> itemList;
	
	@Transient
	private List<Slot> slotList;

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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	 * @return the landMark
	 */
	public String getLandMark() {
		return landMark;
	}

	/**
	 * @param landMark the landMark to set
	 */
	public void setLandMark(String landMark) {
		this.landMark = landMark;
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
	 * @return the businessCategory
	 */
	public int getBusinessCategory() {
		return businessCategory;
	}

	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(int businessCategory) {
		this.businessCategory = businessCategory;
	}

	/**
	 * @return the gstType
	 */
	public int getGstType() {
		return gstType;
	}

	/**
	 * @param gstType the gstType to set
	 */
	public void setGstType(int gstType) {
		this.gstType = gstType;
	}


	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the itemList
	 */
	public List<ItemList> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ItemList> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return the slotList
	 */
	public List<Slot> getSlotList() {
		return slotList;
	}

	/**
	 * @param slotList the slotList to set
	 */
	public void setSlotList(List<Slot> slotList) {
		this.slotList = slotList;
	}

	/**
	 * @return the purchaseAmount
	 */
	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}


	/**
	 * @return the paid
	 */
	public float getPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(float paid) {
		this.paid = paid;
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
	 * @return the cgstAmount
	 */
	public float getCgstAmount() {
		return cgstAmount;
	}

	/**
	 * @param cgstAmount the cgstAmount to set
	 */
	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	/**
	 * @return the sgstAmount
	 */
	public float getSgstAmount() {
		return sgstAmount;
	}

	/**
	 * @param sgstAmount the sgstAmount to set
	 */
	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	/**
	 * @return the igstAmount
	 */
	public float getIgstAmount() {
		return igstAmount;
	}

	/**
	 * @param igstAmount the igstAmount to set
	 */
	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}

	/**
	 * @return the utgstAmount
	 */
	public float getUtgstAmount() {
		return utgstAmount;
	}

	/**
	 * @param utgstAmount the utgstAmount to set
	 */
	public void setUtgstAmount(float utgstAmount) {
		this.utgstAmount = utgstAmount;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	
	

	
}
