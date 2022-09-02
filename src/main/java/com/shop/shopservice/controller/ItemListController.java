package com.shop.shopservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.ExcelStringEntity;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.entity.Vender;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.ICategoryService;
import com.shop.shopservice.service.IImageService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.IMeasurementService;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.ISubCategoryService;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.service.IVenderService;
import com.shop.shopservice.utils.Calculation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/itemlist")
public class ItemListController {
	private final Logger log = LoggerFactory.getLogger(ItemListController.class);

	@Autowired
	private IItemListService itemListService;

	@Autowired
	private ISubCategoryService subCategoryService;

	@Autowired
	private IMeasurementService measurementService;

	@Autowired
	private IAdminService adminService;

//	@Autowired
//	private IVariantStockService variantStockService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private IItemService itemService;

	@Autowired
	private IVenderService venderService;

	@Autowired
	private IImageService imageService;

	@Autowired
	private ISlotService slotService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;

	@Autowired
	private IVariantStockService variantStockService;

	HashMap<String, List<ItemList>> map = new HashMap<String, List<ItemList>>();
//	List<ItemList> allItemList = null;
	LocalData localData = new LocalData();
//	HashMap<String, ItemList> map = new HashMap<String, List<ItemList>>();
	List<ItemList> itemList = new ArrayList<ItemList>();

	@GetMapping("get/{id}")
	public ResponseEntity<ItemList> getById(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getById(id);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getdeactive/{id}")
	public ResponseEntity<ItemList> getDeactiveById(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getDeactiveById(id);
		itemList.setActive(Boolean.FALSE);
		itemListService.updateItemList(itemList);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getall2")
	public ResponseEntity<List<ItemList>> getAll2() {
		List<ItemList> itemList2 = localData.getVariantData();
		if (itemList2.size() <= 0) {
			itemList = itemListService.getAll();
			itemList2 = localData.setVariantData(itemList);
		}
		return new ResponseEntity<List<ItemList>>(itemList2, HttpStatus.OK);
	}

	@GetMapping("setall")
	public ResponseEntity<List<ItemList>> setAll() {
		List<ItemList> itemList2 = localData.getVariantData();
		if (itemList2.size() <= 0) {
			itemList = itemListService.getAll();
			itemList2 = localData.setVariantData(itemList);
		}
		itemList2.get(0).setStock(itemList2.get(0).getStock() - 1);
		itemList2 = localData.setVariantData(itemList2);
		return new ResponseEntity<List<ItemList>>(itemList2, HttpStatus.OK);
	}

	@GetMapping("getall1")
	public ResponseEntity<List<ItemList>> getAll1() {
		itemList = itemListService.getAll();
//		for(int i=0; i<itemList.size(); i++) {
//			int productId = itemList.get(i).getProductId();
//			Item item = itemService.getById(productId);
//			itemList.get(i).setItem(item);
//		}
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("deactive/forexcel/{id}")
	public ResponseEntity<ItemList> getDeActiveForExcel(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getDeActiveForExcel(id);
		itemList.setActive(Boolean.FALSE);
		itemList.setDeleted(Boolean.TRUE);
		itemListService.updateItemList(itemList);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getdeactive/byid/{id}/{slotId}")
	public ResponseEntity<ItemList> getDeactveVender(@PathVariable("id") int id, @PathVariable("slotId") int slotId) {
		ItemList itemList = itemListService.getById(id);
		itemList.setDeleted(Boolean.TRUE);
		itemList.setActive(Boolean.FALSE);
		boolean update = itemListService.updateItemList(itemList);
		if (update) {
			Slot slot = slotService.getById(slotId);
			slot.setVariantCount(slot.getVariantCount() - 1);
			slot.setNetAmount(slot.getNetAmount() - itemList.getNetAmount());
			boolean updateSlot = slotService.updeteSlot(slot);
			if (updateSlot) {
				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotId, id);
				variantStock.setActive(Boolean.FALSE);
				variantStock.setDeleted(Boolean.FALSE);
				variantStockService.updateStock(variantStock);
			}
		}

		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getbyvender/{id}")
	public ResponseEntity<ItemList> getAllvenderById(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getById(id);
		String shopId = itemList.getShopId();
		int venderId = itemList.getVenderId();
		Vender vender = venderService.getById(venderId);
		itemList.setVender(vender);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getall/variant/onlinebyshopid/{shopId}/{isOnline}")
	public ResponseEntity<List<ItemList>> getAllOnlineVariantByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("isOnline") boolean isOnline) {
		List<ItemList> itemList = itemListService.getAllOnlineVariantByShopId(shopId, isOnline);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getall/details/{id}")
	public ResponseEntity<ItemList> getById1(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getById(id);
		if (null != itemList) {
			String shopId = itemList.getShopId();
			int productId = itemList.getProductId();
			List<Image> imageList = imageService.getImageByShopIdAndProductId(shopId, productId);
			itemList.setImage(imageList);
			Item item = itemService.getById(productId);
			itemList.setItem(item);
		}
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getall")
	public ResponseEntity<List<ItemList>> getAll() {
		List<ItemList> itemList = itemListService.getAll();
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/online/{isOnline}")
	public ResponseEntity<List<ItemList>> getAllOnlineVariant(@PathVariable("isOnline") boolean isOnline) {
		List<ItemList> itemList = itemListService.getAllOnlineVariant(isOnline);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("onlinevariant/{productId}/{isOnline}")
	public ResponseEntity<List<ItemList>> getAllOnlineProductByProductId(@PathVariable("productId") int productId,
			@PathVariable("isOnline") boolean isOnline) {
		List<ItemList> itemList = itemListService.getAllOnlineProductByProductId(productId, isOnline);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/onlineproduct/{isOnline}")
	public ResponseEntity<List<ItemList>> getAllOnlineVariantAndProduct(@PathVariable("isOnline") boolean isOnline) {
		List<ItemList> itemList = itemListService.getAllOnlineVariant(isOnline);
		for (int i = 0; i < itemList.size(); i++) {
			int productId = itemList.get(i).getProductId();
			Item item = itemService.getById(productId);
			itemList.get(i).setItem(item);
		}
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getproductid/{productId}")
	public ResponseEntity<List<ItemList>> getProductByProductId(@PathVariable("productId") int productId) {
		List<ItemList> itemList = itemListService.getProductByProductId(productId);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getvariant/productid/{productId}")
	public ResponseEntity<ItemList> getVariantByProductId(@PathVariable("productId") int productId) {
		ItemList itemList = itemListService.getVariantByProductId(productId);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/hotsell/{hotSell}")
	public ResponseEntity<List<ItemList>> getAllHotSellVariant(@PathVariable("hotSell") boolean hotSell) {
		List<ItemList> itemList = itemListService.getAllHotSellVariant(hotSell);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/baggage/{baggage}")
	public ResponseEntity<List<ItemList>> getAllBaggageVariant(@PathVariable("baggage") boolean baggage) {
		List<ItemList> itemList = itemListService.getAllBaggageVariant(baggage);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/milaanoffer/{milaanOffer}")
	public ResponseEntity<List<ItemList>> getAllMilaanOfferVariant(@PathVariable("milaanOffer") boolean milaanOffer) {
		List<ItemList> itemList = itemListService.getAllMilaanOfferVariant(milaanOffer);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/aditionaldiscount/{aditionalDiscount}")
	public ResponseEntity<List<ItemList>> getAllAditionalDiscountVariant(
			@PathVariable("aditionalDiscount") boolean aditionalDiscount) {
		List<ItemList> itemList = itemListService.getAllAditionalDiscountVariant(aditionalDiscount);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getalldetails/byid/{id}")
	public ResponseEntity<ItemList> getAllDetailsById(@PathVariable("id") int id) {
		ItemList itemList = itemListService.getAllDetailsById(id);
		int productId = itemList.getProductId();
		String shopId = itemList.getShopId();
		String slotNumber = itemList.getSlotList();
		Item item = itemService.getById(productId);
		List<Image> imegeList = imageService.getImageByShopIdAndProductId(shopId, productId);
		List<Slot> slotList = new ArrayList<Slot>();
		if (null != slotNumber && slotNumber.length() > 0) {
			String[] array = slotNumber.split(",");
			List<String> myArray = Arrays.asList(array);
			for (int j = 0; j < myArray.size(); j++) {
				Slot slot = slotService.getSlotNumberAndShopId(Integer.parseInt(myArray.get(j)), shopId);
				slotList.add(slot);
				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slot.getId(), id);
				slotList.get(0).setVariantStock(variantStock);
			}
			itemList.setSlot(slotList);
		}
		itemList.setImage(imegeList);
		itemList.setItem(item);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getbybarcode/{barcode}")
	public ResponseEntity<List<ItemList>> getByBarcode(@PathVariable("barcode") String barcode) {
		List<ItemList> itemList = itemListService.getByBarcode(barcode);
		if (null != itemList && itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				int productId = itemList.get(i).getProductId();
				String shopId = itemList.get(i).getShopId();
				String slotNumber = itemList.get(i).getSlotList();
				int variantId = itemList.get(i).getId();
				Item item = itemService.getById(productId);
				List<Image> imageList = imageService.getImageByShopIdAndProductId(shopId, productId);
				List<Slot> slotList = new ArrayList<Slot>();
				if (null != slotNumber && slotNumber.length() > 0) {
					String[] array = slotNumber.split(",");
					List<String> myArray = Arrays.asList(array);
					for (int j = 0; j < myArray.size(); j++) {
						Slot slot = slotService.getSlotNumberAndShopId(Integer.parseInt(myArray.get(j)), shopId);
						VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slot.getId(),
								variantId);
						slotList.add(slot);
						slotList.get(0).setVariantStock(variantStock);
					}
					itemList.get(i).setSlot(slotList);
				}
				itemList.get(i).setImage(imageList);
				itemList.get(i).setItem(item);
			}
		}

		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

//	@GetMapping("getbyshopid/{shopId}")
//	public ResponseEntity<List<ItemList>> getAllItemListByShopId(@PathVariable("shopId") String shopId) {
//		List<ItemList> itemList = itemListService.getAllItemListByShopId(shopId);		
//		if (null != itemList && itemList.size() > 0) {
//			for (int i = 0; i < itemList.size(); i++) {
//				int productId = itemList.get(i).getProductId();
//				Item item = itemService.getById(productId);
//				itemList.get(i).setItem(item);
//			}
//		}
//
//		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
//	}

//	@GetMapping("getbyshopid/{shopId}")
//	public ResponseEntity<List<ItemList>> getAllItemListByShopId(@PathVariable("shopId") String shopId) {
//		List<ItemList> itemList = itemListService.getAllItemListByShopId(shopId);
//		List<Item> allItem = itemService.getAllItemByShopId(shopId);
//		if (null != itemList && itemList.size() > 0) {
//			for (int i = 0; i < itemList.size(); i++) {
//				Item item = null;
//				int productId = itemList.get(i).getProductId();
//				for (int j = 0; j < allItem.size(); j++) {
//					if (productId == allItem.get(j).getId()) {
//						item = allItem.get(j);
//					}
//				}
//				itemList.get(i).setItem(item);
//			}
//		}
//
//		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
//	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<ItemList>> getAllItemListByShopId4(@PathVariable("shopId") String shopId) {
		List<ItemList> itemListLocal = localData.getVariantData();
		List<Item> itemLocal = localData.getItem();
		if (itemListLocal.size() <= 0) {
			List<ItemList> itemList = itemListService.getAllItemListByShopId(shopId);
			itemListLocal = localData.setVariantData(itemList);
		}
		if (itemLocal.size() <= 0) {
			List<Item> allItem = itemService.getAllItemByShopId(shopId);
			itemLocal = localData.setItem(allItem);
		}
		if (null != itemListLocal && itemListLocal.size() > 0) {
			for (int i = 0; i < itemListLocal.size(); i++) {
				Item item = null;
				int productId = itemListLocal.get(i).getProductId();
				for (int j = 0; j < itemLocal.size(); j++) {
					if (productId == itemLocal.get(j).getId()) {
						item = itemLocal.get(j);
					}
				}
				itemListLocal.get(i).setItem(item);
			}
		}

		return new ResponseEntity<List<ItemList>>(itemListLocal, HttpStatus.OK);
	}

	@GetMapping("getbyshopid1/{shopId}")
	public ResponseEntity<List<ItemList>> getAllItemListByShopId5(@PathVariable("shopId") String shopId) {
		List<ItemList> itemList = itemListService.getAllItemListByShopId(shopId);
		List<Item> allItem = itemService.getAllItemByShopId(shopId);
		if (null != itemList && itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				Item item = null;
				int productId = itemList.get(i).getProductId();
				int indexOfElement = IntStream.range(0, allItem.size()).filter(k -> productId == allItem.get(k).getId())
						.findFirst().orElse(-1);
//				for(int j = 0; j < allItem.size(); j++) {
//					if(productId == allItem.get(j).getId()) {
				item = allItem.get(indexOfElement);
//					}
//				}

				itemList.get(i).setItem(item);
			}
		}

		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("add/variant/by/{id}/{slotNumber}")
	public ResponseEntity<Map<String, String>> addVariantBySlotNumber(@PathVariable("id") Integer id,
			@PathVariable("slotNumber") String slotNumber) {
		Map<String, String> response = new HashMap<String, String>();
		ItemList itemList = itemListService.getById(id);
		boolean resutl = itemListService.addVariantBySlotNumber(itemList, slotNumber);
		return ResponseEntity.ok().body(response);

	}

	@GetMapping("check/{productId}/{shopId}/{unitQuantity}/{slotList}")
	public ResponseEntity<Map<String, String>> checkExit(@PathVariable("productId") Integer productId,
			@PathVariable("shopId") String shopId, @PathVariable("unitQuantity") float unitQuantity,
			@PathVariable("slotList") String slotList) {
		Map<String, String> response = new HashMap<String, String>();
		// ItemList itemList = itemListService.getById(id);
		boolean resutl = itemListService.checkExist(productId, shopId, unitQuantity, slotList);
		if (resutl) {
			response.put("status", Boolean.TRUE.toString());
			response.put("Descreption", "ItemList created");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Descreption", "ItemList not created");
		}
		return ResponseEntity.ok().body(response);

	}

	@GetMapping("getby/{productId}/{shopId}/{unitQuantity}/{slotList}")
	public ResponseEntity<ItemList> getForExcel(@PathVariable("productId") Integer productId,
			@PathVariable("shopId") String shopId, @PathVariable("unitQuantity") float unitQuantity,
			@PathVariable("slotList") String slotList) {
		ItemList itemList = itemListService.getForExcel(productId, shopId, unitQuantity, slotList);
		return new ResponseEntity<ItemList>(itemList, HttpStatus.OK);
	}

	@GetMapping("getbyproductid/{productId}")
	public ResponseEntity<List<ItemList>> getByProductId(@PathVariable("productId") int productId) {
		List<ItemList> itemList = itemListService.getByProductId(productId);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);

	}

	@GetMapping("fordelete/{productId}")
	public ResponseEntity<List<ItemList>> getByProductIdForDelete(@PathVariable("productId") int productId) {
		List<ItemList> itemList = itemListService.getByProductIdForDelete(productId);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);

	}

	@GetMapping("getbyproductidshopid/{productId}")
	public ResponseEntity<List<ItemList>> getByProductId1(@PathVariable("productId") int productId) {
		List<ItemList> itemList = itemListService.getByProductId(productId);
		List<ItemList> itemList1 = null;
		String shopId = "MILAAN63";
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getShopId() == shopId) {

				itemList1 = itemListService.getAllItemListByShopId(shopId);
			}

		}
		return new ResponseEntity<List<ItemList>>(itemList1, HttpStatus.OK);

	}

	@GetMapping("getallbyshopid/{shopId}")
	public ResponseEntity<List<ItemList>> getByShopId(@PathVariable("shopId") String shopId) {
		List<ItemList> itemList = itemListService.getByShopId(shopId);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);

	}

	@GetMapping("getallbyshopid4/{shopId}")
	public ResponseEntity<Map<String, String>> getByShopId4(@PathVariable("shopId") String shopId)
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, String> map = localData.getMap();
		if (map.size() <= 0) {
			List<ItemList> itemList = itemListService.getByShopId(shopId);
			for (int i = 0; i < itemList.size(); i++) {
				String stringData = objectMapper.writeValueAsString(itemList.get(i));
				map.put("prod_" + itemList.get(i).getId(), stringData);
				// map.put("prod_5",stringData);
			}

			localData.setMap(map);

		}

		return ResponseEntity.ok().body(map);

	}

//	@GetMapping("getbyshopid/local/{shopId}")
//	public ResponseEntity<List<ItemList>> getAllItemListByShopId6(@PathVariable("shopId") String shopId) throws JsonProcessingException {
//		ObjectMapper objectMapper = new ObjectMapper();  
//		HashMap<String, String> map = localData.getMap();
//		//List<ItemList> itemListLocal = localData.getVariantData();
//		List<Item> itemLocal = localData.getItem();
//		if(map.size() <= 0) {
//			List<ItemList> itemList = itemListService.getByShopId(shopId);
//		for(int i=0; i< itemList.size(); i++) {
//			 String stringData  = objectMapper.writeValueAsString(itemList.get(i));
//			map.put("prod_"+itemList.get(i).getId(), stringData);
//			// map.put("prod_5",stringData);
//		}
//		
//		localData.setMap(map);
//		
//		}
//		if (map.size() <= 0) {
//			List<Item> allItem = itemService.getAllItemByShopId(shopId);
//			for(int i=0; i< allItem.size(); i++) {
//				 String stringData  = objectMapper.writeValueAsString(allItem.get(i));
//				map.put("prod_"+allItem.get(i).getId(), stringData);
//				// map.put("prod_5",stringData);
//			}
//			
//			localData.setMap(map);
//			
//			}
//		
//		if (null != itemListLocal && itemListLocal.size() > 0) {
//			for (int i = 0; i < itemListLocal.size(); i++) {
//				Item item = null;
//				int productId = itemListLocal.get(i).getProductId();
//				for (int j = 0; j < itemLocal.size(); j++) {
//					if (productId == itemLocal.get(j).getId()) {
//						item = itemLocal.get(j);
//					}
//				}
//				itemListLocal.get(i).setItem(item);
//			}
//		}
//
//		return new ResponseEntity<List<ItemList>>(itemListLocal, HttpStatus.OK);
//	}

	@GetMapping("getallbyshopid/forlocal/{shopId}")
	public ResponseEntity<List<ItemList>> getByShopIdForLocal(@PathVariable("shopId") String shopId) {
		List<ItemList> localItemList = localData.getVariantData();
		if (localItemList.size() <= 0) {
			List<ItemList> itemList = itemListService.getByShopId(shopId);
			localItemList = localData.setVariantData(itemList);
		}

		List<Item> localItem = localData.getItem();
		if (localItem.size() <= 0) {
			List<Item> item = itemService.getAllItem(shopId);
			localItem = localData.setItem(item);
		}

		for (int i = 0; i < localItemList.size(); i++) {
			int productId = localItemList.get(i).getProductId();
			Item item = localItem.stream().filter(itm -> productId == itm.getId()).findAny().orElse(null);
			localItemList.get(i).setItem(item);
		}
		// localData.setVariantData(localHost);
		return new ResponseEntity<List<ItemList>>(localItemList, HttpStatus.OK);

	}

//	@GetMapping("getallbyshopidhasmap/{shopId}")
//	public ResponseEntity<Map<String, List<ItemList>>> getByShopId3(@PathVariable("shopId") String shopId) {
////		HashMap<String, List<ItemList>> map=new HashMap<String, List<ItemList>>();
////		List<ItemList> itemList = null;
//
////		System.out.print("Map" +map);
//		if (null != allItemList) {
////			allItemList = itemListService.getByShopId(shopId);
//			map.put("data", allItemList);
////			System.out.print("Map" +map);
//		} else {
//
//		}
//		return new ResponseEntity<Map<String, List<ItemList>>>(map, HttpStatus.OK);
//	}

//	@GetMapping("getallbyshopid/{shopId}")
//	public ResponseEntity<List<ItemList>> getByShopId1(@PathVariable("shopId") String shopId) {
//		List<ItemList> itemList = itemListService.getByShopId(shopId);
//		for(int i= 0; i> itemList.size(); i++) {
//			if(itemList.get(i).getS)
//		}
//		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
//
//	}

	@GetMapping("getallrrbyshopid/{shopId}")
	public ResponseEntity<List<ItemList>> getAllByShopId(@PathVariable("shopId") String shopId) {
		List<ItemList> itemList = itemListService.getAllByShopId(shopId);
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getallbyshopid/item/{shopId}")
	public ResponseEntity<List<ItemList>> getAllInformationByShopId(@PathVariable("shopId") String shopId) {
		List<Item> product = itemService.getAll();
		List<ItemList> itemList = itemListService.getAllByShopId(shopId);
		for (int i = 0; i < itemList.size(); i++) {
			for (int j = 0; j < product.size(); j++) {
				if (itemList.get(i).getProductId() == product.get(j).getId()) {
					Item item = product.get(j);
					itemList.get(i).setItem(item);
				}

			}

		}
		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

//	@SuppressWarnings({})
//	@PostMapping("/create")
//	ResponseEntity<Map<String, String>> createItemList(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE)),
//				gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT)),
//				quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY)),
//				costPrice = Integer.parseInt(json.get(ServiceConstants.COST_PRICE)),
//				unitSellingPrice = Integer.parseInt(json.get(ServiceConstants.UNIT_SELLING_PRICE)),
//				outOfStock = Integer.parseInt(json.get(ServiceConstants.OUT_OF_STOCK)),
//				stock = Integer.parseInt(json.get(ServiceConstants.STOCK)),
//				measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT)),
//				productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)),
//				unitQuantity = Integer.parseInt(json.get(ServiceConstants.UNIT_QUANTITY));
//
//		boolean offerActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND)),
//				isGstIncluded = Boolean.parseBoolean(json.get(ServiceConstants.IS_GST_INCLUDED));
//		String shopId = json.get(ServiceConstants.SHOP_ID), exipryDate = null, manufacturingDate = null, hsnCode = "";
//
//		Admin admin = adminService.getAdminByShopId(shopId);
//		VariantStock variantStock = new VariantStock();
//		String shopName = admin.getShopName();
//
//		Item item = itemService.getById(productId);
//
//		String name = item.getName();
//		Slot slotList = null;
//		Calculation calculation = new Calculation();
//		ItemList itemList = new ItemList();
//
////		Slot slot = slotService.getSlotNumberAndShopId(Integer.parseInt(slotNumber), shopId);
//
//		float discount = 0, gstAmount = 0, oldPrice = 0, totalAmount = 0, price = 0, sellingPrice = 0;
//
//		LocalDate offerTo = null, offerFrom = null;
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		sellingPrice = unitSellingPrice * quantity;
//		if (offerActiveInd) {
//			int offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
//			discount = calculation.DecimalCalculation((sellingPrice * offerPercent) / (float) 100);
//			price = calculation.DecimalCalculation(sellingPrice - discount);
//			gstAmount = calculation.DecimalCalculation((price / (100 + gstPercent)) * gstPercent);
//			oldPrice = calculation.DecimalCalculation(sellingPrice);
//			totalAmount = price;
//
//			itemList.setPrice(price);
//			itemList.setOldPrice((int) oldPrice);
//			itemList.setDiscount((int) discount);
//			itemList.setOfferPercent(offerPercent);
//			itemList.setGstAmount((int) gstAmount);
//			itemList.setTotalAmount((int) price);
//
//			try {
//				offerFrom = LocalDate.parse(json.get(ServiceConstants.OFFER_FROM), formatter);
//				offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO), formatter);
//				itemList.setOfferFrom(offerFrom);
//				itemList.setOfferTo(offerTo);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			gstAmount = calculation.DecimalCalculation((sellingPrice / (100 + gstPercent)) * gstPercent);
//			totalAmount = calculation.DecimalCalculation(sellingPrice);
//			itemList.setPrice(sellingPrice);
//			itemList.setGstAmount((int) gstAmount);
//			itemList.setTotalAmount((int) totalAmount);
//			itemList.setOldPrice((int) sellingPrice);
//		}
//		itemList.setMeasurement(measurement);
//		if (null != json.get(ServiceConstants.HSN_CODE)) {
//			hsnCode = json.get(ServiceConstants.HSN_CODE);
//			itemList.setHsnCode(hsnCode);
//		}
//
//		if (null != json.get(ServiceConstants.DATE_OF_MANUFACTURING)) {
//			manufacturingDate = json.get(ServiceConstants.DATE_OF_MANUFACTURING);
//			LocalDate dateOfManufacturing = null;
//			try {
//				System.out.println("Date" + manufacturingDate);
//				dateOfManufacturing = LocalDate.parse(manufacturingDate, formatter);
//				itemList.setDateOfManufacturing(dateOfManufacturing);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		itemList.setActive(Boolean.TRUE);
//		itemList.setCreatedOn(new Date());
//
//		itemList.setDeleted(Boolean.FALSE);
//		itemList.setSellingPrice((int) sellingPrice);
//		itemList.setUnitQuantity(unitQuantity);
//		itemList.setOfferActiveInd(offerActiveInd);
//		itemList.setOfferAvailable(Boolean.TRUE);
//		itemList.setCostPrice(costPrice);
//		itemList.setGstPercent(gstPercent);
//		itemList.setQuantity(quantity);
//		itemList.setStock(stock);
//		itemList.setShopName(shopName);
//		itemList.setOutOfStock(outOfStock);
//		itemList.setUnitSellingPrice(unitSellingPrice);
////		itemList.setUserId(json.get(ServiceConstants.USER_ID));
//		itemList.setProductId(productId);
//		itemList.setShopId(shopId);
//
//		if (null != json.get(ServiceConstants.SLOT_LIST)) {
//			String slotNumber = json.get(ServiceConstants.SLOT_LIST);
//			slotList = slotService.getSlotNumberAndShopId(Integer.parseInt(slotNumber), shopId);
//			itemList.setSlotList(slotNumber);
//			itemList.setSlotCount(1);
//		}
//
////		itemList.setCartId(Integer.parseInt(json.get(ServiceConstants.CART_ID)));
//		itemList.setName(name);
//
//		if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
//			itemList.setDeliveryCharge(deliveryCharge);
//		}
//
//		if (null != json.get(ServiceConstants.BARCODE)) {
//			itemList.setBarcode(json.get(ServiceConstants.BARCODE));
//		}
//
//		if (itemListService.itemListExits(itemList.getShopId(), itemList.getQuantity(), itemList.getUnitQuantity(),
//				itemList.getUnitSellingPrice(), itemList.getProductId(), itemList.getName())) {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("slotNumber", itemList.getSlotList());
//			response.put("Descreption", "ItemList all ready created");
//		} else {
//			itemListService.itemListCreate(itemList);
//			response.put("itemListId", String.valueOf(itemList.getId()));
//			if (null != json.get(ServiceConstants.SLOT_LIST)) {
//				slotService.addVariantByVariantId(slotList, itemList.getId());
//
//				variantStock.setActive(Boolean.TRUE);
//				variantStock.setCreatedOn(new Date());
//				variantStock.setSlotNumber(slotList.getId());
//				variantStock.setStock(stock);
//				variantStock.setVariantId(itemList.getId());
//				variantStock.setDeleted(Boolean.FALSE);
//				variantStock.setShopId(shopId);
//				variantStock.setCurrentStock(stock);
//				variantStock.setSoldStock(0);
//
//				variantStockService.stockCreated(variantStock);
//			}
//			response.put("status", Boolean.TRUE.toString());
//			response.put("Descreption", "ItemList created");
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@SuppressWarnings({})
	@PostMapping("/create1")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		int stock = Integer.parseInt(json.get(ServiceConstants.STOCK)),
				measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT)),
				productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)),

				vanderId = Integer.parseInt(json.get(ServiceConstants.VENDER_ID));
		float mrp = Float.parseFloat(json.get(ServiceConstants.MRP)),
				unitQuantity = Float.parseFloat(json.get(ServiceConstants.UNIT_QUANTITY)),
				costPrice = Float.parseFloat(json.get(ServiceConstants.COST_PRICE)),
				unitSellingPrice = Float.parseFloat(json.get(ServiceConstants.UNIT_SELLING_PRICE)),
				purchasePrice = Float.parseFloat(json.get(ServiceConstants.PURCHASE_PRICE)),
				netAmount = Float.parseFloat(json.get(ServiceConstants.NET_AMOUNT)), gstAmount = 0;

		boolean offerActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND)),

				isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));

		String shopId = json.get(ServiceConstants.SHOP_ID),

				exipryDate = null, manufacturingDate = null;

		ItemList itemList = new ItemList();

		Admin admin = adminService.getAdminByShopId(shopId);
		VariantStock variantStock = new VariantStock();
		String shopName = admin.getShopName();

		Item item = itemService.getById(productId);
		int categoryId = item.getCategory();
		int subcategoryId = item.getSubCategoryId();
		int brandId = item.getBrand();
		Category category = categoryService.getCategory(categoryId);
		SubCategory subCategory = subCategoryService.getById(subcategoryId);
		Brand brand = brandService.getBrand(brandId);
		String name = item.getName();
		Slot slotList = null;
		Calculation calculation = new Calculation();

		Vender vender = venderService.getById(vanderId);
		int gstType = vender.getGstType();
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "CGST");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "SGST");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "IGST");
		LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "UTGST");
		LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "LOCAL_PURCHASE");
		LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "GST_FREE");
		int cGst = lookUp.getLookUpId();
		int sGst = lookUp1.getLookUpId();
		int iGst = lookUp2.getLookUpId();
		int utGst = lookUp3.getLookUpId();
		int localPurchase = lookUp4.getLookUpId();
//		int gstFree = lookUp5.getLookUpId();

		LocalDate offerTo = null, offerFrom = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (null != json.get(ServiceConstants.GST_AMOUNT)) {
			gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
			itemList.setGstAmount(gstAmount);
		}
		float amount = 0;
		if (gstType == cGst) {
			// gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
			amount = gstAmount / 2;
			vender.setCgstAmount(calculation.DecimalCalculation(vender.getCgstAmount() + amount));
			vender.setIgstAmount(calculation.DecimalCalculation(vender.getIgstAmount() + amount));

		} else if (gstType == sGst) {
			amount = gstAmount / 2;
			vender.setSgstAmount(calculation.DecimalCalculation(vender.getSgstAmount() + amount));
			vender.setIgstAmount(calculation.DecimalCalculation(vender.getIgstAmount() + amount));
		} else if (gstType == iGst) {
			amount = gstAmount / 3;
			vender.setIgstAmount(calculation.DecimalCalculation(vender.getIgstAmount() + amount));
			vender.setCgstAmount(calculation.DecimalCalculation(vender.getCgstAmount() + amount));
			vender.setSgstAmount(calculation.DecimalCalculation(vender.getSgstAmount() + amount));
		} else if (gstType == utGst) {
			amount = gstAmount / 2;
			vender.setUtgstAmount(calculation.DecimalCalculation(vender.getUtgstAmount() + amount));
			vender.setIgstAmount(calculation.DecimalCalculation(vender.getIgstAmount() + amount));

		} else if (gstType == localPurchase) {
			vender.setIgstAmount(0);
			vender.setCgstAmount(0);
			vender.setSgstAmount(0);
			vender.setUtgstAmount(0);
		} else {
			vender.setIgstAmount(0);
			vender.setCgstAmount(0);
			vender.setSgstAmount(0);
			vender.setUtgstAmount(0);
		}
		if (null != json.get(ServiceConstants.BARCODE)) {
			String barcode = json.get(ServiceConstants.BARCODE);
			itemList.setBarcode(barcode);
		}
		if (null != json.get(ServiceConstants.HSN_CODE)) {
			String hsnCode = json.get(ServiceConstants.HSN_CODE);
			itemList.setHsnCode(hsnCode);
		}

		if (null != json.get(ServiceConstants.IS_ONLINE)) {
			isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
			itemList.setOnline(isOnline);
			item.setOnline(isOnline);
			itemService.updateItem(item);
			brand.setOnline(isOnline);
			brandService.updateBrand(brand);
			category.setOnline(isOnline);
			categoryService.updateCategory(category);
			subCategory.setOnline(isOnline);
			subCategoryService.upDateSubCategory(subCategory);
		}

		if (null != json.get(ServiceConstants.VENDER_ID)) {
			int venderId = Integer.parseInt(json.get(ServiceConstants.VENDER_ID));
			itemList.setVenderId(venderId);
		}

		if (null != json.get(ServiceConstants.GST_PERCENT)) {
			int gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT));
			itemList.setGstPercent(gstPercent);
		}
		if (null != json.get(ServiceConstants.IS_GST_INCLUDED)) {
			boolean isGstIncluded = Boolean.parseBoolean(json.get(ServiceConstants.IS_GST_INCLUDED));
			itemList.setGstIncluded(isGstIncluded);
		}

		if (null != json.get(ServiceConstants.OTHER_CHARGE)) {
			int otherCharge = Integer.parseInt(json.get(ServiceConstants.OTHER_CHARGE));
			itemList.setOtherCharge(otherCharge);
		}
		if (null != json.get(ServiceConstants.PURCHASE_OFFER_PRICE)) {
			int purchaseOfferPrice = Integer.parseInt(json.get(ServiceConstants.PURCHASE_OFFER_PRICE));
			itemList.setPurchaseOfferPrice(purchaseOfferPrice);
		}

		if (null != json.get(ServiceConstants.PURCHASE_OFFER_PERCENT)) {
			float purchaseOfferPercent = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.PURCHASE_OFFER_PERCENT)));
			itemList.setPurchaseOfferPercent(purchaseOfferPercent);
		}

		if (null != json.get(ServiceConstants.MARGIN)) {
			float margin = calculation.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.MARGIN)));
			itemList.setMargin(margin);
		}

		if (null != json.get(ServiceConstants.MARGIN_PERCENT)) {
			float marginPercent = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.MARGIN_PERCENT)));
			itemList.setMarginPercent(marginPercent);
		}

		if (null != json.get(ServiceConstants.CUSTOMER_MARGIN_PERCENT)) {
			float customerMarginPercent = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.CUSTOMER_MARGIN_PERCENT)));
			itemList.setCustomerMarginPercent(customerMarginPercent);
		}

		if (null != json.get(ServiceConstants.SLOT_LIST)) {
			String slotNumber = json.get(ServiceConstants.SLOT_LIST);
			slotList = slotService.getSlotNumberAndShopId(Integer.parseInt(slotNumber), shopId);
			slotList.setNetAmount(slotList.getNetAmount() + netAmount);
			itemList.setSlotList(slotNumber);
			itemList.setSlotCount(1);
		}

		if (null != json.get(ServiceConstants.CUSTOMER_MARGIN)) {
			float customerMargin = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.CUSTOMER_MARGIN)));
			itemList.setCustomerMargin(customerMargin);
		}

//		if(null != json.get(ServiceConstants.CUSTOMER_MARGIN_PERCENT)) {
//			float customerBundleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER));
//			itemList.setCustomerBundleOffer(customerBundleOffer);
//		}
//		
		if (null != json.get(ServiceConstants.BUNDLE_MARGIN)) {
			float bundleMargin = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.BUNDLE_MARGIN)));
			itemList.setBundleMargin(bundleMargin);
		}

		if (null != json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
			float customerSingleOffer = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER)));
			itemList.setCustomerSingleOffer(customerSingleOffer);
		}

		if (null != json.get(ServiceConstants.BUNDLE_CUSTOMER_DISCOUNT)){
			float bundleCustomerDiscount = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.BUNDLE_CUSTOMER_DISCOUNT)));
			itemList.setBundleCustomerDiscount(bundleCustomerDiscount);
		}

		if (null != json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
			float customerBundleOffer = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)));
			itemList.setCustomerBundleOffer(customerBundleOffer);
		}

		if (null != json.get(ServiceConstants.BAGGAGE)){
			boolean baggage = Boolean.parseBoolean(json.get(ServiceConstants.BAGGAGE));
			itemList.setBaggage(baggage);
		}

		if (null != json.get(ServiceConstants.MILAAN_OFFER)) {
			boolean milaanOffer = Boolean.parseBoolean(json.get(ServiceConstants.MILAAN_OFFER));
			itemList.setMilaanOffer(milaanOffer);
		}

		if (null != json.get(ServiceConstants.HOT_SELL)) {
			boolean hotSell = Boolean.parseBoolean(json.get(ServiceConstants.HOT_SELL));
			itemList.setHotSell(hotSell);
		}

		if (null != json.get(ServiceConstants.ADITIONAL_DISCOUNT)) {
			boolean aditionalDiscount = Boolean.parseBoolean(json.get(ServiceConstants.ADITIONAL_DISCOUNT));
			itemList.setAditionalDiscount(aditionalDiscount);
		}

		if (null != json.get(ServiceConstants.EXPIRY_ALERT)) {
			String expiryAlert = json.get(ServiceConstants.EXPIRY_ALERT);
			itemList.setExpiryAlert(expiryAlert);
		}

		if (null != json.get(ServiceConstants.TITLE)) {
			String title = json.get(ServiceConstants.TITLE);
			itemList.setTitle(title);
		}

		if (null != json.get(ServiceConstants.OUT_OF_STOCK)) {
			int outOfStock = Integer.parseInt(json.get(ServiceConstants.OUT_OF_STOCK));
			itemList.setOutOfStock(outOfStock);
		}

		if (null != json.get(ServiceConstants.RACK_NUMBER)) {
			String rackNumber = json.get(ServiceConstants.RACK_NUMBER);
			itemList.setRackNumber(rackNumber);
		}

		if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
			int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
			itemList.setDeliveryCharge(deliveryCharge);
		}

		if (null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
			exipryDate = json.get(ServiceConstants.DATE_OF_EXPIRE);
			LocalDate dateOfExpire = null;
			try {
				System.out.println("Date" + exipryDate);
				dateOfExpire = LocalDate.parse(exipryDate, formatter);
				itemList.setDateOfExpire(dateOfExpire);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (null != json.get(ServiceConstants.DATE_OF_MANUFACTURING)) {
			manufacturingDate = json.get(ServiceConstants.DATE_OF_MANUFACTURING);
			LocalDate dateOfManufacturing = null;
			try {
				System.out.println("Date" + manufacturingDate);
				dateOfManufacturing = LocalDate.parse(manufacturingDate, formatter);
				itemList.setDateOfManufacturing(dateOfManufacturing);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null != json.get(ServiceConstants.SELLING_PRICE)) {
			float sellingPrice = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.SELLING_PRICE)));
			itemList.setSellingPrice(sellingPrice);
		}

		if (null != json.get(ServiceConstants.BUNDLE_QUANTITY)) {
			float bundleQuantity = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.BUNDLE_QUANTITY)));
			itemList.setBundleQuantity(bundleQuantity);
		}

		if (null != json.get(ServiceConstants.BUNDLE_PRICE)) {
			float bundlePrice = calculation
					.DecimalCalculation(Float.parseFloat(json.get(ServiceConstants.BUNDLE_PRICE)));
			itemList.setBundlePrice(bundlePrice);
		}

		itemList.setActive(Boolean.TRUE);
		itemList.setCostPrice(costPrice);
		itemList.setCreatedOn(new Date());
		// itemList.setCustomerMargin(customerMarging);
		itemList.setDeleted(Boolean.FALSE);

		itemList.setMeasurement(measurement);
		itemList.setMrp(mrp);
		itemList.setName(name);
		itemList.setNetAmount(netAmount);
		itemList.setProductId(productId);
		itemList.setPurchasePrice(purchasePrice);
		itemList.setQuantity((int) unitQuantity);
//		itemList.setSellingPrice(sellingPrice);
		itemList.setShopId(shopId);
		itemList.setShopName(shopName);
		itemList.setStock(stock);
		itemList.setUnitQuantity(unitQuantity);
		itemList.setUnitSellingPrice(unitSellingPrice);
		itemList.setOfferActiveInd(offerActiveInd);

//		itemList.setBundleQuantity(bundleQuantity);
//		itemList.setBundlePrice(bundlePrice);
//		itemList.setSellingGstAmount(sellingGstAmount);
//		itemList.setBundleGstAmount(bundleGstAmount);

//
////		Slot slot = slotService.getSlotNumberAndShopId(Integer.parseInt(slotNumber), shopId);
//
//		float discount = 0, gstAmount = 0, oldPrice = 0, totalAmount = 0, price = 0, sellingPrice = 0;
//
//		LocalDate offerTo = null, offerFrom = null;
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			offerFrom = LocalDate.parse(json.get(ServiceConstants.OFFER_FROM), formatter);
			offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO), formatter);
			itemList.setOfferFrom(offerFrom);
			itemList.setOfferTo(offerTo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (itemListService.itemListExits(itemList.getSlotList(), itemList.getShopId(), itemList.getUnitQuantity(),
				itemList.getUnitSellingPrice(), itemList.getProductId(), itemList.getBarcode(),
				itemList.getCostPrice())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("slotNumber", itemList.getSlotList());
			response.put("Descreption", "Variant all ready created");
		} else {
			itemListService.itemListCreate(itemList);
//			localData.setVariantData(new ArrayList<ItemList>());

			response.put("itemListId", String.valueOf(itemList.getId()));
			if (null != json.get(ServiceConstants.SLOT_LIST)) {
//				boolean check = slotService.addVariantByVariantId(slotList, itemList.getId());
//				if (check) {
				variantStock.setActive(Boolean.TRUE);
				variantStock.setCreatedOn(new Date());
				variantStock.setSlotNumber(slotList.getId()); // it store the slot Id in variant Stock
				variantStock.setStock(stock);
				variantStock.setVariantId(itemList.getId());
				variantStock.setDeleted(Boolean.FALSE);
				variantStock.setShopId(shopId);
				variantStock.setCurrentStock(stock);
				variantStock.setSoldStock(0);

				variantStockService.stockCreated(variantStock);
				slotService.updeteSlot(slotList);
//				} else {
//					response.put("status", Boolean.TRUE.toString());
//					response.put("Descreption", "Item all ready created");
//				}

			}
			response.put("status", Boolean.TRUE.toString());
			response.put("Descreption", "ItemList created");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateImage(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user:{}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != itemListService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			ItemList itemList = itemListService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			VariantStock variantStock2 = variantStockService
					.getByVariantId(Integer.parseInt(json.get(ServiceConstants.ID)));
			LocalDate offerTo = null, offerFrom = null;
			boolean offerDone = false;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Calculation calculation = new Calculation();

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				itemList.setName(name);
			}

			if (null != json.get(ServiceConstants.MEASUREMENT)) {
				int measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT));
				itemList.setMeasurement(measurement);
			}

			if (null != json.get(ServiceConstants.BARCODE)) {
				String barcode = json.get(ServiceConstants.BARCODE);
				itemList.setBarcode(barcode);
			}
			if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
				int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE));
				itemList.setDeliveryCharge(deliveryCharge);
			}

			if (null != json.get(ServiceConstants.BUNDLE_CUSTOMER_DISCOUNT)) {
				float bundleCustomerDiscount = Float.parseFloat(json.get(ServiceConstants.BUNDLE_CUSTOMER_DISCOUNT));
				itemList.setBundleCustomerDiscount(bundleCustomerDiscount);
			}

			if (null != json.get(ServiceConstants.BUNDLE_GST_AMOUNT)) {
				float bundleGstAmount = Float.parseFloat(json.get(ServiceConstants.BUNDLE_GST_AMOUNT));
				itemList.setBundleGstAmount(bundleGstAmount);
			}

			if (null != json.get(ServiceConstants.BUNDLE_MARGIN)) {
				float bundleMargin = Float.parseFloat(json.get(ServiceConstants.BUNDLE_MARGIN));
				itemList.setBundleMargin(bundleMargin);
			}

			if (null != json.get(ServiceConstants.BUNDLE_PRICE)) {
				float bundlePrice = Float.parseFloat(json.get(ServiceConstants.BUNDLE_PRICE));
				itemList.setBundlePrice(bundlePrice);
			}

			if (null != json.get(ServiceConstants.BUNDLE_QUANTITY)) {
				float bundleQuantity = Float.parseFloat(json.get(ServiceConstants.BUNDLE_QUANTITY));
				itemList.setBundleQuantity(bundleQuantity);
			}

			if (null != json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER)) {
				float customerBundleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_BUNDLE_OFFER));
				itemList.setCustomerBundleOffer(customerBundleOffer);
			}

			if (null != json.get(ServiceConstants.COST_PRICE)) {
				float costPrice = Float.parseFloat(json.get(ServiceConstants.COST_PRICE));
				itemList.setCostPrice(costPrice);
			}
			if (null != json.get(ServiceConstants.CUSTOMER_MARGIN)) {
				float customerMargin = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_MARGIN));
				itemList.setCustomerMargin(customerMargin);
			}

			if (null != json.get(ServiceConstants.CUSTOMER_MARGIN_PERCENT)) {
				float customerMarginPercent = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_MARGIN_PERCENT));
				itemList.setCustomerMarginPercent(customerMarginPercent);
			}

			if (null != json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER)) {
				float customerSingleOffer = Float.parseFloat(json.get(ServiceConstants.CUSTOMER_SINGLE_OFFER));
				itemList.setCustomerSingleOffer(customerSingleOffer);
			}

			if (null != json.get(ServiceConstants.DATE_OF_MANUFACTURING)) {
				String manufacturingDate = null;
				manufacturingDate = json.get(ServiceConstants.DATE_OF_MANUFACTURING);
				LocalDate dateOfManufacturing = null;
				try {
					System.out.println("Date" + manufacturingDate);
					dateOfManufacturing = LocalDate.parse(manufacturingDate, formatter);
					itemList.setDateOfManufacturing(dateOfManufacturing);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
				String exipryDate = null;
				exipryDate = json.get(ServiceConstants.DATE_OF_EXPIRE);
				LocalDate dateOfExpire = null;
				try {
					System.out.println("Date" + exipryDate);
					dateOfExpire = LocalDate.parse(exipryDate, formatter);
					itemList.setDateOfExpire(dateOfExpire);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != json.get(ServiceConstants.EXPIRY_ALERT)) {
				String expiryAlert = json.get(ServiceConstants.EXPIRY_ALERT);
				itemList.setExpiryAlert(expiryAlert);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				float gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
				itemList.setGstAmount(gstAmount);
			}

			if (null != json.get(ServiceConstants.GST_PERCENT)) {
				int gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT));
				itemList.setGstPercent(gstPercent);
			}

			if (null != json.get(ServiceConstants.IS_GST_INCLUDED)) {
				boolean isGstIncluded = Boolean.parseBoolean(json.get(ServiceConstants.IS_GST_INCLUDED));
				itemList.setGstIncluded(isGstIncluded);
			}
			if (null != json.get(ServiceConstants.HSN_CODE)) {
				String hsnCode = json.get(ServiceConstants.HSN_CODE);
				itemList.setHsnCode(hsnCode);
			}

			if (null != json.get(ServiceConstants.MARGIN)) {
				float margin = Float.parseFloat(json.get(ServiceConstants.MARGIN));
				itemList.setMargin(margin);
			}

			if (null != json.get(ServiceConstants.MARGIN_PERCENT)) {
				float marginPercent = Float.parseFloat(json.get(ServiceConstants.MARGIN_PERCENT));
				itemList.setMarginPercent(marginPercent);
			}

			if (null != json.get(ServiceConstants.MRP)) {
				Float mrp = Float.parseFloat(json.get(ServiceConstants.MRP));
				itemList.setMrp(mrp);
			}

			if (null != json.get(ServiceConstants.NET_AMOUNT)) {
				float netAmount = Float.parseFloat(json.get(ServiceConstants.NET_AMOUNT));
				itemList.setNetAmount(netAmount);
			}

			if (null != json.get(ServiceConstants.OFFER_ACTIVE_IND)) {
				boolean offerActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND));
				itemList.setOfferActiveInd(offerActiveInd);
			}

			if (null != json.get(ServiceConstants.OFFER_PERCENT)) {
				int offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
				itemList.setOfferPercent(offerPercent);
			}

//			if (null != json.get(ServiceConstants.IS_ONLINE)) {
//				boolean isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
//				Item item = itemService.getById(itemList.getProductId());
//				if (isOnline) {
//					item.setOnline(isOnline);
//					itemService.updateItem(item);
//				} else {
//					List<ItemList> itemList1 = itemListService.getByProductId(itemList.getProductId());
//					int count = 0;
//					for (int i = 0; i < itemList1.size(); i++) {
//						if (itemList1.get(i).isOnline()) {
//							if (itemList.getId() != itemList1.get(i).getId()) {
//								count++;
//							}
//						}
//					}
//					if (count > 0) {
//						item.setOnline(Boolean.TRUE);
//					} else {
//						item.setOnline(Boolean.FALSE);
//					}
//					itemService.updateItem(item);
//				}
//				itemList.setOnline(isOnline);
//			}

			if (null != json.get(ServiceConstants.IS_ONLINE)) {
				boolean isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
				Item item = itemService.getById(itemList.getProductId());
				int categoryId = item.getCategory();
				int brandId = item.getBrand();
				int subcategoryId = item.getSubCategoryId();
				Brand brand = brandService.getBrand(brandId);
				Category category = categoryService.getCategory(categoryId);
				SubCategory subCategory = subCategoryService.getById(subcategoryId);
				if (isOnline) {
					item.setOnline(isOnline);
					brand.setOnline(isOnline);
					category.setOnline(isOnline);
					subCategory.setOnline(isOnline);
					itemService.updateItem(item);
					brandService.updateBrand(brand);
					categoryService.updateCategory(category);
					subCategoryService.upDateSubCategory(subCategory);
				} else {
					List<ItemList> itemList1 = itemListService.getByProductId(itemList.getProductId());
					int count = 0;
					for (int i = 0; i < itemList1.size(); i++) {
						if (itemList1.get(i).isOnline()) {
							if (itemList.getId() != itemList1.get(i).getId()) {
								count++;
							}
						}
					}
					if (count > 0) {
						item.setOnline(Boolean.TRUE);
						brand.setOnline(Boolean.TRUE);
						category.setOnline(Boolean.TRUE);
						subCategory.setOnline(Boolean.TRUE);
					} else {
						item.setOnline(Boolean.FALSE);
						brand.setOnline(Boolean.FALSE);
						category.setOnline(Boolean.FALSE);
						subCategory.setOnline(Boolean.FALSE);
					}
					itemService.updateItem(item);
					brandService.updateBrand(brand);
					categoryService.updateCategory(category);
					subCategoryService.upDateSubCategory(subCategory);
				}
				itemList.setOnline(isOnline);
			}

			if (null != json.get(ServiceConstants.OUT_OF_STOCK)) {
				int outOfStock = Integer.parseInt(json.get(ServiceConstants.OUT_OF_STOCK));
				itemList.setOutOfStock(outOfStock);
			}

			if (null != json.get(ServiceConstants.OTHER_CHARGE)) {
				int otherCharge = Integer.parseInt(json.get(ServiceConstants.OTHER_CHARGE));
				itemList.setOtherCharge(otherCharge);
			}

			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
				int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID));
				itemList.setProductId(productId);
			}

			if (null != json.get(ServiceConstants.PURCHASE_OFFER_PERCENT)) {
				Float purchaseOfferPercent = Float.parseFloat(json.get(ServiceConstants.PURCHASE_OFFER_PERCENT));
				itemList.setPurchaseOfferPercent(purchaseOfferPercent);
			}

			if (null != json.get(ServiceConstants.PURCHASE_OFFER_PRICE)) {
				Float purchaseOfferPrice = Float.parseFloat(json.get(ServiceConstants.PURCHASE_OFFER_PRICE));
				itemList.setPurchaseOfferPrice(purchaseOfferPrice);
			}

			if (null != json.get(ServiceConstants.PURCHASE_PRICE)) {
				Float purchasePrice = Float.parseFloat(json.get(ServiceConstants.PURCHASE_PRICE));
				itemList.setPurchasePrice(purchasePrice);
			}

			if (null != json.get(ServiceConstants.BAGGAGE)) {
				boolean baggage = Boolean.parseBoolean(json.get(ServiceConstants.BAGGAGE));
				itemList.setBaggage(baggage);
			}

			if (null != json.get(ServiceConstants.MILAAN_OFFER)) {
				boolean milaanOffer = Boolean.parseBoolean(json.get(ServiceConstants.MILAAN_OFFER));
				itemList.setMilaanOffer(milaanOffer);
			}

			if (null != json.get(ServiceConstants.HOT_SELL)) {
				boolean hotSell = Boolean.parseBoolean(json.get(ServiceConstants.HOT_SELL));
				itemList.setHotSell(hotSell);
			}

			if (null != json.get(ServiceConstants.ADITIONAL_DISCOUNT)) {
				boolean aditionalDiscount = Boolean.parseBoolean(json.get(ServiceConstants.ADITIONAL_DISCOUNT));
				itemList.setAditionalDiscount(aditionalDiscount);
			}

			if (null != json.get(ServiceConstants.QUANTITY)) {
				int quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY));
				itemList.setQuantity(quantity);
			}

			if (null != json.get(ServiceConstants.SELLING_GST_AMOUNT)) {
				Float sellingGstAmount = Float.parseFloat(json.get(ServiceConstants.SELLING_GST_AMOUNT));
				itemList.setSellingGstAmount(sellingGstAmount);
			}

			if (null != json.get(ServiceConstants.RACK_NUMBER)) {
				String rackNumber = json.get(ServiceConstants.RACK_NUMBER);
				itemList.setRackNumber(rackNumber);
			}

			if (null != json.get(ServiceConstants.SELLING_PRICE)) {
				Float sellingPrice = Float.parseFloat(json.get(ServiceConstants.SELLING_PRICE));
				itemList.setSellingPrice(sellingPrice);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				itemList.setTotalAmount(totalAmount);
			}

			if (null != json.get(ServiceConstants.STOCK)) {
				int stock = Integer.parseInt(json.get(ServiceConstants.STOCK));
				variantStock2.setActive(Boolean.TRUE);
				itemList.setActive(Boolean.TRUE);
				variantStock2.setCurrentStock(stock);
				variantStock2.setStock(stock);
				variantStockService.updateStock(variantStock2);
				itemList.setStock(stock);
			}

			if (null != json.get(ServiceConstants.TITLE)) {
				String title = json.get(ServiceConstants.TITLE);
				itemList.setTitle(title);
			}

			if (null != json.get(ServiceConstants.UNIT_SELLING_PRICE)) {
				Float unitSellingPrice = Float.parseFloat(json.get(ServiceConstants.UNIT_SELLING_PRICE));
				itemList.setUnitSellingPrice(unitSellingPrice);
			}

			if (null != json.get(ServiceConstants.VENDER_ID)) {
				int venderId = Integer.parseInt(json.get(ServiceConstants.VENDER_ID));
				itemList.setVenderId(venderId);
			}

			if (null != json.get(ServiceConstants.UNIT_QUANTITY)) {
				float unitQuantity = Float.parseFloat(json.get(ServiceConstants.UNIT_QUANTITY));
				itemList.setUnitQuantity(unitQuantity);
			}

			if (null != json.get(ServiceConstants.OLD_PRICE)) {
				int oldPrice = Integer.parseInt(json.get(ServiceConstants.OLD_PRICE));
				itemList.setOldPrice(oldPrice);
			}

			if (null != json.get(ServiceConstants.GST_PERCENT)) {
				int gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT));
				itemList.setGstPercent(gstPercent);
			}

			if (null != json.get(ServiceConstants.QUANTITY)) {
				int quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY));
				itemList.setQuantity(quantity);
			}

			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
				int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID));
				itemList.setProductId(productId);
			}

			if (null != json.get(ServiceConstants.SLOT_LIST)) {
				String slotNumber = json.get(ServiceConstants.SLOT_LIST);
				int stock = Integer.parseInt(json.get(ServiceConstants.STOCK));
				VariantStock variantStock = new VariantStock();
				Slot slotList1 = slotService.getSlotNumberAndShopId(Integer.parseInt(slotNumber), itemList.getShopId());
				String slotList = itemList.getSlotList();
				int slotCount = itemList.getSlotCount();
				if (!Strings.isBlank(slotList) && !slotList.contains(String.valueOf(slotNumber))) {
					slotList = slotList + "," + slotNumber;
					itemList.setSlotCount(slotCount + 1);

					variantStock.setActive(Boolean.TRUE);
					variantStock.setCreatedOn(new Date());
					variantStock.setSlotNumber(slotList1.getId());
					variantStock.setStock(stock);
					variantStock.setVariantId(itemList.getId());
					variantStock.setDeleted(Boolean.FALSE);
					variantStock.setShopId(itemList.getShopId());
					variantStock.setCurrentStock(stock);
					variantStock.setSoldStock(0);

					boolean update = variantStockService.exitsStock(Integer.parseInt(slotNumber), itemList.getId());
					if (update) {
						VariantStock variantStock1 = variantStockService
								.getBySlotNumberAndVariantId(Integer.parseInt(slotNumber), itemList.getId());
						variantStock1.setStock(variantStock.getStock() + stock);
						variantStock1.setCurrentStock(variantStock1.getCurrentStock() + stock);
						variantStockService.updateStock(variantStock1);
					} else {
						variantStockService.stockCreated(variantStock);
					}

				} else if (!Strings.isBlank(slotList) && slotList.contains(String.valueOf(slotNumber))) {
					response.put("slotMsg", "Slot number already added.");
				} else if (Strings.isBlank(slotList)) {
					slotList = String.valueOf(slotNumber);
					itemList.setSlotCount(slotCount == 0 ? 1 : slotCount + 1);
				}
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = (json.get(ServiceConstants.SHOP_ID));
				itemList.setShopId(shopId);
			}

			boolean update = itemListService.updateItemList(itemList);
			localData.setVariantData(new ArrayList<ItemList>());
			if (update) {
				response.put("status", Boolean.TRUE.toString());
				response.put("descreption", "ItemList update");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "ItemList not update");
			}
		}

		return ResponseEntity.ok().body(response);

	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<MyResponse> deleteProduct(@PathVariable("id") int id) {
		MyResponse myResponse = new MyResponse();
		Map<String, String> response = new HashMap<String, String>();
		ItemList itemList = itemListService.getById(id);
		if (null != itemList) {
			boolean result = itemListService.deleteItemList(id);
			if (result) {
				myResponse.setData(itemList);
				myResponse.setStatus(ResponseConstants.DEL_VARI);
				myResponse.setDescription(ResponseConstants.DEL_VARI_MSG[1]);
			}else {
				myResponse.setStatus(ResponseConstants.DEL_NOT_VAR);
				myResponse.setDescription(ResponseConstants.DEL_NOT_VAR_MSG[1]);
			}
		} else {
			myResponse.setStatus(ResponseConstants.DEL_NOT_VAR);
			myResponse.setDescription(ResponseConstants.DEL_NOT_VAR_MSG[1]);
		}
		return ResponseEntity.ok().body(myResponse);
	}

	@PutMapping("delete/{id}/{slotId}")
	ResponseEntity<Map<String, String>> deleteVarianrt(@PathVariable("id") int id, @PathVariable("slotId") int slotId) {
		Map<String, String> response = new HashMap<String, String>();
		ItemList itemList = itemListService.getById(id);
		Calculation calculation = new Calculation();
		if (null != itemList) {
			boolean result = itemListService.deleteItemList(id);
			if (result) {
				Slot slot = slotService.getById(slotId);
				slot.setNetAmount(calculation.DecimalCalculation(slot.getNetAmount() - itemList.getNetAmount()));
				String wishList = slot.getVariantList();
				int variantCount = slot.getVariantCount();
				if (!Strings.isBlank(wishList) && wishList.contains(String.valueOf(id))) {
					wishList = wishList.startsWith(String.valueOf(id)) && wishList.contains(",")
							? wishList.replace(id + ",", Strings.EMPTY)
							: wishList.replace("," + id, Strings.EMPTY);
					if (wishList.startsWith(String.valueOf(id)) && !wishList.contains(","))
						wishList = wishList.replace(String.valueOf(id), Strings.EMPTY);

					slot.setVariantCount(variantCount - 1);
				}
				slotService.updeteSlot(slot);
				VariantStock variantStock = variantStockService.getBySlotNumberAndVariantId(slotId, id);
				variantStock.setDeleted(Boolean.TRUE);
				variantStock.setActive(Boolean.FALSE);
				variantStockService.updateStock(variantStock);
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "ItemList deleted with given  Id :-" + id);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "ItemList does not exist with given Id");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("upload/update/excel/{shopId}")
//	public ResponseEntity<Map<String, String>> getExcelFile(@PathVariable("shopId") String shopId){
	public ResponseEntity<Map<String, String>> UpdateExcelFile(@PathVariable("shopId") String shopId) {
		Map<String, String> response3 = new HashMap<String, String>();
		File data = new File("E:\\java-workspace\\product3.xlsx");
		String requestUrl = "";
		List<Category> categoryList = new ArrayList<Category>();
		List<Brand> brandList = new ArrayList<Brand>();
		List<Item> itemList = new ArrayList<Item>();
		List<ItemList> variantList = new ArrayList<ItemList>();
		List<Measurement> measurementList = new ArrayList<Measurement>();
		VariantStock variantStock = null;
		List<Slot> slottList = new ArrayList<Slot>();
		Category category = null;
		ItemList variant = null;
		Slot slot = null;
		Brand brand = null;
		Item item = null;
		Measurement measurement = null;
		Calculation calculation = new Calculation();
		try {
			FileInputStream file = new FileInputStream(data);
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				System.out.println("Sheet name is " + sh.getSheetName());
				System.out.println("Sheet name is " + sh.getLastRowNum());
				System.out.println("Sheet cell no is " + sh.getRow(0).getLastCellNum());
				System.out.println("col data ==" + sh.getRow(1).getCell(1));
				System.out.println("------------");
				Iterator<Row> iterator = sh.iterator();

//				requestUrl = calculation.ExcelToString(sh.getRow(1).getCell(1));
				String data1 = "" + sh.getRow(1).getCell(1);
//				requestUrl = "Sheet name is " + sh.getRow(1).getCell(1).getCellType() + sh.getRow(1).getCell(1);
				String data2 = new String(data1).trim();
//				requestUrl = data2.replace(" ", "_");
				for (int i = 1; i <= sh.getLastRowNum(); i++) {
					category = new Category();
					brand = new Brand();
					item = new Item();
					variant = new ItemList();
					measurement = new Measurement();
					int categoryId = 0, brandId = 0, productId = 0, measurementId = 0, venderId = 0, variantId = 0,
							gstPercent = 0, otherExpense = 0, stock = 0, outOfStock = 0, variantStockId = 0, slotId = 0;
					float costPrice = 0, purchasePrice = 0, gstAmount = 0, netAmount = 0, purchaseDiscount = 0, mrp = 0;
					Boolean isIncluded = false;
					String slotNumber = null, productName = null, expAlert = null, rackNumber = null, barcode = null,
							hsnCode = null, manufacturingDate = null, dateOfExp = null;
					for (int j = 0; j < sh.getRow(i).getLastCellNum(); j++) {
						String cellHeading = sh.getRow(0).getCell(j).toString();
						String cellType = sh.getRow(i).getCell(j).getCellType().toString();
						String cellData = sh.getRow(i).getCell(j).toString();
						Cell cell = sh.getRow(i).getCell(j);
						Calculation calculation1 = new Calculation();
						int cellDataInt = 0;
						String cellDataString = "";
						float cellDataFloat = 0;

						switch (cellHeading) {
						case "VENDER_ID":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								Boolean response = venderService.checkExits(shopId, cellValue);
								if (response) {
									venderId = cellValue;

								}
							}

							break;
						case "SLOT_NUMBER":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = slotService.checkExist(Integer.parseInt(cellValue.getName()), shopId,
										venderId);
								if (response) {
									slot = slotService.getSlotNumberAndShopId(Integer.parseInt(cellValue.getName()),
											shopId);
									slotNumber = cellValue.getName();
									slotId = slot.getId();
								}
							}

							break;
						case "CATEGORY":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = categoryService.categoryExists(cellValue.getTitle(), shopId);
								categoryList = categoryService.getAllCategory();
								category = categoryList.get(0);
								if (response) {
									category = categoryService.getCategoryTitleByShopId(cellValue.getTitle(), shopId);
									categoryList.add(category);
									categoryId = category.getId();

									category = categoryService.getCategory(categoryId);
									if (null != cellValue.getTitle()) {
										category.setTitle(cellValue.getTitle());
									}
									if (null != cellValue.getName()) {
										category.setName(cellValue.getName());
									}
									if (null != shopId) {
										category.setShopId(shopId);
									}
									category.setActive(true);
									category.setDeleted(false);
									boolean response2 = categoryService.updateCategory(category);
									if (response2) {
										response3.put("status", Boolean.TRUE.toString());
										response3.put("description", "Category update");
									} else {
										response3.put("status", Boolean.FALSE.toString());
										response3.put("description", "Category not update");
									}

								}

							}
							break;

//						case "BRAND":
//							if (null != cellData) {
//								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
//								// Boolean response = brandService.brandExists(cellValue.getTitle(), shopId,
//								// categoryId);
//								brandList = brandService.getAllBrand();
//								Boolean response = Boolean.FALSE;
//								brand = brandList.get(0);
//								if (response) {
//									brand = brandService.getByTitleAndShopId(cellValue.getTitle(), shopId, categoryId, subCategoryId);
//									brandList.add(brand);
//									brandId = brand.getId();
//
//									brand = brandService.getBrandById(brandId);
//									if (null != cellValue.getTitle()) {
//										brand.setTitle(cellValue.getTitle());
//									}
//
//									if (null != cellValue.getTitle()) {
//										brand.setName(cellValue.getName());
//									}
//									if (null != shopId) {
//										brand.setShopId(shopId);
//									}
//
//									boolean response1 = brandService.updateBrand(brand);
//									if (response1) {
//										response3.put("status", Boolean.TRUE.toString());
//										response3.put("description", "Brand update");
//									} else {
//										response3.put("status", Boolean.FALSE.toString());
//										response3.put("description", "Brand not update");
//									}
//								}
//
//							}
//							break;

						case "PRODUCT_NAME":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								productName = cellValue.getName();
								Boolean response = itemService.itemExit(cellValue.getTitle(), shopId, brandId,
										categoryId);
								itemList = itemService.getAll();
								item = itemList.get(0);
								if (response) {
									item = itemService.getItemForExcel(brandId, categoryId, shopId,
											cellValue.getTitle());
									itemList.add(item);
									productId = item.getId();
									item = itemService.getById(productId);
									if (null != cellValue.getName()) {
										item.setName(cellValue.getName());
									}
									if (null != cellValue.getTitle()) {
										item.setName(cellValue.getName());
									}
									if (null != shopId) {
										item.setShopId(shopId);
									}
									item.setActive(true);
									item.setDeleted(false);
								}

								boolean response1 = itemService.updateItem(item);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Product name update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Product name not update");
								}
							}
							break;

						case "DESCRIPTION":
							if (null != cellData && item != null) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);

								item = itemService.getById(productId);
								if (null != cellValue.getName()) {
									item.setDescription(cellValue.getName());
								}
								boolean response1 = itemService.updateItem(item);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Description update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Description not update");
								}
							}
							break;

						case "REVIEW":
							if (null != cellData && item != null) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								item = itemService.getById(productId);
								if (null != cellValue.getName()) {
									item.setReview(cellValue.getName());
								}

								boolean response1 = itemService.updateItem(item);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Review update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Review not update");
								}
							}
							break;
						case "MEASUREMENT":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = measurementService.measurementExists(cellValue.getTitle(), shopId);
								measurementList = measurementService.getAll();
								measurement = measurementList.get(0);
								if (response) {
									measurement = measurementService.getByTitleAndShopId(shopId, cellValue.getTitle());
									measurementList.add(measurement);
									measurementId = measurement.getId();
									measurement = measurementService.getById(measurementId);
									if (null != cellValue.getTitle()) {
										measurement.setTitle(cellValue.getTitle());
									}
									if (null != cellValue.getName()) {
										measurement.setName(cellValue.getName());
									}
									if (null != shopId) {
										measurement.setShopId(shopId);
									}
									measurement.setActive(true);
									measurement.setDeleted(false);
									boolean response1 = measurementService.updateMeasurement(measurement);
									if (response1) {
										response3.put("status", Boolean.TRUE.toString());
										response3.put("description", "Measurement update");
									} else {
										response3.put("status", Boolean.FALSE.toString());
										response3.put("description", "Measurement not update");
									}
								}

							}
							break;

						case "UNIT_QUANTITY":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								Boolean response = itemListService.checkExist(productId, shopId, cellValue, slotNumber);
								if (response) {

									variant = itemListService.getForExcel(productId, shopId, cellValue, slotNumber);
									variantList = itemListService.getAll();
									variant = variantList.get(0);
									variantId = variant.getId();
									variant = itemListService.getById(variantId);

									variant.setUnitQuantity(cellValue);

									boolean response1 = itemListService.updateItemList(variant);
									if (response1) {
										response3.put("status", Boolean.TRUE.toString());
										response3.put("description", "Unit quantity update");
									} else {
										response3.put("status", Boolean.FALSE.toString());
										response3.put("description", "Unit quantity not update");
									}
								}

								boolean response1 = itemListService.itemListCreate(variant);
								if (response1) {
									variantStock = new VariantStock();
									variantId = variant.getId();
									variantStock.setSlotNumber(slotId);
									variantStock.setVariantId(variantId);
									variantStock.setActive(true);
									variantStock.setCreatedOn(new Date());
									variantStock.setDeleted(false);
									variantStock.setShopId(shopId);
									Boolean response2 = variantStockService.stockCreated(variantStock);
									if (response2) {
										variantStockId = variantStock.getId();
//										}
									}
								}
							}
							break;

						case "COST_PRICE":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								variant = itemListService.getById(variantId);

								costPrice = cellValue;
								variant.setCostPrice(calculation.DecimalCalculation(costPrice));
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Cost price update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Cost price not update");
								}
							}
							break;

						case "GST_PERCENT":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								gstPercent = cellValue;
								variant = itemListService.getById(variantId);
								variant.setGstPercent(gstPercent);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Gst precent update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Gst precent not update");
								}
							}
							break;

						case "GST_TYPE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								variant = itemListService.getById(variantId);
								if (cellValue.getName().equals("INC")) {
									isIncluded = true;
									variant.setGstIncluded(isIncluded);
								} else if (cellValue.getName().equals("EXC")) {
									isIncluded = false;
									variant.setGstIncluded(isIncluded);
								}
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Gst type update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Gst type not update");
								}
							}
							break;
						case "OTHER_EXPENSE":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								otherExpense = cellValue;
								variant = itemListService.getById(variantId);
								variant.setOtherCharge(cellValue);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Other expense update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Other expense not update");
								}
							}
							break;

						case "PURCHASE_DISCOUNT":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								purchaseDiscount = cellValue;
								variant = itemListService.getById(variantId);
								variant.setPurchaseOfferPercent(cellValue);
								variant.setPurchaseOfferPrice(cellValue);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Purchase discount update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Purchase discount not update");
								}
							}
							break;

						case "MRP":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								mrp = cellValue;
								variant = itemListService.getById(variantId);
								variant.setMrp(calculation.DecimalCalculation(mrp));
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Mrp update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Mrp not update");
								}
							}
							break;

						case "STOCK":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								variant = itemListService.getById(variantId);
								stock = cellValue;
								variant.setStock(stock);

								variantStock.setStock(cellValue);
								variantStock.setCurrentStock(cellValue);

								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Stock update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Stock not update");
								}
							}
							break;

						case "STOCK_ALERT":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								outOfStock = cellValue;
								variant = itemListService.getById(variantId);
								variant.setOutOfStock(outOfStock);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Stock alert update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Stock  alert not update");
								}
							}
							break;

						case "RACK":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								variant = itemListService.getById(variantId);
								rackNumber = cellValue.getName();
								variant.setRackNumber(rackNumber);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Rack update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Rack not update");
								}
							}
							break;

						case "BARCODE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								variant = itemListService.getById(variantId);
								barcode = cellValue.getName();
								variant.setBarcode(barcode);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Barcode update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Barcode not update");
								}
							}
							break;

						case "HSN_CODE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								variant = itemListService.getById(variantId);
								hsnCode = cellValue.getName();
								variant.setHsnCode(hsnCode);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "HSN code update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "HSN code not update");
								}
							}
							break;

						case "MFG_DATE":
							if (null != cellData) {
								variant = itemListService.getById(variantId);
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
								LocalDate dateOfManufacturing = null;
								try {
									dateOfManufacturing = LocalDate.parse(cellData, formatter);
									variant.setDateOfManufacturing(dateOfManufacturing);
								} catch (Exception e) {
									e.printStackTrace();
								}
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Date of manufactring is update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Date of manufactring not update");
								}
							}
							break;

						case "EXP_DATE":
							if (null != cellData) {
								variant = itemListService.getById(variantId);
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
								LocalDate expDate = null;
								try {
									expDate = LocalDate.parse(cellData, formatter);
									variant.setDateOfExpire(expDate);
								} catch (Exception e) {
									e.printStackTrace();
								}
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Date of expiry is update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Date of expiry not update");
								}
							}
							break;

						case "EXP_ALERT":
							if (null != cellData) {
								variant = itemListService.getById(variantId);
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								expAlert = cellValue.getName();
								variant.setExpiryAlert(expAlert);
								boolean response1 = itemListService.updateItemList(variant);
								if (response1) {
									response3.put("status", Boolean.TRUE.toString());
									response3.put("description", "Expiry alert is update");
								} else {
									response3.put("status", Boolean.FALSE.toString());
									response3.put("description", "Expiry alert not update");
								}
							}
							break;

						case "CALCULATE":
							if (null != cellData) {
								variant = itemListService.getById(variantId);
								Boolean cellValue = calculation.ExcelToBoolean(cell);
								if (cellValue) {
									if (isIncluded) {
										gstAmount = (costPrice / (100 + gstPercent)) * gstPercent;
										purchasePrice = costPrice + otherExpense - purchaseDiscount;
										variant.setGstAmount(calculation.DecimalCalculation(gstAmount));
										variant.setPurchasePrice(calculation.DecimalCalculation(purchasePrice));
										float margin = mrp - purchasePrice;
										float marginPercent = margin / (purchasePrice / 100);
										variant.setMargin(calculation.DecimalCalculation(margin));
										variant.setMarginPercent(calculation.DecimalCalculation(marginPercent));
										variant.setCustomerMargin(0);
										variant.setCustomerSingleOffer(0);
										variant.setCustomerBundleOffer(0);

										variant.setOfferActiveInd(false);
										variant.setDeliveryCharge(0);
										variant.setQuantity(1);
										variant.setBundleQuantity(1);
										variant.setGstIncluded(isIncluded);
										variant.setCustomerMarginPercent(0);
										variant.setVenderId(venderId);
										variant.setOnline(false);
										variant.setSlotCount(1);
										variant.setBundlePrice(calculation.DecimalCalculation(mrp));
										variant.setUnitSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setNetAmount(calculation.DecimalCalculation(stock * purchasePrice));
										variant.setName(productName);

										boolean response1 = itemListService.updateItemList(variant);
										if (response1) {
											response3.put("status", Boolean.TRUE.toString());
											response3.put("description", "Calculation is update");
										} else {
											response3.put("status", Boolean.FALSE.toString());
											response3.put("description", "Calculation not update");
										}
										variant = null;
									} else {
										gstAmount = costPrice * gstPercent / 100;
										purchasePrice = costPrice + gstAmount + otherExpense - purchaseDiscount;
										variant.setGstAmount(calculation.DecimalCalculation(gstAmount));
										variant.setPurchasePrice(calculation.DecimalCalculation(purchasePrice));
										float margin = mrp - purchasePrice;
										float marginPercent = margin / (purchasePrice / 100);
										variant.setMargin(calculation.DecimalCalculation(margin));
										variant.setMarginPercent(calculation.DecimalCalculation(marginPercent));
										variant.setCustomerMargin(0);
										variant.setCustomerSingleOffer(0);
										variant.setCustomerBundleOffer(0);
										variant.setCustomerMarginPercent(0);
										variant.setOnline(false);
										variant.setOfferActiveInd(false);
										variant.setDeliveryCharge(0);
										variant.setQuantity(1);
										variant.setBundleQuantity(1);
										variant.setGstIncluded(isIncluded);
										variant.setCustomerMarginPercent(0);
										variant.setVenderId(venderId);
										variant.setSlotCount(1);
										variant.setBundlePrice(calculation.DecimalCalculation(mrp));
										variant.setUnitSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setNetAmount(calculation.DecimalCalculation(stock * purchasePrice));
										variant.setName(productName);

										boolean response1 = itemListService.updateItemList(variant);
										if (response1) {
											response3.put("status", Boolean.TRUE.toString());
											response3.put("description", "Expiry alert is update");
										} else {
											response3.put("status", Boolean.FALSE.toString());
											response3.put("description", "Expiry alert not update");
										}
										variant = null;
									}
								}
							}
							break;

						case "LOOKUP_LABLE":
							break;
						default:
							String s = "Last data";
						}
					}
				}

			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(response3);
	}

	@GetMapping("upload/delete/excel/{shopId}")
//	public ResponseEntity<Map<String, String>> getExcelFile(@PathVariable("shopId") String shopId){
	public ResponseEntity<Map<String, String>> getUpdateFile(@PathVariable("shopId") String shopId) {
		Map<String, String> response3 = new HashMap<String, String>();
		File data = new File("E:\\java-workspace\\product3.xlsx");
		String requestUrl = "";
		List<Category> categoryList = new ArrayList<Category>();
		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		List<Brand> brandList = new ArrayList<Brand>();
		List<Item> itemList = new ArrayList<Item>();
		List<ItemList> variantList = new ArrayList<ItemList>();
		List<Measurement> measurementList = new ArrayList<Measurement>();
		VariantStock variantStock = null;
		List<Slot> slottList = new ArrayList<Slot>();
		Category category = null;
		ItemList variant = null;
		Slot slot = null;
		Brand brand = null;
		Item item = null;
		SubCategory subCategory = null;
		Measurement measurement = null;
		Calculation calculation = new Calculation();
		try {
			FileInputStream file = new FileInputStream(data);
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				System.out.println("Sheet name is " + sh.getSheetName());
				System.out.println("Sheet name is " + sh.getLastRowNum());
				System.out.println("Sheet cell no is " + sh.getRow(0).getLastCellNum());
				System.out.println("col data ==" + sh.getRow(1).getCell(1));
				System.out.println("------------");
				Iterator<Row> iterator = sh.iterator();

//				requestUrl = calculation.ExcelToString(sh.getRow(1).getCell(1));
				String data1 = "" + sh.getRow(1).getCell(1);
//				requestUrl = "Sheet name is " + sh.getRow(1).getCell(1).getCellType() + sh.getRow(1).getCell(1);
				String data2 = new String(data1).trim();
//				requestUrl = data2.replace(" ", "_");
				for (int i = 1; i <= sh.getLastRowNum(); i++) {
					category = new Category();
					brand = new Brand();
					item = new Item();
					variant = new ItemList();
					subCategory = new SubCategory();
					slot = new Slot();
					measurement = new Measurement();
					int categoryId = 0, brandId = 0, productId = 0, measurementId = 0, venderId = 0, variantId = 0,
							slotId = 0, subCategoryId = 0;

					String slotNumber = null, productName = null;

					for (int j = 0; j < sh.getRow(i).getLastCellNum(); j++) {
						String cellHeading = sh.getRow(0).getCell(j).toString();
						String cellType = sh.getRow(i).getCell(j).getCellType().toString();
						String cellData = sh.getRow(i).getCell(j).toString();
						Cell cell = sh.getRow(i).getCell(j);
						Calculation calculation1 = new Calculation();
						int cellDataInt = 0;
						String cellDataString = "";
						float cellDataFloat = 0;

						switch (cellHeading) {
						case "VENDER_ID":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								Boolean response = venderService.checkExits(shopId, cellValue);
								if (response) {
									venderId = cellValue;

								}
							}

							break;
						case "SLOT_NUMBER":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = slotService.checkExist(Integer.parseInt(cellValue.getName()), shopId,
										venderId);
								if (response) {
									slot = slotService.getSlotNumberAndShopId(Integer.parseInt(cellValue.getName()),
											shopId);
									slotNumber = cellValue.getName();
									slotId = slot.getId();
									Slot deActive = slotService.getDeactiveForExcelFile(slotId);
									slotService.updeteSlot(slot);
								}
							}

							break;
						case "SUB_CATEGORY":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = subCategoryService.cheackExit(categoryId, cellValue.getName());
								subCategoryList = subCategoryService.getAll();
								category = categoryList.get(0);
								if (response) {
									subCategory = subCategoryService.getSubCategoryByCategoryIdAndName(categoryId,
											cellValue.getName());
									subCategoryList.add(subCategory);
									subCategoryId = subCategory.getId();
									subCategory = subCategoryService.deActiveById(subCategoryId);
									boolean response2 = subCategoryService.upDateSubCategory(subCategory);
								}
							}
							break;
						case "CATEGORY":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = categoryService.categoryExists(cellValue.getTitle(), shopId);
								categoryList = categoryService.getAllCategory();
								category = categoryList.get(0);
								if (response) {
									category = categoryService.getCategoryTitleByShopId(cellValue.getTitle(), shopId);
									categoryList.add(category);
									categoryId = category.getId();
									Category delete = categoryService.getDeActiveForExcel(categoryId);
									boolean response2 = categoryService.updateCategory(category);

								}

							}
							break;

						case "BRAND":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								brandList = brandService.getAllBrand();
								Boolean response = Boolean.FALSE;
								brand = brandList.get(0);
								if (response) {
									brand = brandService.getByTitleAndShopId(cellValue.getTitle(), shopId, categoryId,
											subCategoryId);
									brandList.add(brand);
									brandId = brand.getId();

									brand = brandService.getDeActiveForExcel(brandId);

									boolean response1 = brandService.updateBrand(brand);
								}

							}
							break;

						case "PRODUCT_NAME":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								productName = cellValue.getName();
								Boolean response = itemService.itemExit(cellValue.getTitle(), shopId, brandId,
										categoryId);
								itemList = itemService.getAll();
								item = itemList.get(0);
								if (response) {
									item = itemService.getItemForExcel(brandId, categoryId, shopId,
											cellValue.getTitle());
									itemList.add(item);
									productId = item.getId();
									item = itemService.getDeActiveForExcel(productId);
									boolean response1 = itemService.updateItem(item);
								}

							}
							break;

						case "MEASUREMENT":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = measurementService.measurementExists(cellValue.getTitle(), shopId);
								measurementList = measurementService.getAll();
								measurement = measurementList.get(0);
								if (response) {
									measurement = measurementService.getByTitleAndShopId(shopId, cellValue.getTitle());
									measurementList.add(measurement);
									measurementId = measurement.getId();
									measurement = measurementService.getDeActiveForExcel(measurementId);
									measurementService.updateMeasurement(measurement);
								}

							}
							break;

						case "UNIT_QUANTITY":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								Boolean response = itemListService.checkExist(productId, shopId, cellValue, slotNumber);
								if (response) {

									variant = itemListService.getForExcel(productId, shopId, cellValue, slotNumber);
									variantList = itemListService.getAll();
									variant = variantList.get(0);
									variantId = variant.getId();
									variant = itemListService.getDeActiveForExcel(variantId);
									boolean response1 = itemListService.updateItemList(variant);
									VariantStock variantStock2 = variantStockService.getDeActiveForExcel(variantId);
									Boolean response2 = variantStockService.updateStock(variantStock2);

								}
							}
							break;
						default:
							String s = "Last data";
						}
					}
				}

			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(response3);
	}

	@GetMapping("upload/excel1/{shopId}")
	public ResponseEntity<?> getExcelFile1(@PathVariable("shopId") String shopId) {
		File data = new File("D:\\java-workspace\\MIXTURE.xlsx");
//	String requestUrl = "";
		List<Category> categoryList = new ArrayList<Category>();
		List<Brand> brandList = new ArrayList<Brand>();
		List<Item> itemList = new ArrayList<Item>();
		List<ItemList> variantList = new ArrayList<ItemList>();
		List<Measurement> measurementList = new ArrayList<Measurement>();
		VariantStock variantStock = null;
		List<Slot> slottList = new ArrayList<Slot>();
		Category category = null;
		ItemList variant = null;
		Slot slot = null;
		Brand brand = null;
		Item item = null;
		SubCategory subCategory = null;
		Measurement measurement = null;
		Calculation calculation = new Calculation();

		try {
			FileInputStream file = new FileInputStream(data);
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
//				System.out.println("Sheet name is " + sh.getSheetName());
//				System.out.println("Sheet name is " + sh.getLastRowNum());
//				System.out.println("Sheet cell no is " + sh.getRow(0).getLastCellNum());
//				System.out.println("col data ==" + sh.getRow(1).getCell(1));
//				System.out.println("------------");
				Iterator<Row> iterator = sh.iterator();

//		requestUrl = calculation.ExcelToString(sh.getRow(1).getCell(1));
//				String data1 = "" + sh.getRow(1).getCell(1);
//		requestUrl = "Sheet name is " + sh.getRow(1).getCell(1).getCellType() + sh.getRow(1).getCell(1);
//				String data2 = new String(data1).trim();
//		requestUrl = data2.replace(" ", "_");
				for (int i = 1; i <= sh.getLastRowNum(); i++) {
					category = new Category();
					brand = new Brand();
					item = new Item();
					variant = new ItemList();
					subCategory = new SubCategory();
					slot = new Slot();
					measurement = new Measurement();
					int slotNumber = 0, categoryId = 0, brandId = 0, productId = 0, measurementId = 0, venderId = 0,
							variantId = 0, subCategoryId = 0, gstPercent = 0, otherExpense = 0, stock = 0,
							outOfStock = 0, variantStockId = 0, slotId = 0;
					float costPrice = 0, purchasePrice = 0, gstAmount = 0, netAmount = 0, purchaseDiscount = 0, mrp = 0;
					Boolean isIncluded = false;
					String productName = null, expAlert = null, rackNumber = null, barcode = null, hsnCode = null;
					for (int j = 0; j < sh.getRow(i).getLastCellNum(); j++) {
						String cellHeading = sh.getRow(0).getCell(j).toString();
						System.out.println("Data" + sh.getRow(i).getCell(j));

						String cellData = null;
						if (sh.getRow(i).getCell(j) != null) {
							cellData = sh.getRow(i).getCell(j).toString();
						}
						Cell cell = sh.getRow(i).getCell(j);

						switch (cellHeading) {
						case "VENDER_ID":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								Boolean response = venderService.checkExits(shopId, cellValue);
								if (response) {
									venderId = cellValue;
								}
							}

							break;

						case "GST_TYPE1":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
//								ExcelStringEntity cellValue = (ExcelStringEntity) cell;
								LookUp lookUp8 = lookup.findLookUpIdByName("MILAAN", cellValue.getTitle());
								int gstType = lookUp8.getLookUpId();
								slot.setGstType(gstType);
							}
							break;
						case "BILL_NUMBER":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = slotService.checkExist(Integer.parseInt(cellValue.getName()), shopId,
										venderId);
								if (response) {
									slot = slotService.getSlotNumberAndShopId(Integer.parseInt(cellValue.getName()),
											shopId);
									slotNumber = slot.getSlotNumber();
									slotId = slot.getId();
								} else {
									List<Slot> slotList = null;
									slotList = slotService.getByShopId(shopId);
									slotNumber = slotList.size() + 1;

									slot.setActive(Boolean.TRUE);
									slot.setDeleted(Boolean.FALSE);
									slot.setShopId(shopId);
									slot.setBillingNumber(cellValue.getName());
									slot.setVenderId(venderId);
									slot.setSlotNumber(slotNumber);
									// slot.setGstType(Integer.parseInt(cellValue.getTitle()));
									boolean create = slotService.slotCreate(slot);
									if (create) {
										slotId = slot.getId();
									}
								}
							}

							break;
						case "CATEGORY":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = categoryService.categoryExists(cellValue.getTitle(), shopId);
								if (response) {
									category = categoryService.getCategoryTitleByShopId(cellValue.getTitle(), shopId);
//							categoryList.add(category);
									categoryId = category.getId();
								} else {
									category.setTitle(cellValue.getTitle());
									category.setName(cellValue.getName());
									category.setShopId(shopId);
									category.setActive(true);
									category.setDeleted(false);

									boolean response1 = categoryService.createCategory(category);
									if (response1) {
										categoryId = category.getId();
									}
								}
							}
							break;

						case "SUB_CATEGORY":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean checkSubCategory = subCategoryService.cheackExit(categoryId,
										cellValue.getName());
								if (checkSubCategory) {
									subCategory = subCategoryService.getSubCategoryByCategoryIdAndName(categoryId,
											cellValue.getName());
									subCategoryId = subCategory.getId();
								} else {
									subCategory.setCategoryId(categoryId);
									subCategory.setActive(Boolean.TRUE);
									subCategory.setDeleted(Boolean.FALSE);
									subCategory.setName(cellValue.getName());
									subCategory.setCreatedOn(new Date());
									subCategory.setShopId(shopId);
									subCategory.setTitle(cellValue.getName());
									boolean cerateSubCategory = subCategoryService.CreateSubCategory(subCategory);
									if (cerateSubCategory) {
										subCategoryId = subCategory.getId();
									}
								}

							}
							break;

//						case "BRAND":
//							if (null != cellData) {
//								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
//								Boolean response = brandService.brandExists(cellValue.getTitle(), shopId, categoryId,
//										subCategoryId);
////								Boolean response = Boolean.TRUE;
//								if (response) {
//									brand = brandService.getByTitleAndShopId(cellValue.getTitle(), shopId, categoryId,
//											subCategoryId);
////							brandList.add(brand);
//									brandId = brand.getId();
//								} else {
//									brand.setCategory(categoryId);
//									brand.setTitle(cellValue.getTitle());
//									brand.setName(cellValue.getName());
//									brand.setShopId(shopId);
//									brand.setActive(true);
//									brand.setSubCategoryId(subCategoryId);
//									brand.setDeleted(false);
//
//									boolean response1 = brandService.createBrand(brand);
//									if (response1) {
//										brandId = brand.getId();
//									}
//								}
//							}
//							break;

						case "PRODUCT_NAME":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								productName = cellValue.getName();
								Boolean response = itemService.itemExit(cellValue.getTitle(), shopId, brandId,
										categoryId);
								if (response) {
									item = itemService.getItemForExcel(brandId, categoryId, shopId,
											cellValue.getTitle());
									itemList.add(item);
									productId = item.getId();
								} else {
									item.setActive(true);
									item.setBrand(brandId);
									item.setCategory(categoryId);
									item.setCreatedOn(new Date());
									item.setDeleted(false);
									item.setName(cellValue.getName());
									item.setShopId(shopId);
									item.setSubCategoryId(subCategoryId);
									item.setTitle(cellValue.getTitle());

									boolean response1 = itemService.createItem(item);
									if (response1) {
										productId = item.getId();
									}
								}
							}
							break;

						case "COMPANY_NAME":
							if (null != cellData && item != null) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								item.setCompanyName(cellValue.getName());
							}
							break;

						case "DESCRIPTION":
							if (null != cellData && item != null) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								item.setDescription(cellValue.getName());
								itemService.updateItem(item);
							}
							break;

						case "REVIEW":
							if (null != cellData && item != null) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								item.setReview(cellValue.getName());
								itemService.updateItem(item);
							}
							break;
						case "MEASUREMENT":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								Boolean response = measurementService.measurementExists(cellValue.getTitle(), shopId);
								if (response) {
									measurement = measurementService.getByTitleAndShopId(shopId, cellValue.getTitle());
									measurementList.add(measurement);
									measurementId = measurement.getId();

								} else {
									measurement.setActive(true);
									measurement.setCreatedOn(new Date());
									measurement.setDeleted(false);
									measurement.setShopId(shopId);
									measurement.setName(cellValue.getName());
									measurement.setTitle(cellValue.getTitle());

									boolean response1 = measurementService.createMeasurement(measurement);
									if (response1) {
										measurementId = measurement.getId();
									}
								}
							}
							break;

						case "UNIT_QUANTITY":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								Boolean response = itemListService.checkExist(productId, shopId, cellValue,
										String.valueOf(slotNumber));
								// Boolean response = itemListService.checkExistExcel(shopId, cellValue,
								// slotNumber);
								if (response) {

									variant = itemListService.getForExcel(productId, shopId, cellValue,
											String.valueOf(slotNumber));
									variantId = variant.getId();
									variantStock = variantStockService.getBySlotNumberAndVariantId((slotNumber),
											variantId);

								} else {
									variant.setActive(true);
									variant.setCreatedOn(new Date());
									variant.setDeleted(false);
									variant.setUnitQuantity(cellValue);
									variant.setSlotList(String.valueOf(slotNumber));
									variant.setMeasurement(measurementId);
									variant.setProductId(productId);
									variant.setShopId(shopId);

									boolean response1 = itemListService.itemListCreate(variant);
									if (response1) {
										variantStock = new VariantStock();
										variantId = variant.getId();
										variantStock.setSlotNumber(slotId);
										variantStock.setVariantId(variantId);
										variantStock.setActive(true);
										variantStock.setCreatedOn(new Date());
										variantStock.setDeleted(false);
										variantStock.setShopId(shopId);
										Boolean response2 = variantStockService.stockCreated(variantStock);
										if (response2) {
											variantStockId = variantStock.getId();
										}
									}
								}
							}
							break;

						case "COST_PRICE":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								costPrice = cellValue;
								variant.setCostPrice(calculation.DecimalCalculation(costPrice));
							}
							break;

						case "GST_PERCENT":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								gstPercent = cellValue;
								variant.setGstPercent(gstPercent);
							}
							break;

						case "GST_TYPE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								if (cellValue.getName().equals("INC")) {
									isIncluded = true;
									variant.setGstIncluded(isIncluded);
								} else if (cellValue.getName().equals("EXC")) {
									isIncluded = false;
									variant.setGstIncluded(isIncluded);
								}
							}
							break;
						case "OTHER_EXPENSE":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								otherExpense = cellValue;
								variant.setOtherCharge(cellValue);
							}
							break;

						case "PURCHASE_DISCOUNT":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								purchaseDiscount = cellValue;
								variant.setPurchaseOfferPercent(cellValue);
								variant.setPurchaseOfferPrice(cellValue);
							}
							break;

						case "MRP":
							if (null != cellData) {
								float cellValue = calculation.ExcelToFloat(cell);
								mrp = cellValue;
								variant.setMrp(calculation.DecimalCalculation(mrp));
							}
							break;

						case "STOCK":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								stock = cellValue;
								variant.setStock(stock);

								variantStock.setStock(cellValue);
								variantStock.setCurrentStock(cellValue);

								variantStockService.updateStock(variantStock);
							}
							break;

						case "STOCK_ALERT":
							if (null != cellData) {
								int cellValue = calculation.ExcelToInteger(cell);
								outOfStock = cellValue;
								variant.setOutOfStock(outOfStock);
							}
							break;

						case "RACK":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								rackNumber = cellValue.getName();
								variant.setRackNumber(rackNumber);
							}
							break;

						case "BARCODE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								barcode = cellValue.getName();
								variant.setBarcode(barcode);
							}
							break;

						case "HSN_CODE":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								hsnCode = cellValue.getName();
								variant.setHsnCode(hsnCode);
							}
							break;

						case "MFG_DATE":
							if (null != cellData) {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
								LocalDate dateOfManufacturing = null;
								try {
									dateOfManufacturing = LocalDate.parse(cellData, formatter);
									variant.setDateOfManufacturing(dateOfManufacturing);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							break;

						case "EXP_DATE":
							if (null != cellData) {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
								LocalDate expDate = null;
								try {
									expDate = LocalDate.parse(cellData, formatter);
									variant.setDateOfExpire(expDate);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							break;

						case "EXP_ALERT":
							if (null != cellData) {
								ExcelStringEntity cellValue = calculation.ExcelToString(cell);
								expAlert = cellValue.getName();
								variant.setExpiryAlert(expAlert);
							}
							break;

						case "CALCULATE":
							if (null != cellData) {
								Boolean cellValue = calculation.ExcelToBoolean(cell);
								if (cellValue) {
									if (isIncluded) {
										gstAmount = (costPrice / (100 + gstPercent)) * gstPercent;
										purchasePrice = costPrice + otherExpense - purchaseDiscount;
										variant.setGstAmount(calculation.DecimalCalculation(gstAmount));
										variant.setPurchasePrice(calculation.DecimalCalculation(purchasePrice));
										float margin = mrp - purchasePrice;
										float marginPercent = margin / (purchasePrice / 100);
										variant.setMargin(calculation.DecimalCalculation(margin));
										variant.setMarginPercent(calculation.DecimalCalculation(marginPercent));
										variant.setCustomerMargin(0);
										variant.setCustomerSingleOffer(0);
										variant.setCustomerBundleOffer(0);

										variant.setOfferActiveInd(false);
										variant.setDeliveryCharge(0);
										variant.setQuantity(1);
										variant.setBundleQuantity(1);
										variant.setGstIncluded(isIncluded);
										variant.setCustomerMarginPercent(0);
										variant.setVenderId(venderId);
										variant.setOnline(false);
										variant.setSlotCount(1);
										variant.setBundlePrice(calculation.DecimalCalculation(mrp));
										variant.setUnitSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setNetAmount(calculation.DecimalCalculation(stock * purchasePrice));
										variant.setName(productName);

										itemListService.updateItemList(variant);
										variant = null;
									} else {
										gstAmount = costPrice * gstPercent / 100;
										purchasePrice = costPrice + gstAmount + otherExpense - purchaseDiscount;
										variant.setGstAmount(calculation.DecimalCalculation(gstAmount));
										variant.setPurchasePrice(calculation.DecimalCalculation(purchasePrice));
										float margin = mrp - purchasePrice;
										float marginPercent = margin / (purchasePrice / 100);
										variant.setMargin(calculation.DecimalCalculation(margin));
										variant.setMarginPercent(calculation.DecimalCalculation(marginPercent));
										variant.setCustomerMargin(0);
										variant.setCustomerSingleOffer(0);
										variant.setCustomerBundleOffer(0);
										variant.setCustomerMarginPercent(0);
										variant.setOnline(false);
										variant.setOfferActiveInd(false);
										variant.setDeliveryCharge(0);
										variant.setQuantity(1);
										variant.setBundleQuantity(1);
										variant.setGstIncluded(isIncluded);
										variant.setCustomerMarginPercent(0);
										variant.setVenderId(venderId);
										variant.setSlotCount(1);
										variant.setBundlePrice(calculation.DecimalCalculation(mrp));
										variant.setUnitSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setSellingPrice(calculation.DecimalCalculation(mrp));
										variant.setNetAmount(calculation.DecimalCalculation(stock * purchasePrice));
										variant.setName(productName);

										itemListService.updateItemList(variant);
										variant = null;
									}
								}
							}
							break;

						case "LOOKUP_LABLE":
							break;
						default:
							String s = "Last data";
						}
					}
				}

			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(itemList, HttpStatus.OK);
	}
}
