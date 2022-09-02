package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Brand;

public interface IBrandDAO {

	List<Brand> getAllBrand();

	List<Brand> getBrandForUser();

	List<Brand> getBrandByShopId(String shopId);

	List<Brand> getBrandForUserByShopId(String shopId);
	
	public Brand getByTitleAndShopId(String title, String shopId, int category, int subCategoryId);

	public List<Brand> getAllDeactiveBrandByShopId(String shopId);

	public List<Brand> getBrandByShopIdAndId(String shopId, int id);

	public List<Brand> getAllBrandByCategory(int category);
	
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive);
	
	public List<Brand> getBrandBySubCategoryId(int subCategoryId);
	
	List<Brand> getAllOnlineBrandBySubCategoryId(int subCategoryId,boolean  isOnline);
	
	List<Brand> getAllOnlineBrand(boolean isOnline);

	List<Brand> getAllOnlineBrandLocal(String shopId);
	
	List<Brand> getAllBrandByCategoryForDelete(String subCategoryId);
	
	List<Brand> getBrandBySubCategoryIdForDelete(int subCategoryId);
	
	List<Brand> getAllBrandBySubCategoryId(int subCategoryId);
	
	List<Brand> getBrandByCategoryForDelete(int category);
	
	List<Brand> getByShopIdToAll(String shopId);
	boolean brandExists(String title);

	void addBrand(Brand brand);
	
	void addSubcategoryInBrand(Brand brand, String subCategoryId);

	Brand getBrandByCategory(int category);

	void updateBrand(Brand brand);

	Brand getBrandById(int id);
	
	Brand getDeActiveForExcel(int id);

	boolean deleteBrandById(int id);

	public void indexBrand();

	public List<Brand> searchBrand(String keyword);

}
