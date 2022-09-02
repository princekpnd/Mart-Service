package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Notification;

public interface INotificationDAO {
	List<Notification> getAll();

	List<Notification> getByShopId(String shopId);

	List<Notification> getNotificationByNotificationType(int notificationType, int notificationFor);

	List<Notification> getByArticleIdAndNotiFor(int articleId, int notificationFor);

	List<Notification> getByAdminId(int adminId, int notificationFor);

	List<Notification> getByUserId(int userId, int notificationFor);

	public boolean createNotification(int articleId, int notificationType);
}
