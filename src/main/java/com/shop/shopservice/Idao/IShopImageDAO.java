package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.ShopImage;

public interface IShopImageDAO {

	public List<ShopImage> getAllShopImage();

	public ShopImage getShopImageById(int id);

	public List<ShopImage> getShopImageByShopId(String shopId);

	public List<ShopImage> getShopImageByShopIdAndProductId(String shopId, int productId);

	void updateShopImage(ShopImage image);

	boolean shopImageExists(String shopId);

	void addShopImage(ShopImage image);

	boolean deleteImage(int id);

}
