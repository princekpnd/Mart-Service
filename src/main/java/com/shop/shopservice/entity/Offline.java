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
@Table(name ="OFFLINE")
@NamedQueries({
	@NamedQuery(name = "Offline.getAll",
               query = "SELECT ofl FROM Offline ofl"),
	@NamedQuery(name = "Offline.findByBillId",
	           query= "SELECT ofl FROM Offline ofl WHERE ofl.billingId= :billingId"),
	@NamedQuery(name = "Offline.getByShopId",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.cashierId = :cashierId and isActive is TRUE"),
	@NamedQuery(name ="Offline.getByUserName",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.userName = :userName and ofl.shopId = :shopId"),
	@NamedQuery(name ="Offline.offlineExist",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.productName = :productName"),
	@NamedQuery(name ="Offline.findByShopIdAndId",
               query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.billingId = :billingId"),
	@NamedQuery(name= "Offline.checkExist",
                query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.isActive = :isActive and ofl.cashierId = :cashierId"),
	@NamedQuery(name = "Offline.findByShopIdAndMobileNo",
	            query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.mobileNo = :mobileNo"),
	@NamedQuery(name = "Offline.cheackDeactive",
                query = "SELECT ofl FROM Offline ofl WHERE ofl.billingId = :billingId"),
	@NamedQuery(name = "offline.findAllByShopId",
	            query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId"),
	@NamedQuery(name= "Offline.getDeactiveByShopId",
                query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.isActive is FALSE"),
	@NamedQuery(name ="Offline.getOfflineByShopId",
			    query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId"),
	@NamedQuery(name ="Offline.getAllAmount",
			    query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.cashierId = :cashierId"),
	@NamedQuery(name ="Offline.cheakCreated",
			   query = "SELECT ofl FROM Offline ofl WHERE ofl.billingId = :billingId"),
	@NamedQuery(name ="Offline.getActiveBillByCashierId",
			   query = "SELECT ofl FROM Offline ofl WHERE ofl.cashierId = :cashierId and isActive is TRUE")
	})
public class Offline implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int billingId;


	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name ="MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	
	@Column(name = "STOCK", nullable = false)
	private int stock;
	
	@Column(name = "PRODUCT_QUANTITY", nullable = false)
	private int productQuantity;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name ="IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "SELLING_PRICE", nullable = false)
	private float sellingPrice;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name ="CASHIER_ID", nullable = false)
	private String cashierId;
	
	@Column(name ="BARCODE", nullable = false)
	private String barcode;
	
	@Column(name = "BILLING_DATE", nullable = false)
	private  Date billingDate;
	
//	@Column(name = "BILLING_ID", nullable = false)
//	private  int billingId;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private  int productId;
	
	@Column(name = "OFFER_ACTIVE_END", nullable = false)
	private  boolean  offerActiveEnd;
	
	@Column(name = "OLD_PRICE", nullable = false)
	private  float oldPrice;
	
	@Column(name = "TOTAL_PRICE", nullable = false)
	private  float totalPrice;
	
	@Column(name = "DISCOUNT", nullable = false)
	private  float discount;
	
	@Column(name = "OFFER_PERCENT", nullable = false)
	private  int offerPercent;
	
	@Column(name = "GST_PERCENT", nullable = false)
	private  int gstPercent;
	
	@Column(name = "BRAND_NAME", nullable = false)
	private  String brandName;
	
	@Column(name="PAYABLE_AMOUNT", nullable = false)
	private int payableAmount;
	
	@Column(name="PAYMENT_TYPE", nullable = false)
	private String paymentType;
	
	
	
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
	
	@Column(name="EXTRA_BILL_DISCOUNT", nullable = false)
	private float extraBillDiscount;
	
	@Column(name="DESCRIPTION", nullable = false)
	private String description;
	
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
	
	@Column(name = "IS_PERCENT_DISCOUNT", nullable = false)
	private  boolean  isPercentDiscount;
	
	
	
	@Transient
	private List<OfflineProductList> offlineProductList;
	
	public Offline() {
		super();
	}
	
//	public Offline(String shopId, String productName) {
//		this.shopId = shopId;
//		this.productName = productName;
//	}
	
	public Offline(String shopId,  String mobileNo) {
		this.shopId = shopId;
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the billingId
	 */
	public int getBillingId() {
		return billingId;
	}

	/**
	 * @param billingId the billingId to set
	 */
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the sellingPrice
	 */
	public float getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the adminId
	 */
	public int getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(int adminId) {
		this.adminId = adminId;
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
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the billingDate
	 */
	public Date getBillingDate() {
		return billingDate;
	}

	/**
	 * @param billingDate the billingDate to set
	 */
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
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
	 * @return the offerActiveEnd
	 */
	public boolean isOfferActiveEnd() {
		return offerActiveEnd;
	}

	/**
	 * @param offerActiveEnd the offerActiveEnd to set
	 */
	public void setOfferActiveEnd(boolean offerActiveEnd) {
		this.offerActiveEnd = offerActiveEnd;
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
	 * @return the offlineProductList
	 */
	public List<OfflineProductList> getOfflineProductList() {
		return offlineProductList;
	}

	/**
	 * @param offlineProductList the offlineProductList to set
	 */
	public void setOfflineProductList(List<OfflineProductList> offlineProductList) {
		this.offlineProductList = offlineProductList;
	}


	/**
	 * @return the payableAmount
	 */
	public int getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @param payableAmount the payableAmount to set
	 */
	public void setPayableAmount(int payableAmount) {
		this.payableAmount = payableAmount;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public float getExtraBillDiscount() {
		return extraBillDiscount;
	}

	public void setExtraBillDiscount(float extraBillDiscount) {
		this.extraBillDiscount = extraBillDiscount;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(float cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public float getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(float sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public float getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(float igstAmount) {
		this.igstAmount = igstAmount;
	}

	public float getUtgstAmount() {
		return utgstAmount;
	}

	public void setUtgstAmount(float utgstAmount) {
		this.utgstAmount = utgstAmount;
	}

	public int getGstType() {
		return gstType;
	}

	public void setGstType(int gstType) {
		this.gstType = gstType;
	}

	public boolean isPercentDiscount() {
		return isPercentDiscount;
	}

	public void setPercentDiscount(boolean isPercentDiscount) {
		this.isPercentDiscount = isPercentDiscount;
	}

}
