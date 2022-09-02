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
@Table(name = "SUB_CATEGORY")
@NamedQueries({ @NamedQuery(name = "SubCategory.getAll",
                           query = "SELECT sub FROM SubCategory sub"),
	            @NamedQuery(name ="SubCategory.getbyId",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.id =:id"),
	            @NamedQuery(name ="SubCategory.CheackExit", 
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.categoryId =:categoryId and sub.name = :name"),
	            @NamedQuery(name ="SubCategory.findAllSubCategory",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.categoryId =:categoryId and sub.isOnline is TRUE"),
	            @NamedQuery(name ="SubCategory.findByShopId",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.shopId =:shopId and sub.isOnline is TRUE"),
	            @NamedQuery(name= "SubCategory.getForExcel",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.categoryId =:categoryId and sub.name = :name"),
	            @NamedQuery(name ="SubCategory.getdeActiveById",
	            	      query = "SELECT sub FROM SubCategory sub WHERE sub.id =:id"),
	            @NamedQuery(name ="SubCategory.getAllOnlineSubcategory",
	            		   query = "SELECT sub FROM SubCategory sub WHERE sub.categoryId =:categoryId and sub.isOnline = :isOnline" ),
	            @NamedQuery(name ="SubCategory.getAllOnlineSubcategoryByshopId",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.shopId =:shopId and sub.isOnline = :isOnline" ),
	            @NamedQuery(name="SubCategory.getAllSubcategoryByshopId",
	            		  query = "SELECT sub FROM SubCategory sub WHERE sub.shopId =:shopId" ),
	            @NamedQuery(name ="SubCategory, findForDelete",
	            		 query = "SELECT sub FROM SubCategory sub WHERE sub.categoryId =:categoryId")
})
public class SubCategory implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "AVATAR", nullable = false)
	private String avatar;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "CATEGORY_ID", nullable = false)
	private int categoryId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "IS_ONLINE", nullable = false)
	private boolean isOnline;
	
	@Column(name = "BRAND_LIST", nullable = false)
	private String brandList;
	
	@Column(name = "BRAND_LIST_COUNT", nullable = false)
	private int brandListCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getBrandList() {
		return brandList;
	}

	public void setBrandList(String brandList) {
		this.brandList = brandList;
	}

	public int getBrandListCount() {
		return brandListCount;
	}

	public void setBrandListCount(int brandListCount) {
		this.brandListCount = brandListCount;
	}

	
	
	
	
}
