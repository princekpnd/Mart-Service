package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IAddressDAO;
import com.shop.shopservice.entity.Address;
@Repository
@Transactional
public class AddressDAOImpl implements IAddressDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Address> getAllAddress() {
		List<Address> addressList = entityManager.createNamedQuery("Address.findAllAddress", Address.class).getResultList();
		return addressList;
	}

	@Override
	public List<Address> getAddressByShopId(String shopId) {
		List<Address> addressList =entityManager.createNamedQuery("Address.findAddressByShopId",Address.class).setParameter("shopId", shopId).getResultList();
		return addressList;
	}

	@Override
	public boolean addressExists(String userId) {
		Address address = entityManager.createNamedQuery("Address.findByUserId",Address.class).setParameter("userId", userId).getResultList().stream().findFirst().orElse(null);;
		return null != address ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addAddress(Address address) {
		entityManager.persist(address);
		
	}

	@Override
	public Address getAddressById(int id) {
		return this.entityManager.find(Address.class, id);
	}

	@Override
	public void updateAddress(Address address) {
		entityManager.merge(address);
		
	}

	@Override
	public List<Address> getAllAddressByUserId(String userId) {
		List<Address> addressList = entityManager.createNamedQuery("Address.findByUserId",Address.class).setParameter("userId", userId).getResultList();
		return addressList;
	}
	
	
	@Override
	public List<Address> getAllAddressByShopId(String shopId) {
		List<Address> addressList = entityManager.createNamedQuery("Address.findByShopId",Address.class).setParameter("shopId", shopId).getResultList();
		return addressList;
	}

	@Override
	public List<Address> getAddressByUserIdAndShopId(String userId, String shopId) {
		List<Address> addressList = entityManager.createNamedQuery("Address.findByUserIdAndShopId",Address.class).setParameter("userId", userId).setParameter("shopId", shopId).getResultList();
		return addressList;
	}

	@Override
	public Address getDefaultAddress(String userId) {
		 Address address = entityManager.createNamedQuery("Address.findDefaultAddress",Address.class).setParameter("userId", userId).getResultList().stream().findFirst().orElse(null);
		return  address != null ? address : null;
	}

	@Override
	public boolean deleteAddress(int id) {
		Query query = entityManager.createQuery("delete Address where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}
	
	public void indexAddress() {
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
	public List<Address> searchAddress(String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =
	        org.hibernate.search.jpa.Search.
	        getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = 
	        fullTextEntityManager.getSearchFactory()
	        .buildQueryBuilder().forEntity(Address.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query =
	        queryBuilder
	          .keyword()
	          .onFields("city", "city")
	          .matching(keyword)
	          .createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery =
	        fullTextEntityManager.createFullTextQuery(query, Address.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Address> results = (List<Address>)jpaQuery.getResultList();
	    
	    return results;
	}





}
