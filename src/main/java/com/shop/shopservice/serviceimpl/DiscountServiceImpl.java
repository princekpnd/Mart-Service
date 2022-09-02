package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IDiscountDAO;
import com.shop.shopservice.entity.Discount;
import com.shop.shopservice.service.IDiscountService;

@Repository
@Transactional
public class DiscountServiceImpl implements IDiscountService{
	@Autowired
	IDiscountDAO  discountDAO;

	@Override
	public List<Discount> getAll() {
		return discountDAO.getAll();
	}

	@Override
	public Discount getById(int discountId) {
		return discountDAO.getById(discountId);
	}

	@Override
	public List<Discount> getByDiscountType(String offerType) {
		return  discountDAO.getByDiscountType(offerType);
	}

	@Override
	public boolean updateDiscount(Discount discount) {
		discountDAO.updateDiscount(discount);
		return true;
	}

	@Override
	public Discount getByLable(String lable) {
		return discountDAO.getByLable(lable);
	}

	
}
