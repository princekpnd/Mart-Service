package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.SubCategory;

public interface ISubCategoryDAO {
	
	List<SubCategory> getAll();
	
	SubCategory getById(int id);
	
	List<SubCategory> getSubcategoryByCategoryId(int categoryId);
	
	List<SubCategory> getSubcategoryByShopId(String shopId);
	
	SubCategory deActiveById(int id);
	
	List<SubCategory> getAllSubCategoryByShopId(String shopId, boolean isOnline);
	
	List<SubCategory> getAllSubCategoryByShopId(String shopId);
	
	List<SubCategory> getAllOnlineSubcategoryByCategoryId(int categoryId,boolean isOnline);
	
	SubCategory getSubCategoryByCategoryIdAndName(int categoryId, String name);
	
	boolean cheackExit(int categoryId,String name);
	
	boolean deleteSubCategory(int id);
	
	List<SubCategory> getSubcategoryByCategoryIdForDelete(int categoryId);
	
	public void AddSubCategory(SubCategory subCategory);
	
	public void upDateSubCategory(SubCategory subCategory);

}
