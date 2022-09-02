package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.ProductList;

public interface IProductListService {
	List<ProductList> getAllProductList();

	public ProductList getProductListById(int id);

	public List<ProductList> getProductListByUserId(String userId);

	public List<ProductList> getProductListByShopId(String shopId);

	public ProductList getProductList(int id);

	public boolean getProductByProductId(String productId, int cartId);
	
	ProductList getReturnInforamation(String productId,int cartId,float returnQuantity);

	ProductList getProductByProductIdAndCartId(String productId, int cartId);

	List<ProductList> getProductListCartId(int cartId);

	List<ProductList> getByshopIdCartId(String shopId, int cartId);

	public boolean updateProductList(ProductList productList);

	public boolean productListExists(String shopId);

	public boolean createProductList(ProductList productList);

	public boolean deleteProductList(int productListId);

}
