package com.shop.shopservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Cashier;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.Offline;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.ICashierService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;
import com.shop.shopservice.service.IProductService;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.service.IVenderService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/offline")
public class OfflineController {
	private final Logger log = LoggerFactory.getLogger(OfflineController.class);
	@Autowired
	private IOfflineService offlineService;

	@Autowired
	private IVenderService venderService;

	@Autowired
	private IBrandService brandService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IItemService itemService;
	@Autowired
	private ISlotService slotService;
	
	

	@Autowired
	private IItemListService itemListService;

	@Autowired
	private ICashierService cashierService;

	@Autowired
	private IVariantStockService variantStockService;

	@Autowired
	private IOfflineProductListService offlineProductListService;
	LocalData localData = new LocalData();
	
	@GetMapping("getall")
	public ResponseEntity<List<Offline>> getAllOffline() {
		List<Offline> offlineList = offlineService.getAllOffline();
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/bybillid/{billingId}")
	public ResponseEntity<Offline> getByBillId(@PathVariable("billingId") int billingId) {
		Offline offline = offlineService.getByBillId(billingId);
		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
		offline.setOfflineProductList(offlineProductList);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("getamount/byshopid/{shopId}/{cashierId}")
	public ResponseEntity<List<Offline>> getAllAmountByCashierId(@PathVariable("shopId") String shopId,
			@PathVariable("cashierId") String cashierId) {
		List<Offline> offlineList = offlineService.getAllAmountByCashierId(shopId, cashierId);
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("getbycashierid/{cashierId}")
	public ResponseEntity<List<Offline>> getActiveBillByCashierId(@PathVariable("cashierId") String cashierId) {
		List<Offline> offlineList = offlineService.getActiveBillByCashierId(cashierId);
		if (offlineList != null) {
			int id = offlineList.get(0).getBillingId();
			List<OfflineProductList> offlineProductList = offlineProductListService.getByShopIdAndCashierId(cashierId,
					id);
			offlineList.get(0).setOfflineProductList(offlineProductList);
		}

		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("cheackbill/create/{billingId}")
	public ResponseEntity<Boolean> cheakBillCreated(@PathVariable("billingId") int billingId) {
		boolean offlineList = offlineService.cheakBillCreated(billingId);
		return new ResponseEntity<Boolean>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/byshopid/{shopId}/{cashierId}")
	public ResponseEntity<Offline> getAllBillByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("cashierId") String cashierId) {
		Offline offline = offlineService.getAllBillByShopId(shopId, cashierId);
		if (null != offline) {
			int billingId = offline.getBillingId();

			List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
			if (offlineProductList.size() > 0) {
				offline.setOfflineProductList(offlineProductList);
			} else {
				offline.setTotalPrice(0);
				offline.setPayableAmount(0);
				offline.setSellingPrice(0);
				offline.setOldPrice(0);
				offline.setMrp(0);
				offlineService.updateOffline(offline);
			}
		}
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@PutMapping("offline/increment/{billingId}/{productId}/{stockActiveInd}")
	public ResponseEntity<Map<String, String>> getCartByCartIdAndProductId(@PathVariable("billingId") int billingId,
			@PathVariable("productId") String productId, @PathVariable("stockActiveInd") boolean stockActiveInd) {
		Map<String, String> response = new HashMap<String, String>();
		Offline offline = offlineService.getByBillId(billingId);
//		float totalAmount = offline.getTotalPrice(), payableAmount = o ffline.getPayableAmount(),
//				sellingPrice = offline.getSellingPrice(), gstAmount = offline.getGstAmount(), oldPrice = offline.getOldPrice(),
//				discount = offline.getDiscount(), marginPercent = offline.getMarginPercent(), bundleMargin = offline.getBundleMargin(),
//				mrp = offline.getMrp(), unitSellingPrice = offline.getUnitSellingPrice(), purchasePrice = offline.getPurchasePrice(),
//				customerSingleOffer = offline.getCustomerSingleOffer(),customerBundleOffer = offline.getCustomerBundleOffer(),
//				bundleQuantity = offline.getBundleQuantity(), extraBillDiscount = offline.getExtraBillDiscount();
		OfflineProductList offlineProductList = offlineProductListService.getByofflineCartIdAndProductId(billingId,
				productId, stockActiveInd);
		ItemList item = itemListService.getAllDetailsById(Integer.parseInt(productId));
		// float gstAmount = item.getGstAmount()
		offline.setBundleMargin(offline.getBundleMargin() + item.getBundleMargin());
		offline.setBundlePrice(offline.getBundlePrice() + item.getBundlePrice());
		offline.setBundleQuantity(offline.getBundleQuantity() + item.getBundleQuantity());
		offline.setCustomerBundleOffer(offline.getCustomerBundleOffer() + item.getCustomerBundleOffer());
		offline.setCustomerSingleOffer(offline.getCustomerSingleOffer() + item.getCustomerSingleOffer());
		// offline.setDiscount(offli);
		// offline.setExtraBillDiscount(offline.getExtraBillDiscount() + item.gete);
		offline.setGstAmount(offline.getGstAmount() + item.getGstAmount());
		// offline.setGstPercent(offline.getGstPercent() +);
		offline.setMarginPercent(offline.getMarginPercent() + item.getMarginPercent());
		offline.setMrp(offline.getMrp() + item.getMrp());
		offline.setOfferPercent(offline.getOfferPercent() + item.getOfferPercent());
		offline.setOldPrice(offline.getOldPrice() + offline.getOldPrice());
		offline.setPayableAmount(offline.getPayableAmount() + (int) item.getUnitSellingPrice());
		offline.setProductQuantity(offline.getProductQuantity() + item.getQuantity());
		offline.setPurchasePrice(offline.getPurchasePrice() + item.getPurchasePrice());
		offline.setSellingPrice(offline.getSellingPrice() + item.getSellingPrice());
		offline.setTotalPrice(offline.getTotalPrice() + (int) item.getUnitSellingPrice());
		offline.setUnitSellingPrice(offline.getUnitSellingPrice() + item.getUnitSellingPrice());
		boolean update = offlineService.updateOffline(offline);
		if (update) {
			offlineProductList.setBundleMargin(offlineProductList.getBundleMargin() + item.getBundleMargin());
			offlineProductList.setBundlePrice(offlineProductList.getBundlePrice() + item.getBundlePrice());
			offlineProductList.setBundleQuantity(offlineProductList.getBundleQuantity() + item.getBundleQuantity());
			offlineProductList.setCustomerBundleOffer(
					offlineProductList.getCustomerBundleOffer() + item.getCustomerBundleOffer());
			offlineProductList.setCustomerSingleOffer(
					offlineProductList.getCustomerSingleOffer() + item.getCustomerSingleOffer());
			offlineProductList.setPrice(offlineProductList.getPrice() + item.getUnitSellingPrice());
			offlineProductList.setOldPrice(offlineProductList.getOldPrice() + item.getOldPrice());
			offlineProductList.setTotalPrice(offlineProductList.getTotalPrice() + item.getUnitSellingPrice());
			offlineProductList.setGstPercent(offlineProductList.getGstPercent() + item.getGstPercent());
			offlineProductList.setGstAmount(offlineProductList.getGstAmount() + item.getGstAmount());
			offlineProductList.setUnitQuantity(offlineProductList.getUnitQuantity() + 1);
			offlineProductList.setSoldSlotStock(offlineProductList.getSoldSlotStock() - 1);
			offlineProductList.setMarginPercent(offlineProductList.getMarginPercent() + item.getMarginPercent());
			offlineProductList.setMrp(offlineProductList.getMrp() + item.getMrp());
			offlineProductList
					.setUnitSellingPrice(offlineProductList.getUnitSellingPrice() + item.getUnitSellingPrice());
			offlineProductList.setPurchasePrice(offlineProductList.getPurchasePrice() + item.getPurchasePrice());
			offlineProductList.setBundlePrice(offlineProductList.getBundlePrice() + item.getBundlePrice());

		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("get/byshopidarray/{shopId}/{cashierId}")
	public ResponseEntity<Offline> getAllBillByShopId2(@PathVariable("shopId") String shopId,
			@PathVariable("cashierId") String cashierId) {
		Offline offline = offlineService.getAllBillByShopId(shopId, cashierId);
		if (null != offline) {
			int billingId = offline.getBillingId();
			List<OfflineProductList> offlineArrey = new ArrayList<OfflineProductList>();

			offlineArrey = offlineProductListService.getAllProductByCartId(billingId);
			if (offlineArrey.size() > 0) {
				offline.setOfflineProductList(offlineArrey);
			} else {
				offline.setTotalPrice(0);
				offline.setPayableAmount(0);
				offline.setSellingPrice(0);
				offline.setOldPrice(0);
				offline.setMrp(0);
				offlineService.updateOffline(offline);
			}
		}
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("get/alldeactive/byshopId/{shopId}")
	public ResponseEntity<List<Offline>> getDeactiveByShopId(@PathVariable("shopId") String shopId) {
		List<Offline> offlineList = offlineService.getDeactiveByShopId(shopId);
		if (null != offlineList && offlineList.size() > 0) {
			for (int i = 0; i < offlineList.size(); i++) {
				int offlineCartId = offlineList.get(i).getBillingId();
				Offline offline = offlineList.get(i);
				List<OfflineProductList> offlineProductList = offlineProductListService
						.getAllProductByCartId(offlineCartId);
				offline.setOfflineProductList(offlineProductList);
			}
		}
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

//	@GetMapping("get/alldeactive/byshopId/{shopId}")
//	public ResponseEntity<List<Offline>> getDeactiveByShopId1(@PathVariable("shopId") String shopId) {
//		List<Offline> offlineList = offlineService.getDeactiveByShopId(shopId);
//		if (null != offlineList && offlineList.size() > 0) {
//			for (int i = 0; i < offlineList.size(); i++) {
//				int offlineCartId = offlineList.get(i).getBillingId();
//				Offline offline = offlineList.get(i);
//				List<OfflineProductList> offlineProductList = offlineProductListService
//						.getAllProductByCartId(offlineCartId);
//				offline.setOfflineProductList(offlineProductList);
//			}
//		}
//		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
//	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Offline>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Offline> offlineList = offlineService.getByShopId(shopId);
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

//	@GetMapping("get/bydecactivebyid/{billingId}")
//	public ResponseEntity<Offline> checkDecative(@PathVariable("billingId") int billingId) {
//		Offline offline = offlineService.checkDeactive(billingId);
//		offline.setActive(Boolean.FALSE);
//		String cashierId = offline.getCashierId();
//		float totalAmount = offline.getPayableAmount();
//		String shopId = offline.getShopId();
//		Cashier cashier = cashierService.getById(Integer.parseInt(cashierId));
//		
//		List<ItemList> localItemList = localData.getVariantData();
//		if (localItemList.size() <= 0) {
//			List<ItemList> itemList = itemListService.getByShopId(shopId);
//			localItemList = localData.setVariantData(itemList);
//		}
//
//		cashier.setTotalAmount(cashier.getTotalAmount() + (int) totalAmount);
//		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
//		for (int i = 0; i < offlineProductList.size(); i++) {
//			String productId = offlineProductList.get(i).getProductId();
//			boolean stockActiveInd = offlineProductList.get(i).isStockActiveInd();
//			if (stockActiveInd) {
//				OfflineProductList offlineProductList2 = offlineProductListService
//						.getByofflineCartIdAndProductId(billingId, productId, stockActiveInd);
//				ItemList item = itemListService.getById(Integer.parseInt(productId));
//				int slotNumber = offlineProductList2.getSlotNumber();
//				float quantity = offlineProductList2.getQuantity();
//				item.setStock(item.getStock() - (int) quantity);
//				for (int j = 0; j < localItemList.size(); j++) {
//					if(localItemList.get(j).getId() == Integer.parseInt(productId)) {
//						localItemList.get(j).setStock(localItemList.get(j).getStock() - (int) quantity);
//					}					
//				}
//
//				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,
//						Integer.parseInt(productId));
//				variantStock.setCurrentStock(variantStock.getCurrentStock() - (int) quantity);
//				variantStock.setSoldStock(variantStock.getSoldStock() + (int) quantity);
//				boolean variantStockUpdate = variantStockService.updateStock(variantStock);
//				boolean update = itemListService.updateItemList(item);
//
//				if (update && variantStockUpdate) {
//					if (variantStock.getCurrentStock() <= 0 || item.getStock() <= 0) {
//						variantStock.setActive(Boolean.FALSE);
//						item.setActive(Boolean.FALSE);
//					}
//				}
//			}
//		}
//
//		offlineService.updateOffline(offline);
//		cashierService.updateCashier(cashier);
//		localData.setVariantData(localItemList);
//		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
//	}
	
	@GetMapping("get/bydecactivebyid/{billingId}")
	public ResponseEntity<Offline> checkDecative5(@PathVariable("billingId") int billingId) throws IOException {
		Offline offline = offlineService.checkDeactive(billingId);
		offline.setActive(Boolean.FALSE);
		String cashierId = offline.getCashierId();
		float totalAmount = offline.getPayableAmount();
		String shopId = offline.getShopId();
		Cashier cashier = cashierService.getById(Integer.parseInt(cashierId));

		ObjectMapper objectMapper = new ObjectMapper();  
		HashMap<String, String> map = localData.getMap();
		
		if(map.size() <= 0) {
			List<ItemList> itemList = itemListService.getByShopId(shopId);
		for(int i=0; i< itemList.size(); i++) {
			 String stringData  = objectMapper.writeValueAsString(itemList.get(i));
			 map.put("prod_"+itemList.get(i).getId(), stringData);
		}
		}

		cashier.setTotalAmount(cashier.getTotalAmount() + (int) totalAmount);
		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
		for (int i = 0; i < offlineProductList.size(); i++) {
			String productId = offlineProductList.get(i).getProductId();
			boolean stockActiveInd = offlineProductList.get(i).isStockActiveInd();
			if (stockActiveInd) {
				OfflineProductList offlineProductList2 = offlineProductListService
						.getByofflineCartIdAndProductId(billingId, productId, stockActiveInd);
				ItemList item = itemListService.getById(Integer.parseInt(productId));
				int slotNumber = offlineProductList2.getSlotNumber();
				float quantity = offlineProductList2.getQuantity();
				item.setStock(item.getStock() - (int) quantity);
				String localDataToString = map.get("prod_" + productId);
				ItemList itemList = objectMapper.readValue(localDataToString, ItemList.class);
				itemList.setStock(itemList.getStock() -(int) quantity);
				localDataToString = objectMapper.writeValueAsString(itemList);
				map.put("prod_" + productId,localDataToString);

				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,
						Integer.parseInt(productId));
				variantStock.setCurrentStock(variantStock.getCurrentStock() - (int) quantity);
				variantStock.setSoldStock(variantStock.getSoldStock() + (int) quantity);
				boolean variantStockUpdate = variantStockService.updateStock(variantStock);
				boolean update = itemListService.updateItemList(item);

				if (update && variantStockUpdate) {
					if (variantStock.getCurrentStock() <= 0 || item.getStock() <= 0) {
						variantStock.setActive(Boolean.FALSE);
						item.setActive(Boolean.FALSE);
					}
				}
			}
		}

		offlineService.updateOffline(offline);
		cashierService.updateCashier(cashier);
		localData.setMap(map);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("get/shopid/billingid/{shopId}/{billingId}")
	public ResponseEntity<Offline> getByShopIdAndBillId(@PathVariable("shopId") String shopId,
			@PathVariable("billingId") int billingId) {
		Offline offline = offlineService.getByShopIdAndBillId(shopId, billingId);
//		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
//		offline.setOfflineProductList(offlineProductList);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("getofflinebyshopid/{shopId}")
	public ResponseEntity<List<Offline>> getByShopId1(@PathVariable("shopId") String shopId) {
		List<Offline> offlineList = offlineService.getAllOfflineByShopId(shopId);
		if (null != offlineList && offlineList.size() > 0) {
			for (int i = 0; i < offlineList.size(); i++) {
				int cartId = offlineList.get(i).getBillingId();
				List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(cartId);
				offlineList.get(i).setOfflineProductList(offlineProductList);
			}
		}
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("getbill/byshopid/username/{shopId}/{userName}")
	public ResponseEntity<List<Offline>> getAllBillByUserName(@PathVariable("shopId") String shopId,
			@PathVariable("userName") String userName) {
		List<Offline> offlineList = offlineService.getAllBillByUserName(shopId, userName);
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/byshopid/mobileno/{shopId}/{mobileNo}")
	public ResponseEntity<Offline> getByShopIdAndMobileNo(@PathVariable("shopId") String shopId,
			@PathVariable("mobileNo") String mobileNo) {
		Offline offline = offlineService.getByShopIdAndMobileNo(shopId, mobileNo);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("getby/shopid/{shopId}/{billingId}")
	public ResponseEntity<?> getByShopIdAndBillId3(@PathVariable("shopId") String shopId,
			@PathVariable("billingId") int billingId) {
		Map<String, String> response = new HashMap<String, String>();
		Offline offline = offlineService.getByShopIdAndBillId(shopId, billingId);
		if (null != offline) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", offline.toString());
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Given bill is not acive" + offline);
		}
		return ResponseEntity.ok().body(response);

	}

	@GetMapping("updateactive/{billingId}/{cashierId}")
	public ResponseEntity<Map<String, String>> getActive(@PathVariable("billingId") int billingId,
			@PathVariable("cashierId") String cashierId) {
		Map<String, String> response = new HashMap<String, String>();
		Offline offline = null;
		offline = offlineService.getByBillId(billingId);
		String shopId = offline.getShopId();
		List<Offline> offlineList = offlineService.getAllAmountByCashierId(shopId, cashierId);
		for (int i = 1; i < offlineList.size(); i++) {
			offlineList.get(i).setActive(Boolean.FALSE);
			boolean update1 = offlineService.updateOffline(offlineList.get(i));
		}
		offline.setActive(Boolean.TRUE);
		boolean update = offlineService.updateOffline(offline);
		if (update) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Given bill is acive");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Given bill is not acive");
		}

		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create/direct")
	ResponseEntity<Map<String, String>> createOfflineDirect(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.PRODUCT_NAME)
				&& null != json.get(ServiceConstants.BRAND_NAME) && null != json.get(ServiceConstants.SHOP_NAME)
				&& null != json.get(ServiceConstants.QUANTITY) && null != json.get(ServiceConstants.TOTAL_PRICE)
				&& null != json.get(ServiceConstants.PRICE) && null != json.get(ServiceConstants.OFFLINE_CART_ID)
				&& null != json.get(ServiceConstants.OLD_PRICE) && null != json.get(ServiceConstants.CASHIER_ID)
				&& null != json.get(ServiceConstants.STOCK_ACTIVE_IND)) {
			Calculation calculation = new Calculation();
			String shopId = json.get(ServiceConstants.SHOP_ID), productName = json.get(ServiceConstants.PRODUCT_NAME),
					brandName = json.get(ServiceConstants.BRAND_NAME), shopName = json.get(ServiceConstants.SHOP_NAME),
					productId = null, batchNumber = null, cashierId = json.get(ServiceConstants.CASHIER_ID);
			boolean stockActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.STOCK_ACTIVE_IND));
			ItemList product = null;

			float totalAmount = Float.parseFloat(json.get(ServiceConstants.TOTAL_PRICE)),
					sellingPrice = Float.parseFloat(json.get(ServiceConstants.PRICE)),
					productQuantity = Float.parseFloat(json.get(ServiceConstants.QUANTITY)), discount = 0,
					marginPercent = 0, oldPrice = Float.parseFloat(json.get(ServiceConstants.OLD_PRICE)), gstAmount = 0,
					bundleMargin = 0, mrp = 0, unitSellingPrice = 0, purchasePrice = 0, customerSingleOffer = 0,
					customerBundleOffer = 0;
			int offlineCartId = Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID)), gstPercent = 0,
					offerPercent = 0;
			Offline offline = null;
			boolean offlinecreate = false;

			OfflineProductList offlineProductList = null;

			offline = offlineService.getAllBillByShopId(shopId, cashierId);
			if (null != offline) {

				offlineProductList = new OfflineProductList(shopId, productName, brandName);

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
				offlineProductList.setOldPrice(oldPrice);
//						offlineProductList.setUnitQuantity(unitQuantity);
				offlineProductList.setCashierId(cashierId);

				if (null != json.get(ServiceConstants.IGST_AMOUNT)) {
					float igstAmount = Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT));
					offline.setIgstAmount(igstAmount);
				}

				if (null != json.get(ServiceConstants.IGST_AMOUNT)) {
					float utgstAmount = Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT));
					offline.setUtgstAmount(utgstAmount);
				}

				if (null != json.get(ServiceConstants.MARGIN_PERCENT)) {
					marginPercent = Float.parseFloat(json.get(ServiceConstants.MARGIN_PERCENT));
					offline.setMarginPercent(marginPercent);
				}

				if (null != json.get(ServiceConstants.BUNDLE_MARGIN)) {
					bundleMargin = Float.parseFloat(json.get(ServiceConstants.BUNDLE_MARGIN));
					offline.setBundleMargin(bundleMargin);
				}

				if (null != json.get(ServiceConstants.MRP)) {
					mrp = Float.parseFloat(json.get(ServiceConstants.MRP));
					offline.setMrp(mrp);
				}

				if (null != json.get(ServiceConstants.UNIT_SELLING_PRICE)) {
					unitSellingPrice = Float.parseFloat(json.get(ServiceConstants.UNIT_SELLING_PRICE));
					offline.setUnitSellingPrice(unitSellingPrice);
				}

				if (null != json.get(ServiceConstants.PURCHASE_PRICE)) {
					purchasePrice = Float.parseFloat(json.get(ServiceConstants.PURCHASE_PRICE));
					offline.setPurchasePrice(purchasePrice);
				}

				if (null != json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
					customerSingleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER));
					offline.setCustomerSingleOffer(customerSingleOffer);
				}

				if (null != json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
					customerBundleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER));
					offline.setCustomerBundleOffer(customerBundleOffer);
				}

				if (null != json.get(ServiceConstants.BUNDLE_QUANTITY)) {
					float bundleQuantity = Float.parseFloat(json.get(ServiceConstants.BUNDLE_QUANTITY));
					offline.setBundleQuantity(bundleQuantity);
				}
				if (null != json.get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
					float extraBillDiscount = Float.parseFloat(json.get(ServiceConstants.EXTRA_BILL_DISCOUNT));
					offline.setExtraBillDiscount(extraBillDiscount);
				}

				if (null != json.get(ServiceConstants.BUNDLE_PRICE)) {
					float bundlePrice = Float.parseFloat(json.get(ServiceConstants.BUNDLE_PRICE));
					offline.setBundlePrice(bundlePrice);
				}

				if (null != json.get(ServiceConstants.BARCODE)) {
					String barcode = json.get(ServiceConstants.BARCODE);
					offline.setBarcode(barcode);
				}

				if (null != json.get(ServiceConstants.DESCRIPTION)) {
					String description = json.get(ServiceConstants.DESCRIPTION);
					offline.setDescription(description);
				}

				if (null != json.get(ServiceConstants.SGST_AMOUNT)) {
					float sgstAmount = Float.parseFloat(json.get(ServiceConstants.SGST_AMOUNT));
					offline.setSgstAmount(sgstAmount);
				}

				if (null != json.get(ServiceConstants.CGST_AMOUNT)) {
					float cgstAmount = Float.parseFloat(json.get(ServiceConstants.CGST_AMOUNT));
					offline.setCgstAmount(cgstAmount);
				}

				if (null != json.get(ServiceConstants.GST_TYPE)) {
					int gstType = Integer.parseInt(json.get(ServiceConstants.GST_TYPE));
					offline.setGstType(gstType);
				}

				if (null != json.get(ServiceConstants.PRODUCT_ID)) {
					productId = json.get(ServiceConstants.PRODUCT_ID);
					offlineProductList.setProductId(productId);
				}

				if (null != json.get(ServiceConstants.BARCODE)) {
					String barcode = json.get(ServiceConstants.BARCODE);
					offlineProductList.setBarcode(barcode);
				}
				if (null != json.get(ServiceConstants.DISCOUNT)) {
					discount = Float.parseFloat(json.get(ServiceConstants.DISCOUNT));
					offlineProductList.setDiscount(discount);
				}

				if (null != json.get(ServiceConstants.GST_PERCENT)) {
					gstPercent = Integer.parseInt((json.get(ServiceConstants.GST_PERCENT)));
					offlineProductList.setGstPercent(gstPercent);
				}

				if (null != json.get(ServiceConstants.GST_AMOUNT)) {
					gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
					offlineProductList.setGstAmount(gstAmount);
				}

				if (null != json.get(ServiceConstants.OFFER_PERCENT)) {
					offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
					offlineProductList.setOfferPercent(offerPercent);
				}

				if (null != json.get(ServiceConstants.MEASUREMENT)) {
					String measurement = json.get(ServiceConstants.MEASUREMENT);
					offlineProductList.setMeasurement(measurement);
				}

				if (null != json.get(ServiceConstants.IGST_AMOUNT)) {
					float igstAmount = Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT));
					offlineProductList.setIgstAmount(igstAmount);
				}

				if (null != json.get(ServiceConstants.UTGST_AMOUNT)) {
					float utgstAmount = Float.parseFloat(json.get(ServiceConstants.UTGST_AMOUNT));
					offlineProductList.setUtgstAmount(utgstAmount);
				}

				if (null != json.get(ServiceConstants.MARGIN_PERCENT)) {
					marginPercent = Float.parseFloat(json.get(ServiceConstants.MARGIN_PERCENT));
					offlineProductList.setMarginPercent(marginPercent);
				}

				if (null != json.get(ServiceConstants.BUNDLE_MARGIN)) {
					bundleMargin = Float.parseFloat(json.get(ServiceConstants.BUNDLE_MARGIN));
					offlineProductList.setBundleMargin(bundleMargin);
				}

				if (null != json.get(ServiceConstants.MRP)) {
					mrp = Float.parseFloat(json.get(ServiceConstants.MRP));
					offlineProductList.setMrp(mrp);
				}

				if (null != json.get(ServiceConstants.UNIT_SELLING_PRICE)) {
					unitSellingPrice = Float.parseFloat(json.get(ServiceConstants.UNIT_SELLING_PRICE));
					offlineProductList.setUnitSellingPrice(unitSellingPrice);
				}

				if (null != json.get(ServiceConstants.PURCHASE_PRICE)) {
					purchasePrice = Float.parseFloat(json.get(ServiceConstants.PURCHASE_PRICE));
					offlineProductList.setPurchasePrice(purchasePrice);
				}

				if (null != json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
					customerSingleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER));
					offlineProductList.setCustomerSingleOffer(customerSingleOffer);
				}

				if (null != json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
					customerBundleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER));
					offlineProductList.setCustomerBundleOffer(customerBundleOffer);
				}

				if (null != json.get(ServiceConstants.BUNDLE_QUANTITY)) {
					float bundleQuantity = Float.parseFloat(json.get(ServiceConstants.BUNDLE_QUANTITY));
					offlineProductList.setBundleQuantity(bundleQuantity);
				}

				if (null != json.get(ServiceConstants.STOCK)) {
					int stock = Integer.parseInt(json.get(ServiceConstants.STOCK));
					offlineProductList.setStock(stock);
				}

				if (null != json.get(ServiceConstants.BUNDLE_PRICE)) {
					float bundlePrice = Float.parseFloat(json.get(ServiceConstants.BUNDLE_PRICE));
					offlineProductList.setBundlePrice(bundlePrice);
				}

				if (null != json.get(ServiceConstants.SGST_AMOUNT)) {
					float sgstAmount = Float.parseFloat(json.get(ServiceConstants.SGST_AMOUNT));
					offlineProductList.setSgstAmount(sgstAmount);
				}

				if (null != json.get(ServiceConstants.CGST_AMOUNT)) {
					float cgstAmount = Float.parseFloat(json.get(ServiceConstants.CGST_AMOUNT));
					offlineProductList.setCgstAmount(cgstAmount);
				}

				if (null != json.get(ServiceConstants.GST_TYPE)) {
					int gstType = Integer.parseInt(json.get(ServiceConstants.GST_TYPE));
					offlineProductList.setGstType(gstType);
				}

				if (null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
					Date dateOfExpire = null;
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dateOfExpire = formater.parse(json.get(ServiceConstants.DATE_OF_EXPIRE));
						offlineProductList.setDateOfExpire(dateOfExpire);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (null != json.get(ServiceConstants.BATCH_NUMBER)) {
					batchNumber = json.get(ServiceConstants.BATCH_NUMBER);
					offlineProductList.setBatchNumber(batchNumber);
				}

				float offlineGstAmount = offline.getGstAmount() + gstAmount,
						offlineDiscount = offline.getDiscount() + discount,
						offlinePrice = offline.getSellingPrice() + sellingPrice,
						offlineOldPrice = offline.getOldPrice() + oldPrice,
						offlineTotalAmount = offline.getTotalPrice() + totalAmount,
						// second
						offlineMarginPercent = offline.getMarginPercent() + marginPercent,
						offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
						offlineMrp = offline.getMrp() + mrp,
						offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
						offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
						offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
						offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

				int payableAmount = (int) Math.ceil(offlineTotalAmount);

				offline.setGstAmount(offlineGstAmount);
				offline.setDiscount(offlineDiscount);
				offline.setSellingPrice(offlinePrice);
				offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
				offline.setPayableAmount(payableAmount);
				offline.setOldPrice(offlineOldPrice);
				// second
				offline.setMarginPercent(offlineMarginPercent);
				offline.setBundleMargin(offlineBundleMargin);
				offline.setMrp(offlineMrp);
				;
				offline.setUnitSellingPrice(offlineUnitSellingPrice);
				offline.setPurchasePrice(offlinePurchasePrice);
				offline.setCustomerSingleOffer(offlineSingleOffer);
				offline.setCustomerBundleOffer(offlineBundleOffer);

			}
			boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
			if (result) {
				boolean result1 = offlineService.updateOffline(offline);
				if (result1) {
					response.put("status", Strings.EMPTY + result1);
					response.put("description", "ProductList updated successfully with given Shop Id");

				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Cart has not been updated.");
					response.put("cause", "cart_update_faield");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Cart has not been updated.");
				response.put("cause", "product_create_faield");
			}
//				}

//			} else {
//				response.put("status", Boolean.FALSE.toString());
//				response.put("description", "Cart does not exist with given cart ID.");
//				response.put("cause", "cart_not_found");
//			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product creation failed due to less data.");
			response.put("cause", "parameter_faield");
		}

		return ResponseEntity.ok().body(response);

	}

	@SuppressWarnings({})
	@PostMapping("/create/main")
//	ResponseEntity<Map<String, String>> createOfflineForCheck(@Valid @RequestBody ArrayList<Map> data,
	ResponseEntity<Map<String, String>> createOfflineForCheck1(@Valid @RequestBody Map<String, ArrayList<Map>> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		ArrayList<Map> data = json.get("variant");
		ArrayList<Map> cartData = json.get("offline");
		float extraBillDiscount = Float
				.parseFloat(cartData.get(0).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());

		String paymentType = cartData.get(0).get(ServiceConstants.PAYMENT_TYPE).toString();

		if (null != data.get(0).get(ServiceConstants.OFFLINE_CART_ID)) {
			Offline offline = null;
			offline = offlineService.getAllBillByShopId(data.get(0).get(ServiceConstants.SHOP_ID).toString(),
					data.get(0).get(ServiceConstants.CASHIER_ID).toString());
			offline.setGstAmount(0);
			offline.setDiscount(0);
			offline.setSellingPrice(0);
			offline.setExtraBillDiscount(extraBillDiscount);
			offline.setTotalPrice(0);
			offline.setPayableAmount(0);
			offline.setPaymentType(paymentType);
			offline.setOldPrice(0);
			// second
			offline.setMarginPercent(0);
			offline.setBundleMargin(0);
			offline.setMrp(0);
			offline.setUnitSellingPrice(0);
			offline.setPurchasePrice(0);
			offline.setCustomerSingleOffer(0);
			offline.setCustomerBundleOffer(0);

			for (int i = 0; i < data.size(); i++) {
				log.info("Request to update user: {}", data.get(i).get(ServiceConstants.ID));
				// log.info("Request to create user: {}",
				// data.get(i)..get(ServiceConstants.SHOP_ID));
				if (null != data.get(i).get(ServiceConstants.SHOP_ID)
						&& null != data.get(i).get(ServiceConstants.PRODUCT_NAME)
						&& null != data.get(i).get(ServiceConstants.BRAND_NAME)
						&& null != data.get(i).get(ServiceConstants.SHOP_NAME)
						&& null != data.get(i).get(ServiceConstants.QUANTITY)
						&& null != data.get(i).get(ServiceConstants.TOTAL_PRICE)
						&& null != data.get(i).get(ServiceConstants.PRICE)
						&& null != data.get(i).get(ServiceConstants.OFFLINE_CART_ID)
						&& null != data.get(i).get(ServiceConstants.OLD_PRICE)
						&& null != data.get(i).get(ServiceConstants.CASHIER_ID)
						&& null != data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND)) {
					String shopId = data.get(i).get(ServiceConstants.SHOP_ID).toString(),
							productName = data.get(i).get(ServiceConstants.PRODUCT_NAME).toString(),
							brandName = data.get(i).get(ServiceConstants.BRAND_NAME).toString(),
							shopName = data.get(i).get(ServiceConstants.SHOP_NAME).toString(), productId = null,
							batchNumber = null, cashierId = data.get(i).get(ServiceConstants.CASHIER_ID).toString();
					boolean stockActiveInd = Boolean
							.parseBoolean(data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND).toString());
					ItemList product = null;

					float totalAmount = Float.parseFloat(data.get(i).get(ServiceConstants.TOTAL_PRICE).toString()),
							sellingPrice = Float.parseFloat(data.get(i).get(ServiceConstants.PRICE).toString()),
							productQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.QUANTITY).toString()),
							discount = 0, marginPercent = 0,
							oldPrice = Float.parseFloat(data.get(i).get(ServiceConstants.OLD_PRICE).toString()),
							gstAmount = 0, bundleMargin = 0, mrp = 0, unitSellingPrice = 0, purchasePrice = 0,
							customerSingleOffer = 0, customerBundleOffer = 0,
							unitQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.UNIT_QUANTITY).toString());
					int offlineCartId = Integer.parseInt(data.get(i).get(ServiceConstants.OFFLINE_CART_ID).toString()),
							gstPercent = 0, offerPercent = 0,
							slotNumber = Integer.parseInt(data.get(i).get(ServiceConstants.SLOT_NUMBER).toString()),
							varientIdId = Integer.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString());
					ItemList itemList = itemListService.getById(varientIdId);

					boolean offlinecreate = false;
Calculation calculation = new Calculation();
					OfflineProductList offlineProductList = null;
					if (null != offline) {
						if (stockActiveInd) {
							VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,
									varientIdId);
							if (itemList.getStock() >= productQuantity
									&& variantStock.getCurrentStock() >= productQuantity) {
								if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
									productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
									product = itemListService.getById(Integer.parseInt(productId));

									Boolean exists = offlineProductListService.checkExit(offlineCartId, productId,
											cashierId, stockActiveInd);

									if (exists) {
										OfflineProductList offlineProductList2 = offlineProductListService
												.getByofflineCartIdAndProductId(offlineCartId, productId,
														stockActiveInd);
										offlineProductList2.setUnitQuantity(
												offlineProductList2.getUnitQuantity() + productQuantity);

										offlineProductList2.setBrandName(brandName);
										offlineProductList2.setOfflineCartId(offlineCartId);
										offlineProductList2.setPrice(sellingPrice);
										offlineProductList2.setProductName(productName);
										offlineProductList2.setQuantity(productQuantity);
										offlineProductList2.setShopId(shopId);
										offlineProductList2.setTotalPrice(totalAmount);
										offlineProductList2.setShopName(shopName);
										offlineProductList2.setActive(Boolean.TRUE);
										offlineProductList2.setCreatedOn(new Date());
										offlineProductList2.setDeleted(Boolean.FALSE);
										offlineProductList2.setStockActiveInd(stockActiveInd);
										offlineProductList2.setOldPrice(oldPrice);
										offlineProductList2.setUnitQuantity(unitQuantity);
										offlineProductList2.setCashierId(cashierId);
										offlineProductList2.setBarcode(itemList.getBarcode());
										offlineProductList2.setSlotNumber(slotNumber);
//								if(null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
//									offlineProductList.setPaymentType(paymentType);
//								}

										if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
											float igstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
											offline.setIgstAmount(igstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
											float utgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
											offline.setUtgstAmount(utgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
											marginPercent = Float.parseFloat(
													data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
											offline.setMarginPercent(marginPercent);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
											bundleMargin = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
											offline.setBundleMargin(bundleMargin);
										}

										if (null != data.get(i).get(ServiceConstants.MRP)) {
											mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
											offline.setMrp(mrp);
										}

										if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
											unitSellingPrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
											offline.setUnitSellingPrice(unitSellingPrice);
										}

										if (null != cartData.get(0).get(ServiceConstants.IS_PERCENT_DISCOUNT)) {
											boolean isPercentDiscount = Boolean.parseBoolean(
													cartData.get(0).get(ServiceConstants.IS_PERCENT_DISCOUNT).toString());
											offline.setPercentDiscount(isPercentDiscount);
										}
										
										if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
											purchasePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
											offline.setPurchasePrice(purchasePrice);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
											customerSingleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
											offline.setCustomerSingleOffer(customerSingleOffer);
										}
										if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
											offline.setExtraBillDiscount(Float.parseFloat(
													data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString()));

										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
											customerBundleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
											offline.setCustomerBundleOffer(customerBundleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
											float bundleQuantity = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
											offline.setBundleQuantity(bundleQuantity);
										}
										if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
											extraBillDiscount = Float.parseFloat(
													data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
											offline.setExtraBillDiscount(extraBillDiscount);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
											float bundlePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
											offline.setBundlePrice(bundlePrice);
										}

										if (null != data.get(i).get(ServiceConstants.BARCODE)) {
											// String barcode = data.get(i).get(ServiceConstants.BARCODE);
											offline.setBarcode(itemList.getBarcode());
										}

										if (null != data.get(i).get(ServiceConstants.BARCODE)) {
											// String barcode = data.get(i).get(ServiceConstants.BARCODE);
											offlineProductList2.setBarcode(itemList.getBarcode());
										}

										if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
											String description = data.get(i).get(ServiceConstants.DESCRIPTION)
													.toString();
											offline.setDescription(description);
										}

										if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
											float sgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
											offline.setSgstAmount(sgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
											float cgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
											offline.setCgstAmount(cgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
											int gstType = Integer
													.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
											offline.setGstType(gstType);
										}

										if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
											productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
											offlineProductList2.setProductId(productId);
										}
										if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
											discount = Float
													.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
											offlineProductList2.setDiscount(discount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
											gstPercent = Integer.parseInt(
													(data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
											offlineProductList2.setGstPercent(gstPercent);
										}

										if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
											gstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
											offlineProductList2.setGstAmount(gstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
											offerPercent = Integer.parseInt(
													data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
											offlineProductList2.setOfferPercent(offerPercent);
										}

										if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
											String measurement = data.get(i).get(ServiceConstants.MEASUREMENT)
													.toString();
											offlineProductList2.setMeasurement(measurement);
										}

										if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
											float igstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
											offlineProductList2.setIgstAmount(igstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
											float utgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
											offlineProductList2.setUtgstAmount(utgstAmount);
										}

//								if (null != data.get(i).get(ServiceConstants.CASHIER_ID)) {
//									String cashierId = data.get(i).get(ServiceConstants.CASHIER_ID);
//									offline.setCashierId(cashierId);
//								}

										if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
											marginPercent = Float.parseFloat(
													data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
											offlineProductList2.setMarginPercent(marginPercent);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
											bundleMargin = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
											offlineProductList2.setBundleMargin(bundleMargin);
										}

										if (null != data.get(i).get(ServiceConstants.MRP)) {
											mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
											offlineProductList2.setMrp(mrp);
										}

										if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
											unitSellingPrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
											offlineProductList2.setUnitSellingPrice(unitSellingPrice);
										}

										if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
											purchasePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
											offlineProductList2.setPurchasePrice(purchasePrice);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
											customerSingleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
											offlineProductList2.setCustomerSingleOffer(customerSingleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
											customerBundleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
											offlineProductList2.setCustomerBundleOffer(customerBundleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
											float bundleQuantity = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
											offlineProductList2.setBundleQuantity(bundleQuantity);
										}

										if (null != data.get(i).get(ServiceConstants.STOCK)) {
											int stock = Integer
													.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
											offlineProductList2.setStock(stock);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
											float bundlePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
											offlineProductList2.setBundlePrice(bundlePrice);
										}

										if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
											float sgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
											offlineProductList2.setSgstAmount(sgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
											float cgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
											offlineProductList2.setCgstAmount(cgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
											int gstType = Integer
													.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
											offlineProductList2.setGstType(gstType);
										}

										if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
											Date dateOfExpire = null;
											SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
											try {
												dateOfExpire = formater.parse(
														data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
												offlineProductList2.setDateOfExpire(dateOfExpire);
											} catch (ParseException e) {
												e.printStackTrace();
											}
										}
										if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
											batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
											offlineProductList2.setBatchNumber(batchNumber);
										}

										float offlineGstAmount = offline.getGstAmount() + gstAmount,
												offlineDiscount = offline.getDiscount() + discount,
												offlinePrice = offline.getSellingPrice() + sellingPrice,
												offlineOldPrice = offline.getOldPrice() + oldPrice,
												offlineTotalAmount = offline.getTotalPrice() + totalAmount,
												// second
												offlineMarginPercent = offline.getMarginPercent() + marginPercent,
												offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
												offlineMrp = offline.getMrp() + mrp,
												offlineUnitSellingPrice = offline.getUnitSellingPrice()
														+ unitSellingPrice,
												offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
												offlineSingleOffer = offline.getCustomerSingleOffer()
														+ customerSingleOffer,
												offlineBundleOffer = offline.getCustomerBundleOffer()
														+ customerBundleOffer;

										int payableAmount = (int) Math.ceil(offlineTotalAmount - extraBillDiscount);

										offline.setGstAmount(offlineGstAmount);
										offline.setDiscount(offlineDiscount);
										offline.setSellingPrice(offlinePrice);
										offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
										offline.setPayableAmount(payableAmount);
										offline.setOldPrice(offlineOldPrice);
										// second
										offline.setMarginPercent(offlineMarginPercent);
										offline.setBundleMargin(offlineBundleMargin);
										offline.setMrp(offlineMrp);
										offline.setUnitSellingPrice(offlineUnitSellingPrice);
										offline.setPurchasePrice(offlinePurchasePrice);
										offline.setCustomerSingleOffer(offlineSingleOffer);
										offline.setCustomerBundleOffer(offlineBundleOffer);
										offlineProductListService.updateOfflineProductList(offlineProductList2);
										boolean result1 = offlineService.updateOffline(offline);
										response.put("status", Boolean.TRUE.toString());
										response.put("description", "Product allready exists with given product name.");
										response.put("cause", "product_exists");
									} else {
										offlineProductList = new OfflineProductList(shopId, productName, brandName);

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
										offlineProductList.setOldPrice(oldPrice);
										offlineProductList.setUnitQuantity(unitQuantity);
										offlineProductList.setCashierId(cashierId);
										offlineProductList.setSlotNumber(slotNumber);
										offlineProductList
												.setBarcode(data.get(i).get(ServiceConstants.BARCODE).toString());
//								if(null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
//									offlineProductList.setPaymentType(paymentType);
//								}

										if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
											float igstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
											offline.setIgstAmount(igstAmount);
										}

										if (null != cartData.get(0).get(ServiceConstants.IS_PERCENT_DISCOUNT)) {
											boolean isPercentDiscount = Boolean.parseBoolean(
													cartData.get(0).get(ServiceConstants.IS_PERCENT_DISCOUNT).toString());
											offline.setPercentDiscount(isPercentDiscount);
										}

										if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
											marginPercent = Float.parseFloat(
													data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
											offline.setMarginPercent(marginPercent);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
											bundleMargin = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
											offline.setBundleMargin(bundleMargin);
										}

										if (null != data.get(i).get(ServiceConstants.MRP)) {
											mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
											offline.setMrp(mrp);
										}

										if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
											unitSellingPrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
											offline.setUnitSellingPrice(unitSellingPrice);
										}

										if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
											purchasePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
											offline.setPurchasePrice(purchasePrice);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
											customerSingleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
											offline.setCustomerSingleOffer(customerSingleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
											customerBundleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
											offline.setCustomerBundleOffer(customerBundleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
											float bundleQuantity = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
											offline.setBundleQuantity(bundleQuantity);
										}
										if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
											extraBillDiscount = Float.parseFloat(
													data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
											offline.setExtraBillDiscount(extraBillDiscount);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
											float bundlePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
											offline.setBundlePrice(bundlePrice);
										}

										if (null != data.get(i).get(ServiceConstants.BARCODE)) {
											// String barcode = data.get(i).get(ServiceConstants.BARCODE);
											offline.setBarcode(itemList.getBarcode());
										}

										if (null != data.get(i).get(ServiceConstants.BARCODE)) {
											// String barcode = data.get(i).get(ServiceConstants.BARCODE);
											offlineProductList.setBarcode(itemList.getBarcode());
										}

										if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
											String description = data.get(i).get(ServiceConstants.DESCRIPTION)
													.toString();
											offline.setDescription(description);
										}

										if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
											float sgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
											offline.setSgstAmount(sgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
											float cgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
											offline.setCgstAmount(cgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
											int gstType = Integer
													.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
											offline.setGstType(gstType);
										}

										if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
											productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
											offlineProductList.setProductId(productId);
										}
										if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
											discount = Float
													.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
											offlineProductList.setDiscount(discount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
											gstPercent = Integer.parseInt(
													(data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
											offlineProductList.setGstPercent(gstPercent);
										}

										if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
											gstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
											offlineProductList.setGstAmount(gstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
											offerPercent = Integer.parseInt(
													data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
											offlineProductList.setOfferPercent(offerPercent);
										}

										if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
											String measurement = data.get(i).get(ServiceConstants.MEASUREMENT)
													.toString();
											offlineProductList.setMeasurement(measurement);
										}

										if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
											float igstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
											offlineProductList.setIgstAmount(igstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
											float utgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
											offlineProductList.setUtgstAmount(utgstAmount);
										}

//								if (null != data.get(i).get(ServiceConstants.CASHIER_ID)) {
//									String cashierId = data.get(i).get(ServiceConstants.CASHIER_ID);
//									offline.setCashierId(cashierId);
//								}

										if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
											marginPercent = Float.parseFloat(
													data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
											offlineProductList.setMarginPercent(marginPercent);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
											bundleMargin = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
											offlineProductList.setBundleMargin(bundleMargin);
										}

										if (null != data.get(i).get(ServiceConstants.MRP)) {
											mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
											offlineProductList.setMrp(mrp);
										}

										if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
											unitSellingPrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
											offlineProductList.setUnitSellingPrice(unitSellingPrice);
										}

										if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
											purchasePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
											offlineProductList.setPurchasePrice(purchasePrice);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
											customerSingleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
											offlineProductList.setCustomerSingleOffer(customerSingleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
											customerBundleOffer = Float.parseFloat(
													data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
											offlineProductList.setCustomerBundleOffer(customerBundleOffer);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
											float bundleQuantity = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
											offlineProductList.setBundleQuantity(bundleQuantity);
										}

										if (null != data.get(i).get(ServiceConstants.STOCK)) {
											int stock = Integer
													.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
											offlineProductList.setStock(stock);
										}

										if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
											float bundlePrice = Float.parseFloat(
													data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
											offlineProductList.setBundlePrice(bundlePrice);
										}

										if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
											float sgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
											offlineProductList.setSgstAmount(sgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
											float cgstAmount = Float.parseFloat(
													data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
											offlineProductList.setCgstAmount(cgstAmount);
										}

										if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
											int gstType = Integer
													.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
											offlineProductList.setGstType(gstType);
										}

										if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
											Date dateOfExpire = null;
											SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
											try {
												dateOfExpire = formater.parse(
														data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
												offlineProductList.setDateOfExpire(dateOfExpire);
											} catch (ParseException e) {
												e.printStackTrace();
											}
										}
										if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
											batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
											offlineProductList.setBatchNumber(batchNumber);
										}

										float offlineGstAmount = offline.getGstAmount() + gstAmount,
												offlineDiscount = offline.getDiscount() + discount,
												offlinePrice = offline.getSellingPrice() + sellingPrice,
												offlineOldPrice = offline.getOldPrice() + oldPrice,
												offlineTotalAmount = offline.getTotalPrice() + totalAmount,
												// second
												offlineMarginPercent = offline.getMarginPercent() + marginPercent,
												offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
												offlineMrp = offline.getMrp() + mrp,
												offlineUnitSellingPrice = offline.getUnitSellingPrice()
														+ unitSellingPrice,
												offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
												offlineSingleOffer = offline.getCustomerSingleOffer()
														+ customerSingleOffer,
												offlineBundleOffer = offline.getCustomerBundleOffer()
														+ customerBundleOffer;

										int payableAmount = (int) Math.ceil(offlineTotalAmount - extraBillDiscount);

										offline.setGstAmount(offlineGstAmount);
										offline.setDiscount(offlineDiscount);
										offline.setSellingPrice(offlinePrice);
										offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
										offline.setPayableAmount(payableAmount);
										offline.setOldPrice(offlineOldPrice);
										// second
										offline.setMarginPercent(offlineMarginPercent);
										offline.setBundleMargin(offlineBundleMargin);
										offline.setMrp(offlineMrp);
										offline.setUnitSellingPrice(offlineUnitSellingPrice);
										offline.setPurchasePrice(offlinePurchasePrice);
										offline.setCustomerSingleOffer(offlineSingleOffer);
										offline.setCustomerBundleOffer(offlineBundleOffer);

										{
											boolean result = offlineProductListService
													.offlineProductCreate(offlineProductList);
											if (result) {
												boolean result1 = offlineService.updateOffline(offline);
												if (result1) {
													response.put("status", Strings.EMPTY + result1);
													response.put("description",
															"ProductList updated successfully with given Shop Id");

												} else {
													response.put("status", Boolean.FALSE.toString());
													response.put("description", "Cart has not been updated.");
													response.put("cause", "cart_update_faield");
												}
											} else {
												response.put("status", Boolean.FALSE.toString());
												response.put("description", "Cart has not been updated.");
												response.put("cause", "product_create_faield");
											}
										}
									}
								} else {

									boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
									if (result) {
										boolean result1 = offlineService.updateOffline(offline);
										if (result1) {
											response.put("status", Strings.EMPTY + result1);
											response.put("description",
													"ProductList updated successfully with given Shop Id");
										}
									}

								}

							} else {
								response.put("status", Boolean.FALSE.toString());
								response.put("description", "product is out of stock.");
								response.put("cause", "cart_not_found");
							}

						} else {
							productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
							Boolean exists = offlineProductListService.checkExit(offlineCartId, productId, cashierId,
									stockActiveInd);

							if (exists) {
								offlineProductList = offlineProductListService
										.getByofflineCartIdAndProductId(offlineCartId, productId, stockActiveInd);
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
								offlineProductList.setOldPrice(oldPrice);
								offlineProductList.setUnitQuantity(unitQuantity);
								offlineProductList.setCashierId(cashierId);

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offline.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offline.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offline.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offline.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offline.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offline.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offline.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offline.setBundleQuantity(bundleQuantity);
								}
								if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
									extraBillDiscount = Float.parseFloat(
											data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
									offline.setExtraBillDiscount(extraBillDiscount);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offline.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									String barcode = data.get(i).get(ServiceConstants.BARCODE).toString();
									offline.setBarcode(barcode);
								}

								if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
									String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
									offline.setDescription(description);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offline.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offline.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offline.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
									productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
									offlineProductList.setProductId(productId);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									String barcode = data.get(i).get(ServiceConstants.BARCODE).toString();
									offlineProductList.setBarcode(barcode);
								}
								if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
									discount = Float.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
									offlineProductList.setDiscount(discount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
									gstPercent = Integer
											.parseInt((data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
									offlineProductList.setGstPercent(gstPercent);
								}

								if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
									gstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
									offlineProductList.setGstAmount(gstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
									offerPercent = Integer
											.parseInt(data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
									offlineProductList.setOfferPercent(offerPercent);
								}

								if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
									String measurement = data.get(i).get(ServiceConstants.MEASUREMENT).toString();
									offlineProductList.setMeasurement(measurement);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offlineProductList.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
									offlineProductList.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offlineProductList.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offlineProductList.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offlineProductList.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offlineProductList.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offlineProductList.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offlineProductList.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offlineProductList.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offlineProductList.setBundleQuantity(bundleQuantity);
								}

								if (null != data.get(i).get(ServiceConstants.STOCK)) {
									int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
									offlineProductList.setStock(stock);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offlineProductList.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offlineProductList.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offlineProductList.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offlineProductList.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
									Date dateOfExpire = null;
									SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
									try {
										dateOfExpire = formater
												.parse(data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
										offlineProductList.setDateOfExpire(dateOfExpire);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
								if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
									batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
									offlineProductList.setBatchNumber(batchNumber);
								}

								float offlineGstAmount = offline.getGstAmount() + gstAmount,
										offlineDiscount = offline.getDiscount() + discount,
										offlinePrice = offline.getSellingPrice() + sellingPrice,
										offlineOldPrice = offline.getOldPrice() + oldPrice,
										offlineTotalAmount = offline.getTotalPrice() + totalAmount,
										// second
										offlineMarginPercent = offline.getMarginPercent() + marginPercent,
										offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
										offlineMrp = offline.getMrp() + mrp,
										offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
										offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
										offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
										offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

								int payableAmount = (int) Math.ceil(offlineTotalAmount);

								offline.setGstAmount(offlineGstAmount);
								offline.setDiscount(offlineDiscount);
								offline.setSellingPrice(offlinePrice);
								offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
								offline.setPayableAmount(payableAmount);
								offline.setOldPrice(offlineOldPrice);
								// second
								offline.setMarginPercent(offlineMarginPercent);
								offline.setBundleMargin(offlineBundleMargin);
								offline.setMrp(offlineMrp);
								;
								offline.setUnitSellingPrice(offlineUnitSellingPrice);
								offline.setPurchasePrice(offlinePurchasePrice);
								offline.setCustomerSingleOffer(offlineSingleOffer);
								offline.setCustomerBundleOffer(offlineBundleOffer);
								boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
								if (result) {
									response.put("status", Boolean.TRUE.toString());
									response.put("description", "productlist updated");
									boolean result1 = offlineService.updateOffline(offline);
								}

							} else {
								offlineProductList = new OfflineProductList(shopId, productName, brandName);

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
								offlineProductList.setOldPrice(oldPrice);
								offlineProductList.setUnitQuantity(unitQuantity);
								offlineProductList.setCashierId(cashierId);

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offline.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offline.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offline.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offline.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offline.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offline.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offline.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offline.setBundleQuantity(bundleQuantity);
								}
								if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
									extraBillDiscount = Float.parseFloat(
											data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
									offline.setExtraBillDiscount(extraBillDiscount);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offline.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									String barcode = data.get(i).get(ServiceConstants.BARCODE).toString();
									offline.setBarcode(barcode);
								}

								if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
									String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
									offline.setDescription(description);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offline.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offline.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offline.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
									productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
									offlineProductList.setProductId(productId);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									String barcode = data.get(i).get(ServiceConstants.BARCODE).toString();
									offlineProductList.setBarcode(barcode);
								}
								if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
									discount = Float.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
									offlineProductList.setDiscount(discount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
									gstPercent = Integer
											.parseInt((data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
									offlineProductList.setGstPercent(gstPercent);
								}

								if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
									gstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
									offlineProductList.setGstAmount(gstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
									offerPercent = Integer
											.parseInt(data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
									offlineProductList.setOfferPercent(offerPercent);
								}

								if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
									String measurement = data.get(i).get(ServiceConstants.MEASUREMENT).toString();
									offlineProductList.setMeasurement(measurement);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offlineProductList.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
									offlineProductList.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offlineProductList.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offlineProductList.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offlineProductList.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offlineProductList.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offlineProductList.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offlineProductList.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offlineProductList.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offlineProductList.setBundleQuantity(bundleQuantity);
								}

								if (null != data.get(i).get(ServiceConstants.STOCK)) {
									int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
									offlineProductList.setStock(stock);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offlineProductList.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offlineProductList.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offlineProductList.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offlineProductList.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
									Date dateOfExpire = null;
									SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
									try {
										dateOfExpire = formater
												.parse(data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
										offlineProductList.setDateOfExpire(dateOfExpire);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
								if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
									batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
									offlineProductList.setBatchNumber(batchNumber);
								}

								float offlineGstAmount = offline.getGstAmount() + gstAmount,
										offlineDiscount = offline.getDiscount() + discount,
										offlinePrice = offline.getSellingPrice() + sellingPrice,
										offlineOldPrice = offline.getOldPrice() + oldPrice,
										offlineTotalAmount = offline.getTotalPrice() + totalAmount,
										// second
										offlineMarginPercent = offline.getMarginPercent() + marginPercent,
										offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
										offlineMrp = offline.getMrp() + mrp,
										offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
										offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
										offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
										offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

								int payableAmount = (int) Math.ceil(offlineTotalAmount);

								offline.setGstAmount(offlineGstAmount);
								offline.setDiscount(offlineDiscount);
								offline.setSellingPrice(offlinePrice);
								offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
								offline.setPayableAmount(payableAmount);
								offline.setOldPrice(offlineOldPrice);
								// second
								offline.setMarginPercent(offlineMarginPercent);
								offline.setBundleMargin(offlineBundleMargin);
								offline.setMrp(offlineMrp);
								;
								offline.setUnitSellingPrice(offlineUnitSellingPrice);
								offline.setPurchasePrice(offlinePurchasePrice);
								offline.setCustomerSingleOffer(offlineSingleOffer);
								offline.setCustomerBundleOffer(offlineBundleOffer);
								boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
								if (result) {
									response.put("status", Boolean.TRUE.toString());
									response.put("description", "productlist updated");
									boolean result1 = offlineService.updateOffline(offline);
								}
							}
						}

					}

				} else {

					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Cart does not exist with given cart ID.");
					response.put("cause", "cart_not_found");
				}
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create/main4")
//	ResponseEntity<Map<String, String>> createOfflineForCheck(@Valid @RequestBody ArrayList<Map> data,
	ResponseEntity<Map<String, String>> createOfflineForCheck(@Valid @RequestBody Map<String, ArrayList<Map>> json,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		ArrayList<Map> data = json.get("variant");
		ArrayList<Map> cartData = json.get("offline");
		float extraBillDiscount = Float
				.parseFloat(cartData.get(0).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());

		String paymentType = cartData.get(0).get(ServiceConstants.PAYMENT_TYPE).toString();

		if (null != data.get(0).get(ServiceConstants.OFFLINE_CART_ID)) {
			Offline offline = null;
			offline = offlineService.getAllBillByShopId(data.get(0).get(ServiceConstants.SHOP_ID).toString(),
					data.get(0).get(ServiceConstants.CASHIER_ID).toString());
			offline.setGstAmount(0);
			offline.setDiscount(0);
			offline.setSellingPrice(0);
			offline.setExtraBillDiscount(extraBillDiscount);
			offline.setTotalPrice(0);
			offline.setPayableAmount(0);
			offline.setPaymentType(paymentType);
			offline.setOldPrice(0);
			// second
			offline.setMarginPercent(0);
			offline.setBundleMargin(0);
			offline.setMrp(0);
			offline.setUnitSellingPrice(0);
			offline.setPurchasePrice(0);
			offline.setCustomerSingleOffer(0);
			offline.setCustomerBundleOffer(0);

			for (int i = 0; i < data.size(); i++) {
				log.info("Request to update user: {}", data.get(i).get(ServiceConstants.ID));
				// log.info("Request to create user: {}",
				// data.get(i)..get(ServiceConstants.SHOP_ID));
				if (null != data.get(i).get(ServiceConstants.SHOP_ID)
						&& null != data.get(i).get(ServiceConstants.PRODUCT_NAME)
						&& null != data.get(i).get(ServiceConstants.BRAND_NAME)
						&& null != data.get(i).get(ServiceConstants.SHOP_NAME)
						&& null != data.get(i).get(ServiceConstants.QUANTITY)
						&& null != data.get(i).get(ServiceConstants.TOTAL_PRICE)
						&& null != data.get(i).get(ServiceConstants.PRICE)
						&& null != data.get(i).get(ServiceConstants.OFFLINE_CART_ID)
						&& null != data.get(i).get(ServiceConstants.OLD_PRICE)
						&& null != data.get(i).get(ServiceConstants.CASHIER_ID)
						&& null != data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND)) {
					String shopId = data.get(i).get(ServiceConstants.SHOP_ID).toString(),
							productName = data.get(i).get(ServiceConstants.PRODUCT_NAME).toString(),
							brandName = data.get(i).get(ServiceConstants.BRAND_NAME).toString(),
							shopName = data.get(i).get(ServiceConstants.SHOP_NAME).toString(), productId = null,
							batchNumber = null, cashierId = data.get(i).get(ServiceConstants.CASHIER_ID).toString();
					boolean stockActiveInd = Boolean
							.parseBoolean(data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND).toString());
					ItemList product = null;

					float totalAmount = Float.parseFloat(data.get(i).get(ServiceConstants.TOTAL_PRICE).toString()),
							sellingPrice = Float.parseFloat(data.get(i).get(ServiceConstants.PRICE).toString()),
							productQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.QUANTITY).toString()),
							discount = 0, marginPercent = 0,
							oldPrice = Float.parseFloat(data.get(i).get(ServiceConstants.OLD_PRICE).toString()),
							gstAmount = 0, bundleMargin = 0, mrp = 0, unitSellingPrice = 0, purchasePrice = 0,
							customerSingleOffer = 0, customerBundleOffer = 0,
							unitQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.UNIT_QUANTITY).toString());
					int offlineCartId = Integer.parseInt(data.get(i).get(ServiceConstants.OFFLINE_CART_ID).toString()),
							gstPercent = 0, offerPercent = 0,
							slotNumber = Integer.parseInt(data.get(i).get(ServiceConstants.SLOT_NUMBER).toString()),
							varientIdId = Integer.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString());
					ItemList itemList = itemListService.getById(varientIdId);

					boolean offlinecreate = false;
Calculation calculation = new Calculation();
					OfflineProductList offlineProductList = null;

					if (null != offline) {

						if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
							productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
							product = itemListService.getById(Integer.parseInt(productId));

							VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,
									varientIdId);
							Boolean exists = offlineProductListService.checkExit(offlineCartId, productId, cashierId,
									stockActiveInd);

							if (exists) {
								OfflineProductList offlineProductList2 = offlineProductListService
										.getByofflineCartIdAndProductId(offlineCartId, productId, stockActiveInd);
								offlineProductList2
										.setUnitQuantity(offlineProductList2.getUnitQuantity() + productQuantity);

								offlineProductList2.setBrandName(brandName);
								offlineProductList2.setOfflineCartId(offlineCartId);
								offlineProductList2.setPrice(sellingPrice);
								offlineProductList2.setProductName(productName);
								offlineProductList2.setQuantity(productQuantity);
								offlineProductList2.setShopId(shopId);
								offlineProductList2.setTotalPrice(calculation.DecimalCalculation(totalAmount));
								offlineProductList2.setShopName(shopName);
								offlineProductList2.setActive(Boolean.TRUE);
								offlineProductList2.setCreatedOn(new Date());
								offlineProductList2.setDeleted(Boolean.FALSE);
								offlineProductList2.setStockActiveInd(stockActiveInd);
								offlineProductList2.setOldPrice(oldPrice);
								offlineProductList2.setUnitQuantity(unitQuantity);
								offlineProductList2.setCashierId(cashierId);
								offlineProductList2.setBarcode(itemList.getBarcode());
//						if(null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
//							offlineProductList.setPaymentType(paymentType);
//						}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offline.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offline.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offline.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offline.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offline.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offline.setCustomerSingleOffer(customerSingleOffer);
								}
								if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
									offline.setExtraBillDiscount(Float.parseFloat(
											data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString()));

								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offline.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offline.setBundleQuantity(bundleQuantity);
								}
								if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
									extraBillDiscount = Float.parseFloat(
											data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
									offline.setExtraBillDiscount(extraBillDiscount);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offline.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									// String barcode = data.get(i).get(ServiceConstants.BARCODE);
									offline.setBarcode(itemList.getBarcode());
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									// String barcode = data.get(i).get(ServiceConstants.BARCODE);
									offlineProductList2.setBarcode(itemList.getBarcode());
								}

								if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
									String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
									offline.setDescription(description);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offline.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offline.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offline.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
									productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
									offlineProductList2.setProductId(productId);
								}
								if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
									discount = Float.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
									offlineProductList2.setDiscount(discount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
									gstPercent = Integer
											.parseInt((data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
									offlineProductList2.setGstPercent(gstPercent);
								}

								if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
									gstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
									offlineProductList2.setGstAmount(gstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
									offerPercent = Integer
											.parseInt(data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
									offlineProductList2.setOfferPercent(offerPercent);
								}

								if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
									String measurement = data.get(i).get(ServiceConstants.MEASUREMENT).toString();
									offlineProductList2.setMeasurement(measurement);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offlineProductList2.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
									offlineProductList2.setUtgstAmount(utgstAmount);
								}

//						if (null != data.get(i).get(ServiceConstants.CASHIER_ID)) {
//							String cashierId = data.get(i).get(ServiceConstants.CASHIER_ID);
//							offline.setCashierId(cashierId);
//						}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offlineProductList2.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offlineProductList2.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offlineProductList2.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offlineProductList2.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offlineProductList2.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offlineProductList2.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offlineProductList2.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offlineProductList2.setBundleQuantity(bundleQuantity);
								}

								if (null != data.get(i).get(ServiceConstants.STOCK)) {
									int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
									offlineProductList2.setStock(stock);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offlineProductList2.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offlineProductList2.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offlineProductList2.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offlineProductList2.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
									Date dateOfExpire = null;
									SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
									try {
										dateOfExpire = formater
												.parse(data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
										offlineProductList2.setDateOfExpire(dateOfExpire);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
								if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
									batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
									offlineProductList2.setBatchNumber(batchNumber);
								}

								float offlineGstAmount = offline.getGstAmount() + gstAmount,
										offlineDiscount = offline.getDiscount() + discount,
										offlinePrice = offline.getSellingPrice() + sellingPrice,
										offlineOldPrice = offline.getOldPrice() + oldPrice,
										offlineTotalAmount = offline.getTotalPrice() + totalAmount,
										// second
										offlineMarginPercent = offline.getMarginPercent() + marginPercent,
										offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
										offlineMrp = offline.getMrp() + mrp,
										offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
										offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
										offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
										offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

								int payableAmount = (int) Math.ceil(offlineTotalAmount - extraBillDiscount);

								offline.setGstAmount(offlineGstAmount);
								offline.setDiscount(offlineDiscount);
								offline.setSellingPrice(offlinePrice);
								offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
								offline.setPayableAmount(payableAmount);
								offline.setOldPrice(offlineOldPrice);
								// second
								offline.setMarginPercent(offlineMarginPercent);
								offline.setBundleMargin(offlineBundleMargin);
								offline.setMrp(offlineMrp);
								offline.setUnitSellingPrice(offlineUnitSellingPrice);
								offline.setPurchasePrice(offlinePurchasePrice);
								offline.setCustomerSingleOffer(offlineSingleOffer);
								offline.setCustomerBundleOffer(offlineBundleOffer);
								offlineProductListService.updateOfflineProductList(offlineProductList2);
								boolean result1 = offlineService.updateOffline(offline);
								response.put("status", Boolean.TRUE.toString());
								response.put("description", "Product allready exists with given product name.");
								response.put("cause", "product_exists");
							} else {
								offlineProductList = new OfflineProductList(shopId, productName, brandName);

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
								offlineProductList.setOldPrice(oldPrice);
								offlineProductList.setUnitQuantity(unitQuantity);
								offlineProductList.setCashierId(cashierId);
								offlineProductList.setBarcode(itemList.getBarcode());
//						if(null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
//							offlineProductList.setPaymentType(paymentType);
//						}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offline.setUtgstAmount(utgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offline.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offline.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offline.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offline.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offline.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offline.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offline.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offline.setBundleQuantity(bundleQuantity);
								}
								if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
									extraBillDiscount = Float.parseFloat(
											data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
									offline.setExtraBillDiscount(extraBillDiscount);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offline.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									// String barcode = data.get(i).get(ServiceConstants.BARCODE);
									offline.setBarcode(itemList.getBarcode());
								}

								if (null != data.get(i).get(ServiceConstants.BARCODE)) {
									// String barcode = data.get(i).get(ServiceConstants.BARCODE);
									offlineProductList.setBarcode(itemList.getBarcode());
								}

								if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
									String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
									offline.setDescription(description);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offline.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offline.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offline.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
									productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
									offlineProductList.setProductId(productId);
								}
								if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
									discount = Float.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
									offlineProductList.setDiscount(discount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
									gstPercent = Integer
											.parseInt((data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
									offlineProductList.setGstPercent(gstPercent);
								}

								if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
									gstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
									offlineProductList.setGstAmount(gstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
									offerPercent = Integer
											.parseInt(data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
									offlineProductList.setOfferPercent(offerPercent);
								}

								if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
									String measurement = data.get(i).get(ServiceConstants.MEASUREMENT).toString();
									offlineProductList.setMeasurement(measurement);
								}

								if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
									float igstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
									offlineProductList.setIgstAmount(igstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
									float utgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
									offlineProductList.setUtgstAmount(utgstAmount);
								}

//						if (null != data.get(i).get(ServiceConstants.CASHIER_ID)) {
//							String cashierId = data.get(i).get(ServiceConstants.CASHIER_ID);
//							offline.setCashierId(cashierId);
//						}

								if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
									marginPercent = Float
											.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
									offlineProductList.setMarginPercent(marginPercent);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
									bundleMargin = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
									offlineProductList.setBundleMargin(bundleMargin);
								}

								if (null != data.get(i).get(ServiceConstants.MRP)) {
									mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
									offlineProductList.setMrp(mrp);
								}

								if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
									unitSellingPrice = Float.parseFloat(
											data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
									offlineProductList.setUnitSellingPrice(unitSellingPrice);
								}

								if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
									purchasePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
									offlineProductList.setPurchasePrice(purchasePrice);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
									customerSingleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
									offlineProductList.setCustomerSingleOffer(customerSingleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
									customerBundleOffer = Float.parseFloat(
											data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
									offlineProductList.setCustomerBundleOffer(customerBundleOffer);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
									float bundleQuantity = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
									offlineProductList.setBundleQuantity(bundleQuantity);
								}

								if (null != data.get(i).get(ServiceConstants.STOCK)) {
									int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
									offlineProductList.setStock(stock);
								}

								if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
									float bundlePrice = Float
											.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
									offlineProductList.setBundlePrice(bundlePrice);
								}

								if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
									float sgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
									offlineProductList.setSgstAmount(sgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
									float cgstAmount = Float
											.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
									offlineProductList.setCgstAmount(cgstAmount);
								}

								if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
									int gstType = Integer
											.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
									offlineProductList.setGstType(gstType);
								}

								if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
									Date dateOfExpire = null;
									SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
									try {
										dateOfExpire = formater
												.parse(data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
										offlineProductList.setDateOfExpire(dateOfExpire);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
								if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
									batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
									offlineProductList.setBatchNumber(batchNumber);
								}

								float offlineGstAmount = offline.getGstAmount() + gstAmount,
										offlineDiscount = offline.getDiscount() + discount,
										offlinePrice = offline.getSellingPrice() + sellingPrice,
										offlineOldPrice = offline.getOldPrice() + oldPrice,
										offlineTotalAmount = offline.getTotalPrice() + totalAmount,
										// second
										offlineMarginPercent = offline.getMarginPercent() + marginPercent,
										offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
										offlineMrp = offline.getMrp() + mrp,
										offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
										offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
										offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
										offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

								int payableAmount = (int) Math.ceil(offlineTotalAmount - extraBillDiscount);

								offline.setGstAmount(offlineGstAmount);
								offline.setDiscount(offlineDiscount);
								offline.setSellingPrice(offlinePrice);
								offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
								offline.setPayableAmount(payableAmount);
								offline.setOldPrice(offlineOldPrice);
								// second
								offline.setMarginPercent(offlineMarginPercent);
								offline.setBundleMargin(offlineBundleMargin);
								offline.setMrp(offlineMrp);
								offline.setUnitSellingPrice(offlineUnitSellingPrice);
								offline.setPurchasePrice(offlinePurchasePrice);
								offline.setCustomerSingleOffer(offlineSingleOffer);
								offline.setCustomerBundleOffer(offlineBundleOffer);

								if (stockActiveInd) {
									int productId1 = Integer
											.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString()),
											stock = 0;
									if (product.getStock() >= (int) productQuantity) {

										if (null != data.get(i).get(ServiceConstants.SLOT_NUMBER)
												&& data.get(i).get(ServiceConstants.SLOT_NUMBER).toString().length() > 0
												&& null != data.get(i).get(ServiceConstants.PRODUCT_ID) && data.get(i)
														.get(ServiceConstants.PRODUCT_ID).toString().length() > 0) {

											if (variantStock.getCurrentStock() >= productQuantity) {
//										stock = product.getStock();
//										product.setStock(stock - (int) productQuantity);
//										offlineProductList.setStock(product.getStock());
//										offlineProductList.setSlotNumber(slotNumber);
//										offlineProductList
//												.setSoldSlotStock(variantStock.getSoldStock() + (int) productQuantity);
//										offlineProductList.setCurrentSlotStock(
//												variantStock.getCurrentStock() - (int) productQuantity);
//										variantStock.setCurrentStock(
//												variantStock.getCurrentStock() - (int) productQuantity);
//										variantStock.setSoldStock(variantStock.getSoldStock() + (int) productQuantity);
//
//										variantStockService.updateStock(variantStock);

												stock = product.getStock();
												product.setStock(stock);
												offlineProductList.setStock(product.getStock());
												offlineProductList.setSlotNumber(slotNumber);
												offlineProductList.setSoldSlotStock(variantStock.getSoldStock());
												offlineProductList.setCurrentSlotStock(variantStock.getCurrentStock());
												variantStock.setCurrentStock(variantStock.getCurrentStock());
												variantStock.setSoldStock(variantStock.getSoldStock());

												variantStockService.updateStock(variantStock);

												boolean update = itemListService.updateItemList(product);

												boolean result = offlineProductListService
														.offlineProductCreate(offlineProductList);
												if (result) {
													boolean result1 = offlineService.updateOffline(offline);
												}
												response.put("status", Boolean.TRUE.toString());
												response.put("description",
														"ProductList updated successfully with given Shop Id");
											} else {
												response.put("status", Boolean.FALSE.toString());
												response.put("description",
														"Variant current stock is out of stock given Shop Id");
											}

										} else {
											stock = product.getStock();
											product.setStock(stock - (int) productQuantity);
											offlineProductList.setStock(product.getStock());
											itemListService.updateItemList(product);

											boolean result = offlineProductListService
													.offlineProductCreate(offlineProductList);
											if (result) {
												boolean result1 = offlineService.updateOffline(offline);
												response.put("status", Strings.EMPTY + result1);
												response.put("description",
														"ProductList updated successfully with given Shop Id");

											} else {
												response.put("status", Boolean.FALSE.toString());
												response.put("description", "Cart has not been updated.");
												response.put("cause", "cart_update_faield");
											}
										}
									} else {
										response.put("status", Boolean.FALSE.toString());
										response.put("description", "Cart has not been updated.");
										response.put("cause", "product_outofstock");
									}

//							/------------------------NEED TO ADD ELSE PART--------------------------/
								}

								else {
									boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
									if (result) {
										boolean result1 = offlineService.updateOffline(offline);
										if (result1) {
											response.put("status", Strings.EMPTY + result1);
											response.put("description",
													"ProductList updated successfully with given Shop Id");

										} else {
											response.put("status", Boolean.FALSE.toString());
											response.put("description", "Cart has not been updated.");
											response.put("cause", "cart_update_faield");
										}
									} else {
										response.put("status", Boolean.FALSE.toString());
										response.put("description", "Cart has not been updated.");
										response.put("cause", "product_create_faield");
									}
								}
							}
						} else {
							boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
							if (result) {
								boolean result1 = offlineService.updateOffline(offline);
								if (result1) {
									response.put("status", Strings.EMPTY + result1);
									response.put("description", "ProductList updated successfully with given Shop Id");

								} else {
									response.put("status", Boolean.FALSE.toString());
									response.put("description", "Cart has not been updated.");
									response.put("cause", "cart_update_faield");
								}
							} else {
								response.put("status", Boolean.FALSE.toString());
								response.put("description", "Cart has not been updated.");
								response.put("cause", "product_create_faield");
							}
						}

					} else {
						response.put("status", Boolean.FALSE.toString());
						response.put("description", "Cart does not exist with given cart ID.");
						response.put("cause", "cart_not_found");
					}
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product creation failed due to less data.");
					response.put("cause", "parameter_faield");
				}
			}
		}
		return ResponseEntity.ok().body(response);

	}

	@SuppressWarnings({})
	@PostMapping("/create/main1")
	ResponseEntity<Map<String, String>> createOffline(@Valid @RequestBody ArrayList<Map> data,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		for (int i = 0; i < data.size(); i++) {
			log.info("Request to update user: {}", data.get(i).get(ServiceConstants.ID));
			// log.info("Request to create user: {}",
			// data.get(i)..get(ServiceConstants.SHOP_ID));

			if (null != data.get(i).get(ServiceConstants.SHOP_ID)
					&& null != data.get(i).get(ServiceConstants.PRODUCT_NAME)
					&& null != data.get(i).get(ServiceConstants.BRAND_NAME)
					&& null != data.get(i).get(ServiceConstants.SHOP_NAME)
					&& null != data.get(i).get(ServiceConstants.QUANTITY)
					&& null != data.get(i).get(ServiceConstants.TOTAL_PRICE)
					&& null != data.get(i).get(ServiceConstants.PRICE)
					&& null != data.get(i).get(ServiceConstants.OFFLINE_CART_ID)
					&& null != data.get(i).get(ServiceConstants.OLD_PRICE)
					&& null != data.get(i).get(ServiceConstants.CASHIER_ID)
					&& null != data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND)) {
				String shopId = data.get(i).get(ServiceConstants.SHOP_ID).toString(),
						productName = data.get(i).get(ServiceConstants.PRODUCT_NAME).toString(),
						brandName = data.get(i).get(ServiceConstants.BRAND_NAME).toString(),
						shopName = data.get(i).get(ServiceConstants.SHOP_NAME).toString(), productId = null,
						batchNumber = null, cashierId = data.get(i).get(ServiceConstants.CASHIER_ID).toString();
				boolean stockActiveInd = Boolean
						.parseBoolean(data.get(i).get(ServiceConstants.STOCK_ACTIVE_IND).toString());
				ItemList product = null;

				float totalAmount = Float.parseFloat(data.get(i).get(ServiceConstants.TOTAL_PRICE).toString()),
						sellingPrice = Float.parseFloat(data.get(i).get(ServiceConstants.PRICE).toString()),
						productQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.QUANTITY).toString()),
						discount = 0, marginPercent = 0,
						oldPrice = Float.parseFloat(data.get(i).get(ServiceConstants.OLD_PRICE).toString()),
						gstAmount = 0, bundleMargin = 0, mrp = 0, unitSellingPrice = 0, purchasePrice = 0,
						customerSingleOffer = 0, customerBundleOffer = 0,
						unitQuantity = Float.parseFloat(data.get(i).get(ServiceConstants.UNIT_QUANTITY).toString());
				int offlineCartId = Integer.parseInt(data.get(i).get(ServiceConstants.OFFLINE_CART_ID).toString()),
						gstPercent = 0, offerPercent = 0,
						slotNumber = Integer.parseInt(data.get(i).get(ServiceConstants.SLOT_NUMBER).toString()),
						varientIdId = Integer.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString());
				ItemList itemList = itemListService.getById(varientIdId);

				Offline offline = null;
				boolean offlinecreate = false;
Calculation calculation = new Calculation();
				OfflineProductList offlineProductList = null;

				offline = offlineService.getAllBillByShopId(shopId, cashierId);
				if (null != offline) {

					if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
						productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
						product = itemListService.getById(Integer.parseInt(productId));
						VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,
								varientIdId);
						Boolean exists = offlineProductListService.checkExit(offlineCartId, productId, cashierId,
								stockActiveInd);

						if (exists) {
							// OfflineProductList offlineProductList2 =
							// offlineProductListService.getByofflineCartIdAndProductId(offlineCartId,
							// productId);
//						                                  
							response.put("status", Boolean.FALSE.toString());
							response.put("description", "Product allready exists with given product name.");
							response.put("cause", "product_exists");
						} else {
							offlineProductList = new OfflineProductList(shopId, productName, brandName);

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
							offlineProductList.setOldPrice(oldPrice);
							offlineProductList.setUnitQuantity(unitQuantity);
							offlineProductList.setCashierId(cashierId);
							offlineProductList.setBarcode(itemList.getBarcode());
//						if(null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
//							offlineProductList.setPaymentType(paymentType);
//						}

							if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
								float igstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
								offline.setIgstAmount(igstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
								float utgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
								offline.setUtgstAmount(utgstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
								marginPercent = Float
										.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
								offline.setMarginPercent(marginPercent);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
								bundleMargin = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
								offline.setBundleMargin(bundleMargin);
							}

							if (null != data.get(i).get(ServiceConstants.MRP)) {
								mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
								offline.setMrp(mrp);
							}

							if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
								unitSellingPrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
								offline.setUnitSellingPrice(unitSellingPrice);
							}

							if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
								purchasePrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
								offline.setPurchasePrice(purchasePrice);
							}

							if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
								customerSingleOffer = Float
										.parseFloat(data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
								offline.setCustomerSingleOffer(customerSingleOffer);
							}

							if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
								customerBundleOffer = Float
										.parseFloat(data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
								offline.setCustomerBundleOffer(customerBundleOffer);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
								float bundleQuantity = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
								offline.setBundleQuantity(bundleQuantity);
							}
							if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
								float extraBillDiscount = Float
										.parseFloat(data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
								offline.setExtraBillDiscount(extraBillDiscount);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
								float bundlePrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
								offline.setBundlePrice(bundlePrice);
							}

							if (null != data.get(i).get(ServiceConstants.BARCODE)) {
								// String barcode = data.get(i).get(ServiceConstants.BARCODE);
								offline.setBarcode(itemList.getBarcode());
							}

							if (null != data.get(i).get(ServiceConstants.BARCODE)) {
								// String barcode = data.get(i).get(ServiceConstants.BARCODE);
								offlineProductList.setBarcode(itemList.getBarcode());
							}

							if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
								String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
								offline.setDescription(description);
							}

							if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
								float sgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
								offline.setSgstAmount(sgstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
								float cgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
								offline.setCgstAmount(cgstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
								int gstType = Integer.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
								offline.setGstType(gstType);
							}

							if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
								productId = data.get(i).get(ServiceConstants.PRODUCT_ID).toString();
								offlineProductList.setProductId(productId);
							}
							if (null != data.get(i).get(ServiceConstants.DISCOUNT)) {
								discount = Float.parseFloat(data.get(i).get(ServiceConstants.DISCOUNT).toString());
								offlineProductList.setDiscount(discount);
							}

							if (null != data.get(i).get(ServiceConstants.GST_PERCENT)) {
								gstPercent = Integer
										.parseInt((data.get(i).get(ServiceConstants.GST_PERCENT)).toString());
								offlineProductList.setGstPercent(gstPercent);
							}

							if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
								gstAmount = Float.parseFloat(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
								offlineProductList.setGstAmount(gstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.OFFER_PERCENT)) {
								offerPercent = Integer
										.parseInt(data.get(i).get(ServiceConstants.OFFER_PERCENT).toString());
								offlineProductList.setOfferPercent(offerPercent);
							}

							if (null != data.get(i).get(ServiceConstants.MEASUREMENT)) {
								String measurement = data.get(i).get(ServiceConstants.MEASUREMENT).toString();
								offlineProductList.setMeasurement(measurement);
							}

							if (null != data.get(i).get(ServiceConstants.IGST_AMOUNT)) {
								float igstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.IGST_AMOUNT).toString());
								offlineProductList.setIgstAmount(igstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.UTGST_AMOUNT)) {
								float utgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.UTGST_AMOUNT).toString());
								offlineProductList.setUtgstAmount(utgstAmount);
							}

//						if (null != data.get(i).get(ServiceConstants.CASHIER_ID)) {
//							String cashierId = data.get(i).get(ServiceConstants.CASHIER_ID);
//							offline.setCashierId(cashierId);
//						}

							if (null != data.get(i).get(ServiceConstants.MARGIN_PERCENT)) {
								marginPercent = Float
										.parseFloat(data.get(i).get(ServiceConstants.MARGIN_PERCENT).toString());
								offlineProductList.setMarginPercent(marginPercent);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_MARGIN)) {
								bundleMargin = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_MARGIN).toString());
								offlineProductList.setBundleMargin(bundleMargin);
							}

							if (null != data.get(i).get(ServiceConstants.MRP)) {
								mrp = Float.parseFloat(data.get(i).get(ServiceConstants.MRP).toString());
								offlineProductList.setMrp(mrp);
							}

							if (null != data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE)) {
								unitSellingPrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.UNIT_SELLING_PRICE).toString());
								offlineProductList.setUnitSellingPrice(unitSellingPrice);
							}

							if (null != data.get(i).get(ServiceConstants.PURCHASE_PRICE)) {
								purchasePrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.PURCHASE_PRICE).toString());
								offlineProductList.setPurchasePrice(purchasePrice);
							}

							if (null != data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
								customerSingleOffer = Float
										.parseFloat(data.get(i).get(ServiceConstants.CUSTOMER_SINGLE_OFFER).toString());
								offlineProductList.setCustomerSingleOffer(customerSingleOffer);
							}

							if (null != data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
								customerBundleOffer = Float
										.parseFloat(data.get(i).get(ServiceConstants.CUSTOMER_BUNDLE_OFFER).toString());
								offlineProductList.setCustomerBundleOffer(customerBundleOffer);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_QUANTITY)) {
								float bundleQuantity = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_QUANTITY).toString());
								offlineProductList.setBundleQuantity(bundleQuantity);
							}

							if (null != data.get(i).get(ServiceConstants.STOCK)) {
								int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
								offlineProductList.setStock(stock);
							}

							if (null != data.get(i).get(ServiceConstants.BUNDLE_PRICE)) {
								float bundlePrice = Float
										.parseFloat(data.get(i).get(ServiceConstants.BUNDLE_PRICE).toString());
								offlineProductList.setBundlePrice(bundlePrice);
							}

							if (null != data.get(i).get(ServiceConstants.SGST_AMOUNT)) {
								float sgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.SGST_AMOUNT).toString());
								offlineProductList.setSgstAmount(sgstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.CGST_AMOUNT)) {
								float cgstAmount = Float
										.parseFloat(data.get(i).get(ServiceConstants.CGST_AMOUNT).toString());
								offlineProductList.setCgstAmount(cgstAmount);
							}

							if (null != data.get(i).get(ServiceConstants.GST_TYPE)) {
								int gstType = Integer.parseInt(data.get(i).get(ServiceConstants.GST_TYPE).toString());
								offlineProductList.setGstType(gstType);
							}

							if (null != data.get(i).get(ServiceConstants.DATE_OF_EXPIRE)) {
								Date dateOfExpire = null;
								SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
								try {
									dateOfExpire = formater
											.parse(data.get(i).get(ServiceConstants.DATE_OF_EXPIRE).toString());
									offlineProductList.setDateOfExpire(dateOfExpire);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							if (null != data.get(i).get(ServiceConstants.BATCH_NUMBER)) {
								batchNumber = data.get(i).get(ServiceConstants.BATCH_NUMBER).toString();
								offlineProductList.setBatchNumber(batchNumber);
							}

							float offlineGstAmount = offline.getGstAmount() + gstAmount,
									offlineDiscount = offline.getDiscount() + discount,
									offlinePrice = offline.getSellingPrice() + sellingPrice,
									offlineOldPrice = offline.getOldPrice() + oldPrice,
									offlineTotalAmount = offline.getTotalPrice() + totalAmount,
									// second
									offlineMarginPercent = offline.getMarginPercent() + marginPercent,
									offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
									offlineMrp = offline.getMrp() + mrp,
									offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
									offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
									offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
									offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;

							int payableAmount = (int) Math.ceil(offlineTotalAmount);

							offline.setGstAmount(offlineGstAmount);
							offline.setDiscount(offlineDiscount);
							offline.setSellingPrice(offlinePrice);
							offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
							offline.setPayableAmount(payableAmount);
							offline.setOldPrice(offlineOldPrice);
							// second
							offline.setMarginPercent(offlineMarginPercent);
							offline.setBundleMargin(offlineBundleMargin);
							offline.setMrp(offlineMrp);
							offline.setUnitSellingPrice(offlineUnitSellingPrice);
							offline.setPurchasePrice(offlinePurchasePrice);
							offline.setCustomerSingleOffer(offlineSingleOffer);
							offline.setCustomerBundleOffer(offlineBundleOffer);

							if (stockActiveInd) {
								int productId1 = Integer
										.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString()), stock = 0;
								if (product.getStock() >= (int) productQuantity) {

									if (null != data.get(i).get(ServiceConstants.SLOT_NUMBER)
											&& data.get(i).get(ServiceConstants.SLOT_NUMBER).toString().length() > 0
											&& null != data.get(i).get(ServiceConstants.PRODUCT_ID)
											&& data.get(i).get(ServiceConstants.PRODUCT_ID).toString().length() > 0) {

										if (variantStock.getCurrentStock() >= productQuantity) {
//										stock = product.getStock();
//										product.setStock(stock - (int) productQuantity);
//										offlineProductList.setStock(product.getStock());
//										offlineProductList.setSlotNumber(slotNumber);
//										offlineProductList
//												.setSoldSlotStock(variantStock.getSoldStock() + (int) productQuantity);
//										offlineProductList.setCurrentSlotStock(
//												variantStock.getCurrentStock() - (int) productQuantity);
//										variantStock.setCurrentStock(
//												variantStock.getCurrentStock() - (int) productQuantity);
//										variantStock.setSoldStock(variantStock.getSoldStock() + (int) productQuantity);
//
//										variantStockService.updateStock(variantStock);

											stock = product.getStock();
											product.setStock(stock);
											offlineProductList.setStock(product.getStock());
											offlineProductList.setSlotNumber(slotNumber);
											offlineProductList.setSoldSlotStock(variantStock.getSoldStock());
											offlineProductList.setCurrentSlotStock(variantStock.getCurrentStock());
											variantStock.setCurrentStock(variantStock.getCurrentStock());
											variantStock.setSoldStock(variantStock.getSoldStock());

											variantStockService.updateStock(variantStock);

											boolean update = itemListService.updateItemList(product);

											boolean result = offlineProductListService
													.offlineProductCreate(offlineProductList);
											if (result) {
												boolean result1 = offlineService.updateOffline(offline);
											}
											response.put("status", Boolean.TRUE.toString());
											response.put("description",
													"ProductList updated successfully with given Shop Id");
										} else {
											response.put("status", Boolean.FALSE.toString());
											response.put("description",
													"Variant current stock is out of stock given Shop Id");
										}

									} else {
										stock = product.getStock();
										product.setStock(stock - (int) productQuantity);
										offlineProductList.setStock(product.getStock());
										itemListService.updateItemList(product);

										boolean result = offlineProductListService
												.offlineProductCreate(offlineProductList);
										if (result) {
											boolean result1 = offlineService.updateOffline(offline);
											response.put("status", Strings.EMPTY + result1);
											response.put("description",
													"ProductList updated successfully with given Shop Id");

										} else {
											response.put("status", Boolean.FALSE.toString());
											response.put("description", "Cart has not been updated.");
											response.put("cause", "cart_update_faield");
										}
									}
								} else {
									response.put("status", Boolean.FALSE.toString());
									response.put("description", "Cart has not been updated.");
									response.put("cause", "product_outofstock");
								}

//							/------------------NEED TO ADD ELSE PART----------------/
							}

							else {
								boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
								if (result) {
									boolean result1 = offlineService.updateOffline(offline);
									if (result1) {
										response.put("status", Strings.EMPTY + result1);
										response.put("description",
												"ProductList updated successfully with given Shop Id");

									} else {
										response.put("status", Boolean.FALSE.toString());
										response.put("description", "Cart has not been updated.");
										response.put("cause", "cart_update_faield");
									}
								} else {
									response.put("status", Boolean.FALSE.toString());
									response.put("description", "Cart has not been updated.");
									response.put("cause", "product_create_faield");
								}
							}
						}
					} else {
						boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
						if (result) {
							boolean result1 = offlineService.updateOffline(offline);
							if (result1) {
								response.put("status", Strings.EMPTY + result1);
								response.put("description", "ProductList updated successfully with given Shop Id");

							} else {
								response.put("status", Boolean.FALSE.toString());
								response.put("description", "Cart has not been updated.");
								response.put("cause", "cart_update_faield");
							}
						} else {
							response.put("status", Boolean.FALSE.toString());
							response.put("description", "Cart has not been updated.");
							response.put("cause", "product_create_faield");
						}
					}

				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Cart does not exist with given cart ID.");
					response.put("cause", "cart_not_found");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product creation failed due to less data.");
				response.put("cause", "parameter_faield");
			}
		}
		return ResponseEntity.ok().body(response);

	}

	@PutMapping("/updateall")
	ResponseEntity<Map<String, String>> UpdateOfflineAll(@Valid @RequestBody ArrayList<Map> data,
			HttpServletRequest request) throws URISyntaxException {
		Map<String, String> response = new HashMap<String, String>();
		for (int i = 0; i < data.size(); i++) {
			log.info("Request to update user: {}", data.get(i).get(ServiceConstants.ID));
			if (null != data.get(i).get(ServiceConstants.ID)) {
				Offline offline = offlineService
						.getByBillId(Integer.parseInt(data.get(i).get(ServiceConstants.ID).toString()));

				if (null != data.get(i).get(ServiceConstants.USER_NAME)) {
					String userName = data.get(i).get(ServiceConstants.USER_NAME).toString();
					offline.setUserName(userName);
				}
				if (null != data.get(i).get(ServiceConstants.BARCODE)) {
					String barcode = data.get(i).get(ServiceConstants.BARCODE).toString();
					offline.setBarcode(barcode);
				}
				if (null != data.get(i).get(ServiceConstants.USER_ID)) {
					int userId = Integer.parseInt(data.get(i).get(ServiceConstants.USER_ID).toString());
					offline.setUserId(userId);
				}
				if (null != data.get(i).get(ServiceConstants.IS_ACTIVE)) {
					boolean isActive = Boolean.TRUE;
					offline.setActive(isActive);
				}
				if (null != data.get(i).get(ServiceConstants.ADMIN_ID)) {
					int adminId = Integer.parseInt(data.get(i).get(ServiceConstants.ADMIN_ID).toString());
					offline.setAdminId(adminId);
				}

				if (null != data.get(i).get(ServiceConstants.CREATED_ON)) {
					Date createdOn = new Date();
					offline.setCreatedOn(createdOn);
				}
				if (null != data.get(i).get(ServiceConstants.IS_DELETED)) {
					boolean isDeleted = Boolean.FALSE;
					offline.setDeleted(isDeleted);
				}

				if (null != data.get(i).get(ServiceConstants.GST_AMOUNT)) {
					int gstAmount = Integer.parseInt(data.get(i).get(ServiceConstants.GST_AMOUNT).toString());
					offline.setGstAmount(gstAmount);
				}
				if (null != data.get(i).get(ServiceConstants.MOBILE_NUMBER)) {
					String mobileNo = data.get(i).get(ServiceConstants.MOBILE_NUMBER).toString();
					offline.setMobileNo(mobileNo);
				}

				if (null != data.get(i).get(ServiceConstants.PAYMENT_TYPE)) {
					String paymentType = data.get(i).get(ServiceConstants.PAYMENT_TYPE).toString();
					offline.setPaymentType(paymentType);
				}

				if (null != data.get(i).get(ServiceConstants.DESCRIPTION)) {
					String description = data.get(i).get(ServiceConstants.DESCRIPTION).toString();
					offline.setDescription(description);
				}
				if (null != data.get(i).get(ServiceConstants.PRODUCT_ID)) {
					int productId = Integer.parseInt(data.get(i).get(ServiceConstants.PRODUCT_ID).toString());
					offline.setProductId(productId);
				}
				if (null != data.get(i).get(ServiceConstants.PRODUCT_NAME)) {
					String productName = data.get(i).get(ServiceConstants.PRODUCT_NAME).toString();
					offline.setProductName(productName);
				}
				if (null != data.get(i).get(ServiceConstants.PRODUCT_QUANTITY)) {
					int productQuantity = Integer
							.parseInt(data.get(i).get(ServiceConstants.PRODUCT_QUANTITY).toString());
					offline.setProductQuantity(productQuantity);
				}
				if (null != data.get(i).get(ServiceConstants.SELLING_PRICE)) {
					int sellingPrice = Integer.parseInt(data.get(i).get(ServiceConstants.SELLING_PRICE).toString());
					offline.setSellingPrice(sellingPrice);
				}
				if (null != data.get(i).get(ServiceConstants.SHOP_ID)) {
					String shopId = data.get(i).get(ServiceConstants.SHOP_ID).toString();
					offline.setShopId(shopId);
				}
				if (null != data.get(i).get(ServiceConstants.SHOP_NAME)) {
					String shopName = data.get(i).get(ServiceConstants.SHOP_NAME).toString();
					offline.setShopName(shopName);
				}
				if (null != data.get(i).get(ServiceConstants.STOCK)) {
					int stock = Integer.parseInt(data.get(i).get(ServiceConstants.STOCK).toString());
//				List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(Integer.parseInt(data.get(i).get(ServiceConstants.ID).toString()));
//				for(int i = 0 ; i<offlineProductList.size(); i++) {
//					String productId = offlineProductList.get(i).getProductId();
//					int slotNumber = offlineProductList.get(i).getSlotNumber();
//					ItemList item = itemListService.getVariantByProductId(Integer.parseInt(productId));
//					int quantity = item.getQuantity();
//					VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,Integer.parseInt(productId));
//					item.setStock(item.getStock()- quantity);
//					variantStock.setCurrentStock(variantStock.getCurrentStock() - quantity);
//					variantStock.setSoldStock(variantStock.getSoldStock() + quantity);
//					itemListService.updateItemList(item);
//					variantStockService.updateStock(variantStock);
//				}

					offline.setStock(stock);
				}

				if (null != data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
					float extraBillDiscount = Float
							.parseFloat(data.get(i).get(ServiceConstants.EXTRA_BILL_DISCOUNT).toString());
					offline.setExtraBillDiscount(extraBillDiscount);
				}

				if (null != data.get(i).get(ServiceConstants.TOTAL_PRICE)) {
					float totalPrice = Float.parseFloat(data.get(i).get(ServiceConstants.TOTAL_PRICE).toString());
					offline.setTotalPrice(totalPrice);
				}

				if (null != data.get(i).get(ServiceConstants.SELLING_PRICE)) {
					float sellingPrice = Float.parseFloat(data.get(i).get(ServiceConstants.SELLING_PRICE).toString());
					offline.setSellingPrice(sellingPrice);
				}

				if (null != data.get(i).get(ServiceConstants.SELLING_PRICE)) {
					float sellingPrice = Float.parseFloat(data.get(i).get(ServiceConstants.SELLING_PRICE).toString());
					offline.setSellingPrice(sellingPrice);
				}

				offlineService.updateOffline(offline);
				response.put("status", Boolean.TRUE.toString());
				response.put("Description", "Offline bill update");

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("Description", "Offline bill not update");
			}
		}
		return ResponseEntity.ok().body(response);
	}

//	@SuppressWarnings({})
//	@PostMapping("/create/calculation1")
//	ResponseEntity<Map<String, String>> createOfflineCalculation(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.PRODUCT_ID)
//				&& null != json.get(ServiceConstants.VARIANT_ID) && null != json.get(ServiceConstants.OFFLINE_CART_ID)
//				&& null != json.get(ServiceConstants.QUANTITY) && null != json.get(ServiceConstants.CASHIER_ID)) {
//			String shopId = json.get(ServiceConstants.SHOP_ID), shopName = json.get(ServiceConstants.SHOP_NAME),
//					cashierId = json.get(ServiceConstants.CASHIER_ID);
//			// boolean stockActiveInd =
//			// Boolean.parseBoolean(json.get(ServiceConstants.STOCK_ACTIVE_IND));
//			float productQuantity = Float.parseFloat(json.get(ServiceConstants.QUANTITY)), price = 0;
//			int offlineCartId = Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID)), gstPercent = 0,
//					productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)),
//					variantId = Integer.parseInt(json.get(ServiceConstants.VARIANT_ID));
//			// billId = Integer.parseInt(json.get(ServiceConstants.ID));
//			int discount = 0;
//			float marginPercent = 0, bundleMargin = 0, unitSellingPrice = 0, purchasePrice = 0, customerSingleOffer = 0,
//					customerBundleOffer = 0, bundleQuantity = 0, bundlePrice = 0, oldPrice = 0, totalAmount = 0,
//					sellingPrice = 0;
//			Item item = null;
//			ItemList product = null;
//			Offline offline = null;
//			boolean offlinecreate = false;
//			OfflineProductList offlineProductList = null;
//			item = itemService.getById(productId);
//			product = itemListService.getById(variantId);
//			offline = offlineService.getByBillId(offlineCartId);
//			// offline = offlineService.getAllBillByShopId(shopId, cashierId);
//			int brandId = item.getBrand(), categoryId = item.getCategory();
//			float mrp = product.getMrp(), gstAmountProduct = product.getGstAmount();
//
//			Brand brand = brandService.getBrandById(brandId);
//			Calculation calculation = new Calculation();
//			String productName = item.getName(), brandName = brand.getName(), slotNumber = product.getSlotList();
//
//			float gstAmount = calculation.DecimalCalculation(productQuantity * gstAmountProduct);
//			price = product.getUnitSellingPrice() * productQuantity + gstAmount;
//			unitSellingPrice = product.getUnitSellingPrice() * productQuantity;
//			marginPercent = product.getMarginPercent();
//			purchasePrice = product.getPurchasePrice() * productQuantity;
//			bundleMargin = product.getBundleMargin();
//			customerSingleOffer = product.getCustomerSingleOffer();
//			sellingPrice = product.getSellingPrice();
//			mrp = product.getMrp() * productQuantity;
//			float totalPrice = price;
//			int currentStock = product.getStock();
//
//			if (product.getStock() <= productQuantity) {
//
//				boolean offline1 = offlineService.cheakBillCreated(offlineCartId);
//				if (offline1) {
//					Boolean exists = offlineProductListService.checkExit(offlineCartId, String.valueOf(productId),
//							cashierId, stockActiveInd);
//					if (exists) {
//						response.put("status", Boolean.FALSE.toString());
//						response.put("description", "Product allready exists with given product name.");
//						response.put("cause", "product_exists");
//					} else {
//						offlineProductList = new OfflineProductList();
//
//						offlineProductList = new OfflineProductList();
//						offlineProductList.setActive(Boolean.TRUE);
//						offlineProductList.setPurchasePrice(calculation.DecimalCalculation(purchasePrice));
//						offlineProductList.setProductName(productName);
//						offlineProductList.setBundleMargin(bundleMargin);
//						offlineProductList.setCustomerSingleOffer(customerSingleOffer);
//						offlineProductList.setCustomerBundleOffer(product.getCustomerBundleOffer());
//						offlineProductList.setBarcode(product.getBarcode());
//						offlineProductList.setBrandName(brand.getName());
//						offlineProductList.setQuantity(productQuantity);
//						offlineProductList.setMarginPercent(calculation.DecimalCalculation(marginPercent));
//						offlineProductList.setTotalPrice(calculation.DecimalCalculation(totalPrice));
//						offlineProductList.setPrice(calculation.DecimalCalculation(price));
//						// offlineProductList.setOldPrice(calculation.DecimalCalculation(price));
//						offlineProductList.setBundleMargin(product.getBundleMargin());
//						offlineProductList.setBundlePrice(product.getBundlePrice());
//						offlineProductList.setBundleQuantity(product.getBundleQuantity());
//						offlineProductList.setCashierId(cashierId);
//						offlineProductList.setCreatedOn(new Date());
//						// offlineProductList.setCurrentSlotStock(currentStock);
//						offlineProductList.setGstPercent(product.getGstPercent());
//						// offlineProductList.setS
//						offlineProductList.setGstAmount(gstAmount);
//						offlineProductList.setOfflineCartId(offlineCartId);
//						offlineProductList.setShopId(shopId);
//						offlineProductList.setDeleted(Boolean.FALSE);
//						offlineProductList.setProductId(String.valueOf(productId));
//						offlineProductList.setUnitSellingPrice(calculation.DecimalCalculation(unitSellingPrice));
//						offlineProductList.setOldPrice(calculation.DecimalCalculation(unitSellingPrice));
//						offlineProductList.setShopName(shopName);
//						offlineProductList.setMrp(calculation.DecimalCalculation(mrp));
//						// offlineProductList.setMeasurement(measurement);
//						offlineProductList.setStock(product.getStock());
//						offlineProductList.setUnitQuantity(productQuantity);
//						offlineProductList.setSlotNumber(Integer.parseInt(slotNumber));
//						offlineProductList.setCurrentSlotStock(currentStock);
//
//						if (null != json.get(ServiceConstants.BATCH_NUMBER)) {
//							offlineProductList.setBatchNumber(json.get(ServiceConstants.BATCH_NUMBER));
//						}
//
//						if (null != json.get(ServiceConstants.PAYMENT_TYPE)) {
//							offlineProductList
//									.setPaymentType(Integer.parseInt(json.get(ServiceConstants.PAYMENT_TYPE)));
//						}
//
//						if (null != json.get(ServiceConstants.CGST_AMOUNT)) {
//							offlineProductList.setCgstAmount(Float.parseFloat(json.get(ServiceConstants.CGST_AMOUNT)));
//						}
//
//						if (null != json.get(ServiceConstants.SGST_AMOUNT)) {
//							offlineProductList.setSgstAmount(Float.parseFloat(json.get(ServiceConstants.SGST_AMOUNT)));
//						}
//
//						if (null != json.get(ServiceConstants.IGST_AMOUNT)) {
//							offlineProductList.setIgstAmount(Float.parseFloat(json.get(ServiceConstants.IGST_AMOUNT)));
//						}
//
//						if (null != json.get(ServiceConstants.UTGST_AMOUNT)) {
//							offlineProductList
//									.setUtgstAmount(Float.parseFloat(json.get(ServiceConstants.UTGST_AMOUNT)));
//						}
//						Slot slot = slotService.getById(Integer.parseInt(slotNumber));
//						int venderId = slot.getVenderId();
//						Vender vender = venderService.getById(venderId);
//						int gstType = vender.getGstType();
//						offlineProductList.setGstType(gstType);
//
//						price = calculation.DecimalCalculation(productQuantity * product.getUnitSellingPrice());
//
//						totalAmount = calculation.DecimalCalculation(price + gstAmount);
//
//						float offlineGstAmount = offline.getGstAmount() + gstAmount,
//								offlineDiscount = offline.getDiscount() + discount,
//								offlinePrice = offline.getSellingPrice() + sellingPrice,
//								offlineOldPrice = offline.getOldPrice() + totalAmount,
//								offlineTotalAmount = offline.getTotalPrice() + totalAmount,
//								// second//
//								offlineMarginPercent = offline.getMarginPercent() + marginPercent,
//								offlineBundleMargin = offline.getBundleMargin() + bundleMargin,
//								offlineMrp = offline.getMrp() + mrp,
//								offlineUnitSellingPrice = offline.getUnitSellingPrice() + unitSellingPrice,
//								offlinePurchasePrice = offline.getPurchasePrice() + purchasePrice,
//								offlineSingleOffer = offline.getCustomerSingleOffer() + customerSingleOffer,
//								offlineBundleOffer = offline.getCustomerBundleOffer() + customerBundleOffer;
//
//						int payableAmount = (int) Math.ceil(offlineTotalAmount);
//
//						offline.setGstAmount(calculation.DecimalCalculation(offlineGstAmount));
//						offline.setDiscount(calculation.DecimalCalculation(offlineDiscount));
//						offline.setSellingPrice(calculation.DecimalCalculation(offlinePrice));
//						offline.setOldPrice(calculation.DecimalCalculation(offlineOldPrice));
//						offline.setTotalPrice(calculation.DecimalCalculation(offlineTotalAmount));
//						offline.setPayableAmount(payableAmount);
//						offline.setOldPrice(calculation.DecimalCalculation(offlineTotalAmount));
//						// second
//						offline.setMarginPercent(calculation.DecimalCalculation(offlineMarginPercent));
//						offline.setUnitSellingPrice(offlineUnitSellingPrice);
//						offline.setBundleMargin(calculation.DecimalCalculation(offlineBundleMargin));
//						offline.setMrp(calculation.DecimalCalculation(offlineMrp));
//						offline.setUnitSellingPrice(calculation.DecimalCalculation(offlineUnitSellingPrice));
//						offline.setPurchasePrice(calculation.DecimalCalculation(offlinePurchasePrice));
//						offline.setCustomerSingleOffer(calculation.DecimalCalculation(offlineSingleOffer));
//						offline.setCustomerBundleOffer(calculation.DecimalCalculation(offlineBundleOffer));
//
//						if (null != slotNumber && null != String.valueOf(variantId)) {
//							VariantStock variantStock = variantStockService
//									.getBySlotNumberAndVariantId(Integer.parseInt(slotNumber), variantId);
//							offlineProductList.setSoldSlotStock(variantStock.getSoldStock());
//							product.setStock(product.getStock() - (int) productQuantity);
//							variantStock.setCurrentStock(variantStock.getCurrentStock() - (int) productQuantity);
//							variantStock.setSoldStock(variantStock.getSoldStock() + (int) productQuantity);
//							itemListService.updateItemList(product);
//							boolean update = variantStockService.updateStock(variantStock);
//							if (update) {
//								response.put("statue", Boolean.TRUE.toString());
//								response.put("Description", "Your stock is update successfully");
//							} else {
//								response.put("statue", Boolean.FALSE.toString());
//								response.put("Description", "Your stock is not update successfully");
//							}
//						} else {
//							response.put("statue", Boolean.FALSE.toString());
//							response.put("Description", "Your stock is not update successfully");
//							response.put("couse", "Your slotNumber and variantId in not right");
//						}
//						boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
//					}
//					boolean result1 = offlineService.updateOffline(offline);
//					if (result1) {
//						response.put("status", Strings.EMPTY + result1);
//						response.put("description", "ProductList updated successfully with given Shop Id");
//
//					} else {
//						response.put("status", Boolean.FALSE.toString());
//						response.put("description", "Cart has not been updated.");
//						response.put("cause", "cart_update_faield");
//					}
//				} else {
//					response.put("status", Boolean.FALSE.toString());
//					response.put("description", "Cart has not been updated.");
//					response.put("cause", "product_create_faield");
//				}
//
//			} else {
//
//				response.put("status", Boolean.FALSE.toString());
//				response.put("description", "Cart has not been updated.");
//				response.put("cause", "current product stock is out of stock");
//			}
//
//		} else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", "Cart has not been updated due to less data.");
//			response.put("cause", "product_create_faield");
//		}
//		return ResponseEntity.ok().body(response);
//
//	}

	@PostMapping("/create2")
	ResponseEntity<Map<String, String>> createOfflineByShopId(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.MOBILE_NUMBER)
				&& null != json.get(ServiceConstants.CASHIER_ID)) {
			String shopId = json.get(ServiceConstants.SHOP_ID), mobileNumber = json.get(ServiceConstants.MOBILE_NUMBER),
					cashierId = json.get(ServiceConstants.CASHIER_ID);

			Offline offline = new Offline(json.get(ServiceConstants.SHOP_ID), json.get(ServiceConstants.MOBILE_NUMBER));

			if (null != json.get(ServiceConstants.USER_NAME)) {
				offline.setUserName(json.get(ServiceConstants.USER_NAME));
			}

			offline.setActive(Boolean.TRUE);
			offline.setDeleted(Boolean.FALSE);
			offline.setCreatedOn(new Date());
			offline.setMobileNo(mobileNumber);
			offline.setCashierId(cashierId);
			offline.setShopId(shopId);

			if (null != json.get(ServiceConstants.PAYMENT_TYPE)) {
				String paymentType = json.get(ServiceConstants.PAYMENT_TYPE);
				offline.setPaymentType(paymentType);
			}
			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
			if (offlineService.offlineCheckExists(offline.getShopId(), offline.getCashierId(), true)) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Offline cart allready exists with given shop ID.");
				response.put("cause", "cart_exists");
			} else {
				offlineService.offlineCreate2(offline);
				response.put("status", Boolean.TRUE.toString());
				response.put("billingId", String.valueOf(offline.getBillingId()));
				response.put("description", "Offline cart created");

			}

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Cart creation failed due to less data.");
			response.put("cause", "parameter_faield");
		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateOffline(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.ID));
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.ID)) {
			Offline offline = offlineService.getByBillId(Integer.parseInt(json.get(ServiceConstants.ID)));
			List<Offline> offlineList = new ArrayList<Offline>();
			for (int i = 0; i > offlineList.size(); i++) {
				offline = offlineList.get(0);
			}

			if (null != json.get(ServiceConstants.USER_NAME)) {
				String userName = json.get(ServiceConstants.USER_NAME);
				offline.setUserName(userName);
			}

			if (null != json.get(ServiceConstants.BARCODE)) {
				String barcode = json.get(ServiceConstants.BARCODE);
				offline.setBarcode(barcode);
			}
			if (null != json.get(ServiceConstants.USER_ID)) {
				int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID));
				offline.setUserId(userId);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.TRUE;
				offline.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID));
				offline.setAdminId(adminId);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				offline.setCreatedOn(createdOn);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.FALSE;
				offline.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT));
				offline.setGstAmount(gstAmount);
			}
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				offline.setMobileNo(mobileNo);
			}

			if (null != json.get(ServiceConstants.PAYMENT_TYPE)) {
				String paymentType = json.get(ServiceConstants.PAYMENT_TYPE);
				offline.setPaymentType(paymentType);
			}

			if (null != json.get(ServiceConstants.DESCRIPTION)) {
				String description = json.get(ServiceConstants.DESCRIPTION);
				offline.setDescription(description);
			}
			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
				int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID));
				offline.setProductId(productId);
			}
			if (null != json.get(ServiceConstants.PRODUCT_NAME)) {
				String productName = json.get(ServiceConstants.PRODUCT_NAME);
				offline.setProductName(productName);
			}
			if (null != json.get(ServiceConstants.PRODUCT_QUANTITY)) {
				int productQuantity = Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY));
				offline.setProductQuantity(productQuantity);
			}
			if (null != json.get(ServiceConstants.SELLING_PRICE)) {
				int sellingPrice = Integer.parseInt(json.get(ServiceConstants.SELLING_PRICE));
				offline.setSellingPrice(sellingPrice);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				offline.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				offline.setShopName(shopName);
			}
			if (null != json.get(ServiceConstants.STOCK)) {
				int stock = Integer.parseInt(json.get(ServiceConstants.STOCK));
//				List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(Integer.parseInt(json.get(ServiceConstants.ID)));
//				for(int i = 0 ; i<offlineProductList.size(); i++) {
//					String productId = offlineProductList.get(i).getProductId();
//					int slotNumber = offlineProductList.get(i).getSlotNumber();
//					ItemList item = itemListService.getVariantByProductId(Integer.parseInt(productId));
//					int quantity = item.getQuantity();
//					VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotNumber,Integer.parseInt(productId));
//					item.setStock(item.getStock()- quantity);
//					variantStock.setCurrentStock(variantStock.getCurrentStock() - quantity);
//					variantStock.setSoldStock(variantStock.getSoldStock() + quantity);
//					itemListService.updateItemList(item);
//					variantStockService.updateStock(variantStock);
//				}

				offline.setStock(stock);
			}

			if (null != json.get(ServiceConstants.EXTRA_BILL_DISCOUNT)) {
				float extraBillDiscount = Float.parseFloat(json.get(ServiceConstants.EXTRA_BILL_DISCOUNT));
				offline.setExtraBillDiscount(extraBillDiscount);
			}

			if (null != json.get(ServiceConstants.TOTAL_PRICE)) {
				float totalPrice = Float.parseFloat(json.get(ServiceConstants.TOTAL_PRICE));
				offline.setTotalPrice(totalPrice);
			}

			if (null != json.get(ServiceConstants.SELLING_PRICE)) {
				float sellingPrice = Float.parseFloat(json.get(ServiceConstants.SELLING_PRICE));
				offline.setSellingPrice(sellingPrice);
			}

			if (null != json.get(ServiceConstants.SELLING_PRICE)) {
				float sellingPrice = Float.parseFloat(json.get(ServiceConstants.SELLING_PRICE));
				offline.setSellingPrice(sellingPrice);
			}

			offlineService.updateOffline(offline);
			response.put("status", Boolean.TRUE.toString());
			response.put("Description", "Offline bill update");

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Description", "Offline bill not update");
		}
		return ResponseEntity.ok().body(response);
	}

}
