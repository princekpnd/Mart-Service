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
@Table(name="withdraw_request")
@NamedQueries({ @NamedQuery(name = "Withdrawal.findAll",
                             query = "SELECT wi FROM Withdraw wi"),
	            @NamedQuery(name= "Withdraw.findByShopId",
	                         query="SELECT wi FROM Withdraw wi WHERE wi.shopId = :shopId"),
	            @NamedQuery(name ="Withdraw.findByUserId",
	                         query="SELECT wi FROM Withdraw wi WHERE wi.userId = :userId"),
	            @NamedQuery(name ="Withdraw.findByShopIdAndUserId",
	                        query = "SELECT wi FROM Withdraw wi WHERE wi.shopId = :shopId and wi.userId = :userId"),
	            @NamedQuery(name= "Withdraw.exitsWithdraw",
	                        query ="SELECT wi FROM Withdraw wi WHERE wi.accountNum = :accountNum and wi.accountHolderName = :accountHolderName and wi.bankName = :bankName"),
	            @NamedQuery(name ="Withdraw.findActiveWithdraw",
	                        query ="SELECT wi FROM Withdraw wi WHERE wi.shopId = :shopId and  isActive is TRUE and  isDeleted is FALSE"),
	            @NamedQuery(name ="Withdraw.checkActiveWithdraw",
	                        query ="SELECT wi FROM Withdraw wi WHERE wi.adminId = :adminId and wi.isActive = :isActive"),
})
public class Withdraw implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "ACCOUNT_HOLDER_NAME", nullable = false)
	private String accountHolderName;

	@Column(name = "ACCOUNT_NUM", nullable = false)
	private String accountNum;

	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;
	
	@Column(name = "ACCOUNT_ID", nullable = false)
	private int accountId;
	
	@Column(name = "WITHDRAW_BALANCE", nullable = false)
	private int withdrawBalance;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;

	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name ="SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "MOBILE_NUMBER", nullable= false)
	private String mobileNo;
	
	@Column(name ="WITHDRAW_STATUS", nullable = false)
	private int withdrawStatus;
	
	@Column(name ="REQUEST_TRANSACTION_ID", nullable = false)
	private int requestTransactionId;
	
	@Column(name ="PAID_TRANSACTION_ID", nullable = false)
	private int paidTransactionId;
	
	
	
	
	public Withdraw(String accountNum,String bankName, String accountHolderName) {
		this.accountNum = accountNum;
		this.bankName = bankName;
		this.accountHolderName = accountHolderName;
	}
	
	public Withdraw() {
		super();
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
	 * @return the withdrawBalance
	 */
	public int getWithdrawBalance() {
		return withdrawBalance;
	}

	/**
	 * @param withdrawBalance the withdrawBalance to set
	 */
	public void setWithdrawBalance(int withdrawBalance) {
		this.withdrawBalance = withdrawBalance;
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return the withdrawStatus
	 */
	public int isWithdrawStatus() {
		return withdrawStatus;
	}

	/**
	 * @param withdrawStatus the withdrawStatus to set
	 */
	public void setWithdrawStatus(int withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	/**
	 * @return the requestTransactionId
	 */
	public int getRequestTransactionId() {
		return requestTransactionId;
	}

	/**
	 * @param requestTransactionId the requestTransactionId to set
	 */
	public void setRequestTransactionId(int requestTransactionId) {
		this.requestTransactionId = requestTransactionId;
	}

	/**
	 * @return the paidTransactionId
	 */
	public int getPaidTransactionId() {
		return paidTransactionId;
	}

	/**
	 * @param paidTransactionId the paidTransactionId to set
	 */
	public void setPaidTransactionId(int paidTransactionId) {
		this.paidTransactionId = paidTransactionId;
	}

	

	/**
	 * @return the withdrawStatus
	 */
	public int getWithdrawStatus() {
		return withdrawStatus;
	}

}

