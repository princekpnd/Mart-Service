package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.INotificationDAO;
import com.shop.shopservice.entity.Notification;
import com.shop.shopservice.service.INotificationService;

@Transactional
@Repository
public class NotificationServiceImpl implements INotificationService{

	@Autowired
	INotificationDAO  notificationDao;

	@Override
	public List<Notification> getAll() {
		return notificationDao.getAll();
	}

	@Override
	public boolean createNotification(int articleId, int notificationType) {		
		return  notificationDao.createNotification(articleId, notificationType);
	}

	@Override
	public List<Notification> getByShopId(String shopId) {
		return notificationDao.getByShopId(shopId);
	}

	@Override
	public List<Notification> getNotificationByNotificationType(int notificationType, int notificationFor) {
		return notificationDao.getNotificationByNotificationType(notificationType, notificationFor);
	}

	@Override
	public List<Notification> getByArticleIdAndNotiFor(int articleId, int notificationFor) {
		return notificationDao.getByArticleIdAndNotiFor(articleId, notificationFor);
	}

	@Override
	public List<Notification> getByAdminId(int adminId, int notificationFor) {
		return notificationDao.getByAdminId(adminId, notificationFor);
	}

	@Override
	public List<Notification> getByUserId(int userId, int notificationFor) {
		return notificationDao.getByUserId(userId, notificationFor);
	}

}
