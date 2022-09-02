package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ArticleDao;
import com.shop.shopservice.entity.Article;

@Repository
@Transactional
public class ArticleDaoImpl implements ArticleDao{

	@PersistenceContext	
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Article> listArticles() {
		return (List<Article>) entityManager.createNamedQuery("Article.findAll", Article.class).getResultList();
	}

	public Article getArticle(int id) {
		return entityManager.find(Article.class, new Long(id));
	//	return (Article) getSession().createCriteria(Article.class).add(Restrictions.eq("id", new Long(id))).uniqueResult();
		
	}

	public void addArticle(Article article) {
		entityManager.persist(article);
	}

	public void updateArticle(Article article) {
		entityManager.merge(article);
	}

	@Override
	public boolean deleteArticle(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Article> findArticleByType(int type) {
	return (List<Article>) entityManager.createNamedQuery("Article.findByType", Article.class).setParameter("articleType", type).getResultList();
	}


}