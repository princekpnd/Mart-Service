package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ArticleFileDao;
import com.shop.shopservice.entity.ArticleFile;
@Repository
@Transactional
public class ArticleFileDaoImpl implements ArticleFileDao{

	@PersistenceContext	
	private EntityManager entityManager;
	
	Session session;
	
	@SuppressWarnings("unchecked")
	public List<ArticleFile> listArticleFiles() {
		List<ArticleFile> list;
		list = getSession().createCriteria(ArticleFile.class).list();
				return list;
	}

	public ArticleFile getArticleFile(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addArticleFile(ArticleFile articleFile) {
		entityManager.persist(articleFile);
		
	}

	public void updateArticleFile(ArticleFile articleFile) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteArticleFile(int articleFileId) {
		// TODO Auto-generated method stub
		return false;
	}

	public Session getSession() {
		if (null == session) {
			session = entityManager.unwrap(org.hibernate.Session.class);
			return session;
		} else
			return this.session;
	}
}