package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IImageDAO;
import com.shop.shopservice.Idao.IShopImageDAO;
import com.shop.shopservice.entity.Bank;
import com.shop.shopservice.entity.Image;
import com.shop.shopservice.entity.ShopImage;

@Repository
@Transactional
public class ShopImageDAOImpl implements IShopImageDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<ShopImage> getAllShopImage() {
		List<ShopImage> imageList = entityManager.createNamedQuery("ShopImage.findAllImage",ShopImage.class).getResultList();
		return  imageList;
	}

	@Override
	public ShopImage getShopImageById(int id) {
		ShopImage image = entityManager.createNamedQuery("ShopImage.findImageById",ShopImage.class).setParameter("id", id).getSingleResult();
		return image;
	}

	@Override
	public List<ShopImage> getShopImageByShopId(String shopId) {
		List<ShopImage> imageList = entityManager.createNamedQuery("ShopImage.findByShopId",ShopImage.class).setParameter("shopId", shopId).getResultList();
		return imageList;
	}

	@Override
	public List<ShopImage> getShopImageByShopIdAndProductId(String shopId, int productId) {
		List<ShopImage> imageList = entityManager.createNamedQuery("ShopImage.findByShopIdAndProductId",ShopImage.class).setParameter("shopId", shopId).setParameter("productId", productId).getResultList();
		return imageList;
	}

	@Override
	public void updateShopImage(ShopImage image) {
		entityManager.merge(image);
		
	}

	@Override
	public boolean shopImageExists(String shopId) {
		ShopImage image = entityManager.createNamedQuery("ShopImage.findImageByShopId",ShopImage.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);;
		return null != image ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addShopImage(ShopImage image) {
		entityManager.persist(image);
		
	}

	@Override
	public boolean deleteImage(int id) {
		Query query = entityManager.createQuery("delete ShopImage where id = " + id);			
		query.executeUpdate();
		entityManager.flush();		
	return true;
	}	
	

}
