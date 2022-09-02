package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IMessageDao;
import com.shop.shopservice.entity.Message;

@Repository
@Transactional
public class MessageDaoImpl implements IMessageDao{

	@PersistenceContext	
	private EntityManager entityManager;	

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllMessage() {
		return (List<Message>) entityManager.createNativeQuery("select * from Message", Message.class).getResultList();
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
	public void addMessage(Message message) {
		entityManager.persist(message);
	}

}