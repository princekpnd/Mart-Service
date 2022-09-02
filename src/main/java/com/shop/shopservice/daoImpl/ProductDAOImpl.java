package com.shop.shopservice.daoImpl;

import java.util.ArrayList;
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
import com.shop.shopservice.Idao.IProductDAO;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IImageService;

@Repository
@Transactional
public class ProductDAOImpl implements IProductDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IImageService imageService;

	@Autowired
	private IAdminService adminService;

	@Override
	public List<Product> getAllProduct() {
		List<Product> productList = entityManager.createNamedQuery("Product.findAll", Product.class).getResultList();
		return productList;
	}

	@Override
	public List<Product> getAllProductForUser() {
		List<Product> productList = entityManager.createNamedQuery("Product.findAllForUser", Product.class)
				.getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByCartId(int cartId) {
		List<Product> productList = entityManager.createNamedQuery("Product.findProductByCartId", Product.class)
				.setParameter("cartId", cartId).getResultList();
		return productList;
	}

	public void indexProduct() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<Product> searchProduct(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Product.class).get();

		QueryBuilder queryBuilder1 = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Category.class).get();

		QueryBuilder queryBuilder2 = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Brand.class)
				.get();

		QueryBuilder queryBuilder3 = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(LookUp.class).get();

		QueryBuilder queryBuilder4 = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(UserAddress.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name").matching(keyword).createQuery();

		org.apache.lucene.search.Query query1 = queryBuilder1.keyword().onFields("name").matching(keyword)
				.createQuery();

		org.apache.lucene.search.Query query2 = queryBuilder2.keyword().onFields("name").matching(keyword)
				.createQuery();

		org.apache.lucene.search.Query query3 = queryBuilder3.keyword().onFields("lookUpName").matching(keyword)
				.createQuery();

		org.apache.lucene.search.Query query4 = queryBuilder4.keyword().onFields("city").matching(keyword)
				.createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
				Product.class);

		org.hibernate.search.jpa.FullTextQuery jpaQuery1 = fullTextEntityManager.createFullTextQuery(query1,
				Category.class);

		org.hibernate.search.jpa.FullTextQuery jpaQuery2 = fullTextEntityManager.createFullTextQuery(query2,
				Brand.class);

		org.hibernate.search.jpa.FullTextQuery jpaQuery3 = fullTextEntityManager.createFullTextQuery(query3,
				LookUp.class);

		org.hibernate.search.jpa.FullTextQuery jpaQuery4 = fullTextEntityManager.createFullTextQuery(query4,
				UserAddress.class);

		@SuppressWarnings("unchecked")
		List<UserAddress> results4 = (List<UserAddress>) jpaQuery4.getResultList();

		@SuppressWarnings("unchecked")
		List<LookUp> results3 = (List<LookUp>) jpaQuery3.getResultList();

		@SuppressWarnings("unchecked")
		List<Brand> results2 = (List<Brand>) jpaQuery2.getResultList();

		@SuppressWarnings("unchecked")
		List<Category> results1 = (List<Category>) jpaQuery1.getResultList();

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Product> results = (List<Product>) jpaQuery.getResultList();

		if (results.size() > 0) {
			if (results1.size() > 0 || results2.size() > 0 || results3.size() > 0 || results4.size() > 0) {
				if (results1.size() > 0) {
					for (int i = 0; i < results1.size(); i++) {
						List<Product> tempProduct = getProductListByCategory(results1.get(i).getId());
						results.addAll(tempProduct);
					}
				}

				if (results2.size() > 0) {
					for (int i = 0; i < results2.size(); i++) {
						List<Product> tempProduct = getProductByBrand(results2.get(i).getId());
						results.addAll(tempProduct);
					}
				}

				if (results3.size() > 0) {
					int shopType = results3.get(0).getLookUpId();
					List<Admin> shop = adminService.getByShopType(shopType);
					for (int i = 0; i < shop.size(); i++) {
						List<Product> tempProduct = getProductByShopId(shop.get(i).getShopId());
						results.addAll(tempProduct);
					}
				}

				if (results4.size() > 0) {
					for (int i = 0; i < results4.size(); i++) {
						List<Product> tempProduct = getProductByShopId(results4.get(0).getShopId());
						results.addAll(tempProduct);
					}
				}

				for (int i = 0; i < results.size(); i++) {
					for (int j = i + 1; j < results.size(); j++) {
						if (results.get(i).getProductId() == results.get(j).getProductId()) {
							results.remove(j);
						}
					}
				}
			}
			
			List<Product> product = new ArrayList<Product>();

		} else if (results1.size() > 0 || results2.size() > 0 || results3.size() > 0 || results4.size() > 0) {
			if (results1.size() > 0) {
				results = getProductListByCategory(results1.get(0).getId());
				results.clear();
			} else if (results2.size() > 0) {
				results = getProductByBrand(results2.get(0).getId());
				results.clear();
			} else if (results3.size() > 0) {
				int shopType = results3.get(0).getLookUpId();
				List<Admin> shop = adminService.getByShopType(shopType);
				String shopId = shop.get(0).getShopId();
				results = getProductByShopId(shopId);
				results.clear();
			} else if (results4.size() > 0) {
				String shopId = results4.get(0).getShopId();
				results = getProductByShopId(shopId);
				results.clear();
			}

			if (results1.size() > 0) {
				for (int i = 0; i < results1.size(); i++) {
					List<Product> tempProduct = getProductListByCategory(results1.get(i).getId());
					results.addAll(tempProduct);
				}
			}

			if (results2.size() > 0) {
				for (int i = 0; i < results2.size(); i++) {
					List<Product> tempProduct = getProductByBrand(results2.get(i).getId());
					results.addAll(tempProduct);
				}
			}

			if (results3.size() > 0) {
				int shopType = results3.get(0).getLookUpId();
				List<Admin> shop = adminService.getByShopType(shopType);
				for (int i = 0; i < shop.size(); i++) {
					List<Product> tempProduct = getProductByShopId(shop.get(i).getShopId());
					results.addAll(tempProduct);
				}
			}
			if (results4.size() > 0) {
				for (int i = 0; i < results4.size(); i++) {
					List<Product> tempProduct = getProductByShopId(results4.get(0).getShopId());
					results.addAll(tempProduct);
				}
			}

			for (int i = 0; i < results.size(); i++) {
				for (int j = i + 1; j < results.size(); j++) {
					if (results.get(i).getProductId() == results.get(j).getProductId()) {
						results.remove(j);
					}
				}
			}
		}
		return results;
	}

	@Override
	public Product getProductById(int productId) {
		return this.entityManager.find(Product.class, productId);
	}
	
	@Override
	public boolean checkProductById(int productId) {
		Product product = null;
		product = entityManager.createNamedQuery("Product.checkProductById", Product.class).setParameter("productId", productId).getResultList().stream().findFirst().orElse(null);
		return null != product ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Product getProductForOfferByProductId(int productId) {
		Product product = entityManager.createNamedQuery("Product.findProductByProductId", Product.class)
				.setParameter("productId", productId).getSingleResult();
		return product;
	}

	@Override
	public List<Product> getProductByBrand(int brand) {
		List<Product> productList = entityManager.createNamedQuery("Product.findProductByBrandName", Product.class)
				.setParameter("brand", brand).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByName(String name) {
		List<Product> productList = entityManager.createNamedQuery("Product.findByName", Product.class)
				.setParameter("name", name).getResultList();
		return productList;
	}

	@Override
	public Product getProductByCategory(int category) {
		Product product = entityManager.createNamedQuery("Product.findByCategory", Product.class)
				.setParameter("category", category).getSingleResult();
		return product;
	}

	@Override
	public List<Product> getProductByShopIdForCategory(String shopId, int category) {
		List<Product> productList = entityManager
				.createNamedQuery("Product.findProductByShopIdForCategory", Product.class)
				.setParameter("shopId", shopId).setParameter("category", category).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByShopId(String shopId) {
		List<Product> productList = entityManager.createNamedQuery("Product.findByShopId", Product.class)
				.setParameter("shopId", shopId).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductForUserByShopId(String shopId) {
		List<Product> productList = entityManager.createNamedQuery("Product.findProductByShopId", Product.class)
				.setParameter("shopId", shopId).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByBrandName(int brand) {
		List<Product> product = entityManager.createNamedQuery("Product.findProductByBrand", Product.class)
				.setParameter("brand", brand).getResultList();
		return product;
	}

	@Override
	public void updateProduct(Product product) {
		entityManager.merge(product);
	}

	@Override
	public boolean productExists(String shopId, String name, int category, int brand) {
		Product product = entityManager.createNamedQuery("Product.ProductExistByShopId", Product.class)
				.setParameter("shopId", shopId).setParameter("name", name).setParameter("category", category)
				.setParameter("brand", brand).getResultList().stream().findFirst().orElse(null);
		;
		return null != product ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
	}

	@Override
	public List<Product> getProductOfferByShopId(String shopId) {
		List<Product> productList = entityManager.createNamedQuery("Product.findOfferProduct", Product.class)
				.setParameter("shopId", shopId).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByProductIdAndShopId(int productId, String shopId) {
		List<Product> productList = entityManager.createNamedQuery("Product.findProductIdAndShopId", Product.class)
				.setParameter("productId", productId).setParameter("shopId", shopId).getResultList();
		return productList;
	}

	@Override
	public List<Product> getCurrentProduct(String shopId, String name, int category, int brand) {
		List<Product> productList = entityManager.createNamedQuery("Product.findCurrentProduct", Product.class)
				.setParameter("shopId", shopId).setParameter("name", name).setParameter("category", category)
				.setParameter("brand", brand).getResultList();
		return productList;
	}

	@Override
	public List<Product> getWishList(int userId) {
		List<Product> productList = entityManager.createNamedQuery("Product.getWishList", Product.class)
				.setParameter("userId", userId).getResultList();
		return productList;
	}

	@Override
	public List<Product> getProductByShopIdAndBrand(String shopId, int brand) {
		List<Product> productList = entityManager.createNamedQuery("Product.findBuShopIdAndBrand", Product.class)
				.setParameter("shopId", shopId).setParameter("brand", brand).getResultList();
		return productList;
	}

	@Override
	public boolean deleteProduct(int productId) {
		List<Image> imageList = imageService.getImageByProductId(productId);
		if (imageList.size() > 0) {
			for (int i = 0; i < imageList.size(); i++) {
				int imageId = imageList.get(i).getId();
				imageService.deleteImage(imageId);
			}
			Query query = entityManager.createQuery("delete Product where productId = " + productId);
			query.executeUpdate();
			entityManager.flush();
			return true;
		} else {
			Query query = entityManager.createQuery("delete Product where productId = " + productId);
			query.executeUpdate();
			entityManager.flush();
			return true;
		}
	}

	@Override
	public List<Product> getProductListByCategory(int category) {
		List<Product> product = entityManager.createNamedQuery("Product.findByCategory", Product.class)
				.setParameter("category", category).getResultList();
		return product;
	}
	
	@Override
	public List<Product> searchProductByShopIdAndKeyword(String shopId, String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =  org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory() .buildQueryBuilder().forEntity(Product.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query = queryBuilder .keyword().onFields("name").matching(keyword).createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Product> results = (List<Product>)jpaQuery.getResultList();
	    
	    List<Product> products = new ArrayList<Product>();
	    
	    if (results.size() > 0) {
	    	for (int i = 0; i < results.size(); i++) {
	    		if (results.get(i).getShopId().equals(shopId)) {
	    			products.add(results.get(i));
	    		}
	    	}
	    }	    
	    return products;	
	}

	@Override
	public List<Product> searchProductOfferKeyword(String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =  org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory() .buildQueryBuilder().forEntity(Product.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query = queryBuilder .keyword().onFields("name").matching(keyword).createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Product> results = (List<Product>)jpaQuery.getResultList();
	    
    List<Product> products = new ArrayList<Product>();
	    
	    if (results.size() > 0) {
	    	for (int i = 0; i < results.size(); i++) {
	    		if (results.get(i).isOfferActiveInd()) {
	    			products.add(results.get(i));
	    		}
	    	}
	    }	    
	    return products;	
	}

	@Override
	public List<Product> searchProductOfferByShopId(String shopId, String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =  org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory() .buildQueryBuilder().forEntity(Product.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query = queryBuilder .keyword().onFields("name").matching(keyword).createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Product> results = (List<Product>)jpaQuery.getResultList();
	    
    List<Product> products = new ArrayList<Product>();
	    
	    if (results.size() > 0) {
	    	for (int i = 0; i < results.size(); i++) {
	    		if (results.get(i).isOfferActiveInd() && results.get(i).getShopId().equals(shopId)) {
	    			products.add(results.get(i));
	    		}
	    	}
	    }	    
	    return products;
	}

	@Override
	public List<Product> getAllOfferProduct() {
		List<Product> productList = entityManager.createNamedQuery("Product.findAllOfferProduct",Product.class).getResultList();
		return productList;
	}

	@Override
	public Product getStock(int productId) {
		Product product = entityManager.createNamedQuery("Product.getStock",Product.class).setParameter("productId", productId).getSingleResult();
		return product;
	}

}