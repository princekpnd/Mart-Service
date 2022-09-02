package com.shop.shopservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.ShopImage;
import com.shop.shopservice.service.ArticleFileService;
import com.shop.shopservice.service.IShopImageService;

@RestController
@RequestMapping("/api/shopimage")
public class ShopImageController {
	private final Logger log = LoggerFactory.getLogger(ShopImageController.class);

	@Autowired
	private IShopImageService shopImageService;

	@Autowired
	ArticleFileService fileService;

	@Autowired
	IManagedObject ManagedObjectService;

	@GetMapping("getallshopimage")
	public ResponseEntity<List<ShopImage>> getAllImage() {
		List<ShopImage> imageList = shopImageService.getAllShopImage();
		return new ResponseEntity<List<ShopImage>>(imageList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<ShopImage> getImageById(@PathVariable("id") int id) {
		ShopImage image = shopImageService.getShopImageById(id);
		return new ResponseEntity<ShopImage>(image, HttpStatus.OK);
	}

	@GetMapping("getactiveimagebyshopId/{shopId}")
	public ResponseEntity<List<ShopImage>> getImageByShopId(@PathVariable("shopId") String shopId) {
		List<ShopImage> imageList = shopImageService.getShopImageByShopId(shopId);
		return new ResponseEntity<List<ShopImage>>(imageList, HttpStatus.OK);
	}

	@GetMapping("getbyshopidandproductid/{shopId}/{productId}")
	public ResponseEntity<List<ShopImage>> getImageByShopIdAndProductId(@PathVariable("shopId") String shopId,
			@PathVariable("productId") int productId) {
		List<ShopImage> imageList = shopImageService.getShopImageByShopIdAndProductId(shopId, productId);
		return new ResponseEntity<List<ShopImage>>(imageList, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateImage(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != shopImageService.getShopImageById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			ShopImage image = shopImageService.getShopImageById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
				int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID).toString());
				image.setProductId(productId);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID).toString();
				image.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.AVATAR_NAME)) {
				String avatarName = json.get(ServiceConstants.AVATAR_NAME).toString();
				image.setAvatarName(avatarName);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE).toString());
				image.setActive(isActive);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				image.setCreatedOn(createdOn);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
				image.setDeleted(isDeleted);
			}

			shopImageService.updateShopImage(image);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Image updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Image doesn't exist with given ShopId");
		}

		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/upload/avatar/{shopId}/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadavatarFile(@RequestParam("file") MultipartFile file,
			@PathVariable("shopId") String shopId, @PathVariable("productId") String productId) {
		System.out.println("Single SYS file upload with userId! : " + productId);
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
		}

		try {
			fileService.uploadShopImage(Arrays.asList(file), productId, shopId);
			
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);

	}
	
	@PostMapping(value = "/update/avatar/{shopId}/{productId}/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateavatarFile(@RequestParam("file") MultipartFile file,
			@PathVariable("shopId") String shopId, @PathVariable("productId") String productId, @PathVariable("imageId") int imageId) {
		System.out.println("Single SYS file upload with userId! : " + productId);
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
		}

		try {
			fileService.updateShopImage(Arrays.asList(file), productId, shopId, imageId);
			
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Object>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(),
				HttpStatus.OK);

	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.ACCOUNT_NUM));
		Map<String, String> response = new HashMap<String, String>();
		ShopImage shopImage = new ShopImage(json.get(ServiceConstants.SHOP_ID),
				Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)));

		shopImage.setShopId(json.get(ServiceConstants.SHOP_ID));
		shopImage.setActive(true);
		shopImage.setDeleted(false);
		shopImage.setCreatedOn(new Date());
		shopImage.setProductId(Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)));
		shopImage.setAvatarName(json.get(ServiceConstants.AVATAR_NAME));

		response.put("shopId", json.get(ServiceConstants.SHOP_ID));
		if (shopImageService.shopImageExists(shopImage.getShopId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Image already exist with given shopId");
		} else {
			boolean result = shopImageService.createShopImage(shopImage);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Image created successfully with given shopId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteImage(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		ShopImage shopImage = shopImageService.getShopImageById(id);
		if(null != shopImage){
			boolean result = shopImageService.deleteImage(id);
			if(result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description","shopImage delete with given id:" +id);
			}
			}else {
				response.put("status",Boolean.FALSE.toString());
				response.put("description", "shopImage does not exist with given image Id");
			
		}
		return ResponseEntity.ok().body(response);
	}

}

