package com.shop.shopservice.controller;

import java.net.URISyntaxException;
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

import com.shop.shopservice.constants.LocalData;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IMeasurementService;

import lombok.experimental.PackagePrivate;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {
	private final Logger log = LoggerFactory.getLogger(MeasurementController.class);
	@Autowired
	private IMeasurementService measurementService;

	@Autowired
	private IAdminService adminService;
	LocalData localData = new LocalData();
	@GetMapping("getall")
	public ResponseEntity<List<Measurement>> getAllMeasurement() {
		List<Measurement> measurementList = measurementService.getAll();
		return new ResponseEntity<List<Measurement>>(measurementList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Measurement> getById(@PathVariable("id") int id) {
		Measurement measurement = measurementService.getById(id);
		return new ResponseEntity<Measurement>(measurement, HttpStatus.OK);

	}
	
	@GetMapping("deactive/forexcel/{id}")
	public ResponseEntity<Measurement> getDeActiveForExcel(@PathVariable("id") int id){
		Measurement measurement = measurementService.getDeActiveForExcel(id);
		measurement.setActive(Boolean.FALSE);
		measurement.setDeleted(Boolean.TRUE);
		measurementService.updateMeasurement(measurement);
		return new ResponseEntity<Measurement>(measurement, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Measurement>> getMeasurementByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Measurement> measurementListLocal = localData.getMeasurementList();
		List<Measurement> measurementList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			if(measurementListLocal.size() <= 0) {
				measurementList = measurementService.getMeasurementByShopId(shopId);
				measurementListLocal = localData.setMeasurementList(measurementList);
			}
			
		}
		return new ResponseEntity<List<Measurement>>(measurementListLocal, HttpStatus.OK);
	}
	
	@GetMapping("getbyshopid/title/{shopId}/{title}")
	public ResponseEntity<Measurement> getByTitleAndShopId(@PathVariable("shopId") String shopId, @PathVariable("title") String title){
		Measurement measurement = measurementService.getByTitleAndShopId(shopId, title);
		return new ResponseEntity<Measurement>(measurement, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != measurementService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Measurement measurement = measurementService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME).toString();
				measurement.setName(name);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
				measurement.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				measurement.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED));
				measurement.setDeleted(isDeleted);
			}
			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				measurement.setCreatedOn(createdOn);
			}
			if (null != json.get(ServiceConstants.TITLE)) {
				String title = json.get(ServiceConstants.TITLE);
				measurement.setTitle(title);
			}
			measurementService.updateMeasurement(measurement);
			response.put("status", Boolean.TRUE.toString());
			response.put("Discreption", "Measurement updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Discreption", "Measurement not update");
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Measurement measurement = new Measurement(json.get(ServiceConstants.SHOP_ID), json.get(ServiceConstants.NAME));

		measurement.setActive(Boolean.TRUE);
		measurement.setCreatedOn(new Date());
		measurement.setDeleted(Boolean.FALSE);
		measurement.setName(json.get(ServiceConstants.NAME));
		measurement.setShopId(json.get(ServiceConstants.SHOP_ID));
		measurement.setTitle(json.get(ServiceConstants.TITLE));

		response.put("shopId", json.get(ServiceConstants.SHOP_ID));
		if (measurementService.measurementExists(measurement.getTitle(), measurement.getShopId())) {
			response.put("ststus", Boolean.FALSE.toString());
			response.put("Discreption", "Measurement already exist with given shopId and name");
		} else {
			boolean result = measurementService.createMeasurement(measurement);
			localData.setMeasurementList(new ArrayList<Measurement>());
			response.put("status", Strings.EMPTY + result);
			response.put("Discreption",
					"Measurement created successfully with given shopId and name");
		}
		return ResponseEntity.ok().body(response);
	}

}
