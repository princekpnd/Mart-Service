package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Notification;

public interface INotificationService {	
	List<Notification> getAll();
	
	List<Notification> getByShopId(String shopId);
	
	List<Notification> getNotificationByNotificationType(int notificationType, int notificationFor);
	
	public boolean createNotification(int articleId, int notificationType);
	
	List<Notification> getByArticleIdAndNotiFor(int articleId, int notificationFor);

	List<Notification> getByAdminId(int adminId, int notificationFor);

	List<Notification> getByUserId(int userId, int notificationFor);
}
