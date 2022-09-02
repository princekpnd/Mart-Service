package com.shop.shopservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mapstruct.Named;

import java.util.Date;
@Table(name = "DISCOUNT")
@Entity
@NamedQueries({
	 @NamedQuery(name ="Discount.getAll",
  		   query = "SELECT dr FROM Discount dr"),
	 @NamedQuery(name="Discount.getById",
			 query = "SELECT dr FROM Discount dr WHERE dr.discountId =:discountId"),
	 @NamedQuery(name ="Discount.getByDiscountType", 
			   query = "SELECT dr FROM Discount dr WHERE dr.offerType =:offerType and dr.isActive is TRUE"),
	 @NamedQuery(name ="Discount.getBylable",
			  query = "SELECT dr FROM Discount dr WHERE dr.lable =:lable")
})
public class Discount implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int discountId;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "IMAGE", nullable = false)
	private String image;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "LABLE", nullable = false)
	private String lable;
	
	@Column(name = "OFFER_TYPE", nullable = false)
	private String offerType;
	
	@Column(name = "COUNT", nullable = false)
	private int Count;
	
	@Column(name = "TOTAL_COUNT", nullable = false)
	private int totalCount;

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	
	
	
	
}
