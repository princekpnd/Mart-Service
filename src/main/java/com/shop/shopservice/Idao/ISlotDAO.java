package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Slot;

public interface ISlotDAO {
	
	Slot getById(int id);
	
	Slot getItemListById(int itemListId);
	
	Slot getDeactiveForExcelFile(int id);
	
	List<Slot> getIdByVariant(int venderId);
	
	List<Slot> getAll();
	
	List<Slot> getByVenderId(int venderId);
	
	List<Slot> getByShopId(String shopId);
	
	List<Slot> getAllDetails(String nameOfSeller, String mobileNo);
	
	Slot getSlotNumberAndShopId(int slotNumber, String shopId);
	
	Slot Addvariant(int id,int  itemListId);
	
	boolean addVariantByVariantId(Slot slot,int itemListId);
	
	void updeteVariant(Slot slot);
	
	boolean slotExit(String billingNumber, int venderId);
	
	boolean checkExist(int slotNumber,String shopId, int venderId);
	
	void addSlot(Slot slot);
	
	void updeteSlot(Slot slot);

}
