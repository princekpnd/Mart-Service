package com.shop.shopservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
@Table(name = "PRODUCT_LIST")
@NamedQueries({ @NamedQuery(name = "ProductList.findAll", query = "SELECT pdl FROM ProductList pdl"),
		@NamedQuery(name = "ProductList.findProductListByUserId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.userId =:userId"),
		@NamedQuery(name = "ProductList.findProductListByShopId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.shopId =:shopId"),
		@NamedQuery(name = "ProductList.findByShopId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.productId =:shopId"),
		@NamedQuery(name = "ProductList.findProductListByProductId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.productId =:productId and pdl.cartId =:cartId"),
		@NamedQuery(name = "ProductList.findProductListByCartId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.cartId = :cartId and isActive is TRUE"),
		@NamedQuery(name = "ProductList.findByShopIdCartId", query = "SELECT pdl FROM ProductList pdl WHERE pdl.shopId =:shopId and pdl.cartId = :cartId"),
		@NamedQuery(name ="ProductList.findReturn", query = "SELECT pdl FROM ProductList pdl WHERE pdl.productId =:productId and pdl.cartId =:cartId and pdl.returnQuantity =:returnQuantity")

})
public class ProductList implements Serializable {
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;

	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;

	@Column(name = "CART_ID", nullable = false)
	private int cartId;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "PRODUCT_QUANTITY", nullable = false)
	private int productQuantity;

	@Column(name = "PRICE", nullable = false)
	private float price;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "OFFERS_AVAILABLE", nullable = false)
	private boolean offersAvailable;

	@Column(name = "OLD_PRICE", nullable = false)
	private float oldPrice;

	@Column(name = "DISCOUNT", nullable = false)
	private float discount;

	@Column(name = "DELIVERY_CHARGE", nullable = false)
	private float deliveryCharge;

	@Column(name = "OFFER", nullable = false)
	private int offer;

	@Column(name = "OFFER_TO", nullable = false)
	private LocalDate offerTo;

	@Column(name = "OFFER_FROM", nullable = false)
	private LocalDate offerFrom;

	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;

	@Column(name = "MEASUREMENT", nullable = false)
	private int measurement;

	@Column(name = "DATE_OF_MANUFACTURING", nullable = false)
	private LocalDate dateOfManufacturing;

	@Column(name = "DATE_OF_EXPIRE", nullable = false)
	private LocalDate dateOfExpire;

	@Column(name = "GST_PERCENT", nullable = false)
	private int gstPercent;

	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private float totalAmount;
	
	@Column(name = "BUNDLE_DISCOUNT", nullable = false)
	private float bundleDiscount;
	
	@Column(name = "ITEM_ID", nullable = false)
	private int itemId;

	@Column(name = "STOCK", nullable = false)
	private int stock;
	
	@Column(name = "PACK_SIZE", nullable = false)
	private float packSize;
	
	@Column(name = "MRP", nullable = false)
	private float mrp;
	
	@Column(name = "PRODUCT_IMAGE", nullable = false)
	private String productImage;
	
	@Column(name = "BARCODE", nullable = false)
	private String barcode;
	
	@Column(name = "HSN_CODE", nullable = false)
	private String hsnCode;
	
	@Column(name = "CUSTOMER_BUNDLE_OFFER", nullable = false)
	private float customerBundleOffer;
	
	@Column(name = "CUSTOMER_SINGLE_OFFER", nullable = false)
	private float customerSingleOffer;
	
	@Column(name = "BUNDLE_QUANTITY", nullable = false)
	private float bundleQuantity;
	
	@Column(name = "BUNDLE_PRICE", nullable = false)
	private float bundlePrice;
	
	@Column(name = "UNIT_SELLING_PRICE", nullable = false)
	private float unitSellingPrice;
	
	@Column(name = "RETURN_STATUS", nullable = false)
	private boolean returnStatus;
	
	@Column(name = "RETURN_QUANTITY", nullable = false)
	private float returnQuantity;

	@Transient 
	private int currentStock;

	public ProductList() {
		super();
	}

	public ProductList(int cartId, String shopId) {
		this.cartId = cartId;
		this.shopId = shopId;
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
	 * @return the productQuantity
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
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
	 * @return the offersAvailable
	 */
	public boolean isOffersAvailable() {
		return offersAvailable;
	}

	/**
	 * @param offersAvailable the offersAvailable to set
	 */
	public void setOffersAvailable(boolean offersAvailable) {
		this.offersAvailable = offersAvailable;
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
	 * @return the deliveryCharge
	 */
	public float getDeliveryCharge() {
		return deliveryCharge;
	}

	/**
	 * @param deliveryCharge the deliveryCharge to set
	 */
	public void setDeliveryCharge(float deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	/**
	 * @return the offer
	 */
	public int getOffer() {
		return offer;
	}

	/**
	 * @param offer the offer to set
	 */
	public void setOffer(int offer) {
		this.offer = offer;
	}

	/**
	 * @return the offerTo
	 */
	public LocalDate getOfferTo() {
		return offerTo;
	}

	/**
	 * @param offerTo the offerTo to set
	 */
	public void setOfferTo(LocalDate offerTo) {
		this.offerTo = offerTo;
	}

	/**
	 * @return the offerFrom
	 */
	public LocalDate getOfferFrom() {
		return offerFrom;
	}

	/**
	 * @param offerFrom the offerFrom to set
	 */
	public void setOfferFrom(LocalDate offerFrom) {
		this.offerFrom = offerFrom;
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
	 * @return the dateOfManufacturing
	 */
	public LocalDate getDateOfManufacturing() {
		return dateOfManufacturing;
	}

	/**
	 * @param dateOfManufacturing the dateOfManufacturing to set
	 */
	public void setDateOfManufacturing(LocalDate dateOfManufacturing) {
		this.dateOfManufacturing = dateOfManufacturing;
	}

	/**
	 * @return the dateOfExpire
	 */
	public LocalDate getDateOfExpire() {
		return dateOfExpire;
	}

	/**
	 * @param dateOfExpire the dateOfExpire to set
	 */
	public void setDateOfExpire(LocalDate dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
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
	 * @return the totalAmount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
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

	public float getBundleDiscount() {
		return bundleDiscount;
	}

	public void setBundleDiscount(float bundleDiscount) {
		this.bundleDiscount = bundleDiscount;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getPackSize() {
		return packSize;
	}

	public void setPackSize(float packSize) {
		this.packSize = packSize;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public float getCustomerBundleOffer() {
		return customerBundleOffer;
	}

	public void setCustomerBundleOffer(float customerBundleOffer) {
		this.customerBundleOffer = customerBundleOffer;
	}

	public float getCustomerSingleOffer() {
		return customerSingleOffer;
	}

	public void setCustomerSingleOffer(float customerSingleOffer) {
		this.customerSingleOffer = customerSingleOffer;
	}

	public float getBundleQuantity() {
		return bundleQuantity;
	}

	public void setBundleQuantity(float bundleQuantity) {
		this.bundleQuantity = bundleQuantity;
	}

	public float getBundlePrice() {
		return bundlePrice;
	}

	public void setBundlePrice(float bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	public float getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(float unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public boolean isReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(boolean returnStatus) {
		this.returnStatus = returnStatus;
	}

	public float getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(float returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
}
