package com.shop.shopservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.shop.shopservice.entity.ArticleFile;

public interface ArticleFileService {

	public List<ArticleFile> getAllArticleFiles();
	
	public ArticleFile findArticleFileById(int articleFileId);
	
	public void addArticleFile(ArticleFile articleFile);
	
	public void saveArticleFile(ArticleFile articleFile);
	
	public boolean deleteArticleFile(int articleFileId);
	
	public void saveUploadedFiles(List<MultipartFile> files, String guid, String fileType) throws IOException;
	
	public void uploadImages(List<MultipartFile> files, String userType, String userId, String fileType) throws IOException;

	public void uploadProductImage(List<MultipartFile> files, String productId, String shopId) throws IOException;
	
	public void updateProductImage(List<MultipartFile> files, String productId, String shopId, int imageId) throws IOException;
	
	public void uploadShopImage(List<MultipartFile> files, String productId, String shopId) throws IOException;
	
	public void updateShopImage(List<MultipartFile> files, String productId, String shopId, int imageId) throws IOException;
	
	public Resource loadFileAsResource(String fileName);
	
}