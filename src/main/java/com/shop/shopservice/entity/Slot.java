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
@Table(name = "SLOT")
@NamedQueries({ @NamedQuery(name = "Slot.findById",
                           query = "SELECT sl FROM Slot sl WHERE sl.id = :id"),
	   @NamedQuery(name ="Slot.checkExit",
			       query = "SELECT sl FROM Slot sl WHERE sl.billingNumber = :billingNumber and sl.venderId = :venderId"),
	   @NamedQuery(name ="Slot.findByItemListId",
			       query = "SELECT sl FROM Slot sl WHERE sl.itemListId = :itemListId"),
	   @NamedQuery(name ="Slot.findBYShopId",
			        query = "SELECT sl FROM Slot sl WHERE sl.shopId = :shopId"),
	   @NamedQuery(name ="Slot.findBySellerName",
			      query = "SELECT sl FROM Slot sl WHERE sl.nameOfSeller = :nameOfSeller and mobileNo = :mobileNo"),
	   @NamedQuery(name ="Slot.findBySlotNumber",
			      query = "SELECT sl FROM Slot sl WHERE sl.slotNumber = :slotNumber and sl.shopId = :shopId"),
	   @NamedQuery(name ="Slot.addvariantId",
			   query ="SELECT sl FROM Slot sl WHERE sl.id = :id and itemListId = :itemListId"),
	   @NamedQuery(name ="Slot.getAll",
			   query = "SELECT sl FROM Slot sl"),
	   @NamedQuery(name ="Slot.getByVenderId",
			   query ="SELECT sl FROM Slot sl WHERE sl.venderId = :venderId"),
	   @NamedQuery(name ="Slot.getByvariantId",
			   query = "SELECT sl FROM Slot sl WHERE sl.venderId = :venderId"),
	   @NamedQuery(name ="Slot.checkSlotExit", 
			   query = "SELECT sl FROM Slot sl WHERE sl.slotNumber = :slotNumber and sl.shopId = :shopId and sl.venderId = :venderId"),
	   @NamedQuery(name= "Slot.getDeActiveForExcel",
			   query = "SELECT sl FROM Slot sl WHERE sl.id = :id")
	   })
public class Slot implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "SLOT_DATE", nullable = false)
	private Date slotDate;
	
	@Column(name = "SLOT_NUMBER", nullable = false)
	private int slotNumber;
	
	@Column(name = "NAME_OF_SELLER", nullable = false)
	private String nameOfSeller;
	
	@Column(name = "BILLING_AMOUNT", nullable = false)
	private int billingAmount;
	
	@Column(name = "PAID_AMOUNT", nullable = false)
	private int paidAmount;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private int totalAmount;
	
	@Column(name = "DUES", nullable = false)
	private int dues;
	
	@Column(name = "BILLING_NUMBER", nullable = false)
	private String billingNumber;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "ITEM_LIST_ID", nullable = false)
	private int itemListId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "VARIANT_COUNT", nullable = false)
	private int variantCount;
	
	@Column(name = "VARIANT_LIST", nullable = false)
	private String variantList;
	
	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "VENDER_ID", nullable = false)
	private int venderId;
	
	@Column(name = "CREDIT_DAYS", nullable = false)
	private String creditDays;
	
	@Column(name = "NET_AMOUNT", nullable = false)
	private float netAmount;
	
	@Column(name = "GST_TYPE", nullable = false)
	private int gstType;
	
	@Transient
	private List<ItemList> itemList;
	
	@Transient
	private List<VariantStock> variantStockList;
	
	@Transient
	private VariantStock variantStock;
	
	@Transient
	private Vender vender;
	
	
	public Slot(int slotNumber) {
		slotNumber = this.slotNumber;
	}
	
	public Slot() {
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
	 * @return the slotDate
	 */
	public Date getSlotDate() {
		return slotDate;
	}

	/**
	 * @param slotDate the slotDate to set
	 */
	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
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
	 * @return the nameOfSeller
	 */
	public String getNameOfSeller() {
		return nameOfSeller;
	}

	/**
	 * @param nameOfSeller the nameOfSeller to set
	 */
	public void setNameOfSeller(String nameOfSeller) {
		this.nameOfSeller = nameOfSeller;
	}

	/**
	 * @return the billingAmount
	 */
	public int getBillingAmount() {
		return billingAmount;
	}

	/**
	 * @param billingAmount the billingAmount to set
	 */
	public void setBillingAmount(int billingAmount) {
		this.billingAmount = billingAmount;
	}

	/**
	 * @return the paidAmount
	 */
	public int getPaidAmount() {
		return paidAmount;
	}

	/**
	 * @param paidAmount the paidAmount to set
	 */
	public void setPaidAmount(int paidAmount) {
		this.paidAmount = paidAmount;
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
	public int getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the dues
	 */
	public int getDues() {
		return dues;
	}

	/**
	 * @param dues the dues to set
	 */
	public void setDues(int dues) {
		this.dues = dues;
	}

	/**
	 * @return the billingNumber
	 */
	public String getBillingNumber() {
		return billingNumber;
	}

	/**
	 * @param billingNumber the billingNumber to set
	 */
	public void setBillingNumber(String billingNumber) {
		this.billingNumber = billingNumber;
	}

	/**
	 * @return the itemListId
	 */
	public int getItemListId() {
		return itemListId;
	}

	/**
	 * @param itemListId the itemListId to set
	 */
	public void setItemListId(int itemListId) {
		this.itemListId = itemListId;
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
	 * @return the variantCount
	 */
	public int getVariantCount() {
		return variantCount;
	}

	/**
	 * @param variantCount the variantCount to set
	 */
	public void setVariantCount(int variantCount) {
		this.variantCount = variantCount;
	}

	/**
	 * @return the variantList
	 */
	public String getVariantList() {
		return variantList;
	}

	/**
	 * @param variantList the variantList to set
	 */
	public void setVariantList(String variantList) {
		this.variantList = variantList;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	

	/**
	 * @return the venderId
	 */
	public int getVenderId() {
		return venderId;
	}

	/**
	 * @param venderId the venderId to set
	 */
	public void setVenderId(int venderId) {
		this.venderId = venderId;
	}

	/**
	 * @return the variantStockList
	 */
	public List<VariantStock> getVariantStockList() {
		return variantStockList;
	}

	/**
	 * @param variantStockList the variantStockList to set
	 */
	public void setVariantStockList(List<VariantStock> variantStockList) {
		this.variantStockList = variantStockList;
	}

	/**
	 * @return the variantStock
	 */
	public VariantStock getVariantStock() {
		return variantStock;
	}

	/**
	 * @param variantStock the variantStock to set
	 */
	public void setVariantStock(VariantStock variantStock) {
		this.variantStock = variantStock;
	}

	/**
	 * @return the creditDays
	 */
	public String getCreditDays() {
		return creditDays;
	}

	/**
	 * @param creditDays the creditDays to set
	 */
	public void setCreditDays(String creditDays) {
		this.creditDays = creditDays;
	}

	/**
	 * @return the netAmount
	 */
	public float getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the vender
	 */
	public Vender getVender() {
		return vender;
	}

	/**
	 * @param vender the vender to set
	 */
	public void setVender(Vender vender) {
		this.vender = vender;
	}

	/**
	 * @return the gstType
	 */
	public int getGstType() {
		return gstType;
	}

	/**
	 * @param gstType the gstType to set
	 */
	public void setGstType(int gstType) {
		this.gstType = gstType;
	}

	
	
	
}
