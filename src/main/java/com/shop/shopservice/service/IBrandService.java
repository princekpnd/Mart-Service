package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Brand;

public interface IBrandService {

	List<Brand> getAllBrand();

	List<Brand> getBrandForUser();

	public List<Brand> getBrandByShopId(String shopId);

	public List<Brand> getBrandForUserByShopId(String shopId);
	
	Brand getByTitleAndShopId(String title, String shopId, int category, int subCategoryId);

	public List<Brand> getAllDeactiveBrandByShopId(String shopId);
	
	public List<Brand> getAllOnlineBrandBySubCategoryId(int subCategoryId, boolean isOnline);
	
	public List<Brand> getAllOnlineBrand(boolean isOnline);
	
	public List<Brand> getAllOnlineBrandLocal(String shopId);


	public List<Brand> getAllBrandByCategory(int category);
	
	public List<Brand> getByShopIdToAll(String shopId);
	
	public List<Brand> getAllBrandByCategoryForDelete(String subCategoryId);
	
	public List<Brand> getBrandByCategoryForDelete(int category);
	
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive);
	
	public List<Brand> getBrandBySubCategoryId(int subCategoryId);
	
	public List<Brand> getAllBrandBySubCategoryId(int subCategoryId);
	
	public List<Brand> getBrandBySubCategoryIdForDelete(int subCategoryId);

	public List<Brand> getBrandByShopIdAndId(String shopId, int id);

	public boolean brandExists(String title);

	public boolean createBrand(Brand brand);
	
	public boolean addSubcategoryInBrand(Brand brand,String  subCategoryId);

	public Brand getBrand(int category);

	public boolean updateBrand(Brand brand);

	public Brand getBrandById(int id);
	
	public Brand getDeActiveForExcel(int id);

	public boolean deleteBrand(int id);

}
