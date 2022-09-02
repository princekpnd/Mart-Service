package com.shop.shopservice.Idao;

import java.util.Date;
import java.util.List;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.ProductList;

public interface ICartDAO {
	List<Cart> getAllCart();
	
	List<Cart> getAllAmount(String shopId);

	Cart getCartById(int cartId);
	
	public List<Cart> getCartByDeliveryBoyNumber(String dBoyNumber, String shopId);

	ProductList getProductByProductId(String productId, int cartId);

	public void updateProductList(ProductList plId, float productQuantity);

	List<Cart> getCartForUserByShopId(String shopId);

	public List<Cart> getDeactiveCartByUserIdAndShopId(int userId, String shopId);

	public List<Cart> getCartForOrderByCartId(int cartId);
	
	public List<Cart> getOfflineCart(String shopId, int orderStatus);

	public Cart getOrderStatus(int cartId, String shopId);

	List<Cart> getCartByUserId(String userId);
	
	List<Cart> getCartByShopIdId(String shopId);

	void updateCart(Cart cart);

//	 void addCart(Cart cart);
	boolean cartExists(String userId);
	
	boolean offlinecartExists(String shopId, int orderStatus);

	boolean cartExists(String userId, String shopId, int orderStatus);

	void addCart(Cart cart);

	List<Cart> getCartByUserId(String userId, String shopId, int orderStatus);

	List<Cart> getCartByUserIdAndOrderStatus(String userId, int orderStatus);

	List<Cart> orderDetails(String shopId, String userId, int orderStatus);

	List<Cart> combinedOrderDetails(String userId, int orderStatus);
	
	List<Cart> orderDetailForUser(String userId, int orderStatus);

	List<Cart> combinedOrderForAdmin(String shopId, int orderStatus);
	
	List<Cart> getAllCartForDelivery(String shopId,String dBoyNumber,int orderStatus);
	
	List<Cart> getAllCartByShopId(String shopId);
	
	List<Cart> getAcceptCartList(String shopId,int  orderStatus);
	
	List<Cart> getAllPlacedCartList(String shopId,int  orderStatus);
	
	List<Cart> getAllPackedCartList(String shopId, int orderStatus);
	
	List<Cart> getAllDeliveredCart(String shopId, int orderStatus,int deliveryType);
	
	boolean checkSlotTime(String slotTime);
	
	List<Cart> getBySlotDate(String slotTime);
	
	List<Cart> getBySlotTimeAndDate(String slotTime, Date slotDate);
	
	List<Cart> getBySlotDateAndTime(String slotTime, String slotDate); 
	
	List<Cart> getAllDeliverdCartByDBoy(String shopId, int orderStatus);
	
	List<Cart> getAllRejectedCartByDBoy(String shopId, int orderStatus);
	
	List<Cart>  getAllInformationForDeliveryBoy(String shopId, String dBoyNumber, int orderStatus);

}
