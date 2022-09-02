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
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "BRAND")
@NamedQueries({ 
	    @NamedQuery(name = "Brand.findAll",
	    		   query = "SELECT br FROM Brand br"),
		@NamedQuery(name = "Brand.findByShopId", 
		           query = "SELECT br FROM Brand br WHERE br.shopId = :shopId and br.isActive is TRUE"),
		@NamedQuery(name = "Brand.findForUserByShopId", 
		           query = "SELECT br FROM Brand br WHERE br.shopId = :shopId and br.isActive is TRUE and br.isDeleted is FALSE"),
		@NamedQuery(name = "Brand.findByNameShopId", 
		           query = "SELECT br FROM Brand br WHERE br.title = :title"),
		@NamedQuery(name = "Brand.findAllForUser", 
		           query = "SELECT br FROM Brand br WHERE br.isActive is TRUE and br.isDeleted is FALSE"),
		@NamedQuery(name = "Brand.findDeactiveBrand", 
		           query = "SELECT br FROM Brand br WHERE br.shopId = :shopId and br.isActive is FALSE"),
		@NamedQuery(name = "Brand.findBrandByShopIdAndId", 
		           query = "SELECT br FROM Brand br WHERE br.shopId =:shopId and br.id = :id"),
		@NamedQuery(name = "Brand.findByCategory", 
		           query = "SELECT br FROM Brand br WHERE br.category =:category and br.isActive is TRUE and br.isOnline is TRUE"),
		@NamedQuery(name = "Brand.findActiveCategory", 
		           query = "SELECT br FROM Brand br WHERE br.category =:category and br.isActive = :isActive"),
		@NamedQuery(name ="Brand.findByTitleAndShopId", 
		           query = "SELECT br FROM Brand br WHERE br.title = :title and br.shopId = :shopId and br.category = :category"),
		@NamedQuery(name= "Brand.deActiveForExcel",
				   query = "SELECT br FROM Brand br WHERE br.id =:id"),
		@NamedQuery(name ="Brand.findBySubCategoryId",
				   query = "SELECT br FROM Brand br WHERE br.subCategoryId = :subCategoryId and br.isOnline is TRUE"),
		@NamedQuery(name ="Brand.getOnlineBrandBySubcategoryId",
				   query = "SELECT br FROM Brand br WHERE br.subCategoryId = :subCategoryId and br.isOnline = :isOnline"),
		@NamedQuery(name ="Brand.getOnlineBrand",
				query = "SELECT br FROM Brand br WHERE br.isOnline = :isOnline"),
		@NamedQuery(name ="Brand.getOnlineBrandForLocal",
				 query = "SELECT br FROM Brand br WHERE br.shopId = :shopId and br.isOnline is TRUE"),
		@NamedQuery(name ="Brand.forDelete",
				query = "SELECT br FROM Brand br WHERE br.subCategoryId =:subCategoryId"),
		@NamedQuery(name ="Brand.forDeleteBySubCategory",
				 query = "SELECT br FROM Brand br WHERE br.subCategoryId = :subCategoryId"),
		@NamedQuery(name ="Brand.findAllBySubCategoryId",
				 query = "SELECT br FROM Brand br WHERE br.subCategoryId = :subCategoryId"),
		@NamedQuery(name ="Brand, getAllToShopId",
				  query = "SELECT br FROM Brand br WHERE br.shopId = :shopId" ),
		@NamedQuery(name ="Brand.forDeleteByCategory",
				  query = "SELECT br FROM Brand br WHERE br.category = :category" )

})

public class Brand implements Serializable {

	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "AVATAR", nullable = false)
	private String avatar;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Field(store = Store.NO)
	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "CATEGORY", nullable = false)
	private int category;
	
	@Column(name = "SUB_CATEGORY_ID", nullable = false)
	private String subCategoryId;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "IS_ONLINE", nullable = false)
	private boolean isOnline;
	
	@Column(name = "SUBCATEGORY_COUNT", nullable = false)
	private int subcategoryCount;

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

	public Brand() {
		super();

	}

	public Brand(String shopId, int category) {
		this.shopId = shopId;
		this.category = category;
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
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public int getSubcategoryCount() {
		return subcategoryCount;
	}

	public void setSubcategoryCount(int subcategoryCount) {
		this.subcategoryCount = subcategoryCount;
	}

}
