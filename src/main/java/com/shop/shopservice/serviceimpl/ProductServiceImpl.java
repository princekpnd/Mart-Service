package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IProductDAO;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.service.IProductService;

@Transactional
@Repository


public class ProductServiceImpl implements IProductService{
	
	@Autowired
	IProductDAO productDao;
	

	@Override
	public List<Product> getAllProduct() {
		return productDao.getAllProduct();
	}
	
	@Override
	public List<Product> getAllProductForUser() {
		return productDao.getAllProductForUser();
	}

	@Override
	public Product getProduct(int productId) {
		return productDao.getProductById(productId);
	}
	
	@Override
	public boolean checkProduct(int productId) {
		return productDao.checkProductById(productId);
	}
	
	@Override
	public Product getProductForOfferByProductId(int productId) {
		return  productDao.getProductForOfferByProductId(productId);
	}
	
	@Override
	public List<Product> getProductByCartId(int cartId) {
		return productDao.getProductByCartId(cartId);
	}
	
	@Override
	public List<Product> getProductByName(String name) {
		return productDao.getProductByName(name);
	}
	@Override
	public Product getProductByCategory(int category) {
		return productDao.getProductByCategory(category);
	}
	@Override
	public List<Product> getProductByBrand(int brand) {
		return productDao.getProductByBrand(brand) ;
	}
	
	@Override
	public List<Product> getProductByShopId(String shopId) {
		return productDao.getProductByShopId(shopId);
	}
	@Override
	public List<Product> getProductForUserByShopId(String shopId) {
		return productDao.getProductForUserByShopId(shopId);
	}

	@Override
	public List<Product> getProductByBrandName(int brand) {
		return productDao.getProductByBrandName(brand);
	}
	
	@Override
	public boolean updateProduct(Product product) {
		productDao.updateProduct(product);
		return true;
	}
	
	@Override
	public boolean productExists(String shopId ,String name,int category,int brand) {
		return productDao.productExists(shopId,name, category,brand);
	}
	
	public boolean createProduct(Product product) {

		if (productExists(product.getShopId(),product.getName(),product.getCategory(),product.getBrand())) {
			return false;
		} else {
			productDao.addProduct(product);			
			return true;
		}
	}

	@Override
	public List<Product> getProductByShopIdForCategory(String shopId, int category) {
		return productDao.getProductByShopIdForCategory(shopId,category) ;
	}

	@Override
	public List<Product> getProductOffreByShopId(String shopId) {
		return productDao.getProductOfferByShopId(shopId);
	}

	@Override
	public List<Product> getProductByProductIdAndShopId(int productId, String shopId) {
		return productDao.getProductByProductIdAndShopId(productId, shopId);
	}

	@Override
	public List<Product> getCurrentProduct(String shopId, String name, int category, int brand) {
		return productDao.getCurrentProduct(shopId, name, category, brand);
	}

	@Override
	public List<Product> getProductForWishList(int userId) {
		return productDao.getWishList(userId);
	}

	@Override
	public List<Product> getProductByShopIdAndBrand(String shopId, int brand) {
		return  productDao.getProductByShopIdAndBrand(shopId, brand);
	}

	@Override
	public boolean deleteProduct(int productId) {		
		return productDao.deleteProduct(productId);
	}

	@Override
	public List<Product> getProductListByCategory(int category) {
		return  productDao.getProductListByCategory(category);
	}

	@Override
	public List<Product> getAllOfferProduct() {
		return productDao.getAllOfferProduct();
	}

	@Override
	public Product getStock(int productId) {
	return productDao.getStock(productId);
	}


	
	

	
}
