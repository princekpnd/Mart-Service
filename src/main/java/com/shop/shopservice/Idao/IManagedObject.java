package com.shop.shopservice.Idao;

import com.shop.shopservice.entity.ManagedObject;

public interface IManagedObject {

	public boolean isManagedObjectExist(int id);

	public void createManagedObject(ManagedObject mo);

	public Object getManagedObject(int id);
}
