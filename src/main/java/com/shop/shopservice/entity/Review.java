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
@Table(name = "REVIEW")
	@NamedQueries({ 
		@NamedQuery(name = "Review.findAll",
					query = "SELECT re FROM Review re"),
		@NamedQuery(name="Review.findReviewByShopId",
                    query="SELECT re FROM Review re WHERE re.shopId = :shopId and isActive is TRUE and isDeleted is FALSE "),
		@NamedQuery(name="Review.findReviewByProductId",
                    query="SELECT re FROM Review re WHERE re.productId = :productId"),
		@NamedQuery(name="Review.findByProductId",
                    query="SELECT re FROM Review re WHERE re.productId = :productId"),
		@NamedQuery(name = "Review.findReviewUserByShopId",
		            query = "SELECT re FROM Review re WHERE re.shopId = :shopId  and isActive is TRUE and isDeleted is FALSE"),
               
})

public class Review implements Serializable {

	private static final long serialVersionUID = 1385794955661915701L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;

	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "COMMENT", nullable = false)
	private String comment;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "CART_ID", nullable = false)
	private int cartId;
	
	@Column(name = "REVIEW_TYPE", nullable = false)
	private int reviewType;
	
	
	public Review() {
		super();
	}
	
	public Review(String shopId, String productId) {
		this.shopId= shopId;
		this.productId= productId;
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the reviewType
	 */
	public int getReviewType() {
		return reviewType;
	}

	/**
	 * @param reviewType the reviewType to set
	 */
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
	}

	
}
