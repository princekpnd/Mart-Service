package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IImageDAO;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.service.IImageService;

@Transactional
@Repository

public class ImageServiceImpl implements IImageService {

	@Autowired
	IImageDAO imageDao;

	@Override
	public List<Image> getAllImage() {
		return imageDao.getAllImage();
	}

	@Override
	public Image getImageById(int id) {
		return imageDao.getImageById(id);
	}

	@Override
	public List<Image> getImageByShopId(String shopId) {
		return imageDao.getImageByShopId(shopId);
	}

	@Override
	public List<Image> getImageByShopIdAndProductId(String shopId, int productId) {
		return imageDao.getImageByShopIdAndProductId(shopId, productId);
	}

	@Override
	public boolean updateImage(Image image) {
		imageDao.updateImage(image);
		return true;
	}

	@Override
	public boolean imageExists(String shopId) {
		return imageDao.imageExists(shopId);
	}

	@Override
	public boolean createImage(Image image) {
		imageDao.addImage(image);
		return true;
	}

	@Override
	public boolean deleteImage(int id) {
		imageDao.deleteImage(id);
		return true;
				
	}

	@Override
	public List<Image> getImageByProductId(int productId) {
		return imageDao.getImageByProductId(productId);
	}

}
