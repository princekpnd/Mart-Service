package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Discount;

public interface IDiscountDAO {
List<Discount> getAll();

Discount getById(int discountId);

List<Discount> getByDiscountType(String offerType);

void updateDiscount(Discount discount);

Discount getByLable(String lable);
}
