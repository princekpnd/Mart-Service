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
@Table(name = "TRANSACTION")
@NamedQueries({ 
	@NamedQuery(name = "Transaction.findAll",
				query = "SELECT tr FROM Transaction tr"),
	@NamedQuery(name = "Transaction.findTransactionByUserId",
				query = "SELECT tr FROM Transaction tr WHERE tr.userId = :userId and tr.transactionStatus = :transactionStatus"),
	@NamedQuery(name = "Transaction.findTransactionByShopId",
	            query = "SELECT tr FROM Transaction tr WHERE tr.shopId = :shopId and tr.transactionStatus = :transactionStatus"),
	@NamedQuery(name = "Transaction.findTransactionByTransactionId",
                query = "SELECT tr FROM Transaction tr WHERE tr.transactionId = :transactionId"),
	@NamedQuery(name="Transaction.findByCartId",
	            query="SELECT tr FROM Transaction tr WHERE tr.cartId= :cartId and isActive is TRUE "),
	@NamedQuery(name ="Transaction.findTransactionForUserByShopId",
	            query ="SELECT tr FROM Transaction tr WHERE tr.shopId =:shopId and isActive is TRUE and isDeleted is FALSE "),
	@NamedQuery(name ="Transaction.findByTransactionCartId",
	            query="SELECT tr FROM Transaction tr WHERE tr.cartId= :cartId" ),
	@NamedQuery(name="Transaction.findByUserIdAndShopId",
    			query="SELECT tr FROM Transaction tr WHERE tr.userId = :userId and tr.shopId = :shopId and tr.transactionStatus = :transactionStatus"),
	@NamedQuery(name="Transaction.findByAdminId",
				query="SELECT tr FROM Transaction tr WHERE tr.adminId = :adminId and tr.transactionType = :transactionType and tr.isActive = :isActive"),
	@NamedQuery(name ="Transaction.findAllAmount",
	            query ="SELECT tr FROM Transaction tr WHERE tr.shopId =:shopId")
	})


public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "PAYMENT_MODE", nullable = false)
	private int paymentMode;
	
	@Column(name = "AMOUNT", nullable = false)
	private float amount;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "TRANSACTION_ID", nullable = false)
	private String transactionId;
	
	@Column(name = "DISCREPTION", nullable = false)
	private String discreption;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private float totalAmount;
	
	@Column(name = "PAID", nullable = false)
	private float paid;
	
	@Column(name = "DUES", nullable = false)
	private float dues;
	
	@Column(name = "CART_ID", nullable = false)
	private int cartId;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "TRANSACTION_STATUS", nullable = false)
	private int transactionStatus;
	
	@Column(name = "ORDER_RCPTID_ID", nullable = false)
	private String orderRcptidId;
	
	@Column(name ="TRANSACTION_TYPE", nullable = false)
	private int transactionType;
	
	@Column(name ="PURCHASE_ID", nullable = false)
	private int purchaseId;
	
	@Column(name ="WITHDRAW_ID", nullable = false)
	private int withdrawId;
	
	
	/**
	 * @return the orderRcptidId
	 */
	public String getOrderRcptidId() {
		return orderRcptidId;
	}


	/**
	 * @param orderRcptidId the orderRcptidId to set
	 */
	public void setOrderRcptidId(String orderRcptidId) {
		this.orderRcptidId = orderRcptidId;
	}


	public Transaction() {
		super();
	}
	
	
	public Transaction(String shopId, int userId) {
		this.shopId = shopId;
		this.userId = userId;
	}
	
	

	/**
	 * @return the totalAmount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}


	/**
	 * @return the discreption
	 */
	public String getDiscreption() {
		return discreption;
	}


	/**
	 * @param discreption the discreption to set
	 */
	public void setDiscreption(String discreption) {
		this.discreption = discreption;
	}


	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
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
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}


	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	/**
	 * @return the transactionStatus
	 */
	public int getTransactionStatus() {
		return transactionStatus;
	}


	/**
	 * @param transactionStatus the transactionStatus to set
	 */
	public void setTransactionStatus(int transactionStatus) {
		this.transactionStatus = transactionStatus;
	}


	/**
	 * @return the transactionType
	 */
	public int getTransactionType() {
		return transactionType;
	}


	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}


	/**
	 * @return the purchaseId
	 */
	public int getPurchaseId() {
		return purchaseId;
	}


	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}


	/**
	 * @return the withdrawId
	 */
	public int getWithdrawId() {
		return withdrawId;
	}


	/**
	 * @param withdrawId the withdrawId to set
	 */
	public void setWithdrawId(int withdrawId) {
		this.withdrawId = withdrawId;
	}

	
}
