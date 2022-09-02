package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.SubCategory;

public interface ISubCategoryService {
	
	List<SubCategory> getAll();
	
	public SubCategory getById(int id);
	
	public List<SubCategory> getSubcategoryByCategoryId(int categoryId);
	
	public List<SubCategory> getSubcategoryByCategoryIdForDelete(int categoryId);
	
	public List<SubCategory> getAllSubCategoryByShopId(String shopId, boolean isOnline);
	
	public List<SubCategory> getAllOnlineSubcategoryByCategoryId(int categoryId,boolean isOnline);
	
	public List<SubCategory> getSubcategoryByShopId(String shopId);
	
	public List<SubCategory> getAllSubcategoryByShopId(String shopId);
	
	public SubCategory deActiveById(int id);
	
	public SubCategory getSubCategoryByCategoryIdAndName(int categoryId, String name);
	
	public boolean cheackExit(int categoryId, String name);
	
	public boolean CreateSubCategory(SubCategory subCategory);
	
	public boolean upDateSubCategory(SubCategory subCategory);
	
	public boolean deleteSubCategory(int id);

}
