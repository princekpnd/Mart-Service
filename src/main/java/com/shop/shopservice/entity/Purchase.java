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
@Table(name ="PURCHASE")
@NamedQueries({ 
	@NamedQuery(name = "Purchase.findAll",
				query = "SELECT pu FROM Purchase pu"),
	@NamedQuery(name ="Purchase.findPurchasePlan",
	            query ="SELECT pu FROM Purchase  pu WHERE pu.planType = :planType"),
	@NamedQuery(name ="Purchase.findByShopId",
	            query ="SELECT pu FROM Purchase pu WHERE pu.shopId = :shopId"),
	@NamedQuery(name ="Purchase.purchaseExits",
	            query = "SELECT pu FROM Purchase pu WHERE pu.adminId = :adminId and isActive is TRUE"),
	
})
public class Purchase implements Serializable{

	private static final long serialVersionUID = 1385794955661915701L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "PLAN_TYPE", nullable = false)
	private int planType;
	
	@Column(name = "EXPIRY_DATE", nullable = false)
	private Date expiryDate;
	
	@Column(name = "PLAN_VALIDITY", nullable = false)
	private int planValidity;
	
	@Column(name = "TRANSACTION_ID", nullable = false)
	private int transactionId;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date  createdOn;
	
	@Column(name = "PLAN_ID", nullable = false)
	private int planId;
	
	@Column(name = "PLAN_AMOUNT", nullable = false)
	private float planAmount;
	
	@Column(name = "IS_DELETED", nullable = false)
	private  boolean isDeleted;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private  boolean isActive;
	
	@Column(name ="PAYMENT_MODE", nullable = false)
	private int paymentMode;
	
	@Column(name ="IS_DISCOUNT", nullable = false)
	private boolean isDiscount;
	
	@Column(name ="AMOUNT", nullable = false)
	private float amount;
	
	
	@Column(name ="DISCOUNT_PERCENT", nullable = false)
	private int discountPercent;
	
	@Column(name ="DISCOUNT_AMOUNT", nullable = false)
	private float discountAmount;
	
	@Column(name ="DISCOUNT_TYPE", nullable = false)
	private String discountType;
	
	@Column(name ="GST_PERCENT", nullable = false)
	private int gstPercent;
	
	@Column(name ="GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name ="TOTAL_AMOUNT", nullable = false)
	private float totalAmount;
	
	@Column(name ="PAYABLE_AMOUNT", nullable = false)
	private int payableAmount;
	
	public Purchase() {
		super();
	}
	
	public  Purchase(int adminId, int planId, int transactionId) {
		adminId = this.adminId;
		planId = this.planId;
		transactionId = this.transactionId;
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
	 * @return the planType
	 */
	public int getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(int planType) {
		this.planType = planType;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the planValidity
	 */
	public int getPlanValidity() {
		return planValidity;
	}

	/**
	 * @param planValidity the planValidity to set
	 */
	public void setPlanValidity(int planValidity) {
		this.planValidity = planValidity;
	}

	/**
	 * @return the transactionId
	 */
	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
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
	 * @return the planId
	 */
	public int getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(int planId) {
		this.planId = planId;
	}

	/**
	 * @return the planAmount
	 */
	public float getPlanAmount() {
		return planAmount;
	}

	/**
	 * @param planAmount the planAmount to set
	 */
	public void setPlanAmount(float planAmount) {
		this.planAmount = planAmount;
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
	 * @return the paymentMode
	 */
	public int getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the isDiscount
	 */
	public boolean isDiscount() {
		return isDiscount;
	}

	/**
	 * @param isDiscount the isDiscount to set
	 */
	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the discountPercent
	 */
	public int getDiscountPercent() {
		return discountPercent;
	}

	/**
	 * @param discountPercent the discountPercent to set
	 */
	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	/**
	 * @return the discountAmount
	 */
	public float getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the discountType
	 */
	public String getDiscountType() {
		return discountType;
	}

	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	/**
	 * @return the gstPercent
	 */
	public int getGstPercent() {
		return gstPercent;
	}

	/**
	 * @param gstPercent the gstPercent to set
	 */
	public void setGstPercent(int gstPercent) {
		this.gstPercent = gstPercent;
	}

	/**
	 * @return the gstAmount
	 */
	public float getGstAmount() {
		return gstAmount;
	}

	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(float gstAmount) {
		this.gstAmount = gstAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the paybableAmount
	 */
	public int getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @param paybableAmount the paybableAmount to set
	 */
	public void setPayableAmount(int payableAmount) {
		this.payableAmount = payableAmount;
	}


}
