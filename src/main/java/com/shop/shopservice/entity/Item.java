package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Indexed;
import org.mapstruct.Named;

@Entity
@Indexed
@Table(name = "ITEM")
@NamedQueries({ 
	    @NamedQuery(name = "Item.getAll", 
	    		   query = "SELECT it FROM Item it"),
		@NamedQuery(name = "Item.getByName", 
		           query = "SELECT it FROM Item it WHERE it.name = :name"),
		@NamedQuery(name = "Item.getAllProductByName", 
		           query = "SELECT it FROM Item it WHERE it.shopId = :shopId and isActive is TRUE and isDeleted is FALSE"),
		@NamedQuery(name = "Item.getById", 
		           query = "SELECT it FROM Item it WHERE it.id = :id"),
		@NamedQuery(name ="Item.exitItem",
		           query = "SELECT it FROM Item it WHERE it.title = :title and it.shopId = :shopId and it.brand =:brand and it.category = :category"),
        @NamedQuery(name ="Item.getByShopId", 
                   query ="SELECT it FROM Item it WHERE it.shopId = :shopId"),
        @NamedQuery(name ="Item.findByName", 
                   query ="SELECT it FROM Item it WHERE it.shopId = :shopId and it.name = :name and isActive is TRUE and isDeleted is FALSE"),
        @NamedQuery(name ="Item,getAllProductByShopId", 
                   query ="SELECT it FROM Item it WHERE it.shopId = :shopId and isActive is TRUE"),
        @NamedQuery(name ="Item,findByBrand",
                   query ="SELECT it FROM Item it WHERE it.brand = :brand"),
        @NamedQuery(name ="Item,findByActiveProduct",
                   query ="SELECT it FROM Item it WHERE it.shopId = :shopId and isActive is TRUE"),
        @NamedQuery(name ="Item.getByTitle",
                   query ="SELECT it FROM Item it WHERE it.title = :title and it.shopId = :shopId"),
        @NamedQuery(name ="Item.findByExcel", 
                   query ="SELECT it FROM Item it WHERE it.brand = :brand and it.category = :category and it.shopId = :shopId and it.title = :title"),
        @NamedQuery(name ="Item.deActiveForExcel",
        		    query = "SELECT it FROM Item it WHERE it.id = :id"),
        @NamedQuery(name  ="Item.getAllProductByCategory",
        		   query = "SELECT it FROM Item it WHERE it.category = :category"),
        @NamedQuery(name ="Item.getAllProductByOnline",
        		   query = "SELECT it FROM Item it WHERE it.isOnline = :isOnline"),
        @NamedQuery(name ="Item.getAllOnlineByCategory",
        		   query = "SELECT it FROM Item it WHERE it.category = :category and it.isOnline = :isOnline"),
        @NamedQuery(name ="Item.getAllOnlineByBrand",
        		   query = "SELECT it FROM Item it WHERE it.brand = :brand and it.isOnline = :isOnline"),
        @NamedQuery(name ="Item.getAllOnlineProductByShopId",
        		query = "SELECT it FROM Item it WHERE it.shopId = :shopId and it.isOnline = :isOnline"),
        @NamedQuery(name ="Item.getAllOnlineProductBySubCategoryId",
                    query  = "SELECT it FROM Item it WHERE it.subCategoryId = :subCategoryId"),
        @NamedQuery(name ="Item.getAllOnlineByHotSale",
        		query  = "SELECT it FROM Item it WHERE it.hotSell = :hotSell" ),
        @NamedQuery(name="Item.getAllOnlineByBaggage",
        		query  = "SELECT it FROM Item it WHERE it.baggage = :baggage" ),
        @NamedQuery(name = "Item.getAllOnlineByMilaanOffer",
        		query  = "SELECT it FROM Item it WHERE it.milaanOffer = :milaanOffer" ),
        @NamedQuery(name ="Item.getAllOnlineByAditionalOffer",
        		query  = "SELECT it FROM Item it WHERE it.aditionalDiscount = :aditionalDiscount" ),
        @NamedQuery(name ="Item.getByShopIdAndId",
        		query = "SELECT it FROM Item it WHERE it.id = :id and it.shopId = :shopId" ),
        @NamedQuery(name ="Item.getBySubCategoryIdAndOnline",
        		query = "SELECT it FROM Item it WHERE it.subCategoryId = :subCategoryId and it.isOnline = :isOnline"),
        @NamedQuery(name ="Item.forDelete",
        		  query = "SELECT it FROM Item it WHERE it.category = :category" )
		})
public class Item implements Serializable {
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "CATEGORY", nullable = false)
	private int category;

	@Column(name = "BRAND", nullable = false)
	private int brand;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "REVIEW", nullable = false)
	private String review;

	@Column(name = "MEASUREMENT", nullable = false)
	private int measurement;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "SUB_CATEGORY_ID", nullable = false)
	private int subCategoryId;
	
	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name = "IS_ONLINE", nullable = false)
	private boolean isOnline;
	
	@Column(name ="HOT_SELL", nullable = false)
	private boolean hotSell;
	
	@Column(name ="BAGGAGE", nullable = false)
	private boolean baggage;
	
	@Column(name ="MILAAN_OFFER", nullable = false)
	private boolean milaanOffer;
	
	@Column(name ="ADITIONAL_DISCOUNT", nullable = false)
	private boolean aditionalDiscount;
	
	@Column(name ="PRODUCT_IMAGE", nullable = false)
	private String productImage;
	
	@Column(name = "SEARCH_KEYWORD", nullable = false)
	private String searchKeyword;
	
		
	@Transient
	private List<ItemList> itemList;
	
	@Transient
	private ItemList variant;
	
	@Transient
	private List<Image> image;

	public Item(String shopId, String name) {
		shopId = this.shopId;
		name = this.name;
	}
	
	public Item() {
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
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the brand
	 */
	public int getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(int brand) {
		this.brand = brand;
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
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * @return the measurement
	 */
	public int getMeasurement() {
		return measurement;
	}

	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(int measurement) {
		this.measurement = measurement;
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
	 * @return the itemList
	 */
	public List<ItemList> getItemList() {
		return itemList;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ItemList> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the image
	 */
	public List<Image> getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(List<Image> image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public ItemList getVariant() {
		return variant;
	}

	public void setVariant(ItemList variant) {
		this.variant = variant;
	}

	public boolean isHotSell() {
		return hotSell;
	}

	public void setHotSell(boolean hotSell) {
		this.hotSell = hotSell;
	}

	public boolean isBaggage() {
		return baggage;
	}

	public void setBaggage(boolean baggage) {
		this.baggage = baggage;
	}

	public boolean isMilaanOffer() {
		return milaanOffer;
	}

	public void setMilaanOffer(boolean milaanOffer) {
		this.milaanOffer = milaanOffer;
	}

	public boolean isAditionalDiscount() {
		return aditionalDiscount;
	}

	public void setAditionalDiscount(boolean aditionalDiscount) {
		this.aditionalDiscount = aditionalDiscount;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

}
