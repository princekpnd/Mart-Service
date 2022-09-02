package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Image;

public interface IImageService {

	List<Image> getAllImage();

	public Image getImageById(int id);

	public List<Image> getImageByShopId(String shopId);

	public List<Image> getImageByShopIdAndProductId(String shopId, int productId);

	public List<Image> getImageByProductId(int productId);

	public boolean updateImage(Image image);

	public boolean imageExists(String shopId);

	public boolean createImage(Image image);

	public boolean deleteImage(int id);

}
