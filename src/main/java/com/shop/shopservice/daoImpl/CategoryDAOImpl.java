package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ICategoryDAO;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.entity.SubCategory;
import com.shop.shopservice.service.IBrandService;
import com.shop.shopservice.service.IItemListService;
import com.shop.shopservice.service.IItemService;
import com.shop.shopservice.service.ISubCategoryService;

@Repository
@Transactional
public class CategoryDAOImpl implements ICategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private IItemListService itemListService;

	@Autowired
	private ISubCategoryService subCategoryService;

	@Autowired
	private IItemService itemService;

	@Override
	public List<Category> getAllCategory() {
		List<Category> categoryList = entityManager.createNamedQuery("Category.findAll", Category.class)
				.getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getCategoryForUser() {
		List<Category> categoryList = entityManager.createNamedQuery("Category.findCategoryForUser", Category.class)
				.getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getCategoryForUserByShopId(String shopId) {
		List<Category> categoryList = entityManager
				.createNamedQuery("Category.findCategoryForUserByShopId", Category.class).setParameter("shopId", shopId)
				.getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getAllDeactiveCategoryByShopId(String shopId) {
		List<Category> categoryList = entityManager.createNamedQuery("Category.findDeactiveCategory", Category.class)
				.setParameter("shopId", shopId).getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getCategoryByShopIdAndId(String shopId, int id) {
		List<Category> categoryList = entityManager
				.createNamedQuery("Category.findCategoryByShopIdAndId", Category.class).setParameter("shopId", shopId)
				.setParameter("id", id).getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getCategoryByShopId(String shopId) {
		List<Category> categoryList = entityManager.createNamedQuery("Category.findCategoryByShopId", Category.class)
				.setParameter("shopId", shopId).getResultList();
		return categoryList;
	}

	@Override
	public boolean categoryExists(String name, String shopId) {
		Category category = entityManager.createNamedQuery("Category.findCategoryByName", Category.class)
				.setParameter("title", name).setParameter("shopId", shopId).getResultList().stream().findFirst()
				.orElse(null);
		;
		return null != category ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addCategory(Category category) {
		entityManager.persist(category);

	}

	@Override
	public Category getCategoryById(int id) {
		return this.entityManager.find(Category.class, id);

	}

	@Override
	public void updateCategory(Category category) {
		entityManager.merge(category);

	}

	@Override
	public boolean deleteCategory(int id) {

		List<Brand> brandList = brandService.getBrandByCategoryForDelete(id);
		List<SubCategory> subCategoryList = subCategoryService.getSubcategoryByCategoryIdForDelete(id);
		List<Item> productList = itemService.getByCategoryForDelete(id);

		if (subCategoryList.size() > 0) {
			return false;
		} else {
//			for (int i = 0; i < subCategoryList.size(); i++) {
//				int subCategoryId = subCategoryList.get(i).getId();
//				subCategoryService.deleteSubCategory(subCategoryId);
//			}

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
//		} 
//		if (brandList.size() > 0) {
//			for (int i = 0; i < brandList.size(); i++) {
//				int brandId = brandList.get(i).getId();
//				brandService.deleteBrand(brandId);
//			}
//
//			Query query = entityManager.createQuery("delete Category where id = " + id);
//			query.executeUpdate();
//			entityManager.flush();
//		} else {
//			Query query = entityManager.createQuery("delete Category where id = " + id);
//			query.executeUpdate();
//			entityManager.flush();
//		}
			Query query = entityManager.createQuery("delete Category where id = " + id);
			query.executeUpdate();
			entityManager.flush();
			return true;
		}
	}

	public void indexCategory() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<Category> searchCategory(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Category.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "name").matching(keyword)
				.createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
				Category.class);

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Category> results = (List<Category>) jpaQuery.getResultList();

		return results;
	}

	@Override
	public Category getCategoryTitleByShopId(String title, String shopId) {
		Category category = entityManager.createNamedQuery("Category.findCategoryByTitleAndShopId", Category.class)
				.setParameter("title", title).setParameter("shopId", shopId).getResultList().stream().findFirst()
				.orElse(null);
		;
		return category;
	}

	@Override
	public Category getDeActiveForExcel(int id) {
		Category category = entityManager.createNamedQuery("Category.deActiveForExcel", Category.class)
				.setParameter("id", id).getSingleResult();
		return category;
	}

	@Override
	public List<Category> getAllOnlineCategoryById(int id, boolean isOnline) {
		List<Category> categoryList = entityManager.createNamedQuery("Category.findAllOnlineCategory", Category.class)
				.setParameter("id", id).setParameter("isOnline", isOnline).getResultList();
		return categoryList;
	}

	@Override
	public List<Category> getAllOnlineCategory(boolean isOnline) {
		List<Category> categoryList = entityManager.createNamedQuery("Category.getAllOnlineCategory", Category.class)
				.setParameter("isOnline", isOnline).getResultList();
		return categoryList;
	}

	@Override
	public Category updateOnlineByCategoryId(int id) {
		Category category = entityManager.createNamedQuery("Category.getAllOnlineId", Category.class)
				.setParameter("id", id).getSingleResult();
		return category;
	}

	@Override
	public List<Category> getAllCategoryForLocal(String shopId) {
		List<Category> categoryList = entityManager
				.createNamedQuery("Category.getAllOnlineCategoryForLocal", Category.class)
				.setParameter("shopId", shopId).getResultList();
		return categoryList;
	}

}
