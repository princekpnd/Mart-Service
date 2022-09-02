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
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IItemDAO;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.entity.ItemList;
import com.shop.shopservice.service.IItemListService;

@Repository
@Transactional
public class ItemDAOImpl implements IItemDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private IItemListService itemListService;

	

	@Override
	public List<Item> getAll() {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAll", Item.class).getResultList();
		return itemList;
	}

	@Override
	public Item getByName(String name) {
		Item item = entityManager.createNamedQuery("Item.getByName", Item.class).setParameter("name", name)
				.getSingleResult();
		return item;
	}

	@Override
	public List<Item> getAllProductByShopId(String shopId) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllProductByName", Item.class)
				.setParameter("shopId", shopId).getResultList();
		return itemList;
	}

	@Override
	public Item getById(int id) {
		Item item = entityManager.createNamedQuery("Item.getById", Item.class).setParameter("id", id).getResultList().stream().findFirst().orElse(null);
		return item;
	}

	@Override
	public boolean itemExit(String title, String shopId, int brand, int category) {
		Item item = entityManager.createNamedQuery("Item.exitItem", Item.class).setParameter("title", title)
				.setParameter("shopId", shopId).setParameter("brand", brand).setParameter("category", category)
				.getResultList().stream().findFirst().orElse(null);
		return null != item ? Boolean.TRUE : Boolean.FALSE;

	}

	@Override
	public void AddItem(Item item) {
		entityManager.persist(item);
	}

	@Override
	public void updateItem(Item item) {
		entityManager.merge(item);

	}

	@Override
	public List<Item> getAllItem(String shopId) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getByShopId", Item.class)
				.setParameter("shopId", shopId).getResultList();
		return itemList;
	}

	@Override
	public Item getByNameAndShopId(String shopId, String name) {
		Item item = entityManager.createNamedQuery("Item.findByName", Item.class).setParameter("shopId", shopId)
				.setParameter("name", name).getResultList().stream().findFirst().orElse(null);
		return item;
	}

	@Override
	public void indexBrand() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
		
	}

	@Override
	public List<Item> searchItem(String keyword) {
		// get the full text entity manager
				FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
						.getFullTextEntityManager(entityManager);

				// create the query using Hibernate Search query DSL
				QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class)
						.get();

				// a very basic query by keywords
				org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "name").matching(keyword)
						.createQuery();

				// wrap Lucene query in an Hibernate Query object
				org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Item.class);

				// execute search and return results (sorted by relevance as default)
				@SuppressWarnings("unchecked")
				List<Item> results = (List<Item>) jpaQuery.getResultList();

				return results;
	}

	@Override
	public boolean deleteProduct(int id) {
		List<ItemList> itemList = itemListService.getByProductIdForDelete(id);
		if(itemList.size()< 0) {
			return false;
//			for(int i= 0; i <itemList.size(); i++) {
//				int productId = itemList.get(i).getId();
//				itemListService.deleteItemList(productId);
//			}
//			Query query = entityManager.createQuery("delete Item where id = " + id);			
//			query.executeUpdate();
//			entityManager.flush();
			
		}else {
			Query query = entityManager.createQuery("delete Item where id = " + id);			
			query.executeUpdate();
			entityManager.flush();
			return true;
			
		}
		
		
	}

	@Override
	public List<Item> getAllItemByShopId(String shopId) {
	List<Item> itemList = entityManager.createNamedQuery("Item,getAllProductByShopId",Item.class).setParameter("shopId", shopId).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getItemByBrand(int brand) {
		List<Item> itemList = entityManager.createNamedQuery("Item,findByBrand",Item.class).setParameter("brand", brand).getResultList();
		return itemList != null ?itemList :null;
	}

	@Override
	public List<Item> getActiveProduct(String shopId) {
		List<Item> itemList = entityManager.createNamedQuery("Item,findByActiveProduct",Item.class).setParameter("shopId", shopId).getResultList();
		return itemList;
	}

	@Override
	public Item getItemByTitle(String title, String shopId) {
		Item item = entityManager.createNamedQuery("Item.getByTitle",Item.class).setParameter("title", title).setParameter("shopId", shopId).getSingleResult();
		return item;
	}

	@Override
	public Item getItemForExcel(int brand, int category, String shopId, String title) {
		Item item = entityManager.createNamedQuery("Item.findByExcel",Item.class).setParameter("brand", brand).setParameter("category", category).setParameter("shopId", shopId).setParameter("title", title).getSingleResult();
		return item;
	}

	@Override
	public Item getDeActiveForExcel(int id) {
		Item item = entityManager.createNamedQuery("Item.deActiveForExcel",Item.class).setParameter("id", id).getSingleResult();
		return item;
	}

	@Override
	public List<Item> getByCategory(int category) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllProductByCategory", Item.class)
				.setParameter("category", category).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllOnlineItem(boolean isOnline) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllProductByOnline", Item.class)
				.setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllOnlineCategory(int category, boolean isOnline) {
	List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByCategory",Item.class).setParameter("category", category).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllOnlineByBrand(int brand, boolean isOnline) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByBrand", Item.class).setParameter("brand", brand).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllOnlineProductByShopId(String shopId, boolean isOnline) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineProductByShopId", Item.class).setParameter("shopId", shopId).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllItemBySubCategory(int subCategoryId) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineProductBySubCategoryId", Item.class).setParameter("subCategoryId", subCategoryId).getResultList();
		return itemList;	
		}

	@Override
	public List<Item> getAllHotSale(boolean hotSell) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByHotSale", Item.class).setParameter("hotSell", hotSell).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllBaggage(boolean baggage) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByBaggage", Item.class).setParameter("baggage", baggage).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllMilaanOffer(boolean milaanOffer) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByMilaanOffer", Item.class).setParameter("milaanOffer", milaanOffer).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getAllAditionlDiscount(boolean aditionalDiscount) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getAllOnlineByAditionalOffer", Item.class).setParameter("aditionalDiscount", aditionalDiscount).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getProductByShopIdAndId(int id, String shopId) {
	List<Item> itemList = entityManager.createNamedQuery("Item.getByShopIdAndId", Item.class).setParameter("id",id).setParameter("shopId", shopId).getResultList();
		return null != itemList? itemList:null;
	}

	@Override
	public List<Item> getAllItemBySubCategoryAndOnline(int subCategoryId, boolean isOnline) {
		List<Item> itemList = entityManager.createNamedQuery("Item.getBySubCategoryIdAndOnline", Item.class).setParameter("subCategoryId",subCategoryId).setParameter("isOnline", isOnline).getResultList();
		return itemList;
	}

	@Override
	public List<Item> getByCategoryForDelete(int category) {
		List<Item> itemList = entityManager.createNamedQuery("Item.forDelete", Item.class)
				.setParameter("category", category).getResultList();
		return itemList;
	}

}
