package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.ArrayUtils;
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
import com.shop.shopservice.Idao.IBrandDAO;
import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ResponseConstants;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.ICategoryService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.ISubCategoryService;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
	private final Logger log = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private IBrandService brandService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ISubCategoryService subCategoryService;

	@Autowired
	private IItemService itemService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	IBrandDAO brandDao;

	@Autowired
	private IAdminService adminService;
	LocalData localData = new LocalData();

	@GetMapping("getallbrand")
	public ResponseEntity<List<Brand>> getAllbrand() {
		List<Brand> brandList = brandService.getAllBrand();
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

//	@GetMapping("getallbrandtest")
//	public ResponseEntity<List<Brand>> getAllbrandTest() {
//		List<Brand> brandList = brandService.getAllBrand();
//		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
//	}

	@GetMapping("getalldeactivebrandbyshopid/{shopId}")
	public ResponseEntity<List<Brand>> getAllDeactiveBrandByShopId(@PathVariable("shopId") String shopId) {
		List<Brand> brandList = brandService.getAllDeactiveBrandByShopId(shopId);
		// DateTimeFormatter myFormatObj = DateTimeFormatter.
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getbrand/{title}/{shopId}/{category}/{subCategoryId}")
	public ResponseEntity<Brand> getByTitleAndShopId(@PathVariable("title") String title,
			@PathVariable("shopId") String shopId, @PathVariable("category") int category,
			@PathVariable("subCategoryId") int subCategoryId) {
		Brand brand = brandService.getByTitleAndShopId(title, shopId, category, subCategoryId);
		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
	}

	@GetMapping("get/onlinebrand/{subCategoryId}/{isOnline}")
	public ResponseEntity<List<Brand>> getAllOnlineBrandBySubCategoryId(
			@PathVariable("subCategoryId") int subCategoryId, @PathVariable("isOnline") boolean isOnline) {
		List<Brand> brandList = brandService.getAllOnlineBrandBySubCategoryId(subCategoryId, isOnline);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getallonline/brand/{isOnline}")
	public ResponseEntity<List<Brand>> getAllOnlineBrand(@PathVariable("isOnline") boolean isOnline) {
		List<Brand> brandList = brandService.getAllOnlineBrand(isOnline);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getallonline/brandlocal/{shopId}")
	public ResponseEntity<List<Brand>> getAllOnlineBrandLocal(@PathVariable("shopId") String shopId) {
//		List<Brand> localBrandList = localData.getBrandList();
//		if(localBrandList.size() <=0) {
		List<Brand> brandList = brandService.getAllOnlineBrandLocal(shopId);
//			 localBrandList = localData.setBrandList(brandList);
//		}

		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("get/bycategory/{category}")
	public ResponseEntity<List<Brand>> getAllBrandByCategory(@PathVariable("category") int category) {
		List<Brand> brandList = brandService.getAllBrandByCategory(category);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("get/by/shopId/{shopId}")
	public ResponseEntity<List<Brand>> getByShopIdToAll(@PathVariable("shopId") String shopId) {
		List<Brand> brandList = brandService.getByShopIdToAll(shopId);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("subcategory/fordelete/{subCategoryId}")
	public ResponseEntity<List<Brand>> getAllBrandByCategoryForDelete(
			@PathVariable("subCategoryId") String subCategoryId) {
		List<Brand> brandList = brandService.getAllBrandByCategoryForDelete(subCategoryId);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("category/fordelete/{category}")
	public ResponseEntity<List<Brand>> getBrandByCategoryForDelete(@PathVariable("category") int category) {
		List<Brand> brandList = brandService.getBrandByCategoryForDelete(category);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("get/activecategory/{category}")
	public ResponseEntity<List<Brand>> getAllActiveBrandByCategory(@PathVariable("category") int category) {
		boolean isActive = true;
		List<Brand> brandList = brandService.getAllActiveBrandByCategory(category, isActive);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getbrand/bysubcategoryid/{subCategoryId}")
	public ResponseEntity<MyResponse> getBrandBySubCategoryId(@PathVariable("subCategoryId") int subCategoryId) {
		SubCategory subCategory = subCategoryService.getById(subCategoryId);
		MyResponse response = new MyResponse();
		List<Brand> brandList = new ArrayList<Brand>();
		String subCategoryBrand = subCategory.getBrandList();
		if (null != subCategoryBrand && subCategoryBrand.length() > 0) {
			String[] array = subCategoryBrand.split(",");
			List<String> myArray = Arrays.asList(array);
			for (int j = 0; j < myArray.size(); j++) {
				Brand brand = brandService.getBrand(Integer.parseInt(myArray.get(j)));
				brandList.add(brand);
			}
		}
		if (brandList.size() > 0) {
			response.setStatus(ResponseConstants.SUCCESS_MSG[0]);
			response.setDescription(ResponseConstants.SUCCESS_MSG[1]);
		} else {
			response.setStatus(ResponseConstants.NULL_MSG[0]);
			response.setDescription(ResponseConstants.NULL_MSG[1]);
		}
		response.setData(brandList);

		return new ResponseEntity<MyResponse>(response, HttpStatus.OK);
	}

	@GetMapping("get/bysubcategoryid/{subCategoryId}")
	public ResponseEntity<List<Brand>> getAllBrandBySubCategoryId(@PathVariable("subCategoryId") int subCategoryId) {
		List<Brand> brandList = brandService.getAllBrandBySubCategoryId(subCategoryId);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

//	@GetMapping("getallby/{id}")
//	public ResponseEntity<Brand> getByIdAndBrand(@PathVariable("id") int id){
//		boolean isActive = true;
//		Brand brand = brandService.getByIdAndBrand(id, isActive);
//		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
//	}

	@GetMapping("/fordelete/{subCategoryId}")
	public ResponseEntity<List<Brand>> getBrandBySubCategoryIdForDelete(
			@PathVariable("subCategoryId") int subCategoryId) {
		List<Brand> brandList = brandService.getBrandBySubCategoryIdForDelete(subCategoryId);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("wishlisttest")
	public ResponseEntity<Map<String, String>> addWishListByProductIdTest() {
		Map<String, String> response = new HashMap<String, String>();
		String str2 = "hello,computer,34,55,234,122";
		String[] array = str2.split(",");
		array = (String[]) ArrayUtils.remove(array, 2);
		array = (String[]) ArrayUtils.add(array, "100");
		// array=(String[]) ArrayUtils.
		String str = String.join(" ", array);
		response.put("data", str);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("gettest")
	public ResponseEntity<Map<String, String>> getForTest7() {
		Map<String, String> response = new HashMap<String, String>();
		String str = "22,33,55,66,88,99";
		String[] array1 = str.split(" ");
		// response.put("result1", array1);
		response.put("", str);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("gettest1")
	public ResponseEntity<Map<String, String>> getForTest() {
		Map<String, String> response = new HashMap<String, String>();
		String str = "22,33,55,66,88,99";
		String[] array1 = str.split(",");
		array1 = (String[]) ArrayUtils.add(array1, "100");
		array1 = (String[]) ArrayUtils.remove(array1, 1);
		String str1 = String.join(" ", array1);
		String[] str3 = array1;
		response.put("result1", str1);
		// response.put("result1", array1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("get/deactivecategory/{category}")
	public ResponseEntity<List<Brand>> getAllDeactiveBrandByCategory(@PathVariable("category") int category) {
		boolean isActive = false;
		List<Brand> brandList = brandService.getAllActiveBrandByCategory(category, isActive);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("add/subcategory/{id}/{subCategoryId}")
	public ResponseEntity<Map<String, String>> addSubcategoryInBrand(@PathVariable("id") int id,
			@PathVariable("subCategoryId") String subCategoryId) {
		Map<String, String> response = new HashMap<String, String>();
		Brand brand = brandService.getBrandById(id);

		if (null != brand) {
			String subCategoryList = brand.getSubCategoryId();
			String[] subCategoryArray = {};
			SubCategory subCategory = subCategoryService.getById(Integer.parseInt(subCategoryId));
			String brandList = subCategory.getBrandList();
			int brandListCount = subCategory.getBrandListCount();
			int brandId = brand.getId();
			String[] brandListArray = {};
			boolean find = false;
			boolean findBrand = false;
			int index = 0, brandIndex = 0;
			String wishLisListFinal = null;
			String brandListFinal = null;
			int wishCount = brand.getSubcategoryCount();
			if (!Strings.isBlank(subCategoryList)) {
				subCategoryArray = subCategoryList.split(",");
				for (int i = 0; i < subCategoryArray.length; i++) {
					if (subCategoryArray[i].equals(subCategoryId)) {
						index = i;
						find = Boolean.TRUE;
					}
				}
			}

			if (!Strings.isBlank(brandList)) {
				brandListArray = brandList.split(",");
				for (int i = 0; i < brandListArray.length; i++) {
					if (brandListArray[i].equals(String.valueOf(brand.getId()))) {
						brandIndex = i;
						findBrand = Boolean.TRUE;
					}
				}
			}

			if (findBrand) {
				brandListFinal = subCategory.getBrandList();
			} else {
				brandListArray = (String[]) ArrayUtils.add(brandListArray, String.valueOf(id));
				brandListFinal = String.join(",", brandListArray);
				subCategory.setBrandListCount(brandListCount + 1);
			}
			if (find) {
				wishLisListFinal = brand.getSubCategoryId();
			} else {
				subCategoryArray = (String[]) ArrayUtils.add(subCategoryArray, subCategoryId);
				brand.setSubcategoryCount(wishCount + 1);
				wishLisListFinal = String.join(",", subCategoryArray);
			}

			brand.setSubCategoryId(wishLisListFinal);
			subCategory.setBrandList(brandListFinal);
			subCategoryService.upDateSubCategory(subCategory);
			boolean result = brandService.updateBrand(brand);

			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Add succesfull");
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Add not succesfull");
			}
		}

//		boolean result = brandService.addSubcategoryInBrand(brand, subCategoryId);

		return ResponseEntity.ok().body(response);

	}

	@GetMapping("get/{id}")
	public ResponseEntity<Brand> getBrandById(@PathVariable("id") int id) {
		Brand brand = brandService.getBrandById(id);
		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
	}

	@GetMapping("deactive/forexcel/{id}")
	public ResponseEntity<Brand> getDeActiveForExcel(@PathVariable("id") int id) {
		Brand brand = brandService.getDeActiveForExcel(id);
		brand.setActive(Boolean.FALSE);
		brand.setDeleted(Boolean.TRUE);
		brandService.updateBrand(brand);
		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
	}

	@GetMapping("getbrandbyshopidandid/{shopId}/{id}")
	public ResponseEntity<List<Brand>> getBrandByShopIdAndId(@PathVariable("shopId") String shopId,
			@PathVariable("id") int id) {
		List<Brand> brandList = brandService.getBrandByShopIdAndId(shopId, id);
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getbrandforuserbyshopid/{shopId}")
	public ResponseEntity<List<Brand>> getBrandForUserByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Brand> brandList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			brandList = brandService.getBrandForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("getbrandbyshopid/{shopId}")
	public ResponseEntity<List<Brand>> getBrandByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
//		List<Brand> brandListLocal = localData.getBrandList();
		List<Brand> brandList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
//			if(brandListLocal.size() <= 0) {
			brandList = brandService.getBrandByShopId(shopId);
//				brandListLocal = localData.setBrandList(brandList);
		}

//		}
		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		brandDao.indexBrand();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("add")
	public String add() {
		String a = "ram" + " " + "mohan";
		return a;
	}
//	@GetMapping("loop")
//	public int loop() {
//		int  a= 0;
//		for(int i=1; i <10; i++) {
//		System.out.print("all data" + i);
//		}
//		return a;
//	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Brand>> searchAllBrandBykeyword(@PathVariable("keyword") String keyword) {
		List<Brand> result = new ArrayList<Brand>();
		result = brandDao.searchBrand(keyword);
		return new ResponseEntity<List<Brand>>(result, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBrand(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Brand brand = new Brand(json.get(ServiceConstants.SHOP_ID),
				(Integer.parseInt(json.get(ServiceConstants.CATEGORY))));

		brand.setTitle(json.get(ServiceConstants.TITLE));
		brand.setName(json.get(ServiceConstants.NAME));
		brand.setShopId(json.get(ServiceConstants.SHOP_ID));
		brand.setActive(Boolean.TRUE);
		brand.setDeleted(Boolean.FALSE);
		brand.setSubcategoryCount(1);
		brand.setCategory(Integer.parseInt(json.get(ServiceConstants.CATEGORY)));
		brand.setSubCategoryId(json.get(ServiceConstants.SUB_CATEGORY_ID));

		if (null != json.get(ServiceConstants.AVATAR)) {
			brand.setAvatar(json.get(ServiceConstants.AVATAR));
		}

		response.put("BRAND_NAME", json.get(ServiceConstants.NAME));
		if (brandService.brandExists(brand.getTitle())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Brand already exist with given shopId");
		} else {
			boolean result = brandService.createBrand(brand);
			if (result) {
				SubCategory subCategory = subCategoryService
						.getById(Integer.parseInt(json.get(ServiceConstants.SUB_CATEGORY_ID)));

				if (null != subCategory.getBrandList()) {
					if (!subCategory.getBrandList().isEmpty()) {
						String[] brandListArray = {};
						String finalBrandList = subCategory.getBrandList();
						int brandCount = subCategory.getBrandListCount();
						brandListArray = finalBrandList.split(",");
						brandListArray = (String[]) ArrayUtils.add(brandListArray, String.valueOf(brand.getId()));
						finalBrandList = String.join(",", brandListArray);
						subCategory.setBrandListCount(brandCount + 1);
						subCategory.setBrandList(finalBrandList);
						subCategoryService.upDateSubCategory(subCategory);
					} else {
						subCategory.setBrandList(String.valueOf(brand.getId()));
						subCategory.setBrandListCount(1);
						subCategoryService.upDateSubCategory(subCategory);
					}
				} else {
					subCategory.setBrandList(String.valueOf(brand.getId()));
					subCategory.setBrandListCount(1);
					subCategoryService.upDateSubCategory(subCategory);
				}

			}

			// localData.setBrandList(new ArrayList<Brand>());
			response.put("brandId", String.valueOf(brand.getId()));
			response.put("status", Strings.EMPTY + result);
			response.put("description", "Brand created successfully with given shopId");
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateAccount(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != brandService.getBrand(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Brand brand = brandService.getBrand(Integer.parseInt(json.get(ServiceConstants.ID)));

			int OldCategoryId = brand.getCategory();

			if (null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				brand.setId(id);

			}
			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR).toString();
				System.out.println(avatar);
				brand.setAvatar(avatar);

			}

			if (null != json.get(ServiceConstants.IS_ONLINE)) {
				boolean isOnline = Boolean.parseBoolean(json.get(ServiceConstants.IS_ONLINE));
				int subCategoryId = Integer.parseInt(json.get(ServiceConstants.OLD_SUB_CATEGORY_ID));
				int categoryId = brand.getCategory();
				SubCategory subCategory = subCategoryService.getById(subCategoryId);
				Category category = categoryService.getCategory(categoryId);
				if (isOnline) {
					category.setOnline(isOnline);
					subCategory.setOnline(isOnline);
					subCategoryService.upDateSubCategory(subCategory);
					categoryService.updateCategory(category);
				} else {
					List<Brand> brandList = brandService.getBrandBySubCategoryId(subCategoryId);
					int count = 0;
					for (int i = 0; i < brandList.size(); i++) {
						if (brandList.get(i).isOnline()) {
							if (brand.getId() != brandList.get(i).getId()) {
								count++;
							}

						}

					}

					if (count > 0) {
						category.setOnline(Boolean.TRUE);
						subCategory.setOnline(Boolean.TRUE);
					} else {
						category.setOnline(Boolean.FALSE);
						subCategory.setOnline(Boolean.FALSE);
					}
					subCategoryService.upDateSubCategory(subCategory);
					categoryService.updateCategory(category);
				}
				brand.setOnline(isOnline);
			}

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME).toString();
				System.out.println(name);
				brand.setName(name);

			}
			if (null != json.get(ServiceConstants.SUB_CATEGORY_ID)
					&& null != json.get(ServiceConstants.OLD_SUB_CATEGORY_ID)) {
				String OldSubCategoryId = json.get(ServiceConstants.OLD_SUB_CATEGORY_ID);
				String newSubCategoryId = json.get(ServiceConstants.SUB_CATEGORY_ID);
				SubCategory subCategory = subCategoryService.deActiveById(Integer.parseInt(newSubCategoryId));
				SubCategory oldSubCategory = subCategoryService.deActiveById(Integer.parseInt(OldSubCategoryId));

				if (OldSubCategoryId == newSubCategoryId) {
					brand.setSubCategoryId(brand.getSubCategoryId());
				} else {
					if (null != brand) {
						String subCategoryList = brand.getSubCategoryId();
						String brandList = subCategory.getBrandList();
						int brandCount = subCategory.getBrandListCount();

						String oldBrandList = oldSubCategory.getBrandList();
						int oldBrandCount = oldSubCategory.getBrandListCount();
						int wishCount = brand.getSubcategoryCount();
						String[] subCategoryArray = {};
						String[] brandListArray = {};
						String[] oldBrandListArray = {};

						boolean find = false;
						boolean findSubCategory = false;
						boolean findOldSubCategory = false;
						int index = 0, subCatIndex = 0, oldSubCatIndex = 0;

						String wishLisListFinal = null;
						String finalBrandList = null;
						String finalOldBrandList = null;
						if (!Strings.isBlank(subCategoryList)) {
							subCategoryArray = subCategoryList.split(",");
							for (int i = 0; i < subCategoryArray.length; i++) {
								if (subCategoryArray[i].equals(OldSubCategoryId)) {
									index = i;
									find = Boolean.TRUE;
								}
							}
						}

						if (!Strings.isBlank(brandList)) {
							brandListArray = brandList.split(",");
							for (int i = 0; i < brandListArray.length; i++) {
								if (brandListArray[i].equals(String.valueOf(brand.getId()))) {
									subCatIndex = i;
									findSubCategory = Boolean.TRUE;
								}
							}
						}
						if (!Strings.isBlank(oldBrandList)) {
							oldBrandListArray = oldBrandList.split(",");
							for (int i = 0; i < oldBrandListArray.length; i++) {
								if (oldBrandListArray[i].equals(String.valueOf(brand.getId()))) {
									oldSubCatIndex = i;
									findOldSubCategory = Boolean.TRUE;
								}
							}
						}

						if (findOldSubCategory) {
							oldBrandListArray = (String[]) ArrayUtils.remove(oldBrandListArray, oldSubCatIndex);
							finalOldBrandList = String.join(",", oldBrandListArray);
							oldSubCategory.setBrandListCount(oldBrandCount - 1);
						} else {
							finalOldBrandList = oldSubCategory.getBrandList();
						}

						if (!findSubCategory) {
							brandListArray = (String[]) ArrayUtils.add(brandListArray, String.valueOf(brand.getId()));
							finalBrandList = String.join(",", brandListArray);
							subCategory.setBrandListCount(brandCount + 1);
						} else {
							finalBrandList = subCategory.getBrandList();
						}

						if (find) {
							subCategoryArray = (String[]) ArrayUtils.remove(subCategoryArray, index);
							brand.setSubcategoryCount(wishCount - 1);
							wishLisListFinal = String.join(",", subCategoryArray);

							subCategoryArray = (String[]) ArrayUtils.add(subCategoryArray, newSubCategoryId);
							brand.setSubcategoryCount(wishCount + 1);
							wishLisListFinal = String.join(",", subCategoryArray);

						} else {
							response.put("status", Boolean.TRUE.toString());
							response.put("description", "subcategory not found");
							wishLisListFinal = brand.getSubCategoryId();
						}
						subCategory.setBrandList(finalBrandList);
						oldSubCategory.setBrandList(finalOldBrandList);
						brand.setSubCategoryId(wishLisListFinal);
						subCategoryService.upDateSubCategory(subCategory);
						subCategoryService.upDateSubCategory(oldSubCategory);
					}

					List<Item> itemList = itemService.getItemByBrand(brand.getId());
					for (int i = 0; i < itemList.size(); i++) {
						itemList.get(i).setSubCategoryId(Integer.parseInt(newSubCategoryId));
						itemService.updateItem(itemList.get(i));
					}
				}

			}
			if (null != json.get(ServiceConstants.CATEGORY)) {
				int newCategoryId = Integer.parseInt(json.get(ServiceConstants.CATEGORY));
				if (OldCategoryId == newCategoryId) {
					brand.setCategory(newCategoryId);
				} else {
					List<Item> itemList = itemService.getItemByBrand(brand.getId());
					for (int i = 0; i < itemList.size(); i++) {
						itemList.get(i).setCategory(newCategoryId);
						itemService.updateItem(itemList.get(i));
					}
					brand.setCategory(newCategoryId);
				}
				brand.setCategory(Integer.parseInt(json.get(ServiceConstants.CATEGORY)));
			}

			if (null != json.get(ServiceConstants.TITLE)) {
				String title = json.get(ServiceConstants.TITLE);
				System.out.println(title);
				brand.setTitle(title);
			}

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				brand.setShopId(shopId);

			}

			brandService.updateBrand(brand);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Brand updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Brand doesn't updated");
		}

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("delete/{id}")
	ResponseEntity<MyResponse> deleteBrand(@PathVariable("id") int id) {
		MyResponse myResponse = new MyResponse();
		Map<String, String> response = new HashMap<String, String>();
		Brand brand = brandService.getBrandById(id);
		if (null != brand) {

			List<Item> productList = itemService.getItemByBrand(id);
			if (productList.size() > 0) {
//				myResponse.setData(productList);
				myResponse.setStatus(ResponseConstants.DEL_BEF_PRO);
				myResponse.setDescription(ResponseConstants.DEL_BEF_PRO_MSG[1]);
			} else {
				boolean result = brandService.deleteBrand(id);
				if (result) {
					myResponse.setData(brand);
					myResponse.setStatus(ResponseConstants.DEL_BRAND);
					myResponse.setDescription(ResponseConstants.DEL_BRAND_MSG[1]);

				} else {
					myResponse.setStatus(ResponseConstants.DEL_NOT_BRAND);
					myResponse.setDescription(ResponseConstants.DEL_NOT_BRAND_MSG[1]);

				}

			}
		} else {
			myResponse.setStatus(ResponseConstants.DEL_NOT_BRAND);
			myResponse.setDescription(ResponseConstants.DEL_NOT_BRAND_MSG[1]);
		}
		return ResponseEntity.ok().body(myResponse);
	}
}
