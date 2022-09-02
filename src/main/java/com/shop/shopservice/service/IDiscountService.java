package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Discount;

public interface IDiscountService {
List<Discount> getAll();

public Discount getById(int discountId);

public List<Discount> getByDiscountType(String offerType);

public Discount getByLable(String lable);
 
public boolean updateDiscount(Discount discount);
}
