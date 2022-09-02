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
@Table(name = "SHOP_IMAGE")
@NamedQueries({ @NamedQuery(name = "ShopImage.findAllImage", query = "SELECT sim FROM ShopImage sim"),
		@NamedQuery(name = "ShopImage.findImageById", query = "SELECT sim FROM ShopImage sim WHERE sim.id = :id"),
		@NamedQuery(name = "ShopImage.findByShopId", query = "SELECT sim FROM ShopImage sim WHERE sim.shopId = :shopId and isActive is TRUE"),
		@NamedQuery(name = "ShopImage.findByShopIdAndProductId", query = "SELECT sim FROM ShopImage sim WHERE sim.shopId = :shopId and sim.productId = :productId"),
		@NamedQuery(name = "ShopImage.findImageByShopId", query = "SELECT sim FROM ShopImage sim WHERE sim.shopId = :shopId"), })

public class ShopImage implements Serializable {
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "PRODUCT_ID", nullable = false)
	private int productId;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "AVATAR_NAME", nullable = false)
	private String avatarName;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	public ShopImage(String shopId, int productId) {
		this.shopId = shopId;
		this.productId = productId;
	}

	public ShopImage() {
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
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
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
	 * @return the avatarName
	 */
	public String getAvatarName() {
		return avatarName;
	}

	/**
	 * @param avatarName the avatarName to set
	 */
	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
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

}
