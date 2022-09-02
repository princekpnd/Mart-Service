package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Article;

public interface ArticleService {

	public List<Article> getAllArticle();
	
	public Article findArticleById(int id);
	
	public void addArticle(Article article);
	
	public void saveArticle(Article article);
	
	public boolean deleteArticle(int id);
	public List<Article> findArticleByType(int type);
	
}