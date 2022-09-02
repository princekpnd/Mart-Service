package com.shop.shopservice.controller;

import java.net.URISyntaxException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.IItemDAO;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICategoryService;
import com.shop.shopservice.service.IImageService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.IUserService;

@RestController
@RequestMapping("/api/item")
public class ItemController {
	@Autowired
	private IItemService itemService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IItemListService itemListService;
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IImageService imageService;

	@Autowired
	IItemDAO listDao;

	private final Logger log = LoggerFactory.getLogger(ItemController.class);
	LocalData localData = new LocalData();

	@GetMapping("getall")
	public ResponseEntity<List<Item>> getAll() {
		List<Item> itemList = itemService.getAll();
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getbyid/shopid/{id}/{shopId}")
	public ResponseEntity<List<Item>> getProductByShopIdAndId(@PathVariable("id") int id,
			@PathVariable("shopId") String shopId) {
		List<Item> itemList = itemService.getProductByShopIdAndId(id, shopId);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getitem/{subCategoryId}")
	public ResponseEntity<List<Item>> getAllItemBySubCategory(@PathVariable("subCategoryId") int subCategoryId) {
		List<Item> itemList = itemService.getAllItemBySubCategory(subCategoryId);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getitem/online/{subCategoryId}/{isOnline}")
	public ResponseEntity<List<Item>> getAllItemBySubCategoryAndOnline(@PathVariable("subCategoryId") int subCategoryId,
			@PathVariable("isOnline") boolean isOnline) {
		List<Item> itemList = itemService.getAllItemBySubCategoryAndOnline(subCategoryId, isOnline);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getby/category/{category}")
	public ResponseEntity<List<Item>> getByCategory(@PathVariable("category") int category) {
		List<Item> itemList = itemService.getByCategory(category);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("fordelete/category/{category}")
	public ResponseEntity<List<Item>> getByCategoryForDelete(@PathVariable("category") int category) {
		List<Item> itemList = itemService.getByCategoryForDelete(category);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/hotSell/{hotSell}")
	public ResponseEntity<List<Item>> getAllHotSale(@PathVariable("hotSell") boolean hotSell) {
		List<Item> itemList = itemService.getAllHotSale(hotSell);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/baggage/{baggage}")
	public ResponseEntity<List<Item>> getAllBaggage(@PathVariable("baggage") boolean baggage) {
		List<Item> itemList = itemService.getAllBaggage(baggage);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/milaanoffer/{milaanOffer}")
	public ResponseEntity<List<Item>> getAllMilaanOffer(@PathVariable("milaanOffer") boolean milaanOffer) {
		List<Item> itemList = itemService.getAllMilaanOffer(milaanOffer);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/aditionaldiscount/{aditionalDiscount}")
	public ResponseEntity<List<Item>> getAllAditionlDiscount(
			@PathVariable("aditionalDiscount") boolean aditionalDiscount) {
		List<Item> itemList = itemService.getAllAditionlDiscount(aditionalDiscount);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/onlinecategory/{category}/{isOnline}")
	public ResponseEntity<List<Item>> getAllOnlineCategory(@PathVariable("category") int category,
			@PathVariable("isOnline") boolean isOnline) {
		List<Item> itemList = itemService.getAllOnlineCategory(category, isOnline);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("get/onlinebrand/{brand}/{isOnline}")
	public ResponseEntity<List<Item>> getAllOnlineByBrand(@PathVariable("brand") int brand,
			@PathVariable("isOnline") boolean isOnline) {
		List<Item> itemList = itemService.getAllOnlineByBrand(brand, isOnline);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getall/productonline/byshopid/{shopId}/{isOnline}")
	public ResponseEntity<List<Item>> getAllOnlineProductByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("isOnline") boolean isOnline) {
		List<Item> itemList = itemService.getAllOnlineProductByShopId(shopId, isOnline);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getallitem")
	public ResponseEntity<List<Item>> getAll1() {
		List<Item> itemList = itemService.getAll();
		List<Item> arrayItem = new ArrayList<Item>();
		int fromIndex = 0, toIndex = 30;
		for (int i = 0; i <= itemList.size(); i++) {
			toIndex = i;
		}
		return new ResponseEntity<List<Item>>(itemList.subList(fromIndex, toIndex), HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Item> getById(@PathVariable("id") int id) {
		Item item = itemService.getById(id);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getall/information/{id}")
	public ResponseEntity<List<ItemList>> getAllInformation(@PathVariable("id") int id) {
		List<ItemList> itemList = itemListService.getProductByProductId(id);
		Item item = itemService.getById(id);
		List<Image> imageList = imageService.getImageByProductId(id);
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).setItem(item);
			itemList.get(i).setImage(imageList);
		}

		return new ResponseEntity<List<ItemList>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getproduct/wishlist/{shopId}/{userId}")
	public ResponseEntity<ArrayList<Item>> getProductForUserByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("userId") int userId) {
		User user = UserService.getUser(userId);
		List<ItemList> localDatastore = localData.getVariantData();
		String wishList = user.getWishList();
		String[] wishLists = wishList.split(",");
		Admin admin = adminService.getAdminByShopId(shopId);
		ArrayList<Item> itemList = new ArrayList<>();
		List<Item> itemList1 = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			for (int i = 0; i < wishLists.length; i++) {
				int itemId = Integer.parseInt(wishLists[i]);
				itemList1 = itemService.getProductByShopIdAndId(itemId, shopId);
				if (itemList1.size() > 0) {
					List<ItemList> variantList = itemListService.getByProductId(itemId);
					itemList1.get(0).setItemList(variantList);
					itemList.add(itemList1.get(0));
				}
			}

		}
		return new ResponseEntity<ArrayList<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("deactive/forexcel/{id}")
	public ResponseEntity<Item> getDeActiveForExcel(@PathVariable("id") int id) {
		Item item = itemService.getDeActiveForExcel(id);
		item.setActive(Boolean.FALSE);
		item.setDeleted(Boolean.TRUE);
		itemService.updateItem(item);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getbytitle/{title}/{shopId}")
	public ResponseEntity<Item> getItemByTitle(@PathVariable("title") String title,
			@PathVariable("shopId") String shopId) {
		Item item = itemService.getItemByTitle(title, shopId);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getforexcel/{brand}/{category}/{shopId}/{title}")
	public ResponseEntity<Item> getItemForExcel(@PathVariable("brand") int brand,
			@PathVariable("category") int category, @PathVariable("shopId") String shopId,
			@PathVariable("title") String title) {
		Item item = itemService.getItemForExcel(brand, category, shopId, title);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getimage/{id}")
	public ResponseEntity<Item> getImageById(@PathVariable("id") int id) {
		Item item = itemService.getById(id);
		String shopId = item.getShopId();
		List<Image> imageList = imageService.getImageByShopIdAndProductId(shopId, id);
		item.setImage(imageList);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getlist/{id}")
	public ResponseEntity<Item> getById1(@PathVariable("id") int id) {
		Item item = itemService.getById(id);
		List<ItemList> itemList = itemListService.getByProductId(id);
		List<Image> productImage = imageService.getImageByProductId(id);
		item.setItemList(itemList);
		item.setImage(productImage);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getitembybrand/{brand}")
	public ResponseEntity<List<Item>> getItemByBrand(@PathVariable("brand") int brand) {
		List<Item> itemList = itemService.getItemByBrand(brand);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		listDao.indexBrand();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Item>> searchAllItem(@PathVariable("keyword") String keyword) {
		List<Item> itemList = new ArrayList<Item>();
		itemList = listDao.searchItem(keyword);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getbyname/{name}")
	public ResponseEntity<Item> getByName(@PathVariable("name") String name) {
		Item item = itemService.getByName(name);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getallonline/{isOnline}")
	public ResponseEntity<List<Item>> getAllOnlineItem(@PathVariable("isOnline") boolean isOnline) {
		List<Item> itemList = itemService.getAllOnlineItem(isOnline);
		List<ItemList> itemList1 = itemListService.getAllOnlineVariant(isOnline);
		for (int i = 0; i < itemList.size(); i++) {
			int productId = itemList.get(i).getId();
			List<ItemList> variant = new ArrayList<ItemList>();
			for (int j = 0; j < itemList1.size(); j++) {
				if (productId == itemList1.get(j).getProductId()) {
					variant.add(itemList1.get(j));
				}
			}
			itemList.get(i).setItemList(variant);
		}

		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

//	@GetMapping("getallitembyshopid/{shopId}")
//	public ResponseEntity<List<Item>> getAllItemByShopId(@PathVariable("shopId") String shopId) {
//		List<Item> itemListLocal = localData.getItem();
//		if(itemListLocal.size() <= 0) {
//			List<Item> itemList = itemService.getAllItemByShopId(shopId);
//			itemListLocal = localData.setItem(itemList);
//		}
//		
//		return new ResponseEntity<List<Item>>(itemListLocal, HttpStatus.OK);
//	}

	@GetMapping("getallitembyshopid/{shopId}")
	public ResponseEntity<List<Item>> getAllByShopId(@PathVariable("shopId") String shopId) {
		List<Item> itemList = itemService.getAllItemByShopId(shopId);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);

	}

	@GetMapping("getactiveproduct/{shopId}")
	public ResponseEntity<List<Item>> getActiveProduct(@PathVariable("shopId") String shopId) {
		List<Item> itemList = itemService.getActiveProduct(shopId);
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@GetMapping("getproduct/{shopId}")
	public ResponseEntity<List<Item>> getAllItem(@PathVariable("shopId") String shopId) {
		List<Item> itemListLocal = localData.getItem();
		if (itemListLocal.size() <= 0) {
			List<Item> itemList = itemService.getAllItem(shopId);
			itemListLocal = localData.setItem(itemList);
		}

		return new ResponseEntity<List<Item>>(itemListLocal, HttpStatus.OK);
	}

	@GetMapping("getproduct/forlocal/{shopId}")
	public ResponseEntity<List<Item>> getAllItemForLocal(@PathVariable("shopId") String shopId) {
		List<Item> localItemWithVariant = localData.getItemWithVariant();
		if (localItemWithVariant.size() <= 0) {

			List<ItemList> localItemList = localData.getVariantData();
			List<Item> localItem = localData.getItem();
			if (localItemList.size() <= 0) {
				List<ItemList> itemList = itemListService.getByShopId(shopId);
				localItemList = localData.setVariantData(itemList);
			}

			if (localItem.size() <= 0) {
				List<Item> item = itemService.getAllItem(shopId);
				localItem = localData.setItem(item);
			}

			for (int i = 0; i < localItem.size(); i++) {
				int productId = localItem.get(i).getId();
				List<ItemList> itemList = new ArrayList<ItemList>();
				for (int j = 0; j < localItemList.size(); j++) {
					if (productId == localItemList.get(j).getProductId()) {
						itemList.add(localItemList.get(j));
					}
					localItem.get(i).setItemList(itemList);
				}
			}
			localData.setItemWithVariant(localItem);
			localItemWithVariant = localItem;
		}
		return new ResponseEntity<List<Item>>(localItemWithVariant, HttpStatus.OK);
	}

	@GetMapping("getby/shopid/name/{shopId}/{name}")
	public ResponseEntity<Item> getByNameAndShopId(@PathVariable("shopId") String shopId,
			@PathVariable("name") String name) {
		Item item = itemService.getByNameAndShopId(shopId, name);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Item>> getAllProductByShopId(@PathVariable("shopId") String shopId) {
		List<Item> itemList = null;
		Admin admin = adminService.getAdminByShopId(shopId);
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			itemList = itemService.getAllProductByShopId(shopId);

		}
		return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.NAME) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.CATEGORY) && null != json.get(ServiceConstants.BRAND)) {
			String name = json.get(ServiceConstants.NAME), shopId = json.get(ServiceConstants.SHOP_ID);
			int brand = Integer.parseInt(json.get(ServiceConstants.BRAND)),
					category = Integer.parseInt(json.get(ServiceConstants.CATEGORY));

			Item item = new Item(shopId, name);
			if (json.get(ServiceConstants.DESCRIPTION) != null) {
				item.setDescription(json.get(ServiceConstants.DESCRIPTION));
			}

			if (json.get(ServiceConstants.TITLE) != null) {
				item.setTitle(json.get(ServiceConstants.TITLE));
			}

			if (json.get(ServiceConstants.COMPANY_NAME) != null) {
				item.setCompanyName(json.get(ServiceConstants.COMPANY_NAME));
			}

			if (json.get(ServiceConstants.REVIEW) != null) {
				item.setReview(json.get(ServiceConstants.REVIEW));
			}

			if (json.get(ServiceConstants.IS_ONLINE) != null) {
				item.setOnline(Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE)));
			}

			if (json.get(ServiceConstants.PRODUCT_IMAGE) != null) {
				item.setProductImage((json.get(ServiceConstants.PRODUCT_IMAGE)));
			}

			item.setOnline(Boolean.FALSE);
			item.setActive(Boolean.TRUE);
			item.setBrand(brand);
			item.setCategory(category);
			item.setDeleted(Boolean.FALSE);
			item.setCreatedOn(new Date());
			item.setShopId(shopId);
			item.setName(name);
			item.setSubCategoryId(Integer.parseInt(json.get(ServiceConstants.SUB_CATEGORY_ID)));

			response.put("descreption", item.getName());
			if (itemService.itemExit(item.getTitle(), shopId, brand, category)) {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "All ready created");
			} else {
				itemService.createItem(item);
				localData.setItem(new ArrayList<Item>());
				response.put("productId", String.valueOf(item.getId()));
				response.put("status", Boolean.TRUE.toString());
				response.put("descreption", "Product created");
				response.put("productId", String.valueOf(item.getId()));
			}

		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateItem(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != itemService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Item item = itemService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			List<ItemList> itemList = itemListService.getByProductId(Integer.parseInt(json.get(ServiceConstants.ID)));
			List<Category> categoryList = categoryService.getAllCategory();

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				for (int i = 0; i < itemList.size(); i++) {
					int id = itemList.get(i).getId();
					ItemList itemList1 = itemListService.getById(id);
					itemList1.setName(name);
					itemListService.updateItemList(itemList1);
				}
				item.setName(name);
			}

			if (null != json.get(ServiceConstants.CATEGORY)) {
				int category = Integer.parseInt(json.get(ServiceConstants.CATEGORY));
				item.setCategory(category);
			}

			if (json.get(ServiceConstants.IS_ONLINE) != null) {
				item.setOnline(Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE)));
			}

			if (json.get(ServiceConstants.COMPANY_NAME) != null) {
				item.setCompanyName(json.get(ServiceConstants.COMPANY_NAME));
			}

			if (json.get(ServiceConstants.TITLE) != null) {
				item.setTitle(json.get(ServiceConstants.TITLE));
			}

			if (null != json.get(ServiceConstants.BRAND)) {
				int brand = Integer.parseInt(json.get(ServiceConstants.BRAND));
				item.setBrand(brand);
			}

			if (null != json.get(ServiceConstants.MEASUREMENT)) {
				int measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT));
				item.setMeasurement(measurement);
			}

			if (json.get(ServiceConstants.PRODUCT_IMAGE) != null) {
				item.setProductImage((json.get(ServiceConstants.PRODUCT_IMAGE)));
			}

			if (null != json.get(ServiceConstants.SUB_CATEGORY_ID)) {
				item.setSubCategoryId(Integer.parseInt(json.get(ServiceConstants.SUB_CATEGORY_ID)));
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				item.setShopId(shopId);
			}

			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				item.setShopName(shopName);
			}

			if (null != json.get(ServiceConstants.REVIEW)) {
				String review = json.get(ServiceConstants.REVIEW);
				item.setReview(review);
			}

			if (json.get(ServiceConstants.DESCRIPTION) != null) {
				item.setDescription(json.get(ServiceConstants.DESCRIPTION));
			}
			boolean update = itemService.updateItem(item);
			if (update) {
				response.put("status", Boolean.TRUE.toString());
				response.put("descreption", "Update item");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "Update not item");
			}

		}
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<MyResponse> deleteProduct(@PathVariable("id") int id) {
		MyResponse myResponse = new MyResponse();
		Map<String, String> response = new HashMap<String, String>();
		Item item = itemService.getById(id);
		if (null != item) {
		List<ItemList> itemList = itemListService.getByProductIdForDelete(id);
		if (itemList.size() >0) {
//			myResponse.setData(itemList);
			myResponse.setStatus(ResponseConstants.DEL_BEF_VARI);
			myResponse.setDescription(ResponseConstants.DEL_BEF_VARI_MSG[1]);
		} else {
				boolean result = itemService.deleteProduct(id);
				if (result) {
					myResponse.setData(item);
					myResponse.setStatus(ResponseConstants.DEL_PRO);
					myResponse.setDescription(ResponseConstants.DEL_PRO_MSG[1]);
				} else {
					myResponse.setData(ResponseConstants.DEL_NOT_PRO);
					myResponse.setDescription(ResponseConstants.DEL_NOT_PRO_MSG[1]);
				}
		}
		}else {
			myResponse.setData(ResponseConstants.DEL_NOT_PRO);
			myResponse.setDescription(ResponseConstants.DEL_NOT_PRO_MSG[1]);
		}

		return ResponseEntity.ok().body(myResponse);
	}

}
