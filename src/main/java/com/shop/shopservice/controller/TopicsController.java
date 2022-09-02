package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.shop.shopservice.Idao.ITopicsDAO;
import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.entity.Topics;
import com.shop.shopservice.entity.User;

/**
 * @author Avinash
 *
 */
@RestController
@RequestMapping("/api/topics")
public class TopicsController {

	private final Logger log = LoggerFactory.getLogger(TopicsController.class);

	@Autowired
	ITopicsDAO topicsDao; 
	
	@Autowired
	IUsersDAO userDao;
	
	@GetMapping("getall")
	public ResponseEntity<List<Topics>> findAllTopics() {
		List<Topics> listTopics = topicsDao.listAllTopics();
		return new ResponseEntity<List<Topics>>(listTopics, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	ResponseEntity<Topics> createTopics(@Valid @RequestBody Topics topics,
			HttpServletRequest request,@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.debug("Request to create Topics: {}", topics);
		if(null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId))
		{
			User user = userDao.getUsersById(topics.getUserId());
			topics.setUserDisplayName(user.getName());
			topics.setCreatedOn(new Date());
		topicsDao.addTopics(topics);
			
		}
		return ResponseEntity.ok().body(topics);
	}
	
	@PutMapping("/update")
	ResponseEntity<Topics> updateTopics(@Valid @RequestBody Topics topics,
			HttpServletRequest request,@RequestHeader("deviceId") String deviceId) throws URISyntaxException {
		log.info("Request to create Topics: {}", topics);
		if(null != deviceId && deviceId.length() > 10 && null != userDao.validateUserByDeviceId(deviceId))
		{
			User user = userDao.getUsersById(topics.getUserId());
			topics.setUserDisplayName(user.getName());
		topicsDao.updateTopics(topics);
		}
		return ResponseEntity.ok().body(topics);
	}
	
	@GetMapping("findByUser/{userId}")
	public ResponseEntity<List<Topics>> findAllTopicsByUser(@PathVariable("userId") int userId) {
		List<Topics> listTopics = topicsDao.findTopicsByUser(userId);
		return new ResponseEntity<List<Topics>>(listTopics, HttpStatus.OK);
	}
	
	@GetMapping("findByCatagory/{catagoryId}")
	public ResponseEntity<List<Topics>> findAllTopicsByCatagory(@PathVariable("catagoryId") int catagoryId) {
		List<Topics> listTopics = topicsDao.findTopicsByCatagory(catagoryId);
		return new ResponseEntity<List<Topics>>(listTopics, HttpStatus.OK);
	}
	
	@GetMapping("findByHashtag/{hashtagId}")
	public ResponseEntity<List<Topics>> findAllTopicsByHashtag(@PathVariable("hashtagId") int hashtagId) {
		List<Topics> listTopics = topicsDao.findTopicsByHashTag(hashtagId);
		return new ResponseEntity<List<Topics>>(listTopics, HttpStatus.OK);
	}
	
	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Topics>> searchAllTopicsBykeyword(@PathVariable("keyword") String keyword) {
		List<Topics> result = new ArrayList<Topics>();
		result = topicsDao.searchTopics(keyword);
		return new ResponseEntity<List<Topics>>(result, HttpStatus.OK);
	}
	
	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		topicsDao.indexTopics();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("like/byuser/{topicsId}/{userId}")
	public ResponseEntity<Topics> liketopicsByUserId(@PathVariable("userId") Integer userId, @PathVariable("topicsId") Integer topicsId) {
		Topics topics = topicsDao.getTopics(topicsId);
		topicsDao.likeTopics(topics, userId);
		return new ResponseEntity<Topics>(topics, HttpStatus.OK);
	}
	
	@GetMapping("dislike/byuser/{topicsId}/{userId}")
	public ResponseEntity<Topics> disliketopicsByUserId(@PathVariable("userId") Integer userId, @PathVariable("topicsId") Integer topicsId) {
		Topics topics = topicsDao.getTopics(topicsId);
		topicsDao.disLikeTopics(topics, userId);
		return new ResponseEntity<Topics>(topics, HttpStatus.OK);
	}
	
	@GetMapping("favorite/byuser/{topicsId}/{userId}")
	public ResponseEntity<Topics> favoriteTopicsByUserId(@PathVariable("userId") Integer userId, @PathVariable("topicsId") Integer topicsId) {
		Topics topics = topicsDao.getTopics(topicsId);
		topicsDao.favoriteTopics(topics, userId);
		return new ResponseEntity<Topics>(topics, HttpStatus.OK);
	}
}
