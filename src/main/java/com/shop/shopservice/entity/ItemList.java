package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
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
@Table(name = "ITEM_LIST")
@NamedQueries({ @NamedQuery(name = "ItemList.getById", 
              query = "SELECT itl FROM ItemList itl WHERE itl.id = :id"),
	@NamedQuery(name ="ItemList.findByShopId",
	          query= "SELECT itl FROM ItemList itl WHERE itl.shopId = :shopId and isActive is TRUE"),
	@NamedQuery(name ="ItemList.itemListExits",
			  query= "SELECT itl FROM ItemList itl WHERE itl.slotList = :slotList and itl.shopId = :shopId and itl.unitQuantity = :unitQuantity and itl.unitSellingPrice = :unitSellingPrice and itl.productId = :productId and itl.barcode = :barcode and itl.costPrice = :costPrice"),
    @NamedQuery(name ="ItemList.findByProductId",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.productId = :productId and itl.isOnline is TRUE"),
    @NamedQuery(name ="ItemList.findAll",
    		  query = "SELECT itl FROM ItemList itl"),
    @NamedQuery(name ="ItemList.getByBarcode",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.barcode = :barcode and isActive is TRUE"),
    @NamedQuery(name ="ItemList.getAllShopId",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.shopId = :shopId and isActive is TRUE"),
    @NamedQuery(name="ItemList.getAllByShopId",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.shopId = :shopId"),
    @NamedQuery(name ="ItemList.getAllDetailsById",
    		  query = "SELECT itl FROM ItemList itl WHERE itl.id = :id"),
    @NamedQuery(name ="ItemList.itemListCheckExits",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.shopId = :shopId and itl.unitQuantity = :unitQuantity and itl.productId = :productId and  itl.slotList = :slotList"),
    @NamedQuery(name ="ItemList.getAllOnline",
    	      query= "SELECT itl FROM ItemList itl WHERE itl.isOnline = :isOnline"),
    @NamedQuery(name ="ItemList.getAllHotSell",
    	      query= "SELECT itl FROM ItemList itl WHERE itl.hotSell = :hotSell"),
    @NamedQuery(name ="ItemList.getAllBaggage",
    		 query= "SELECT itl FROM ItemList itl WHERE itl.baggage = :baggage"),
    @NamedQuery(name ="ItemList.getAllMilaanOffer",
    		 query= "SELECT itl FROM ItemList itl WHERE itl.milaanOffer = :milaanOffer"),
    @NamedQuery(name ="ItemList.getAllAditionalDiscount",
    		 query= "SELECT itl FROM ItemList itl WHERE itl.aditionalDiscount = :aditionalDiscount"),
    @NamedQuery(name ="ItemList.getDeActiveForExcel", 
    		 query= "SELECT itl FROM ItemList itl WHERE itl.id = :id"),
     @NamedQuery(name ="ItemList.getItemListByProductId",
              query= "SELECT itl FROM ItemList itl WHERE itl.productId = :productId"),
     @NamedQuery(name ="ItemList.getAllVariantByOnlineByShopId",
              query= "SELECT itl FROM ItemList itl WHERE itl.shopId = :shopId and itl.isOnline = :isOnline"),
     @NamedQuery(name ="ItemList.getVariantByProductId",
    		 query= "SELECT itl FROM ItemList itl WHERE itl.productId = :productId"),
     @NamedQuery(name="ItemList.getAllVariantByOnlineByProductId",
    		 query= "SELECT itl FROM ItemList itl WHERE itl.productId = :productId and itl.isOnline = :isOnline"),
     @NamedQuery(name ="ItemList.getDeActiveById",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.id = :id"),
     @NamedQuery(name ="ItemList.forDelete",
    		  query= "SELECT itl FROM ItemList itl WHERE itl.productId = :productId")
	
})
public class ItemList implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private int productId;
	
	@Column(name = "PRICE", nullable = false)
	private float price;
	
	@Column(name = "BARCODE", nullable = false)
	private String barcode;
	
	@Column(name = "OFFER_FROM", nullable = false)
	private LocalDate offerFrom;
	
	@Column(name = "OFFER_TO", nullable = false)
	private LocalDate offerTo;
	
	@Column(name = "OFFER_ACTIVE_IND", nullable = false)
	private boolean offerActiveInd;
	
	@Column(name = "DELIVERY_CHARGE", nullable = false)
	private int deliveryCharge;
	
	@Column(name = "SELLING_PRICE", nullable = false)
	private float sellingPrice;
	
	@Column(name = "OFFER_PERCENT", nullable = false)
	private int offerPercent;
	
	@Column(name = "DATE_OF_MANUFACTURING", nullable = false)
	private LocalDate dateOfManufacturing;
	
	@Column(name = "DATE_OF_EXPIRE", nullable = false)
	private LocalDate dateOfExpire;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	
	@Column(name = "OFFER_AVAILABLE", nullable = false)
	private boolean offerAvailable;
	
	@Column(name = "OLD_PRICE", nullable = false)
	private int oldPrice;
	
	@Column(name = "DISCOUNT", nullable = false)
	private int discount;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "GST_PERCENT", nullable = false)
	private int gstPercent;
	
	@Column(name = "STOCK", nullable = false)
	private int stock;
	
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private int totalAmount;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "QUANTITY", nullable = false)
	private int quantity;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "COST_PRICE", nullable = false)
	private float costPrice;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "HSN_CODE", nullable = false)
	private String hsnCode;
	
	@Column(name = "MEASUREMENT", nullable = false)
	private int measurement;
	
	@Column(name = "UNIT_QUANTITY", nullable = false)
	private float unitQuantity;
	
	@Column(name = "OUT_OF_STOCK", nullable = false)
	private int outOfStock;
	
	@Column(name = "UNIT_SELLING_PRICE", nullable = false)
	private float unitSellingPrice;
	
	@Column(name ="SLOT_LIST", nullable = false)
	private String slotList;
	
	@Column(name ="SLOT_COUNT", nullable = false)
	private int slotCount;
	
	@Column(name ="IS_GST_INCLUDED", nullable = false)
	private boolean isGstIncluded;
	
	@Column(name ="MARGIN", nullable = false)
	private float margin;
	
	@Column(name ="MRP", nullable = false)
	private float mrp;
	
	@Column(name ="PURCHASE_PRICE", nullable = false)
	private float purchasePrice;
	
	@Column(name ="OTHER_CHARGE", nullable = false)
	private int otherCharge;
	
	@Column(name ="CUSTOMER_MARGIN", nullable = false)
	private float customerMargin;
	
	@Column(name ="NET_AMOUNT", nullable = false)
	private float netAmount;
	
	@Column(name ="MARGIN_PERCENT", nullable = false)
	private float marginPercent;
	
	@Column(name ="CUSTOMER_MARGIN_PERCENT", nullable = false)
	private float customerMarginPercent;
	
	@Column(name ="PURCHASE_OFFER_PRICE", nullable = false)
	private float purchaseOfferPrice;
	
	@Column(name ="PURCHASE_OFFER_PERCENT", nullable = false)
	private float purchaseOfferPercent;
	
	@Column(name ="BUNDLE_QUANTITY", nullable = false)
	private float bundleQuantity;
	
	@Column(name ="BUNDLE_CUSTOMER_DISCOUNT", nullable = false)
	private float bundleCustomerDiscount;
	
	@Column(name ="BUNDLE_PRICE", nullable = false)
	private float bundlePrice;
	
	@Column(name ="CUSTOMER_BUNDLE_OFFER", nullable = false)
	private float customerBundleOffer;
	
	@Column(name ="CUSTOMER_SINGLE_OFFER", nullable = false)
	private float customerSingleOffer;
	
	@Column(name ="BUNDLE_MARGIN", nullable = false)
	private float bundleMargin;
	
	@Column(name ="RACK_NUMBER", nullable = false)
	private String rackNumber;
	
	@Column(name ="IS_ONLINE", nullable = false)
	private boolean isOnline;
	
	@Column(name ="EXPIRY_ALERT", nullable = false)
	private String expiryAlert;
	
	@Column(name ="TITLE", nullable = false)
	private String title;
	
	@Column(name ="VENDER_ID", nullable = false)
	private int venderId;
	
	@Column(name ="SELLING_GST_AMOUNT", nullable = false)
	private float sellingGstAmount;
	
	@Column(name ="BUNDLE_GST_AMOUNT", nullable = false)
	private float bundleGstAmount;
	
	@Column(name ="HOT_SELL", nullable = false)
	private boolean hotSell;
	
	@Column(name ="BAGGAGE", nullable = false)
	private boolean baggage;
	
	@Column(name ="MILAAN_OFFER", nullable = false)
	private boolean milaanOffer;
	
	@Column(name ="ADITIONAL_DISCOUNT", nullable = false)
	private boolean aditionalDiscount;
	
	
	
	@Transient
	private Item item;
	
	@Transient
	private List<Slot> slot;
	
	@Transient
	private List<Image> image;
	
	@Transient
	private List<VariantStock> variantStock;
	
	@Transient
	private Vender vender;
	
	public  ItemList() {
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
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	 * @return the offerActiveInd
	 */
	public boolean isOfferActiveInd() {
		return offerActiveInd;
	}

	/**
	 * @param offerActiveInd the offerActiveInd to set
	 */
	public void setOfferActiveInd(boolean offerActiveInd) {
		this.offerActiveInd = offerActiveInd;
	}

	/**
	 * @return the deliveryCharge
	 */
	public int getDeliveryCharge() {
		return deliveryCharge;
	}

	/**
	 * @param deliveryCharge the deliveryCharge to set
	 */
	public void setDeliveryCharge(int deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
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
	 * @return the offerAvailable
	 */
	public boolean isOfferAvailable() {
		return offerAvailable;
	}

	/**
	 * @param offerAvailable the offerAvailable to set
	 */
	public void setOfferAvailable(boolean offerAvailable) {
		this.offerAvailable = offerAvailable;
	}

	/**
	 * @return the oldPrice
	 */
	public int getOldPrice() {
		return oldPrice;
	}

	/**
	 * @param oldPrice the oldPrice to set
	 */
	public void setOldPrice(int oldPrice) {
		this.oldPrice = oldPrice;
	}

	/**
	 * @return the discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	 * @return the costPrice
	 */
	public float getCostPrice() {
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(float costPrice) {
		this.costPrice = costPrice;
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
	 * @return the hsnCode
	 */
	public String getHsnCode() {
		return hsnCode;
	}

	/**
	 * @param hsnCode the hsnCode to set
	 */
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
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
	 * @return the unitQuantity
	 */
	public float getUnitQuantity() {
		return unitQuantity;
	}

	/**
	 * @param unitQuantity the unitQuantity to set
	 */
	public void setUnitQuantity(float unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	/**
	 * @return the outOfStock
	 */
	public int getOutOfStock() {
		return outOfStock;
	}

	/**
	 * @param outOfStock the outOfStock to set
	 */
	public void setOutOfStock(int outOfStock) {
		this.outOfStock = outOfStock;
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
	 * @return the slotList
	 */
	public String getSlotList() {
		return slotList;
	}

	/**
	 * @param slotList the slotList to set
	 */
	public void setSlotList(String slotList) {
		this.slotList = slotList;
	}

	/**
	 * @return the slotCount
	 */
	public int getSlotCount() {
		return slotCount;
	}

	/**
	 * @param slotCount the slotCount to set
	 */
	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	/**
	 * @return the isGstIncluded
	 */
	public boolean isGstIncluded() {
		return isGstIncluded;
	}

	/**
	 * @param isGstIncluded the isGstIncluded to set
	 */
	public void setGstIncluded(boolean isGstIncluded) {
		this.isGstIncluded = isGstIncluded;
	}

	/**
	 * @return the margin
	 */
	public float getMargin() {
		return margin;
	}

	/**
	 * @param margin the margin to set
	 */
	public void setMargin(float margin) {
		this.margin = margin;
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
	 * @return the otherCharge
	 */
	public int getOtherCharge() {
		return otherCharge;
	}

	/**
	 * @param otherCharge the otherCharge to set
	 */
	public void setOtherCharge(int otherCharge) {
		this.otherCharge = otherCharge;
	}

	/**
	 * @return the customerMargin
	 */
	public float getCustomerMargin() {
		return customerMargin;
	}

	/**
	 * @param customerMargin the customerMargin to set
	 */
	public void setCustomerMargin(float customerMargin) {
		this.customerMargin = customerMargin;
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
	 * @return the customerMarginPercent
	 */
	public float getCustomerMarginPercent() {
		return customerMarginPercent;
	}

	/**
	 * @param customerMarginPercent the customerMarginPercent to set
	 */
	public void setCustomerMarginPercent(float customerMarginPercent) {
		this.customerMarginPercent = customerMarginPercent;
	}

	/**
	 * @return the purchaseOfferPrice
	 */
	public float getPurchaseOfferPrice() {
		return purchaseOfferPrice;
	}

	/**
	 * @param purchaseOfferPrice the purchaseOfferPrice to set
	 */
	public void setPurchaseOfferPrice(float purchaseOfferPrice) {
		this.purchaseOfferPrice = purchaseOfferPrice;
	}

	/**
	 * @return the purchaseOfferPercent
	 */
	public float getPurchaseOfferPercent() {
		return purchaseOfferPercent;
	}

	/**
	 * @param purchaseOfferPercent the purchaseOfferPercent to set
	 */
	public void setPurchaseOfferPercent(float purchaseOfferPercent) {
		this.purchaseOfferPercent = purchaseOfferPercent;
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
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the slot
	 */
	public List<Slot> getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(List<Slot> slot) {
		this.slot = slot;
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

	/**
	 * @return the variantStock
	 */
	public List<VariantStock> getVariantStock() {
		return variantStock;
	}

	/**
	 * @param variantStock the variantStock to set
	 */
	public void setVariantStock(List<VariantStock> variantStock) {
		this.variantStock = variantStock;
	}

	/**
	 * @return the rackNumber
	 */
	public String getRackNumber() {
		return rackNumber;
	}

	/**
	 * @param rackNumber the rackNumber to set
	 */
	public void setRackNumber(String rackNumber) {
		this.rackNumber = rackNumber;
	}

	/**
	 * @return the isOnline
	 */
	public boolean isOnline() {
		return isOnline;
	}

	/**
	 * @param isOnline the isOnline to set
	 */
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * @return the expiryAlert
	 */
	public String getExpiryAlert() {
		return expiryAlert;
	}

	/**
	 * @param expiryAlert the expiryAlert to set
	 */
	public void setExpiryAlert(String expiryAlert) {
		this.expiryAlert = expiryAlert;
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
	 * @return the sellingGstAmount
	 */
	public float getSellingGstAmount() {
		return sellingGstAmount;
	}

	/**
	 * @param sellingGstAmount the sellingGstAmount to set
	 */
	public void setSellingGstAmount(float sellingGstAmount) {
		this.sellingGstAmount = sellingGstAmount;
	}

	/**
	 * @return the bundleGstAmount
	 */
	public float getBundleGstAmount() {
		return bundleGstAmount;
	}

	/**
	 * @param bundleGstAmount the bundleGstAmount to set
	 */
	public void setBundleGstAmount(float bundleGstAmount) {
		this.bundleGstAmount = bundleGstAmount;
	}

	/**
	 * @return the bundleCustomerDiscount
	 */
	public float getBundleCustomerDiscount() {
		return bundleCustomerDiscount;
	}

	/**
	 * @param bundleCustomerDiscount the bundleCustomerDiscount to set
	 */
	public void setBundleCustomerDiscount(float bundleCustomerDiscount) {
		this.bundleCustomerDiscount = bundleCustomerDiscount;
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

	
	
}
