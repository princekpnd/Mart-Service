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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IVariantStockService;


@RestController
@RequestMapping("/api/stock")
public class VariantStockController {
	private final Logger log = LoggerFactory.getLogger(VariantStockController.class);

	@Autowired
	private IVariantStockService variantStockService;
	
	@Autowired
	private IItemListService itemListService;

	@GetMapping("getall")
	public ResponseEntity<List<VariantStock>> getAll() {
		List<VariantStock> stockList = variantStockService.getAll();
		return new ResponseEntity<List<VariantStock>>(stockList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<VariantStock> getById(@PathVariable("id") int id) {
		VariantStock stock = variantStockService.getById(id);
		return new ResponseEntity<VariantStock>(stock, HttpStatus.OK);
	}
	
	@GetMapping("getbyvariant/{variantId}")
	public ResponseEntity<VariantStock> getByVariantId(@PathVariable("variantId") int variantId) {
		VariantStock stock = variantStockService.getByVariantId(variantId);
		return new ResponseEntity<VariantStock>(stock, HttpStatus.OK);
	}
	@GetMapping("getby/{slotNumber}/{variantId}")
	public ResponseEntity<VariantStock> getBySlotNumberAndVariantId(@PathVariable("slotNumber") int slotNumber,@PathVariable("variantId") int variantId){
		VariantStock stock = variantStockService.getBySlotNumberAndVariantId(slotNumber,variantId );
		return new ResponseEntity<VariantStock>(stock, HttpStatus.OK);
	}
	
	@GetMapping("deactive/forexcel/{variantId}")
	public ResponseEntity<VariantStock> getDeActiveForExcel(@PathVariable("variantId") int variantId){
		VariantStock stock = variantStockService.getDeActiveForExcel(variantId);
		stock.setActive(Boolean.FALSE);
		stock.setDeleted(Boolean.TRUE);
		variantStockService.updateStock(stock);
		return new ResponseEntity<VariantStock>(stock, HttpStatus.OK);
	}
	

	
	@GetMapping("getbyslotnumber/{slotNumber}")
	public ResponseEntity<List<VariantStock>> getBySlotNumber(@PathVariable("slotNumber") int slotNumber){
		List<VariantStock> stockList = variantStockService.getBySlotNumber(slotNumber);
		for(int i =0; i<stockList.size(); i++) {
			int variantId = stockList.get(i).getVariantId();
			ItemList itemList = itemListService.getById(variantId);
			stockList.get(i).setVariant(itemList);
		}
		return new ResponseEntity<List<VariantStock>>(stockList, HttpStatus.OK);
	}
//	@GetMapping("get/{shopId}")
//	public ResponseEntity<List<VariantStock>> getByShopId(@PathVariable("shopId") dint shopId) {
//		List<VariantStock> stockList = variantStockService.getByShopId(shopId);
//		return new ResponseEntity<List<VariantStock>>(stockList, HttpStatus.OK);
//	}
	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createWithdraw(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if(null != json.get(ServiceConstants.SHOP_ID)){
		VariantStock variantStock = new VariantStock();
		String shopId = json.get(ServiceConstants.SHOP_ID);
		
		
		List<ItemList> itemList = itemListService.getAllItemListByShopId(shopId);
		ItemList  item = itemList.get(0);
		int stock = item.getStock();
		int slotNumber = item.getSlotCount();
		int variantId = item.getId();

		variantStock.setActive(Boolean.TRUE);
		variantStock.setCreatedOn(new Date());
		variantStock.setCurrentStock(Integer.parseInt(json.get(ServiceConstants.CURRENT_STOCK)));
		variantStock.setDeleted(Boolean.FALSE);
		variantStock.setSlotNumber(slotNumber);
		variantStock.setSoldStock(Integer.parseInt(json.get(ServiceConstants.SOLD_STOCK)));
		variantStock.setStock(stock);
		variantStock.setVariantId(variantId);
		
		
		variantStock.setShopId(shopId);

		if (variantStockService.exitsStock(variantStock.getSlotNumber(),
				variantStock.getVariantId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("descreption", "All ready created");
		} else {
			variantStockService.stockCreated(variantStock);
			response.put("status", Boolean.TRUE.toString());
			response.put("descreption", "Stock created");
		}
		}
		return ResponseEntity.ok().body(response);
	}
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID) && null != variantStockService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			VariantStock variantStock = variantStockService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));
			boolean update = variantStockService.updateStock(variantStock);
		}
		return ResponseEntity.ok().body(response);
	}

}
