package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ICommentsDAO;
import com.shop.shopservice.entity.Comments;

/**
 * @author Avinash
 *
 */
@Repository
@Transactional
public class CommentsDaoImpl implements ICommentsDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	public List<Comments> listComments() {
		return (List<Comments>) entityManager.createNamedQuery("Comments.findAll", Comments.class).getResultList();
	}

	public Comments getComments(int id) {
		return entityManager.find(Comments.class, new Long(id));	
	}

	public void addComments(Comments comments) {
		entityManager.persist(comments);
	}

	public void updateComments(Comments comments) {
		entityManager.merge(comments);
	}

	@Override
	public boolean deleteComments(int id) {
		return false;
	}

	@Override
	public List<Comments> findCommentsByTopics(int topicsId) {
	return (List<Comments>) entityManager.createNamedQuery("Comments.findByTopics", Comments.class).setParameter("topicsId", topicsId).getResultList();
	}

	

}