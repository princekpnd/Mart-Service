package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IVariantStockDAO;
import com.shop.shopservice.Idao.IWithdrawDAO;
import com.shop.shopservice.entity.VariantStock;

@Repository
@Transactional
public class VariantStockDAOImpl implements IVariantStockDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VariantStock> getAll() {
		List<VariantStock> stockList = entityManager.createNamedQuery("VariantStock.findAll", VariantStock.class)
				.getResultList();
		return stockList;
	}

	@Override
	public VariantStock getById(int id) {
		VariantStock stock = entityManager.createNamedQuery("VariantStock.findById", VariantStock.class)
				.setParameter("id", id).getSingleResult();
		return stock;
	}

	@Override
	public boolean exitsStock(int slotNumber, int variantId) {
		VariantStock stock1 = entityManager.createNamedQuery("VariantStock.cheackCreated", VariantStock.class)
				.setParameter("slotNumber", slotNumber).setParameter("variantId", variantId).getResultList().stream()
				.findFirst().orElse(null);
		;
		return null != stock1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addStock(VariantStock variantStock) {
		entityManager.persist(variantStock);

	}

	@Override
	public VariantStock getBySlotNumberAndVariantId(int slotNumber, int variantId) {
		VariantStock stock = entityManager.createNamedQuery("VariantStock.findBySlotNumber", VariantStock.class)
				.setParameter("slotNumber", slotNumber).setParameter("variantId", variantId).getSingleResult();
		return stock;
	}

	@Override
	public void updateStock(VariantStock variantStock) {
	entityManager.merge(variantStock);
		
	}

	@Override
	public List<VariantStock> getBySlotNumber(String slotNumber) {
		/////// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VariantStock> getBySlotNumber(int slotNumber) {
		List<VariantStock> variantStocksList = entityManager.createNamedQuery("VariantStock.getBySlotNumber",VariantStock.class).setParameter("slotNumber", slotNumber).getResultList();
		return variantStocksList;
	}

	@Override
	public VariantStock getDeActiveForExcel(int variantId) {
		VariantStock variantStock = entityManager.createNamedQuery("VariantStock.getDeActiveForExcel",VariantStock.class).setParameter("variantId", variantId).getSingleResult();
		return variantStock;
	}

	@Override
	public VariantStock getByVariantId(int variantId) {
	VariantStock stock = entityManager.createNamedQuery("variantStock.getByVariantStock", VariantStock.class).setParameter("variantId", variantId).getSingleResult();
		return stock;
	}
}
