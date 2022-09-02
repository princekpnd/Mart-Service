package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Comments;

public interface ICommentsDAO {

	public List<Comments> listComments();

	public Comments getComments(int id);

	public void addComments(Comments comments);

	public void updateComments(Comments comments);

	public boolean deleteComments(int id);

	public List<Comments> findCommentsByTopics(int topicsId);

}
