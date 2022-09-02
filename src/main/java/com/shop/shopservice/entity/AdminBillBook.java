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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ADMIN_BILL_BOOK")
@NamedQueries({
	@NamedQuery(name = "AdminBillBook.findAll",
			query = "SELECT abl FROM AdminBillBook abl"),
	@NamedQuery(name = "AdminBillBook.findByBillByAdminId",
	         query  ="SELECT abl FROM AdminBillBook abl WHERE abl.adminId = :adminId" ),
	@NamedQuery(name ="AdminBillBook.findByAdminId",
	         query ="SELECT abl FROM AdminBillBook abl WHERE abl.adminId = :adminId")
})

public class AdminBillBook implements Serializable{

	private static final long serialVersionUID = 1385794955661915701L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private String adminId;
	
	@Column(name ="USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "DEBIT", nullable = false)
	private float debit;
	
	@Column(name = "CREDIT", nullable = false)
	private float credit;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_DEBITED_ON", nullable = false)
	private Date lastDebitedOn;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_CREDITED_ON", nullable = false)
	private Date lastCreditedOn;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "UPDATED_ON", nullable = false)
	private Date updatedOn;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;
	
	
	
	
	
	public AdminBillBook(String adminId, String shopId) {
		this.adminId = adminId;
		this.shopId = shopId;
	}
	
	public AdminBillBook() {
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
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
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
	 * @return the debit
	 */
	public float getDebit() {
		return debit;
	}

	/**
	 * @param debit the debit to set
	 */
	public void setDebit(float debit) {
		this.debit = debit;
	}

	/**
	 * @return the credit
	 */
	public float getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(float credit) {
		this.credit = credit;
	}

	/**
	 * @return the lastDebitedOn
	 */
	public Date getLastDebitedOn() {
		return lastDebitedOn;
	}

	/**
	 * @param lastDebitedOn the lastDebitedOn to set
	 */
	public void setLastDebitedOn(Date lastDebitedOn) {
		this.lastDebitedOn = lastDebitedOn;
	}

	/**
	 * @return the lastCreditedOn
	 */
	public Date getLastCreditedOn() {
		return lastCreditedOn;
	}

	/**
	 * @param lastCreditedOn the lastCreditedOn to set
	 */
	public void setLastCreditedOn(Date lastCreditedOn) {
		this.lastCreditedOn = lastCreditedOn;
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
	
}

