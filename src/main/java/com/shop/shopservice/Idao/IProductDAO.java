package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Product;

public interface IProductDAO {

	List<Product> getAllProduct();
	
	List<Product> getAllOfferProduct();

	List<Product> getAllProductForUser();
	
	Product getStock(int productId);

	Product getProductById(int productId);
	
	boolean checkProductById(int productId);

	List<Product> getProductByName(String name);

	List<Product> getProductOfferByShopId(String shopId);

	List<Product> getProductByProductIdAndShopId(int productId, String shopId);

	List<Product> getProductByShopIdAndBrand(String shopId, int brand);

	List<Product> getCurrentProduct(String shopId, String name, int category, int brand);

	Product getProductByCategory(int category);

	List<Product> getProductByShopId(String shopId);

	List<Product> getWishList(int userId);

	List<Product> getProductForUserByShopId(String shopId);

	public Product getProductForOfferByProductId(int productId);

	public List<Product> getProductByCartId(int cartId);

	List<Product> getProductByShopIdForCategory(String shopId, int category);

	List<Product> getProductByBrandName(int brand);

	public List<Product> getProductByBrand(int brand);

	void updateProduct(Product product);

	boolean productExists(String shopId, String name, int category, int brand);

	void addProduct(Product product);

	boolean deleteProduct(int productId);

	public void indexProduct();

	public List<Product> searchProduct(String keyword);

	List<Product> getProductListByCategory(int category);
	
	public List<Product> searchProductByShopIdAndKeyword(String shopId, String keyword);
	
	public List<Product> searchProductOfferKeyword(String keyword);
	
	public List<Product> searchProductOfferByShopId(String shopId, String keyword);
	
	
}
