//package com.shop.shopservice.controller;
//
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import org.apache.logging.log4j.util.Strings;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shop.shopservice.constants.ServiceConstants;
//import com.shop.shopservice.entity.Brand;
//import com.shop.shopservice.entity.Category;
//import com.shop.shopservice.entity.Item;
//import com.shop.shopservice.entity.Search;
//import com.shop.shopservice.entity.SubCategory;
//import com.shop.shopservice.service.IBrandService;
//import com.shop.shopservice.service.ICategoryService;
//import com.shop.shopservice.service.IItemService;
//import com.shop.shopservice.service.ISearchService;
//import com.shop.shopservice.service.ISubCategoryService;
//
//@RestController
//@RequestMapping("/api/search")
//public class SearchController {
//	private final Logger log = LoggerFactory.getLogger(ReviewController.class);
//	@Autowired
//	private ISearchService searchService;
//
//	@Autowired
//	private IItemService itemService;
//
//	@Autowired
//	private ISubCategoryService subCategoryService;
//
//	@Autowired
//	private ICategoryService categoryService;
//
//	@Autowired
//	private IBrandService brandService;
//
//	@GetMapping("getall")
//	public ResponseEntity<List<Search>> getAll() {
//		List<Search> searchList = searchService.getAll();
//		return new ResponseEntity<List<Search>>(searchList, HttpStatus.OK);
//	}
//
//	@GetMapping("get/{id}")
//	public ResponseEntity<Search> getById(@PathVariable("id") int id) {
//		Search search = searchService.getById(id);
//		return new ResponseEntity<Search>(search, HttpStatus.OK);
//	}
//
//	@GetMapping("create/{shopId}")
//	ResponseEntity<Map<String, String>> createSalary(@PathVariable("shopId") String shopId) {
//
//		Map<String, String> response = new HashMap<String, String>();
//		boolean truncate = searchService.truncateTable();
//		List<Brand> brandList = brandService.getAllOnlineBrand(true);
//		List<Category> categoryList = categoryService.getAllOnlineCategory(true);
//		List<Item> itemList = itemService.getAllOnlineItem(true);
//		List<SubCategory> subCategoryList = subCategoryService.getSubcategoryByShopId(shopId);
//
//		for (int i = 0; i < itemList.size(); i++) {
//			String searchKey = itemList.get(i).getName();
//			int subCategoryId = itemList.get(i).getSubCategoryId();
//			int brandId = itemList.get(i).getBrand();
//			int categoryId = itemList.get(i).getCategory();
//			
//			for (int j = 0; j < subCategoryList.size(); j++) {
//				if (subCategoryList.get(j).getId() == subCategoryId) {
//					searchKey = searchKey + "," + subCategoryList.get(j).getName();
//				}
//			}
//
//			for (int j = 0; j < categoryList.size(); j++) {
//				if (categoryList.get(j).getId() == categoryId) {
//					searchKey = searchKey + "," + categoryList.get(j).getName();
//				}
//			}
//
//			for (int j = 0; j < brandList.size(); j++) {
//				if (brandList.get(j).getId() == brandId) {
//					searchKey = searchKey + "," + brandList.get(j).getName();
//				}
//			}
//			itemList.get(i).setSearchKeyword(searchKey);
//			boolean update = itemService.updateItem(itemList.get(i));
//			if(update) {
//				response.put("status",Boolean.TRUE.toString());
//				response.put("discription","SUCCESSFUL");
//			}else {
//				response.put("status",Boolean.FALSE.toString());
//				response.put("discription","NOT SUCCESSFUL");
//			}
//
//		}
//
////		for(int i =0; i<subCategoryList.size(); i++) {
////			Search search = new Search();
////			search.setSearchKey(subCategoryList.get(i).getName());
////			search.setTypeId(String.valueOf(subCategoryList.get(i).getId()));
////			search.setType("SUBCAT");
////			search.setSubCategoryId("0");
////			search.setName("");
////			
////			boolean create = searchService.createSearch(search);
////		}
////		
////		
////		for(int i =0; i<categoryList.size(); i++) {
////			Search search = new Search();
////			search.setSearchKey(categoryList.get(i).getName());
////			search.setTypeId(String.valueOf(categoryList.get(i).getId()));
////			search.setType("CATEGORY");
////			search.setSubCategoryId("0");
////			search.setName("");
////			
////			boolean create = searchService.createSearch(search);
////		}
////		
////		
////		for(int i =0; i<brandList.size(); i++) {
////			Search search = new Search();
////			search.setSearchKey(brandList.get(i).getName());
////			search.setTypeId(String.valueOf(brandList.get(i).getId()));
////			search.setType("BRAND");
////			search.setSubCategoryId(String.valueOf(brandList.get(i).getSubCategoryId()));
////			search.setName("");
////			
////			boolean create = searchService.createSearch(search);
////		}
//
//		return ResponseEntity.ok().body(response);
//	}
//
//	@SuppressWarnings({})
//	@PostMapping("/create")
//	ResponseEntity<Map<String, String>> createSalary(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
//		Map<String, String> response = new HashMap<String, String>();
//		Search search = new Search();
//
//		search.setSearchKey(json.get(ServiceConstants.SEARCH_KEY));
//		search.setTypeId(json.get(ServiceConstants.TYPE_ID));
//		search.setType(json.get(ServiceConstants.TYPE));
//		search.setSubCategoryId(json.get(ServiceConstants.SUB_CAT_ID));
//		search.setName(json.get(ServiceConstants.NAME));
//		boolean create = searchService.createSearch(search);
//		response.put("status", Strings.EMPTY + create);
//		response.put("description",
//				"Salary created successfully with given employeeId, go through your inbox to activate");
//		return ResponseEntity.ok().body(response);
//	}
//
//	@GetMapping("truncate")
//	public ResponseEntity<Search> truncateTable() {
//		boolean search = searchService.truncateTable();
//		return new ResponseEntity<Search>(HttpStatus.OK);
//	}
//
//}
