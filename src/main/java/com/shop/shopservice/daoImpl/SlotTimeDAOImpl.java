package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.ISlotDAO;
import com.shop.shopservice.Idao.ISlotTimeDAO;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.SlotTime;
@Repository
@Transactional
public class SlotTimeDAOImpl implements ISlotTimeDAO{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SlotTime> getAll() {
		List<SlotTime> slotTime = entityManager.createNamedQuery("SlotTime.getAll",SlotTime.class).getResultList();
		return slotTime;
	}

	@Override
	public void createSlotTime(SlotTime slotTime) {
		entityManager.persist(slotTime);
		
	}

	@Override
	public void updateSlotTime(SlotTime slotTime) {
		entityManager.merge(slotTime);
	}

	@Override
	public List<SlotTime> getBySlotTime(int slotTime) {
		List<SlotTime> slot = entityManager.createNamedQuery("SlotTime.getBySlot", SlotTime.class).setParameter("slotTime", slotTime).getResultList();
		return slot;
	}
}
