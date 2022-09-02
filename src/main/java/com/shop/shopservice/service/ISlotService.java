package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Slot;

public interface ISlotService {
	
	public Slot getById(int id);
	
	public Slot getDeactiveForExcelFile(int id);
	
	public List<Slot> getIdByVariant(int venderId);
	
	public Slot getItemListById(int itemListId);
	
	List<Slot> getAll();
	
	public boolean addVariantByVariantId(Slot slot,int itemListId);
	
	public boolean checkExist(int slotNumber,String shopId, int venderId);
	
	public List<Slot> getByShopId(String shopId);
	
	public Slot getSlotNumberAndShopId(int slotNumber, String shopId);
	
	public List<Slot> getByVenderId(int venderId);
	
	public Slot Addvariant(int id,int itemListId);
	
	public List<Slot> getAllDetails(String nameOfSeller,String mobileNo);
	
	public boolean slotExit(String billingNumber, int venderId);
	
	public boolean updeteVariant(Slot slot);
	
	public boolean updeteSlot(Slot slot);
	
	public boolean slotCreate(Slot slot);

}
