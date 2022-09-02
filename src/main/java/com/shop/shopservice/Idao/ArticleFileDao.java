package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.ArticleFile;

public interface ArticleFileDao {

	public List<ArticleFile> listArticleFiles();

	public ArticleFile getArticleFile(int id);

	public void addArticleFile(ArticleFile articleFile);

	public void updateArticleFile(ArticleFile articleFile);

	public boolean deleteArticleFile(int articleFileId);
}