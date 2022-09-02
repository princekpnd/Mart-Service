package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IShopImageDAO;
import com.shop.shopservice.Idao.ISlotDAO;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IShopImageService;
import com.shop.shopservice.service.ISlotService;
import com.shop.shopservice.service.IVariantStockService;

@Transactional
@Repository
public class SlotServiceImpl implements ISlotService{
	
	@Autowired
	ISlotDAO slotDao;
	
	@Autowired
	private IVariantStockService variantStockService;

	@Override
	public Slot getById(int id) {
		return slotDao.getById(id);
	}

	@Override
	public boolean slotExit(String billingNumber, int venderId) {
	return slotDao.slotExit(billingNumber, venderId);
	}

	@Override
	public boolean slotCreate(Slot slot) {
	if(slotExit(slot.getBillingNumber(), slot.getVenderId())) {
		return false;
	}else {
		slotDao.addSlot(slot);
		return true;
	}
		
	}

	@Override
	public boolean updeteSlot(Slot slot) {
		slotDao.updeteSlot(slot);
		return true;
	}

	@Override
	public Slot getItemListById(int itemListId) {
		return slotDao.getItemListById(itemListId);
	}

	@Override
	public List<Slot> getByShopId(String shopId) {
	return slotDao.getByShopId(shopId);
	}

	@Override
	public boolean addVariantByVariantId(Slot slot, int itemListId) {
		return slotDao.addVariantByVariantId(slot,itemListId);		 
	}

	@Override
	public List<Slot> getAllDetails(String nameOfSeller, String mobileNo) {
	return slotDao.getAllDetails(nameOfSeller, mobileNo);
	}

	@Override
	public Slot getSlotNumberAndShopId(int slotNumber, String shopId) {
		
		return slotDao.getSlotNumberAndShopId(slotNumber, shopId);
	}

	@Override
	public Slot Addvariant(int id, int itemListId) {
	return slotDao.Addvariant(id, itemListId);
	}

	@Override
	public boolean updeteVariant(Slot slot) {
		slotDao.updeteVariant(slot);
		return true;
	}

	@Override
	public List<Slot> getAll() {
		return slotDao.getAll();
	}

	@Override
	public List<Slot> getByVenderId(int venderId) {
		List<Slot> slotList = slotDao.getByVenderId(venderId);
		for(int i = 0 ; i< slotList.size(); i++) {
			int slotNumber = slotList.get(i).getId();
			List<VariantStock>  stockList = variantStockService.getBySlotNumber(slotNumber);
			slotList.get(i).setVariantStockList(stockList);
		}
		return slotList;
	}

	@Override
	public List<Slot> getIdByVariant(int venderId) {
		return slotDao.getIdByVariant(venderId);
	}

	@Override
	public boolean checkExist(int slotNumber, String shopId, int venderId) {
		return slotDao.checkExist(slotNumber,shopId, venderId);
	}

	@Override
	public Slot getDeactiveForExcelFile(int id) {
	return slotDao.getDeactiveForExcelFile(id);
	}
}
