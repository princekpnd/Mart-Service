package com.shop.shopservice.transformer;

import com.shop.shopservice.entity.ManagedObject;

/**
 * @author Avinash
 *
 */
public interface IArticleTransformer {

	public Object transformBusinessObject(ManagedObject managedObject);

	public ManagedObject transformManagedObject(Object object);
}
