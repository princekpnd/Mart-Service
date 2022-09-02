package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.VariantStock;

public interface IVariantStockDAO {
	
	List<VariantStock> getAll();
	
	VariantStock getById(int id);
	
	VariantStock getDeActiveForExcel(int variantId);
	
	VariantStock getByVariantId(int variantId);
	
	List<VariantStock> getBySlotNumber(String slotNumber);
	
	VariantStock getBySlotNumberAndVariantId(int slotNumber, int variantId );
	
	boolean exitsStock(int slotNumber, int variantId);
	
	void updateStock(VariantStock variantStock);
	
	List<VariantStock> getBySlotNumber(int slotNumber);
	
	void addStock(VariantStock variantStock);

}
