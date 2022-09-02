package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ITopicsDAO;
import com.shop.shopservice.entity.TimeLine;
import com.shop.shopservice.entity.Topics;

/**
 * @author Avinash
 *
 */
@Repository
@Transactional
public class TopicsDaoImpl implements ITopicsDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	public List<Topics> listAllTopics() {
		return (List<Topics>) entityManager.createNamedQuery("Topics.findAll", Topics.class).getResultList();
	}

	public Topics getTopics(int id) {
		return entityManager.find(Topics.class, new Long(id));	
	}

	public void addTopics(Topics topics) {
		entityManager.persist(topics);
	}

	public void updateTopics(Topics topics) {
		entityManager.merge(topics);
	}

	@Override
	public boolean deleteTopics(int id) {
		return false;
	}

	@Override
	public List<Topics> findTopicsByCatagory(int catagoryId) {
	return (List<Topics>) entityManager.createNamedQuery("Topics.findByCatagory", Topics.class).setParameter("catagoryId", catagoryId).getResultList();
	}

	@Override
	public List<Topics> findTopicsByHashTag(int hashtagId) {
		return (List<Topics>) entityManager.createNamedQuery("Topics.findByHashtag", Topics.class).setParameter("hashtagId", hashtagId).getResultList();
	}

	@Override
	public List<Topics> findTopicsByUser(int userId) {
		return (List<Topics>) entityManager.createNamedQuery("Topics.findByUser", Topics.class).setParameter("userId", userId).getResultList();
	}

	@Override
	public List<Topics> findTopicsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topics> findTopicsByUserAndCatagory(int userId, int catagoryId) {
		return (List<Topics>) entityManager.createNamedQuery("Topics.findByUserAndCatagory", Topics.class).setParameter("catagoryId", catagoryId).setParameter("userId", userId).getResultList();
	}


	@Override
	public List<Topics> searchTopics(String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =
	        org.hibernate.search.jpa.Search.
	        getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = 
	        fullTextEntityManager.getSearchFactory()
	        .buildQueryBuilder().forEntity(Topics.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query =
	        queryBuilder
	          .keyword()
	          .onFields("topicsArticle", "keyword")
	          .matching(keyword)
	          .createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery =
	        fullTextEntityManager.createFullTextQuery(query, Topics.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Topics> results = (List<Topics>)jpaQuery.getResultList();
	    
	    return results;
	}
	
	public void indexTopics() {
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
	public void likeTopics(Topics topics, int userId) {
		if (null != topics) {
			String likeUserId = topics.getLikeUserId();
			if (!Strings.isBlank(likeUserId) && !likeUserId.contains(Integer.toString(userId))) {
				likeUserId = likeUserId + "," + Integer.toString(userId);
				topics.setLikeCount(topics.getLikeCount() + 1);
			} else if (!Strings.isBlank(likeUserId) && likeUserId.contains(Integer.toString(userId))) {
				
				likeUserId = likeUserId.startsWith(Integer.toString(userId)) && likeUserId.contains(",")
						? likeUserId.replace(Integer.toString(userId) + ",", Strings.EMPTY)
						: likeUserId.replace("," + Integer.toString(userId), Strings.EMPTY);
				if (likeUserId.startsWith(Integer.toString(userId)) && !likeUserId.contains(",") )
					likeUserId=  likeUserId.replace(Integer.toString(userId), Strings.EMPTY);
							
				topics.setLikeCount(topics.getLikeCount() - 1);
			} else if (Strings.isBlank(likeUserId)) {
				likeUserId = Integer.toString(userId);
				topics.setLikeCount(topics.getLikeCount() == 0 ? 1 : topics.getLikeCount() + 1);
			}
			topics.setLikeUserId(likeUserId);
			entityManager.persist(topics);
			topics = null;
		}
	}

	@Override
	public void favoriteTopics(Topics topics, int userId) {
		if (null != topics) {
			String favoriteUserId = topics.getFavoriteUserId();
			if (!Strings.isBlank(favoriteUserId) && !favoriteUserId.contains(Integer.toString(userId))) {
				favoriteUserId = favoriteUserId + "," + Integer.toString(userId);
				topics.setFavoriteCount(topics.getFavoriteCount() + 1);
			} else if (!Strings.isBlank(favoriteUserId) && favoriteUserId.contains(Integer.toString(userId))) {
				
				favoriteUserId = favoriteUserId.startsWith(Integer.toString(userId)) && favoriteUserId.contains(",")
						? favoriteUserId.replace(Integer.toString(userId) + ",", Strings.EMPTY)
						: favoriteUserId.replace("," + Integer.toString(userId), Strings.EMPTY);
				if (favoriteUserId.startsWith(Integer.toString(userId)) && !favoriteUserId.contains(",") )
					favoriteUserId=  favoriteUserId.replace(Integer.toString(userId), Strings.EMPTY);
							
				topics.setFavoriteCount(topics.getFavoriteCount() - 1);
			} else if (Strings.isBlank(favoriteUserId)) {
				favoriteUserId = Integer.toString(userId);
				topics.setFavoriteCount(topics.getFavoriteCount() == 0 ? 1 : topics.getFavoriteCount() + 1);
			}
			topics.setFavoriteUserId(favoriteUserId);
			entityManager.persist(topics);
			topics = null;
		}
	}
	
	@Override
	public void disLikeTopics(Topics topics, int userId) {
		if (null != topics) {
			String dislikeUserId = topics.getDisLikeUserId();
			if(!Strings.isBlank(dislikeUserId) && !dislikeUserId.contains(Integer.toString(userId))) {
				dislikeUserId = dislikeUserId + "," + Integer.toString(userId);
				topics.setDislikeCount(topics.getDislikeCount()+1);
			} else if(!Strings.isBlank(dislikeUserId) && dislikeUserId.contains(Integer.toString(userId))) {
				
				dislikeUserId = dislikeUserId.startsWith(Integer.toString(userId)) && dislikeUserId.contains(",")
						? dislikeUserId.replace(Integer.toString(userId) + ",", Strings.EMPTY)
						: dislikeUserId.replace("," + Integer.toString(userId), Strings.EMPTY);
				if (dislikeUserId.startsWith(Integer.toString(userId)) && !dislikeUserId.contains(",") )
					dislikeUserId = dislikeUserId.replace(Integer.toString(userId), Strings.EMPTY);
				
				topics.setDislikeCount(topics.getDislikeCount() - 1);
			} else if (Strings.isBlank(dislikeUserId)) {
				dislikeUserId = Integer.toString(userId);
				topics.setDislikeCount(topics.getDislikeCount() == 0 ? 1 : topics.getDislikeCount() + 1);
			}
			topics.setDisLikeUserId(dislikeUserId);
			entityManager.persist(topics);
			topics = null;
		}
	}

	
}