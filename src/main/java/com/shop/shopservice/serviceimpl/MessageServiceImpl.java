package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IManagedObject;
import com.shop.shopservice.Idao.IMessageDao;
import com.shop.shopservice.entity.Message;
import com.shop.shopservice.service.MessageService;
@Transactional
@Repository
public class MessageServiceImpl implements MessageService {

	@Autowired
	IMessageDao messageDao;
	
	@Autowired
	EntityManager em;
	@Autowired
	IManagedObject managedObject;
	@Override
	public List<Message> findAllMessage() {
		return messageDao.findAllMessage();
	}
	@Override
	public List<Message> findAllMessageFrom(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Message> findAllMessageTo(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Message> findAllMessageFromTo(int fromId, int sentToId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transactional
	public void addMessage(Message message) {
		messageDao.addMessage(message);
	}
	
	
	
}
