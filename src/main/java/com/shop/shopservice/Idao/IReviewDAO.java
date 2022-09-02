package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Review;

public interface IReviewDAO {

	List<Review> getAllReview();

	List<Review> getReviewByShopId(String shopId);

	List<Review> getReviewForUserByShopId(String shopId);

	boolean reviewExists(String productId);

	void addReview(Review review);

	Review getReviewByProductId(String productId);

	void updateReview(Review review);

}
