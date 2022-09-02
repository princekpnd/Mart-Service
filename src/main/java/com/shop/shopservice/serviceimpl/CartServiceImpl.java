package com.shop.shopservice.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ICartDAO;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.ProductList;
import com.shop.shopservice.entity.UserDeviceID;
import com.shop.shopservice.service.ICartService;

@Repository
@Transactional


public class CartServiceImpl implements ICartService{
	
	@Autowired
	ICartDAO cartDao;
	
	
	@Override
	public List<Cart> getAllCart() {
		return cartDao.getAllCart();
	}
	
	@Override
	public Cart getCart(int cartId) {
		return cartDao.getCartById(cartId);
	}
	
	@Override
	public ProductList getProductByProductId(String productId, int cartId) {
		return cartDao.getProductByProductId(productId, cartId);
	}
   @Override
	public List<Cart> getCartForOrderByCartId(int cartId) {
		return  cartDao.getCartForOrderByCartId(cartId);
	}
	@Override
	public void updateProductList(ProductList plId, float productQuantity) {
		cartDao.updateProductList(plId, productQuantity);		
	}
		@Override
	public List<Cart> getDeactiveCartByUserIdAndShopId(int userId, String shopId) {
		return  cartDao.getDeactiveCartByUserIdAndShopId(userId ,shopId);
	}
	@Override
	public List<Cart> getCartForUserByShopId(String shopId) {
		return cartDao.getCartForUserByShopId(shopId) ;
	}
		@Override
	public List<Cart> getCartByUserId(String userId) {
		return cartDao.getCartByUserId(userId);
	}
//	@Override
//	public List<Cart> getCartByUserId(String userId, String shopId, boolean orderStatus) {
//		return cartDao.getCartByUserId(userId, shopId, orderStatus);
//	}
		@Override
	public boolean cartExists(String userId) {			
		return cartDao.cartExists(userId);
	}
	
//	@Override
//	public List<Cart> getCartByUserId(String userId, String shopId, boolean orderStatus) {
//		return cartDao.getCartByUserId(userId, shopId, orderStatus);
//	}
	@Override
	public boolean updateCart(Cart cart) {
		cartDao.updateCart(cart);
		return true;
	}
	
		@Override
	public boolean cartExists(String userId, String shopId, int orderStatus) {
		return cartDao.cartExists(userId, shopId, orderStatus);
	}
	
//		public boolean createCart(Cart cart) {
//
//		if (cartExists(cart.getUserId())) {
//			return false;
//		} else {
//
//			cartDao.addCart(cart);
//			cart = null;
//			return true;
//		}
//	}
	


	
//	@Override
//	public boolean cartExists(String userId, String shopId, boolean orderStatus) {
//		return cartDao.cartExists(userId, shopId, orderStatus);
//	}
	
//	public boolean createCart(Cart cart) {
//
//		if (cartExists(cart.getUserId())) {
//			return false;
//		} else {
//			cartDao.addCart(cart);			
//			return true;
//		}
//	}

	@Override
	public List<Cart> getCartByUserId(String userId, String shopId, int orderStatus) {
		return cartDao.getCartByUserId(userId, shopId, orderStatus);
	}
	
	@Override
	public List<Cart> getCartByUserIdAndOrderStatus(String userId, int orderStatus) {
		return cartDao.getCartByUserIdAndOrderStatus(userId, orderStatus);
	}
	
	@Override
	public List<Cart> combinedOrderForAdmin(String shopId, int orderStatus) {
		return cartDao.combinedOrderForAdmin(shopId, orderStatus);
	}

	@Override
	public boolean createCart(Cart cart) {
//		if (cartExists(cart.getUserId())) {
//			return false;
//		} else {
			cartDao.addCart(cart);			
			return true;
//		}
	}

	@Override
	public  Cart getOrderStatus(int cartId, String shopId) {
		return cartDao.getOrderStatus(cartId, shopId);
	}

	@Override
	public List<Cart> orderDetails(String shopId, String userId, int orderStatus) {
		return cartDao.orderDetails(shopId, userId, orderStatus);
		
	}

	@Override
	public List<Cart> combinedOrderDetails(String userId, int orderStatus) {
	return cartDao.combinedOrderDetails( userId, orderStatus);
	}

	@Override
	public List<Cart> getCartByShopIdId(String shopId) {
		return cartDao.getCartByShopIdId(shopId);
	}

	@Override
	public List<Cart> orderDetailForUser(String userId, int orderStatus) {
		return cartDao.orderDetailForUser( userId, orderStatus);
	}

	@Override
	public boolean offlinecartExists(String shopId, int orderStatus) {		
		return cartDao.offlinecartExists(shopId, orderStatus);
	}

	@Override
	public List<Cart> getOfflineCart(String shopId, int orderStatus) {
		return cartDao.getOfflineCart(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllAmount(String shopId) {
		return cartDao.getAllAmount(shopId);
	}

	@Override
	public List<Cart> getCartByDeliveryBoyNumber(String dBoyNumber, String shopId) {
		return cartDao.getCartByDeliveryBoyNumber(dBoyNumber, shopId);
	}

	@Override
	public List<Cart> getAllCartForDelivery(String shopId, String dBoyNumber, int orderStatus) {
		return cartDao.getAllCartForDelivery(shopId, dBoyNumber, orderStatus);
	}

	@Override
	public List<Cart> getAllCartByShopId(String shopId) {
		return cartDao.getAllCartByShopId(shopId);
	}

	@Override
	public List<Cart> getAcceptCartList(String shopId, int orderStatus) {
		return cartDao.getAcceptCartList(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllPlacedCartList(String shopId, int orderStatus) {
		return cartDao.getAllPlacedCartList(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllPackedCartList(String shopId, int orderStatus) {
		return cartDao.getAllPackedCartList(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllDeliveredCart(String shopId, int orderStatus, int deliveryType) {
		return cartDao.getAllDeliveredCart(shopId, orderStatus,deliveryType);
	}

	@Override
	public boolean checkSlotTime(String slotTime) {
		return cartDao.checkSlotTime(slotTime);
	}

	@Override
	public List<Cart> getBySlotDate(String slotTime) {
		return cartDao.getBySlotDate(slotTime);
	}

	@Override
	public List<Cart> getBySlotTimeAndDate(String slotTime, Date slotDate) {
		return cartDao.getBySlotTimeAndDate(slotTime, slotDate);
	}

	@Override
	public List<Cart> getBySlotDateAndTime(String slotTime, String slotDate) {
		return cartDao.getBySlotDateAndTime(slotTime,slotDate); 
	}

	@Override
	public List<Cart> getAllDeliverdCartByDBoy(String shopId, int orderStatus) {
		return cartDao.getAllDeliverdCartByDBoy(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllRejectedCartByDBoy(String shopId, int orderStatus) {
		return cartDao.getAllRejectedCartByDBoy(shopId, orderStatus);
	}

	@Override
	public List<Cart> getAllInformationForDeliveryBoy(String shopId, String dBoyNumber, int orderStatus) {
		return cartDao. getAllInformationForDeliveryBoy(shopId, dBoyNumber,orderStatus);
	}



}
