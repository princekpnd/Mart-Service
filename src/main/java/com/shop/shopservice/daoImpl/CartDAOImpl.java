package com.shop.shopservice.daoImpl;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.ICartDAO;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.ProductList;

@Repository
@Transactional


public class CartDAOImpl implements ICartDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
	private ILookUp lookup;

	@Override
	public List<Cart> getAllCart() {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAll", Cart.class).getResultList();
		return cartList;
	}

	@Override
	public Cart getCartById(int cartId) {
		return this.entityManager.find(Cart.class, cartId);
	}
	
	@Override
	public ProductList getProductByProductId(String productId, int cartId) {
		ProductList pl = null;
		try {
			pl = (ProductList) entityManager.createNamedQuery("ProductList.fetchProductById", ProductList.class)
					.setParameter("productId", productId).setParameter("cartId", cartId).getSingleResult();
		} catch (NoResultException nre) {
		}
		return null != pl ? pl : null;
	}
	
	@Override
	public List<Cart> getCartForOrderByCartId(int cartId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findCartForOrder",Cart.class).setParameter("cartId", cartId).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getDeactiveCartByUserIdAndShopId(int userId, String shopId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findDeactiveCart",Cart.class).setParameter("userId", userId).setParameter("shopId", shopId).getResultList();
		return cartList;
	}
	
	@Override
	public void updateProductList(ProductList plId, float productQuantity) {
		Query query = entityManager.createQuery("update ProductList set productQuantity =" + productQuantity + "where id = " + plId.getId());
		query.executeUpdate();
	}

	@Override
	public List<Cart> getCartByUserId(String userId, String shopId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findByOrderActiveUserId", Cart.class).setParameter("userId", userId)
				.setParameter("shopId", shopId).setParameter("orderStatus", orderStatus)
				.getResultList();
		return  cartList != null ? cartList : null;
	}
	
	@Override
	public List<Cart> getCartByUserIdAndOrderStatus(String userId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findByDeactiveOrder", Cart.class).setParameter("userId", userId)
				.setParameter("orderStatus", orderStatus)
				.getResultList();
		return null != cartList ? cartList : null;
	}
	
//	@Override
//	public List<Cart> getCartByUserId(String userId, String shopId, boolean orderStatus) {
//		return this.entityManager.createNamedQuery("Cart.findByOrderActiveUserId", Cart.class).setParameter("userId", userId)
//				.setParameter("shopId", shopId).setParameter("orderStatus", orderStatus)
//				.getResultList();
//	}

	@Override
	public void updateCart(Cart cart) {
		entityManager.merge(cart);
	}

	@Override
	public boolean cartExists(String userId, String shopId, int orderStatus) {
		Cart cart = entityManager.createNamedQuery("Cart.findByOrderActiveUserId", Cart.class).setParameter("userId", userId)
				.setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList().stream().findFirst()
				.orElse(null);
		;
		return null != cart ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addCart(Cart cart) {
		entityManager.persist(cart);
	}

	@Override
	public List<Cart> getCartForUserByShopId(String shopId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findCartForUserByShopId", Cart.class)
				.setParameter("shopId", shopId).getResultList();
		return cartList;
	}



	@Override
	public List<Cart> getCartByUserId(String userId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findCartForUserByUserId", Cart.class)
				.setParameter("userId", userId).getResultList();
		return cartList;
	}

	@Override
	public boolean cartExists(String userId) {
		Cart cart = entityManager.createNamedQuery("Cart.findCartForUserByUserId", Cart.class).setParameter("userId", userId)
				.getResultList().stream().findFirst()
				.orElse(null);
		;
		return null != cart ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public  Cart getOrderStatus(int cartId, String shopId) {
		 Cart cart = entityManager.createNamedQuery("Cart.findOrderStatus", Cart.class).setParameter("cartId", cartId).setParameter("shopId", shopId).getSingleResult();
		return cart;
	}

	@Override
	public List<Cart> orderDetails(String shopId, String userId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.orderDetails", Cart.class).setParameter("shopId", shopId).setParameter("userId", userId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> combinedOrderDetails(String userId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.combinedOrderDetails", Cart.class).setParameter("userId", userId).setParameter("orderStatus", orderStatus).getResultList();
		return  cartList;
	}

	@Override
	public List<Cart> combinedOrderForAdmin(String shopId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findOrderForAdmin", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getCartByShopIdId(String shopId) {
		LookUp lookup1 = lookup.findLookUpIdByName("MILAAN", "DEACTIVE");
		int orderStatus = lookup1.getLookUpId();
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllCartByShopId", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> orderDetailForUser(String userId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllCartForUser", Cart.class).setParameter("userId", userId).setParameter("orderStatus", orderStatus).getResultList();
		return  cartList;
	}

	@Override
	public boolean offlinecartExists(String shopId, int orderStatus) {
		Cart cart = entityManager.createNamedQuery("Cart.findOfflineCart",Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList().stream().findFirst()
				.orElse(null);
		return null != cart ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public List<Cart> getOfflineCart(String shopId, int orderStatus) {
	List<Cart> cartList = entityManager.createNamedQuery("Cart.findOfflineByShopId",Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return  cartList;
	}

	@Override
	public List<Cart> getAllAmount(String shopId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllAmount",Cart.class).setParameter("shopId", shopId).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getCartByDeliveryBoyNumber(String dBoyNumber, String shopId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findByDeliveryBoy",Cart.class).setParameter("dBoyNumber", dBoyNumber).setParameter("shopId", shopId).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllCartForDelivery(String shopId, String dBoyNumber, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findForDMobile",Cart.class).setParameter("shopId", shopId).setParameter("dBoyNumber", dBoyNumber).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllCartByShopId(String shopId) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findByShopId",Cart.class).setParameter("shopId", shopId).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAcceptCartList(String shopId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllAcceptCart", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllPlacedCartList(String shopId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllPlacedCart", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllPackedCartList(String shopId, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllPackedCart", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllDeliveredCart(String shopId, int orderStatus, int deliveryType) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllDeliveredCart", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).setParameter("deliveryType", deliveryType).getResultList();
		return cartList;
	}

	@Override
	public boolean checkSlotTime(String slotTime) {
		Cart cart = entityManager.createNamedQuery("Cart.findCartBySlotTime", Cart.class).setParameter("slotTime", slotTime).getResultList().stream().findFirst()
				.orElse(null);
		return null != cart ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public List<Cart> getBySlotDate(String slotTime) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findBySlotTime", Cart.class).setParameter("slotTime", slotTime).getResultList();
				
		return null != cartList ? cartList : null;
	}

	@Override
	public List<Cart> getBySlotTimeAndDate(String slotTime, Date slotDate) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findBySlotTimeAndDate", Cart.class).setParameter("slotTime", slotTime).setParameter("slotDate", slotDate).getResultList();
		
		return null != cartList ? cartList : null;
	}

	@Override
	public List<Cart> getBySlotDateAndTime(String slotTime, String slotDate) {
List<Cart> cartList = entityManager.createNamedQuery("Cart.findByAndDate", Cart.class).setParameter("slotTime", slotTime).setParameter("slotDate", slotDate).getResultList();
		
		return cartList;
	}

	@Override
	public List<Cart> getAllDeliverdCartByDBoy(String shopId, int orderStatus) {
List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllDeliveryByDBoy", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		
		return cartList;
	}

	@Override
	public List<Cart> getAllRejectedCartByDBoy(String shopId, int orderStatus) {
List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllRejectrdByDBoy", Cart.class).setParameter("shopId", shopId).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

	@Override
	public List<Cart> getAllInformationForDeliveryBoy(String shopId, String dBoyNumber, int orderStatus) {
		List<Cart> cartList = entityManager.createNamedQuery("Cart.findAllDeliveryInformatio", Cart.class).setParameter("shopId", shopId).setParameter("dBoyNumber", dBoyNumber).setParameter("orderStatus", orderStatus).getResultList();
		return cartList;
	}

}