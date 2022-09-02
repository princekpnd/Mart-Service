package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ICommentsDAO;
import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.entity.Comments;
import com.shop.shopservice.entity.User;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

	private final Logger log = LoggerFactory.getLogger(CommentsController.class);

	@Autowired
	ICommentsDAO commentsDao;
	
	@Autowired
	IUsersDAO userDao;
	
	@GetMapping("getall")
	public ResponseEntity<List<Comments>> findAllComments() {
		List<Comments> listComments = commentsDao.listComments();
		return new ResponseEntity<List<Comments>>(listComments, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	ResponseEntity<Comments> createComments(@Valid @RequestBody Comments comments, HttpServletRequest request,
			@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.info("Request to create Comments: {}", deviceId);
		if (null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId)) {
			User user = userDao.getUsersById(comments.getUserId());
			comments.setUserDisplayName(user.getName());
			comments.setCreatedOn(new Date());
			commentsDao.addComments(comments);
		}
		return ResponseEntity.ok().body(comments);
	}
	
	@PutMapping("/update")
	ResponseEntity<Comments> updateComments(@Valid @RequestBody Comments comments, HttpServletRequest request,
			@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.info("Request to update Comments: {}", deviceId);
		if (null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId)) {
			User user = userDao.getUsersById(comments.getUserId());
			comments.setUserDisplayName(user.getName());
			commentsDao.updateComments(comments);
		}
		return ResponseEntity.ok().body(comments);
	}
	
	@GetMapping("getcommentsbytopics/{topicsId}")
	public ResponseEntity<List<Comments>> findAllCommentsByTopics(@PathVariable("topicsId") int topicsId) {
		List<Comments> listComments = commentsDao.findCommentsByTopics(topicsId);
		return new ResponseEntity<List<Comments>>(listComments, HttpStatus.OK);
	}
}
