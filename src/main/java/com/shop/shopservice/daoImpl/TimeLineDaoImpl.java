package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import com.shop.shopservice.Idao.INotificationDAOOLD;
import com.shop.shopservice.Idao.ITimelineDao;
import com.shop.shopservice.Idao.IUsersDAO;
//import com.shop.shopservice.entity.NotificationOLD;
import com.shop.shopservice.entity.TimeLine;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.utils.PropertyBundle;

@Repository
@Transactional
public class TimeLineDaoImpl implements ITimelineDao{

	@PersistenceContext	
	private EntityManager entityManager;
	
//	@Autowired
//	INotificationDAOOLD notificationDao;
	@Autowired
	IUsersDAO userDao;
	
	@Override
	public List<TimeLine> getAllTimeLine() {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findAll", TimeLine.class).getResultList();
	}

	@Override
	public List<TimeLine> getAllTimeLineByuser(int userId) {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findByUser", TimeLine.class).setParameter("userId", userId).getResultList();
	}

	@Override
	public List<TimeLine> getAllTimeLineByConsultant(int consultantId) {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findByConsultant", TimeLine.class).setParameter("consultantId", consultantId).getResultList();
	}

	@Override
	public List<TimeLine> getAllTimeLineByUserConsultant(int userId, int consultantId, boolean isAnswered) {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findByUser", TimeLine.class)
				.setParameter("userId", userId).setParameter("consultantId", userId)
				.setParameter("isAnswered", isAnswered).getResultList();
	}

	@Override
	public void createTimeline(TimeLine timeline) {
//		entityManager.persist(timeline);
//		NotificationOLD notification = new NotificationOLD();
//		notification.setUserId(timeline.getConsultantId());
//		notification.setDisplayName(timeline.getqArticle().length() > 50 ? timeline.getqArticle().substring(0, 50)
//				: timeline.getqArticle());
//		notification.setSummeryDetails(timeline.getqArticle());
//		notification.setTimelineId(Long.valueOf(timeline.getId()).intValue());
//		notification.setCatagory(timeline.getCatagoryId());
//		notification.setStatus(PropertyBundle.systemMap.get("NOTIFICATION_STATUS").get(0).getLookUpId());
//		notificationDao.createNotification(notification);
//		timeline = null;
//		notification = null;
	}

	@Override
	public List<TimeLine> findTimeline(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void likeTimeline(TimeLine timeline, int userId) {
		if (null != timeline) {
			String likeUserId = timeline.getLikeUserId();
			if (!Strings.isBlank(likeUserId) && !likeUserId.contains(Integer.toString(userId))) {
				likeUserId = likeUserId + "," + Integer.toString(userId);
				timeline.setLikeCount(timeline.getLikeCount() + 1);
			} else if (!Strings.isBlank(likeUserId) && likeUserId.contains(Integer.toString(userId))) {
				
				likeUserId = likeUserId.startsWith(Integer.toString(userId)) && likeUserId.contains(",")
						? likeUserId.replace(Integer.toString(userId) + ",", Strings.EMPTY)
						: likeUserId.replace("," + Integer.toString(userId), Strings.EMPTY);
				if (likeUserId.startsWith(Integer.toString(userId)) && !likeUserId.contains(",") )
					likeUserId=  likeUserId.replace(Integer.toString(userId), Strings.EMPTY);
							
				timeline.setLikeCount(timeline.getLikeCount() - 1);
			} else if (Strings.isBlank(likeUserId)) {
				likeUserId = Integer.toString(userId);
				timeline.setLikeCount(timeline.getLikeCount() == 0 ? 1 : timeline.getLikeCount() + 1);
			}
			timeline.setLikeUserId(likeUserId);
			entityManager.persist(timeline);
			timeline = null;
		}
	}

	@Override
	public void disLikeTimeline(TimeLine timeline, int userId) {
		if (null != timeline) {
			String dislikeUserId = timeline.getDisLikeUserId();
			if(!Strings.isBlank(dislikeUserId) && !dislikeUserId.contains(Integer.toString(userId))) {
				dislikeUserId = dislikeUserId + "," + Integer.toString(userId);
				timeline.setDislikeCount(timeline.getDislikeCount()+1);
			} else if(!Strings.isBlank(dislikeUserId) && dislikeUserId.contains(Integer.toString(userId))) {
				
				dislikeUserId = dislikeUserId.startsWith(Integer.toString(userId)) && dislikeUserId.contains(",")
						? dislikeUserId.replace(Integer.toString(userId) + ",", Strings.EMPTY)
						: dislikeUserId.replace("," + Integer.toString(userId), Strings.EMPTY);
				if (dislikeUserId.startsWith(Integer.toString(userId)) && !dislikeUserId.contains(",") )
					dislikeUserId = dislikeUserId.replace(Integer.toString(userId), Strings.EMPTY);
				
				timeline.setDislikeCount(timeline.getDislikeCount() - 1);
			} else if (Strings.isBlank(dislikeUserId)) {
				dislikeUserId = Integer.toString(userId);
				timeline.setDislikeCount(timeline.getDislikeCount() == 0 ? 1 : timeline.getDislikeCount() + 1);
			}
			timeline.setDisLikeUserId(dislikeUserId);
			entityManager.persist(timeline);
			timeline = null;
		}
	}
	


	@Override
	public List<TimeLine> getAllTimeLineByuserAndCatagory(int userId, int catagoryId) {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findByUserAndCatagory", TimeLine.class).setParameter("userId", userId).setParameter("catagoryId", catagoryId).getResultList();
	}

	@Override
	public List<TimeLine> getAllTimeLineByCatagory(int catagoryId) {
		return (List<TimeLine>) entityManager.createNamedQuery("TimeLine.findByCatagory", TimeLine.class).setParameter("catagoryId", catagoryId).getResultList();
	}

	@Override
	public TimeLine getTimeLineByTwitterId(Long twitterId) {
		String twittersId = Long.toString(twitterId);
		try {
		return (TimeLine) entityManager.createNamedQuery("TimeLine.findByTwitterId", TimeLine.class).setParameter("twitterId", twittersId).getSingleResult();
	} catch (NoResultException nre) {
	return null;	
	}
	}

	@Override
	public TimeLine getTimeLineById(Integer timeLineid) {
		try {
			return (TimeLine) entityManager.find(TimeLine.class, Long.valueOf(timeLineid));
		} catch (NoResultException nre) {
			return null;	
		}
	}

	@Override
	public List<TimeLine> searchTimeLine(String keyword) {
		// get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =
	        org.hibernate.search.jpa.Search.
	        getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = 
	        fullTextEntityManager.getSearchFactory()
	        .buildQueryBuilder().forEntity(TimeLine.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query =
	        queryBuilder
	          .keyword()
	          .onFields("qArticle", "aArticle")
	          .matching(keyword)
	          .createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery =
	        fullTextEntityManager.createFullTextQuery(query, TimeLine.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<TimeLine> results = (List<TimeLine>)jpaQuery.getResultList();
	    
	    return results;
	}
	
	public void indexTime() {
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
	public void updateTimeLine(TimeLine timeLine) {
//	entityManager.merge(timeLine);
//	NotificationOLD notification = new NotificationOLD();
//	notification.setUserId(timeLine.getUserId());
//	notification.setDisplayName(timeLine.getaArticle().length() > 50 ? timeLine.getqArticle().substring(0, 50)
//			: timeLine.getaArticle());
//	notification.setSummeryDetails(timeLine.getqArticle());
//	notification.setTimelineId(Long.valueOf(timeLine.getId()).intValue());
//	notification.setCatagory(timeLine.getCatagoryId());
//	notification.setStatus(PropertyBundle.systemMap.get("NOTIFICATION_STATUS").get(0).getLookUpId());
//	notificationDao.createNotification(notification);
	}
}
