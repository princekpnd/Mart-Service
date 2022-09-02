package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.Offline;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;
import com.shop.shopservice.service.IProductService;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/offlineproductlist")
public class OfflineProductListController {
	private final Logger log = LoggerFactory.getLogger(OfflineProductListController.class);

	@Autowired
	private IOfflineProductListService offlineProductListService;

	@Autowired
	private IOfflineService offlineService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IItemListService itemListService;
	
	@Autowired
	private IVariantStockService variantStockService;

	@GetMapping("getall")
	public ResponseEntity<List<OfflineProductList>> getAll() {
		List<OfflineProductList> offlineProductList = offlineProductListService.getAll();
		return new ResponseEntity<List<OfflineProductList>>(offlineProductList, HttpStatus.OK);

	}

	@GetMapping("get/byshopId/{shopId}")
	public ResponseEntity<List<OfflineProductList>> getByShopId(@PathVariable("shopId") String shopId) {
		List<OfflineProductList> offlineProductList = offlineProductListService.getByShopId(shopId);
		return new ResponseEntity<List<OfflineProductList>>(offlineProductList, HttpStatus.OK);
	}
	
//	@GetMapping("get/cashierid/{offlineCartId}/{cashierId)")
//	public ResponseEntity<List<OfflineProductList>> getByShopIdAndCashierId(@PathVariable("offlineCartId") int offlineCartId, @PathVariable("cashierId") String cashierId){
//		List<OfflineProductList> offlineProductList = offlineProductListService.getByShopIdAndCashierId(offlineCartId, cashierId);
//		return new ResponseEntity<List<OfflineProductList>>(offlineProductList, HttpStatus.OK);
//	}
	
	@GetMapping("getby/cashierId/{cashierId}/{offlineCartId}")
	public ResponseEntity<List<OfflineProductList>> getByShopIdAndCashierId(@PathVariable("cashierId") String cashierId, @PathVariable("offlineCartId") int offlineCartId){
		List<OfflineProductList> offlineProductList = offlineProductListService.getByShopIdAndCashierId(cashierId,offlineCartId);
		return new ResponseEntity<List<OfflineProductList>>(offlineProductList, HttpStatus.OK);
	}

	@GetMapping("get/byid/{id}")
	public ResponseEntity<OfflineProductList> getById(@PathVariable("id") int id) {
		OfflineProductList offlineProduct = offlineProductListService.getById(id);
		return new ResponseEntity<OfflineProductList>(offlineProduct, HttpStatus.OK);
	}

	@GetMapping("get/allproduct/bycartid/{offlineCartId}")
	public ResponseEntity<List<OfflineProductList>> getAllProductByCartId(
			@PathVariable("offlineCartId") int offlineCartId) {
		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(offlineCartId);
		return new ResponseEntity<List<OfflineProductList>>(offlineProductList, HttpStatus.OK);

	}
	
	@GetMapping("get/productlistbycaetid/{offlineCartId}/{productId}/{stockActiveInd}")
	public ResponseEntity<OfflineProductList> getByofflineCartIdAndProductId(@PathVariable("offlineCartId") int offlineCartId, @PathVariable("productId") String productId, @PathVariable("stockActiveInd") boolean stockActiveInd){
		OfflineProductList offlineProductList = offlineProductListService.getByofflineCartIdAndProductId(offlineCartId, productId, stockActiveInd);
		return new ResponseEntity<OfflineProductList>(offlineProductList, HttpStatus.OK);
	}
	
	
	
//	@GetMapping("getbyproductid/{productId}")
//	public ResponseEntity<OfflineProductList> getByProductId(@PathVariable("productId") String productId){
//		OfflineProductList offlineProductList = offlineProductListService.getByProductId(productId);
//		return new ResponseEntity<OfflineProductList>(offlineProductList, HttpStatus.OK);
//	}
	

	@GetMapping("check/{offlineCartId}/{productId}/{cashierId}/{stockActiveInd}")
	public ResponseEntity<Boolean> checkExit(@PathVariable("offlineCartId") int offlineCartId,
			@PathVariable("productId") String productId, @PathVariable("cashierId") String cashierId, @PathVariable("stockActiveInd") boolean stockActiveInd) {
		boolean offlineProduct = offlineProductListService.checkExit(offlineCartId, productId, cashierId, stockActiveInd);
		return new ResponseEntity<Boolean>(offlineProduct, HttpStatus.OK);
	}

	@GetMapping("deactive/byid/{id}")    
	public ResponseEntity<Map<String, String>> getDeactiveById(@PathVariable("id") int id){
		Map<String, String> response = new HashMap<String, String>();
		OfflineProductList offlineProduct = offlineProductListService.getDeactiveById(id);
		
		int offlineCartId = offlineProduct.getOfflineCartId();
		boolean stockActiveInd = offlineProduct.isStockActiveInd();
		Offline offline = offlineService.getByBillId(offlineCartId);
		
			
			float price = offlineProduct.getPrice(),
					oldPrice = offlineProduct.getOldPrice(), totalPrice = offlineProduct.getTotalPrice(),
					discount = offlineProduct.getDiscount(), gstAmount = offlineProduct.getGstAmount(),marginPercent = offlineProduct.getMarginPercent(),
					bundleMargin = offlineProduct.getBundleMargin(), mrp = offlineProduct.getMrp(), unitSellingPrice = offlineProduct.getUnitSellingPrice(),
					purchasePrice = offlineProduct.getPurchasePrice(), bundlePrice = offlineProduct.getBundlePrice();
			int payableAmount =(int) Math.ceil(offline.getTotalPrice() - totalPrice);
			
			if (null != offlineProduct && null != offline){

			offline.setTotalPrice(offline.getTotalPrice() - totalPrice);
			offline.setOldPrice(offline.getOldPrice() - oldPrice);
			offline.setPayableAmount(payableAmount);
			offline.setDiscount(offline.getDiscount() - discount);
			offline.setGstAmount(offline.getGstAmount() - gstAmount);
			offline.setSellingPrice(offline.getSellingPrice() - price);
			
			offline.setMarginPercent(offline.getMarginPercent() - marginPercent);
			offline.setBundleMargin(offline.getBundleMargin() - bundleMargin);
			offline.setMrp(offline.getMrp() - mrp);
			offline.setUnitSellingPrice(offline.getUnitSellingPrice() - unitSellingPrice);
			offline.setPurchasePrice(offline.getPurchasePrice() - purchasePrice);
			offline.setBundlePrice(offline.getBundlePrice() - bundlePrice);
			offlineService.updateOffline(offline);
//			if (stockActiveInd){
//				int slotNumber = offlineProduct.getSlotNumber();
//				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber, Integer.parseInt(offlineProduct.getProductId()));
//			
//				ItemList product = itemListService.getById(Integer.parseInt(offlineProduct.getProductId()));
//				float quantity = offlineProduct.getQuantity();
//				product.setStock(product.getStock());
//				variantStock.setSoldStock(variantStock.getSoldStock());
//				variantStock.setCurrentStock(variantStock.getCurrentStock());  
//
//
//				itemListService.updateItemList(product);
//				variantStockService.updateStock(variantStock);
//			}
		offlineProduct.setActive(Boolean.FALSE);
		boolean Update = offlineProductListService.updateOfflineProductList(offlineProduct);
		if(Update) {
			response.put("status", Boolean.TRUE.toString());
			response.put("descreption", "Given product list is deactive");
			
		}else {
			response.put("status", Boolean.FALSE.toString());
			response.put("descreption", "Given productList is not deactive");
		}
		
		
	}
		return ResponseEntity.ok().body(response);
	}
	
	

//	@SuppressWarnings({})
//	@PostMapping("/create")
//	ResponseEntity<Map<String, String>> createOfflineProductList(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.SHOP_ID)) {
//			OfflineProductList offlineProductList = new OfflineProductList(json.get(ServiceConstants.SHOP_ID),
//					json.get(ServiceConstants.PRODUCT_NAME),
//					 json.get(ServiceConstants.BRAND_NAME));
//			
//			offlineProductList.setBrandName(json.get(ServiceConstants.BRAND_NAME));
//			offlineProductList.setDiscount(Integer.parseInt(json.get(ServiceConstants.DISCOUNT)));
//			offlineProductList.setGstAmount(Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT)));
//			offlineProductList.setGstPercent(Integer.parseInt(json.get(ServiceConstants.GST_PERCENT)));
//			offlineProductList.setOfferPercent(Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT)));
//			offlineProductList.setOfflineCartId(Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID)));
//			offlineProductList.setOldPrice(Integer.parseInt(json.get(ServiceConstants.OLD_PRICE)));
//			offlineProductList.setPrice(Integer.parseInt(json.get(ServiceConstants.PRICE)));
//			offlineProductList.setProductName(json.get(ServiceConstants.PRODUCT_NAME));
//			offlineProductList.setQuantity(Integer.parseInt(json.get(ServiceConstants.QUANTITY)));
//			offlineProductList.setShopId(json.get(ServiceConstants.SHOP_ID));
//			offlineProductList.setTotalPrice(Integer.parseInt(json.get(ServiceConstants.TOTAL_PRICE)));
//			offlineProductList.setActive(Boolean.TRUE);
//			offlineProductList.setCreatedOn(new Date());
//			offlineProductList.setDeleted(Boolean.FALSE);
//			
//			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
//			if(offlineProductListService.offlineProductExists(offlineProductList.getProductName(), offlineProductList.getShopId(), offlineProductList.getBrandName())) {
//				response.put("status", Boolean.FALSE.toString());
//				response.put("description", "Product all ready exist with given cartId");
//			}else {
//				 offlineProductListService.offlineProductCreate(offlineProductList);
//				response.put("status", Boolean.TRUE.toString());
//				response.put("Discreption","Product created with given shopId and product name");
//			}
//			
//
//		}
//
//		return ResponseEntity.ok().body(response);
//	}
	
	
	
	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createOfflineProductList(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.PRODUCT_NAME)
				&& null != json.get(ServiceConstants.BRAND_NAME) && null != json.get(ServiceConstants.SHOP_NAME)
				&& null != json.get(ServiceConstants.QUANTITY)
				&& null != json.get(ServiceConstants.TOTAL_AMOUNT) && null != json.get(ServiceConstants.SELLING_PRICE)
				&& null != json.get(ServiceConstants.OFFLINE_CART_ID) && null != json.get(ServiceConstants.STOCK_ACTIVE_IND)) {
			String shopId = json.get(ServiceConstants.SHOP_ID), productName = json.get(ServiceConstants.PRODUCT_NAME),
					brandName = json.get(ServiceConstants.BRAND_NAME), shopName = json.get(ServiceConstants.SHOP_NAME);
							
			boolean stockActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.STOCK_ACTIVE_IND));
			int productQuantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY)), 
					totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT)),
					sellingPrice = Integer.parseInt(json.get(ServiceConstants.SELLING_PRICE)),
					offlineCartId = Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID)),
					paymentType = Integer.parseInt(json.get(ServiceConstants.PAYMENT_TYPE));
					
			Calculation calculation = new Calculation();
			  OfflineProductList offlineProductList = new OfflineProductList(shopId, productName, brandName);
			
			  offlineProductList.setBrandName(brandName);
				offlineProductList.setOfflineCartId(offlineCartId);
				offlineProductList.setPrice(sellingPrice);
				offlineProductList.setProductName(productName);
				offlineProductList.setQuantity(productQuantity);
				offlineProductList.setShopId(shopId);
				offlineProductList.setTotalPrice(calculation.DecimalCalculation(totalAmount));
				offlineProductList.setShopName(shopName);
				offlineProductList.setActive(Boolean.TRUE);
				offlineProductList.setCreatedOn(new Date());
				offlineProductList.setDeleted(Boolean.FALSE);
				offlineProductList.setStockActiveInd(stockActiveInd);
				offlineProductList.setPaymentType(paymentType);
				
				if(null != json.get(ServiceConstants.PRODUCT_ID)) {
					offlineProductList.setProductId((json.get(ServiceConstants.PRODUCT_ID)));
				}
				if(null != json.get(ServiceConstants.DISCOUNT)) {
					offlineProductList.setDiscount(Integer.parseInt(json.get(ServiceConstants.DISCOUNT)));
				}
				if(null != json.get(ServiceConstants.OLD_PRICE)) {
					offlineProductList.setOldPrice(Integer.parseInt(json.get(ServiceConstants.OLD_PRICE)));
				}
				if(null != json.get(ServiceConstants.GST_PERCENT)) {
					offlineProductList.setGstPercent(Integer.parseInt(json.get(ServiceConstants.GST_PERCENT)));
				}
				if(null != json.get(ServiceConstants.OLD_PRICE)) {
					offlineProductList.setOldPrice(Integer.parseInt(json.get(ServiceConstants.OLD_PRICE)));
				}
				if(null != json.get(ServiceConstants.GST_AMOUNT)) {
					offlineProductList.setGstAmount(Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT)));
				}
				if(null != json.get(ServiceConstants.BATCH_NUMBER)) {
					offlineProductList.setBatchNumber(json.get(ServiceConstants.BATCH_NUMBER));
				}
				if(null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
					offlineProductList.setDateOfExpire(new Date(json.get(ServiceConstants.DATE_OF_EXPIRE)));
				}
				if(null != json.get(ServiceConstants.MEASUREMENT)) {
					String measurement = (json.get(ServiceConstants.MEASUREMENT));
					offlineProductList.setMeasurement(measurement);
				}
				if(null != json.get(ServiceConstants.IGST_AMOUNT)) {
					float igstAmount = Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT));
					offlineProductList.setIgstAmount(igstAmount);
				}
				
				if(null != json.get(ServiceConstants.UTGST_AMOUNT)) {
					float utgstAmount = Float.parseFloat(json.get(ServiceConstants.UTGST_AMOUNT));
					offlineProductList.setUtgstAmount(utgstAmount);
				}
				
				if(null != json.get(ServiceConstants.SGST_AMOUNT)) {
					float sgstAmount = Float.parseFloat(json.get(ServiceConstants.SGST_AMOUNT));
					offlineProductList.setSgstAmount(sgstAmount);
				}
				
				if(null != json.get(ServiceConstants.CGST_AMOUNT)) {
					float cgstAmount = Float.parseFloat(json.get(ServiceConstants.CGST_AMOUNT));
					offlineProductList.setCgstAmount(cgstAmount);
				}
				
				if(null != json.get(ServiceConstants.GST_TYPE)) {
					int gstType = Integer.parseInt(json.get(ServiceConstants.GST_TYPE));
					offlineProductList.setGstType(gstType);
				}
				
				
			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
			if(offlineProductListService.offlineProductExists(offlineProductList.getProductName(), offlineProductList.getShopId(), offlineProductList.getBrandName())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product all ready exist with given cartId");
			}else {
				 offlineProductListService.offlineProductCreate(offlineProductList);
				response.put("status", Boolean.TRUE.toString());
				response.put("Discreption","Product created with given shopId and product name");
			}
			

		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateOfflineProductList(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.BILLING_ID));
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.ID)) {
			OfflineProductList offlineProductList = offlineProductListService
					.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			if (null != json.get(ServiceConstants.PRODUCT_NAME)) {
				String productName = json.get(ServiceConstants.PRODUCT_NAME);
				offlineProductList.setProductName(productName);
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				offlineProductList.setShopId(shopId);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.TRUE;
				offlineProductList.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.BRAND_NAME)) {
				String brandName = json.get(ServiceConstants.BRAND_NAME);
				offlineProductList.setBrandName(brandName);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				offlineProductList.setCreatedOn(createdOn);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDelated = Boolean.FALSE;
				offlineProductList.setDeleted(isDelated);
			}

			if (null != json.get(ServiceConstants.DISCOUNT)) {
				int discount = Integer.parseInt(json.get(ServiceConstants.DISCOUNT));
				offlineProductList.setDiscount(discount);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT));
				offlineProductList.setGstAmount(gstAmount);
			}
			if (null != json.get(ServiceConstants.GST_PERCENT)) {
				int gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT));
				offlineProductList.setGstPercent(gstPercent);
			}
			if (null != json.get(ServiceConstants.OFFER_PERCENT)) {
				int offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
				offlineProductList.setOfferPercent(offerPercent);
			}
			if (null != json.get(ServiceConstants.OFFLINE_CART_ID)) {
				int offlineCartId = Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID));
				offlineProductList.setOfflineCartId(offlineCartId);
			}
			if (null != json.get(ServiceConstants.OLD_PRICE)) {
				int oldPrice = Integer.parseInt(json.get(ServiceConstants.OLD_PRICE));
				offlineProductList.setOldPrice(oldPrice);

			}
			if (null != json.get(ServiceConstants.PRICE)) {
				int price = Integer.parseInt(json.get(ServiceConstants.PRICE));
				offlineProductList.setPrice(price);
			}
			if (null != json.get(ServiceConstants.PRODUCT_NAME)) {
				String productName = json.get(ServiceConstants.PRODUCT_NAME);
				offlineProductList.setProductName(productName);
			}
			if (null != json.get(ServiceConstants.QUANTITY)) {
				int quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY));
				offlineProductList.setQuantity(quantity);
			}
			if (null != json.get(ServiceConstants.TOTAL_PRICE)) {
				int totalPrice = Integer.parseInt(json.get(ServiceConstants.TOTAL_PRICE));
				offlineProductList.setTotalPrice(totalPrice);
			}

			offlineProductListService.updateOfflineProductList(offlineProductList);
			response.put("status", Boolean.TRUE.toString());
			response.put("Discreption", "Product list updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Discreption", "Product list not updated");
		}

		return ResponseEntity.ok().body(response);
	}

//	@DeleteMapping("delete/{id}")
//	ResponseEntity<Map<String, String>> deleteOfflineProductList(@PathVariable("id") int id) {
//		Map<String, String> response = new HashMap<String, String>();
//		OfflineProductList offlineProduct = offlineProductListService.getById(id);
//		int offlineCartId = offlineProduct.getOfflineCartId();
//		boolean stockActiveInd = offlineProduct.isStockActiveInd();
//
//		Offline offline = offlineService.getByBillId(offlineCartId);
//
//		if (null != offlineProduct && null != offline){
//			if(null !=offlineProduct){
//				
//		
//			float price = offlineProduct.getPrice(),
//					oldPrice = offlineProduct.getOldPrice(), totalPrice = offlineProduct.getTotalPrice(),
//					discount = offlineProduct.getDiscount(), gstAmount = offlineProduct.getGstAmount();
//			int payableAmount =(int) Math.ceil(offline.getTotalPrice() - totalPrice);
//
//
//			offline.setTotalPrice(offline.getTotalPrice() - totalPrice);
//			offline.setOldPrice(offline.getOldPrice() - oldPrice);
//			offline.setPayableAmount(payableAmount);
//			offline.setDiscount(offline.getDiscount() - discount);
//			offline.setGstAmount(offline.getGstAmount() - gstAmount);
//			offline.setSellingPrice(offline.getSellingPrice() - price);
//			offlineService.updateOffline(offline);
//			if (stockActiveInd) {
//				ItemList product = itemListService.getById(Integer.parseInt(offlineProduct.getProductId()));
//                 // Product product = productService.getProduct(Integer.parseInt(offlineProduct.getProductId()));
//                 //Product product = productService.getProduct(Integer.parseInt(offlineProduct.getProductId()));
//                 //Product product = productService.getProduct(Integer.parseInt(offlineProduct.getProductId()));
//				float quantity = offlineProduct.getQuantity();
//				product.setStock(product.getStock() + (int) quantity);
//				itemListService.updateItemList(product);
//			}
//
//			boolean result = offlineProductListService.deleteOfflineProductList(id);
//			if (result) {
//				response.put("status", Boolean.TRUE.toString());
//				response.put("description", "Product List delete with given id:" + id);
//			}
//			}
//			
//		} else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", "Product List does not exist with given category Id");
//
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteOfflineProductList1(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		OfflineProductList offlineProduct = offlineProductListService.getById(id);
		int offlineCartId = offlineProduct.getOfflineCartId();
		boolean stockActiveInd = offlineProduct.isStockActiveInd();
		Offline offline = offlineService.getByBillId(offlineCartId);
		if (null != offlineProduct && null != offline) {
			if (null != offlineProduct) {

			}
			float price = offlineProduct.getPrice(), oldPrice = offlineProduct.getOldPrice(),
					totalPrice = offlineProduct.getTotalPrice(), discount = offlineProduct.getDiscount(),
					gstAmount = offlineProduct.getGstAmount(), marginPercent = offlineProduct.getMarginPercent(),
					bundleMargin = offlineProduct.getBundleMargin(), mrp = offlineProduct.getMrp(),
					unitSellingPrice = offlineProduct.getUnitSellingPrice(),
					purchasePrice = offlineProduct.getPurchasePrice(), bundlePrice = offlineProduct.getBundlePrice();
			int payableAmount = (int) Math.ceil(offline.getTotalPrice() - totalPrice);

			offline.setTotalPrice(offline.getTotalPrice() - totalPrice);
			offline.setOldPrice(offline.getOldPrice() - oldPrice);
			offline.setPayableAmount(payableAmount);
			offline.setDiscount(offline.getDiscount() - discount);
			offline.setGstAmount(offline.getGstAmount() - gstAmount);
			offline.setSellingPrice(offline.getSellingPrice() - price);

			offline.setMarginPercent(offline.getMarginPercent() - marginPercent);
			offline.setBundleMargin(offline.getBundleMargin() - bundleMargin);
			offline.setMrp(offline.getMrp() - mrp);
			offline.setUnitSellingPrice(offline.getUnitSellingPrice() - unitSellingPrice);
			offline.setPurchasePrice(offline.getPurchasePrice() - purchasePrice);
			offline.setBundlePrice(offline.getBundlePrice() - bundlePrice);
			offlineService.updateOffline(offline);
			if (stockActiveInd) {
				ItemList product = itemListService.getById(Integer.parseInt(offlineProduct.getProductId()));
//		Product product = productService.getProduct(Integer.parseInt(offlineProduct.getProductId()));
				float quantity = offlineProduct.getQuantity();
				product.setStock(product.getStock() + (int) quantity);
				itemListService.updateItemList(product);
			}

			boolean result = offlineProductListService.deleteOfflineProductList(id);
			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Product List delete with given id:" + id);
			}

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product List does not exist with given category Id");

		}
		return ResponseEntity.ok().body(response);
	}

//	@DeleteMapping("delete1/{id}")
//	ResponseEntity<Map<String, String>> deleteOfflineProductListTemp(@PathVariable("id") int id) {
//		Map<String, String> response = new HashMap<String, String>();
//		OfflineProductList offlineProduct = offlineProductListService.getById(id);
//		int offlineCartId = offlineProduct.getOfflineCartId();
//		boolean stockActiveInd = offlineProduct.isStockActiveInd();
//		Offline offline = null;
//	 offline = offlineService.getByBillId(offlineCartId);
//		if (null != offlineProduct && null != offline){
//			if(null != offlineProduct) {
//				
//			
//			float price = offlineProduct.getPrice(),
//					oldPrice = offlineProduct.getOldPrice(), totalPrice = offlineProduct.getTotalPrice(),
//					discount = offlineProduct.getDiscount(), gstAmount = offlineProduct.getGstAmount();
//			int payableAmount =(int) Math.ceil(offline.getTotalPrice() - totalPrice);
//
//
//			offline.setTotalPrice(offline.getTotalPrice() - totalPrice);
//			offline.setOldPrice(offline.getOldPrice() - oldPrice);
//			offline.setPayableAmount(payableAmount);
//			offline.setDiscount(offline.getDiscount() - discount);
//			offline.setGstAmount(offline.getGstAmount() - gstAmount);
//			offline.setSellingPrice(offline.getSellingPrice() - price);
//			offlineService.updateOffline(offline);
//			if (stockActiveInd) {
//				ItemList product = itemListService.getById(Integer.parseInt(offlineProduct.getProductId()));
////		Product product = productService.getProduct(Integer.parseInt(offlineProduct.getProductId()));
//				float quantity = offlineProduct.getQuantity();
//				product.setStock(product.getStock() + (int) quantity);
//				itemListService.updateItemList(product);
//			}
//			}else {
//				offline.setTotalPrice(0);
//			}
//
//			boolean result = offlineProductListService.deleteOfflineProductList(id);
//			if (result) {
//				response.put("status", Boolean.TRUE.toString());
//				response.put("description", "Product List delete with given id:" + id);
//			}
//			
//		} else {
//	//		offline.setTotalPrice(0);
//			offlineProduct.setBundleQuantity(0);
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", "Product List does not exist with given category Id");
//
//		}
//		return ResponseEntity.ok().body(response);
//	}

}
