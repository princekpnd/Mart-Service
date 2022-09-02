package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Bank;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.ShopImage;

public interface IShopImageService {

	List<ShopImage> getAllShopImage();

	public ShopImage getShopImageById(int id);

	public List<ShopImage> getShopImageByShopId(String shopId);

	public List<ShopImage> getShopImageByShopIdAndProductId(String shopId, int productId);

	public boolean updateShopImage(ShopImage image);

	public boolean shopImageExists(String shopId);

	public boolean createShopImage(ShopImage image);

	public boolean deleteImage(int id);

}
