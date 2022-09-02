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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CART")
@NamedQueries({ @NamedQuery(name = "Cart.findAll", query = "SELECT cr FROM Cart cr"),
		@NamedQuery(name = "Cart.findCartByUserId", query = "SELECT cr FROM Cart cr WHERE cr.userId = :userId"),
		@NamedQuery(name = "Cart.findByUserId", query = "SELECT cr FROM Cart cr WHERE cr.userId = :userId"),
		@NamedQuery(name = "Cart.findCartForUserByShopId", query = "SELECT cr FROM Cart cr WHERE cr.shopId= :shopId"),
		@NamedQuery(name = "Cart.findCartForUserByUserId", query = "SELECT cr FROM Cart cr WHERE cr.userId= :userId"),
		@NamedQuery(name = "Cart.findByDeactiveOrder", query = "SELECT cr FROM Cart cr WHERE cr.userId= :userId and cr.orderStatus = :orderStatus"),
		@NamedQuery(name = "Cart.findByOrderActiveUserId", query = "SELECT cr FROM Cart  cr WHERE cr.userId = :userId  and cr.shopId = :shopId and  cr.orderStatus = :orderStatus"),
		@NamedQuery(name = "Cart.findDeactiveCart", query = "SELECT cr FROM Cart  cr WHERE cr.userId = :userId  and cr.shopId = :shopId and  isActive is FALSE"),
		@NamedQuery(name = "Cart.findCartForOrder", query = "SELECT cr FROM Cart cr WHERE cr.cartId =:cartId"),
		@NamedQuery(name = "Cart.findOrderStatus", query = "SELECT cr FROM Cart cr WHERE cr.cartId = :cartId and cr.shopId = :shopId"),
		@NamedQuery(name = "Cart.orderDetails", query = " SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.userId = :userId and cr. orderStatus = :orderStatus"),
		@NamedQuery(name = "Cart.combinedOrderDetails", query = " SELECT cr FROM Cart cr WHERE cr.userId = :userId and cr.orderStatus = :orderStatus"),
		@NamedQuery(name = "Cart.findOrderForAdmin", query = " SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
		@NamedQuery(name = "Cart.findAllCartByShopId", query = " SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus != :orderStatus"),
		@NamedQuery(name = "Cart.findAllCartForUser", query = " SELECT cr FROM Cart cr WHERE cr.userId = :userId and cr.orderStatus != :orderStatus"),
		@NamedQuery(name = "Cart.findOfflineCart", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus =:orderStatus and  isOffline is TRUE"),
		@NamedQuery(name = "Cart.findOfflineByShopId", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus and isOffline is TRUE "),
		@NamedQuery(name = "Cart.findAllAmount", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId"),
		@NamedQuery(name ="Cart.findByDeliveryBoy", query = "SELECT cr FROM Cart cr WHERE cr.dBoyNumber = :dBoyNumber and cr.shopId = :shopId"),
        @NamedQuery(name ="Cart.findForDMobile", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.dBoyNumber =:dBoyNumber and cr.orderStatus = :orderStatus"),
        @NamedQuery(name ="Cart.findByShopId", query= "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId"),
        @NamedQuery(name ="Cart.findAllAcceptCart", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
        @NamedQuery(name ="Cart.findAllPlacedCart", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
        @NamedQuery(name ="Cart.findAllPackedCart", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
        @NamedQuery(name ="Cart.findAllDeliveredCart", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus and cr.deliveryType = :deliveryType"),
        @NamedQuery(name ="Cart.findCartBySlotTime", query = "SELECT cr FROM Cart cr WHERE cr.slotTime = :slotTime"),
        @NamedQuery(name ="Cart.findBySlotTime", query = "SELECT cr FROM Cart cr WHERE cr.slotTime = :slotTime"),
        @NamedQuery(name ="Cart.findBySlotTimeAndDate", query = "SELECT cr FROM Cart cr WHERE cr.slotTime = :slotTime and cr.slotDate =:slotDate"),
        @NamedQuery(name ="Cart.findByAndDate", query = "SELECT cr FROM Cart cr WHERE cr.slotTime = :slotTime and cr.slotDate =:slotDate"),
        @NamedQuery(name ="Cart.findAllDeliveryByDBoy", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
        @NamedQuery(name ="Cart.findAllRejectrdByDBoy", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.orderStatus = :orderStatus"),
        @NamedQuery(name = "Cart.findAllDeliveryInformatio", query = "SELECT cr FROM Cart cr WHERE cr.shopId = :shopId and cr.dBoyNumber = :dBoyNumber and cr.orderStatus = :orderStatus")
        

})

public class Cart implements Serializable {

	private static final long serialVersionUID = 1385794955661915701L;

	@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
//	@SequenceGenerator(name="book_generator", sequenceName = "book_seq", allocationSize=50)
//	private Long id;

	@Column(name = "ID", nullable = false)
	private int cartId;

	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;

	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private float totalAmount;

	@Column(name = "TRANSACTION_ID", nullable = false)
	private String transactionId;

	@Column(name = "PAID", nullable = false)
	private float paid;

	@Column(name = "DUES", nullable = false)
	private float dues;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;

	@Column(name = "ORDER_STATUS", nullable = false)
	private int orderStatus;

	@Column(name = "TRANSACTION_TYPE", nullable = false)
	private int transactionType;

	@Column(name = "ORDER_DATE", nullable = false)
	private Date orderDate;

	@Column(name = "DELIVERY_CHARGE", nullable = false)
	private float deliveryCharge;
	
	@Column(name = "PRICE", nullable = false)
	private float price;

	@Column(name = "DISCOUNT", nullable = false)
	private float discount;

	@Column(name = "ORDER_TYPE", nullable = false)
	private int orderType;

	@Column(name = "ADDRESS_ID", nullable = false)
	private int addressId;

	@Column(name = "MOBILE_NUMBER", nullable = false)
	private String mobileNo;

	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	@Column(name = "REVIEW", nullable = false)
	private String review;
	
	@Column(name = "SLOT_DATE", nullable = false)
	private Date slotDate;	

	@Column(name = "DELIVERY_DATE", nullable = false)
	private Date deliveryDate;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "SHIPPING_ID", nullable = false)
	private String shippingId;
	
	@Column(name ="DELIVERY_TYPE", nullable = false)
	private int deliveryType;
	
	@Column(name ="SHIPPING_DATE", nullable = false)
	private Date  shippingDate;
	
	@Column(name = "SHIPPING_NAME", nullable = false)
	private String shippingName;
	
	@Column(name = "D_BOY_NAME", nullable = false)
	private String dBoyName;
	
	@Column(name = "D_BOY_NUMBER", nullable = false)
	private String dBoyNumber;	

	@Column(name = "COURIER_NAME", nullable = false)
	private  String courierName;
	
	@Column(name = "IS_OFFLINE", nullable = false)
	private  boolean isOffline;
	
	@Column(name ="SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private String adminId;
	
	@Column(name = "PAYABLE_AMOUNT", nullable = false)
	private int payableAmount;
	
	@Column(name = "OTP", nullable = false)
	private int otp;
	
	@Column(name = "INSIDE_SHOP", nullable = false)
	private boolean insideShop;
	
	@Column(name = "BUNDLE_DISCOUNT", nullable = false)
	private float bundleDiscount;
	
	@Column(name = "CURRENT_ADDRESS", nullable = false)
	private String currentAddress;
	
	@Column(name = "LONGITUDE", nullable = false)
	private String longitude;
	
	@Column(name = "LATITUDE", nullable = false)
	private String latitude;
	
	@Column(name = "MRP", nullable = false)
	private float mrp;
	
	@Column(name = "SHOP_IMAGE", nullable = false)
	private String shopImage;
	
	@Column(name = "SLOT_TIME", nullable = false)
	private String slotTime;
	
	@Column(name = "DELIVERY_TIME", nullable = false)
	private String deliveryTime;
	
	@Column(name = "USER_IMAGE", nullable = false)
	private String userImage;
	
	@Column(name = "PRODUCT_RETURN", nullable = false)
	private boolean productReturn;
	
	@Transient
	private List<ProductList> productList;

	@Transient
	private Address address;

	public Cart() {
		super();
	}

	public Cart(String userId, String shopId) {
		this.userId = userId;
		this.shopId = shopId;
	}
	
	public Cart(String userName , String shopId, String mobileNo) {
		this.userName = userName;
		this.shopId = shopId;
		this.mobileNo = mobileNo;
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
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the paid
	 */
	public float getPaid() {
		return paid;
	}

	/**
	 * @param paid the paid to set
	 */
	public void setPaid(float paid) {
		this.paid = paid;
	}

	/**
	 * @return the dues
	 */
	public float getDues() {
		return dues;
	}

	/**
	 * @param dues the dues to set
	 */
	public void setDues(float dues) {
		this.dues = dues;
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
	 * @return the orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the transactionType
	 */
	public int getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
	 * @return the orderType
	 */
	public int getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the addressId
	 */
	public int getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
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
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	 * @return the shippingId
	 */
	public String getShippingId() {
		return shippingId;
	}

	/**
	 * @param shippingId the shippingId to set
	 */
	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	/**
	 * @return the deliveryType
	 */
	public int getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(int deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the shippingDate
	 */
	public Date getShippingDate() {
		return shippingDate;
	}

	/**
	 * @param shippingDate the shippingDate to set
	 */
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	/**
	 * @return the shippingName
	 */
	public String getShippingName() {
		return shippingName;
	}

	/**
	 * @param shippingName the shippingName to set
	 */
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	/**
	 * @return the dBoyName
	 */
	public String getdBoyName() {
		return dBoyName;
	}

	/**
	 * @param dBoyName the dBoyName to set
	 */
	public void setdBoyName(String dBoyName) {
		this.dBoyName = dBoyName;
	}

	/**
	 * @return the dBoyNumber
	 */
	public String getdBoyNumber() {
		return dBoyNumber;
	}

	/**
	 * @param dBoyNumber the dBoyNumber to set
	 */
	public void setdBoyNumber(String dBoyNumber) {
		this.dBoyNumber = dBoyNumber;
	}

	/**
	 * @return the courierName
	 */
	public String getCourierName() {
		return courierName;
	}

	/**
	 * @param courierName the courierName to set
	 */
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	/**
	 * @return the isOffline
	 */
	public boolean isOffline() {
		return isOffline;
	}

	/**
	 * @param isOffline the isOffline to set
	 */
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
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
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
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
	 * @return the productList
	 */
	public List<ProductList> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductList> productList) {
		this.productList = productList;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the otp
	 */
	public int getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(int otp) {
		this.otp = otp;
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
	 * @return the insideShop
	 */
	public boolean isInsideShop() {
		return insideShop;
	}

	/**
	 * @param insideShop the insideShop to set
	 */
	public void setInsideShop(boolean insideShop) {
		this.insideShop = insideShop;
	}

	public float getBundleDiscount() {
		return bundleDiscount;
	}

	public void setBundleDiscount(float bundleDiscount) {
		this.bundleDiscount = bundleDiscount;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public boolean isProductReturn() {
		return productReturn;
	}

	public void setProductReturn(boolean productReturn) {
		this.productReturn = productReturn;
	}
}
