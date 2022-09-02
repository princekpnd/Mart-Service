package com.shop.shopservice.daoImpl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IUserAddressDAO;
import com.shop.shopservice.entity.UserAddress;
@Repository
@Transactional
public class UserAddressDAOImpl implements IUserAddressDAO{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UserAddress> getAllUserAddress() {
		List<UserAddress> userAddressList= entityManager.createNamedQuery("UserAddress.findAllUserAddress",UserAddress.class).getResultList();
		return userAddressList;
	}

	@Override
	public List<UserAddress> getAddressByUserId(String userId) {
		List<UserAddress> userAddressList=entityManager.createNamedQuery("UserAddress.findByUserId",UserAddress.class).setParameter("userId", userId).getResultList();
		return userAddressList;
	}

	@Override
	public List<UserAddress> getAddressByShopId(String shopId) {
		List<UserAddress> userAddressList =entityManager.createNamedQuery("UserAddress.findByShopId",UserAddress.class).setParameter("shopId", shopId).getResultList();
		return userAddressList;
	}

	@Override
	public boolean userAddressExists(String userId) {
		UserAddress userAddress = entityManager.createNamedQuery("UserAdderss.findAddressByUserId",UserAddress.class).setParameter("userId", userId).getResultList().stream().findFirst().orElse(null);;
		return null != userAddress ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addUserAddress(UserAddress userAddress) {
			entityManager.persist(userAddress);
			
		}

	@Override
	public void updateUserAddress(UserAddress userAddress) {
			entityManager.merge(userAddress);
			
		}

	@Override
	public UserAddress getUserAddress(String userId) {
		UserAddress userAddress = entityManager.createNamedQuery("UserAddress.createAddressByUserId",UserAddress.class).setParameter("userId", userId).getSingleResult();
		return userAddress;
	}
	

	public void indexUserAddress() {
		try {
		      FullTextEntityManager fullTextEntityManager =
		        Search.getFullTextEntityManager(entityManager);
		      fullTextEntityManager.createIndexer().startAndWait();
		    }
		    catch (InterruptedException e) {
		      System.out.println(
		        "An error occurred trying to build the serach index: " +
		         e.toString());
		    }
	}
	

	@Override
	public List<UserAddress> searchUserAddress(String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =
	        org.hibernate.search.jpa.Search.
	        getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = 
	        fullTextEntityManager.getSearchFactory()
	        .buildQueryBuilder().forEntity(UserAddress.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query =
	        queryBuilder
	          .keyword()
	          .onFields("city", "city")
	          .matching(keyword)
	          .createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery =
	        fullTextEntityManager.createFullTextQuery(query, UserAddress.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<UserAddress> results = (List<UserAddress>)jpaQuery.getResultList();
	    
	    return results;
	}
	}
		


