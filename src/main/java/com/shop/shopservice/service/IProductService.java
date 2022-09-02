package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Product;

public interface IProductService {

	List<Product> getAllProduct();
	
	List<Product> getAllOfferProduct();

	List<Product> getAllProductForUser();

	public Product getProduct(int productId);
	
	public Product getStock(int productd);
	
	public boolean checkProduct(int productId);

	public List<Product> getProductByName(String name);

	public Product getProductByCategory(int category);

	public List<Product> getProductByShopId(String shopId);

	public List<Product> getProductByProductIdAndShopId(int productId, String shopId);

	public List<Product> getCurrentProduct(String shopId, String name, int category, int brand);

	public List<Product> getProductForUserByShopId(String shopId);

	public Product getProductForOfferByProductId(int productId);

	public List<Product> getProductByBrandName(int brand);

	public List<Product> getProductByBrand(int brand);

	public List<Product> getProductForWishList(int userId);

	public List<Product> getProductOffreByShopId(String shopId);

	public List<Product> getProductByCartId(int cartId);
	

	public List<Product> getProductByShopIdForCategory(String shopId, int category);

	public List<Product> getProductByShopIdAndBrand(String shopId, int brand);

	public boolean updateProduct(Product product);

	public boolean productExists(String shopId, String name, int category, int brand);

	public boolean createProduct(Product product);

	public boolean deleteProduct(int productId);
	

	public List<Product> getProductListByCategory(int category);
	
	
}
