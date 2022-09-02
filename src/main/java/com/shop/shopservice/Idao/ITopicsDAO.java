package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Topics;

/**
 * @author Avinash
 *
 */
public interface ITopicsDAO {

	public List<Topics> listAllTopics();

	public Topics getTopics(int id);

	public void addTopics(Topics topics);

	public void updateTopics(Topics topics);

	public boolean deleteTopics(int id);

	public List<Topics> findTopicsByCatagory(int catagoryId);

	public List<Topics> findTopicsByHashTag(int hashtagId);

	public List<Topics> findTopicsByUser(int userId);

	public List<Topics> findTopicsByKeyword(String keyword);

	public List<Topics> findTopicsByUserAndCatagory(int userId, int catagoryId);

	public List<Topics> searchTopics(String keyword);

	public void indexTopics();

	public void favoriteTopics(Topics topics, int userId);

	public void likeTopics(Topics topics, int userId);

	public void disLikeTopics(Topics topics, int userId);

}
