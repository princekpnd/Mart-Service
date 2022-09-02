package com.shop.shopservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Notification;
import com.shop.shopservice.service.INotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private ILookUp lookup;

	@GetMapping("/getall")
	public ResponseEntity<List<Notification>> getAll() {
		List<Notification> notificationList = notificationService.getAll();
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}

	@GetMapping("getby/{shopId}")
	public ResponseEntity<List<Notification>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Notification> notificationList = notificationService.getByShopId(shopId);
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}

	@GetMapping("getby/notificationtype/notificationfor/{notificationType}/{notificationFor}")
	public ResponseEntity<List<Notification>> getNotificationByNotificationType(
			@PathVariable("notificationType") int notificationType,
			@PathVariable("notificationFor") int notificationFor) {
		List<Notification> notificationList = notificationService.getNotificationByNotificationType(notificationType,
				notificationFor);
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}

	@GetMapping("getby/articleid/notificationfor/{articleId}/{notificationFor}")
	public ResponseEntity<List<Notification>> getByArticleAndNotificationFor(@PathVariable("articleId") int articleId,
			@PathVariable("notificationFor") int notificationFor) {
		List<Notification> notificationList = notificationService.getByArticleIdAndNotiFor(articleId, notificationFor);
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}
	

	@GetMapping("getby/adminid/{adminId}")
	public ResponseEntity<List<Notification>> getByAdminId(@PathVariable("adminId") int adminId) {
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "ADMIN");
		int notificationFor = lookUp.getLookUpId();
		List<Notification> notificationList = notificationService.getByAdminId(adminId, notificationFor);
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}

	@GetMapping("getby/userid/{userId}")
	public ResponseEntity<List<Notification>> getByUserId(@PathVariable("userId") int userId) {
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");  
		int notificationFor = lookUp.getLookUpId();
		List<Notification> notificationList = notificationService.getByUserId(userId, notificationFor);
		return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> create() {
		Map<String, String> response = new HashMap<String, String>();
		boolean result = notificationService.createNotification(17, 141);
		if (result) {
			response.put("status", String.valueOf(result));
			response.put("description", "Notification has been created successfully.");
		} else {
			response.put("status", String.valueOf(result));
			response.put("description", "Notification has been created successfully.");
		}
		return ResponseEntity.ok().body(response);
	}

}
