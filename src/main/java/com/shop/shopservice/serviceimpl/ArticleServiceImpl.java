package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.ArticleDao;
import com.shop.shopservice.entity.Article;
import com.shop.shopservice.service.ArticleService;

@Repository
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	
	public List<Article> getAllArticle() {
		return articleDao.listArticles();
	}

	public Article findArticleById(int id) {
	return articleDao.getArticle(id);
	}

	public void addArticle(Article article) {
	articleDao.addArticle(article);

	}

	public void saveArticle(Article article) {
		articleDao.updateArticle(article);

	}

	public boolean deleteArticle(int id) {
		return articleDao.deleteArticle(id);
	}

	@Override
	public List<Article> findArticleByType(int type) {
	return articleDao.findArticleByType(type);
	}

}