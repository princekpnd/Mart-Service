package com.shop.shopservice.daoImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.INotificationDAO;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Notification;
import com.shop.shopservice.entity.Purchase;
import com.shop.shopservice.entity.Transaction;
import com.shop.shopservice.entity.Withdraw;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICartService;
import com.shop.shopservice.service.IPurchaseService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.service.IWithdrawService;
import com.shop.shopservice.constants.ServiceConstants;

@Repository
@Transactional
public class NotificationDAOImpl implements INotificationDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICartService cartService;

	@Autowired
	private IPurchaseService purchaseService;

	@Autowired
	private IWithdrawService withdrawService;

	@Autowired
	private ILookUp lookup;

	@Override
	public List<Notification> getAll() {
		List<Notification> notificationList = entityManager.createNamedQuery("Notification.getAll", Notification.class)
				.getResultList();
		return notificationList;
	}

	@Override
	public boolean createNotification(int articleId, int notificationType) {
		Notification notification = new Notification();

		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ORDER_NOTIFICATION");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "RECHARGE_NOTIFICATION");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "WITHDRAW_NOTIFICATION");
		LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "TRANSFER_NOTIFICATION");
		LookUp lookUp4 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
		LookUp lookUp5 = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
		LookUp lookUp6 = lookup.findLookUpIdByName("MILAAN", "PLACED");
		LookUp lookUp7 = lookup.findLookUpIdByName("MILAAN", "PACKED");
		LookUp lookUp8 = lookup.findLookUpIdByName("MILAAN", "SHIPPED");
		LookUp lookUp9 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp10 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		LookUp lookUp11 = lookup.findLookUpIdByName("MILAAN", "ACCEPTED");
		LookUp lookUp12 = lookup.findLookUpIdByName("MILAAN", "REJECTED");
		LookUp lookUp13 = lookup.findLookUpIdByName("MILAAN", "DENIED");
		LookUp lookUp14 = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp15 = lookup.findLookUpIdByName("MILAAN", "BY_DELIVERY_BOY");
		LookUp lookUp16 = lookup.findLookUpIdByName("MILAAN", "SELF_DELIVERY");
		LookUp lookUp17 = lookup.findLookUpIdByName("MILAAN", "COURIER");
		LookUp lookUp18 = lookup.findLookUpIdByName("MILAAN", "SUPER_ADMIN");

		int orderNotification = lookUp.getLookUpId(), rechargeNotification = lookUp1.getLookUpId(),
				withdrawNotification = lookUp2.getLookUpId(), transferNotification = lookUp3.getLookUpId(),
				admin = lookUp4.getLookUpId(), customer = lookUp5.getLookUpId(), placed = lookUp6.getLookUpId(),
				packed = lookUp7.getLookUpId(), shipped = lookUp8.getLookUpId(), delivered = lookUp9.getLookUpId(),
				successful = lookUp10.getLookUpId(), accepted = lookUp11.getLookUpId(),
				rejected = lookUp12.getLookUpId(), denied = lookUp13.getLookUpId(), received = lookUp14.getLookUpId(),
				byDBoy = lookUp15.getLookUpId(), selfPickUp = lookUp16.getLookUpId(), courier = lookUp17.getLookUpId(),
				superAdmin = lookUp18.getLookUpId();

		String summaryDetail = "", shopId = "", shopName = "", userName = "", summaryDetailForAdmin = " ";

		if (notificationType == orderNotification) {
			Cart cart = cartService.getCart(articleId);
			int payableAmount = cart.getPayableAmount(), orderStatus = cart.getOrderStatus(),
					deliveryType = cart.getDeliveryType(), adminId = Integer.parseInt(cart.getAdminId()),
					userId = Integer.parseInt(cart.getUserId()), cartId = cart.getCartId();
			shopName = cart.getShopName();
			shopId = cart.getShopId();
			userName = cart.getUserName();

//			summaryDetail = "your order (" + ") of Rs. " + " has been shipped by" + "Vishal."
//					+ " through through delivery boy " + "Rakesh" + "and mobile number is 123456789";
//
//			summaryDetail = "your order (" + ") of Rs. " + " successfully received by " + "Vishal.";

			if (orderStatus == placed) {
				summaryDetail = shopName + " you have got an order ( ID " + cartId + " ), of Rs. " + payableAmount
						+ " from " + userName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(customer);
				notification.setNotificationFor(admin);

				entityManager.persist(notification);
			} else if (orderStatus == accepted) {
				summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " has been accepted by " + shopName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			} else if (orderStatus == rejected) {
				summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " has been rejected by " + shopName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			} else if (orderStatus == packed) {
				summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " has been packed by " + shopName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			} else if (orderStatus == shipped) {
				if (deliveryType == selfPickUp) {
					summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
							+ " is ready to self pick up at " + shopName + ".";

					notification.setActive(Boolean.TRUE);
					notification.setDeleted(Boolean.FALSE);
					notification.setCreatedOn(new Date());
					notification.setAdminId(adminId);
					notification.setArticleId(cartId);
					notification.setNotificationType(notificationType);
					notification.setShopId(shopId);
					notification.setShopName(shopName);
					notification.setSummeryDetails(summaryDetail);
					notification.setUserId(userId);
					notification.setUserName(userName);
					notification.setUserType(admin);
					notification.setNotificationFor(customer);

					entityManager.persist(notification);
				} else if (deliveryType == byDBoy) {
					String dBoyName = cart.getdBoyName(), dBoyNumber = cart.getdBoyNumber();
					summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
							+ " has been shipped by " + shopName + " through delivery boy " + dBoyName
							+ " and mobile number is " + dBoyNumber + ".";

					notification.setActive(Boolean.TRUE);
					notification.setDeleted(Boolean.FALSE);
					notification.setCreatedOn(new Date());
					notification.setAdminId(adminId);
					notification.setArticleId(cartId);
					notification.setNotificationType(notificationType);
					notification.setShopId(shopId);
					notification.setShopName(shopName);
					notification.setSummeryDetails(summaryDetail);
					notification.setUserId(userId);
					notification.setUserName(userName);
					notification.setUserType(admin);
					notification.setNotificationFor(customer);

					entityManager.persist(notification);
				} else if (deliveryType == courier) {
					String courierName = cart.getCourierName(), trackingId = cart.getShippingId();
					summaryDetail = userName + " your order ( ID " + cartId + " ) of Rs. " + payableAmount
							+ " has been shipped by " + shopName + " through courier " + courierName
							+ " and tracking ID is " + trackingId + ".";

					notification.setActive(Boolean.TRUE);
					notification.setDeleted(Boolean.FALSE);
					notification.setCreatedOn(new Date());
					notification.setAdminId(adminId);
					notification.setArticleId(cartId);
					notification.setNotificationType(notificationType);
					notification.setShopId(shopId);
					notification.setShopName(shopName);
					notification.setSummeryDetails(summaryDetail);
					notification.setUserId(userId);
					notification.setUserName(userName);
					notification.setUserType(admin);
					notification.setNotificationFor(customer);

					entityManager.persist(notification);
				}

			} else if (orderStatus == delivered) {
				summaryDetail = userName + " you have received your order ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " by " + shopName + ".";
				
				summaryDetailForAdmin = shopName + " you have successfully delivered ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " by " + userName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);
				
				entityManager.persist(notification);
				
				notification.setSummeryDetails(summaryDetailForAdmin);
				notification.setUserName(userName);
				notification.setUserType(customer);
				notification.setNotificationFor(admin);

				entityManager.persist(notification);
			} else if (orderStatus == received) {
				summaryDetail = userName + " you have received your order ( ID " + cartId + " ) of Rs. " + payableAmount
						+ " by " + shopName + ".";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			} else if (orderStatus == denied) {
				summaryDetail = "you have received your order (" + ") of Rs. " + " by " + "Vishal.";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			} else if (orderStatus == successful) {
				summaryDetail = "you have received your order (" + ") of Rs. " + " by " + "Vishal.";

				notification.setActive(Boolean.TRUE);
				notification.setDeleted(Boolean.FALSE);
				notification.setCreatedOn(new Date());
				notification.setAdminId(adminId);
				notification.setArticleId(cartId);
				notification.setNotificationType(notificationType);
				notification.setShopId(shopId);
				notification.setShopName(shopName);
				notification.setSummeryDetails(summaryDetail);
				notification.setUserId(userId);
				notification.setUserName(userName);
				notification.setUserType(admin);
				notification.setNotificationFor(customer);

				entityManager.persist(notification);
			}

		} else if (notificationType == rechargeNotification) {
			Purchase purchase = purchaseService.getById(articleId);
			shopId = purchase.getShopId();
			Admin adminData = adminService.getAdminByShopId(shopId);
			userName = adminData.getShopName();
			shopName = "MILAAN";
			int validity = purchase.getPlanValidity(), userId = 0, adminId = purchase.getAdminId();
			summaryDetail = userName + " your plan validity has been extended by " + validity
					+ " days. Thank you for being a part of MILAAN";

			notification.setActive(Boolean.TRUE);
			notification.setDeleted(Boolean.FALSE);
			notification.setCreatedOn(new Date());
			notification.setAdminId(adminId);
			notification.setArticleId(articleId);
			notification.setNotificationType(notificationType);
			notification.setShopId(shopId);
			notification.setShopName(shopName);
			notification.setSummeryDetails(summaryDetail);
			notification.setUserId(userId);
			notification.setUserName(userName);
			notification.setUserType(superAdmin);
			notification.setNotificationFor(admin);

			entityManager.persist(notification);

		} else if (notificationType == withdrawNotification) {
			Withdraw withdraw = withdrawService.getById(articleId);
			shopId = withdraw.getShopId();
			Admin adminData = adminService.getAdminByShopId(shopId);
			userName = adminData.getShopName();
			shopName = "MILAAN";
			int withdrawBalance = withdraw.getWithdrawBalance(), userId = 0, adminId = withdraw.getAdminId();
			summaryDetail = userName + " you have made a withdrawl request of Rs. " + withdrawBalance + ", Request ID ( " + articleId + " ). Your requested amount will be deposited in your given Bank Account within 3 to 6 working days. Thank you for being a part of MILAAN.";

			notification.setActive(Boolean.TRUE);
			notification.setDeleted(Boolean.FALSE);
			notification.setCreatedOn(new Date());
			notification.setAdminId(adminId);
			notification.setArticleId(articleId);
			notification.setNotificationType(notificationType);
			notification.setShopId(shopId);
			notification.setShopName(shopName);
			notification.setSummeryDetails(summaryDetail);
			notification.setUserId(userId);
			notification.setUserName(userName);
			notification.setUserType(superAdmin);
			notification.setNotificationFor(admin);

			entityManager.persist(notification);

		} else if (notificationType == transferNotification) {

		}

		return false;
	}

	@Override
	public List<Notification> getByShopId(String shopId) {
		List<Notification> notificationList= entityManager.createNamedQuery("Notification.findByShopId",Notification.class).setParameter("shopId", shopId).getResultList();
		return notificationList;
	}

	@Override
	public List<Notification> getNotificationByNotificationType(int notificationType, int notificationFor) {
		List<Notification> notificationList = entityManager.createNamedQuery("Notification.findByNotificationType", Notification.class).setParameter("notificationType", notificationType).setParameter("notificationFor", notificationFor).getResultList();
		return notificationList;
	}

	@Override
	public List<Notification> getByArticleIdAndNotiFor(int articleId, int notificationFor) {
		List<Notification> notificationList = entityManager.createNamedQuery("Notification.findByArticleIdAndNotificationFor", Notification.class).setParameter("articleId", articleId).setParameter("notificationFor", notificationFor).getResultList();
		return notificationList != null ? notificationList : null;
	}

	@Override
	public List<Notification> getByAdminId(int adminId, int notificationFor) {
		List<Notification> notificationList = entityManager.createNamedQuery("Notification.findByAdminIdAndNotificationFor", Notification.class).setParameter("adminId", adminId).setParameter("notificationFor", notificationFor).getResultList();
		return notificationList != null ? notificationList : null;
	}

	@Override
	public List<Notification> getByUserId(int userId, int notificationFor) {
		List<Notification> notificationList = entityManager.createNamedQuery("Notification.findByUserIdAndNotificationFor", Notification.class).setParameter("userId", userId).setParameter("notificationFor", notificationFor).getResultList();
		return notificationList != null ? notificationList : null;
	}

}
