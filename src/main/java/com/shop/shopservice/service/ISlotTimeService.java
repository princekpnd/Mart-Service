package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.SlotTime;

public interface ISlotTimeService {
	
	List<SlotTime> getAll();
	
	public List<SlotTime> getBySlotTime(int slotTime);
	
	public boolean createSlotTime(SlotTime slotTime);
	
	public boolean updateSlotTime(SlotTime slotTime);

}
