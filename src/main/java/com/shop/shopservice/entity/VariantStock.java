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

@Entity
@Table(name="variant_stock")
@NamedQueries({ @NamedQuery(name = "VariantStock.findAll",
                           query = "SELECT st FROM VariantStock st"),
	            @NamedQuery(name ="VariantStock.findById",
	            		   query = "SELECT st FROM VariantStock st WHERE st.id = :id"),
	            @NamedQuery(name ="VariantStock.cheackCreated",
	            		   query = "SELECT st FROM VariantStock st WHERE st.slotNumber = :slotNumber and st.variantId = :variantId"),
	            @NamedQuery(name ="VariantStock.findBySlotNumber",
	            		   query = "SELECT st FROM VariantStock st WHERE st.slotNumber = :slotNumber and st.variantId = :variantId"),
	            @NamedQuery(name ="VariantStock.getBySlotNumber",
	            		   query = "SELECT st FROM VariantStock st WHERE st.slotNumber = :slotNumber and isActive is TRUE"),
	            @NamedQuery(name ="VariantStock.getDeActiveForExcel",
	            		   query = "SELECT st FROM VariantStock st WHERE st.variantId = :variantId"),
	            @NamedQuery(name ="variantStock.getByVariantStock",
	            		  query = "SELECT st FROM VariantStock st WHERE st.variantId = :variantId")
})

public class VariantStock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "VARIANT_ID", nullable = false)
	private int variantId;
	
	@Column(name = "SLOT_NUMBER", nullable = false)
	private int slotNumber;
	
	@Column(name = "STOCK", nullable = false)
	private int stock;
	
	@Column(name = "CURRENT_STOCK", nullable = false)
	private int currentStock;
	
	@Column(name = "SOLD_STOCK", nullable = false)
	private int soldStock;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	public VariantStock () {
		super();
	}
	
	@Transient
	private List<ItemList> itemList;
	
	@Transient
	private ItemList variant;


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
	 * @return the variantId
	 */
	public int getVariantId() {
		return variantId;
	}

	/**
	 * @param variantId the variantId to set
	 */
	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}

	/**
	 * @return the slotNumber
	 */
	public int getSlotNumber() {
		return slotNumber;
	}

	/**
	 * @param slotNumber the slotNumber to set
	 */
	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * @return the currentStock
	 */
	public int getCurrentStock() {
		return currentStock;
	}

	/**
	 * @param currentStock the currentStock to set
	 */
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	/**
	 * @return the soldStock
	 */
	public int getSoldStock() {
		return soldStock;
	}

	/**
	 * @param soldStock the soldStock to set
	 */
	public void setSoldStock(int soldStock) {
		this.soldStock = soldStock;
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
	 * @return the itemList
	 */
	public List<ItemList> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ItemList> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return the variant
	 */
	public ItemList getVariant() {
		return variant;
	}

	/**
	 * @param variant the variant to set
	 */
	public void setVariant(ItemList variant) {
		this.variant = variant;
	}
	
	
	
	
}
