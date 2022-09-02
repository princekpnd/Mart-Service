package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IImageDAO;
import com.shop.shopservice.entity.Image;

@Repository
@Transactional
public class ImageDAOImpl implements IImageDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<Image> getAllImage() {
		List<Image> imageList = entityManager.createNamedQuery("Image.findAllImage",Image.class).getResultList();
		return  imageList;
	}

	@Override
	public Image getImageById(int id) {
		Image image = entityManager.createNamedQuery("Image.findImageById",Image.class).setParameter("id", id).getSingleResult();
		return image;
	}

	@Override
	public List<Image> getImageByShopId(String shopId) {
		List<Image> imageList = entityManager.createNamedQuery("Image.findByShopId",Image.class).setParameter("shopId", shopId).getResultList();
		return imageList;
	}

	@Override
	public List<Image> getImageByShopIdAndProductId(String shopId, int productId) {
		List<Image> imageList = entityManager.createNamedQuery("Image.findByShopIdAndProductId",Image.class).setParameter("shopId", shopId).setParameter("productId", productId).getResultList();
		return imageList;
	}

	@Override
	public void updateImage(Image image) {
		entityManager.merge(image);
		
	}

	@Override
	public boolean imageExists(String shopId) {
		Image image = entityManager.createNamedQuery("Image.findImageByShopId",Image.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);;
		return null != image ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addImage(Image image) {
		entityManager.persist(image);
		
	}

	@Override
	public boolean deleteImage(int id) {		
			Query query = entityManager.createQuery("delete Image where id = " + id);			
			query.executeUpdate();
			entityManager.flush();		
		return true;
	}

	@Override
	public List<Image> getImageByProductId(int productId) {
	List<Image> imageList = entityManager.createNamedQuery("Image.findByProductId",Image.class).setParameter("productId", productId).getResultList();
		return imageList;
	}	
	

}
