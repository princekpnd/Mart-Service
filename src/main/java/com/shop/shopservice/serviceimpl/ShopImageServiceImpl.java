package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IShopImageDAO;
import com.shop.shopservice.entity.ShopImage;
import com.shop.shopservice.service.IShopImageService;

@Transactional
@Repository

public class ShopImageServiceImpl implements IShopImageService {

	@Autowired
	IShopImageDAO shopImageDao;

	@Override
	public List<ShopImage> getAllShopImage() {
		return shopImageDao.getAllShopImage();
	}

	@Override
	public ShopImage getShopImageById(int id) {
		return shopImageDao.getShopImageById(id);
	}

	@Override
	public List<ShopImage> getShopImageByShopId(String shopId) {
		return shopImageDao.getShopImageByShopId(shopId);
	}

	@Override
	public List<ShopImage> getShopImageByShopIdAndProductId(String shopId, int productId) {
		return shopImageDao.getShopImageByShopIdAndProductId(shopId, productId);
	}

	@Override
	public boolean updateShopImage(ShopImage image) {
		shopImageDao.updateShopImage(image);
		return true;
	}

	@Override
	public boolean shopImageExists(String shopId) {
		return shopImageDao.shopImageExists(shopId);
	}

	@Override
	public boolean createShopImage(ShopImage image) {
		shopImageDao.addShopImage(image);
		return true;
	}

	@Override
	public boolean deleteImage(int id) {
	return shopImageDao.deleteImage(id);
	}

}
