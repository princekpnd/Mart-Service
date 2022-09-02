package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Message;

public interface MessageService {

	public List<Message> findAllMessage();

	public List<Message> findAllMessageFrom(int userId);

	public List<Message> findAllMessageTo(int userId);

	public List<Message> findAllMessageFromTo(int fromId, int sentToId);

	public void addMessage(Message message);
}
