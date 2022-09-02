package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IVariantStockDAO;
import com.shop.shopservice.Idao.IWithdrawDAO;
import com.shop.shopservice.entity.VariantStock;
import com.shop.shopservice.service.IVariantStockService;
import com.shop.shopservice.service.IWithdrawService;

@Transactional
@Repository
public class VariantStockServiceImpl implements IVariantStockService{
	@Autowired
	IVariantStockDAO  variantStockDao;

	@Override
	public List<VariantStock> getAll() {
		return variantStockDao.getAll();
	}

	@Override
	public VariantStock getById(int id) {
		return variantStockDao.getById(id);
	}

	@Override
	public boolean exitsStock(int slotNumber, int variantId) {
		variantStockDao.exitsStock(slotNumber, variantId);
		return true;
	}

	@Override
	public boolean stockCreated(VariantStock variantStock) {
		if(variantStockDao.exitsStock(variantStock.getSlotNumber(), variantStock.getVariantId())) {
			return false;
		}else {
			variantStockDao.addStock(variantStock);
			return true;
		}
		
	}

	@Override
	public VariantStock getBySlotNumberAndVariantId(int slotNumber, int variantId) {
		return variantStockDao.getBySlotNumberAndVariantId(slotNumber,variantId );
	}

	@Override
	public boolean updateStock(VariantStock variantStock) {
		variantStockDao.updateStock(variantStock);
		return true;
	}

	@Override
	public List<VariantStock> getBySlotNumber(int slotNumber) {
		return variantStockDao.getBySlotNumber(slotNumber);
	}

	@Override
	public VariantStock getDeActiveForExcel(int variantId) {
		return variantStockDao.getDeActiveForExcel(variantId);
	}

	@Override
	public VariantStock getByVariantId(int variantId) {
		return variantStockDao.getByVariantId(variantId);
	}

//	@Override
//	public List<VariantStock> getBySlotNumber(int slotNumber) {
//	    variantStockDao.getBySlotNumber(slotNumber);
//		return true;
//		}
}
