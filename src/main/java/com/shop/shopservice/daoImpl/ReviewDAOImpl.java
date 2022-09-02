package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IReviewDAO;
import com.shop.shopservice.entity.Review;

@Repository
@Transactional
public class ReviewDAOImpl  implements IReviewDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Review> getAllReview() {
		List<Review> reviewList= entityManager.createNamedQuery("Review.findAll",Review.class).getResultList();
		return reviewList;
	}


	
	@Override
	public List<Review> getReviewByShopId(String shopId) {
		List<Review> reviewList= entityManager.createNamedQuery("Review.findReviewByShopId",Review.class).setParameter("shopId", shopId).getResultList();
		return reviewList;
	}
	
	@Override
	public List<Review> getReviewForUserByShopId(String shopId) {
		List<Review> reviewList =entityManager.createNamedQuery("Review.findReviewUserByShopId",Review.class).setParameter("shopId", shopId).getResultList();
		return reviewList ;
	}
	
	@Override
	public boolean reviewExists(String productId) {
		Review review = entityManager.createNamedQuery("Review.findReviewByProductId",Review.class).setParameter("productId", productId).getResultList().stream().findFirst().orElse(null);;
		return null != review ?Boolean.TRUE:Boolean.FALSE;
	}
	
	@Override
	public void addReview(Review review) {
		entityManager.persist(review);

	}



	@Override
	public Review getReviewByProductId(String productId) {
		Review review= entityManager.createNamedQuery("Review.findByProductId",Review.class).setParameter("productId", productId).getSingleResult();
		return review;
	}



	@Override
	public void updateReview(Review review) {
		entityManager.merge(review);
		
	}



	
}
