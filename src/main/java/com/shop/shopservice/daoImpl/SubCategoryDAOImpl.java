package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.ISubCategoryDAO;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;

@Repository
@Transactional
public class SubCategoryDAOImpl implements ISubCategoryDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IItemService itemService;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private IItemListService itemListService;

	@Override
	public List<SubCategory> getAll() {
		List<SubCategory> categorieList = entityManager.createNamedQuery("SubCategory.getAll", SubCategory.class)
				.getResultList();
		return categorieList;
	}

	@Override
	public SubCategory getById(int id) {
		SubCategory subCategory = entityManager.createNamedQuery("SubCategory.getbyId", SubCategory.class)
				.setParameter("id", id).getResultList().stream().findFirst()
				.orElse(null);
		return null != subCategory ?subCategory:subCategory;
	}

	@Override
	public boolean cheackExit(int categoryId, String name) {
		SubCategory subCategory = entityManager.createNamedQuery("SubCategory.CheackExit", SubCategory.class)
				.setParameter("categoryId", categoryId).setParameter("name", name).getResultList().stream().findFirst()
				.orElse(null);
		return null != subCategory ? Boolean.TRUE : Boolean.FALSE;
	}

//	Cashier cashier = entityManager.createNamedQuery("Cashier.chrekCashier",Cashier.class).setParameter("mobileNo", mobileNo).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);
//	return null != cashier ?Boolean.TRUE:Boolean.FALSE;

	@Override
	public void AddSubCategory(SubCategory subCategory) {
		entityManager.persist(subCategory);

	}

	@Override
	public void upDateSubCategory(SubCategory subCategory) {
		entityManager.merge(subCategory);

	}

	@Override
	public List<SubCategory> getSubcategoryByCategoryId(int categoryId) {
		List<SubCategory> subCategorieList = entityManager
				.createNamedQuery("SubCategory.findAllSubCategory", SubCategory.class)
				.setParameter("categoryId", categoryId).getResultList();
		return subCategorieList;
	}

	@Override
	public List<SubCategory> getSubcategoryByShopId(String shopId) {
		List<SubCategory> subCategorieList = entityManager
				.createNamedQuery("SubCategory.findByShopId", SubCategory.class).setParameter("shopId", shopId)
				.getResultList();
		return subCategorieList;
	}

	@Override
	public SubCategory getSubCategoryByCategoryIdAndName(int categoryId, String name) {
		SubCategory subCategory = entityManager.createNamedQuery("SubCategory.getForExcel", SubCategory.class)
				.setParameter("categoryId", categoryId).setParameter("name", name).getSingleResult();
		return subCategory;
	}

	@Override
	public SubCategory deActiveById(int id) {
		SubCategory subCategory = entityManager.createNamedQuery("SubCategory.getdeActiveById", SubCategory.class)
				.setParameter("id", id).getSingleResult();
		return subCategory;
	}

	@Override
	public List<SubCategory> getAllOnlineSubcategoryByCategoryId(int categoryId, boolean isOnline) {
		List<SubCategory> subCategorieList = entityManager
				.createNamedQuery("SubCategory.getAllOnlineSubcategory", SubCategory.class)
				.setParameter("categoryId", categoryId).setParameter("isOnline", isOnline).getResultList();
		return subCategorieList;
	}

	@Override
	public List<SubCategory> getAllSubCategoryByShopId(String shopId, boolean isOnline) {
		List<SubCategory> subCategorieList = entityManager
				.createNamedQuery("SubCategory.getAllOnlineSubcategoryByshopId", SubCategory.class)
				.setParameter("shopId", shopId).setParameter("isOnline", isOnline).getResultList();
		return subCategorieList;
	}

	@Override
	public List<SubCategory> getAllSubCategoryByShopId(String shopId) {
		List<SubCategory> subCategorieList = entityManager
				.createNamedQuery("SubCategory.getAllSubcategoryByshopId", SubCategory.class)
				.setParameter("shopId", shopId).getResultList();
		return subCategorieList;
	}

	@Override
	public boolean deleteSubCategory(int id) {
		List<Brand> brandList = brandService.getAllBrandByCategoryForDelete(String.valueOf(id));
		List<Item> productList = itemService.getAllItemBySubCategory(id);
//		if (productList.size() > 0) {
//			for (int j = 0; j < productList.size(); j++) {
//				int productId = productList.get(j).getId();
//				itemService.deleteProduct(productId);
//				List<ItemList> variantList = itemListService.getByProductIdForDelete(productId);
//				if (variantList.size() > 0) {
//					for (int k = 0; k < variantList.size(); k++) {
//						int variantId = variantList.get(k).getId();
//						itemListService.deleteItemList(variantId);
//					}
//				}
//			}
//
//		}
		if (brandList.size() > 0) {
			return false;
//			for (int i = 0; i < brandList.size(); i++) {
//				int brandId = brandList.get(i).getId();
//				brandService.deleteBrand(brandId);
//			}
//			Query query = entityManager.createQuery("delete SubCategory where id = " + id);
//			query.executeUpdate();
//			entityManager.flush();
		} else {
//			Query query = entityManager.createQuery("delete SubCategory where id = " + id);
//			query.executeUpdate();
//			entityManager.flush();
//		}
			Query query = entityManager.createQuery("delete SubCategory where id = " + id);
			query.executeUpdate();
			
			entityManager.flush();
			return true;
		}
	}

	@Override
	public List<SubCategory> getSubcategoryByCategoryIdForDelete(int categoryId) {
		List<SubCategory> subCategories = entityManager
				.createNamedQuery("SubCategory, findForDelete", SubCategory.class)
				.setParameter("categoryId", categoryId).getResultList();
		return subCategories;
	}
}
