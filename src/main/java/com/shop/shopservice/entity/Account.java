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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shop.shopservice.utils.PrePersistUtil;

@Entity
@Table(name = "ACCOUNT")
@NamedQueries({ @NamedQuery(name = "Account.findAll",
                query = "SELECT ac FROM Account ac"),
	@NamedQuery(name = "Account.findAccountByUserId",
	            query = "SELECT ac FROM Account ac WHERE ac.adminId = :adminId"),
	 @NamedQuery(name="Account.findByBankName",
		        query="SELECT ac FROM Account ac WHERE ac.bankName = :bankName"),
	 @NamedQuery(name = "Account.findAccountByMobileNumber",
		        query = "SELECT ac FROM Account ac WHERE ac.mobileNo = :mobileNo"),
	 @NamedQuery(name = "Account.findAccountByAccountNumber",
		        query = "SELECT ac FROM Account ac WHERE ac.accountNum = :accountNum"),
	 @NamedQuery(name="Account.findByUserId",
		         query="SELECT ac FROM Account ac WHERE ac.adminId = :adminId"),
	 @NamedQuery(name = "Account.findAccountByShopId",
	             query = "SELECT ac FROM Account ac WHERE ac.shopId = :shopId "),
	 @NamedQuery(name ="Account.findByVenderId",
			 query = "SELECT ac FROM Account ac WHERE ac.venderId = :venderId")
	
})



public class Account implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "ACCOUNT_NUM", nullable = false)
	private String accountNum;
	
	
	@Column(name = "IFSC", nullable = false)
	private String ifsc;
	
	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;	
	
	@Column(name = "CURRENCY", nullable = false)
	private int currency;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "UPDATED_ON", nullable = false)
	private Date updatedOn;
	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;	

	@Column(name = "ACCOUNT_HOLDER_NAME", nullable = false)
	private String accountHolderName;
	
	@Column(name = "BRANCH_NAME", nullable = false)
	private String branchName;
	
	@Column(name = "ADHAR_NUM", nullable = false)
	private String adharNumber;
	
	@Column(name = "PAN_NUM", nullable = false)
	private String panNumber;
	
	@Column(name = "VENDER_ID", nullable = false)
	private int venderId;
	
	

	public Account() {
		super();
	}
	
	public Account(int adminId, String accountHolderName) {
		this.adminId = adminId;
		this.accountHolderName = accountHolderName;
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
	 * @return the accountNum
	 */
	public String getAccountNum() {
		return accountNum;
	}

	/**
	 * @param accountNum the accountNum to set
	 */
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	/**
	 * @return the ifsc
	 */
	public String getIfsc() {
		return ifsc;
	}

	/**
	 * @param ifsc the ifsc to set
	 */
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the currency
	 */
	public int getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(int currency) {
		this.currency = currency;
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
	 * @return the updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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
	 * @return the accountHolderName
	 */
	public String getAccountHolderName() {
		return accountHolderName;
	}

	/**
	 * @param accountHolderName the accountHolderName to set
	 */
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * @return the venderId
	 */
	public int getVenderId() {
		return venderId;
	}

	/**
	 * @param venderId the venderId to set
	 */
	public void setVenderId(int venderId) {
		this.venderId = venderId;
	}


}
