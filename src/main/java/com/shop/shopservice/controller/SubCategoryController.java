package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.ArrayUtils;
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
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.ICategoryService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.ISubCategoryService;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

	private final Logger log = LoggerFactory.getLogger(SubCategoryController.class);
	@Autowired
	private ISubCategoryService subCategoryService;

	@Autowired
	private IItemService itemService;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private ICategoryService categoryService;
	LocalData localData = new LocalData();

	@GetMapping("getall")
	public ResponseEntity<List<SubCategory>> getAll() {
		List<SubCategory> categoryList = subCategoryService.getAll();
		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getall1")
	public int[] getAllTest() {
		int array[] = { 45, 67, 34, 12, 44, 66 };
		int array1[] = { 00, 11, 11, 11, 11, 11 };
		int index = 5;
		int data = 45;
//		int[] addArray = ArrayUtils.addAll(array, array1);
		for (int i = 0; i < array.length; i++) {
			if (array.equals(data)) {
				array = ArrayUtils.remove(array, index);
			} else {
				array = ArrayUtils.add(array, data);
			}
		}
		return array1;
	}

	@GetMapping("getstring")
	public String[] getAllTestString() {
		String array[] = {"12","129","129","127","0909"};
		int array1[] = {00, 11, 11, 11, 11,11};
		int index =0;
		boolean check = false;
		String data = "129";
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(data)) {
				index = i;
				check = Boolean.TRUE;
				
				
			}

		}
		if(check) {
			array = (String[]) ArrayUtils.remove(array, index);
			
		}else {
			array = (String[]) ArrayUtils.add(array, data);
		}
		

		return array;

	}

	@GetMapping("get/{id}")
	public ResponseEntity<SubCategory> getById(@PathVariable("id") int id) {
		SubCategory subCategory = subCategoryService.getById(id);
		return new ResponseEntity<SubCategory>(subCategory, HttpStatus.OK);
	}

//	@GetMapping("getbyid/{categoryid}")
//	public ResponseEntity<List<SubCategory>> getByCategoryId(@PathVariable("categoryid") int categoryid){
//		SubCategory category = subCategoryService.getByCategoryId(categoryid);
//		return new ResponseEntity<List<SubCategory>>(HttpStatus.OK);
//	}

	@GetMapping("getallcategory/{categoryId}")
	public ResponseEntity<List<SubCategory>> getSubcategoryByCategoryId(@PathVariable("categoryId") int categoryId) {
		List<SubCategory> categoryList = subCategoryService.getSubcategoryByCategoryId(categoryId);
		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getallcategory/fordelete/{categoryId}")
	public ResponseEntity<List<SubCategory>> getSubcategoryByCategoryIdForDelete(
			@PathVariable("categoryId") int categoryId) {
		List<SubCategory> categoryList = subCategoryService.getSubcategoryByCategoryIdForDelete(categoryId);
		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("getallonline/byshopid/{shopId}/{isOnline}")
	public ResponseEntity<List<SubCategory>> getAllSubCategoryByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("isOnline") boolean isOnline) {
		List<SubCategory> subcategoryList = subCategoryService.getAllSubCategoryByShopId(shopId, isOnline);
		return new ResponseEntity<List<SubCategory>>(subcategoryList, HttpStatus.OK);
	}

	@GetMapping("getall/onlinecategory/{categoryId}/{isOnline}")
	public ResponseEntity<List<SubCategory>> getAllOnlineSubcategoryByCategoryId(
			@PathVariable("categoryId") int categoryId, @PathVariable("isOnline") boolean isOnline) {
		List<SubCategory> categoryList = subCategoryService.getAllOnlineSubcategoryByCategoryId(categoryId, isOnline);
		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
	}

//	@GetMapping("getbyshopid/{shopId}")
//	public ResponseEntity<List<SubCategory>> getSubcategoryByShopId(@PathVariable("shopId") String shopId) {
////		List<SubCategory> subCategorieListLocal = localData.getSubcategoryList();
////		if(subCategorieListLocal.size() <= 0) {
//			List<SubCategory> categoryList = subCategoryService.getSubcategoryByShopId(shopId);
//		//	subCategorieListLocal = localData.setSubcategoryList(categoryList);
////		}
//		
//		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
//	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<SubCategory>> getAllSubCategoryByShopId(@PathVariable("shopId") String shopId) {
		List<SubCategory> subCategoryList = subCategoryService.getAllSubcategoryByShopId(shopId);
		return new ResponseEntity<List<SubCategory>>(subCategoryList, HttpStatus.OK);
	}

	@GetMapping("getbyshopidforlocal/{shopId}")
	public ResponseEntity<List<SubCategory>> getSubcategoryByShopIdForLocal(@PathVariable("shopId") String shopId) {
		List<SubCategory> categoryList = null;
		List<SubCategory> subCategoryListForLocal = localData.getSubcategoryList();
		if (subCategoryListForLocal.size() <= 0) {
			categoryList = subCategoryService.getSubcategoryByShopId(shopId);
			subCategoryListForLocal = localData.setSubcategoryList(categoryList);
		}

		return new ResponseEntity<List<SubCategory>>(subCategoryListForLocal, HttpStatus.OK);
	}

	@GetMapping("get/subcategory/{shopId}")
	public ResponseEntity<List<SubCategory>> getSubcategoryByShopId(@PathVariable("shopId") String shopId) {
		List<SubCategory> categoryList = subCategoryService.getSubcategoryByShopId(shopId);
		return new ResponseEntity<List<SubCategory>>(categoryList, HttpStatus.OK);
	}

	@GetMapping("get/deactive/byid/{id}")
	public ResponseEntity<SubCategory> deActiveById(@PathVariable("id") int id) {
		SubCategory subCategory = subCategoryService.deActiveById(id);
		subCategory.setActive(Boolean.FALSE);
		subCategory.setDeleted(Boolean.TRUE);
		subCategoryService.upDateSubCategory(subCategory);
		return new ResponseEntity<SubCategory>(subCategory, HttpStatus.OK);
	}

	@GetMapping("getsubcategory/{categoryId}/{name}")
	public ResponseEntity<SubCategory> getSubCategoryByCategoryIdAndName(@PathVariable("categoryId") int categoryId,
			@PathVariable("name") String name) {
		SubCategory subCategory = subCategoryService.getSubCategoryByCategoryIdAndName(categoryId, name);
		return new ResponseEntity<SubCategory>(subCategory, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> createSubCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.CATEGORY_ID) && null != json.get(ServiceConstants.SHOP_ID)) {
			SubCategory subCategory = new SubCategory();

			subCategory.setActive(Boolean.TRUE);
			if (null != json.get(ServiceConstants.AVATAR)) {
				subCategory.setAvatar(json.get(ServiceConstants.AVATAR));
			}
			if (null != json.get(ServiceConstants.TITLE)) {
				subCategory.setTitle(json.get(ServiceConstants.TITLE));
			}

			subCategory.setCategoryId(Integer.parseInt(json.get(ServiceConstants.CATEGORY_ID)));
			subCategory.setCreatedOn(new Date());
			subCategory.setDeleted(Boolean.FALSE);
			subCategory.setName(json.get(ServiceConstants.NAME));
			subCategory.setShopId(json.get(ServiceConstants.SHOP_ID));

			boolean created = subCategoryService.cheackExit(subCategory.getCategoryId(), subCategory.getName());
			if (created) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "All ready created");
			} else {
				subCategoryService.CreateSubCategory(subCategory);
				localData.setSubcategoryList(new ArrayList<SubCategory>());
				response.put("status", Boolean.TRUE.toString());
				response.put("subCategoryId", String.valueOf(subCategory.getId()));
				response.put("description", "Sub-category created");
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateSubCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != subCategoryService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			SubCategory subCategory = subCategoryService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			int oldCategoryId = subCategory.getCategoryId();

			if (null != json.get(ServiceConstants.NAME)) {
				subCategory.setName(json.get(ServiceConstants.NAME));
			}

			if (null != json.get(ServiceConstants.TITLE)) {
				subCategory.setTitle(json.get(ServiceConstants.TITLE));
			}

			if (null != json.get(ServiceConstants.CATEGORY_ID)) {
				int newCategoryId = Integer.parseInt(json.get(ServiceConstants.CATEGORY_ID));
				if (oldCategoryId == newCategoryId) {
					subCategory.setCategoryId(Integer.parseInt(json.get(ServiceConstants.CATEGORY_ID)));
				} else {

					List<Brand> brandList = brandService.getAllBrandBySubCategoryId(subCategory.getId());
					List<Item> productList = itemService.getAllItemBySubCategory(subCategory.getId());
					subCategory.setCategoryId(Integer.parseInt(json.get(ServiceConstants.CATEGORY_ID)));
					for (int i = 0; i < brandList.size(); i++) {
						brandList.get(i).setCategory(newCategoryId);
						brandService.updateBrand(brandList.get(i));
					}
					for (int i = 0; i < productList.size(); i++) {
						productList.get(i).setCategory(newCategoryId);
						itemService.updateItem(productList.get(i));
					}
				}
//				subCategory.setCategoryId(Integer.parseInt(json.get(ServiceConstants.CATEGORY_ID)));

			}

			if (null != json.get(ServiceConstants.IS_ONLINE)) {
				boolean isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
				int categoryId = subCategory.getCategoryId();
				Category category = categoryService.getCategory(categoryId);
				if (isOnline) {
					category.setOnline(Boolean.TRUE);
					categoryService.updateCategory(category);
				} else {
					List<SubCategory> subCategories = subCategoryService.getSubcategoryByCategoryId(categoryId);
					int count = 0;
					for (int i = 0; i < subCategories.size(); i++) {
						if (subCategories.get(i).isOnline()) {
							if (subCategory.getId() != subCategories.get(i).getId()) {
								count++;
							}
							if (count > 0) {
								category.setOnline(Boolean.TRUE);

							} else {
								category.setOnline(Boolean.FALSE);
							}
							categoryService.updateCategory(category);

						}
					}
				}

				subCategory.setOnline(isOnline);
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				subCategory.setShopId(json.get(ServiceConstants.SHOP_ID));
			}

			if (null != json.get(ServiceConstants.AVATAR)) {
				subCategory.setAvatar(json.get(ServiceConstants.AVATAR));
			}

			boolean update = subCategoryService.upDateSubCategory(subCategory);
			if (update) {
				response.put("status", Boolean.TRUE.toString());
				response.put("descreption", "Update sub Category");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "Sub Category is not update");
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<MyResponse> deleteSubCategory(@PathVariable("id") int id) {
		MyResponse myResponse = new MyResponse();
		Map<String, String> response = new HashMap<String, String>();
		SubCategory subCategory = subCategoryService.getById(id);
		if (null != subCategory) {
		List<Brand> brandList = brandService.getAllBrandByCategoryForDelete(String.valueOf(id));
		if(brandList.size() >0) {
//			myResponse.setData(brandList);
			myResponse.setStatus(ResponseConstants.DEL_BEF_BRAND);
			myResponse.setDescription(ResponseConstants.DEL_BEF_BRAND_MSG[1]);
		}else {
				boolean result = subCategoryService.deleteSubCategory(id);
				if (result) {
					myResponse.setData(subCategory);
					myResponse.setStatus(ResponseConstants.DEL_SUB_CAT);
					myResponse.setDescription(ResponseConstants.DEL_SUB_CAT_MSG[1]);
				}else {
					myResponse.setStatus(ResponseConstants.DEL_NOT_SUB_CAT);
					myResponse.setDescription(ResponseConstants.DEL_NOT_SUB_CAT_MSG[1]);

				}
		}
		}else {
			myResponse.setStatus(ResponseConstants.DEL_NOT_SUB_CAT);
			myResponse.setDescription(ResponseConstants.DEL_NOT_SUB_CAT_MSG[1]);
		}
		
		return ResponseEntity.ok().body(myResponse);

	}

}
