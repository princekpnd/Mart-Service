package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Article;

public interface ArticleDao {

	public List<Article> listArticles();

	public Article getArticle(int id);

	public void addArticle(Article article);

	public void updateArticle(Article article);

	public boolean deleteArticle(int id);

	public List<Article> findArticleByType(int type);

}