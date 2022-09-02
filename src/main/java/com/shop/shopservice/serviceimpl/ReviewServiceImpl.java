package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IReviewDAO;
import com.shop.shopservice.entity.Review;
import com.shop.shopservice.service.IReviewService;
@Repository
@Transactional
public class ReviewServiceImpl implements IReviewService{
	
	

	@Autowired
	IReviewDAO reviewDao;
	
	@Override
	public List<Review> getAllReview() {
		return reviewDao.getAllReview();
	}

	@Override
	public List<Review> getReviewByShopId(String shopId) {
		return  reviewDao.getReviewByShopId(shopId);
	}
	
	@Override
	public List<Review> getReviewForUserByShopId(String shopId) {
		return reviewDao.getReviewForUserByShopId(shopId);
	}


	@Override
	public boolean reviewExists(String ProductId) {
		return reviewDao.reviewExists( ProductId);
	}
	
	public boolean createReview(Review review) {

		if (reviewExists(review.getProductId())) {
			return false;
		} else {

			reviewDao.addReview(review);
			review = null;
			return true;
		}
	}

	@Override
	public Review getReview(String productId) {
		return  reviewDao.getReviewByProductId(productId) ;
	}

	@Override
	public boolean updateReview(Review review) {
		 reviewDao.updateReview(review);
		 return true;
	}

	

}
