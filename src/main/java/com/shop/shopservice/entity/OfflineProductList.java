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
@Table(name ="OFFLINE_PRODUCT")

@NamedQueries({
	@NamedQuery(name = "OfflineProductList.getAll",
               query = "SELECT ofp FROM OfflineProductList ofp"),
	@NamedQuery(name = "OfflineProductList.existProductList",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.productName =:productName and ofp.shopId =:shopId and ofp.brandName =:brandName"),
	@NamedQuery(name = "OfflineProductList.existProduct",
               query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.productName =:productName and ofp.offlineCartId =:offlineCartId and ofp.brandName =:brandName"),
	@NamedQuery(name ="OfflineProductList.findByShopId",
	           query ="SELECT ofp FROM OfflineProductList ofp WHERE ofp.shopId = :shopId"),
	@NamedQuery(name ="OfflineProductList.findByCartId",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.offlineCartId = :offlineCartId and isActive is TRUE"),
	@NamedQuery(name = "OfflineProductList.checkexistProductList",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.offlineCartId = :offlineCartId and ofp.productId = :productId and ofp.cashierId =:cashierId and ofp.stockActiveInd =:stockActiveInd and isActive is TRUE"),
	@NamedQuery(name ="OfflineProductList.getById",
			   query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.id =:id"),
	@NamedQuery(name ="OfflineProductList.findByProductId",
			   query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.productId =:productId"),
	@NamedQuery(name ="OfflineProductList.findByCartIdAndProductId",
			    query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.offlineCartId = :offlineCartId and ofp.productId = :productId and ofp.stockActiveInd = :stockActiveInd and isActive is TRUE"),
	@NamedQuery(name ="OfflineProductList.getByCashierId",
			    query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.cashierId = :cashierId and ofp.offlineCartId = :offlineCartId")
})  
public class OfflineProductList implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	
	@Column(name = "BARCODE", nullable = false)
	private String barcode;
	
	

	@Column(name = "BRAND_NAME", nullable = false)
	private String brandName;
	
	@Column(name = "QUANTITY", nullable = false)
	private float quantity;
	
	@Column(name = "PRICE", nullable = false)
	private float price;
	
	@Column(name = "OLD_PRICE", nullable = false)
	private float oldPrice;
	
	@Column(name = "TOTAL_PRICE", nullable = false)
	private float totalPrice;
	
	@Column(name = "DISCOUNT", nullable = false)
	private float discount;
	
	@Column(name = "OFFER_PERCENT", nullable = false)
	private int offerPercent;
	
	@Column(name = "GST_PERCENT", nullable = false)
	private int gstPercent;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "OFFLINE_CART_ID", nullable = false)
	private int offlineCartId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date  createdOn;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String  shopName;
	
	@Column(name = "MEASUREMENT", nullable = false)
	private String measurement;
	
	@Column(name = "BATCH_NUMBER", nullable = false)
	private String batchNumber;
	
	@Column(name = "DATE_OF_EXPIRE", nullable = false)
	private Date dateOfExpire;
	
	@Column(name = "STOCK_ACTIVE_IND", nullable = false)
	private  boolean  stockActiveInd;
	
	@Column(name = "STOCK", nullable = false)
	private  int  stock;
	
	@Column(name = "UNIT_QUANTITY", nullable = false)
	private  float  unitQuantity;
	
	@Column(name = "SLOT_NUMBER", nullable = false)
	private  int  slotNumber;
	
	@Column(name = "CURRENT_SLOT_STOCK", nullable = false)
	private  int  currentSlotStock;
	
	@Column(name = "SOLD_SLOT_STOCK", nullable = false)
	private  int  soldSlotStock;
	
	@Column(name = "PAYMENT_TYPE", nullable = false)
	private  int  paymentType;
	
	@Column(name = "CGST_AMOUNT", nullable = false)
	private  float  cgstAmount;
	
	@Column(name = "SGST_AMOUNT", nullable = false)
	private  float  sgstAmount;
	
	@Column(name = "IGST_AMOUNT", nullable = false)
	private  float  igstAmount;
	
	@Column(name = "UTGST_AMOUNT", nullable = false)
	private  float  utgstAmount;
	
	@Column(name = "GST_TYPE", nullable = false)
	private  int  gstType;
	
	
	@Column(name="MARGIN_PERCENT", nullable = false)
	private float marginPercent;
	
	@Column(name="BUNDLE_MARGIN", nullable = false)
	private float bundleMargin;
	
	@Column(name="MRP", nullable = false)
	private float mrp;
	
	@Column(name="UNIT_SELLING_PRICE", nullable = false)
	private float unitSellingPrice;
	
	@Column(name="PURCHASE_PRICE", nullable = false)
	private float purchasePrice;
	
	@Column(name="CUSTOMER_SINGLE_OFFER", nullable = false)
	private float customerSingleOffer;
	
	@Column(name="CUSTOMER_BUNDLE_OFFER", nullable = false)
	private float customerBundleOffer;
	
	@Column(name="BUNDLE_QUANTITY", nullable = false)
	private float bundleQuantity;
	
	@Column(name="BUNDLE_PRICE", nullable = false)
	private float bundlePrice;
	
	@Column(name="CASHIER_ID", nullable = false)
	private String cashierId;
	

	
	public OfflineProductList(String shopId, String productName, String brandName) {
		this.shopId = shopId;
		this.productName = productName;
		this.brandName= brandName;
	}
	
	public OfflineProductList() {
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the oldPrice
	 */
	public float getOldPrice() {
		return oldPrice;
	}

	/**
	 * @param oldPrice the oldPrice to set
	 */
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}

	/**
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	/**
	 * @return the offerPercent
	 */
	public int getOfferPercent() {
		return offerPercent;
	}

	/**
	 * @param offerPercent the offerPercent to set
	 */
	public void setOfferPercent(int offerPercent) {
		this.offerPercent = offerPercent;
	}

	/**
	 * @return the gstPercent
	 */
	public int getGstPercent() {
		return gstPercent;
	}

	/**
	 * @param gstPercent the gstPercent to set
	 */
	public void setGstPercent(int gstPercent) {
		this.gstPercent = gstPercent;
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
	 * @return the offlineCartId
	 */
	public int getOfflineCartId() {
		return offlineCartId;
	}

	/**
	 * @param offlineCartId the offlineCartId to set
	 */
	public void setOfflineCartId(int offlineCartId) {
		this.offlineCartId = offlineCartId;
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
	 * @return the measurement
	 */
	public String getMeasurement() {
		return measurement;
	}

	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the dateOfExpire
	 */
	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	/**
	 * @param dateOfExpire the dateOfExpire to set
	 */
	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

	/**
	 * @return the stockActiveInd
	 */
	public boolean isStockActiveInd() {
		return stockActiveInd;
	}

	/**
	 * @param stockActiveInd the stockActiveInd to set
	 */
	public void setStockActiveInd(boolean stockActiveInd) {
		this.stockActiveInd = stockActiveInd;
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

	

	public float getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(float unitQuantity) {
		this.unitQuantity = unitQuantity;
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
	 * @return the currentSlotStock
	 */
	public int getCurrentSlotStock() {
		return currentSlotStock;
	}

	/**
	 * @param currentSlotStock the currentSlotStock to set
	 */
	public void setCurrentSlotStock(int currentSlotStock) {
		this.currentSlotStock = currentSlotStock;
	}

	/**
	 * @return the soldSlotStock
	 */
	public int getSoldSlotStock() {
		return soldSlotStock;
	}

	/**
	 * @param soldSlotStock the soldSlotStock to set
	 */
	public void setSoldSlotStock(int soldSlotStock) {
		this.soldSlotStock = soldSlotStock;
	}

	/**
	 * @return the paymentType
	 */
	public int getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the cgstAmount
	 */
	public float getCgstAmount() {
		return cgstAmount;
	}

	/**
	 * @param cgstAmount the cgstAmount to set
	 */
	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	/**
	 * @return the sgstAmount
	 */
	public float getSgstAmount() {
		return sgstAmount;
	}

	/**
	 * @param sgstAmount the sgstAmount to set
	 */
	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	/**
	 * @return the igstAmount
	 */
	public float getIgstAmount() {
		return igstAmount;
	}

	/**
	 * @param igstAmount the igstAmount to set
	 */
	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}

	/**
	 * @return the utgstAmount
	 */
	public float getUtgstAmount() {
		return utgstAmount;
	}

	/**
	 * @param utgstAmount the utgstAmount to set
	 */
	public void setUtgstAmount(float utgstAmount) {
		this.utgstAmount = utgstAmount;
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

	/**
	 * @return the marginPercent
	 */
	public float getMarginPercent() {
		return marginPercent;
	}

	/**
	 * @param marginPercent the marginPercent to set
	 */
	public void setMarginPercent(float marginPercent) {
		this.marginPercent = marginPercent;
	}

	/**
	 * @return the bundleMargin
	 */
	public float getBundleMargin() {
		return bundleMargin;
	}

	/**
	 * @param bundleMargin the bundleMargin to set
	 */
	public void setBundleMargin(float bundleMargin) {
		this.bundleMargin = bundleMargin;
	}

	/**
	 * @return the mrp
	 */
	public float getMrp() {
		return mrp;
	}

	/**
	 * @param mrp the mrp to set
	 */
	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the unitSellingPrice
	 */
	public float getUnitSellingPrice() {
		return unitSellingPrice;
	}

	/**
	 * @param unitSellingPrice the unitSellingPrice to set
	 */
	public void setUnitSellingPrice(float unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	/**
	 * @return the purchasePrice
	 */
	public float getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @param purchasePrice the purchasePrice to set
	 */
	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * @return the customerSingleOffer
	 */
	public float getCustomerSingleOffer() {
		return customerSingleOffer;
	}

	/**
	 * @param customerSingleOffer the customerSingleOffer to set
	 */
	public void setCustomerSingleOffer(float customerSingleOffer) {
		this.customerSingleOffer = customerSingleOffer;
	}

	/**
	 * @return the customerBundleOffer
	 */
	public float getCustomerBundleOffer() {
		return customerBundleOffer;
	}

	/**
	 * @param customerBundleOffer the customerBundleOffer to set
	 */
	public void setCustomerBundleOffer(float customerBundleOffer) {
		this.customerBundleOffer = customerBundleOffer;
	}

	/**
	 * @return the bundleQuantity
	 */
	public float getBundleQuantity() {
		return bundleQuantity;
	}

	/**
	 * @param bundleQuantity the bundleQuantity to set
	 */
	public void setBundleQuantity(float bundleQuantity) {
		this.bundleQuantity = bundleQuantity;
	}

	/**
	 * @return the bundlePrice
	 */
	public float getBundlePrice() {
		return bundlePrice;
	}

	/**
	 * @param bundlePrice the bundlePrice to set
	 */
	public void setBundlePrice(float bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	public String getCashierId() {
		return cashierId;
	}

	public void setCashierId(String cashierId) {
		this.cashierId = cashierId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	}
