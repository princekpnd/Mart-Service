package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.VariantStock;

public interface IVariantStockService {
	
	List<VariantStock> getAll();
	
	public VariantStock  getById(int id);
	
	public VariantStock getByVariantId(int variantId);
	
	public VariantStock getDeActiveForExcel(int variantId);
	
	public VariantStock getBySlotNumberAndVariantId(int slotNumber,int variantId );
	
	public List<VariantStock> getBySlotNumber(int slotNumber);
	
	public boolean exitsStock(int slotNumber, int variantId);
	
	public boolean stockCreated(VariantStock variantStock);
	
	public boolean updateStock(VariantStock variantStock);

}
