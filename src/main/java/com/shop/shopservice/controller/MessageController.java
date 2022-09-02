package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.entity.Message;
import com.shop.shopservice.service.MessageService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

	private final Logger log = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	MessageService messageService;
	
	@GetMapping("getallmessage")
	public ResponseEntity<List<Message>> findAllArticle() {
		List<Message> listMessage = messageService.findAllMessage();
		return new ResponseEntity<List<Message>>(listMessage, HttpStatus.OK);
	}
	
	@GetMapping("getallmessage/usertype/{userType}")
	public ResponseEntity<List<Message>> findAllArticleByUserTYpe(@PathVariable("userType") String userType) {
		List<Message> listMessage = messageService.findAllMessage();
		return new ResponseEntity<List<Message>>(listMessage, HttpStatus.OK);
	}
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create message: {}", json.get("text"));
		Map<String, String> response = new HashMap<String, String>();
		Message message = new Message(Integer.parseInt(json.get("from")), Integer.parseInt(json.get("to")),
				json.get("text"));
		message.setCreatedAt(json.get("createdat"));
		response.put("text", json.get("text"));
			messageService.addMessage(message);
			response.put("status", Strings.EMPTY + true);
			response.put("description",
					"Message created successfully with given text");
			message = null;
		return ResponseEntity.ok().body(response);
	}
}
