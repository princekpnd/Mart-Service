package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Review;

public interface IReviewService {
	List<Review> getAllReview();

	public List<Review> getReviewByShopId(String shopId);

	public List<Review> getReviewForUserByShopId(String shopId);

	public boolean reviewExists(String productId);

	public boolean createReview(Review review);

	public Review getReview(String productId);

	public boolean updateReview(Review review);

}
