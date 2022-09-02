package com.shop.shopservice.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IProductListDAO;
import com.shop.shopservice.entity.ProductList;

@Repository
@Transactional
public class ProductListDAOImpl implements IProductListDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<ProductList> getAllProductList() {
		List<ProductList> productList = entityManager.createNamedQuery("ProductList.findAll",ProductList.class).getResultList();
		return productList;
	}

	@Override
	public ProductList getProductListById(int id) {
		return this.entityManager.find(ProductList.class, id);
	}

	@Override
	public List<ProductList> getProductListByUserId(String userId) {
	List<ProductList> productList = entityManager.createNamedQuery("ProductList.findProductListByUserId",ProductList.class).setParameter("userId",userId).getResultList();
		return productList;
	}

	@Override
	public List<ProductList> getProductListByShopId(String shopId) {
	List<ProductList> productList = entityManager.createNamedQuery("ProductList.findProductListByShopId",ProductList.class).setParameter("shopId", shopId).getResultList();
		return productList;
	}

	@Override
	public ProductList getProductList(int id) {
		return this.entityManager.find(ProductList.class, id);
	}

	@Override
	public void updateProductList(ProductList productList) {
		entityManager.merge(productList);
		
	}

	@Override
	public boolean productListExists(String shopId) {
		ProductList productList = entityManager.createNamedQuery("ProductList.findByShopId", ProductList.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);
		return  null != productList ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addProductList(ProductList productList) {
		entityManager.persist(productList);
		
	}

	@Override
	public boolean getProductByProductId(String productId, int cartId) {
		ProductList productList = entityManager.createNamedQuery("ProductList.findProductListByProductId",ProductList.class).setParameter("productId",productId).setParameter("cartId", cartId).getResultList().stream().findFirst().orElse(null);
		return  null != productList ? Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public ProductList getProductByProductIdAndCartId(String productId, int cartId) {
		ProductList productList = entityManager.createNamedQuery("ProductList.findProductListByProductId" ,ProductList.class).setParameter("productId", productId).setParameter("cartId", cartId).getResultList().stream().findFirst().orElse(null);
		return null != productList ? productList : null;
	}

	@Override
	public List<ProductList> getProductListCartId(int cartId){
		List<ProductList> productList = entityManager.createNamedQuery("ProductList.findProductListByCartId" ,ProductList.class).setParameter("cartId", cartId).getResultList();
		return productList;

	}

	@Override
	public boolean deleteProductList(int id) {
		Query query = entityManager.createQuery("delete ProductList where id = " + id);
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

	@Override
	public List<ProductList> getByshopIdCartId(String shopId, int cartId) {
		List<ProductList> productList = entityManager.createNamedQuery("ProductList.findByShopIdCartId" ,ProductList.class).setParameter("shopId", shopId).setParameter("cartId", cartId).getResultList();
		return productList;
	}

	@Override
	public ProductList getReturnInforamation(String productId, int cartId, float returnQuantity) {
		ProductList productList = entityManager.createNamedQuery("ProductList.findReturn",ProductList.class).setParameter("productId", productId).setParameter("cartId", cartId).setParameter("returnQuantity", returnQuantity).getSingleResult();
		return productList;
	}
	
	
//	@Override
//	public boolean productListExists(String shopId) {
//		ProductList productList = entityManager.createNamedQuery("ProductList.findByShopId",ProductList.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);;
//		return null != productList ?Boolean.TRUE:Boolean.FALSE;
//	}

}
