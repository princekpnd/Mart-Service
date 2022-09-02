package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.Idao.IProductDAO;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Distance;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.ArticleFileService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IImageService;
import com.shop.shopservice.service.IProductService;
import com.shop.shopservice.service.IUserAddressService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/product")

public class ProductController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IProductService productService;

	@Autowired
	ArticleFileService fileService;

	@Autowired
	IManagedObject ManagedObjectService;

	@Autowired
	private IImageService imageService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ILookUp lookup;

	@Autowired
	IUserAddressService userAddressService;

	@Autowired
	private IProductDAO productDao;

	@Autowired
	private IAdminService adminService;

	/* _____________________________POST SERVICE_____________________________ */

	/* _____________________________CREATE PRODUCT_____________________________ */

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createProduct(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();

		/* ___________________CHECK MENDATORY VARIABLES___________________ */
		if (null != json.get(ServiceConstants.BRAND) && null != json.get(ServiceConstants.SHOP_ID)
				&& null != json.get(ServiceConstants.OUT_OF_STOCK)) {

			/* ___________________VARIABLE CREATION___________________ */
			int measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT)),
					sellingPrice = Integer.parseInt(json.get(ServiceConstants.SELLING_PRICE)),
					gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT)),
					category = Integer.parseInt(json.get(ServiceConstants.CATEGORY)),
					quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY)),
					costPrice = Integer.parseInt(json.get(ServiceConstants.COST_PRICE)),
					stock = Integer.parseInt(json.get(ServiceConstants.STOCK)),
					brand = Integer.parseInt(json.get(ServiceConstants.BRAND)),
					outOfStock = Integer.parseInt(json.get(ServiceConstants.OUT_OF_STOCK)), offerPercent = 0;

			float gstAmount = 0, price = 0, discount = 0, oldPrice = 0, totalAmount = 0;

			boolean offerActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND));

			String shopId = json.get(ServiceConstants.SHOP_ID), exipryDate = null, manufacturingDate = null,
					shopName = null, name = json.get(ServiceConstants.NAME);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Calculation calculation = new Calculation();
			LocalDate offerTo = null, offerFrom = null;

			Admin admin = adminService.getAdminByShopId(shopId);
			shopName = admin.getShopName();
			Product product = new Product(brand, shopId);

			/* ___________________OFFER CHECKING TRUE PART___________________ */
			if (offerActiveInd) {
				offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
				discount = calculation.DecimalCalculation((sellingPrice * offerPercent) / (float) 100);
				price = calculation.DecimalCalculation(sellingPrice - discount);
				oldPrice = calculation.DecimalCalculation(sellingPrice);
				gstAmount = calculation.DecimalCalculation((price * gstPercent) / (float) 100);
				totalAmount = calculation.DecimalCalculation(price + gstAmount);
				

				product.setPrice(price);
				product.setOldPrice(oldPrice);
				product.setDiscount(discount);
				product.setOfferPercent(offerPercent);

				try {
					offerFrom = LocalDate.parse(json.get(ServiceConstants.OFFER_FROM), formatter);
					offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO), formatter);
					product.setOfferFrom(offerFrom);
					product.setOfferTo(offerTo);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				/* ___________________OFFER CHECKING FALSE PART___________________ */
				
				gstAmount = calculation.DecimalCalculation((sellingPrice * gstPercent) / (float) 100);
				totalAmount = calculation.DecimalCalculation(sellingPrice + gstAmount);
				
				product.setPrice(sellingPrice);
			}

			product.setGstAmount(gstAmount);
			product.setName(name);
			product.setCategory(category);
			product.setBrand(brand);
			product.setShopId(shopId);
			product.setQuantity(quantity);
			product.setSellingPrice(sellingPrice);
			product.setCostPrice(costPrice);
			product.setOfferActiveInd(offerActiveInd);
			product.setGstPercent(gstPercent);
			product.setMeasurement(measurement);
			product.setStock(stock);
			product.setShopName(shopName);
			product.setActive(Boolean.TRUE);
			product.setDeleted(Boolean.FALSE);
			product.setCreatedOn(new Date());
			product.setOutOfStock(outOfStock);
			product.setTotalAmount(totalAmount);

			if (null != json.get(ServiceConstants.DESCRIPTION)) {
				product.setDescription(json.get(ServiceConstants.DESCRIPTION));
			}

			if (null != json.get(ServiceConstants.BARCODE)) {
				product.setBarcode(json.get(ServiceConstants.BARCODE));
			}

			if (null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
				exipryDate = json.get(ServiceConstants.DATE_OF_EXPIRE);
				LocalDate dateOfExpire = null;
				try {
					System.out.println("Date" + exipryDate);
					dateOfExpire = LocalDate.parse(exipryDate, formatter);
					product.setDateOfExpire(dateOfExpire);
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
					product.setDateOfManufacturing(dateOfManufacturing);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			List<UserAddress> userAddressList = userAddressService.getAddressByShopId(shopId);
			String lat = userAddressList.get(0).getLatitude();
			String lon = userAddressList.get(0).getLongitude();
			product.setLatitude(lat);
			product.setLongitude(lon);

			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
			if (productService.productExists(shopId, name, category, brand)) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product already exist with given Shop Id");
			} else {
				boolean result = productService.createProduct(product);
				if (result && productService.productExists(shopId, name, category, brand)) {
					List<Product> productList = productService.getCurrentProduct(shopId, name, category, brand);
					int productId = productList.get(0).getProductId();
					response.put("productId", Strings.EMPTY + productId);
					response.put("status", Strings.EMPTY + result);
					response.put("description",
							"Product created successfully with given Shop Id.");
				}

			}
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.ok().body(response);
		}
	}
//	@PutMapping("increase/{productId}/{quantity}")
//	public ResponseEntity<Map<String, String>>increaseProduct(@PathVariable("productId") int productId, @PathVariable("quantity") int quantity){
//		Map<String, String> response = new HashMap<String, String>();
//		Product product = productService.getProduct(productId);
//		int quantity1 = product.getQuantity();
//		int gstAmount
//		return ResponseEntity.ok().body(response);
//	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCart(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate offerTo = null, offerFrom = null;
		boolean offerDone = false;

		if (null != json.get(ServiceConstants.PRODUCT_ID)) {
			if (productService.checkProduct(Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)))) {

				Product product = productService.getProduct(Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)));

				if (null != json.get(ServiceConstants.NAME)) {
					String name = json.get(ServiceConstants.NAME).toString();
					product.setName(name);
				}

				if (null != json.get(ServiceConstants.CATEGORY)) {
					int category = Integer.parseInt(json.get(ServiceConstants.CATEGORY).toString());
					product.setCategory(category);
				}

				if (null != json.get(ServiceConstants.BRAND)) {
					int brand = Integer.parseInt(json.get(ServiceConstants.BRAND).toString());
					product.setBrand(brand);
				}

				if (null != json.get(ServiceConstants.QUANTITY)) {
					int quantity = Integer.parseInt(json.get(ServiceConstants.QUANTITY).toString());
					product.setQuantity(quantity);
				}

				if (null != json.get(ServiceConstants.DESCRIPTION)) {
					String description = json.get(ServiceConstants.DESCRIPTION).toString();
					product.setDescription(description);
				}

				if (null != json.get(ServiceConstants.IS_ACTIVE)) {
					boolean isActive = Boolean.parseBoolean((json.get(ServiceConstants.IS_ACTIVE).toString()));
					product.setActive(isActive);
				}

				if (null != json.get(ServiceConstants.IS_DELETED)) {
					boolean isDeleted = Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED).toString());
					product.setDeleted(isDeleted);
				}

				if (null != json.get(ServiceConstants.BARCODE)) {
					String barcode = json.get(ServiceConstants.BARCODE).toString();
					product.setBarcode(barcode);
				}

				if (null != json.get(ServiceConstants.STOCK)) {
					int stock = Integer.parseInt(json.get(ServiceConstants.STOCK).toString());
					product.setStock(stock);

				}

				if (null != json.get(ServiceConstants.COST_PRICE)) {
					int costPrice = (Integer.parseInt(json.get(ServiceConstants.COST_PRICE).toString()));
					product.setCostPrice(costPrice);
				}

				if (null != json.get(ServiceConstants.SELLING_PRICE)) {
					int sellingPrice = Integer.parseInt(json.get(ServiceConstants.SELLING_PRICE).toString()),
							offerPercent = 0, gstPercent = 0;
					float gstAmount = 0, discount = 0, oldPrice = 0, price = 0;

					product.setSellingPrice(sellingPrice);
					if (product.isOfferActiveInd()){
						gstPercent = product.getGstPercent();
						offerPercent = product.getOfferPercent();
						discount = (sellingPrice * offerPercent) / (float) 100;
						price = sellingPrice - discount;
						oldPrice = sellingPrice;
						gstAmount = (price * gstPercent) / (float) 100;

						product.setDiscount(discount);
						product.setPrice(price);
						product.setOldPrice(oldPrice);
						product.setGstAmount(gstAmount);
					} else {
						gstPercent = product.getGstPercent();
						price = sellingPrice;
						oldPrice = sellingPrice;
						gstAmount = (price * gstPercent) / (float) 100;

						product.setPrice(price);
						product.setOldPrice(oldPrice);
						product.setGstAmount(gstAmount);
						product.setDiscount(0);
						product.setOfferPercent(0);
					}
				}

				if (null != json.get(ServiceConstants.OFFER_ACTIVE_IND)) {
					int offerPercent = 0, gstPercent = 0;
					float sellingPrice = 0, gstAmount = 0, discount = 0, oldPrice = 0, price = 0;
					boolean offerActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND));

					if (offerActiveInd) {
						if (null != json.get(ServiceConstants.OFFER_PERCENT)
								&& null != json.get(ServiceConstants.OFFER_FROM)
								&& null != json.get(ServiceConstants.OFFER_TO)) {

							sellingPrice = product.getSellingPrice();
							gstPercent = product.getGstPercent();
							offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
							discount = (sellingPrice * offerPercent) / (float) 100;
							price = sellingPrice - discount;
							oldPrice = sellingPrice;
							gstAmount = (price * gstPercent) / (float) 100;

							try {
								offerFrom = LocalDate.parse(json.get(ServiceConstants.OFFER_FROM), formatter);
								offerTo = LocalDate.parse(json.get(ServiceConstants.OFFER_TO), formatter);
								LocalDate nowDate = LocalDate.now();
								long fromDiff = ChronoUnit.DAYS.between(nowDate, offerFrom);
								long toDiff = ChronoUnit.DAYS.between(offerFrom, offerTo);

								if (fromDiff >= 0 && toDiff > 0) {
									product.setOfferFrom(offerFrom);
									product.setOfferTo(offerTo);
									product.setOfferActiveInd(offerActiveInd);
									product.setOfferPercent(offerPercent);
									product.setDiscount(discount);
									product.setPrice(price);
									product.setOldPrice(oldPrice);
									product.setGstAmount(gstAmount);
									offerDone = true;
								} else {
									offerDone = false;
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							offerDone = false;
						}

					} else {
						sellingPrice = product.getSellingPrice();
						gstPercent = product.getGstPercent();
						price = sellingPrice;
						oldPrice = sellingPrice;
						gstAmount = (price * gstPercent) / (float) 100;

						product.setPrice(price);
						product.setOldPrice(oldPrice);
						product.setOfferActiveInd(offerActiveInd);
						product.setDiscount(0);
						product.setOfferPercent(0);
						product.setGstAmount(gstAmount);
						offerDone = true;
					}

				}

				if (null != json.get(ServiceConstants.GST_PERCENT)) {
					int gstPercent = Integer.parseInt(json.get(ServiceConstants.GST_PERCENT).toString());
					float gstAmount = 0, price = 0;
					price = product.getPrice();
					product.setGstPercent(gstPercent);
					gstAmount = (price * gstPercent) / (float) 100;

					product.setGstAmount(gstAmount);
					product.setGstPercent(gstPercent);

				}

				if (null != json.get(ServiceConstants.DELIVERY_CHARGE)) {
					int deliveryCharge = Integer.parseInt(json.get(ServiceConstants.DELIVERY_CHARGE).toString());
					System.out.println(deliveryCharge);
					product.setDeliveryCharge(deliveryCharge);
				}

				if (null != json.get(ServiceConstants.MEASUREMENT)) {
					int measurement = Integer.parseInt(json.get(ServiceConstants.MEASUREMENT).toString());
					System.out.println(measurement);
					product.setMeasurement(measurement);
				}

				if (null != json.get(ServiceConstants.DATE_OF_MANUFACTURING)) {
					LocalDate dateOfManufacturing = LocalDate.parse(json.get(ServiceConstants.DATE_OF_MANUFACTURING));
					product.setDateOfManufacturing(dateOfManufacturing);
				}

				if (null != json.get(ServiceConstants.OUT_OF_STOCK)) {
					int outOfStock = Integer.parseInt(json.get(ServiceConstants.OUT_OF_STOCK));
					product.setOutOfStock(outOfStock);
				}

				if (null != json.get(ServiceConstants.OFFER_ACTIVE_IND)) {
					if (offerDone) {
						productService.updateProduct(product);
						response.put("status", Boolean.TRUE.toString());
						response.put("description", "Product updated");
					} else {
						response.put("status", Boolean.FALSE.toString());
						response.put("description", "Please enter a valid offer date/offer percent.");
						response.put("cause", "invalid_offer_date");
					}
				} else {
					productService.updateProduct(product);
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "Product updated");
				}
			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Product not found with given product ID.");
				response.put("cause", "product_not_found");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product id must be in parameter.");
			response.put("cause", "productId_not_found");
		}
		return ResponseEntity.ok().body(response);
	}

//	private static final GeoApiContext context = new GeoApiContext().setApiKey(ServiceConstants.GOOGLE_MAP_API);

//	public DistanceMatrix estimateRouteTime(DateTime time, Boolean isForCalculateArrivalTime, DirectionsApi.RouteRestriction routeRestriction, LatLng departure, LatLng... arrivals) {
//	    try {
//	        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
//	        if (isForCalculateArrivalTime) {
//	            req.departureTime(time);
//	        } else {
//	            req.arrivalTime(time);
//	        }
//	        if (routeRestriction == null) {
//	            routeRestriction = DirectionsApi.RouteRestriction.TOLLS;
//	        }
//	        DistanceMatrix trix = req.origins(departure)
//	                .destinations(arrivals)
//	                .mode(TravelMode.DRIVING)
//	                .avoid(routeRestriction)
//	                .language("fr-FR")
//	                .await();
//	        return trix;
//
//	    } catch (ApiException e) {
//	        System.out.println(e.getMessage());
//	    } catch (Exception e) {
//	        System.out.println(e.getMessage());
//	    }
//	    return null;
//	}
//	
//	private JsonElement callAndParse(String endpoint) {
//		URL url;
//		try {
//			url = new URL(endpoint);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLine;
//			StringBuffer content = new StringBuffer();
//			while ((inputLine = in.readLine()) != null) {
//				content.append(inputLine);
//			}
//			in.close();
//			con.disconnect();
//			return new JsonParser().parse(content.toString());
//		} catch (IOException e) {
//			JsonObject error = new JsonObject();
//			error.add("error", new JsonPrimitive(e.getMessage()));
//			return error;
//		}
//	}

//	@GetMapping("/distance")
//	ResponseEntity<?> getDistance() throws IOException {
//		Map<String, String> response = new HashMap<String, String>();
////
////		String response1 = callAndParse(
////				"https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=25.594095,85.137566&destinations=26.120888,85.364723&key="
////						+ ServiceConstants.GOOGLE_MAP_API).toString();
//
//		boolean result = true;
//
//		if (result) {
//			response.put("status", Boolean.TRUE.toString());
//			response.put("description", "Product updated");
//			response.put("data", response1 + "");
//		} else {
//			response.put("status", Boolean.FALSE.toString());
//			response.put("description", "Product doesn't exist with given  userid");
//		}
//
////		JsonObject json = JsonPrimitive(response1);
//		return ResponseEntity.ok().body(response1);
//	}
//	@GetMapping("get/all/activeproduct/{name}")
//	public ResponseEntity<List<Product>> getActiveProduct(@PathVariable("name") String name){
//		
//	}

	@GetMapping("getallproduct")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> productList = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getallofferproduct")
	public ResponseEntity<List<Product>> getAllOfferProduct() {
		List<Product> productList = productService.getAllOfferProduct();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getallproductforuser")
	public ResponseEntity<List<Product>> getAllProductForUser() {
		List<Product> productList = productService.getAllProductForUser();
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("get/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
		Product product = productService.getProduct(productId.intValue());
		String shopId = product.getShopId();
		List<Image> imageList = imageService.getImageByShopIdAndProductId(shopId, productId);
		product.setImage(imageList);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@GetMapping("getstock/{productId}")
	public ResponseEntity<Map<String, String>> getStockList(@PathVariable("productId") int productId){
		Map<String, String> response = new HashMap<String, String>();
	 	Product product = productService.getStock(productId);
		if(product.isActive() == Boolean.TRUE ) {
			int stock = product.getStock();
			if(product.getOutOfStock() <= stock && product.getOutOfStock() > 0) {
				product.setStock(product.getStock() + stock);
				boolean result = productService.updateProduct(product);
				if(result) {
					response.put("status", Boolean.TRUE.toString());
					response.put("status",String.valueOf(product.getStock()));
				}else {
					response.put("Status", Boolean.FALSE.toString());
					response.put("descreption", "Product not update");
				}
			}else {
				response.put("Status", Boolean.FALSE.toString());
				response.put("Descreption", "Product is out of stock");
			}
			
		}else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Descreption","Product is not available in the shop");
		}
		
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("check/{productId}")
	public ResponseEntity<Boolean> checkProductById(@PathVariable("productId") Integer productId) {
		Boolean product = productService.checkProduct(productId.intValue());
		return new ResponseEntity<Boolean>(product, HttpStatus.OK);
	}

	@GetMapping("getproductforofferbyproductid/{productId}")
	public ResponseEntity<Product> getProductForOfferByProductId(@PathVariable("productId") Integer productId) {
		// Product product = productService.getProductForOfferByProductId(productId
		// .intValue());
		Product product = productService.getProductForOfferByProductId(productId.intValue());
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@GetMapping("getproductbycartid/{cartId}")
	public ResponseEntity<List<Product>> getProductByCart(@PathVariable("cartId") int cartId) {
		List<Product> productList = productService.getProductByCartId(cartId);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbyname/{name}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable("name") String name) {
		List<Product> productList = productService.getProductByName(name);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbycategory/{category}")
	public ResponseEntity<Product> getProductByCategory(@PathVariable("category") Integer category) {
		Product product = productService.getProductByCategory(category.intValue());
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("getproductbyshopidandcategory/{shopId}/{category}")
	public ResponseEntity<List<Product>> getProductByShopIdForCategory(@PathVariable("shopId") String shopId,
			@PathVariable("category") Integer category) {
		List<Product> productList = productService.getProductByShopIdForCategory(shopId, category.intValue());
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproduct/shopid/brand/{shopId}/{brand}")
	public ResponseEntity<List<Product>> getProductByShopIdAndBrand(@PathVariable("shopId") String shopId,
			@PathVariable("brand") int brand) {
		List<Product> productList = productService.getProductByShopIdAndBrand(shopId, brand);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbybrand/{brand}")
	public ResponseEntity<List<Product>> getProductByBrand(@PathVariable("brand") int brand) {
		List<Product> productList = productService.getProductByBrand(brand);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("get/offerproduct/byshopid/{shopId}")
	public ResponseEntity<List<Product>> getProductOfferByShopId(@PathVariable("shopId") String shopId) {
		List<Product> productList = productService.getProductOffreByShopId(shopId);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbyshopid/{shopId}")
	public ResponseEntity<List<Product>> getProductByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Product> productList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			productList = productService.getProductByShopId(shopId);
		}
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductforuserbyshopId/{shopId}")
	public ResponseEntity<List<Product>> getProductForUserByShopId(@PathVariable("shopId") String shopId) {
		Admin admin = adminService.getAdminByShopId(shopId);
		List<Product> productList = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			productList = productService.getProductForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbyproductidandshopid/{productId}/{shopId}")
	public ResponseEntity<List<Product>> getProductByProductIdAndShopId(@PathVariable("productId") int productId,
			@PathVariable("shopId") String shopId) {
		List<Product> productList = productService.getProductByProductIdAndShopId(productId, shopId);
		List<Image> imageList = imageService.getImageByShopIdAndProductId(shopId, productId);
		productList.get(0).setImage(imageList);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getcurrentproduct/{shopId}/{name}/{category}/{brand}")
	public ResponseEntity<List<Product>> getCurrentProduct(@PathVariable("shopId") String shopId,
			@PathVariable("name") String name, @PathVariable("category") int category,
			@PathVariable("brand") int brand) {
		List<Product> productList = productService.getCurrentProduct(shopId, name, category, brand);

		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproduct/wishlist/{shopId}/{userId}")
	public ResponseEntity<ArrayList<Product>> getProductForUserByShopId(@PathVariable("shopId") String shopId,
			@PathVariable("userId") int userId) {
		User user = userService.getUser(userId);
		String wishList = user.getWishList();
		String[] wishLists = wishList.split(",");
		Admin admin = adminService.getAdminByShopId(shopId);
		ArrayList<Product> productList = new ArrayList<>();
		List<Product> productList1 = null;
		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
			for (int i = 0; i < wishLists.length; i++) {
				int productId = Integer.parseInt(wishLists[i]);
				productList1 = productService.getProductByProductIdAndShopId(productId, shopId);
				if (productList1.size() > 0) {
					productList.add(productList1.get(0));
				}
			}
		}
		return new ResponseEntity<ArrayList<Product>>(productList, HttpStatus.OK);
	}

//	@GetMapping("getwishlist/{userId}")
//	public ResponseEntity<List<Product>> getProductWishList(@PathVariable("userId") int userId){
//        User user = userService.getUser(userId);
//		
//		String wishList = user.getWishList();
//		String[] wishLists = wishList.split(",");
//		Admin admin = adminService.getAdminByShopId(shopId);
//		ArrayList<Product> productList = new ArrayList<>();
//		Product product = null;
//		if (admin != null && admin.isActive() == Boolean.TRUE && admin.isDeleted() == Boolean.FALSE) {
//			for(int i = 0; i < wishLists.length; i++) {
//				int productId = Integer.parseInt(wishLists[i]);
//				product = productService.getProduct(productId);
//				productList.add(product);
//			}		
//		}
//	//	List<Product> productList = productService.getProductForWishList(userId);
//		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
//		
//		
//	}

	@GetMapping("getwishlist/{userId}")
	public ResponseEntity<List<Product>> getProductWishList(@PathVariable("userId") int userId) {
		User user = userService.getUser(userId);

		String wishList = user.getWishList();
		String[] wishLists = wishList.split(",");
		ArrayList<Product> productList = new ArrayList<>();
		Product product = null;
		if (user != null && user.getIsActive() == Boolean.TRUE && user.isDeleted() == Boolean.FALSE) {
			for (int i = 0; i < wishLists.length; i++) {
				int productId = Integer.parseInt(wishLists[i]);
				product = productService.getProduct(productId);
				if (product != null) {
					productList.add(product);
				}
			}
		}
		// List<Product> productList = productService.getProductForWishList(userId);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);

	}

	@GetMapping("getbylocation/{latitude}/{longitude}")
	public ResponseEntity<?> getAllProductWithLocation(@PathVariable("latitude") double latitude,
			@PathVariable("longitude") double longitude) {
		Map<String, String> response = new HashMap<String, String>();
		List<UserAddress> userAddressList = userAddressService.getAllUserAddress();
		double response1 = 0;

		List<Distance> responseList = new ArrayList<Distance>();
		List<Product> productList = new ArrayList<Product>();
		Distance tempList = new Distance();

		for (int i = 0; i < userAddressList.size(); i++) {
			double destLat = Double.parseDouble(userAddressList.get(i).getLatitude());
			double destLong = Double.parseDouble(userAddressList.get(i).getLongitude());
			if (destLat > 1 && destLong > 1) {

				DistanceController distanceController = new DistanceController();
				response1 = distanceController.Distance(latitude, longitude, destLat, destLong);

				if (response1 > 0) {

					Distance temp = new Distance();

					temp.setText(String.valueOf(response1));
					temp.setValue(userAddressList.get(i).getShopId());
					responseList.add(temp);
					response.put(String.valueOf(response1), userAddressList.get(i).getShopId());
				}
			}
		}

		if (responseList.size() > 0) {
			for (int i = 0; i < responseList.size(); i++) {
				for (int j = i + 1; j < responseList.size(); j++) {
					double old = Double.parseDouble(responseList.get(i).getText()),
							newData = Double.parseDouble(responseList.get(j).getText());
					if (newData < old) {
						tempList = responseList.get(i);
						responseList.set(i, responseList.get(j));
						responseList.set(j, tempList);
					}
				}

				List<Product> product = productService.getProductByShopId(responseList.get(i).getValue());
				productList.addAll(product);
			}
		}
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping("getbyofferproduct/{latitude}/{longitude}")
	public ResponseEntity<?> getAllOffreProductWithLocation(@PathVariable("latitude") double latitude,
			@PathVariable("longitude") double longitude) {
		Map<String, String> response = new HashMap<String, String>();
		List<UserAddress> userAddressList = userAddressService.getAllUserAddress();
		double response1 = 0;

		List<Distance> responseList = new ArrayList<Distance>();
		List<Product> productList = new ArrayList<Product>();
		Distance tempList = new Distance();

		for (int i = 0; i < userAddressList.size(); i++) {
			double destLat = Double.parseDouble(userAddressList.get(i).getLatitude());
			double destLong = Double.parseDouble(userAddressList.get(i).getLongitude());
			if (destLat > 1 && destLong > 1) {

				DistanceController distanceController = new DistanceController();
				response1 = distanceController.Distance(latitude, longitude, destLat, destLong);

				Distance temp = new Distance();

				temp.setText(String.valueOf(response1));
				temp.setValue(userAddressList.get(i).getShopId());
				responseList.add(temp);
				response.put(String.valueOf(response1), userAddressList.get(i).getShopId());
			}
		}

		if (responseList.size() > 0) {
			for (int i = 0; i < responseList.size(); i++) {
				for (int j = i + 1; j < responseList.size(); j++) {
					double old = Double.parseDouble(responseList.get(i).getText()),
							newData = Double.parseDouble(responseList.get(j).getText());
					if (newData < old) {
						tempList = responseList.get(i);
						responseList.set(i, responseList.get(j));
						responseList.set(j, tempList);
					}
				}
				List<Product> product = productService.getProductOffreByShopId(responseList.get(i).getValue());
				productList.addAll(product);
			}
		}
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		productDao.indexProduct();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Product>> searchAllUserBykeyword(@PathVariable("keyword") String keyword) {
		List<Product> result = new ArrayList<Product>();
		result = productDao.searchProduct(keyword);
		return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
	}

	@GetMapping("search/{keyword}/{latitude}/{longitude}")
	public ResponseEntity<List<Product>> searchAllProductBykeyword(@PathVariable("keyword") String keyword,
			@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
		Map<String, String> response = new HashMap<String, String>();
		List<Product> result = new ArrayList<Product>();
		double response1 = 0;

		result = productDao.searchProduct(keyword);
		List<Distance> responseList = new ArrayList<Distance>();
		List<Product> productList = new ArrayList<Product>();
		Distance tempList = new Distance();
		if (result.size() > 0)
			for (int i = 0; i < result.size(); i++) {
				double destLat = Double.parseDouble(result.get(i).getLatitude());
				double destLong = Double.parseDouble(result.get(i).getLongitude());
				if (destLat > 1 && destLong > 1) {

					DistanceController distanceController = new DistanceController();
					response1 = distanceController.Distance(latitude, longitude, destLat, destLong);

					Distance temp = new Distance();

					temp.setText(String.valueOf(response1));
					temp.setValue(String.valueOf(result.get(i).getProductId()));
					responseList.add(temp);
					response.put(String.valueOf(response1), String.valueOf(result.get(i).getProductId()));
				}
			}
		if (responseList.size() > 0) {
			for (int i = 0; i < responseList.size(); i++) {
				for (int j = 0; j < responseList.size(); j++) {
					double old = Double.parseDouble(responseList.get(i).getText());
					double newDate = Double.parseDouble(responseList.get(j).getText());
					if (newDate < old) {
						tempList = responseList.get(i);
						responseList.set(i, responseList.get(j));
						responseList.set(j, tempList);
					}
				}
				Product product = productService.getProduct(Integer.parseInt(responseList.get(i).getValue()));
				productList.add(product);
			}
		}

		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@GetMapping("getproductbybrandname/{shopId}/{brandName}")
	public ResponseEntity<List<Product>> getProductByBrand(@PathVariable("shopId") String shopId,
			@PathVariable("brandName") String brand) {
		int lookUpId = lookup.findLookUpIdByName(shopId, brand).getLookUpId();
		List<Product> productList = productService.getProductByBrandName(lookUpId);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

//	@GetMapping("search/shopid/{shopId}/{keyword}")
//	public ResponseEntity<List<Product>> searchAllProductBykeywordAndShopId(@PathVariable("shopId") String shopId, @PathVariable("keyword") String keyword) {
//		List<Product> result = new ArrayList<Product>();
//		result = productDao.searchProductByShopIdAndKeyword(shopId, keyword);
//		return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
//	}

	@GetMapping("search/offer/{keyword}")
	public ResponseEntity<List<Product>> searchAllProductOffer(@PathVariable("keyword") String keyword) {
		List<Product> result = new ArrayList<Product>();
		result = productDao.searchProductOfferKeyword(keyword);
		return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
	}

	@GetMapping("search/offer/shopId/{shopId}/{keyword}")
	public ResponseEntity<List<Product>> searchOfferProductByshopId(@PathVariable("shopId") String shopId,
			@PathVariable("keyword") String keyword) {
		List<Product> result = new ArrayList<Product>();
		result = productDao.searchProductOfferByShopId(shopId, keyword);
		return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
	}

	@GetMapping("search/shopId/{shopId}/{keyword}")
	public ResponseEntity<List<Product>> searchProductByshopId(@PathVariable("shopId") String shopId,
			@PathVariable("keyword") String keyword) {
		List<Product> result = new ArrayList<Product>();
		result = productDao.searchProductByShopIdAndKeyword(shopId, keyword);
		return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
	}

//	@PostMapping(value = "/upload/avatar/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	// If not @RestController, uncomment this
//	// @ResponseBody
//	public ResponseEntity<?> uploadavatarFile(@RequestParam("file") MultipartFile file,
//			@PathVariable("userId") String userId) {
//
//		// int moid =
//		// userService.getUser(Integer.parseInt(userId)).getManagementObject();
//		// ManagedObject mo = (ManagedObject)
//		// ManagedObjectService.getManagedObject(moid);
////		logger.debug("Single file upload with userId! : " + userId);
//		System.out.println("Single SYS file upload with userId! : " + userId);
//		if (file.isEmpty()) {
//			return new ResponseEntity<Object>("please select a file!", HttpStatus.OK);
//		}
//
//		try {
//
//			fileService.saveUploadedFiles(Arrays.asList(file), userId, "avatar");
//
//		} catch (IOException e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<Object>("Successfully uploaded - " + file.getOriginalFilename(), new HttpHeaders(),
//				HttpStatus.OK);
//
//	}

	@DeleteMapping("delete/{productId}")
	ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("productId") int productId) {
		Map<String, String> response = new HashMap<String, String>();
		Product product = productService.getProduct(productId);
		if (null != product) {
			boolean result = productService.deleteProduct(productId);
			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "Product deleted with given product Id :-" + productId);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product does not exist with given product Id");
		}
		return ResponseEntity.ok().body(response);
	}

}
