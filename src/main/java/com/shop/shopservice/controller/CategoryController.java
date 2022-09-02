package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ICategoryDAO;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.ICategoryService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.ISubCategoryService;

@RestController
@RequestMapping("/api/category")

public class CategoryController {
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IItemListService itemListService;

	@Autowired
	ICategoryDAO categoryDao;

	@Autowired
	private IAdminService adminservice;

	@Autowired
	private IItemService itemService;

	@Autowired
	private ISubCategoryService subCategoryService;

	@Autowired
	private IBrandService brandService;
	LocalData localData = new LocalData();

	@GetMapping("getallcategory")
	public ResponseEntity<List<Category>> getAllcategory() {
		List<Category> categoryList = categoryService.getAllCategory();
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getdeactivecategorybyshopid/{shopId}")
	public ResponseEntity<List<Category>> getAllDeactiveCategoryByShopId(@PathVariable("shopId") String shopId) {
		List<Category> categoryList = categoryService.getAllDeactiveCategoryByShopId(shopId);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getcategory/online/{id}/{isOnline}")
	public ResponseEntity<List<Category>> getAllOnlineCategoryById(@PathVariable("id") int id,
			@PathVariable("isOnline") boolean isOnline) {
		List<Category> categoryList = categoryService.getAllOnlineCategoryById(id, isOnline);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getallonline/{isOnline}")
	public ResponseEntity<List<Category>> getAllOnlineCategory(@PathVariable("isOnline") boolean isOnline) {
		List<Category> categoryList = categoryService.getAllOnlineCategory(isOnline);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getcategory/{title}/{shopId}")
	public ResponseEntity<Category> getCategoryTitleByShopId(@PathVariable("title") String title,
			@PathVariable("shopId") String shopId) {
		Category category = categoryService.getCategoryTitleByShopId(title, shopId);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Category> getById(@PathVariable("id") int id) {
		Category category = categoryService.getCategory(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("getdeactive/forexcel/{id}")
	public ResponseEntity<Category> getDeActiveForExcel(@PathVariable("id") int id) {
		Category category = categoryService.getDeActiveForExcel(id);
		category.setActive(Boolean.FALSE);
		category.setDeleted(Boolean.TRUE);
		categoryService.updateCategory(category);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("getcategorybyshopidandcategoryid/{shopId}/{id}")
	public ResponseEntity<List<Category>> getCategoryByShopIdAndId(@PathVariable("shopId") String shopId,
			@PathVariable("id") int id) {
		List<Category> categoryList = categoryService.getCategoryByShopIdAndId(shopId, id);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);

	}

	@GetMapping("getall/categorybyshopidforlocal/{shopId}")
	public ResponseEntity<List<Category>> getAllCategoryForLocal(@PathVariable("shopId") String shopId) {
//		List<Category> categoryListLocal = localData.getCategoryList();
//		if(categoryListLocal.size() <=0) {
		List<Category> categorieList = categoryService.getAllCategoryForLocal(shopId);
//			categoryListLocal = localData.setCategoryList(categorieList);
//		}

		return new ResponseEntity<List<Category>>(categorieList, HttpStatus.OK);
	}

//this
	@GetMapping("getcategoryforuserbyshopid/{shopId}")
	public ResponseEntity<List<Category>> getAllCategoryByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminservice.getAdminByShopId(shopId);
		List<Category> categoryList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			categoryList = categoryService.getCategoryForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getcategoryforuser")
	public ResponseEntity<List<Category>> getCategoryForUser() {
		List<Category> categoryList = categoryService.getCategoryForUser();
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getcategorybyshopid/{shopId}")
	public ResponseEntity<List<Category>> getCategoryByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminservice.getAdminByShopId(shopId);
//		List<Category> categoryListLocal = localData.getCategoryList();
		List<Category> categoryList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
//			if(categoryListLocal.size() <=0) {
			categoryList = categoryService.getCategoryByShopId(shopId);
//				categoryListLocal = localData.setCategoryList(categoryList);
		}

//		}
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		categoryDao.indexCategory();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Category>> searchAllCategoryBykeyword(@PathVariable("keyword") String keyword) {
		List<Category> result = new ArrayList<Category>();
		result = categoryDao.searchCategory(keyword);
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("getcategoryall/online/{id}")
	public ResponseEntity<Map<String, String>> getAllOnlineCategoryByAllProduct(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Category category = categoryService.updateOnlineByCategoryId(id);

		// Category category = categoryList.get(0);
//		List<SubCategory> subSategoryList = subCategoryService.getAllOnlineSubcategoryByCategoryId(id, isOnline);
//		for (int i = 0; i > subSategoryList.size(); i++) {
//			int subCategoryId = subSategoryList.get(i).getId();
//			List<Brand> brandList = brandService.getAllOnlineBrandBySubCategoryId(subCategoryId, isOnline);
//			for (int j = 0; j > brandList.size(); j++) {
//				int brandId = brandList.get(j).getId();
//				brandList.get(j).setOnline(isOnline);
//				brandService.updateBrand(brandList.get(j));
//				List<Item> itemList = itemService.getAllOnlineByBrand(brandId, isOnline);
//				for (int k = 0; k > itemList.size(); k++) {
//					int itemListId = itemList.get(k).getId();
//					itemList.get(k).setOnline(isOnline);
//					itemService.updateItem(itemList.get(k));
//					List<ItemList> variant = itemListService.getAllOnlineProductByProductId(itemListId, isOnline);
//					for (int l = 0; l > variant.size(); l++) {
//						variant.get(l).setOnline(isOnline);
//						itemListService.updateItemList(variant.get(l));
//					}
//				}
//
//			}
//
//			subSategoryList.get(i).setOnline(isOnline);
//
//			subCategoryService.upDateSubCategory(subSategoryList.get(i));
//		}
		category.setOnline(Boolean.TRUE);
		boolean update = categoryService.updateCategory(category);
		if (update) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "update online category");
		}
		// else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", "update online not category").toString();
//		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createCasul(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Category category = new Category(json.get(ServiceConstants.SHOP_ID), (json.get(ServiceConstants.NAME)));

		category.setName(json.get(ServiceConstants.NAME));
		category.setTitle(json.get(ServiceConstants.TITLE));
		category.setShopId(json.get(ServiceConstants.SHOP_ID));
		category.setActive(Boolean.TRUE);
		category.setDeleted(Boolean.FALSE);

		if (null != json.get(ServiceConstants.AVATAR)) {
			category.setAvatar(json.get(ServiceConstants.AVATAR));
		}

		response.put("NAME", json.get(ServiceConstants.NAME));
		if (categoryService.categoryExists(category.getName(), category.getShopId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Category already exist with given shopId");
		} else {
			boolean result = categoryService.createCategory(category);
			localData.setCategoryList(new ArrayList<Category>());
			response.put("categoryId", String.valueOf(category.getId()));
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Category created successfully with given shopId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != categoryService.getCategory(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Category category = categoryService.getCategory(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				category.setId(id);
			}

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				System.out.println(name);
				category.setName(name);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				category.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.TITLE)) {
				String title = json.get(ServiceConstants.TITLE);
				category.setTitle(title);
			}

			if (null != json.get(ServiceConstants.IS_ONLINE)) {
				boolean isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
				category.setOnline(isOnline);
			}
			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR);
				System.out.println(avatar);
				category.setAvatar(avatar);
			}

			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = (Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE)));
				System.out.println(isActive);
				category.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = (Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED)));
				System.out.println(isDeleted);
				category.setDeleted(isDeleted);
			}

			categoryService.updateCategory(category);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Category updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Category doesn't exist with given Id ");
		}

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<MyResponse> deleteCategory(@PathVariable("id") int id) {
		MyResponse myResponse = new MyResponse();
		Map<String, String> response = new HashMap<String, String>();
		Category category = categoryService.getCategory(id);
		if (null != category) {
		List<SubCategory> subCategoryList = subCategoryService.getSubcategoryByCategoryIdForDelete(id);
		if (subCategoryList.size() >0) {
//			myResponse.setData(subCategoryList);
			myResponse.setStatus(ResponseConstants.DEL_BEF_SUB_CAT);
			myResponse.setDescription(ResponseConstants.DEL_BEF_SUB_CAT_MSG[1]);

		} else {

				boolean result = categoryService.deleteCategory(id);
				if (result) {
					myResponse.setData(category);
					myResponse.setStatus(ResponseConstants.DEL_CAT);
					myResponse.setDescription(ResponseConstants.DEL_CAT_MSG[1]);

				} else {
					myResponse.setStatus(ResponseConstants.DEL_NOT_CAT);
					myResponse.setDescription(ResponseConstants.DEL_NOT_CAT_MSG[1]);

				}
		}
		}else {
			myResponse.setStatus(ResponseConstants.DEL_NOT_CAT);
			myResponse.setDescription(ResponseConstants.DEL_NOT_CAT_MSG[1]);
		}

		return ResponseEntity.ok().body(myResponse);
	}

}
