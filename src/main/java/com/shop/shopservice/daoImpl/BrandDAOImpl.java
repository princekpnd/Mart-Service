package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IBrandDAO;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.IProductService;

@Repository
@Transactional

public class BrandDAOImpl implements IBrandDAO {

	@Autowired
	private IProductService productService;

	@Autowired
	private IItemListService itemListService;

	@PersistenceContext	
	private EntityManager entityManager;

	@Autowired
	private IItemService itemService;

	@Override
	public List<Brand> getAllBrand() {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findAll", Brand.class).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandForUser() {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findAllForUser", Brand.class).getResultList();
		return brandList;
	}

//	@Override
//	public List<Brand> getBrandByShopId(String shopId) {
//		List<Brand> brandList= entityManager.createNamedQuery("Brand.findByShopId",Brand.class).setParameter("shopId", shopId).getResultList();
//		return  brandList;
//	}

	@Override
	public List<Brand> getBrandByShopId(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findByShopId", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllDeactiveBrandByShopId(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findDeactiveBrand", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandForUserByShopId(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findForUserByShopId", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandByShopIdAndId(String shopId, int id) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findBrandByShopIdAndId", Brand.class)
				.setParameter("shopId", shopId).setParameter("id", id).getResultList();
		return brandList;
	}

	@Override
	public boolean brandExists(String title) {
		Brand brand = entityManager.createNamedQuery("Brand.findByNameShopId", Brand.class).setParameter("title", title)
				.getResultList().stream().findFirst().orElse(null);
		
		return null != brand ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addBrand(Brand brand) {
		entityManager.persist(brand);

	}

	@Override
	public Brand getBrandByCategory(int category) {
		return this.entityManager.find(Brand.class, category);
	}

	@Override
	public void updateBrand(Brand brand) {
		entityManager.merge(brand);

	}

	@Override
	public Brand getBrandById(int id) {
		return this.entityManager.find(Brand.class, id);

	}

	@Override
	public boolean deleteBrandById(int id) {

		// List<Product> productList = productService.getProductByBrand(id);
		List<Item> productList = itemService.getItemByBrand(id);
		if (productList.size() > 0) {
			return false;
//			for (int i = 0; i < productList.size(); i++) {
//				int productId = productList.get(i).getId();
//				itemService.deleteProduct(productId);
//				List<ItemList> variantList = itemListService.getByProductIdForDelete(productId);
//				if (variantList.size() > 0) {
//					for (int k = 0; k < variantList.size(); k++) {
//						int variantId = variantList.get(k).getId();
//						itemListService.deleteItemList(variantId);
//					}
//				}
//
//			}
//			Query query = entityManager.createQuery("delete Brand where id = " + id);
//			query.executeUpdate();
//			entityManager.flush();
		} else {
			Query query = entityManager.createQuery("delete Brand where id = " + id);
			query.executeUpdate();
			entityManager.flush();
			return true;
		}

	}

	@Override
	public List<Brand> getAllBrandByCategory(int category) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findByCategory", Brand.class)
				.setParameter("category", category).getResultList();
		return brandList;
	}

	public void indexBrand() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<Brand> searchBrand(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Brand.class)
				.get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "name").matching(keyword)
				.createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Brand.class);

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Brand> results = (List<Brand>) jpaQuery.getResultList();

		return results;
	}

	@Override
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findActiveCategory", Brand.class)
				.setParameter("category", category).setParameter("isActive", isActive).getResultList();
		return brandList;
	}

	@Override
	public Brand getByTitleAndShopId(String title, String shopId, int category, int subCategoryId) {
		Brand brand = entityManager.createNamedQuery("Brand.findByNameShopId", Brand.class).setParameter("title", title)
				.setParameter("shopId", shopId).setParameter("category", category)
				.setParameter("subCategoryId", subCategoryId).getSingleResult();
		return brand;
	}

	@Override
	public Brand getDeActiveForExcel(int id) {
		Brand brand = entityManager.createNamedQuery("Brand.deActiveForExcel", Brand.class).setParameter("id", id)
				.getSingleResult();
		return brand;
	}

	@Override
	public List<Brand> getBrandBySubCategoryId(int subCategoryId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findBySubCategoryId", Brand.class)
				.setParameter("subCategoryId", subCategoryId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllOnlineBrandBySubCategoryId(int subCategoryId, boolean isOnline) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.getOnlineBrandBySubcategoryId", Brand.class)
				.setParameter("subCategoryId", subCategoryId).setParameter("isOnline", isOnline).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllOnlineBrand(boolean isOnline) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.getOnlineBrand", Brand.class)
				.setParameter("isOnline", isOnline).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllOnlineBrandLocal(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.getOnlineBrandForLocal", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllBrandByCategoryForDelete(String subCategoryId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.forDelete", Brand.class)
				.setParameter("subCategoryId", subCategoryId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandBySubCategoryIdForDelete(int subCategoryId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.forDeleteBySubCategory", Brand.class)
				.setParameter("subCategoryId", subCategoryId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllBrandBySubCategoryId(int subCategoryId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findAllBySubCategoryId", Brand.class)
				.setParameter("subCategoryId", subCategoryId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getByShopIdToAll(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand, getAllToShopId", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public void addSubcategoryInBrand(Brand brand, String subCategoryId) {
//		if (null != brand) {
//			String subCategoryList = brand.getSubCategoryId();
//			String[] subCategoryArray = {};
//			SubCategory subCategory = subCategoryService.getById(Integer.parseInt(subCategoryId));
//			String brandList = subCategory.getBrandList();
//			int brandListCount = subCategory.getBrandListCount();
//			int brandId = brand.getId();
//			String[] brandListArray = {};
//			boolean find = false;
//			int index = 0;
//			String wishLisListFinal = null;
//			String  brandListFinal = null;
//			int wishCount = brand.getSubcategoryCount();
//			if (!Strings.isBlank(subCategoryList)) {
//				subCategoryArray = subCategoryList.split(",");
//				for (int i = 0; i < subCategoryArray.length; i++) {
//					if (subCategoryArray[i].equals(subCategoryId)) {
//						index = i;
//						find = Boolean.TRUE;
//					}
//				}
//			}
//			
//			if (!Strings.isBlank(brandList)) {
//				brandListArray = brandList.split(",");
//			}
//			if (find) {
////				subCategoryArray = (String[]) ArrayUtils.remove(subCategoryArray, index);
////				brand.setSubcategoryCount(wishCount - 1);
////				wishLisListFinal = String.join(",", subCategoryArray);
//				wishLisListFinal = brand.getSubCategoryId();
//				
//			} else {
//				subCategoryArray = (String[]) ArrayUtils.add(subCategoryArray, subCategoryId);
//				brand.setSubcategoryCount(wishCount + 1);
//				wishLisListFinal = String.join(",", subCategoryArray);
//				brandListArray = (String[]) ArrayUtils.add(brandListArray, brandId);
//				subCategory.setBrandListCount(brandListCount + 1);
//				brandListFinal = String.join(",", brandListArray);
//			}
//
//			brand.setSubCategoryId(wishLisListFinal);
//			subCategory.setBrandList(brandListFinal);
//			subCategoryService.upDateSubCategory(subCategory);
//			entityManager.persist(brand);
//			brand = null;
//		}

	}

	@Override
	public List<Brand> getBrandByCategoryForDelete(int category) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.forDeleteByCategory", Brand.class)
				.setParameter("category", category).getResultList();
		return brandList;
	}

}
