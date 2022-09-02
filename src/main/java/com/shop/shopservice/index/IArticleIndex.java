package com.shop.shopservice.index;

import java.util.List;

import com.shop.shopservice.entity.ManagedObject;

/**
 * @author Avinash
 *
 */
public interface IArticleIndex {

	public boolean createIndex(ManagedObject managedObject);

	public List<ManagedObject> retrieveFromIndex(String Keyword);

	public List<ManagedObject> retrieveFromIndex(String Keyword, ManagedObject managedObject);
}
