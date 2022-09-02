package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ISubCategoryDAO;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.ISubCategoryService;

@Transactional
@Repository
public class SubCategoryServiceImpl implements ISubCategoryService{
	@Autowired
	ISubCategoryDAO  subCategoryDAO;

	@Override
	public List<SubCategory> getAll() {
	return subCategoryDAO.getAll();
	}

	@Override
	public SubCategory getById(int id) {
		return subCategoryDAO.getById(id);
	}

	@Override
	public boolean cheackExit(int categoryId, String name) {
		return subCategoryDAO.cheackExit(categoryId, name);
	}

	@Override
	public boolean CreateSubCategory(SubCategory subCategory) {
		if(cheackExit(subCategory.getCategoryId(), subCategory.getName())) {
			return false;
		}else {
			subCategoryDAO.AddSubCategory(subCategory);
			return true;
		}
		
	}

	@Override
	public boolean upDateSubCategory(SubCategory subCategory) {
		subCategoryDAO.upDateSubCategory(subCategory);
		return  true;
	}

	@Override
	public List<SubCategory> getSubcategoryByCategoryId(int categoryId) {
		return subCategoryDAO.getSubcategoryByCategoryId(categoryId);
	}

	@Override
	public List<SubCategory> getSubcategoryByShopId(String shopId) {
		return subCategoryDAO.getSubcategoryByShopId(shopId);
	}

	@Override
	public SubCategory getSubCategoryByCategoryIdAndName(int categoryId, String name) {
		return subCategoryDAO.getSubCategoryByCategoryIdAndName(categoryId, name);
	}

	@Override
	public SubCategory deActiveById(int id) {
		return subCategoryDAO.deActiveById(id);
	}

	@Override
	public List<SubCategory> getAllOnlineSubcategoryByCategoryId(int categoryId, boolean isOnline) {
		return subCategoryDAO.getAllOnlineSubcategoryByCategoryId(categoryId,isOnline);
	}

	@Override
	public List<SubCategory> getAllSubCategoryByShopId(String shopId, boolean isOnline) {
		return subCategoryDAO.getAllSubCategoryByShopId(shopId, isOnline);
	}

	@Override
	public List<SubCategory> getAllSubcategoryByShopId(String shopId) {
	return subCategoryDAO.getAllSubCategoryByShopId(shopId);
	}

	@Override
	public boolean deleteSubCategory(int id) {
		return subCategoryDAO.deleteSubCategory(id);
	}

	@Override
	public List<SubCategory> getSubcategoryByCategoryIdForDelete(int categoryId) {
		return subCategoryDAO.getSubcategoryByCategoryIdForDelete(categoryId);
	}
}
