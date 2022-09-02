package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IDiscountDAO;
import com.shop.shopservice.entity.Discount;

@Repository
@Transactional
public class DiscontDAOImpl implements IDiscountDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Discount> getAll() {
	List<Discount> discountList = entityManager.createNamedQuery("Discount.getAll", Discount.class).getResultList();
		return discountList;
	}

	@Override
	public Discount getById(int discountId) {
		Discount discount = entityManager.createNamedQuery("Discount.getById", Discount.class).setParameter("discountId", discountId).getSingleResult();
		return discount;
	}

	@Override
	public List<Discount> getByDiscountType(String offerType) {
		List<Discount> discountsList = entityManager.createNamedQuery("Discount.getByDiscountType", Discount.class).setParameter("offerType", offerType).getResultList();
		return  discountsList;
	}

	

	@Override
	public void updateDiscount(Discount discount) {
		entityManager.merge(discount);
	}

	@Override
	public Discount getByLable(String lable) {
		Discount discount = entityManager.createNamedQuery("Discount.getBylable", Discount.class).setParameter("lable", lable).getSingleResult();
		return discount;
	}
}
