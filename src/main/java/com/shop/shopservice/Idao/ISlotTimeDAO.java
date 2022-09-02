package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.SlotTime;

public interface ISlotTimeDAO {
	
	List<SlotTime> getAll();
	
	void createSlotTime(SlotTime slotTime);
	
	void updateSlotTime(SlotTime slotTime);
	
	List<SlotTime> getBySlotTime(int slotTime);

}
