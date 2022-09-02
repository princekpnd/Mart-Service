package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Image;

public interface IImageDAO {

	public List<Image> getAllImage();

	public Image getImageById(int id);

	public List<Image> getImageByShopId(String shopId);

	public List<Image> getImageByShopIdAndProductId(String shopId, int productId);

	List<Image> getImageByProductId(int productId);

	void updateImage(Image image);

	boolean imageExists(String shopId);

	void addImage(Image image);

	boolean deleteImage(int id);

}
