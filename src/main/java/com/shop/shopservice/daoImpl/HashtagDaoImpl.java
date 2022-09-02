package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IHashtagDAO;
import com.shop.shopservice.entity.Hashtag;

@Repository
@Transactional
public class HashtagDaoImpl implements IHashtagDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	public List<Hashtag> listHashtag() {
		return (List<Hashtag>) entityManager.createNamedQuery("Hashtag.findAll", Hashtag.class).getResultList();
	}

	public Hashtag getHashtag(int id) {
		return entityManager.find(Hashtag.class, new Long(id));	
	}

	public void addHashtag(Hashtag hashtag) {
		entityManager.persist(hashtag);
	}

	public void updateHashtag(Hashtag hashtag) {
		entityManager.merge(hashtag);
	}

	@Override
	public boolean deleteHashtag(int id) {
		return false;
	}

	@Override
	public List<Hashtag> findHashtagByCatagory(int catagoryId) {
	return (List<Hashtag>) entityManager.createNamedQuery("Hashtag.findByType", Hashtag.class).setParameter("catagoryId", catagoryId).getResultList();
	}

	

}