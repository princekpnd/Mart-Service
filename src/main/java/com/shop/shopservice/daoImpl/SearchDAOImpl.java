package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ISearchDAO;
import com.shop.shopservice.entity.Search;

@Repository
@Transactional
public class SearchDAOImpl implements ISearchDAO  {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Search> getAll() {
		List<Search> searchList = entityManager.createNamedQuery("Search.getAll", Search.class).getResultList();
		return searchList;
	}

	@Override
	public Search getById(int id) {
		Search search = entityManager.createNamedQuery("Search.findById", Search.class).setParameter("id", id)
				.getSingleResult();
		return search;
	}

	@Override
	public void createSearch(Search search) {
		entityManager.persist(search);
	}

	@Override
	public boolean truncateTable() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE search_keyword");
		
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

}
