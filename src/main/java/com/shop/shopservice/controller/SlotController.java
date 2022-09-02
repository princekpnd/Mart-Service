package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.entity.Vender;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.service.IVenderService;

@RestController
@RequestMapping("/api/slot")
public class SlotController {
	private final Logger log = LoggerFactory.getLogger(SlotController.class);

	@Autowired
	private ISlotService slotService;

	@Autowired
	private IVenderService venderService;

	@Autowired
	private IItemListService itemListService;

	@Autowired
	private IVariantStockService variantStockService;
	LocalData localData = new LocalData();
	@GetMapping("get/{id}")
	public ResponseEntity<Slot> getById(@PathVariable("id") int id) {
		Slot slot = slotService.getById(id);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}
	
	@GetMapping("useforexcel/deactive/{id}")
	public ResponseEntity<Slot> getDeactiveForExcelFile(@PathVariable("id") int id){
		Slot slot = slotService.getDeactiveForExcelFile(id);
		slot.setActive(Boolean.FALSE);
		slot.setDeleted(Boolean.TRUE);
		slotService.updeteSlot(slot);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}

	@GetMapping("getvariant/{id}")
	public ResponseEntity<Slot> getAllVariantById(@PathVariable("id") int id) {
		Slot slot = slotService.getById(id);
		int vanderId = slot.getVenderId();
		Vender vender = venderService.getById(vanderId);
		slot.setVender(vender);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}

	@GetMapping("getallvariant/{venderId}")
	public ResponseEntity<List<Slot>> getById2(@PathVariable("venderId") int venderId) {
		List<Slot> slotList = slotService.getIdByVariant(venderId);
		int slotNumber = slotList.get(0).getSlotNumber();
		List<VariantStock> variantStockList = variantStockService.getBySlotNumber(slotNumber);
		for (int i = 0; i < variantStockList.size(); i++) {
			slotList.get(0).setVariantStock(variantStockList.get(i));
			// slotList.get(0).setVariantStockList(variantStockList.get(i));
		}
		return new ResponseEntity<List<Slot>>(slotList, HttpStatus.OK);

	}
	

	@GetMapping("getall")
	public ResponseEntity<List<Slot>> getAll() {
		List<Slot> slotList = slotService.getAll();
		return new ResponseEntity<List<Slot>>(slotList, HttpStatus.OK);
	}

	@GetMapping("getlist/{itemListId}")
	public ResponseEntity<Slot> getById1(@PathVariable("itemListId") int itemListId) {
		Slot slot = slotService.getItemListById(itemListId);
		List<ItemList> itemList = itemListService.getByProductId(itemListId);
		slot.setItemList(itemList);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Slot>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Slot> slotListLocal = localData.getSlotList();
		if(slotListLocal.size() <=0) {
			List<Slot> slotList = slotService.getByShopId(shopId);
			slotListLocal = localData.setSlotList(slotList);
		}
	//	List<Slot> slotList = slotService.getByShopId(shopId);
		return new ResponseEntity<List<Slot>>(slotListLocal, HttpStatus.OK);
	}

//	@GetMapping("getbyslotshopid/{shopId}")
//	public ResponseEntity<List<Slot>> getByShopId1(@PathVariable("shopId") String shopId) {
//		List<Slot> slotList = slotService.getByShopId(shopId);
//		for(int i =0; i<slotList.size(); i++) {
//			int slotNumber = slotList.get(i).getId();
//			List<VariantStock> variantStock = variantStockService.getBySlotNumber(slotNumber);
//			for(int j=0; j<variantStock.size(); j++) {
//				slotList.get(i).setVariantStock(variantStock.get(0));
//			}
//		}
//		
//		
//		return new ResponseEntity<List<Slot>>(slotList, HttpStatus.OK);
//	}

	@GetMapping("get/byslotnumber/shopid/{slotNumber}/{shopId}")
	public ResponseEntity<Slot> getSlotNumberAndShopId(@PathVariable("slotNumber") int slotNumber,
			@PathVariable("shopId") String shopId) {
		Slot slot = slotService.getSlotNumberAndShopId(slotNumber, shopId);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}
	
	@GetMapping("check/{slotNumber}/{shopId}/{venderId}")
	public ResponseEntity<Map<String, String>> getSlotNumberAndShopId(@PathVariable("slotNumber") int slotNumber,
			@PathVariable("shopId") String shopId, @PathVariable("venderId") int venderId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean slot = slotService.checkExist(slotNumber,shopId, venderId);
		if(slot) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Created");
		}else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Not created");
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getbyvenderid/{venderId}")
	public ResponseEntity<List<Slot>> getByVenderId(@PathVariable("venderId") int venderId) {
		List<Slot> slotList = slotService.getByVenderId(venderId);
		return new ResponseEntity<List<Slot>>(slotList, HttpStatus.OK);
	}

	@GetMapping("addvariant/{id}/{itemListId}")
	public ResponseEntity<Slot> Addvariant(@PathVariable("id") int id, @PathVariable("itemListId") int itemListId) {
		Slot slot = slotService.Addvariant(id, itemListId);
//		slot.setItemListId(itemListId);
		slot.setVariantCount(slot.getVariantCount() + 1);
		slot.setVariantList(String.valueOf(itemListId));
		slotService.updeteVariant(slot);
		return new ResponseEntity<Slot>(slot, HttpStatus.OK);
	}

//	@GetMapping("addvariant/{id}/{itemListId}")
//	public ResponseEntity<Map<String, String>> Addvariant(@PathVariable("id") int id, @PathVariable("itemListId") int itemListId){
//		Map<String, String> response = new HashMap<String, String>();
//		Slot slot = slotService.Addvariant(id, itemListId);
////		slot.setItemListId(itemListId);
//		slot.setVariantCount(slot.getVariantCount() +1);
//		slot.setVariantList(String.valueOf(itemListId));
//	boolean update =slotService.updeteVariant(slot);
//	if(update) {
//		response.put("status", Boolean.TRUE.toString());
//		response.put("descreption", "update");
//	}else {
//		response.put("status", Boolean.FALSE.toString());
//		response.put("descreption", "Not update");
//	}
//		return ResponseEntity.ok().body(response);
//	}

	@GetMapping("get/bysellername/{nameOfSeller}/{mobileNo}")
	public ResponseEntity<List<Slot>> getAllDetails(@PathVariable("nameOfSeller") String nameOfSeller,
			@PathVariable("mobileNo") String mobileNo) {
		List<Slot> slotList = slotService.getAllDetails(nameOfSeller, mobileNo);
		return new ResponseEntity<List<Slot>>(slotList, HttpStatus.OK);
	}

	@GetMapping("add/variant/{id}/{itemListId}")
	public ResponseEntity<Map<String, String>> addVariantByVariantId(@PathVariable("id") Integer id,
			@PathVariable("itemListId") int itemListId) {
		Map<String, String> response = new HashMap<String, String>();
		Slot slot = slotService.getById(id);
		boolean resutl = slotService.addVariantByVariantId(slot, itemListId);
		if (resutl) {
			response.put("status", Boolean.TRUE.toString());
			response.put("descreption", "Add wishList");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("descreption", "Not added wishList");
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.BILLING_NUMBER));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID)) {
			String shopId = json.get(ServiceConstants.SHOP_ID);
			int gstAmount = 0, slotNumber = 0, paid = 0, billingAmount = 0, totalAmount = 0, dues = 0;
			Slot slot = new Slot(slotNumber);
			List<Slot> slotList  = null;
			slotList = slotService.getByShopId(shopId);    
			slotNumber = slotList.size() + 1;
			int venderId = Integer.parseInt(json.get(ServiceConstants.VENDER_ID));
			Vender vender = venderService.getById(venderId);
			int gstType = vender.getGstType();
			
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			slot.setVenderId(venderId);
			slot.setBillingNumber(json.get(ServiceConstants.BILLING_NUMBER));             
			slot.setActive(Boolean.TRUE);
			slot.setCreatedOn(new Date());
			slot.setDeleted(Boolean.FALSE);
			slot.setGstType(gstType);
			slot.setShopId(shopId);
			slot.setSlotNumber(slotNumber);

			if (null != json.get(ServiceConstants.BILLING_AMOUNT)) {
				billingAmount = Integer.parseInt(json.get(ServiceConstants.BILLING_AMOUNT));
				slot.setBillingAmount(billingAmount);
			}
			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT));
				slot.setGstAmount(gstAmount);
			}

			if (null != json.get(ServiceConstants.NAME_OF_SELLER)) {
				slot.setNameOfSeller(json.get(ServiceConstants.NAME_OF_SELLER));
			}

			if (null != json.get(ServiceConstants.PAID)) {
				paid = Integer.parseInt(json.get(ServiceConstants.PAID));
				slot.setPaidAmount(paid);
			}
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				slot.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			}

			if (null != json.get(ServiceConstants.CREDIT_DAYS)) {
				String creditDays = json.get(ServiceConstants.CREDIT_DAYS);
				slot.setCreditDays(creditDays);
			}

			try {
				slot.setSlotDate(new SimpleDateFormat("yyyy-MM-dd").parse(json.get(ServiceConstants.SLOT_DATE)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (null != json.get(ServiceConstants.BILLING_AMOUNT) && null != json.get(ServiceConstants.PAID)) {
				totalAmount = (gstAmount + billingAmount);
				dues = totalAmount - paid;
				slot.setTotalAmount(totalAmount);
				slot.setDues(dues);
			}

			if (slotService.slotExit(slot.getBillingNumber(), slot.getVenderId())){
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "Slot all ready created by bill number");
			} else {
				slotService.slotCreate(slot);	
				localData.setSlotList(new ArrayList<Slot>());
				vender.setPurchaseAmount(vender.getPurchaseAmount() + slot.getBillingAmount());
				vender.setPaid(vender.getPaid() + slot.getPaidAmount());
				vender.setDues(vender.getDues() + slot.getDues());
				venderService.updateVender(vender);
				response.put("status", Boolean.TRUE.toString());
				response.put("slotId", String.valueOf(slot.getId()));
				response.put("slotNumber", String.valueOf(slot.getSlotNumber()));
				response.put("descreption", "Slot created");
			}

		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateImage(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != slotService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Slot slot = slotService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

//			if(null != json.get(ServiceConstants.SLOT_NUMBER)) {
//				String slotNumber = json.get(ServiceConstants.SLOT_NUMBER);
//				slot.setSlotNumber(slotNumber);				
//			}
			int venderId = slot.getVenderId();
			Vender vender = venderService.getById(venderId);
					
			if (null != json.get(ServiceConstants.BILLING_AMOUNT)) {    
				int billingAmount = Integer.parseInt(json.get(ServiceConstants.BILLING_AMOUNT));
				slot.setBillingAmount(billingAmount);
				vender.setPurchaseAmount(vender.getPurchaseAmount() + billingAmount);
				slot.setTotalAmount(slot.getBillingAmount() + billingAmount);
				venderService.updateVender(vender);
				
			}
			
			if (null != json.get(ServiceConstants.BILLING_AMOUNT)) {    
				int billingAmount = Integer.parseInt(json.get(ServiceConstants.BILLING_AMOUNT));
				slot.setBillingAmount(billingAmount);
				int purchaseAmount = vender.getPurchaseAmount()- slot.getBillingAmount() + billingAmount;
				vender.setPurchaseAmount(purchaseAmount);
				slot.setTotalAmount(slot.getBillingAmount() + billingAmount);
				venderService.updateVender(vender);
				
			}

			if (null != json.get(ServiceConstants.BILLING_NUMBER)) {
				String billingNumber = (json.get(ServiceConstants.BILLING_NUMBER));
				slot.setBillingNumber(billingNumber);
			}

			if (null != json.get(ServiceConstants.PAID)) {
				int paidAmount = Integer.parseInt(json.get(ServiceConstants.PAID));
				vender.setPaid(vender.getPaid() + paidAmount);
				vender.setDues(vender.getDues() - paidAmount);
				vender.setPurchaseAmount(vender.getPurchaseAmount() + paidAmount);
				slot.setPaidAmount(paidAmount);
				venderService.updateVender(vender);
			}
			
//			if(null != json.get(ServiceConstants.BILLING_AMOUNT)) {
//				int bill = Integer.parseInt(json.get(ServiceConstants.BILLING_AMOUNT));
//				slot.setBillingAmount(bill);
//			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				float gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
				slot.setGstAmount(gstAmount);
			}

			if (null != json.get(ServiceConstants.ITEM_LIST_ID)) {
				int itemListId = Integer.parseInt(json.get(ServiceConstants.ITEM_LIST_ID));
				slot.setItemListId(itemListId);
			}

			if (null != json.get(ServiceConstants.NAME_OF_SELLER)) {
				String nameOfSeller = (json.get(ServiceConstants.NAME_OF_SELLER));
				slot.setNameOfSeller(nameOfSeller);
			}
			if (null != json.get(ServiceConstants.PAID_AMOUNT)) {
				int paidAmount = Integer.parseInt(json.get(ServiceConstants.PAID_AMOUNT));
				slot.setPaidAmount(paidAmount);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				slot.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				slot.setMobileNo(mobileNo);
			}

			boolean update = slotService.updeteSlot(slot);
			if (update) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Image updated");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Image doesn't exist with given ShopId");
			}

		}
		return ResponseEntity.ok().body(response);
	}

}
