package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Message;

public interface IMessageDao {

	public List<Message> findAllMessage();

	public List<Message> findAllMessageFrom(int userId);

	public List<Message> findAllMessageTo(int userId);

	public List<Message> findAllMessageFromTo(int fromId, int sentToId);

	public void addMessage(Message message);
}
