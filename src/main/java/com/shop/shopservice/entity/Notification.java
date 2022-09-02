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
	@Table(name ="NOTIFICATION")
	@NamedQueries({
		@NamedQuery(name = "Notification.getAll",
				 query = "SELECT no FROM Notification no"),
		@NamedQuery(name ="Notification.findByShopId",
		          query ="SELECT no FROM Notification no WHERE no.shopId = :shopId"),
		@NamedQuery(name = "Notification.findByArticleIdAndNotificationFor",
					query ="SELECT no FROM Notification no WHERE no.articleId = :articleId AND no.notificationFor =:notificationFor"),
		@NamedQuery(name = "Notification.findByAdminIdAndNotificationFor",
					query ="SELECT no FROM Notification no WHERE no.adminId = :adminId AND no.notificationFor =:notificationFor"),
		@NamedQuery(name = "Notification.findByUserIdAndNotificationFor",
					query ="SELECT no FROM Notification no WHERE no.userId = :userId AND no.notificationFor =:notificationFor"),
		@NamedQuery(name ="Notification.findByNotificationType",
		          query ="SELECT no FROM Notification no WHERE no.notificationType = :notificationType and no.notificationFor = :notificationFor")
		})
	public class Notification implements Serializable{
		
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int notificationId;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name = "ARTICLE_ID", nullable = false)
	private int articleId;
	
	@Column(name = "SUMMERY_DETAILS", nullable = false)
	private String summeryDetails;
	
	@Column(name = "NOTIFICATION_TYPE", nullable = false)
	private int notificationType;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "USER_TYPE", nullable = false)
	private int userType;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "SHOP_ID")
	private String shopId;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "NOTIFICATION_FOR", nullable = false)
	private int notificationFor;
	

	/**
	 * @return the notificationId
	 */
	public int getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
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
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the summeryDetails
	 */
	public String getSummeryDetails() {
		return summeryDetails;
	}

	/**
	 * @param summeryDetails the summeryDetails to set
	 */
	public void setSummeryDetails(String summeryDetails) {
		this.summeryDetails = summeryDetails;
	}

	/**
	 * @return the notificationType
	 */
	public int getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the notificationFor
	 */
	public int getNotificationFor() {
		return notificationFor;
	}

	/**
	 * @param notificationFor the notificationFor to set
	 */
	public void setNotificationFor(int notificationFor) {
		this.notificationFor = notificationFor;
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


	}
