package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.LookUpType;

public interface ILookUpType {

	public List<LookUpType> findAllLookUpType();

	public LookUpType findLookUpTypeByName(String name);

}
