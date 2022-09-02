package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.ISlotDAO;
import com.shop.shopservice.Idao.ISlotTimeDAO;
import com.shop.shopservice.entity.SlotTime;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.ISlotTimeService;

@Transactional
@Repository
public class SlotTimeServiceImpl implements ISlotTimeService{
	
	@Autowired
	ISlotTimeDAO slotTimeDao;

	@Override
	public List<SlotTime> getAll() {
		return slotTimeDao.getAll();
	}

	@Override
	public boolean createSlotTime(SlotTime slotTime) {
		slotTimeDao.createSlotTime(slotTime);
		return true;
	}

	@Override
	public boolean updateSlotTime(SlotTime slotTime) {
		slotTimeDao.updateSlotTime(slotTime);
		return false;
	}

	@Override
	public List<SlotTime> getBySlotTime(int slotTime) {
		return slotTimeDao.getBySlotTime(slotTime);
	}

}
