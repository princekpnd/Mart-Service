package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.ProductList;

public interface IProductListDAO {

	List<ProductList> getAllProductList();

	ProductList getProductListById(int id);

	List<ProductList> getProductListByUserId(String userId);

	List<ProductList> getProductListByShopId(String shopId);

	ProductList getProductList(int id);

	boolean getProductByProductId(String productId, int cartId);

	ProductList getProductByProductIdAndCartId(String productId, int cartId);

	List<ProductList> getProductListCartId(int cartId);
	
	List<ProductList> getByshopIdCartId(String shopId, int cartId);
	
	ProductList getReturnInforamation(String productId,int cartId,float returnQuantity);

	void updateProductList(ProductList productList);

	boolean productListExists(String shopId);

	void addProductList(ProductList productList);

	boolean deleteProductList(int productListId);
}
